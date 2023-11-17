package christmas.validation;

import static christmas.constant.ValidateMessage.MENU_CANNOT_BE_NULL_OR_EMPTY;
import static christmas.constant.ValidateMessage.MENU_CANNOT_BE_ONLY_DRINK;
import static christmas.constant.ValidateMessage.MENU_CANNOT_ORDER_MORE_THAN_TWENTY;
import static christmas.constant.ValidateMessage.MENU_ERROR_FORM;
import static christmas.constant.ValidateMessage.MENU_IS_DUPLICATE;
import static christmas.constant.ValidateMessage.MENU_IS_NOT_EXIST;
import static christmas.constant.ValidateMessage.MENU_ORDER_MORE_THAN_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderValidatorTest {
    private final OrderValidator orderValidator;

    OrderValidatorTest() {
        orderValidator = new OrderValidator();
    }

    @DisplayName("주문 내용이 null or empty 이면 예외가 발생한다.")
    @Test
    void isNullOrEmptyTest() {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.isNullOrEmpty(null));

        //then
        assertTrue(exception.getMessage().contains(MENU_CANNOT_BE_NULL_OR_EMPTY.message));
    }

    @DisplayName("첫 입력 문자가 한글이 아니면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd-1", "123-1", "abc-1,123-2"})
    void firstInputMustBeKorean(String inputOrder) {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.checkErrorInput(inputOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_ERROR_FORM.message));
    }

    @DisplayName("입력한 내용 중 ',,'가 있으면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {",,타파스-1,제로콜라-1", "타파스-1,,제로콜라-1", "타파스-1,제로콜라-1,,"})
    void containDoubleCommaTest(String inputOrder) {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.checkErrorInput(inputOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_ERROR_FORM.message));
    }

    @DisplayName("마지막 입력 문자는 숫자이거나 빈칸이 아니면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1aa", "타파스-1,제로콜라-1,", "타파스-1,제로콜라-1,바비큐립"})
    void lastInputMustBeNumberOrBlank(String inputOrder) {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.checkErrorInput(inputOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_ERROR_FORM.message));
    }

    @DisplayName("주문 입력 폼이 올바른 경우 테스트는 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1", "시저샐러드-2, 티본스테이크-1, 아이스크림-5"})
    void rightOrderForm(String inputOrder) {
        //given
        Map<String, Integer> expectedOrder = getErrorOrder(inputOrder);

        //when
        Map<String, Integer> actualOrder = orderValidator.isErrorForm(inputOrder);

        //then
        assertEquals(expectedOrder, actualOrder);
    }

    @DisplayName("중복된 메뉴를 주문하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,타파스-1", "티본스테이크-1,바비큐립-1, 티본스테이크-3", "시저샐러드-2, 아이스크림-1, 아이스크림-5"})
    void duplicateOrderMenu(String inputOrder) {
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.isErrorForm(inputOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_IS_DUPLICATE.message));
    }

    @DisplayName("존재하지 않은 음식을 주문하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,다코야끼-1", "양갈비-1,소고기-1, 스테이크-3", "사과샐러드-2, 터키아이스크림-1, 초코아이스크림-5"})
    void isNotExistTest(String inputOrder) {
        //given
        Map<String, Integer> errorOrder = getErrorOrder(inputOrder);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.isNotExist(errorOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_IS_NOT_EXIST.message));
    }

    @DisplayName("주문을 1개 이상 하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-0,제로콜라-1", "티본스테이크-0,바비큐립-0,초코케이크-0,제로콜라-0", "시저샐러드-2, 티본스테이크-1, 아이스크림-0"})
    void isMoreThanOneTest(String inputOrder) {
        //given
        Map<String, Integer> errorOrder = getErrorOrder(inputOrder);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.isMoreThanOne(errorOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_ORDER_MORE_THAN_ONE.message));
    }

    @DisplayName("음료만 주문하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"레드와인-1,제로콜라-1", "레드와인-2,제로콜라-2,샴페인-3"})
    void onlyOrderDrinkTest(String inputOrder) {
        //given
        Map<String, Integer> errorOrder = getErrorOrder(inputOrder);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.onlyOrderDrink(errorOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_CANNOT_BE_ONLY_DRINK.message));
    }

    @DisplayName("20개가 넘는 음식을 주문하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"레드와인-1,제로콜라-2,티본스테이크-18", "해산물파스타-18,레드와인-2,아이스크림-5,샴페인-3"})
    void validateMoreThanTwentyTest(String inputOrder) {
        //given
        Map<String, Integer> errorOrder = getErrorOrder(inputOrder);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderValidator.validateMoreThanTwenty(errorOrder));

        //then
        assertTrue(exception.getMessage().contains(MENU_CANNOT_ORDER_MORE_THAN_TWENTY.message));
    }
    
    private static Map<String, Integer> getErrorOrder(String inputOrder) {
        Map<String, Integer> errorOrder = Stream.of(inputOrder.split(","))
                .map(item -> item.split("-"))
                .collect(Collectors.toMap(
                        arr -> arr[0].trim(),
                        arr -> Integer.parseInt(arr[1].trim())));
        return errorOrder;
    }
}