package org.anti_ad.mc.ipnext.event.villagers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen.TradeOfferButton;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.alias.client.gui.screen.ingame.InGameExKt;
import org.anti_ad.mc.alias.client.gui.widget.WidgetExKt;
import org.anti_ad.mc.alias.entity.EntityExKt;
import org.anti_ad.mc.alias.village.VillageExKt;
import org.anti_ad.mc.common.IInputHandler;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.Hotkeys;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nVillagerTradeManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VillagerTradeManager.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerTradeManager\n+ 2 PassiveEx.kt\norg/anti_ad/mc/alias/entity/passive/PassiveExKt\n+ 3 VillageEx.kt\norg/anti_ad/mc/alias/village/VillageExKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 6 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 7 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 8 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 9 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,647:1\n23#2:648\n25#3:649\n1869#4,2:650\n1869#4:664\n1870#4:688\n1583#4,11:692\n1878#4,2:703\n1880#4:706\n1594#4:707\n1869#4:708\n1870#4:783\n1583#4,11:785\n1878#4,2:796\n1880#4:799\n1594#4:800\n295#4,2:835\n66#5,4:652\n66#5,4:656\n66#5,4:660\n124#5:691\n78#5:709\n88#5:710\n71#5:711\n66#5,7:712\n88#5:719\n71#5:720\n66#5,7:721\n88#5:728\n71#5:729\n66#5,7:730\n88#5:739\n71#5:740\n66#5,7:741\n88#5:748\n71#5:749\n66#5,7:750\n88#5:761\n71#5:762\n66#5,7:763\n88#5:770\n71#5:771\n66#5,7:772\n124#5:784\n66#5,4:803\n66#5,4:814\n66#5,4:825\n168#5:837\n168#5:838\n168#5:839\n168#5:840\n168#5:841\n168#5:842\n168#5:843\n168#5:844\n168#5:845\n168#5:846\n168#5:847\n168#5:848\n168#5:849\n168#5:850\n168#5:851\n168#5:852\n168#5:853\n168#5:854\n131#6:665\n112#6:666\n331#6,2:667\n106#6:669\n334#6,2:670\n131#6:672\n112#6:673\n331#6,2:674\n106#6:676\n334#6,2:677\n131#6:679\n112#6:680\n131#6:681\n112#6:682\n331#6,2:683\n106#6:685\n334#6,2:686\n131#6:737\n112#6:738\n131#6:757\n112#6:758\n131#6:759\n112#6:760\n131#6:779\n112#6:780\n131#6:781\n112#6:782\n131#6:807\n112#6:808\n331#6,2:809\n106#6:811\n334#6,2:812\n131#6:818\n112#6:819\n331#6,2:820\n106#6:822\n334#6,2:823\n112#6:829\n331#6,2:830\n106#6:832\n334#6,2:833\n130#7:689\n1#8:690\n1#8:705\n1#8:798\n1310#9,2:801\n*S KotlinDebug\n*F\n+ 1 VillagerTradeManager.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerTradeManager\n*L\n109#1:648\n109#1:649\n161#1:650,2\n229#1:664\n229#1:688\n329#1:692,11\n329#1:703,2\n329#1:706\n329#1:707\n335#1:708\n335#1:783\n364#1:785,11\n364#1:796,2\n364#1:799\n364#1:800\n453#1:835,2\n224#1:652,4\n226#1:656,4\n227#1:660,4\n327#1:691\n338#1:709\n341#1:710\n341#1:711\n341#1:712,7\n348#1:719\n348#1:720\n348#1:721,7\n348#1:728\n348#1:729\n348#1:730,7\n349#1:739\n349#1:740\n349#1:741,7\n349#1:748\n349#1:749\n349#1:750,7\n352#1:761\n352#1:762\n352#1:763,7\n352#1:770\n352#1:771\n352#1:772,7\n362#1:784\n406#1:803,4\n410#1:814,4\n414#1:825,4\n479#1:837\n480#1:838\n481#1:839\n497#1:840\n498#1:841\n499#1:842\n527#1:843\n528#1:844\n529#1:845\n545#1:846\n546#1:847\n547#1:848\n574#1:849\n575#1:850\n576#1:851\n592#1:852\n593#1:853\n594#1:854\n230#1:665\n230#1:666\n231#1:667,2\n231#1:669\n231#1:670,2\n232#1:672\n232#1:673\n233#1:674,2\n233#1:676\n233#1:677,2\n235#1:679\n235#1:680\n236#1:681\n236#1:682\n236#1:683,2\n236#1:685\n236#1:686,2\n348#1:737\n348#1:738\n349#1:757\n349#1:758\n349#1:759\n349#1:760\n352#1:779\n352#1:780\n352#1:781\n352#1:782\n407#1:807\n407#1:808\n408#1:809,2\n408#1:811\n408#1:812,2\n411#1:818\n411#1:819\n412#1:820,2\n412#1:822\n412#1:823,2\n415#1:829\n416#1:830,2\n416#1:832\n416#1:833,2\n315#1:689\n315#1:690\n329#1:705\n364#1:798\n384#1:801,2\n*E\n"
)
public final class VillagerTradeManager implements IInputHandler {
   @NotNull
   public static final VillagerTradeManager INSTANCE = new VillagerTradeManager();
   @NotNull
   private static final List defaultEmpty;
   @Nullable
   private static AbstractVillager currentVillager;
   @NotNull
   private static List currentVillagerBookmarks = defaultEmpty = CollectionsKt.emptyList();
   @NotNull
   private static List currentVillagerBookmarks1 = defaultEmpty;
   @NotNull
   private static List currentVillagerBookmarks2 = defaultEmpty;
   @NotNull
   private static List currentGlobalBookmarks = defaultEmpty;
   @NotNull
   private static List currentGlobalBookmarks1 = defaultEmpty;
   @NotNull
   private static List currentGlobalBookmarks2 = defaultEmpty;
   @NotNull
   private static Function2 doTrades = new VillagerTradeManager$doTrades$1(INSTANCE);

   private VillagerTradeManager() {
   }

   @Nullable
   public final AbstractVillager getCurrentVillager() {
      return currentVillager;
   }

   public final void setCurrentVillager(@Nullable AbstractVillager var1) {
      if (ModSettings.INSTANCE.getVILLAGER_TRADING_ENABLE().getBooleanValue()) {
         currentVillager = var1;
         if (var1 != null) {
            currentVillagerBookmarks = VillagerDataManager.INSTANCE.getLocal(EntityExKt.get_uuidString_/* $VF was: get(uuidString) */((Entity)var1));
            currentGlobalBookmarks = VillagerDataManager.INSTANCE.getGlobal(this.getProfesion(var1));
            currentVillagerBookmarks1 = VillagerDataManager.INSTANCE.getLocal1(EntityExKt.get_uuidString_/* $VF was: get(uuidString) */((Entity)var1));
            currentGlobalBookmarks1 = VillagerDataManager.INSTANCE.getGlobal1(this.getProfesion(var1));
            currentVillagerBookmarks2 = VillagerDataManager.INSTANCE.getLocal2(EntityExKt.get_uuidString_/* $VF was: get(uuidString) */((Entity)var1));
            currentGlobalBookmarks2 = VillagerDataManager.INSTANCE.getGlobal2(this.getProfesion(var1));
         } else {
            VillagerDataManager.INSTANCE.saveIfDirty();
            currentGlobalBookmarks = defaultEmpty;
            currentVillagerBookmarks = defaultEmpty;
            currentGlobalBookmarks1 = defaultEmpty;
            currentVillagerBookmarks1 = defaultEmpty;
            currentGlobalBookmarks2 = defaultEmpty;
            currentVillagerBookmarks2 = defaultEmpty;
         }
      }
   }

   @NotNull
   public final List getCurrentVillagerBookmarks() {
      return currentVillagerBookmarks;
   }

   public final void setCurrentVillagerBookmarks(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentVillagerBookmarks = var1;
   }

   @NotNull
   public final List getCurrentVillagerBookmarks1() {
      return currentVillagerBookmarks1;
   }

   public final void setCurrentVillagerBookmarks1(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentVillagerBookmarks1 = var1;
   }

   @NotNull
   public final List getCurrentVillagerBookmarks2() {
      return currentVillagerBookmarks2;
   }

   public final void setCurrentVillagerBookmarks2(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentVillagerBookmarks2 = var1;
   }

   @NotNull
   public final List getCurrentGlobalBookmarks() {
      return currentGlobalBookmarks;
   }

   public final void setCurrentGlobalBookmarks(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentGlobalBookmarks = var1;
   }

   @NotNull
   public final List getCurrentGlobalBookmarks1() {
      return currentGlobalBookmarks1;
   }

   public final void setCurrentGlobalBookmarks1(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentGlobalBookmarks1 = var1;
   }

   @NotNull
   public final List getCurrentGlobalBookmarks2() {
      return currentGlobalBookmarks2;
   }

   public final void setCurrentGlobalBookmarks2(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentGlobalBookmarks2 = var1;
   }

   private final String getProfesion(AbstractVillager var1) {
      if (var1 instanceof Villager) {
         VillagerProfession var10000 = ((Villager)var1).getVillagerData().getProfession();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         String var2;
         Intrinsics.checkNotNull(var2 = var10000.name());
         return var2;
      } else {
         return "___+++___ wandering ___+++___";
      }
   }

   private final boolean isLocalAvailable(AbstractVillager var1) {
      return var1 instanceof Villager;
   }

   public final void addColors(@NotNull List var1, @Nullable Integer var2, @Nullable Integer var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var2 != null || var3 != null) {
         var1.add(new Pair(var2, var3));
      }
   }

   public final void drawingButton(
      @NotNull MerchantScreen var1,
      @NotNull NativeContext var2,
      int var3,
      int var4,
      @NotNull MerchantOffer var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var5, "");
      if (ModSettings.INSTANCE.getVILLAGER_TRADING_ENABLE().getBooleanValue()) {
         List var11 = new ArrayList();
         this.addColors(
            var11,
            this.has(currentGlobalBookmarks, var5) ? ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR().getValue() : null,
            this.has(currentVillagerBookmarks, var5) ? ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR().getValue() : null
         );
         if (ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_1().getValue()) {
            this.addColors(
               var11,
               this.has(currentGlobalBookmarks1, var5) ? ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR1().getValue() : null,
               this.has(currentVillagerBookmarks1, var5) ? ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR1().getValue() : null
            );
         }

         if (ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_2().getValue()) {
            this.addColors(
               var11,
               this.has(currentGlobalBookmarks2, var5) ? ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR2().getValue() : null,
               this.has(currentVillagerBookmarks2, var5) ? ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR2().getValue() : null
            );
         }

         if ((var3 = var11.size()) != 0) {
            byte var15;
            var3 = (var15 = (byte)(var3 == 1 ? 86 : 86 / var3)) * var3 < 86 ? 86 - var15 * var3 : 0;
            int var16 = 0;
            Iterator var12 = var11.iterator();

            while (var12.hasNext()) {
               Pair var17;
               Integer var20 = (Integer)(var17 = (Pair)var12.next()).component1();
               Integer var18 = (Integer)var17.component2();
               if (var20 != null && var18 != null) {
                  RectKt.rFillGradient(var2, new Rectangle(var9 - 4 + var16, var8 + 2, var15 + var3, 18), var20, var18);
                  var16 += var15 + var3;
                  var3 = 0;
               } else {
                  int var10000;
                  if (var20 != null) {
                     var10000 = var20;
                  } else {
                     Intrinsics.checkNotNull(var18);
                     var10000 = var18;
                  }

                  int var19 = var10000;
                  RectKt.rFillRect(var2, new Rectangle(var9 - 4 + var16, var8 + 2, var15 + var3, 18), var19);
                  var16 += var15 + var3;
                  var3 = 0;
               }
            }
         }
      }
   }

   private final boolean isNullOrAir(CharSequence var1) {
      return var1 == null || Intrinsics.areEqual(var1, "minecraft:air");
   }

   private final String nullIfAir(String var1) {
      return var1 != null && !Intrinsics.areEqual(var1, "minecraft:air") ? var1 : null;
   }

   public final boolean has(@NotNull List var1, @NotNull MerchantOffer var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      ItemStack var10000 = VillageExKt.get_originalFirstBuyItem_/* $VF was: get(originalFirstBuyItem) */(var2).itemStack();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var4 = var10000;
      Item var10002 = var4.getItem();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      DataComponentMap var10003 = var4.getComponents();
      Intrinsics.checkNotNull(var10003);
      ItemType var3 = new ItemType(
         var10002, (PatchedDataComponentMap)var10003, var4.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var4), false, false, null, 112, null
      );
      ItemCost var18 = VillageExKt.get_secondBuyItem_/* $VF was: get(secondBuyItem) */(var2);
      var10000 = var18 != null ? var18.itemStack() : null;
      ItemType var20;
      if (var10000 != null) {
         ItemStack var5 = var10000;
         var10002 = var5.getItem();
         Intrinsics.checkNotNullExpressionValue(var10002, "");
         var10003 = var5.getComponents();
         Intrinsics.checkNotNull(var10003);
         var20 = new ItemType(
            var10002, (PatchedDataComponentMap)var10003, var5.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var5), false, false, null, 112, null
         );
      } else {
         var20 = null;
      }

      ItemType var10 = var20;
      var10000 = VillageExKt.get_sellItem_/* $VF was: get(sellItem) */(var2);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var8 = var10000;
      var10002 = var8.getItem();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      var10003 = var8.getComponents();
      Intrinsics.checkNotNull(var10003);
      ItemType var11 = new ItemType(
         var10002, (PatchedDataComponentMap)var10003, var8.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var8), false, false, null, 112, null
      );
      Iterator var7 = var1.iterator();

      while (true) {
         if (!var7.hasNext()) {
            return false;
         }

         VillagerTradeData var9;
         String var22 = (var9 = (VillagerTradeData)var7.next()).getPriceItem1();
         ItemType var6 = var3;
         ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var6.getItem());
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         String var28 = var10001.toString();
         Intrinsics.checkNotNullExpressionValue(var28, "");
         if (Intrinsics.areEqual(var22, var28)) {
            Tag var23 = NbtUtils.INSTANCE.nullIfEmpty(var9.getPrice1Nbt());
            NbtUtils var29 = NbtUtils.INSTANCE;
            var6 = var3;
            Tag var41;
            if (!ItemTypeExtensionsKt.isEmpty(var3)) {
               Codec var40 = ItemTypeExtensionsKt.getCOMPONENTS_CHANGES_CODEC();
               DynamicOps var48 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
               ItemStack var10004 = new ItemStack;
               ItemLike var10006 = (ItemLike)var6.getItem();
               PatchedDataComponentMap var10008 = var6.getTag();
               if (var10008 == null) {
                  DataComponentMap var55 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10008 = (PatchedDataComponentMap)var55;
               }

               var10004./* $VF: Unable to resugar constructor */<init>(var10006, 1, var10008);
               var41 = (Tag)var40.encodeStart(var48, var10004).getOrThrow();
            } else {
               var41 = null;
            }

            if (Intrinsics.areEqual(var23, var29.nullIfEmpty(var41))) {
               String var24 = var9.getResultItem();
               var6 = var11;
               var10001 = BuiltInRegistries.ITEM.getKey(var6.getItem());
               Intrinsics.checkNotNullExpressionValue(var10001, "");
               String var31 = var10001.toString();
               Intrinsics.checkNotNullExpressionValue(var31, "");
               if (Intrinsics.areEqual(var24, var31)) {
                  Tag var25 = NbtUtils.INSTANCE.nullIfEmpty(var9.getResultNbt());
                  NbtUtils var32 = NbtUtils.INSTANCE;
                  var6 = var11;
                  Tag var43;
                  if (!ItemTypeExtensionsKt.isEmpty(var11)) {
                     Codec var42 = ItemTypeExtensionsKt.getCOMPONENTS_CHANGES_CODEC();
                     DynamicOps var49 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
                     ItemStack var51 = new ItemStack;
                     ItemLike var53 = (ItemLike)var6.getItem();
                     PatchedDataComponentMap var56 = var6.getTag();
                     if (var56 == null) {
                        DataComponentMap var57 = DataComponentMap.EMPTY;
                        Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                        var56 = (PatchedDataComponentMap)var57;
                     }

                     var51./* $VF: Unable to resugar constructor */<init>(var53, 1, var56);
                     var43 = (Tag)var42.encodeStart(var49, var51).getOrThrow();
                  } else {
                     var43 = null;
                  }

                  if (Intrinsics.areEqual(var25, var32.nullIfEmpty(var43))) {
                     if (INSTANCE.isNullOrAir(var9.getPriceItem2())) {
                        String var34;
                        if (var10 != null) {
                           var6 = var10;
                           var10001 = BuiltInRegistries.ITEM.getKey(var6.getItem());
                           Intrinsics.checkNotNullExpressionValue(var10001, "");
                           var34 = var10001.toString();
                           Intrinsics.checkNotNullExpressionValue(var34, "");
                        } else {
                           var34 = null;
                        }

                        if (INSTANCE.isNullOrAir(var34)) {
                           break;
                        }
                     }

                     if (var9.getPriceItem2() != null && var10 != null) {
                        String var26 = var9.getPriceItem2();
                        var6 = var10;
                        var10001 = BuiltInRegistries.ITEM.getKey(var6.getItem());
                        Intrinsics.checkNotNullExpressionValue(var10001, "");
                        String var36 = var10001.toString();
                        Intrinsics.checkNotNullExpressionValue(var36, "");
                        if (Intrinsics.areEqual(var26, var36)) {
                           Tag var27 = NbtUtils.INSTANCE.nullIfEmpty(var9.getPrice1Nbt());
                           NbtUtils var37 = NbtUtils.INSTANCE;
                           var6 = var10;
                           Tag var45;
                           if (!ItemTypeExtensionsKt.isEmpty(var10)) {
                              Codec var44 = ItemTypeExtensionsKt.getCOMPONENTS_CHANGES_CODEC();
                              DynamicOps var50 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
                              ItemStack var52 = new ItemStack;
                              ItemLike var54 = (ItemLike)var6.getItem();
                              PatchedDataComponentMap var58 = var6.getTag();
                              if (var58 == null) {
                                 DataComponentMap var59 = DataComponentMap.EMPTY;
                                 Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                                 var58 = (PatchedDataComponentMap)var59;
                              }

                              var52./* $VF: Unable to resugar constructor */<init>(var54, 1, var58);
                              var45 = (Tag)var44.encodeStart(var50, var52).getOrThrow();
                           } else {
                              var45 = null;
                           }

                           if (Intrinsics.areEqual(var27, var37.nullIfEmpty(var45))) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   public final boolean onInput(int var1, int var2) {
      if (!ModSettings.INSTANCE.getVILLAGER_TRADING_ENABLE().getBooleanValue()) {
         return false;
      } else if (!VanillaUtil.INSTANCE.inGame()) {
         return false;
      } else {
         Screen var10000 = Vanilla.INSTANCE.screen();
         if (var10000 == null) {
            return false;
         } else {
            Screen var10 = var10000;
            if (!(var10000 instanceof MerchantScreen)) {
               return false;
            } else if (currentVillager == null) {
               return false;
            } else {
               AbstractVillager var11 = currentVillager;
               boolean var3;
               boolean var4 = (var3 = this.isLocalAvailable(var11)) && Hotkeys.INSTANCE.getLOCAL_BOOKMARK_TRADE().isActivated();
               boolean var5 = Hotkeys.INSTANCE.getGLOBAL_BOOKMARK_TRADE().isActivated();
               boolean var6 = var3 && Hotkeys.INSTANCE.getLOCAL_BOOKMARK_TRADE1().isActivated();
               boolean var7 = Hotkeys.INSTANCE.getGLOBAL_BOOKMARK_TRADE1().isActivated();
               boolean var8 = var3 && Hotkeys.INSTANCE.getLOCAL_BOOKMARK_TRADE2().isActivated();
               boolean var9 = Hotkeys.INSTANCE.getGLOBAL_BOOKMARK_TRADE2().isActivated();
               if (var4 || var5) {
                  return this.handleBookmarkKeys((MerchantScreen)var10, var5, var11, 0);
               } else if (var6 || var7) {
                  return this.handleBookmarkKeys((MerchantScreen)var10, var7, var11, 1);
               } else if (var8 || var9) {
                  return this.handleBookmarkKeys((MerchantScreen)var10, var9, var11, 2);
               } else if (Hotkeys.INSTANCE.getDO_GLOBAL_TRADE().isActivated()) {
                  return this.doGlobalTrades((MerchantScreen)var10);
               } else if (var3 && Hotkeys.INSTANCE.getDO_LOCAL_TRADE().isActivated()) {
                  return this.doLocalTrades((MerchantScreen)var10);
               } else if (Hotkeys.INSTANCE.getDO_GLOBAL_TRADE1().isActivated()) {
                  return this.doGlobalTrades1((MerchantScreen)var10);
               } else if (var3 && Hotkeys.INSTANCE.getDO_LOCAL_TRADE1().isActivated()) {
                  return this.doLocalTrades1((MerchantScreen)var10);
               } else if (Hotkeys.INSTANCE.getDO_GLOBAL_TRADE2().isActivated()) {
                  return this.doGlobalTrades2((MerchantScreen)var10);
               } else {
                  return var3 && Hotkeys.INSTANCE.getDO_LOCAL_TRADE2().isActivated() ? this.doLocalTrades2((MerchantScreen)var10) : false;
               }
            }
         }
      }
   }

   public final boolean doGlobalTrades(@NotNull MerchantScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.toList(currentGlobalBookmarks);
      return this.checkAndDoTrades(var1, var2);
   }

   public final boolean doGlobalTrades1(@NotNull MerchantScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.toList(currentGlobalBookmarks1);
      return this.checkAndDoTrades(var1, var2);
   }

   public final boolean doGlobalTrades2(@NotNull MerchantScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.toList(currentGlobalBookmarks2);
      return this.checkAndDoTrades(var1, var2);
   }

   public final boolean doLocalTrades(@NotNull MerchantScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.toList(currentVillagerBookmarks);
      return this.checkAndDoTrades(var1, var2);
   }

   public final boolean doLocalTrades1(@NotNull MerchantScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.toList(currentVillagerBookmarks1);
      return this.checkAndDoTrades(var1, var2);
   }

   public final boolean doLocalTrades2(@NotNull MerchantScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2 = CollectionsKt.toList(currentVillagerBookmarks2);
      return this.checkAndDoTrades(var1, var2);
   }

   private final boolean checkAndDoTrades(MerchantScreen var1, List var2) {
      boolean var3;
      boolean var4 = var3 = !var2.isEmpty();
      if (var3) {
         Vanilla.INSTANCE.queueForMainThread(VillagerTradeManager::checkAndDoTrades$lambda$3$lambda$2);
      }

      return var4;
   }

   @NotNull
   public final Function2 getDoTrades() {
      return doTrades;
   }

   public final void setDoTrades(@NotNull Function2 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      doTrades = var1;
   }

   private final void doTradesReal(MerchantScreen var1, List var2) {
      if (Vanilla.INSTANCE.screen() == var1) {
         AbstractContainerMenu var10000 = ((AbstractContainerScreen)var1).getMenu();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         Intrinsics.checkNotNull(var10000);
         MerchantMenu var3;
         if ((var3 = (MerchantMenu & AbstractContainerMenu)var10000) == Vanilla.INSTANCE.container()) {
            MerchantOffers var37 = InGameExKt.get_recipes_/* $VF was: get(recipes) */(var1);
            Intrinsics.checkNotNullExpressionValue(var37, "");
            Iterable var4 = (Iterable)var37;
            var var5 = new ArrayList();
            int var6 = 0;

            for (Object var8 : var4) {
               int var9;
               if ((var9 = var6++) < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               MerchantOffer var11 = (MerchantOffer)var8;
               int var18 = var9;
               Intrinsics.checkNotNull(var11);
               Integer var39 = !VillageExKt.get_isDisabled_/* $VF was: get(isDisabled) */(var11) && INSTANCE.has(var2, var11) ? var18 : null;
               if (var39 != null) {
                  Integer var12 = var39;
                  var5.add(var12);
               }
            }

            Iterator var13 = var5.iterator();

            while (var13.hasNext()) {
               int var10 = ((Number)var13.next()).intValue();
               Log.INSTANCE.trace("Found offer: " + var10);
               if (var10 >= 0) {
                  NonNullList var40 = ((AbstractContainerMenu)var3).slots;
                  Intrinsics.checkNotNullExpressionValue(((AbstractContainerMenu)var3).slots, "");
                  Slot var17 = (Slot)((List)var40).get(2);
                  InGameExKt.set_selectedIndex_/* $VF was: set(selectedIndex) */(var1, var10);
                  InGameExKt._syncRecipeIndex_/* $VF was: (syncRecipeIndex) */(var1);
                  ItemStack var41 = var17.getItem();
                  Intrinsics.checkNotNullExpressionValue(var41, "");
                  ItemStack var30 = var41;
                  org.anti_ad.mc.ipnext.item.ItemStack var42;
                  if (var41.isEmpty()) {
                     var42 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                  } else {
                     ItemStack$Companion var43 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                     ItemStack var14 = var30;
                     Item var10003 = var14.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10003, "");
                     DataComponentMap var10004 = var14.getComponents();
                     Intrinsics.checkNotNull(var10004);
                     var42 = var43.invoke(
                        new ItemType(
                           var10003,
                           (PatchedDataComponentMap)var10004,
                           var14.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var14),
                           false,
                           false,
                           null,
                           112,
                           null
                        ),
                        var30.getCount()
                     );
                  }

                  ItemType var15 = var42.getItemType();
                  Log.INSTANCE.trace("selected recipe with resultType: " + var15);
                  var6 = 0;

                  while (true) {
                     ContainerClicker.INSTANCE.shiftClick(2);
                     var6++;
                     Log var44 = Log.INSTANCE;
                     ItemStack var10001 = var17.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10001, "");
                     ItemStack var19 = var10001;
                     org.anti_ad.mc.ipnext.item.ItemStack var61;
                     if (var10001.isEmpty()) {
                        var61 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                     } else {
                        ItemStack$Companion var62 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                        var30 = var19;
                        Item var76 = var30.getItem();
                        Intrinsics.checkNotNullExpressionValue(var76, "");
                        DataComponentMap var10005 = var30.getComponents();
                        Intrinsics.checkNotNull(var10005);
                        var61 = var62.invoke(
                           new ItemType(
                              var76,
                              (PatchedDataComponentMap)var10005,
                              var30.getComponentsPatch(),
                              new VanillaAccessorsKt$(itemType)$1(var30),
                              false,
                              false,
                              null,
                              112,
                              null
                           ),
                           var19.getCount()
                        );
                     }

                     boolean var63 = ItemStackExtensionsKt.isEmpty(var61);
                     ItemStack var10002 = var17.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10002, "");
                     var19 = var10002;
                     org.anti_ad.mc.ipnext.item.ItemStack var68;
                     if (var10002.isEmpty()) {
                        var68 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                     } else {
                        ItemStack$Companion var69 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                        var30 = var19;
                        Item var81 = var30.getItem();
                        Intrinsics.checkNotNullExpressionValue(var81, "");
                        DataComponentMap var10006 = var30.getComponents();
                        Intrinsics.checkNotNull(var10006);
                        var68 = var69.invoke(
                           new ItemType(
                              var81,
                              (PatchedDataComponentMap)var10006,
                              var30.getComponentsPatch(),
                              new VanillaAccessorsKt$(itemType)$1(var30),
                              false,
                              false,
                              null,
                              112,
                              null
                           ),
                           var19.getCount()
                        );
                     }

                     ItemType var21 = var68.getItemType();
                     ResourceLocation var70 = BuiltInRegistries.ITEM.getKey(var21.getItem());
                     Intrinsics.checkNotNullExpressionValue(var70, "");
                     String var71 = var70.toString();
                     Intrinsics.checkNotNullExpressionValue(var71, "");
                     var44.trace("state after click " + var63 + ", " + var71);
                     if (var6 <= 200) {
                        ItemStack var45 = var17.getItem();
                        Intrinsics.checkNotNullExpressionValue(var45, "");
                        var19 = var45;
                        org.anti_ad.mc.ipnext.item.ItemStack var46;
                        if (var45.isEmpty()) {
                           var46 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                        } else {
                           ItemStack$Companion var47 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                           var30 = var19;
                           Item var72 = var30.getItem();
                           Intrinsics.checkNotNullExpressionValue(var72, "");
                           DataComponentMap var77 = var30.getComponents();
                           Intrinsics.checkNotNull(var77);
                           var46 = var47.invoke(
                              new ItemType(
                                 var72,
                                 (PatchedDataComponentMap)var77,
                                 var30.getComponentsPatch(),
                                 new VanillaAccessorsKt$(itemType)$1(var30),
                                 false,
                                 false,
                                 null,
                                 112,
                                 null
                              ),
                              var19.getCount()
                           );
                        }

                        if (!ItemStackExtensionsKt.isEmpty(var46)) {
                           ItemStack var48 = var17.getItem();
                           Intrinsics.checkNotNullExpressionValue(var48, "");
                           var19 = var48;
                           org.anti_ad.mc.ipnext.item.ItemStack var49;
                           if (var48.isEmpty()) {
                              var49 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                           } else {
                              ItemStack$Companion var50 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                              var30 = var19;
                              Item var73 = var30.getItem();
                              Intrinsics.checkNotNullExpressionValue(var73, "");
                              DataComponentMap var78 = var30.getComponents();
                              Intrinsics.checkNotNull(var78);
                              var49 = var50.invoke(
                                 new ItemType(
                                    var73,
                                    (PatchedDataComponentMap)var78,
                                    var30.getComponentsPatch(),
                                    new VanillaAccessorsKt$(itemType)$1(var30),
                                    false,
                                    false,
                                    null,
                                    112,
                                    null
                                 ),
                                 var19.getCount()
                              );
                           }

                           ItemType var24 = var49.getItemType();
                           ResourceLocation var51 = BuiltInRegistries.ITEM.getKey(var24.getItem());
                           Intrinsics.checkNotNullExpressionValue(var51, "");
                           String var52 = var51.toString();
                           Intrinsics.checkNotNullExpressionValue(var52, "");
                           ItemType var25 = var15;
                           ResourceLocation var64 = BuiltInRegistries.ITEM.getKey(var25.getItem());
                           Intrinsics.checkNotNullExpressionValue(var64, "");
                           String var65 = var64.toString();
                           Intrinsics.checkNotNullExpressionValue(var65, "");
                           if (Intrinsics.areEqual(var52, var65)) {
                              continue;
                           }
                        }
                     }

                     InGameExKt.set_selectedIndex_/* $VF was: set(selectedIndex) */(var1, var10);
                     InGameExKt._syncRecipeIndex_/* $VF was: (syncRecipeIndex) */(var1);
                     if (var6 > 400) {
                        break;
                     }

                     ItemStack var53 = var17.getItem();
                     Intrinsics.checkNotNullExpressionValue(var53, "");
                     var19 = var53;
                     org.anti_ad.mc.ipnext.item.ItemStack var54;
                     if (var53.isEmpty()) {
                        var54 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                     } else {
                        ItemStack$Companion var55 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                        var30 = var19;
                        Item var74 = var30.getItem();
                        Intrinsics.checkNotNullExpressionValue(var74, "");
                        DataComponentMap var79 = var30.getComponents();
                        Intrinsics.checkNotNull(var79);
                        var54 = var55.invoke(
                           new ItemType(
                              var74,
                              (PatchedDataComponentMap)var79,
                              var30.getComponentsPatch(),
                              new VanillaAccessorsKt$(itemType)$1(var30),
                              false,
                              false,
                              null,
                              112,
                              null
                           ),
                           var19.getCount()
                        );
                     }

                     if (ItemStackExtensionsKt.isEmpty(var54)) {
                        break;
                     }

                     ItemStack var56 = var17.getItem();
                     Intrinsics.checkNotNullExpressionValue(var56, "");
                     var19 = var56;
                     org.anti_ad.mc.ipnext.item.ItemStack var57;
                     if (var56.isEmpty()) {
                        var57 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                     } else {
                        ItemStack$Companion var58 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                        var30 = var19;
                        Item var75 = var30.getItem();
                        Intrinsics.checkNotNullExpressionValue(var75, "");
                        DataComponentMap var80 = var30.getComponents();
                        Intrinsics.checkNotNull(var80);
                        var57 = var58.invoke(
                           new ItemType(
                              var75,
                              (PatchedDataComponentMap)var80,
                              var30.getComponentsPatch(),
                              new VanillaAccessorsKt$(itemType)$1(var30),
                              false,
                              false,
                              null,
                              112,
                              null
                           ),
                           var19.getCount()
                        );
                     }

                     ItemType var28 = var57.getItemType();
                     ResourceLocation var59 = BuiltInRegistries.ITEM.getKey(var28.getItem());
                     Intrinsics.checkNotNullExpressionValue(var59, "");
                     String var60 = var59.toString();
                     Intrinsics.checkNotNullExpressionValue(var60, "");
                     ItemType var29 = var15;
                     ResourceLocation var66 = BuiltInRegistries.ITEM.getKey(var29.getItem());
                     Intrinsics.checkNotNullExpressionValue(var66, "");
                     String var67 = var66.toString();
                     Intrinsics.checkNotNullExpressionValue(var67, "");
                     if (!Intrinsics.areEqual(var60, var67)) {
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   public final void doTrades116(@NotNull MerchantScreen var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (Vanilla.INSTANCE.screen() == var1) {
         AbstractContainerMenu var10000 = ((AbstractContainerScreen)var1).getMenu();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         Intrinsics.checkNotNull(var10000);
         MerchantMenu var3;
         if ((var3 = (MerchantMenu)var10000) == Vanilla.INSTANCE.container()) {
            MerchantOffers var14 = InGameExKt.get_recipes_/* $VF was: get(recipes) */(var1);
            Intrinsics.checkNotNullExpressionValue(var14, "");
            Iterable var4 = (Iterable)var14;
            var var5 = new ArrayList();
            int var6 = 0;

            for (MerchantOffer var7 : var4) {
               int var8;
               if ((var8 = var6++) < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               var7 = var7;
               var8 = var8;
               Intrinsics.checkNotNull(var7);
               Integer var16 = !VillageExKt.get_isDisabled_/* $VF was: get(isDisabled) */(var7) && INSTANCE.has(var2, var7) ? var8 : null;
               if (var16 != null) {
                  Integer var12 = var16;
                  var5.add(var12);
               }
            }

            var2 = var5;
            new VillagerTradeManager$VillageTrader(var2, var1, var3).run();
         }
      }
   }

   private final boolean handleBookmarkKeys(MerchantScreen var1, boolean var2, AbstractVillager var3, int var4) {
      TradeOfferButton var10000 = InGameExKt.get_offers_/* $VF was: get(offers) */(var1);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Object[] var5 = (Object[])var10000;
      int var6 = 0;
      int var7 = var5.length;

      while (true) {
         if (var6 >= var7) {
            var10000 = null;
            break;
         }

         Object var8;
         TradeOfferButton var9;
         Intrinsics.checkNotNull(var9 = (TradeOfferButton)(var8 = var5[var6]));
         if (WidgetExKt.get_isHovered_/* $VF was: get(isHovered) */((AbstractButton)var9)) {
            var10000 = (TradeOfferButton)var8;
            break;
         }

         var6++;
      }

      TradeOfferButton var10;
      if ((var10 = var10000) != null) {
         var7 = var10.getIndex() + InGameExKt.get_indexStartOffset_/* $VF was: get(indexStartOffset) */(var1);
         INSTANCE.toggleBookmark(var1, var7, var2, var3, var4);
         return true;
      } else {
         return false;
      }
   }

   public final void toggleBookmark(@NotNull MerchantScreen var1, int var2, boolean var3, @NotNull AbstractVillager var4, int var5) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var4, "");
      MerchantOffer var11;
      Intrinsics.checkNotNull(var11 = (MerchantOffer)InGameExKt.get_recipes_/* $VF was: get(recipes) */(var1).get(var2));
      ItemType var10000 = VillageExKt.get_originalFirstBuyItem_/* $VF was: get(originalFirstBuyItem) */(var11).itemStack();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var6 = var10000;
      Item var10002 = var6.getItem();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      DataComponentMap var10003 = var6.getComponents();
      Intrinsics.checkNotNull(var10003);
      ItemType var14;
      ItemType var8 = var14 = new ItemType(
         var10002, (PatchedDataComponentMap)var10003, var6.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var6), false, false, null, 112, null
      );
      ResourceLocation var25 = BuiltInRegistries.ITEM.getKey(var8.getItem());
      Intrinsics.checkNotNullExpressionValue(var25, "");
      String var26 = var25.toString();
      Intrinsics.checkNotNullExpressionValue(var26, "");
      String var20 = var26;
      NbtUtils var27 = NbtUtils.INSTANCE;
      Tag var36;
      if (!ItemTypeExtensionsKt.isEmpty(var14)) {
         Codec var10001 = ItemTypeExtensionsKt.getCOMPONENTS_CHANGES_CODEC();
         DynamicOps var44 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
         ItemType var9 = var14;
         ItemStack var49 = new ItemStack;
         ItemLike var10005 = (ItemLike)var9.getItem();
         PatchedDataComponentMap var10007 = var9.getTag();
         if (var10007 == null) {
            DataComponentMap var56 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10007 = (PatchedDataComponentMap)var56;
         }

         var49./* $VF: Unable to resugar constructor */<init>(var10005, 1, var10007);
         var36 = (Tag)var10001.encodeStart(var44, var49).getOrThrow();
      } else {
         var36 = null;
      }

      Tag var7;
      label56: {
         var7 = var27.nullIfEmpty(var36);
         ItemCost var28 = VillageExKt.get_secondBuyItem_/* $VF was: get(secondBuyItem) */(var11);
         if (var28 != null) {
            ItemStack var29 = var28.itemStack();
            if (var29 != null) {
               ItemStack var15 = var29;
               var10002 = var15.getItem();
               Intrinsics.checkNotNullExpressionValue(var10002, "");
               var10003 = var15.getComponents();
               Intrinsics.checkNotNull(var10003);
               var10000 = new ItemType(
                  var10002,
                  (PatchedDataComponentMap)var10003,
                  var15.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var15),
                  false,
                  false,
                  null,
                  112,
                  null
               );
               break label56;
            }
         }

         var10000 = null;
      }

      ItemType var16 = var10000;
      String var38;
      if (var16 != null) {
         ItemType var10 = var16;
         ResourceLocation var37 = BuiltInRegistries.ITEM.getKey(var10.getItem());
         Intrinsics.checkNotNullExpressionValue(var37, "");
         var38 = var37.toString();
         Intrinsics.checkNotNullExpressionValue(var38, "");
      } else {
         var38 = null;
      }

      String var21;
      Tag var31;
      if ((var21 = this.nullIfAir(var38)) != null) {
         if (var16 != null) {
            ItemType var17 = var16;
            if (!ItemTypeExtensionsKt.isEmpty(var16)) {
               Codec var40 = ItemTypeExtensionsKt.getCOMPONENTS_CHANGES_CODEC();
               DynamicOps var46 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
               ItemStack var51 = new ItemStack;
               ItemLike var54 = (ItemLike)var17.getItem();
               PatchedDataComponentMap var57 = var17.getTag();
               if (var57 == null) {
                  DataComponentMap var58 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var57 = (PatchedDataComponentMap)var58;
               }

               var51./* $VF: Unable to resugar constructor */<init>(var54, 1, var57);
               var36 = (Tag)var40.encodeStart(var46, var51).getOrThrow();
            } else {
               var36 = null;
            }
         } else {
            var36 = null;
         }

         var31 = NbtUtils.INSTANCE.nullIfEmpty(var36);
      } else {
         var31 = null;
      }

      Tag var22 = var31;
      ItemStack var32 = VillageExKt.get_sellItem_/* $VF was: get(sellItem) */(var11);
      Intrinsics.checkNotNullExpressionValue(var32, "");
      ItemStack var23 = var32;
      var10002 = var23.getItem();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      var10003 = var23.getComponents();
      Intrinsics.checkNotNull(var10003);
      ItemType var18;
      ItemType var12 = var18 = new ItemType(
         var10002, (PatchedDataComponentMap)var10003, var23.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var23), false, false, null, 112, null
      );
      ResourceLocation var33 = BuiltInRegistries.ITEM.getKey(var12.getItem());
      Intrinsics.checkNotNullExpressionValue(var33, "");
      String var34 = var33.toString();
      Intrinsics.checkNotNullExpressionValue(var34, "");
      String var24 = var34;
      NbtUtils var35 = NbtUtils.INSTANCE;
      ItemType var13 = var18;
      if (!ItemTypeExtensionsKt.isEmpty(var18)) {
         Codec var42 = ItemTypeExtensionsKt.getCOMPONENTS_CHANGES_CODEC();
         DynamicOps var48 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
         ItemStack var53 = new ItemStack;
         ItemLike var55 = (ItemLike)var13.getItem();
         PatchedDataComponentMap var59 = var13.getTag();
         if (var59 == null) {
            DataComponentMap var60 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var59 = (PatchedDataComponentMap)var60;
         }

         var53./* $VF: Unable to resugar constructor */<init>(var55, 1, var59);
         var36 = (Tag)var42.encodeStart(var48, var53).getOrThrow();
      } else {
         var36 = null;
      }

      Tag var19 = var35.nullIfEmpty(var36);
      switch (var5) {
         case 0:
            this.doToggle(var3, var20, var7, var21, var22, var24, var19, var4);
            return;
         case 1:
            this.doToggle1(var3, var20, var7, var21, var22, var24, var19, var4);
            return;
         case 2:
            this.doToggle2(var3, var20, var7, var21, var22, var24, var19, var4);
      }
   }

   private final VillagerTradeData search(Iterable var1, String var2, Tag var3, String var4, Tag var5, String var6, Tag var7) {
      Iterator var10 = var1.iterator();

      Object var10000;
      while (true) {
         if (!var10.hasNext()) {
            var10000 = null;
            break;
         }

         Object var8;
         VillagerTradeData var9;
         if (Intrinsics.areEqual((var9 = (VillagerTradeData)(var8 = var10.next())).getPriceItem1(), var2)
            && Intrinsics.areEqual(NbtUtils.INSTANCE.nullIfEmpty(var9.getPrice1Nbt()), NbtUtils.INSTANCE.nullIfEmpty(var3))
            && Intrinsics.areEqual(var9.getPriceItem2(), var4)
            && Intrinsics.areEqual(NbtUtils.INSTANCE.nullIfEmpty(var9.getPrice2Nbt()), NbtUtils.INSTANCE.nullIfEmpty(var5))
            && Intrinsics.areEqual(var9.getResultItem(), var6)
            && Intrinsics.areEqual(NbtUtils.INSTANCE.nullIfEmpty(var9.getResultNbt()), NbtUtils.INSTANCE.nullIfEmpty(var7))) {
            var10000 = var8;
            break;
         }
      }

      return (VillagerTradeData)var10000;
   }

   private final void doToggle(boolean var1, String var2, Tag var3, String var4, Tag var5, String var6, Tag var7, AbstractVillager var8) {
      if (var1) {
         VillagerTradeData var10 = this.search(currentGlobalBookmarks, var2, var3, var4, var5, var6, var7);
         String var12 = this.getProfesion(var8);
         List var15;
         if (var10 == null) {
            VillagerDataManager var14 = VillagerDataManager.INSTANCE;
            VillagerTradeData var16 = new VillagerTradeData;
            String var17;
            if (var7 != null) {
               var17 = var7.getAsString();
               Intrinsics.checkNotNullExpressionValue(var17, "");
            } else {
               var17 = null;
            }

            String var18;
            if (var3 != null) {
               var18 = var3.getAsString();
               Intrinsics.checkNotNullExpressionValue(var18, "");
            } else {
               var18 = null;
            }

            String var19;
            if (var5 != null) {
               var19 = var5.getAsString();
               Intrinsics.checkNotNullExpressionValue(var19, "");
            } else {
               var19 = null;
            }

            var16./* $VF: Unable to resugar constructor */<init>(var6, var2, var4, var17, var18, var19);
            var14.addGlobal(var12, var16);
            var15 = VillagerDataManager.INSTANCE.getGlobal(var12);
         } else {
            VillagerDataManager.INSTANCE.removeGlobal(var12, var10);
            var15 = VillagerDataManager.INSTANCE.getGlobal(var12);
         }

         currentGlobalBookmarks = var15;
      } else {
         VillagerTradeData var9 = this.search(currentVillagerBookmarks, var2, var3, var4, var5, var6, var7);
         String var11 = EntityExKt.get_uuidString_/* $VF was: get(uuidString) */((Entity)var8);
         List var13;
         if (var9 == null) {
            VillagerDataManager var10000 = VillagerDataManager.INSTANCE;
            VillagerTradeData var10002 = new VillagerTradeData;
            String var10007;
            if (var7 != null) {
               var10007 = var7.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10007, "");
            } else {
               var10007 = null;
            }

            String var10008;
            if (var3 != null) {
               var10008 = var3.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10008, "");
            } else {
               var10008 = null;
            }

            String var10009;
            if (var5 != null) {
               var10009 = var5.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10009, "");
            } else {
               var10009 = null;
            }

            var10002./* $VF: Unable to resugar constructor */<init>(var6, var2, var4, var10007, var10008, var10009);
            var10000.addLocal(var11, var10002);
            var13 = VillagerDataManager.INSTANCE.getLocal(var11);
         } else {
            VillagerDataManager.INSTANCE.removeLocal(var11, var9);
            var13 = VillagerDataManager.INSTANCE.getLocal(var11);
         }

         currentVillagerBookmarks = var13;
      }
   }

   private final void doToggle1(boolean var1, String var2, Tag var3, String var4, Tag var5, String var6, Tag var7, AbstractVillager var8) {
      if (var1) {
         VillagerTradeData var10 = this.search(currentGlobalBookmarks1, var2, var3, var4, var5, var6, var7);
         String var12 = this.getProfesion(var8);
         List var15;
         if (var10 == null) {
            VillagerDataManager var14 = VillagerDataManager.INSTANCE;
            VillagerTradeData var16 = new VillagerTradeData;
            String var17;
            if (var7 != null) {
               var17 = var7.getAsString();
               Intrinsics.checkNotNullExpressionValue(var17, "");
            } else {
               var17 = null;
            }

            String var18;
            if (var3 != null) {
               var18 = var3.getAsString();
               Intrinsics.checkNotNullExpressionValue(var18, "");
            } else {
               var18 = null;
            }

            String var19;
            if (var5 != null) {
               var19 = var5.getAsString();
               Intrinsics.checkNotNullExpressionValue(var19, "");
            } else {
               var19 = null;
            }

            var16./* $VF: Unable to resugar constructor */<init>(var6, var2, var4, var17, var18, var19);
            var14.addGlobal1(var12, var16);
            var15 = VillagerDataManager.INSTANCE.getGlobal1(var12);
         } else {
            VillagerDataManager.INSTANCE.removeGlobal1(var12, var10);
            var15 = VillagerDataManager.INSTANCE.getGlobal1(var12);
         }

         currentGlobalBookmarks1 = var15;
      } else {
         VillagerTradeData var9 = this.search(currentVillagerBookmarks1, var2, var3, var4, var5, var6, var7);
         String var11 = EntityExKt.get_uuidString_/* $VF was: get(uuidString) */((Entity)var8);
         List var13;
         if (var9 == null) {
            VillagerDataManager var10000 = VillagerDataManager.INSTANCE;
            VillagerTradeData var10002 = new VillagerTradeData;
            String var10007;
            if (var7 != null) {
               var10007 = var7.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10007, "");
            } else {
               var10007 = null;
            }

            String var10008;
            if (var3 != null) {
               var10008 = var3.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10008, "");
            } else {
               var10008 = null;
            }

            String var10009;
            if (var5 != null) {
               var10009 = var5.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10009, "");
            } else {
               var10009 = null;
            }

            var10002./* $VF: Unable to resugar constructor */<init>(var6, var2, var4, var10007, var10008, var10009);
            var10000.addLocal1(var11, var10002);
            var13 = VillagerDataManager.INSTANCE.getLocal1(var11);
         } else {
            VillagerDataManager.INSTANCE.removeLocal1(var11, var9);
            var13 = VillagerDataManager.INSTANCE.getLocal1(var11);
         }

         currentVillagerBookmarks1 = var13;
      }
   }

   private final void doToggle2(boolean var1, String var2, Tag var3, String var4, Tag var5, String var6, Tag var7, AbstractVillager var8) {
      if (var1) {
         VillagerTradeData var10 = this.search(currentGlobalBookmarks2, var2, var3, var4, var5, var6, var7);
         String var12 = this.getProfesion(var8);
         List var15;
         if (var10 == null) {
            VillagerDataManager var14 = VillagerDataManager.INSTANCE;
            VillagerTradeData var16 = new VillagerTradeData;
            String var17;
            if (var7 != null) {
               var17 = var7.getAsString();
               Intrinsics.checkNotNullExpressionValue(var17, "");
            } else {
               var17 = null;
            }

            String var18;
            if (var3 != null) {
               var18 = var3.getAsString();
               Intrinsics.checkNotNullExpressionValue(var18, "");
            } else {
               var18 = null;
            }

            String var19;
            if (var5 != null) {
               var19 = var5.getAsString();
               Intrinsics.checkNotNullExpressionValue(var19, "");
            } else {
               var19 = null;
            }

            var16./* $VF: Unable to resugar constructor */<init>(var6, var2, var4, var17, var18, var19);
            var14.addGlobal2(var12, var16);
            var15 = VillagerDataManager.INSTANCE.getGlobal2(var12);
         } else {
            VillagerDataManager.INSTANCE.removeGlobal2(var12, var10);
            var15 = VillagerDataManager.INSTANCE.getGlobal2(var12);
         }

         currentGlobalBookmarks2 = var15;
      } else {
         VillagerTradeData var9 = this.search(currentVillagerBookmarks2, var2, var3, var4, var5, var6, var7);
         String var11 = EntityExKt.get_uuidString_/* $VF was: get(uuidString) */((Entity)var8);
         List var13;
         if (var9 == null) {
            VillagerDataManager var10000 = VillagerDataManager.INSTANCE;
            VillagerTradeData var10002 = new VillagerTradeData;
            String var10007;
            if (var7 != null) {
               var10007 = var7.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10007, "");
            } else {
               var10007 = null;
            }

            String var10008;
            if (var3 != null) {
               var10008 = var3.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10008, "");
            } else {
               var10008 = null;
            }

            String var10009;
            if (var5 != null) {
               var10009 = var5.getAsString();
               Intrinsics.checkNotNullExpressionValue(var10009, "");
            } else {
               var10009 = null;
            }

            var10002./* $VF: Unable to resugar constructor */<init>(var6, var2, var4, var10007, var10008, var10009);
            var10000.addLocal2(var11, var10002);
            var13 = VillagerDataManager.INSTANCE.getLocal2(var11);
         } else {
            VillagerDataManager.INSTANCE.removeLocal2(var11, var9);
            var13 = VillagerDataManager.INSTANCE.getLocal2(var11);
         }

         currentVillagerBookmarks2 = var13;
      }
   }

   private static final void checkAndDoTrades$lambda$3$lambda$2(MerchantScreen var0, List var1) {
      doTrades.invoke(var0, var1);
   }
}
