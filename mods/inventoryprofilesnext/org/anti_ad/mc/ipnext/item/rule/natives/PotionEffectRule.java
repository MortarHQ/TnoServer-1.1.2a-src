package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.PotionEffect;

@SourceDebugExtension(
   "SMAP\nNativeRule.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/PotionEffectRule\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,456:1\n417#2:457\n414#2:458\n421#2,3:462\n414#2:466\n421#2,3:469\n1563#3:459\n1634#3,2:460\n1636#3:465\n1634#3,2:467\n1636#3:472\n*S KotlinDebug\n*F\n+ 1 NativeRule.kt\norg/anti_ad/mc/ipnext/item/rule/natives/PotionEffectRule\n*L\n452#1:457\n452#1:458\n452#1:462,3\n452#1:466\n452#1:469,3\n452#1:459\n452#1:460,2\n452#1:465\n452#1:467,2\n452#1:472\n*E\n"
)
public final class PotionEffectRule extends NativeRule {
   public PotionEffectRule() {
      this.setComparator(PotionEffectRule::_init_$lambda$0);
   }

   private static final int _init_$lambda$0(ItemType var0, ItemType var1) {
      Iterable var20;
      label45: {
         Intrinsics.checkNotNullParameter(var0, "");
         Intrinsics.checkNotNullParameter(var1, "");
         PatchedDataComponentMap var10000 = var0.getTag();
         if (var10000 != null) {
            PotionContents var18 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
            if (var18 != null) {
               var20 = var18.getAllEffects();
               if (var20 != null) {
                  var20 = CollectionsKt.toList(var20);
                  if (var20 != null) {
                     break label45;
                  }
               }
            }
         }

         var20 = CollectionsKt.emptyList();
      }

      Iterable var5;
      Iterable var2 = var5 = var20;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (MobEffectInstance var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         var4.add(new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var3.getEffect().value())), var3.getAmplifier(), var3.getDuration()));
      }

      label32: {
         var20 = var6;
         var0 = var1;
         var10 = var20;
         PatchedDataComponentMap var22 = var0.getTag();
         if (var22 != null) {
            PotionContents var23 = (PotionContents)var22.get(DataComponents.POTION_CONTENTS);
            if (var23 != null) {
               var20 = var23.getAllEffects();
               if (var20 != null) {
                  var20 = CollectionsKt.toList(var20);
                  if (var20 != null) {
                     break label32;
                  }
               }
            }
         }

         var20 = CollectionsKt.emptyList();
      }

      Iterable var8;
      var2 = var8 = var20;
      var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));

      for (MobEffectInstance var15 : var2) {
         var15 = var15;
         ArrayList var17 = var9;
         var17.add(new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var15.getEffect().value())), var15.getAmplifier(), var15.getDuration()));
      }

      return Kt_commonKt.compareTo(var10, var9);
   }
}
