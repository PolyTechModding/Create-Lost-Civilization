package com.polytechmodding.createlostcivilization.mixins;

import com.polytechmodding.createlostcivilization.world.level.dimension.CivilizationDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCoralPlantTypeBlock.class)
public abstract class BaseCoralPlantTypeBlockMixin extends Block
        implements SimpleWaterloggedBlock {
    @Shadow @Final public static BooleanProperty WATERLOGGED;

    public BaseCoralPlantTypeBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "scanForWater(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
    at = @At("HEAD"), cancellable = true)
    private static void scanForWaterInject(BlockState blockState, BlockGetter blockGetter,
                                       BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        Level levelAccessor = (Level) blockGetter;
        if(!levelAccessor.dimensionTypeId()
                .equals(CivilizationDimensions.MYSTERY_PLANET)) {
            if (blockState.getValue(WATERLOGGED)) {
                cir.setReturnValue(true);
            } else {
                for (Direction direction : Direction.values()) {
                    if (blockGetter.getFluidState(blockPos.relative(direction)).is(FluidTags.WATER)) {
                        cir.setReturnValue(true);
                    }
                }
                cir.setReturnValue(false);
            }
        } else {
            cir.setReturnValue(createLostCivilization$scanForAir(blockState, blockGetter, blockPos));
        }

    }

    @Unique
    private static boolean createLostCivilization$scanForAir(BlockState arg, BlockGetter arg2, BlockPos arg3) {
        if (arg.getValue(WATERLOGGED)) {
            return false;
        }
        for (Direction direction : Direction.values()) {
            if (!arg2.getBlockState(arg3.relative(direction)).is(Blocks.AIR)) continue;
            return true;
        }
        return false;
    }
}
