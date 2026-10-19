package artifacts.mixin.ability.enchantment;

import artifacts.equipment.EquipmentHelper;
import artifacts.platform.PlatformServices;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
   @ModifyReturnValue(
      method = "getFishingTimeReduction(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;)F",
      at = @At("RETURN")
   )
   private static float increaseFishingTimeReduction(float original, ServerLevel level, ItemStack stack, Entity entity) {
      if (original > 25.0F) {
         return original;
      } else {
         return PlatformServices.platformHelper.isFishingRod(stack) && entity instanceof LivingEntity livingEntity
            ? Math.min(25.0F, original + 5 * EquipmentHelper.getEnchantmentLevelIncrease(Enchantments.LURE, livingEntity))
            : original;
      }
   }

   @ModifyReturnValue(
      method = "getFishingLuckBonus(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;)I",
      at = @At("RETURN")
   )
   private static int increaseFishingLuckBonus(int original, ServerLevel level, ItemStack stack, Entity entity) {
      return PlatformServices.platformHelper.isFishingRod(stack) && entity instanceof LivingEntity livingEntity
         ? original + EquipmentHelper.getEnchantmentLevelIncrease(Enchantments.LUCK_OF_THE_SEA, livingEntity)
         : original;
   }
}
