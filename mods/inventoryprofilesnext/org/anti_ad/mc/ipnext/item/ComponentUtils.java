package org.anti_ad.mc.ipnext.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nComponentUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,261:1\n1803#2,3:262\n1803#2,3:265\n1056#2:268\n1056#2:269\n774#2:270\n865#2,2:271\n1869#2,2:273\n1869#2,2:275\n1878#2,3:277\n*S KotlinDebug\n*F\n+ 1 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n57#1:262,3\n65#1:265,3\n186#1:268\n187#1:269\n188#1:270\n188#1:271,2\n191#1:273,2\n251#1:275,2\n103#1:277,3\n*E\n"
)
public final class ComponentUtils {
   @NotNull
   public static final ComponentUtils INSTANCE = new ComponentUtils();
   @NotNull
   private static final Map registry = new LinkedHashMap();
   @NotNull
   private static final Comparator DEFAULT_COMPARATOR = ComparisonsKt.nullsLast(ComponentUtils::DEFAULT_COMPARATOR$lambda$3);
   @NotNull
   private static final Comparator nbtComparator;
   @NotNull
   private static final Comparator textComparator;
   @NotNull
   private static final Comparator mapColorComponentComparator;
   @NotNull
   private static final Comparator dyedComponentComparator;
   @NotNull
   private static final Comparator mapIdComparator;
   @NotNull
   private static final Comparator mapMapPostProcessingComponentComparator;
   @NotNull
   private static final Comparator itemTypeComparator;
   @NotNull
   private static final Comparator chargedProjectilesComponentComparator;
   @NotNull
   private static final Comparator itemEnchantmentsComponentComparator;
   @NotNull
   private static final Comparator potionEffectsComponentComparator;

   private ComponentUtils() {
   }

   private final double getScore(ItemEnchantments var1) {
      Set var10000 = var1.entrySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterable var7 = var10000;
      double var3 = 0.0;

      for (Object var2 : var7) {
         Entry var9 = (Entry)var2;
         double var5 = var3;
         Intrinsics.checkNotNull(var9);
         Holder var11 = (Holder)((java.util.Map.Entry)var9).getKey();
         Integer var10 = (Integer)((java.util.Map.Entry)var9).getValue();
         var3 = var5 + (var11.is(EnchantmentTags.CURSE) ? -0.001 : (double)var10.intValue() / ((Enchantment)var11.value()).getMaxLevel());
      }

      return -var3;
   }

   // $VF: Could not properly define all variable types!
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final double getScore(PotionContents var1) {
      if (var1.hasEffects()) {
         Iterable var10000 = var1.getAllEffects();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         Iterable var9 = CollectionsKt.toList(var10000);
         double var3 = 0.0;

         for (MobEffectInstance var2 : var9) {
            var2 = var2;
            double var5 = var3;
            <unknown> var13;

            MobEffect var12;
            (var12 = (MobEffect)var2.getEffect().value()).getCategory();
            double var7 = switch (var13 == null ? -1 : ComponentUtils$WhenMappings.$EnumSwitchMapping$0[var13.ordinal()]) {
               case -1, 2 -> 0.0;
               default -> throw new NoWhenBranchMatchedException();
               case 1 -> -5.0;
               case 3 -> 5.0;
            } + var2.getDuration() / 20.0 / 1000.0 + var2.getAmplifier() * 5.0 + (var2.isInfiniteDuration() ? 5.0 : 0.0);
            var12.getCategory();
            var3 = var5 - var7;
         }

         return var3;
      } else {
         return 0.0;
      }
   }

   private final Comparator getTextComparator() {
      return textComparator;
   }

   @NotNull
   public final Comparator getItemTypeComparator() {
      return itemTypeComparator;
   }

   @NotNull
   public final Comparator comparatorFor(@Nullable DataComponentType var1) {
      Comparator var2;
      if ((var2 = (Comparator)registry.get(String.valueOf(var1))) != null) {
         return var2;
      } else if (var1 instanceof Comparable) {
         Comparator var10000 = ComparisonsKt.nullsLast(new ComponentUtils$comparatorFor$$inlined$compareBy$1());
         Intrinsics.checkNotNull(var10000);
         return var10000;
      } else {
         return DEFAULT_COMPARATOR;
      }
   }

   public final int compareComponents(@NotNull ItemType var1, @NotNull ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      PatchedDataComponentMap var8 = var1.getTag();
      PatchedDataComponentMap var9 = var2.getTag();
      boolean var3 = var8 == null;
      int var4 = var9 == null;
      if (var3 != var4) {
         return var3 ? -1 : 1;
      }

      if (var8 != null && var9 != null) {
         Set var10000 = var8.keySet();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         List var10 = CollectionsKt.sortedWith(CollectionsKt.toList(var10000), new ComponentUtils$compareComponents$$inlined$sortedBy$1());
         var10000 = var9.keySet();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         List var14 = CollectionsKt.sortedWith(CollectionsKt.toList(var10000), new ComponentUtils$compareComponents$$inlined$sortedBy$2());
         Iterable var11 = var10;
         var var5 = new ArrayList();
         Iterator var12 = var11.iterator();

         while (var12.hasNext()) {
            Object var6;
            DataComponentType var7 = (DataComponentType)(var6 = var12.next());
            if (var14.contains(var7)) {
               var5.add(var6);
            }
         }

         for (DataComponentType var15 : var5) {
            Object var18 = var8.get(var15);
            Object var19 = var9.get(var15);
            Log.INSTANCE.trace("c1: " + (var18 != null ? var18.getClass() : null) + ", c2: " + (var19 != null ? var19.getClass() : null));
            int var17;
            if (var18 instanceof Comparable && (var17 = ((Comparable)var18).compareTo(var19)) != 0) {
               return var17;
            }

            Comparator var21 = var15 != null ? INSTANCE.comparatorFor(var15) : null;
            if (var21 != null) {
               if ((var4 = var21.compare(var18, var19)) != 0) {
                  return var4;
               }
            }
         }

         return Intrinsics.compare(var8.size(), var9.size());
      } else {
         return 0;
      }
   }

   @Nullable
   public final Tag toFilteredNbtOrNull(@NotNull DataComponentType var1, @NotNull Optional var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (var2.isEmpty()) {
         return null;
      }

      Codec var10000 = var1.codecOrThrow();
      Intrinsics.checkNotNull(var10000);
      Codec var3 = var10000;
      Object var4;
      if ((var4 = var2.get()) != null && var4 instanceof Tag) {
         return ((Tag)var4).copy();
      }

      if ((var2 = var3.encodeStart((DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE), var2.get())
            .resultOrPartial())
         .isPresent()) {
         if (Intrinsics.areEqual(var1, DataComponents.ENCHANTMENTS)) {
            Object var10001 = var2.get();
            Intrinsics.checkNotNull(var10001);
            return this.sanitizeEnchantments((CompoundTag)var10001);
         }

         if (!Intrinsics.areEqual(var1, DataComponents.DAMAGE) && !Intrinsics.areEqual(var1, DataComponents.CUSTOM_NAME)) {
            return (Tag)var2.get();
         }
      }

      return null;
   }

   @Nullable
   public final Tag toFullNbtOrNull(@NotNull DataComponentType var1, @NotNull Optional var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (var2.isEmpty()) {
         return null;
      }

      Codec var10000 = var1.codecOrThrow();
      Intrinsics.checkNotNull(var10000);
      Codec var4 = var10000;
      Object var3;
      if ((var3 = var2.get()) != null && var3 instanceof Tag) {
         return ((Tag)var3).copy();
      }

      Optional var5;
      return (var5 = var4.encodeStart((DynamicOps)Vanilla.INSTANCE.world().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE), var2.get())
               .resultOrPartial())
            .isPresent()
         ? (Tag)var5.get()
         : null;
   }

   private final Tag sanitizeEnchantments(CompoundTag var1) {
      Tag var10000 = var1.get("levels");
      Intrinsics.checkNotNull(var10000);
      var1 = (CompoundTag)var10000;
      ListTag var2 = new ListTag();
      Set var6 = var1.getAllKeys();
      Intrinsics.checkNotNullExpressionValue(var6, "");

      for (String var3 : var6) {
         Log.INSTANCE.trace("found level " + var3);
         var2.add(StringTag.valueOf(var3));
      }

      return (Tag)var2;
   }

   @NotNull
   public final DataComponentPatch _withRemovedIf_/* $VF was: (withRemovedIf) */(@NotNull DataComponentPatch var1, @NotNull Predicate var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      DataComponentPatch var10000 = var1.forget(var2);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   private static final int DEFAULT_COMPARATOR$lambda$2(Object var0, Object var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return 0;
   }

   private static final int DEFAULT_COMPARATOR$lambda$3(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private static final int nbtComparator$lambda$4(CustomData var0, CustomData var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      NbtUtils var10000 = NbtUtils.INSTANCE;
      CompoundTag var10001 = var0.copyTag();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      Tag var2 = (Tag)var10001;
      CompoundTag var10002 = var1.copyTag();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      return var10000.compareTo(var2, (Tag)var10002);
   }

   private static final int nbtComparator$lambda$5(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private static final int textComparator$lambda$11(Component var0, Component var1) {
      List var10000 = var0.getSiblings();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterable var5 = var10000;
      int var2 = 0;

      for (Object var3 : var5) {
         int var4;
         if ((var4 = var2++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Component var7 = (Component)var3;
         var4 = var4;
         int var8;
         if ((var8 = INSTANCE.getTextComparator().compare(var7, (Component)var1.getSiblings().get(var4))) != 0) {
            return var8;
         }
      }

      return 0;
   }

   private static final int textComparator$lambda$12(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   static {
      Comparator var0;
      Comparator var1 = var0 = ComparisonsKt.nullsLast(ComponentUtils::nbtComparator$lambda$5);
      Map var10000 = registry;
      String var10001 = DataComponents.BLOCK_ENTITY_DATA.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      registry.put(DataComponents.CUSTOM_DATA.toString(), var1);
      registry.put(DataComponents.BUCKET_ENTITY_DATA.toString(), var1);
      registry.put(DataComponents.ENTITY_DATA.toString(), var1);
      nbtComparator = var0;
      var1 = new ComponentUtils$special$$inlined$compareBy$1();
      Comparator var22 = (new ComponentUtils$special$$inlined$thenBy$1(var1)).thenComparing(ComponentUtils::textComparator$lambda$12);
      Intrinsics.checkNotNullExpressionValue(var22, "");
      var1 = var0 = ComparisonsKt.nullsLast(var22);
      var10000 = registry;
      var10001 = DataComponents.CUSTOM_NAME.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      registry.put(DataComponents.ITEM_NAME.toString(), var1);
      textComparator = var0;
      var1 = var0 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$compareBy$2());
      var10000 = registry;
      var10001 = DataComponents.MAP_COLOR.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      mapColorComponentComparator = var0;
      var1 = var0 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$compareBy$3());
      var10000 = registry;
      var10001 = DataComponents.DYED_COLOR.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      dyedComponentComparator = var0;
      var1 = var0 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$compareBy$4());
      var10000 = registry;
      var10001 = DataComponents.MAP_ID.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      mapIdComparator = var0;
      var1 = var0 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$compareBy$5());
      var10000 = registry;
      var10001 = DataComponents.MAP_POST_PROCESSING.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      mapMapPostProcessingComponentComparator = var0;
      var0 = new ComponentUtils$special$$inlined$compareBy$6();
      var0 = new ComponentUtils$special$$inlined$thenBy$2(var0);
      itemTypeComparator = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$thenComparator$1(var0));
      var0 = new ComponentUtils$special$$inlined$compareBy$7();
      Comparator var10;
      var1 = var10 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$thenComparator$2(var0));
      var10000 = registry;
      var10001 = DataComponents.CHARGED_PROJECTILES.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      chargedProjectilesComponentComparator = var10;
      var1 = var0 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$compareBy$8());
      var10000 = registry;
      var10001 = DataComponents.ENCHANTMENTS.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      itemEnchantmentsComponentComparator = var0;
      var1 = var0 = ComparisonsKt.nullsLast(new ComponentUtils$special$$inlined$compareBy$9());
      var10000 = registry;
      var10001 = DataComponents.POTION_CONTENTS.toString();
      Intrinsics.checkNotNull(var1);
      var10000.put(var10001, var1);
      potionEffectsComponentComparator = var0;
   }
}
