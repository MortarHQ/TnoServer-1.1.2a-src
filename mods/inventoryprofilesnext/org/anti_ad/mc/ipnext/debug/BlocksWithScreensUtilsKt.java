package org.anti_ad.mc.ipnext.debug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.alias.registry.RegistryExKt;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nBlocksWithScreensUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlocksWithScreensUtils.kt\norg/anti_ad/mc/ipnext/debug/BlocksWithScreensUtilsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,209:1\n1068#2:210\n1068#2:211\n1068#2:212\n1869#2:213\n1869#2,2:214\n1870#2:216\n1869#2:217\n1869#2,2:218\n1870#2:220\n1869#2:221\n1870#2:250\n1869#2:251\n1870#2:259\n384#3,7:222\n384#3,7:229\n384#3,7:236\n384#3,7:243\n384#3,7:252\n216#4,2:260\n*S KotlinDebug\n*F\n+ 1 BlocksWithScreensUtils.kt\norg/anti_ad/mc/ipnext/debug/BlocksWithScreensUtilsKt\n*L\n44#1:210\n45#1:211\n46#1:212\n75#1:213\n82#1:214,2\n75#1:216\n97#1:217\n98#1:218,2\n97#1:220\n126#1:221\n126#1:250\n164#1:251\n164#1:259\n133#1:222,7\n138#1:229,7\n144#1:236,7\n150#1:243,7\n165#1:252,7\n169#1:260,2\n*E\n"
)
public final class BlocksWithScreensUtilsKt {
   @NotNull
   public static final List generateCommands(@NotNull Set var0, @NotNull Map var1, @NotNull Map var2, @NotNull Map var3, boolean var4) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      List var18 = new ArrayList();
      int var5 = var1.isEmpty()
         ? 0
         : ((List)CollectionsKt.sortedWith(var1.values(), new BlocksWithScreensUtilsKt$generateCommands$$inlined$sortedByDescending$1()).get(0)).size();
      int var6 = var3.isEmpty()
         ? 0
         : ((List)CollectionsKt.sortedWith(var3.values(), new BlocksWithScreensUtilsKt$generateCommands$$inlined$sortedByDescending$2()).get(0)).size();
      int var7 = var2.isEmpty()
         ? 0
         : ((List)CollectionsKt.sortedWith(var2.values(), new BlocksWithScreensUtilsKt$generateCommands$$inlined$sortedByDescending$3()).get(0)).size();
      int var19 = Math.max(var5, Math.max(var6, var7)) << 1;
      long var10 = (long)Vanilla.INSTANCE.getPx();
      long var12 = (long)Vanilla.INSTANCE.getPy();
      long var14 = (long)Vanilla.INSTANCE.getPz();
      byte var20 = 1;
      String var21 = Vanilla.INSTANCE.player().getGameProfile().getName();
      var18.add("/tp " + var21 + " ~ ~ ~ 180 0");
      var18.add(
         "/fill " + (var10 - 3L) + " " + (var12 - 1L) + " " + (var14 + 4L) + " " + (var10 + var19 + 2L) + " " + (var12 - 1L) + " " + var14 + " minecraft:glass"
      );
      var18.add(
         "/fill "
            + (var10 - 4L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 + 4L)
            + " "
            + (var10 + var19 + 2L)
            + " "
            + (var12 + 1L)
            + " "
            + (var14 + 4L)
            + " minecraft:black_stained_glass"
      );
      var18.add(
         "/fill "
            + (var10 - 4L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 + 4L)
            + " "
            + (var10 - 4L)
            + " "
            + (var12 + 1L)
            + " "
            + (var14 - 1L)
            + " minecraft:black_stained_glass"
      );
      var18.add(
         "/fill "
            + (var10 + var19 + 3L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 + 4L)
            + " "
            + (var10 + var19 + 3L)
            + " "
            + (var12 + 1L)
            + " "
            + (var14 - 1L)
            + " minecraft:black_stained_glass"
      );
      Intrinsics.checkNotNull(var21);
      genRowScript(var10, var12, var14, var18, var21, var19);

      for (String var8 : var1.keySet()) {
         List var9;
         if ((var9 = (List)var1.get(var8)) != null) {
            var18.add(
               "/setblock "
                  + (var10 - 2L)
                  + " "
                  + var12
                  + " "
                  + (var14 - var20)
                  + " minecraft:oak_sign[rotation=12,waterlogged=false]{Text1:\"{\\\"text\\\":\\\""
                  + var8
                  + "\\\"}\"} destroy"
            );
            genRowScript(var10, var12, var14 - var20, var18, var21, var19);
            byte var16 = 0;

            for (String var25 : var9) {
               var18.add("/setblock " + (var10 + var16) + " " + var12 + " " + (var14 - var20) + " " + var8 + ":" + var25 + " destroy");
               var16 += 2;
               Thread.sleep(20L);
            }

            var20 += 4;
         }
      }

      var18.add(
         "/fill "
            + (var10 - 3L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 - var20 - 3L)
            + " "
            + (var10 + var19 + 2L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 - var20)
            + " minecraft:glass"
      );
      var18.add(
         "/fill "
            + (var10 - 4L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 - var20 - 3L)
            + " "
            + (var10 + var19 + 2L)
            + " "
            + (var12 + 1L)
            + " "
            + (var14 - var20 - 3L)
            + " minecraft:black_stained_glass"
      );
      var18.add(
         "/fill "
            + (var10 - 4L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 - var20 - 3L)
            + " "
            + (var10 - 4L)
            + " "
            + (var12 + 1L)
            + " "
            + (var14 - var20)
            + " minecraft:black_stained_glass"
      );
      var18.add(
         "/fill "
            + (var10 + var19 + 3L)
            + " "
            + (var12 - 1L)
            + " "
            + (var14 - var20 - 3L)
            + " "
            + (var10 + var19 + 3L)
            + " "
            + (var12 + 1L)
            + " "
            + (var14 - var20)
            + " minecraft:black_stained_glass"
      );
      if (var4) {
         for (String var24 : var3.keySet()) {
            List var10000 = (List)var3.get(var24);
            if (var10000 != null) {
               for (String var27 : var10000) {
                  var18.add("/give @a " + var24 + ":" + var27);
               }
            }
         }
      }

      return var18;
   }

   private static final void genRowScript(long var0, long var2, long var4, List var6, String var7, int var8) {
      var6.add(
         "/fill " + (var0 - 4L) + " " + (var2 - 1L) + " " + var4 + " " + (var0 - 4L) + " " + (var2 + 1L) + " " + (var4 - 3L) + " minecraft:black_stained_glass"
      );
      var6.add("/fill " + (var0 - 3L) + " " + (var2 - 1L) + " " + var4 + " " + (var0 - 1L) + " " + (var2 - 1L) + " " + (var4 - 3L) + " minecraft:glass");
      var6.add("/fill " + var0 + " " + (var2 - 1L) + " " + var4 + " " + (var0 + var8) + " " + (var2 - 1L) + " " + (var4 - 3L) + " minecraft:smooth_stone");
      var6.add(
         "/fill " + (var0 + var8) + " " + (var2 - 1L) + " " + var4 + " " + (var0 + var8 + 2L) + " " + (var2 - 1L) + " " + (var4 - 3L) + " minecraft:glass"
      );
      var6.add(
         "/fill "
            + (var0 + var8 + 3L)
            + " "
            + (var2 - 1L)
            + " "
            + var4
            + " "
            + (var0 + var8 + 3L)
            + " "
            + (var2 + 1L)
            + " "
            + (var4 - 3L)
            + " minecraft:black_stained_glass"
      );
   }

   public static final void extractBlockInfo(@NotNull Set var0, @NotNull Map var1, @NotNull Map var2, @NotNull Map var3, @NotNull Map var4, @NotNull List var5) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      List var10000 = RegistryExKt.get_REGISTRIES_CONTAINER_IDS_/* $VF was: get(REGISTRIES-CONTAINER-IDS) */();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterator var6 = var10000.iterator();

      while (var6.hasNext()) {
         ResourceLocation var7;
         if (!Intrinsics.areEqual((var7 = (ResourceLocation)var6.next()).getNamespace(), "minecraft")) {
            String var10001 = var7.getNamespace();
            Intrinsics.checkNotNullExpressionValue(var10001, "");
            var0.add(var10001);
            boolean var8 = RegistryExKt.get_REGISTRIES_BLOCK_IDS_/* $VF was: get(REGISTRIES-BLOCK-IDS) */().contains(var7);
            boolean var9 = RegistryExKt.get_REGISTRIES_ITEM_IDS_/* $VF was: get(REGISTRIES-ITEM-IDS) */().contains(var7);
            boolean var10 = RegistryExKt.get_REGISTRIES_BLOCK_ENTITY_TYPES_IDS_/* $VF was: get(REGISTRIES-BLOCK_ENTITY_TYPES-IDS) */().contains(var7);
            if (var8) {
               Map var11 = var1;
               String var12 = var7.getNamespace();
               Object var13;
               if ((var13 = var11.get(var12)) == null) {
                  List var14 = new ArrayList();
                  var11.put(var12, var14);
                  var10000 = var14;
               } else {
                  var10000 = (List)var13;
               }

               var10000 = var10000;
               var10001 = var7.getPath();
               Intrinsics.checkNotNullExpressionValue(var10001, "");
               var10000.add(var10001);
            }

            if (var9 && !var8 && !var10) {
               Map var15 = var2;
               String var18 = var7.getNamespace();
               Object var22;
               if ((var22 = var15.get(var18)) == null) {
                  List var25 = new ArrayList();
                  var15.put(var18, var25);
                  var10000 = var25;
               } else {
                  var10000 = (List)var22;
               }

               var10000 = var10000;
               var10001 = var7.getPath();
               Intrinsics.checkNotNullExpressionValue(var10001, "");
               var10000.add(var10001);
            }

            if (var10 && !var9 && !var8) {
               Map var16 = var3;
               String var19 = var7.getNamespace();
               Object var23;
               if ((var23 = var16.get(var19)) == null) {
                  List var26 = new ArrayList();
                  var16.put(var19, var26);
                  var10000 = var26;
               } else {
                  var10000 = (List)var23;
               }

               var10000 = var10000;
               var10001 = var7.getPath();
               Intrinsics.checkNotNullExpressionValue(var10001, "");
               var10000.add(var10001);
            }

            int var17;
            if ((var17 = Kt_commonKt.plus(Kt_commonKt.plus(var10, var8), var9)) == 0) {
               Map var20 = var4;
               String var24 = var7.getNamespace();
               Object var27;
               if ((var27 = var20.get(var24)) == null) {
                  var27 = new ArrayList();
                  var20.put(var24, var27);
               }

               var10000 = (List)var27;
               var10001 = var7.getPath();
               Intrinsics.checkNotNullExpressionValue(var10001, "");
               var10000.add(var10001);
            }

            if (var17 > 1) {
               String var21 = var7 + "  ->  " + (var8 ? "block, " : "") + (var9 ? "items, " : "") + (var10 ? "blockEntities" : "");
               var5.add(var21);
            }
         }
      }
   }

   public static final void extractAllItems(@NotNull Map var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Map var1 = new LinkedHashMap();
      Set var10000 = RegistryExKt.get_REGISTRIES_ITEM_IDS_/* $VF was: get(REGISTRIES-ITEM-IDS) */();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (ResourceLocation var3 : var10000) {
         Map var4 = var1;
         String var5 = var3.getNamespace();
         Object var6;
         if ((var6 = var4.get(var5)) == null) {
            var6 = new ArrayList();
            var4.put(var5, var6);
         }

         List var11 = (List)var6;
         String var10001 = var3.getPath();
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         var11.add(var10001);
      }

      Iterator var7 = var1.entrySet().iterator();

      while (var7.hasNext()) {
         Entry var8;
         String var9 = (String)(var8 = (Entry)var7.next()).getKey();
         List var10;
         CollectionsKt.sort(var10 = (List)var8.getValue());
         var0.put(var9, var10);
      }
   }
}
