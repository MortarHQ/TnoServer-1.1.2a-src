package com.blackgear.vanillabackport.core.mixin.common.item;

import com.blackgear.vanillabackport.common.api.bundle.BundleFeatures;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.BundleContents.Mutable;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin {
   @Unique
   private static final int FULL_BAR_COLOR = ARGB32.colorFromFloat(1.0F, 1.0F, 0.33F, 0.33F);
   @Unique
   private static final int BAR_COLOR = ARGB32.colorFromFloat(1.0F, 0.44F, 0.53F, 1.0F);

   @Shadow
   protected abstract void playInsertSound(Entity var1);

   @Shadow
   protected abstract void playRemoveOneSound(Entity var1);

   @Inject(
      method = "overrideStackedOnOther(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/Slot;Lnet/minecraft/world/inventory/ClickAction;Lnet/minecraft/world/entity/player/Player;)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   private void vb$overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player, CallbackInfoReturnable<Boolean> cir) {
      if (BundleFeatures.onBundleUpdate()) {
         BundleContents contents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
         if (contents == null) {
            cir.setReturnValue(false);
         } else {
            ItemStack itemInSlot = slot.getItem();
            Mutable mutable = new Mutable(contents);
            if (action == ClickAction.PRIMARY && !itemInSlot.isEmpty()) {
               if (mutable.tryTransfer(slot, player) > 0) {
                  this.playInsertSound(player);
               } else {
                  BundleFeatures.playInsertFailSound(player);
               }

               stack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
               BundleFeatures.broadcastChangesOnContainerMenu(player);
               cir.setReturnValue(true);
            } else if (action == ClickAction.SECONDARY && itemInSlot.isEmpty()) {
               ItemStack removed = mutable.removeOne();
               if (removed != null) {
                  ItemStack insert = slot.safeInsert(removed);
                  if (insert.getCount() > 0) {
                     mutable.tryInsert(insert);
                  } else {
                     this.playRemoveOneSound(player);
                  }
               }

               stack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
               BundleFeatures.broadcastChangesOnContainerMenu(player);
               cir.setReturnValue(true);
            } else {
               cir.setReturnValue(false);
            }
         }
      }
   }

   @Inject(
      method = "overrideOtherStackedOnMe(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/Slot;Lnet/minecraft/world/inventory/ClickAction;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/SlotAccess;)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   private void vb$overrideOtherStackedOnMe(
      ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access, CallbackInfoReturnable<Boolean> cir
   ) {
      if (BundleFeatures.onBundleUpdate()) {
         if (action == ClickAction.PRIMARY && other.isEmpty()) {
            BundleFeatures.toggleSelectedItem(stack, -1);
            cir.setReturnValue(false);
         } else {
            BundleContents contents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
            if (contents == null) {
               cir.setReturnValue(false);
            } else {
               Mutable mutable = new Mutable(contents);
               if (action == ClickAction.PRIMARY && !other.isEmpty()) {
                  if (slot.allowModification(player) && mutable.tryInsert(other) > 0) {
                     this.playInsertSound(player);
                  } else {
                     BundleFeatures.playInsertFailSound(player);
                  }

                  stack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
                  BundleFeatures.broadcastChangesOnContainerMenu(player);
                  cir.setReturnValue(true);
               } else if (action == ClickAction.SECONDARY && other.isEmpty()) {
                  if (slot.allowModification(player)) {
                     ItemStack removed = mutable.removeOne();
                     if (removed != null) {
                        this.playRemoveOneSound(player);
                        access.set(removed);
                     }
                  }

                  stack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
                  BundleFeatures.broadcastChangesOnContainerMenu(player);
                  cir.setReturnValue(true);
               } else {
                  BundleFeatures.toggleSelectedItem(stack, -1);
                  cir.setReturnValue(false);
               }
            }
         }
      }
   }

   @Inject(method = "getBarColor(Lnet/minecraft/world/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
   private void vb$getBarColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
      if (BundleFeatures.onBundleUpdate()) {
         BundleContents contents = (BundleContents)stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
         cir.setReturnValue(contents.weight().compareTo(Fraction.ONE) >= 0 ? FULL_BAR_COLOR : BAR_COLOR);
      }
   }

   @Inject(method = "dropContents(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z", at = @At("HEAD"), cancellable = true)
   private static void vb$dropContents(ItemStack stack, Player player, CallbackInfoReturnable<Boolean> cir) {
      if (BundleFeatures.onBundleUpdate()) {
         BundleContents contents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
         if (contents != null && !contents.isEmpty()) {
            Optional<ItemStack> optional = BundleFeatures.removeOneItemFromBundle(stack, player, contents);
            if (optional.isPresent()) {
               player.drop(optional.get(), true);
               cir.setReturnValue(true);
            } else {
               cir.setReturnValue(false);
            }
         } else {
            cir.setReturnValue(false);
         }
      }
   }

   @Inject(
      method = "appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void vb$appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci) {
      if (BundleFeatures.onBundleUpdate()) {
         ci.cancel();
      }
   }
}
