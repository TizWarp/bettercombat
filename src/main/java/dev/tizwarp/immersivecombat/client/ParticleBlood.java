package dev.tizwarp.immersivecombat.client;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleBlood extends Particle
{
	private final float lavaParticleScale;

	protected ParticleBlood(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.800000011920929D;
		this.motionY *= 0.800000011920929D;
		this.motionZ *= 0.800000011920929D;
		this.motionY = (double) (this.rand.nextFloat() * 0.4F + 0.05F);
		this.particleRed = 1.0F;
		this.particleGreen = 0.5F;
		this.particleBlue = 0.5F;
		this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
		this.lavaParticleScale = this.particleScale;
		this.particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		this.setParticleTextureIndex(49);
	}

	public int getBrightnessForRender(float p_189214_1_)
	{
		int i = super.getBrightnessForRender(p_189214_1_);
		// int j = 240;
		int k = i >> 16 & 255;
		return 240 | k << 16;
	}

	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
		this.particleScale = this.lavaParticleScale * (1.0F - f * f);
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}

//        float f = (float)this.particleAge / (float)this.particleMaxAge;

//        if (this.rand.nextFloat() > f)
//        {
//            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
//        }

		this.motionY -= 0.03D;
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9990000128746033D;
		this.motionY *= 0.9990000128746033D;
		this.motionZ *= 0.9990000128746033D;

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

	}

	@SideOnly(Side.CLIENT)
	public static class Factory implements IParticleFactory
	{
		public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
		{
			return new ParticleBlood(worldIn, xCoordIn, yCoordIn, zCoordIn);
		}
	}
}