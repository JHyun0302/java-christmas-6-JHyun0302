package christmas.controller;

import christmas.model.ReservationDay;
import christmas.service.InputReservationDayService;
import christmas.view.output.OutputView;

public class InputReservationDayController {
    private final InputReservationDayService inputReservationDayService;
    private final OutputView outputView;

    public InputReservationDayController(InputReservationDayService inputReservationDayService, OutputView outputView) {
        this.inputReservationDayService = inputReservationDayService;
        this.outputView = outputView;
    }

    public ReservationDay inputReservationDay() {
        outputView.printInputDay();
        return new ReservationDay(inputReservationDayService.inputReservationDay());
    }
}
