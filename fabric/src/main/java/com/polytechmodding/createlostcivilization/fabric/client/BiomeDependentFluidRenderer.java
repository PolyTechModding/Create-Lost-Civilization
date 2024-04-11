package com.polytechmodding.createlostcivilization.fabric.client;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BiomeDependentFluidRenderer implements FluidRenderHandler {

    private final TextureAtlasSprite[] sprites = new TextureAtlasSprite[3];

    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final int defaultTint;

    public BiomeDependentFluidRenderer(ResourceLocation stillTexture,
                                       ResourceLocation flowingTexture,
                                       ResourceLocation overlayTexture,
                                       int defaultTint) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.defaultTint = defaultTint;
    }

    @Override
    public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
        return sprites;
    }

    @Override
    public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
        if (view != null && pos != null) {
            return BiomeColors.getAverageWaterColor(view, pos);
        } else {
            return defaultTint;
        }
    }

    @Override
    public void reloadTextures(@NotNull TextureAtlas textureAtlas) {
        sprites[0] = textureAtlas.getSprite(stillTexture);
        sprites[1] = textureAtlas.getSprite(flowingTexture);
        sprites[2] = textureAtlas.getSprite(overlayTexture);
    }
}
