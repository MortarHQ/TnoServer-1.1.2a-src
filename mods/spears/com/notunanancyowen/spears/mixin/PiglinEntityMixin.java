package com.notunanancyowen.spears.mixin;

import com.notunanancyowen.spears.Spears;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Piglin.class)
public abstract class PiglinEntityMixin {
   @ModifyArg(
      method = "createSpawnWeapon()Lnet/minecraft/world/item/ItemStack;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V", ordinal = 1)
   )
   private ItemLike spawnWithGoldSpear(ItemLike item) {
      return (ItemLike)(((Piglin)this).getRandom().nextInt(10) == 0 ? Spears.GOLDEN_SPEAR : item);
   }
}
