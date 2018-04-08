package ca.nexcel.awbc.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The Aviation Weather Blockchain Client provides a Java API
 * to query the Aviation Weather Blockchain which contains 
 * metars. 
 * <p/>
 * The Aviation Weather Blockchain is implemented using Multichain
 * blockchain implementation, and this class interfaces with the 
 * Multichain JSON RPC server which must be running and granting
 * permissions to the client hosting this library.
 * 
 * @author George Franciscus
 *
 */
public class AviationWeatherBlockchainClient {
	
	private static final String KEY_DATE_FORMAT = "yyyyMMdd";

	/*
	 * The name of the JSON property returned by the JSON RPC Server
	 * containing data
	 */
	private static final String JSON_RPC_DATA_PROPERTY_NAME = "text";
	
	//Multichain methods
	private static final String LISTSTREAMS = "liststreams";
	//private static final String LISTSTREAMKEYS = "liststreamkeys";
	//private static final String LISTSTREAMITEMS = "liststreamitems";
	private static final String LISTSTREAMKEYITEMS = "liststreamkeyitems";

	/**
	 * The JSON RPC Client used to connect to the JSON RPC server
	 */
	private JsonRpcClient jsonRpcClient;
	
	/**
	 * Constructor
	 * 
	 * @param url JSON RPC URL
	 * @param port JSON RPC port number
	 * @param chainName the chain name that is subject of this client
	 */
	public AviationWeatherBlockchainClient(String url, String username, String password) {
		jsonRpcClient = new JsonRpcClient(url, username, password);
	}
	
	
	/**
	 * Obtains the list of supported countries.
	 * 
	 * @return the list of supported countries
	 */
	public List<String> getSupportedCountries() {
		/*
		 * Each country is segregated into a stream.
		 */
		Object[] obs = new Object[]{};
		String returnValue = jsonRpcClient.invoke(LISTSTREAMS, obs);
		List<String> streamNames = Utils.extractTextFromJSon(returnValue, "name");
		streamNames.remove("root"); //remove default stream
		Collections.sort(streamNames);
		return streamNames;
	}
	




	/**
	 * Obtains the "x" most recent recorded metars for a country code
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param count the number of results to return
	 * @param start the offset. Positive from the oldest and negative from the most recent 
	 * 
	 * @return metars
	 */
//	public List<String> getMetarsForCountry(String country, int count, int start) {
//		Object [] params = new Object[] {country, false, count, start};
//		String returnValue = jsonRpcClient.invoke(LISTSTREAMITEMS, params);
//		List<String> metars = Utils.extractTextFromJSon(returnValue, JSON_RPC_DATA_PROPERTY_NAME);
//		Collections.sort(metars);
//		return metars;
//
//	}

	
	/**
	 * Obtains all of the recorded metars for all stations within
	 * a country on a date. Assumes maximum 7,200 metars per
	 * country in a day. 
	 * 
	 * Most countries are less than 50, and a few are less than 130.
	 * Conservatively allow 300.
	 * 
	 *  7,200 = 24 hours * 300
	 *  
	 * @param country an ISO 3166-1 two character country code
	 * @param observationDate the date the metar was observed
	 * 
	 * @return metars
	 */
//	public List<String> getMetarsForCountryAndObservationDate(String country,  Date observationDate) {
//		return getMetarsForCountryAndObservationDate(country, observationDate, 7200);
//	}
	
	/**
	 * Obtains x number of recorded metars for all stations within
	 * a country on a date.
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param observationDate the date the metar was observed
	 * @param the number of metars to obtain
	 * 
	 * @return metars
	 */
//	public List<String> getMetarsForCountryAndObservationDate(String country,  Date observationDate, int count) {
//		SimpleDateFormat sdf = new SimpleDateFormat(KEY_DATE_FORMAT);
//		String date = sdf.format(observationDate);
//		Object [] params = new Object[] {country, date, false, count};
//		String returnValue = jsonRpcClient.invoke(LISTSTREAMKEYITEMS, params);
//		List<String> metars = Utils.extractTextFromJSon(returnValue, JSON_RPC_DATA_PROPERTY_NAME);
//		Collections.reverse(metars);
//		return metars;
//	}
	
	
	/**
	 * Obtains all of the recorded metars for all stations within a date range
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param startObservationDate the starting metar observation date
	 * @param endObservationDate the ending metar observation date
	 * 
	 * @return metar
	 */
//	public List<String> getMetarsForCountryBetweenObservationDates(String country, Date startObservationDate, Date endObservationDate) {
//		if (endObservationDate.before(startObservationDate)) {
//			SimpleDateFormat sdf = new SimpleDateFormat(KEY_DATE_FORMAT);
//			String startDate = sdf.format(startObservationDate);
//			String endDate = sdf.format(endObservationDate);
//			throw new RuntimeException(	"start observation date is after end observation date. "
//					+ "startDate="+startDate+" endDate="+endDate);
//		}
//
//		List<String> metars = new ArrayList<String>();
//		Date processDate = startObservationDate;
//		while (!processDate.after(endObservationDate)) {
//			metars.addAll(getMetarsForCountryAndObservationDate(country,processDate));
//			processDate = Utils.addDay(processDate, 1);
//		}
//		Collections.reverse(metars);
//		return metars;
//	}
	
	
	/**
	 * Obtain all recorded metars for station identifier within a country
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param stationId 4-character ICAO station identifier code
	 * @param count the number of results to return
	 * 
	 * @return metars
	 */
	public List<String> getMetarsForCountryAndStationId(String country, String stationId, int count) {
		Object [] params = new Object[] {country, stationId, false, count};
		String returnValue = jsonRpcClient.invoke(LISTSTREAMKEYITEMS, params);
		List<String> metars = Utils.extractTextFromJSon(returnValue, JSON_RPC_DATA_PROPERTY_NAME);
		Collections.reverse(metars);
		return metars;
	}
	
	
	/**
	 * Obtains the most recent recorded metar for a station identifier
	 * within a country
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param stationId 4-character ICAO station identifier code
	 * 
	 * @return metars
	 */
//	public String getMetarsForCountryAndStationIdLast(String country, String stationId) {
//		List<String> metars = getMetarsForCountryAndStationIdMostRecent(country, stationId, 1);
//		if ((null == metars) || 0 == metars.size()) {
//			return null;
//		}
//		return metars.get(0);
//	}
	
	
	/**
	 * Obtains the "x" most recent recorded metar for a station identifier
	 * within a country
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param stationId 4-character ICAO station identifier code
	 * @param numberOfMostRecent the number of recent metars requested
	 * @return
	 */
//	public List<String> getMetarsForCountryAndStationIdMostRecent(String country, String stationId, int numberOfMostRecent) {
//		Object [] params = new Object[] {country, stationId, false, numberOfMostRecent, -1};
//		String returnValue = jsonRpcClient.invoke(LISTSTREAMKEYITEMS, params);
//		List<String> metars = Utils.extractTextFromJSon(returnValue, JSON_RPC_DATA_PROPERTY_NAME);
//		Collections.reverse(metars);
//		return metars;
//	}
	
	
	/**
	 * Obtains all of the recorded metars for a station identifier within a country
	 * for all stations within a date range
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param stationId 4-character ICAO station identifier code
	 * @param observationDate the date the metar was observed
	 * 
	 * @return metars
	 */
	public List<String> getMetarsForCountryAndStationIdAndObservationDate(String country, String stationId, Date observationDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(KEY_DATE_FORMAT);
		String date = sdf.format(observationDate);
		
		/*
		 * Assume one per hour, and double for an re-issues
		 */
		Object [] params = new Object[] {country, date, false, 7200};
		String returnValue = jsonRpcClient.invoke(LISTSTREAMKEYITEMS, params);
		List<String> metars = Utils.extractTextFromJSon(returnValue, JSON_RPC_DATA_PROPERTY_NAME);
		Utils.removeElementsFromListNotStartingWith(metars, stationId);
//		System.out.println(metars.size());
		Collections.reverse(metars);
		return metars;
	}
	
	
	/**
	 * Obtains all of the recorded metars for a station within a country code
	 * within a date range 
	 * 
	 * @param country an ISO 3166-1 two character country code
	 * @param stationId 4-character ICAO station identifier code
	 * @param startObservationDate the starting metar observation date
	 * @param endObservationDate the ending metar observation date
	 * 
	 * @return metars
	 */
	public List<String> getMetarsForCountryAndStationIdAndBetweenObservationDates(String country, String stationId, Date startObservationDate, Date endObservationDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(KEY_DATE_FORMAT);
		if (endObservationDate.before(startObservationDate)) {
			String startDate = sdf.format(startObservationDate);
			String endDate = sdf.format(endObservationDate);
			throw new RuntimeException(	"start observation date is after end observation date. "
					+ "startDate="+startDate+" endDate="+endDate);
		}
		
		List<String> metars = new ArrayList<String>();
		Date processDate = startObservationDate;
		while(! processDate.after(endObservationDate)) {
			metars.addAll(getMetarsForCountryAndStationIdAndObservationDate(country, stationId, processDate));
			processDate = Utils.addDay(processDate, 1);
		}
		
		Collections.reverse(metars);
		return metars;
	}
}
