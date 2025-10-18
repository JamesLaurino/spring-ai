package com.trackcreation.budgettrack.response.facture;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Facture {
    private String id;
    private LocalDateTime date;
    private Double total;
    private String category;
    private String description;
}
