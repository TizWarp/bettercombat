package dev.tizwarp.immersivecombat.network;

import dev.tizwarp.immersivecombat.util.Helpers;
import dev.tizwarp.immersivecombat.util.ConfigurationHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static com.elenai.elenaidodge2.api.FeathersHelper.decreaseFeathers;
import static com.elenai.elenaidodge2.api.FeathersHelper.getFeatherLevel;

public class PacketMainhandAttack implements IMessage
{
	private Integer entityId;

	public PacketMainhandAttack()
	{
	}

	public PacketMainhandAttack( Integer parEntityId )
	{		
		this.entityId = parEntityId;
	}

	@Override
	public void fromBytes( ByteBuf buf )
	{
		if ( buf.readBoolean() )
		{
			this.entityId = ByteBufUtils.readVarInt(buf, 4);
		}
	}

	@Override
	public void toBytes( ByteBuf buf )
	{
		buf.writeBoolean(this.entityId != null);
		
		if ( this.entityId != null )
		{
			ByteBufUtils.writeVarInt(buf, this.entityId, 4);
		}
	}

	public static class Handler implements IMessageHandler<PacketMainhandAttack, IMessage>
	{
		@Override
		public IMessage onMessage( final PacketMainhandAttack message, final MessageContext ctx )
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private static void handle( PacketMainhandAttack message, MessageContext ctx )
		{

			EntityPlayerMP player = ctx.getServerHandler().player;

			if (ConfigurationHandler.elanaiDodgeCompat && Loader.isModLoaded("elenaidodge2")) {

				double damage = Helpers.getMainhandAttackDamage(player,player.getHeldItemMainhand());
				int cost = (int) ((damage/ ConfigurationHandler.featherCostDamageRatioMainHand)* ConfigurationHandler.elenaiDodgeMainHandFeatherCost);

				if (getFeatherLevel(player) < cost) {
					return;
				}

				decreaseFeathers(player, cost);

			}

			if ( message.entityId != null )
			{
				Entity target = player.world.getEntityByID(message.entityId);

				if ( target != null )
				{
					Helpers.playerAttackVictim(player, target, true);
				}
			}

			Helpers.applySwingInteria(player);
			
//			if ( ConfigurationHandler.momentumOnAttack != 0.0F )
//			{
//				if ( player.onGround )
//				{
//					player.motionY += 0.001D;
//				}
//		
//				player.motionX -= ConfigurationHandler.momentumOnAttack*MathHelper.sin(player.rotationYaw * 0.017453292F);
//				player.motionZ += ConfigurationHandler.momentumOnAttack*MathHelper.cos(player.rotationYaw * 0.017453292F);
//				
//				player.velocityChanged = true;
//			}
		}
	}
}