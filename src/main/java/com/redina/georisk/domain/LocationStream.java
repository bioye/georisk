package com.redina.georisk.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;

public class LocationStream {
	
	public static void main(String[]args) {
		//System.out.println(getRandomLocationJson());
	}
	
	public static Flux<String> getInfiniteJsonFlux() {
		return Flux.fromStream(getInfiniteJsonStream());
	}
	
	public static Flux<RiskLocation> getInfiniteFlux() {
		return Flux.fromStream(getInfiniteStream());
	}
	
	/*
	 * Generates infinite stream from CSV. Loads entire content into memory (list) first.
	 */
	public static Stream<String> getInfiniteJsonStream(){
		Supplier<String> randomSupplier = LocationStream::getRandomLocationJson;
		return Stream.generate(randomSupplier);	
	}
	
	/*
	 * Generates infinite stream from CSV. Loads entire content into memory (list) first.
	 */
	public static Stream<RiskLocation> getInfiniteStream(){
		Supplier<RiskLocation> randomSupplier = LocationStream::getRandomLocation;
		return Stream.generate(randomSupplier);	
	}
	
	/*
	 * Return RiskLocation as GeoJson point in String form
	 */
	public static String getRandomLocationJson() {
		List<RiskLocation> locations = getRiskLocations();
		int index = ThreadLocalRandom.current().nextInt(locations.size());
		return locations.get(index).getJsonPoint();
	}
	
	public static RiskLocation getRandomLocation() {
		List<RiskLocation> locations = getRiskLocations();
		int index = ThreadLocalRandom.current().nextInt(locations.size());
		return locations.get(index);
	}
	
	public static List<RiskLocation> storeAllLocations() {
		List<RiskLocation> locations=null;
		try {
			InputStream fileStream = new ClassPathResource("Coordinates.csv").getInputStream();
			locations=new BufferedReader(new InputStreamReader(fileStream)).lines()
				.skip(1)
				.map(line->line.split(","))
				.filter(a->a.length==2)
				.map(a->RiskLocation.getNewInstance(a[0], a[1]))
				.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("size = "+locations.size());
		return locations;
	}
	/*
	 * Generates a stream from  CSV and ends at EOF.  Only one line loaded into memory at each moment.
	 */
	public static Stream<RiskLocation> getLocations() {
		String fileName = "Coordinates.csv";
		Stream<RiskLocation> stream=null;
		try {
			stream=Files.lines(Paths.get(fileName))
					.skip(1)
					.map(line->line.split(","))
					.filter(a->a.length==2)
					.map(a->RiskLocation.getSingleton(a[0], a[1]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream;
	}
	
	private static List<RiskLocation> riskLocations=null;

	public static List<RiskLocation> getRiskLocations() {
		if(riskLocations==null) riskLocations=storeAllLocations();
		return riskLocations;
	}
}
