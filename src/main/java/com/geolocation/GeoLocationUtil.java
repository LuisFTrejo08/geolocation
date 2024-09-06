package com.geolocation;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeoLocationUtil {

	private static final String API_KEY = "64905f201ef3d02e1f3b5676d7c0eef2";
	private static final String BASE_URL = "http://api.openweathermap.org/geo/1.0/";

	// This method processes input strings (city/state or zip code)
	public static void getLocationData(List<String> locations) throws Exception {
		for (String location : locations) {
			if (location.matches("\\d{5}")) {
				getCoordinatesByZipCode(location);
			} else {
				String[] parts = location.split(",");
				if (parts.length == 2) {
					String city = parts[0].trim();
					String state = parts[1].trim();
					getCoordinatesByCityState(city, state);
				} else {
					System.out.println("Invalid location format: " + location);
				}
			}
		}
	}

	// API call for city and state
	private static void getCoordinatesByCityState(String city, String state) throws Exception {
		String url = BASE_URL + "direct?q=" + city + "," + state + ",US&limit=1&appid=" + API_KEY;
		String jsonResponse = sendGetRequest(url);

		if (jsonResponse != null) {
			JSONArray jsonArray = new JSONArray(jsonResponse);
			if (jsonArray.length() > 0) {
				JSONObject location = jsonArray.getJSONObject(0);
				printLocationData(location);
			} else {
				System.out.println("No location found for " + city + ", " + state);
			}
		}
	}

	// API call for zip code
	private static void getCoordinatesByZipCode(String zip) throws Exception {
		String url = BASE_URL + "zip?zip=" + zip + ",US&appid=" + API_KEY;
		String jsonResponse = sendGetRequest(url);

		if (jsonResponse != null) {
			JSONObject location = new JSONObject(jsonResponse);
			printLocationData(location);
		}
	}

	// Generic method for sending GET request
	private static String sendGetRequest(String url) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

	// This method prints the retrieved location data
	private static void printLocationData(JSONObject location) {
		String name = location.optString("name", "N/A");
		double lat = location.getDouble("lat");
		double lon = location.getDouble("lon");
		String country = location.optString("country", "N/A");
		String state = location.optString("state", "N/A");

		System.out.println("Place: " + name + ", State: " + state + ", Country: " + country);
		System.out.println("Latitude: " + lat + ", Longitude: " + lon);
		System.out.println("=====================================");
	}

	// Main method to run the utility from the command line
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: geoloc-util [location1] [location2] ...");
			return;
		}
		getLocationData(List.of(args));
	}
}
