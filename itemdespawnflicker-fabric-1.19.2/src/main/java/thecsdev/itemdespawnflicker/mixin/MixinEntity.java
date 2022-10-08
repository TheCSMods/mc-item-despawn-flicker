package thecsdev.itemdespawnflicker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;

/**
 * I chose not to use this in the end as the
 * MixinServerPlayNetworkHandler already sends out
 * updates frequently enough.<br/>
 * <br/>
 * This one could even end up spamming packets way too
 * frequently if there's a command block modifying an
 * entity's age, causing a lot of lag and bandwidth waste. 
 */
@Mixin(Entity.class)
public abstract class MixinEntity
{
	// ==================================================
	private static final int itemdespawnflicker_wait = 4000;
	// --------------------------------------------------
	private long itemdespawnflicker_lastUpdateTime = System.currentTimeMillis() - itemdespawnflicker_wait - 1;
	private boolean itemdespawnflicker_needsUpdate = false;
	// ==================================================
	/**
	 * When an entity's NBT gets updated by a command block or
	 * anything else, the age will be broadcasted. This will help
	 * resolve bugs where changing an entity's age won't let the
	 * client know about the change.
	 */
	@Inject(method = "readNbt", at = @At("RETURN"))
	public void onReadNbt(NbtCompound nbt, CallbackInfo callback)
	{
		//control the update frequency
		long curr = System.currentTimeMillis();
		if(curr - itemdespawnflicker_lastUpdateTime < itemdespawnflicker_wait)
		{
			itemdespawnflicker_needsUpdate = true;
			return;
		}
		
		//update
		itemdespawnflicker_updateClients(curr);
	}
	// --------------------------------------------------
	/**
	 * Ticking will be used to send age updates.
	 */
	@Inject(method = "baseTick", at = @At("RETURN"))
	public void onBaseTick(CallbackInfo callback)
	{
		if(itemdespawnflicker_needsUpdate)
		{
			//control the update frequency
			long curr = System.currentTimeMillis();
			if(curr - itemdespawnflicker_lastUpdateTime < itemdespawnflicker_wait)
				return;
			
			//update
			itemdespawnflicker_updateClients(curr);
		}
	}
	// ==================================================
	private void itemdespawnflicker_updateClients(long currentTime)
	{
		//get entity
		Entity thisEntity = (Entity)(Object)this;
		
		//update clients
		if(thisEntity instanceof ItemEntity) //only do this for item entities
			thecsdev.itemdespawnflicker.server.network.ServerPlayNetworkHandler.updateClientsEntityAge(thisEntity);
		itemdespawnflicker_lastUpdateTime = currentTime;
		itemdespawnflicker_needsUpdate = false;
	}
	// ==================================================
}
