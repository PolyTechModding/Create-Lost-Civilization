package com.polytechmodding.createlostcivilization.entities;

public interface AirEntity {
    default boolean createLostCivilization$isInAir() {
        return false;
    }
    default boolean createLostCivilization$isUnderAir() {
        return false;
    }
}
