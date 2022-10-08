package thecsdev.itemdespawnflicker.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import thecsdev.itemdespawnflicker.config.IDFConfig;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler
{
	/**
	 * This will broadcast an entity's age right as it "spawns"
	 * aka loads in a client's client world. That way the clients
	 * will always know the age of the given entities.
	 */
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("RETURN"))
	public void onSendPacket(
			Packet<?> packet,
			@Nullable PacketCallbacks callbacks,
			CallbackInfo callback)
	{
		if(!IDFConfig.ENABLED) return;
		
		//check if it's an entity spawn packet
		if(packet instanceof EntitySpawnS2CPacket)
		{
			//cast objects
			ServerPlayNetworkHandler spnh = (ServerPlayNetworkHandler)(Object)this;
			EntitySpawnS2CPacket esp = (EntitySpawnS2CPacket)packet;
			
			//get the entity in question
			Entity entity = spnh.getPlayer().getWorld().getEntityById(esp.getId());
			
			//update clients on the entity's age
			if(entity instanceof ItemEntity) //only do this for item entities
				thecsdev.itemdespawnflicker.server.network.ServerPlayNetworkHandler.updateClientsEntityAge(entity);
		}
		//check if it's an entity velocity update packet
		//(this one is to really make sure it goes thru, because sometimes the previous one fails)
		//(Note: no longer needed for now. the NBT tracker is sufficient enough, plus avoid packet spam)
		/*else if(packet instanceof EntityVelocityUpdateS2CPacket)
		{
			//cast objects
			ServerPlayNetworkHandler spnh = (ServerPlayNetworkHandler)(Object)this;
			EntityVelocityUpdateS2CPacket evu = (EntityVelocityUpdateS2CPacket)packet;
			
			//get the entity in question
			Entity entity = spnh.getPlayer().getWorld().getEntityById(evu.getId());
			
			//update clients on the entity's age
			if(entity instanceof ItemEntity) //only do this for item entities
				thecsdev.itemdespawnflicker.server.network.ServerPlayNetworkHandler.updateClientsEntityAge(entity);
		}*/
	}
}