package com.github.b4ndithelps.tensuraenigmatic.handler;

import com.github.b4ndithelps.tensuraenigmatic.items.RingOfTheElves;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.util.CurioHelper;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class RingOfTheElvesHandler {
   private static final String RING_ENCHANT_TAG = "tensuraenigmatic_ring_protection";
   private static final Map<UUID, Boolean> PLAYER_RING_STATUS = new HashMap<>();
   private static final ResourceKey<Enchantment> PROTECTION_KEY = Enchantments.PROTECTION;

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide() && player.tickCount % 20 == 0) {
         boolean hasRing = CurioHelper.hasCurio(player, TensuraEnigmaticItems.RING_OF_THE_ELVES);
         Boolean previousStatus = PLAYER_RING_STATUS.get(player.getUUID());
         if (previousStatus == null || previousStatus != hasRing) {
            if (hasRing) {
               applyProtectionToArmor(player);
            } else {
               removeProtectionMarkFromArmor(player);
            }

            PLAYER_RING_STATUS.put(player.getUUID(), hasRing);
         }

         if (hasRing) {
            TensuraEnergyHelper.restoreMagicule(player, RingOfTheElves.getMagiculeRecovery());
         }
      }
   }

   private static void applyProtectionToArmor(Player player) {
      Holder<Enchantment> protection = player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(PROTECTION_KEY);

      for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
         ItemStack armorStack = player.getItemBySlot(slot);
         if (!armorStack.isEmpty() && armorStack.getItem() instanceof ArmorItem && !hasRingEnchantTag(armorStack)) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(armorStack);
            int currentLevel = enchantments.getLevel(protection);
            int targetLevel = RingOfTheElves.getProtectionLevel();
            if (currentLevel < targetLevel) {
               EnchantmentHelper.updateEnchantments(armorStack, mutable -> mutable.set(protection, targetLevel));
               addRingEnchantTag(armorStack);
            }
         }
      }
   }

   private static void removeProtectionMarkFromArmor(Player player) {
      for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
         ItemStack armorStack = player.getItemBySlot(slot);
         if (!armorStack.isEmpty() && armorStack.getItem() instanceof ArmorItem && hasRingEnchantTag(armorStack)) {
            removeRingEnchantTag(armorStack);
         }
      }
   }

   private static boolean hasRingEnchantTag(ItemStack stack) {
      return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getBoolean("tensuraenigmatic_ring_protection");
   }

   private static void addRingEnchantTag(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.putBoolean("tensuraenigmatic_ring_protection", true);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   private static void removeRingEnchantTag(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.remove("tensuraenigmatic_ring_protection");
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }
}
