package thecsdev.itemdespawnflicker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import thecsdev.itemdespawnflicker.config.IDFConfig;

public class IDF extends Object
{
	// ==================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(getModID());
	// --------------------------------------------------
	private static String ModName = "null";
	private static final String ModID = "itemdespawnflicker";
	private static IDF Instance;
	// --------------------------------------------------
	public final ModContainer modInfo;
	// ==================================================
	/**
	 * Initializes this mod. This action may only be performed by the fabric-loader.
	 */
	public IDF()
	{
		//validate instance first
		if(isModInitialized())
			throw new IllegalStateException(getModID() + " has already been initialized.");
		else if(!isInstanceValid(this))
			throw new UnsupportedOperationException("Invalid " + getModID() + " type: " + this.getClass().getName());
		
		//assign instance
		Instance = this;
		modInfo = FabricLoader.getInstance().getModContainer(getModID()).get();
		ModName = modInfo.getMetadata().getName();
		
		//log stuff
		LOGGER.info("Initializing '" + getModName() + "' as '" + getClass().getSimpleName() + "'.");
		
		//load properties
		IDFConfig.loadProperties();
	}
	// --------------------------------------------------
	/** Returns the Fabric {@link ModContainer} containing information about this mod. */
	public ModContainer getModInfo() { return modInfo; }
	// ==================================================
	/** Returns the instance of this mod. */
	public static IDF getInstance() { return Instance; }
	// --------------------------------------------------
	public static String getModName() { return ModName; }
	public static String getModID() { return ModID; }
	// --------------------------------------------------
	public static boolean isModInitialized() { return isInstanceValid(Instance); }
	private static boolean isInstanceValid(IDF instance) { return isServer(instance) || isClient(instance); }
	// --------------------------------------------------
	public static boolean isServer() { return isServer(Instance); }
	public static boolean isClient() { return isClient(Instance); }
	
	private static boolean isServer(IDF arg0) { return arg0 instanceof thecsdev.itemdespawnflicker.server.IDFServer; }
	private static boolean isClient(IDF arg0) { return arg0 instanceof thecsdev.itemdespawnflicker.client.IDFClient; }
	// ==================================================
	public static Text lt(String text) { return new LiteralText(text); }
	public static Text tt(String key)
	{
		MutableText txt = new TranslatableText(key);
		if(txt.getString().startsWith("ref="))
			txt = new TranslatableText(txt.getString().substring(4));
		return txt;
	}
	// ==================================================
}