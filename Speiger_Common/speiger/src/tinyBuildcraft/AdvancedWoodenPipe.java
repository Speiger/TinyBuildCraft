package speiger.src.tinyBuildcraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.BuildCraftTransport;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeIconProvider;
import buildcraft.transport.PipeTransportPower;


public class AdvancedWoodenPipe extends Pipe<PipeTransportPower> implements IPowerReceptor
{
	private PowerHandler powerHandler;
	private boolean[] powerSources = new boolean[6];
	private boolean full;

	public AdvancedWoodenPipe(int itemID) {
		super(new PipeTransportPower(), itemID);

		powerHandler = new PowerHandler(this, Type.PIPE);
		initPowerProvider();
		transport.initFromPipe(getClass());
	}

	private void initPowerProvider() {
		powerHandler.configure(2, 1000, 1, 15000);
		powerHandler.configurePowerPerdition(1, 10);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider() {
		return IconProvider.getIcon();
	}

	@Override
	public int getIconIndex(ForgeDirection direction) {
		return 0;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (container.worldObj.isRemote)
			return;

		if (powerHandler.getEnergyStored() <= 0)
			return;

		int sources = 0;
		for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
			if (!container.isPipeConnected(o)) {
				powerSources[o.ordinal()] = false;
				continue;
			}
			if (powerHandler.isPowerSource(o)) {
				powerSources[o.ordinal()] = true;
			}
			if (powerSources[o.ordinal()]) {
				sources++;
			}
		}


		float energyToRemove;

		if(powerHandler.getEnergyStored() > 1000)
		{
			energyToRemove = powerHandler.getEnergyStored() / 100;
		}
		else if (powerHandler.getEnergyStored() > 100 && powerHandler.getEnergyStored() <= 1000) {
			energyToRemove = powerHandler.getEnergyStored() / 100;
		}
		else if (powerHandler.getEnergyStored() > 100) {
			energyToRemove = powerHandler.getEnergyStored() / 10;
		}
		else if (powerHandler.getEnergyStored() > 10) {
		energyToRemove = powerHandler.getEnergyStored() / 1;
		}
		else {
			energyToRemove = 1;
		}
		energyToRemove /= (float) sources;

		for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
			if (!powerSources[o.ordinal()])
				continue;

			float energyUsable = powerHandler.useEnergy(0, energyToRemove, false);

			float energySend = transport.receiveEnergy(o, energyUsable);
			if (energySend > 0) {
				powerHandler.useEnergy(0, energySend, true);
			}
		}
	}

	public boolean requestsPower() {
		if (full) {
			boolean request = powerHandler.getEnergyStored() < powerHandler.getMaxEnergyStored() / 2;
			if (request) {
				full = false;
			}
			return request;
		}
		full = powerHandler.getEnergyStored() >= powerHandler.getMaxEnergyStored() - 10;
		return !full;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		powerHandler.writeToNBT(data);
		for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			data.setBoolean("powerSources[" + i + "]", powerSources[i]);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		powerHandler.readFromNBT(data);
		initPowerProvider();
		for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			powerSources[i] = data.getBoolean("powerSources[" + i + "]");
		}
	}
}
