package dev.tizwarp.immersivecombat.core;


import fermiumbooter.FermiumRegistryAPI;
import fermiumbooter.mixin.FermiumMixinLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("ImmersiveCombat-Mixin")
public class MixinLoader implements IFMLLoadingPlugin, IEarlyMixinLoader{


    @Nullable
    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Nullable
    @Override
    public String getModContainerClass() {
        return "";
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return "";
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Nullable
    @Override
    public String getAccessTransformerClass() {
        return "";
    }

    @Override
    public List<String> getMixinConfigs() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("immersivecombat.mixins.json");
        return ret;
    }
}
