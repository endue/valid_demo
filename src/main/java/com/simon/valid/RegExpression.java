package com.simon.valid;

/**
 * @data: 2020/6/10 17:24
 * @author: simon
 * @version:
 * @description:
 */
public class RegExpression {

    /**
     * 取值范围：1-10
     */
    public static final String RANGE_1_10 = "^(([1-9])|(10))$";

    /**
     * 校验时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME = "^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]) (0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})$";

}
