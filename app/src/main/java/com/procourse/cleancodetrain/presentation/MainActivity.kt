package com.procourse.cleancodetrain.presentation

import GetUserNameUseCase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.procourse.cleancodetrain.R
import com.procourse.cleancodetrain.data.repository.UserRepositoryImpl
import com.procourse.cleancodetrain.data.storage.sharedPrefs.SharedPrefUserStorage
import com.procourse.cleancodetest.domain.models.SaveUserNameParam
import com.procourse.cleancodetest.domain.models.UserName
import com.procourse.cleancodetest.domain.usecase.SaveUserNameUseCase

/*
Для нормальной работы ViewModel MainActivity нужно наследовать не от Activity, а от
AppCompatActivity
 */
class MainActivity : AppCompatActivity() {


    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("AAA", "Activity created")

        // фабрика - прослойка для передачи контекста при создании ViewModel
        vm = ViewModelProvider(this, MainViewModelFactory(this))
            .get(MainViewModel::class.java)
        /* ViewModelProvider возвращает ссылку на MainViewModel, если модель
        * уже была создана, иначе создает объект MainViewModel. Т.о MainViewModel
        * является SingleTone'ом в рамках жизненного цикла Activity */

        val dataTextView = findViewById<TextView>(R.id.dataTextView)
        val dataEditView = findViewById<EditText>(R.id.dataEditText)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val receiveButton = findViewById<Button>(R.id.receiveButton)

        /*
        observe - подписка на изменение данных. LiveData сама отпишется в противоположном
        методе, если вызвана в onCreate, то отпишется в onDestroy и т.п
         */
        vm.resultLiveData.observe(this, Observer {
            /*
            Все, что внутри Observer'a будет работать, когда LiveData изменится.
            Также он отработает, если в LiveData есть данные, допустим, при повороте экрана
             */
            dataTextView.text = it
        })

        sendButton.setOnClickListener {
            val fullName = dataEditView.text.toString().split(" ")
            vm.save(fullName)
        }

        receiveButton.setOnClickListener {
            vm.load()
        }
        /*
        Activity полностью делегирует всю логику ViewModel и лишь выводит данные
         */
    }
}