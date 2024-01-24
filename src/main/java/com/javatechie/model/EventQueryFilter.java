package com.javatechie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventQueryFilter {
    private String tenant;
    private String user_id;
    private String url_domain;
    private String from_date;
    private String to_date;
    private String category;
}
