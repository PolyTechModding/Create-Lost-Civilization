package com.polytechmodding.createlostcivilization.world.trees;

import com.mojang.datafixers.kinds.Monoid;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static java.lang.Math.round;

public class KelpTrunkPlacer extends TrunkPlacer {

    public static final Codec<KelpTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> KelpTrunkPlacer.trunkPlacerParts(instance).apply(instance, KelpTrunkPlacer::new));

    public KelpTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return CivilizationFeatures.KELP_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int height, BlockPos blockPos, TreeConfiguration treeConfiguration) {

        int radius = randomSource.nextInt(3, 5);
        float frequency = (float) (((float) radius) / 20.0);

        ArrayList<FoliagePlacer.FoliageAttachment> foliageAttachmentArrayList = new ArrayList<>();

        //Place the actual Blocks
        for (int i = 0; i < height * 5; i++) {

            for(int j = 0; j < 3; j++){
                placeTrunkBlock(levelSimulatedReader, biConsumer, randomSource, (float) (i / 5.0), blockPos, radius, frequency, 2*j, treeConfiguration, foliageAttachmentArrayList, 0, true);
                placeTrunkBlock(levelSimulatedReader, biConsumer, randomSource, (float) (i / 5.0), blockPos, radius+3, frequency, 2*j, treeConfiguration, foliageAttachmentArrayList, 120, false);
                placeTrunkBlock(levelSimulatedReader, biConsumer, randomSource, (float) (i / 5.0), blockPos, radius+6, frequency, 2*j, treeConfiguration, foliageAttachmentArrayList, 240, true);
            }
//
//            this.placeLog(levelSimulatedReader, biConsumer, randomSource, this.calculateBlockPos((float) (i / 5.0), blockPos, radius, frequency, 0), treeConfiguration);
//            this.placeLog(levelSimulatedReader, biConsumer, randomSource, this.calculateBlockPos((float) (i / 5.0), blockPos, radius, frequency, 2), treeConfiguration);
//            this.placeLog(levelSimulatedReader, biConsumer, randomSource, this.calculateBlockPos((float) (i / 5.0), blockPos, radius, frequency, 4), treeConfiguration);
//
//            //TELL THE CODE WHERE TO PUT LEAVES
//            foliageAttachmentArrayList.add(new FoliagePlacer.FoliageAttachment(this.calculateBlockPos((float) (i / 5.0), blockPos, radius, frequency, 0), i, false));
//            foliageAttachmentArrayList.add(new FoliagePlacer.FoliageAttachment(this.calculateBlockPos((float) (i / 5.0), blockPos, radius, frequency, 2), i, false));
//            foliageAttachmentArrayList.add(new FoliagePlacer.FoliageAttachment(this.calculateBlockPos((float) (i / 5.0), blockPos, radius, frequency, 4), i, false));
        }

        return foliageAttachmentArrayList;
    }

    private void placeTrunkBlock(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, float i, BlockPos blockPos, int radius, float frequency, int j, TreeConfiguration treeConfiguration, ArrayList<FoliagePlacer.FoliageAttachment> foliageAttachmentArrayList, int off, boolean counterClockWise){
        int sin = (int) round((radius*(Math.sin(i*frequency + (j* Math.PI/3) + ((off / 360.0)*Math.PI*2)))));
        int cos = (int) round((radius*(Math.cos(i*frequency + (j* Math.PI/3) + ((off / 360.0)*Math.PI*2)))));

        int b = sin;
        int a = cos;

        if(counterClockWise){
            b = cos;
            a = sin;
        }

        this.placeLog(levelSimulatedReader, biConsumer, randomSource, blockPos.offset(a, (int)i, b), treeConfiguration);
        //TELL THE CODE WHERE TO PUT LEAVES
        foliageAttachmentArrayList.add(new FoliagePlacer.FoliageAttachment(blockPos.offset(a, (int)i, b), (int)i, false));
    }
}
