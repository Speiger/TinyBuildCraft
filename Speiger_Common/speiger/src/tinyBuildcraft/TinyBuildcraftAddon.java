package speiger.src.tinyBuildcraft;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import speiger.src.api.common.config.APIItems;
import speiger.src.api.common.functions.PathProxy;
import buildcraft.BuildCraftTransport;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TransportProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = "tinybuildcraft", name = "Buildcraft & Tiny Chest Addon", version = "1.0.0", dependencies = "required-after:TinyChest;required-after:BuildCraft|Transport")
public class TinyBuildcraftAddon
{
	@SidedProxy(clientSide = "speiger.src.tinyBuildcraft.TinyBuildcraftClient", serverSide = "speiger.src.tinyBuildcraft.TinyBuildcraftCore")
	public static TinyBuildcraftCore core;
	
	public static Configuration config;
	public static final String items = "Items";
	public static final Logger tinyBCLog = Logger.getLogger("TinyChest&Buildcraft");
	public static Item advWoodenPipe = null;
	@EventHandler
    public void preInit(FMLPreInitializationEvent par1)
	{
		config = new Configuration(new File(par1.getModConfigurationDirectory(), "Spmod/TinyChestAddon.cfg"));
		try 
		{
			
			PipeTransportPower.powerCapacities.put(AdvancedWoodenPipe.class, 1024);
			advWoodenPipe = BuildItem(29744, AdvancedWoodenPipe.class, "Emerald Conductive Pipe");
			PathProxy.addSRecipe(new ItemStack(advWoodenPipe), new Object[]{BuildCraftTransport.pipeItemsEmerald, APIItems.logicDiamant});
			PathProxy.addSRecipe(new ItemStack(BuildCraftTransport.pipeItemsEmerald), new Object[]{advWoodenPipe});
		}
		catch (Exception e) 
		{
			tinyBCLog.info("Could Not Load Config File");
		}
		finally
		{
			config.save();
		}
    }
	

	
	
	@EventHandler
	public void load(FMLInitializationEvent evt)
	{
		core.render();
		BuildcraftOverriding.overrideBuildcraft();
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{
		BuildcraftOverriding.loadGearRecipes();
	}
	
	public Item BuildItem(int defaultID, Class<? extends Pipe> clas, String descr)
	{
		ItemPipe res = new ItemPipe(config.get(items, "Emerald Conductive Pipe", defaultID).getInt(defaultID)){

			@Override
			public String getItemDisplayName(ItemStack itemstack) 
			{
				return "Emerald Conductive Pipe";
			}
			
		};
		PathProxy.registItem(res, "EP");
		BlockGenericPipe.pipes.put(res.itemID, clas);
		Pipe dummyPipe = BlockGenericPipe.createPipe(res.itemID);
		if (dummyPipe != null) {
			res.setPipeIconIndex(dummyPipe.getIconIndexForItem());
			TransportProxy.proxy.setIconProviderFromPipe(res, dummyPipe);
		}

		return res;
	}
}
