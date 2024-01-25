package com.javatechie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String event_id;
    private String event_timestamp;
    private String user_id;
    private String url;
    private String url_domain;
    private String body;
    private String category;
    private String tenant;
    private Set<String> categories;
}
