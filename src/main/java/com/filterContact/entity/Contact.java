package com.filterContact.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity of Contact which mapping on "CONTACT" table to database
 */
@Data
@Entity
@Table(name = "CONTACT")
@NoArgsConstructor
public class Contact {

    /** Identifiable */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** Name of Contact */
    @NotNull(message = "Name is required!")
    private String name;

    public Contact(String name) {
        this.name = name;
    }
}
