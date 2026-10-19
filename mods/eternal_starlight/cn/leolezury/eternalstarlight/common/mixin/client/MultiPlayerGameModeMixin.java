package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
   @Inject(
      method = "performUseItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void performUseItemOn(
      LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir
   ) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         cir.setReturnValue(InteractionResult.SUCCESS);
         cir.cancel();
      }
   }

   @Inject(
      method = "useItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void useItem(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         cir.setReturnValue(InteractionResult.PASS);
         cir.cancel();
      }
   }

   @Inject(method = "attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
   private void attack(Player player, Entity entity, CallbackInfo ci) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         ci.cancel();
      }
   }

   @Inject(
      method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void interact(Player player, Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         cir.setReturnValue(InteractionResult.PASS);
         cir.cancel();
      }
   }

   @Inject(method = "startDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At("HEAD"), cancellable = true)
   private void startDestroyBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         cir.setReturnValue(false);
         cir.cancel();
      }
   }

   @Inject(method = "continueDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At("HEAD"), cancellable = true)
   private void continueDestroyBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         cir.setReturnValue(false);
         cir.cancel();
      }
   }

   @Inject(method = "destroyBlock(Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
   private void destroyBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
      if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
         cir.setReturnValue(false);
         cir.cancel();
      }
   }
}
