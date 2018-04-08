package ca.nexcel.awbc.client;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/*
 * A collection of utility methods.
 * 
 * @author George Franciscus
 */
public class Utils {


    /**
     * Add days to a date
     * 
     * @param date the date to which days are added
     * @param numberOfDaysToAdd the number of days to add to the date
     * @return the date with the days added
     */
    public static Date addDay(Date date, int numberOfDaysToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, numberOfDaysToAdd);
        return cal.getTime();
    }
    
    
    /**
     * Extract the value from a JSON string as presented
     * by the JSON RPC server.
     * 
     * @param text the text containing JSON key and value
     * @param jsonKey the key associated with the value to be extracted
     * @return the value associated with the key
     */
    public static List<String> extractTextFromJSon(String text, String jsonKey) {
      	List<String> list = new ArrayList<String>();
     	String regex =  jsonKey +  "=(.*?)(\\, | \\} | \\])";
     	text = text.replaceAll("\\{", "");
     	text = text.replaceAll("\\}", "");
     	Pattern pattern = Pattern.compile(regex);
     	Matcher matcher = pattern.matcher(text);
     	while(matcher.find()) {
     	  list.add(matcher.group(1));
    	}
    	
    	return list;
    }
    
    
    static public void removeElementsFromListNotStartingWith(List<String> list, String key) {
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
               String listValue = iterator.next();
               if (! listValue.startsWith(key)) {
                 iterator.remove();
               }
        }
    }

    
}
