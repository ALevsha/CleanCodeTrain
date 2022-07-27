package com.procourse.cleancodetrain.presentation

import GetUserNameUseCase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.procourse.cleancodetrain.R
import com.procourse.cleancodetrain.data.repository.UserRepositoryImpl
import com.procourse.cleancodetrain.domain.models.SaveUserNameParam
import com.procourse.cleancodetrain.domain.models.UserName
import com.procourse.cleancodetrain.domain.usecase.SaveUserNameUseCase

class MainActivity : AppCompatActivity() {

    /* this в контекст не передаем, т.к он связан с главным экраном.
    Лучше передавать репозиторию applicationContext
    by lazy {...} означает, что объект будет создан только в момент вызова ссылки на него
    (LazyThreadSafetyMode.NONE) - значит, что потоки будут не синхронизированы с основным,
    а будут выполняться в отдельных потоках после*/
    private val userRepository by lazy(LazyThreadSafetyMode.NONE) { UserRepositoryImpl(context = applicationContext) }
    private val getUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) { GetUserNameUseCase(userRepository = userRepository) }
    private val saveUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveUserNameUseCase(userRepository = userRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataTextView = findViewById<TextView>(R.id.dataTextView)
        val dataEditView = findViewById<EditText>(R.id.dataEditText)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val receiveButton = findViewById<Button>(R.id.receiveButton)

        sendButton.setOnClickListener{
            /*
            по клику на SaveDataButton берется текст, закидывается в параметр объекта модели
            SaveUserNameParam и подаем на вход UseCase'а com.procourse.cleancodetrain.domain.usecase.SaveUserNameUseCase. Результат выводится
            в строку dataTextView
             */
            val fullName = dataEditView.text.toString().split(" ")
            val fName = fullName[0]

            val result: Boolean = saveUserNameUseCase.execute(param =
            if(fName.isEmpty() || fullName.size == 1)
                SaveUserNameParam(firsName = fName, lastName = "")
            else {
                val lName = fullName[1]
                SaveUserNameParam(firsName = fName, lastName = lName)
            }
            )
            dataTextView.text = "Save result = $result"

            /* можно конечно же подать этот текст напрямую в поле, но тогда не будет понимания,
            что это за текст. Эта проблема решается наличием модели данных в которой обозначено,
            что этот текс тявляется ex. именем пользователя. В этом случае логика приложения будет
            быстрее пониматься (скорее всего оно будет весьма большим). Не всегда короткий код
            является понятным
             */
        }

        receiveButton.setOnClickListener {
            //по клику на GetDataButton в текстовом поле выводится объект userName
            val userName: UserName = getUserNameUseCase.execute()
            dataTextView.text = "${userName.firstName} ${userName.lastName}"
            /* основная задача presentation слоя - вывод данных и забор данных от пользователя.
            Более серьезной логики в нем быть НЕ ДОЛЖНО!!!
             */
        }
    }
}