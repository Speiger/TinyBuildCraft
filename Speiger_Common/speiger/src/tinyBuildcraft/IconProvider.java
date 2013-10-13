package speiger.src.tinyBuildcraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.api.core.IIconProvider;

public class IconProvider implements IIconProvider 
{
	public Icon pipe;
	private static IconProvider icon = new IconProvider();
	public static IconProvider getIcon()
	{
		return icon;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int iconIndex) 
	{
		return pipe;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) 
	{
		pipe = iconRegister.registerIcon("tinybuildcraft:PipePowerEmerald");
	}

}
