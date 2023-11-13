package christmas.dto;

public class MenuAndQuantity {
    private final String menuName;
    private final Integer quantity;

    public MenuAndQuantity(String menuName, Integer quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public String getMenuName() {
        return menuName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
