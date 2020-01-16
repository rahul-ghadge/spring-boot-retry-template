package com.arya.demo.controller;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.arya.demo.exception.AgeNotInRangeException;
import com.arya.demo.exception.SuperHeroNotFoundException;
import com.arya.demo.model.SuperHero;
import com.arya.demo.util.SuperHeroUtils;

@RestController
public class SuperHeroController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private SuperHeroUtils superHeroUtils;
	
	
	
	@Autowired
	private RetryTemplate retryTemplate;
	

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
    
    
	@GetMapping("/super-heros")
	public List<SuperHero> getHeroSuperHeros() {    	
		List<SuperHero> superHeros =  superHeroUtils.getSuperHeros();
		return superHeros;
	}
    
	
    @Retryable(value = {SuperHeroNotFoundException.class}, maxAttempts = 5)
	@GetMapping("/super-heros/name/{name}")
	public SuperHero getHeroByName(@PathVariable String name) {
    	
    	logger.info("Getting super hero data for {}", name);
    	
    	SuperHero superHero = superHeroUtils.getSuperHeros().stream()
				.filter(hero -> hero.getName().equals(name)).findFirst()
				.orElseThrow(() -> new SuperHeroNotFoundException(name + " not found"));
    	
    	return superHero;
	}
    
    
    @GetMapping("/super-heros/age/{age}")
	public ResponseEntity<SuperHero> getHeroByAge(@PathVariable int age) {
    	
    	return retryTemplate.execute(context -> {
    		
    		logger.info("Retry count : {}", context.getRetryCount());
    		
    		if(context.getRetryCount() > 0) {
    			
    			int recoveredAge = recoverHeroByAge(age, context.getRetryCount());
    			
    			logger.info("Getting super hero data for age {}", recoveredAge);
    			
    			SuperHero superHero = superHeroUtils.getSuperHeros().stream()
        				.filter(hero -> hero.getAge() == recoveredAge).findFirst().get();
        		 
    			logger.info(superHero);
                return new ResponseEntity<SuperHero>(superHero, HttpStatus.OK);
    			
    		} else {
    			
    			logger.info("Getting super hero data for age {}", age);
    			
    			if(20 >= age)
        			throw new AgeNotInRangeException("We don't recruit teen as Super Hero");
            	
    			SuperHero superHero = superHeroUtils.getSuperHeros().stream()
        				.filter(hero -> hero.getAge() == age).findFirst().get();
        		        
                return new ResponseEntity<SuperHero>(superHero, HttpStatus.OK);
    		}   	
    	});
    	
    	
		
	}
    
//	 Increment age by 5 years
    public int recoverHeroByAge(int age, int count) {
    	
//		if (age % 5 == 0)
//			age += 5;
//		else
//			age = 5 * (Math.round(age / 5));
//    	return age;
		
		return (int) ((age % 5 == 0) ? age : 5 * (Math.floor(age / 5))) + 5 * count;
    }
}
