package thecsdev.itemdespawnflicker.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.entity.EntityRenderer;

@Deprecated
@Mixin(value = EntityRenderer.class)
public abstract class MixinEntityRenderer
{
	/*@Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
	public void onShouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> callback)
	{
		//make sure it is an item entity
		if(!IDFConfig.ENABLED || !(entity instanceof ItemEntity))
			return;
		ItemEntity ie = (ItemEntity)entity;
		int da = MixinItemEntity.getDespawnAge();
		
		//obtain entity age (hint: it is not actually stored in entity.age)
		int age = ie.getItemAge(); //TODO - Note: This is tracked server-side!
		
		//verify age for flickering
		if(age < da - 240) return;
		boolean cancel = false;
		
		//handle flicker
		if(age < da - 160) //STAGE 1 FLICKER
			cancel = age % 6 < 3;
		else if(age < da - 80) //STAGE 2 FLICKER
			cancel = age % 4 < 2;
		else if(age < da) //STAGE 3 FLICKER
			cancel = age % 3 < 1;
		
		//finally, cancel
		if(cancel)
		{
			callback.setReturnValue(false);
			callback.cancel();
		}
	}*/
}