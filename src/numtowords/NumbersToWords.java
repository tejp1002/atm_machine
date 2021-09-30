package numtowords;

import static utils.StringUtils.EMPTY;
import static utils.StringUtils.SPACE;
import static utils.StringUtils.ZERO;

/**
 * Helper class for converting the number into equivalent word
 */
public class NumbersToWords {
    private static final String[] oneToNine = {
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] tenToNineteen = {
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] dozens = {
            "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    public String getWordsFromNumber(int number) {
        if(number == 0)
            return ZERO;

        return this.generate(number).trim();
    }

    private String generate(int number) {
        if(number >= 1000000000) {
            return generate(number / 1000000000) + " billion " + generate(number % 1000000000);
        }
        else if(number >= 1000000) {
            return generate(number / 1000000) + " million " + generate(number % 1000000);
        }
        else if(number >= 1000) {
            return generate(number / 1000) + " thousand " + generate(number % 1000);
        }
        else if(number >= 100) {
            return generate(number / 100) + " hundred " + generate(number % 100);
        }

        return generate1To99(number);
    }

    private String generate1To99(int number) {
        if (number == 0)
            return EMPTY;

        if (number <= 9)
            return oneToNine[number - 1];
        else if (number <= 19)
            return tenToNineteen[number % 10];
        else {
            return dozens[number / 10 - 1] + SPACE + generate1To99(number % 10);
        }
    }
}
