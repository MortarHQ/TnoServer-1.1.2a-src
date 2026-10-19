package org.crypticdev.ancient_artifacts.mixin.tensura.storage.ep;

import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.handler.GearHandler;
import io.github.manasmods.tensura.item.weapon.spell.SimpleSpellCastItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import io.github.manasmods.tensura.storage.ep.ExistenceStorage;
import java.util.Map;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactSlot;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExistenceStorage.class)
public class ExistenceStorageMixin {
   @Inject(method = "handleGearEnergyRegen", at = @At("HEAD"))
   private static void artifactRegen(MinecraftServer server, LivingEntity user, double arenaMagicule, CallbackInfo ci) {
      if (user instanceof Player player && !(player.containerMenu instanceof InventoryMenu)) {
         for (ArtifactSlot artifactSlot : ArtifactSlot.values()) {
            Map<Integer, ItemStack> itemStackMap = PlatformEquipment.getArtifactStacksinSlot(user, artifactSlot);

            for (Integer slotIndex : itemStackMap.keySet()) {
               ItemStack stack = PlatformEquipment.getArtifactStackinSlot(user, slotIndex, artifactSlot);
               if (stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
                  if (stack.is(TensuraItemTags.SPELL_CAST_WEAPONS) && server.getTickCount() % 20 == 0) {
                     SimpleSpellCastItem.tickUnlearntInstance(user.level(), stack, user);
                  }

                  if (TensuraEnchantmentHelper.getEnchantmentLevel(user.level(), TensuraEnchantments.RUINATION, stack) <= 0) {
                     int multiplier = 10;
                     int damage = stack.getDamageValue();
                     double durabilityEP = (Double)stack.get((DataComponentType)TensuraDataComponents.EP_DURABILITY.get());
                     double restoration = TensuraEnchantmentHelper.getEnchantmentLevel(user.level(), TensuraEnchantments.RESTORATION, stack);
                     int regen = (int)Math.min(durabilityEP, damage * multiplier);
                     if (damage > 0 && regen / multiplier > 0) {
                        stack.setDamageValue(damage - (int)(regen / multiplier * (1.0 + restoration)));
                        stack.set((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), durabilityEP - regen);
                     } else {
                        double EP = (Double)stack.get((DataComponentType)TensuraDataComponents.EP.get());
                        if (durabilityEP < EP) {
                           stack.set((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), Math.min(EP, durabilityEP + arenaMagicule));
                        }
                     }
                  }
               } else {
                  GearHandler.initiateGearExistence(user.level(), stack);
               }
            }
         }
      }
   }
}
