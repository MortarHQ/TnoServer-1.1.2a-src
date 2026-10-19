package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.List;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.alias.client.gui.screen.ingame.InGameExKt;
import org.anti_ad.mc.alias.screen.ScreenExKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nAnvilHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnvilHandler.kt\norg/anti_ad/mc/ipnext/event/AnvilHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,232:1\n1869#2:233\n1870#2:253\n1869#2,2:254\n78#3,11:234\n71#3:245\n66#3,7:246\n*S KotlinDebug\n*F\n+ 1 AnvilHandler.kt\norg/anti_ad/mc/ipnext/event/AnvilHandler\n*L\n70#1:233\n70#1:253\n223#1:254,2\n71#1:234,11\n71#1:245\n71#1:246,7\n*E\n"
)
public final class AnvilHandler {
   @NotNull
   public static final AnvilHandler INSTANCE = new AnvilHandler();
   @NotNull
   private static final Object sync = new Object();
   @NotNull
   private static final Object mainSync = new Object();
   private static int ticksAfterLastPacket = 2;
   @NotNull
   private static String lastText = "";
   @NotNull
   private static final List slots = new ArrayList();
   private static boolean afterPre;
   @Nullable
   private static AnvilMenu container;
   private static boolean stillProcessingLast;
   private static boolean skipNext;
   @NotNull
   private static final List onTickRunnableList = new ArrayList();

   private AnvilHandler() {
   }

   @NotNull
   public final Object getSync() {
      return sync;
   }

   @NotNull
   public final Object getMainSync() {
      return mainSync;
   }

   public final int getTicksAfterLastPacket() {
      return ticksAfterLastPacket;
   }

   public final void setTicksAfterLastPacket(int var1) {
      ticksAfterLastPacket = var1;
   }

   private final boolean getEnabled() {
      return GuiSettings.INSTANCE.getFAST_RENAME_SAVED_VALUE().getBooleanValue();
   }

   public final boolean onTakeOutPre(@NotNull AnvilMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      synchronized (mainSync) {
         if (!INSTANCE.getEnabled()) {
            return false;
         }

         if (stillProcessingLast) {
            Log.INSTANCE.trace("still processing last");
            return true;
         }

         skipNext = true;
         container = var1;
         Screen var10000 = Vanilla.INSTANCE.screen();
         if (var10000 != null) {
            Screen var3 = var10000;
            if (var10000 instanceof AnvilScreen) {
               String var11 = InGameExKt.get_nameFieldText_/* $VF was: get(nameFieldText) */((AnvilScreen)var3);
               if (var11 == null) {
                  var11 = "";
               }

               lastText = var11;
               slots.clear();
               List var12 = ScreenExKt.get_inputSlotIndices_/* $VF was: get(inputSlotIndices) */((ItemCombinerMenu)var1);
               Intrinsics.checkNotNullExpressionValue(var12, "");

               for (Integer var4 : var12) {
                  NonNullList var13 = ((AbstractContainerMenu)var1).slots;
                  Intrinsics.checkNotNullExpressionValue(((AbstractContainerMenu)var1).slots, "");
                  List var14 = (List)var13;
                  Intrinsics.checkNotNull(var4);
                  ItemStack var15 = ((Slot)var14.get(var4)).getItem();
                  Intrinsics.checkNotNullExpressionValue(var15, "");
                  ItemStack var5 = var15;
                  org.anti_ad.mc.ipnext.item.ItemStack var16;
                  if (var15.isEmpty()) {
                     var16 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                  } else {
                     ItemStack$Companion var17 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                     ItemStack var6 = var5;
                     Item var10003 = var6.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10003, "");
                     DataComponentMap var10004 = var6.getComponents();
                     Intrinsics.checkNotNull(var10004);
                     var16 = var17.invoke(
                        new ItemType(
                           var10003,
                           (PatchedDataComponentMap)var10004,
                           var6.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var6),
                           false,
                           false,
                           null,
                           112,
                           null
                        ),
                        var5.getCount()
                     );
                  }

                  org.anti_ad.mc.ipnext.item.ItemStack var10 = var16;
                  if (!ItemStackExtensionsKt.isEmpty(var16)) {
                     slots.add(TuplesKt.to(var4, var10.getItemType()));
                  }
               }

               afterPre = true;
            }
         }
      }

      return false;
   }

   public final void onTakeOutPost(@NotNull AnvilMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      synchronized (mainSync) {
         if (!INSTANCE.getEnabled()) {
            return;
         }

         if (!stillProcessingLast) {
            if (afterPre && container == var1) {
               Screen var10000 = Vanilla.INSTANCE.screen();
               if (var10000 != null) {
                  Screen var3 = var10000;
                  if (var10000 instanceof AnvilScreen) {
                     INSTANCE.restoreState((AnvilScreen)var3, var1, lastText, CollectionsKt.toList(slots));
                  }
               }

               afterPre = false;
            }

            afterPre = false;
            container = null;
            lastText = "";
            slots.clear();
            return;
         }

         Log.INSTANCE.trace("still processing last in post", new Exception());
      }
   }

   private final void restoreState(AnvilScreen var1, AnvilMenu var2, String var3, List var4) {
      if (this.getEnabled()) {
         stillProcessingLast = true;
         synchronized (sync) {
            if (ticksAfterLastPacket < 2) {
               INSTANCE.addOnTickRunnable(new AnvilHandler$FirstStageRunnable(var1, var2, var3, var4));
            } else {
               VanillaAccessorsKt._send_/* $VF was: (send) */(Vanilla.INSTANCE.mc(), new AnvilHandler$FirstStageRunnable(var1, var2, var3, var4));
            }
         }
      }
   }

   public final void addOnTickRunnable(@NotNull Runnable var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      synchronized (onTickRunnableList) {
         onTickRunnableList.add(var1);
      }
   }

   public final void onTickInGame() {
      List var1 = new ArrayList();
      synchronized (onTickRunnableList) {
         if (onTickRunnableList.isEmpty()) {
            return;
         }

         var1.addAll(onTickRunnableList);
         onTickRunnableList.clear();
      }

      for (Runnable var8 : var1) {
         try {
            var8.run();
         } catch (Throwable var5) {
            Log.INSTANCE.error("Error while AnvilHandler#onTickInGame");
         }
      }
   }
}
