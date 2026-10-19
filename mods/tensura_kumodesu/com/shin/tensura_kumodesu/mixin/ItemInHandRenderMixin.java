package com.shin.tensura_kumodesu.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shin.tensura_kumodesu.races.other.SmallLesserTaratectRace;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import java.util.Optional;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRenderMixin {
   @Inject(
      method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void renderItem(
      LivingEntity livingEntity,
      ItemStack itemStack,
      ItemDisplayContext itemDisplayContext,
      boolean leftHand,
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int i,
      CallbackInfo ci
   ) {
      Player player = this.tensura_kumodesu$resolvePlayer(livingEntity);
      if (player != null) {
         Optional<ManasRace> raceOpt = RaceAPI.getRaceFrom(player).getRace().map(ManasRaceInstance::getRace);
         if (!raceOpt.isEmpty()) {
            ManasRace race = raceOpt.get();
            if (!race.equals(KumodesuRaces.ARACHNE.get()) && race instanceof SmallLesserTaratectRace) {
               ci.cancel();
            }
         }
      }
   }

   @Unique
   private Player tensura_kumodesu$resolvePlayer(LivingEntity entity) {
      if (entity instanceof Player player) {
         return player;
      } else {
         return entity instanceof CloneEntity clone && clone.getOwner() instanceof Player player ? player : null;
      }
   }
}
