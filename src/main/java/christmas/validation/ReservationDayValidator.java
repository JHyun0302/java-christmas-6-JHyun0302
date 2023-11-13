package christmas.validation;

import static christmas.constant.ValidateMessage.VISIT_DAY_CANNOT_BE_NULL_OR_EMPTY;
import static christmas.constant.ValidateMessage.VISIT_DAY_IS_NOT_BETWEEN_1_AND_31;
import static christmas.constant.ValidateMessage.VISIT_DAY_IS_NOT_INTEGER;

public class ReservationDayValidator {

    public void isNullOrEmpty(String reservationDay) {
        if (reservationDay == null || reservationDay.length() == 0) {
            VISIT_DAY_CANNOT_BE_NULL_OR_EMPTY.throwException();
        }
    }

    public int isInteger(String reservationDay) {
        if (!isNumeric(reservationDay)) {
            VISIT_DAY_IS_NOT_INTEGER.throwException();
        }
        return Integer.parseInt(reservationDay);
    }

    private boolean isNumeric(String reservationDay) {
        try {
            Integer.parseInt(reservationDay);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int notExistDay(int day) {
        if (day < 1 || day > 31) {
            VISIT_DAY_IS_NOT_BETWEEN_1_AND_31.throwException();
        }
        return day;
    }
}
