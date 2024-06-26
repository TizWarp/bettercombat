package dev.tizwarp.immersivecombat.client;

import java.util.Random;

import dev.tizwarp.immersivecombat.util.ConfigurationHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;

public class BetterCombatHand
{
	public BetterCombatHand()
	{
		this.resetBetterCombatWeapon();
	}
	
	public void setBetterCombatWeapon(ConfigurationHandler.CustomWeapon customWeapon, ItemStack itemStack, int cooldownTicks )
	{
		this.customWeapon = customWeapon;
		
		/* Get the total sweep for the weapon */
		int sweepMod = customWeapon.sweepMod;
		
		NBTTagList nbttaglist = itemStack.getEnchantmentTagList();
		
		for ( int i = 0; i < nbttaglist.tagCount(); ++i )
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			Enchantment enchantment = Enchantment.getEnchantmentByID(nbttagcompound.getShort("id"));
			int j = nbttagcompound.getShort("lvl");
			
			if ( enchantment == Enchantments.SWEEPING )
			{
				if ( j > 0 )
				{
					sweepMod += j;
				}
			}
		}
		
		this.canWeaponParry = customWeapon.parry;
		this.sweep = sweepMod;
				
		int cd = MathHelper.clamp(cooldownTicks, ConfigurationHandler.minimumAttackSpeedTicks, 15);

		this.equipTimerIncrement = 1.0F / (3.0F * (cd));
		this.equipSoundTimer = cd / 2;
	}
	
	public boolean hasCustomWeapon()
	{
		return this.customWeapon != null;
	}
	
	public void tick()
	{
		this.swingTimer--;
	}
	
	public ConfigurationHandler.WeaponProperty getWeaponProperty()
	{
		if ( this.hasCustomWeapon() )
		{
			return this.getCustomWeapon().property;
		}
		
		return ConfigurationHandler.WeaponProperty.ONEHAND;
	}
	
	private ConfigurationHandler.CustomWeapon getCustomWeapon()
	{
		return this.customWeapon;
	}

	public ConfigurationHandler.Animation getAnimation()
	{
		if ( this.hasCustomWeapon() )
		{
			return this.getCustomWeapon().animation;
		}
		
		return ConfigurationHandler.Animation.NONE;
	}
	
	public ConfigurationHandler.SoundType getSoundType()
	{
		if ( this.hasCustomWeapon() )
		{
			return this.getCustomWeapon().soundType;
		}
		
		return ConfigurationHandler.SoundType.NONE;
	}
	
	public double getFatigue()
	{
		if ( this.hasCustomWeapon() )
		{
			if ( this.getCustomWeapon().property == ConfigurationHandler.WeaponProperty.VERSATILE )
			{
				return ConfigurationHandler.versatileFatigueAmount;
			}
		}
		
		return 0.0;
	}
	
	public double getAdditionalReach()
	{
		if ( this.hasCustomWeapon() )
		{
			return this.getCustomWeapon().additionalReachMod;
		}
		
		return -ConfigurationHandler.fistAndNonWeaponReachReduction;
	}
	
	public boolean canWeaponParry()
	{
		return this.canWeaponParry; // !ConfigurationHandler.disablecanWeaponParrying && 
	}
	
	public int getSweep()
	{
		return this.sweep;
	}
	
	public float getEquipTimerIncrement()
	{
		return this.equipTimerIncrement;
	}
	
	public void resetBetterCombatWeapon()
	{
		this.customWeapon = null;
		
		this.canWeaponParry = false;
		this.sweep = 0;
		
		this.swingTimer = 0;
		this.swingTimerCap = 0;
		this.swingTimerIncrement = 0.0F;

		this.equipSoundTimer = 0;
		this.equipTimerIncrement = 0.5F;
		
		this.swingTimestampSound = 0;
		this.swingTimestampDamage = 0;
		
		this.mining = false;
	}
	
	/* The custom weapon */
	ConfigurationHandler.CustomWeapon customWeapon = null;
	
	private boolean canWeaponParry = false;
	
	/* The weapons custom reach amount */
	private int sweep = 0;

	/* How long the swing timer is in ticks, counting down to 0 */
	private int swingTimer = 0;
	/* The value the swing timer started at in ticks */
	private int swingTimerCap = 0;
	/* How fast the animation counts down, in partial ticks */
	private float swingTimerIncrement = 0.0F;
	
	/* How long the equip sound timer is in ticks after equipping a weapon, counting down to 0, This is only used for determining equip/sheathe sounds */
	public int equipSoundTimer = 0;
	/* How long the equip animation is, in partial ticks */
	private float equipTimerIncrement = 0.5F;
	
	/* When the swingTimer reaches this number, make a swing sound */
	private int swingTimestampSound = 0;
	/* When the swingTimer reaches this number, send a damage packet */
	private int swingTimestampDamage = 0;

	/* Mouse held down and is mining a block */
	private boolean mining = false;
	
	
	
	public float moveRightVariance = 1.0F;
	public float moveUpVariance = 1.0F;
	public float moveCloseVariance = 1.0F;

	public float rotateUpVariance = 1.0F;
	public float rotateCounterClockwiseVariance = 1.0F;
	public float rotateLeftVariance = 1.0F;
	
	public boolean alternateAnimation = false;

	private final Random rand = new Random();
	
	public void randomizeVariances()
	{		
		this.moveRightVariance = this.randomMoveVariance();
		this.moveUpVariance = this.randomMoveVariance();
		this.moveCloseVariance = this.randomMoveVariance();

		this.rotateUpVariance = this.randomRotationVariance();
		this.rotateCounterClockwiseVariance = this.randomRotationVariance();
		this.rotateLeftVariance = this.randomRotationVariance();
	}
	
	public float randomMoveVariance()
	{
		return 1.06F - this.rand.nextFloat() * 0.12F;
	}

	public float randomRotationVariance()
	{
		return 1.03F - this.rand.nextFloat() * 0.06F;
	}
	
	public float randomPreciseRotationVariance()
	{
		return 1.015F - this.rand.nextFloat() * 0.03F;
	}
	
	public void stopAttack()
	{
		this.swingTimer = 0;
	}
	
	public boolean soundReady()
	{
		return this.swingTimer == this.swingTimestampSound;
	}
	
	public boolean damageReady()
	{
		return this.swingTimer == this.getSwingTimestampDamage();
	}
	
	public void initiateAnimation( int i )
	{
		if ( this.hasCustomWeapon() )
		{
			switch( this.getCustomWeapon().animation )
			{
				case SWEEP:
				{
					this.setSweeping(i);
					return;
				}
				case STAB:
				{
					this.setStabbing(i);
					return;
				}
				case CHOP:
				{
					this.setChopping(i);
					return;
				}
				default:
				{
					this.setPunching(i);
					return;
				}
			}
		}
		else
		{
			this.setPunching(i);
			return;
		}
	}
	
	public void setSweeping( int i )
	{
		this.mining = false;

		this.swingTimer = MathHelper.clamp(i, ConfigurationHandler.minimumAttackSpeedTicks, 14)-2;
		this.swingTimerCap = this.swingTimer;
		this.swingTimerIncrement = 1.0F/this.swingTimer;
		
		if ( this.alternateAnimation = this.rand.nextBoolean() )
		{
			this.swingTimestampSound = Math.round(this.swingTimer*0.5F);
			this.swingTimestampDamage = this.swingTimestampSound-1;
		}
		else
		{
			this.swingTimestampSound = Math.round(this.swingTimer*0.8F);
			this.swingTimestampDamage = this.swingTimestampSound-1;
		}
		
		this.moveRightVariance = this.randomMoveVariance();
		this.moveUpVariance = this.randomMoveVariance();
		this.moveCloseVariance = this.randomMoveVariance();

		this.rotateUpVariance = this.randomPreciseRotationVariance();
		this.rotateCounterClockwiseVariance = this.randomRotationVariance();
		this.rotateLeftVariance = this.randomRotationVariance();
	}
	
	public void setStabbing( int i )
	{
		this.mining = false;

		this.swingTimer = MathHelper.clamp(i, ConfigurationHandler.minimumAttackSpeedTicks, 13)-1;
		this.swingTimerCap = this.swingTimer;
		this.swingTimerIncrement = 1.0F/this.swingTimer;
				
		this.swingTimestampSound = Math.round(this.swingTimer*0.8F);
		this.swingTimestampDamage = this.swingTimestampSound-1;
		
		this.randomizeVariances();
	}
	
	public void setChopping( int i )
	{
		this.mining = false;
		
		this.swingTimer = MathHelper.clamp(i, ConfigurationHandler.minimumAttackSpeedTicks, 14)-2;
		this.swingTimerCap = this.swingTimer;
		this.swingTimerIncrement = 1.0F/this.swingTimer;
				
		this.swingTimestampSound = Math.round(this.swingTimer*0.8F);
		this.swingTimestampDamage = this.swingTimestampSound-1;
		
		this.randomizeVariances();
	}

	public void setPunching( int i )
	{
		this.mining = false;
		
		this.swingTimer = MathHelper.clamp(i, ConfigurationHandler.minimumAttackSpeedTicks, 14)-2;
		this.swingTimerCap = this.swingTimer;
		this.swingTimerIncrement = 1.0F/this.swingTimer;
				
		this.swingTimestampSound = Math.round(this.swingTimer*0.8F);
		this.swingTimestampDamage = this.swingTimestampSound-1;
	}
	
	public void startMining( int i )
	{
		this.mining = true;
		
		this.swingTimer = i;
		this.swingTimerCap = this.swingTimer;
		this.swingTimerIncrement = 1.0F/this.swingTimer;
		
		this.swingTimestampSound = 8;
		this.swingTimestampDamage = 9;
		
		this.randomizeVariances();
	}

	public void setShieldBashing()
	{
		this.mining = false;
		
		this.swingTimer = 10;
		this.swingTimerCap = this.swingTimer;
		this.swingTimerIncrement = 0.1F;
		
		this.swingTimestampSound = 8;
		this.swingTimestampDamage = 9;
	}
	
	/* How long the swing is in ticks, counting down to 0 */
	public int getSwingTimer()
	{
		return this.swingTimer;
	}
	
	public float getSwingTimerIncrement()
	{
		return this.swingTimerIncrement;
	}
	
	public void stopMining()
	{
		this.mining = false;
	}
	
	public boolean isMining()
	{
		return this.mining;
	}

	public int getSwingTimestampDamage()
	{
		return swingTimestampDamage;
	}

	public float getSwingTimerCap()
	{
		return this.swingTimerCap;
	}
}