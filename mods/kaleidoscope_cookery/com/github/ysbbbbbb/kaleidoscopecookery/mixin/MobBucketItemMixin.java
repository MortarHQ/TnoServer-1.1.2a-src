package com.github.ysbbbbbb.kaleidoscopecookery.mixin;

import com.github.ysbbbbbb.kaleidoscopecookery.advancements.critereon.ModEventTrigger;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModTrigger;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobBucketItem.class)
public class MobBucketItemMixin {
   @Inject(method = "checkExtraContent", at = @At("RETURN"))
   private void onCheckExtraContent(Player player, Level level, ItemStack containerStack, BlockPos pos, CallbackInfo ci) {
      if (!level.isClientSide) {
         MobBucketItem bucket = (MobBucketItem)this;
         if (bucket.type.is(TagMod.RICE_GROWTH_BOOSTER)) {
            MutableBlockPos mutable = pos.mutable();

            for (int x = -1; x <= 1; x++) {
               for (int z = -1; z <= 1; z++) {
                  if (level.getBlockState(mutable.offset(x, 0, z)).is((Block)ModBlocks.RICE_CROP.get())) {
                     ((ModEventTrigger)ModTrigger.EVENT.get()).trigger(player, "place_fish_in_rice_field");
                     return;
                  }
               }
            }
         }
      }
   }
}
