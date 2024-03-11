package com.polytechmodding.createlostcivilization.families;

import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.arcaneengineering.arcanelib.registry.BlockFamilies;

public final class BlockFamiliesFactory implements BlockFamilies.BlockFamiliesFactory {
    @Override
    public BlockFamilies create(RegistrationContext registrationContext) {
        return new BlockFamilies(registrationContext);
    }
}
