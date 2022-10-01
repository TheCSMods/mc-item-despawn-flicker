package thecsdev.itemdespawnflicker.network;

import net.minecraft.util.Identifier;
import static thecsdev.itemdespawnflicker.IDF.getModID;

public final class PlayNetworkChannels
{
	// ==================================================
	private PlayNetworkChannels() {}
	// ==================================================
	/**
	 * <b>Note:</b><br/>
	 * For security reasons, this mod may choose to only broadcast
	 * ages of item entities. There likely is a reason why clients
	 * can't know what a given entity's true age is.<br/>
	 * <br/>
	 * Maybe that would enable cheating or something?
	 * Whatever the reason may be, it is probably a good one.
	 */
	public static final Identifier PNC_ENTITY_AGE = new Identifier(getModID(), "entity_age");
	// ==================================================
}