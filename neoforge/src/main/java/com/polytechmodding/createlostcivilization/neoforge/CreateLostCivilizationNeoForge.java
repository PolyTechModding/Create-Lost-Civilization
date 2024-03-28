package com.polytechmodding.createlostcivilization.neoforge;

import com.arcaneengineering.arcanelib.ArcaneLib;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import net.neoforged.fml.common.Mod;

@Mod(CreateLostCivilization.MOD_ID)
public class CreateLostCivilizationNeoForge {
    public CreateLostCivilizationNeoForge() {
		// Submit our event bus to let architectury register our content on the right time
        // EventBuses.registerModEventBus(CreateLostCivilization.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CreateLostCivilization.init(ArcaneLib.getInstance());
    }
}