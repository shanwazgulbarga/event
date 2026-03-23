/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.dto;

public class WikidataLocationDTO {

    private String cityName;
    private String wikidataUri;
    private double latitude;
    private double longitude;
    private String countryName;
    private String countryWikidataUri;

    public WikidataLocationDTO(){}

    public String getCityName(){ return cityName; }
    public void setCityName(String cityName){ this.cityName = cityName; }

    public String getWikidataUri(){ return wikidataUri; }
    public void setWikidataUri(String wikidataUri){ this.wikidataUri = wikidataUri; }

    public double getLatitude(){ return latitude; }
    public void setLatitude(double latitude){ this.latitude = latitude; }

    public double getLongitude(){ return longitude; }
    public void setLongitude(double longitude){ this.longitude = longitude; }

    public String getCountryName(){ return countryName; }
    public void setCountryName(String countryName){ this.countryName = countryName; }

    public String getCountryWikidataUri(){ return countryWikidataUri; }
    public void setCountryWikidataUri(String countryWikidataUri){ this.countryWikidataUri = countryWikidataUri; }
}
