package com.polytechmodding.createlostcivilization.client;

import com.arcaneengineering.arcanelib.datagen.models.ArcaneModelTemplate;
import com.polytechmodding.createlostcivilization.CreateLostCivilization;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class CivilizationModelTemplates {
    public static final ArcaneModelTemplate VERTICAL_SLAB_ALL =
            CivilizationModelTemplates.create("vertical_slab_all",
                    TextureSlot.ALL);

    private static ArcaneModelTemplate create(String blockModelLocation, TextureSlot ... requiredSlots) {
        return new ArcaneModelTemplate(Optional.of(new ResourceLocation(CreateLostCivilization.MOD_ID,
                "block/" + blockModelLocation)), Optional.empty(), requiredSlots);
    }
}
