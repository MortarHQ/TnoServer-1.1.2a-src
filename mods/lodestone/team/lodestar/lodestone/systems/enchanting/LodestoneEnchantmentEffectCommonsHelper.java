package team.lodestar.lodestone.systems.enchanting;

import java.util.function.Predicate;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LodestoneEnchantmentEffectCommonsHelper {
   public static boolean isChargedAttack(LivingEntity attacker) {
      return isChargedAttack(attacker, 0.9F);
   }

   public static boolean isChargedAttack(LivingEntity attacker, float threshold) {
      return attacker instanceof Player player ? !(player.getAttackStrengthScale(0.5F) <= threshold) : true;
   }

   public static boolean disableShield(LivingEntity shieldUser, ItemStack stack, int duration) {
      return disableShield(shieldUser, stack, duration, true);
   }

   public static boolean disableShield(LivingEntity shieldUser, ItemStack stack, int duration, boolean swing) {
      if (shieldUser instanceof Player player) {
         Item shieldItem = stack.getItem();
         boolean wasOnCooldown = player.getCooldowns().isOnCooldown(shieldItem);
         player.stopUsingItem();
         if (!player.isCreative() && !wasOnCooldown) {
            player.getCooldowns().addCooldown(shieldItem, duration);
         }

         return !wasOnCooldown;
      } else {
         if (swing) {
            InteractionHand hand = shieldUser.getMainHandItem().equals(stack) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            shieldUser.swing(hand, true);
         }

         return true;
      }
   }

   public static Predicate<LivingEntity> attackPredicate(LivingEntity attacker) {
      return entity -> {
         if (entity.isSpectator()) {
            return false;
         } else if (entity == attacker) {
            return false;
         } else if (attacker.isAlliedTo(entity)) {
            return false;
         } else {
            return entity instanceof TamableAnimal tamableanimal && tamableanimal.isTame() && attacker.getUUID().equals(tamableanimal.getOwnerUUID())
               ? false
               : !(entity instanceof ArmorStand armorstand && armorstand.isMarker());
         }
      };
   }
}
