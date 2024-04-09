package com.polytechmodding.createlostcivilization.client;

import com.arcaneengineering.arcanelib.client.ClientBlockVariant;
import com.arcaneengineering.arcanelib.client.ClientVariants;
import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.config.types.BlockVariant;
import com.arcaneengineering.arcanelib.datagen.models.ArcaneModelTemplate;
import com.arcaneengineering.arcanelib.datagen.models.ArcaneModelTemplates;
import com.arcaneengineering.arcanelib.registry.BlockFamily;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import com.polytechmodding.createlostcivilization.families.CivilizationVariants;
import com.polytechmodding.createlostcivilization.world.blocks.VerticalSlabBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CivilizationClientVariants {

    public static void loadClass() {
        ClientVariants.addClientBlockVariant(CivilizationVariants.LOG_ROOTS_CORNER, LOG_ROOTS_CORNER);
        ClientVariants.addClientBlockVariant(CivilizationVariants.STRIPPED_LOG_ROOTS_CORNER, STRIPPED_LOG_ROOTS_CORNER);
        ClientVariants.addClientBlockVariant(CivilizationVariants.LOG_ROOTS, LOG_ROOTS);
        ClientVariants.addClientBlockVariant(CivilizationVariants.STRIPPED_LOG_ROOTS, STRIPPED_LOG_ROOTS);
        ClientVariants.addClientBlockVariant(CivilizationVariants.VERTICAL_LOG_ROOTS, VERTICAL_SLAB);
    }

    public static VerticalSlab VERTICAL_SLAB = new VerticalSlab();

    public static class VerticalSlab implements ClientBlockVariant {

        @Override
        public void modelDefinition(Block block, BlockFamily blockFamily, BlockModelGenerators generators,
                                    BlockVariant<?> blockVariant, TextureMapping baseBlockTextureMapping,
                                    ResourceLocation fullBlock, boolean withItem) {
            ResourceLocation logLocation = new ResourceLocation(CreateLostCivilization.MOD_ID, "block/cypress/log");
            ResourceLocation resourceLocation = CivilizationModelTemplates.VERTICAL_SLAB_ALL
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, (new TextureMapping()).put(TextureSlot.ALL, logLocation), generators.modelOutput);
            BlockStateGenerator stateGenerator = MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, VerticalSlabBlock.SINGLE)
                    .select(Direction.NORTH, true, Variant.variant()
                            .with(VariantProperties.MODEL, resourceLocation)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                            .with(VariantProperties.UV_LOCK, true))
                    .select(Direction.EAST, true, Variant.variant()
                            .with(VariantProperties.MODEL, resourceLocation)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                            .with(VariantProperties.UV_LOCK, true))
                    .select(Direction.SOUTH, true, Variant.variant()
                            .with(VariantProperties.MODEL, resourceLocation)
                            .with(VariantProperties.UV_LOCK, true))
                    .select(Direction.WEST, true, Variant.variant()
                            .with(VariantProperties.MODEL, resourceLocation)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                            .with(VariantProperties.UV_LOCK, true))
                    .select(Direction.NORTH, false, Variant.variant()
                            .with(VariantProperties.MODEL, logLocation))
                    .select(Direction.EAST, false, Variant.variant()
                            .with(VariantProperties.MODEL, logLocation))
                    .select(Direction.SOUTH, false, Variant.variant()
                            .with(VariantProperties.MODEL, logLocation))
                    .select(Direction.WEST, false, Variant.variant()
                            .with(VariantProperties.MODEL, logLocation))
            );
            generators.blockStateOutput.accept(stateGenerator);
            if(withItem) {
                generators.delegateItemModel(block, resourceLocation);
            }
        }
    }

    public static LogRootsCorner LOG_ROOTS_CORNER = new LogRootsCorner();

    public static class LogRootsCorner implements ClientBlockVariant {
        @Override
        public void modelDefinition(Block block, BlockFamily blockFamily, BlockModelGenerators generators,
                                    BlockVariant<?> blockVariant, TextureMapping baseBlockTextureMapping,
                                    ResourceLocation fullBlock, boolean withItem) {
            TextureMapping textureMapping = TexturedModel.createDefault((texturedModel) -> new TextureMapping()
                            .put(TextureSlot.SIDE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily))
                            .put(TextureSlot.END, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily)), ModelTemplates.CUBE_COLUMN)
                    .get(blockFamily.get(Variants.LOG).get()).getMapping();
            ResourceLocation resourceLocation = ArcaneModelTemplates.STAIRS_INNER
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            ResourceLocation resourceLocation1 = ArcaneModelTemplates.STAIRS_STRAIGHT
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            ResourceLocation resourceLocation2 = ArcaneModelTemplates.STAIRS_OUTER
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            generators.blockStateOutput.accept(BlockModelGenerators
                    .createStairs(block, resourceLocation, resourceLocation1, resourceLocation2));
            if(withItem) {
                generators.delegateItemModel(block, resourceLocation1);
            }
        }
    }

    public static StrippedLogRootsCorner STRIPPED_LOG_ROOTS_CORNER = new StrippedLogRootsCorner();

    public static class StrippedLogRootsCorner implements ClientBlockVariant {



        @Override
        public void modelDefinition(Block block, BlockFamily blockFamily, BlockModelGenerators generators,
                                    BlockVariant<?> blockVariant, TextureMapping baseBlockTextureMapping,
                                    ResourceLocation fullBlock, boolean withItem) {
            TextureMapping textureMapping = TexturedModel.createDefault((texturedModel) -> new TextureMapping()
                            .put(TextureSlot.SIDE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily))
                            .put(TextureSlot.END, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily)), ModelTemplates.CUBE_COLUMN)
                    .get(blockFamily.get(Variants.STRIPPED_LOG).get()).getMapping();
            ResourceLocation resourceLocation = ArcaneModelTemplates.STAIRS_INNER
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            ResourceLocation resourceLocation1 = ArcaneModelTemplates.STAIRS_STRAIGHT
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            ResourceLocation resourceLocation2 = ArcaneModelTemplates.STAIRS_OUTER
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            generators.blockStateOutput.accept(BlockModelGenerators
                    .createStairs(block, resourceLocation, resourceLocation1, resourceLocation2));
            if(withItem) {
                generators.delegateItemModel(block, resourceLocation1);
            }
        }
    }

    public static LogRoots LOG_ROOTS = new LogRoots();

    public static class LogRoots implements ClientBlockVariant {
        @Override
        public void modelDefinition(Block block, BlockFamily blockFamily, BlockModelGenerators generators,
                                    BlockVariant<?> blockVariant, TextureMapping baseBlockTextureMapping,
                                    ResourceLocation fullBlock, boolean withItem) {
            TextureMapping textureMapping = TexturedModel.createDefault((texturedModel) -> new TextureMapping()
                            .put(TextureSlot.SIDE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily))
                            .put(TextureSlot.END, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily)), ModelTemplates.CUBE_COLUMN)
                    .get(blockFamily.get(Variants.LOG).get()).getMapping();
            ResourceLocation logLocation = new ResourceLocation(CreateLostCivilization.MOD_ID, "block/cypress/log");
            ResourceLocation resourceLocation = ArcaneModelTemplates.SLAB_BOTTOM.create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            ResourceLocation resourceLocation1 = ArcaneModelTemplates.SLAB_TOP.create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            generators.blockStateOutput.accept(BlockModelGenerators.createSlab(block, resourceLocation, resourceLocation1, logLocation));
            if(withItem) {
                generators.delegateItemModel(block, resourceLocation);
            }
        }
    }

    public static StrippedLogRoots STRIPPED_LOG_ROOTS = new StrippedLogRoots();

    public static class StrippedLogRoots implements ClientBlockVariant {
        @Override
        public void modelDefinition(Block block, BlockFamily blockFamily, BlockModelGenerators generators,
                                    BlockVariant<?> blockVariant, TextureMapping baseBlockTextureMapping,
                                    ResourceLocation fullBlock, boolean withItem) {
            TextureMapping textureMapping = TexturedModel.createDefault((texturedModel) -> new TextureMapping()
                            .put(TextureSlot.SIDE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily))
                            .put(TextureSlot.END, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily)), ModelTemplates.CUBE_COLUMN)
                    .get(blockFamily.get(Variants.STRIPPED_LOG).get()).getMapping();
            ResourceLocation logLocation = new ResourceLocation(CreateLostCivilization.MOD_ID, "block/cypress/stripped_log");
            ResourceLocation resourceLocation = ArcaneModelTemplates.SLAB_BOTTOM.create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            ResourceLocation resourceLocation1 = ArcaneModelTemplates.SLAB_TOP.create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, textureMapping, generators.modelOutput);
            generators.blockStateOutput.accept(BlockModelGenerators.createSlab(block, resourceLocation, resourceLocation1, logLocation));
            if(withItem) {
                generators.delegateItemModel(block, resourceLocation);
            }
        }
    }
}
