package com.polytechmodding.createlostcivilization.client;

import com.arcaneengineering.arcanelib.client.ClientBlockVariant;
import com.arcaneengineering.arcanelib.client.ClientVariants;
import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.config.types.BlockVariant;
import com.arcaneengineering.arcanelib.datagen.helpers.ModelGenerators;
import com.arcaneengineering.arcanelib.datagen.models.ArcaneModelTemplate;
import com.arcaneengineering.arcanelib.registry.BlockFamily;
import com.polytechmodding.createlostcivilization.families.BlockFamiliesFactory;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CivilizationClientVariants {

    public static void loadClass() {
        ClientVariants.addClientBlockVariantOverride(Variants.LOG, KELP_LOG, BlockFamiliesFactory.RED_KELP_FAMILY);
    }

    public static KelpLog KELP_LOG = new KelpLog();

    public static class KelpLog implements ClientBlockVariant {
        @Override
        public void modelDefinition(Block block, BlockFamily blockFamily, BlockModelGenerators generators,
                                    BlockVariant<?> blockVariant, TextureMapping baseBlockTextureMapping,
                                    ResourceLocation fullBlock, boolean withItem) {
            TexturedModel logColumn = TexturedModel.createDefault((texturedModel) -> new TextureMapping()
                            .put(TextureSlot.PARTICLE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel,"_stripes", blockFamily))
                            .put(TextureSlot.BOTTOM, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, "_bottom_edge", blockFamily))
                            .put(TextureSlot.TOP, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, "_top_edge", blockFamily))
                            .put(TextureSlot.SIDE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, "_side_edge", blockFamily))
                                    .put(TextureSlot.TEXTURE, ArcaneModelTemplate.getArcaneResourceLocationDefault(texturedModel, blockFamily)),
                            CivilizationModelTemplates.KELP_LOG.getModelTemplate())
                    .get(block);
            ResourceLocation resourceLocation = CivilizationModelTemplates.KELP_LOG
                    .create(ClientVariants.getClientBlockVariant(blockFamily, blockVariant).getRenderType(), blockFamily, block, logColumn.getMapping(), generators.modelOutput);
            var generator = MultiVariantGenerator.multiVariant(block, Variant.variant()
                            .with(VariantProperties.MODEL, resourceLocation))
                    .with(ModelGenerators.createRotatedPillar());
            generators.blockStateOutput.accept(generator);
            if(withItem) {
                generators.delegateItemModel(block, resourceLocation);
            }
        }
    }
}
