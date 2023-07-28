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

public class SignalControl3Test {

    private SignalControl3 signalControl;
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
        signalControl = new SignalControl3(lightControllerMock, distanceSensorMock, speedSensorMock, testCar, testPerson);
    }

    @Test
    public void shouldSetYellowWhenSafeModeIsOn(){
        signalControl.turnSafeMode();
        Mockito.verify(lightControllerMock).setYellow();
    }

    @Test
    public void shouldReturnCarRegistrationNumIfCarIsIn100mRange() {
        Mockito.when(distanceSensorMock.getDistance()).thenReturn(99.0);
        Mockito.when(testCar.getRegistrationNum()).thenReturn("GW52341");
        String registrationNum = signalControl.getRegistrationNumFromTheCarIfTheCarIsIn100mRange();
        assertEquals("GW52341", registrationNum);
    }


}