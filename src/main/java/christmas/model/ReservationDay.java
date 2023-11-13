package christmas.model;

import christmas.constant.Date;

public class ReservationDay {
    private final int days;
    private final String date;

    public ReservationDay(int days) {
        this.days = days;
        this.date = calculateDay(days);
    }

    public int getDays() {
        return days;
    }

    public String getDate() {
        return date;
    }


    public String calculateDay(int days) {
        Date[] day = Date.values();
        int dayIdx = (days - 1) % 7;
        return day[dayIdx].toString();
    }
}
