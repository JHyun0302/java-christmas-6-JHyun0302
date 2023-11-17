package christmas.constant;

import static christmas.constant.PrintOutMessage.NONE;

import java.util.Arrays;

public enum EventBadge {
    SANTA_BADGE("산타", 20000),
    TREE_BADGE("트리", 10000),
    STAR_BADGE("별", 5000);

    private final String message;
    private final int totalSalesPrice;

    EventBadge(String message, int totalSalesPrice) {
        this.message = message;
        this.totalSalesPrice = totalSalesPrice;
    }

    public String getMessage() {
        return message;
    }

    public int getTotalSalesPrice() {
        return totalSalesPrice;
    }

    public static String calculateBadge(int totalSalePrice) {
        return Arrays.stream(values())
                .filter(badge -> totalSalePrice >= badge.getTotalSalesPrice())
                .findFirst()
                .map(EventBadge::getMessage)
                .orElse(NONE.message);
    }
}