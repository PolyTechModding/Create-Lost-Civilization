package com.polytechmodding.createlostcivilization.world.fluids;

import com.polytechmodding.createlostcivilization.world.CivilizationRegistry;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public abstract class ToxicBarrierFluid extends BarrierFluid {

    @Override
    public @NotNull Item getBucket() {
        return null;
    }

    @Override
    public @NotNull Fluid getSource() {
        return FluidRegistry.STILL_TOXIC_BARRIER_FLUID.get();
    }

    @Override
    public @NotNull Fluid getFlowing() {
        return FluidRegistry.FLOWING_TOXIC_BARRIER_FLUID.get();
    }

    @Override
    protected @NotNull BlockState createLegacyBlock(FluidState state) {
        return CivilizationRegistry.TOXIC_BARRIER_FLUID.get()
                .defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(state));
    }

    public static class Flowing extends ToxicBarrierFluid {

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Still extends ToxicBarrierFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
