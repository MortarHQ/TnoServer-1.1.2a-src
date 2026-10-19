package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.collective_common_fabric.functions.TaskFunctions;
import net.minecraft.class_1268;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2680;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = class_1657.class, priority = 999)
public class PlayerMixin {
   @ModifyVariable(
      method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V",
      at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;max(FF)F", ordinal = 0),
      ordinal = 0,
      argsOnly = true
   )
   private float Player_actuallyHurt(float f, class_1282 damageSource, float damage) {
      class_1309 livingEntity = (class_1309)this;
      class_1937 world = livingEntity.method_5770();
      float newDamage = ((CollectiveEntityEvents.Entity_Living_Damage)CollectiveEntityEvents.ON_LIVING_DAMAGE_CALC.invoker())
         .onLivingDamageCalc(world, livingEntity, damageSource, f);
      return newDamage != -1.0F && newDamage != f ? newDamage : f;
   }

   @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
   private void Player_getDestroySpeed(class_2680 blockState, CallbackInfoReturnable<Float> cir, float f) {
      class_1657 player = (class_1657)this;
      float newSpeed = ((CollectivePlayerEvents.Player_Dig_Speed_Calc)CollectivePlayerEvents.ON_PLAYER_DIG_SPEED_CALC.invoker())
         .onDigSpeedCalc(player.method_5770(), player, f, blockState);
      if (newSpeed != -1.0F && newSpeed != f) {
         cir.setReturnValue(newSpeed);
      }
   }

   @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"))
   private void Player_drop(class_1799 itemStack, boolean bl, CallbackInfoReturnable<class_1542> ci) {
      class_1657 player = (class_1657)this;
      ((CollectiveItemEvents.Item_Tossed)CollectiveItemEvents.ON_ITEM_TOSSED.invoker()).onItemTossed(player, itemStack);
   }

   @Inject(method = "hurtCurrentlyUsedShield(F)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
   protected void hurtCurrentlyUsedShield(float f, CallbackInfo ci) {
      class_1657 player = (class_1657)this;
      if (player.method_6030().method_7936() - player.method_6030().method_7919() < 3) {
         class_1268 interactionHand = player.method_6058();
         class_1799 copy = player.method_6030().method_7972();
         TaskFunctions.enqueueTask(player.method_5770(), () -> {
            if (player.method_6030().method_7960()) {
               ((CollectiveItemEvents.Item_Destroyed)CollectiveItemEvents.ON_ITEM_DESTROYED.invoker()).onItemDestroyed(player, copy, interactionHand);
            }
         }, 0);
      }
   }

   @ModifyVariable(
      method = "interactOn(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
      at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/InteractionResult;consumesAction()Z", ordinal = 0),
      ordinal = 1
   )
   public class_1799 Player_interactOn_0(class_1799 itemStack2, class_1297 entity, class_1268 interactionHand) {
      class_1657 player = (class_1657)this;
      class_1799 itemStack = player.method_5998(interactionHand);
      if (itemStack.method_7960() && !player.method_31549().field_7477) {
         ((CollectiveItemEvents.Item_Destroyed)CollectiveItemEvents.ON_ITEM_DESTROYED.invoker()).onItemDestroyed(player, itemStack2, interactionHand);
      }

      return itemStack2;
   }

   @ModifyVariable(
      method = "interactOn(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"
      ),
      ordinal = 1
   )
   public class_1799 Player_interactOn_1(class_1799 itemStack2, class_1297 entity, class_1268 interactionHand) {
      class_1657 player = (class_1657)this;
      class_1799 itemStack = player.method_5998(interactionHand);
      if (itemStack.method_7960() && !player.method_31549().field_7477) {
         ((CollectiveItemEvents.Item_Destroyed)CollectiveItemEvents.ON_ITEM_DESTROYED.invoker()).onItemDestroyed(player, itemStack2, interactionHand);
      }

      return itemStack2;
   }
}
