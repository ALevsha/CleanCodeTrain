package com.procourse.cleancodetest.domain.usecase

import com.procourse.cleancodetest.domain.models.SaveUserNameParam
import com.procourse.cleancodetest.domain.repository.UserRepository

/* здесь получается полиморфизм. На вход приходит объект интерфейса, => реализация его неизвестна
 */
class SaveUserNameUseCase(private val userRepository: UserRepository) {
    /*
    Наследуемся от интерфейса UserRepository, чтобы не зависеть от реализации, которая находится
    в data-слое
     */

    /*
    лучше создавать для каждого параметра отдельную модель, т.к будет неудобно, если в 1 модели
    будет много параметров
     */
    fun execute(param: SaveUserNameParam): Boolean{
        val oldUserName = userRepository.getName()

        if (oldUserName.firstName == param.firstName && oldUserName.lastName == param.lastName)
            return true

        val result: Boolean = userRepository.saveName(saveParam = param)
        return result
    }
}
