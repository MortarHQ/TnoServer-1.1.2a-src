package com.chronodawn.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class UnstableHourglassItem extends Item {
   public UnstableHourglassItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1);
   }

   public void onCraftedBy(ItemStack stack, Level level, Player player) {
      super.onCraftedBy(stack, level, player);
      if (!level.isClientSide) {
         this.triggerReversedResonance(level, player);
      }
   }

   private void triggerReversedResonance(Level level, Player player) {
      int EFFECT_DURATION = 900;
      double MOB_EFFECT_RADIUS = 16.0;
      MobEffectInstance playerSlowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 3, false, true);
      player.addEffect(playerSlowness);
      Vec3 playerPos = player.position();
      AABB boundingBox = new AABB(playerPos.x - 16.0, playerPos.y - 16.0, playerPos.z - 16.0, playerPos.x + 16.0, playerPos.y + 16.0, playerPos.z + 16.0);

      for (Mob mob : level.getEntitiesOfClass(Mob.class, boundingBox, mobx -> mobx.distanceToSqr(playerPos) <= 256.0)) {
         MobEffectInstance mobSpeed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 1, false, false);
         mob.addEffect(mobSpeed);
      }
   }
}
