package thecsdev.itemdespawnflicker.client.thirdParties;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import thecsdev.itemdespawnflicker.client.gui.screen.IDFConfigScreen;

public class IDFModMenu implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory()
	{
		return parent -> new IDFConfigScreen(parent);
	}
}