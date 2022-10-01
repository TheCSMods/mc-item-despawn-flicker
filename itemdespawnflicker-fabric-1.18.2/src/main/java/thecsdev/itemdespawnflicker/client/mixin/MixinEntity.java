package thecsdev.itemdespawnflicker.client.mixin;

import org.apache.commons.lang3.time.StopWatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.world.World;
import thecsdev.itemdespawnflicker.config.IDFConfig;
import thecsdev.itemdespawnflicker.mixin.MixinItemEntity;

@Mixin(Entity.class)
public class MixinEntity
{
	// ==================================================
	private static int itemdespawnflicker_da = MixinItemEntity.getDespawnAge();
	private static int itemdespawnflicker_da_a = itemdespawnflicker_da - 240;
	private static int itemdespawnflicker_da_b = itemdespawnflicker_da - 160;
	private static int itemdespawnflicker_da_c = itemdespawnflicker_da - 80;
	// ==================================================
	private ItemEntity itemdespawnflicker_item = null;
	
	/**
	 * I was gonna use age (in ticks) at for handling the rendering at first,
	 * but some mods for some reason choose to modify the way ticking is
	 * handled, throwing this mod completely off-guard and <i>rendering</i> it unusable.<br/>
	 * <br/>
	 * Workaround: Track time instead.
	 */
	private StopWatch itemdespawnflicker_stopwatch;
	// ==================================================
	@Inject(method = "<init>", at = @At("RETURN"))
	public void onInit(EntityType<?> entityType, World world, CallbackInfo callback)
	{
		itemdespawnflicker_item = (((Object)this) instanceof ItemEntity) ? ((ItemEntity)(Object)this) : null;
		if(itemdespawnflicker_item != null)
		{
			itemdespawnflicker_stopwatch = new StopWatch();
			itemdespawnflicker_stopwatch.start();
		}
	}
	// --------------------------------------------------
	@Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
	public void onShouldRender(double cameraX, double cameraY, double cameraZ, CallbackInfoReturnable<Boolean> callback)
	{
		if(itemdespawnflicker_item == null || !IDFConfig.ENABLED) return;
		
		//obtain entity age (hint: it is not actually stored in entity.age)
		int age = itemdespawnflicker_item.getItemAge(); //TODO - Note: This is tracked server-side!
		
		//verify age for flickering
		if(age < itemdespawnflicker_da_a) return;
		boolean cancel = false;
		long time = itemdespawnflicker_stopwatch.getTime();
		
		//handle flicker
		if(age < itemdespawnflicker_da_b) //STAGE 1 FLICKER
			cancel = time % 300 < 150;
		else if(age < itemdespawnflicker_da_c) //STAGE 2 FLICKER
			cancel = time % 200 < 100;
		else if(age < itemdespawnflicker_da) //STAGE 3 FLICKER
			cancel = time % 150 < 50;
		
		//finally, cancel
		if(cancel)
		{
			callback.setReturnValue(false);
			callback.cancel();
		}
	}
	// ==================================================
	@Inject(method = "setRemoved", at = @At("RETURN"))
	public void onSetRemoved(RemovalReason reason, CallbackInfo callback)
	{
		//dispose of the stopwatch once the entity is about to die/despawn
		//(avoids possible memory leaks? idk how stopwatches work. i think so?)
		if(itemdespawnflicker_stopwatch != null)
		{
			itemdespawnflicker_stopwatch.stop();
		}
	}
	// --------------------------------------------------
	@Inject(method = "unsetRemoved", at = @At("RETURN"))
	public void onUnsetRemoved(CallbackInfo callback)
	{
		//should the entity for whatever reason become
		//resurrected, reset and restart the stopwatch
		if(itemdespawnflicker_stopwatch != null)
		{
			itemdespawnflicker_stopwatch.reset();
			itemdespawnflicker_stopwatch.start();
		}
	}
	// ==================================================
}