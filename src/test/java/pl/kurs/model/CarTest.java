package pl.kurs.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CarTest {

    private Car car;

    @Before
    public void init() {
        car = Mockito.mock(Car.class);
    }

    @Test
    public void shouldReturnFerrariOnGetProduceR() {
        //new Car("Ferrari");
        Mockito.when(car.getProducer()).thenReturn("Ferrari");
        assertEquals("Ferrari", car.getProducer());
    }

    /*
        serwis pogodowy, getWeather(intefejs temperatura) -> jesli jest 35 stopni to zwraca ze jest goraco
        jesli jest -20 to np zwraca ze jest mróz

        ZLE:
        mockuja getWeather() ze zwraca ze jest goraco i sprawdzaja czy jest goraco

        DOBRZE:
        mockujemy getTemperature()  ze zwraca 35, i przy tych 35 testujemy czy getWeather() zwraca ze jest goraco


     */

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionOnGetProducer() {
        Mockito.when(car.getProducer()).thenThrow(new IllegalArgumentException());
        car.getProducer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionOnSetProducer() {
        Mockito.doThrow(new IllegalArgumentException()).when(car).setProducer(Mockito.anyString());
        car.setProducer("Polonfez");
    }
}