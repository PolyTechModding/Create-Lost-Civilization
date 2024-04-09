package com.polytechmodding.createlostcivilization.families;

import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.arcaneengineering.arcanelib.registry.BlockFamilies;
import com.arcaneengineering.arcanelib.registry.BlockFamily;
import com.polytechmodding.createlostcivilization.world.level.features.CivilizationFeatures;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.material.MapColor;

public final class BlockFamiliesFactory implements BlockFamilies.BlockFamiliesFactory {

    public static BlockFamily BLUE_KELP_FAMILY, RED_KELP_FAMILY, ORANGE_KELP_FAMILY, PINK_KELP_FAMILY, YELLOW_KELP_FAMILY;

    @Override
    public BlockFamilies create(RegistrationContext registrationContext) {
        BlockFamilies blockFamilies = new BlockFamilies(registrationContext);

        BLUE_KELP_FAMILY = registerKelpWoodType(blockFamilies, registrationContext, "blue", MapColor.COLOR_BLUE);

        RED_KELP_FAMILY = registerKelpWoodType(blockFamilies, registrationContext, "red", MapColor.COLOR_RED);

        ORANGE_KELP_FAMILY = registerKelpWoodType(blockFamilies, registrationContext, "orange", MapColor.COLOR_ORANGE);

        PINK_KELP_FAMILY = registerKelpWoodType(blockFamilies, registrationContext, "pink", MapColor.COLOR_PINK);

        YELLOW_KELP_FAMILY = registerKelpWoodType(blockFamilies, registrationContext, "yellow", MapColor.COLOR_YELLOW);

        return blockFamilies;
    }

    private static BlockFamily registerKelpWoodType(BlockFamilies blockFamilies, RegistrationContext registrationContext, String color, MapColor MC){
        return blockFamilies.register(blockFamilies
                .getOrganicBuilder(
                        BlockFamilies.getOverworldWoodType(registrationContext, color+"_kelp"),
                        MC, color+"_kelp", BuiltinDimensionTypes.OVERWORLD, true)
                        .addWoodBlock(Variants.LOG)
                        .addWoodBlock(Variants.STRIPPED_LOG)
                        .addWoodBlock(Variants.WOOD)
                        .addWoodBlock(Variants.STRIPPED_WOOD)

                        .addWoodBlock(Variants.BOOKSHELF)

                        .addWoodBlock(Variants.WALL_HANGING_SIGN)
                        .addWoodBlock(Variants.WALL_SIGN)
                        .addWoodBlock(Variants.HANGING_SIGN)
                        .addWoodBlock(Variants.SIGN)

                        .addBlock(Variants.STAIRS)
                        .addBlock(Variants.SLAB)

                        .addItem(Variants.BOAT)
                        .addItem(Variants.CHEST_BOAT)

                        .addBlock(Variants.FENCE)
                        .addWoodBlock(Variants.FENCE_GATE)

                        .addBlock(Variants.DOOR)
                        .addBlock(Variants.TRAPDOOR)

                        .addBlock(Variants.BUTTON)
                        .addBlock(Variants.PRESSURE_PLATE)
                        .addGrower(Variants.SAPLING, null, () -> CivilizationFeatures.KELP_TREE, null, null)
                );
    }
}
