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
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CivilizationClientVariants {

    public static void loadClass() {
        ClientVariants.addClientBlockVariant(CivilizationVariants.LOG_ROOTS_CORNER, LOG_ROOTS_CORNER);
        ClientVariants.addClientBlockVariant(CivilizationVariants.STRIPPED_LOG_ROOTS_CORNER, ClientVariants.STAIRS);
        ClientVariants.addClientBlockVariant(CivilizationVariants.LOG_ROOTS, LOG_ROOTS);
        ClientVariants.addClientBlockVariant(CivilizationVariants.STRIPPED_LOG_ROOTS, ClientVariants.SLAB);
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
}
