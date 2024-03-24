package com.polytechmodding.createlostcivilization.terrablender;

import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class CivilizationBiomes {

  public static final ResourceKey<Biome> KELP_FOREST = register("kelp_forest");
  public static final ResourceKey<Biome> CORAL_REEF = register("coral_reef");
  public static final ResourceKey<Biome> SWAMP = register("swamp");

  private static ResourceKey<Biome> register(String name) {
    return ResourceKey.create(
        Registries.BIOME,
        new ResourceLocation(CreateLostCivilization.MOD_ID, name));
  }
}
