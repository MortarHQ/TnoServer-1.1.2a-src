package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESEnchantments;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class ThrownBoomerang extends AbstractArrow {
   private static final String TAG_DEALT_DAMAGE = "dealt_damage";
   private boolean dealtDamage;

   public ThrownBoomerang(EntityType<? extends ThrownBoomerang> type, Level level) {
      super(type, level);
   }

   public ThrownBoomerang(
      EntityType<? extends ThrownBoomerang> type, Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack
   ) {
      super(type, x, y, z, level, pickupItemStack, pickupItemStack);
      this.setOwner(owner);
   }

   public void tick() {
      Entity owner = this.getOwner();
      if (this.inGround || owner == null && this.tickCount > 40 || owner != null && this.distanceTo(owner) > 15.0F + owner.getBbWidth() / 2.0F) {
         this.dealtDamage = true;
      }

      if (!this.level().isClientSide && !this.dealtDamage) {
         float homing = 0.0F;
         if (this.level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
            homing = ESEnchantments.modifyBoomerangHomingStrength(serverLevel, this.getWeaponItem(), homing);
         }

         homing = Mth.clamp(homing, 0.0F, 1.0F);
         Vec3 homingTarget = null;

         for (LivingEntity living : this.level()
            .getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0))
            .stream()
            .sorted(Comparator.comparingDouble(i -> i.distanceTo(this)))
            .toList()) {
            if (ESEntityUtil.shouldHarm(owner, living)) {
               homingTarget = living.position().add(0.0, living.getBbHeight() / 2.0F, 0.0);
               break;
            }
         }

         if (homingTarget != null && homing > 0.0F && this.getDeltaMovement().normalize().dot(homingTarget.subtract(this.position()).normalize()) > 0.5) {
            this.setDeltaMovement(
               this.getDeltaMovement()
                  .normalize()
                  .scale(1.0F - homing)
                  .add(homingTarget.subtract(this.position()).normalize().scale(homing))
                  .normalize()
                  .scale(this.getDeltaMovement().length())
            );
         }
      }

      if (owner instanceof Player player) {
         float pickupRadius = 0.0F;
         if (this.level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
            pickupRadius = ESEnchantments.modifyBoomerangPickupRadius(serverLevel, this.getWeaponItem(), pickupRadius);
         }

         if (pickupRadius > 0.0F) {
            for (ItemEntity item : this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(pickupRadius))) {
               item.playerTouch(player);
            }
         }
      }

      if ((this.dealtDamage || this.isNoPhysics()) && owner != null) {
         this.inGround = false;
         if (!(owner instanceof Player) && !this.level().isClientSide) {
            this.discard();
         }

         if (!this.isAcceptableReturnOwner()) {
            if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
               this.spawnAtLocation(this.getPickupItem(), 0.1F);
            }

            this.discard();
         } else {
            this.setNoPhysics(true);
            Vec3 diff = owner.getEyePosition().subtract(this.position());
            double speed = Math.max(this.getDeltaMovement().length(), 1.5);
            if (speed > diff.length()) {
               speed = diff.length();
            }

            this.setDeltaMovement(diff.normalize().scale(speed));
         }
      }

      super.tick();
   }

   private boolean isAcceptableReturnOwner() {
      Entity entity = this.getOwner();
      return entity != null && entity.isAlive() ? !(entity instanceof ServerPlayer) || !entity.isSpectator() : false;
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 vec3, Vec3 vec32) {
      return this.dealtDamage ? null : super.findHitEntity(vec3, vec32);
   }

   private double getItemDamage(AttributeInstance instance) {
      double baseValue = instance != null ? instance.getBaseValue() : 1.0;
      ItemStack weapon = this.getWeaponItem();
      if (weapon == null) {
         return baseValue;
      }

      double result = baseValue;
      List<AttributeModifier> allModifiers = new ArrayList<>();
      List<AttributeModifier> originalModifiers = instance != null ? new ArrayList<>(instance.getModifiers()) : new ArrayList<>();
      ItemAttributeModifiers modifiers = (ItemAttributeModifiers)weapon.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
      originalModifiers.removeIf(
         o -> modifiers.modifiers()
            .stream()
            .anyMatch(modifierx -> modifierx.attribute().is(Attributes.ATTACK_DAMAGE) && modifierx.modifier().id().equals(o.id()))
      );

      for (Entry entry : modifiers.modifiers()) {
         if (entry.attribute().is(Attributes.ATTACK_DAMAGE)) {
            allModifiers.addFirst(entry.modifier());
         }
      }

      allModifiers.addAll(originalModifiers);

      for (AttributeModifier modifier : allModifiers) {
         double amount = modifier.amount();

         result += switch (modifier.operation()) {
            case ADD_VALUE -> amount;
            case ADD_MULTIPLIED_BASE -> amount * baseValue;
            case ADD_MULTIPLIED_TOTAL -> amount * result;
            default -> throw new MatchException(null, null);
         };
      }

      return result;
   }

   protected void onHitEntity(EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.getEntity();
      float damage = (float)this.getItemDamage(this.getOwner() instanceof LivingEntity living ? living.getAttribute(Attributes.ATTACK_DAMAGE) : null);
      DamageSource damageSource = this.damageSources().thrown(this, this.getOwner());
      float critChance = 0.0F;
      boolean critSuccess = false;
      if (this.level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
         damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damage);
         critChance = ESEnchantments.modifyBoomerangCritChance(serverLevel, this.getWeaponItem(), entity, damageSource, critChance);
      }

      if (this.getRandom().nextFloat() < critChance) {
         damage *= 1.5F;
         critSuccess = true;
      }

      this.dealtDamage = true;
      if (entity.hurt(damageSource, damage)) {
         if (entity.getType() == EntityType.ENDERMAN) {
            return;
         }

         if (this.level() instanceof ServerLevel serverLevel) {
            DamageSource directSource = this.getOwner() instanceof Player player
               ? this.damageSources().playerAttack(player)
               : (this.getOwner() instanceof LivingEntity living ? this.damageSources().mobAttack(living) : damageSource);
            EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, directSource, this.getWeaponItem());
         }

         if (entity instanceof LivingEntity livingEntity) {
            this.doKnockback(livingEntity, damageSource);
            this.doPostHurtEffects(livingEntity);
         }

         if (critSuccess && this.getOwner() instanceof Player player) {
            player.crit(entity);
         }
      }

      this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0F, 1.0F);
   }

   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return SoundEvents.PLAYER_ATTACK_CRIT;
   }

   protected boolean tryPickup(Player player) {
      return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
   }

   public void playerTouch(Player player) {
      if (this.ownedBy(player) || this.getOwner() == null) {
         super.playerTouch(player);
      }
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.dealtDamage = compoundTag.getBoolean("dealt_damage");
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("dealt_damage", this.dealtDamage);
   }

   protected float getWaterInertia() {
      return 0.99F;
   }

   public boolean shouldRender(double d, double e, double f) {
      return true;
   }
}
