package org.crypticdev.ancient_artifacts.mixin.tensura.handler;

import io.github.manasmods.tensura.handler.DamagingHandler;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamagingHandler.class)
public class DamageHandlerMixin {
   @Inject(method = "triggerDodge", at = @At("HEAD"), cancellable = true)
   private static void bypassDodgeBypass(LivingEntity target, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
      if (PlatformEquipment.hasArtifact(target, Artifacts.HornsOfObservation)) {
         ItemStack itemStack = PlatformEquipment.getArtifactStack(target, Artifacts.HornsOfObservation);
         Item item = itemStack.getItem();
         ArtifactItem artifactItem = (ArtifactItem)item;
         if (target instanceof Player player && artifactItem.getArtifact().artifactEffects().get(2).unlockCondition().apply(itemStack, player)) {
            ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
            if (data == null) {
               cir.setReturnValue(false);
            } else if (source.getDirectEntity() != null && source.getDirectEntity() != target) {
               data.setDodgeCooldown(0);
               data.markDirty();
               cir.setReturnValue(true);
            } else {
               cir.setReturnValue(false);
            }
         }
      }
   }
}
