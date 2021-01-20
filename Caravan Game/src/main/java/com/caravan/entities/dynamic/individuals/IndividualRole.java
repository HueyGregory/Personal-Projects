package com.caravan.entities.dynamic.individuals;

import com.caravan.Role;

import java.util.ArrayList;
import java.util.List;

public enum IndividualRole implements Role {

    VOLUNTEER, HIRED, ESCORTED, SLAVE, PRISONER;

    private final List<Individual> individuals = new ArrayList<>();

    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public void removeIndividual(Individual individual) {
        individuals.remove(individual);
    }

    public List<Individual> getList() {
        return individuals;
    }

}
