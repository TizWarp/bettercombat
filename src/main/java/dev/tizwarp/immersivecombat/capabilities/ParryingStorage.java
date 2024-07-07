package dev.tizwarp.immersivecombat.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ParryingStorage implements Capability.IStorage<IParrying> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IParrying> capability, IParrying instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("parrying", instance.isParrying());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IParrying> capability, IParrying instance, EnumFacing side, NBTBase nbt) {
        instance.setParrying(((NBTTagCompound) nbt).getBoolean("parrying"));
    }
}
