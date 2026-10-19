package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.GreenThumbSkillHelper;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

public final class GreenThumbHandler {
   private static final String BLESSED = "TensuraMoreSkillsGreenThumbBlessed";
   private static final String OLD_EP_VALUE = "TensuraMoreSkillsGreenThumbEP";
   private static final String OLD_EP_LIST = "TensuraMoreSkillsGreenThumbEPList";

   private GreenThumbHandler() {
   }

   public static void init() {
      NeoForge.EVENT_BUS.addListener(GreenThumbHandler::onBlockDrops);
      NeoForge.EVENT_BUS.addListener(GreenThumbHandler::onItemCrafted);
      NeoForge.EVENT_BUS.addListener(GreenThumbHandler::onFoodFinish);
   }

   private static void onBlockDrops(BlockDropsEvent event) {
      if (event.getBreaker() instanceof ServerPlayer player) {
         if (hasSkill(player)) {
            if (isHarvestedPlant(event.getState())) {
               for (ItemEntity drop : event.getDrops()) {
                  ItemStack stack = drop.getItem();
                  if (!stack.isEmpty()) {
                     bless(stack);
                  }
               }
            }
         }
      }
   }

   private static void onItemCrafted(ItemCraftedEvent event) {
      if (event.getEntity() instanceof ServerPlayer) {
         ItemStack result = event.getCrafting();
         if (!result.isEmpty() && GreenThumbSkillHelper.isFood(result)) {
            Container inventory = event.getInventory();

            for (int i = 0; i < inventory.getContainerSize(); i++) {
               ItemStack ingredient = inventory.getItem(i);
               if (!ingredient.isEmpty() && isBlessed(ingredient)) {
                  bless(result);
                  return;
               }
            }
         }
      }
   }

   private static void onFoodFinish(Finish event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack stack = event.getItem();
         if (!stack.isEmpty() && GreenThumbSkillHelper.isFood(stack)) {
            if (isBlessed(stack)) {
               int ep = randomEp(player);
               applyEpChange(player, ep);
               if (ep > 0) {
                  player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.ep_gain", new Object[]{ep}), true);
               } else if (ep < 0) {
                  player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.ep_loss", new Object[]{-ep}), true);
               } else {
                  player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.ep_zero"), true);
               }
            }
         }
      }
   }

   private static int randomEp(ServerPlayer player) {
      int min = (Integer)MoreSkillsConfigs.GRAND.greenThumb.eatenEpMinimum.get();
      int max = (Integer)MoreSkillsConfigs.GRAND.greenThumb.eatenEpMaximum.get();
      if (max < min) {
         int swap = min;
         min = max;
         max = swap;
      }

      return min + player.getRandom().nextInt(max - min + 1);
   }

   private static void bless(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.putBoolean("TensuraMoreSkillsGreenThumbBlessed", true);
      tag.remove("TensuraMoreSkillsGreenThumbEP");
      tag.remove("TensuraMoreSkillsGreenThumbEPList");
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      stack.set(
         DataComponents.CUSTOM_NAME,
         Component.translatable("tensuramoreskills.item.green_thumb.blessed", new Object[]{stack.getItem().getDescription()}).withStyle(ChatFormatting.GREEN)
      );
   }

   private static boolean isBlessed(ItemStack stack) {
      if (!stack.has(DataComponents.CUSTOM_DATA)) {
         return false;
      }

      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      return tag.getBoolean("TensuraMoreSkillsGreenThumbBlessed")
         || tag.contains("TensuraMoreSkillsGreenThumbEP", 3)
         || tag.contains("TensuraMoreSkillsGreenThumbEPList", 9);
   }

   private static void applyEpChange(ServerPlayer player, int ep) {
      if (ep != 0) {
         double magicule = EnergyHelper.getBaseMaxMagicule(player);
         double aura = EnergyHelper.getBaseMaxAura(player);
         double total = magicule + aura;
         if (ep > 0) {
            EnergyHelper.setMaxMagicule(player, magicule + ep * 0.5);
            EnergyHelper.setMaxAura(player, aura + ep * 0.5);
            clampEnergy(player);
         } else if (!(total <= 2.0)) {
            double loss = Math.min(-ep, total - 2.0);
            double magiculeRatio = total <= 0.0 ? 0.5 : magicule / total;
            double magiculeLoss = loss * magiculeRatio;
            double auraLoss = loss - magiculeLoss;
            EnergyHelper.setMaxMagicule(player, Math.max(1.0, magicule - magiculeLoss));
            EnergyHelper.setMaxAura(player, Math.max(1.0, aura - auraLoss));
            clampEnergy(player);
         }
      }
   }

   private static void clampEnergy(ServerPlayer player) {
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      existence.setMagicule(Math.min(existence.getMagicule(), EnergyHelper.getMaxMagicule(player)));
      existence.setAura(Math.min(existence.getAura(), EnergyHelper.getMaxAura(player)));
      existence.markDirty();
   }

   private static boolean hasSkill(ServerPlayer player) {
      return SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GREEN_THUMB.get()).isPresent();
   }

   private static boolean isHarvestedPlant(BlockState state) {
      return state.getBlock() instanceof CropBlock crop ? crop.getAge(state) >= crop.getMaxAge() : state.getBlock() instanceof BonemealableBlock;
   }
}
