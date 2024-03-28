package com.polytechmodding.createlostcivilization.families;

import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.config.types.GrowerVariant;
import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.arcaneengineering.arcanelib.registry.BlockFamilies;
import com.polytechmodding.createlostcivilization.world.level.features.CivilizationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;

public final class BlockFamiliesFactory
    implements BlockFamilies.BlockFamiliesFactory {
  @Override
  public BlockFamilies create(RegistrationContext registrationContext) {
    BlockFamilies blockFamilies = new BlockFamilies(registrationContext);

    blockFamilies.register(blockFamilies
            .getOrganicBuilder(BlockFamilies.getOverworldWoodType(registrationContext, "cypress"),
                    MapColor.TERRACOTTA_GREEN, "cypress", BuiltinDimensionTypes.OVERWORLD, true)
            .addBlock(Variants.BUTTON)
            .addBlock(Variants.DOOR)
            .addBlock(Variants.FENCE)
            .addWoodBlock(Variants.FENCE_GATE)
            .addWoodBlock(Variants.HANGING_SIGN)
            .addWoodBlock(Variants.WALL_HANGING_SIGN)
            .addBlock(Variants.LEAVES)
            .addWoodBlock(Variants.LOG)
            .addBlock(Variants.PRESSURE_PLATE)
            .addWoodBlock(Variants.SIGN)
            .addWoodBlock(Variants.WALL_SIGN)
            .addBlock(Variants.SLAB)
            .addBlock(Variants.STAIRS)
            .addWoodBlock(Variants.STRIPPED_LOG)
            .addWoodBlock(Variants.STRIPPED_WOOD)
            .addBlock(Variants.TRAPDOOR)
            .addWoodBlock(Variants.WOOD)
            .addGrower(Variants.SAPLING, null,
                    () -> CivilizationFeatures.CYPRESS_TREE, null, null)
            .addItem(Variants.BOAT)
            .addItem(Variants.CHEST_BOAT)
    );
    return blockFamilies;
  }
}
