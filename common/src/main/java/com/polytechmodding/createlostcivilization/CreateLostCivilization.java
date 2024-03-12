package com.polytechmodding.createlostcivilization;

import com.arcaneengineering.arcanelib.ArcaneLib;
import com.arcaneengineering.arcanelib.context.RegistrationContext;
import com.polytechmodding.createlostcivilization.families.BlockFamiliesFactory;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;

public class CreateLostCivilization
{
	public static final String MOD_ID = "create_lost_civilization";
	public static RegistrationContext CONTEXT;

	public static void init() {
		ArcaneLib arcaneLib = ArcaneLib.getInstance();
		CONTEXT = arcaneLib.apiInit(MOD_ID, new BlockFamiliesFactory(), new ArrayList<>());
	}
}
