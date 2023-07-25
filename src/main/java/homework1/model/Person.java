package homework1.model;

import homework1.exceptions.PenaltyPointsHigherThan24Exception;

public class Person {
    private String name;
    private String surname;
    private String pesel;
    private int penaltyPoints;
    private boolean privilegedPerson;

    public Person(String name, String surname, String pesel, int penaltyPoints, boolean privilegedPerson) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.penaltyPoints = penaltyPoints;
        this.privilegedPerson = privilegedPerson;
        if(penaltyPoints > 24) {
            throw new PenaltyPointsHigherThan24Exception();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
        if(this.penaltyPoints > 24) {
            System.out.println(name +" JUST HAVE LOST DRIVING LICENCE!");
        }
    }

    public boolean isPrivilegedPerson() {
        return privilegedPerson;
    }

    public void setPrivilegedPerson(boolean privilegedPerson) {
        this.privilegedPerson = privilegedPerson;
    }
}
