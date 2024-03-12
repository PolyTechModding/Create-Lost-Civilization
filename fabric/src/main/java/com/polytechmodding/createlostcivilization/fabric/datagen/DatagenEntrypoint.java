package com.polytechmodding.createlostcivilization.fabric.datagen;

import static com.polytechmodding.createlostcivilization.CreateLostCivilization.CONTEXT;

import com.arcaneengineering.arcanelib.fabric.datagen.ArcaneDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DatagenEntrypoint implements DataGeneratorEntrypoint {
  @Override
  public void
  onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    ArcaneDataGenerator.onInitializeDataGenerator(fabricDataGenerator, CONTEXT);
  }
}
