package christmas.utils;

import static christmas.constant.FoodType.DESSERT;
import static christmas.constant.FoodType.MAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.model.Order;
import christmas.model.ReservationDay;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class SalePolicyTest {

    @DisplayName("크리마스 디데이 할인은 1,000원으로 시작하여 크리스마스가 다가올수록 날마다 할인 금액이 100원씩 증가한다.")
    @ParameterizedTest
    @CsvSource({
            "1, 1000",
            "2, 1100",
            "3, 1200",
            "4, 1300",
            "5, 1400",
            "24, 3300",
            "25, 3400"
    })
    void christmasSaleTest(int day, int expectedDiscount) {
        //given
        int christmasSale = getChristmasSale(day);

        //then
        assertEquals(expectedDiscount, christmasSale);
    }

    @DisplayName("12월 26, 27, 28, 29, 30, 31일은 크리마스 디데이 혜택 적용이 안된다.")
    @ParameterizedTest
    @CsvSource({
            "26, 0",
            "27, 0",
            "28, 0",
            "29, 0",
            "30, 0",
            "31, 0",
    })
    void notAdaptChristmasSale(int day, int expectedDiscount) {
        //given
        int christmasSale = getChristmasSale(day);

        //then
        assertEquals(expectedDiscount, christmasSale);
    }

    private static int getChristmasSale(int day) {
        ReservationDay reservationDay = new ReservationDay(day);
        SalePolicy salePolicy = new SalePolicy();
        //when
        int christmasSale = salePolicy.christmasSale(reservationDay);
        return christmasSale;
    }

    @DisplayName("평일 할인은 평일에 디저트 메뉴 1개당 2,023원 할인한다.")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3; 4046; '타파스-1,제로콜라-1, 초코케이크-2'",
            "4; 4046; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "10; 10115; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "11; 6069; '시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "27; 0; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "31; 2023; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'",
    })
    void weekdayTest(int day, int expectedDiscount, String input) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        SalePolicy salePolicy = new SalePolicy();

        //when
        int discount = salePolicy.weekdaySale(reservationDay, order, DESSERT.foodType);

        //then
        assertEquals(expectedDiscount, discount);
    }

    @DisplayName("평일 할인은 일,월,화,수,목요일에만 할인된다.")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1; 0; '타파스-1,제로콜라-1, 초코케이크-2'",
            "2; 0; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "15; 0; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "22; 0; '시저샐러드-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "29; 0; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "30; 0; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'",
    })
    void notAdaptWeekday(int day, int expectedDiscount, String input) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        SalePolicy salePolicy = new SalePolicy();

        //when
        int discount = salePolicy.weekdaySale(reservationDay, order, DESSERT.foodType);

        //then
        assertEquals(expectedDiscount, discount);
    }

    @DisplayName("주말 할인은 메인 메뉴 1개당 2023원 할인된다.")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1; 0; '타파스-1,제로콜라-1, 초코케이크-2'",
            "8; 4046; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "15; 2023; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "22; 6069; '해산물파스타-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "23; 8092; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "30; 2023; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'",
    })
    void weekendSaleTest(int day, int expectedDiscount, String input) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        SalePolicy salePolicy = new SalePolicy();

        //when
        int discount = salePolicy.weekendSale(reservationDay, order, MAIN.foodType);
        //then
        assertEquals(expectedDiscount, discount);
    }

    @DisplayName("주말 할인은 금,토 요일에만 할인된다.")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4; 0; '타파스-1,제로콜라-1, 초코케이크-2'",
            "10; 0; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "17; 0; '시저샐러드-2, 티본스테이크-1, 아이스크림-5'",
            "25; 0; '해산물파스타-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "26; 0; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "31; 0; '시저샐러드-2, 티본스테이크-1, 아이스크림-1, 샴페인-2'",
    })
    void notAdaptWeekend(int day, int expectedDiscount, String input) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);

        SalePolicy salePolicy = new SalePolicy();

        //when
        int discount = salePolicy.weekendSale(reservationDay, order, MAIN.foodType);
        //then
        assertEquals(expectedDiscount, discount);
    }


    @DisplayName("특별 할인은 3, 10, 17, 24, 25, 31일에 총 주문 금액에서 1,000원 할인한다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    void specialSaleTest(int day) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        SalePolicy salePolicy = new SalePolicy();

        //when
        int discount = salePolicy.specialSale(reservationDay);
        //then
        assertEquals(1000, discount);
    }

    @DisplayName("특별 할인은 3, 10, 17, 24, 25, 31일이 아니면 할인이 적용되지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 18, 19, 20, 21, 22, 23, 26, 27, 28, 29, 30})
    void notAdaptSpecialSale(int day) {
        //given
        ReservationDay reservationDay = new ReservationDay(day);
        SalePolicy salePolicy = new SalePolicy();

        //when
        int discount = salePolicy.specialSale(reservationDay);
        //then
        assertEquals(0, discount);
    }

    @DisplayName("증정 이벤트는 총 주문 금액이 12만원 이상일 때 적용된다.")
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "0; '타파스-1,제로콜라-1, 초코케이크-2'",
            "25000; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1'",
            "25000; '티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
            "25000; '해산물파스타-2, 티본스테이크-1, 아이스크림-2, 초코케이크-1'",
            "25000; '시저샐러드-2, 티본스테이크-1, 바비큐립-3'",
            "0; '시저샐러드-2, 아이스크림-1, 샴페인-2'",
    })
    void giftSaleTest(int expectedDiscount, String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);
        Order order = new Order(expectedOrder);
        SalePolicy salePolicy = new SalePolicy();

        //when
        int champagnePrice = salePolicy.giftSale(order);
        //then
        assertEquals(expectedDiscount, champagnePrice);
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