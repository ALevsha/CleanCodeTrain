package com.procourse.cleancodetrain.data.repository

import android.content.Context
import com.procourse.cleancodetrain.domain.models.SaveUserNameParam
import com.procourse.cleancodetrain.domain.models.UserName
import com.procourse.cleancodetrain.domain.repository.UserRepository

//необходимо сохранить имя пользователя и получить имя пользователя
// в data должна лежать реализация интерфейса репозитория, которая находится в domain слое

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_FIRST_NAME = "firstName"
private const val KEY_LAST_NAME = "lastName"
private const val DEFAULT_NAME = "Default last name"

class UserRepositoryImpl(private val context: Context):UserRepository {// наследуемся от UserRepository из domain-слоя

    // установка реальной реализации. Для использования SharedPreferences нужен context
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveName(saveParam: SaveUserNameParam): Boolean{
        if (saveParam.firsName.isEmpty() ||
            (saveParam.firsName.isNotEmpty() && saveParam.lastName.isEmpty())) {
            sharedPreferences.edit().putString(KEY_FIRST_NAME, saveParam.firsName).apply()
            sharedPreferences.edit().putString(KEY_LAST_NAME, DEFAULT_NAME).apply()
        }
        else {
            sharedPreferences.edit().putString(KEY_FIRST_NAME, saveParam.firsName).apply()
            sharedPreferences.edit().putString(KEY_LAST_NAME, saveParam.lastName).apply()
        }

    //.apply() ничего не возвращает (void) => просто вернем true
    return true
}

override fun getName(): UserName{
    // м.б nullable => к доп. значению добавляем еще одно, если null через Elvis-оператор
    val firsName = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
    val lastName = sharedPreferences.getString(KEY_LAST_NAME, DEFAULT_NAME) ?: DEFAULT_NAME

    return UserName(firstName = firsName, lastName = lastName)
}
}