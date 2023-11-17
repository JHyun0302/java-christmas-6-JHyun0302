package christmas.controller;

import christmas.constant.SalePolicyName;
import christmas.dto.MenuAndQuantity;
import christmas.model.Order;
import christmas.model.ReservationDay;
import christmas.service.ShowDetailInfoService;
import christmas.view.output.OutputView;
import java.util.List;
import java.util.Map;

public class ShowDetailInfoController {
    private final ShowDetailInfoService showDetailInfoService;
    private final OutputView outputView;

    public ShowDetailInfoController(ShowDetailInfoService showDetailInfoService, OutputView outputView) {
        this.showDetailInfoService = showDetailInfoService;
        this.outputView = outputView;
    }

    // 주문 메뉴
    public void showOrderMenu(ReservationDay reservationDay, Order order) {
        outputView.printEvent(reservationDay.getDays());
        outputView.printMenuInfo();

        List<MenuAndQuantity> menuOrders = showDetailInfoService.showMenuAndQuantity(order);
        for (MenuAndQuantity menuOrder : menuOrders) {
            outputView.printOrderMenu(menuOrder.getMenuName(), menuOrder.getQuantity());
        }
    }

    // 할인 전 총주문 금액
    public void beforeDiscount(Order order) {
        outputView.printBeforeDiscount(order.getOrderAmount());
    }

    // 증정 메뉴
    public void checkGiftMenu(Order order) {
        outputView.printGiftMenu(order);
    }

    // 혜택 내역 & 총혜택 금액 계산
    public int showBenefit(ReservationDay reservationDay, Order order) {
        Map<SalePolicyName, Integer> totalSales = showDetailInfoService.showBenefitDetails(reservationDay, order);
        int totalSalePrice = showDetailInfoService.calculateTotalSales(totalSales, order);
        outputView.printBenefitDetails(totalSales, totalSalePrice);

        outputView.printTotalBenefitPrice(totalSalePrice);
        return totalSalePrice;
    }


    // 할인 후 예상 결제 금액
    public void printAfterDiscount(Order order, int totalSalePrice) {
        outputView.printAfterDiscountPrice(order, totalSalePrice);
    }

    // 이벤트 배지 이름
    public void printEventBadge(int totalSalePrice) {
        outputView.printEventBadge(totalSalePrice);
    }
}
