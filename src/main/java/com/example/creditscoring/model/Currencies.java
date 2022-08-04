package com.example.creditscoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currencies  {

    @Id
    private Long id;
    private String name;

    public Currencies(String name) {
        this.name = name;
    }
}
