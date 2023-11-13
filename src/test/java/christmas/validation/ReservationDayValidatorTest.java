package christmas.validation;

import static christmas.constant.ValidateMessage.VISIT_DAY_CANNOT_BE_NULL_OR_EMPTY;
import static christmas.constant.ValidateMessage.VISIT_DAY_IS_NOT_BETWEEN_1_AND_31;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationDayValidatorTest {
    private final ReservationDayValidator reservationDayValidator;

    ReservationDayValidatorTest() {
        reservationDayValidator = new ReservationDayValidator();
    }

    @DisplayName("방문 날짜가 null or empty 이면 예외가 발생한다.")
    @Test
    void isNullOrEmptyTest() {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationDayValidator.isNullOrEmpty(null));

        //then
        assertTrue(exception.getMessage().contains(VISIT_DAY_CANNOT_BE_NULL_OR_EMPTY.message));
    }

    @DisplayName("방문 날짜는 1과 31 사이 숫자인 경우 테스트는 성공한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31})
    void passNotExistDayTest(int inputDay) {
        //given
        int reservationDay = reservationDayValidator.notExistDay(inputDay);

        //then
        assertEquals(reservationDay, inputDay);
    }

    @DisplayName("방문 날짜는 1과 31 사이 숫자가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, 32, 100})
    void notExistDayTest(int inputDay) {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationDayValidator.notExistDay(inputDay));

        //then
        assertTrue(exception.getMessage().contains(VISIT_DAY_IS_NOT_BETWEEN_1_AND_31.message));
    }
}