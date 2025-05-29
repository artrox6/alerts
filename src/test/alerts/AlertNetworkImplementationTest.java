package alerts;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertNetworkImplementationTest {

    @Test
    void shouldReturnProperValueForInitialGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = List.of("A", "B", "C");
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "C");

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnDifferentPathsForDisconnectedGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList1 = List.of("A", "B", "C");
        List<String> myList2 = List.of("E", "F", "H");
        List<String> myList3 = List.of("E", "F", "G");
        createTwoDisconnectedGraph(alertNetwork);

        //When
        List<String> result1 = alertNetwork.findAlertPropagationPath("A", "C");
        List<String> result2 = alertNetwork.findAlertPropagationPath("E", "H");
        List<String> result3 = alertNetwork.findAlertPropagationPath("E", "G");

        //Then
        assertEquals(result1,myList1);
        assertEquals(result2,myList2);
        assertEquals(result3,myList3);
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
        List<String> outcome = List.of("A");
        createCircleGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "A");

        //Then
        assertEquals(result,outcome);
    }

    @Test
    void shouldReturnEmptyListForDisconnectedGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createDisconnectedTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "E");

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForEmptyGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", "E");

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForNullSource() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, "C");

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForNullTarget() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", null);

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForNullValues() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createPDFTestGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, null);

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForNullTargetWithNullFilledGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath("A", null);

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForNullSourceWithNullFilledGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, "C");

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldReturnEmptyListForNullValuesWithNullFilledGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        List<String> myList = Collections.emptyList();
        createNullFilledGraph(alertNetwork);

        //When
        List<String> result = alertNetwork.findAlertPropagationPath(null, null);

        //Then
        assertEquals(result,myList);
    }

    @Test
    void shouldSuggestContainmentEdgesForDisconnectedGraph() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        createTwoDisconnectedGraph(alertNetwork);
        Pair<String,String> expected = new Pair<>("A","B");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
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
        Pair<String,String> expected = new Pair<>("A","B");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
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
        Pair<String,String> expected = new Pair<>("A","D");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
    }

    @Test
    void shouldSuggestContainmentEdgesWithDoublePathGraphWithScatteredOneOnEnd() {
        //Given
        AlertNetworkImplementation alertNetwork = new AlertNetworkImplementation();
        createDoublePathGraphWithScatteredOneOnEnd(alertNetwork);
        Pair<String,String> expected = new Pair<>("A","D");

        //When
        List<Pair<String,String>> result = alertNetwork.suggestContainmentEdges("A");

        //Then
        assertEquals(expected, result.get(0));
    }
    private void createCircleGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'C').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("C","A");
    }

    private void createAcyclicGraphAtRoot(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'C').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","A");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("C","A");
    }

    private void createAcyclicGraphAtRootWithAroundPath(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'D').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("B","A");
        alertNetwork.addDependency("D","C");
        alertNetwork.addDependency("C","B");
    }

    private void createStarGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'S').mapToObj(Character::toString).forEach(alertNetwork::addService); // Generates an IntStream from ASCII 'A' to 'Z'

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","C");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("A","E");

        alertNetwork.addDependency("E","G");
        alertNetwork.addDependency("E","H");
        alertNetwork.addDependency("E","I");

        alertNetwork.addDependency("D","J");
        alertNetwork.addDependency("D","K");
        alertNetwork.addDependency("D","L");

        alertNetwork.addDependency("C","M");
        alertNetwork.addDependency("C","N");
        alertNetwork.addDependency("C","O");

        alertNetwork.addDependency("B","P");
        alertNetwork.addDependency("B","R");
        alertNetwork.addDependency("B","S");
        alertNetwork.addDependency("B","S");
    }

    private void createCircleGraphWithDeeperScatteredGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'I').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("C","A");
        alertNetwork.addDependency("C","D");
        alertNetwork.addDependency("D","E");
        alertNetwork.addDependency("E","F");
        alertNetwork.addDependency("E","G");
        alertNetwork.addDependency("E","H");
        alertNetwork.addDependency("E","I");

    }

    private void createPDFTestGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'D').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("D","C");
    }

    private void createDisconnectedTestGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'E').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("D","C");
    }

    private void createGraphWithLongerAndShorterPathToSameNode(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'F').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","C");
        alertNetwork.addDependency("B","D");
        alertNetwork.addDependency("C","E");
        alertNetwork.addDependency("C","F");
        alertNetwork.addDependency("D","F");
        alertNetwork.addDependency("E","F");
    }

    private void createTwoDisconnectedGraph(AlertNetworkImplementation alertNetwork) {

        IntStream.rangeClosed('A', 'H').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("D","C");
        alertNetwork.addDependency("E","F");
        alertNetwork.addDependency("F","G");
        alertNetwork.addDependency("F","H");
    }

    private void createDoublePathGraphWithScatteredOneOnEnd(AlertNetworkImplementation alertNetwork) {

        IntStream.rangeClosed('A', 'I').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("D","E");
        alertNetwork.addDependency("C","F");
        alertNetwork.addDependency("E","F");
        alertNetwork.addDependency("F","G");
        alertNetwork.addDependency("F","H");
        alertNetwork.addDependency("F","I");
    }

    public void createNullFilledGraph(AlertNetworkImplementation alertNetwork) {
        alertNetwork.addService(null);
    }
}