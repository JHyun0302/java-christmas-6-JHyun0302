package christmas.utils;

import static christmas.constant.CheckCondition.CHRISTMAS;
import static christmas.constant.Date.FRIDAY;
import static christmas.constant.Date.MONDAY;
import static christmas.constant.Date.SATURDAY;
import static christmas.constant.Date.SUNDAY;
import static christmas.constant.Date.THURSDAY;
import static christmas.constant.Date.TUESDAY;
import static christmas.constant.Date.WEDNESDAY;
import static christmas.constant.SalePolicyMoney.baseDiscount;
import static christmas.constant.SalePolicyMoney.discountPerDessert;
import static christmas.constant.SalePolicyMoney.increaseDiscountPerDay;

import christmas.constant.Menu;
import christmas.model.Order;
import christmas.model.ReservationDay;
import java.util.Arrays;
import java.util.List;

public class SalePolicy {

    public int christmasSale(ReservationDay reservationDay) {
        int day = reservationDay.getDays();

        if (isInSalesPeriod(day)) {
            return calculateDiscount(day);
        }

        return 0;
    }

    private boolean isInSalesPeriod(int day) {
        return day >= 1 && day <= 25;
    }

    private int calculateDiscount(int day) {
        return baseDiscount + ((day - 1) * increaseDiscountPerDay);
    }

    public int weekdaySale(ReservationDay reservationDay, Order order, String discountFoodType) {
        String date = reservationDay.getDate();

        List<String> discountDays = Arrays.asList(SUNDAY.toString(), MONDAY.toString(), TUESDAY.toString(),
                WEDNESDAY.toString(), THURSDAY.toString());

        if (discountDays.contains(date)) {
            return calculateDiscount(order, discountPerDessert, discountFoodType);
        }
        return 0;
    }

    public int weekendSale(ReservationDay reservationDay, Order order, String discountFoodType) {
        String date = reservationDay.getDate();

        List<String> discountDays = Arrays.asList(FRIDAY.toString(), SATURDAY.toString());

        if (discountDays.contains(date)) {
            return calculateDiscount(order, discountPerDessert, discountFoodType);
        }
        return 0;
    }

    private int calculateDiscount(Order order, int discountPerDessert, String discountFoodType) {
        int totalDiscount = 0;
        List<String> foodTypes = order.getFoodType();
        for (String foodType : foodTypes) {
            if (foodType.equals(discountFoodType)) {
                totalDiscount += discountPerDessert;
            }
        }
        return totalDiscount;
    }

    public int specialSale(ReservationDay reservationDay) {
        int days = reservationDay.getDays();
        String date = reservationDay.getDate();

        List<String> discountDays = Arrays.asList(SUNDAY.toString());
        if (discountDays.contains(date) || days == CHRISTMAS) {
            return baseDiscount;
        }
        return 0;
    }

    public int giftSale(Order order) {
        if (order.getCheckGift()) {
            return Menu.CHAMPAGNE.price;
        }
        return 0;
    }
}
