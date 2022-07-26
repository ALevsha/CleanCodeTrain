package com.procourse.cleancodetrain.presentation

import GetUserNameUseCase
import SaveUserNameUseCase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.procourse.cleancodetrain.R
import com.procourse.cleancodetrain.domain.models.SaveUserNameParam
import com.procourse.cleancodetrain.domain.models.UserName

class MainActivity : AppCompatActivity() {

    private val getUserNameUseCase = GetUserNameUseCase()
    private val saveUserNameUseCase = SaveUserNameUseCase()

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
            SaveUserNameParam и подаем на вход UseCase'а SaveUserNameUseCase. Результат выводится
            в строку dataTextView
             */
            val text = dataEditView.text.toString()
            val params = SaveUserNameParam(name = text)
            val result: Boolean = saveUserNameUseCase.execute(param = params)
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