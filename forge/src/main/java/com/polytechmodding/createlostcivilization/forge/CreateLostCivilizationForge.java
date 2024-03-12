package com.polytechmodding.createlostcivilization.forge;

import com.arcaneengineering.arcanelib.ArcaneLib;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateLostCivilization.MOD_ID)
public class CreateLostCivilizationForge {
  public CreateLostCivilizationForge() {
    // Submit our event bus to let architectury register our content on the
    // right time
    EventBuses.registerModEventBus(
        CreateLostCivilization.MOD_ID,
        FMLJavaModLoadingContext.get().getModEventBus());
    CreateLostCivilization.init(ArcaneLib.getInstance());
  }
}
