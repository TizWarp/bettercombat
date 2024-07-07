package dev.tizwarp.immersivecombat.capabilities;

public class ParyyingCapability implements IParrying{

    private boolean Parrying  = false;

    @Override
    public void setParrying(boolean parrying) {
        this.Parrying = parrying;
    }

    @Override
    public boolean isParrying() {
        return this.Parrying;
    }
}
