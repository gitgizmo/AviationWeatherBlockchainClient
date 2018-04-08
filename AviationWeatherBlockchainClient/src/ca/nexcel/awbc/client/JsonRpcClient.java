package ca.nexcel.awbc.client;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

 
public class JsonRpcClient {
	
	/**
	 * A JSON RPC Client
	 */
	private JsonRpcHttpClient client;
	
	/**
	 * Constructor
	 * 
	 * @param url the URL to the JSON RPC server
	 * @param port the JSON RPC server connection port for the chain
	 * @param chainName the name of the chain
	 * @param username RPC server username
	 * @param password RPC server password
	 */
	public JsonRpcClient(String url, String username, String password) {
		Map<String, String> headers = new HashMap<String, String>(1);
        final String un = username;
        final String pw = password;
        
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {          
                return new PasswordAuthentication(un, pw.toCharArray());
            }
        });

        
        try {

			client = new JsonRpcHttpClient(new URL(url), headers);
		} catch (MalformedURLException e) {
			throw new RuntimeException("unable to create JSON RPC Client to " + url );
		}
	}
	
	
	/**
	 * Call to JSO RPC Server
	 * 
	 * @param method JSON RPC method name
	 * @param params JSON RPC call parameters
	 * 
	 * @return return value from the call
	 */
	public String invoke(String method,Object [] params) {
		Object returnValue = "";
        try {
        	returnValue = client.invoke(method, params, Object.class);
		} catch (Throwable e) {
			throw new RuntimeException("JSON RPC server invocation failed for method=" + method + " parameters=" + params.toString());
		}
		
		return returnValue.toString();
	}

}
