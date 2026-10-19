package com.b1n_ry.yigd.compat;

import com.b1n_ry.yigd.components.InventoryComponent;
import com.b1n_ry.yigd.config.CompatConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.events.YigdEvents;
import com.b1n_ry.yigd.util.DropRule;
import java.util.Collection;
import java.util.function.Predicate;
import lain.mods.cos.impl.ModObjects;
import lain.mods.cos.impl.inventory.InventoryCosArmor;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;

public class CosmeticArmorCompat implements InvModCompat<NonNullList<GraveItem>> {
   @Override
   public String getModName() {
      return "cosmeticarmor";
   }

   @Override
   public void clear(ServerPlayer player) {
      InventoryCosArmor inv = ModObjects.invMan.getCosArmorInventory(player.getUUID());
      inv.clearContent();
   }

   @Override
   public CompatComponent<NonNullList<GraveItem>> readNbt(CompoundTag nbt, Provider registries) {
      NonNullList<GraveItem> items = InventoryComponent.listFromNbt(nbt, itemTag -> {
         DropRule dropRule = DropRule.valueOf(itemTag.getString("dropRule"));
         ItemStack stack = ItemStack.parse(registries, itemTag).orElse(ItemStack.EMPTY);
         return new GraveItem(stack, dropRule);
      }, InventoryComponent.EMPTY_GRAVE_ITEM);
      return new CosmeticArmorCompat.CosmeticArmorCompatComponent(items);
   }

   @Override
   public CompatComponent<NonNullList<GraveItem>> getNewComponent(ServerPlayer player) {
      return new CosmeticArmorCompat.CosmeticArmorCompatComponent(player);
   }

   static class CosmeticArmorCompatComponent extends CompatComponent<NonNullList<GraveItem>> {
      public CosmeticArmorCompatComponent(ServerPlayer player) {
         super(player);
      }

      public CosmeticArmorCompatComponent(NonNullList<GraveItem> inventory) {
         super(inventory);
      }

      public NonNullList<GraveItem> getInventory(ServerPlayer player) {
         InventoryCosArmor inventory = ModObjects.invMan.getCosArmorInventory(player.getUUID());
         NonNullList<GraveItem> list = NonNullList.create();

         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            list.add(new GraveItem(stack, DropRule.PUT_IN_GRAVE));
         }

         return list;
      }

      @Override
      public NonNullList<ItemStack> storeToPlayer(ServerPlayer player) {
         NonNullList<ItemStack> extraItems = NonNullList.create();
         InventoryCosArmor cosArmor = ModObjects.invMan.getCosArmorInventory(player.getUUID());

         for (int i = 0; i < cosArmor.getContainerSize() && i < this.inventory.size(); i++) {
            GraveItem graveItem = ((GraveItem)this.inventory.get(i)).copy();
            if (cosArmor.getItem(i).isEmpty()) {
               cosArmor.setItem(i, graveItem.stack);
            } else {
               extraItems.add(graveItem.stack);
            }
         }

         return extraItems;
      }

      @Override
      public void handleDropRules(DeathContext context) {
         CompatConfig compatConfig = YigdConfig.getConfig().compatConfig;

         for (GraveItem graveItem : this.inventory) {
            if (!graveItem.stack.isEmpty()) {
               DropRule dropRule = compatConfig.defaultCosmeticArmorDropRule;
               if (dropRule == DropRule.PUT_IN_GRAVE) {
                  dropRule = ((YigdEvents.DropRuleEvent)NeoForge.EVENT_BUS.post(new YigdEvents.DropRuleEvent(graveItem.stack, -1, context, true)))
                     .getDropRule();
                  graveItem.dropRule = dropRule;
               }
            }
         }
      }

      @Override
      public NonNullList<GraveItem> getAsGraveItemList() {
         return NonNullList.copyOf((Collection)this.inventory);
      }

      @Override
      public void clear() {
         this.inventory.clear();
      }

      @Override
      public CompoundTag writeNbt(Provider registries) {
         return InventoryComponent.listToNbt(this.inventory, graveItem -> {
            CompoundTag itemTag = (CompoundTag)graveItem.stack.save(registries);
            itemTag.putString("dropRule", graveItem.dropRule.toString());
            return itemTag;
         }, graveItem -> graveItem.stack.isEmpty());
      }

      @Override
      public boolean removeItem(Predicate<ItemStack> predicate, int itemCount) {
         for (GraveItem graveItem : this.inventory) {
            ItemStack stack = graveItem.stack;
            if (predicate.test(stack)) {
               stack.shrink(itemCount);
               return true;
            }
         }

         return false;
      }

      @Override
      public CompatComponent<NonNullList<GraveItem>> filterInv(Predicate<DropRule> predicate) {
         NonNullList<GraveItem> list = NonNullList.create();

         for (GraveItem graveItem : this.inventory) {
            if (predicate.test(graveItem.dropRule)) {
               list.add(graveItem);
            } else {
               list.add(InventoryComponent.EMPTY_GRAVE_ITEM);
            }
         }

         return new CosmeticArmorCompat.CosmeticArmorCompatComponent(list);
      }

      @Override
      public NonNullList<GraveItem> merge(CompatComponent<?> mergingComponent, ServerPlayer merger) {
         NonNullList<GraveItem> extraItems = NonNullList.create();
         NonNullList<GraveItem> mergingItems = (NonNullList<GraveItem>)mergingComponent.inventory;

         for (int i = 0; i < mergingItems.size(); i++) {
            GraveItem graveItem = (GraveItem)mergingItems.get(i);
            if (i >= this.inventory.size()) {
               extraItems.add(graveItem);
            } else {
               ItemStack thisStack = ((GraveItem)this.inventory.get(i)).stack;
               if (thisStack.isEmpty()) {
                  this.inventory.set(i, graveItem);
               } else {
                  extraItems.add(graveItem);
               }
            }
         }

         return extraItems;
      }
   }
}
