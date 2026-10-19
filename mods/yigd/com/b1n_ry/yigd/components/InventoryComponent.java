package com.b1n_ry.yigd.components;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.compat.CompatComponent;
import com.b1n_ry.yigd.compat.InvModCompat;
import com.b1n_ry.yigd.config.InventoryConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.events.YigdEvents;
import com.b1n_ry.yigd.util.DropRule;
import com.b1n_ry.yigd.util.GraveItemModificationConsumer;
import com.b1n_ry.yigd.util.GraveOverrideAreas;
import com.b1n_ry.yigd.util.YigdTags;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;

public class InventoryComponent {
   private final NonNullList<GraveItem> items = NonNullList.create();
   private final Map<String, CompatComponent<?>> modInventoryItems;
   public final int mainSize;
   public final int armorSize;
   public final int offHandSize;
   private static final Random RANDOM = new Random();
   public static final GraveItem EMPTY_GRAVE_ITEM = new GraveItem(ItemStack.EMPTY, GraveOverrideAreas.INSTANCE.defaultDropRule);

   public InventoryComponent(ServerPlayer player) {
      for (ItemStack stack : this.getInventoryItems(player)) {
         this.items.add(new GraveItem(stack, DropRule.PUT_IN_GRAVE));
      }

      this.modInventoryItems = this.getModInventoryItems(player);
      Inventory inventory = player.getInventory();
      this.mainSize = inventory.items.size();
      this.armorSize = inventory.armor.size();
      this.offHandSize = inventory.offhand.size();
   }

   private InventoryComponent(NonNullList<GraveItem> items, Map<String, CompatComponent<?>> modInventoryItems, int mainSize, int armorSize, int offHandSize) {
      this.items.addAll(items);
      this.modInventoryItems = modInventoryItems;
      this.mainSize = mainSize;
      this.armorSize = armorSize;
      this.offHandSize = offHandSize;
   }

   public NonNullList<GraveItem> getItems() {
      return this.items;
   }

   public NonNullList<ItemStack> getAllExtraItems(boolean withoutEmpty) {
      NonNullList<ItemStack> stacks = NonNullList.create();

      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         for (GraveItem graveItem : compatComponent.getAsGraveItemList()) {
            ItemStack stack = graveItem.stack;
            if (!withoutEmpty || !stack.isEmpty()) {
               stacks.add(stack);
            }
         }
      }

      return stacks;
   }

   public boolean removeItem(Predicate<ItemStack> predicate, int itemCount) {
      predicate = predicate.and(stackx -> stackx.getCount() >= itemCount);

      for (GraveItem graveItem : this.items) {
         ItemStack stack = graveItem.stack;
         if (predicate.test(stack)) {
            stack.shrink(itemCount);
            return true;
         }
      }

      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         if (compatComponent.removeItem(predicate, itemCount)) {
            return true;
         }
      }

      return false;
   }

   private NonNullList<ItemStack> getInventoryItems(ServerPlayer player) {
      Inventory inventory = player.getInventory();
      NonNullList<ItemStack> items = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);

      for (int i = 0; i < inventory.getContainerSize(); i++) {
         items.set(i, inventory.getItem(i));
      }

      return items;
   }

   private Map<String, CompatComponent<?>> getModInventoryItems(ServerPlayer player) {
      Map<String, CompatComponent<?>> modInventories = new HashMap<>();

      for (InvModCompat<?> compatMod : InvModCompat.invCompatMods) {
         modInventories.put(compatMod.getModName(), compatMod.getNewComponent(player));
      }

      return modInventories;
   }

   public void onDeath(DeathContext context) {
      YigdConfig config = YigdConfig.getConfig();
      if (config.inventoryConfig.dropPlayerHead) {
         ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
         playerHead.set(DataComponents.PROFILE, new ResolvableProfile(context.player().getGameProfile()));
         this.items.add(new GraveItem(playerHead, GraveOverrideAreas.INSTANCE.defaultDropRule));
      }

      this.handleDropRules(context);
      InventoryComponent dropInventory = this.filteredInv(dropRule -> dropRule == DropRule.DROP);
      dropInventory.dropAll(context.world(), context.deathPos());
   }

   private void handleDropRules(DeathContext context) {
      for (int i = 0; i < this.items.size(); i++) {
         GraveItem graveItem = (GraveItem)this.items.get(i);
         ItemStack item = graveItem.stack;
         if (!item.isEmpty()) {
            YigdEvents.DropRuleEvent event = (YigdEvents.DropRuleEvent)NeoForge.EVENT_BUS.post(new YigdEvents.DropRuleEvent(item, i, context, true));
            graveItem.dropRule = event.getDropRule();
         }
      }

      for (InvModCompat<?> compatMod : InvModCompat.invCompatMods) {
         String modName = compatMod.getModName();
         CompatComponent<?> compatComponent = this.modInventoryItems.get(modName);
         compatComponent.handleDropRules(context);
      }

      NeoForge.EVENT_BUS.post(new YigdEvents.AdjustDropRuleEvent(this, context));
   }

   public void applyLoss() {
      YigdConfig config = YigdConfig.getConfig();
      InventoryConfig.ItemLossConfig itemLoss = config.inventoryConfig.itemLoss;
      int from;
      int to;
      if (itemLoss.usePercentRange) {
         NonNullList<ItemStack> vanillaStacks = NonNullList.create();

         for (GraveItem graveItem : this.items) {
            if (!graveItem.stack.isEmpty() && graveItem.dropRule != DropRule.DESTROY) {
               vanillaStacks.add(graveItem.stack);
            }
         }

         int itemCount = vanillaStacks.size();
         if (!itemLoss.affectStacks) {
            itemCount = 0;

            for (ItemStack stack : vanillaStacks) {
               itemCount += stack.getCount();
            }
         }

         from = (int)(itemCount * itemLoss.lossRangeFrom / 100.0F);
         to = (int)(itemCount * itemLoss.lossRangeTo / 100.0F);
      } else {
         from = itemLoss.lossRangeFrom;
         to = itemLoss.lossRangeTo;
      }

      int amount = from < to ? new Random().nextInt(from, to + 1) : from;

      for (int i = 0; i < amount; i++) {
         if (!(Math.random() > itemLoss.percentChanceOfLoss / 100.0)) {
            this.loseRandomItem();
         }
      }
   }

   private void loseRandomItem() {
      YigdConfig config = YigdConfig.getConfig();
      InventoryConfig.ItemLossConfig itemLoss = config.inventoryConfig.itemLoss;
      List<Integer> itemSlots = new ArrayList<>();
      List<Integer> slotWeights = new ArrayList<>();
      int totalLosableItemCount = 0;
      int vanillaLimit = this.items.size();

      for (int i = 0; i < vanillaLimit; i++) {
         GraveItem graveItem = (GraveItem)this.items.get(i);
         ItemStack stack = graveItem.stack;
         if (!stack.isEmpty() && (graveItem.dropRule != DropRule.KEEP || itemLoss.canLoseSoulbound) && !stack.is(YigdTags.LOSS_IMMUNE)) {
            itemSlots.add(i);
            int itemCount = stack.getCount();
            slotWeights.add(itemCount);
            totalLosableItemCount += itemCount;
         }
      }

      NonNullList<GraveItem> extraItems = NonNullList.create();
      if (itemLoss.includeModdedInventories) {
         for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
            for (GraveItem graveItem : compatComponent.getAsGraveItemList()) {
               if (!graveItem.stack.isEmpty()
                  && (graveItem.dropRule != DropRule.KEEP || itemLoss.canLoseSoulbound)
                  && !graveItem.stack.is(YigdTags.LOSS_IMMUNE)) {
                  extraItems.add(graveItem);
               }
            }
         }

         for (int i = 0; i < extraItems.size(); i++) {
            itemSlots.add(vanillaLimit + i);
            int itemCount = ((GraveItem)extraItems.get(i)).stack.getCount();
            slotWeights.add(itemCount);
            totalLosableItemCount += itemCount;
         }
      }

      if (!itemSlots.isEmpty()) {
         DropRule appliedDropRule = itemLoss.lossDropRule;
         int slot;
         if (itemLoss.weightedSelection) {
            int random = RANDOM.nextInt(totalLosableItemCount);
            int i = 0;

            for (int j = 0; random >= j; i++) {
               j += slotWeights.get(i);
            }

            slot = itemSlots.get(i - 1);
         } else {
            int random = RANDOM.nextInt(itemSlots.size());
            slot = itemSlots.get(random);
         }

         GraveItem removeFrom = slot >= vanillaLimit ? (GraveItem)extraItems.get(slot - vanillaLimit) : (GraveItem)this.items.get(slot);
         if (!itemLoss.affectStacks && removeFrom.stack.getMaxStackSize() != 1) {
            ItemStack stack = removeFrom.stack;
            stack.shrink(1);
            GraveItem lostItem = new GraveItem(stack.copyWithCount(1), appliedDropRule);
            this.mergeSingleItem(lostItem, false);
         } else {
            removeFrom.dropRule = appliedDropRule;
         }
      }
   }

   public void dropAll(ServerLevel world, Vec3 pos) {
      for (GraveItem graveItem : this.items) {
         ItemStack stack = graveItem.stack;
         if (!stack.isEmpty()) {
            dropItemIfToBeDropped(stack, pos.x, pos.y, pos.z, world);
         }
      }

      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         compatComponent.dropItems(world, pos);
      }
   }

   public void dropGraveItems(ServerLevel world, Vec3 pos) {
      for (GraveItem graveItem : this.items) {
         ItemStack stack = graveItem.stack;
         if (!stack.isEmpty() && graveItem.dropRule == DropRule.PUT_IN_GRAVE) {
            graveItem.dropRule = DropRule.DROP;
            dropItemIfToBeDropped(stack, pos.x, pos.y, pos.z, world);
         }
      }

      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         compatComponent.dropGraveItems(world, pos);
      }
   }

   public NonNullList<ItemStack> merge(InventoryComponent mergingComponent, ServerPlayer merger) {
      NonNullList<GraveItem> extraItems = this.merge(mergingComponent, merger, true);
      NonNullList<ItemStack> extraItemStacks = NonNullList.create();

      for (GraveItem graveItem : extraItems) {
         extraItemStacks.add(graveItem.stack);
      }

      return extraItemStacks;
   }

   public NonNullList<GraveItem> mergeKeepDropRules(InventoryComponent mergingComponent, ServerPlayer merger) {
      return this.merge(mergingComponent, merger, false);
   }

   private NonNullList<GraveItem> merge(InventoryComponent mergingComponent, ServerPlayer merger, boolean mergeDropRules) {
      YigdConfig config = YigdConfig.getConfig();
      NonNullList<GraveItem> extraItems = NonNullList.create();

      for (int i = 0; i < mergingComponent.items.size(); i++) {
         int currentComponentIndex;
         if (i < mergingComponent.mainSize) {
            int groupIndex = i;
            currentComponentIndex = groupIndex < this.mainSize ? groupIndex : 0;
         } else if (i < mergingComponent.mainSize + mergingComponent.armorSize) {
            int groupIndex = i - mergingComponent.mainSize;
            currentComponentIndex = groupIndex < this.armorSize ? groupIndex + this.mainSize : this.mainSize;
         } else if (i < mergingComponent.mainSize + mergingComponent.armorSize + mergingComponent.offHandSize) {
            int groupIndex = i - (mergingComponent.mainSize + mergingComponent.armorSize);
            currentComponentIndex = groupIndex < this.offHandSize ? groupIndex + this.mainSize + this.armorSize : this.mainSize + this.armorSize;
         } else {
            int groupIndex = i - (mergingComponent.mainSize + mergingComponent.armorSize + mergingComponent.offHandSize);
            currentComponentIndex = groupIndex + this.mainSize + this.armorSize + this.offHandSize;
         }

         GraveItem mergingGraveItem = ((GraveItem)mergingComponent.items.get(i)).copy();
         if (currentComponentIndex > this.items.size()) {
            extraItems.add(mergingGraveItem);
         } else {
            GraveItem currentGraveItem = (GraveItem)this.items.get(currentComponentIndex);
            if (config.graveConfig.treatBindingCurse
               && i >= mergingComponent.mainSize
               && i < mergingComponent.mainSize + mergingComponent.armorSize
               && EnchantmentHelper.has(mergingGraveItem.stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
               if (!currentGraveItem.stack.isEmpty()) {
                  extraItems.add(currentGraveItem);
               }

               this.items.set(currentComponentIndex, EMPTY_GRAVE_ITEM);
               currentGraveItem = (GraveItem)this.items.get(currentComponentIndex);
            }

            if (config.graveConfig.mergeStacksOnRetrieve) {
               int combinationSlot = this.findMatchingStackSlot(mergingGraveItem, mergeDropRules);
               if (combinationSlot != -1) {
                  this.mergeItemInSlot(mergingGraveItem.stack, combinationSlot);
               }
            }

            if (!mergingGraveItem.stack.isEmpty()) {
               if (currentGraveItem.stack.isEmpty()) {
                  this.items.set(currentComponentIndex, mergingGraveItem);
               } else {
                  extraItems.add(mergingGraveItem);
               }
            }
         }
      }

      for (InvModCompat<?> modCompat : InvModCompat.invCompatMods) {
         String modName = modCompat.getModName();
         if (mergingComponent.modInventoryItems.containsKey(modName)) {
            CompatComponent<?> mergingCompatComponent = mergingComponent.modInventoryItems.get(modName);
            if (!this.modInventoryItems.containsKey(modName)) {
               for (GraveItem graveItem : mergingCompatComponent.getAsGraveItemList()) {
                  if (!graveItem.stack.isEmpty()) {
                     extraItems.add(graveItem.copy());
                  }
               }
            } else {
               CompatComponent<?> compatComponent = this.modInventoryItems.get(modName);
               NonNullList<GraveItem> extraModItems = compatComponent.merge(mergingCompatComponent, merger);
               extraItems.addAll(extraModItems);
            }
         }
      }

      this.addStacksToMain(extraItems, mergeDropRules);
      return extraItems;
   }

   public void mergeSingleItem(GraveItem graveItem, boolean mergeDropRules) {
      while (!graveItem.stack.isEmpty()) {
         int addToSlot = this.findMatchingStackSlot(graveItem, mergeDropRules);
         if (addToSlot == -1) {
            addToSlot = this.findEmptySlot();
            if (addToSlot == -1) {
               this.addExtraGraveItem(graveItem);
               break;
            }
         }

         ItemStack addToStack = ((GraveItem)this.items.get(addToSlot)).stack;
         if (addToStack.isEmpty()) {
            this.items.set(addToSlot, graveItem);
         } else {
            this.mergeItemInSlot(graveItem.stack, addToSlot);
            if (!graveItem.stack.isEmpty()) {
               continue;
            }
         }
         break;
      }
   }

   public void addExtraGraveItem(GraveItem graveItem) {
      this.items.add(graveItem);
   }

   private int findMatchingStackSlot(GraveItem graveItem, boolean mergeDropRules) {
      ItemStack graveStack = graveItem.stack;

      for (int i = 0; i < this.items.size(); i++) {
         GraveItem iGraveItem = (GraveItem)this.items.get(i);
         ItemStack iStack = iGraveItem.stack;
         if (ItemStack.isSameItemSameComponents(graveStack, iStack)
            && iStack.isStackable()
            && iStack.getMaxStackSize() > iStack.getCount()
            && (mergeDropRules || graveItem.dropRule == iGraveItem.dropRule)) {
            return i;
         }
      }

      return -1;
   }

   private void mergeItemInSlot(ItemStack toMerge, int slot) {
      ItemStack mergeTo = ((GraveItem)this.items.get(slot)).stack;
      int remaining = mergeTo.getMaxStackSize() - mergeTo.getCount();
      int ableToAdd = Math.min(toMerge.getCount(), remaining);
      mergeTo.grow(ableToAdd);
      toMerge.shrink(ableToAdd);
   }

   public NonNullList<ItemStack> pullBindingCurseItems(ServerPlayer playerRef) {
      NonNullList<ItemStack> bindingItems = NonNullList.create();

      for (int i = 0; i < this.armorSize; i++) {
         ItemStack armorStack = ((GraveItem)this.items.get(this.mainSize + i)).stack;
         if (EnchantmentHelper.has(armorStack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
            bindingItems.add(armorStack);
            this.items.set(this.mainSize + i, EMPTY_GRAVE_ITEM);
         }
      }

      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         bindingItems.addAll(compatComponent.pullBindingCurseItems(playerRef));
      }

      return bindingItems;
   }

   private void addStacksToMain(NonNullList<GraveItem> extraItems, boolean mergeDropRules) {
      YigdConfig config = YigdConfig.getConfig();

      while (!extraItems.isEmpty()) {
         GraveItem graveItem = (GraveItem)extraItems.getFirst();
         int addToSlot = -1;
         if (config.graveConfig.mergeStacksOnRetrieve) {
            addToSlot = this.findMatchingStackSlot(graveItem, mergeDropRules);
         }

         if (addToSlot == -1) {
            addToSlot = this.findEmptySlot();
            if (addToSlot == -1) {
               return;
            }
         }

         ItemStack addToStack = ((GraveItem)this.items.get(addToSlot)).stack;
         if (addToStack.isEmpty()) {
            this.items.set(addToSlot, graveItem);
            extraItems.removeFirst();
         } else {
            this.mergeItemInSlot(graveItem.stack, addToSlot);
            if (graveItem.stack.isEmpty()) {
               extraItems.removeFirst();
            }
         }
      }
   }

   private int findEmptySlot() {
      for (int i = 0; i < this.mainSize; i++) {
         if (((GraveItem)this.items.get(i)).stack.isEmpty()) {
            return i;
         }
      }

      return -1;
   }

   public boolean containsAny(Predicate<ItemStack> itemPredicate, Predicate<String> modPredicate, Predicate<Integer> slotPredicate) {
      if (modPredicate.test("vanilla")) {
         for (int i = 0; i < this.items.size(); i++) {
            GraveItem graveItem = (GraveItem)this.items.get(i);
            if (slotPredicate.test(i) && itemPredicate.test(graveItem.stack)) {
               return true;
            }
         }
      }

      for (Entry<String, CompatComponent<?>> entry : this.modInventoryItems.entrySet()) {
         CompatComponent<?> compatComponent = entry.getValue();
         if (modPredicate.test(entry.getKey()) && compatComponent.containsAny(itemPredicate)) {
            return true;
         }
      }

      return false;
   }

   public void handleGraveItems(Predicate<String> modPredicate, GraveItemModificationConsumer modification) {
      if (modPredicate.test("vanilla")) {
         for (int i = 0; i < this.items.size(); i++) {
            GraveItem graveItem = (GraveItem)this.items.get(i);
            modification.accept(graveItem.stack, i, graveItem);
         }
      }

      for (Entry<String, CompatComponent<?>> entry : this.modInventoryItems.entrySet()) {
         CompatComponent<?> compatComponent = entry.getValue();
         if (modPredicate.test(entry.getKey())) {
            compatComponent.handleGraveItems(modification);
         }
      }
   }

   public boolean isGraveEmpty() {
      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         if (compatComponent.containsGraveItems()) {
            return false;
         }
      }

      for (GraveItem graveItem : this.items) {
         if (!graveItem.stack.isEmpty() && graveItem.dropRule == DropRule.PUT_IN_GRAVE) {
            return false;
         }
      }

      return true;
   }

   public boolean isEmpty() {
      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         if (!compatComponent.isEmpty()) {
            return false;
         }
      }

      for (GraveItem graveItem : this.items) {
         if (!graveItem.stack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int graveSize() {
      int size = 0;

      for (CompatComponent<?> compatComponent : this.modInventoryItems.values()) {
         for (GraveItem graveItem : compatComponent.getAsGraveItemList()) {
            if (!graveItem.stack.isEmpty() && graveItem.dropRule == DropRule.PUT_IN_GRAVE) {
               size++;
            }
         }
      }

      for (GraveItem graveItem : this.items) {
         if (!graveItem.stack.isEmpty() && graveItem.dropRule == DropRule.PUT_IN_GRAVE) {
            size++;
         }
      }

      return size;
   }

   public NonNullList<ItemStack> applyToPlayer(ServerPlayer player) {
      NonNullList<ItemStack> extraItems = NonNullList.create();
      Inventory inventory = player.getInventory();
      int invMainSize = inventory.items.size();
      int invArmorSize = inventory.armor.size();
      int invOffHandSize = inventory.offhand.size();

      for (int i = 0; i < this.items.size(); i++) {
         int playerInvIndex;
         if (i < this.mainSize) {
            int groupIndex = i;
            playerInvIndex = groupIndex < invMainSize ? groupIndex : -1;
         } else if (i < this.mainSize + this.armorSize) {
            int groupIndex = i - this.mainSize;
            playerInvIndex = groupIndex < invArmorSize ? groupIndex + invMainSize : -1;
         } else if (i < this.mainSize + this.armorSize + this.offHandSize) {
            int groupIndex = i - (this.mainSize + this.armorSize);
            playerInvIndex = groupIndex < invOffHandSize ? groupIndex + invMainSize + invArmorSize : -1;
         } else {
            int groupIndex = i - (this.mainSize + this.armorSize + this.offHandSize);
            playerInvIndex = groupIndex + invMainSize + invArmorSize + invOffHandSize;
         }

         ItemStack stack = ((GraveItem)this.items.get(i)).stack.copy();
         if (playerInvIndex < inventory.getContainerSize() && playerInvIndex != -1) {
            inventory.setItem(playerInvIndex, stack);
         } else {
            extraItems.add(stack);
         }
      }

      for (InvModCompat<?> modCompat : InvModCompat.invCompatMods) {
         String modName = modCompat.getModName();
         if (this.modInventoryItems.containsKey(modName)) {
            NonNullList<ItemStack> extraModItems = this.modInventoryItems.get(modName).storeToPlayer(player);
            extraItems.addAll(extraModItems);
         }
      }

      return extraItems;
   }

   public InventoryComponent filteredInv(Predicate<DropRule> filter) {
      NonNullList<GraveItem> filteredItems = NonNullList.create();

      for (GraveItem graveItem : this.items) {
         if (filter.test(graveItem.dropRule)) {
            filteredItems.add(graveItem);
         } else {
            filteredItems.add(EMPTY_GRAVE_ITEM);
         }
      }

      Map<String, CompatComponent<?>> filteredModInventories = new HashMap<>();

      for (InvModCompat<?> compatMod : InvModCompat.invCompatMods) {
         String modName = compatMod.getModName();
         if (this.modInventoryItems.containsKey(modName)) {
            CompatComponent<?> compatInv = this.modInventoryItems.get(modName);
            CompatComponent<?> filteredCompatInv = compatInv.filterInv(filter);
            filteredModInventories.put(modName, filteredCompatInv);
         }
      }

      return new InventoryComponent(filteredItems, filteredModInventories, this.mainSize, this.armorSize, this.offHandSize);
   }

   public void clear() {
      Collections.fill(this.items, EMPTY_GRAVE_ITEM);

      for (CompatComponent<?> component : this.modInventoryItems.values()) {
         component.clear();
      }
   }

   public CompoundTag toNbt(Provider lookupRegistry) {
      CompoundTag nbt = new CompoundTag();
      CompoundTag vanillaInventoryNbt = listToNbt(this.items, graveItem -> {
         CompoundTag itemNbt = (CompoundTag)graveItem.stack.save(lookupRegistry);
         itemNbt.putString("dropRule", graveItem.dropRule.name());
         return itemNbt;
      }, graveItem -> graveItem.stack.isEmpty());
      vanillaInventoryNbt.putInt("mainSize", this.mainSize);
      vanillaInventoryNbt.putInt("armorSize", this.armorSize);
      vanillaInventoryNbt.putInt("offHandSize", this.offHandSize);
      CompoundTag modInventoriesNbt = new CompoundTag();

      for (InvModCompat<?> compatMod : InvModCompat.invCompatMods) {
         String modName = compatMod.getModName();
         if (this.modInventoryItems.containsKey(modName)) {
            CompatComponent<?> compatInv = this.modInventoryItems.get(modName);
            if (!compatInv.isEmpty()) {
               modInventoriesNbt.put(modName, compatInv.writeNbt(lookupRegistry));
            }
         }
      }

      nbt.put("vanilla", vanillaInventoryNbt);
      nbt.put("mods", modInventoriesNbt);
      return nbt;
   }

   public static InventoryComponent fromNbt(CompoundTag nbt, Provider lookupRegistry) {
      CompoundTag vanillaInvNbt = nbt.getCompound("vanilla");
      NonNullList<GraveItem> items = listFromNbt(vanillaInvNbt, itemNbt -> {
         ItemStack stack = ItemStack.parseOptional(lookupRegistry, itemNbt);
         DropRule dropRule = GraveOverrideAreas.INSTANCE.defaultDropRule;
         if (itemNbt.contains("dropRule")) {
            dropRule = DropRule.valueOf(itemNbt.getString("dropRule"));
         }

         return new GraveItem(stack, dropRule);
      }, EMPTY_GRAVE_ITEM);
      int mainSize = vanillaInvNbt.getInt("mainSize");
      int armorSize = vanillaInvNbt.getInt("armorSize");
      int offHandSize = vanillaInvNbt.getInt("offHandSize");
      CompoundTag modInventoriesNbt = nbt.getCompound("mods");
      Map<String, CompatComponent<?>> compatComponents = new HashMap<>();

      for (InvModCompat<?> compatMod : InvModCompat.invCompatMods) {
         String modName = compatMod.getModName();
         if (modInventoriesNbt.contains(modName)) {
            CompoundTag modNbt = modInventoriesNbt.getCompound(modName);
            compatComponents.put(modName, compatMod.readNbt(modNbt, lookupRegistry));
         }
      }

      return new InventoryComponent(items, compatComponents, mainSize, armorSize, offHandSize);
   }

   public static <T> CompoundTag listToNbt(NonNullList<T> list, Function<T, CompoundTag> mappingFunction, Predicate<T> isEmpty) {
      return listToNbt(list, mappingFunction, isEmpty, "Items", "Slot");
   }

   public static <T> CompoundTag listToNbt(
      NonNullList<T> list, Function<T, CompoundTag> mappingFunction, Predicate<T> isEmpty, String listName, String itemName
   ) {
      CompoundTag nbt = new CompoundTag();
      int size = list.size();
      nbt.putInt("size", size);
      ListTag nbtList = new ListTag();

      for (int i = 0; i < size; i++) {
         T item = (T)list.get(i);
         if (!isEmpty.test(item)) {
            try {
               CompoundTag itemNbt = mappingFunction.apply(item);
               itemNbt.putInt(itemName, i);
               nbtList.add(itemNbt);
            } catch (Exception e) {
               Yigd.LOGGER.error("Error while converting item to NBT: {}", item, e);
            }
         }
      }

      nbt.put(listName, nbtList);
      return nbt;
   }

   public static <T> NonNullList<T> listFromNbt(CompoundTag nbt, Function<CompoundTag, T> mappingFunction, T emptyValue) {
      return listFromNbt(nbt, mappingFunction, emptyValue, "Items", "Slot");
   }

   public static <T> NonNullList<T> listFromNbt(CompoundTag nbt, Function<CompoundTag, T> mappingFunction, T emptyValue, String listName, String itemName) {
      int size = nbt.getInt("size");
      NonNullList<T> list = NonNullList.withSize(size, emptyValue);

      for (Tag element : nbt.getList(listName, 10)) {
         CompoundTag itemNbt = (CompoundTag)element;
         int index = itemNbt.getInt(itemName);
         T item = mappingFunction.apply(itemNbt);
         list.set(index, item);
      }

      return list;
   }

   public static void dropItemIfToBeDropped(ItemStack stack, double x, double y, double z, ServerLevel world) {
      YigdEvents.DropItemEvent event = (YigdEvents.DropItemEvent)NeoForge.EVENT_BUS.post(new YigdEvents.DropItemEvent(stack, x, y, z, world));
      if (event.shouldDrop()) {
         Containers.dropItemStack(world, x, y, z, stack.copy());
      }
   }

   public static void clearPlayer(ServerPlayer player) {
      Inventory inventory = player.getInventory();

      for (int i = 0; i < inventory.getContainerSize(); i++) {
         inventory.setItem(i, ItemStack.EMPTY);
      }

      for (InvModCompat<?> invModCompat : InvModCompat.invCompatMods) {
         invModCompat.clear(player);
      }
   }
}
