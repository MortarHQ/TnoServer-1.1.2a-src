package com.tensura_tno.mixin.client;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura_neb.entity.RimuruOgreFightEntity", remap = false)
public abstract class NebRimuruOneShotDeathMixin {
   @Shadow
   public abstract boolean isFinalPhase();

   @Shadow
   public abstract void setFinalPhase(boolean var1);

   @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"), remap = false)
   private void tensuraTno$completeLethalFirstPhase(DamageSource source, CallbackInfo ci) {
      LivingEntity entity = (LivingEntity)this;
      if (!entity.level().isClientSide()
         && !entity.isRemoved()
         && !this.isFinalPhase()
         && entity.getHealth() <= 0.0F
         && !source.is(DamageTypes.GENERIC_KILL)
         && !source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
         if (ResourceLocation.fromNamespaceAndPath("tensura_neb", "rimuru_ogre_fight").equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()))) {
            this.setFinalPhase(true);
         }
      }
   }
}
