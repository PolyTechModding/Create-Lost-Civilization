package com.polytechmodding.createlostcivilization.fabric;

import com.arcaneengineering.arcanelib.ArcaneLib;
import com.arcaneengineering.arcanelib.fabric.entrypoint.ArcaneEntrypoint;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import com.polytechmodding.createlostcivilization.terrablender.CivilizationRegion;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class CreateLostCivilizationFabric implements ArcaneEntrypoint, TerraBlenderApi {

    @Override
    public void onInitialize(ArcaneLib arcaneLib) {
        CreateLostCivilization.init(arcaneLib);
    }

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new CivilizationRegion(new ResourceLocation(CreateLostCivilization.MOD_ID,
                "mystery_planet"), 2));
    }
}