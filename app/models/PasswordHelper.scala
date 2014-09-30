package models

import com.typesafe.config.ConfigFactory
import com.github.t3hnar.bcrypt._

object PasswordHelper {
 
  def create(password: String) : String = {
    password.bcrypt(ConfigFactory.load().getString("application.salt"))
  }
 
  def check(password: String, encryptedPassword: String) : Boolean = {
    password.isBcrypted(encryptedPassword)
  }
}