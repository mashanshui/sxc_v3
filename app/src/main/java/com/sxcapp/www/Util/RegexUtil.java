package com.sxcapp.www.Util;

/**
 * Created by wenleitao on 2017/8/19.
 */

public class RegexUtil {

    /**
     * 比较真实完整的判断身份证号码的工具
     *
     * @param IdCard 用户输入的身份证号码
     * @return [true符合规范, false不符合规范]
     */
    public static boolean isRealIDCard(String IdCard) {
        if (IdCard != null) {
            int correct = new IdCardUtil(IdCard).isCorrect();
            if (0 == correct) {// 符合规范
                return true;
            }
        }
        return false;
    }
}


