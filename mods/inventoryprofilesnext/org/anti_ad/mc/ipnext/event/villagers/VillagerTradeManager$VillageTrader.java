package org.anti_ad.mc.ipnext.event.villagers;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.alias.client.gui.screen.ingame.InGameExKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nVillagerTradeManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VillagerTradeManager.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerTradeManager$VillageTrader\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,647:1\n78#2:648\n88#2:649\n71#2:650\n66#2,7:651\n88#2:658\n71#2:659\n66#2,7:660\n*S KotlinDebug\n*F\n+ 1 VillagerTradeManager.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerTradeManager$VillageTrader\n*L\n627#1:648\n630#1:649\n630#1:650\n630#1:651,7\n637#1:658\n637#1:659\n637#1:660,7\n*E\n"
)
public final class VillagerTradeManager$VillageTrader implements Runnable {
   @NotNull
   private final List indexes;
   @NotNull
   private final MerchantScreen screen;
   @NotNull
   private final MerchantMenu container;
   private int atIndex;
   private boolean changeIndex;
   private boolean doTrade;
   private int iterations;

   public VillagerTradeManager$VillageTrader(@NotNull List var1, @NotNull MerchantScreen var2, @NotNull MerchantMenu var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.indexes = var1;
      this.screen = var2;
      this.container = var3;
   }

   @Override
   public final void run() {
      int var1 = this.iterations++;
      if (this.iterations <= 500) {
         if (Vanilla.INSTANCE.screen() == this.screen && this.container == Vanilla.INSTANCE.container()) {
            if (this.changeIndex) {
               var1 = this.atIndex++;
               if (this.atIndex >= this.indexes.size()) {
                  return;
               }

               this.changeIndex = false;
            } else {
               NonNullList var10000 = ((AbstractContainerMenu)this.container).slots;
               Intrinsics.checkNotNullExpressionValue(((AbstractContainerMenu)this.container).slots, "");
               Slot var4 = (Slot)((List)var10000).get(2);
               if (this.doTrade) {
                  ContainerClicker.INSTANCE.shiftClick(2);
                  ItemStack var8 = var4.getItem();
                  Intrinsics.checkNotNullExpressionValue(var8, "");
                  ItemStack var5 = var8;
                  org.anti_ad.mc.ipnext.item.ItemStack var9;
                  if (var8.isEmpty()) {
                     var9 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                  } else {
                     ItemStack$Companion var10 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                     ItemStack var2 = var5;
                     Item var10003 = var2.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10003, "");
                     DataComponentMap var10004 = var2.getComponents();
                     Intrinsics.checkNotNull(var10004);
                     var9 = var10.invoke(
                        new ItemType(
                           var10003,
                           (PatchedDataComponentMap)var10004,
                           var2.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var2),
                           false,
                           false,
                           null,
                           112,
                           null
                        ),
                        var5.getCount()
                     );
                  }

                  if (ItemStackExtensionsKt.isEmpty(var9)) {
                     this.doTrade = false;
                  }
               } else {
                  this.doTrade = true;
                  InGameExKt.set_selectedIndex_/* $VF was: set(selectedIndex) */(this.screen, ((Number)this.indexes.get(this.atIndex)).intValue());
                  InGameExKt._syncRecipeIndex_/* $VF was: (syncRecipeIndex) */(this.screen);
                  ItemStack var11 = var4.getItem();
                  Intrinsics.checkNotNullExpressionValue(var11, "");
                  ItemStack var6 = var11;
                  org.anti_ad.mc.ipnext.item.ItemStack var12;
                  if (var11.isEmpty()) {
                     var12 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                  } else {
                     ItemStack$Companion var13 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                     ItemStack var7 = var6;
                     Item var14 = var7.getItem();
                     Intrinsics.checkNotNullExpressionValue(var14, "");
                     DataComponentMap var15 = var7.getComponents();
                     Intrinsics.checkNotNull(var15);
                     var12 = var13.invoke(
                        new ItemType(
                           var14,
                           (PatchedDataComponentMap)var15,
                           var7.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var7),
                           false,
                           false,
                           null,
                           112,
                           null
                        ),
                        var6.getCount()
                     );
                  }

                  if (ItemStackExtensionsKt.isEmpty(var12)) {
                     this.changeIndex = true;
                  }
               }
            }

            Vanilla.INSTANCE.queueForMainThread(this);
         }
      }
   }
}
