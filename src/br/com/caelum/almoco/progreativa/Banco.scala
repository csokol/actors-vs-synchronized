package br.com.caelum.almoco.progreativa

import akka.actor.Actor
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Success
import scala.concurrent.ExecutionContext
import scala.util.Failure
import scala.util.Try
import scala.util.Success
import scala.collection.mutable.ListBuffer
import akka.actor.ActorRef

case class Banco(val concurrency:Int) extends Actor {

  implicit val timeout: Timeout = MainActors.timeout 
  implicit val executionContext = ExecutionContext.Implicits.global
  var id = 0l;

  val usuarios = ListBuffer[ActorRef]()
  val conta = context.actorOf(Props(classOf[Conta], 1, self))
  var acks = 0;
  var ti: Long = 0;
  var tf: Long = 0;

  (0 until concurrency).foreach { i =>
    val actor = context.actorOf(Props(classOf[Usuario], conta))
    usuarios += actor
  }

  def receive = {
    case Start => {
      ti = System.currentTimeMillis
      usuarios.foreach(_ ! Start)
    }
    case Ok => {
      acks += 1
      if (acks >= concurrency) {
        (conta ? GetSaldo(1)).onComplete {
          case Success(GetSaldoResp(id, numero, saldo)) => {
//            println("concorrencia: " + concurrency)
//            println("saldo final: " + saldo)
            tf = System.currentTimeMillis
            println(concurrency + "," + (tf - ti))
            exit
          }
        }
      }
    }
  }

}

case class Start