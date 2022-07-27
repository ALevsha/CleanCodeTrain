package com.procourse.cleancodetrain.data.repository

import com.procourse.cleancodetrain.data.storage.models.User
import com.procourse.cleancodetrain.data.storage.UserStorage
import com.procourse.cleancodetrain.domain.models.SaveUserNameParam
import com.procourse.cleancodetrain.domain.models.UserName
import com.procourse.cleancodetrain.domain.repository.UserRepository

/*необходимо сохранить имя пользователя и получить имя пользователя
 в data должна лежать реализация интерфейса репозитория, которая находится в domain слое
 репозиторий по своей сути является связующим звеном между domain и data, следовательно в нем
 нельзя заниматься манипуляциями с данными, т.к при расширении репозиторий будет отвечать
 за несколько функций, допустим отправка данных в интернет и сохранение в БД, что недопустимо
 всё это необходимо вывести в отдельные классы, чтобы не нарушать SOLID- принципы.
 Доп модели нагружают доп логику, но дают независимость*/

/*
* если надо добавить функционал отправки данных в сеть, то в параметрах класса
* будет добавлен еще оджин объект, через который будет осущеспвлена отправка.
* */

class UserRepositoryImpl(private val userStorage: UserStorage
/*(example), private val networkApi: NetworkApi*/):UserRepository {// наследуемся от UserRepository из domain-слоя

    // установка реальной реализации. Для использования SharedPreferences нужен context
    override fun saveName(saveParam: SaveUserNameParam): Boolean{
        val user = mapToStorage(saveParam)
        val result = userStorage.save(user)
        /*
        * networkApi.sendData(...)
        * */
        return result
    }

    override fun getName(): UserName{
        val user = userStorage.get()

        val userName = mapToDomain(user)

        return userName
    }

    private fun mapToDomain(user: User): UserName{
        return UserName(firstName = user.firstName, lastName = user.lastName)
    }

    private fun mapToStorage(saveParam: SaveUserNameParam): User{
        return User(firstName = saveParam.firstName, lastName = saveParam.lastName)
    }
}