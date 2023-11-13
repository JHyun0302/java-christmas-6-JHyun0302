package christmas.model;

import static christmas.constant.CheckCondition.CHECK_GIFT_MENU;
import static christmas.constant.Menu.getPrice;

import christmas.constant.Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class Order {
    private final Map<String, Integer> menu;
    private final List<String> foodType;
    private final int orderAmount;
    private final boolean checkGift;

    public Order(Map<String, Integer> menu) {
        this.menu = menu;
        this.foodType = findFoodType(menu);
        this.orderAmount = calculateOrderAmount(menu);
        this.checkGift = checkGiftMenu();
    }

    public Map<String, Integer> getMenu() {
        return menu;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public List<String> getFoodType() {
        return foodType;
    }

    public boolean getCheckGift() {
        return checkGift;
    }

    private List<String> findFoodType(Map<String, Integer> menu) {
        List<String> types = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : menu.entrySet()) {
            String menuName = entry.getKey();
            int quantity = entry.getValue();
            Optional<String> foodType = Menu.findFoodType(menuName);

            for (int i = 0; i < quantity; i++) {
                foodType.ifPresent(types::add);
            }
        }
        return types;
    }

    private int calculateOrderAmount(Map<String, Integer> menu) {
        int total = 0;
        for (Entry<String, Integer> entry : menu.entrySet()) {
            String menuName = entry.getKey();
            int quantity = entry.getValue();
            total += getPrice(menuName) * quantity;
        }
        return total;
    }


    private boolean checkGiftMenu() {
        if (orderAmount >= CHECK_GIFT_MENU) {
            return true;
        }
        return false;
    }
}
