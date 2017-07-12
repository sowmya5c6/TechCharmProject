package wordnet

import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet


object WordNetSpark {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutil")
    val conf = new SparkConf().setAppName("WordNetSpark").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)


    val data=sc.textFile("data/sentenceSample")

    val dd=data.map(f=>{
      val wordnet = new RiWordNet("C:\\Users\\sreel\\IdeaProjects\\WordNet-3.0")
      val farr=f.split(" ")
      getSynoymns(wordnet,"retriever")
    })
    dd.take(1).foreach(f=>println(f.mkString(" ")))
  }
  def getSynoymns(wordnet:RiWordNet,word:String): Array[String] ={
    println(word)
    val pos=wordnet.getPos(word)
    println(pos.mkString(" "))
    val syn=wordnet.getAllSynonyms(word, pos(0), 10)
    syn
  }

}
