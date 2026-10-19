package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_1542.class, priority = 1001)
public abstract class ItemEntityMixin extends class_1297 {
   public ItemEntityMixin(class_1299<?> entityType, class_1937 level) {
      super(entityType, level);
   }

   @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;discard()V", ordinal = 1))
   public void ItemEntity_tick(CallbackInfo ci) {
      class_1542 itemEntity = (class_1542)this;
      class_1799 itemStack = itemEntity.method_6983();
      ((CollectiveItemEvents.Item_Expire)CollectiveItemEvents.ON_ITEM_EXPIRE.invoker()).onItemExpire(itemEntity, itemStack);
   }

   @Redirect(
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"),
      method = "playerTouch"
   )
   public boolean playerTouch(class_1661 inventory, class_1799 itemStack) {
      class_1657 player = inventory.field_7546;
      ((CollectivePlayerEvents.Player_Picked_Up_Item)CollectivePlayerEvents.ON_ITEM_PICKED_UP.invoker())
         .onItemPickedUp(player.method_37908(), player, itemStack);
      return inventory.method_7394(itemStack);
   }
}
