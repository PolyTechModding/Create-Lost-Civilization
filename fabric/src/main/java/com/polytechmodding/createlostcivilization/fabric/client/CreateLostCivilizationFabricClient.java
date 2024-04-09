package com.polytechmodding.createlostcivilization.fabric.client;

import com.polytechmodding.createlostcivilization.world.fluids.FluidRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CreateLostCivilizationFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(
                FluidRegistry.STILL_TOXIC_BARRIER_FLUID.get(), FluidRegistry.FLOWING_TOXIC_BARRIER_FLUID.get(),
                new SimpleFluidRenderHandler(
                new ResourceLocation("minecraft:block/water_still"),
                new ResourceLocation("minecraft:block/water_flow"),
                0x4CC248
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(),
                FluidRegistry.STILL_TOXIC_BARRIER_FLUID.get(), FluidRegistry.FLOWING_TOXIC_BARRIER_FLUID.get());
    }
}
