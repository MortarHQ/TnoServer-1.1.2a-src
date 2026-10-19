package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.class_1536;
import net.minecraft.class_1799;
import net.minecraft.class_2058;
import net.minecraft.class_3222;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_2058.class, priority = 1001)
public abstract class FishingRodHookedTriggerMixin {
   @Inject(
      method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/FishingHook;Ljava/util/Collection;)V",
      at = @At("HEAD")
   )
   public void trigger(class_3222 serverPlayer, class_1799 itemStack, class_1536 fishingHook, Collection<class_1799> collection, CallbackInfo ci) {
      ((CollectiveItemEvents.Item_Fished)CollectiveItemEvents.ON_ITEM_FISHED.invoker()).onItemFished(new ArrayList<>(collection), fishingHook);
   }
}
