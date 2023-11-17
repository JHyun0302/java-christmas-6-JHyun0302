package christmas.controller;

import static camp.nextstep.edu.missionutils.Console.close;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.service.OrderMenuService;
import christmas.validation.OrderValidator;
import christmas.validation.ReservationDayValidator;
import christmas.view.input.InputView;
import christmas.view.input.Template.InputValidatorTemplate;
import christmas.view.output.OutputView;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderMenuControllerTest {
    private final OrderMenuController orderMenuController;
    private final OrderMenuService orderMenuService;
    private final OutputView outputView;

    OrderMenuControllerTest() {
        outputView = new OutputView();
        orderMenuService = new OrderMenuService(
                new InputView(new ReservationDayValidator(), new OrderValidator(), new InputValidatorTemplate()));
        orderMenuController = new OrderMenuController(orderMenuService, outputView);
    }

    @BeforeEach
    void setUp() {
        close();
    }

    @DisplayName("주문 객체 생성 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void orderMenuTest(String input) {
        //given
        System.setIn(new ByteArrayInputStream(String.valueOf(input).getBytes()));

        Map<String, Integer> expectedOrder = Stream.of(input.split(","))
                .map(item -> item.split("-"))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> Integer.parseInt(arr[1].trim())));

        //when
        Map<String, Integer> order = orderMenuService.orderMenu();

        //then
        assertEquals(expectedOrder, order);
    }
}