package list;

import java.util.*;

public class ReconstructList {

    public static List<String> reconstructOrder(List<List<String>> pairs) {
        List<String> reconstructedList = new ArrayList<>();

        if(isInvalidList(pairs)) {
            return reconstructedList;
        }

        HashMap<String,String> orderMap = new LinkedHashMap<>();

        pairs.forEach(strings -> orderMap.put(strings.get(0), strings.get(1)));
        String tmp = pairs.get(0).get(0);
        while (tmp != null) {
            reconstructedList.add(tmp);
            tmp = orderMap.get(tmp);
        }


        return reconstructedList;
    }

    private static boolean isInvalidList(List<List<String>> pairs) {
        return pairs == null || pairs.stream().flatMap(Collection::stream).allMatch(String::isEmpty);
    }
}
