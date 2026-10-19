package com.notunanancyowen.spears.mixin;

import com.notunanancyowen.spears.Spears;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ZombifiedPiglin.class)
public abstract class ZombifiedPiglinEntityMixin {
   @ModifyArg(
      method = "populateDefaultEquipmentSlots(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/DifficultyInstance;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V")
   )
   private ItemLike spawnWithGoldSpear(ItemLike item) {
      return (ItemLike)(((ZombifiedPiglin)this).getRandom().nextInt(20) == 0 ? Spears.GOLDEN_SPEAR : item);
   }
}
