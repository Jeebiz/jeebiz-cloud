/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.setup.quartz;

import org.quartz.CronExpression;
import org.quartz.DateBuilder;

import java.text.ParseException;

public class CronExpressionBuilder {

    /**
     * Create a CronScheduleBuilder with a cron-expression that sets the schedule to
     * fire every day at the given time (hour and minute).
     *
     * @param hour   the hour of day to fire
     * @param minute the minute of the given hour to fire
     * @return the new CronScheduleBuilder
     * @see CronExpression
     */
    public static String dailyAtHourAndMinute(int hour, int minute) {
        DateBuilder.validateHour(hour);
        DateBuilder.validateMinute(minute);

        String cronExpression = String.format("0 %d %d ? * *", minute, hour);

        return cronExpression;
    }

    /**
     * Create a CronScheduleBuilder with a cron-expression that sets the schedule to
     * fire at the given day at the given time (hour and minute) on the given days
     * of the week.
     *
     * @param daysOfWeek the dasy of the week to fire
     * @param hour       the hour of day to fire
     * @param minute     the minute of the given hour to fire
     * @return the new CronScheduleBuilder
     * @see CronExpression
     * @see DateBuilder#MONDAY
     * @see DateBuilder#TUESDAY
     * @see DateBuilder#WEDNESDAY
     * @see DateBuilder#THURSDAY
     * @see DateBuilder#FRIDAY
     * @see DateBuilder#SATURDAY
     * @see DateBuilder#SUNDAY
     */

    public static String atHourAndMinuteOnGivenDaysOfWeek(int hour, int minute, Integer... daysOfWeek) {
        if (daysOfWeek == null || daysOfWeek.length == 0)
            throw new IllegalArgumentException("You must specify at least one day of week.");
        for (int dayOfWeek : daysOfWeek)
            DateBuilder.validateDayOfWeek(dayOfWeek);
        DateBuilder.validateHour(hour);
        DateBuilder.validateMinute(minute);

        String cronExpression = String.format("0 %d %d ? * %d", minute, hour, daysOfWeek[0]);

        for (int i = 1; i < daysOfWeek.length; i++) {
            cronExpression = cronExpression + "," + daysOfWeek[i];
        }

        return cronExpression;
    }

    /**
     * Create a CronScheduleBuilder with a cron-expression that sets the schedule to
     * fire one per week on the given day at the given time (hour and minute).
     *
     * @param dayOfWeek the day of the week to fire
     * @param hour      the hour of day to fire
     * @param minute    the minute of the given hour to fire
     * @return the new CronScheduleBuilder
     * @throws ParseException
     * @see CronExpression
     * @see DateBuilder#MONDAY
     * @see DateBuilder#TUESDAY
     * @see DateBuilder#WEDNESDAY
     * @see DateBuilder#THURSDAY
     * @see DateBuilder#FRIDAY
     * @see DateBuilder#SATURDAY
     * @see DateBuilder#SUNDAY
     */
    public static String weeklyOnDayAndHourAndMinute(int dayOfWeek, int hour, int minute) {
        DateBuilder.validateDayOfWeek(dayOfWeek);
        DateBuilder.validateHour(hour);
        DateBuilder.validateMinute(minute);
        String cronExpression = String.format("0 %d %d ? * %d", minute, hour, dayOfWeek);
        return cronExpression;
    }

    /**
     * Create a CronScheduleBuilder with a cron-expression that sets the schedule to
     * fire one per month on the given day of month at the given time (hour and
     * minute).
     *
     * @param dayOfMonth the day of the month to fire
     * @param hour       the hour of day to fire
     * @param minute     the minute of the given hour to fire
     * @return the new CronScheduleBuilder
     * @see CronExpression
     */
    public static String monthlyOnDayAndHourAndMinute(int dayOfMonth, int hour, int minute) {
        DateBuilder.validateDayOfMonth(dayOfMonth);
        DateBuilder.validateHour(hour);
        DateBuilder.validateMinute(minute);
        String cronExpression = String.format("0 %d %d %d * ?", minute, hour, dayOfMonth);
        return cronExpression;
    }

}
