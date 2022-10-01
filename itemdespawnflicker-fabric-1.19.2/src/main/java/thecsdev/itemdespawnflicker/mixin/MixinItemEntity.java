package thecsdev.itemdespawnflicker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.ItemEntity;

/**
 * This is an "Accessor Mixin".<br/>
 * Only {@link Accessor}s and {@link Invoker}s are allowed here.<br/>
 * <b>Anything else will result in a crash!<b/>
 */
@Mixin(ItemEntity.class)
public interface MixinItemEntity
{
	@Accessor(value = "DESPAWN_AGE")
	public static int getDespawnAge() { throw new RuntimeException("Failed to apply Mixin on ItemEntity.class"); }
	
	@Accessor(value = "NEVER_DESPAWN_AGE")
	public static int getNeverDespawnAge() { throw new RuntimeException("Failed to apply Mixin on ItemEntity.class"); }
	
	@Accessor(value = "itemAge")
	public void setItemAge(int age);
}