package controllers

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._
import play.mvc.Http._
import play.api.libs.json.JsString

class TestUserController extends Specification {
  
  "Application" should {
 
    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }
 
    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/signin")).get
 
      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Hello Play Framework")
    }
   
//curl -X POST -H "Content-Type: application/json" -d '{"key":"value"}' http://localhost:9000/users        
    "test" in new WithApplication{
     
//      val json = Json.obj(
//            "description" -> JsString("this is test desc")
//                 )
     
      val json = Json.obj("key"->JsString("value"))
     
      val url = controllers.routes.UserController.createUser().url
      println("Url: "+url) 
      
      val fakeRequest = FakeRequest(
          Helpers.POST,
          url,         
          FakeHeaders(
            Seq(HeaderNames.CONTENT_TYPE->Seq("application/json"))
          ),
          json)
     
       var result = route(fakeRequest).get
      
       status(result) must equalTo(OK)
       contentType(result) must beSome.which(_ == "application/json")

       val response = contentAsString(result)    
       println("Response: "+response)   
       
       val responseNode = Json.parse(response)
       println("Result: "+responseNode)
        
        //println("Json: "+responseNode)  

    }
    
//curl -X GET -H "Content-Type: application/json" -d '{"key":"value"}' http://localhost:9000/users/542b144878781b6ea4e5bf16    
    "test1" in new WithApplication{
     
//      val json = Json.obj(
//            "description" -> JsString("this is test desc")
//                 )
     
      val json = Json.obj("key"->JsString("value"))
     
      val url = controllers.routes.UserController.getUser("542b144878781b6ea4e5bf16").url
      println("Url: "+url) 
      
      val fakeRequest = FakeRequest(
          Helpers.GET,
          url,         
          FakeHeaders(
            Seq(HeaderNames.CONTENT_TYPE->Seq("application/json"))
          ),
          json)
     
       var result = route(fakeRequest).get
             
       status(result) must equalTo(OK)
       contentType(result) must beSome.which(_ == "application/json")
       //contentAsString(result) must contain("ok")
      
       val response = contentAsString(result)    
       println("Response: "+response)   
       
       val responseNode = Json.parse(response)
       println("Result: "+responseNode)      
      
//       val response = contentAsString(result)     
//       println("Response: "+response)
//       
//       result.map{
//         r=>println("Result: "+r)  
//         val responseNode = Json.parse(response)
//         println("Result: "+responseNode)
//        }      
        
        //println("Json: "+responseNode)  

    }
  }
}