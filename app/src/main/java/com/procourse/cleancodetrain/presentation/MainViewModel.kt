package com.procourse.cleancodetrain.presentation

import GetUserNameUseCase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.procourse.cleancodetest.domain.models.SaveUserNameParam
import com.procourse.cleancodetest.domain.models.UserName
import com.procourse.cleancodetest.domain.usecase.SaveUserNameUseCase
import com.procourse.cleancodetrain.data.repository.UserRepositoryImpl
import com.procourse.cleancodetrain.data.storage.sharedPrefs.SharedPrefUserStorage

/*
Во ViewModel контекта быть не должно!!!
 */
class MainViewModel(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase
) : ViewModel() {

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData : LiveData<String> = _resultLiveData
    /*
    вообще в ViewModel в методах не должно быть никаких return. Все взаимодействие идет через
    LiveData
     */

    init {
        Log.e("AAA", "VM created")
    }

    /*
    onCleared позволяет подчистить за собой данные: убрать подписки, подчистить кеш и т.п
     */
    override fun onCleared() {
        Log.e("AAA", "VM cleared")
        super.onCleared()
    }

    fun save(fullName: List<String>) {
        /*
            по клику на SaveDataButton берется текст, закидывается в параметр объекта модели
            SaveUserNameParam и подаем на вход UseCase'а com.procourse.cleancodetest.domain.usecase.SaveUserNameUseCase. Результат выводится
            в строку dataTextView
             */
        val fName = fullName[0]

        val result: Boolean = saveUserNameUseCase.execute(
            param =
            if (fName.isEmpty() || fullName.size == 1)
                SaveUserNameParam(firstName = fName, lastName = "")
            else {
                val lName = fullName[1]
                SaveUserNameParam(firstName = fName, lastName = lName)
            }
        )
        _resultLiveData.value = "Save result = $result"

        /* можно конечно же подать этот текст напрямую в поле, но тогда не будет понимания,
        что это за текст. Эта проблема решается наличием модели данных в которой обозначено,
        что этот текс тявляется ex. именем пользователя. В этом случае логика приложения будет
        быстрее пониматься (скорее всего оно будет весьма большим). Не всегда короткий код
        является понятным
         */
    }

    fun load() {
        //по клику на GetDataButton в текстовом поле выводится объект userName
        val userName: UserName = getUserNameUseCase.execute()
        _resultLiveData.value =  "${userName.firstName} ${userName.lastName}"
        /* основная задача presentation слоя - вывод данных и забор данных от пользователя.
        Более серьезной логики в нем быть НЕ ДОЛЖНО!!!
         */
    }
}