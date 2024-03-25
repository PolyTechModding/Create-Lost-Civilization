package com.polytechmodding.createlostcivilization.world.level.dimension;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import java.util.OptionalLong;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

public class CivilizationDimensions {

  public static final ResourceKey<DimensionType> MYSTERY_PLANET =
      ResourceKey.create(Registries.DIMENSION_TYPE,
                         new ResourceLocation(CreateLostCivilization.MOD_ID,
                                              "mystery_planet"));

  public static final ResourceKey<DimensionType> MYSTERY_PLANET_CAVES =
      ResourceKey.create(Registries.DIMENSION_TYPE,
                         new ResourceLocation(CreateLostCivilization.MOD_ID,
                                              "mystery_planet_caves"));

  public static final BiMap<ResourceKey<DimensionType>, DimensionType> map = HashBiMap.create();

  /* boolean hasSkyLight,
  boolean hasCeiling,
  boolean ultraWarm,
  boolean natural,
  double coordinateScale,
  boolean bedWorks,
  boolean respawnAnchorWorks,
  int minY,
  int height,
  int logicalHeight,
  TagKey<Block> infiniburn,
  ResourceLocation effectsLocation,
  float ambientLight,
  MonsterSettings monsterSettings */

  // boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest,
  // int monsterSpawnBlockLightLimit
  public static void bootstrapType(BootstapContext<DimensionType> context) {
    var mystery_planet = new DimensionType(OptionalLong.empty(), true, true, false, true, 1.0,
            true, false, -64, 384, 384,
            BlockTags.INFINIBURN_OVERWORLD,
            BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0f,
            new DimensionType.MonsterSettings(
                    false, false, UniformInt.of(0, 7), 0));
    context.register(
        MYSTERY_PLANET, mystery_planet);

    var mystery_planet_caves = new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0,
            true, false, -64, 384, 384,
            BlockTags.INFINIBURN_OVERWORLD,
            BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0f,
            new DimensionType.MonsterSettings(
                    false, false, UniformInt.of(0, 7), 0));

    context.register(
        MYSTERY_PLANET_CAVES, mystery_planet_caves);

    map.put(MYSTERY_PLANET, mystery_planet);
    map.put(MYSTERY_PLANET_CAVES, mystery_planet_caves);
  }
}
