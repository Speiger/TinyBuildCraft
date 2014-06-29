package speiger.src.tinyBuildcraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.api.core.IIconProvider;

public class IconProvider implements IIconProvider 
{
	public Icon pipe;
	public Icon ironPipe;
	public Icon ironPipeClosed;
	private static IconProvider icon = new IconProvider();
	public static IconProvider getIcon()
	{
		return icon;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i) 
	{
		if(i == 0)
		{
			return pipe;
		}
		if(i == 2)
		{
			return ironPipeClosed;
		}
		return ironPipe;
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) 
	{
		pipe = iconRegister.registerIcon("tinybuildcraft:PipePowerEmerald");
		ironPipe = iconRegister.registerIcon("tinybuildcraft:pipeIronAllOpen");
		ironPipeClosed = iconRegister.registerIcon("tinybuildcraft:pipeIronAllClosed");
	}

}
