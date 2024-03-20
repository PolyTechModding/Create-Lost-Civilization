package com.polytechmodding.createlostcivilization.fabric.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public class CivilizationWorldGenerator extends FabricDynamicRegistryProvider {
  public CivilizationWorldGenerator(
      FabricDataOutput output,
      CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(HolderLookup.Provider registries, Entries entries) {
    entries.addAll(registries.lookupOrThrow(Registries.DIMENSION_TYPE));
  }

  @Override
  public @NotNull String getName() {
    return "World Generation";
  }
}
