package models
    
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson._
import play.api.libs.iteratee.Iteratee
import scala.concurrent.Future
import play.api.libs.json.Json
    
case class User(email:String, password: String)
    
object User{
    
    //implicit val userFormat = Macros.handler[User]
  implicit val userFormat = Json.format[User]
        
    def findByEmail(email: String): Option[User] = {
        Some(User(email,"123"))   
    }
}