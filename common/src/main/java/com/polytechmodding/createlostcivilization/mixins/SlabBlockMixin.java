package com.polytechmodding.createlostcivilization.mixins;

import com.arcaneengineering.arcanelib.config.Variants;
import com.llamalad7.mixinextras.sugar.Local;
import com.polytechmodding.createlostcivilization.world.fluids.FluidRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends Block implements SimpleWaterloggedBlock {

    public SlabBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
    at = @At("HEAD"), cancellable = true)
    public void updateShapeMixin(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (state.getValue(SlabBlock.WATERLOGGED)) {
            level.scheduleTick(pos, level.getFluidState(pos).getType(), level.getFluidState(pos).getType().getTickDelay(level));
        }
        cir.setReturnValue(super.updateShape(state, direction, neighborState, level, pos, neighborPos));
    }

    @Inject(method = "getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/BlockPlaceContext;getClickedFace()Lnet/minecraft/core/Direction;"))
    public void getStateForPlacementMixin(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir,
                                          @Local(ordinal = 1) BlockState blockState2, @Local FluidState fluidState) {
        blockState2.setValue(SlabBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER
                || fluidState.getType() == FluidRegistry.STILL_TOXIC_BARRIER_FLUID.get());
    }
}
