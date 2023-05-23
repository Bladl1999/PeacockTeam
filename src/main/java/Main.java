import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Main {

    private Integer groupCounter = 1;
    public Map<String, Set<List<String>>> rowsMap = new HashMap<>();
    public Map<String, Set<String>> interceptionsMap = new ConcurrentHashMap<>();
    public List<Set<List<String>>> interceptionRowSetsList = new LinkedList<>();
    public Map<List<String>, Set<List<String>>> interceptionGroupMap = new HashMap<>();


    public Main() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src\\main\\resources\\lng.txt")))) {
            while (reader.ready()) {
                List<String> rowList = getRowSet(reader.readLine());
                if (!rowList.isEmpty()) buildMaps(rowList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        collapseMaps();
        collapseGroups();
        outputGroups();
    }

    public void buildMaps(List<String> row) {
        for (String rowItem : row) {
            Set<String> vertexSet = new HashSet<>();
            Set<String> iterationRow = new HashSet<>(row);
            iterationRow.remove(rowItem);
            vertexSet.addAll(iterationRow);
            interceptionsMap.merge(rowItem, vertexSet, (x, y) -> {
                x.addAll(y);
                return x;
            });
            Set<List<String>> rowSet = new HashSet<>();
            rowSet.add(row);
            rowsMap.merge(rowItem, rowSet, (x, y) -> {
                x.addAll(y);
                return x;
            });
        }
    }

    private void collapseMaps() {
        Iterator<Map.Entry<String, Set<String>>> interceptionsMapIterator = interceptionsMap.entrySet().iterator();
        while (interceptionsMapIterator.hasNext()) {
            Map.Entry<String, Set<String>> entryIntspMap = interceptionsMapIterator.next();
            String key = entryIntspMap.getKey();
            for (String iteratedValue : entryIntspMap.getValue()) {
                if (interceptionsMap.containsKey(iteratedValue)) {
                    Set<String> collapseIntersections = interceptionsMap.get(key);
                    interceptionsMap.merge(iteratedValue, collapseIntersections, (x, y) -> {
                        x.addAll(y);
                        return x;
                    });
                    Set<List<String>> collapseRows = rowsMap.get(key);
                    rowsMap.merge(iteratedValue, collapseRows, (x, y) -> {
                        x.addAll(y);
                        return x;
                    });
                }
            }
            Set<List<String>> interceptNextRows = rowsMap.get(key);
            interceptionRowSetsList.add(interceptNextRows);
            interceptionsMap.remove(key);
            rowsMap.remove(key);
        }
    }

    private void collapseGroups() {
        Iterator<Set<List<String>>> interceptionRowSetIterator = interceptionRowSetsList.iterator();
        Set<List<String>> currentRowSet = interceptionRowSetIterator.next();
        while (interceptionRowSetIterator.hasNext()) {
            Set<List<String>> rowSet = interceptionRowSetIterator.next();
            boolean isIntercepted = currentRowSet.contains(rowSet);
            if (isIntercepted) {
                rowSet.addAll(currentRowSet);
                interceptionRowSetsList.add(rowSet);
                interceptionRowSetsList.remove(rowSet);
            }
        }
        buildGroups(interceptionRowSetsList);
    }

    public void buildGroups(List<Set<List<String>>> interceptionRowSetsList) {
        for (Set<List<String>> interceptionRowSet : interceptionRowSetsList) {
            Set<List<String>> iterationRowsSet = new HashSet<>(interceptionRowSet);
            for (List<String> row : iterationRowsSet) {
                Set<List<String>> vertexSet = new HashSet<>();
                Set<List<String>> iterationRowSet = new HashSet<>(vertexSet);
                iterationRowSet.remove(row);
                vertexSet.addAll(iterationRowsSet);
                interceptionGroupMap.merge(row, vertexSet, (x, y) -> {
                    x.addAll(y);
                    return x;
                });
            }
        }
    }


    private List<String> getRowSet(String row) {
        return Arrays.stream(row.split(";"))
                .map(item -> item.replaceAll("\\\"", ""))
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
    }


    private void outputGroups() {
        interceptionGroupMap.values().stream()
                .sorted(Comparator.comparingInt(Set::size))
                .distinct().forEach(item -> System.out.printf("\nGroup<%s>: %s" + item,groupCounter++,item));
    }

    public static void main(String[] args) {
        Main main = new Main();
    }


}

