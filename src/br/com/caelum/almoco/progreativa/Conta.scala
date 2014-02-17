package br.com.caelum.almoco.progreativa

import akka.actor.Actor
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Success
import scala.concurrent.ExecutionContext


case class Conta(numero: Int, banco: ActorRef) extends Actor {
  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val executionContext = ExecutionContext.Implicits.global
  
  var saldo = 0.0
  
  def receive = {
    case Deposita(id, valor) => {
      this.saldo += valor
      sender ! Ok(id)
    }
    case GetSaldo(id) => {
      sender ! GetSaldoResp(id, this.numero, this.saldo)
    }
    case Transfere(id, valor, outra) => {
      this.saldo -= valor
      val future = outra ? Deposita(id, valor)
      val senderOriginal = sender
      future.onComplete {
        case Success(Ok(id)) => {
          senderOriginal ! Ok(id)
        }
      }
    }
    case perdida => {
      println("msg perdida em " + toString() + ": " + perdida)
    }
  }
}

case class Deposita(id: Long, valor: Double)
case class GetSaldo(id: Long)
case class Transfere(id: Long, valor: Double, outra:ActorRef)

case class Ok(id: Long)
case class GetSaldoResp(id: Long, numero:Int, saldo: Double)
