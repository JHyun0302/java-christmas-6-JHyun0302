package christmas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

    @DisplayName("생성된 주문 객체 확인")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void createOrder(String input) {
        //given
        Map<String, Integer> createOrder = Stream.of(input.split(","))
                .map(item -> item.split("-"))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> Integer.parseInt(arr[1].trim())));

        //when
        Order actualOrder = new Order(createOrder);

        //then
        assertEquals(createOrder, actualOrder.getMenu());
    }
}