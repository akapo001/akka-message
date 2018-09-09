
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
/**
  * Created by akapo on 2018/09/08.
  */
object main extends App {
  import MyActor._
  implicit lazy val timeout = Timeout(10 seconds)
  val system = ActorSystem("myActor")
  import system.dispatcher

  val myActor = system.actorOf(MyActor.props)
  myActor ! TellMessage()

  (myActor ? AskMessageReq()).mapTo[AskMessageRes].map {
    case msg: AskMessageRes =>
      println(s"receive ${msg.getClass.getName}")
  }

  Await.result(system.whenTerminated, Duration.Inf)
}
