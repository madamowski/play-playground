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
import java.util.UUID

object RestAuth extends Controller with MongoController{
 
    val AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    val AUTH_TOKEN = "authToken";
   
    def getToken() = Action.async(parse.json) {  implicit request =>     
      
      val authToken = UUID.randomUUID().toString();
     
      val json = Json.obj("Test" -> "test")
     
      //response().setCookie(AUTH_TOKEN, authToken);
      Future(Ok(Json.toJson(json))
          .withHeaders(AUTH_TOKEN_HEADER->authToken))
    }  
    
    def setToken() = Action.async(parse.json) {  implicit request =>     
      
      request.headers     
      val authToken = request.headers.get("X-AUTH-TOKEN").get     
      
      val json = Json.obj("Test" -> "test")
     
      //response().setCookie(AUTH_TOKEN, authToken);
      Future(Ok(Json.toJson(json))
          .withHeaders(AUTH_TOKEN_HEADER->authToken))
    }
}
