package com.polytechmodding.createlostcivilization.world.trees;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.polytechmodding.createlostcivilization.world.level.features.CivilizationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FancyRootedTrunkPlacer extends TrunkPlacer {

    public static final Codec<FancyRootedTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance)
            .apply(instance, FancyRootedTrunkPlacer::new));

    public FancyRootedTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return CivilizationFeatures.CYPRESS_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int i, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        FancyRootedTreeConfiguration fancyRootedTreeConfiguration;
        try {
            fancyRootedTreeConfiguration = (FancyRootedTreeConfiguration) treeConfiguration;
        } catch (ClassCastException e) {
            throw new RuntimeException("TreeConfiguration is not a FancyRootedTreeConfiguration");
        }
        BlockPos below = blockPos.below();
        for(int x = -6; x <= 6; x++) {
            for(int z = -6; z <= 6; z++) {
                if(!((x >= -2 && x <= 2) && (z >= -2 && z <= 2))) {
                    setFluidAt(levelSimulatedReader, biConsumer, randomSource, blockPos.offset(x, 0, z), fancyRootedTreeConfiguration);
                }
                TrunkPlacer.setDirtAt(levelSimulatedReader, biConsumer, randomSource, below.offset(x, 0, z), treeConfiguration);
            }
        }
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int j = 0; j < i; j++) {
            if(j < 3) {
                for(int x = -2; x <= 2; x++) {
                    for(int z = -2; z <= 2; z++) {
                        this.placeLogIfFreeWithOffset(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration, blockPos, x, j, z);
                    }
                }
            } else {
                for(int x = -1; x <= 1; x++) {
                    for(int z = -1; z <= 1; z++) {
                        this.placeLogIfFreeWithOffset(levelSimulatedReader, biConsumer, randomSource, mutableBlockPos, treeConfiguration, blockPos, x, j, z);
                    }
                }
            }

        }
        placeCorners(levelSimulatedReader, biConsumer, randomSource, blockPos, fancyRootedTreeConfiguration);
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(blockPos.above(i), 0, true));
    }

    protected void placeCorners(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, FancyRootedTreeConfiguration config) {
        // 3,3 -3,3 3,-3 -3,-3
        List<BlockPos> corners = new ArrayList<>();
        corners.add(pos.offset(3, 0, 3));
        corners.add(pos.offset(3, 0, -3));
        corners.add(pos.offset(-3, 0, 3));
        corners.add(pos.offset(-3, 0, -3));
        // Choose corner type
        for(BlockPos corner : corners) {
            int randomCorner = random.nextInt(3);
            switch (randomCorner) {
                case 0: placeCornerType2(level, blockSetter, random, corner, config);
                case 1: placeCornerType2(level, blockSetter, random, corner, config);
                case 2: placeCornerType2(level, blockSetter, random, corner, config);
                case 3: placeCornerType2(level, blockSetter, random, corner, config);
            }
        }
    }

    protected void placeCorner(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, FancyRootedTreeConfiguration config) {
        Direction[] directions = getCornerDirections(level, config, random, pos);
        placeLogIfFreeWithOffset(level, blockSetter, random, new BlockPos.MutableBlockPos(), config, pos, 0,0,0);
        setCornerAt(level, blockSetter, random, pos.offset(0, 1, 0), config, directions[0], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, pos.relative(directions[0].getOpposite(), 1),
                config, directions[0], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, pos.relative(directions[1], 1),
                config, directions[0], Half.BOTTOM, StairsShape.INNER_RIGHT);
        setCornerAt(level, blockSetter, random, pos.relative(directions[1], 1)
                        .relative(directions[0].getOpposite(), 1),
                config, directions[1].getOpposite(), Half.BOTTOM, StairsShape.OUTER_LEFT);
        setCornerAt(level, blockSetter, random, pos.relative(directions[1].getOpposite(), 1),
                config, directions[0], Half.BOTTOM, StairsShape.OUTER_LEFT);

        BlockPos toNorth = pos.relative(directions[0], 1);
        placeLogIfFreeWithOffset(level, blockSetter, random, new BlockPos.MutableBlockPos(), config, toNorth, 0,0,0);
        placeLogIfFreeWithOffset(level, blockSetter, random, new BlockPos.MutableBlockPos(), config, toNorth, 0,1,0);

        setCornerAt(level, blockSetter, random, toNorth.offset(0, 2, 0),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, toNorth.relative(directions[1].getOpposite(), 1),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, toNorth.relative(directions[1].getOpposite(), 2),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, toNorth.relative(directions[1].getOpposite(), 1).offset(0, 1, 0),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);

        BlockPos toNorthTwo = pos.relative(directions[0], 2);
        placeLogIfFreeWithOffset(level, blockSetter, random, new BlockPos.MutableBlockPos(), config, toNorthTwo, 0,0,0);
        setCornerAt(level, blockSetter, random, toNorthTwo.offset(0, 1, 0),
                config, directions[0].getOpposite(), Half.BOTTOM, StairsShape.STRAIGHT);
        setSlabAt(level, blockSetter, random, toNorthTwo.relative(directions[1].getOpposite(), 1),
                config, SlabType.DOUBLE);

        BlockPos toNorthThree = pos.relative(directions[0], 3);
        setCornerAt(level, blockSetter, random, toNorthThree.offset(0, 0, 0),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);
    }

    protected void placeCornerType2(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, FancyRootedTreeConfiguration config) {
        Direction[] directions = getCornerDirections(level, config, random, pos);

        setCornerAt(level, blockSetter, random, pos.offset(0, 0, 0),
                config, directions[0], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, pos.relative(directions[1], 1),
                config, directions[1], Half.BOTTOM, StairsShape.INNER_RIGHT);

        BlockPos toEast = pos.relative(directions[0], 1);
        placeLogIfFreeWithOffset(level, blockSetter, random, new BlockPos.MutableBlockPos(), config, toEast, 0,0,0);
        placeLogIfFreeWithOffset(level, blockSetter, random, new BlockPos.MutableBlockPos(), config, toEast, 0,1,0);
        setCornerAt(level, blockSetter, random, toEast.offset(0, 2, 0),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);

        setCornerAt(level, blockSetter, random, toEast.relative(directions[1].getOpposite(), 1),
                config, directions[1], Half.BOTTOM, StairsShape.STRAIGHT);
        setCornerAt(level, blockSetter, random, toEast.relative(directions[0], 1),
                config, directions[0].getOpposite(), Half.BOTTOM, StairsShape.STRAIGHT);

    }

    protected Direction[] getCornerDirections(LevelSimulatedReader level, TreeConfiguration config, RandomSource random, BlockPos pos) {
        Direction[] directions = new Direction[2];
        List<BlockPos> corners = new ArrayList<>();
        corners.add(pos.offset(1, 0, 1));
        corners.add(pos.offset(1, 0, -1));
        corners.add(pos.offset(-1, 0, 1));
        corners.add(pos.offset(-1, 0, -1));
        // This O(4*4) = O(16) = O(1)
        for(BlockPos left : corners) {
            for(BlockPos right : corners) {
                if(isTrunk(level, config, random, left) && left.equals(right)) {
                    if(pos.west().north().equals(left)) {
                        directions[0] = Direction.NORTH;
                        directions[1] = Direction.WEST;
                    } else if(pos.south().west().equals(left)) {
                        directions[0] = Direction.WEST;
                        directions[1] = Direction.SOUTH;
                    } else if(pos.east().south().equals(left)) {
                        directions[0] = Direction.SOUTH;
                        directions[1] = Direction.EAST;
                    } else if(pos.north().east().equals(left)) {
                        directions[0] = Direction.EAST;
                        directions[1] = Direction.NORTH;
                    }
                }
            }
        }
        return directions;
    }

    private static boolean isTrunk(LevelSimulatedReader level, TreeConfiguration config, RandomSource random, BlockPos pos) {
        return level.isStateAtPosition(pos, (arg) -> arg.is(config.trunkProvider.getState(random, pos).getBlock()));
    }

    protected static void setFluidAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, FancyRootedTreeConfiguration config) {
        blockSetter.accept(pos, config.fluidProvider.getState(random, pos));
    }

    protected static void setCornerAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos,
                                      FancyRootedTreeConfiguration config, Direction direction, Half half, StairsShape stairsShape) {
        blockSetter.accept(pos, config.stairsProvider.getState(random, pos).setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                .setValue(BlockStateProperties.HALF, half).setValue(BlockStateProperties.STAIRS_SHAPE, stairsShape));
    }

    protected static void setSlabAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos,
                                    FancyRootedTreeConfiguration config, SlabType slabType) {
        blockSetter.accept(pos, config.slabProvider.getState(random, pos).setValue(BlockStateProperties.SLAB_TYPE, slabType));
    }

    protected static void setVerticalSlabAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, FancyRootedTreeConfiguration config) {
        blockSetter.accept(pos, config.verticalSlabProvider.getState(random, pos));
    }

    private void placeLogIfFreeWithOffset(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, BlockPos.MutableBlockPos mutableBlockPos, TreeConfiguration treeConfiguration, BlockPos blockPos, int i, int j, int k) {
        mutableBlockPos.setWithOffset(blockPos, i, j, k);
        biConsumer.accept(mutableBlockPos, (BlockState)Function.identity().apply(treeConfiguration.trunkProvider.getState(randomSource, blockPos)));
    }
}
