package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
   @Shadow
   public abstract boolean hasEffect(Holder<MobEffect> var1);

   public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
   public void onCanAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
      LivingEntity self = (LivingEntity)this;
      if (self.hasEffect(ModEffect.EFFECTSTUN)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z", at = @At("HEAD"), cancellable = true)
   private void onAddEffect(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
      this.dev1_21_1$handleStunEffectLogic(effectInstance, null, cir);
   }

   @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
   private void onAddEffectWithSource(MobEffectInstance effectInstance, @Nullable Entity source, CallbackInfoReturnable<Boolean> cir) {
      this.dev1_21_1$handleStunEffectLogic(effectInstance, source, cir);
   }

   @Unique
   private void dev1_21_1$handleStunEffectLogic(MobEffectInstance effectInstance, @Nullable Entity source, CallbackInfoReturnable<Boolean> cir) {
      LivingEntity self = (LivingEntity)this;
      if (self instanceof Player player && effectInstance.getEffect().equals(ModEffect.EFFECTSTUN)) {
         if (EntityUtil.isEquipped((Item)ModItems.UNBREAKABLE_SKULL.get(), player)
            && !player.getCooldowns().isOnCooldown((Item)ModItems.UNBREAKABLE_SKULL.get())) {
            int cooldownTicks = 900;
            player.getCooldowns().addCooldown((Item)ModItems.UNBREAKABLE_SKULL.get(), cooldownTicks);
            cir.setReturnValue(false);
         }
      }
   }
}
