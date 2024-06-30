package com.dd.movies_api;

import org.springframework.boot.SpringApplication;

public class TestMoviesApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MoviesApiApplication::main).run(args);
	}

}
