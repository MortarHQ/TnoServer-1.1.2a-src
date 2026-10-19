package com.mafuyu404.oneenoughitem.mixin;

import com.mafuyu404.oneenoughitem.client.ClientContext;
import com.mafuyu404.oneenoughitem.init.ItemReplacementCache;
import com.mafuyu404.oneenoughitem.init.access.CreativeModeTabIconRefresher;
import com.mafuyu404.oneenoughitem.util.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTab.class)
public abstract class CreativeModeTabMixin implements CreativeModeTabIconRefresher {
   @Mutable
   @Shadow
   @Nullable
   private ItemStack iconItemStack;
   @Shadow
   private Collection<ItemStack> displayItems;
   @Shadow
   private Set<ItemStack> displayItemsSearchTab;
   @Shadow
   @Final
   private Supplier<ItemStack> iconGenerator;

   @Inject(method = "buildContents", at = @At("HEAD"))
   private void onBuildStart(ItemDisplayParameters itemDisplayParameters, CallbackInfo ci) {
      ClientContext.beginBuilding();
   }

   @Inject(method = "buildContents", at = @At("RETURN"))
   private void onBuildEnd(ItemDisplayParameters itemDisplayParameters, CallbackInfo ci) {
      this.oei$applyReplacementToTabItems();
      ClientContext.endBuilding();
   }

   @Override
   public void oei$refreshIconCache() {
      this.iconItemStack = null;
   }

   private void oei$applyReplacementToTabItems() {
      try {
         if (this.displayItems != null && !this.displayItems.isEmpty()) {
            ArrayList<ItemStack> original = new ArrayList<>(this.displayItems);
            this.displayItems.clear();

            for (ItemStack stack : original) {
               ItemStack replaced = this.oei$replaceStackPreservingComponents(stack);
               this.displayItems.add(replaced);
            }
         }

         if (this.displayItemsSearchTab != null && !this.displayItemsSearchTab.isEmpty()) {
            ArrayList<ItemStack> originalSearch = new ArrayList<>(this.displayItemsSearchTab);
            this.displayItemsSearchTab.clear();

            for (ItemStack stack : originalSearch) {
               ItemStack replaced = this.oei$replaceStackPreservingComponents(stack);
               this.displayItemsSearchTab.add(replaced);
            }
         }
      } catch (Throwable var5) {
      }
   }

   private ItemStack oei$replaceStackPreservingComponents(ItemStack original) {
      try {
         Item srcItem = original.getItem();
         String srcId = Utils.getItemRegistryName(srcItem);
         if (Utils.isItemIdEmpty(srcId)) {
            return original;
         }

         String targetId = ItemReplacementCache.matchItem(srcId);
         if (targetId != null && !targetId.equals(srcId)) {
            Item targetItem = Utils.getItemById(targetId);
            if (targetItem == null) {
               return original;
            }

            PatchedDataComponentMap originalComponents = ((ItemStackAccessor)original).oei$getComponents();
            DataComponentPatch patch = originalComponents.asPatch();
            return new ItemStack(targetItem.builtInRegistryHolder(), 1, patch);
         } else {
            return original;
         }
      } catch (Throwable t) {
         return original;
      }
   }
}
