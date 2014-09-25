package models
    
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson._
import play.api.libs.iteratee.Iteratee
import scala.concurrent.Future
    
case class User(email:String, name: String, password: String)
    
object User{
    
    implicit val userFormat = Macros.handler[User]
        
    def findByEmail(email: String): Option[User] = {
        Some(User(email,"Tom","123"))   
    }
}