package net.jadenxgamer.netherexp.core.entity;

import java.awt.Color;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.util.BlockCrackTracker;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class SlugPellet extends AbstractPellet {
   public SlugPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
      super(entityType, level);
   }

   public SlugPellet(double x, double y, double z, Level level) {
      this(JNEEntityType.SLUG_PELLET.get(), level);
      this.setPos(x, y, z);
   }

   public SlugPellet(double x, double y, double z, Level level, Entity owner) {
      this(JNEEntityType.SLUG_PELLET.get(), level);
      this.setPos(x, y, z);
      this.setOwner(owner);
   }

   @Override
   public Color getTrailColor() {
      return new Color(16756224);
   }

   @Override
   public Color getHitColor() {
      return new Color(16756224);
   }

   @Override
   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      BlockCrackTracker.onBlockHit(
         this.level(), result.getBlockPos(), this.level().getBlockState(result.getBlockPos()), (Double)JNEConfigs.SLUG_BLOCK_DAMAGE_STRENGTH.get()
      );
   }

   protected ItemStack getDefaultPickupItem() {
      return Items.AIR.getDefaultInstance();
   }

   @Override
   protected ResourceKey<DamageType> getDamageSource() {
      return JNEDamageSources.SLUG_PELLET;
   }
}
