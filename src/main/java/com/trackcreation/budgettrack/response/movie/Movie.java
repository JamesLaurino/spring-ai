package com.trackcreation.budgettrack.response.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private String id;
    private String title;
    private String genre;
    private String director;
    private String description;
}
