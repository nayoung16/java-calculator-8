package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class DelimitedNumberParser {
    private static final String DEFAULT_DELIMS_REGEX = "[,:]";
    private static final String ERR_NEED_NL = "잘못된 형식: 커스텀 구분자 뒤에는 줄바꿈(\\n)이 필요합니다.";
    private static final String ERR_CUSTOM_LEN = "커스텀 구분자는 단일 문자여야 합니다.";
    private static final String ERR_EMPTY_TOKEN = "빈 값(연속 구분자 포함)은 허용되지 않습니다.";

    public List<Integer> parse(String input) {
        if (input == null || input.isEmpty()) return Collections.emptyList();

        String numbersPart = input;
        String delimiterRegex = DEFAULT_DELIMS_REGEX;

        if (input.startsWith("//")) {
            int nl = input.indexOf("\\");
            int ni = input.indexOf("n");
            if ((nl < 0) || (ni < 0) || ((ni-nl) != 1)) {
                throw new IllegalArgumentException(ERR_NEED_NL);
            }
            String custom = input.substring(2, nl);
            if (custom.length() != 1) {
                throw new IllegalArgumentException(ERR_CUSTOM_LEN);
            }
            delimiterRegex = Pattern.quote(custom);
            numbersPart = input.substring(ni + 1);
        }

        if (numbersPart.isEmpty()) return Collections.emptyList();

        String[] tokens = numbersPart.split(delimiterRegex, -1);
        List<Integer> result = new ArrayList<>(tokens.length);

        for (String token : tokens) {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException(ERR_EMPTY_TOKEN);
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
