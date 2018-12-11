package logs.scanner

import java.io.{BufferedReader, InputStreamReader}
import java.util.zip.{GZIPInputStream, ZipInputStream}

import logs.reader.LogReader
import org.apache.spark.SparkContext
import org.apache.spark.input.PortableDataStream
import org.apache.spark.rdd.RDD

object Implicits {

  implicit class ZipSparkContext(val sc: SparkContext) extends AnyVal {

    def readFile(path: String,
                 minPartitions: Int = sc.defaultMinPartitions, patternStr: String): RDD[String] = {

      if (path.endsWith(".zip")) {
        sc.binaryFiles(path, minPartitions)
          .flatMap { case (_: String, content: PortableDataStream) =>
            val zis = new ZipInputStream(content.open)
            Stream.continually(zis.getNextEntry)
              .takeWhile {
                case null => zis.close(); false
                case _ => true
              }
              .filter{entry => entry.getName.contains("backend.log")}
              .flatMap { _ =>
                val lr = new LogReader(new InputStreamReader(zis), patternStr)
                Stream.continually(lr.readRecord()).takeWhile(_ != null)
              }
          }
      } else if (path.endsWith(".gz")) {
        sc.binaryFiles(path, minPartitions)
          .flatMap { case (_: String, content: PortableDataStream) =>
            val zis = new GZIPInputStream(content.open)
            val lr = new LogReader(new InputStreamReader(zis), patternStr)
            Stream.continually(lr.readRecord()).takeWhile(_ != null)
          }
      } else {
        sc.textFile(path, minPartitions)
      }
    }
  }

}