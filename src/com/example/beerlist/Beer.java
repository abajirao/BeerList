package com.example.beerlist;

public class Beer {
	
	private String imagePath;
	private String beerName;
	private String beerType;
	
	public Beer(String imagePath, String beerName, String beerType) {
		this.setImagePath(imagePath);
		this.setBeerName(beerName);
		this.setBeerType(beerType);
	}

	public Beer() {
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getBeerName() {
		return beerName;
	}

	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}

	public String getBeerType() {
		return beerType;
	}

	public void setBeerType(String beerType) {
		this.beerType = beerType;
	}

}
