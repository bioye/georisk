package com.redina.georisk.domain;

import java.io.File;
import java.io.IOException;
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
		//List<RiskLocation> generatedLocations = getInfiniteStream().limit(1000000).collect(Collectors.toList());
		//final AtomicInteger indexHolder = new AtomicInteger();
		//getInfiniteStream().forEach(element->System.out.println(indexHolder.getAndIncrement()+" - "+ element));
		System.out.println(getRandomLocationJson());
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
		ClassPathResource res = new ClassPathResource("Coordinates.csv");    
		File file = new File(res.getPath());
		//File file = new File(LocationStream.class.getResource("Coordinates.csv").getFile());
		//String fileName = "Coordinates.csv";
		//String fileName = "C:\\Users\\Abioye\\Downloads\\Coordinates.csv";
		List<RiskLocation> locations=null;
		try {
			locations = Files.readAllLines(file
					//new File(fileName)
				.toPath())
				.stream()
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
		String fileName = "C:\\Users\\Abioye\\Downloads\\Coordinates.csv";
		Stream<RiskLocation> stream=null;
		try {
			stream=Files.lines(Paths.get(fileName))
					.skip(1)
					.map(line->line.split(","))
					.filter(a->a.length==2)
					.map(a->RiskLocation.getSingleton(a[0], a[1]));
			//stream.forEach(System.out::println);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}
	
	public static Stream<RiskLocation> getLocationsWhile() {
		String fileName = "C:\\Users\\Abioye\\Downloads\\Coordinates.csv";
		Stream<RiskLocation> stream=null;
		boolean k=true;
		while(k) {
		try {			
			stream=Files.lines(Paths.get(fileName))
					.skip(1)
					.map(line->line.split(","))
					.filter(a->a.length==2)
					.map(a->RiskLocation.getSingleton(a[0], a[1]));
			final AtomicInteger indexHolder = new AtomicInteger();
			stream.forEach(element->System.out.println(indexHolder.getAndIncrement()+" - "+ element));
			k=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return stream;
	}
	
	public static Stream<RiskLocation> getLocations2() {
		String fileName = "C:\\Users\\Abioye\\Downloads\\Coordinates.csv";
		Stream<RiskLocation> stream=null;
		//boolean k=true;
		//while(k) {
		try {
			//System.out.format("Lines in file = %,d", Files.readAllLines(new File(fileName).toPath()).size());
			
			stream=Files.lines(Paths.get(fileName))
					.skip(1)
					.map(line->line.split(","))
					.filter(a->a.length==2)
					.map(a->RiskLocation.getSingleton(a[0], a[1]));
			final AtomicInteger indexHolder = new AtomicInteger();
			stream.forEach(element->System.out.println(indexHolder.getAndIncrement()+" - "+ element));
			//k=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//}
		}
		return stream;
	}
	
	public static Stream<String[]> getLocations3() {
		String fileName = "C:\\Users\\Abioye\\Downloads\\Coordinates.csv";
		Stream<String[]> stream=null;
		try {
			//System.out.format("Lines in file = %,d", Files.readAllLines(new File(fileName).toPath()).size());
			
			stream=Files.lines(Paths.get(fileName))
					.skip(1)
					.map(line->line.trim())
					.filter(line->!line.isEmpty())
					.map(line->line.split(","))
					.filter(a->a.length!=2);
			final AtomicInteger indexHolder = new AtomicInteger();
			//todo print arrays nicely not cryptic
			stream.forEach(element->System.out.println(indexHolder.getAndIncrement()+" - "+ element.length));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
