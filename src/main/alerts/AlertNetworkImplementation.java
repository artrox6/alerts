package alerts;

import java.util.*;
import java.util.stream.Collectors;

public class AlertNetworkImplementation implements AlertNetwork {

    private final HashMap<String, List<String>> nodes;

    public AlertNetworkImplementation() {
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
        return breadthFirstSearch(source, target, nodes);
    }

    @Override
    public List<String> getAffectedServices(String source) {
        return depthFirstSearch(source, nodes);
    }

    @Override
    public List<Pair<String, String>> suggestContainmentEdges(String source) {
        return suggestEdgesSimpleApproach(source, nodes);
    }
    public Map<String, String> breadthFirst(String source, Map<String, List<String>> graph) {

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

    public List<String> depthFirstSearch(String source, Map<String, List<String>> graph) {

        if (!graph.containsKey(source)) {
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

    public List<String> findPath(String source, String target, Map<String, String> prev) {
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

    public List<String> breadthFirstSearch(String source, String target, Map<String, List<String>> graph) {
        if (!graph.containsKey(source) || !graph.containsKey(target)) {
            return Collections.emptyList();
        }
        Map<String,String> prev = breadthFirst(source, graph);
        return findPath(source, target, prev);
    }

    public List<Pair<String, String>> suggestEdgesSimpleApproach(String source, Map<String, List<String>> graph) {

        HashMap<String, Integer> edgeCounter = new HashMap<>();
        int initialLinkedNodes = depthFirstSearch(source, graph).size();

        for (String node: graph.get(source)) {
            Map<String,List<String>> graphCopy = createDeepCopyOfGraph(graph);
            disableConnection(source, node, graphCopy);
            List<String> afterCutLinkedNodes = depthFirstSearch(source,graphCopy);
            edgeCounter.put(source + node, calculateRemovedNodes(initialLinkedNodes, afterCutLinkedNodes));
        }

        return List.of(edgeCounter.entrySet()
                .stream().max(Comparator.comparingInt(Map.Entry::getValue))
                .map(e -> new Pair<>(e.getKey().substring(0,1),e.getKey().substring(1,2)))
                .orElse(new Pair<>()));

    }

    private static void disableConnection(String source, String node, Map<String, List<String>> graphCopy) {
        graphCopy.get(source).remove(node);
    }

    private static Map<String, List<String>> createDeepCopyOfGraph(Map<String, List<String>> graph) {
        return graph.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, c -> new LinkedList<>(c.getValue())));
    }

    private static int calculateRemovedNodes(int initialLinkedNodes, List<String> afterCutLinkedNodes) {
        return initialLinkedNodes - afterCutLinkedNodes.size();
    }

}
