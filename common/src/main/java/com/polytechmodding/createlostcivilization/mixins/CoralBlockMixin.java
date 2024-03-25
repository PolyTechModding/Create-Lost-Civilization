package com.polytechmodding.createlostcivilization.mixins;

import com.polytechmodding.createlostcivilization.world.level.dimension.CivilizationDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlock.class)
public abstract class CoralBlockMixin extends Block {

    public CoralBlockMixin(Properties properties) {
        super(properties);
    }

    @Shadow protected abstract boolean scanForWater(BlockGetter arg, BlockPos arg2);

    @Shadow @Final private Block deadBlock;

    @Inject(method = "tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V",
            at = @At("HEAD"), cancellable = true)
    private void redirectTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        if (createLostCivilization$isDecaying(serverLevel, blockPos)) {
            serverLevel.setBlock(blockPos, this.deadBlock.defaultBlockState(), 2);
        }
        ci.cancel();
    }

    @Inject(method = "updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
    at = @At("HEAD"), cancellable = true)
    private void redirectUpdateShape(BlockState blockState, Direction direction,
                                     BlockState blockState2, LevelAccessor levelAccessor,
                                     BlockPos blockPos, BlockPos blockPos2, CallbackInfoReturnable<BlockState> cir) {
        if (createLostCivilization$isDecaying((Level) levelAccessor, blockPos)) {
            levelAccessor.scheduleTick(blockPos, this, 60 + levelAccessor.getRandom().nextInt(40));
        }

        cir.setReturnValue(super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2));
    }

    @Inject(method = "getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;",
            at = @At("HEAD"), cancellable = true)
    private void redirectUpdateShape(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
        if (createLostCivilization$isDecaying(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
            blockPlaceContext.getLevel().scheduleTick(blockPlaceContext.getClickedPos(), this, 60 + blockPlaceContext.getLevel().getRandom().nextInt(40));
        }

        cir.setReturnValue(defaultBlockState());
    }

    @Unique
    protected boolean createLostCivilization$isDecaying(Level levelAccessor, BlockPos blockPos) {
        return (!this.scanForWater(levelAccessor, blockPos) &&
                !levelAccessor.dimensionTypeId()
                        .equals(CivilizationDimensions.MYSTERY_PLANET)) ||
                (!this.createLostCivilization$scanForAir(levelAccessor, blockPos) &&
                        levelAccessor.dimensionTypeId()
                                .equals(CivilizationDimensions.MYSTERY_PLANET));
    }

    @Unique
    protected boolean createLostCivilization$scanForAir(BlockGetter blockGetter, BlockPos blockPos) {
        for(Direction direction: Direction.values()) {
            BlockState blockState = blockGetter.getBlockState(blockPos.relative(direction));
            if (blockState.is(Blocks.AIR)) {
                return true;
            }
        }
        return false;
    }
}
