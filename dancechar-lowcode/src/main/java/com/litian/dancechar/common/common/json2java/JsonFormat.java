package com.litian.dancechar.common.common.json2java;

public class JsonFormat {

    private static final String empty = "  ";

    public static String format(String json) {
        try {
            json = removeUnUsedSpaces(json);
            int empty = 0;
            char[] chs = json.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < chs.length; ) {
                if (chs[i] == '"') {
                    stringBuilder.append(chs[i]);
                    i++;
                    while (i < chs.length) {
                        if (chs[i] == '"' && isDoubleSerialBackslash(chs, i - 1)) {
                            stringBuilder.append(chs[i]);
                            i++;
                            break;
                        }
                        stringBuilder.append(chs[i]);
                        i++;
                    }
                    continue;
                }
                if (chs[i] == ',') {
                    stringBuilder.append(',').append('\n').append(getEmpty(empty));
                    i++;
                    continue;
                }
                if (chs[i] == '{' || chs[i] == '[') {
                    empty++;
                    stringBuilder.append(chs[i]).append('\n').append(getEmpty(empty));
                    i++;
                    continue;
                }
                if (chs[i] == '}' || chs[i] == ']') {
                    empty--;
                    stringBuilder.append('\n').append(getEmpty(empty)).append(chs[i]);
                    i++;
                    continue;
                }
                stringBuilder.append(chs[i]);
                i++;
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }
    }

    private static boolean isDoubleSerialBackslash(char[] chs, int i) {
        int count = 0;
        for (int j = i; j > -1; j--) {
            if (chs[j] == '\\') {
                count++;
            } else {
                return (count % 2 == 0);
            }
        }
        return (count % 2 == 0);
    }

    private static String getEmpty(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++){
            stringBuilder.append("  ");
        }
        return stringBuilder.toString();
    }

    private static String removeUnUsedSpaces(String json) {
        char[] chs = json.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chs.length; ) {
            if (chs[i] == '"') {
                stringBuilder.append(chs[i]);
                i++;
                while (i < chs.length) {
                    if (chs[i] == '"' && isDoubleSerialBackslash(chs, i - 1)) {
                        stringBuilder.append(chs[i]);
                        i++;
                        break;
                    }
                    stringBuilder.append(chs[i]);
                    i++;
                }
                continue;
            }
            if (chs[i] == ' ' || chs[i] == '\t' || chs[i] == '\n') {
                i++;
                continue;
            }
            stringBuilder.append(chs[i]);
            i++;
        }
        return stringBuilder.toString();
    }
}
