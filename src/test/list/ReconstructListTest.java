package list;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static list.ReconstructList.reconstructOrder;
import static org.junit.jupiter.api.Assertions.*;

class ReconstructListTest {

    @Test
    void shouldReturnOrderedList() {

        //Given
        List<List<String>> pairs =
            List.of(
                    List.of("A", "B"),
                    List.of("B", "C"),
                    List.of("C", "D"),
                    Arrays.asList("D", null)
            );
        List<String> expected = List.of("A", "B", "C", "D");

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnOrderedListWhenValuesAreScrambled() {

        //Given
        List<List<String>> pairs =
                List.of(
                        List.of("A", "B"),
                        List.of("C", "D"),
                        List.of("B", "C"),
                        Arrays.asList("D", null)
                );
        List<String> expected = List.of("A", "B", "C", "D");

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }



    @Test
    void shouldReturnOrderedListWhenNonAlphabeticalOrder() {

        //Given
        List<List<String>> pairs =
                List.of(
                        List.of("Z", "Y"),
                        List.of("Y", "X"),
                        List.of("X", "W"),
                        List.of("W", "V")
                );
        List<String> expected = List.of("Z", "Y", "X", "W", "V");

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnOrderedListWhenLastValueNotNull() {

        //Given
        List<List<String>> pairs =
                List.of(
                        List.of("A", "B"),
                        List.of("C", "D"),
                        List.of("B", "C"),
                        Arrays.asList("D", "E")
                );
        List<String> expected = List.of("A", "B", "C", "D", "E");

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnOrderedListForListWithSingleList() {

        //Given
        List<List<String>> pairs =
                List.of(
                        List.of("A", "B")
                );
        List<String> expected = List.of("A", "B");

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnOrderedListForListWithSingleListAndNullAtEnd() {

        //Given
        List<List<String>> pairs =
                List.of(
                        Arrays.asList("A", null)
                );
        List<String> expected = List.of("A");

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnEmptyListWhenReceivedListWithEmptyLists() {
        //Given
        List<List<String>> pairs =
                List.of(List.of(),List.of());

        List<String> expected = List.of();

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnEmptyListWhenReceivedEmptyList() {
        //Given
        List<List<String>> pairs =
                List.of();

        List<String> expected = List.of();

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }

    @Test
    void shouldReturnEmptyListWhenReceivedNullList() {
        //Given
        List<List<String>> pairs = null;

        List<String> expected = List.of();

        //When
        List<String> reconstructedList = reconstructOrder(pairs);

        //Then
        assertEquals(expected,reconstructedList);
    }
}