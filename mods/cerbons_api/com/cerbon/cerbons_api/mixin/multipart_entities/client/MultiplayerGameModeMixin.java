package com.cerbon.cerbons_api.mixin.multipart_entities.client;

import com.cerbon.cerbons_api.api.multipart_entities.client.PlayerInteractMultipartEntity;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.packet.custom.MultipartEntityInteractionC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiplayerGameModeMixin {
   @Shadow
   private GameType localPlayerMode;

   @Shadow
   protected abstract void ensureHasSentCarriedItem();

   @Inject(method = "attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
   private void attackHook(Player player, Entity target, CallbackInfo ci) {
      if (target instanceof MultipartAwareEntity multipartAwareEntity) {
         this.ensureHasSentCarriedItem();
         Minecraft client = Minecraft.getInstance();
         Vec3 pos = client.cameraEntity.getEyePosition(client.getTimer().getGameTimeDeltaTicks());
         Vec3 dir = client.cameraEntity.getViewVector(client.getTimer().getGameTimeDeltaTicks());
         double reach = client.player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
         String part = multipartAwareEntity.getBounds().raycast(pos, pos.add(dir.scale(reach)));
         if (part == null) {
            return;
         }

         Dispatcher.sendToServer(
            new MultipartEntityInteractionC2SPacket(
               target.getId(), part, InteractionHand.MAIN_HAND, client.cameraEntity.isShiftKeyDown(), PlayerInteractMultipartEntity.InteractionType.ATTACK
            )
         );
         if (this.localPlayerMode == GameType.SPECTATOR) {
            return;
         }

         multipartAwareEntity.setNextDamagedPart(part);
         player.attack(target);
         player.resetAttackStrengthTicker();
         ci.cancel();
      }
   }

   @Inject(
      method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void interactHook(Player player, Entity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
      if (entity instanceof MultipartAwareEntity multipartAwareEntity) {
         this.ensureHasSentCarriedItem();
         Minecraft client = Minecraft.getInstance();
         Vec3 pos = client.cameraEntity.getEyePosition(client.getTimer().getGameTimeDeltaTicks());
         Vec3 dir = client.cameraEntity.getViewVector(client.getTimer().getGameTimeDeltaTicks());
         double reach = client.player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
         String part = multipartAwareEntity.getBounds().raycast(pos, pos.add(dir.scale(reach)));
         if (part == null) {
            return;
         }

         Dispatcher.sendToServer(
            new MultipartEntityInteractionC2SPacket(
               entity.getId(), part, hand, client.cameraEntity.isShiftKeyDown(), PlayerInteractMultipartEntity.InteractionType.INTERACT
            )
         );
         if (this.localPlayerMode != GameType.SPECTATOR) {
            cir.setReturnValue(multipartAwareEntity.interact(player, hand, part));
         }

         cir.setReturnValue(InteractionResult.PASS);
      }
   }
}
