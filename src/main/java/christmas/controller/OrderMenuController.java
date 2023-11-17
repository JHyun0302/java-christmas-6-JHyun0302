package christmas.controller;

import christmas.model.Order;
import christmas.service.OrderMenuService;
import christmas.view.output.OutputView;

public class OrderMenuController {
    private final OrderMenuService orderMenuService;
    private final OutputView outputView;

    public OrderMenuController(OrderMenuService orderMenuService, OutputView outputView) {
        this.orderMenuService = orderMenuService;
        this.outputView = outputView;
    }

    public Order orderMenu() {
        outputView.printOrder();
        return new Order(orderMenuService.orderMenu());
    }
}
