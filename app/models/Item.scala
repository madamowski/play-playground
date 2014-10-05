package models
    
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson._
import play.api.libs.iteratee.Iteratee
import scala.concurrent.Future
import play.api.libs.json.Json
import play.modules.reactivemongo.json.BSONFormats._
import play.api.libs.json._
import scala.util.Try

case class Item(_id: Option[BSONObjectID], name: String)
    
object Item{
    
  implicit object ItemBSONReader extends BSONDocumentReader[Item]{
    def read(doc: BSONDocument): Item = {
      println("ID CONVERT: "+doc.getAs[BSONObjectID]("_id"))
      Item(
          doc.getAs[BSONObjectID]("_id"),
          doc.getAs[String]("name").get)
    }
  }
  
  implicit object ItemBSONWriter extends BSONDocumentWriter[Item]{
    def write(item: Item): BSONDocument = {
      BSONDocument(
       "_id"-> item._id.getOrElse(BSONObjectID.generate),
       "name"-> item.name)
    }
  }
  
    //implicit val userFormat = Macros.handler[User]
  implicit val itemFormat = Json.format[Item]

//  implicit object BSONObjectIDFormat extends Format[BSONObjectID] {
//    def writes(objectId: BSONObjectID): JsValue = JsString(objectId.toString())
//    def reads(json: JsValue): JsResult[BSONObjectID] = json match {
//      case JsString(x) => {
//        val maybeOID: Try[BSONObjectID] = BSONObjectID.parse(x)
//        if(maybeOID.isSuccess) JsSuccess(maybeOID.get) else {
//          JsError("Expected BSONObjectID as JsString")
//        }
//      }
//      case _ => JsError("Expected BSONObjectID as JsString")
//    }
//  }
  
  
}