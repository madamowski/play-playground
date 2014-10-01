package controllers
 
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import models._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import scala.concurrent.Future
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONObjectID

object UserController extends Controller with MongoController{
   
  def users: JSONCollection = db.collection[JSONCollection]("users")
  
  def getUsers = Action(parse.json) {  implicit request =>
    request.body.validate[User].map{
      case task => Ok(Json.toJson(task))
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
    }
  }
 
  def getUser(id: String) = Action.async(parse.json) {  implicit request =>     

     val futureItem = users.find(Json.obj("_id"->Json.obj("$oid" -> id))).one[User]
     
      futureItem.map{
        user => 
          //val u = User("mail","")
          //Ok(Json.toJson(u))
          Ok(Json.toJson(user))
      }
  }
 
  def createUser = Action.async(parse.json) {  implicit request =>
    //Created
    //Future(Ok(Json.obj("name"->"Bob")))
    val u = User("mail","")
    Future(Ok(Json.toJson(u)))
  }
 
  def updateUser(id: String) = Action(parse.json) {  implicit request =>
    NoContent
  }
 
  def deleteUser(id: String) = Action(parse.json) {  implicit request =>
    NoContent
  }
}

 