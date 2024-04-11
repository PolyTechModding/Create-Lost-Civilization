package com.polytechmodding.createlostcivilization.world;

import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.polytechmodding.createlostcivilization.world.blocks.NoBucketLiquidBlock;
import com.polytechmodding.createlostcivilization.world.fluids.FluidRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class CivilizationRegistry {

    public static Supplier<? extends Block> TOXIC_BARRIER_FLUID;

    public static void loadClass(RegistrationContext context) {
        TOXIC_BARRIER_FLUID = context.createBlock(() -> new NoBucketLiquidBlock(
                (FlowingFluid) FluidRegistry.STILL_TOXIC_BARRIER_FLUID.get(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)),
                "toxic_barrier_fluid");
    }
}
