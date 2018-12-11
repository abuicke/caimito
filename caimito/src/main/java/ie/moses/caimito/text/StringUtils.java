package ie.moses.caimito.text;

public final class StringUtils {

    public static String toCsv(Iterable<? extends CharSequence> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence string : strings) {
            stringBuilder.append(string)
                    .append(',');
        }
        int lastCharIndex = stringBuilder.length() - 1;
        stringBuilder.deleteCharAt(lastCharIndex);
        return stringBuilder.toString();
    }

    private StringUtils() {
    }

}
