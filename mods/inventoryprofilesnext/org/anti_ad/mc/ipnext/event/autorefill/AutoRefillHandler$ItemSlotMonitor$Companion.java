package org.anti_ad.mc.ipnext.event.autorefill;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.AutoRefillNbtMatchType;
import org.anti_ad.mc.ipnext.config.AutoRefillSettings;
import org.anti_ad.mc.ipnext.config.ThresholdUnit;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.AreaTypes;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.item.PotionEffect;
import org.anti_ad.mc.ipnext.item.rule.parameter.Match;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAutoRefillHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 6 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 7 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/NativeRuleKt\n+ 8 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/NativeRuleKt$compareByMatch$1\n*L\n1#1,928:1\n78#2:929\n88#2:933\n71#2:934\n66#2,7:935\n1563#3:930\n1634#3,2:931\n1636#3:942\n1563#3:948\n1634#3,2:949\n1636#3:954\n1563#3:955\n1634#3,3:956\n1878#3,2:961\n1880#3:965\n1869#3:979\n1870#3:982\n1869#3,2:1014\n1563#3:1029\n1634#3,2:1030\n1636#3:1035\n1634#3,3:1036\n1869#3:1061\n1870#3:1064\n354#4:943\n106#4:944\n408#4:945\n417#4:946\n414#4:947\n421#4,3:951\n135#4:959\n106#4:960\n131#4:963\n112#4:964\n276#4:970\n272#4:971\n269#4:972\n287#4,4:973\n280#4,2:977\n282#4,2:980\n284#4,17:983\n123#4:1001\n106#4:1002\n125#4:1003\n123#4:1004\n106#4:1005\n127#4:1006\n106#4:1007\n125#4:1008\n123#4:1009\n106#4:1010\n127#4:1011\n106#4:1012\n361#4:1013\n354#4:1016\n106#4:1017\n356#4:1018\n106#4:1019\n354#4:1020\n106#4:1021\n358#4:1022\n356#4:1023\n106#4:1024\n341#4:1025\n106#4:1026\n417#4:1027\n414#4:1028\n421#4,3:1032\n276#4:1052\n272#4:1053\n269#4:1054\n287#4,4:1055\n280#4,2:1059\n282#4,2:1062\n284#4,17:1065\n1#5:966\n183#6,2:967\n183#6:969\n184#6:1000\n212#7,11:1039\n224#7:1051\n216#8:1050\n*S KotlinDebug\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n*L\n565#1:929\n573#1:933\n573#1:934\n573#1:935,7\n572#1:930\n572#1:931,2\n572#1:942\n653#1:948\n653#1:949,2\n653#1:954\n653#1:955\n653#1:956,3\n704#1:961,2\n704#1:965\n722#1:979\n722#1:982\n762#1:1014,2\n655#1:1029\n655#1:1030,2\n655#1:1035\n655#1:1036,3\n728#1:1061\n728#1:1064\n577#1:943\n577#1:944\n652#1:945\n653#1:946\n653#1:947\n653#1:951,3\n659#1:959\n659#1:960\n705#1:963\n705#1:964\n722#1:970\n722#1:971\n722#1:972\n722#1:973,4\n722#1:977,2\n722#1:980,2\n722#1:983,17\n738#1:1001\n738#1:1002\n739#1:1003\n739#1:1004\n739#1:1005\n739#1:1006\n739#1:1007\n739#1:1008\n739#1:1009\n739#1:1010\n739#1:1011\n739#1:1012\n751#1:1013\n790#1:1016\n790#1:1017\n793#1:1018\n793#1:1019\n585#1:1020\n585#1:1021\n585#1:1022\n585#1:1023\n585#1:1024\n585#1:1025\n585#1:1026\n655#1:1027\n655#1:1028\n655#1:1032,3\n728#1:1052\n728#1:1053\n728#1:1054\n728#1:1055,4\n728#1:1059,2\n728#1:1062,2\n728#1:1065,17\n713#1:967,2\n717#1:969\n717#1:1000\n668#1:1039,11\n668#1:1051\n668#1:1050\n*E\n"
)
public final class AutoRefillHandler$ItemSlotMonitor$Companion implements SpecificItemSlotMonitor {
   private AutoRefillHandler$ItemSlotMonitor$Companion() {
   }

   private final Integer findCorrespondingSlot(ItemStack var1, ItemStack var2) {
      InventoryMenu var5;
      NonNullList var10000 = ((AbstractContainerMenu)(var5 = Vanilla.INSTANCE.playerContainer())).slots;
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      List var8 = (List)var10000;
      AreaTypes var7 = AreaTypes.INSTANCE;
      Iterable var6;
      Iterable var21 = var6 = (AutoRefillSettings.INSTANCE.getIGNORE_LOCKED_SLOTS().getValue()
            ? var7.getPlayerStorage()
            : var7.getPlayerStorage().minus(var7.getLockedSlots()))
         .getItemArea((AbstractContainerMenu)var5, var8)
         .getSlotIndices();
      var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));

      for (Object var10 : var21) {
         int var34 = ((Number)var10).intValue();
         ArrayList var48 = var9;
         IndexedValue var52 = new IndexedValue;
         int var10002 = var34 - 9;
         net.minecraft.world.item.ItemStack var10003 = ((Slot)var8.get(var34)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         net.minecraft.world.item.ItemStack var3 = var10003;
         ItemStack var79;
         if (var10003.isEmpty()) {
            var79 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var80 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var4 = var3;
            Item var10006 = var4.getItem();
            Intrinsics.checkNotNullExpressionValue(var10006, "");
            DataComponentMap var10007 = var4.getComponents();
            Intrinsics.checkNotNull(var10007);
            var79 = var80.invoke(
               new ItemType(
                  var10006,
                  (PatchedDataComponentMap)var10007,
                  var4.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var4),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var3.getCount()
            );
         }

         var52./* $VF: Unable to resugar constructor */<init>(var10002, var79);
         var48.add(var52);
      }

      Sequence var16 = CollectionsKt.asSequence(var9);
      ItemType var20;
      ItemType var35 = var20 = var1.getItemType();
      net.minecraft.world.item.ItemStack var53 = new net.minecraft.world.item.ItemStack;
      ItemLike var66 = (ItemLike)var35.getItem();
      PatchedDataComponentMap var10004 = var35.getTag();
      if (var10004 == null) {
         DataComponentMap var81 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var81;
      }

      var53./* $VF: Unable to resugar constructor */<init>(var66, 1, var10004);
      if (var53.isDamageableItem()) {
         int var23 = AutoRefillSettings.INSTANCE.getREFILL_BEFORE_TOOL_BREAK().getBooleanValue() ? this.getThreshold(var20) : -1;
         var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$4);
         DefaultedRegistry var10001 = BuiltInRegistries.ITEM;
         Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
         Holder var11 = this.getEntry(var10001, var20.getItem());
         TagKey var67 = ItemTags.LEG_ARMOR;
         Intrinsics.checkNotNullExpressionValue(ItemTags.LEG_ARMOR, "");
         if (this.isIn(var11, var67)) {
            var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$5);
         } else {
            TagKey var68 = ItemTags.FOOT_ARMOR;
            Intrinsics.checkNotNullExpressionValue(ItemTags.FOOT_ARMOR, "");
            if (this.isIn(var11, var68)) {
               var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$6);
            } else {
               TagKey var69 = ItemTags.HEAD_ARMOR;
               Intrinsics.checkNotNullExpressionValue(ItemTags.HEAD_ARMOR, "");
               if (this.isIn(var11, var69)) {
                  var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$7);
               } else {
                  TagKey var70 = ItemTags.CHEST_ARMOR;
                  Intrinsics.checkNotNullExpressionValue(ItemTags.CHEST_ARMOR, "");
                  if (this.isIn(var11, var70)) {
                     var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$8);
                  } else {
                     TagKey var71 = ItemTags.SWORDS;
                     Intrinsics.checkNotNullExpressionValue(ItemTags.SWORDS, "");
                     if (this.isIn(var11, var71)) {
                        var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$9);
                     } else {
                        TagKey var72 = ItemTags.SHOVELS;
                        Intrinsics.checkNotNullExpressionValue(ItemTags.SHOVELS, "");
                        if (this.isIn(var11, var72)) {
                           var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$10);
                        } else {
                           TagKey var73 = ItemTags.PICKAXES;
                           Intrinsics.checkNotNullExpressionValue(ItemTags.PICKAXES, "");
                           if (this.isIn(var11, var73)) {
                              var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$11);
                           } else {
                              TagKey var74 = ItemTags.AXES;
                              Intrinsics.checkNotNullExpressionValue(ItemTags.AXES, "");
                              if (this.isIn(var11, var74)) {
                                 var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$12);
                              } else {
                                 TagKey var75 = ItemTags.HOES;
                                 Intrinsics.checkNotNullExpressionValue(ItemTags.HOES, "");
                                 if (this.isIn(var11, var75)) {
                                    var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$13);
                                 } else if (var20.getItem() instanceof FishingRodItem) {
                                    var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$14);
                                 } else {
                                    var16 = this.defaultItemMatch(var16, var20);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         label137: {
            PatchedDataComponentMap var54 = var1.getItemType().getTag();
            if (var54 != null) {
               PotionContents var55 = (PotionContents)var54.get(DataComponents.POTION_CONTENTS);
               if (var55 != null) {
                  var56 = var55.hasEffects();
                  break label137;
               }
            }

            var56 = false;
         }

         if (var56) {
            label131: {
               PatchedDataComponentMap var57 = var1.getItemType().getTag();
               if (var57 != null) {
                  PotionContents var58 = (PotionContents)var57.get(DataComponents.POTION_CONTENTS);
                  if (var58 != null) {
                     Iterable var59 = var58.getAllEffects();
                     if (var59 != null) {
                        var60 = CollectionsKt.toList(var59);
                        if (var60 != null) {
                           break label131;
                        }
                     }
                  }
               }

               var60 = CollectionsKt.emptyList();
            }

            Iterable var40;
            Iterable var24 = var40 = var60;
            var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var40, 10));

            for (Object var49 : var24) {
               MobEffectInstance var36 = (MobEffectInstance)var49;
               ArrayList var50 = var9;
               var50.add(
                  new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var36.getEffect().value())), var36.getAmplifier(), var36.getDuration())
               );
            }

            Iterable var12;
            Iterable var41 = var12 = var9;
            var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));

            for (Object var13 : var41) {
               PotionEffect var26 = (PotionEffect)var13;
               ArrayList var14 = var8;
               var14.add(var26.getEffect());
            }

            List var27 = var8;
            var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$17);
         } else {
            var16 = this.defaultItemMatch(var16, var20);
            ItemType var37 = var1.getItemType();
            net.minecraft.world.item.ItemStack var61 = new net.minecraft.world.item.ItemStack;
            var66 = (ItemLike)var37.getItem();
            var10004 = var37.getTag();
            if (var10004 == null) {
               DataComponentMap var83 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10004 = (PatchedDataComponentMap)var83;
            }

            var61./* $VF: Unable to resugar constructor */<init>(var66, 1, var10004);
            if (var61.isStackable() && Intrinsics.areEqual(var1.getItemType().getItem(), var2.getItemType().getItem())) {
               var16 = SequencesKt.filter(var16, AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$18);
            }
         }
      }

      Comparator var28 = AutoRefillHandler$ItemSlotMonitor$Companion::findCorrespondingSlot$lambda$20;
      Comparator var29 = new AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$1(var28);
      Comparator var30 = new AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$2(var29);
      Comparator var31 = new AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$3(var30);
      Sequence var19;
      Iterable var32 = SequencesKt.toHashSet(
         var19 = SequencesKt.sortedWith(var16, new AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$4(var31))
      );
      int var38 = 0;

      for (Object var44 : var32) {
         int var33;
         if ((var33 = var38++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         IndexedValue var47 = (IndexedValue)var44;
         int var15 = var33;
         Log var63 = Log.INSTANCE;
         ItemType var51 = ((ItemStack)var47.getValue()).getItemType();
         ResourceLocation var77 = BuiltInRegistries.ITEM.getKey(var51.getItem());
         Intrinsics.checkNotNullExpressionValue(var77, "");
         String var78 = var77.toString();
         Intrinsics.checkNotNullExpressionValue(var78, "");
         var63.trace("refill candidate " + var15 + " -> " + var78);
      }

      IndexedValue var64 = (IndexedValue)SequencesKt.firstOrNull(var19);
      Integer var39;
      Integer var65 = (var39 = var64 != null ? var64.getIndex() : -1).intValue() >= 0 ? var39 : null;
      return var65 != null ? var65 + 9 : null;
   }

   private final Sequence defaultItemMatch(Sequence var1, ItemType var2) {
      Iterator var3 = var1.iterator();

      Object var10000;
      while (true) {
         if (var3.hasNext()) {
            Object var4;
            IndexedValue var5 = (IndexedValue)(var4 = var3.next());
            if (!AutoRefillHandler$ItemSlotMonitor.Companion.typeItemMatch(var5, var2)) {
               continue;
            }

            var10000 = (Iterable)var4;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 != null) {
         return SequencesKt.filter(var1, AutoRefillHandler$ItemSlotMonitor$Companion::defaultItemMatch$lambda$28);
      }

      var3 = var1.iterator();

      while (true) {
         if (!var3.hasNext()) {
            var10000 = null;
            break;
         }

         Object var12;
         label106: {
            ItemType var13 = ((ItemStack)((IndexedValue)(var12 = var3.next())).getValue()).getItemType();
            boolean var6 = AutoRefillSettings.INSTANCE.getAUTO_REFILL_MATCH_HARMFUL_FOOD().getBooleanValue();
            if (AutoRefillSettings.INSTANCE.getAUTO_REFILL_MATCH_ANY_FOOD().getBooleanValue()
               && var2.getItem().components().get(DataComponents.FOOD) != null
               && var13.getItem().components().get(DataComponents.FOOD) != null) {
               var10000 = (FoodProperties)var13.getItem().components().get(DataComponents.FOOD);
               if (var10000 == null) {
                  throw new IllegalStateException("this shouldn't happen".toString());
               }

               FoodProperties var14 = (FoodProperties)var10000;
               boolean var7 = false;
               List var8;
               List var9 = var8 = new ArrayList();
               var10000 = var14.effects();
               Intrinsics.checkNotNullExpressionValue(var10000, "");

               for (PossibleEffect var10 : var10000) {
                  var9.add(new Pair(var10.effect(), var10.probability()));
               }

               Iterator var16 = var8.iterator();

               while (true) {
                  if (var16.hasNext()) {
                     if (((MobEffect)((MobEffectInstance)((Pair)var16.next()).getFirst()).getEffect().value()).getCategory() != MobEffectCategory.HARMFUL) {
                        continue;
                     }

                     var7 = true;
                  }

                  if (!var7 || var6) {
                     var10000 = true;
                     break label106;
                  }
                  break;
               }
            }

            var10000 = false;
         }

         if (var10000) {
            var10000 = var12;
            break;
         }
      }

      return var10000 != null ? SequencesKt.filter(var1, AutoRefillHandler$ItemSlotMonitor$Companion::defaultItemMatch$lambda$30) : SequencesKt.emptySequence();
   }

   private final boolean typeItemMatch(IndexedValue var1, ItemType var2) {
      ItemType var4 = var2;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var4.getItem();
      PatchedDataComponentMap var10004 = var4.getTag();
      if (var10004 == null) {
         DataComponentMap var21 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var21;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) == null) {
         var4 = ((ItemStack)var1.getValue()).getItemType();
         var10000 = new net.minecraft.world.item.ItemStack;
         var10002 = (ItemLike)var4.getItem();
         var10004 = var4.getTag();
         if (var10004 == null) {
            DataComponentMap var23 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var23;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) == null) {
            return Intrinsics.areEqual(((ItemStack)var1.getValue()).getItemType().getItem(), var2.getItem()) && this.checkNBTIfNeeded(var1, var2);
         }
      }

      if (AutoRefillSettings.INSTANCE.getAUTO_REFILL_MATCH_CUSTOM_NAME().getBooleanValue()) {
         if (Intrinsics.areEqual(((ItemStack)var1.getValue()).getItemType().getItem(), var2.getItem())) {
            ItemType var3;
            var4 = var3 = ((ItemStack)var1.getValue()).getItemType();
            var10000 = new net.minecraft.world.item.ItemStack;
            var10002 = (ItemLike)var4.getItem();
            var10004 = var4.getTag();
            if (var10004 == null) {
               DataComponentMap var25 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10004 = (PatchedDataComponentMap)var25;
            }

            var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
            String var14;
            if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
               var4 = var3;
               var10000 = new net.minecraft.world.item.ItemStack;
               var10002 = (ItemLike)var4.getItem();
               var10004 = var4.getTag();
               if (var10004 == null) {
                  DataComponentMap var27 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10004 = (PatchedDataComponentMap)var27;
               }

               var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
               var14 = var10000.getDisplayName().getString();
               Intrinsics.checkNotNullExpressionValue(var14, "");
            } else {
               var14 = "";
            }

            var3 = var2;
            var4 = var2;
            net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
            ItemLike var10003 = (ItemLike)var4.getItem();
            PatchedDataComponentMap var10005 = var4.getTag();
            if (var10005 == null) {
               DataComponentMap var28 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10005 = (PatchedDataComponentMap)var28;
            }

            var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
            String var16;
            if (var10001.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
               var4 = var3;
               var10001 = new net.minecraft.world.item.ItemStack;
               var10003 = (ItemLike)var4.getItem();
               var10005 = var4.getTag();
               if (var10005 == null) {
                  DataComponentMap var30 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10005 = (PatchedDataComponentMap)var30;
               }

               var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
               var16 = var10001.getDisplayName().getString();
               Intrinsics.checkNotNullExpressionValue(var16, "");
            } else {
               var16 = "";
            }

            if (Intrinsics.areEqual(var14, var16) && this.checkNBTIfNeeded(var1, var2)) {
               return true;
            }
         }

         return false;
      } else {
         return Intrinsics.areEqual(((ItemStack)var1.getValue()).getItemType().getItem(), var2.getItem()) && this.checkNBTIfNeeded(var1, var2);
      }
   }

   private final boolean checkNBTIfNeeded(IndexedValue var1, ItemType var2) {
      if (!AutoRefillSettings.INSTANCE.getAUTO_REFILL_MATCH_NBT().getBooleanValue()) {
         return true;
      }

      Predicate var3 = AutoRefillHandler$ItemSlotMonitor$Companion::checkNBTIfNeeded$lambda$31;
      ItemType var4 = var2;
      if (var2.getItem() instanceof BucketItem || var4.getItem() instanceof MilkBucketItem || var4.getItem() instanceof SolidBucketItem) {
         var4 = var2;
         if (!(var2.getItem() instanceof BucketItem) && !(var4.getItem() instanceof MilkBucketItem) && !(var4.getItem() instanceof SolidBucketItem)
            || AutoRefillSettings.INSTANCE.getAUTO_REFILL_IGNORE_NBT_FOR_BUCKETS().getBooleanValue()) {
            return true;
         }
      }

      switch ((AutoRefillNbtMatchType)AutoRefillSettings.INSTANCE.getAUTO_REFILL_MATCH_NBT_TYPE().getValue()) {
         case CAN_HAVE_EXTRA:
            DataComponentPatch var8 = ComponentUtils.INSTANCE._withRemovedIf_/* $VF was: (withRemovedIf) */(var2.getChanges(), var3);
            DataComponentPatch var6 = ComponentUtils.INSTANCE
               ._withRemovedIf_/* $VF was: (withRemovedIf) */(((ItemStack)var1.getValue()).getItemType().getChanges(), var3);
            boolean var10 = var8.isEmpty() && var6.isEmpty();
            if (!var8.isEmpty() && !var6.isEmpty()) {
               var10 = true;
               Set var10000 = var8.entrySet();
               Intrinsics.checkNotNullExpressionValue(var10000, "");
               Iterator var9 = var10000.iterator();

               while (var9.hasNext()) {
                  Entry var12;
                  Intrinsics.checkNotNull(var12 = (Entry)var9.next());
                  DataComponentType var5 = (DataComponentType)var12.getKey();
                  if (!Intrinsics.areEqual((Optional)var12.getValue(), var6.get(var5))) {
                     var10 = false;
                     break;
                  }
               }
            }

            return var10;
         case EXACT:
            DataComponentPatch var7 = ComponentUtils.INSTANCE._withRemovedIf_/* $VF was: (withRemovedIf) */(var2.getChanges(), var3);
            return Intrinsics.areEqual(
               ComponentUtils.INSTANCE._withRemovedIf_/* $VF was: (withRemovedIf) */(((ItemStack)var1.getValue()).getItemType().getChanges(), var3), var7
            );
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   private final int getThreshold(ItemType var1) {
      ItemType var2 = var1;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var5 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var5;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      if (!var10000.isDamageableItem()) {
         return 0;
      }

      return RangesKt.coerceAtLeast(switch ((ThresholdUnit)AutoRefillSettings.INSTANCE.getTHRESHOLD_UNIT().getValue()) {
         case ABSOLUTE -> AutoRefillSettings.INSTANCE.getTOOL_DAMAGE_THRESHOLD().getIntegerValue();
         case PERCENTAGE -> {
            int var3 = AutoRefillSettings.INSTANCE.getTOOL_DAMAGE_THRESHOLD().getIntegerValue();
            net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
            ItemLike var10003 = (ItemLike)var1.getItem();
            PatchedDataComponentMap var10005 = var1.getTag();
            if (var10005 == null) {
               DataComponentMap var6 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10005 = (PatchedDataComponentMap)var6;
            }

            var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
            yield var3 * var10001.getMaxDamage() / 100;
         }
         default -> throw new NoWhenBranchMatchedException();
      }, 0);
   }

   @NotNull
   @Override
   public final Holder getEntry(@NotNull DefaultedRegistry var1, Object var2) {
      return SpecificItemSlotMonitor$DefaultImpls.getEntry(this, var1, var2);
   }

   @Override
   public final boolean isIn(@NotNull Holder var1, @NotNull TagKey var2) {
      return SpecificItemSlotMonitor$DefaultImpls.isIn(this, var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$4(int var0, IndexedValue var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var3;
      if (!ItemTypeExtensionsKt.isEmpty(var3 = ((ItemStack)var1.getValue()).getItemType())) {
         ItemType var2 = var3;
         net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
         ItemLike var10002 = (ItemLike)var2.getItem();
         PatchedDataComponentMap var10004 = var2.getTag();
         if (var10004 == null) {
            DataComponentMap var10 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var10;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         if (var10000.isDamageableItem()) {
            var2 = var3;
            ItemType var4 = var3;
            var10000 = new net.minecraft.world.item.ItemStack;
            var10002 = (ItemLike)var4.getItem();
            var10004 = var4.getTag();
            if (var10004 == null) {
               DataComponentMap var12 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10004 = (PatchedDataComponentMap)var12;
            }

            var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
            int var8 = var10000.getMaxDamage();
            ItemType var5 = var2;
            net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
            ItemLike var10003 = (ItemLike)var5.getItem();
            PatchedDataComponentMap var10005 = var5.getTag();
            if (var10005 == null) {
               DataComponentMap var13 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10005 = (PatchedDataComponentMap)var13;
            }

            var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
            if (var8 - var10001.getDamageValue() > var0) {
               return true;
            }
         }
      }

      return false;
   }

   private static final boolean findCorrespondingSlot$lambda$5(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.LEG_ARMOR;
      Intrinsics.checkNotNullExpressionValue(ItemTags.LEG_ARMOR, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$6(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.FOOT_ARMOR;
      Intrinsics.checkNotNullExpressionValue(ItemTags.FOOT_ARMOR, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$7(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.HEAD_ARMOR;
      Intrinsics.checkNotNullExpressionValue(ItemTags.HEAD_ARMOR, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$8(ItemType var0, IndexedValue var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (ItemTypeExtensionsKt.isEmpty(var0)) {
         return false;
      }

      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var2 = var10001.getEntry(var10002, ((ItemStack)var1.getValue()).getItemType().getItem());
      TagKey var3 = ItemTags.CHEST_ARMOR;
      Intrinsics.checkNotNullExpressionValue(ItemTags.CHEST_ARMOR, "");
      return var10000.isIn(var2, var3);
   }

   private static final boolean findCorrespondingSlot$lambda$9(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.SWORDS;
      Intrinsics.checkNotNullExpressionValue(ItemTags.SWORDS, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$10(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.SHOVELS;
      Intrinsics.checkNotNullExpressionValue(ItemTags.SHOVELS, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$11(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.PICKAXES;
      Intrinsics.checkNotNullExpressionValue(ItemTags.PICKAXES, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$12(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.AXES;
      Intrinsics.checkNotNullExpressionValue(ItemTags.AXES, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$13(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AutoRefillHandler$ItemSlotMonitor$Companion var10000 = AutoRefillHandler$ItemSlotMonitor.Companion;
      AutoRefillHandler$ItemSlotMonitor$Companion var10001 = AutoRefillHandler$ItemSlotMonitor.Companion;
      DefaultedRegistry var10002 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      Holder var1 = var10001.getEntry(var10002, ((ItemStack)var0.getValue()).getItemType().getItem());
      TagKey var2 = ItemTags.HOES;
      Intrinsics.checkNotNullExpressionValue(ItemTags.HOES, "");
      return var10000.isIn(var1, var2);
   }

   private static final boolean findCorrespondingSlot$lambda$14(IndexedValue var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return ((ItemStack)var0.getValue()).getItemType().getItem() instanceof FishingRodItem;
   }

   private static final boolean findCorrespondingSlot$lambda$17(List var0, IndexedValue var1) {
      Iterable var18;
      label33: {
         Intrinsics.checkNotNullParameter(var1, "");
         PatchedDataComponentMap var10000 = ((ItemStack)var1.getValue()).getItemType().getTag();
         if (var10000 != null) {
            PotionContents var16 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
            if (var16 != null) {
               var18 = var16.getAllEffects();
               if (var18 != null) {
                  var18 = CollectionsKt.toList(var18);
                  if (var18 != null) {
                     break label33;
                  }
               }
            }
         }

         var18 = CollectionsKt.emptyList();
      }

      Iterable var2;
      Iterable var5 = var2 = var18;
      var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

      for (Object var6 : var5) {
         MobEffectInstance var7 = (MobEffectInstance)var6;
         ArrayList var4 = var2;
         var4.add(new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var7.getEffect().value())), var7.getAmplifier(), var7.getDuration()));
      }

      Iterable var8;
      var2 = var8 = (Iterable & List)var2;
      var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));

      for (PotionEffect var13 : var2) {
         var13 = var13;
         ArrayList var15 = var9;
         var15.add(var13.getEffect());
      }

      return var9.containsAll(var0);
   }

   private static final boolean findCorrespondingSlot$lambda$18(ItemStack var0, IndexedValue var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return ((ItemStack)var1.getValue()).getCount() > var0.getCount();
   }

   private static final int findCorrespondingSlot$lambda$20(ItemType var0, IndexedValue var1, IndexedValue var2) {
      ItemType var5 = ((ItemStack)var1.getValue()).getItemType();
      ItemType var7 = ((ItemStack)var2.getValue()).getItemType();
      Match var3 = Match.FIRST;
      boolean var4 = Intrinsics.areEqual(var5.getItem(), var0.getItem());
      boolean var6 = Intrinsics.areEqual(var7.getItem(), var0.getItem());
      return var4 == var6 ? 0 : var3.getMultiplier() * (var4 ? -1 : 1);
   }

   private static final boolean defaultItemMatch$lambda$28(ItemType var0, IndexedValue var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return AutoRefillHandler$ItemSlotMonitor.Companion.typeItemMatch(var1, var0);
   }

   private static final boolean defaultItemMatch$lambda$30(ItemType var0, IndexedValue var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var8 = ((ItemStack)var1.getValue()).getItemType();
      boolean var2 = AutoRefillSettings.INSTANCE.getAUTO_REFILL_MATCH_HARMFUL_FOOD().getBooleanValue();
      if (var0.getItem().components().get(DataComponents.FOOD) != null && var8.getItem().components().get(DataComponents.FOOD) != null) {
         FoodProperties var10000 = (FoodProperties)var8.getItem().components().get(DataComponents.FOOD);
         if (var10000 == null) {
            throw new IllegalStateException("this shouldn't happen".toString());
         }

         FoodProperties var6 = var10000;
         boolean var9 = false;
         List var3;
         List var4 = var3 = new ArrayList();
         List var11 = var6.effects();
         Intrinsics.checkNotNullExpressionValue(var11, "");

         for (PossibleEffect var5 : var11) {
            var4.add(new Pair(var5.effect(), var5.probability()));
         }

         Iterator var10 = var3.iterator();

         while (true) {
            if (var10.hasNext()) {
               if (((MobEffect)((MobEffectInstance)((Pair)var10.next()).getFirst()).getEffect().value()).getCategory() != MobEffectCategory.HARMFUL) {
                  continue;
               }

               var9 = true;
            }

            if (!var9 || var2) {
               return true;
            }
            break;
         }
      }

      return false;
   }

   private static final boolean checkNBTIfNeeded$lambda$31(DataComponentType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return Intrinsics.areEqual(var0, DataComponents.DAMAGE);
   }
}
