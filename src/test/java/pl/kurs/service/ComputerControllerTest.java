package pl.kurs.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kurs.intefaces.RoofController;
import pl.kurs.intefaces.SpeedSensor;

import static org.junit.Assert.*;

public class ComputerControllerTest {

    private ComputerController controller;

    @Mock
    private SpeedSensor speedSensorMock;

    @Mock
    private RoofController roofControllerMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        controller = new ComputerController(roofControllerMock, speedSensorMock);
    }

    @Test
    public void shouldOpenRoofWithSpeed30() {
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(30.0);
        controller.openRoof();
        Mockito.verify(roofControllerMock).open();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgExceptionWhenCloseRoofWithSpeed31() {
        Mockito.when(speedSensorMock.getSpeed()).thenReturn(31.0);
        controller.closeRoof();
    }

}