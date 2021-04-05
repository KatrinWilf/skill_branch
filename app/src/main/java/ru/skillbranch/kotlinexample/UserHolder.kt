package ru.skillbranch.kotlinexample

import android.provider.ContactsContract
import androidx.annotation.VisibleForTesting

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User {
        return User.makeUser(fullName, email = email, password = password)
            .also {user -> if (map.containsKey(user.login)) {
                throw IllegalArgumentException("User exists")
            }
            }
            .also {
                user -> map[user.login] = user
            }
    }

    fun loginUser(login: String, password: String):String? {
        return map[login.trim()]?.run {
            if (checkPassword(password)) this.userInfo
            else null
        }
    }
    
    fun registerUserByPhone(
        fullName: String,
        phone:String
    ):User {
        return User.makeUser(fullName, phone = phone)
            .also {user -> if (map.containsKey(phone)) {
                throw IllegalArgumentException("User exists")
            }}
                .also { user -> map[phone] = user }
    }

    fun requestAccessCode(phone:String):Unit
    {
        val user:User? = map[phone]
        user?.changeAccessCode()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }
}