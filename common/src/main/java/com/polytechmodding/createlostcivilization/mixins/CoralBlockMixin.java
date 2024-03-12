package com.polytechmodding.createlostcivilization.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlock.class)
public class CoralBlockMixin {

    @Inject(method = "updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
    at = @At("HEAD"))
    private void redirectUpdateShape(BlockState blockState, Direction direction,
                                     BlockState blockState2, LevelAccessor levelAccessor,
                                     BlockPos blockPos, BlockPos blockPos2, CallbackInfoReturnable<BlockState> cir) {
    }

    @Unique
    protected boolean createLostCivilization$scanForAir(BlockGetter blockGetter, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            BlockState blockState = blockGetter.getBlockState(blockPos.relative(direction));
            if (!blockState.is(Blocks.AIR)) continue;
            return true;
        }
        return false;
    }
}
