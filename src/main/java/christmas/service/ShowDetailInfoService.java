package christmas.service;

import static christmas.constant.CheckCondition.CHECK_APPLY_BENEFIT;
import static christmas.constant.FoodType.DESSERT;
import static christmas.constant.FoodType.MAIN;
import static christmas.constant.SalePolicyName.CHRISTMAS_SALE;
import static christmas.constant.SalePolicyName.GIFT_SALE;
import static christmas.constant.SalePolicyName.SPECIAL_SALE;
import static christmas.constant.SalePolicyName.WEEKDAY_SALE;
import static christmas.constant.SalePolicyName.WEEKEND_SALE;

import christmas.constant.SalePolicyName;
import christmas.dto.MenuAndQuantity;
import christmas.model.Order;
import christmas.model.ReservationDay;
import christmas.utils.SalePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDetailInfoService {

    public List<MenuAndQuantity> showMenuAndQuantity(Order order) {
        List<MenuAndQuantity> menuAndQuantity = new ArrayList<>();

        for (Map.Entry<String, Integer> menu : order.getMenu().entrySet()) {
            String menuName = menu.getKey();
            Integer quantity = menu.getValue();
            menuAndQuantity.add(new MenuAndQuantity(menuName, quantity));
        }
        return menuAndQuantity;
    }

    public Map<SalePolicyName, Integer> showBenefitDetails(ReservationDay reservationDay, Order order) {
        SalePolicy salePolicy = new SalePolicy();
        Map<SalePolicyName, Integer> totalSales = new HashMap<>();

        totalSales.put(CHRISTMAS_SALE, salePolicy.christmasSale(reservationDay));
        totalSales.put(WEEKDAY_SALE, salePolicy.weekdaySale(reservationDay, order, DESSERT.foodType));
        totalSales.put(WEEKEND_SALE, salePolicy.weekendSale(reservationDay, order, MAIN.foodType));
        totalSales.put(SPECIAL_SALE, salePolicy.specialSale(reservationDay));
        totalSales.put(GIFT_SALE, salePolicy.giftSale(order));

        return totalSales;
    }

    public int calculateTotalSales(Map<SalePolicyName, Integer> totalSales, Order order) {
        if (order.getOrderAmount() > CHECK_APPLY_BENEFIT) {
            return totalSales.values().stream().mapToInt(Integer::intValue).sum();
        }
        return 0;
    }
}
