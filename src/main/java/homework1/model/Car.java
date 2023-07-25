package homework1.model;

import homework1.exceptions.PersonDoNotExistException;
import homework1.exceptions.PersonNotEntitledToDriveException;

public class Car {
    private String registrationNum;
    private Person person;
    private boolean emergencyVehicle;
    private boolean onSignal = false;

    public Car(String registrationNum, Person person, boolean emergencyVehicle) {
        if(person == null) {
            throw new PersonDoNotExistException();
        }
        this.registrationNum = registrationNum;
        this.person = person;
        this.emergencyVehicle = emergencyVehicle;
        if(emergencyVehicle && !person.isPrivilegedPerson()) {
            throw new PersonNotEntitledToDriveException();
        }
    }

    public void toggleSignal() {
        onSignal = !onSignal;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isEmergencyVehicle() {
        return emergencyVehicle;
    }

    public void setEmergencyVehicle(boolean emergencyVehicle) {
        this.emergencyVehicle = emergencyVehicle;
    }

    public boolean isOnSignal() {
        return onSignal;
    }

    public void setOnSignal(boolean onSignal) {
        this.onSignal = onSignal;
    }
}
