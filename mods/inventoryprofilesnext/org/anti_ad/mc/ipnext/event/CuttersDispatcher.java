package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nStoneCutterCraftingHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StoneCutterCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/CuttersDispatcher\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,293:1\n295#2,2:294\n295#2,2:296\n295#2,2:298\n295#2,2:300\n1869#2,2:302\n1869#2,2:304\n*S KotlinDebug\n*F\n+ 1 StoneCutterCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/CuttersDispatcher\n*L\n213#1:294,2\n218#1:296,2\n223#1:298,2\n228#1:300,2\n239#1:302,2\n244#1:304,2\n*E\n"
)
public final class CuttersDispatcher {
   @NotNull
   public static final CuttersDispatcher INSTANCE = new CuttersDispatcher();
   @NotNull
   private static final List handlers;

   private CuttersDispatcher() {
   }

   public final boolean isAnyRefillTick() {
      Iterator var1 = handlers.iterator();

      Object var10000;
      while (true) {
         if (var1.hasNext()) {
            Object var2;
            if (!((CutterCraftingHandlerBase)(var2 = var1.next())).isRefillTick()) {
               continue;
            }

            var10000 = var2;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 != null;
   }

   public final boolean isAnySkipTick() {
      Iterator var1 = handlers.iterator();

      Object var10000;
      while (true) {
         if (var1.hasNext()) {
            Object var2;
            if (!((CutterCraftingHandlerBase)(var2 = var1.next())).getSkipTick()) {
               continue;
            }

            var10000 = var2;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 != null;
   }

   public final boolean isAnyStillCrafting() {
      Iterator var1 = handlers.iterator();

      Object var10000;
      while (true) {
         if (var1.hasNext()) {
            Object var2;
            if (!((CutterCraftingHandlerBase)(var2 = var1.next())).getStillCrafting()) {
               continue;
            }

            var10000 = var2;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 != null;
   }

   public final boolean isAnyOldScreen() {
      Iterator var1 = handlers.iterator();

      Object var10000;
      while (true) {
         if (var1.hasNext()) {
            Object var2;
            if (((CutterCraftingHandlerBase)(var2 = var1.next())).isNewScreen()) {
               continue;
            }

            var10000 = var2;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 != null;
   }

   public final void addHandler(@NotNull CutterCraftingHandlerBase var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (!handlers.contains(StoneCutterCraftingHandler.INSTANCE)) {
         handlers.add(StoneCutterCraftingHandler.INSTANCE);
      }

      handlers.add(var1);
   }

   public final void onTickInGame() {
      Iterator var1 = handlers.iterator();

      while (var1.hasNext()) {
         ((CutterCraftingHandlerBase)var1.next()).onTickBase();
      }
   }

   public final void onCrafted() {
      Iterator var1 = handlers.iterator();

      while (var1.hasNext()) {
         ((CutterCraftingHandlerBase)var1.next()).onCraftedSink();
      }
   }

   static {
      (handlers = new ArrayList()).add(StoneCutterCraftingHandler.INSTANCE);
   }
}
