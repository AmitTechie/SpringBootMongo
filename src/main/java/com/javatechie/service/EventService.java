package com.javatechie.service;

import com.javatechie.model.Event;
import com.javatechie.model.EventQueryFilter;
import com.javatechie.repository.EventRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventService {

    static final String key = "eRnOV1BapnsgtXVZQGUY";
    static final String secret = "IZbRyS3Gw84SXju1AFQm";

    @Autowired
    private EventRepository eventRepository;

    // Create, Read, Update, Delete


    public Event addEvent(Event event){
        event.setEvent_id(UUID.randomUUID().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setEvent_timestamp(simpleDateFormat.format(System.currentTimeMillis()));
        System.out.println(event.getEvent_timestamp());

        //get the category..
        Set<String> categories = getUrlCategories(event.getUrl());
        if(categories != null || !categories.isEmpty()){
            event.setCategories(categories);
        }

        return eventRepository.save(event);
    }

    public List<Event> findAllEvent(){
        List<Event> all = eventRepository.findAll();
        System.out.println("FOUND:::::: "+all.size());
        return all;
    }

    public List<Event> getEventsByFilter(EventQueryFilter eventQueryFilter){
        List<Event> res = new ArrayList<>();
        List<Event> events = eventRepository.findByFilter(eventQueryFilter.getTenant());
        System.out.println(eventQueryFilter.toString());
        if(events != null) {

            for (Event event : events) {
                // filter by user_id
                if(!eventQueryFilter.getUser_id().isEmpty() && !event.getUser_id().equals(eventQueryFilter.getUser_id())){
                    System.out.println("user_id didn't matched!"+event.getUser_id()+" - "+eventQueryFilter.getUser_id());
                    continue;
                }

                // filter by category
                if(!eventQueryFilter.getCategory().isEmpty() && !event.getCategory().equals(eventQueryFilter.getCategory())){
                    System.out.println("category didn't matched!"+event.getCategory()+" - "+eventQueryFilter.getCategory());
                    continue;
                }

                // filter by domain
                if(!eventQueryFilter.getUrl_domain().isEmpty() && !event.getUrl_domain().equals(eventQueryFilter.getUrl_domain())){
                    System.out.println("domain didn't matched!"+event.getUrl_domain()+" - "+eventQueryFilter.getUrl_domain());
                    continue;
                }

                // filter by date
                if(!eventQueryFilter.getFrom_date().isEmpty() && !eventQueryFilter.getTo_date().isEmpty()){
                    System.out.println("date filter entered!!");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date start_date = simpleDateFormat.parse(eventQueryFilter.getFrom_date());
                        Date end_date = simpleDateFormat.parse(eventQueryFilter.getTo_date());

                        Date event_date = simpleDateFormat.parse(event.getEvent_timestamp());

                        if(event_date.before(start_date) || event_date.after(end_date)){
                            System.out.println("date didn't matched!");
                            continue;
                        }
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                //filter by category
                if(!eventQueryFilter.getCategory().isEmpty()){
                    if(event.getCategories() != null && !event.getCategories().contains(eventQueryFilter.getCategory())){
                        continue;
                    }
                }
                res.add(event);
            }
        }
        return res;
    }

    public  String base64UrlDecode(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8)));
    }
    String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public String getUrlData(String url_path){

        String jsonText = "";
        String urlPath = "https://api.webshrinker.com/categories/v3/"+base64UrlDecode(url_path);
        try {
            URL url = new URI(urlPath).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String auth = Base64.getEncoder().encodeToString(((key + ':' + secret).getBytes()));
            conn.setRequestProperty("Authorization", "Basic " + auth);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            System.out.println(conn.getResponseCode());
            //Getting the response code
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                //throw new RuntimeException("HttpResponseCode: " + responseCode);
                return "";
            } else {

                InputStream is = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                jsonText = readAll(rd);
                is.close();
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return  jsonText;
    }

    public Set<String> getUrlCategories(String url_path){

        String jsonText = getUrlData(url_path);

        System.out.println(jsonText);
        if (jsonText.isEmpty()){
            return  null;
        }
        Set<String> categories = new HashSet<>();
        JSONObject jsonObject = new JSONObject(jsonText);
        System.out.println(jsonObject.toString());

        JSONArray arr = (JSONArray) jsonObject.get("data");
        JSONObject catData = (JSONObject) arr.get(0);
        JSONArray categoriesList = (JSONArray) catData.get("categories");
        System.out.println("Total categories: " + categoriesList.length());
        for (int i = 0; i < categoriesList.length(); i++) {

            JSONObject categoryObj = (JSONObject) categoriesList.get(i);

            String category = categoryObj.get("label").toString();

            System.out.println(category);
            if(!category.isEmpty()){
                categories.add(category);
            }
        }


        return categories;
        }
}
