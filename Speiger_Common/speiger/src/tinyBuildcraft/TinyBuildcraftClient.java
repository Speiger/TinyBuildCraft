package speiger.src.tinyBuildcraft;

import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.BuildCraftTransport;
import buildcraft.transport.TransportProxyClient;

public class TinyBuildcraftClient extends TinyBuildcraftCore
{
	@Override
	public void render()
	{
		MinecraftForgeClient.registerItemRenderer(TinyBuildcraftAddon.advWoodenPipe.itemID, TransportProxyClient.pipeItemRenderer);
		MinecraftForgeClient.registerItemRenderer(TinyBuildcraftAddon.ironPowerPipe.itemID, TransportProxyClient.pipeItemRenderer);
	}
}
