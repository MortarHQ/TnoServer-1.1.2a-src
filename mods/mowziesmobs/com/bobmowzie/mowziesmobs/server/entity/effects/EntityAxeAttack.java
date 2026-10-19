package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemWroughtAxe;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

public class EntityAxeAttack extends EntityMagicEffect {
   private static final EntityDataAccessor<Boolean> VERTICAL = SynchedEntityData.defineId(EntityAxeAttack.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<ItemStack> AXE_STACK = SynchedEntityData.defineId(EntityAxeAttack.class, EntityDataSerializers.ITEM_STACK);
   public static int SWING_DURATION_HOR = 24;
   public static int SWING_DURATION_VER = 30;
   private float quakeAngle = 0.0F;
   private AABB quakeBB = new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

   public EntityAxeAttack(EntityType<? extends EntityAxeAttack> type, Level world) {
      super(type, world);
   }

   public EntityAxeAttack(EntityType<? extends EntityAxeAttack> type, Level world, LivingEntity caster, boolean vertical) {
      super(type, world, caster);
      this.setVertical(vertical);
      this.setAxeStack(caster.getMainHandItem());
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VERTICAL, false);
      builder.define(AXE_STACK, ((ItemWroughtAxe)ItemHandler.WROUGHT_AXE.get()).getDefaultInstance());
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getCaster() != null) {
         if (!this.getCaster().isAlive()) {
            this.discard();
         }

         this.absMoveTo(
            this.getCaster().getX(),
            this.getCaster().getY() + this.getCaster().getEyeHeight(),
            this.getCaster().getZ(),
            this.getCaster().getYRot(),
            this.getCaster().getXRot()
         );
      }

      if (!this.level().isClientSide && this.tickCount == 7) {
         this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 0.7F, 1.1F);
      }

      if (!this.level().isClientSide && this.getCaster() != null) {
         if (!this.getVertical() && this.tickCount == SWING_DURATION_HOR / 2 - 1) {
            this.dealDamage(
               7.0F * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue() / 9.0F,
               4.0F,
               160.0F,
               1.2F
            );
         } else if (this.getVertical() && this.tickCount == SWING_DURATION_VER / 2 - 1) {
            this.dealDamage(
               ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue(), 4.5F, 40.0F, 0.8F
            );
            this.quakeAngle = this.getYRot();
            this.quakeBB = this.getBoundingBox().move(0.0, -this.getCaster().getEyeHeight(), 0.0);
            this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_LAND.get(), 0.3F, 0.5F);
            this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), 2.0F, 0.9F + this.random.nextFloat() * 0.1F);
         } else if (this.getVertical() && this.tickCount == SWING_DURATION_VER / 2 + 1) {
            EntityCameraShake.cameraShake(this.level(), this.position(), 10.0F, 0.05F, 0, 10);
         }
      }

      if (this.getVertical() && this.getCaster() != null && this.tickCount >= SWING_DURATION_VER / 2) {
         int maxDistance = 16;
         double perpFacing = this.quakeAngle * (Math.PI / 180.0);
         double facingAngle = perpFacing + (Math.PI / 2);
         int hitY = Mth.floor(this.quakeBB.minY - 0.5);
         int distance = this.tickCount - 15;
         double spread = 1.099557410031076;
         int arcLen = Mth.ceil(distance * spread);
         double minY = this.quakeBB.minY;
         double maxY = this.quakeBB.maxY;

         for (int i = 0; i < arcLen; i++) {
            double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
            double vx = Math.cos(theta);
            double vz = Math.sin(theta);
            double px = this.getX() + vx * distance;
            double pz = this.getZ() + vz * distance;
            float factor = 1.0F - (float)distance / maxDistance;
            AABB selection = new AABB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);

            for (Entity entity : this.level().getEntitiesOfClass(Entity.class, selection)) {
               if (entity.onGround() && entity != this && !(entity instanceof FallingBlockEntity) && entity != this.getCaster()) {
                  float applyKnockbackResistance = 0.0F;
                  boolean hitEntity = false;
                  if (this.raytraceCheckEntity(entity)) {
                     if (this.getCaster() instanceof Player) {
                        hitEntity = entity.hurt(
                           this.damageSources().playerAttack((Player)this.getCaster()),
                           (factor * 5.0F + 1.0F)
                              * (((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue() / 9.0F)
                        );
                     } else {
                        hitEntity = entity.hurt(
                           this.damageSources().mobAttack(this.getCaster()),
                           (factor * 5.0F + 1.0F)
                              * (((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue() / 9.0F)
                        );
                     }

                     if (entity instanceof LivingEntity) {
                        applyKnockbackResistance = (float)((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
                     }

                     if (hitEntity) {
                        double magnitude = -4.0;
                        double x = vx * (1.0F - factor) * magnitude * (1.0F - applyKnockbackResistance);
                        double y = 0.0;
                        if (entity.onGround()) {
                           y += 0.15 * (1.0F - applyKnockbackResistance);
                        }

                        double z = vz * (1.0F - factor) * magnitude * (1.0F - applyKnockbackResistance);
                        entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
                        if (entity instanceof ServerPlayer) {
                           ((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
                        }
                     }
                  }
               }
            }

            if (this.level().random.nextBoolean()) {
               int hitX = Mth.floor(px);
               int hitZ = Mth.floor(pz);
               BlockPos pos = new BlockPos(hitX, hitY, hitZ);
               BlockPos abovePos = new BlockPos(pos).above();
               BlockState block = this.level().getBlockState(pos);
               BlockState blockAbove = this.level().getBlockState(abovePos);
               if (!block.isAir() && block.isRedstoneConductor(this.level(), pos) && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
                  EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.level(), block, 0.3F);
                  fallingBlock.setPos(hitX + 0.5, hitY + 1, hitZ + 0.5);
                  this.level().addFreshEntity(fallingBlock);
               }
            }
         }
      }

      if (this.tickCount > SWING_DURATION_HOR) {
         this.discard();
      }
   }

   private void dealDamage(float damage, float range, float arc, float applyKnockback) {
      boolean hit = false;

      for (Entity entityHit : this.getEntitiesNearby(range, 2.0, range, range)) {
         if (!(entityHit instanceof ItemEntity)) {
            float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
            float entityAttackingAngle = this.getYRot() % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            float entityHitDistance = (float)Math.sqrt(
                  (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
               )
               - entityHit.getBbWidth() / 2.0F;
            if ((
                  entityHit != this.getCaster()
                        && (!(entityHit instanceof Parrot) || entityHit.getVehicle() != this.getCaster())
                        && entityHitDistance <= range
                        && entityRelativeAngle <= arc / 2.0F
                        && entityRelativeAngle >= -arc / 2.0F
                     || entityRelativeAngle >= 360.0F - arc / 2.0F
                     || entityRelativeAngle <= -360.0F + arc / 2.0F
               )
               && this.raytraceCheckEntity(entityHit)) {
               if (this.getCaster() instanceof Player player) {
                  PlayerData data = DataHandler.getData(player, DataHandler.PLAYER_DATA);
                  data.setAxeCanAttack(true);
                  this.attackTargetEntityWithCurrentItem(
                     entityHit,
                     player,
                     damage / ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamage.get()).floatValue(),
                     applyKnockback
                  );
                  data.setAxeCanAttack(false);
               }

               hit = true;
            }
         }
      }

      if (hit) {
         this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 0.3F, 0.5F);
      }
   }

   public void setVertical(boolean vertical) {
      this.getEntityData().set(VERTICAL, vertical);
   }

   public boolean getVertical() {
      return (Boolean)this.getEntityData().get(VERTICAL);
   }

   private List<Entity> getEntitiesNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(distanceX, distanceY, distanceZ));
      return list.stream()
         .filter(entityNeighbor -> entityNeighbor != null && this.distanceTo(entityNeighbor) <= radius + entityNeighbor.getBbWidth() / 2.0F)
         .collect(Collectors.toCollection(ArrayList::new));
   }

   public void setAxeStack(ItemStack axeStack) {
      this.getEntityData().set(AXE_STACK, axeStack);
   }

   public ItemStack getAxeStack() {
      return (ItemStack)this.getEntityData().get(AXE_STACK);
   }

   public void attackTargetEntityWithCurrentItem(Entity targetEntity, Player player, float damageMult, float knockbackMult) {
      if (CommonHooks.onPlayerAttackTarget(player, targetEntity) && targetEntity.isAttackable()) {
         ItemStack oldStack = player.getMainHandItem();
         ItemStack newStack = this.getAxeStack();
         this.resetModifiers(player, oldStack, newStack);
         player.setItemInHand(InteractionHand.MAIN_HAND, newStack);
         if (targetEntity.skipAttackInteraction(player)) {
            this.resetModifiers(player, newStack, oldStack);
            player.setItemInHand(InteractionHand.MAIN_HAND, oldStack);
         } else if (targetEntity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE)
            && targetEntity instanceof Projectile projectile
            && projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this, this, true)) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, this.getSoundSource());
            this.resetModifiers(player, newStack, oldStack);
            player.setItemInHand(InteractionHand.MAIN_HAND, oldStack);
         } else {
            DamageSource damageSource = this.damageSources().playerAttack(player);
            float attackDamage = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE) * damageMult;
            float enchantedDamage = player.getEnchantedDamage(targetEntity, attackDamage, damageSource);
            if (attackDamage > 0.0F || enchantedDamage > 0.0F) {
               boolean wasSprinting = false;
               if (player.isSprinting()) {
                  player.level()
                     .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, player.getSoundSource(), 1.0F, 1.0F);
                  wasSprinting = true;
               }

               attackDamage += newStack.getItem().getAttackDamageBonus(targetEntity, attackDamage, damageSource);
               float damage = attackDamage + enchantedDamage;
               float targetHealth = targetEntity instanceof LivingEntity livingTarget ? livingTarget.getHealth() : 0.0F;
               Vec3 targetMovement = targetEntity.getDeltaMovement();
               boolean wasHurt = targetEntity.hurt(damageSource, damage);
               if (wasHurt) {
                  float knockback = player.getKnockback(targetEntity, damageSource) + (wasSprinting ? 1 : 0);
                  if (knockback > 0.0F) {
                     if (targetEntity instanceof LivingEntity livingTarget) {
                        livingTarget.knockback(
                           knockback * 0.5F * knockbackMult,
                           Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)),
                           -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0))
                        );
                     } else {
                        targetEntity.push(
                           -Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * knockback * 0.5F * knockbackMult,
                           0.1,
                           Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * knockback * 0.5F * knockbackMult
                        );
                     }

                     player.setDeltaMovement(player.getDeltaMovement().multiply(0.6, 1.0, 0.6));
                     player.setSprinting(false);
                  }

                  if (targetEntity instanceof ServerPlayer && targetEntity.hurtMarked) {
                     ((ServerPlayer)targetEntity).connection.send(new ClientboundSetEntityMotionPacket(targetEntity));
                     targetEntity.hurtMarked = false;
                     targetEntity.setDeltaMovement(targetMovement);
                  }

                  player.level()
                     .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F);
                  if (enchantedDamage > 0.0F) {
                     player.magicCrit(targetEntity);
                  }

                  player.setLastHurtMob(targetEntity);
                  Entity entity = targetEntity;
                  if (targetEntity instanceof PartEntity<?> part) {
                     entity = part.getParent();
                  }

                  ItemStack copy = newStack.copy();
                  boolean hurtEnemy = false;
                  if (this.level() instanceof ServerLevel serverLevel) {
                     if (entity instanceof LivingEntity livingEntity) {
                        hurtEnemy = newStack.hurtEnemy(livingEntity, player);
                     }

                     EnchantmentHelper.doPostAttackEffects(serverLevel, targetEntity, damageSource);
                  }

                  if (!this.level().isClientSide() && !copy.isEmpty() && entity instanceof LivingEntity livingEntity) {
                     if (hurtEnemy) {
                        newStack.postHurtEnemy(livingEntity, player);
                     }

                     if (newStack.isEmpty()) {
                        EventHooks.onPlayerDestroyItem(player, copy, InteractionHand.MAIN_HAND);
                        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                     }
                  }

                  if (targetEntity instanceof LivingEntity livingTarget) {
                     float healthDifference = targetHealth - livingTarget.getHealth();
                     player.awardStat(Stats.DAMAGE_DEALT, Math.round(healthDifference * 10.0F));
                     if (this.level() instanceof ServerLevel serverLevel && healthDifference > 2.0F) {
                        int particleCount = (int)(healthDifference * 0.5);
                        serverLevel.sendParticles(
                           ParticleTypes.DAMAGE_INDICATOR, targetEntity.getX(), targetEntity.getY(0.5), targetEntity.getZ(), particleCount, 0.1, 0.0, 0.1, 0.2
                        );
                     }
                  }

                  player.causeFoodExhaustion(0.1F);
               } else {
                  player.level()
                     .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0F, 1.0F);
               }
            }

            this.resetModifiers(player, newStack, oldStack);
            player.setItemInHand(InteractionHand.MAIN_HAND, oldStack);
         }
      }
   }

   private void resetModifiers(Player player, ItemStack removeFrom, ItemStack addFrom) {
      removeFrom.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
         AttributeInstance instance = player.getAttribute(attribute);
         if (instance != null && instance.hasModifier(modifier.id())) {
            instance.removeModifier(modifier);
         }
      });
      addFrom.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
         AttributeInstance instance = player.getAttribute(attribute);
         if (instance != null && !instance.hasModifier(modifier.id())) {
            instance.addTransientModifier(modifier);
         }
      });
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setAxeStack(ItemStack.parseOptional(this.registryAccess(), compound.getCompound("axe_stack")));
      this.setVertical(compound.getBoolean("vertical"));
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.put("axe_stack", this.getAxeStack().save(this.registryAccess(), new CompoundTag()));
      compound.putBoolean("vertical", this.getVertical());
   }
}
