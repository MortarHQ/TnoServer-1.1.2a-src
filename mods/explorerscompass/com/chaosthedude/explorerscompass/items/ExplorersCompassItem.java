package com.chaosthedude.explorerscompass.items;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.config.ConfigHandler;
import com.chaosthedude.explorerscompass.gui.GuiWrapper;
import com.chaosthedude.explorerscompass.network.SyncPacket;
import com.chaosthedude.explorerscompass.util.CompassState;
import com.chaosthedude.explorerscompass.util.ItemUtils;
import com.chaosthedude.explorerscompass.util.PlayerUtils;
import com.chaosthedude.explorerscompass.util.StructureUtils;
import com.chaosthedude.explorerscompass.worker.SearchWorkerManager;
import com.google.common.collect.ListMultimap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.network.PacketDistributor;

public class ExplorersCompassItem extends Item {
   public static final String NAME = "explorerscompass";
   private SearchWorkerManager workerManager = new SearchWorkerManager();

   public ExplorersCompassItem() {
      super(new Properties().stacksTo(1));
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (!player.isCrouching()) {
         if (this.isBroken(player.getItemInHand(hand))) {
            return new InteractionResultHolder(InteractionResult.PASS, player.getItemInHand(hand));
         }

         if (level.isClientSide()) {
            ItemStack stack = ItemUtils.getHeldItem(player, ExplorersCompass.explorersCompass);
            GuiWrapper.openGUI(level, player, stack);
         } else {
            ServerLevel serverLevel = (ServerLevel)level;
            ServerPlayer serverPlayer = (ServerPlayer)player;
            boolean canTeleport = (Boolean)ConfigHandler.GENERAL.allowTeleport.get() && PlayerUtils.canTeleport(player.getServer(), player);
            int maxNextSearches = (Integer)ConfigHandler.GENERAL.maxNextSearches.get();
            boolean hasInfiniteXp = player.hasInfiniteMaterials();
            List<ResourceLocation> allowedStructureKeys = StructureUtils.getAllowedStructureKeys(serverLevel);
            Map<ResourceLocation, Integer> xpLevels = StructureUtils.getXpLevelsForAllowedStructures(serverLevel, allowedStructureKeys);
            ListMultimap<ResourceLocation, ResourceLocation> generatingDimensions = StructureUtils.getGeneratingDimensionsForAllowedStructures(
               serverLevel, allowedStructureKeys
            );
            PacketDistributor.sendToPlayer(
               serverPlayer,
               new SyncPacket(
                  canTeleport,
                  maxNextSearches,
                  hasInfiniteXp,
                  allowedStructureKeys,
                  xpLevels,
                  generatingDimensions,
                  StructureUtils.getStructureKeysToTypeKeys(serverLevel),
                  StructureUtils.getTypeKeysToStructureKeys(serverLevel)
               ),
               new CustomPacketPayload[0]
            );
         }
      } else {
         this.workerManager.stop();
         this.workerManager.clear();
         this.setInactive(player.getItemInHand(hand), player);
      }

      return new InteractionResultHolder(InteractionResult.PASS, player.getItemInHand(hand));
   }

   public boolean isBarVisible(ItemStack stack) {
      int max = (Integer)ConfigHandler.GENERAL.compassDurability.get();
      if (max > 0) {
         int damage = (Integer)stack.getOrDefault(ExplorersCompass.DAMAGE_COMPONENT, 0);
         return damage > 0;
      } else {
         return false;
      }
   }

   public int getBarWidth(ItemStack stack) {
      int max = (Integer)ConfigHandler.GENERAL.compassDurability.get();
      if (max > 0) {
         int damage = (Integer)stack.getOrDefault(ExplorersCompass.DAMAGE_COMPONENT, 0);
         return Math.clamp(Math.round(13.0F * (1.0F - (float)damage / max)), 0, 13);
      } else {
         return 13;
      }
   }

   public int getBarColor(ItemStack stack) {
      int max = (Integer)ConfigHandler.GENERAL.compassDurability.get();
      int damage = (Integer)stack.getOrDefault(ExplorersCompass.DAMAGE_COMPONENT, 0);
      float f = max > 0 ? (float)damage / max : 0.0F;
      return Mth.hsvToRgb(Math.max(0.0F, (1.0F - f) / 3.0F), 1.0F, 1.0F);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return this.getState(oldStack) == this.getState(newStack) ? false : super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
   }

   public void searchForStructure(
      Level level, Player player, ResourceLocation groupOrStructureKey, List<ResourceLocation> structureKeys, BlockPos pos, ItemStack stack, boolean isGroup
   ) {
      if (!this.isBroken(stack)) {
         this.setSearching(stack, groupOrStructureKey, isGroup, player);
         this.setSearchRadius(stack, 0, player);
         if (level instanceof ServerLevel serverLevel) {
            List<Structure> structures = new ArrayList<>();

            for (ResourceLocation key : structureKeys) {
               structures.add(StructureUtils.getStructureForKey(serverLevel, key));
            }

            List<BlockPos> prevPos = new ArrayList<>();
            this.workerManager.stop();
            this.workerManager.createWorkers(serverLevel, player, stack, structures, groupOrStructureKey, isGroup, pos, prevPos);
            boolean started = this.workerManager.start();
            if (!started) {
               this.setNotFound(stack, 0, 0);
            }

            int xpLevelsToConsume = StructureUtils.getXpLevelsForStructure(serverLevel, groupOrStructureKey);
            if (!player.hasInfiniteMaterials() && xpLevelsToConsume > 0) {
               player.giveExperienceLevels(-xpLevelsToConsume);
            }
         }
      }
   }

   public void searchForNextStructure(ServerLevel level, Player player, BlockPos pos, ItemStack stack) {
      if (!this.isBroken(stack)) {
         ResourceLocation structureKey = this.getStructureKey(stack);
         List<BlockPos> storedPrevPos = this.getPrevPos(stack);
         List<BlockPos> prevPos = storedPrevPos != null ? new ArrayList<>(storedPrevPos) : null;
         boolean isGroup = this.getIsGroup(stack);
         if (stack.has(ExplorersCompass.STRUCTURE_ID_COMPONENT) && prevPos != null) {
            this.setSearchRadius(stack, 0, player);
            List<ResourceLocation> structureKeys;
            if (isGroup) {
               Structure structure = StructureUtils.getStructureForKey(level, structureKey);
               ResourceLocation groupKey = StructureUtils.getTypeForStructure(level, structure);
               this.setSearching(stack, groupKey, isGroup, player);
               structureKeys = new ArrayList<>(StructureUtils.getTypeKeysToStructureKeys(level).get(groupKey));
            } else {
               this.setSearching(stack, structureKey, isGroup, player);
               structureKeys = List.of(structureKey);
            }

            List<Structure> structures = new ArrayList<>();

            for (ResourceLocation key : structureKeys) {
               structures.add(StructureUtils.getStructureForKey(level, key));
            }

            this.workerManager.stop();
            this.workerManager.createWorkers(level, player, stack, structures, structureKey, isGroup, pos, prevPos);
            boolean started = this.workerManager.start();
            if (!started) {
               this.setNotFound(stack, 0, 0);
            }

            int xpLevelsToConsume = StructureUtils.getXpLevelsForStructure(level, structureKey);
            if (!player.hasInfiniteMaterials() && xpLevelsToConsume > 0) {
               player.giveExperienceLevels(-xpLevelsToConsume);
            }
         }
      }
   }

   public void succeed(
      ItemStack stack, ResourceLocation structureKey, boolean isGroup, int x, int z, List<BlockPos> prevPos, int samples, boolean displayCoordinates
   ) {
      this.setFound(stack, structureKey, isGroup, x, z, samples);
      this.setDisplayCoordinates(stack, displayCoordinates);
      if (prevPos != null) {
         stack.set(ExplorersCompass.PREV_POS_COMPONENT, prevPos);
      }

      this.damageCompass(stack);
      this.workerManager.clear();
   }

   public void fail(ItemStack stack, ResourceLocation structureKey, int radius, int samples) {
      this.workerManager.pop();
      boolean started = this.workerManager.start();
      if (!started) {
         this.setNotFound(stack, radius, samples);
         if (ItemUtils.isCompass(stack)) {
            stack.set(ExplorersCompass.STRUCTURE_ID_COMPONENT, structureKey.toString());
         }
      }
   }

   public boolean isActive(ItemStack stack) {
      return ItemUtils.isCompass(stack) ? this.getState(stack) != CompassState.INACTIVE : false;
   }

   public boolean isBroken(ItemStack stack) {
      int max = (Integer)ConfigHandler.GENERAL.compassDurability.get();
      return max > 0 ? this.getDamage(stack) >= max : false;
   }

   public int getDamage(ItemStack stack) {
      return (Integer)stack.getOrDefault(ExplorersCompass.DAMAGE_COMPONENT, 0);
   }

   private void damageCompass(ItemStack stack) {
      int max = (Integer)ConfigHandler.GENERAL.compassDurability.get();
      if (max > 0) {
         int damage = this.getDamage(stack) + 1;
         stack.set(ExplorersCompass.DAMAGE_COMPONENT, damage);
      }
   }

   public void setSearching(ItemStack stack, ResourceLocation structureKey, boolean isGroup, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.STRUCTURE_ID_COMPONENT, structureKey.toString());
         stack.set(ExplorersCompass.IS_GROUP_COMPONENT, isGroup);
         stack.set(ExplorersCompass.COMPASS_STATE_COMPONENT, CompassState.SEARCHING.getID());
      }
   }

   public void setFound(ItemStack stack, ResourceLocation structureKey, boolean isGroup, int x, int z, int samples) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.COMPASS_STATE_COMPONENT, CompassState.FOUND.getID());
         stack.set(ExplorersCompass.STRUCTURE_ID_COMPONENT, structureKey.toString());
         stack.set(ExplorersCompass.IS_GROUP_COMPONENT, isGroup);
         stack.set(ExplorersCompass.FOUND_X_COMPONENT, x);
         stack.set(ExplorersCompass.FOUND_Z_COMPONENT, z);
         stack.set(ExplorersCompass.SAMPLES_COMPONENT, samples);
      }
   }

   public void setNotFound(ItemStack stack, int searchRadius, int samples) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.COMPASS_STATE_COMPONENT, CompassState.NOT_FOUND.getID());
         stack.set(ExplorersCompass.SEARCH_RADIUS_COMPONENT, searchRadius);
         stack.set(ExplorersCompass.SAMPLES_COMPONENT, samples);
      }
   }

   public void setInactive(ItemStack stack, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.COMPASS_STATE_COMPONENT, CompassState.INACTIVE.getID());
         stack.remove(ExplorersCompass.STRUCTURE_ID_COMPONENT);
         stack.remove(ExplorersCompass.PREV_POS_COMPONENT);
      }
   }

   public void setState(ItemStack stack, BlockPos pos, CompassState state, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.COMPASS_STATE_COMPONENT, state.getID());
      }
   }

   public void setFoundStructureX(ItemStack stack, int x, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.FOUND_X_COMPONENT, x);
      }
   }

   public void setFoundStructureZ(ItemStack stack, int z, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.FOUND_Z_COMPONENT, z);
      }
   }

   public void setStructureKey(ItemStack stack, ResourceLocation structureKey, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.STRUCTURE_ID_COMPONENT, structureKey.toString());
      }
   }

   public void setSearchRadius(ItemStack stack, int searchRadius, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.SEARCH_RADIUS_COMPONENT, searchRadius);
      }
   }

   public void setSamples(ItemStack stack, int samples, Player player) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.SAMPLES_COMPONENT, samples);
      }
   }

   public void setDisplayCoordinates(ItemStack stack, boolean displayPosition) {
      if (ItemUtils.isCompass(stack)) {
         stack.set(ExplorersCompass.DISPLAY_COORDS_COMPONENT, displayPosition);
      }
   }

   public CompassState getState(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.COMPASS_STATE_COMPONENT)
         ? CompassState.fromID((Integer)stack.get(ExplorersCompass.COMPASS_STATE_COMPONENT))
         : null;
   }

   public boolean getIsGroup(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.IS_GROUP_COMPONENT) ? (Boolean)stack.get(ExplorersCompass.IS_GROUP_COMPONENT) : false;
   }

   public List<BlockPos> getPrevPos(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.PREV_POS_COMPONENT) ? (List)stack.get(ExplorersCompass.PREV_POS_COMPONENT) : null;
   }

   public int getFoundStructureX(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.FOUND_X_COMPONENT) ? (Integer)stack.get(ExplorersCompass.FOUND_X_COMPONENT) : 0;
   }

   public int getFoundStructureZ(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.FOUND_Z_COMPONENT) ? (Integer)stack.get(ExplorersCompass.FOUND_Z_COMPONENT) : 0;
   }

   public ResourceLocation getStructureKey(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.STRUCTURE_ID_COMPONENT)
         ? ResourceLocation.parse((String)stack.get(ExplorersCompass.STRUCTURE_ID_COMPONENT))
         : ResourceLocation.fromNamespaceAndPath("", "");
   }

   public int getSearchRadius(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.SEARCH_RADIUS_COMPONENT)
         ? (Integer)stack.get(ExplorersCompass.SEARCH_RADIUS_COMPONENT)
         : -1;
   }

   public int getSamples(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.SAMPLES_COMPONENT) ? (Integer)stack.get(ExplorersCompass.SAMPLES_COMPONENT) : -1;
   }

   public int getDistanceToBiome(Player player, ItemStack stack) {
      return StructureUtils.getHorizontalDistanceToLocation(player, this.getFoundStructureX(stack), this.getFoundStructureZ(stack));
   }

   public boolean shouldDisplayCoordinates(ItemStack stack) {
      return ItemUtils.isCompass(stack) && stack.has(ExplorersCompass.DISPLAY_COORDS_COMPONENT)
         ? (Boolean)stack.get(ExplorersCompass.DISPLAY_COORDS_COMPONENT)
         : true;
   }
}
