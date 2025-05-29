package alerts;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertNetworkAffectedServicesTest extends AlertTest{

    @Test
    void shouldReturnProperValueForInitialGraph() {

        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = List.of("A", "B", "C", "D");
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnDifferentServicesForDisconnectedGraph() {

        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = List.of("A", "B", "C", "D");
        List<String> expected2 = List.of("E", "F", "G", "H");
        createTwoDisconnectedGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices("A");
        List<String> result2 = alertNetwork.getAffectedServices("E");

        //Then
        assertEquals(expected, result);
        assertEquals(expected2, result2);
    }

    @Test
    void shouldReturnServicesForGraphWithLongerAndShorterPath() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = List.of("A", "B", "C", "D", "E", "F");
        createGraphWithLongerAndShorterPathToSameNode(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnServicesForCircleGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = List.of("A","B","C");
        createCircleGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnSingleServiceForSingleDisconnectedNodeGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = List.of("E");
        createDisconnectedTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices("E");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForEmptyGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = Collections.emptyList();

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullSource() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices(null);

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyListForNullTargetWithNullFilledGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        List<String> expected = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.getAffectedServices(null);

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldSuggestContainmentEdgesCircleGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createCircleGraph(alertNetwork);
        List<String> expected = List.of("A", "B", "C");

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldSuggestContainmentEdgesCircleWithDeeperScatteredGraphGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createCircleGraphWithDeeperScatteredGraph(alertNetwork);
        List<String> expected = IntStream.rangeClosed('A', 'I').mapToObj(Character::toString).collect(Collectors.toList());

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldSuggestContainmentEdgesWithStarGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createStarGraph(alertNetwork);
        List<String> expected = List.of("A", "B", "C", "D", "E", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S");

        //When
        List<String> result = alertNetwork.getAffectedServices("A");

        //Then
        assertEquals(expected, result);
    }
    
}