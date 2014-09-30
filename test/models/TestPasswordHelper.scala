package models

import org.specs2.mutable.Specification

class TestPasswordHelper extends Specification {
   "PasswordHelper" should {    
     
    "encrypt and check if encrypted" >> {
      val encrypted = PasswordHelper.create("Password")
      PasswordHelper.check("Password",encrypted) must beTrue
      PasswordHelper.check("Password1",encrypted) must beFalse
    }  
   }
}