package com.polytechmodding.createlostcivilization.world.trees;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.polytechmodding.createlostcivilization.world.level.features.CivilizationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

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
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(blockPos.above(i), 0, true));
    }

    protected static void setFluidAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, FancyRootedTreeConfiguration config) {
        blockSetter.accept(pos, config.fluidProvider.getState(random, pos));
    }

    private void placeLogIfFreeWithOffset(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, BlockPos.MutableBlockPos mutableBlockPos, TreeConfiguration treeConfiguration, BlockPos blockPos, int i, int j, int k) {
        mutableBlockPos.setWithOffset(blockPos, i, j, k);
        biConsumer.accept(mutableBlockPos, (BlockState)Function.identity().apply(treeConfiguration.trunkProvider.getState(randomSource, blockPos)));
    }
}
