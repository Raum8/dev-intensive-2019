package ru.skillbranch.devintensive.ui.profile


import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel


class ProfileActivity : AppCompatActivity() {
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
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
        Log.d("M_ProfileActivity", "onCreate")
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
    }


    /*
    * метод вызывается по окончании работы Activity, при вызове метода finish() или в случае,
    * когда система уничтожает этот экземпляр активности для освобождения ресурсов.
    * */
    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity", "updateTheme")
        delegate.localNightMode = mode
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields) {
                v.text = it[k].toString()
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentModel(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) {
                if (wr_repository.isErrorEnabled) {
                    wr_repository.isErrorEnabled = false
                    et_repository.setText("")
                    saveProfileInfo()
                } else
                    saveProfileInfo()
            }
            isEditMode = !isEditMode
            showCurrentModel(isEditMode)
        }
        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showCurrentModel(isEdit: Boolean) {
        val info = viewFields.filter {
            setOf(
                "firstName",
                "lastName",
                "about",
                "repository"
            ).contains(it.key)
        }
        for ((_, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? = if (isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else null
            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_24, theme)
            } else
                resources.getDrawable(R.drawable.ic_baseline_edit_24, theme)

            background.colorFilter = filter
            setImageDrawable(icon)
        }

        et_repository.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (verifyRepository(p0.toString())) {
                    wr_repository.isErrorEnabled = true
                    wr_repository.error = "Невалидный адрес репозитория"
                } else {
                    wr_repository.error = ""
                    wr_repository.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun saveProfileInfo() {
        // не сохраняем репозиторий, если невалидный
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    private fun verifyRepository(url: String = ""): Boolean {
        val res1 =  when (url.substringAfterLast('/')) {
            "enterprise", "features", "topics", "collections", "trending", "events",
            "marketplace", "pricing", "nonprofit", "customer-stories", "security",
            "login", "join", "github.com", "tree", "something", " " -> true
            else -> false
        }
        val res2 = when(url){
            "www.github.com", "www.github.com/", "github.com", "github.com/",
            "https://github.com", "https://github.com/" -> true
            else -> false
        }
        return res1 || res2
    }

}