package com.b1n_ry.yigd.compat;

import com.b1n_ry.yigd.components.InventoryComponent;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.events.YigdEvents;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosCompat implements InvModCompat<Map<String, CuriosCompat.CuriosSlotEntry>> {
   @Override
   public String getModName() {
      return "curios";
   }

   @Override
   public void clear(ServerPlayer player) {
      CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
         for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
            IDynamicStackHandler[] stackHandlers = new IDynamicStackHandler[]{stacksHandler.getStacks(), stacksHandler.getCosmeticStacks()};

            for (IDynamicStackHandler dynamicStackHandler : stackHandlers) {
               for (int i = 0; i < dynamicStackHandler.getSlots(); i++) {
                  dynamicStackHandler.setStackInSlot(i, ItemStack.EMPTY);
               }
            }
         }
      });
   }

   @Override
   public CompatComponent<Map<String, CuriosCompat.CuriosSlotEntry>> readNbt(CompoundTag nbt, Provider registries) {
      Map<String, CuriosCompat.CuriosSlotEntry> inventory = new HashMap<>();

      for (String key : nbt.getAllKeys()) {
         CompoundTag slotNbt = nbt.getCompound(key);
         NonNullList<GraveItem> normalSlot = InventoryComponent.listFromNbt(slotNbt.getCompound("normal"), itemNbt -> {
            ItemStack stack = ItemStack.parse(registries, itemNbt).orElse(ItemStack.EMPTY);
            com.b1n_ry.yigd.util.DropRule dropRule;
            if (itemNbt.contains("dropRule")) {
               String dropRuleString = itemNbt.getString("dropRule");
               if (dropRuleString.equals("DEFAULT")) {
                  dropRule = YigdConfig.getConfig().compatConfig.defaultCuriosDropRule;
               } else {
                  dropRule = com.b1n_ry.yigd.util.DropRule.valueOf(dropRuleString);
               }
            } else {
               dropRule = YigdConfig.getConfig().compatConfig.defaultCuriosDropRule;
            }

            return new GraveItem(stack, dropRule);
         }, InventoryComponent.EMPTY_GRAVE_ITEM);
         NonNullList<GraveItem> cosmeticSlot = InventoryComponent.listFromNbt(slotNbt.getCompound("cosmetic"), itemNbt -> {
            ItemStack stack = ItemStack.parse(registries, itemNbt).orElse(ItemStack.EMPTY);
            com.b1n_ry.yigd.util.DropRule dropRule;
            if (itemNbt.contains("dropRule")) {
               String dropRuleString = itemNbt.getString("dropRule");
               if (dropRuleString.equals("DEFAULT")) {
                  dropRule = YigdConfig.getConfig().compatConfig.defaultCuriosDropRule;
               } else {
                  dropRule = com.b1n_ry.yigd.util.DropRule.valueOf(dropRuleString);
               }
            } else {
               dropRule = YigdConfig.getConfig().compatConfig.defaultCuriosDropRule;
            }

            return new GraveItem(stack, dropRule);
         }, InventoryComponent.EMPTY_GRAVE_ITEM);
         inventory.put(key, new CuriosCompat.CuriosSlotEntry(normalSlot, cosmeticSlot));
      }

      return new CuriosCompat.CuriosCompatComponent(inventory);
   }

   @Override
   public CompatComponent<Map<String, CuriosCompat.CuriosSlotEntry>> getNewComponent(ServerPlayer player) {
      return new CuriosCompat.CuriosCompatComponent(player);
   }

   private static class CuriosCompatComponent extends CompatComponent<Map<String, CuriosCompat.CuriosSlotEntry>> {
      public CuriosCompatComponent(ServerPlayer player) {
         super(player);
      }

      public CuriosCompatComponent(Map<String, CuriosCompat.CuriosSlotEntry> inventory) {
         super(inventory);
      }

      public Map<String, CuriosCompat.CuriosSlotEntry> getInventory(ServerPlayer player) {
         Map<String, CuriosCompat.CuriosSlotEntry> inventory = new HashMap<>();
         Optional<ICuriosItemHandler> optionalHandler = CuriosApi.getCuriosInventory(player);
         if (optionalHandler.isEmpty()) {
            return inventory;
         }

         for (Entry<String, ICurioStacksHandler> entry : optionalHandler.get().getCurios().entrySet()) {
            ICurioStacksHandler stacksHandler = entry.getValue();
            NonNullList<GraveItem> normalItems = NonNullList.create();
            NonNullList<GraveItem> cosmeticItems = NonNullList.create();
            IDynamicStackHandler normalEquipped = stacksHandler.getStacks();

            for (int i = 0; i < normalEquipped.getSlots(); i++) {
               normalItems.add(new GraveItem(normalEquipped.getStackInSlot(i).copy(), com.b1n_ry.yigd.util.DropRule.PUT_IN_GRAVE));
            }

            IDynamicStackHandler cosmeticEquipped = stacksHandler.getCosmeticStacks();

            for (int i = 0; i < cosmeticEquipped.getSlots(); i++) {
               cosmeticItems.add(new GraveItem(cosmeticEquipped.getStackInSlot(i).copy(), com.b1n_ry.yigd.util.DropRule.PUT_IN_GRAVE));
            }

            inventory.put(entry.getKey(), new CuriosCompat.CuriosSlotEntry(normalItems, cosmeticItems));
         }

         return inventory;
      }

      @Override
      public NonNullList<GraveItem> merge(CompatComponent<?> mergingComponent, ServerPlayer merger) {
         NonNullList<GraveItem> extraItems = NonNullList.create();
         Map<String, CuriosCompat.CuriosSlotEntry> mergingInventory = (Map<String, CuriosCompat.CuriosSlotEntry>)mergingComponent.inventory;

         for (Entry<String, CuriosCompat.CuriosSlotEntry> entry : mergingInventory.entrySet()) {
            String key = entry.getKey();
            if (!this.inventory.containsKey(key)) {
               entry.getValue().addAllNonEmptyToList(extraItems);
            } else {
               CuriosCompat.CuriosSlotEntry mergingSlot = entry.getValue();
               CuriosCompat.CuriosSlotEntry thisSlot = this.inventory.get(key);

               for (int i = 0; i < mergingSlot.normal.size(); i++) {
                  GraveItem mergingGraveItem = ((GraveItem)mergingSlot.normal.get(i)).copy();
                  ItemStack mergingStack = mergingGraveItem.stack;
                  if (!mergingStack.isEmpty()) {
                     if (thisSlot.normal.size() <= i) {
                        extraItems.add(mergingGraveItem);
                     } else {
                        GraveItem currentGraveItem = (GraveItem)thisSlot.normal.get(i);
                        ItemStack thisStack = currentGraveItem.stack;
                        if (YigdConfig.getConfig().graveConfig.treatBindingCurse && this.blockUnequip(mergingStack, key, i, merger, false)) {
                           extraItems.add(currentGraveItem);
                           thisSlot.normal.set(i, mergingGraveItem);
                        } else if (!thisStack.isEmpty()) {
                           extraItems.add(mergingGraveItem);
                        } else {
                           thisSlot.normal.set(i, mergingGraveItem);
                        }
                     }
                  }
               }

               for (int i = 0; i < mergingSlot.cosmetic.size(); i++) {
                  GraveItem mergingGraveItem = ((GraveItem)mergingSlot.cosmetic.get(i)).copy();
                  ItemStack mergingStack = mergingGraveItem.stack;
                  if (!mergingStack.isEmpty()) {
                     if (thisSlot.cosmetic.size() <= i) {
                        extraItems.add(mergingGraveItem);
                     } else {
                        GraveItem currentGraveItem = (GraveItem)thisSlot.cosmetic.get(i);
                        ItemStack thisStack = currentGraveItem.stack;
                        if (YigdConfig.getConfig().graveConfig.treatBindingCurse && this.blockUnequip(mergingStack, key, i, merger, true)) {
                           extraItems.add(currentGraveItem);
                           thisSlot.cosmetic.set(i, mergingGraveItem);
                        } else if (!thisStack.isEmpty()) {
                           extraItems.add(mergingGraveItem);
                        } else {
                           thisSlot.cosmetic.set(i, mergingGraveItem);
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

         for (Entry<String, CuriosCompat.CuriosSlotEntry> entry : this.inventory.entrySet()) {
            CuriosCompat.CuriosSlotEntry inventorySlot = entry.getValue();

            for (int i = 0; i < inventorySlot.normal.size(); i++) {
               GraveItem graveItem = (GraveItem)inventorySlot.normal.get(i);
               ItemStack stack = graveItem.stack;
               boolean isBound = this.blockUnequip(stack, entry.getKey(), i, playerRef, false);
               if (isBound) {
                  noUnequipItems.add(stack);
                  graveItem.stack = ItemStack.EMPTY;
               }
            }

            for (int i = 0; i < inventorySlot.cosmetic.size(); i++) {
               GraveItem graveItem = (GraveItem)inventorySlot.cosmetic.get(i);
               ItemStack stack = graveItem.stack;
               boolean isBound = this.blockUnequip(stack, entry.getKey(), i, playerRef, true);
               if (isBound) {
                  noUnequipItems.add(stack);
                  graveItem.stack = ItemStack.EMPTY;
               }
            }
         }

         return noUnequipItems;
      }

      private boolean blockUnequip(ItemStack stack, String key, int index, ServerPlayer playerRef, boolean cosmetic) {
         Optional<ICurio> iCurio = CuriosApi.getCurio(stack);
         return !iCurio.<Boolean>map(curio -> curio.canUnequip(new SlotContext(key, playerRef, index, cosmetic, false))).orElse(true);
      }

      @Override
      public NonNullList<ItemStack> storeToPlayer(ServerPlayer player) {
         NonNullList<ItemStack> extraItems = NonNullList.create();
         Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
         if (optional.isEmpty()) {
            return extraItems;
         }

         Map<String, ICurioStacksHandler> stacksHandlerMap = optional.get().getCurios();

         for (Entry<String, CuriosCompat.CuriosSlotEntry> entry : this.inventory.entrySet()) {
            String key = entry.getKey();
            if (!stacksHandlerMap.containsKey(key)) {
               entry.getValue().addAllNonEmptyToStackList(extraItems);
            } else {
               ICurioStacksHandler stacksHandler = stacksHandlerMap.get(key);
               CuriosCompat.CuriosSlotEntry slotEntry = entry.getValue();
               IDynamicStackHandler normalEquipped = stacksHandler.getStacks();
               IDynamicStackHandler cosmeticEquipped = stacksHandler.getCosmeticStacks();

               for (int i = 0; i < slotEntry.normal.size(); i++) {
                  GraveItem graveItem = ((GraveItem)slotEntry.normal.get(i)).copy();
                  if (i >= normalEquipped.getSlots()) {
                     extraItems.add(graveItem.stack);
                  } else {
                     normalEquipped.setStackInSlot(i, graveItem.stack);
                  }
               }

               for (int i = 0; i < slotEntry.cosmetic.size(); i++) {
                  GraveItem graveItem = ((GraveItem)slotEntry.cosmetic.get(i)).copy();
                  if (i >= cosmeticEquipped.getSlots()) {
                     extraItems.add(graveItem.stack);
                  } else {
                     cosmeticEquipped.setStackInSlot(i, graveItem.stack);
                  }
               }
            }
         }

         return extraItems;
      }

      private DropRule getDropRule(
         ItemStack stack, String key, int index, DeathContext context, boolean cosmetic, List<Tuple<Predicate<ItemStack>, DropRule>> overrides
      ) {
         for (Tuple<Predicate<ItemStack>, DropRule> t : overrides) {
            if (((Predicate)t.getA()).test(stack)) {
               return (DropRule)t.getB();
            }
         }

         Optional<ICurio> iCurio = CuriosApi.getCurio(stack);
         return iCurio.<DropRule>map(curio -> curio.getDropRule(new SlotContext(key, context.player(), index, cosmetic, false), context.deathSource(), true))
            .orElse(DropRule.DEFAULT);
      }

      @Override
      public void handleDropRules(DeathContext context) {
         ServerPlayer player = context.player();
         List<Tuple<Predicate<ItemStack>, DropRule>> overrides = new ArrayList<>();
         CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            DropRulesEvent event = (DropRulesEvent)NeoForge.EVENT_BUS.post(new DropRulesEvent(player, handler, context.deathSource(), 0, false));
            overrides.addAll(event.getOverrides());
         });

         for (Entry<String, CuriosCompat.CuriosSlotEntry> entry : this.inventory.entrySet()) {
            String key = entry.getKey();
            CuriosCompat.CuriosSlotEntry inventorySlot = entry.getValue();

            for (int i = 0; i < inventorySlot.normal.size(); i++) {
               GraveItem graveItem = (GraveItem)inventorySlot.normal.get(i);
               ItemStack stack = graveItem.stack;

               graveItem.dropRule = switch (this.getDropRule(stack, key, i, context, false, overrides)) {
                  case DESTROY -> com.b1n_ry.yigd.util.DropRule.DESTROY;
                  case ALWAYS_KEEP -> com.b1n_ry.yigd.util.DropRule.KEEP;
                  default -> {
                     com.b1n_ry.yigd.util.DropRule defaultDropRule = YigdConfig.getConfig().compatConfig.defaultCuriosDropRule;
                     yield defaultDropRule == com.b1n_ry.yigd.util.DropRule.PUT_IN_GRAVE
                        ? ((YigdEvents.DropRuleEvent)NeoForge.EVENT_BUS.post(new YigdEvents.DropRuleEvent(stack, -1, context, true))).getDropRule()
                        : defaultDropRule;
                  }
               };
            }

            for (int i = 0; i < inventorySlot.cosmetic.size(); i++) {
               GraveItem graveItem = (GraveItem)inventorySlot.cosmetic.get(i);
               ItemStack stack = graveItem.stack;

               graveItem.dropRule = switch (this.getDropRule(stack, key, i, context, true, overrides)) {
                  case DESTROY -> com.b1n_ry.yigd.util.DropRule.DESTROY;
                  case ALWAYS_KEEP -> com.b1n_ry.yigd.util.DropRule.KEEP;
                  default -> {
                     com.b1n_ry.yigd.util.DropRule defaultDropRule = YigdConfig.getConfig().compatConfig.defaultCuriosDropRule;
                     yield defaultDropRule == com.b1n_ry.yigd.util.DropRule.PUT_IN_GRAVE
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

         for (CuriosCompat.CuriosSlotEntry slotEntry : this.inventory.values()) {
            allItems.addAll(slotEntry.normal);
            allItems.addAll(slotEntry.cosmetic);
         }

         return allItems;
      }

      @Override
      public CompatComponent<Map<String, CuriosCompat.CuriosSlotEntry>> filterInv(Predicate<com.b1n_ry.yigd.util.DropRule> predicate) {
         Map<String, CuriosCompat.CuriosSlotEntry> filtered = new HashMap<>();

         for (Entry<String, CuriosCompat.CuriosSlotEntry> entry : this.inventory.entrySet()) {
            CuriosCompat.CuriosSlotEntry inventorySlot = entry.getValue();
            NonNullList<GraveItem> normalSlot = NonNullList.create();
            NonNullList<GraveItem> cosmeticSlot = NonNullList.create();

            for (GraveItem graveItem : inventorySlot.normal) {
               if (predicate.test(graveItem.dropRule)) {
                  normalSlot.add(graveItem);
               } else {
                  normalSlot.add(InventoryComponent.EMPTY_GRAVE_ITEM);
               }
            }

            for (GraveItem graveItem : inventorySlot.cosmetic) {
               if (predicate.test(graveItem.dropRule)) {
                  cosmeticSlot.add(graveItem);
               } else {
                  cosmeticSlot.add(InventoryComponent.EMPTY_GRAVE_ITEM);
               }
            }

            filtered.put(entry.getKey(), new CuriosCompat.CuriosSlotEntry(normalSlot, cosmeticSlot));
         }

         return new CuriosCompat.CuriosCompatComponent(filtered);
      }

      @Override
      public boolean removeItem(Predicate<ItemStack> predicate, int itemCount) {
         for (CuriosCompat.CuriosSlotEntry inventorySlot : this.inventory.values()) {
            for (GraveItem graveItem : inventorySlot.normal) {
               ItemStack stack = graveItem.stack;
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
         for (CuriosCompat.CuriosSlotEntry inventorySlot : this.inventory.values()) {
            Collections.fill(inventorySlot.normal, InventoryComponent.EMPTY_GRAVE_ITEM);
            Collections.fill(inventorySlot.cosmetic, InventoryComponent.EMPTY_GRAVE_ITEM);
         }
      }

      @Override
      public CompoundTag writeNbt(Provider registries) {
         CompoundTag nbt = new CompoundTag();

         for (Entry<String, CuriosCompat.CuriosSlotEntry> entry : this.inventory.entrySet()) {
            CuriosCompat.CuriosSlotEntry inventorySlot = entry.getValue();
            CompoundTag slotNbt = new CompoundTag();
            CompoundTag normalNbt = InventoryComponent.listToNbt(inventorySlot.normal, graveItem -> {
               CompoundTag itemNbt = (CompoundTag)graveItem.stack.save(registries);
               itemNbt.putString("dropRule", graveItem.dropRule.name());
               return itemNbt;
            }, graveItem -> graveItem.stack.isEmpty());
            CompoundTag cosmeticNbt = InventoryComponent.listToNbt(inventorySlot.cosmetic, graveItem -> {
               CompoundTag itemNbt = (CompoundTag)graveItem.stack.save(registries);
               itemNbt.putString("dropRule", graveItem.dropRule.name());
               return itemNbt;
            }, graveItem -> graveItem.stack.isEmpty());
            slotNbt.put("normal", normalNbt);
            slotNbt.put("cosmetic", cosmeticNbt);
            nbt.put(entry.getKey(), slotNbt);
         }

         return nbt;
      }
   }

   public record CuriosSlotEntry(NonNullList<GraveItem> normal, NonNullList<GraveItem> cosmetic) {
      private void addAllNonEmptyToList(Collection<GraveItem> list) {
         this.addAllNonEmptyToList(list, graveItem -> graveItem);
      }

      private void addAllNonEmptyToStackList(Collection<ItemStack> list) {
         this.addAllNonEmptyToList(list, graveItem -> graveItem.stack);
      }

      private <T> void addAllNonEmptyToList(Collection<T> list, Function<GraveItem, T> mapFunc) {
         for (GraveItem graveItem : this.normal) {
            if (!graveItem.stack.isEmpty()) {
               list.add(mapFunc.apply(graveItem.copy()));
            }
         }

         for (GraveItem graveItem : this.cosmetic) {
            if (!graveItem.stack.isEmpty()) {
               list.add(mapFunc.apply(graveItem.copy()));
            }
         }
      }
   }
}
