package homework1.service;

import homework1.interfaces.DistanceSensor;
import homework1.interfaces.LightController;
import homework1.interfaces.SpeedSensor;
import homework1.model.Car;
import homework1.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class SignalControlTest {

    private SignalControl signalControl;

    private Person person1;

    private Car car1;

    @Mock
    private DistanceSensor distanceSensorMock;

    @Mock
    private LightController lightControllerMock;

    @Mock
    private SpeedSensor speedSensorMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        person1 = new Person("Michal", "Barnat", "80121237898", 4, false);
        car1 = new Car("RZ777", person1, false);
        //person1 = Mockito.mock(Person.class);
        //car1 = Mockito.mock(Car.class);
        signalControl = new SignalControl(lightControllerMock, distanceSensorMock, speedSensorMock, car1, person1);
    }

    @Test
    public void shouldSetGreenIfCarDriveSlowly() {
        //JESLI AUTO ZBLIZA SIE DO SWIATEL (100m) i jedzie max 50km/h to zapal zielone
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(100.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(50.0);
        signalControl.setGreenIfCarDriveSlowly();
        Mockito.verify(lightControllerMock).setGreen();
    }

    @Test
    public void shouldAdd8PointsAsFine() {
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(69.9);
        //Mockito.when(person1.getPenaltyPoints()).thenReturn(8);
        person1.setPenaltyPoints(0);
        signalControl.addFine();
        assertEquals(8, person1.getPenaltyPoints());
    }

    @Test
    public void shouldSetRedLightAndAddFineWhenDistanceIsMin300AndSpeedOver100() {
        //JESLI ZBLIZA SIE AUTO (300m) KTORE JEDZIE SZYBCIEJ NIZ 100KM/H TO DAJ CZERWONE! [I DODAJ PUNKTY KARNE!]
        person1.setPenaltyPoints(0);
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(300.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(101.0);
        signalControl.setRedIfCarDriveToFast();
        Mockito.verify(lightControllerMock).setRed();
        assertEquals(40, person1.getPenaltyPoints());
    }

    @Test
    public void shouldSetGreenLightWhenEmergencyVehicleIsOnSignal() {
        //JESLI ZBLIZA SIE AUTO (300m) UPRZYWILEJOWANE NA SYGNALE TO DAJ ZIELONE
        car1.setEmergencyVehicle(true);
        car1.setOnSignal(true);
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(290.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(120.0);
        signalControl.setGreenIfCarOnSignal();
        Mockito.verify(lightControllerMock).setGreen();
    }

    @Test
    public void shouldSetRedLightWhenCarSpeddIsOver40AndDistanceIsMinimum50m() {
        //jeżeli jedzie więcje niż 40 kmh i jest w odległości nie większej niż 50m to ma się zapalić czerwone światło
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(60.0);
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(40.0);
        signalControl.setRedIfSpeedIsOver40AndDistanceMax50();
        Mockito.verify(lightControllerMock).setRed();
    }


}