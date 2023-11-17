package christmas.controller;

import christmas.model.Order;
import christmas.model.ReservationDay;
import christmas.service.ShowDetailInfoService;
import christmas.view.output.OutputView;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ShowDetailInfoControllerTest {
    private final ShowDetailInfoController showDetailInfoController;
    private final ShowDetailInfoService showDetailInfoService;
    private final OutputView outputView;

    ShowDetailInfoControllerTest() {
        outputView = new OutputView();
        showDetailInfoService = new ShowDetailInfoService();
        showDetailInfoController = new ShowDetailInfoController(showDetailInfoService, outputView);
    }

    @DisplayName("주문 메뉴 출력")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3; '타파스-1,제로콜라-1, 초코케이크-2'",
            "4; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "10; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "11; '시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "27; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "31; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'"
    })
    void showOrderMenu(int days, String input) {
        //given
        ReservationDay reservationDay = new ReservationDay(days);
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        //then
        showDetailInfoController.showOrderMenu(reservationDay, order);
    }

    @DisplayName("할인 전 총주문 금액 출력")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1, 초코케이크-2",
            "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
            "시저샐러드-2, 티본스테이크-1, 아이스크림-5",
            "시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1",
            "시저샐러드-2, 티본스테이크-1, 바비큐립-3",
            "시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2"})
    void showBeforeDiscount(String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        //then
        showDetailInfoController.beforeDiscount(order);
    }

    @DisplayName("증정 메뉴 출력")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1, 초코케이크-2",
            "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
            "시저샐러드-2, 티본스테이크-1, 아이스크림-5",
            "시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1",
            "시저샐러드-2, 티본스테이크-1, 바비큐립-3",
            "시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2"})
    void showCheckGiftMenu(String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        //then
        showDetailInfoController.checkGiftMenu(order);
    }

    @DisplayName("혜택 내역 & 총혜택 금액 출력")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3; '타파스-1,제로콜라-1, 초코케이크-2'",
            "4; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "10; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "11; '시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "27; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "31; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'"
    })
    void showBenefit(int day, String input) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        //then
        showDetailInfoController.showBenefit(reservationDay, order);
    }

    @DisplayName("할인 후 예상 결제 금액")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "6246; '타파스-1,제로콜라-1, 초코케이크-2'",
            "30346; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "13015; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "8069; '시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "25000; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "28023; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'"
    })
    void showAfterDiscount(int totalSalePrice, String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        //then
        showDetailInfoController.printAfterDiscount(order, totalSalePrice);
    }

    @DisplayName("할인 후 예상 결제 금액")
    @ParameterizedTest
    @ValueSource(ints = {6246, 30346, 13015, 8069, 25000, 28023})
    void showEventBadge(int totalSalePrice) {
        //then
        showDetailInfoController.printEventBadge(totalSalePrice);
    }


    private static Map<String, Integer> createOrder(String input) {
        Map<String, Integer> expectedOrder = Stream.of(input.split(","))
                .map(item -> item.split("-"))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> Integer.parseInt(arr[1].trim())));
        return expectedOrder;
    }
}