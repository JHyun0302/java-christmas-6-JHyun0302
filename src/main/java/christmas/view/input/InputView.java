package christmas.view.input;

import christmas.validation.OrderValidator;
import christmas.validation.ReservationDayValidator;
import christmas.view.input.Template.InputValidatorTemplate;
import java.util.Map;

public class InputView {
    private final ReservationDayValidator reservationDayValidator;
    private final OrderValidator orderValidator;
    private final InputValidatorTemplate template;

    public InputView(ReservationDayValidator reservationDayValidator, OrderValidator orderValidator,
                     InputValidatorTemplate template) {
        this.reservationDayValidator = reservationDayValidator;
        this.orderValidator = orderValidator;
        this.template = template;
    }

    public int readDate() {
        return template.InputWithValidation(this::reservationDayValidate);
    }

    public Map<String, Integer> inputOrder() {
        return template.InputWithValidation(this::orderValidate);
    }

    private int reservationDayValidate(String inputVisitDay) {
        reservationDayValidator.isNullOrEmpty(inputVisitDay);
        int reservationDay = reservationDayValidator.isInteger(inputVisitDay);
        return reservationDayValidator.notExistDay(reservationDay);
    }

    private Map<String, Integer> orderValidate(String order) {
        orderValidator.isNullOrEmpty(order);
        orderValidator.checkErrorInput(order);
        Map<String, Integer> orderMenu = orderValidator.isErrorForm(order);
        orderValidator.isNotExist(orderMenu);
        orderValidator.isMoreThanOne(orderMenu);
        orderValidator.onlyOrderDrink(orderMenu);
        return orderValidator.validateMoreThanTwenty(orderMenu);
    }

}
