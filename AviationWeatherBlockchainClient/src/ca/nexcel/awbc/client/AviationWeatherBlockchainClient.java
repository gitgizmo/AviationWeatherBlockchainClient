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
