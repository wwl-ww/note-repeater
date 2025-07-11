package chiww6.noterepeater;

import chiww6.noterepeater.registry.ModBlocks;
import chiww6.noterepeater.registry.ModCommands;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteRepeaterMod implements ModInitializer {
	public static final String MOD_ID = "note_repeater";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// 注册方块和命令
		ModBlocks.register();
		ModCommands.registerModCommands();
		LOGGER.info("Hello Fabric world!");
	}
}