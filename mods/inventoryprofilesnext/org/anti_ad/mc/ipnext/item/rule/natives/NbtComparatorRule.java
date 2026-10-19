package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.Optional;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.alias.registry.RegistryExKt;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;

public final class NbtComparatorRule extends NativeRule {
   public NbtComparatorRule() {
      this.getArguments().defineParameter(NativeParametersKt.getComponent_id());
      this.setComparator(NbtComparatorRule::_init_$lambda$3);
   }

   private static final int lambda$3$lambda$1(Tag var0, Tag var1) {
      NbtUtils var10000 = NbtUtils.INSTANCE;
      Intrinsics.checkNotNull(var0);
      Intrinsics.checkNotNull(var1);
      return var10000.compareTo(var0, var1);
   }

   private static final int lambda$3$lambda$2(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private static final int _init_$lambda$3(NbtComparatorRule var0, ItemType var1, ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Registry var10000 = BuiltInRegistries.DATA_COMPONENT_TYPE;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.DATA_COMPONENT_TYPE, "");
      DataComponentType var3;
      DataComponentType var6 = var3 = (DataComponentType)RegistryExKt._get_/* $VF was: (get) */(
         var10000, (ResourceLocation)var0.getArguments().get(NativeParametersKt.getComponent_id())
      );
      Tag var8;
      if (var6 != null) {
         ComponentUtils var7 = ComponentUtils.INSTANCE;
         PatchedDataComponentMap var10002 = var1.getTag();
         Optional var11 = Optional.ofNullable(var10002 != null ? var10002.get(var3) : null);
         Intrinsics.checkNotNullExpressionValue(var11, "");
         var8 = var7.toFilteredNbtOrNull(var6, var11);
      } else {
         var8 = null;
      }

      Tag var5 = var8;
      Tag var10;
      if (var3 != null) {
         ComponentUtils var9 = ComponentUtils.INSTANCE;
         PatchedDataComponentMap var12 = var2.getTag();
         Optional var13 = Optional.ofNullable(var12 != null ? var12.get(var3) : null);
         Intrinsics.checkNotNullExpressionValue(var13, "");
         var10 = var9.toFilteredNbtOrNull(var3, var13);
      } else {
         var10 = null;
      }

      Tag var4 = var10;
      return ComparisonsKt.nullsLast(NbtComparatorRule::lambda$3$lambda$2).compare(var5, var4);
   }
}
