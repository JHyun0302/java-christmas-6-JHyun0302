package christmas.service;

import christmas.view.input.InputView;

public class InputReservationDayService {
    private final InputView inputView;

    public InputReservationDayService(InputView inputView) {
        this.inputView = inputView;
    }

    public int inputReservationDay() {
        while (true) {
            try {
                return inputView.readDate();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
