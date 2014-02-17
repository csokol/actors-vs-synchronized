package br.com.caelum.almoco.progreativa

import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import scala.concurrent.duration._
object MainActors extends App {
	val concurrency = Integer.parseInt(args(0))
	
    val timeout = Timeout(10 seconds)
	val system = ActorSystem("almoco")
	
	val banco = system.actorOf(Props(classOf[Banco], concurrency))
	banco ! Start
	
	system.awaitTermination
}