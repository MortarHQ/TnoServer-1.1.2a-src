package com.chronodawn.items.tools;

import com.chronodawn.core.time.MobAICanceller;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TimeClockItem extends Item {
   public static final int COOLDOWN_TICKS = 200;
   public static final int BOSS_COOLDOWN_TICKS = 600;
   public static final double EFFECT_RADIUS = 8.0;
   public static final double TARGET_RANGE = 32.0;

   public TimeClockItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (player.getCooldowns().isOnCooldown(this)) {
         return InteractionResultHolder.fail(itemStack);
      }

      if (!level.isClientSide) {
         TimeTyrantEntity targetedTyrant = this.getTargetedTimeTyrant(level, player);
         if (targetedTyrant != null) {
            boolean success = targetedTyrant.applyTimeClockWeakening();
            if (success) {
               player.displayClientMessage(Component.translatable("message.chronodawn.time_clock_boss_success"), true);
               player.getCooldowns().addCooldown(this, 600);
               return InteractionResultHolder.success(itemStack);
            }

            player.displayClientMessage(Component.translatable("message.chronodawn.time_clock_boss_limit"), true);
            return InteractionResultHolder.fail(itemStack);
         }

         MobAICanceller.cancelAttackAI(level, player.position(), 8.0);
         player.getCooldowns().addCooldown(this, 200);
      } else {
         player.getCooldowns().addCooldown(this, 200);
      }

      return InteractionResultHolder.success(itemStack);
   }

   private TimeTyrantEntity getTargetedTimeTyrant(Level level, Player player) {
      Vec3 eyePos = player.getEyePosition();
      Vec3 lookVec = player.getLookAngle();
      Vec3 endPos = eyePos.add(lookVec.scale(32.0));
      AABB searchBox = new AABB(eyePos, endPos).inflate(2.0);
      List<TimeTyrantEntity> tyrants = level.getEntitiesOfClass(TimeTyrantEntity.class, searchBox, entity -> entity.distanceToSqr(player) <= 1024.0);
      if (tyrants.isEmpty()) {
         return null;
      }

      TimeTyrantEntity closestTyrant = null;
      double closestDistance = Double.MAX_VALUE;

      for (TimeTyrantEntity tyrant : tyrants) {
         Vec3 tyrantPos = tyrant.position().add(0.0, tyrant.getBbHeight() / 2.0F, 0.0);
         Vec3 toTyrant = tyrantPos.subtract(eyePos).normalize();
         double dotProduct = lookVec.dot(toTyrant);
         if (dotProduct > 0.9) {
            double distance = eyePos.distanceToSqr(tyrantPos);
            if (distance < closestDistance) {
               closestDistance = distance;
               closestTyrant = tyrant;
            }
         }
      }

      return closestTyrant;
   }
}
