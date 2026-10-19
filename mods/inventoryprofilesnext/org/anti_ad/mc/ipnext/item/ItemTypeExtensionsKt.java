package org.anti_ad.mc.ipnext.item;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.CreativeModeTab.Type;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.mixinhelpers.IMixinItemGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemTypeExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,494:1\n131#1:495\n112#1:496\n131#1:499\n112#1:500\n106#1:501\n112#1:502\n106#1:503\n123#1:504\n106#1:505\n127#1:506\n106#1:507\n106#1:508\n133#1:509\n106#1:510\n112#1:511\n106#1:512\n106#1:513\n123#1:514\n106#1:515\n127#1:516\n106#1:517\n133#1:518\n106#1:519\n131#1:532\n112#1:533\n102#1:534\n112#1:541\n269#1:542\n280#1,2:545\n282#1,2:548\n284#1:551\n106#1:554\n106#1:555\n106#1:556\n106#1:560\n106#1:565\n106#1:566\n356#1:567\n106#1:568\n341#1:569\n106#1:570\n276#1:571\n106#1:572\n306#1:573\n402#1:574\n414#1:575\n421#1,3:579\n1869#2,2:497\n346#2,4:520\n350#2,4:528\n774#2:535\n865#2,2:536\n1878#2,3:538\n1869#2,2:543\n1869#2:547\n1870#2:550\n1869#2,2:552\n1803#2,3:557\n1267#2,4:561\n1563#2:576\n1634#2,2:577\n1636#2:582\n66#3,4:524\n1#4:583\n*S KotlinDebug\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n76#1:495\n76#1:496\n84#1:499\n84#1:500\n98#1:501\n115#1:502\n123#1:503\n125#1:504\n125#1:505\n125#1:506\n125#1:507\n127#1:508\n129#1:509\n129#1:510\n131#1:511\n133#1:512\n135#1:513\n138#1:514\n138#1:515\n138#1:516\n138#1:517\n138#1:518\n138#1:519\n232#1:532\n232#1:533\n239#1:534\n257#1:541\n272#1:542\n290#1:545,2\n290#1:548,2\n290#1:551\n332#1:554\n341#1:555\n344#1:556\n349#1:560\n354#1:565\n356#1:566\n358#1:567\n358#1:568\n358#1:569\n358#1:570\n397#1:571\n397#1:572\n397#1:573\n405#1:574\n417#1:575\n417#1:579,3\n77#1:497,2\n228#1:520,4\n228#1:528,4\n242#1:535\n242#1:536,2\n248#1:538,3\n281#1:543,2\n290#1:547\n290#1:550\n322#1:552,2\n344#1:557,3\n349#1:561,4\n417#1:576\n417#1:577,2\n417#1:582\n229#1:524,4\n*E\n"
)
public final class ItemTypeExtensionsKt {
   @NotNull
   private static final Codec COMPONENTS_CHANGES_CODEC;

   @NotNull
   public static final String fullItemInfoAsJson(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var2 = var0;
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var2.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var5 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var5, "");
      String var1 = "{\n\t\"id\" : \"" + var5 + "\",\n";
      PatchedDataComponentMap var6 = var0.getTag();
      if (var6 != null) {
         Set var7 = var6.keySet();
         if (var7 != null) {
            for (DataComponentType var3 : var7) {
               var1 = var1 + "\t{\n\t\t\"" + var3 + "\" : " + var0.getTag().get(var3) + "\n},";
            }
         }
      }

      return var1 + "\n}";
   }

   @NotNull
   public static final String toNamespacedString(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var1 = var0;
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var1.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Object var2 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var2, "");
      Object var10001 = var0.getTag();
      if (var10001 == null) {
         var10001 = "";
      }

      return var2 + var10001;
   }

   @NotNull
   public static final ItemType getEMPTY(@NotNull ItemType$Companion var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Item var10002 = Items.AIR;
      Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
      return new ItemType(
         var10002,
         new PatchedDataComponentMap(Items.AIR.components()),
         DataComponentPatch.EMPTY,
         ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
         false,
         false,
         null,
         112,
         null
      );
   }

   public static final boolean isEmpty(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return Intrinsics.areEqual(var0.getItem(), Items.AIR);
   }

   public static final int getMaxCount(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getMaxStackSize();
   }

   @NotNull
   public static final net.minecraft.world.item.ItemStack getSearchItemStack(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return new net.minecraft.world.item.ItemStack((ItemLike)var0.getItem(), 1);
   }

   @NotNull
   public static final net.minecraft.world.item.ItemStack getVanillaStack(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000;
   }

   @NotNull
   public static final net.minecraft.world.item.ItemStack vanillaStackWithCount(@NotNull ItemType var0, int var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var2;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, var1, var10004);
      return var10000;
   }

   @NotNull
   public static final ResourceLocation getIdentifier(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var0.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public static final String getNamespace(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var0.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var1 = var10000.getNamespace();
      Intrinsics.checkNotNullExpressionValue(var1, "");
      return var1;
   }

   @NotNull
   public static final Class getItemClass(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getItem().getClass();
   }

   public static final boolean getHasCustomName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null;
   }

   @NotNull
   public static final String getCustomName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var1 = var0;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var6 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var6;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         var1 = var0;
         var10000 = new net.minecraft.world.item.ItemStack;
         var10002 = (ItemLike)var1.getItem();
         var10004 = var1.getTag();
         if (var10004 == null) {
            DataComponentMap var8 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var8;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         String var4 = var10000.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var4, "");
         return var4;
      } else {
         return "";
      }
   }

   @NotNull
   public static final String getDisplayName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var2;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      String var1 = var10000.getDisplayName().getString();
      Intrinsics.checkNotNullExpressionValue(var1, "");
      return var1;
   }

   @NotNull
   public static final String getTranslatedName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      I18n var10000 = I18n.INSTANCE;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10005 = var0.getTag();
      if (var10005 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var2;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var1 = var10001.getDescriptionId();
      Intrinsics.checkNotNullExpressionValue(var1, "");
      return var10000.translate(var1, new Object[0]);
   }

   @NotNull
   public static final String getItemId(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var0.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var1 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "");
      return var1;
   }

   @NotNull
   public static final String getTranslationKey(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var2;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      String var1 = var10000.getDescriptionId();
      Intrinsics.checkNotNullExpressionValue(var1, "");
      return var1;
   }

   public static final boolean isStackable(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.isStackable();
   }

   @NotNull
   public static final String getCustomOrTranslatedName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var1 = var0;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var9 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var9;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         var1 = var0;
         var10000 = new net.minecraft.world.item.ItemStack;
         var10002 = (ItemLike)var1.getItem();
         var10004 = var1.getTag();
         if (var10004 == null) {
            DataComponentMap var11 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var11;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         String var6 = var10000.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var6, "");
         return var6;
      } else {
         I18n var4 = I18n.INSTANCE;
         var1 = var0;
         net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var1.getItem();
         PatchedDataComponentMap var10005 = var1.getTag();
         if (var10005 == null) {
            DataComponentMap var12 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var12;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         String var7 = var10001.getDescriptionId();
         Intrinsics.checkNotNullExpressionValue(var7, "");
         return var4.translate(var7, new Object[0]);
      }
   }

   public static final int getPriorityIndex(@NotNull CreativeModeTab var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (var0 instanceof IMixinItemGroup) {
         int var1;
         ComponentContents var2;
         if ((var1 = ((IMixinItemGroup)var0).getIPNPriorityIndex()) == -1 && (var2 = var0.getDisplayName().getContents()) instanceof TranslatableContents) {
            var1 = ItemTypeExtensionsObject.INSTANCE.getTranslationKeysPriorityList().indexOf(((TranslatableContents)var2).getKey());
            ((IMixinItemGroup)var0).setIPNPriorityIndex(var1 == -1 ? Integer.MAX_VALUE : var1);
         }

         return var1;
      } else {
         return Integer.MAX_VALUE;
      }
   }

   private static final boolean initGroupIndex() {
      FeatureFlagSet var0;
      RegistryAccess var7;
      label36: {
         LocalPlayer var10000 = Vanilla.INSTANCE.mc().player;
         var0 = var10000 != null && var10000.connection != null ? var10000.connection.enabledFeatures() : null;
         LocalPlayer var5 = Vanilla.INSTANCE.mc().player;
         if (var5 != null) {
            Level var6 = var5.level();
            if (var6 != null) {
               var7 = var6.registryAccess();
               break label36;
            }
         }

         var7 = null;
      }

      RegistryAccess var1;
      label31: {
         var1 = var7;
         if ((Boolean)Vanilla.INSTANCE.mc().options.operatorItemsTab().get()) {
            LocalPlayer var8 = Vanilla.INSTANCE.playerNullable();
            if (var8 != null ? var8.canUseGameMasterBlocks() : false) {
               var9 = true;
               break label31;
            }
         }

         var9 = false;
      }

      boolean var2 = var9;
      if (var0 != null && var1 != null) {
         CreativeModeTabs.tryRebuildTabContents(var0, var2, (Provider)var1);
         return true;
      } else {
         return false;
      }
   }

   public static final int getSearchTabIndex(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      initGroupIndex();
      Collection var10000 = CreativeModeTabs.searchTab().getSearchTabDisplayItems();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterable var2 = var10000;
      int var3 = 0;
      Iterator var1 = var2.iterator();

      while (true) {
         if (!var1.hasNext()) {
            var9 = -1;
            break;
         }

         net.minecraft.world.item.ItemStack var5 = (net.minecraft.world.item.ItemStack)var1.next();
         if (var3 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         label28: {
            if ((var5 = var5) != null) {
               Item var10002 = var5.getItem();
               Intrinsics.checkNotNullExpressionValue(var10002, "");
               DataComponentMap var10003 = var5.getComponents();
               Intrinsics.checkNotNull(var10003);
               if (Intrinsics.areEqual(
                  new ItemType(
                        var10002,
                        (PatchedDataComponentMap)var10003,
                        var5.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var5),
                        false,
                        false,
                        null,
                        112,
                        null
                     )
                     .getItem(),
                  var0.getItem()
               )) {
                  var8 = true;
                  break label28;
               }
            }

            var8 = false;
         }

         if (var8) {
            var9 = var3;
            break;
         }

         var3++;
      }

      int var4 = var9;
      if (var9 == -1) {
         Log var10 = Log.INSTANCE;
         ItemType var7 = var0;
         ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var7.getItem());
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         String var11 = var10001.toString();
         Intrinsics.checkNotNullExpressionValue(var11, "");
         var10.warn("Item " + var11 + " not present in the Creative Inventory Search Tab!");
      }

      return var4;
   }

   public static final int getGroupIndex(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var2 = var0;
      net.minecraft.world.item.ItemStack var1 = new net.minecraft.world.item.ItemStack((ItemLike)var2.getItem(), 1);
      initGroupIndex();
      int var8 = Integer.MAX_VALUE;
      List var10000 = CreativeModeTabs.allTabs();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      List var3 = var10000;
      var var4 = new ArrayList();
      Iterator var5 = var3.iterator();

      while (var5.hasNext()) {
         Object var6;
         Type var17 = ((CreativeModeTab)(var6 = var5.next())).getType();
         if (var17 == null) {
            var17 = Type.INVENTORY;
         }

         if (var17 == Type.CATEGORY) {
            var4.add(var6);
         }
      }

      Iterable var11 = var3 = CollectionsKt.sortedWith(var4, new ItemGroupComparator());
      int var14 = 0;

      for (CreativeModeTab var7 : var11) {
         int var12;
         if ((var12 = var14++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var7 = var7;
         int var13 = var12;
         if (var7.contains(var1)) {
            var8 = var13;
            break;
         }
      }

      if (var8 >= 0 ? var8 < var3.size() : false) {
         CreativeModeTab var10 = (CreativeModeTab)var3.get(var8);
         Log var19 = Log.INSTANCE;
         ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var0.getItem());
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         var19.trace(var10001 + " - > " + var8 + " -> " + (var10 != null ? var10.getDisplayName() : null));
      }

      return var8;
   }

   @NotNull
   public static final FoodProperties get_foodComponent_/* $VF was: get(foodComponent) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return (FoodProperties)var0.getItem().components().get(DataComponents.FOOD);
   }

   @NotNull
   public static final FoodProperties get_consumableComponent_/* $VF was: get(consumableComponent) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return (FoodProperties)var0.getItem().components().get(DataComponents.FOOD);
   }

   public static final boolean get_isFood_/* $VF was: get(isFood) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getItem().components().get(DataComponents.FOOD) != null;
   }

   @NotNull
   public static final List get_statusEffects_/* $VF was: get(statusEffects) */(@NotNull FoodProperties var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      List var1;
      List var2 = var1 = new ArrayList();
      List var10000 = var0.effects();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (PossibleEffect var3 : var10000) {
         var2.add(new Pair(var3.effect(), var3.probability()));
      }

      return var1;
   }

   public static final boolean get_isHarmful_/* $VF was: get(isHarmful) */(@NotNull FoodProperties var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      boolean var1 = false;
      List var2;
      List var3 = var2 = new ArrayList();
      List var10000 = var0.effects();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (PossibleEffect var4 : var10000) {
         var3.add(new Pair(var4.effect(), var4.probability()));
      }

      Iterator var6 = var2.iterator();

      while (var6.hasNext()) {
         if (((MobEffect)((MobEffectInstance)((Pair)var6.next()).getFirst()).getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
            var1 = true;
            break;
         }
      }

      return var1;
   }

   public static final float get_saturationModifier_/* $VF was: get(saturationModifier) */(@NotNull FoodProperties var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.saturation();
   }

   public static final boolean get_convertsToBawl_/* $VF was: get(convertsToBawl) */(@NotNull FoodProperties var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.usingConvertsTo().isPresent()
         && Intrinsics.areEqual(((net.minecraft.world.item.ItemStack)var0.usingConvertsTo().get()).getItem(), Items.BOWL);
   }

   @NotNull
   public static final Codec getCOMPONENTS_CHANGES_CODEC() {
      return COMPONENTS_CHANGES_CODEC;
   }

   @NotNull
   public static final List _componentsToCodecList_/* $VF was: (componentsToCodecList) */(@NotNull net.minecraft.world.item.ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      List var1 = new ArrayList();
      if (!var0.getComponentsPatch().isEmpty()) {
         Set var10000 = var0.getComponentsPatch().entrySet();
         Intrinsics.checkNotNullExpressionValue(var10000, "");

         for (Entry var2 : var10000) {
            Codec var10001 = ((DataComponentType)var2.getKey()).codecOrThrow();
            Intrinsics.checkNotNullExpressionValue(var10001, "");
            var1.add(var10001);
         }
      }

      return var1;
   }

   @Nullable
   public static final Tag get_componentsToNbt_/* $VF was: get(componentsToNbt) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (!isEmpty(var0)) {
         Codec var10000 = getCOMPONENTS_CHANGES_CODEC();
         DynamicOps var10001 = (DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
         net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
         ItemLike var10004 = (ItemLike)var0.getItem();
         PatchedDataComponentMap var10006 = var0.getTag();
         if (var10006 == null) {
            DataComponentMap var1 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10006 = (PatchedDataComponentMap)var1;
         }

         var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
         return (Tag)var10000.encodeStart(var10001, var10002).getOrThrow();
      } else {
         return null;
      }
   }

   public static final int getRawId(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      DefaultedRegistry var10000 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      return VanillaAccessorsKt._getRawId_/* $VF was: (getRawId) */(var10000, var0.getItem());
   }

   public static final int getDamage(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getDamageValue();
   }

   public static final double get_enchantmentsScore_/* $VF was: get(enchantmentsScore) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var12 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var12;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      Set var11 = EnchantmentHelper.getEnchantmentsForCrafting(var10000).entrySet();
      Intrinsics.checkNotNullExpressionValue(var11, "");
      Iterable var7 = var11;
      double var3 = 0.0;

      for (Object var1 : var7) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var9 = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry)var1;
         double var5 = var3;
         Intrinsics.checkNotNull(var9);
         Holder var2 = (Holder)((Entry)var9).getKey();
         Integer var10 = (Integer)((Entry)var9).getValue();
         var3 = var5 + (var2.is(EnchantmentTags.CURSE) ? -0.001 : (double)var10.intValue() / ((Enchantment)var2.value()).getMaxLevel());
      }

      return var3;
   }

   @NotNull
   public static final Map get_enchantments_/* $VF was: get(enchantments) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var1 = var0;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var10 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var10;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      Set var9 = EnchantmentHelper.getEnchantmentsForCrafting(var10000).entrySet();
      Intrinsics.checkNotNullExpressionValue(var9, "");
      Iterable var4 = var9;
      Map var6 = new LinkedHashMap();

      for (Object var2 : var4) {
         Map var3 = var6;
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var7;
         Pair var8 = TuplesKt.to(((Holder)(var7 = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry)var2).getKey()).unwrapKey().get(), var7.getIntValue());
         var3.put(var8.getFirst(), var8.getSecond());
      }

      return var6;
   }

   public static final boolean isDamageable(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.isDamageableItem();
   }

   public static final int getMaxDamage(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var0.getItem();
      PatchedDataComponentMap var10004 = var0.getTag();
      if (var10004 == null) {
         DataComponentMap var1 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var1;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getMaxDamage();
   }

   public static final int getDurability(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var1 = var0;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var4 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var4;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      int var3 = var10000.getMaxDamage();
      var1 = var0;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10005 = var1.getTag();
      if (var10005 == null) {
         DataComponentMap var5 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var5;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return var3 - var10001.getDamageValue();
   }

   public static final boolean isBucket(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getItem() instanceof BucketItem || var0.getItem() instanceof MilkBucketItem || var0.getItem() instanceof SolidBucketItem;
   }

   public static final boolean isFullBucket(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getItem() instanceof BucketItem && !Intrinsics.areEqual(var0.getItem(), Items.BUCKET)
         || var0.getItem() instanceof MilkBucketItem
         || var0.getItem() instanceof SolidBucketItem;
   }

   public static final boolean isEmptyBucket(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return Intrinsics.areEqual(var0.getItem(), Items.BUCKET);
   }

   public static final boolean isEmptyComparedTo(@NotNull ItemType var0, @NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Item var2 = var1.getItem();
      Log.INSTANCE.trace("isEmpty item: " + var0.getItem().getClass());
      Log.INSTANCE.trace("isEmpty otherItem: " + var0.getItem().getClass());
      if ((var0.getItem() instanceof MilkBucketItem || var0.getItem() instanceof SolidBucketItem)
         && var2 instanceof BucketItem
         && Intrinsics.areEqual(((BucketItem)var2).content, Fluids.EMPTY)) {
         return true;
      } else {
         return Intrinsics.areEqual(var2, Items.BUCKET)
               && var0.getItem() instanceof BucketItem
               && !Intrinsics.areEqual(((BucketItem)var0.getItem()).content, Fluids.EMPTY)
            ? true
            : var0.getItem() instanceof MobBucketItem && var2 instanceof BucketItem && !(var2 instanceof MobBucketItem);
      }
   }

   public static final boolean isFullComparedTo(@NotNull ItemType var0, @NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Item var2 = var1.getItem();
      Log.INSTANCE.trace("isFull item: " + var0.getItem().getClass());
      Log.INSTANCE.trace("isFull otherItem: " + var0.getItem().getClass());
      return !Intrinsics.areEqual(var0.getItem(), Items.BUCKET) || !(var2 instanceof MilkBucketItem) && !(var2 instanceof SolidBucketItem)
         ? !(var0.getItem() instanceof MobBucketItem) && var2 instanceof MobBucketItem
         : true;
   }

   public static final boolean isHoneyBottle(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return Intrinsics.areEqual(var0.getItem(), Items.HONEY_BOTTLE);
   }

   public static final boolean isStew(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (var0.getItem().components().get(DataComponents.FOOD) != null) {
         net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
         ItemLike var10002 = (ItemLike)var0.getItem();
         PatchedDataComponentMap var10004 = var0.getTag();
         if (var10004 == null) {
            DataComponentMap var4 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var4;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         FoodProperties var2 = (FoodProperties)var10000.getComponents().get(DataComponents.FOOD);
         boolean var3;
         if (var2 != null) {
            FoodProperties var1 = var2;
            var3 = var2.usingConvertsTo().isPresent()
               && Intrinsics.areEqual(((net.minecraft.world.item.ItemStack)var1.usingConvertsTo().get()).getItem(), Items.BOWL);
         } else {
            var3 = false;
         }

         if (var3) {
            return true;
         }
      }

      return false;
   }

   public static final boolean getHasPotionName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      PatchedDataComponentMap var10000 = var0.getTag();
      return var10000 != null ? var10000.has(DataComponents.POTION_CONTENTS) : false;
   }

   @NotNull
   public static final String getPotionName(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (var0.getTag() != null) {
         PatchedDataComponentMap var10000 = var0.getTag();
         if (var10000 != null ? var10000.has(DataComponents.POTION_CONTENTS) : false) {
            label18: {
               PotionContents var1 = (PotionContents)var0.getTag().get(DataComponents.POTION_CONTENTS);
               if (var1 != null) {
                  var2 = var1.potion();
                  if (var2 != null) {
                     break label18;
                  }
               }

               var2 = Optional.empty();
            }

            String var3 = Potion.getName(var2, "");
            Intrinsics.checkNotNullExpressionValue(var3, "");
            return var3;
         }
      }

      return "";
   }

   public static final boolean getHasPotionEffects(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      PatchedDataComponentMap var10000 = var0.getTag();
      if (var10000 != null) {
         PotionContents var1 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
         if (var1 != null) {
            return var1.hasEffects();
         }
      }

      return false;
   }

   public static final boolean getHasCustomPotionEffects(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      PatchedDataComponentMap var10000 = var0.getTag();
      if (var10000 != null) {
         PotionContents var1 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
         if (var1 != null) {
            List var2 = var1.customEffects();
            if (var2 != null) {
               if (!var2.isEmpty()) {
                  return true;
               }

               return false;
            }
         }
      }

      return false;
   }

   @NotNull
   public static final List getPotionEffects(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      PatchedDataComponentMap var10000 = var0.getTag();
      if (var10000 != null) {
         PotionContents var1 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
         if (var1 != null) {
            Iterable var2 = var1.getAllEffects();
            if (var2 != null) {
               List var3 = CollectionsKt.toList(var2);
               if (var3 != null) {
                  return var3;
               }
            }
         }
      }

      return CollectionsKt.emptyList();
   }

   @NotNull
   public static final List getComparablePotionEffects(@NotNull ItemType var0) {
      Iterable var10;
      label24: {
         Intrinsics.checkNotNullParameter(var0, "");
         PatchedDataComponentMap var10000 = var0.getTag();
         if (var10000 != null) {
            PotionContents var8 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
            if (var8 != null) {
               var10 = var8.getAllEffects();
               if (var10 != null) {
                  var10 = CollectionsKt.toList(var10);
                  if (var10 != null) {
                     break label24;
                  }
               }
            }
         }

         var10 = CollectionsKt.emptyList();
      }

      Iterable var4;
      Iterable var1 = var4 = var10;
      var var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

      for (MobEffectInstance var2 : var1) {
         var2 = var2;
         ArrayList var3 = var5;
         var3.add(new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var2.getEffect().value())), var2.getAmplifier(), var2.getDuration()));
      }

      return var5;
   }

   @NotNull
   public static final PotionEffect get_asComparable_/* $VF was: get(asComparable) */(@NotNull MobEffectInstance var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var0.getEffect().value())), var0.getAmplifier(), var0.getDuration());
   }

   @Nullable
   public static final EquipmentSlot get_equipmentSlot_/* $VF was: get(equipmentSlot) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getItem() instanceof ArmorItem ? ((ArmorItem)var0.getItem()).getEquipmentSlot() : null;
   }

   public static final boolean get_isBundle_/* $VF was: get(isBundle) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return false;
   }

   public static final boolean get_isBundleAndHasItems_/* $VF was: get(isBundleAndHasItems) */(@NotNull ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      PatchedDataComponentMap var10000 = var0.getTag();
      if (var10000 != null) {
         BundleContents var1 = (BundleContents)var10000.get(DataComponents.BUNDLE_CONTENTS);
         if (var1 != null) {
            if (var1.size() > 0) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   private static final DataComponentPatch COMPONENTS_CHANGES_CODEC$lambda$13$lambda$12$lambda$10(net.minecraft.world.item.ItemStack var0) {
      return var0 != null ? var0.getComponentsPatch() : null;
   }

   private static final net.minecraft.world.item.ItemStack COMPONENTS_CHANGES_CODEC$lambda$13$lambda$12$lambda$11(DataComponentPatch var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return new net.minecraft.world.item.ItemStack((Holder)BuiltInRegistries.ITEM.getHolder(BuiltInRegistries.ITEM.getKey(Items.AIR)).get(), 0, var0);
   }

   private static final App COMPONENTS_CHANGES_CODEC$lambda$13$lambda$12(Instance var0) {
      return var0.group(
            (App)DataComponentPatch.CODEC
               .optionalFieldOf("components", DataComponentPatch.EMPTY)
               .forGetter(ItemTypeExtensionsKt::COMPONENTS_CHANGES_CODEC$lambda$13$lambda$12$lambda$10)
         )
         .apply((Applicative)var0, ItemTypeExtensionsKt::COMPONENTS_CHANGES_CODEC$lambda$13$lambda$12$lambda$11);
   }

   private static final Codec COMPONENTS_CHANGES_CODEC$lambda$13() {
      return RecordCodecBuilder.create(ItemTypeExtensionsKt::COMPONENTS_CHANGES_CODEC$lambda$13$lambda$12);
   }

   static {
      Codec var10000 = Codec.lazyInitialized(ItemTypeExtensionsKt::COMPONENTS_CHANGES_CODEC$lambda$13);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      COMPONENTS_CHANGES_CODEC = var10000;
   }
}
