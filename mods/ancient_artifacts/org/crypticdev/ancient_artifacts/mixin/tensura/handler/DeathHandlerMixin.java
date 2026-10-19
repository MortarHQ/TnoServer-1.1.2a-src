package org.crypticdev.ancient_artifacts.mixin.tensura.handler;

import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.enchantment.EngravingHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.handler.DeathHandler;
import io.github.manasmods.tensura.handler.GearHandler;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Map;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactSlot;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeathHandler.class)
public abstract class DeathHandlerMixin {
   @Shadow
   private static double getGearEPGain(LivingEntity entity, ItemStack stack, double totalEP) {
      return 0.0;
   }

   @Inject(
      method = "lambda$init$1",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/damagesource/DamageSource;getDirectEntity()Lnet/minecraft/world/entity/Entity;",
         shift = Shift.BEFORE
      )
   )
   private static void afterGearLoop(LivingEntity entity, DamageSource source, CallbackInfoReturnable<EventResult> cir) {
      Entity pAttacker = source.getEntity();
      if (pAttacker != entity) {
         if (pAttacker instanceof CloneEntity clone) {
            pAttacker = clone.getOwner();
         }

         if (!(pAttacker instanceof LivingEntity attacker)) {
            attacker = entity.getLastAttacker();
         }

         double EP = EnergyHelper.getEPGain(entity, attacker, false);
         if (!(EP <= 0.0)) {
            if (!TensuraStorages.getExistenceFrom(entity).isSkippingEPDrop()) {
               float multiplier = entity.level().getGameRules().getInt(TensuraGameRules.EP_GAIN_MULTIPLIER);

               for (ArtifactSlot artifactSlot : ArtifactSlot.values()) {
                  Map<Integer, ItemStack> itemStackMap = PlatformEquipment.getArtifactStacksinSlot(attacker, artifactSlot);

                  for (Integer slotIndex : itemStackMap.keySet()) {
                     gearGetEP(attacker, slotIndex, artifactSlot, EP * multiplier);
                  }
               }
            }
         }
      }
   }

   private static void gearGetEP(LivingEntity entity, int index, ArtifactSlot slot, double totalEP) {
      ItemStack stack = PlatformEquipment.getArtifactStackinSlot(entity, index, slot);
      if (stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
         double maxEP = (Double)stack.get((DataComponentType)TensuraDataComponents.MAX_EP.get());
         double currentEP = (Double)stack.get((DataComponentType)TensuraDataComponents.EP.get());
         double newEP = currentEP + getGearEPGain(entity, stack, totalEP);
         if (newEP < maxEP) {
            stack.set((DataComponentType)TensuraDataComponents.EP.get(), newEP);
            EngravingHelper.grantRandomEngraving(entity, stack, newEP);
            GearHandler.applyUniqueGearEvolution(stack, newEP);
         } else {
            ResourceLocation location = (ResourceLocation)stack.get((DataComponentType)TensuraDataComponents.EVOLUTION.get());
            if (location != null && TensuraEnchantmentHelper.getEnchantmentLevel(entity.level(), TensuraEnchantments.STAGNATION, stack) <= 0) {
               newEP = Math.min(newEP, maxEP);
               stack.set((DataComponentType)TensuraDataComponents.EP.get(), newEP);
               EngravingHelper.grantRandomEngraving(entity, stack, newEP);
               ItemStack evolution = new ItemStack(((Item)BuiltInRegistries.ITEM.get(location)).arch$holder(), stack.getCount(), stack.getComponentsPatch());
               GearHandler.initiateGearEvolution(entity.level(), evolution);
               if (entity instanceof Player) {
                  PlatformEquipment.setArtifactInSlot(entity, index, slot, evolution);
               }
            } else if (currentEP != maxEP) {
               newEP = Math.min(newEP, maxEP);
               stack.set((DataComponentType)TensuraDataComponents.EP.get(), newEP);
               EngravingHelper.grantRandomEngraving(entity, stack, newEP);
               GearHandler.applyUniqueGearEvolution(stack, newEP);
            }
         }
      }
   }
}
