package com.procourse.cleancodetrain.domain.repository

import com.procourse.cleancodetrain.domain.models.SaveUserNameParam
import com.procourse.cleancodetrain.domain.models.UserName

/* здесь будет происходить связывание domain с data слоем, но так, чтобы domain от data не зависил,
т.е будет создан интерфейс, реализация которого будет находиться в data
 */
interface UserRepository {

    fun saveName(saveParam: SaveUserNameParam): Boolean

    fun getName(): UserName

}