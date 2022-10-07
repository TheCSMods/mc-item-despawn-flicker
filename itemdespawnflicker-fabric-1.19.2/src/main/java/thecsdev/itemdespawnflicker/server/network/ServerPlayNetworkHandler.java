package thecsdev.itemdespawnflicker.server.network;

import static thecsdev.itemdespawnflicker.network.PlayNetworkChannels.PNC_ENTITY_AGE;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public final class ServerPlayNetworkHandler
{
	// ==================================================
	private static boolean registered = false;
	// ==================================================
	private ServerPlayNetworkHandler() {}
	// ==================================================
	public static void registerEventHandlers()
	{
		//flag
		if(registered) return;
		registered = true;
		
	}
	// ==================================================
	/**
	 * Lets all the "in-range" clients know what a given entity's
	 * age value is. "In-range" clients are clients that currently
	 * have the given entity loaded in their client worlds.
	 * @param entity The entity whose age will be broadcasted.
	 */
	@SuppressWarnings("resource") //some static Closeables are referenced
	public static boolean updateClientsEntityAge(Entity entity)
	{
		//obtain essential things
		if(entity == null) return false;
		MinecraftServer server = entity.getServer();
		if(server == null) return false; //return if on client for some reason
		ServerWorld world = (entity.world instanceof ServerWorld) ? (ServerWorld)entity.world : null;
		if(world == null) return false;
		
		//obtain id and age
		int entityId = entity.getId();
		int entityAge = (entity instanceof ItemEntity) ? ((ItemEntity)entity).getItemAge() : entity.age;
		
		//create the entity age packet
		PacketByteBuf eaPacket = new PacketByteBuf(Unpooled.buffer());
		eaPacket.writeInt(entityId);
		eaPacket.writeInt(entityAge);
		
		//----- ----- send the packet to players in range
		CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(PNC_ENTITY_AGE, eaPacket);
		
		//the attempt below doesn't work: TODO - MAKE IT WORK
		//world.getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(entity, packet);
		
		//so i am gonna attempt a different way Minecraft uses.
		//less practical, but it does the trick
		for(ServerPlayerEntity sPlayer : world.getPlayers())
		{
			//skip players that are too far away
			//(yes, even Minecraft uses 512 for some reason)
			//(but then again 32 render distance is 512 blocks)
			if(sPlayer.distanceTo(entity) > 512) //no way someone has a higher entity render distance lol
				continue; //i hate this approach, but got nothing else for now
			
			//send packet
			sPlayer.networkHandler.sendPacket(packet);
		}
		
		//return
		return true;
	}
	// ==================================================
}