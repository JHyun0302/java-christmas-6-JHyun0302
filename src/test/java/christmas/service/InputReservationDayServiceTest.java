package christmas.service;

import static camp.nextstep.edu.missionutils.Console.close;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.validation.OrderValidator;
import christmas.validation.ReservationDayValidator;
import christmas.view.input.InputView;
import christmas.view.input.Template.InputValidatorTemplate;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputReservationDayServiceTest {
    private final InputReservationDayService reservationDayService;
    private final InputView inputView;

    InputReservationDayServiceTest() {
        inputView = new InputView(new ReservationDayValidator(),
                new OrderValidator(),
                new InputValidatorTemplate());
        reservationDayService = new InputReservationDayService(inputView);
    }

    @BeforeEach
    void setUp() {
        close();
    }

    @DisplayName("방문 날짜 입력 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26",
            "27", "28", "29", "30", "31"})
    void readDateTest(String input) {
        //given
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when
        int reservationDay = inputView.readDate();

        //then
        assertEquals(reservationDay, Integer.parseInt(input));
    }
}