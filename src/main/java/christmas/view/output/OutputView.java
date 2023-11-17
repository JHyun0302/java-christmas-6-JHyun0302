package christmas.view.output;

import static christmas.constant.EventBadge.calculateBadge;
import static christmas.constant.PrintOutMessage.AFTER_DISCOUNT;
import static christmas.constant.PrintOutMessage.BEFORE_DISCOUNT;
import static christmas.constant.PrintOutMessage.BENEFIT_DETAILS;
import static christmas.constant.PrintOutMessage.EVENT_BADGE;
import static christmas.constant.PrintOutMessage.GIFT;
import static christmas.constant.PrintOutMessage.GIFT_MENU;
import static christmas.constant.PrintOutMessage.NONE;
import static christmas.constant.PrintOutMessage.ORDER_MENU;
import static christmas.constant.PrintOutMessage.PLZ_INPUT_ORDER;
import static christmas.constant.PrintOutMessage.PLZ_INPUT_VISIT_DAY;
import static christmas.constant.PrintOutMessage.SHOW_EVENT;
import static christmas.constant.PrintOutMessage.TOTAL_BENEFIT_DETAILS;
import static christmas.constant.PrintOutMessage.beforeDiscount;
import static christmas.constant.PrintOutMessage.printAfterDiscount;
import static christmas.constant.PrintOutMessage.printMenuDetail;
import static christmas.constant.PrintOutMessage.printTotalBenefit;
import static christmas.constant.PrintOutMessage.printTotalBenefitDetails;

import christmas.constant.SalePolicyName;
import christmas.model.Order;
import java.util.Map;

public class OutputView {
    public void printInputDay() {
        System.out.println(PLZ_INPUT_VISIT_DAY.message);
    }

    public void printOrder() {
        System.out.println(PLZ_INPUT_ORDER.message);
    }

    public void printEvent(int reservationDay) {
        System.out.println(SHOW_EVENT.getMessage(reservationDay));
    }

    public void printMenuInfo() {
        System.out.println(ORDER_MENU.message);
    }


    public void printOrderMenu(String menuName, Integer quantity) {
        System.out.println(printMenuDetail(menuName, quantity));
    }

    public void printBeforeDiscount(int beforeDiscount) {
        System.out.println(BEFORE_DISCOUNT.message);
        System.out.println(beforeDiscount(beforeDiscount));
    }

    public void printGiftMenu(Order order) {
        String giftMessage = getGiftMessage(order);
        printMessage(GIFT_MENU.message, giftMessage);
    }

    private String getGiftMessage(Order order) {
        if (order.getCheckGift()) {
            return GIFT.message;
        }
        return NONE.message;
    }

    private void printMessage(String menuMessage, String giftMessage) {
        System.out.println(menuMessage + "\n" + giftMessage);
    }


    public void printBenefitDetails(Map<SalePolicyName, Integer> totalSales, int totalSalePrice) {
        System.out.println(BENEFIT_DETAILS.message);

        if (totalSalePrice == 0) {
            System.out.println(NONE.message);
            return;
        }
        System.out.println(printTotalBenefitDetails(totalSales));
    }


    public void printTotalBenefitPrice(int totalSales) {
        System.out.println(TOTAL_BENEFIT_DETAILS.message);
        System.out.println(printTotalBenefit(totalSales));

    }

    public void printAfterDiscountPrice(Order order, int totalSalePrice) {
        System.out.println(AFTER_DISCOUNT.message);
        System.out.println(printAfterDiscount(order, totalSalePrice));
    }

    public void printEventBadge(int totalSalePrice) {
        System.out.println(EVENT_BADGE.message);
        System.out.println(calculateBadge(totalSalePrice));
    }

}
