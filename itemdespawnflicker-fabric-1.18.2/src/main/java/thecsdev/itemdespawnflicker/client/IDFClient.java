package thecsdev.itemdespawnflicker.client;

import net.fabricmc.api.ClientModInitializer;
import thecsdev.itemdespawnflicker.IDF;
import thecsdev.itemdespawnflicker.client.network.ClientPlayNetworkHandler;

public final class IDFClient extends IDF implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ClientPlayNetworkHandler.registerEventHandlers();
	}
}