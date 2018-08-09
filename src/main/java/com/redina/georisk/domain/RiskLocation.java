package com.redina.georisk.domain;

import io.netty.util.internal.ThreadLocalRandom;
import javax.json.*;

public class RiskLocation {
	
	public String getJsonFeature() {
		JsonObject value = Json.createObjectBuilder()
				.add("type", "Feature")
				.add("geometry", Json.createObjectBuilder()
						.add("type", "Point").add("coordinates", Json.createArrayBuilder()
								.add(longi).add(lati)
						 )
				 ).build();						
		return value.toString();
	}
	
	public String getJsonPoint() {
		JsonObject value = Json.createObjectBuilder()
						.add("type", "Point").add("coordinates", Json.createArrayBuilder()
								.add(longi).add(lati)).build();						
		return value.toString();
	}
	
	public static RiskLocation getSingleton(String longi, String lati) {
		return getSingleton(Float.parseFloat(longi),Float.parseFloat(lati));
	}
	
	public static RiskLocation getNewInstance(String longi, String lati) {
		return new RiskLocation(Float.parseFloat(longi),Float.parseFloat(lati));
	}
	
	public static RiskLocation getSingleton(float longi, float lati) {
		onlyLocation.setLongi(longi);
		onlyLocation.setLati(lati);
		onlyLocation.setRisk(ThreadLocalRandom.current().nextInt(10)+1);
		return onlyLocation;
	}
	
	@Override
	public String toString() {
		return "RiskLocation [longi=" + longi + ", lati=" + lati+  ", risk=" + risk +"]";
	}
	
	
	public float getLongi() {
		return longi;
	}

	public float getLati() {
		return lati;
	}

	public int getRisk() {
		return risk;
	}
	public void setRisk(int risk) {
		this.risk = risk;
	}
	public void setLongi(float longi) {
		this.longi = longi;
	}

	public void setLati(float lati) {
		this.lati = lati;
	}

	public RiskLocation(float longi, float lati) {
		this.longi = longi;
		this.lati = lati;
		//this.risk  = ThreadLocalRandom.current().nextInt(10)+1;
		this.risk  = ThreadLocalRandom.current().nextInt(4)+1;
	}
	
	private RiskLocation() {
	}

	private static RiskLocation onlyLocation = new RiskLocation();
	private int risk;
	//private String riskColour;
	
	//consider Enum
	private float longi;
	private float lati;
}