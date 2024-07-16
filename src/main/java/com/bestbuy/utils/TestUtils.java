package com.bestbuy.utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class TestUtils {

	public static String getRandomValue() {
		Random random = new Random();
		int randomInt = random.nextInt(100000);
		return Integer.toString(randomInt);
	}

	public static String generateName() {
		Faker faker = new Faker();
		return faker.name().name();
	}

	public static String generateLastName() {
		Faker faker = new Faker();
		return faker.name().lastName();
	}

	public static String generateZipCode() {
		Faker faker = new Faker();
		return faker.address().zipCode();
	}
}