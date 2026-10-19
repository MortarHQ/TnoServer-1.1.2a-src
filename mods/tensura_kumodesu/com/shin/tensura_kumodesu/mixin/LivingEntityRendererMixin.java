package com.shin.tensura_kumodesu.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Optional;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
   @Inject(method = "getShadowRadius(Lnet/minecraft/world/entity/LivingEntity;)F", at = @At("RETURN"), cancellable = true)
   private void tensura_kumodesu$scaleShadow(LivingEntity livingEntity, CallbackInfoReturnable<Float> cir) {
      if (livingEntity instanceof Player player) {
         float shadowSize = (float)player.getAttributeValue(KumodesuAttributes.SHADOW_SIZE);
         cir.setReturnValue(shadowSize * player.getScale());
      }
   }

   @Inject(
      method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"
      )
   )
   private void kumodesu$offsetVanillaModel(T entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
      if (entity instanceof Player player) {
         Optional<ManasRace> raceOpt = RaceAPI.getRaceFrom(player).getRace().map(ManasRaceInstance::getRace);
         if (raceOpt.isEmpty()) {
            return;
         }

         if (raceOpt.get().equals(KumodesuRaces.ARACHNE.get())) {
            poseStack.translate(0.0, -0.7, 0.0);
         }
      }
   }
}
