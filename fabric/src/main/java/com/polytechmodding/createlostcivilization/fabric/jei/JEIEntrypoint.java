package com.polytechmodding.createlostcivilization.fabric.jei;

import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import mezz.jei.api.IModPlugin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class JEIEntrypoint implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(CreateLostCivilization.MOD_ID, "");
    }
}
