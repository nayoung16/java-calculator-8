package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class DelimitedNumberParser {
    private static final String DEFAULT_DELIMS_REGEX = "[,:]";
    private static final String ERR_NEED_NL = "잘못된 형식: 커스텀 구분자 뒤에는 줄바꿈(\\n)이 필요합니다.";
    private static final String ERR_EMPTY_TOKEN = "빈 값(연속 구분자 포함)은 허용되지 않습니다.";

    private static class Parsed {
        final String delimRegex;
        final String numbersPart;
        Parsed(String delimRegex, String numbersPart) {
            this.delimRegex = delimRegex;
            this.numbersPart = numbersPart;
        }
    }

    public List<Integer> parse(String input) {
        if (input == null || input.isEmpty()) return Collections.emptyList();

        Parsed p = parseHeaderIfAny(input);
        if (p.numbersPart.isEmpty()) return Collections.emptyList();

        String[] tokens = p.numbersPart.split(p.delimRegex, -1);
        return toIntList(tokens);
    }

    private Parsed parseHeaderIfAny(String input) {
        if (!input.startsWith("//")) {
            return new Parsed(DEFAULT_DELIMS_REGEX, input);
        }
        int bs = input.indexOf("\\");
        int n  = input.indexOf("n", bs + 1);
        if (bs < 0 || n < 0 || (n - bs) != 1) {
            throw new IllegalArgumentException(ERR_NEED_NL);
        }

        String custom = input.substring(2, bs);

        String delimRegex = Pattern.quote(custom) + "|" + DEFAULT_DELIMS_REGEX;
        String numbersPart = input.substring(n + 1);
        return new Parsed(delimRegex, numbersPart);
    }

    private List<Integer> toIntList(String[] tokens) {
        List<Integer> result = new ArrayList<>(tokens.length);
        for (String token : tokens) {
            String t = (token == null) ? "" : token.trim();
            if (t.isEmpty()) {
                throw new IllegalArgumentException(ERR_EMPTY_TOKEN);
            }
            int num;
            try {
                num = Integer.parseInt(t);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
            }
            if (num < 0) {
                throw new IllegalArgumentException("음수는 허용되지 않습니다: " + num);
            }
            result.add(num);
        }
        return result;
    }
}
