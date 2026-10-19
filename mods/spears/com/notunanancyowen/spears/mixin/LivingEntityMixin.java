package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.components.KineticWeapon;
import com.notunanancyowen.spears.components.SwingAnimation;
import com.notunanancyowen.spears.components.UseEffects;
import com.notunanancyowen.spears.dataholders.SpearUser;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements SpearUser {
   @Unique
   private long spears$lastKineticAttackTime = -2147483648L;
   @Unique
   @Nullable
   private Object2LongMap<Entity> spears$piercingCooldowns;

   @Shadow
   public abstract ItemStack getMainHandItem();

   @Shadow
   public abstract boolean hasEffect(Holder<MobEffect> var1);

   @Shadow
   @Nullable
   public abstract MobEffectInstance getEffect(Holder<MobEffect> var1);

   @Shadow
   public abstract ItemStack getUseItem();

   @Inject(method = "getCurrentSwingDuration()I", at = @At("HEAD"), cancellable = true)
   private void overrideSwingDurationForSpear(CallbackInfoReturnable<Integer> cir) {
      if (this.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s) {
         LivingEntity me = (LivingEntity)this;
         int swing = s.swingTicks();
         if (MobEffectUtil.hasDigSpeed(me)) {
            cir.setReturnValue(swing - (1 + MobEffectUtil.getDigSpeedAmplification(me)));
         } else {
            cir.setReturnValue(
               this.hasEffect(MobEffects.DIG_SLOWDOWN)
                  ? swing + (1 + Objects.requireNonNull(this.getEffect(MobEffects.DIG_SLOWDOWN)).getAmplifier()) * 2
                  : swing
            );
         }
      }
   }

   @Inject(
      method = "startUsingItem(Lnet/minecraft/world/InteractionHand;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V")
   )
   private void recordPiercingCooldowns(InteractionHand hand, CallbackInfo ci) {
      if (this.getUseItem().get(Spears.KINETIC_WEAPON) instanceof KineticWeapon k) {
         this.spears$piercingCooldowns = new Object2LongOpenHashMap();
         k.playSound((LivingEntity)this);
      }
   }

   @WrapOperation(
      method = "startUsingItem(Lnet/minecraft/world/InteractionHand;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V")
   )
   private void suppressSoundOnStart(LivingEntity instance, Holder<GameEvent> registryEntry, Operation<Void> original) {
      if (!(instance.getUseItem().get(Spears.USE_EFFECTS) instanceof UseEffects u && !u.interactVibrations())) {
         original.call(new Object[]{instance, registryEntry});
      }
   }

   @WrapOperation(
      method = "stopUsingItem()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V")
   )
   private void suppressSoundOnStop(LivingEntity instance, Holder<GameEvent> registryEntry, Operation<Void> original) {
      if (!(instance.getUseItem().get(Spears.USE_EFFECTS) instanceof UseEffects u && !u.interactVibrations())) {
         original.call(new Object[]{instance, registryEntry});
      }
   }

   @Inject(method = "stopUsingItem()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setLivingEntityFlag(IZ)V"))
   private void forgetPiercingCooldowns(CallbackInfo ci) {
      this.spears$piercingCooldowns = null;
   }

   @Inject(method = "handleEntityEvent(B)V", at = @At("HEAD"))
   private void handleKineticAttack(byte status, CallbackInfo ci) {
      if (status == 2) {
         this.spears$lastKineticAttackTime = ((LivingEntity)this).getCommandSenderWorld().getGameTime();
      }
   }

   @Inject(method = "tick()V", at = @At("TAIL"))
   private void tickPiercingCooldowns(CallbackInfo ci) {
      if (!((LivingEntity)this).getCommandSenderWorld().isClientSide() && this.spears$piercingCooldowns != null) {
         ObjectIterator var3 = this.spears$piercingCooldowns.keySet().iterator();

         while (var3.hasNext()) {
            Entity e = (Entity)var3.next();
            if (!(e instanceof LivingEntity l && l.hurtTime != 0)) {
               long l = this.spears$piercingCooldowns.getLong(e);
               if (l > 1L) {
                  this.spears$piercingCooldowns.replace(e, l - 1L);
               } else {
                  this.spears$piercingCooldowns.remove(e, l);
               }
            }
         }
      }
   }

   @Override
   public float getTimeSinceLastKineticAttack(float tickProgress) {
      LivingEntity me = (LivingEntity)this;
      return this.spears$lastKineticAttackTime < 0L
         ? 0.0F
         : (float)(me.getCommandSenderWorld().getGameTime() - this.spears$lastKineticAttackTime) + tickProgress;
   }

   @Override
   public boolean isInPiercingCooldown(Entity target, int cooldownTicks) {
      if (this.spears$piercingCooldowns == null) {
         return false;
      }

      LivingEntity me = (LivingEntity)this;
      return this.spears$piercingCooldowns.containsKey(target)
         ? me.getCommandSenderWorld().getGameTime() - this.spears$piercingCooldowns.getLong(target) < cooldownTicks
         : false;
   }

   @Override
   public void startPiercingCooldown(Entity target) {
      if (this.spears$piercingCooldowns != null) {
         LivingEntity me = (LivingEntity)this;
         this.spears$piercingCooldowns.put(target, me.getCommandSenderWorld().getGameTime());
      }
   }

   @Override
   public int countSpearedMobs() {
      return this.spears$piercingCooldowns == null ? 0 : this.spears$piercingCooldowns.size();
   }

   @Override
   public boolean pierce(EquipmentSlot slot, Entity target, float damage, boolean dealDamage, boolean knockback, boolean dismount) {
      LivingEntity attacker = (LivingEntity)this;
      if (!(attacker.getCommandSenderWorld() instanceof ServerLevel serverWorld)) {
         return false;
      } else if (attacker instanceof Player player) {
         ItemStack itemStack = attacker.getItemBySlot(slot);
         DamageSource damageSource = attacker.damageSources().playerAttack(player);
         float halfCD = attacker.isUsingItem() ? 1.0F : player.getAttackStrengthScale(0.5F);
         float f = halfCD * (EnchantmentHelper.modifyDamage(serverWorld, player.getWeaponItem(), target, damageSource, damage) - damage);
         if (!attacker.isUsingItem()) {
            damage *= 0.2F + halfCD * halfCD * 0.8F;
         }

         if (knockback
            && target.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE)
            && target instanceof Projectile projectileEntity
            && projectileEntity.deflect(ProjectileDeflection.AIM_DEFLECT, attacker, attacker, true)) {
            return true;
         } else {
            float g = dealDamage ? damage + f : 0.0F;
            float h = 0.0F;
            if (target instanceof LivingEntity livingEntity) {
               h = livingEntity.getHealth();
            }

            boolean bl = dealDamage && target.hurt(damageSource, g);
            float kb = EnchantmentHelper.modifyKnockback(
               serverWorld, itemStack, target, damageSource, (float)attacker.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 0.4F
            );
            if (knockback) {
               if (target instanceof LivingEntity knockable) {
                  knockable.knockback(kb, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
               } else {
                  double x = attacker.getX() - target.getX();
                  double z = attacker.getZ() - target.getZ();
                  target.hasImpulse = true;
                  Vec3 vec3d = target.getDeltaMovement();

                  while (x * x + z * z < 1.0E-5F) {
                     x = (Math.random() - Math.random()) * 0.01;
                     z = (Math.random() - Math.random()) * 0.01;
                  }

                  Vec3 vec3d2 = new Vec3(x, 0.0, z).normalize().scale(kb);
                  target.setDeltaMovement(vec3d.x / 2.0 - vec3d2.x, target.onGround() ? Math.min(0.4, vec3d.y / 2.0 + kb) : vec3d.y, vec3d.z / 2.0 - vec3d2.z);
               }
            }

            boolean bl2 = false;
            if (dismount && target.isPassenger()) {
               bl2 = true;
               target.stopRiding();
            }

            if (!bl && !knockback && !bl2) {
               return false;
            }

            if (EnchantmentHelper.modifyDamage(serverWorld, itemStack, target, damageSource, f) > f) {
               player.magicCrit(target);
            }

            player.setLastHurtMob(target);
            Entity entity = target;
            if (target instanceof EnderDragonPart) {
               entity = ((EnderDragonPart)target).parentMob;
            }

            boolean runEnchantmentEffects = bl;
            bl = false;
            if (entity instanceof LivingEntity livingEntity) {
               bl = itemStack.hurtEnemy(livingEntity, player);
            }

            if (runEnchantmentEffects) {
               EnchantmentHelper.doPostAttackEffectsWithItemSource(serverWorld, target, damageSource, itemStack);
            }

            if (!player.getCommandSenderWorld().isClientSide() && !itemStack.isEmpty() && entity instanceof LivingEntity livingEntity) {
               if (bl) {
                  itemStack.postHurtEnemy(livingEntity, player);
               }

               if (itemStack.isEmpty()) {
                  if (itemStack == player.getMainHandItem()) {
                     player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                  } else {
                     player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                  }
               }
            }

            if (target instanceof LivingEntity) {
               f = h - ((LivingEntity)target).getHealth();
               player.awardStat(Stats.DAMAGE_DEALT, Math.round(f * 10.0F));
               if (player.getCommandSenderWorld() instanceof ServerLevel && f > 2.0F) {
                  int i = (int)(f * 0.5);
                  serverWorld.sendParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getY(0.5), target.getZ(), i, 0.1, 0.0, 0.1, 0.2);
               }
            }

            player.causeFoodExhaustion(0.1F);
            return true;
         }
      } else {
         ItemStack itemStack = attacker.getItemBySlot(slot);
         DamageSource damageSource = attacker.damageSources().mobAttack(attacker);
         float f = EnchantmentHelper.modifyDamage(serverWorld, itemStack, target, damageSource, damage);
         boolean bl2 = dealDamage && target.hurt(damageSource, f);
         boolean bl = knockback | bl2;
         if (knockback && target instanceof LivingEntity knockable) {
            knockable.knockback(
               EnchantmentHelper.modifyKnockback(
                  serverWorld, itemStack, target, damageSource, (float)attacker.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 0.4F
               ),
               attacker.getX() - target.getX(),
               attacker.getZ() - target.getZ()
            );
         }

         if (dismount && target.isPassenger()) {
            bl = true;
            target.stopRiding();
         }

         if (bl2) {
            EnchantmentHelper.doPostAttackEffects(serverWorld, target, damageSource);
         }

         if (!bl) {
            return false;
         }

         if (attacker instanceof LivingEntity livingEntity) {
            livingEntity.setLastHurtMob(target);
         }

         return true;
      }
   }
}
