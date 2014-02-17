package br.com.caelum.almoco.threads

import java.util.concurrent.Executors
import scala.collection.mutable.ListBuffer

object MainThreads extends App {
  
  val concurrency = Integer.parseInt(args(0))
  val pool = Executors.newFixedThreadPool(concurrency)
  val conta = new SyncConta();
  val workers = ListBuffer[Thread]()
  (0 until concurrency).foreach {i=>
	workers += new Thread(new Worker(conta))
  }
  
  val t1 = System.currentTimeMillis()
  workers.foreach(_.start())
  workers.foreach(_.join())
  val tf = System.currentTimeMillis()
  println(concurrency + "," + (tf - t1))

}