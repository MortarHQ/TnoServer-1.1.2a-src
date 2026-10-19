package com.b1n_ry.yigd.compat;

import com.b1n_ry.yigd.components.InventoryComponent;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.util.DropRule;
import com.b1n_ry.yigd.util.GraveItemModificationConsumer;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public abstract class CompatComponent<T> {
   protected T inventory;

   public CompatComponent(ServerPlayer player) {
      this.inventory = this.getInventory(player);
   }

   public CompatComponent(T inventory) {
      this.inventory = inventory;
   }

   public abstract T getInventory(ServerPlayer var1);

   public NonNullList<ItemStack> pullBindingCurseItems(ServerPlayer playerRef) {
      return NonNullList.create();
   }

   public abstract NonNullList<GraveItem> merge(CompatComponent<?> var1, ServerPlayer var2);

   public abstract NonNullList<ItemStack> storeToPlayer(ServerPlayer var1);

   public abstract void handleDropRules(DeathContext var1);

   public abstract NonNullList<GraveItem> getAsGraveItemList();

   public abstract CompatComponent<T> filterInv(Predicate<DropRule> var1);

   public abstract boolean removeItem(Predicate<ItemStack> var1, int var2);

   public void dropItems(ServerLevel world, Vec3 pos) {
      for (GraveItem graveItem : this.getAsGraveItemList()) {
         ItemStack stack = graveItem.stack;
         if (!stack.isEmpty()) {
            InventoryComponent.dropItemIfToBeDropped(graveItem.stack, pos.x, pos.y, pos.z, world);
         }
      }
   }

   public void dropGraveItems(ServerLevel world, Vec3 pos) {
      for (GraveItem graveItem : this.getAsGraveItemList()) {
         ItemStack stack = graveItem.stack;
         if (!stack.isEmpty() && graveItem.dropRule != DropRule.KEEP && graveItem.dropRule != DropRule.DESTROY) {
            graveItem.dropRule = DropRule.DROP;
            InventoryComponent.dropItemIfToBeDropped(graveItem.stack, pos.x, pos.y, pos.z, world);
         }
      }
   }

   public abstract void clear();

   public boolean containsGraveItems() {
      for (GraveItem graveItem : this.getAsGraveItemList()) {
         if (!graveItem.stack.isEmpty() && graveItem.dropRule == DropRule.PUT_IN_GRAVE) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      return !this.containsAny(stack -> !stack.isEmpty());
   }

   public boolean containsAny(Predicate<ItemStack> predicate) {
      for (GraveItem graveItem : this.getAsGraveItemList()) {
         if (predicate.test(graveItem.stack)) {
            return true;
         }
      }

      return false;
   }

   public void handleGraveItems(GraveItemModificationConsumer modification) {
      for (GraveItem graveItem : this.getAsGraveItemList()) {
         modification.accept(graveItem.stack, -1, graveItem);
      }
   }

   public abstract CompoundTag writeNbt(Provider var1);
}
