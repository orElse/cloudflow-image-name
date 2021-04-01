package imagename.test.shared

import akka.event.Logging
import akka.stream.Attributes
import cloudflow.akkastream.scaladsl.RunnableGraphStreamletLogic
import imagename.test.model.Job
import cloudflow.akkastream.{AkkaStreamlet, AkkaStreamletLogic}
import cloudflow.streamlets.StreamletShape
import cloudflow.streamlets.avro.AvroInlet


class LoggingStreamlet extends AkkaStreamlet {

  val in = AvroInlet[Job]("jobs")

  override def shape(): StreamletShape = StreamletShape(in)

  override protected def createLogic(): AkkaStreamletLogic = new RunnableGraphStreamletLogic() {
    override def runnableGraph =
      sourceWithCommittableContext(in)
        .log("Received element").withAttributes(Attributes.logLevels(onElement = Logging.InfoLevel))
        .to(committableSink)
  }

}
