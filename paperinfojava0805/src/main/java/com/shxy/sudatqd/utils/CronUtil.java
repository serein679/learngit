package com.shxy.sudatqd.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.CronExpression;

import java.text.ParseException;

///**
// * @author Lenl
// * @version v1.0
// * @create 2022-09-28 15:44
// * @description
// */
public class CronUtil {
    public static Boolean TimeInCorn(String cron, String dateStr) throws ParseException {
        if (StringUtils.isBlank(cron) || StringUtils.isBlank(dateStr)) {
            return false;
        }
        CronExpression exp = new CronExpression(cron);
        Boolean inCron = exp.isSatisfiedBy(DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss"));
        return inCron;

    }
}
