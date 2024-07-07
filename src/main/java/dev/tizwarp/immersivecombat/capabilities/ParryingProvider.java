package dev.tizwarp.immersivecombat.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ParryingProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IParrying.class)
    public static final Capability<IParrying> PARRYING_CAPABILITY = null;

    private IParrying instance = PARRYING_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PARRYING_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == PARRYING_CAPABILITY ? PARRYING_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return PARRYING_CAPABILITY.getStorage().writeNBT(PARRYING_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        PARRYING_CAPABILITY.getStorage().readNBT(PARRYING_CAPABILITY, this.instance, null, nbt);
    }
}
