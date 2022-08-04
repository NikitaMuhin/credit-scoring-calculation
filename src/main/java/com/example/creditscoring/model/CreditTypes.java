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
public class CreditTypes {

    @Id
    private Long id;
    private String description;

    public CreditTypes(String description) {
        this.description = description;
    }
}
