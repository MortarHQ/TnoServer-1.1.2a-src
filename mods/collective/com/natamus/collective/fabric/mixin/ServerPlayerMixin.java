package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import net.minecraft.class_1282;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5454;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_3222.class, priority = 1001)
public class ServerPlayerMixin {
   @Inject(method = "tick()V", at = @At("HEAD"))
   public void ServerPlayer_tick(CallbackInfo ci) {
      class_3222 player = (class_3222)this;
      class_3218 world = (class_3218)player.method_5770();
      ((CollectivePlayerEvents.Player_Tick)CollectivePlayerEvents.PLAYER_TICK.invoker()).onTick(world, player);
   }

   @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"))
   public void ServerPlayer_die(class_1282 damageSource, CallbackInfo ci) {
      class_3222 player = (class_3222)this;
      class_3218 world = (class_3218)player.method_5770();
      ((CollectivePlayerEvents.Player_Death)CollectivePlayerEvents.PLAYER_DEATH.invoker()).onDeath(world, player);
   }

   @Inject(method = "changeDimension(Lnet/minecraft/world/level/portal/DimensionTransition;)Lnet/minecraft/world/entity/Entity;", at = @At("RETURN"))
   public void ServerPlayer_changeDimension(class_5454 dimensionTransition, CallbackInfoReturnable<Boolean> ci) {
      class_3222 player = (class_3222)this;
      ((CollectivePlayerEvents.Player_Change_Dimension)CollectivePlayerEvents.PLAYER_CHANGE_DIMENSION.invoker())
         .onChangeDimension(player.method_51469(), player);
   }

   @Inject(
      method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z")
   )
   private void ServerPlayer_drop(class_1799 itemStack, boolean bl, boolean bl2, CallbackInfoReturnable<class_1542> ci) {
      class_1657 player = (class_1657)this;
      ((CollectiveItemEvents.Item_Tossed)CollectiveItemEvents.ON_ITEM_TOSSED.invoker()).onItemTossed(player, itemStack);
   }
}
