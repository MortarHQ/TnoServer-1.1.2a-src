package net.jadenxgamer.netherexp.core.entity;

import java.awt.Color;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class PhasmoPellet extends AbstractPellet {
   public PhasmoPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
      super(entityType, level);
   }

   public PhasmoPellet(double x, double y, double z, Level level) {
      this(JNEEntityType.PHASMO_PELLET.get(), level);
      this.setPos(x, y, z);
   }

   public PhasmoPellet(double x, double y, double z, Level level, Entity owner) {
      this(JNEEntityType.PHASMO_PELLET.get(), level);
      this.setPos(x, y, z);
      this.setOwner(owner);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().getBlockState(this.blockPosition()).isAir()) {
         Vec3 delta = this.getDeltaMovement();
         this.setDeltaMovement(delta.x, delta.y, delta.z);
      }
   }

   @Override
   public Color getTrailColor() {
      return new Color(1255988);
   }

   @Override
   public Color getHitColor() {
      return new Color(2450017);
   }

   protected ItemStack getDefaultPickupItem() {
      return Items.AIR.getDefaultInstance();
   }

   @Override
   protected void onHitBlock(BlockHitResult blockHitResult) {
   }
}
