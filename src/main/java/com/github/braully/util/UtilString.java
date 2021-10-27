package com.github.braully.util;

/**
 *
 * @author strike
 */
public class UtilString {

    public static String primeiroNome(String nome) {
        String prim = "";
        if (nome != null && !nome.trim().isEmpty()) {
            String[] split = nome.trim().split(" ");
            if (split != null && split.length > 0) {
                prim = split[0];
            }
        }
        return prim;
    }

    public static String nomesIniciais(String nome) {
        String prim = "";
        if (nome != null && !nome.trim().isEmpty()) {
            String[] split = nome.trim().split(" ");
            if (split != null) {
                if (split.length > 0) {
                    prim = split[0];
                }

                if (split.length > 1) {
                    prim = prim + " " + split[1];
                }
            }
        }
        return prim;
    }

    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String capitalize(String str, char[] delimiters) {
        int delimLen = (delimiters == null ? -1 : delimiters.length);
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);
            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        replaceAll(buffer, "De ", "de ");
        replaceAll(buffer, "Do ", "do ");
        replaceAll(buffer, "Da ", "da ");
        replaceAll(buffer, " E ", " e ");
        String strf = buffer.toString();
        return strf;
    }

    public static void replaceAll(StringBuffer builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }
    }

    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (int i = 0, isize = delimiters.length; i < isize; i++) {
            if (ch == delimiters[i]) {
                return true;
            }
        }
        return false;
    }

    public static String extract(String strTel, String ini, String fim) {
        if (!UtilValidation.isStringValid(strTel, ini, fim)) {
            return null;
        }
        String ret = null;
        int indexOf = strTel.indexOf(ini);
        int lastIndexOf = strTel.lastIndexOf(fim);
        if (indexOf >= 0 && indexOf < lastIndexOf) {
            ret = strTel.substring(indexOf + 1, lastIndexOf);
        }
        return ret;
    }

    public static String numbers(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\D", "");
    }

    public static String trim(String str, int max) {
        if (str == null) {
            return null;
        }
        max = Math.min(max, str.length());
        return str.substring(0, max);
    }

    public static String numbersTrim(String str, int max) {
        return trim(numbers(str), max);
    }

    public static String removeLeftZeros(String number) {
        if (number == null) {
            return null;
        }
        return number.replaceFirst("^0+(?!$)", "");
    }

    public static String concatPlusRightTrunk(String deflt, String ret) {
        String result = deflt;
        if (deflt == null) {
            return null;
        }
        if (ret == null) {
            ret = "";
        }
        result = ret + deflt;
        result = result.substring(0, deflt.length());
        return result;
    }

    public static String concatPlusLeftTrunk(String deflt, String ret) {
        String result = deflt;
        if (deflt == null) {
            return null;
        }
        if (ret == null) {
            ret = "";
        }
        result = deflt + ret;
        result = result.substring(result.length() - deflt.length());
        return result;
    }

    public static String firstNonNull(String... strs) {
        if (strs != null) {
            for (String str : strs) {
                if (str != null) {
                    return str;
                }
            }
        }
        return null;
    }

    public static String firstNonEmpty(String... strs) {
        if (strs != null) {
            for (String str : strs) {
                if (UtilValidation.isStringValid(str)) {
                    return str;
                }
            }
        }
        return null;
    }

    public static String emptyIfNull(String fiscalCode) {
        if (fiscalCode == null) {
            return "";
        }
        return fiscalCode;
    }

    public static String emptyIfNull(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    public static String[] splitInNewLine(String str) {
        if (UtilValidation.isStringEmpty(str)) {
            return new String[0];
        }
        String[] ret = null;
        //Try first windows new line style
        String delim = "\r\n";
        if (str.contains(delim)) {
            ret = str.split(delim);
        } else {
            ret = str.split("\n");
        }
        return ret;
    }
}
