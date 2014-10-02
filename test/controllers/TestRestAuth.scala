package controllers

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._
import play.mvc.Http._
import play.api.libs.json.JsString

class TestRestAuth extends Specification {
  
  "Application" should {
    
//curl -X GET -D - -H "Content-Type: application/json" -d '{"key":"value"}' http://localhost:9000/gettoken
    "gettoken" in new WithApplication{
     
      val json = Json.obj("key"->JsString("value"))
     
      val url = controllers.routes.RestAuth.getToken().url
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

       val authToken = header("X-AUTH-TOKEN",result)
       println("AuthToken: "+authToken)
       
       val response = contentAsString(result)    
       println("Response: "+response)   
       
       val responseNode = Json.parse(response)
       println("Result: "+responseNode)
        
        //println("Json: "+responseNode)  

    }
    
//curl -X GET -D - -H "Content-Type: application/json" -H "X-AUTH-TOKEN: abcded" -d '{"key":"value"}' http://localhost:9000/settoken
    "settoken" in new WithApplication{
     
      val json = Json.obj("key"->JsString("value"))
     
      val url = controllers.routes.RestAuth.setToken().url
      println("Url: "+url) 
      
      val fakeRequest = FakeRequest(
          Helpers.GET,
          url,         
          FakeHeaders(
            Seq(
                HeaderNames.CONTENT_TYPE->Seq("application/json"),
                "X-AUTH-TOKEN"->Seq("abcded")
            )
          ),
          json)
     
       var result = route(fakeRequest).get
      
       status(result) must equalTo(OK)
       contentType(result) must beSome.which(_ == "application/json")
      
       header("X-AUTH-TOKEN",result) must beSome("abcded")
       
       val response = contentAsString(result)    
       println("Response: "+response)   
       
       val responseNode = Json.parse(response)
       println("Result: "+responseNode)
        
        //println("Json: "+responseNode)  

    }
  }
}