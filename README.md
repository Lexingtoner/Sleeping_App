# Sleeping App (Jetpack Compose)

Android-приложение на Kotlin + Jetpack Compose с экранами и активностями для вечернего ритуала сна.

## Реализовано

- **Home Screen**: круг сна, кнопка старта трекинга, карточки метрик и блок `Prepare for rest`.
- **AI Chat Screen (Luna)**: чат-сообщения, поле ввода, отправка и быстрый сценарий расслабления.
- **Evening Check-in Screen**: выбор факторов (кофеин/алкоголь/экранное время и т.д.), стресс-слайдер, заметки.
- **Sleep Statistics Screen**: общий sleep score, визуальные циклы сна, ключевые KPI.
- **Edit Alarm Screen**: редактирование будильника, Smart Wake-up, расписание по дням.
- **Sound Library Screen**: recently played, категории звуков, поиск.
- Нижняя навигация между основными разделами.

## Технологии

- Kotlin
- Jetpack Compose (Material 3)
- Navigation Compose
- Android SDK 34

## Запуск

1. Откройте проект в Android Studio (Hedgehog+ / Iguana+).
2. Выполните Gradle Sync.
3. Запустите `app` на эмуляторе или устройстве.

## Структура

- `app/src/main/java/com/sleepingapp/MainActivity.kt` — все экраны, навигация и UI.
- `app/src/main/AndroidManifest.xml` — Android entry point.
- `app/build.gradle.kts` — зависимости Compose и настройки Android.
