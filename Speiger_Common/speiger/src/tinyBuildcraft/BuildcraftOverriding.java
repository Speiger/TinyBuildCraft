package speiger.src.tinyBuildcraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import speiger.src.api.common.config.APIItems;
import speiger.src.api.common.functions.PathProxy;
import buildcraft.BuildCraftBuilders;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftFactory;
import buildcraft.BuildCraftSilicon;
import buildcraft.BuildCraftTransport;
import buildcraft.core.proxy.CoreProxy;

public class BuildcraftOverriding
{
	
	
	public static void overrideBuildcraft()
	{
		//BuildCraft OreDictionary
		OreDictionary.registerOre("WoodenGear", BuildCraftCore.woodenGearItem);
		OreDictionary.registerOre("CobbleStoneGear", BuildCraftCore.stoneGearItem);
		OreDictionary.registerOre("IronGear", BuildCraftCore.ironGearItem);
		OreDictionary.registerOre("GoldGear", BuildCraftCore.goldGearItem);
		OreDictionary.registerOre("DiamondGear", BuildCraftCore.diamondGearItem);
		
		//Spmod Dictionary
		OreDictionary.registerOre("WoodenGear", APIItems.holzzahnrad);
		OreDictionary.registerOre("CobbleStoneGear", APIItems.pflastersteinzahnrad);
		OreDictionary.registerOre("StoneGear", APIItems.steinzahnrad);
		OreDictionary.registerOre("IronGear", APIItems.eisenzahnrad);
		OreDictionary.registerOre("GoldGear", APIItems.goldzahnrad);
		OreDictionary.registerOre("RedstoneGear", APIItems.redstonezahnrad);
		OreDictionary.registerOre("DiamondGear", APIItems.diamondzahnrad);
		

	}
	
	public static void loadGearRecipes()
	{
		loadWoodenGears();
		loadCobbleGears();
		loadStoneGears();
		loadIronGears();
		loadGoldGears();
		loadDiamondGears();
		overrideRecipe();
	}
	



	
	public static void overrideRecipe() 
	{
		if(BuildCraftBuilders.fillerBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftBuilders.fillerBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftBuilders.fillerBlock, 1), new Object[]{"btb", "ycy", "gCg", 'b', new ItemStack(Item.dyePowder, 1, 0), 't',BuildCraftBuilders.markerBlock, 'y', new ItemStack(Item.dyePowder, 1, 11), 'c', Block.workbench, 'g', "GoldGear", 'C', Block.chest});
		}
		if(BuildCraftBuilders.builderBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftBuilders.builderBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftBuilders.builderBlock, 1), new Object[]{"btb", "ycy", "gCg", 'b', new ItemStack(Item.dyePowder, 1, 0), 't', BuildCraftBuilders.markerBlock, 'y', new ItemStack(Item.dyePowder, 1, 11), 'c', Block.workbench, 'g', "DiamondGear", 'C', Block.chest});

		}
		if(BuildCraftBuilders.architectBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftBuilders.architectBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftBuilders.architectBlock, 1), new Object[]{"btb", "ycy", "gCg", 'b', new ItemStack(Item.dyePowder, 1, 0), 't', BuildCraftBuilders.markerBlock, 'y', new ItemStack(Item.dyePowder, 1, 11), 'c', Block.workbench, 'g', "DiamondGear", 'C',	new ItemStack(BuildCraftBuilders.templateItem, 1)});

		}
		if(BuildCraftEnergy.engineBlock != null)
		{
			PathProxy.removeRecipe(new ItemStack(BuildCraftEnergy.engineBlock, 1, 0), true);
			PathProxy.removeRecipe(new ItemStack(BuildCraftEnergy.engineBlock, 1, 1), true);
			PathProxy.removeRecipe(new ItemStack(BuildCraftEnergy.engineBlock, 1, 2), true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftEnergy.engineBlock, 1, 0), new Object[]{"www", " g ", "GpG", 'w', "plankWood", 'g', Block.glass, 'G', "WoodenGear", 'p', Block.pistonBase});
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftEnergy.engineBlock, 1, 1), new Object[]{"www", " g ", "GpG", 'w', "cobblestone", 'g', Block.glass, 'G', "CobbleStoneGear", 'p', Block.pistonBase});
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftEnergy.engineBlock, 1, 2), new Object[]{"www", " g ", "GpG", 'w', Item.ingotIron, 'g', Block.glass, 'G', "IronGear", 'p', Block.pistonBase});
		}
		if(BuildCraftFactory.allowMining && BuildCraftFactory.miningWellBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.miningWellBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.miningWellBlock, 1), "ipi",	"igi", "iPi", 'p', Item.redstone, 'i', Item.ingotIron, 'g', "IronGear", 'P', Item.pickaxeIron);
		}
		if(!BuildCraftFactory.allowMining && BuildCraftFactory.miningWellBlock == null && BuildCraftFactory.pumpBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.pumpBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.pumpBlock), "iri", "iTi", "gpg", 'r', Item.redstone, 'i', Item.ingotIron, 'T', BuildCraftFactory.tankBlock != null ? BuildCraftFactory.tankBlock : Block.glass, 'g', "IronGear", 'p', BuildCraftTransport.pipeFluidsGold);
		}
		if(BuildCraftFactory.allowMining && BuildCraftFactory.quarryBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.quarryBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.quarryBlock), "ipi", "gig",	"dDd", 'i', "IronGear", 'p', Item.redstone, 'g', "GoldGear", 'd', "DiamondGear", 'D', Item.pickaxeDiamond);
		}
		if(BuildCraftFactory.autoWorkbenchBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.autoWorkbenchBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.autoWorkbenchBlock), " g ", "gwg", " g ", 'w', Block.workbench, 'g', "WoodenGear");
		}
		if(BuildCraftFactory.refineryBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.refineryBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.refineryBlock), "RTR", "TGT", 'T', BuildCraftFactory.tankBlock != null ? BuildCraftFactory.tankBlock : Block.glass, 'G', "DiamondGear", 'R', Block.torchRedstoneActive);
		}
		if(BuildCraftFactory.hopperBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.hopperBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.hopperBlock),
					"ICI",
					"IGI",
					" I ",
					'I', Item.ingotIron,
					'C', Block.chest,
					'G', "CobbleStoneGear");
		}
		if(BuildCraftFactory.floodGateBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftFactory.floodGateBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftFactory.floodGateBlock), "IGI", "FTF", "IFI", 'I', Item.ingotIron, 'T', BuildCraftFactory.tankBlock != null ? BuildCraftFactory.tankBlock : Block.glass, 'G', "IronGear", 'F', new ItemStack(Block.fenceIron));
		}
		
		if(BuildCraftSilicon.assemblyTableBlock != null)
		{
			PathProxy.removeRecipe(BuildCraftSilicon.assemblyTableBlock, true);
			CoreProxy.proxy.addCraftingRecipe(new ItemStack(BuildCraftSilicon.assemblyTableBlock, 1, 0), new Object[]{"ORO", "ODO", "OGO", 'O', Block.obsidian, 'R', Item.redstone, 'D', Item.diamond, 'G', "DiamondGear",});
		}
		
	}

	public static void loadDiamondGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("DiamondGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}
	}
	
	public static void loadRedstoneGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("RedstoneGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}
	}
	
	public static void loadGoldGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("GoldGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}
	}
	
	public static void loadIronGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("IronGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}
	}
	
	public static void loadStoneGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("StoneGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}
	}
	
	
	public static void loadWoodenGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("WoodenGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}
	}
	
	public static void loadCobbleGears()
	{
		List<ItemStack> Gears = OreDictionary.getOres("CobbleStoneGear");
		if(Gears.size() > 1)
		{
			for(int i = 0; i<Gears.size();i++)
			{
				if(i+1 < Gears.size())
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(i+1));
				}
				else
				{
					PathProxy.addTransmutationsRecipe(Gears.get(i), Gears.get(0));
				}
			}
		}

	}
	
	
}
