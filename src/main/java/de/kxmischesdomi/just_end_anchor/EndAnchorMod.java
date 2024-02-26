package de.kxmischesdomi.just_end_anchor;

import de.kxmischesdomi.just_end_anchor.common.registry.ModBlockEntities;
import de.kxmischesdomi.just_end_anchor.common.registry.ModItems;
import de.kxmischesdomi.just_end_anchor.loot_table.CompassTypeLootFunction;
import de.kxmischesdomi.just_end_anchor.recipes.EndAnchorRecipe;
import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class EndAnchorMod implements ModInitializer {

	public static final String MOD_ID = "just_end_anchor";

	public  static  final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean respawnAfterCredits = false;

	@Override
	public void onInitialize() {
		ModItems.init();
		ModBlockEntities.init();
		EndAnchorRecipe.init();
		CompassTypeLootFunction.init();

	}

}
