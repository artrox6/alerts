package alerts;

import java.util.*;
import java.util.stream.Collectors;

public class AlertNetworkImpl implements AlertNetwork {

    private final HashMap<String, List<String>> nodes;
    private static final Double THRESHOLD = 0.7;

    public AlertNetworkImpl() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void addService(String service) {
        nodes.put(service, new LinkedList<>());
    }

    @Override
    public void addDependency(String fromService, String toService) {
        nodes.get(fromService).add(toService);
    }

    @Override
    public List<String> getDependencies(String service) {
        return nodes.get(service);
    }

    @Override
    public List<String> findAlertPropagationPath(String source, String target) {
        return findShortestPath(source, target, nodes);
    }

    @Override
    public List<String> getAffectedServices(String source) {
        return depthFirstSearch(source, nodes);
    }

    @Override
    public List<Pair<String, String>> suggestContainmentEdges(String source) {
        return suggestEdgesToCut(source, nodes);
    }

    private Map<String, String> breadthFirstSearch(String source, Map<String, List<String>> graph) {

        if (!graph.containsKey(source)) {
            return Collections.emptyMap();
        }
        LinkedList<String> queue = new LinkedList<>();
        HashSet<String> marked = new HashSet<>();
        LinkedHashMap<String, String> prev = new LinkedHashMap<>();

        queue.add(source);
        marked.add(source);

        while (!queue.isEmpty()) {
            final String node = queue.removeFirst();
                graph.get(node).forEach( neighbor -> {
                    if(!marked.contains(neighbor)) {
                        queue.add(neighbor);
                        marked.add(neighbor);
                        prev.put(neighbor,node);
                    }
                });
        }
        return prev;
    }

    private List<String> depthFirstSearch(String source, Map<String, List<String>> graph) {

        if ( source == null || !graph.containsKey(source)) {
            return Collections.emptyList();
        }

        LinkedList<String> queue = new LinkedList<>();
        HashSet<String> marked = new HashSet<>();
        queue.add(source);
        marked.add(source);

        while (!queue.isEmpty()) {
            final String current = queue.removeFirst();
            List<String> neighbors = graph.get(current);
            for (String neighbor: neighbors) {
                if(!marked.contains(neighbor)) {
                    queue.addFirst(neighbor);
                    marked.add(neighbor);
                }
            }
        }
        return new ArrayList<>(marked);
    }

    private List<String> findPath(String source, String target, Map<String, String> prev) {
        String current = target;
        List<String> path = new LinkedList<>();

        while (current != null) {
            path.add(0, current);
            current = prev.get(current);
        }
        if(!path.isEmpty() && !path.get(0).equals(source)) {
            return new LinkedList<>();
        }
        return path;
    }

    private List<String> findShortestPath(String source, String target, Map<String, List<String>> graph) {
        if (!graph.containsKey(source) || !graph.containsKey(target)) {
            return Collections.emptyList();
        }
        Map<String,String> prev = breadthFirstSearch(source, graph);
        return findPath(source, target, prev);
    }

    private List<Pair<String, String>> suggestEdgesToCut(String source, Map<String, List<String>> graph) {

        HashMap<String, Integer> edgeRemoveStore = new HashMap<>();
        int initialLinkedNodes = depthFirstSearch(source, graph).size();
        int nodesThresholdToMeet = (int) (initialLinkedNodes * THRESHOLD);
        Map<String,List<String>> graphCopy = createDeepCopyOfGraph(graph);

        List<Map.Entry<String, Integer>> sortedEntries = getConnectedNodes(source, graph, graphCopy);

        int sumOfRemovesNodes = 0;
        for (Map.Entry<String, Integer> target : sortedEntries) {
            disableConnection(source, target.getKey(), graphCopy);
            List<String> afterCutLinkedNodes = depthFirstSearch(source, graphCopy);
            int removedNodes = calculateRemovedNodes(initialLinkedNodes, afterCutLinkedNodes);
            initialLinkedNodes = initialLinkedNodes - removedNodes;

            if (isViableCandidate(nodesThresholdToMeet, sumOfRemovesNodes, removedNodes, target)) {
                sumOfRemovesNodes = sumOfRemovesNodes + removedNodes;
                edgeRemoveStore.put(target.getKey(), removedNodes);
            }
        }

        return edgeRemoveStore.keySet().stream().map(integer -> new Pair<>(source, integer)).toList();

    }

    private List<Map.Entry<String, Integer>> getConnectedNodes(String source, Map<String, List<String>> graph, Map<String, List<String>> graphCopy) {
        Map<String, Integer> nextInPathNodes = graphCopy
                .get(source)
                .stream()
                .collect(Collectors.toMap(k -> k, n -> depthFirstSearch(n, graph).size()));
        
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(nextInPathNodes.entrySet());
        
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sortedEntries;
    }

    private boolean isViableCandidate(int nodesThresholdToMet, int removedNodesSum, int removedNodes, Map.Entry<String, Integer> current) {
        if(removedNodesSum > nodesThresholdToMet) {
            return false;
        }
        if(removedNodes == 0) {
            return false;
        }
        return current.getValue() != 0;
    }

    private void disableConnection(String source, String node, Map<String, List<String>> graphCopy) {
        graphCopy.get(source).remove(node);
    }

    private Map<String, List<String>> createDeepCopyOfGraph(Map<String, List<String>> graph) {
        return graph.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, c -> new LinkedList<>(c.getValue())));
    }

    private int calculateRemovedNodes(int initialLinkedNodes, List<String> afterCutLinkedNodes) {
        return initialLinkedNodes - afterCutLinkedNodes.size();
    }

}
