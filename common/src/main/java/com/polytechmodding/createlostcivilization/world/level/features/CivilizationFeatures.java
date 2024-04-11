package com.polytechmodding.createlostcivilization.world.level.features;

import com.arcaneengineering.arcanelib.config.Variants;
import com.mojang.serialization.Codec;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import com.polytechmodding.createlostcivilization.families.BlockFamiliesFactory;
import com.polytechmodding.createlostcivilization.families.CivilizationVariants;
import com.polytechmodding.createlostcivilization.world.CivilizationRegistry;
import com.polytechmodding.createlostcivilization.world.trees.CypressFoliagePlacer;
import com.polytechmodding.createlostcivilization.world.trees.CypressRootFoliagePlacer;
import com.polytechmodding.createlostcivilization.world.trees.FancyRootedTreeConfiguration;
import com.polytechmodding.createlostcivilization.world.trees.FancyRootedTrunkPlacer;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Optional;

public final class CivilizationFeatures {
    private static final DeferredRegister<TrunkPlacerType<?>> trunkPlacerTypeRegistry =
            DeferredRegister.create(CreateLostCivilization.MOD_ID, Registries.TRUNK_PLACER_TYPE);

    private static final DeferredRegister<FoliagePlacerType<?>> foliagePlacerTypeRegistry =
            DeferredRegister.create(CreateLostCivilization.MOD_ID, Registries.FOLIAGE_PLACER_TYPE);

    private static final DeferredRegister<TreeDecoratorType<?>> decorationPlacerTypeRegistry =
            DeferredRegister.create(CreateLostCivilization.MOD_ID, Registries.TREE_DECORATOR_TYPE);
 /*
    public static final Supplier<? extends ConfiguredFeature<TreeConfiguration, ?>> exampleTree =
            featuresRegistry.register("test_tree", () -> new ConfiguredFeature<>(Feature.TREE,
                    new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(Blocks.NETHERITE_BLOCK), // Trunk block provider
                    new StraightTrunkPlacer(8, 3, 0), // places a straight trunk
                    BlockStateProvider.simple(Blocks.DIAMOND_BLOCK), // Foliage block provider
                    new BlobFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0), 3), // places leaves as a blob (radius, offset from trunk, height)
                    new TwoLayersFeatureSize(1, 0, 1) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
            ).build()));
    public static final Supplier<? extends TrunkPlacerType<?>> exampleTrunkPlayer =
            trunkPlacerTypeRegistry.register("test_trunk_placer", () -> new TrunkPlacerType<>(StraightTrunkPlacer.CODEC));

    public static final Supplier<? extends FoliagePlacerType<?>> exampleFoliagePlayer =
            foliagePlacerTypeRegistry.register("test_foliage_placer", () -> new FoliagePlacerType<>(FancyFoliagePlacer.CODEC));

    public static final Supplier<? extends TreeDecoratorType<?>> exampleDecorationPlayer =
            decorationPlacerTypeRegistry.register("test_decoration_placer", () -> new TreeDecoratorType<>(TrunkVineDecorator.CODEC));
    
  */

    public static ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "cypress_tree"));


    public static RegistrySupplier<TrunkPlacerType<?>> CYPRESS_TRUNK_PLACER =
            trunkPlacerTypeRegistry.register("cypress_trunk_placer",
            () -> new TrunkPlacerType<>(FancyRootedTrunkPlacer.CODEC));

    public static RegistrySupplier<FoliagePlacerType<?>> CYPRESS_ROOT_FOLIAGE_PLACER =
            foliagePlacerTypeRegistry.register("cypress_root_foliage_placer",
                    () -> new FoliagePlacerType<>(CypressRootFoliagePlacer.CODEC));

    public static RegistrySupplier<FoliagePlacerType<?>> CYPRESS_FOLIAGE_PLACER =
            foliagePlacerTypeRegistry.register("cypress_foliage_placer",
                    () -> new FoliagePlacerType<>(CypressFoliagePlacer.CODEC));


    public static void register() {
        featureRegistry.register();
        foliagePlacerTypeRegistry.register();
        trunkPlacerTypeRegistry.register();
        decorationPlacerTypeRegistry.register();
    }

    private static final DeferredRegister<Feature<?>> featureRegistry =
            DeferredRegister.create(CreateLostCivilization.MOD_ID, Registries.FEATURE);

    public static RegistrySupplier<Feature<TreeConfiguration>> TREE =
            featureRegistry.register("fancy_rooted_tree",
                    () -> new TreeFeature((Codec<TreeConfiguration>)((Object) FancyRootedTreeConfiguration.CODEC)));

    public static void bootstrapType(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> holderGetter = context.lookup(Registries.BLOCK);
        context.register(CYPRESS_TREE, new ConfiguredFeature<>(TREE.get(),
                new FancyRootedTreeConfiguration(
                        BlockStateProvider.simple(BlockFamiliesFactory.CYPRESS_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new FancyRootedTrunkPlacer(13, 2, 14), // places a straight trunk
                        BlockStateProvider.simple(Blocks.DIAMOND_BLOCK), // Foliage block provider
                        new CypressFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves as a blob (radius, offset from trunk, height)
                        Optional.empty(),
                        BlockStateProvider.simple(Blocks.DIRT),
                        new TwoLayersFeatureSize(2, 0, 2), // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                        List.of(), false, false,
                        BlockStateProvider.simple(CivilizationRegistry.TOXIC_BARRIER_FLUID.get()),
                        BlockStateProvider.simple(BlockFamiliesFactory.CYPRESS_FAMILY.get(CivilizationVariants.LOG_ROOTS_CORNER).get()),
                        BlockStateProvider.simple(BlockFamiliesFactory.CYPRESS_FAMILY.get(CivilizationVariants.LOG_ROOTS).get()),
                        BlockStateProvider.simple(BlockFamiliesFactory.CYPRESS_FAMILY.get(CivilizationVariants.VERTICAL_LOG_ROOTS).get()),
                        0
                )));
    }
}
