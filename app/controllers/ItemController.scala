package controllers

import play.api._
import play.api.mvc._
import views._
import models._
import scala.concurrent._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.json.Json
import reactivemongo.api.gridfs.GridFS
import reactivemongo.bson._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.api.gridfs.Implicits.DefaultReadFileReader
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import reactivemongo.api.collections.default.BSONCollection

object ItemController extends Controller with Secured with MongoController {

  def items : JSONCollection = db.collection[JSONCollection]("items");
  
  //def items1 : BSONCollection = db.collection[BSONCollection]("items");
  
  val gridFS = new GridFS(db)
  
    val itemForm = Form(
      mapping(
        "id" -> ignored[Option[BSONObjectID]](None),
        "name" -> text
      )(Item.apply)(Item.unapply)
    )  
    
    def index = Action.async { implicit request =>
      
//      val fi = items.find(Json.obj("name"->"Item1")).one[Item]
//      fi.map{
//        i=>
//          println("TEST A: "+i.get._id)
//          println("TEST B: "+i.get.name)
//      }   
      
//      val query = BSONDocument("name" -> "Item1")
      
//      val futureList2: Future[List[BSONDocument]] =
//      items1.find(query,BSONDocument.empty).
//      cursor[BSONDocument].
//      collect[List]()
//      
//      futureList2.map { list =>
//        list.foreach { doc =>
//          println("found document: " + BSONDocument.pretty(doc))
//        }
//      }
      
      val futureItems = items.find(Json.obj()).cursor[Item] 
      futureItems.collect[List]().map { items =>
        items.map(item=>println(item._id+"-"+item.name))
        Ok(views.html.items(items))
      }.recover {
      case e =>
        e.printStackTrace()
        BadRequest(e.getMessage())
      }
      //Ok(html.item(itemForm))
    }
  
    def newitem = Action { implicit request =>
        Ok(html.item(itemForm))
    }
  
    def item(id: String) = Action { implicit request =>
        Ok(html.item(itemForm))
    }
    
    def delete(id: String) = Action { implicit request =>
        Ok(html.item(itemForm))
    }
  
    def add = Action.async { implicit request =>
      val item = itemForm.bindFromRequest.get         
      val futureResult = items.insert(item)  
     
      futureResult.map(_ => 
        //Ok(html.index(""))
        Redirect(routes.ItemController.index)
      )
    } 
  
//  def addPicture = Action(parse.multipartFormData){ request =>
//    request.body.file("file").map{ file =>
//      Ok("File uploaded")  
//    }.getOrElse{
//      Redirect(routes.FileController.uploadpage).flashing(
//        "error"-> "Missing file"    
//      )
//    }
//  }
  
  def addPicture(id: String) = Action.async(gridFSBodyParser(gridFS)){ request =>
    
    val futureFile = request.body.files.head.ref
    
    val futureUpdate = for {
      file <- futureFile
      // here, the file is completely uploaded, so it is time to update the article
      updateResult <- {
        gridFS.files.update(
          BSONDocument("_id" -> file.id),
          BSONDocument("$set" -> BSONDocument("article" -> BSONObjectID(id))))
      }
    } yield updateResult

    futureUpdate.map {
      case _ => 
        //Redirect(routes.Articles.showEditForm(id))
        Redirect(routes.ItemController.index)
    }.recover {
      case e => InternalServerError(e.getMessage())
    }
  }
  
//  def getAttachment(id: String) = Action.async { request =>
//    // find the matching attachment, if any, and streams it to the client
//    val file = gridFS.find(BSONDocument("_id" -> new BSONObjectID(id)))
//    request.getQueryString("inline") match {
//      case Some("true") => serve(gridFS, file, CONTENT_DISPOSITION_INLINE)
//      case _            => serve(gridFS, file)
//    }
//  }
//
//  def removeAttachment(id: String) = Action.async {
//    gridFS.remove(new BSONObjectID(id)).map(_ => Ok).recover { case _ => InternalServerError }
//  }
  
  
}