package thecsdev.itemdespawnflicker.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.Entity;

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
	/**
	 * When an entity's NBT gets updated by a command block or
	 * anything else, the age will be broadcasted. This will help
	 * resolve bugs where changing an entity's age won't let the
	 * client know about the change.
	 */
	/*@Inject(method = "readNbt", at = @At("RETURN"))
	public void onReadNbt(NbtCompound nbt, CallbackInfo callback)
	{
		//get entity
		Entity thisEntity = (Entity)(Object)this;
		
		//update clients on the entity's age
		if(thisEntity instanceof ItemEntity) //only do this for item entities
			thecsdev.itemdespawnflicker.server.network.ServerPlayNetworkHandler.updateClientsEntityAge(thisEntity);
	}*/
}
