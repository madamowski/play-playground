package controllers
    
import play.api._
import play.api.mvc._
import views._
import models._
    
object Application extends Controller with Secured{
    
    def index = withAuth { username => implicit request =>
        Ok(html.index(username))
    }
    
    def user() = withUser { user => implicit request =>
        val email = user.email
        Ok(html.index(email))
    }
}