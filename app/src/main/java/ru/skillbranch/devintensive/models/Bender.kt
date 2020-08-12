package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question.answers.contains(answer.toLowerCase())) {
            var phrase = checkAnswer(answer, this.question)
            if (phrase != "err")
                return "$phrase\n${question.question}" to status.color
            else {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
        } else {
            if (status == Status.CRITICAL) {
                status = status.nextStatus()
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            status = status.nextStatus()
            "Это не правильный ответ!\n${question.question}" to status.color
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 80)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }

    fun checkAnswer(answer: String, question: Question): String {
        var containsNum: Boolean = false
        for (char in answer) {
            if (char.isDigit())
                containsNum = true
        }
        val res: Boolean = answer.all { it.isDigit() }
        return when (question) {
            Question.NAME -> if (answer[0] != answer[0].toUpperCase()) "Имя должно начинаться с заглавной буквы" else "err"
            Question.PROFESSION -> if (answer[0] == answer[0].toUpperCase()) "Профессия должна начинаться со строчной буквы" else "err"
            Question.MATERIAL -> if (containsNum) "Материал не должен содержать цифр" else "err"
            Question.BDAY -> if (!res) "Год моего рождения должен содержать только цифры" else "err"
            Question.SERIAL -> if (!res && answer.length != 7) "Серийный номер содержит только цифры, и их 7" else "err"
            else -> "err"
        }
    }
}