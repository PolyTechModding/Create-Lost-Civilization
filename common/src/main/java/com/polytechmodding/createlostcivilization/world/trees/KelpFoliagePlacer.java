package com.polytechmodding.createlostcivilization.world.trees;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
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
        return null;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i, FoliageAttachment foliageAttachment, int j, int k, int l) {
        boolean bl = foliageAttachment.doubleTrunk();
        BlockPos blockPos = foliageAttachment.pos().above(l);
        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, k + foliageAttachment.radiusOffset(), -1 - j, bl);
        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, k - 1, -j, bl);
        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, blockPos, k + foliageAttachment.radiusOffset() - 1, 0, bl);
        for (int m = l; m >= l - j; --m) {
            int n = k + (m == l || m == l - j ? 0 : 1);
            this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, foliageAttachment.pos(), n, m, foliageAttachment.doubleTrunk());
        }
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
