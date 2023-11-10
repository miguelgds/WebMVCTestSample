package com.miguelgs.webmvctest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SpringBootApplication
public class WebmvctestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebmvctestApplication.class, args);
	}

	@RestController
	public static class HelloController {
		@GetMapping("/hello")
		public Greeting hello(@RequestParam(name = "name") Optional<String> name)	{
			return name.map(n -> new Greeting(String.format("Hello %s!", n)))
					.orElseThrow(() -> new NoNameException());
		}
	}

	@RestControllerAdvice
	public static class GlobalExceptionHandler {

		@ExceptionHandler(NoNameException.class)
		public ResponseEntity<Error> handleNoNameException(NoNameException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Error(1, e.getMessage()));
		}
	}

	public record Greeting(String name){};
	public record Error(int code, String description){};

	public static class NoNameException extends RuntimeException {
		public NoNameException() {
			super("The name field is mandatory");
		}
	}
}
