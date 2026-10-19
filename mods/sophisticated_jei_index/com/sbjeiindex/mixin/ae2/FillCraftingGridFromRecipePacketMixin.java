package com.sbjeiindex.mixin.ae2;

import com.sbjeiindex.util.BackpackExtraction;
import com.sbjeiindex.util.BackpackHelper;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "appeng.core.network.serverbound.FillCraftingGridFromRecipePacket", remap = false)
public class FillCraftingGridFromRecipePacketMixin {
   @Inject(method = "takeIngredientFromPlayer", at = @At("RETURN"), cancellable = true, remap = false)
   private void sbjeiindex_takeIngredientFromPlayer(@Coerce Object cct, ServerPlayer player, Ingredient ingredient, CallbackInfoReturnable<ItemStack> cir) {
      ItemStack current = (ItemStack)cir.getReturnValue();
      if (current == null || current.isEmpty()) {
         List<InventoryHandler> handlers = BackpackHelper.getEquippedBackpackInventoryHandlersWithJEIIndexUpgrade(player);
         if (!handlers.isEmpty()) {
            ItemStack extracted = BackpackExtraction.extractIngredient(handlers, ingredient);
            if (!extracted.isEmpty()) {
               cir.setReturnValue(extracted);
            }
         }
      }
   }
}
