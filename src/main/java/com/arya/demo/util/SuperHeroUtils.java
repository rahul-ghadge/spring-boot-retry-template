package com.arya.demo.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.arya.demo.model.SuperHero;


@Component
public class SuperHeroUtils {
	
	
	public List<SuperHero> getSuperHeros() {

		Supplier<List<SuperHero>> superHerosList = () -> 
			 Arrays.asList(new SuperHero("Wade", "Deadpool", "Street fighter", 28, true), 
					new SuperHero("Bruce", "Hulk", "Doctore", 50, false), 
					new SuperHero("Steve", "Captain America", "Solder", 120, false), 
					new SuperHero("Tony", "Iron Man", "Business man", 45, true),
					new SuperHero("Peter", "Spider Man", "Student", 21, true));

		return superHerosList.get();
	}

}
