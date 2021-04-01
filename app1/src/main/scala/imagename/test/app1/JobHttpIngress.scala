package imagename.test.app1

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import cloudflow.akkastream.AkkaServerStreamlet
import cloudflow.akkastream.util.scaladsl.HttpServerLogic
import cloudflow.streamlets.{RoundRobinPartitioner, StreamletShape}
import cloudflow.streamlets.avro.AvroOutlet

import imagename.test.model.Job
import imagename.test.model.util.JsonFormats._

class JobHttpIngress extends AkkaServerStreamlet {
  val out = AvroOutlet[Job]("out").withPartitioner(RoundRobinPartitioner)

  override def shape(): StreamletShape = StreamletShape.withOutlets(out)

  override def createLogic = HttpServerLogic.default(this, out)

}

