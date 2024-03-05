package com.polytechmodding.createlostcivilization.fabric;

import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import net.fabricmc.api.ModInitializer;

public class CreateLostCivilizationFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateLostCivilization.init();
    }
}