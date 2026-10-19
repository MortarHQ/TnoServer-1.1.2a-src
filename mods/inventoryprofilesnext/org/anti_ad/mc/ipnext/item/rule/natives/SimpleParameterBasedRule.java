package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.alias.registry.RegistryExKt;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.anti_ad.mc.ipnext.item.rule.ArgumentMap;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParametersKt;
import org.anti_ad.mc.ipnext.profiles.config.ProfilesConfigParserKt;

public final class SimpleParameterBasedRule extends BooleanBasedRule {
   public SimpleParameterBasedRule() {
      ArgumentMap var1;
      (var1 = this.getArguments())
         .defineParameter(NativeParametersKt.getComponent_id(), ProfilesConfigParserKt.get_asIdentifier_/* $VF was: get(asIdentifier) */("ipn:none"));
      var1.defineParameter(NativeParametersKt.getNbt(), new CompoundTag());
      var1.defineParameter(NativeParametersKt.getAllow_extra(), Boolean.TRUE);
      this.setValueOf(SimpleParameterBasedRule::_init_$lambda$1);
   }

   private static final boolean _init_$lambda$1(Rule var0, ItemType var1) {
      Tag var6;
      label20: {
         Intrinsics.checkNotNullParameter(var0, "");
         Intrinsics.checkNotNullParameter(var1, "");
         Registry var10000 = BuiltInRegistries.DATA_COMPONENT_TYPE;
         Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.DATA_COMPONENT_TYPE, "");
         DataComponentType var2;
         DataComponentType var4 = var2 = (DataComponentType)RegistryExKt._get_/* $VF was: (get) */(
            var10000, (ResourceLocation)var0.getArguments().get(NativeParametersKt.getComponent_id())
         );
         if (var4 != null) {
            ComponentUtils var5 = ComponentUtils.INSTANCE;
            PatchedDataComponentMap var10002 = var1.getTag();
            Optional var7 = Optional.ofNullable(var10002 != null ? var10002.get(var2) : null);
            Intrinsics.checkNotNullExpressionValue(var7, "");
            var6 = var5.toFilteredNbtOrNull(var4, var7);
            if (var6 != null) {
               break label20;
            }
         }

         var6 = (Tag)(new CompoundTag());
      }

      Tag var3 = var6;
      return var0.getArguments().get(NativeParametersKt.getAllow_extra())
         ? NbtUtils.INSTANCE.matchNbt((Tag)var0.getArguments().get(NativeParametersKt.getNbt()), var3)
         : NbtUtils.INSTANCE.matchNbtNoExtra((Tag)var0.getArguments().get(NativeParametersKt.getNbt()), var3);
   }
}
