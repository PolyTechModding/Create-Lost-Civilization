package com.polytechmodding.createlostcivilization.families;

import com.arcaneengineering.arcanelib.config.Variants;
import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.arcaneengineering.arcanelib.registry.BlockFamilies;
import com.arcaneengineering.arcanelib.registry.BlockFamily;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.material.MapColor;

public final class BlockFamiliesFactory
    implements BlockFamilies.BlockFamiliesFactory {
  @Override
  public BlockFamilies create(RegistrationContext registrationContext) {
    return new BlockFamilies(registrationContext);
  }
}
