package com.polytechmodding.createlostcivilization.families;

import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.arcaneengineering.arcanelib.registry.BlockFamilies;
import com.arcaneengineering.arcanelib.registry.BlockFamily;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.material.MapColor;

public final class BlockFamiliesFactory implements BlockFamilies.BlockFamiliesFactory {
    @Override
    public BlockFamilies create(RegistrationContext registrationContext) {
        BlockFamilies blockFamilies = new BlockFamilies(registrationContext);

        registerKelpWoodType(blockFamilies, registrationContext, "blue", MapColor.COLOR_BLUE);

        registerKelpWoodType(blockFamilies, registrationContext, "red", MapColor.COLOR_RED);

        registerKelpWoodType(blockFamilies, registrationContext, "orange", MapColor.COLOR_ORANGE);

        registerKelpWoodType(blockFamilies, registrationContext, "pink", MapColor.COLOR_PINK);

        registerKelpWoodType(blockFamilies, registrationContext, "yellow", MapColor.COLOR_YELLOW);

        return blockFamilies;
    }

    private static void registerKelpWoodType(BlockFamilies blockFamilies, RegistrationContext registrationContext, String color, MapColor MC){
        blockFamilies.register(blockFamilies
                .getOrganicBuilder(
                        BlockFamilies.getOverworldWoodType(registrationContext, color+"_kelp"),
                        MC, color+"_kelp", BuiltinDimensionTypes.OVERWORLD, true)
                        .addWoodBlock(Variants.LOG)
                        .addWoodBlock(Variants.STRIPPED_LOG)
                        .addWoodBlock(Variants.WOOD)
                        .addWoodBlock(Variants.STRIPPED_WOOD)

                        .addWoodBlock(Variants.BOOKSHELF)

                        .addWoodBlock(Variants.HANGING_SIGN)
                        .addWoodBlock(Variants.SIGN)
                        .addWoodBlock(Variants.WALL_HANGING_SIGN)
                        .addWoodBlock(Variants.WALL_SIGN)

                        .addBlock(Variants.STAIRS)
                        .addBlock(Variants.SLAB)

                        .addItem(Variants.BOAT)
                        .addItem(Variants.CHEST_BOAT)

                        .addWoodBlock(Variants.FENCE)
                        .addWoodBlock(Variants.FENCE_GATE)

                        .addWoodBlock(Variants.DOOR)
                        .addWoodBlock(Variants.TRAPDOOR)

                        .addWoodBlock(Variants.BUTTON)
                        .addWoodBlock(Variants.PRESSURE_PLATE)
                );
    }
}
