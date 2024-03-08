package com.litian.dancechar.common.common.json2java;

import java.io.IOException;

public class Json2JavaMain {

    /*public static void main(String[] args) {
        String ss = "{\"count\":0.0,\"doubleList\":[1,2,3.0,4],\"intList\":[1,2,3,4,5,6]}";
        System.out.println(ss);
        (new Json2Bean(ss, "RootBean", (NameGenerator)new MyNameGenerator(), (JsonParse)new MyJsonParser(), (BeanGenerator)new MyBeanGenerator("com.test1"))).execute();
        System.out.println("------------------------------");
        String s = "{\"doubleList\":[1,2.0000,3,4,5,6],\"intList\":[1,2,3,4,5,6],\"multyList\":[[[\"d\",\"e\",\"f\"],[\"d1\",\"e1\",\"f1\"]],[[\"d\",\"e\",\"f\"],[\"d2\",\"e2\",\"f2\"]]],\"code\":0,\"data\":{\"count\":\"6\",\"list\":[{\"pid\":\"236\",\"author\":\"M12345678977\",\"author_id\":\"31\",\"subject\":\"\",\"dateline\":\"1459232596\",\"message\":\"\",\"useip\":\"2104960333\",\"invisible\":\"0\",\"status\":\"1\",\"comment\":\"0\",\"position\":\"0\",\"tid\":\"121\",\"fid\":\"1\",\"dateline_show\":\"2016-03-29 14:23:16\",\"user_info\":{\"id\":\"31\",\"username\":\"M12345678977\",\"nickname\":\"123566\",\"head\":\"948\",\"group\":{\"adminid\":\"2\",\"alloweditpost\":\"1\",\"allowstickthread\":\"1\",\"allowdelpost\":\"1\",\"allowbanuser\":\"1\",\"allowdigestthread\":\"1\",\"allowpost\":\"1\",\"name\":\"\",\"fid\":\"1\"}},\"img\":[\"948\"]},{\"pid\":\"237\",\"author\":\"M18267152148\",\"author_id\":\"27\",\"subject\":\"\",\"dateline\":\"1459234314\",\"message\":\"thugs \",\"useip\":\"2062279264\",\"invisible\":\"0\",\"status\":\"1\",\"comment\":\"0\",\"position\":\"1\",\"tid\":\"121\",\"fid\":\"1\",\"dateline_show\":\"2016-03-29 14:51:54\",\"user_info\":{\"id\":\"27\",\"username\":\"M18267152148\",\"nickname\":\"123456\",\"head\":\"865\",\"group\":{\"adminid\":\"2\",\"alloweditpost\":\"1\",\"allowstickthread\":\"1\",\"allowdelpost\":\"1\",\"allowbanuser\":\"1\",\"allowdigestthread\":\"1\",\"allowpost\":\"1\",\"name\":\"\",\"fid\":\"1\"}},\"img\":[\"865\"]},{\"pid\":\"238\",\"author\":\"M18267152148\",\"author_id\":\"27\",\"subject\":\"\",\"dateline\":\"1459234741\",\"message\":\"hfs schizophrenia [:f00}[:f01}[:f02}\",\"useip\":\"2062279264\",\"invisible\":\"0\",\"status\":\"1\",\"comment\":\"0\",\"position\":\"2\",\"tid\":\"121\",\"fid\":\"1\",\"dateline_show\":\"2016-03-29 14:59:01\",\"user_info\":{\"id\":\"27\",\"username\":\"M18267152148\",\"nickname\":\"123456\",\"head\":\"865\",\"group\":{\"adminid\":\"2\",\"alloweditpost\":\"1\",\"allowstickthread\":\"1\",\"allowdelpost\":\"1\",\"allowbanuser\":\"1\",\"allowdigestthread\":\"1\",\"allowpost\":\"1\",\"name\":\"\",\"fid\":\"1\"}},\"img\":[]},{\"pid\":\"239\",\"author\":\"M18267152148\",\"author_id\":\"27\",\"subject\":\"\",\"dateline\":\"1459234984\",\"message\":\"[:f020}[:f021}[:f022}[:f010}[:f009}\",\"useip\":\"2062279264\",\"invisible\":\"0\",\"status\":\"1\",\"comment\":\"0\",\"position\":\"3\",\"tid\":\"121\",\"fid\":\"1\",\"dateline_show\":\"2016-03-29 15:03:04\",\"user_info\":{\"id\":\"27\",\"username\":\"M18267152148\",\"nickname\":\"123456\",\"head\":\"865\",\"group\":{\"adminid\":\"2\",\"alloweditpost\":\"1\",\"allowstickthread\":\"1\",\"allowdelpost\":\"1\",\"allowbanuser\":\"1\",\"allowdigestthread\":\"1\",\"allowpost\":\"1\",\"name\":\"\",\"fid\":\"1\"}},\"img\":[]},{\"pid\":\"240\",\"author\":\"M18267152148\",\"author_id\":\"27\",\"subject\":\"\",\"dateline\":\"1459235016\",\"message\":\"Sfyhgff\",\"useip\":\"2062279264\",\"invisible\":\"0\",\"status\":\"1\",\"comment\":\"0\",\"position\":\"4\",\"tid\":\"121\",\"fid\":\"1\",\"dateline_show\":\"2016-03-29 15:03:36\",\"user_info\":{\"id\":\"27\",\"username\":\"M18267152148\",\"nickname\":\"123456\",\"head\":\"865\",\"group\":{\"adminid\":\"2\",\"alloweditpost\":\"1\",\"allowstickthread\":\"1\",\"allowdelpost\":\"1\",\"allowbanuser\":\"1\",\"allowdigestthread\":\"1\",\"allowpost\":\"1\",\"name\":\"\",\"fid\":\"1\"}},\"img\":[]},{\"pid\":\"241\",\"author\":\"M12345678977\",\"author_id\":\"31\",\"subject\":\"\",\"dateline\":\"1459238898\",\"message\":\"\",\"useip\":\"2104960333\",\"invisible\":\"0\",\"status\":\"1\",\"comment\":\"0\",\"position\":\"5\",\"tid\":\"121\",\"fid\":\"1\",\"dateline_show\":\"2016-03-29 16:08:18\",\"user_info\":{\"id\":\"31\",\"username\":\"M12345678977\",\"nickname\":\"123566\",\"head\":\"948\",\"group\":{\"adminid\":\"2\",\"alloweditpost\":\"1\",\"allowstickthread\":\"1\",\"allowdelpost\":\"1\",\"allowbanuser\":\"1\",\"allowdigestthread\":\"1\",\"allowpost\":\"1\",\"name\":\"\",\"fid\":\"1\"}},\"img\":[]}]},\"notify_id\":\"1459300528\"}";
        System.out.println(s);
        (new Json2Bean(s, "RootBean", (NameGenerator)new MyNameGenerator(), (JsonParse)new MyJsonParser(), (BeanGenerator)new MyBeanGenerator("com.test2"))).execute();
        System.out.println("------------------------------");
        List<List<List<String>>> list = new ArrayList<>();
        List<List<String>> li1 = new ArrayList<>();
        li1.add(Arrays.asList(new String[] { "d", "e", "f" }));
        li1.add(Arrays.asList(new String[] { "d1", "e1", "f1" }));
        List<List<String>> li2 = new ArrayList<>();
        li2.add(Arrays.asList(new String[] { "d", "e", "f" }));
        li2.add(Arrays.asList(new String[] { "d2", "e2", "f2" }));
        list.add(li1);
        list.add(li2);
        s = JSON.toJSONString(list);
        System.out.println(s);
        (new Json2Bean(s, "RootBean", (NameGenerator)new MyNameGenerator(), (JsonParse)new MyJsonParser(), (BeanGenerator)new MyBeanGenerator("com.test3"))).execute();
        System.out.println("------------------------------");
        s = "{\"post_message\":\"[:f002}[:f003}[:f003}[:f004}[:f004}\",\"intlist\":[1,2,3],\"str\":\"{}\"}";
        System.out.println(JsonFormat.format(s));
        (new Json2Bean(s, "RootBean", (NameGenerator)new MyNameGenerator(), (JsonParse)new MyJsonParser(), (BeanGenerator)new MyBeanGenerator("com.test4"))).execute();
        s = "[[[{\"name\":\"xm1\",\"age\":19},{\"name\":\"[xm2\",\"age\":19},{\"name\":\"{xm3\",\"age\":19}],[{\"name\":\"[[xm4\",\"age\":19},{\"name\":\"{{xm5\",\"age\":19}]],[[{\"name\":\"xm6\",\"age\":19},{\"name\":\"xm7\",\"age\":19}],[{\"name\":\"xm8\",\"age\":19}]]]";
        System.out.println(JsonFormat.format(s));
        (new Json2Bean(s, "RootBean", (NameGenerator)new MyNameGenerator(), (JsonParse)new MyJsonParser(), (BeanGenerator)new MyBeanGenerator("com.test5"))).execute();
        s = "{\"multyList\":[[[{\"name\":\"xm1\",\"age\":19}]]]}";
        System.out.println(JsonFormat.format(s));
        (new Json2Bean(s, "RootBean", (NameGenerator)new MyNameGenerator(), (JsonParse)new MyJsonParser(), (BeanGenerator)new MyBeanGenerator("com.test6"))).execute();
    }*/

    public static void main(String[] args) throws IOException {
        //String s = "{\"count\":0.0,\"doubleList\":[1,2,3.0,4],\"intList\":[1,2,3,4,5,6]}";
        //json2Bean(s);
        String s1 = "{\"id\":12, \"b\": \"52626\", \"hello\":[{\"name\": \"guo\", \"student\": {\"a\": 12}},{\"name\": \"guo\",\"student\": {\"a\": 12}}], \"good\":{\"age\" :13}}";
        json2Bean(s1);
    }

    public static void json2Bean(String jsonStr) throws IOException {
        String[] names = new String[]{
                "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J",
                "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z",
                "AA", "BB", "CC"};
        Json2BeanZip json2Bean = new Json2BeanZip(jsonStr, "RootBean",
                new MyNameGenerator(names),
                new MyJsonParser(),
                new MyBeanGeneratorToZip("com.test5"));
        json2Bean.execute(null);
    }
}
