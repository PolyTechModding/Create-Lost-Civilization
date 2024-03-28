package com.polytechmodding.createlostcivilization.client;

import com.arcaneengineering.arcanelib.client.ClientVariants;
import com.polytechmodding.createlostcivilization.families.CivilizationVariants;

public class CivilizationClientVariants {

    public static void loadClass() {
        ClientVariants.addClientBlockVariant(CivilizationVariants.LOG_ROOTS_CORNER, ClientVariants.STAIRS);
        ClientVariants.addClientBlockVariant(CivilizationVariants.STRIPPED_LOG_ROOTS_CORNER, ClientVariants.STAIRS);
        ClientVariants.addClientBlockVariant(CivilizationVariants.LOG_ROOTS, ClientVariants.SLAB);
        ClientVariants.addClientBlockVariant(CivilizationVariants.STRIPPED_LOG_ROOTS, ClientVariants.SLAB);
    }
}
