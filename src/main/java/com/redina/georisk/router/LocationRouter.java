package com.redina.georisk.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.redina.georisk.handler.LocationHandler;

@Configuration
public class LocationRouter {
	
	/*@Bean
	public RouterFunction<ServerResponse> route(LocationHandler locationHandler) {
        return RouterFunctions.route(GET("/locations").and(accept(MediaType.APPLICATION_JSON)), locationHandler::all);
    }*/

}
