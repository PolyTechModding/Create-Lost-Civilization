package com.polytechmodding.createlostcivilization.fabric;

import com.arcaneengineering.arcanelib.ArcaneLib;
import com.arcaneengineering.arcanelib.fabric.entrypoint.ArcaneEntrypoint;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;

public class CreateLostCivilizationFabric implements ArcaneEntrypoint {

    @Override
    public void onInitialize(ArcaneLib arcaneLib) {
        CreateLostCivilization.init(arcaneLib);
    }
}