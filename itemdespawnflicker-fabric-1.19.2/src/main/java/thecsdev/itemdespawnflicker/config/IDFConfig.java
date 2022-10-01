package thecsdev.itemdespawnflicker.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import thecsdev.itemdespawnflicker.IDF;

public final class IDFConfig
{
	// ==================================================
	public static boolean ENABLED = true;
	// ==================================================
	public static void saveProperties()
	{
		try
		{
			//get and make sure the properties file exists
			File fProp = getPropertiesFile();
			if(!fProp.exists())
			{
				fProp.getParentFile().mkdirs();
				fProp.createNewFile();
			}
			
			//create a Properties instance and store the properties
			Properties prop = new Properties();
			prop.setProperty("ENABLED", Boolean.toString(ENABLED));
			
			//save the properties
			FileOutputStream fos = new FileOutputStream(fProp);
			prop.store(fos, IDF.getModID() + " properties");
			fos.close();

			//log
			IDF.LOGGER.info("Saved '" + IDF.getModID() + "' config.");
		}
		catch (IOException ioExc)
		{
			//if saving is unsuccessful, throw a CrashException
			String mid = IDF.getModID();
			throw new CrashException(new CrashReport("Unable to save the '" + mid + "' mod config.", ioExc));
		}
	}
	// --------------------------------------------------
	public static void loadProperties()
	{
		try
		{
			//get and make sure the properties file exists
			File fProp = getPropertiesFile();
			if(!fProp.exists())
			{
				IDF.LOGGER.info("Could not load '" + IDF.getModID() + "' config. File not found.");
				return;
			}
			
			//create a Properties instance and load the properties
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(fProp);
			prop.load(fis);
			fis.close();
			
			//read the properties
			ENABLED = smartBool(prop.getProperty("ENABLED"), true);
		}
		catch(IOException ioExc)
		{
			//if loading is unsuccessful, throw a CrashException
			String mid = IDF.getModID();
			throw new CrashException(new CrashReport("Unable to load the '" + mid + "' mod config.", ioExc));
		}
	}
	// ==================================================
	public static File getPropertiesFile()
	{
		return new File(System.getProperty("user.dir") + "/config/" + IDF.getModID() + ".properties");
	}
	// --------------------------------------------------
	private static boolean smartBool(String arg0, boolean def)
	{
		if(arg0 == null) return def;
		String a = arg0.split(" ")[0].toLowerCase();
		return (a.startsWith("true") || a.startsWith("ye") ||
				a.startsWith("ok") || a.startsWith("sur")) && a.length() <= 5;
	}
	// ==================================================
}