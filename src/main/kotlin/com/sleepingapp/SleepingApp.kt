package com.sleepingapp

private data class SleepSessionPlan(
    val environmentTip: String,
    val breathingPlan: List<String>,
    val mentalImagery: String,
    val gentleReminder: String
)

private class EmbeddedAI {
    private val environmentTips = listOf(
        "Уменьши яркость экрана и приглуши свет в комнате.",
        "Проветри комнату за 10 минут до сна, чтобы воздух стал прохладнее.",
        "Поставь телефон на беззвучный режим, чтобы не отвлекаться.",
        "Подготовь удобную подушку и убедись, что одеяло не перегревает."
    )

    private val imageryPrompts = listOf(
        "Представь, что ты лежишь на берегу теплого моря, и волны тихо накатывают.",
        "Вообрази мягкий туман, который укутывает тебя и приносит спокойствие.",
        "Представь ночное небо, где звезды медленно мерцают и успокаивают.",
        "Вообрази лесную тропу, по которой ты идешь легко и неторопливо."
    )

    private val reminders = listOf(
        "Ты уже сделал(а) достаточно сегодня — можно отпустить все мысли.",
        "Если появляются тревоги, мягко отпусти их, словно облака.",
        "Пусть сон сам придет — нет необходимости торопиться.",
        "Твое тело умеет расслабляться, просто позволь ему это сделать."
    )

    fun generatePlan(name: String?): SleepSessionPlan {
        val greeting = name?.takeIf { it.isNotBlank() }?.let { "${it.trim()}, " } ?: ""
        val environmentTip = greeting + environmentTips.random()
        val breathingPlan = listOf(
            "Вдох 4 секунды — задержка 4 секунды — выдох 6 секунд.",
            "Повтори 6 раз, наблюдая за дыханием."
        )
        val mentalImagery = imageryPrompts.random()
        val gentleReminder = reminders.random()
        return SleepSessionPlan(environmentTip, breathingPlan, mentalImagery, gentleReminder)
    }

    fun generateResponse(feeling: String): String {
        val responses = listOf(
            "Понимаю это ощущение. Давай дадим телу шанс расслабиться шаг за шагом.",
            "Спасибо, что поделился(лась). Попробуй мягко сосредоточиться на выдохе.",
            "Это нормально. Сделаем короткую практику дыхания, чтобы успокоиться.",
            "Ты не один(одна) в этом. Можно начать с небольшого расслабления плеч."
        )
        return "Ты сказал(а): \"$feeling\". ${responses.random()}"
    }
}

private fun printDivider() {
    println("\n" + "-".repeat(48) + "\n")
}

fun main() {
    println("Добро пожаловать в Sleeping App — помощник для засыпания.")
    print("Как тебя зовут? ")
    val name = readlnOrNull()?.trim().orEmpty()

    val ai = EmbeddedAI()
    val plan = ai.generatePlan(name)

    printDivider()
    println("Персональный план расслабления:")
    println("• Среда: ${plan.environmentTip}")
    println("• Дыхание:")
    plan.breathingPlan.forEach { println("  - $it") }
    println("• Образ: ${plan.mentalImagery}")
    println("• Напоминание: ${plan.gentleReminder}")

    printDivider()
    println("Расскажи, что сейчас больше всего мешает уснуть?")
    val feeling = readlnOrNull()?.trim().orEmpty()
    if (feeling.isNotBlank()) {
        println(ai.generateResponse(feeling))
    } else {
        println("Понял(а). Давай просто сделаем несколько спокойных вдохов вместе.")
    }

    printDivider()
    println("Запускаю таймер расслабления на 2 минуты. Сконцентрируйся на дыхании.")
    startBreathingCountdown(seconds = 120)
    println("Отлично. Пусть сон придет мягко. Спокойной ночи!")
}

private fun startBreathingCountdown(seconds: Int) {
    val phases = listOf(
        "Вдох" to 4,
        "Задержка" to 4,
        "Выдох" to 6
    )

    var remaining = seconds
    while (remaining > 0) {
        for ((label, duration) in phases) {
            if (remaining <= 0) break
            val effectiveDuration = minOf(duration, remaining)
            print("$label: ")
            repeat(effectiveDuration) { tick ->
                print("${effectiveDuration - tick} ")
                Thread.sleep(1000)
            }
            println()
            remaining -= effectiveDuration
        }
    }
}
