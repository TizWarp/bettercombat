package dev.tizwarp.immersivecombat.client;

import dev.tizwarp.immersivecombat.util.Reference;
import dev.tizwarp.immersivecombat.util.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig
{
	public ModGuiConfig( GuiScreen guiScreen )
	{
		super(guiScreen, new ConfigElement(ConfigurationHandler.config.getCategory("general")).getChildElements(), Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
	}
}