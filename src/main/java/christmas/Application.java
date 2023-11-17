package christmas;

import christmas.controller.InputReservationDayController;
import christmas.controller.OrderMenuController;
import christmas.controller.ShowDetailInfoController;
import christmas.model.Order;
import christmas.model.ReservationDay;
import christmas.service.InputReservationDayService;
import christmas.service.OrderMenuService;
import christmas.service.ShowDetailInfoService;
import christmas.validation.OrderValidator;
import christmas.validation.ReservationDayValidator;
import christmas.view.input.InputView;
import christmas.view.input.Template.InputValidatorTemplate;
import christmas.view.output.OutputView;

public class Application {
    public static void main(String[] args) {
        // 객체 생성
        InputView inputView = createInputView();
        OutputView outputView = new OutputView();
        InputReservationDayController inputReservationDayController = createInputReservationDayController(inputView,
                outputView);
        OrderMenuController orderMenuController = createOrderMenuController(inputView, outputView);
        ShowDetailInfoController showDetailInfoController = createShowDetailInfoController(outputView);

        // 메서드 호출
        executeControllers(inputReservationDayController, orderMenuController, showDetailInfoController);
    }

    private static InputView createInputView() {
        ReservationDayValidator reservationDayValidator = new ReservationDayValidator();
        OrderValidator orderValidator = new OrderValidator();
        InputValidatorTemplate template = new InputValidatorTemplate();
        return new InputView(reservationDayValidator, orderValidator, template);
    }

    private static InputReservationDayController createInputReservationDayController(InputView inputView,
                                                                                     OutputView outputView) {
        InputReservationDayService inputReservationDayService = new InputReservationDayService(inputView);
        return new InputReservationDayController(inputReservationDayService, outputView);
    }

    private static OrderMenuController createOrderMenuController(InputView inputView, OutputView outputView) {
        OrderMenuService orderMenuService = new OrderMenuService(inputView);
        return new OrderMenuController(orderMenuService, outputView);
    }

    private static ShowDetailInfoController createShowDetailInfoController(OutputView outputView) {
        ShowDetailInfoService showDetailInfoService = new ShowDetailInfoService();
        return new ShowDetailInfoController(showDetailInfoService, outputView);
    }

    private static void executeControllers(InputReservationDayController inputReservationDayController,
                                           OrderMenuController orderMenuController,
                                           ShowDetailInfoController showDetailInfoController) {
        ReservationDay reservationDay = inputReservationDayController.inputReservationDay();
        Order order = orderMenuController.orderMenu();

        showDetailInfoController.showOrderMenu(reservationDay, order); // 주문 메뉴
        showDetailInfoController.beforeDiscount(order); // 할인 전 총주문 금액
        showDetailInfoController.checkGiftMenu(order); // 증정 메뉴
        int totalSalePrice = showDetailInfoController.showBenefit(reservationDay, order); // 혜택 내역 & 총혜택 금액 계산
        showDetailInfoController.printAfterDiscount(order, totalSalePrice); // 할인 후 예상 결제 금액
        showDetailInfoController.printEventBadge(totalSalePrice); // 이벤트 배지 이름
    }
}
