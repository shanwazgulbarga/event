/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soct.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SemanticEventDTO {

    @JsonProperty("@context")
    private String context = "https://schema.org";

    @JsonProperty("@type")
    private String type = "ItemList";

    @JsonProperty("itemListElement")
    private List<SemanticEventItem> itemListElement;

    public SemanticEventDTO(){}

    public String getContext(){ return context; }
    public void setContext(String context){ this.context = context; }

    public String getType(){ return type; }
    public void setType(String type){ this.type = type; }

    public List<SemanticEventItem> getItemListElement(){ return itemListElement; }
    public void setItemListElement(List<SemanticEventItem> itemListElement){ this.itemListElement = itemListElement; }

    // ── Inner class: each item in the list ──────────────────────────────────

    public static class SemanticEventItem {

        @JsonProperty("@type")
        private String type = "ListItem";

        @JsonProperty("position")
        private int position;

        @JsonProperty("item")
        private SemanticEvent item;

        public SemanticEventItem(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public int getPosition(){ return position; }
        public void setPosition(int position){ this.position = position; }

        public SemanticEvent getItem(){ return item; }
        public void setItem(SemanticEvent item){ this.item = item; }
    }

    // ── Inner class: the Event entity in schema.org vocabulary ──────────────

    public static class SemanticEvent {

        @JsonProperty("@type")
        private String type = "Event";

        @JsonProperty("name")
        private String name;

        @JsonProperty("startDate")
        private String startDate;

        @JsonProperty("url")
        private String url;

        @JsonProperty("location")
        private SemanticLocation location;

        public SemanticEvent(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }

        public String getStartDate(){ return startDate; }
        public void setStartDate(String startDate){ this.startDate = startDate; }

        public String getUrl(){ return url; }
        public void setUrl(String url){ this.url = url; }

        public SemanticLocation getLocation(){ return location; }
        public void setLocation(SemanticLocation location){ this.location = location; }
    }

    // ── Inner class: the Place entity in schema.org vocabulary ──────────────

    public static class SemanticLocation {

        @JsonProperty("@type")
        private String type = "Place";

        @JsonProperty("name")
        private String name;

        public SemanticLocation(){}

        public String getType(){ return type; }
        public void setType(String type){ this.type = type; }

        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }
    }
}
