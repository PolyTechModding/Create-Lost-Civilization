package com.polytechmodding.createlostcivilization.world.blocks;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VerticalSlabBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<VerticalSlabBlock> CODEC = simpleCodec(VerticalSlabBlock::new);
    public static final BooleanProperty SINGLE = BooleanProperty.create("single_slab");

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SINGLE,
                BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    private final SoundEvent blockPlaceSound;

    public VerticalSlabBlock(BlockBehaviour.Properties properties) {
        this(properties, SoundEvents.WOOD_PLACE);
    }

    public VerticalSlabBlock(BlockBehaviour.Properties properties, SoundEvent blockPlaceSound) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, false)
                .setValue(SINGLE, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));

        this.blockPlaceSound = blockPlaceSound;
    }

    public void playPlaceSound(Level world, BlockPos pos) {
        world.playSound(
                null, pos, blockPlaceSound, SoundSource.BLOCKS, 1f, 0.75f);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        // changes hitbox depending on block state

        Direction dir = state.getValue(FACING);
        if (!state.getValue(SINGLE)) {
            return Shapes.block();
        } else if (dir == Direction.NORTH) {
            return Shapes.box(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
        } else if (dir == Direction.SOUTH) {
            return Shapes.box(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
        } else if (dir == Direction.EAST) {
            return Shapes.box(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        } else if (dir == Direction.WEST) {
            return Shapes.box(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
        }
        return Shapes.block();

    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level,
                                          BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {

        return combineSlab(state, level, pos, player, hand, hit);
    }

    private InteractionResult combineSlab(BlockState state, Level level,
                                     BlockPos pos, Player player, InteractionHand hand,
                                     BlockHitResult hit) {

        player.getItemInHand(hand);
        // checks if player clicked on block with same item type
        if (player.getAbilities().mayBuild
                && ItemStack.matches(new ItemStack(this), player.getItemInHand(hand))
                && state.getValue(SINGLE)) {

            InteractionResult RESULT = InteractionResult.FAIL;
            player.getBlockX();

            if (state.getValue(SINGLE)) {
                Direction HITSIDE = hit.getDirection();

                // is facing north and hit from south
                if (state.getValue(FACING) == Direction.NORTH) {
                    if (HITSIDE == Direction.SOUTH) {
                        level.setBlockAndUpdate(pos, state.setValue(SINGLE, false));
                        RESULT = successfulPlace(player, hand, level, pos);
                    }
                }
                // is facing east and hit from west
                else if (state.getValue(FACING) == Direction.EAST) {
                    if (HITSIDE == Direction.WEST) {
                        level.setBlockAndUpdate(pos, state.setValue(SINGLE, false));
                        RESULT = successfulPlace(player, hand, level, pos);
                    }
                }
                // is facing south and hit from north
                else if (state.getValue(FACING) == Direction.SOUTH) {
                    if (HITSIDE == Direction.NORTH) {
                        level.setBlockAndUpdate(pos, state.setValue(SINGLE, false));
                        RESULT = successfulPlace(player, hand, level, pos);
                    }
                }
                // is facing west and hit from east
                else if (state.getValue(FACING) == Direction.WEST) {
                    if (HITSIDE == Direction.EAST) {
                        level.setBlockAndUpdate(pos, state.setValue(SINGLE, false));
                        RESULT = successfulPlace(player, hand, level, pos);
                    }
                }

            }

            return RESULT;
        } else {
            return InteractionResult.PASS;
        }

    }

    private InteractionResult successfulPlace(Player player, InteractionHand hand, Level level, BlockPos pos) {
        // checks if player is in creative and removes 1 item if not
        boolean isInCreative = player.getAbilities().invulnerable;
        if (!isInCreative) {
            player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
        }
        playPlaceSound(level, pos);

        // makes arm swing
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection());
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(SINGLE)) {
            return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
        }
        return false;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        if (state.getValue(SINGLE)) {
            return SimpleWaterloggedBlock.super.canPlaceLiquid(player, level, pos, state, fluid);
        }
        return false;
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return state.getFluidState();
    }
}