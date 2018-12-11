package logs.scanner

import java.io.File

import com.typesafe.config.ConfigFactory
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.fs.FileUtil
import org.apache.hadoop.conf.Configuration

import scala.util.control.Breaks._

object Main extends InitSpark {
  def main(args: Array[String]): Unit = {
    import logs.scanner.Implicits.ZipSparkContext
    val conf = ConfigFactory.load
    val patternStr = conf.getString("patternStr")
    var initialized = false

    breakable {
      while (true) {
        val source = if (!initialized && conf.getString("source") != null) conf.getString("source") else {
          print("Enter source: ")
          scala.io.StdIn.readLine
        }
        initialized = true
        if (source == "") break
        val data = sc.readFile(source, patternStr = patternStr)
        FileUtil.fullyDelete(new File("temp"))

        breakable {
          while (true) {
            print("Enter pattern: ")
            val pattern = scala.io.StdIn.readLine
            if (pattern == "") break

            data
              .filter(("(?i)" + pattern).r.findFirstMatchIn(_).nonEmpty)
              .sortBy(r => r, true)
              .coalesce(1)
              .saveAsTextFile("temp")

            val dest = new File(if (conf.getString("destination") != null) conf.getString("destination") else source + ".log")
            if (dest.exists()) dest.delete

            merge("temp", dest.getAbsolutePath)
          }

        }
      }
    }

    close
  }


  def merge(srcPath: String, dstPath: String): Unit = {
    val hadoopConfig = new Configuration
    val hdfs = FileSystem.get(hadoopConfig)
    FileUtil.copyMerge(hdfs, new Path(srcPath), hdfs, new Path(dstPath), true, hadoopConfig, null)
  }

}
