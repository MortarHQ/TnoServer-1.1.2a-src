package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESFoods;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Item.class)
public abstract class ItemMixin {
   @ModifyVariable(
      method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
      at = @At(value = "STORE", ordinal = 0),
      ordinal = 0
   )
   private FoodProperties modifyFoodProperties(FoodProperties value, @Local(argsOnly = true) Player player) {
      return value == null
            && ESAccessoryUtil.getActiveAccessoriesOnArmors(player).contains(ESItems.FUNGUS_AMULET.get())
            && ((Item)this).builtInRegistryHolder().is(ESTags.Items.CONSUMABLE_WHEN_WEARING_FUNGUS_AMULET)
         ? ESFoods.FUNGUS.get()
         : value;
   }

   @ModifyVariable(
      method = {
            "finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;",
            "getUseDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"
      },
      at = @At(value = "STORE", ordinal = 0),
      ordinal = 0
   )
   private FoodProperties modifyFoodProperties(FoodProperties value, @Local(argsOnly = true) LivingEntity entity) {
      return value == null
            && ESAccessoryUtil.getActiveAccessoriesOnArmors(entity).contains(ESItems.FUNGUS_AMULET.get())
            && ((Item)this).builtInRegistryHolder().is(ESTags.Items.CONSUMABLE_WHEN_WEARING_FUNGUS_AMULET)
         ? ESFoods.FUNGUS.get()
         : value;
   }
}
