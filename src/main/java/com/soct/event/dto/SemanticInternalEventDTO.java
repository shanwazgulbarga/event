/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SemanticInternalEventDTO {

    @JsonProperty("@context")
    private String context = "https://schema.org";

    @JsonProperty("@type")
    private String type = "ItemList";

    @JsonProperty("itemListElement")
    private List<SemanticInternalEventItem> itemListElement;

    public SemanticInternalEventDTO(){}

    public String getContext(){ return context; }
    public void setContext(String context){ this.context = context; }

    public String getType(){ return type; }
    public void setType(String type){ this.type = type; }

    public List<SemanticInternalEventItem> getItemListElement(){ return itemListElement; }
    public void setItemListElement(List<SemanticInternalEventItem> itemListElement){ this.itemListElement = itemListElement; }

    // ── ListItem wrapper ─────────────────────────────────────────────────────

    public static class SemanticInternalEventItem {

        @JsonProperty("@type")
        private String type = "ListItem";

        @JsonProperty("position")
        private int position;

        @JsonProperty("item")
        private SemanticInternalEvent item;

        public SemanticInternalEventItem(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public int getPosition(){ return position; }
        public void setPosition(int position){ this.position = position; }

        public SemanticInternalEvent getItem(){ return item; }
        public void setItem(SemanticInternalEvent item){ this.item = item; }
    }

    // ── Event ────────────────────────────────────────────────────────────────

    public static class SemanticInternalEvent {

        @JsonProperty("@type")
        private String type = "Event";

        @JsonProperty("name")
        private String name;

        @JsonProperty("startDate")
        private String startDate;

        @JsonProperty("eventStatus")
        private String eventStatus;

        @JsonProperty("maximumAttendeeCapacity")
        private int maximumAttendeeCapacity;

        @JsonProperty("remainingAttendeeCapacity")
        private int remainingAttendeeCapacity;

        @JsonProperty("offers")
        private SemanticOffer offers;

        @JsonProperty("organizer")
        private SemanticOrganizer organizer;

        @JsonProperty("aggregateRating")
        private SemanticAggregateRating aggregateRating;

        @JsonProperty("image")
        private String image;

        @JsonProperty("location")
        private SemanticEnrichedLocation location;

        public SemanticInternalEvent(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }

        public String getStartDate(){ return startDate; }
        public void setStartDate(String startDate){ this.startDate = startDate; }

        public String getEventStatus(){ return eventStatus; }
        public void setEventStatus(String eventStatus){ this.eventStatus = eventStatus; }

        public int getMaximumAttendeeCapacity(){ return maximumAttendeeCapacity; }
        public void setMaximumAttendeeCapacity(int maximumAttendeeCapacity){ this.maximumAttendeeCapacity = maximumAttendeeCapacity; }

        public int getRemainingAttendeeCapacity(){ return remainingAttendeeCapacity; }
        public void setRemainingAttendeeCapacity(int remainingAttendeeCapacity){ this.remainingAttendeeCapacity = remainingAttendeeCapacity; }

        public SemanticOffer getOffers(){ return offers; }
        public void setOffers(SemanticOffer offers){ this.offers = offers; }

        public SemanticOrganizer getOrganizer(){ return organizer; }
        public void setOrganizer(SemanticOrganizer organizer){ this.organizer = organizer; }

        public SemanticAggregateRating getAggregateRating(){ return aggregateRating; }
        public void setAggregateRating(SemanticAggregateRating aggregateRating){ this.aggregateRating = aggregateRating; }

        public String getImage(){ return image; }
        public void setImage(String image){ this.image = image; }

        public SemanticEnrichedLocation getLocation(){ return location; }
        public void setLocation(SemanticEnrichedLocation location){ this.location = location; }
    }

    // ── Offer (cost) ─────────────────────────────────────────────────────────

    public static class SemanticOffer {

        @JsonProperty("@type")
        private String type = "Offer";

        @JsonProperty("price")
        private int price;

        @JsonProperty("priceCurrency")
        private String priceCurrency = "GBP";

        @JsonProperty("availability")
        private String availability;

        public SemanticOffer(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public int getPrice(){ return price; }
        public void setPrice(int price){ this.price = price; }

        public String getPriceCurrency(){ return priceCurrency; }
        public void setPriceCurrency(String priceCurrency){ this.priceCurrency = priceCurrency; }

        public String getAvailability(){ return availability; }
        public void setAvailability(String availability){ this.availability = availability; }
    }

    // ── Organizer (publisher) ─────────────────────────────────────────────────

    public static class SemanticOrganizer {

        @JsonProperty("@type")
        private String type = "Person";

        @JsonProperty("identifier")
        private String identifier;

        public SemanticOrganizer(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public String getIdentifier(){ return identifier; }
        public void setIdentifier(String identifier){ this.identifier = identifier; }
    }

    // ── AggregateRating ───────────────────────────────────────────────────────

    public static class SemanticAggregateRating {

        @JsonProperty("@type")
        private String type = "AggregateRating";

        @JsonProperty("ratingValue")
        private double ratingValue;

        @JsonProperty("bestRating")
        private int bestRating = 5;

        @JsonProperty("worstRating")
        private int worstRating = 1;

        public SemanticAggregateRating(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public double getRatingValue(){ return ratingValue; }
        public void setRatingValue(double ratingValue){ this.ratingValue = ratingValue; }

        public int getBestRating(){ return bestRating; }
        public void setBestRating(int bestRating){ this.bestRating = bestRating; }

        public int getWorstRating(){ return worstRating; }
        public void setWorstRating(int worstRating){ this.worstRating = worstRating; }
    }

    // ── Enriched Location (with Wikidata) ─────────────────────────────────────

    public static class SemanticEnrichedLocation {

        @JsonProperty("@type")
        private String type = "Place";

        @JsonProperty("name")
        private String name;

        @JsonProperty("@id")
        private String wikidataUri;

        @JsonProperty("geo")
        private SemanticGeoCoordinates geo;

        @JsonProperty("containedInPlace")
        private SemanticCountry containedInPlace;

        public SemanticEnrichedLocation(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }

        public String getWikidataUri(){ return wikidataUri; }
        public void setWikidataUri(String wikidataUri){ this.wikidataUri = wikidataUri; }

        public SemanticGeoCoordinates getGeo(){ return geo; }
        public void setGeo(SemanticGeoCoordinates geo){ this.geo = geo; }

        public SemanticCountry getContainedInPlace(){ return containedInPlace; }
        public void setContainedInPlace(SemanticCountry containedInPlace){ this.containedInPlace = containedInPlace; }
    }

    // ── GeoCoordinates ────────────────────────────────────────────────────────

    public static class SemanticGeoCoordinates {

        @JsonProperty("@type")
        private String type = "GeoCoordinates";

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

        public SemanticGeoCoordinates(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public double getLatitude(){ return latitude; }
        public void setLatitude(double latitude){ this.latitude = latitude; }

        public double getLongitude(){ return longitude; }
        public void setLongitude(double longitude){ this.longitude = longitude; }
    }

    // ── Country ───────────────────────────────────────────────────────────────

    public static class SemanticCountry {

        @JsonProperty("@type")
        private String type = "Country";

        @JsonProperty("name")
        private String name;

        @JsonProperty("@id")
        private String wikidataUri;

        public SemanticCountry(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }

        public String getWikidataUri(){ return wikidataUri; }
        public void setWikidataUri(String wikidataUri){ this.wikidataUri = wikidataUri; }
    }
}