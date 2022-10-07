package thecsdev.itemdespawnflicker.client.network;

import static thecsdev.itemdespawnflicker.network.PlayNetworkChannels.PNC_ENTITY_AGE;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import thecsdev.itemdespawnflicker.mixin.MixinItemEntity;
import thecsdev.itemdespawnflicker.util.Vector2;

public final class ClientPlayNetworkHandler
{
	// ==================================================
	private static boolean registered = false;
	// --------------------------------------------------
	/**
	 * KEY - Entity ID - The integer ID of the target entity.<br/>
	 * VALUE X - Entity Age - The target entity's age.<br/>
	 * VALUE Y - Timeout - To avoid memory leaks and the server sending invalid IDs.
	 */
	private static final HashMap<Integer, Vector2> ENTITY_AGE_QUEUE = Maps.newHashMap();
	// ==================================================
	private ClientPlayNetworkHandler() {}
	// ==================================================
	public static void registerEventHandlers()
	{
		//flag
		if(registered) return;
		registered = true;
		
		//handle ENTITY_AGE_QUEUE
		ClientTickEvents.END_WORLD_TICK.register(clientWorld ->
		{
			//i don't trust Fabric API here, so do a null check
			if(clientWorld == null || ENTITY_AGE_QUEUE.size() == 0)
				return;
			
			//iterate all items in the QUEUE
			Iterator<Entry<Integer, Vector2>> entityAgeQueueIterator = ENTITY_AGE_QUEUE.entrySet().iterator();
			while(entityAgeQueueIterator.hasNext())
			{
				//get the next item in the QUEUE
				Entry<Integer, Vector2> eaqEntry = entityAgeQueueIterator.next();
				Vector2 eaqEntryV = eaqEntry.getValue();
				
				//handle the timeout
				if(eaqEntryV.y > 0) eaqEntryV.y--; //tick by tick, reduce the timeout
				else
				{
					//timed out, remove it
					try { entityAgeQueueIterator.remove(); }
					catch(ConcurrentModificationException cme) { /*(2) you too, just for good measure*/ }
					return;
				}
				
				//get entity id and age
				int entityId = eaqEntry.getKey();
				int entityAge = eaqEntryV.x;
				
				//get entity
				Entity entity = clientWorld.getEntityById(entityId);
				if(entity == null) return; //timeout will decrease as this returns
				
				//update item age
				if(!(entity instanceof ItemEntity)) entity.age = entityAge;
				else ((MixinItemEntity)entity).setItemAge(entityAge);
				
				//done. now remove it
				eaqEntryV.y = -1; //time it out, just in case the removal fails
				try { entityAgeQueueIterator.remove(); }
				catch(ConcurrentModificationException cme) { /*(1) ah no you don't, not anymore*/ }
			}
		});
		
		//handle PNC_ENTITY_AGE packets
		ClientPlayNetworking.registerGlobalReceiver(PNC_ENTITY_AGE, (client, netHandler, packetBuf, sender) ->
		{
			try
			{
				//Fabric, STOP broadcasting events while the WORLD is NULL.
				//because apparently this is a thing:
				if(netHandler.getWorld() == null) return;
				
				//read data
				int entityId = packetBuf.readInt();
				int entityAge = packetBuf.readInt();
				
				//add to QUEUE (because the entity doesn't exist yet,
				//it will in a few ticks) (100 ticks for the timeout sounds good enough)
				/** See {@link #ENTITY_AGE_QUEUE} */
				ENTITY_AGE_QUEUE.put(entityId, new Vector2(entityAge, 100));
			}
			catch(Exception exc)
			{
				//commented to avoid spam
				//LOGGER.error("The server sent an invalid message for packet ID: " + PNC_ENTITY_AGE);
			}
		});
	}
	// ==================================================
}