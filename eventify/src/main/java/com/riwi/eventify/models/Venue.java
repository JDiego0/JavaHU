package com.riwi.eventify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"events"})
@ToString(exclude = {"events"})
@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 200)
    private String address;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(nullable = false, length = 100)
    private String city;
    
    @OneToMany(mappedBy = "venue")
    @JsonIgnore
    private List<Event> events = new ArrayList<>();
    
    public Venue(String name, String address, Integer capacity, String city) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.city = city;
    }
    
    public Venue(Long id, String name, String address, Integer capacity, String city) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.city = city;
    }
}
