package org.anti_ad.mc.ipnext.event;

import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StonecutterMenu;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.container.ExtensionsKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nStoneCutterCraftingHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StoneCutterCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/StoneCutterCraftingHandler\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,293:1\n124#2:294\n124#2:295\n*S KotlinDebug\n*F\n+ 1 StoneCutterCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/StoneCutterCraftingHandler\n*L\n258#1:294\n271#1:295\n*E\n"
)
public final class StoneCutterCraftingHandler extends CutterCraftingHandlerBase {
   @NotNull
   public static final StoneCutterCraftingHandler INSTANCE = new StoneCutterCraftingHandler();

   private StoneCutterCraftingHandler() {
   }

   @Override
   public final void typeSpecificOnTickInGame(@NotNull AbstractContainerScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      AbstractContainerMenu var10000 = var1.getMenu();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      AbstractContainerMenu var2 = var10000;
      if (var10000 instanceof StonecutterMenu) {
         this.setNewScreen(false);
         this.init(var1, var2);
         Log.INSTANCE.traceIf(StoneCutterCraftingHandler::typeSpecificOnTickInGame$lambda$0);
         this.setCraftClick(
            ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing() && this.getSHIFT().isPressing() && !ItemStackExtensionsKt.isEmpty(this.getInput())
         );
      }
   }

   @Override
   public final void typeSpecificNewContainer(@NotNull AbstractContainerScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      AbstractContainerMenu var10000 = var1.getMenu();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      AbstractContainerMenu var2 = var10000;
      if (var10000 instanceof StonecutterMenu) {
         this.setNewScreen(false);
         this.init(var1, var2);
      }
   }

   public final void selectRecipe(@NotNull StonecutterMenu var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Log.INSTANCE.trace("selectRecipe: " + var2);
      ExtensionsKt.selectRecipe(var1, var2);
   }

   public final int selectedRecipe(@NotNull StonecutterMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return VanillaAccessorsKt.get_selectedRecipe_/* $VF was: get(selectedRecipe) */(var1);
   }

   @Nullable
   public final Integer selectedRecipeOrNull(@Nullable StonecutterMenu var1) {
      return var1 != null ? VanillaAccessorsKt.get_selectedRecipe_/* $VF was: get(selectedRecipe) */(var1) : null;
   }

   private static final Unit typeSpecificOnTickInGame$lambda$0() {
      Log.INSTANCE.trace("INCLUDE_HOTBAR_MODIFIER: " + ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing());
      Log.INSTANCE.trace("SHIFT: " + INSTANCE.getSHIFT().isPressing());
      return Unit.INSTANCE;
   }
}
