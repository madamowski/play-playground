package controllers
 
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
 
import views._
import models._
   
import reactivemongo.api._  
import reactivemongo.bson._
   
import play.api.libs.json._   
    
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection   
    
object Mongo extends Controller with MongoController{       
    
    def usersCollection: JSONCollection = db.collection[JSONCollection]("test")
   
    def index = Action.async {
        
        val json = Json.obj("name" -> "Kit")           
        
        usersCollection.insert(json).map(lastError =>
            Ok("Mongo LastError: %s".format(lastError)))
       
        //usersCollection.save(User("Don@com","Dom","ppp")).map(lastError =>
        //    Ok("Mongo LastError: %s".format(lastError)))
    }       
       
/*   
    def index = Action.async {
        
        val json = Json.obj(
            "name" -> "Eva")           
         
        usersCollection.insert(json).map(lastError =>
            Ok("Mongo LastError: %s".format(lastError)))
    }
*/
   
/*   
    def getUser(email: String) = Action.async {
       
        var cursor: Cursor[JsObject] = usersCollection.find(Json.obj("email"->email).cursor[JsObject]
                                                           
    }
   
    def addUser(email: String, password: String) = Action.async {
       
        val json = Json.obj(
            "email" -> email,
            "password" => password) 
            
        usersCollection.insert(json).map(lastError =>
            Ok("Mongo LastError: %s".format(lastError)))
    }
*/   
}