package speiger.src.tinyBuildcraft;

import net.minecraft.item.ItemStack;
import buildcraft.transport.ItemPipe;

public class ItemAdvPipe extends ItemPipe 
{

	public ItemAdvPipe(int i) 
	{
		super(i);
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return "Test";
	}
	
	

}
