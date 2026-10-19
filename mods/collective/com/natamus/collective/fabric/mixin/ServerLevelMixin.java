package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import java.util.EnumSet;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_3218;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_3218.class, priority = 1001)
public class ServerLevelMixin {
   @Inject(method = "addEntity(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
   private void serverLevel_addEntity(class_1297 entity, CallbackInfoReturnable<Boolean> ci) {
      class_1937 world = entity.method_5770();
      if (!((CollectiveEntityEvents.Pre_Entity_Join_World)CollectiveEntityEvents.PRE_ENTITY_JOIN_WORLD.invoker()).onPreSpawn(world, entity)) {
         ci.setReturnValue(false);
      }
   }

   @Inject(method = "updateNeighborsAt", at = @At("HEAD"), cancellable = true)
   public void Level_updateNeighborsAt(class_2338 blockPos, class_2248 block, CallbackInfo ci) {
      class_3218 serverLevel = (class_3218)this;
      if (!((CollectiveBlockEvents.On_Neighbour_Notify)CollectiveBlockEvents.NEIGHBOUR_NOTIFY.invoker())
         .onNeighbourNotify(serverLevel, blockPos, serverLevel.method_8320(blockPos), EnumSet.allOf(class_2350.class), false)) {
         ci.cancel();
      }
   }

   @Inject(method = "updateNeighborsAtExceptFromFacing", at = @At("HEAD"), cancellable = true)
   public void Level_updateNeighborsAtExceptFromFacing(class_2338 blockPos, class_2248 block, class_2350 direction, CallbackInfo ci) {
      class_3218 serverLevel = (class_3218)this;
      EnumSet<class_2350> directions = EnumSet.allOf(class_2350.class);
      directions.remove(direction);
      if (!((CollectiveBlockEvents.On_Neighbour_Notify)CollectiveBlockEvents.NEIGHBOUR_NOTIFY.invoker())
         .onNeighbourNotify(serverLevel, blockPos, serverLevel.method_8320(blockPos), directions, false)) {
         ci.cancel();
      }
   }
}
