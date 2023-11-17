package christmas.constant;

public enum Date {
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일"),
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일");

    private String date;

    Date(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date;
    }
}
