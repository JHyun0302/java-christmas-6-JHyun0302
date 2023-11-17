package christmas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationDayTest {
    @DisplayName("예약 날짜 객체 확인")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31})
    void createOrder(int input) {
        //given

        //when
        ReservationDay actualReservationDay = new ReservationDay(input);

        //then
        assertEquals(input, actualReservationDay.getDays());
    }

}