package logs.scanner

import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.sql.{DataFrameReader, SparkSession}

trait InitSpark {
  val spark = SparkSession.builder()
    .appName("Logs Scanner")
    .master("local[*]")
    .config("option", "some-value")
    .getOrCreate()

  val sc = spark.sparkContext
  val sqlContext = spark.sqlContext
  def reader: DataFrameReader = spark.read
    .option("header",true)
    .option("inferSchema", true)
    .option("mode", "DROPMALFORMED")
  def readerWithoutHeader: DataFrameReader = spark.read
    .option("header",true)
    .option("inferSchema", true)
    .option("mode", "DROPMALFORMED")
  private def init(): Unit = {
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)
    LogManager.getRootLogger.setLevel(Level.ERROR)
  }
  init
  def close(): Unit = {
    spark.close
  }
}
