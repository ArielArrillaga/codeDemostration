package ar.com.tdm.codeDemostration.utils;

import java.io.*;
import java.net.*;

import java.util.HashMap;

import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

//import ar.com.tdm.gol.proxy.entitys.ContratoSuscripto;

public class CallHttp {

	//private final Logger log = LoggerFactory.getLogger(CallHttp.class);

	public static final String[] UNSAFE_CHARACTERS = { " ", ",", "#" };
	public static final String[] ENCODED_CHARACTERS = { "%20", "%2C", "%23" };
	public static final int TIMEOUT = 5000;

	public String llamadoHttp(URL url) throws IOException {

		url = new URL(encodeURL(url.toString()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		HttpURLConnection con = null;

		con = (HttpURLConnection) url.openConnection();

		con.setConnectTimeout(TIMEOUT);
		con.setReadTimeout(TIMEOUT);

		con.setRequestMethod("POST");
		con.setRequestProperty("cache-control", "no-cache");

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			//log.error("Error " + url.toString() + " - Code: " + responseCode);
			return con.getContent().toString();
		}
	}
	
	public String llamadoHttps(URL url) throws IOException {

		url = new URL(encodeURL(url.toString()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		HttpsURLConnection con = null;

		con = (HttpsURLConnection) url.openConnection();

		con.setConnectTimeout(TIMEOUT);
		con.setReadTimeout(TIMEOUT);
		
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("charset", "utf-8");

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			//log.error("Error " + url.toString() + " - Code: " + responseCode);
			return con.getContent().toString();
		}
	}
		
    public static String llamadoHttpPost(URL url) throws IOException {

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();
        
        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("POST");
        con.setRequestProperty("cache-control", "no-cache");

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return con.getContent().toString();
        }
    }

    public static String llamadoHttpGet(URL url) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return con.getContent().toString();
        }
    }

    public static String encodeURL(String strUrl) {
        String encodedURL = strUrl;
        for (int i = 0; i < UNSAFE_CHARACTERS.length; i++) {
            encodedURL = encodedURL.replace(UNSAFE_CHARACTERS[i], ENCODED_CHARACTERS[i]);
        }
        return encodedURL;
    }
    
    public static Boolean llamadoHttpGetBoolean(URL url) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return Boolean.parseBoolean(response.toString());
        } else {
            return Boolean.parseBoolean(con.getContent().toString());
        }
    }
        
    public static Boolean llamadoHttpPutBoolean(URL url) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("PUT");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return Boolean.parseBoolean(response.toString());
        } else {
            return Boolean.parseBoolean(con.getContent().toString());
        }
    }
    
    public static String llamadoHttpPut(URL url, HashMap<String, String> body) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        
        //recorro el hashMap y voy creando el json  en un string, este string es el que se envia como body
        String stringJson ="{";
        int aux = body.size();
        for (Entry<String, String> entry : body.entrySet()) {
	     
	        stringJson = stringJson + "\""+entry.getKey()+"\":\""+entry.getValue()+"\""; // en llamadoHttpPost esta mejorado revisar si surge error
	        aux=aux-1;
	        if (aux>0) {
	        	 stringJson = stringJson + ",";
	        }
	    }
        stringJson = stringJson + "}";
        
        osw.write(stringJson);
     

        osw.flush();
        osw.close();

        con.connect();

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return con.getContent().toString();
        }
    }
    
    public static Boolean llamadoHttpPutBooleanBody(URL url, HashMap<String, String> body) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("PUT");
     
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        
        //recorro el hashMap y voy creando el json  en un string, este string es el que se envia como body
        String stringJson ="{";
        int aux = body.size();
        for (Entry<String, String> entry : body.entrySet()) {
	     
	        stringJson = stringJson + "\""+entry.getKey()+"\":\""+entry.getValue()+"\""; // en llamadoHttpPost esta mejorado revisar si surge error
	        aux=aux-1;
	        if (aux>0) {
	        	 stringJson = stringJson + ",";
	        }
	    }
        stringJson = stringJson + "}";
        
        osw.write(stringJson);
     

        osw.flush();
        osw.close();

        con.connect();

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return Boolean.parseBoolean(response.toString());
        } else {
            return Boolean.parseBoolean(con.getContent().toString());
        }
    }
    
    public static String llamadoHttpPost(URL url, HashMap<String, String> body) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        
        //recorro el hashMap y voy creando el json  en un string, este string es el que se envia como body
        String stringJson ="{";
        int aux = body.size();
        for (Entry<String, String> entry : body.entrySet()) {
            
	     if (entry.getValue()!=null){
	         if (entry.getValue().contains("{")) {
	             stringJson = stringJson + "\""+entry.getKey()+"\":"+entry.getValue();  // si el valor tiene { quiere decir que es un "sub-json" entonces no le agrego las comillas
	         }else {
	             stringJson = stringJson + "\""+entry.getKey()+"\":\""+entry.getValue()+"\"";	             
	         }
         }else{
              stringJson = stringJson + "\""+entry.getKey()+"\":"+entry.getValue();//si el valor es null elimino las comillas, sino me lo toma como un string
         }
	        aux=aux-1;
	        if (aux>0) {
	        	 stringJson = stringJson + ",";
	        }
	    }
        stringJson = stringJson + "}";
        
        osw.write(stringJson);
     

        osw.flush();
        osw.close();

        con.connect();

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return con.getContent().toString();
        }
    }
    
    public static String llamadoHttpPost(URL url, String json) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(TIMEOUT);
        con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

        osw.write(json);
     
        osw.flush();
        osw.close();

        con.connect();

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return con.getContent().toString();
        }
    }

    public static String llamadoHttpDelete(URL url) throws IOException {

        url = new URL(encodeURL(url.toString()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        HttpURLConnection con = null;

        con = (HttpURLConnection) url.openConnection();

     //   con.setConnectTimeout(TIMEOUT);
     //   con.setReadTimeout(TIMEOUT);
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return con.getContent().toString();
        }
    }

}
