package dev.tizwarp.immersivecombat.util;

import com.cleanroommc.hackery.Reflection;
import dev.tizwarp.immersivecombat.capabilities.IParrying;
import dev.tizwarp.immersivecombat.capabilities.ParryingStorage;
import dev.tizwarp.immersivecombat.capabilities.ParyyingCapability;
import dev.tizwarp.immersivecombat.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	public void preInit( FMLPreInitializationEvent event )
	{
		MinecraftForge.EVENT_BUS.register(new EventHandlers());
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        ConfigurationHandler.createInstLists();
        
		PacketHandler.registerMessages(Reference.MOD_ID);
		SoundHandler.registerSounds();
		CapabilityManager.INSTANCE.register(IParrying.class, new ParryingStorage(), ParyyingCapability::new);

//		registerParticles();
	}
	
	/* I&F */
    public static Class<?> partEntityClass = null;

	public void postInit( FMLPostInitializationEvent event ) throws Exception
	{
		ConfigurationHandler.postConfig();
		
		if ( partEntityClass == null )
		{
        	try
			{
				partEntityClass = Class.forName("net.ilexiconn.llibrary.server.entity.multipart.PartEntity");
			}
			catch ( ClassNotFoundException e )
			{

			}
		}
	}

	// PARTICLE
//	public abstract ParticleWizardry createParticle(ResourceLocation type, World world, double x, double y, double z);
//	
//	public abstract void registerParticles();
//	
}