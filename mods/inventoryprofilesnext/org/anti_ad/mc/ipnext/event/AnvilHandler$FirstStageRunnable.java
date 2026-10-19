package org.anti_ad.mc.ipnext.event;

import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.AreaType;
import org.anti_ad.mc.ipnext.inventory.AreaTypes;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAnvilHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnvilHandler.kt\norg/anti_ad/mc/ipnext/event/AnvilHandler$FirstStageRunnable\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,232:1\n78#2:233\n78#2:234\n88#2:236\n71#2:237\n66#2,7:238\n84#2:245\n84#2:246\n84#2:247\n1869#3:235\n1870#3:248\n*S KotlinDebug\n*F\n+ 1 AnvilHandler.kt\norg/anti_ad/mc/ipnext/event/AnvilHandler$FirstStageRunnable\n*L\n143#1:233\n146#1:234\n150#1:236\n150#1:237\n150#1:238,7\n152#1:245\n153#1:246\n154#1:247\n148#1:235\n148#1:248\n*E\n"
)
final class AnvilHandler$FirstStageRunnable implements Runnable {
   @NotNull
   private final AnvilScreen screen;
   @NotNull
   private final AnvilMenu container;
   @NotNull
   private final String lastText;
   @NotNull
   private final List idToType;
   private boolean doRenameClick;

   public AnvilHandler$FirstStageRunnable(@NotNull AnvilScreen var1, @NotNull AnvilMenu var2, @NotNull String var3, @NotNull List var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      super();
      this.screen = var1;
      this.container = var2;
      this.lastText = var3;
      this.idToType = var4;
   }

   @Override
   public final void run() {
      synchronized (AnvilHandler.INSTANCE.getSync()) {
         if (AnvilHandler.INSTANCE.getTicksAfterLastPacket() >= 2) {
            AreaTypes var3 = AreaTypes.INSTANCE;
            AreaType var10000 = AreaTypes.INSTANCE.getPlayerStorage().plus(var3.getPlayerHotbar()).plus(var3.getPlayerOffhand()).minus(var3.getLockedSlots());
            AbstractContainerMenu var10001 = (AbstractContainerMenu)this.container;
            NonNullList var10002 = ((AbstractContainerMenu)this.container).slots;
            Intrinsics.checkNotNullExpressionValue(((AbstractContainerMenu)this.container).slots, "");
            List var13 = var10000.getItemArea(var10001, (List)var10002).getSlotIndices();
            if (Vanilla.INSTANCE.screen() == this.screen && Vanilla.INSTANCE.container() == this.container) {
               NonNullList var20 = ((AbstractContainerMenu)this.container).slots;
               Intrinsics.checkNotNullExpressionValue(((AbstractContainerMenu)this.container).slots, "");
               List var4;
               if ((var4 = (List)var20).isEmpty()) {
                  AnvilHandler.access$setStillProcessingLast$p(false);
                  return;
               }

               Iterator var14 = this.idToType.iterator();

               while (var14.hasNext()) {
                  Pair var5;
                  int var6 = ((Number)(var5 = (Pair)var14.next()).component1()).intValue();
                  ItemType var16 = (ItemType)var5.component2();
                  Iterator var7 = var13.iterator();

                  label94: {
                     while (var7.hasNext()) {
                        Object var8;
                        int var9 = ((Number)(var8 = var7.next())).intValue();
                        ItemStack var21 = ((Slot)var4.get(var9)).getItem();
                        Intrinsics.checkNotNullExpressionValue(var21, "");
                        ItemStack var19 = var21;
                        org.anti_ad.mc.ipnext.item.ItemStack var22;
                        if (var21.isEmpty()) {
                           var22 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                        } else {
                           ItemStack$Companion var23 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                           ItemStack var10 = var19;
                           Item var10003 = var10.getItem();
                           Intrinsics.checkNotNullExpressionValue(var10003, "");
                           DataComponentMap var10004 = var10.getComponents();
                           Intrinsics.checkNotNull(var10004);
                           var22 = var23.invoke(
                              new ItemType(
                                 var10003,
                                 (PatchedDataComponentMap)var10004,
                                 var10.getComponentsPatch(),
                                 new VanillaAccessorsKt$(itemType)$1(var10),
                                 false,
                                 false,
                                 null,
                                 112,
                                 null
                              ),
                              var19.getCount()
                           );
                        }

                        if (Intrinsics.areEqual(var22.getItemType(), var16)) {
                           var24 = var8;
                           break label94;
                        }
                     }

                     var24 = null;
                  }

                  Integer var17;
                  if ((var17 = (Integer)var24) != null) {
                     int var18 = var17.intValue();
                     Log.INSTANCE.trace("Restoring from " + ((Slot)var4.get(var18)).index + " to input slot " + ((Slot)var4.get(var6)).index);
                     ContainerClicker.INSTANCE.leftClick(((Slot)var4.get(var18)).index);
                     ContainerClicker.INSTANCE.leftClick(((Slot)var4.get(var6)).index);
                     GeneralInventoryActions.INSTANCE.cleanCursor();
                  }
               }

               Runnable var15 = new AnvilHandler$FirstStageRunnable$run$1$secondStageRunnable$1(this);
               VanillaAccessorsKt._send_/* $VF was: (send) */(Vanilla.INSTANCE.mc(), var15);
               return;
            }

            AnvilHandler.access$setStillProcessingLast$p(false);
            return;
         }

         int var2 = AnvilHandler.INSTANCE.getTicksAfterLastPacket();
         AnvilHandler.INSTANCE.setTicksAfterLastPacket(var2 + 1);
         Log.INSTANCE.trace("Waiting packet processing " + AnvilHandler.INSTANCE.getTicksAfterLastPacket());
         GeneralInventoryActions.INSTANCE.cleanCursor();
         this.doRenameClick = true;
         AnvilHandler.INSTANCE.addOnTickRunnable(this);
      }
   }
}
