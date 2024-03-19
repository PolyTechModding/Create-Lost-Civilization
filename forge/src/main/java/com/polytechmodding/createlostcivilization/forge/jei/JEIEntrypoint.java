package com.polytechmodding.createlostcivilization.forge.jei;

import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIEntrypoint implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(CreateLostCivilization.MOD_ID, "");
    }
}
