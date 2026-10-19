package com.iafenvoy.uranus.mixin;

import com.iafenvoy.uranus.object.item.ItemBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
   @Shadow
   public abstract Level level();

   @Inject(method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"))
   private void onDropItem(ItemStack stack, float yOffset, CallbackInfoReturnable<ItemEntity> cir) {
      if (stack.getItem() instanceof ItemBase itemBase) {
         Entity var7 = (Entity)this;
         if (var7 instanceof Player player) {
            itemBase.onDroppedByPlayer(stack, player);
         }
      }
   }
}
