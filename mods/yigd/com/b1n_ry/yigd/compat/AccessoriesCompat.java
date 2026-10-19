package com.b1n_ry.yigd.compat;

import com.b1n_ry.yigd.components.InventoryComponent;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.events.YigdEvents;
import com.b1n_ry.yigd.util.DropRule;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;

public class AccessoriesCompat implements InvModCompat<Map<String, AccessoriesCompat.AccessoriesInventoryGroup>> {
   @Override
   public String getModName() {
      return "accessories";
   }

   @Override
   public void clear(ServerPlayer player) {
      AccessoriesCapability.getOptionally(player).ifPresent(inv -> inv.reset(false));
   }

   @Override
   public CompatComponent<Map<String, AccessoriesCompat.AccessoriesInventoryGroup>> readNbt(CompoundTag nbt, Provider registryLookup) {
      Map<String, AccessoriesCompat.AccessoriesInventoryGroup> inventory = new HashMap<>();

      for (String key : nbt.getAllKeys()) {
         CompoundTag slotNbt = nbt.getCompound(key);
         NonNullList<AccessoriesCompat.AccessoriesInventorySlot> normalSlot = InventoryComponent.listFromNbt(slotNbt.getCompound("normal"), itemNbt -> {
            ItemStack stack = ItemStack.parse(registryLookup, itemNbt).orElse(ItemStack.EMPTY);
            DropRule dropRule;
            if (itemNbt.contains("dropRule")) {
               String dropRuleString = itemNbt.getString("dropRule");
               if (dropRuleString.equals("DEFAULT")) {
                  dropRule = YigdConfig.getConfig().compatConfig.defaultAccessoriesDropRule;
               } else {
                  dropRule = DropRule.valueOf(dropRuleString);
               }
            } else {
               dropRule = YigdConfig.getConfig().compatConfig.defaultAccessoriesDropRule;
            }

            boolean visible = !itemNbt.contains("visible") || itemNbt.getBoolean("visible");
            return new AccessoriesCompat.AccessoriesInventorySlot(stack, dropRule, visible);
         }, AccessoriesCompat.AccessoriesInventorySlot.empty(true));
         NonNullList<AccessoriesCompat.AccessoriesInventorySlot> cosmeticSlot = InventoryComponent.listFromNbt(slotNbt.getCompound("cosmetic"), itemNbt -> {
            ItemStack stack = ItemStack.parse(registryLookup, itemNbt).orElse(ItemStack.EMPTY);
            DropRule dropRule;
            if (itemNbt.contains("dropRule")) {
               String dropRuleString = itemNbt.getString("dropRule");
               if (dropRuleString.equals("DEFAULT")) {
                  dropRule = YigdConfig.getConfig().compatConfig.defaultAccessoriesDropRule;
               } else {
                  dropRule = DropRule.valueOf(dropRuleString);
               }
            } else {
               dropRule = YigdConfig.getConfig().compatConfig.defaultAccessoriesDropRule;
            }

            boolean visible = !itemNbt.contains("visible") || itemNbt.getBoolean("visible");
            return new AccessoriesCompat.AccessoriesInventorySlot(stack, dropRule, visible);
         }, AccessoriesCompat.AccessoriesInventorySlot.empty(true));
         inventory.put(key, new AccessoriesCompat.AccessoriesInventoryGroup(normalSlot, cosmeticSlot));
      }

      return new AccessoriesCompat.AccessoriesCompatComponent(inventory);
   }

   @Override
   public CompatComponent<Map<String, AccessoriesCompat.AccessoriesInventoryGroup>> getNewComponent(ServerPlayer player) {
      return new AccessoriesCompat.AccessoriesCompatComponent(player);
   }

   private static class AccessoriesCompatComponent extends CompatComponent<Map<String, AccessoriesCompat.AccessoriesInventoryGroup>> {
      public AccessoriesCompatComponent(ServerPlayer player) {
         super(player);
      }

      public AccessoriesCompatComponent(Map<String, AccessoriesCompat.AccessoriesInventoryGroup> inventory) {
         super(inventory);
      }

      public Map<String, AccessoriesCompat.AccessoriesInventoryGroup> getInventory(ServerPlayer player) {
         Map<String, AccessoriesCompat.AccessoriesInventoryGroup> inventory = new HashMap<>();
         AccessoriesCapability capability = AccessoriesCapability.get(player);
         if (capability == null) {
            return inventory;
         }

         Map<String, AccessoriesContainer> containers = capability.getContainers();

         for (Entry<String, AccessoriesContainer> entry : containers.entrySet()) {
            NonNullList<AccessoriesCompat.AccessoriesInventorySlot> normalSlot = NonNullList.create();
            NonNullList<AccessoriesCompat.AccessoriesInventorySlot> cosmeticSlot = NonNullList.create();
            AccessoriesContainer container = entry.getValue();
            ExpandedSimpleContainer normal = container.getAccessories();
            ExpandedSimpleContainer cosmetic = container.getCosmeticAccessories();

            for (int i = 0; i < normal.getContainerSize(); i++) {
               boolean shouldRender = container.shouldRender(i);
               normalSlot.add(new AccessoriesCompat.AccessoriesInventorySlot(normal.getItem(i).copy(), DropRule.PUT_IN_GRAVE, shouldRender));
            }

            for (int i = 0; i < cosmetic.getContainerSize(); i++) {
               boolean shouldRender = container.shouldRender(i);
               cosmeticSlot.add(new AccessoriesCompat.AccessoriesInventorySlot(cosmetic.getItem(i).copy(), DropRule.PUT_IN_GRAVE, shouldRender));
            }

            inventory.put(entry.getKey(), new AccessoriesCompat.AccessoriesInventoryGroup(normalSlot, cosmeticSlot));
         }

         return inventory;
      }

      @Override
      public NonNullList<GraveItem> merge(CompatComponent<?> mergingComponent, ServerPlayer merger) {
         NonNullList<GraveItem> extraItems = NonNullList.create();
         Map<String, AccessoriesCompat.AccessoriesInventoryGroup> mergingInventory = (Map<String, AccessoriesCompat.AccessoriesInventoryGroup>)mergingComponent.inventory;

         for (Entry<String, AccessoriesCompat.AccessoriesInventoryGroup> mergeEntry : mergingInventory.entrySet()) {
            String key = mergeEntry.getKey();
            if (!this.inventory.containsKey(key)) {
               mergeEntry.getValue().addAllNonEmptyToList(extraItems);
            } else {
               AccessoriesCompat.AccessoriesInventoryGroup mergingGroup = mergeEntry.getValue();
               AccessoriesCompat.AccessoriesInventoryGroup thisGroup = this.inventory.get(key);

               for (int i = 0; i < mergingGroup.normal.size(); i++) {
                  AccessoriesCompat.AccessoriesInventorySlot mergingSlot = (AccessoriesCompat.AccessoriesInventorySlot)mergingGroup.normal.get(i);
                  ItemStack mergingStack = mergingSlot.graveItem.stack.copy();
                  if (!mergingStack.isEmpty()) {
                     if (thisGroup.normal.size() <= i) {
                        extraItems.add(new GraveItem(mergingStack, mergingSlot.graveItem.dropRule));
                     } else {
                        AccessoriesCompat.AccessoriesInventorySlot currentSlot = (AccessoriesCompat.AccessoriesInventorySlot)thisGroup.normal.get(i);
                        ItemStack currentStack = currentSlot.graveItem.stack;
                        if (YigdConfig.getConfig().graveConfig.treatBindingCurse && !AccessoriesAPI.canUnequip(mergingStack, SlotReference.of(merger, key, i))) {
                           extraItems.add(new GraveItem(currentStack, currentSlot.graveItem.dropRule));
                           thisGroup.normal
                              .set(i, new AccessoriesCompat.AccessoriesInventorySlot(mergingStack, mergingSlot.graveItem.dropRule, mergingSlot.visible));
                        } else if (!currentStack.isEmpty()) {
                           extraItems.add(new GraveItem(mergingStack, mergingSlot.graveItem.dropRule));
                        } else {
                           thisGroup.normal.set(i, mergingSlot);
                        }
                     }
                  }
               }

               for (int i = 0; i < mergingGroup.cosmetic.size(); i++) {
                  AccessoriesCompat.AccessoriesInventorySlot mergingSlot = (AccessoriesCompat.AccessoriesInventorySlot)mergingGroup.cosmetic.get(i);
                  ItemStack mergingStack = mergingSlot.graveItem.stack.copy();
                  if (!mergingStack.isEmpty()) {
                     if (thisGroup.cosmetic.size() <= i) {
                        extraItems.add(new GraveItem(mergingStack, mergingSlot.graveItem.dropRule));
                     } else {
                        AccessoriesCompat.AccessoriesInventorySlot currentSlot = (AccessoriesCompat.AccessoriesInventorySlot)thisGroup.cosmetic.get(i);
                        ItemStack currentStack = currentSlot.graveItem.stack;
                        if (YigdConfig.getConfig().graveConfig.treatBindingCurse && !AccessoriesAPI.canUnequip(mergingStack, SlotReference.of(merger, key, i))) {
                           extraItems.add(new GraveItem(currentStack, currentSlot.graveItem.dropRule));
                           thisGroup.cosmetic
                              .set(i, new AccessoriesCompat.AccessoriesInventorySlot(mergingStack, mergingSlot.graveItem.dropRule, mergingSlot.visible));
                        } else if (!currentStack.isEmpty()) {
                           extraItems.add(new GraveItem(mergingStack, mergingSlot.graveItem.dropRule));
                        } else {
                           thisGroup.cosmetic.set(i, mergingSlot);
                        }
                     }
                  }
               }
            }
         }

         return extraItems;
      }

      @Override
      public NonNullList<ItemStack> pullBindingCurseItems(ServerPlayer playerRef) {
         NonNullList<ItemStack> noUnequipItems = NonNullList.create();
         if (!YigdConfig.getConfig().graveConfig.treatBindingCurse) {
            return noUnequipItems;
         }

         for (Entry<String, AccessoriesCompat.AccessoriesInventoryGroup> entry : this.inventory.entrySet()) {
            AccessoriesCompat.AccessoriesInventoryGroup inventoryGroup = entry.getValue();

            for (int i = 0; i < inventoryGroup.normal.size(); i++) {
               AccessoriesCompat.AccessoriesInventorySlot slot = (AccessoriesCompat.AccessoriesInventorySlot)inventoryGroup.normal.get(i);
               ItemStack stack = slot.graveItem.stack;
               boolean isBound = !AccessoriesAPI.canUnequip(stack, SlotReference.of(playerRef, entry.getKey(), i));
               if (isBound) {
                  noUnequipItems.add(stack);
                  slot.graveItem.stack = ItemStack.EMPTY;
               }
            }

            for (int i = 0; i < inventoryGroup.cosmetic.size(); i++) {
               AccessoriesCompat.AccessoriesInventorySlot slot = (AccessoriesCompat.AccessoriesInventorySlot)inventoryGroup.cosmetic.get(i);
               ItemStack stack = slot.graveItem.stack;
               boolean isBound = !AccessoriesAPI.canUnequip(stack, SlotReference.of(playerRef, entry.getKey(), i));
               if (isBound) {
                  noUnequipItems.add(stack);
                  slot.graveItem.stack = ItemStack.EMPTY;
               }
            }
         }

         return noUnequipItems;
      }

      @Override
      public NonNullList<ItemStack> storeToPlayer(ServerPlayer player) {
         NonNullList<ItemStack> extraItems = NonNullList.create();
         AccessoriesCapability capability = AccessoriesCapability.get(player);
         if (capability == null) {
            return extraItems;
         }

         Map<String, AccessoriesContainer> containers = capability.getContainers();

         for (Entry<String, AccessoriesCompat.AccessoriesInventoryGroup> entry : this.inventory.entrySet()) {
            String key = entry.getKey();
            if (!containers.containsKey(key)) {
               entry.getValue().addAllNonEmptyToStackList(extraItems);
            } else {
               AccessoriesContainer container = containers.get(key);
               AccessoriesCompat.AccessoriesInventoryGroup inventorySlot = entry.getValue();
               ExpandedSimpleContainer normalAccessories = container.getAccessories();
               ExpandedSimpleContainer cosmeticAccessories = container.getCosmeticAccessories();

               for (int i = 0; i < inventorySlot.normal.size(); i++) {
                  AccessoriesCompat.AccessoriesInventorySlot slot = (AccessoriesCompat.AccessoriesInventorySlot)inventorySlot.normal.get(i);
                  if (i >= normalAccessories.getContainerSize()) {
                     extraItems.add(slot.graveItem.stack.copy());
                  } else {
                     container.renderOptions().set(i, slot.visible);
                     if (!slot.graveItem.stack.isEmpty()) {
                        normalAccessories.setItem(i, slot.graveItem.stack.copy());
                     }
                  }
               }

               for (int i = 0; i < inventorySlot.cosmetic.size(); i++) {
                  AccessoriesCompat.AccessoriesInventorySlot slot = (AccessoriesCompat.AccessoriesInventorySlot)inventorySlot.cosmetic.get(i);
                  if (i >= cosmeticAccessories.getContainerSize()) {
                     extraItems.add(slot.graveItem.stack.copy());
                  } else {
                     container.renderOptions().set(i, slot.visible);
                     if (!slot.graveItem.stack.isEmpty()) {
                        cosmeticAccessories.setItem(i, slot.graveItem.stack.copy());
                     }
                  }
               }

               container.markChanged(false);
            }
         }

         return extraItems;
      }

      @Override
      public void handleDropRules(DeathContext context) {
         for (Entry<String, AccessoriesCompat.AccessoriesInventoryGroup> entry : this.inventory.entrySet()) {
            String key = entry.getKey();
            AccessoriesCompat.AccessoriesInventoryGroup inventorySlot = entry.getValue();

            for (int i = 0; i < inventorySlot.normal.size(); i++) {
               AccessoriesCompat.AccessoriesInventorySlot slot = (AccessoriesCompat.AccessoriesInventorySlot)inventorySlot.normal.get(i);
               ItemStack stack = slot.graveItem.stack;

               slot.graveItem.dropRule = switch (AccessoriesAPI.getOrDefaultAccessory(stack)
                  .getDropRule(stack, SlotReference.of(context.player(), key, i), context.deathSource())) {
                  case DESTROY -> DropRule.DESTROY;
                  case KEEP -> DropRule.KEEP;
                  default -> {
                     DropRule defaultDropRule = YigdConfig.getConfig().compatConfig.defaultAccessoriesDropRule;
                     yield defaultDropRule == DropRule.PUT_IN_GRAVE
                        ? ((YigdEvents.DropRuleEvent)NeoForge.EVENT_BUS.post(new YigdEvents.DropRuleEvent(stack, -1, context, true))).getDropRule()
                        : defaultDropRule;
                  }
               };
            }

            for (int i = 0; i < inventorySlot.cosmetic.size(); i++) {
               AccessoriesCompat.AccessoriesInventorySlot slot = (AccessoriesCompat.AccessoriesInventorySlot)inventorySlot.cosmetic.get(i);
               ItemStack stack = slot.graveItem.stack;

               slot.graveItem.dropRule = switch (AccessoriesAPI.getOrDefaultAccessory(stack)
                  .getDropRule(stack, SlotReference.of(context.player(), key, i), context.deathSource())) {
                  case DESTROY -> DropRule.DESTROY;
                  case KEEP -> DropRule.KEEP;
                  default -> {
                     DropRule defaultDropRule = YigdConfig.getConfig().compatConfig.defaultAccessoriesDropRule;
                     yield defaultDropRule == DropRule.PUT_IN_GRAVE
                        ? ((YigdEvents.DropRuleEvent)NeoForge.EVENT_BUS.post(new YigdEvents.DropRuleEvent(stack, -1, context, true))).getDropRule()
                        : defaultDropRule;
                  }
               };
            }
         }
      }

      @Override
      public NonNullList<GraveItem> getAsGraveItemList() {
         NonNullList<GraveItem> allItems = NonNullList.create();

         for (AccessoriesCompat.AccessoriesInventoryGroup group : this.inventory.values()) {
            for (AccessoriesCompat.AccessoriesInventorySlot slot : group.normal) {
               allItems.add(new GraveItem(slot.graveItem.stack, slot.graveItem.dropRule));
            }

            for (AccessoriesCompat.AccessoriesInventorySlot slot : group.cosmetic) {
               allItems.add(new GraveItem(slot.graveItem.stack, slot.graveItem.dropRule));
            }
         }

         return allItems;
      }

      @Override
      public CompatComponent<Map<String, AccessoriesCompat.AccessoriesInventoryGroup>> filterInv(Predicate<DropRule> predicate) {
         Map<String, AccessoriesCompat.AccessoriesInventoryGroup> filtered = new HashMap<>();

         for (Entry<String, AccessoriesCompat.AccessoriesInventoryGroup> entry : this.inventory.entrySet()) {
            AccessoriesCompat.AccessoriesInventoryGroup inventorySlot = entry.getValue();
            NonNullList<AccessoriesCompat.AccessoriesInventorySlot> normalSlot = NonNullList.create();
            NonNullList<AccessoriesCompat.AccessoriesInventorySlot> cosmeticSlot = NonNullList.create();

            for (AccessoriesCompat.AccessoriesInventorySlot slot : inventorySlot.normal) {
               if (predicate.test(slot.graveItem.dropRule)) {
                  normalSlot.add(slot);
               } else {
                  normalSlot.add(AccessoriesCompat.AccessoriesInventorySlot.empty(slot.visible));
               }
            }

            for (AccessoriesCompat.AccessoriesInventorySlot slot : inventorySlot.cosmetic) {
               if (predicate.test(slot.graveItem.dropRule)) {
                  cosmeticSlot.add(slot);
               } else {
                  cosmeticSlot.add(AccessoriesCompat.AccessoriesInventorySlot.empty(slot.visible));
               }
            }

            filtered.put(entry.getKey(), new AccessoriesCompat.AccessoriesInventoryGroup(normalSlot, cosmeticSlot));
         }

         return new AccessoriesCompat.AccessoriesCompatComponent(filtered);
      }

      @Override
      public boolean removeItem(Predicate<ItemStack> predicate, int itemCount) {
         for (AccessoriesCompat.AccessoriesInventoryGroup inventorySlot : this.inventory.values()) {
            for (AccessoriesCompat.AccessoriesInventorySlot slot : inventorySlot.normal) {
               ItemStack stack = slot.graveItem.stack;
               if (predicate.test(stack)) {
                  stack.shrink(itemCount);
                  return true;
               }
            }
         }

         return false;
      }

      @Override
      public void clear() {
         for (AccessoriesCompat.AccessoriesInventoryGroup inventorySlot : this.inventory.values()) {
            Collections.fill(inventorySlot.normal, AccessoriesCompat.AccessoriesInventorySlot.empty(true));
            Collections.fill(inventorySlot.cosmetic, AccessoriesCompat.AccessoriesInventorySlot.empty(true));
         }
      }

      @Override
      public CompoundTag writeNbt(Provider registryLookup) {
         CompoundTag nbt = new CompoundTag();

         for (Entry<String, AccessoriesCompat.AccessoriesInventoryGroup> entry : this.inventory.entrySet()) {
            AccessoriesCompat.AccessoriesInventoryGroup inventorySlot = entry.getValue();
            CompoundTag slotNbt = new CompoundTag();
            CompoundTag normalNbt = InventoryComponent.listToNbt(inventorySlot.normal, slot -> {
               CompoundTag itemNbt = !slot.graveItem.stack.isEmpty() ? (CompoundTag)slot.graveItem.stack.save(registryLookup) : new CompoundTag();
               itemNbt.putString("dropRule", slot.graveItem.dropRule.name());
               itemNbt.putBoolean("visible", slot.visible);
               return itemNbt;
            }, slot -> slot.graveItem.stack.isEmpty() && slot.visible);
            CompoundTag cosmeticNbt = InventoryComponent.listToNbt(inventorySlot.cosmetic, slot -> {
               CompoundTag itemNbt = !slot.graveItem.stack.isEmpty() ? (CompoundTag)slot.graveItem.stack.save(registryLookup) : new CompoundTag();
               itemNbt.putString("dropRule", slot.graveItem.dropRule.name());
               itemNbt.putBoolean("visible", slot.visible);
               return itemNbt;
            }, slot -> slot.graveItem.stack.isEmpty() && slot.visible);
            slotNbt.put("normal", normalNbt);
            slotNbt.put("cosmetic", cosmeticNbt);
            nbt.put(entry.getKey(), slotNbt);
         }

         return nbt;
      }
   }

   public record AccessoriesInventoryGroup(
      NonNullList<AccessoriesCompat.AccessoriesInventorySlot> normal, NonNullList<AccessoriesCompat.AccessoriesInventorySlot> cosmetic
   ) {
      private void addAllNonEmptyToList(Collection<GraveItem> list) {
         this.addAllNonEmptyToList(list, slot -> new GraveItem(slot.graveItem.stack, slot.graveItem.dropRule));
      }

      private void addAllNonEmptyToStackList(Collection<ItemStack> list) {
         this.addAllNonEmptyToList(list, slot -> slot.graveItem.stack);
      }

      private <T> void addAllNonEmptyToList(Collection<T> list, Function<AccessoriesCompat.AccessoriesInventorySlot, T> mapFunc) {
         for (AccessoriesCompat.AccessoriesInventorySlot slot : this.normal) {
            if (!slot.graveItem.stack.isEmpty()) {
               list.add(mapFunc.apply(slot));
            }
         }

         for (AccessoriesCompat.AccessoriesInventorySlot slot : this.cosmetic) {
            if (!slot.graveItem.stack.isEmpty()) {
               list.add(mapFunc.apply(slot));
            }
         }
      }
   }

   public static class AccessoriesInventorySlot {
      public GraveItem graveItem;
      public boolean visible;

      public AccessoriesInventorySlot(ItemStack stack, DropRule dropRule, boolean visible) {
         this.graveItem = new GraveItem(stack, dropRule);
         this.visible = visible;
      }

      public static AccessoriesCompat.AccessoriesInventorySlot empty(boolean visible) {
         return new AccessoriesCompat.AccessoriesInventorySlot(ItemStack.EMPTY, DropRule.PUT_IN_GRAVE, visible);
      }
   }
}
