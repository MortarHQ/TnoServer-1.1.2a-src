package com.b1n_ry.yigd.events;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.config.ExtraFeaturesConfig;
import com.b1n_ry.yigd.config.GraveConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.ListMode;
import com.b1n_ry.yigd.util.DropRule;
import com.b1n_ry.yigd.util.GraveCompassHelper;
import com.b1n_ry.yigd.util.GraveOverrideAreas;
import com.b1n_ry.yigd.util.YigdTags;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;

public class YigdServerEventHandler {
   @SubscribeEvent
   public void dropRuleEvent(YigdEvents.DropRuleEvent event) {
      ItemStack item = event.getStack();
      int slot = event.getSlot();
      DeathContext context = event.getDeathContext();
      boolean modify = event.isModify();
      YigdConfig config = YigdConfig.getConfig();
      MobEffect statusEffect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.fromNamespaceAndPath("amethyst_imbuement", "soulbinding"));
      if (statusEffect != null) {
         Holder<MobEffect> statusEffectEntry = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(statusEffect);
         if (context != null && context.player().hasEffect(statusEffectEntry)) {
            event.setDropRule(DropRule.KEEP);
            event.setCanceled(true);
            return;
         }
      }

      if (config.inventoryConfig.soulboundSlots.contains(slot)) {
         event.setDropRule(DropRule.KEEP);
         event.setCanceled(true);
      } else if (config.inventoryConfig.vanishingSlots.contains(slot)) {
         event.setDropRule(DropRule.DESTROY);
         event.setCanceled(true);
      } else if (config.inventoryConfig.dropOnGroundSlots.contains(slot)) {
         event.setDropRule(DropRule.DROP);
         event.setCanceled(true);
      } else if (item.is(YigdTags.NATURAL_SOULBOUND)) {
         event.setDropRule(DropRule.KEEP);
         event.setCanceled(true);
      } else if (item.is(YigdTags.NATURAL_VANISHING)) {
         event.setDropRule(DropRule.DESTROY);
         event.setCanceled(true);
      } else if (item.is(YigdTags.GRAVE_INCOMPATIBLE)) {
         event.setDropRule(DropRule.DROP);
         event.setCanceled(true);
      } else {
         if (!item.isEmpty() && item.has(DataComponents.CUSTOM_DATA)) {
            CustomData nbt = (CustomData)item.get(DataComponents.CUSTOM_DATA);
            assert nbt != null;
            CompoundTag itemNbt = nbt.copyTag();
            if (itemNbt.contains("Botania_keepIvy") && itemNbt.getBoolean("Botania_keepIvy")) {
               if (modify) {
                  CustomData replaced = nbt.update(nbtCompound -> nbtCompound.remove("Botania_keepIvy"));
                  item.set(DataComponents.CUSTOM_DATA, replaced);
               }

               event.setDropRule(DropRule.KEEP);
               event.setCanceled(true);
               return;
            }
         }

         DropRule dropRule;
         if (context != null) {
            dropRule = GraveOverrideAreas.INSTANCE.getDropRuleFromArea(BlockPos.containing(context.deathPos()), context.world());
         } else {
            dropRule = GraveOverrideAreas.INSTANCE.defaultDropRule;
         }

         if (EnchantmentHelper.hasTag(item, YigdTags.VANISHING)) {
            event.setDropRule(DropRule.DESTROY);
            event.setCanceled(true);
         } else if (!EnchantmentHelper.hasTag(item, YigdTags.SOULBOUND)) {
            event.setDropRule(dropRule);
         } else {
            if (config.inventoryConfig.loseSoulboundLevelOnDeath) {
               for (Holder<Enchantment> enchantmentRegistryEntry : EnchantmentHelper.getEnchantmentsForCrafting(item).keySet()) {
                  if (enchantmentRegistryEntry.is(YigdTags.SOULBOUND)) {
                     int level = EnchantmentHelper.getTagEnchantmentLevel(enchantmentRegistryEntry, item);
                     if (level > 1) {
                        EnchantmentHelper.updateEnchantments(item, builder -> builder.set(enchantmentRegistryEntry, level - 1));
                     } else {
                        EnchantmentHelper.updateEnchantments(item, builder -> builder.removeIf(enchant -> enchant.equals(enchantmentRegistryEntry)));
                     }
                  }
               }
            }

            event.setDropRule(DropRule.KEEP);
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void graveClaimEvent(YigdEvents.GraveClaimEvent event) {
      ServerPlayer player = event.getPlayer();
      ServerLevel level = event.getLevel();
      GraveComponent grave = event.getGrave();
      ItemStack tool = event.getTool();
      if (player.isDeadOrDying()) {
         event.setCanClaim(false);
         event.setCanceled(true);
      } else {
         YigdConfig config = YigdConfig.getConfig();
         if (config.extraFeatures.graveCompass.consumeOnUse
            || config.extraFeatures.graveCompass.pointToClosest != ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED) {
            Inventory inventory = player.getInventory();

            for (int i = 0; i < inventory.getContainerSize(); i++) {
               ItemStack stack = inventory.getItem(i);
               if (stack.is(Items.COMPASS)) {
                  if (config.extraFeatures.graveCompass.consumeOnUse) {
                     CustomData stackNbtComponent = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
                     CompoundTag stackNbt = stackNbtComponent != null ? stackNbtComponent.copyTag() : null;
                     if (stack.is(Items.COMPASS) && stackNbt != null && stackNbt.contains("linked_grave")) {
                        UUID graveId = stackNbt.getUUID("linked_grave");
                        if (graveId.equals(grave.getGraveId())) {
                           stack.setCount(0);
                           break;
                        }
                     }
                  } else {
                     GraveCompassHelper.updateClosestNbt(level.dimension(), player.blockPosition(), player.getUUID(), stack);
                  }
               }
            }
         }

         if (config.extraFeatures.graveKeys.enabled) {
            if (tool.is(Yigd.GRAVE_KEY_ITEM)) {
               CustomData nbtComponent = (CustomData)tool.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
               CompoundTag nbt = nbtComponent.copyTag();
               CompoundTag userNbt = nbt.getCompound("user");
               Tag uuidNbt = nbt.get("grave");
               ExtraFeaturesConfig.GraveKeyConfig.KeyTargeting targeting = config.extraFeatures.graveKeys.targeting;
               switch (targeting) {
                  case ANY_GRAVE:
                     tool.shrink(1);
                     event.setCanClaim(true);
                     event.setCanceled(true);
                     return;
                  case PLAYER_GRAVE:
                     if (Objects.equals(ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, userNbt).result().orElse(null), grave.getOwner())) {
                        tool.shrink(1);
                        event.setCanClaim(true);
                        event.setCanceled(true);
                        return;
                     }
                     break;
                  case SPECIFIC_GRAVE:
                     if (uuidNbt != null && Objects.equals(NbtUtils.loadUUID(uuidNbt), grave.getGraveId())) {
                        tool.shrink(1);
                        event.setCanClaim(true);
                        event.setCanceled(true);
                        return;
                     }
               }
            }

            if (config.extraFeatures.graveKeys.required) {
               player.sendSystemMessage(Component.translatable("text.yigd.message.missing_key"), true);
               event.setCanClaim(false);
               return;
            }
         }

         if (config.graveConfig.requireShovelToLoot && !tool.is(ItemTags.SHOVELS)) {
            player.sendSystemMessage(Component.translatable("text.yigd.message.no_shovel"), true);
            event.setCanClaim(false);
         } else if (player.getUUID().equals(grave.getOwner().id().orElse(null))) {
            event.setCanClaim(true);
         } else if (!grave.isLocked()) {
            event.setCanClaim(true);
         } else {
            GraveConfig.GraveRobbing robConfig = config.graveConfig.graveRobbing;
            if (!robConfig.enabled) {
               event.setCanClaim(false);
            } else if (robConfig.killerSkipWaitTime && player.getUUID().equals(grave.getKillerId())) {
               event.setCanClaim(true);
            } else {
               int tps = 20;
               if (!grave.hasExistedTicks(robConfig.timeUnit.toSeconds(robConfig.afterTime) * 20L)) {
                  player.sendSystemMessage(Component.translatable("text.yigd.message.rob.too_early", new Object[]{grave.getTimeUntilRobbable()}), true);
                  event.setCanClaim(false);
               } else {
                  event.setCanClaim(true);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void allowGraveGenerationEvent(YigdEvents.AllowGraveGenerationEvent event) {
      DeathContext context = event.getDeathContext();
      GraveComponent grave = event.getGrave();
      GraveConfig graveConfig = YigdConfig.getConfig().graveConfig;
      if (!graveConfig.enabled) {
         event.setAllowGeneration(false);
      } else if ((
            DeathInfoManager.INSTANCE.getGraveListMode() != ListMode.WHITELIST
               || DeathInfoManager.INSTANCE.isInList(new ResolvableProfile(context.player().getGameProfile()))
         )
         && (
            DeathInfoManager.INSTANCE.getGraveListMode() != ListMode.BLACKLIST
               || !DeathInfoManager.INSTANCE.isInList(new ResolvableProfile(context.player().getGameProfile()))
         )) {
         if (!graveConfig.generateEmptyGraves && grave.isGraveEmpty()) {
            event.setAllowGeneration(false);
         } else if (graveConfig.dimensionBlacklist.contains(grave.getWorldRegistryKey().location().toString())) {
            event.setAllowGeneration(false);
         } else if (!graveConfig.generateGraveInVoid && grave.getPos().getY() < context.world().getMinBuildHeight()) {
            event.setAllowGeneration(false);
         } else {
            if (graveConfig.requireItem) {
               Item item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(graveConfig.requiredItem));
               if (!grave.getInventoryComponent().removeItem(stack -> stack.is(item), graveConfig.requiredItemCount)) {
                  event.setAllowGeneration(false);
                  return;
               }
            }

            event.setAllowGeneration(!graveConfig.ignoredDeathTypes.contains(context.deathSource().getMsgId()));
         }
      } else {
         Yigd.LOGGER.info("{} found on whitelist/blacklist, disallowing grave generation", context.player().getGameProfile().getName());
         event.setAllowGeneration(false);
      }
   }

   @SubscribeEvent
   public void allowBlockUnderGraveGenerationEvent(YigdEvents.AllowBlockUnderGraveGenerationEvent event) {
      event.setAllowPlacement(YigdConfig.getConfig().graveConfig.blockUnderGrave.enabled && event.getBlockUnder().is(YigdTags.REPLACE_SOFT_WHITELIST));
   }

   @SubscribeEvent
   public void graveGenerationEvent(YigdEvents.GraveGenerationEvent event) {
      ServerLevel level = event.getLevel();
      BlockPos pos = event.getPos();
      int nthTry = event.getNthTry();
      if (!level.isOutsideBuildHeight(pos) && level.getWorldBorder().isWithinBounds(pos)) {
         BlockState state = level.getBlockState(pos);
         GraveConfig config = YigdConfig.getConfig().graveConfig;
         if (level.getBlockEntity(pos) != null) {
            event.setCanGenerate(false);
         } else {
            switch (nthTry) {
               case 0:
                  if (!config.useSoftBlockWhitelist) {
                     event.setCanGenerate(false);
                     return;
                  }

                  if (!state.is(YigdTags.REPLACE_SOFT_WHITELIST)) {
                     event.setCanGenerate(false);
                     return;
                  }
                  break;
               case 1:
                  if (!config.useStrictBlockBlacklist) {
                     event.setCanGenerate(false);
                     return;
                  }

                  if (state.is(YigdTags.KEEP_STRICT_BLACKLIST)) {
                     event.setCanGenerate(false);
                     return;
                  }
            }

            event.setCanGenerate(true);
         }
      } else {
         event.setCanGenerate(false);
      }
   }

   @SubscribeEvent
   public void dropItemEvent(YigdEvents.DropItemEvent event) {
      if (event.getStack().isEmpty()) {
         event.setShouldDrop(false);
         event.setCanceled(true);
      } else {
         event.setShouldDrop(true);
      }
   }
}
