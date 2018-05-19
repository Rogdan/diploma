package ua.rogdan.trag.tools;

import android.content.Context;

import ua.rogdan.trag.R;

public class SuffixFormatter {
    public static String formatWithSuffix(int number, String[] suffixArray) {
        return number + " " + getJustSuffix(number, suffixArray);
    }

    public static String getJustSuffix(int number, String[] suffixArray) {
        int pos;

        if (isUnique(number)) {
            pos = FOR_ZERO_AND_MANY;
        } else {
            String numberString = String.valueOf(number);
            char lastChar = numberString.charAt(numberString.length() - 1);
            int lastDigit = Integer.valueOf(String.valueOf(lastChar));

            if (lastDigit == 0) {
                pos = FOR_ZERO_AND_MANY;
            } else if (lastDigit == 1) {
                pos = FOR_ONE;
            } else if (lastDigit <= 4){
                pos = FOR_2_3_4;
            } else {
                pos = FOR_ZERO_AND_MANY;
            }
        }

        return suffixArray[pos];
    }

    private static boolean isUnique(int number) {
        String numberString = String.valueOf(number);
        int length = numberString.length();

        if (numberString.length() >= UNIQUE_NUMBERS_MINIMUM_LENGTH) {

            String last2DigitsString = numberString.substring(length - 2);
            int last2Digits = Integer.valueOf(last2DigitsString);
            return last2Digits >= UNIQUE_START && last2Digits <= UNIQUE_END;
        } else {
            return false;
        }
    }

    public static String formatMinutes(int minutes, Context context) {
        int hours = minutes / 60;
        minutes %= 60;

        String minString = minutes + " " + context.getString(R.string.min);
        if (hours == 0) {
            return minString;
        } else {
            return hours + " " + context.getString(R.string.h) + " " + minString;
        }
    }

    private static final int FOR_ZERO_AND_MANY = 0; //бонусов
    private static final int FOR_ONE = 1; //бонус
    private static final int FOR_2_3_4 = 2; //бонуса

    /**
     * For cases ..11, ..12, ..13, ..14
     * eg. for 13, 111 or 314 etc
     */
    private static final int UNIQUE_START = 11;
    private static final int UNIQUE_END = 14;
    private static final int UNIQUE_NUMBERS_MINIMUM_LENGTH = 2;
}
