package com.shin.tensura_kumodesu.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shin.tensura_kumodesu.races.other.SmallLesserTaratectRace;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Optional;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class RenderArmourMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
   @Inject(
      method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
      at = @At("HEAD"),
      cancellable = true
   )
   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      T livingEntity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      CallbackInfo ci
   ) {
      if (livingEntity instanceof Player player) {
         Optional<ManasRace> raceOpt = RaceAPI.getRaceFrom(player).getRace().map(ManasRaceInstance::getRace);
         if (!raceOpt.isEmpty()) {
            ManasRace race = raceOpt.get();
            if (!race.equals(KumodesuRaces.ARACHNE.get()) && race instanceof SmallLesserTaratectRace) {
               ci.cancel();
            }
         }
      }
   }

   @Inject(
      method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void renderArmorPiece(
      PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot slot, int packedLight, HumanoidModel<T> model, CallbackInfo ci
   ) {
      if (entity instanceof Player player) {
         Optional<ManasRace> raceOpt = RaceAPI.getRaceFrom(player).getRace().map(ManasRaceInstance::getRace);
         if (!raceOpt.isEmpty()) {
            ManasRace race = raceOpt.get();
            if (race.equals(KumodesuRaces.ARACHNE.get()) && (slot == EquipmentSlot.FEET || slot == EquipmentSlot.LEGS)) {
               ci.cancel();
            }
         }
      }
   }
}
