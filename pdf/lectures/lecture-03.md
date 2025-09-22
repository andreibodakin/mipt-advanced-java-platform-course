
# 🧩 Лекция 2 — Java Collections Framework — Интерфейсы и Реализации

---

## 🎯 Основные темы

- Полная структура коллекций Java: иерархия интерфейсов, основные реализации, сложность операций, когда что использовать.

## Интерфейсы и **реализации** коллекций

package java.util

Collection.java

// The root interface in the collection hierarchy.

public interface Collection<E> extends Iterable<E>


Iterable
└── Collection
    ├── List
    │ ├── **ArrayList**
    │ ├── **LinkedList**
    ├── Set
    │ ├── **HashSet**
    │ └── **LinkedHashSet**
    │ └── SortedSet
    │   └── **TreeSet**
    └── Queue
        ├── **PriorityQueue**
        └── Deque
            └── **ArrayDeque**

Map (не наследуется от Collection!)
    ├── SortedMap
    │ └── **TreeMap**
    ├── **HashMap**
    └── ConcurrentMap
      └── **ConcurrentHashMap**

## ArrayList

class ArrayList {
    Object[] elementData;
    int size;
}

List<String> list = new ArrayList<>(); // создается массив длины 0

Расширяется в 1.5 раза если capacity заполнилось полностью и места для добавления элемента больше нет.
Прирост oldCapacity >> 1 // то есть 50%.
Если массив уменьшается, capacity не уменьшается. Надо сделать trimToSize, если хочется ее уменьшить.

## Алгоритмическая сложность ArrayList

list.get(i); // O(1)
list.add(value); // O(1) - амортизированная, O(n) - худшая
list.add(i, value); // O(n)
list.remove(i); // O(n)
list.contains(v); // O(n)




