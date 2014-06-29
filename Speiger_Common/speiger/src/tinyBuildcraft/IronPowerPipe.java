package speiger.src.tinyBuildcraft;

import java.util.LinkedList;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.BuildCraftTransport;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.IAction;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.transport.IPipeTransportPowerHook;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipeLogicIron;
import buildcraft.transport.pipes.PipePowerWood;
import buildcraft.transport.triggers.ActionPipeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IronPowerPipe extends Pipe<PipeTransportPower> implements IPipeTransportPowerHook{

	
	public IronPowerPipe(int itemID) 
	{
		super(new PipeTransportPower(), itemID);
		this.transport.maxPower = 1024;
	}
	
	
	
	private PipeLogicIron logic = new PipeLogicIron(this){

		@Override
		protected boolean isValidConnectingTile(TileEntity tile) 
		{			
			if (tile instanceof TileGenericPipe) 
			{
				Pipe otherPipe = ((TileGenericPipe) tile).pipe;
				if(otherPipe instanceof PipePowerWood)
				{
					return false;
				}
				else if(otherPipe.transport instanceof PipeTransportPower)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(tile instanceof IPowerReceptor)
			{
				IPowerReceptor machine = (IPowerReceptor)tile;
				if(machine.getPowerReceiver(ForgeDirection.UNKNOWN) != null)
				{
					return true;
				}
			}
			return false;

		}
		
	};
	


	

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider() 
	{
		return IconProvider.getIcon();
	}


	
	@Override
	public int getIconIndex(ForgeDirection direction) {
		if (direction == ForgeDirection.UNKNOWN)
			return 2;
		else {
			int metadata = container.getBlockMetadata();

			if (metadata != direction.ordinal())
				return 2;
			else
				return 1;
		}
	}
	

	@Override
	public boolean blockActivated(EntityPlayer entityplayer) {
		return logic.blockActivated(entityplayer);
	}

	@Override
	public void onNeighborBlockChange(int blockId) {
		logic.switchOnRedstone();
		super.onNeighborBlockChange(blockId);
	}

	@Override
	public void onBlockPlaced() {
		logic.onBlockPlaced();
		super.onBlockPlaced();
	}

	@Override
	public void initialize() {
		logic.initialize();
		super.initialize();
	}

	@Override
	public boolean outputOpen(ForgeDirection to) {
		return logic.outputOpen(to);
	}
	
	@Override
	public boolean canConnectRedstone() {
		return true;
	}
	
	@Override
	protected void actionsActivated(Map<IAction, Boolean> actions) {
		super.actionsActivated(actions);

		for (Map.Entry<IAction, Boolean> action : actions.entrySet()) {
			if (action.getKey() instanceof ActionPipeDirection && action.getValue() != null && action.getValue()) {
				logic.setFacing(((ActionPipeDirection) action.getKey()).direction);
				break;
			}
		}
	}
	
	@Override
	public LinkedList<IAction> getActions() {
		LinkedList<IAction> action = super.getActions();
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			if (container.isPipeConnected(direction))
				action.add(BuildCraftTransport.actionPipeDirection[direction.ordinal()]);
		}
		return action;
	}

	@Override
	public float receiveEnergy(ForgeDirection from, float val)
	{
		int meta = this.container.getBlockMetadata();
		
		if((meta != from.ordinal()) && val > 0.0D)
		{
			((PipeTransportPower)this.transport).internalNextPower[from.ordinal()] += val;
		    if (((PipeTransportPower)this.transport).internalNextPower[from.ordinal()] > ((PipeTransportPower)this.transport).maxPower) 
		    {
		    	val = ((PipeTransportPower)this.transport).internalNextPower[from.ordinal()] - ((PipeTransportPower)this.transport).maxPower;
		        ((PipeTransportPower)this.transport).internalNextPower[from.ordinal()] = ((PipeTransportPower)this.transport).maxPower;
		    }
		}
		return val;
	}
	
	public float requestEnergy(ForgeDirection form, float amount)
	{
		int meta = this.container.getBlockMetadata();
		
		if(meta == form.ordinal())
		{
			return amount;
		}
		
		return 0.0F;
	}

}
