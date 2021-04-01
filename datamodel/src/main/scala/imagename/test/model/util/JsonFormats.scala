package imagename.test.model.util

import imagename.test.model.Job
import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, JsonFormat, deserializationError}

import java.time.Instant

trait InstantJsonSupport extends DefaultJsonProtocol {

  implicit object InstantFormat extends JsonFormat[Instant] {
    def write(instant: Instant) = JsNumber(instant.toEpochMilli)

    def read(json: JsValue): Instant = json match {
      case JsNumber(value) ⇒ Instant.ofEpochMilli(value.toLong)
      case other ⇒ deserializationError(s"Expected Instant as JsNumber, but got: $other")
    }
  }

}

object JsonFormats extends DefaultJsonProtocol with InstantJsonSupport {
  implicit val sensorDataFormat = jsonFormat2(Job.apply)
}
