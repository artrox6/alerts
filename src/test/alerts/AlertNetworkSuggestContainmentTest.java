package alerts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertNetworkSuggestContainmentTest extends AlertTest {

    @Test
    void shouldSuggestContainmentEdgesForDisconnectedGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
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
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
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
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
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
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
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
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
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
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
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
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        createDoublePathGraphWithScatteredOneOnEnd(alertNetwork);
        List<Pair<String,String>> expected = List.of(new Pair<>("A","B"), new Pair<>("A","D"));

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result);
    }

}