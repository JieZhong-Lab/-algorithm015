public class ExerciseString {
    //709. 转换成小写字母
    public String toLowerCase(String str) {
        if (str == null || str.length() == 0) return str;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= 'A' || ch <= 'Z') {
                ch += 32;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

}
