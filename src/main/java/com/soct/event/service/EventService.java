package com.soct.event.service;

import com.soct.event.dto.EventWeatherDTO;
import com.soct.event.dto.ExternalEventDTO;
import com.soct.event.dto.SemanticEventDTO;
import com.soct.event.dto.SemanticEventDTO.SemanticEvent;
import com.soct.event.dto.SemanticEventDTO.SemanticEventItem;
import com.soct.event.dto.SemanticEventDTO.SemanticLocation;
import com.soct.event.dto.SemanticInternalEventDTO;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticInternalEventItem;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticInternalEvent;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticOffer;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticOrganizer;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticAggregateRating;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticEnrichedLocation;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticGeoCoordinates;
import com.soct.event.dto.SemanticInternalEventDTO.SemanticCountry;
import com.soct.event.dto.WikidataLocationDTO;
import java.util.HashMap;
import java.util.Map;
import com.soct.event.model.ActivityLog;
import com.soct.event.model.Event;
import com.soct.event.repository.ActivityLogRepository;
import com.soct.event.repository.EventRepository;
import com.soct.event.dto.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soct.event.dto.EventDTO;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private WeatherService weatherService;
   
    @Autowired
private PixabayService pixabayService;

@Autowired
private ReviewService reviewService;
    
   @Autowired
    private SentimentService sentimentService;



    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private SkiddleService skiddleService;

    @Autowired
    private WikidataService wikidataService;

    public Event createEvent(Event event){

        event.setRegisteredParticipants(0);

        Event savedEvent = eventRepository.save(event);

        // Create activity log
        ActivityLog log = new ActivityLog();
        log.setStudentId(event.getPublisherId());
        log.setAction("Created event: " + event.getTitle());
        log.setTimestamp(LocalDateTime.now().toString());

        activityLogRepository.save(log);

        return savedEvent;
    }

    
     private double convertSentimentToRating(String sentiment){

        switch (sentiment){
            case "POSITIVE": return 5.0;
            case "NEUTRAL": return 3.0;
            case "NEGATIVE": return 1.0;
            default: return 3.0;
        }
    }
    
    private double getSentimentScore(String text){

    String sentiment = sentimentService.analyzeSentiment(text);

    return convertSentimentToRating(sentiment);
}
    
    
    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }
    public List<Event> searchByType(String type){
        return eventRepository.findByType(type);
    }
    
    public List<Event> searchByLocation(String location){
    return eventRepository.findByLocation(location);
}

public List<Event> searchByDate(String date){
    return eventRepository.findByDate(date);
}

public List<Event> searchByTypeAndLocation(String type, String location){
    return eventRepository.findByTypeAndLocation(type, location);
}
    
    public List<Event> getEventsByPublisher(String publisherId){

    return eventRepository.findByPublisherId(publisherId);
}
  public List<EventWeatherDTO> getAllEventsWithWeather(){

    List<Event> events = eventRepository.findAll();

    List<EventWeatherDTO> result = new ArrayList<>();

    for(Event event : events){

        EventWeatherDTO dto = new EventWeatherDTO();

        dto.setTitle(event.getTitle());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate());

        String weather = weatherService.getWeather(event.getLocation());

        dto.setWeather(weather);

        result.add(dto);
    }

    return result;
}

  
public List<EventDTO> getAllEventsFull(){

    List<Event> events = eventRepository.findAll();
    List<EventDTO> result = new ArrayList<>();

    for(Event event : events){

        EventDTO dto = new EventDTO();

        // 🔹 Basic data
        dto.setId(event.getId());
        dto.setPublisherId(event.getPublisherId());
        dto.setTitle(event.getTitle());
        dto.setType(event.getType());
        dto.setDate(event.getDate());
        dto.setLocation(event.getLocation());
        dto.setCost(event.getCost());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setRegisteredParticipants(event.getRegisteredParticipants());

        // Build text for sentiment
        String text = event.getTitle() + " " + event.getLocation();

        // Sentiment score
        double sentimentScore = getSentimentScore(text);
        dto.setSentimentRating(sentimentScore);

        //  Average rating
        double avgRating = reviewService.getAverageRating(event.getId());
        dto.setAverageRating(avgRating);

        // Final smart score
        double finalScore = (avgRating + sentimentScore) / 2;
        dto.setFinalScore(finalScore);

        //  Media (Pixabay)
        dto.setImages(
            pixabayService.getImages(text).stream().limit(2).toList()
        );

        //  Weather
        dto.setWeather(
            weatherService.getWeather(event.getLocation())
        );

        result.add(dto);
    }

    // Sort by best events
    result.sort((a, b) ->
        Double.compare(b.getFinalScore(), a.getFinalScore())
    );

    return result;
}

public List<ExternalEventDTO> getExternalEventsByDatabaseLocations(){

    List<Event> events = eventRepository.findAll();

    List<String> locations = new ArrayList<>();

    for(Event event : events){
        String location = event.getLocation();
        if(location != null && !location.isEmpty() && !locations.contains(location)){
            locations.add(location);
        }
    }

    List<ExternalEventDTO> result = new ArrayList<>();

    for(String location : locations){
        try {
            List<ExternalEventDTO> skiddleEvents = skiddleService.getEvents(location);
            result.addAll(skiddleEvents);
        } catch (Exception e){
            // skip this location if Skiddle fails, continue with others
        }
    }

    return result;
}

public SemanticEventDTO getSemanticExternalEvents(){

    List<ExternalEventDTO> events = getExternalEventsByDatabaseLocations();

    List<SemanticEventItem> items = new ArrayList<>();

    int position = 1;

    for(ExternalEventDTO event : events){

        SemanticLocation semanticLocation = new SemanticLocation();
        semanticLocation.setName(event.getVenue());

        SemanticEvent semanticEvent = new SemanticEvent();
        semanticEvent.setName(event.getTitle());
        semanticEvent.setStartDate(event.getDate());
        semanticEvent.setUrl(event.getLink());
        semanticEvent.setLocation(semanticLocation);

        SemanticEventItem item = new SemanticEventItem();
        item.setPosition(position);
        item.setItem(semanticEvent);

        items.add(item);
        position++;
    }

    SemanticEventDTO result = new SemanticEventDTO();
    result.setItemListElement(items);

    return result;
}

public SemanticInternalEventDTO getSemanticInternalEvents(){

    List<Event> events = eventRepository.findAll();

    // Cache Wikidata lookups — one call per unique city not per event
    Map<String, WikidataLocationDTO> locationCache = new HashMap<>();

    List<SemanticInternalEventItem> items = new ArrayList<>();

    int position = 1;

    for(Event event : events){

        // ── Wikidata enrichment (cached) ──────────────────────────────────
        String cityName = event.getLocation();

        if(!locationCache.containsKey(cityName)){
            locationCache.put(cityName, wikidataService.enrichLocation(cityName));
        }

        WikidataLocationDTO wikidata = locationCache.get(cityName);

        // ── Location ──────────────────────────────────────────────────────
        SemanticGeoCoordinates geo = new SemanticGeoCoordinates();
        geo.setLatitude(wikidata.getLatitude());
        geo.setLongitude(wikidata.getLongitude());

        SemanticCountry country = new SemanticCountry();
        country.setName(wikidata.getCountryName());
        country.setWikidataUri(wikidata.getCountryWikidataUri());

        SemanticEnrichedLocation location = new SemanticEnrichedLocation();
        location.setName(cityName);
        location.setWikidataUri(wikidata.getWikidataUri());
        location.setGeo(geo);
        location.setContainedInPlace(country);

        // ── Offer (cost) ──────────────────────────────────────────────────
        int remaining = event.getMaxParticipants() - event.getRegisteredParticipants();

        SemanticOffer offer = new SemanticOffer();
        offer.setPrice(event.getCost());
        offer.setAvailability(
            remaining > 0
                ? "https://schema.org/InStock"
                : "https://schema.org/SoldOut"
        );

        // ── Organizer ─────────────────────────────────────────────────────
        SemanticOrganizer organizer = new SemanticOrganizer();
        organizer.setIdentifier(event.getPublisherId());

        // ── Aggregate rating ──────────────────────────────────────────────
        double avgRating = reviewService.getAverageRating(event.getId());

        SemanticAggregateRating aggregateRating = new SemanticAggregateRating();
        aggregateRating.setRatingValue(avgRating);

        // ── Image (first Pixabay result) ──────────────────────────────────
        String imageUrl = "";
        try {
            List<com.soct.event.dto.ImageDTO> images = pixabayService.getImages(event.getTitle());
            if(!images.isEmpty()){
                imageUrl = images.get(0).getImageUrl();
            }
        } catch (Exception e){
            // leave empty if Pixabay fails
        }

        // ── Event status ──────────────────────────────────────────────────
        String eventStatus = remaining > 0
                ? "https://schema.org/EventScheduled"
                : "https://schema.org/EventSoldOut";

        // ── Build semantic event ──────────────────────────────────────────
        SemanticInternalEvent semanticEvent = new SemanticInternalEvent();
        semanticEvent.setName(event.getTitle());
        semanticEvent.setStartDate(event.getDate());
        semanticEvent.setEventStatus(eventStatus);
        semanticEvent.setMaximumAttendeeCapacity(event.getMaxParticipants());
        semanticEvent.setRemainingAttendeeCapacity(remaining);
        semanticEvent.setOffers(offer);
        semanticEvent.setOrganizer(organizer);
        semanticEvent.setAggregateRating(aggregateRating);
        semanticEvent.setImage(imageUrl);
        semanticEvent.setLocation(location);

        SemanticInternalEventItem item = new SemanticInternalEventItem();
        item.setPosition(position);
        item.setItem(semanticEvent);

        items.add(item);
        position++;
    }

    SemanticInternalEventDTO result = new SemanticInternalEventDTO();
    result.setItemListElement(items);

    return result;
}

    
  
}