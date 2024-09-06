package com.geolocation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class GeoLocationUtilTest {
	 @Test
	    public void testCityStateLookup() {
	        // Testing valid city/state input
	        assertDoesNotThrow(() -> GeoLocationUtil.getLocationData(List.of("Madison, WI")));
	    }

	    @Test
	    public void testZipCodeLookup() {
	        // Testing valid zip code input
	        assertDoesNotThrow(() -> GeoLocationUtil.getLocationData(List.of("90210")));
	    }

	    @Test
	    public void testMultipleLocations() {
	        // Testing multiple locations (city/state + zip code)
	        assertDoesNotThrow(() -> GeoLocationUtil.getLocationData(List.of("Madison, WI", "12345")));
	    }

	    @Test
	    public void testInvalidLocationFormat() {
	        // Testing invalid location input
	        assertDoesNotThrow(() -> GeoLocationUtil.getLocationData(List.of("InvalidFormat")));
	    }
	}
