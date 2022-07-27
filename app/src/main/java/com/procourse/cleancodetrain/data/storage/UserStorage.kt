package com.procourse.cleancodetrain.data.storage

import com.procourse.cleancodetrain.data.storage.models.User

interface UserStorage {

    fun save(user: User): Boolean

    fun get(): User
}