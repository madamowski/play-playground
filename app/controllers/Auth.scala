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
 
object Auth extends Controller with MongoController{
   
  def users: JSONCollection = db.collection[JSONCollection]("users")
  
  val userForm = Form(
      mapping(
        "email" -> text,
        "password" -> text
      )(User.apply)(User.unapply)
    )   
   
    def signin = Action { implicit request =>
        Ok(html.signin(userForm))
    }
  
    def authenticate = Action.async { implicit request =>
        
      //Logger.debug("login page")
      
      val formUser = userForm.bindFromRequest.get  
      //val dbUser = users.find(selector, projection)
      
      val futureUser = users
      .find(Json.obj("email" -> formUser.email))
      .one[User];
      
      val futureCheck = futureUser.map{
       user=>PasswordHelper.check(formUser.password, user.get.password) 
      }
      
      futureCheck.map{
        isOk=>
          if(isOk) Redirect(routes.Application.index).withSession(Security.username -> formUser.email)
          else{
            //BadRequest(html.login(loginForm.fill(uf))).flashing("failure" -> "wrong password")
            BadRequest(html.signin(userForm.fill(formUser).withGlobalError("wrong password")))
            //Ok(html.login(loginForm.fill(uf)))           
            //Ok(html.login(loginForm.fill(Login("test",""))))           
            //Flash("failure", "wrong password");
            //Redirect(routes.Application.index).flashing("failure"->"wrong password")
          }
      }
      
      //PasswordHelper.check(formUser.password, dbUser.password)
      //futureList.onSuccess()

    // gather all the JsObjects in a list
//    val futureUsersList: Future[List[User]] = cursor.collect[List]()
//
//    // everything's ok! Let's reply with the array
//    futureUsersList.map { persons =>
//      Ok(persons.toString)
//    }
//      
//      val user1 = User(user.email, PasswordHelper.create(user.password))
//      val futureResult = users.insert(user1)
     
      //futureResult.map(_ => Ok)
        //loginForm.bindFromRequest.fold(
        //    formWithErrors => BadRequest(html.login(formWithErrors)),
        //    user => Redirect(routes.Application.index).withSession(Security.username -> user._1)
    
        //Ok(html.index)
      }
 
    def logout = Action {
        Redirect(routes.Auth.signin).withNewSession.flashing(
            "success" -> "You are now logged out"
        )
    }
    
    def signup = Action { implicit request =>   
      Ok(html.signup(userForm))
    }   
   
    def create = Action.async { implicit request =>
      val user = userForm.bindFromRequest.get         
      //val user = User(x._1, lastName, email, PasswordHelper.create(password))
      val user1 = User(user.email, PasswordHelper.create(user.password))
      val futureResult = users.insert(user1)
     
      futureResult.map(_ => 
        //Ok(html.index(""))
        Redirect(routes.Application.index).withSession(Security.username -> user.email)
      )
    } 
 
}
 
 
trait Secured {
 
  def username(request: RequestHeader) = request.session.get(Security.username)
 
  def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.signin)
 
  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }
   
  def withUser(f: User => Request[AnyContent] => Result) = withAuth { username => implicit request =>
    User.findByEmail(username).map { user =>
      f(user)(request)
    }.getOrElse(onUnauthorized(request))
  }
 
}
 