package tests;

import org.junit.Test;
import repo.DateParser;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class DateParserTest {

    private DateParser dateParser = new DateParser();

    public DateParserTest() {}

    @Test
    public void DateParserTest() throws ParseException {
        String expected, res;
        String toCheck = "Jun 18 20:56 MSK 2009";
        res = dateParser.parseDate(toCheck);
        expected = "2009-06-18";
        assertEquals(expected, res);
    }
}
