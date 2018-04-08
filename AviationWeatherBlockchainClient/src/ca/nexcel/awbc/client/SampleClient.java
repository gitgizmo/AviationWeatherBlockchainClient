package ca.nexcel.awbc.client;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SampleClient {

	public static void main(String[] args) {
		String url = "http://127.0.0.1:2652/";
 
		String username = "user1";
		String password = "password1";
		
		AviationWeatherBlockchainClient awbc = new AviationWeatherBlockchainClient(url, username, password);
		
		System.out.println("**countries**");
		List<String> countries = awbc.getSupportedCountries();
		output(countries);
		
		System.out.println("**get 10 metars for country and station**");
		List<String> metarsForStation = awbc.getMetarsForCountryAndStationId("GM","GBYD", 60);
		output(metarsForStation);
		
		System.out.println("**get ALL metars for country, station and date**");
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        Date date3 = cal1.getTime();
		List<String> metarsForStationCountryStationDate = awbc.getMetarsForCountryAndStationIdAndObservationDate("CA", "CYYZ", date3);
		output(metarsForStationCountryStationDate);
		
		System.out.println("**get ALL metars for country, station and between two dates**");
		Date date1 = new Date();
		
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date date2 = cal.getTime();

		List<String> metarsForStationCountryStationDates = awbc.getMetarsForCountryAndStationIdAndBetweenObservationDates("CA", "CYYZ", date2, date1);
		output(metarsForStationCountryStationDates);
		
	
	}

	private static void output(List<String> list) {
		int count = 0;
		Iterator<String> i = list.iterator();
		while (i.hasNext()) {
			count++;
			System.out.println(count + " " + i.next());
		}
	}

}
