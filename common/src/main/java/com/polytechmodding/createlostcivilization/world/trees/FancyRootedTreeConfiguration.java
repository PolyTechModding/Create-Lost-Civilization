package com.polytechmodding.createlostcivilization.world.trees;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.List;
import java.util.Optional;

public class FancyRootedTreeConfiguration extends TreeConfiguration {

    public static final Codec<FancyRootedTreeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((treeConfiguration) -> treeConfiguration.trunkProvider), TrunkPlacer.CODEC.fieldOf("trunk_placer").forGetter((treeConfiguration) -> treeConfiguration.trunkPlacer), BlockStateProvider.CODEC.fieldOf("foliage_provider").forGetter((treeConfiguration) -> treeConfiguration.foliageProvider), FoliagePlacer.CODEC.fieldOf("foliage_placer").forGetter((treeConfiguration) -> treeConfiguration.foliagePlacer), RootPlacer.CODEC.optionalFieldOf("root_placer").forGetter((treeConfiguration) -> treeConfiguration.rootPlacer), BlockStateProvider.CODEC.fieldOf("dirt_provider").forGetter((treeConfiguration) -> treeConfiguration.dirtProvider), FeatureSize.CODEC.fieldOf("minimum_size").forGetter((treeConfiguration) -> treeConfiguration.minimumSize), TreeDecorator.CODEC.listOf().fieldOf("decorators").forGetter((treeConfiguration) -> treeConfiguration.decorators), Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter((treeConfiguration) -> treeConfiguration.ignoreVines), Codec.BOOL.fieldOf("force_dirt").orElse(false).forGetter((treeConfiguration) -> treeConfiguration.forceDirt), BlockStateProvider.CODEC.fieldOf("fluid_provider").forGetter((treeConfiguration) -> treeConfiguration.fluidProvider), BlockStateProvider.CODEC.fieldOf("stairs_provider").forGetter((treeConfiguration) -> treeConfiguration.stairsProvider), BlockStateProvider.CODEC.fieldOf("slab_provider").forGetter((treeConfiguration) -> treeConfiguration.slabProvider), BlockStateProvider.CODEC.fieldOf("vertical_slab_provider").forGetter((treeConfiguration) -> treeConfiguration.verticalSlabProvider)).apply(instance, FancyRootedTreeConfiguration::new));

    public final BlockStateProvider fluidProvider;
    public final BlockStateProvider stairsProvider;
    public final BlockStateProvider slabProvider;
    public final BlockStateProvider verticalSlabProvider;

    protected FancyRootedTreeConfiguration(BlockStateProvider trunkProvider,
                                           TrunkPlacer trunkPlacer,
                                           BlockStateProvider foliageProvider,
                                           FoliagePlacer foliagePlacer,
                                           Optional<RootPlacer> rootPlacer,
                                           BlockStateProvider dirtProvider,
                                           FeatureSize minimumSize,
                                           List<TreeDecorator> decorators,
                                           boolean ignoreVines,
                                           boolean forceDirt,
                                           BlockStateProvider fluidProvider,
                                           BlockStateProvider stairsProvider,
                                           BlockStateProvider slabProvider,
                                           BlockStateProvider verticalSlabProvider) {
        super(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, rootPlacer, dirtProvider, minimumSize, decorators, ignoreVines, forceDirt);
        this.fluidProvider = fluidProvider;
        this.stairsProvider = stairsProvider;
        this.slabProvider = slabProvider;
        this.verticalSlabProvider = verticalSlabProvider;
    }

    public FancyRootedTreeConfiguration(BlockStateProvider trunkProvider,
                                        TrunkPlacer trunkPlacer,
                                        BlockStateProvider foliageProvider,
                                        FoliagePlacer foliagePlacer,
                                        Optional<RootPlacer> rootPlacer,
                                        BlockStateProvider dirtProvider,
                                        FeatureSize minimumSize,
                                        List<TreeDecorator> decorators,
                                        boolean ignoreVines,
                                        boolean forceDirt,
                                        BlockStateProvider fluidProvider,
                                        BlockStateProvider stairsProvider,
                                        BlockStateProvider slabProvider,
                                        BlockStateProvider verticalSlabProvider,
                                        int i) {
        this(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, rootPlacer, dirtProvider, minimumSize, decorators, ignoreVines, forceDirt, fluidProvider, stairsProvider, slabProvider, verticalSlabProvider);
    }
}
