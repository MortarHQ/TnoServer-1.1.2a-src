package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveAttackEvents;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1271;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_1753.class, priority = 1001)
public class BowItemMixin {
   @Inject(
      method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z"),
      cancellable = true
   )
   public void use(class_1937 level, class_1657 player, class_1268 interactionHand, CallbackInfoReturnable<class_1271<class_1799>> cir) {
      class_1799 itemStack = player.method_5998(interactionHand);
      class_1271<class_1799> resultHolder = ((CollectiveAttackEvents.On_Arrow_Nock)CollectiveAttackEvents.ON_ARROW_NOCK.invoker())
         .onArrowNock(itemStack, level, player, interactionHand, !player.method_18808(itemStack).method_7960());
      if (!resultHolder.method_5467().equals(class_1269.field_5811)) {
         cir.setReturnValue(resultHolder);
      }
   }
}
