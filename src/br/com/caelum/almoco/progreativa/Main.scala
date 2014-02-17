package br.com.caelum.almoco.progreativa

import akka.actor.ActorSystem
import akka.actor.Props

object Main extends App {
	val system = ActorSystem("almoco")
	
	val banco = system.actorOf(Props[Banco])
	
	system.awaitTermination
}