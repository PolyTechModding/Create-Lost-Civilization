package com.polytechmodding.createlostcivilization.world.fluids;

import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class FluidRegistry {

    private static final DeferredRegister<Fluid> fluidRegistry =
            DeferredRegister.create(CreateLostCivilization.MOD_ID, Registries.FLUID);

    public static Supplier<? extends Fluid> STILL_TOXIC_BARRIER_FLUID =
            fluidRegistry.register("still_toxic_barrier_fluid", ToxicBarrierFluid.Still::new);
    public static Supplier<? extends Fluid> FLOWING_TOXIC_BARRIER_FLUID =
            fluidRegistry.register("flowing_toxic_barrier_fluid", ToxicBarrierFluid.Flowing::new);

    public static void register() {
        fluidRegistry.register();
    }
}
