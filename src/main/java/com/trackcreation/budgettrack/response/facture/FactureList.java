package com.trackcreation.budgettrack.response.facture;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class FactureList {
    private List<Facture> factures;
}
