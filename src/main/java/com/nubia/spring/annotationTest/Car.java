package com.nubia.spring.annotationTest;

public class Car {
	private String brand;
	private double price;

	// 省略 get/setter

	@Override
	public String toString() {
		return "brand:" + brand + "," + "price:" + price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
