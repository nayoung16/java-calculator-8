package calculator;
import camp.nextstep.edu.missionutils.Console;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        DelimitedNumberParser parser = new DelimitedNumberParser();
        Calculator calculator = new Calculator();
        try {
            System.out.println("덧셈할 문자열을 입력해 주세요.");
            String string = Console.readLine();
            List<Integer> numbers = parser.parse(string);
            int sum = calculator.add(numbers);
            System.out.println("결과 : " + sum);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] " + e.getMessage());
            System.exit(1);
        }
    }
}
