package com.tensura_tno.mixin.client;

import java.lang.reflect.Method;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity", remap = false)
public abstract class BossDeathBypassFixMixin extends Mob {
   @Shadow(remap = false)
   @Final
   protected ServerBossEvent bossEvent;

   protected BossDeathBypassFixMixin() {
      super(null, null);
   }

   @Shadow(remap = false)
   public abstract boolean shouldCancelDeath();

   @Inject(method = "hurt", at = @At("RETURN"), remap = true, require = 0)
   private void tensuraTno$fixBypassDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      if (this.isDeadOrDying()) {
         if (this.shouldCancelDeath()) {
            if (this.isDeadOrDying()) {
               this.setHealth(0.1F);
            }

            try {
               Class<?> proc = Class.forName("net.unusual.block_factorys_bosses.procedures.BossCancelDieProcedure");
               Method exec = proc.getMethod("execute", Entity.class);
               exec.invoke(null, this);
            } catch (Exception var6) {
            }
         }
      }
   }

   @Inject(method = "remove", at = @At("TAIL"), remap = true, require = 0)
   private void tensuraTno$cleanupBossbar(RemovalReason reason, CallbackInfo ci) {
      if (this.bossEvent != null) {
         this.bossEvent.removeAllPlayers();
      }
   }
}
