package homework1.service;

import homework1.exceptions.CarDoNotExistException;
import homework1.exceptions.PersonDoNotExistException;
import homework1.interfaces.DistanceSensor;
import homework1.interfaces.LightController;
import homework1.interfaces.SpeedSensor;
import homework1.model.Car;
import homework1.model.Person;


public class SignalControl2 {

    private LightController lightController;
    private DistanceSensor distanceSensor;
    private SpeedSensor speedSensor;

    private Car car;
    private Person person;

    public SignalControl2(LightController lightController, DistanceSensor distanceSensor, SpeedSensor speedSensor, Car car, Person person) {
        if (car == null) {
            throw new CarDoNotExistException();
        }
        if (person == null) {
            throw new PersonDoNotExistException();
        }
        this.lightController = lightController;
        this.distanceSensor = distanceSensor;
        this.speedSensor = speedSensor;
        this.car = car;
        this.person = person;
    }

    //DODAJ PUNKTY KARNE (zakładamy ze przed każdymi światłami jest ograniczenie 50km/h i za przekroczone każde kolejne 10km/h dajemy 8 punktów)
    public void addFine() {
        double speed = speedSensor.getSpeed();
        double overrun = speed - 50.0;
        int points = (int) (overrun / 10.0) * 8;
        if (points > 0) {
            person.setPenaltyPoints(person.getPenaltyPoints() + points);
        }

    }

    private void setLight() {
        double distance = distanceSensor.getDistance();

        if (isTheCarOnSignal()) {
            setGreen();
        } else if (distance <= 300 && distance > 100) {
            giveAFineIfDeserved();
        } else if (distance <= 100 && distance > 50) {
            setLightFor100mRange();
        } else if (distance <= 50) {
            setLightFor50mRange();
        } else {
            setYellow();
        }
    }

    public boolean isTheCarOnSignal() {
        boolean check = false;
        double distance = distanceSensor.getDistance();
        if (distance <= 300 && car.isEmergencyVehicle() && car.isOnSignal()) {
            check = true;
        }
        return check;
    }

    public void setGreen() {
        lightController.setGreen();
    }

    public void setYellow() {
        lightController.setYellow();
    }

    public void setLightFor50mRange() {
        double speed = speedSensor.getSpeed();
        if (speed > 40) {
            lightController.setRed();
        } else {
            lightController.setGreen();
        }
    }

    public void setLightFor100mRange() {
        double speed = speedSensor.getSpeed();
        if (speed > 50) {
            lightController.setRed();
        } else {
            lightController.setGreen();
        }
    }

    public void giveAFineIfDeserved() {
        double speed = speedSensor.getSpeed();
        if (speed >= 100) {
            lightController.setRed();
            addFine();
        } else {
            lightController.setYellow();
        }
    }


}
