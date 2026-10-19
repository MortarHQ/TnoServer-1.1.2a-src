package com.b1n_ry.yigd.mixin;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.GraveStatus;
import com.b1n_ry.yigd.util.GraveCompassHelper;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompassItem.class)
public class CompassItemMixin {
   @Inject(method = "inventoryTick", at = @At("TAIL"))
   private void onInventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
      if (!world.isClientSide) {
         if (stack.is(Items.COMPASS)) {
            if (YigdConfig.getConfig().extraFeatures.graveCompass.deleteWhenUnlinked) {
               UUID graveId = (UUID)stack.get(Yigd.GRAVE_ID);
               if (graveId != null) {
                  if (world.getGameTime() % 200L == 0L) {
                     GraveCompassHelper.updateClosestNbt(world.dimension(), entity.blockPosition(), entity.getUUID(), stack);
                  }

                  DeathInfoManager.INSTANCE.getGrave(graveId).ifPresent(grave -> {
                     if (grave.getStatus() != GraveStatus.UNCLAIMED) {
                        stack.setCount(0);
                     }
                  });
               }
            }
         }
      }
   }
}
