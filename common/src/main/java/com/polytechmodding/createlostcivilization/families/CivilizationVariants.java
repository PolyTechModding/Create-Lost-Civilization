package com.polytechmodding.createlostcivilization.families;

import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.config.attributes.FlammableVariant;
import com.arcaneengineering.arcanelib.config.attributes.StrippableVariant;
import com.arcaneengineering.arcanelib.config.attributes.StrippedVariant;
import com.arcaneengineering.arcanelib.config.types.BlockVariant;
import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.arcaneengineering.arcanelib.registry.BlockFamily;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;
import java.util.function.Supplier;

public class CivilizationVariants {

    public static LogRootsCorner LOG_ROOTS_CORNER = new LogRootsCorner();

    public static class LogRootsCorner implements BlockVariant<WoodType>, FlammableVariant, StrippableVariant {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_roots_corner";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(RegistrationContext context, WoodType blockSetType, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, Supplier<? extends Block> baseBlock) {
            return Variants.STAIRS.getBlockSupplier(context, blockSetType.setType(), dimension, particleOption, color, family, baseBlock);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return Variants.STAIRS.getItemSupplier(blockSetType.setType(), associatedBlock, dimension, family, context);
        }

        @Override
        public List<TagKey<Block>> getAssociatedBlockTag(RegistrationContext context) {
            return Variants.STAIRS.getAssociatedBlockTag(context);
        }

        @Override
        public LootTable.Builder getLootTable(RegistrationContext context, BlockFamily blockFamily, Supplier<? extends Item> associatedItem) {
            return Variants.STAIRS.getLootTable(context, blockFamily, associatedItem);
        }

        @Override
        public int getBurnChance() {
            return Variants.STAIRS.getBurnChance();
        }

        @Override
        public int getSpreadChance() {
            return Variants.STAIRS.getSpreadChance();
        }

        @Override
        public BlockVariant<WoodType> getStrippedVariant(ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return STRIPPED_ROOTS_CORNER;
        }

        @Override
        public Supplier<? extends Block> getStrippedBlock(ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return family.get(STRIPPED_ROOTS_CORNER);
        }
    }

    public static StrippedRootsCorner STRIPPED_ROOTS_CORNER = new StrippedRootsCorner();

    public static class StrippedRootsCorner implements BlockVariant<WoodType>, FlammableVariant,StrippedVariant {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "stripped_";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_roots_corner";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(RegistrationContext context, WoodType blockSetType, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, Supplier<? extends Block> baseBlock) {
            return Variants.STAIRS.getBlockSupplier(context, blockSetType.setType(), dimension, particleOption, color, family, baseBlock);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return Variants.STAIRS.getItemSupplier(blockSetType.setType(), associatedBlock, dimension, family, context);
        }

        @Override
        public List<TagKey<Block>> getAssociatedBlockTag(RegistrationContext context) {
            return Variants.STAIRS.getAssociatedBlockTag(context);
        }

        @Override
        public LootTable.Builder getLootTable(RegistrationContext context, BlockFamily blockFamily, Supplier<? extends Item> associatedItem) {
            return Variants.STAIRS.getLootTable(context, blockFamily, associatedItem);
        }

        @Override
        public int getBurnChance() {
            return Variants.STAIRS.getBurnChance();
        }

        @Override
        public int getSpreadChance() {
            return Variants.STAIRS.getSpreadChance();
        }

        @Override
        public BlockVariant<WoodType> getStrippableVariant(ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return LOG_ROOTS_CORNER;
        }
    }
}
