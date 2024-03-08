package com.litian.dancechar.common.common.json2java;

public class MyNameGenerator implements NameGenerator {

    String[] names = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J",
            "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z",
            "AA", "BB", "CC"};

    public MyNameGenerator(String[] names) {
        this.names = names;
    }

    int posiiotn;

    @Override
    public String nextName() {
        return this.names[this.posiiotn++];
    }

    @Override
    public String formatName(String name) {
        char[] chs = name.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(chs[0]);
        for (int i = 1; i < chs.length; i++) {
            if (chs[i - 1] == '_') {
                stringBuilder.append((char) (chs[i] - 32));
            } else {
                stringBuilder.append(chs[i]);
            }
        }
        String s = stringBuilder.toString().replace("_", "");
        chs = s.toCharArray();
        if (chs[0] >= 'a' && chs[0] <= 'z')
            chs[0] = (char) (chs[0] - 32);
        return new String(chs);
    }
}