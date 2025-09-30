
# 🧩 Лекция 4 - Дженерики в Java

### 1. **Синтаксис использования**
```java
List<String> list = new ArrayList<>(); // Diamond operator (Java 7+)
```
→ Компилятор гарантирует, что в список можно добавлять **только `String`**, а при извлечении — **не нужен каст**.

---

### 2. **Проблема без дженериков: `Object[]` — небезопасен**
```java
Object[] objects = new Integer[10]; // Компилируется!
objects[0] = "543";                 // Runtime: ArrayStoreException!
```
→ **Ошибка только в рантайме** → нарушение type safety.

> 💡 **Идея дженериков**: если код компилируется — **никаких `ClassCastException` или `ArrayStoreException` в рантайме быть не должно**.

---

### 3. **Type Erasure (стирание типов)**
- Дженерики существуют **только на этапе компиляции**.
- В байт-коде: `List<String>` → `List` (raw type), все операции — с `Object`.
- **Исключение**: информация о дженериках сохраняется в **сигнатурах методов и классов** (через `Signature` атрибут) → доступна через Reflection.

> ⚠️ Поэтому **нельзя**:
> - Создать массив дженериков: `new T[]`
> - Проверить тип в рантайме: `if (list instanceof List<String>)` → ошибка компиляции

---

### 4. **Инвариантность дженериков**
```java
List<Object> list = new ArrayList<Integer>(); // ❌ Ошибка компиляции!
```
→ Даже если `Integer` — подтип `Object`, `List<Integer>` **не является подтипом** `List<Object>`.

> Это называется **инвариантность**: `List<A>` и `List<B>` не связаны, даже если `A extends B`.

---

### 5. **Wildcards: `? extends` и `? super`**

#### ✅ `? extends T` — **Producer** (только чтение)
```java
List<? extends Number> list = new ArrayList<Integer>();
Number n = list.get(0); // ✅ OK
list.add(42);           // ❌ Запрещено! (может быть List<Double>)
list.add(null);         // ✅ Единственное разрешённое значение
```

#### ✅ `? super T` — **Consumer** (только запись)
```java
List<? super Integer> list = new ArrayList<Number>();
list.add(42);     // ✅ OK
Number n = list.get(0); // ❌ Тип Object — не знаем точный тип
```

#### 📌 **PECS (Producer Extends, Consumer Super)**
- Если объект **возвращает** значения (producer) → `? extends T`
- Если объект **принимает** значения (consumer) → `? super T`
- Если и то, и другое → **без wildcard**, просто `T`

---

### 6. **Объявление дженериков**

#### На уровне класса:
```java
public interface List<E> { ... }
public class Box<T> { ... }
```

#### На уровне метода:
```java
public static <E> E max(List<E> list, Comparator<E> comparator) { ... }
```

#### Ограничения (bounds):
- `E extends Comparable<E>` — верхняя граница (только подтипы `Comparable`)
- `E super SomeClass` — **нельзя** на уровне объявления типа!  
  → `super` работает **только в wildcards**: `List<? super Number>`

> ✅ Правильно:  
> ```java
> public static <E extends Comparable<? super E>> E max(List<E> list)
> ```
> → Это позволяет сравнивать `E`, даже если `compareTo` реализован в родителе (например, `LocalDateTime` наследует `Comparable<ChronoLocalDateTime>`).

---

### 7. **Соглашения по именам типов**
- `E` — Element (в коллекциях)
- `K` — Key
- `V` — Value
- `T` — Type
- `R` — Return type
- `S`, `U`, `V` — дополнительные типы

---

### 8. **Примеры сравнения**

| Выражение | Можно присвоить | Можно добавить | Можно прочитать как |
|----------|------------------|----------------|----------------------|
| `List<Number>` | только `List<Number>` | `Number` и подтипы | `Number` |
| `List<? extends Number>` | `List<Integer>`, `List<Double>` | только `null` | `Number` |
| `List<? super Number>` | `List<Number>`, `List<Object>` | `Number` и подтипы | `Object` |
| `List<?>` | любой `List` | только `null` | `Object` |

---

### 9. **Важно помнить**
- **Raw types (`List`) — избегать!** Они отключают проверку типов.
- **Дженерики не работают с примитивами** → используй `List<Integer>`, а не `List<int>`.
- **Массивы и дженерики несовместимы** → предпочитай коллекции.
