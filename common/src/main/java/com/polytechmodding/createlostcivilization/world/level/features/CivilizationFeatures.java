package com.polytechmodding.createlostcivilization.world.level.features;

import com.arcaneengineering.arcanelib.config.Variants;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import com.polytechmodding.createlostcivilization.families.BlockFamiliesFactory;
import com.polytechmodding.createlostcivilization.world.trees.KelpFoliagePlacer;
import com.polytechmodding.createlostcivilization.world.trees.KelpTrunkPlacer;
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
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

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

    public static ResourceKey<ConfiguredFeature<?, ?>> BLUE_KELP_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "blue_kelp_tree"));

    public static ResourceKey<ConfiguredFeature<?, ?>> GREEN_KELP_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "green_kelp_tree"));

    public static ResourceKey<ConfiguredFeature<?, ?>> ORANGE_KELP_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "orange_kelp_tree"));

    public static ResourceKey<ConfiguredFeature<?, ?>> PINK_KELP_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "pink_kelp_tree"));

    public static ResourceKey<ConfiguredFeature<?, ?>> RED_KELP_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "red_kelp_tree"));

    public static ResourceKey<ConfiguredFeature<?, ?>> YELLOW_KELP_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(CreateLostCivilization.MOD_ID, "yellow_kelp_tree"));


    public static RegistrySupplier<TrunkPlacerType<?>> KELP_TRUNK_PLACER =
            trunkPlacerTypeRegistry.register("kelp_trunk_placer",
                    () -> new TrunkPlacerType<>(KelpTrunkPlacer.CODEC));

    public static RegistrySupplier<FoliagePlacerType<?>> KELP_FOLIAGE_PLACER =
            foliagePlacerTypeRegistry.register("kelp_foliage_placer",
                    () -> new FoliagePlacerType<>(KelpFoliagePlacer.CODEC));


    public static void register() {
        foliagePlacerTypeRegistry.register();
        trunkPlacerTypeRegistry.register();
        decorationPlacerTypeRegistry.register();
    }

    public static void bootstrapType(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> holderGetter = context.lookup(Registries.BLOCK);
        context.register(BLUE_KELP_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockFamiliesFactory.BLUE_KELP_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new KelpTrunkPlacer(13, 2, 14), // places a spiral trunk
                        BlockStateProvider.simple(BlockFamiliesFactory.BLUE_KELP_FAMILY.get(Variants.LEAVES).get()), // Foliage block provider
                        new KelpFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves
                        new TwoLayersFeatureSize(2, 0, 2) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                ).build()));
        context.register(GREEN_KELP_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockFamiliesFactory.GREEN_KELP_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new KelpTrunkPlacer(13, 2, 14), // places a spiral trunk
                        BlockStateProvider.simple(BlockFamiliesFactory.GREEN_KELP_FAMILY.get(Variants.LEAVES).get()), // Foliage block provider
                        new KelpFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves
                        new TwoLayersFeatureSize(2, 0, 2) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                ).build()));
        context.register(ORANGE_KELP_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockFamiliesFactory.ORANGE_KELP_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new KelpTrunkPlacer(13, 2, 14), // places a spiral trunk
                        BlockStateProvider.simple(BlockFamiliesFactory.ORANGE_KELP_FAMILY.get(Variants.LEAVES).get()), // Foliage block provider
                        new KelpFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves
                        new TwoLayersFeatureSize(2, 0, 2) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                ).build()));
        context.register(PINK_KELP_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockFamiliesFactory.PINK_KELP_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new KelpTrunkPlacer(13, 2, 14), // places a spiral trunk
                        BlockStateProvider.simple(BlockFamiliesFactory.PINK_KELP_FAMILY.get(Variants.LEAVES).get()), // Foliage block provider
                        new KelpFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves
                        new TwoLayersFeatureSize(2, 0, 2) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                ).build()));
        context.register(RED_KELP_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockFamiliesFactory.RED_KELP_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new KelpTrunkPlacer(13, 2, 14), // places a spiral trunk
                        BlockStateProvider.simple(BlockFamiliesFactory.RED_KELP_FAMILY.get(Variants.LEAVES).get()), // Foliage block provider
                        new KelpFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves
                        new TwoLayersFeatureSize(2, 0, 2) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                ).build()));
        context.register(YELLOW_KELP_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockFamiliesFactory.YELLOW_KELP_FAMILY.get(Variants.LOG).get()), // Trunk block provider
                        new KelpTrunkPlacer(13, 2, 14), // places a spiral trunk
                        BlockStateProvider.simple(BlockFamiliesFactory.YELLOW_KELP_FAMILY.get(Variants.LEAVES).get()), // Foliage block provider
                        new KelpFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)), // places leaves
                        new TwoLayersFeatureSize(2, 0, 2) // The width of the tree at different layers; used to see how tall the tree can be without clipping into blocks
                ).build()));
    }
}