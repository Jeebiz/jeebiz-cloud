package io.hiwepy.cloud.base.quartz.setup.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {

    private Timer timer = new Timer();

    public void scheduleAt(TimerTask task, Date time) {
        timer.schedule(task, time);
    }

    public void scheduleAt(TimerTask task, long delay) {
        timer.schedule(task, delay);
    }

    public void scheduleAt(TimerTask task, long delay, long period) {
        timer.schedule(task, delay, period);
    }

    public void scheduleAt(TimerTask task, Date firstTime, long period) {
        timer.schedule(task, firstTime, period);
    }

    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
        timer.scheduleAtFixedRate(task, firstTime, period);
    }

}
