package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender
import android.widget.TextView as TextView1

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var benderImage: ImageView
    lateinit var sendBtn: ImageView
    lateinit var textTxt: TextView1
    lateinit var messageEt: EditText

    lateinit var benderObj: Bender

    /*
    * Вызывается при первом запуске или перезапуске Activity.
    *
    * здесь задается внешний вид Activity через метод setContentView().
    * инициализируются представления
    * представления связываются с необходимыми данными и ресурсами
    * связываются данные со списками
    *
    * Этот метод также предоставляет Bundle, содержащий ранее сохраненное
    * состояние Activity, если оно было.
    *
    * Всегда сопровождается вызовом onStart().
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        benderImage = iv_bender
        sendBtn = iv_send
        textTxt = tv_text
        messageEt = et_message

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        Log.d("M_MainActivity", "onCreate $status and $question")
        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener { _, actionId, _ ->
            val b1: Boolean = if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendDone()
                true
            } else {
                false
            }
            b1
        }
    }


    /*
    * Если Activity возвращается в приоритетный режим после вызова onStop(),
    * то в этом случае вызывается метод onRestart().
    * Т.е. вызывается после того, как Activity была остановлена и снова была запущена пользователем.
    * Всегда сопровождается вызовом метода onStart().
    *
    * Используется для специальных действий, которые должны выполняться только при повторном запуске Activity.
    * */
    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }


    /*
    * При вызове onStart() окно еще не видно пользователю, но вскоре будет видно.
    * Вызывается непосредственно перед тем, как активность становится видмой пользователю.
    *
    * Чтение из базы данных
    * Запуск сложной анимации
    * Запуск потоков, отслеживание показаний датчиков, запросов GPS, таймеров, сервисов или других процессов,
    * которые нужны исключительно для обновления пользовательского интерфейса
    *
    * Затем следует onResume(), если Activity выходит на передний план.
    * */
    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
    }


    /*
    * Вызывается, когда Activity начнет взаимодействовать с пользователем.
    *
    * Запуск воспроизведения анимации, видео и аудио
    * регистрация любых BroadcastReceiver или других процессов, которые вы освободили/приостановили в onPause()
    * выполнение любых других инициализаций, которые должны происходить, когда Activity вновь активна (камера).
    *
    * Тут должен быть максимально легкий и быстрый код, чтобы приложение оставалось отзывчивым
    * */
    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }


    /*
    * Метод onPause() вызывается после сворачивания текущей активности или перехода к новому.
    * От onPause() можно перейти к вызову либо onResume(), либо onStop().
    *
    * остановка аудио и видео, анимации
    * сохранение состояния пользовательского ввода (легкие процессы)
    * сохранение в DB, если данные должны быть доступны в новой Activity
    * остановка сервисов, подписок, BroadcastReceiver
    *
    * Тут должен быть максимально легкий и быстрый код, чтобы приложение оставалось отзывчивым
    * */
    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }


    /*
    * Метод onStop() вызывается, когда Activity становится невидимым для пользователя.
    * Это может произойти при её уничтожении, или если была запущена другая Activity (существующая или новая),
    * перекрывшая окно текущей Activity.
    *
    * запись в базу данных
    * приостановка сложной анимации
    * приостановка потоков, отслеживания показаний датчиков, запросов к GPS, таймеров, сервисов или других процессов,
    * которые нужны исключительно для обновления пользовательского интерфейса
    *
    * Не вызывается при вызове метода finish() у Activity
    * */
    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }


    /*
    * метод вызывается по окончании работы Activity, при вызове метода finish() или в случае,
    * когда система уничтожает этот экземпляр активности для освобождения ресурсов.
    * */
    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
            messageEt.setText("")
            val (r, g, b) = color
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase
            hideKeyboard()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("STATUS", benderObj.status.name)
        outState.putString("QUESTION", benderObj.question.name)
        Log.d(
            "M_MainActivity",
            "onSaveInstanceState ${benderObj.status.name} and ${benderObj.question.name}"
        )
    }

    private fun sendDone() {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
        hideKeyboard()
    }
}