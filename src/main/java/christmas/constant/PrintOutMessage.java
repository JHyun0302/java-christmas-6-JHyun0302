package christmas.constant;

import static christmas.constant.SalePolicyName.CHRISTMAS_SALE;
import static christmas.constant.SalePolicyName.GIFT_SALE;
import static christmas.constant.SalePolicyName.SPECIAL_SALE;
import static christmas.constant.SalePolicyName.WEEKDAY_SALE;
import static christmas.constant.SalePolicyName.WEEKEND_SALE;

import christmas.model.Order;
import java.util.Map;

public enum PrintOutMessage {
    PLZ_INPUT_VISIT_DAY("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n"
            + "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    PLZ_INPUT_ORDER("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),
    SHOW_EVENT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
    ORDER_MENU("\n<주문 메뉴>"),
    BEFORE_DISCOUNT("\n<할인 전 총주문 금액>"),
    GIFT_MENU("\n<증정 메뉴>"),
    GIFT("샴페인 1개"),
    BENEFIT_DETAILS("\n<혜택 내역>"),
    RESULT_CHRISTMAS_D_DAY_SALE("\n크리스마스 디데이 할인: "),
    RESULT_WEEKDAY_SALE("\n평일 할인: "),
    RESULT_WEEKEND_SALE("\n주말 할인: "),
    RESULT_SPECIAL_SALE("\n특별 할인: "),
    RESULT_GIFT_SALE("\n증정 이벤트: "),
    TOTAL_BENEFIT_DETAILS("\n<총혜택 금액>"),
    AFTER_DISCOUNT("\n<할인 후 예상 결제 금액>"),
    EVENT_BADGE("\n<12월 이벤트 배지>"),
    COUNT("개"),
    WON("원"),
    NONE("없음");

    public final String message;

    PrintOutMessage(String message) {
        this.message = message;
    }

    public String getMessage(int reservationDay) {
        return String.format(message, reservationDay);
    }

    public static String printMenuDetail(String menuName, int quantity) {
        return menuName + " " + quantity + COUNT.message;
    }

    public static String beforeDiscount(int beforeDiscount) {
        return String.format("%,d", beforeDiscount) + WON.message;
    }


    public static String printTotalBenefitDetails(Map<SalePolicyName, Integer> totalSales) {
        StringBuilder sb = new StringBuilder();
        appendIfNonZero(sb, RESULT_CHRISTMAS_D_DAY_SALE.message, totalSales.get(CHRISTMAS_SALE));
        appendIfNonZero(sb, RESULT_WEEKDAY_SALE.message, totalSales.get(WEEKDAY_SALE));
        appendIfNonZero(sb, RESULT_WEEKEND_SALE.message, totalSales.get(WEEKEND_SALE));
        appendIfNonZero(sb, RESULT_SPECIAL_SALE.message, totalSales.get(SPECIAL_SALE));
        appendIfNonZero(sb, RESULT_GIFT_SALE.message, totalSales.get(GIFT_SALE));
        return sb.toString();
    }

    private static void appendIfNonZero(StringBuilder sb, String message, Integer value) {
        if (value != 0) {
            sb.append(message).append("-").append(String.format("%,d", value)).append(WON.message);
        }
    }

    public static String printTotalBenefit(int totalSalePrice) {
        if (totalSalePrice == 0) {
            return totalSalePrice + WON.message;
        }

        return "-" + String.format("%,d", totalSalePrice) + WON.message;
    }

    public static String printAfterDiscount(Order order, int totalSalePrice) {
        int afterDiscountPrice = order.getOrderAmount() - totalSalePrice;
        if (order.getCheckGift()) {
            afterDiscountPrice += Menu.CHAMPAGNE.price;
        }
        return formatPrice(afterDiscountPrice);
    }

    private static String formatPrice(int price) {
        return String.format("%,d%s", price, WON.message);
    }
}
