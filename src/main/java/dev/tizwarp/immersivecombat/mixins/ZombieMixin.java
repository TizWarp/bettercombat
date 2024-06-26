package dev.tizwarp.immersivecombat.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityHusk.class)
public class ZombieMixin {

    @Inject(method = "shouldBurnInDay", at = @At("HEAD"), cancellable = true)
    protected void shouldBurnInDay(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }

}
