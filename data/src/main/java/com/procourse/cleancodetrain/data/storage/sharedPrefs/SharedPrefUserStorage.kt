package com.procourse.cleancodetrain.data.storage.sharedPrefs

import android.content.Context
import com.procourse.cleancodetrain.data.storage.UserStorage
import com.procourse.cleancodetrain.data.storage.models.User

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_FIRST_NAME = "firstName"
private const val KEY_LAST_NAME = "lastName"
private const val DEFAULT_LAST_NAME = "Default last name"
private const val DEFAULT_FIRST_NAME = "Default first name"

/*
Этот класс теперь будет заниматься сугубо сохранением данных в локальном хранилище,
по идее здесь не должно быть никаких ветвлений, циклов и т.п. Тупо сохранил и получил.
Логика находится в UseCase'ах
*/

class SharedPrefUserStorage(private val context: Context): UserStorage {

    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun save(user: User): Boolean{
        sharedPreferences.edit().putString(KEY_FIRST_NAME, user.firstName).apply()
        sharedPreferences.edit().putString(KEY_LAST_NAME, user.lastName).apply()
        //.apply() ничего не возвращает (void) => просто вернем true
        return true
    }

    override fun get(): User {
        // м.б nullable => к доп. значению добавляем еще одно, если null через Elvis-оператор
        val firsName = sharedPreferences.getString(KEY_FIRST_NAME, DEFAULT_FIRST_NAME) ?: DEFAULT_FIRST_NAME
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, DEFAULT_LAST_NAME) ?: DEFAULT_LAST_NAME
        return when{
            firsName.isEmpty() -> User(firstName = DEFAULT_FIRST_NAME, lastName = DEFAULT_LAST_NAME)
            firsName.isNotEmpty() && lastName.isEmpty() -> User(firstName = firsName, lastName = DEFAULT_LAST_NAME)
            else -> User(firstName = firsName, lastName = lastName)
        }
    }
}