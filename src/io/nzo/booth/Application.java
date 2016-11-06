package io.nzo.booth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application 
{
	public static void main(String[] args) 
	{
		System.out.println("SpringApplication.run(BoothApplication.class, args);");
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) 
	{
		return builder.build();
	}

//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception 
//	{
//		return args -> { 
//			Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
//			log.info(quote.toString());
//		};
//	}
	
}