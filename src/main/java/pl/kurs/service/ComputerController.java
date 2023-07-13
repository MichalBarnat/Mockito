package pl.kurs.service;

import pl.kurs.intefaces.RoofController;
import pl.kurs.intefaces.SpeedSensor;

public class ComputerController {

    private RoofController roofController;
    private SpeedSensor speedSensor;

    public ComputerController(RoofController roofController, SpeedSensor speedSensor) {
        this.roofController = roofController;
        this.speedSensor = speedSensor;
    }

    public void openRoof() {
        double speed = speedSensor.getSpeed();

        if (speed <= 30.0) {
            roofController.open();
        } else {
            System.out.println("jedziesz za szybko");
        }
    }

    public void closeRoof() {
        double speed = speedSensor.getSpeed();

        if (speed <= 30.0) {
            roofController.close();
        } else {
            throw new IllegalArgumentException("to fassst");
        }
    }
}
