import java.util.HashMap;
import java.util.Map;

// Лекция 3
// Пример использования HashMap для подсчета частоты использования букв в строке.

public class CharsFrequency {
    public static void main(String[] args) {
        String s = "Hello world!";
        
        System.out.println(charsFrequencyInString(s));
    }

    private static Map<Character, Integer> charsFrequencyInString(String s) {
        if (s == null) {
            throw new RuntimeException("String should not be null");
        }

        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        return frequency;
    }
}
