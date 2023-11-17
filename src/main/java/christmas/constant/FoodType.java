package christmas.constant;

public enum FoodType {
    APPETIZER("appetizer"),
    MAIN("main"),
    DESSERT("dessert"),
    DRINK("drink");

    public final String foodType;

    FoodType(String foodType) {
        this.foodType = foodType;
    }
}
