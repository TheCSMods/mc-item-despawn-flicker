package thecsdev.itemdespawnflicker.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import thecsdev.itemdespawnflicker.IDF;
import thecsdev.itemdespawnflicker.server.network.ServerPlayNetworkHandler;

public final class IDFServer extends IDF implements DedicatedServerModInitializer
{
	@Override
	public void onInitializeServer()
	{
		ServerPlayNetworkHandler.registerEventHandlers();
	}
}