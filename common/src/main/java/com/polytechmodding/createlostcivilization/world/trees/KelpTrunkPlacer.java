package com.polytechmodding.createlostcivilization.world.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class KelpTrunkPlacer extends TrunkPlacer {
    public KelpTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return null;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int height, BlockPos blockPos, TreeConfiguration treeConfiguration) {

        int radius = randomSource.nextInt(5, 8);
        float frequency = randomSource.nextFloat();

        //Place the actual Blocks
        for (int i = 0; i < height; i++) {
            this.placeLog(levelSimulatedReader, biConsumer, randomSource, blockPos.offset(i, radius*((int)Math.sin(i*frequency + (0* Math.PI/3))), radius*((int)Math.cos(i*frequency + (0* Math.PI/3)))), treeConfiguration);
            this.placeLog(levelSimulatedReader, biConsumer, randomSource, blockPos.offset(i, radius*((int)Math.sin(i*frequency + (2* Math.PI/3))), radius*((int)Math.cos(i*frequency + (2* Math.PI/3)))), treeConfiguration);
            this.placeLog(levelSimulatedReader, biConsumer, randomSource, blockPos.offset(i, radius*((int)Math.sin(i*frequency + (4* Math.PI/3))), radius*((int)Math.cos(i*frequency + (4* Math.PI/3)))), treeConfiguration);
        }

        return null;
    }
}
