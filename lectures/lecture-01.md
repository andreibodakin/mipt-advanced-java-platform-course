
## 🧩 Лекция 1 — Основы синтаксиса Java + Настройка окружения и работа в IntelliJ IDEA

---

## 🎯 Основные темы

- Установка и настройка JDK
- Компиляция и запуск через `javac` и `java`
- Переменные среды: `PATH`, `CLASSPATH`
- Выбор и настройка IDE (IntelliJ IDEA)
- Горячие клавиши и рефакторинги в IntelliJ IDEA
- Основы синтаксиса: классы, методы, переменные, управляющие конструкции

---

## 🛠️ Настройка Java-окружения

### Установка JDK

→ Скачать можно с:
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- [OpenJDK (Adoptium / Temurin)](https://adoptium.net/) ← **рекомендуется**

Проверка установки:
```bash
java -version
javac -version
```

→ Должны вывести версию Java и компилятора.

---

### Переменная окружения `PATH`

→ `PATH` — список директорий, где система ищет исполняемые файлы.

✅ Добавь путь к `bin` JDK в `PATH`:

**Linux/macOS** (в `~/.bashrc` или `~/.zshrc`):
```bash
export PATH="/path/to/jdk/bin:$PATH"
```

**Windows**:
- Панель управления → Система → Дополнительные параметры → Переменные среды → `PATH` → Добавить путь, например:  
  `C:\Program Files\Java\jdk-21\bin`

---

### CLASSPATH

→ Указывает JVM, где искать `.class`-файлы и библиотеки.

✅ Обычно не нужно настраивать вручную при работе с IDE или Maven/Gradle.

→ Если компилируешь вручную:
```bash
javac -cp ".;lib/*" MyClass.java
java -cp ".;lib/*" MyClass
```

> 💡 `.` — текущая директория.  
> 💡 `;` — разделитель в Windows, `:` — в Linux/macOS.

---

## 💻 Работа с IntelliJ IDEA

### Почему IntelliJ IDEA?

- Самая популярная и мощная IDE для Java.
- Умное автодополнение, рефакторинги, отладка, интеграция с Maven/Gradle, Git.
- Community Edition — бесплатна и достаточно для обучения.

---

## ⌨️ Горячие клавиши IntelliJ IDEA (must-have)

| Действие                            | Windows/Linux       | macOS               |
|-------------------------------------|---------------------|---------------------|
| Автодополнение                      | `Ctrl + Space`      | `Cmd + Space`       |
| Быстрое исправление / подсказки     | `Alt + Enter`       | `Option + Enter`    |
| Запуск программы                    | `Shift + F10`       | `Ctrl + R`          |
| Отладка                             | `Shift + F9`        | `Ctrl + D`          |
| Поиск по проекту                    | `Ctrl + Shift + F`  | `Cmd + Shift + F`   |
| Поиск класса                        | `Ctrl + N`          | `Cmd + O`           |
| Поиск файла                         | `Ctrl + Shift + N`  | `Cmd + Shift + O`   |
| Переход к определению               | `Ctrl + B`          | `Cmd + B`           |
| Рефакторинг: переименование         | `Shift + F6`        | `Shift + F6`        |
| Закомментировать строку             | `Ctrl + /`          | `Cmd + /`           |
| Форматирование кода                 | `Ctrl + Alt + L`    | `Cmd + Option + L`  |
| Открыть структуру класса            | `Ctrl + F12`        | `Cmd + F12`         |

---

## 🔧 Полезные Live Templates (шаблоны кода)

| Шаблон | Результат                          | Описание |
|--------|------------------------------------|----------|
| `sout` | `System.out.println();`            | Быстрый вывод в консоль |
| `iter` | `for (Type item : collection) { }` | Цикл for-each |
| `psvm` | `public static void main(String[] args) { }` | Главный метод |
| `itar` | `for (int i = 0; i < arr.length; i++) { }` | Цикл по индексу |
| `ifn`  | `if (var == null) { }`             | Проверка на null |
| `inn`  | `if (var != null) { }`             | Проверка на не-null |

→ Просто введи шаблон и нажми `Tab`.

---

## 🔄 Рефакторинги в IntelliJ IDEA

### ➤ Extract Method (Вынести метод)

Выдели код → `Ctrl + Alt + M` → дай имя методу → готово!

**Было:**
```java
public void process() {
    int a = 5;
    int b = 10;
    int sum = a + b;
    System.out.println("Sum: " + sum);
}
```

**Стало:**
```java
public void process() {
    printSum(5, 10);
}

private void printSum(int a, int b) {
    int sum = a + b;
    System.out.println("Sum: " + sum);
}
```

→ Улучшает читаемость и переиспользование.

---

### ➤ Inline Method (Встроить метод)

Если метод слишком простой — можно "встроить" его обратно:  
`Ctrl + Alt + N`

→ Полезно при оптимизации или упрощении.

---

## 🧩 Основы синтаксиса Java

### Структура программы

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

→ Каждая программа начинается с `main`.

---

### Переменные и типы

```java
int age = 25;
double price = 19.99;
boolean isActive = true;
String name = "Alice";
```

---

### Управляющие конструкции

```java
if (age >= 18) {
    System.out.println("Совершеннолетний");
} else {
    System.out.println("Несовершеннолетний");
}

for (int i = 0; i < 5; i++) {
    System.out.println(i);
}

while (condition) {
    // ...
}
```

---

## 💡 Советы для новичков

- Всегда проверяй, что `java -version` работает в терминале.
- Не бойся использовать `Alt + Enter` — IntelliJ IDEA часто знает, как исправить ошибку.
- Учись пользоваться рефакторингами — они экономят кучу времени.

---

## 📚 Полезные ссылки

- [Скачать IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/)
- [OpenJDK (Adoptium)](https://adoptium.net/)
- [Горячие клавиши IntelliJ IDEA (официальная шпаргалка)](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
- [Теория по Java](https://metanit.com/java/tutorial/)
```
