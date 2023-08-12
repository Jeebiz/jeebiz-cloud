package io.hiwepy.cloud.api.utils;


import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 随机数工具类
 */
public class RandomUtil {
    private static SecureRandom srandom = new SecureRandom();

    public static int getRandomNumber() {
        return srandom.nextInt();
    }

    public static int getRandomNumber(int maxInt) {
        return srandom.nextInt(maxInt);
    }

    public static Long getRandomNumber(Long maxInt) {
        return srandom.nextLong();
    }

    public static int getRandomNumber(int minInt, int maxInt) {
        return srandom.nextInt(maxInt - minInt) + minInt;
    }

    public static float getRandomFloat() {
        return srandom.nextFloat();
    }

    public static double getMathRandom() {
        return Math.random();
    }

    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 生成字符串从此序列中取
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static int getMultiRandomVal(Map<Integer, Double> map, int count) {
        double sum = 0;
        for (Double value : map.values()) {
            sum += value;
        }
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(srandom.nextFloat() * sum);
        }
        Collections.sort(list);

        sum = 0;
        int index = 0;
        int result = 0;
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            sum += entry.getValue();
            for (int i = index; i < list.size(); i++, index++) {
                Double random = list.get(i);
                if (random < sum) {
                    result += entry.getKey();
                } else {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 得到八位数随机唯一Id
     *
     * @return
     */
    public static String getRandomNum8() {
        String all = "00000000";
        while (true) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Pattern pattern = compile("[^0-9]");
            Matcher matcher = pattern.matcher(uuid);
           /* Calendar c = Calendar.getInstance();
            int seconds = c.get(Calendar.SECOND);
            all = matcher.replaceAll("").substring(0,6)+seconds;
            Pattern compile = Pattern.compile("^(\\d)\\1{7}$");
            if(!compile.matcher(all).matches()){
                break;
            }*/
            all = matcher.replaceAll("").substring(0, 8);
            all = all.replaceFirst("0", "1");
            if (!isMather(all)) {
                break;
            }
        }
        return all;
    }

    //正则过滤
    private static boolean isMather(String oldStr) {
        String w1 = "^(\\d)\\1{7}$";
        // 不能以 520结尾
        String w2 = "^[0-9]*(520)$";
        // 4-8 位置重复
        String w3 = "^\\d*(\\d)\\1{2,}\\d*$";
        // AAABBB
        String w4 = "^\\d*(\\d)\\1\\1(\\d)\\2\\2\\d*$";
        // AABB
        String w5 = "^\\d*(\\d)\\1(\\d)\\2\\d*$";
        // 匹配4-9位连续的数字
        String w6 = "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){3,}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){3,})\\d";
        Pattern compile1 = compile(w1);
        if (compile1.matcher(oldStr).matches()) {
            return true;
        }
        Pattern compile2 = compile(w2);
        if (compile2.matcher(oldStr).matches()) {
            return true;
        }
        Pattern compile3 = compile(w3);
        if (compile3.matcher(oldStr).matches()) {
            return true;
        }
        Pattern compile4 = compile(w4);
        if (compile4.matcher(oldStr).matches()) {
            return true;
        }
        Pattern compile5 = compile(w5);
        if (compile5.matcher(oldStr).matches()) {
            return true;
        }
        Pattern compile6 = compile(w6);
        if (compile6.matcher(oldStr).matches()) {
            return true;
        }
        if (oldStr.startsWith("520")) {
            return true;
        }
        return false;
    }
}
