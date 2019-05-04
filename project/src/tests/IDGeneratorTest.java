package tests;
import org.junit.Test;
import repo.IDgenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IDGeneratorTest {

    IDgenerator generator = new IDgenerator();

    public IDGeneratorTest() {}

    @Test
    public void IDGenerationTestForLast() {
        List<Integer> allIDs = new ArrayList<>();
        allIDs.add(1);
        allIDs.add(2);
        allIDs.add(3);
        Integer expected = 4;
        Integer res = generator.generateNewID(allIDs);
        assertEquals(expected, res);
    }

    @Test
    public void IDGenerationTestWithMissed() {
        List<Integer> allIDs = new ArrayList<>();
        allIDs.add(1);
        allIDs.add(3);
        Integer expected = 2;
        Integer res = generator.generateNewID(allIDs);
        assertEquals(expected, res);
    }

    @Test
    public void IDGenerationTestToStart() {
        List<Integer> allIDs = new ArrayList<>();
        Integer expected = 1;
        Integer res = generator.generateNewID(allIDs);
        assertEquals(expected, res);
    }
}
