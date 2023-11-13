package christmas.controller;

import static camp.nextstep.edu.missionutils.Console.close;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.constant.Date;
import christmas.model.ReservationDay;
import christmas.service.InputReservationDayService;
import christmas.validation.OrderValidator;
import christmas.validation.ReservationDayValidator;
import christmas.view.input.InputView;
import christmas.view.input.Template.InputValidatorTemplate;
import christmas.view.output.OutputView;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class InputReservationDayControllerTest {
    private final InputReservationDayController inputReservationDayController;
    private final InputReservationDayService inputReservationDayService;
    private final OutputView outputView;

    InputReservationDayControllerTest() {
        outputView = new OutputView();
        inputReservationDayService = new InputReservationDayService(
                new InputView(new ReservationDayValidator(), new OrderValidator(), new InputValidatorTemplate()));
        inputReservationDayController = new InputReservationDayController(inputReservationDayService, outputView);
    }

    @BeforeEach
    void setUp() {
        close();
    }

    @DisplayName("예약 날짜 객체 생성 테스트")
    @ParameterizedTest
    @CsvSource({
            "1, FRIDAY",
            "2, SATURDAY",
            "3, SUNDAY",
            "4, MONDAY",
            "30, SATURDAY",
            "31, SUNDAY"
    })
    void inputReservationDayTest(int day, Date actualDate) {
        //given
        System.setIn(new ByteArrayInputStream(String.valueOf(day).getBytes()));

        //when
        ReservationDay reservationDay = inputReservationDayController.inputReservationDay();

        //then
        assertEquals(reservationDay.getDays(), day);
        assertEquals(reservationDay.getDate(), actualDate.toString());
    }

}