package org.anti_ad.mc.ipnext.item.rule.natives;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.extensions.ByPropertyName;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.parameter.ItemTypeMatcher;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.anti_ad.mc.ipnext.item.rule.parameter.NumberOrder;
import org.anti_ad.mc.ipnext.item.rule.parameter.RequireNbt;
import org.anti_ad.mc.ipnext.item.rule.parameter.StringCompare;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nDefinedNativeRules.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefinedNativeRules.kt\norg/anti_ad/mc/ipnext/item/rule/natives/DefinedNativeRulesKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,159:1\n1#2:160\n127#3:161\n106#3:162\n125#3:163\n123#3:164\n106#3:165\n127#3:166\n106#3:167\n129#3,5:168\n106#3:173\n133#3:174\n106#3:175\n131#3:176\n112#3:177\n405#3:178\n402#3:179\n339#3:180\n341#3:181\n106#3:182\n356#3:183\n106#3:184\n358#3:185\n356#3:186\n106#3:187\n341#3:188\n106#3:189\n344#3:190\n106#3:191\n345#3:194\n346#3:196\n123#3:197\n106#3:198\n354#3:199\n106#3:200\n1803#4,2:192\n1805#4:195\n*S KotlinDebug\n*F\n+ 1 DefinedNativeRules.kt\norg/anti_ad/mc/ipnext/item/rule/natives/DefinedNativeRulesKt\n*L\n109#1:161\n109#1:162\n110#1:163\n110#1:164\n110#1:165\n110#1:166\n110#1:167\n111#1:168,5\n111#1:173\n112#1:174\n112#1:175\n114#1:176\n114#1:177\n116#1:178\n116#1:179\n121#1:180\n124#1:181\n124#1:182\n125#1:183\n125#1:184\n126#1:185\n126#1:186\n126#1:187\n126#1:188\n126#1:189\n127#1:190\n127#1:191\n127#1:194\n127#1:196\n133#1:197\n133#1:198\n134#1:199\n134#1:200\n127#1:192,2\n127#1:195\n*E\n"
)
public final class DefinedNativeRulesKt {
   @NotNull
   private static final Map NATIVE_MAP = new LinkedHashMap();
   @NotNull
   private static final AsDelegate none$delegate;
   @NotNull
   private static final AsDelegate display_name$delegate;
   @NotNull
   private static final AsDelegate custom_name$delegate;
   @NotNull
   private static final AsDelegate translated_name$delegate;
   @NotNull
   private static final AsDelegate translation_key$delegate;
   @NotNull
   private static final AsDelegate item_id$delegate;
   @NotNull
   private static final AsDelegate potion_name$delegate;
   @NotNull
   private static final AsDelegate raw_id$delegate;
   @NotNull
   private static final AsDelegate creative_menu_group_index$delegate;
   @NotNull
   private static final AsDelegate search_tab_index$delegate;
   @NotNull
   private static final AsDelegate damage$delegate;
   @NotNull
   private static final AsDelegate max_damage$delegate;
   @NotNull
   private static final AsDelegate durability$delegate;
   @NotNull
   private static final AsDelegate enchantments_score$delegate;
   @NotNull
   private static final AsDelegate accumulated_count$delegate;
   @NotNull
   private static final AsDelegate has_custom_name$delegate;
   @NotNull
   private static final AsDelegate is_damageable$delegate;
   @NotNull
   private static final AsDelegate component_match_nbt$delegate;
   @NotNull
   private static final AsDelegate is_tag$delegate;
   @NotNull
   private static final AsDelegate is_item$delegate;
   @NotNull
   private static final AsDelegate component_by_nbt$delegate;
   @NotNull
   private static final AsDelegate component_nbt_comparator$delegate;
   @NotNull
   private static final AsDelegate potion_effect$delegate;
   @NotNull
   private static final AsDelegate components_comparator$delegate;
   @NotNull
   private static final AsDelegate enchantments_tooltip_order$delegate;

   private static final TypeBasedRuleProvider type(Function0 var0, Function2 var1) {
      return new TypeBasedRuleProvider(var0, var1, null, null, 12, null);
   }

   private static final ByPropertyName rule(Function0 var0) {
      return new ByPropertyName(DefinedNativeRulesKt::rule$lambda$1);
   }

   @NotNull
   public static final Function0 getNone() {
      return (Function0)none$delegate.getValue(null, $$delegatedProperties[0]);
   }

   @NotNull
   public static final Function0 getDisplay_name() {
      return (Function0)display_name$delegate.getValue(null, $$delegatedProperties[1]);
   }

   @NotNull
   public static final Function0 getCustom_name() {
      return (Function0)custom_name$delegate.getValue(null, $$delegatedProperties[2]);
   }

   @NotNull
   public static final Function0 getTranslated_name() {
      return (Function0)translated_name$delegate.getValue(null, $$delegatedProperties[3]);
   }

   @NotNull
   public static final Function0 getTranslation_key() {
      return (Function0)translation_key$delegate.getValue(null, $$delegatedProperties[4]);
   }

   @NotNull
   public static final Function0 getItem_id() {
      return (Function0)item_id$delegate.getValue(null, $$delegatedProperties[5]);
   }

   @NotNull
   public static final Function0 getPotion_name() {
      return (Function0)potion_name$delegate.getValue(null, $$delegatedProperties[6]);
   }

   @NotNull
   public static final Function0 getRaw_id() {
      return (Function0)raw_id$delegate.getValue(null, $$delegatedProperties[7]);
   }

   @NotNull
   public static final Function0 getCreative_menu_group_index() {
      return (Function0)creative_menu_group_index$delegate.getValue(null, $$delegatedProperties[8]);
   }

   @NotNull
   public static final Function0 getSearch_tab_index() {
      return (Function0)search_tab_index$delegate.getValue(null, $$delegatedProperties[9]);
   }

   @NotNull
   public static final Function0 getDamage() {
      return (Function0)damage$delegate.getValue(null, $$delegatedProperties[10]);
   }

   @NotNull
   public static final Function0 getMax_damage() {
      return (Function0)max_damage$delegate.getValue(null, $$delegatedProperties[11]);
   }

   @NotNull
   public static final Function0 getDurability() {
      return (Function0)durability$delegate.getValue(null, $$delegatedProperties[12]);
   }

   @NotNull
   public static final Function0 getEnchantments_score() {
      return (Function0)enchantments_score$delegate.getValue(null, $$delegatedProperties[13]);
   }

   @NotNull
   public static final Function0 getAccumulated_count() {
      return (Function0)accumulated_count$delegate.getValue(null, $$delegatedProperties[14]);
   }

   @NotNull
   public static final Function0 getHas_custom_name() {
      return (Function0)has_custom_name$delegate.getValue(null, $$delegatedProperties[15]);
   }

   @NotNull
   public static final Function0 is_damageable() {
      return (Function0)is_damageable$delegate.getValue(null, $$delegatedProperties[16]);
   }

   @NotNull
   public static final Function0 getComponent_match_nbt() {
      return (Function0)component_match_nbt$delegate.getValue(null, $$delegatedProperties[17]);
   }

   @NotNull
   public static final Function0 is_tag() {
      return (Function0)is_tag$delegate.getValue(null, $$delegatedProperties[18]);
   }

   @NotNull
   public static final Function0 is_item() {
      return (Function0)is_item$delegate.getValue(null, $$delegatedProperties[19]);
   }

   @NotNull
   public static final Function0 getComponent_by_nbt() {
      return (Function0)component_by_nbt$delegate.getValue(null, $$delegatedProperties[20]);
   }

   @NotNull
   public static final Function0 getComponent_nbt_comparator() {
      return (Function0)component_nbt_comparator$delegate.getValue(null, $$delegatedProperties[21]);
   }

   @NotNull
   public static final Function0 getPotion_effect() {
      return (Function0)potion_effect$delegate.getValue(null, $$delegatedProperties[22]);
   }

   @NotNull
   public static final Function0 getComponents_comparator() {
      return (Function0)components_comparator$delegate.getValue(null, $$delegatedProperties[23]);
   }

   @NotNull
   public static final Function0 getEnchantments_tooltip_order() {
      return (Function0)enchantments_tooltip_order$delegate.getValue(null, $$delegatedProperties[24]);
   }

   private static final Function0 rule$lambda$1(Function0 var0, ByPropertyName var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Function0 var3 = var0;
      NATIVE_MAP.put(var2, var3);
      return var0;
   }

   private static final String display_name_delegate$lambda$2(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var4 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var4;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      String var3 = var10000.getDisplayName().getString();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      return var3;
   }

   private static final String custom_name_delegate$lambda$3(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      var1 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var8 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var8;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         var1 = var2;
         var10000 = new ItemStack;
         var10002 = (ItemLike)var1.getItem();
         var10004 = var1.getTag();
         if (var10004 == null) {
            DataComponentMap var10 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var10;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         String var6 = var10000.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var6, "");
         return var6;
      } else {
         return "";
      }
   }

   private static final String translated_name_delegate$lambda$4(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      I18n var10000 = I18n.INSTANCE;
      ItemStack var10001 = new ItemStack;
      ItemLike var10003 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10005 = var2.getTag();
      if (var10005 == null) {
         DataComponentMap var4 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var4;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var3 = var10001.getDescriptionId();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      return var10000.translate(var3, new Object[0]);
   }

   private static final String translation_key_delegate$lambda$5(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var4 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var4;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      String var3 = var10000.getDescriptionId();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      return var3;
   }

   private static final String item_id_delegate$lambda$6(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var2.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var3 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      return var3;
   }

   private static final String potion_name_delegate$lambda$7(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      if (var1.getTag() != null) {
         PatchedDataComponentMap var10000 = var2.getTag();
         if (var10000 != null ? var10000.has(DataComponents.POTION_CONTENTS) : false) {
            label18: {
               PotionContents var3 = (PotionContents)var2.getTag().get(DataComponents.POTION_CONTENTS);
               if (var3 != null) {
                  var4 = var3.potion();
                  if (var4 != null) {
                     break label18;
                  }
               }

               var4 = Optional.empty();
            }

            String var5 = Potion.getName(var4, "");
            Intrinsics.checkNotNullExpressionValue(var5, "");
            return var5;
         }
      }

      return "";
   }

   private static final Number raw_id_delegate$lambda$8(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      DefaultedRegistry var10000 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      return VanillaAccessorsKt._getRawId_/* $VF was: (getRawId) */(var10000, var2.getItem());
   }

   private static final Number creative_menu_group_index_delegate$lambda$9(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return ItemTypeExtensionsKt.getGroupIndex(var1);
   }

   private static final Number search_tab_index_delegate$lambda$10(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return ItemTypeExtensionsKt.getSearchTabIndex(var1);
   }

   private static final Number damage_delegate$lambda$11(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var3;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getDamageValue();
   }

   private static final Number max_damage_delegate$lambda$12(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var3;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getMaxDamage();
   }

   private static final Number durability_delegate$lambda$13(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      var1 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var6 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var6;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      int var5 = var10000.getMaxDamage();
      var1 = var2;
      ItemStack var10001 = new ItemStack;
      ItemLike var10003 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10005 = var1.getTag();
      if (var10005 == null) {
         DataComponentMap var7 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var7;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return var5 - var10001.getDamageValue();
   }

   private static final Number enchantments_score_delegate$lambda$14(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var7 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var7.getItem();
      PatchedDataComponentMap var10004 = var7.getTag();
      if (var10004 == null) {
         DataComponentMap var14 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var14;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      Set var13 = EnchantmentHelper.getEnchantmentsForCrafting(var10000).entrySet();
      Intrinsics.checkNotNullExpressionValue(var13, "");
      Iterable var8 = var13;
      double var3 = 0.0;

      for (Object var10 : var8) {
         Entry var11 = (Entry)var10;
         double var5 = var3;
         Intrinsics.checkNotNull(var11);
         Holder var2 = (Holder)((java.util.Map.Entry)var11).getKey();
         Integer var12 = (Integer)((java.util.Map.Entry)var11).getValue();
         var3 = var5 + (var2.is(EnchantmentTags.CURSE) ? -0.001 : (double)var12.intValue() / ((Enchantment)var2.value()).getMaxLevel());
      }

      return var3;
   }

   private static final Number accumulated_count_delegate$lambda$15(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getAccumulatedCount();
   }

   private static final boolean has_custom_name_delegate$lambda$16(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var3;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null;
   }

   private static final boolean is_damageable_delegate$lambda$17(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var3;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.isDamageableItem();
   }

   private static final boolean is_tag_delegate$lambda$19$lambda$18(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return ((RequireNbt)var0.getArguments().get(NativeParametersKt.getRequire_nbt())).match(var1)
         && ((ItemTypeMatcher)var0.getArguments().get(NativeParametersKt.getTag_name())).match(var1);
   }

   private static final Unit is_tag_delegate$lambda$19(MatchNbtRule var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.andValue(DefinedNativeRulesKt::is_tag_delegate$lambda$19$lambda$18);
      return Unit.INSTANCE;
   }

   private static final boolean is_item_delegate$lambda$21$lambda$20(Rule var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemTypeMatcher var2 = (ItemTypeMatcher)var0.getArguments().get(NativeParametersKt.getItem_name());
      boolean var3 = ((RequireNbt)var0.getArguments().get(NativeParametersKt.getRequire_nbt())).match(var1);
      boolean var4 = var2.match(var1);
      return var3 && var4;
   }

   private static final Unit is_item_delegate$lambda$21(SimpleParameterBasedRule var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.andValue(DefinedNativeRulesKt::is_item_delegate$lambda$21$lambda$20);
      return Unit.INSTANCE;
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[25])[0] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "none", "getNone()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[1] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "display_name", "getDisplay_name()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[2] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "custom_name", "getCustom_name()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[3] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "translated_name", "getTranslated_name()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[4] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "translation_key", "getTranslation_key()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[5] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "item_id", "getItem_id()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[6] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "potion_name", "getPotion_name()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[7] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "raw_id", "getRaw_id()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[8] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(
               DefinedNativeRulesKt.class, "creative_menu_group_index", "getCreative_menu_group_index()Lkotlin/jvm/functions/Function0;", 1
            )
         )
      );
      var0[9] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "search_tab_index", "getSearch_tab_index()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[10] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "damage", "getDamage()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[11] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "max_damage", "getMax_damage()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[12] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "durability", "getDurability()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[13] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "enchantments_score", "getEnchantments_score()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[14] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "accumulated_count", "getAccumulated_count()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[15] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "has_custom_name", "getHas_custom_name()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[16] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "is_damageable", "is_damageable()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[17] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "component_match_nbt", "getComponent_match_nbt()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[18] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "is_tag", "is_tag()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[19] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "is_item", "is_item()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[20] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "component_by_nbt", "getComponent_by_nbt()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[21] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(
               DefinedNativeRulesKt.class, "component_nbt_comparator", "getComponent_nbt_comparator()Lkotlin/jvm/functions/Function0;", 1
            )
         )
      );
      var0[22] = Reflection.property0(
         (PropertyReference0)(new PropertyReference0Impl(DefinedNativeRulesKt.class, "potion_effect", "getPotion_effect()Lkotlin/jvm/functions/Function0;", 1))
      );
      var0[23] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(DefinedNativeRulesKt.class, "components_comparator", "getComponents_comparator()Lkotlin/jvm/functions/Function0;", 1)
         )
      );
      var0[24] = Reflection.property0(
         (PropertyReference0)(
            new PropertyReference0Impl(
               DefinedNativeRulesKt.class, "enchantments_tooltip_order", "getEnchantments_tooltip_order()Lkotlin/jvm/functions/Function0;", 1
            )
         )
      );
      $$delegatedProperties = var0;
      none$delegate = rule(DefinedNativeRulesKt$none$2.INSTANCE).provideDelegate(null, $$delegatedProperties[0]);
      display_name$delegate = type(DefinedNativeRulesKt$display_name$2.INSTANCE, DefinedNativeRulesKt::display_name_delegate$lambda$2)
         .provideDelegate(null, $$delegatedProperties[1]);
      custom_name$delegate = type(DefinedNativeRulesKt$custom_name$2.INSTANCE, DefinedNativeRulesKt::custom_name_delegate$lambda$3)
         .provideDelegate(null, $$delegatedProperties[2]);
      translated_name$delegate = type(DefinedNativeRulesKt$translated_name$2.INSTANCE, DefinedNativeRulesKt::translated_name_delegate$lambda$4)
         .provideDelegate(null, $$delegatedProperties[3]);
      translation_key$delegate = type(DefinedNativeRulesKt$translation_key$2.INSTANCE, DefinedNativeRulesKt::translation_key_delegate$lambda$5)
         .param(NativeParametersKt.getString_compare(), StringCompare.UNICODE)
         .provideDelegate(null, $$delegatedProperties[4]);
      item_id$delegate = type(DefinedNativeRulesKt$item_id$2.INSTANCE, DefinedNativeRulesKt::item_id_delegate$lambda$6)
         .param(NativeParametersKt.getString_compare(), StringCompare.UNICODE)
         .provideDelegate(null, $$delegatedProperties[5]);
      potion_name$delegate = type(DefinedNativeRulesKt$potion_name$2.INSTANCE, DefinedNativeRulesKt::potion_name_delegate$lambda$7)
         .param(NativeParametersKt.getString_compare(), StringCompare.UNICODE)
         .provideDelegate(null, $$delegatedProperties[6]);
      raw_id$delegate = type(DefinedNativeRulesKt$raw_id$2.INSTANCE, DefinedNativeRulesKt::raw_id_delegate$lambda$8)
         .provideDelegate(null, $$delegatedProperties[7]);
      creative_menu_group_index$delegate = type(
            DefinedNativeRulesKt$creative_menu_group_index$2.INSTANCE, DefinedNativeRulesKt::creative_menu_group_index_delegate$lambda$9
         )
         .provideDelegate(null, $$delegatedProperties[8]);
      search_tab_index$delegate = type(DefinedNativeRulesKt$search_tab_index$2.INSTANCE, DefinedNativeRulesKt::search_tab_index_delegate$lambda$10)
         .provideDelegate(null, $$delegatedProperties[9]);
      damage$delegate = type(DefinedNativeRulesKt$damage$2.INSTANCE, DefinedNativeRulesKt::damage_delegate$lambda$11)
         .provideDelegate(null, $$delegatedProperties[10]);
      max_damage$delegate = type(DefinedNativeRulesKt$max_damage$2.INSTANCE, DefinedNativeRulesKt::max_damage_delegate$lambda$12)
         .provideDelegate(null, $$delegatedProperties[11]);
      durability$delegate = type(DefinedNativeRulesKt$durability$2.INSTANCE, DefinedNativeRulesKt::durability_delegate$lambda$13)
         .provideDelegate(null, $$delegatedProperties[12]);
      enchantments_score$delegate = type(DefinedNativeRulesKt$enchantments_score$2.INSTANCE, DefinedNativeRulesKt::enchantments_score_delegate$lambda$14)
         .param(NativeParametersKt.getNumber_order(), NumberOrder.DESCENDING)
         .provideDelegate(null, $$delegatedProperties[13]);
      accumulated_count$delegate = type(DefinedNativeRulesKt$accumulated_count$2.INSTANCE, DefinedNativeRulesKt::accumulated_count_delegate$lambda$15)
         .param(NativeParametersKt.getNumber_order(), NumberOrder.DESCENDING)
         .provideDelegate(null, $$delegatedProperties[14]);
      has_custom_name$delegate = type(DefinedNativeRulesKt$has_custom_name$2.INSTANCE, DefinedNativeRulesKt::has_custom_name_delegate$lambda$16)
         .provideDelegate(null, $$delegatedProperties[15]);
      is_damageable$delegate = type(DefinedNativeRulesKt$is_damageable$2.INSTANCE, DefinedNativeRulesKt::is_damageable_delegate$lambda$17)
         .provideDelegate(null, $$delegatedProperties[16]);
      component_match_nbt$delegate = rule(DefinedNativeRulesKt$component_match_nbt$2.INSTANCE).provideDelegate(null, $$delegatedProperties[17]);
      is_tag$delegate = type$default(DefinedNativeRulesKt$is_tag$2.INSTANCE, null, 2, null)
         .param(NativeParametersKt.getTag_name())
         .param(NativeParametersKt.getRequire_nbt(), RequireNbt.NOT_REQUIRED)
         .post(DefinedNativeRulesKt::is_tag_delegate$lambda$19)
         .provideDelegate(null, $$delegatedProperties[18]);
      is_item$delegate = type$default(DefinedNativeRulesKt$is_item$2.INSTANCE, null, 2, null)
         .param(NativeParametersKt.getItem_name())
         .param(NativeParametersKt.getRequire_nbt(), RequireNbt.NOT_REQUIRED)
         .post(DefinedNativeRulesKt::is_item_delegate$lambda$21)
         .provideDelegate(null, $$delegatedProperties[19]);
      component_by_nbt$delegate = rule(DefinedNativeRulesKt$component_by_nbt$2.INSTANCE).provideDelegate(null, $$delegatedProperties[20]);
      component_nbt_comparator$delegate = rule(DefinedNativeRulesKt$component_nbt_comparator$2.INSTANCE).provideDelegate(null, $$delegatedProperties[21]);
      potion_effect$delegate = rule(DefinedNativeRulesKt$potion_effect$2.INSTANCE).provideDelegate(null, $$delegatedProperties[22]);
      components_comparator$delegate = rule(DefinedNativeRulesKt$components_comparator$2.INSTANCE).provideDelegate(null, $$delegatedProperties[23]);
      enchantments_tooltip_order$delegate = rule(DefinedNativeRulesKt$enchantments_tooltip_order$2.INSTANCE).provideDelegate(null, $$delegatedProperties[24]);
   }
}
