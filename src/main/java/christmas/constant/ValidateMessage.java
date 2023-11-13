package christmas.constant;

public enum ValidateMessage {
    VISIT_DAY_CANNOT_BE_NULL_OR_EMPTY("방문 날짜는 null 값이거나 빈 값 일 수 없습니다."),
    VISIT_DAY_IS_NOT_INTEGER("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    VISIT_DAY_IS_NOT_BETWEEN_1_AND_31("유효하지 않은 날짜입니다. 다시 입력해 주세요."),


    MENU_CANNOT_BE_NULL_OR_EMPTY("주문 메뉴는 null 값이거나 빈 값 일 수 없습니다."),
    MENU_ERROR_FORM("유효하지 않은 주문입니다. 다시 입력해 주세요.11"),
    MENU_IS_DUPLICATE("유효하지 않은 주문입니다. 다시 입력해 주세요.22"),
    MENU_IS_NOT_EXIST("유효하지 않은 주문입니다. 다시 입력해 주세요.33"),
    MENU_ORDER_MORE_THAN_ONE("유효하지 않은 주문입니다. 다시 입력해 주세요.44"),
    MENU_CANNOT_BE_ONLY_DRINK("유효하지 않은 주문입니다. 다시 입력해 주세요.55"),
    MENU_CANNOT_ORDER_MORE_THAN_TWENTY("한 번에 최대 20개까지만 주문 가능 합니다.");

    private static final String errorMessage = "[ERROR] ";
    public final String message;

    ValidateMessage(String message) {
        this.message = message;
    }

    public void throwException() {
        throw new IllegalArgumentException(errorMessage + message);
    }
}
