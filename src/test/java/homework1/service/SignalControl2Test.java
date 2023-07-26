package homework1.service;

import homework1.exceptions.PersonNotEntitledToDriveException;
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

public class SignalControl2Test {

    private SignalControl2 signalControl;
    private Person testPerson;
    private Car testCar;

    @Mock
    private DistanceSensor distanceSensorMock;

    @Mock
    private LightController lightControllerMock;

    @Mock
    private SpeedSensor speedSensorMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        testPerson = Mockito.mock(Person.class);
        testCar = Mockito.mock(Car.class);
        signalControl = new SignalControl2(lightControllerMock, distanceSensorMock, speedSensorMock, testCar, testPerson);
    }


    @Test
    public void shouldSetGreenWhenSpeedIsLessThan40AndDistanceLessThan50() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(50.0, 49.0, 20.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(40.0, 39.0, 20.0);

        signalControl.setLightFor50mRange();
        Mockito.verify(lightControllerMock).setGreen();
    }

    @Test
    public void shouldSetRedWhenSpeedIsMoreThan40AndDistanceMoreThan50() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(51.0, 60.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(41.0, 50.0);

        signalControl.setLightFor50mRange();
        Mockito.verify(lightControllerMock).setRed();
    }

    @Test
    public void shouldSetGreenWhenSpeedIsLessThan50AndDistanceLessThan100AndMoreThan51() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(100.0, 75.0, 51.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(50.0, 49.0, 20.0);

        signalControl.setLightFor100mRange();
        Mockito.verify(lightControllerMock).setGreen();
    }

    @Test
    public void shouldSetRedWhenSpeedIsMoreThan50AndDistanceLessThan100AndMoreThan51() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(100.0, 75.0, 51.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(51.0, 60.0);

        signalControl.setLightFor100mRange();
        Mockito.verify(lightControllerMock).setRed();
    }

    @Test
    public void shouldSetGreenWhenSpeedIsLessThan100AndDistanceLessThan300AndMoreThan101() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(101.0, 200.0, 300.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(99.0, 80.0);

        signalControl.giveAFineImmediatelyIfDeserved();
        Mockito.verify(lightControllerMock).setGreen();
    }

    @Test
    public void shouldSetRedWhenSpeedIsMoreThan100AndDistanceLessThan300AndMoreThan101() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(101.0, 200.0, 300.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(100.0, 101.0);

        signalControl.giveAFineImmediatelyIfDeserved();
        Mockito.verify(lightControllerMock).setRed();
    }

    @Test
    public void shouldSetGreenWhenPreviligedPersonIsDrivingEmergencyCarOnSignalIn300m() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(300.0, 299.0, 200.0, 100.0, 50.0, 49.0);


        Person testPerson2 = new Person("Jan", "Nowak", "90010135201", 0, true);
        Car testCar2 = new Car("GD32598", testPerson2, true);
        testCar2.toggleSignal();
        SignalControl2 signalControl2 =
                new SignalControl2(lightControllerMock, distanceSensorMock, speedSensorMock, testCar2, testPerson2);

        signalControl2.setLight();
        Mockito.verify(lightControllerMock).setGreen();
    }

    @Test
    public void shouldSetRedWhenPreviligedPersonIsDrivingEmergencyCarWith100SpeedIn300mButNotOnSignal() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(300.0, 299.0, 200.0, 100.0, 50.0, 49.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(100.0);


        Person testPerson2 = new Person("Jan", "Nowak", "90010135201", 0, true);
        Car testCar2 = new Car("GD32598", testPerson2, true);
        SignalControl2 signalControl2 =
                new SignalControl2(lightControllerMock, distanceSensorMock, speedSensorMock, testCar2, testPerson2);

        signalControl2.setLight();
        Mockito.verify(lightControllerMock).setRed();
    }

    @Test
    public void shouldSetRedWhenPreviligedPersonIsDrivingNormalCarOnFakeSignalWith100SpeedIn300m() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(300.0, 299.0, 200.0, 100.0, 50.0, 49.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(100.0);


        Person testPerson2 = new Person("Jan", "Nowak", "90010135201", 0, true);
        Car testCar2 = new Car("GD32598", testPerson2, false);
        testCar2.toggleSignal();
        SignalControl2 signalControl2 =
                new SignalControl2(lightControllerMock, distanceSensorMock, speedSensorMock, testCar2, testPerson2);

        signalControl2.setLight();
        Mockito.verify(lightControllerMock).setRed();
    }

    @Test (expected = PersonNotEntitledToDriveException.class)
    public void shouldThrowPersonNotEntitledToDriveExceptionIfNotPrevileged() {
        Person testPerson2 = new Person("Jan", "Nowak", "90010135201", 0, false);
        Car testCar2 = new Car("GD32598", testPerson2, true);
    }


    @Test
    public void shouldAddFineOf8PointsImmediatelyWhenSpeedBetween60And69() {
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(60.0, 65.0, 69.0);

        Person testPerson2 = new Person("Jan", "Nowak", "90010135201", 0, false);
        SignalControl2 signalControl2 =
                new SignalControl2(lightControllerMock, distanceSensorMock, speedSensorMock, testCar, testPerson2);

        signalControl2.addFine();
        assertEquals(8, testPerson2.getPenaltyPoints());
    }

    @Test
    public void shouldGiveFineOf40PointsImmediatelyWhenSpeedBetween100And109AndDistanceBetween101and300() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(101.0, 200.0, 300.0);
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(100.0, 101.0);

        Person testPerson2 = new Person("Jan", "Nowak", "90010135201", 0, false);
        SignalControl2 signalControl2 =
                new SignalControl2(lightControllerMock, distanceSensorMock, speedSensorMock, testCar, testPerson2);

        signalControl2.giveAFineImmediatelyIfDeserved();
        assertEquals(40, testPerson2.getPenaltyPoints());
    }

}