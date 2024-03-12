package com.polytechmodding.createlostcivilization.world.level.dimension;

import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class CivilizationDimensions {

    public static final ResourceKey<DimensionType> MYSTERY_PLANET =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "mystery_planet_type"));


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

    // boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(MYSTERY_PLANET, new DimensionType(OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0))
        );
    }

}
