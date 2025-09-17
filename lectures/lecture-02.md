
# 🧩 Лекция 2 — Примитивные типы, классы-обёртки, Пакеты Java, Object, equals/hashCode

---

## 🎯 Основные темы

- Примитивные типы данных в Java (включая размер в битах)
- Классы-обёртки (Wrapper Classes)
- Автобоксинг и автораспаковка
- Передача параметров по значению
- Пакеты: `java.lang`, `java.util`, `java.io`
- Класс `Object` — корень иерархии
- Контракт `equals()` и `hashCode()`
- Почему важно переопределять их вместе
- Переполнение (overflow) примитивных типов
- `BigDecimal` — точные вычисления (для денег)
- `StringBuilder` — эффективная работа со строками
- Практические примеры и антипаттерны

---

## 🔢 Примитивные типы данных

В Java 8 примитивных типов. Размер указан в **битах и байтах**.

| Тип      | Размер (биты) | Размер (байты) | Диапазон значений               | Значение по умолчанию |
|----------|---------------|----------------|----------------------------------|------------------------|
| `byte`   | 8 бит         | 1 байт         | -128 до 127                     | `0`                   |
| `short`  | 16 бит        | 2 байта        | -32768 до 32767                 | `0`                   |
| `int`    | 32 бита       | 4 байта        | -2^31 до 2^31-1                 | `0`                   |
| `long`   | 64 бита       | 8 байт         | -2^63 до 2^63-1                 | `0L`                  |
| `float`  | 32 бита       | 4 байта        | ±3.4e38 (7 знаков)              | `0.0f`                |
| `double` | 64 бита       | 8 байт         | ±1.7e308 (15 знаков)            | `0.0d`                |
| `char`   | 16 бит        | 2 байта        | `\u0000` до `\uffff` (Unicode)  | `\u0000`              |
| `boolean`| не определён* | не определён*  | `true` / `false`                | `false`               |

> 💡 `*` — размер `boolean` не определён спецификацией JVM — зависит от реализации. Обычно хранится как `int` (32 бита) внутри.

---

## 📦 Классы-обёртки (Wrapper Classes)

Каждый примитив имеет объектный аналог:

| Примитив | Класс-обёртка |
|----------|----------------|
| `int`    | `Integer`      |
| `long`   | `Long`         |
| `double` | `Double`       |
| `boolean`| `Boolean`      |
| `char`   | `Character`    |
| ...      | ...            |

### Зачем нужны?
- Для хранения в коллекциях (`List<Integer>`, а не `List<int>`).
- Для использования `null`.
- Для вызова методов: `Integer.parseInt()`, `Character.isDigit()` и т.д.

---

## 🔄 Автобоксинг и автораспаковка

→ **Автобоксинг** — автоматическое преобразование примитива в объект.  
→ **Автораспаковка** — наоборот.

```java
Integer a = 10;        // ← автобоксинг: int → Integer
int b = a;             // ← автораспаковка: Integer → int

List<Integer> list = new ArrayList<>();
list.add(5);           // ← автобоксинг
int first = list.get(0); // ← автораспаковка
```

⚠️ **Осторожно с `null`!**

```java
Integer x = null;
int y = x; // ← NullPointerException!
```

→ Всегда проверяй на `null` перед распаковкой.

---

## 📥 Передача параметров по значению

> ❗ **В Java всё передаётся по значению — даже объекты!**

→ При передаче объекта — копируется **ссылка на объект**, а не сам объект.

### Пример:

```java
public static void main(String[] args) {
    Person p = new Person("Alice");
    changeName(p);
    System.out.println(p.name); // → "Bob"
}

static void changeName(Person person) {
    person.name = "Bob"; // ← изменяем объект по ссылке
}
```

→ Объект изменился, потому что мы **изменяли данные по скопированной ссылке**.

### Но:

```java
static void reassign(Person person) {
    person = new Person("Charlie"); // ← переприсваиваем ссылку — оригинал не меняется!
}
```

→ После вызова `reassign(p)` — `p.name` всё ещё `"Alice"`.

---

## 📂 Пакеты Java

Пакеты — это пространства имён для классов.

### Основные пакеты:

- `java.lang` — автоматически импортируется (`String`, `Object`, `System`, `Math`).
- `java.util` — коллекции, дата/время, Scanner, Random.
- `java.io` — ввод/вывод, файлы.
- `java.time` — современные даты (Java 8+).

### Импорт:

```java
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

// Или импорт всего пакета:
import java.util.*;
```

> 💡 `*` — не нагружает приложение, только упрощает написание кода.

---

## 🧬 Класс `Object` — корень иерархии

→ Корневой класс всей иерархии в Java.  
→ Любой класс — наследник `Object` (явно или неявно).

### Основные методы:

- `toString()` — строковое представление объекта.
- `equals(Object obj)` — сравнение объектов.
- `hashCode()` — хеш-код для использования в хеш-таблицах.
- `getClass()` — получить класс объекта.
- `clone()` — неглубокое клонирование (осторожно!).
- `finalize()` — освобождение ресурсов перед удалением, deprecated (Java 9+).

---

## 1.1.1 `equals`

```java
public boolean equals(@Nullable Object obj)
```

> ⚖️ **Назначение**: проверяет, равны ли два объекта *логически* (по содержимому), а не физически (по ссылке).

### 📌 Поведение по умолчанию

→ В классе `Object` метод `equals()` сравнивает **ссылки**:

```java
Object a = new Object();
Object b = new Object();
System.out.println(a.equals(b)); // false — разные объекты
```

→ Это эквивалентно `a == b`.

---

### ✍️ Переопределение

→ В подклассах `equals()` **часто переопределяют**, чтобы сравнивать объекты по полям:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(name, person.name) &&
           Objects.equals(age, person.age);
}
```

---

### 📐 Контракт `equals()`

Для **ненулевых объектов** метод `equals()` должен задавать **отношение эквивалентности**:

1. **Рефлексивность**: `x.equals(x)` → `true`
2. **Симметричность**: если `x.equals(y) == true`, то `y.equals(x) == true`
3. **Транзитивность**: если `x.equals(y) == true` и `y.equals(z) == true`, то `x.equals(z) == true`
4. **Консистентность**: если данные объекта не менялись, то `x.equals(y)` должен возвращать одно и то же значение при повторных вызовах.
5. **Для любого ненулевого `x`**: `x.equals(null) == false`

> 💡 Нарушение контракта → непредсказуемое поведение в коллекциях (`HashSet`, `HashMap` и др.).

---

## 1.1.2 `hashCode`

```java
public native int hashCode();
```

> 🧮 **Назначение**: возвращает целочисленный хеш-код объекта. Используется в хеш-структурах: `HashMap`, `HashSet`, `HashTable` и др.

---

### 📜 Контракт `hashCode()`

1. **Консистентность**: если данные объекта не менялись, `hashCode()` должен возвращать **одно и то же значение** при каждом вызове.
2. **Согласованность с `equals()`**: если `x.equals(y) == true`, то `x.hashCode() == y.hashCode()` — **обязательно**.
3. **Необязательное условие**: если `x.equals(y) == false`, то `x.hashCode()` и `y.hashCode()` **могут совпадать** — это называется **коллизия хешей** (нормально для хеш-таблиц).

---

### 🏗️ Реализация по умолчанию

→ В ранних версиях JVM `hashCode()` возвращал **адрес объекта в памяти**.

→ **Сейчас** — используется **псевдослучайное число**, которое:
- Генерируется при первом вызове `hashCode()`.
- Записывается в **заголовок объекта** (object header).
- **Не меняется** в течение жизни объекта, даже если объект перемещается сборщиком мусора.

> 💡 Почему изменили?  
> — При маленьком heap-е адреса были близки → хеши были не равномерны → **ухудшалась производительность хеш-таблиц**.  
> — Новая реализация даёт **лучшее распределение хешей** → меньше коллизий → быстрее работа `HashMap`.

---

### ⚠️ Почему важно переопределять `hashCode()` вместе с `equals()`

→ Представь, что ты положил объект в `HashMap`, а потом изменил поле, участвующее в `equals()`, но не обновил `hashCode()`:

```java
Person p = new Person("Alice");
map.put(p, "value");

p.setName("Bob"); // ← изменили поле, которое участвует в equals/hashCode

map.get(p); // → null! Потому что hashCode изменился, а HashMap ищет в другой "корзине".
```

→ **Решение**: поля, используемые в `equals()` и `hashCode()`, должны быть **неизменяемыми (immutable)**.

---

## ✅ Правильное переопределение

### Способ 1: Вручную

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(name, person.name) &&
           Objects.equals(age, person.age);
}

@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

### Способ 2: Через Lombok (если используется)

```java
@EqualsAndHashCode
public class Person {
    private String name;
    private Integer age;
}
```

### Способ 3: В IntelliJ IDEA → `Alt + Insert` → `equals() and hashCode()`

→ IDE сгенерирует корректный код.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(name, person.name) &&
           Objects.equals(age, person.age);
}

@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

> 💡 `Objects.hash(...)` — безопасно обрабатывает `null` и генерирует хороший хеш на основе переданных полей.

---

## 🚫 Частые ошибки

- Сравнение через `==` для объектов → сравниваются ссылки, а не содержимое.
- Забыли `@Override` → можно случайно создать перегрузку, а не переопределение.
- Не переопределили `hashCode()` → проблемы с `HashMap`.
- Использовали изменяемые поля в `hashCode()` → объект "сломается" в `HashSet`, если поле изменится.
- Использовали `float`/`double` в `hashCode()` без округления → нестабильность из-за точности.

---

## 💡 Советы

- Всегда переопределяй `equals()` и `hashCode()` **вместе**.
- Используй `java.util.Objects` — безопасно и читаемо.
- В IntelliJ IDEA: `Alt + Insert` → генерация методов — экономит время.
- Тестируй поведение в `HashMap` — это частый вопрос на собеседованиях.
- Поля в `hashCode()` и `equals()` — лучше делать `final`.


---

## ⚖️ Контракт `equals()` и `hashCode()`

> Если два объекта равны по `equals()` — их `hashCode()` **должен быть одинаковым**.

### Почему это важно?

→ `HashMap`, `HashSet`, `HashTable` используют `hashCode()` для определения "корзины", а `equals()` — для точного сравнения.

### Антипаттерн:

```java
@Override
public boolean equals(Object o) {
    // ... логика сравнения
}

// ❌ Забыли переопределить hashCode()
```

→ Объекты могут "потеряться" в `HashMap`.

---

## 🚫 Частые ошибки

- Сравнение через `==` для объектов → сравниваются ссылки, а не содержимое.
- Забыли `@Override` → можно случайно создать перегрузку, а не переопределение.
- Не переопределили `hashCode()` → проблемы с `HashMap`.
- Использовали изменяемые поля в `hashCode()` → объект "сломается" в `HashSet`, если поле изменится.

---

## 💥 Переполнение (Overflow)

→ Происходит, когда результат операции **выходит за пределы диапазона типа**.

### Пример с `int`:

```java
int max = Integer.MAX_VALUE; // 2147483647
int overflow = max + 1;      // → -2147483648 (переполнение!)
System.out.println(overflow);
```

→ Никакого исключения — просто "заворачивается" (как одометр в машине).

### Как избежать?

- Используй `long` для больших чисел.
- Используй `Math.addExact()`, `Math.multiplyExact()` — бросают `ArithmeticException` при переполнении.

```java
try {
    int result = Math.addExact(Integer.MAX_VALUE, 1);
} catch (ArithmeticException e) {
    System.out.println("Переполнение!");
}
```

- Для критических вычислений — используй `BigInteger`.

---

## 💰 `BigDecimal` — для точных вычислений (деньги!)

→ `float` и `double` **не подходят** для финансовых расчётов — из-за ошибок округления.

### Пример проблемы:

```java
double a = 0.1;
double b = 0.2;
System.out.println(a + b); // → 0.30000000000000004 😱
```

### Решение — `BigDecimal`:

```java
BigDecimal a = new BigDecimal("0.1");
BigDecimal b = new BigDecimal("0.2");
BigDecimal sum = a.add(b);
System.out.println(sum); // → 0.3 ✅
```

> 💡 **Всегда создавай `BigDecimal` из `String`, а не из `double`!**

```java
new BigDecimal(0.1)   // ← НЕПРАВИЛЬНО — сохраняет неточность double
new BigDecimal("0.1") // ← ПРАВИЛЬНО
```

### Операции:

```java
a.add(b)
a.subtract(b)
a.multiply(b)
a.divide(b, 2, RoundingMode.HALF_UP) // ← обязательно указывать округление!
```

→ Используй `BigDecimal` для:
- Денег
- Процентов
- Точных научных расчётов

---

## 🧵 `StringBuilder` — эффективная работа со строками

→ `String` в Java **неизменяем (immutable)** → каждая операция `"a" + "b"` создаёт новый объект.

### Проблема:

```java
String result = "";
for (int i = 0; i < 1000; i++) {
    result += "a"; // ← создаёт 1000 промежуточных объектов!
}
```

→ Медленно и расходует память.

### Решение — `StringBuilder`:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("a"); // ← изменяет один объект
}
String result = sb.toString();
```

→ В **100+ раз быстрее** для больших объёмов.

### Основные методы:

```java
sb.append("text")
sb.insert(0, "prefix")
sb.delete(0, 5)
sb.reverse()
sb.toString()
```

> 💡 Если нужна потокобезопасность — используй `StringBuffer` (но он медленнее из-за синхронизации).

---

## 💡 Советы

- Всегда переопределяй `equals()` и `hashCode()` **вместе**.
- Используй `java.util.Objects` — безопасно и читаемо.
- В IntelliJ IDEA: `Alt + Insert` → генерация методов — экономит время.
- Для денег — только `BigDecimal`.
- Для конкатенации строк в цикле — только `StringBuilder`.
- Проверяй переполнение в критических местах — используй `Math.*Exact()`.

---

## 📚 Полезные ссылки

- [Oracle: Primitive Data Types](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)
- [Oracle: BigDecimal](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/math/BigDecimal.html)
- [Oracle: StringBuilder](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/StringBuilder.html)
- [Хабр: Контракт equals/hashCode](https://habr.com/ru/articles/168613/)
- [Baeldung: Guide to hashCode()](https://www.baeldung.com/java-hashcode)
- [Baeldung: BigDecimal](https://www.baeldung.com/java-bigdecimal)

```
