package com.procourse.cleancodetest.domain.repository

import com.procourse.cleancodetest.domain.models.SaveUserNameParam
import com.procourse.cleancodetest.domain.models.UserName

/* здесь будет происходить связывание domain с data слоем, но так, чтобы domain от data не зависил,
т.е будет создан интерфейс, реализация которого будет находиться в data
 */
interface UserRepository {

    fun saveName(saveParam: SaveUserNameParam): Boolean

    fun getName(): UserName

}