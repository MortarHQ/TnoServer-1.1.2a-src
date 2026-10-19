package net.jadenxgamer.netherexp.core.entity;

import javax.annotation.Nullable;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BlackIcicle extends AbstractArrow {
   public BlackIcicle(EntityType<? extends AbstractArrow> entityType, Level level) {
      super(entityType, level);
   }

   public BlackIcicle(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
      super(JNEEntityType.BLACK_ICICLE.get(), owner, level, pickupItemStack, firedFromWeapon);
   }

   public BlackIcicle(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
      super(JNEEntityType.BLACK_ICICLE.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide && !this.inGround) {
         this.level().addParticle((ParticleOptions)JNEParticleTypes.BLACK_FLAKE.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
      }
   }

   private void shatterIcicle() {
      this.playSound(JNESoundEvents.BLACK_ICE_BREAK.get(), 1.0F, 1.4F);
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 8; i++) {
            serverLevel.sendParticles(
               new BlockParticleOption(ParticleTypes.BLOCK, JNEBlocks.BLACK_ICICLE.get().defaultBlockState()),
               this.getX(),
               this.getY(),
               this.getZ(),
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }
      }

      this.discard();
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      this.shatterIcicle();
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (result.getEntity() instanceof LivingEntity victim) {
         victim.setTicksFrozen(victim.getTicksFrozen() + (Integer)JNEConfigs.BLACK_ICE_FREEZING_TICKS.get());
      }

      this.shatterIcicle();
   }

   protected double getDefaultGravity() {
      return 0.15;
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)JNEBlocks.BLACK_ICICLE.get());
   }
}
