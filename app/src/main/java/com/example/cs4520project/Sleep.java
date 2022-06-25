package com.example.cs4520project;

public class Sleep {
    private int sleepHr, sleepMin, wakeHr, wakeMin;

    public Sleep() {

    }

    public Sleep(int sleepHr, int sleepMin, int wakeHr, int wakeMin) {
        this.sleepHr = sleepHr;
        this.sleepMin = sleepMin;
        this.wakeHr = wakeHr;
        this.wakeMin = wakeMin;
    }

    public int getSleepHr() {
        return sleepHr;
    }

    public void setSleepHr(int sleepHr) {
        this.sleepHr = sleepHr;
    }

    public int getSleepMin() {
        return sleepMin;
    }

    public void setSleepMin(int sleepMin) {
        this.sleepMin = sleepMin;
    }

    public int getWakeHr() {
        return wakeHr;
    }

    public void setWakeHr(int wakeHr) {
        this.wakeHr = wakeHr;
    }

    public int getWakeMin() {
        return wakeMin;
    }

    public void setWakeMin(int wakeMin) {
        this.wakeMin = wakeMin;
    }

    @Override
    public String toString() {
        return "Sleep{" +
                "sleepHr=" + sleepHr +
                ", sleepMin=" + sleepMin +
                ", wakeHr=" + wakeHr +
                ", wakeMin=" + wakeMin +
                '}';
    }

    public String getSleepTimeInTwelveHrFormat() {
        String hr, min, meridiem;

        if (sleepMin < 10) {
            min = "0" + sleepMin;
        } else {
            min = Integer.toString(sleepMin);
        }

        if (this.sleepHr < 12) {
            hr = Integer.toString(sleepHr);
            meridiem = "AM";
        } else {
            hr = Integer.toString(sleepHr - 12);
            meridiem = "PM";
        }

        return hr + ":" + min + meridiem;
    }

    public String getWakeTimeInTwelveHrFormat() {
        String hr, min, meridiem;

        if (wakeMin < 10) {
            min = "0" + wakeMin;
        } else {
            min = Integer.toString(wakeMin);
        }

        if (this.wakeHr < 12) {
            hr = Integer.toString(wakeHr);
            meridiem = "AM";
        } else {
            hr = Integer.toString(wakeHr - 12);
            meridiem = "PM";
        }

        return hr + ":" + min + meridiem;
    }

    public String getAmountOfSleep() {
        String hr, min;
        int hours, minutes;

        if (sleepHr >= 12) {
            hours = wakeHr + 24 - sleepHr - 1;
        } else {
            hours = wakeHr - sleepHr;
        }

        if (wakeMin > sleepMin) {
            minutes = wakeMin - sleepMin;
        } else {
            minutes = wakeMin + 60 - sleepMin;
            hours += -1;
        }

        hr = Integer.toString(hours);
        min = Integer.toString(minutes);

        return hr + " hours and " + min + " minutes";
    }
}