package org.crypticdev.ancient_artifacts.mixin.world.inventory;

import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
   @Shadow
   @Final
   private DataSlot cost;
   @Shadow
   @Final
   public static int INPUT_SLOT;
   @Shadow
   @Final
   public static int ADDITIONAL_SLOT;
   @Shadow
   private int repairItemCountCost;
   @Shadow
   @Nullable
   private String itemName;

   @Shadow
   public abstract boolean setItemName(String var1);

   public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
      super(menuType, i, inventory, containerLevelAccess);
   }

   @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
   private void ancientArtifacts$createRefinementResult(CallbackInfo ci) {
      ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
      ItemStack addition = this.inputSlots.getItem(ADDITIONAL_SLOT);
      ArtifactRefinement.tryCombine(base, addition).ifPresent(result -> {
         if (result.has((DataComponentType)TensuraDataComponents.EP.get()) && addition.has((DataComponentType)TensuraDataComponents.EP.get())) {
            double maxEP = (Double)result.get((DataComponentType)TensuraDataComponents.MAX_EP.get());
            double currentEP = (Double)result.get((DataComponentType)TensuraDataComponents.EP.get());
            double currentEPDurability = (Double)result.get((DataComponentType)TensuraDataComponents.EP_DURABILITY.get());
            double additionalEP = (Double)addition.get((DataComponentType)TensuraDataComponents.EP.get());
            double additionalEPDurability = (Double)addition.get((DataComponentType)TensuraDataComponents.EP_DURABILITY.get());
            double EP = Math.min(maxEP, currentEP + additionalEP);
            double EPDurability = Math.min(EP, currentEPDurability + additionalEPDurability);
            result.set((DataComponentType)TensuraDataComponents.EP.get(), EP);
            result.set((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), EPDurability);
         }

         if (this.itemName != null && !StringUtil.isBlank(this.itemName)) {
            if (!this.itemName.equals(base.getHoverName().getString())) {
               result.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
            }
         } else if (base.has(DataComponents.CUSTOM_NAME)) {
            result.remove(DataComponents.CUSTOM_NAME);
         }

         this.resultSlots.setItem(0, result);
         this.cost.set(1);
         this.repairItemCountCost = 1;
         ci.cancel();
      });
   }
}
