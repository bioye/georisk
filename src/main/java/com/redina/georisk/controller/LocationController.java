package com.redina.georisk.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redina.georisk.domain.LocationStream;
import com.redina.georisk.domain.RiskLocation;

import reactor.core.publisher.Flux;

@RestController
public class LocationController {
	
	/*@GetMapping("/limited")
	public Flux<RiskLocation> getLimitedLocations(){
		return null;
	}*/

	//@GetMapping(value = "/locations", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@GetMapping("/locations")
	public Flux<RiskLocation> getUnlimitedLocations(){
		return LocationStream.getInfiniteFlux();
	}
	
	@GetMapping("/locationObjects")
	public Flux<String> getUnlimitedJsonLocations(){
		return LocationStream.getInfiniteJsonFlux();
	}

}