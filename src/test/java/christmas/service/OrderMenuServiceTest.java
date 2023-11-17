package christmas.service;

import static camp.nextstep.edu.missionutils.Console.close;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.validation.OrderValidator;
import christmas.validation.ReservationDayValidator;
import christmas.view.input.InputView;
import christmas.view.input.Template.InputValidatorTemplate;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderMenuServiceTest {
    private final OrderMenuService orderMenuService;
    private final InputView inputView;

    OrderMenuServiceTest() {
        inputView = new InputView(new ReservationDayValidator(),
                new OrderValidator(),
                new InputValidatorTemplate());
        orderMenuService = new OrderMenuService(inputView);
    }

    @BeforeEach
    void setUp() {
        close();
    }


    @DisplayName("주문 입력 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void inputOrderTest(String input) {
        //given
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Map<String, Integer> expectedOrder = Stream.of(input.split(","))
                .map(item -> item.split("-"))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> Integer.parseInt(arr[1].trim())));

        //when
        Map<String, Integer> actualOrder = orderMenuService.orderMenu();

        //then
        assertEquals(expectedOrder, actualOrder);
    }
}