package speiger.src.tinyBuildcraft;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import speiger.src.api.common.config.APIItems;
import speiger.src.api.common.functions.PathProxy;
import buildcraft.BuildCraftTransport;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TransportProxy;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@Mod(modid = "tinybuildcraft", name = "Buildcraft & Tiny Chest Addon", version = "1.0.0", dependencies = "required-after:tinychest;required-after:BuildCraft|Transport")
public class TinyBuildcraftAddon
{
	@SidedProxy(clientSide = "speiger.src.tinyBuildcraft.TinyBuildcraftClient", serverSide = "speiger.src.tinyBuildcraft.TinyBuildcraftCore")
	public static TinyBuildcraftCore core;
	
	public static Configuration config;
	public static final String items = "Items";
	public static final Logger tinyBCLog = Logger.getLogger("TinyChest&Buildcraft");
	public static Item ironPowerPipe = null;
	public static Item advWoodenPipe;
	@EventHandler
    public void preInit(FMLPreInitializationEvent par1)
	{
		config = new Configuration(new File(par1.getModConfigurationDirectory(), "Spmod/TinyChestAddon.cfg"));
		try 
		{
			PipeTransportPower.powerCapacities.put(AdvancedWoodenPipe.class, 1024);
			advWoodenPipe = BuildCraftTransport.buildPipe(29744, AdvancedWoodenPipe.class, "Emerald Conductive Pipe");
			PathProxy.addSRecipe(new ItemStack(advWoodenPipe), new Object[]{BuildCraftTransport.pipeItemsEmerald, APIItems.logicDiamant});
			PathProxy.addSRecipe(new ItemStack(BuildCraftTransport.pipeItemsEmerald), new Object[]{advWoodenPipe});
			PipeTransportPower.powerCapacities.put(IronPowerPipe.class, 512);
			ironPowerPipe = BuildCraftTransport.buildPipe(29745, IronPowerPipe.class, "Iron Power Pipe");
			PathProxy.addSRecipe(new ItemStack(ironPowerPipe), new Object[]{BuildCraftTransport.pipePowerIron, Item.redstone});
			PathProxy.addSRecipe(new ItemStack(BuildCraftTransport.pipePowerIron), new Object[]{ironPowerPipe});
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
	
	public Item BuildItem(int defaultID, Class<? extends Pipe> clas, final String descr)
	{
		try
		{
			ItemPipe res = new ItemPipe(config.get(items, descr, defaultID).getInt(defaultID)){

				@Override
				public String getItemDisplayName(ItemStack itemstack) 
				{
					return descr;
				}
				
			};
			PathProxy.registItem(res, clas.getName().toLowerCase());
			BlockGenericPipe.pipes.put(res.itemID, clas);
			Pipe dummyPipe = BlockGenericPipe.createPipe(res.itemID);
			if (dummyPipe != null) {
				res.setPipeIconIndex(dummyPipe.getIconIndexForItem());
				TransportProxy.proxy.setIconProviderFromPipe(res, dummyPipe);
			}

			return res;
		}
		catch (Exception e)
		{
			FMLLog.getLogger().info("Could not install Pipe because: "+e.getLocalizedMessage());
			return null;
		}
	}
}
