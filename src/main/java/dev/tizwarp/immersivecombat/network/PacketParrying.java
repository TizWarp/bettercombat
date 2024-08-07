package dev.tizwarp.immersivecombat.network;

import dev.tizwarp.immersivecombat.capabilities.IParrying;
import dev.tizwarp.immersivecombat.capabilities.ParryingProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.lwjgl.Sys;

public class PacketParrying implements IMessage
{
//	private Integer entityId;
	private boolean parrying;

	public PacketParrying()
	{

	}
	
	public PacketParrying( boolean b ) //, int damage, int knockback )
	{
//		this.entityId = entId;
		this.parrying = b;
	}

	public void fromBytes( ByteBuf buf )
	{
//		if ( buf.readBoolean() )
//		{
//			this.entityId = ByteBufUtils.readVarInt(buf, 4);
//		}
		
		this.parrying = buf.readBoolean();
	}

	public void toBytes( ByteBuf buf )
	{
//		buf.writeBoolean(this.entityId != null);
//		
//		if ( this.entityId != null )
//		{
//			ByteBufUtils.writeVarInt(buf, this.entityId, 4);
//		}
		
		buf.writeBoolean(this.parrying);
	}

	public void handleClientSide( PacketParrying message, EntityPlayer player )
	{

	}
	
	public static class Handler implements IMessageHandler<PacketParrying, IMessage>
	{
		@Override
		public IMessage onMessage( final PacketParrying message, final MessageContext ctx )
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private static void handle( PacketParrying message, MessageContext ctx )
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
						
			if ( message.parrying )
			{
				player.setActiveHand(EnumHand.MAIN_HAND);
				player.getEntityData().setBoolean("isParrying", true);
				IParrying parrying = player.getCapability(ParryingProvider.PARRYING_CAPABILITY, null);
				parrying.setParrying(true);
				System.out.println("I am now parrying");
			}
			else
			{
				player.stopActiveHand();
				IParrying parrying = player.getCapability(ParryingProvider.PARRYING_CAPABILITY, null);
				parrying.setParrying(false);
				player.getEntityData().setBoolean("isParrying", false);
			}
		}
	}
}