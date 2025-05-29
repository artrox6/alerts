package alerts;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertNetworkShortestPathTest extends AlertTest {

    @Test
    void shouldReturnProperValueForInitialGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = List.of("A", "B", "C");
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "C");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnDifferentPathsForDisconnectedGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected1 = List.of("A", "B", "C");
        List<String> expected2 = List.of("E", "F", "H");
        List<String> expected3 = List.of("E", "F", "G");
        createTwoDisconnectedGraph(alertNetwork);

        //When
        List<String> result1 = alertNetwork.findAlertPropagationPath("A", "C");
        List<String> result2 = alertNetwork.findAlertPropagationPath("E", "H");
        List<String> result3 = alertNetwork.findAlertPropagationPath("E", "G");

        //Then
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
        assertEquals(expected3, result3);
    }

    @Test
    void shouldReturnShortestPathIfLongerExist() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = List.of("A", "C", "F");
        createGraphWithLongerAndShorterPathToSameNode(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "F");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnShortestPathForCircleGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = List.of("A");
        createCircleGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForDisconnectedGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createDisconnectedTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "E");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForEmptyGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "E");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullSource() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, "C");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullTarget() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", null);

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullValues() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, null);

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullTargetWithNullFilledGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", null);

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullSourceWithNullFilledGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, "C");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullValuesWithNullFilledGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> expected = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, null);

        //Then
        assertEquals(expected, result);
    }

}