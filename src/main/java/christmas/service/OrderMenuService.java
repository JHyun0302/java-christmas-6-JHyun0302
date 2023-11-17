package christmas.service;

import christmas.view.input.InputView;
import java.util.Map;

public class OrderMenuService {
    private final InputView inputView;

    public OrderMenuService(InputView inputView) {
        this.inputView = inputView;
    }

    public Map<String, Integer> orderMenu() {
        while (true) {
            try {
                return inputView.inputOrder();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
