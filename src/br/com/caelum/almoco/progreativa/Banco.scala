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

case class Banco extends Actor {

  implicit val timeout: Timeout = Timeout(5 seconds)
  implicit val executionContext = ExecutionContext.Implicits.global

  val c1 = context.actorOf(Props(classOf[Conta], 1, self))
  val c2 = context.actorOf(Props(classOf[Conta], 2, self))
  var id = 0l;

  c1 ! Deposita(nextid, 100.0)
  c1 ! Deposita(nextid, 100.0)
  (c1 ? GetSaldo(nextid)).onComplete {mostraSaldo}

  val transf = nextid
  c1 ! Transfere(transf, 100.0, c2)
  println("oi")

  def receive = {
//        case GetSaldoResp(id, numero, valor) => {
//          println("saldo da conta " + numero + ": " + valor)
//        }
    case Ok(this.transf) => {
      (c2 ? GetSaldo(nextid)).onComplete {mostraSaldo}
    }
    case Ok(id) => {
      println("ok perdido: " + id)
    }
    case perdida => {
      println("msg perdida em " + toString() + ": " + perdida)
    }
  }

  def mostraSaldo: Try[Any] => Unit = { t =>
    t match {
      case Success(GetSaldoResp(id, numero, saldo)) => {
        println("saldo: " + saldo)
      }
      case Failure(e) => {
        println(e)
      }
    }
  }
  
  def nextid = {
    id += 1
    id
  }
  
}