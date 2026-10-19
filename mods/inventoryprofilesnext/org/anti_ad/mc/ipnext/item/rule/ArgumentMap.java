package org.anti_ad.mc.ipnext.item.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParameters;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nArgumentMap.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ArgumentMap.kt\norg/anti_ad/mc/ipnext/item/rule/ArgumentMap\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,110:1\n136#2,9:111\n216#2:120\n217#2:123\n145#2:124\n1#3:121\n1#3:122\n1563#4:125\n1634#4,3:126\n*S KotlinDebug\n*F\n+ 1 ArgumentMap.kt\norg/anti_ad/mc/ipnext/item/rule/ArgumentMap\n*L\n40#1:111,9\n40#1:120\n40#1:123\n40#1:124\n40#1:122\n100#1:125\n100#1:126,3\n*E\n"
)
public final class ArgumentMap {
   @NotNull
   private final Map defaultValues = new LinkedHashMap();
   @NotNull
   private final Map values = new LinkedHashMap();

   @NotNull
   public final List getMissingParameters() {
      Map var1 = this.defaultValues;
      var var2 = new ArrayList();
      Iterator var6 = var1.entrySet().iterator();

      while (var6.hasNext()) {
         Entry var3;
         String var4 = (String)(var3 = (Entry)var6.next()).getKey();
         Object var7 = var3.getValue();
         String var5 = var4;
         String var10000 = var7 == null && !this.values.containsKey(var4) ? var5 : null;
         if (var10000 != null) {
            var7 = var10000;
            var2.add(var7);
         }
      }

      return var2;
   }

   public final void defineParameter(@NotNull Parameter var1, @NotNull Object var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.defaultValues.put(var1.getName(), var2);
   }

   public final void defineParameter(@NotNull Parameter var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.defaultValues.put(var1.getName(), null);
   }

   public final void defineParametersFrom(@NotNull ArgumentMap var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.defaultValues.putAll(var1.defaultValues);
   }

   @NotNull
   public final Object get(@NotNull Parameter var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Object var10000 = this.values.get(var1.getName());
      if (var10000 == null) {
         var10000 = MapsKt.getValue(this.defaultValues, var1.getName());
      }

      Intrinsics.checkNotNull(var10000);
      return var10000;
   }

   private final Object get(String var1) {
      Object var10000 = this.values.get(var1);
      if (var10000 == null) {
         var10000 = MapsKt.getValue(this.defaultValues, var1);
      }

      return var10000;
   }

   public final boolean isDefaultValue(@NotNull Parameter var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return !this.values.containsKey(var1.getName());
   }

   public final boolean trySetArgument(@NotNull Parameter var1, @NotNull String var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (!this.contains(var1.getName())) {
         return false;
      }

      Object var3;
      if ((var3 = var1.getArgumentType().parse(var2)) == null) {
         return false;
      }

      this.values.put(var1.getName(), var3);
      return true;
   }

   public final void setArgumentsFrom(@NotNull ArgumentMap var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      for (String var3 : var1.defaultValues.keySet()) {
         if (this.contains(var3)) {
            Object var10000 = var1.get(var3);
            if (var10000 != null) {
               Object var4 = var10000;
               this.values.put(var3, var4);
            }
         }
      }
   }

   public final boolean contains(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.defaultValues.containsKey(var1);
   }

   @NotNull
   public final List dumpAsPairList() {
      Iterable var1;
      Iterable var2 = var1 = this.defaultValues.keySet();
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (String var3 : var2) {
         var3 = var3;
         ArrayList var6 = var1;
         Object var4;
         Pair var10000;
         if ((var4 = this.get(var3)) == null) {
            var10000 = TuplesKt.to(var3, String.valueOf(var4));
         } else {
            Parameter var10 = (Parameter)NativeParameters.INSTANCE.getMap().get(var3);
            ArgumentType var5;
            var10000 = (var5 = var10 != null ? var10.getArgumentType() : null) == null
               ? TuplesKt.to(var3, var4.toString())
               : TuplesKt.to(var3, var5.toString(var4));
         }

         var6.add(var10000);
      }

      return var1;
   }
}
