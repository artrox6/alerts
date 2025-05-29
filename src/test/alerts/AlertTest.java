package alerts;

import java.util.stream.IntStream;

public abstract class AlertTest {
    protected void createCircleGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'C').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("C","A");
    }

    protected void createAcyclicGraphAtRoot(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'C').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","A");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("C","A");
    }

    protected void createAcyclicGraphAtRootWithAroundPath(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'E').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("A","E");
        alertNetwork.addDependency("B","A");
        alertNetwork.addDependency("D","C");
        alertNetwork.addDependency("C","B");
    }

    protected void createStarGraph(AlertNetworkImplementation alertNetwork) {
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

    protected void createCircleGraphWithDeeperScatteredGraph(AlertNetworkImplementation alertNetwork) {
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

    protected void createTwoDisconnectedGraph(AlertNetworkImplementation alertNetwork) {

        IntStream.rangeClosed('A', 'H').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("D","C");
        alertNetwork.addDependency("E","F");
        alertNetwork.addDependency("F","G");
        alertNetwork.addDependency("F","H");
    }

    protected void createDoublePathGraphWithScatteredOneOnEnd(AlertNetworkImplementation alertNetwork) {

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

    protected void createPDFTestGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'D').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("D","C");
    }

    protected void createDisconnectedTestGraph(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'E').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("B","C");
        alertNetwork.addDependency("A","D");
        alertNetwork.addDependency("D","C");
    }

    protected void createGraphWithLongerAndShorterPathToSameNode(AlertNetworkImplementation alertNetwork) {
        IntStream.rangeClosed('A', 'F').mapToObj(Character::toString).forEach(alertNetwork::addService);

        alertNetwork.addDependency("A","B");
        alertNetwork.addDependency("A","C");
        alertNetwork.addDependency("B","D");
        alertNetwork.addDependency("C","E");
        alertNetwork.addDependency("C","F");
        alertNetwork.addDependency("D","F");
        alertNetwork.addDependency("E","F");
    }

    protected void createNullFilledGraph(AlertNetworkImplementation alertNetwork) {
        alertNetwork.addService(null);
    }

}
