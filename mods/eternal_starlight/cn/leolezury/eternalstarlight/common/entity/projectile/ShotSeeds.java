package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherAmmoType;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class ShotSeeds extends ThrowableItemProjectile {
   private ItemStack firedFromWeapon;

   public ShotSeeds(EntityType<? extends ShotSeeds> entityType, Level level) {
      super(entityType, level);
   }

   public ShotSeeds(Level level, LivingEntity livingEntity, ItemStack stack, ItemStack weapon) {
      super(ESEntities.SHOT_SEEDS.get(), livingEntity, level);
      this.setItem(stack);
      if (weapon != null) {
         this.firedFromWeapon = weapon.copy();
      }

      if (level instanceof ServerLevel serverLevel && weapon != null) {
         onProjectileSpawned(serverLevel, weapon, this, item -> this.firedFromWeapon = null);
      }
   }

   public ShotSeeds(Level level, double x, double y, double z) {
      super(ESEntities.SHOT_SEEDS.get(), x, y, z, level);
   }

   private static void onProjectileSpawned(ServerLevel serverLevel, ItemStack itemStack, ShotSeeds seeds, Consumer<Item> consumer) {
      LivingEntity living;
      if (seeds.getOwner() instanceof LivingEntity livingEntity) {
         living = livingEntity;
      } else {
         living = null;
      }

      EnchantedItemInUse enchantedItemInUse = new EnchantedItemInUse(itemStack, null, living, consumer);
      EnchantmentHelper.runIterationOnItem(
         itemStack, (holder, i) -> ((Enchantment)holder.value()).onProjectileSpawned(serverLevel, i, enchantedItemInUse, seeds)
      );
   }

   public void tick() {
      super.tick();
      if (this.tickCount > (this.isOnFire() ? 40 : 80)) {
         this.discard();
      }

      if (this.isInWaterOrRain() || this.level().getBlockState(this.blockPosition()).is(Blocks.POWDER_SNOW)) {
         this.clearFire();
      }
   }

   @Nullable
   public ItemStack getWeaponItem() {
      return this.firedFromWeapon;
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      this.discard();
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
      Entity entity = hitResult.getEntity();
      Entity owner = this.getOwner();
      float damage = 0.5F;
      DamageSource source = ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.SEEDS, this, owner);
      if (this.getWeaponItem() != null && this.level() instanceof ServerLevel serverLevel) {
         damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, source, damage);
      }

      damage += (float)(this.getDeltaMovement().length() * 1.25);
      SeedsLauncherAmmoType type = (SeedsLauncherAmmoType)SeedsLauncherAmmoType.getAmmoType(this.level().registryAccess(), this.getItem().getItem()).value();
      damage *= type.damageMultiplier();
      entity.invulnerableTime = 0;
      if (entity.hurt(source, damage)) {
         if (entity instanceof LivingEntity living && this.level() instanceof ServerLevel serverLevel) {
            EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, living, source, this.getWeaponItem());
         }

         if (this.isOnFire()) {
            entity.igniteForSeconds(this.getRemainingFireTicks() / 20.0F / 15.0F);
         }
      }
   }

   protected Item getDefaultItem() {
      return Items.WHEAT_SEEDS;
   }
}
