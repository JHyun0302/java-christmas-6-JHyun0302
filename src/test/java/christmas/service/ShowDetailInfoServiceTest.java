package christmas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.constant.SalePolicyName;
import christmas.dto.MenuAndQuantity;
import christmas.model.Order;
import christmas.model.ReservationDay;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ShowDetailInfoServiceTest {
    private final ShowDetailInfoService showDetailInfoService;

    ShowDetailInfoServiceTest() {
        showDetailInfoService = new ShowDetailInfoService();
    }

    @DisplayName("주문 메뉴와 수량 확인 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void showMenuAndQuantityTest(String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);

        Order order = new Order(expectedOrder);

        //when
        List<MenuAndQuantity> actualMenuAndQuantities = showDetailInfoService.showMenuAndQuantity(order);

        //then
        for (MenuAndQuantity actualMenuAndQuantity : actualMenuAndQuantities) {
            assertTrue(expectedOrder.containsKey(actualMenuAndQuantity.getMenuName()));
            assertEquals(expectedOrder.get(actualMenuAndQuantity.getMenuName()), actualMenuAndQuantity.getQuantity());
        }
    }

    @DisplayName("12월 3일 혜택 내역 확인 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void showBenefitDetailsTest(String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);

        Order order = new Order(expectedOrder);

        //when
        Map<SalePolicyName, Integer> actualBenefitDetails =
                showDetailInfoService.showBenefitDetails(new ReservationDay(3), order);

        //then
        assertNotNull(actualBenefitDetails);

        for (Map.Entry<SalePolicyName, Integer> entry : actualBenefitDetails.entrySet()) {
            assertNotNull(entry.getKey());
            System.out.println("entry.getKey() = " + entry.getKey());
            assertNotNull(entry.getValue());
            System.out.println("entry.getValue() = " + entry.getValue());
        }
    }

    @DisplayName("총 혜택 금액 계산 확인 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void calculateTotalSalesTest(String input) {
        //given
        Map<String, Integer> expectedOrder = createOrder(input);

        Order order = new Order(expectedOrder);

        Map<SalePolicyName, Integer> actualBenefitDetails =
                showDetailInfoService.showBenefitDetails(new ReservationDay(3), order);

        //when
        int totalSales = showDetailInfoService.calculateTotalSales(actualBenefitDetails, order);

        //then
        System.out.println("totalSales = " + totalSales);
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