package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DelimitedNumberParser {
    private static final String DEFAULT_DELIMS_REGEX = "[,:]";
    public List<Integer> parse(String input) {
        if (input == null || input.isEmpty()) return Collections.emptyList();

        String numbersPart = input;
        String delimiterRegex = DEFAULT_DELIMS_REGEX;

        if (numbersPart.isEmpty()) return Collections.emptyList();

        String[] tokens = numbersPart.split(delimiterRegex, -1);
        List<Integer> result = new ArrayList<>(tokens.length);

        for (String token : tokens) {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("빈 값(연속 구분자 포함)은 허용되지 않습니다.");
            }
            int n;
            try {
                n = Integer.parseInt(token.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
            }
            if (n < 0) {
                throw new IllegalArgumentException("음수는 허용되지 않습니다: " + n);
            }
            result.add(n);
        }
        return result;
    }
}
