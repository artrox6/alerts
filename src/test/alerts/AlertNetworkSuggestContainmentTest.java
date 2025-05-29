package alerts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertNetworkSuggestContainmentTest extends AlertTest {

    @Test
    void shouldSuggestContainmentEdgesForDisconnectedGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createTwoDisconnectedGraph(alertNetwork);
        List<Pair<String,String>> expected = List.of(new Pair<>("A","B"), new Pair<>("A","D"));

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldSuggestContainmentEdgesCircleGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createCircleGraph(alertNetwork);
        Pair<String,String> expected = new Pair<>("A","B");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
    }

    @Test
    void shouldSuggestContainmentEdgesCircleWithDeeperScatteredGraphGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createCircleGraphWithDeeperScatteredGraph(alertNetwork);
        Pair<String,String> expected = new Pair<>("A","B");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
    }

    @Test
    void shouldSuggestContainmentEdgesWithStarGraph() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createStarGraph(alertNetwork);
        List<Pair<String,String>> expected = List.of(new Pair<>("A","B"), new Pair<>("A","C"), new Pair<>("A","D"));

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldSuggestContainmentEdgesWithAcyclicGraphAtRoot() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createAcyclicGraphAtRoot(alertNetwork);
        Pair<String,String> expected = new Pair<>("A","B");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
    }

    @Test
    void shouldSuggestContainmentEdgesWithAcyclicGraphAtRootWithAroundPath() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createAcyclicGraphAtRootWithAroundPath(alertNetwork);
        List<Pair<String,String>> expected = List.of(new Pair<>("A","D"), new Pair<>("A","E"));

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result);
    }

    @Test
    void shouldSuggestContainmentEdgesWithDoublePathGraphWithScatteredOneOnEnd() {
        //Given
        AlertNetworkImpl alertNetwork = new AlertNetworkImpl();
        createDoublePathGraphWithScatteredOneOnEnd(alertNetwork);
        List<Pair<String,String>> expected = List.of(new Pair<>("A","B"), new Pair<>("A","D"));

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result);
    }

}