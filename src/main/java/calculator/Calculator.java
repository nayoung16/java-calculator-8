package calculator;

import java.util.List;
import java.util.Objects;

public class Calculator {
    public int add(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) return 0;
        int sum = 0;
        for (Integer n : numbers) {
            sum += Objects.requireNonNullElse(n, 0);
        }
        return sum;
    }
}
