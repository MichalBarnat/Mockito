package homework1.service;

import homework1.exceptions.CarDoNotExistException;
import homework1.exceptions.PersonDoNotExistException;
import homework1.interfaces.DistanceSensor;
import homework1.interfaces.LightController;
import homework1.interfaces.SpeedSensor;
import homework1.model.Car;
import homework1.model.Person;

public class SignalControl3 {
        private LightController lightController;
        private DistanceSensor distanceSensor;
        private SpeedSensor speedSensor;

        private Car car;
        private Person person;

        public SignalControl3(LightController lightController, DistanceSensor distanceSensor, SpeedSensor speedSensor, Car car, Person person) {
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

        //Włacz tryb awaryjny
    public void turnSafeMode(){
        lightController.setYellow();
    }

        //Jesli samochod jest w odleglosci 100 m albo blizej kamera czyta tablibe rejstracyjna
    public String getRegistrationNumFromTheCarIfTheCarIsIn100mRange(){
        double distance = distanceSensor.getDistance();
        if (distance <= 100){
            return car.getRegistrationNum();
        }
        return null;
    }

}
