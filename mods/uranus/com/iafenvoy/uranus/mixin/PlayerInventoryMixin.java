package com.iafenvoy.uranus.mixin;

import com.iafenvoy.uranus.object.item.ArmorWithTickItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {
   @Final
   @Shadow
   public Player player;

   @Deprecated(forRemoval = true)
   @Inject(method = "tick()V", at = @At("RETURN"))
   private void onInventoryTick(CallbackInfo ci) {
      ArmorWithTickItem.doTick(this.player.level(), this.player);
   }
}
