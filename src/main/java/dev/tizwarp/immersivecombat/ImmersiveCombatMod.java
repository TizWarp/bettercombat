package dev.tizwarp.immersivecombat;

import dev.tizwarp.immersivecombat.util.CommonProxy;
import dev.tizwarp.immersivecombat.util.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION, guiFactory="dev.tizwarp.immersivecombat.client.GUIFactory", acceptedMinecraftVersions="[1.12.2]")
public class ImmersiveCombatMod
{
    @SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @Mod.Instance(Reference.MOD_ID)
    public static ImmersiveCombatMod modInstance;

	public static Logger LOG = LogManager.getLogger(Reference.MOD_ID);

	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event )
	{
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init( FMLInitializationEvent event )
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit( FMLPostInitializationEvent event ) throws Exception
	{
		proxy.postInit(event);
	}
	
	public void registerParticles()
	{
		
	}

	// PARTICLE
//	public ParticleWizardry createParticle(ResourceLocation type, World world, double x, double y, double z)
//	{
//		return null;
//	}
}