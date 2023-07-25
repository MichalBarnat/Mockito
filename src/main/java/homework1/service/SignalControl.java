package homework1.service;

import homework1.exceptions.CarDoNotExistException;
import homework1.exceptions.PersonDoNotExistException;
import homework1.interfaces.DistanceSensor;
import homework1.interfaces.LightController;
import homework1.interfaces.SpeedSensor;
import homework1.model.Car;
import homework1.model.Person;

public class SignalControl {
    private LightController lightController;
    private DistanceSensor distanceSensor;
    private SpeedSensor speedSensor;

    private Car car;
    private Person person;

    public SignalControl(LightController lightController, DistanceSensor distanceSensor, SpeedSensor speedSensor, Car car, Person person) {
        if(car == null) {
            throw new CarDoNotExistException();
        }
        if(person == null) {
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
        if(points > 0) {
            person.setPenaltyPoints(person.getPenaltyPoints() + points);
        }

    }

    //JESLI AUTO ZBLIZA SIE DO SWIATEL (100m) i jedzie max 50km/h to zapal zielone
    public void setGreenIfCarDriveSlowly() {
        double distance = distanceSensor.getDistance();
        double speed = speedSensor.getSpeed();
        if(distance <= 100 && speed <= 50) {
            lightController.setGreen();
        }
    }

    //JESLI ZBLIZA SIE AUTO (300m) KTORE JEDZIE SZYBCIEJ NIZ 100KM/H TO DAJ CZERWONE! [I DODAJ PUNKTY KARNE!]
    public void setRedIfCarDriveToFast() {
        double distance = distanceSensor.getDistance();
        double speed = speedSensor.getSpeed();
        if(distance == 300 && speed >= 100) {

            lightController.setYellow();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lightController.setRed();
        }
        addFine();
    }


    //JESLI ZBLIZA SIE AUTO (300m) UPRZYWILEJOWANE NA SYGNALE TO DAJ ZIELONE
    public void setGreenIfCarOnSignal() {
        double distance = distanceSensor.getDistance();
        if(distance <= 300 && car.isEmergencyVehicle() && car.isOnSignal()) {
            lightController.setGreen();
        }
    }

    //jeżeli jedzie więcje niż 40 kmh i jest w odległości nie większej niż 50m to ma się zapalić czerwone światło
    public void setRedIfSpeedIsOver40AndDistanceMax50() {
        double speed = speedSensor.getSpeed();
        double distance = distanceSensor.getDistance();
        if(speed > 40 && distance <= 50) {
            lightController.setRed();
        }
    }



}
