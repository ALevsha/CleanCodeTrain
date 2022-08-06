package com.procourse.cleancodetrain.presentation

import GetUserNameUseCase
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.procourse.cleancodetest.domain.usecase.SaveUserNameUseCase
import com.procourse.cleancodetrain.data.repository.UserRepositoryImpl
import com.procourse.cleancodetrain.data.storage.sharedPrefs.SharedPrefUserStorage

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {

    /*
    Фабрика занимается созданием ViewModel и UseCase'ов и репозиториев
     */

    /* this в контекст не передаем, т.к он связан с главным экраном.
    Лучше передавать репозиторию applicationContext
    by lazy {...} означает, что объект будет создан только в момент вызова ссылки на него
    (LazyThreadSafetyMode.NONE) - значит, что потоки будут не синхронизированы с основным,
    а будут выполняться в отдельных потоках после*/

    /*
    * слои общаются только через публичные интерфейсы
    * */


    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        UserRepositoryImpl(userStorage = SharedPrefUserStorage(context = context))
    }

    // UseCase'ы в конструктор берут только объект интерфейса, который создается через класс реализации???
    private val getUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetUserNameUseCase(
            userRepository = userRepository
        )
    }
    private val saveUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SaveUserNameUseCase(
            userRepository = userRepository
        )
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            getUserNameUseCase = getUserNameUseCase,
            saveUserNameUseCase = saveUserNameUseCase
        ) as T
    }
}