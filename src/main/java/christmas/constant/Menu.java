package christmas.constant;

import static christmas.constant.ValidateMessage.MENU_IS_NOT_EXIST;

import java.util.Optional;

public enum Menu {
    PINE_MUSHROOM_SOUP("양송이수프", "appetizer", 6000),
    TAPAS("타파스", "appetizer", 5500),
    CAESAR_SALAD("시저샐러드", "appetizer", 8000),

    T_BONE_STEAK("티본스테이크", "main", 55000),
    BARBECUE_RIBS("바비큐립", "main", 54000),
    SEAFOOD_PASTA("해산물파스타", "main", 35000),
    CHRISTMAS_PASTA("크리스마스파스타", "main", 25000),

    CHOCOLATE_CAKE("초코케이크", "dessert", 15000),
    ICE_CREAM("아이스크림", "dessert", 5000),

    ZERO_COLA("제로콜라", "drink", 3000),
    RED_WINE("레드와인", "drink", 60000),
    CHAMPAGNE("샴페인", "drink", 25000);

    public final String foodName;

    public final String foodType;
    public final int price;

    Menu(String foodName, String foodType, int price) {
        this.foodName = foodName;
        this.foodType = foodType;
        this.price = price;
    }

    public String getFoodType() {
        return foodType;
    }

    public static Optional<String> findFoodType(String menuName) {
        for (Menu menu : Menu.values()) {
            if (menu.foodName.equals(menuName)) {
                return Optional.of(menu.getFoodType());
            }
        }
        return Optional.empty();
    }

    public static Integer getPrice(String menuName) {
        for (Menu menu : Menu.values()) {
            if (menu.foodName.equals(menuName)) {
                return menu.price;
            }
        }
        MENU_IS_NOT_EXIST.throwException();
        return 0;
    }

    public static boolean compareFoodName(String menuName) {
        for (Menu menu : values()) {
            if (menu.foodName.equals(menuName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnlyDrink(String menuName) {
        for (Menu menu : values()) {
            if (menu.foodName.equals(menuName) && menu.foodType.equals("drink")) {
                return true;
            }
        }
        return false;
    }
}

