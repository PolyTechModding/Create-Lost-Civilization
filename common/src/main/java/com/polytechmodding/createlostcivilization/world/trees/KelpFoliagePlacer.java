package com.polytechmodding.createlostcivilization.world.trees;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.polytechmodding.createlostcivilization.world.level.features.CivilizationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

public class KelpFoliagePlacer extends FoliagePlacer {

    public static final Codec<KelpFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> KelpFoliagePlacer.foliagePlacerParts(instance).apply(instance, KelpFoliagePlacer::new));

    private final IntProvider foliageHeight;
    public KelpFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
        this.foliageHeight = radius;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return CivilizationFeatures.KELP_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliagePlacer.FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        boolean bl = attachment.doubleTrunk();
        BlockPos blockPos = attachment.pos().above(offset);
        this.placeLeavesRow(level, blockSetter, random, config, blockPos, 0, 1, false);
    }

    @Override
    public int foliageHeight(RandomSource randomSource, int trunkHeight, TreeConfiguration treeConfiguration) {
        return foliageHeight.sample(randomSource);
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource randomSource, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
