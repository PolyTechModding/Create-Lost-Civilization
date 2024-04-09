package com.polytechmodding.createlostcivilization.fabric.datagen;

import static com.polytechmodding.createlostcivilization.CreateLostCivilization.CONTEXT;

import com.arcaneengineering.arcanelib.fabric.datagen.ArcaneDataGenerator;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import com.polytechmodding.createlostcivilization.client.CivilizationClientVariants;
import com.polytechmodding.createlostcivilization.world.level.dimension.CivilizationDimensions;
import com.polytechmodding.createlostcivilization.world.level.features.CivilizationFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DatagenEntrypoint implements DataGeneratorEntrypoint {
  @Override
  public void
  onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    CivilizationClientVariants.loadClass();
    ArcaneDataGenerator.onInitializeDataGenerator(fabricDataGenerator, CONTEXT);
    fabricDataGenerator.createPack().addProvider(
        CivilizationWorldGenerator::new);
  }

  @Override
  public void buildRegistry(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.DIMENSION_TYPE,
                        CivilizationDimensions::bootstrapType);
    registryBuilder.add(Registries.CONFIGURED_FEATURE,
                        CivilizationFeatures::bootstrapType);
  }
}
