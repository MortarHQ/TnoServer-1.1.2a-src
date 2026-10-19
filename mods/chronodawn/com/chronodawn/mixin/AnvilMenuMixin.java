package com.chronodawn.mixin;

import com.chronodawn.anvil.AnvilRepairHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
   public AnvilMenuMixin(MenuType<?> p_39773_, int p_39774_, Inventory p_39775_, ContainerLevelAccess p_39776_) {
      super(p_39773_, p_39774_, p_39775_, p_39776_);
   }

   @Inject(method = "createResult()V", at = @At("HEAD"), cancellable = true)
   private void onCreateResult(CallbackInfo ci) {
      ItemStack leftInput = this.inputSlots.getItem(0);
      ItemStack rightInput = this.inputSlots.getItem(1);
      ItemStack result = AnvilRepairHandler.calculateTimeCrystalRepair(leftInput, rightInput);
      if (result != null) {
         int experienceCost = AnvilRepairHandler.calculateRepairCost(leftInput, result);
         int leftWorkCost = (Integer)leftInput.getOrDefault(DataComponents.REPAIR_COST, 0);
         int newWorkCost = leftWorkCost * 2 + 1;
         result.set(DataComponents.REPAIR_COST, newWorkCost);
         this.resultSlots.setItem(0, result);
         ((AnvilMenuAccessor)this).getCost().set(experienceCost);
         ((AnvilMenuAccessor)this).setRepairItemCountCost(1);
         this.broadcastChanges();
         ci.cancel();
      }
   }
}
