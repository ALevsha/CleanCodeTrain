package com.procourse.cleancodetrain.data.repository

import com.procourse.cleancodetrain.domain.models.SaveUserNameParam
import com.procourse.cleancodetrain.domain.models.UserName
import com.procourse.cleancodetrain.domain.repository.UserRepository

//необходимо сохранить имя пользователя и получить имя пользователя
// в data должна лежать реализация интерфейса репозитория, которая находится в domain слое
class UserRepositoryImpl: UserRepository {// наследуемся от UserRepository из domain-слоя

    override fun saveName(saveParam: SaveUserNameParam): Boolean{

        return true
    }

    override fun getName(): UserName{

        return UserName(firstName = "exampleFName", lastName = "exampleLName")
    }
}