package repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    private SimpleDateFormat parser = new SimpleDateFormat("MMM d HH:mm zzz yyyy", Locale.ENGLISH);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public DateParser() {}

    public String parseDate(String input) throws ParseException {
        Date date = parser.parse(input);
        return formatter.format(date);
    }
}
