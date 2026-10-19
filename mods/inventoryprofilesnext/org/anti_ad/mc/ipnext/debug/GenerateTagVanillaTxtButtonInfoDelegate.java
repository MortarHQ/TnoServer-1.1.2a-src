package org.anti_ad.mc.ipnext.debug;

import com.mojang.datafixers.util.Pair;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.anti_ad.mc.common.LogBase;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nGenerateTagVanillaTxtButtonInfoDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GenerateTagVanillaTxtButtonInfoDelegate.kt\norg/anti_ad/mc/ipnext/debug/GenerateTagVanillaTxtButtonInfoDelegate\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Log.kt\norg/anti_ad/mc/common/LogBase\n*L\n1#1,105:1\n1869#2,2:106\n1869#2:109\n1869#2,2:110\n1870#2:112\n1#3:108\n113#4,8:113\n*S KotlinDebug\n*F\n+ 1 GenerateTagVanillaTxtButtonInfoDelegate.kt\norg/anti_ad/mc/ipnext/debug/GenerateTagVanillaTxtButtonInfoDelegate\n*L\n47#1:106,2\n71#1:109\n73#1:110,2\n71#1:112\n86#1:113,8\n*E\n"
)
public final class GenerateTagVanillaTxtButtonInfoDelegate extends ConfigButtonClickHandler {
   @NotNull
   public static final GenerateTagVanillaTxtButtonInfoDelegate INSTANCE = new GenerateTagVanillaTxtButtonInfoDelegate();
   @NotNull
   private static final Path fileDatapack = Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "tags.combined.txt");

   private GenerateTagVanillaTxtButtonInfoDelegate() {
   }

   @NotNull
   public final Path getFileDatapack() {
      return fileDatapack;
   }

   private final List toMutableListOf(Named var1) {
      List var2 = new ArrayList();

      for (Holder var3 : (Iterable)var1) {
         ResourceLocation var5;
         if (!Intrinsics.areEqual(var5 = BuiltInRegistries.ITEM.getKey(var3.value()), BuiltInRegistries.ITEM.getDefaultKey())) {
            Intrinsics.checkNotNull(var5);
            var2.add(var5);
         }
      }

      return var2;
   }

   public final void onClick(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      TellPlayer.INSTANCE.chat("Generating " + Java_ioKt.getName(fileDatapack) + " ...");
      if (Vanilla.INSTANCE.server() == null) {
         TellPlayer.INSTANCE.chat("This works best in single player game... Giving up!");
      } else {
         Map var6 = new LinkedHashMap();
         BuiltInRegistries.ITEM.getTags().forEach(GenerateTagVanillaTxtButtonInfoDelegate::onClick$lambda$4);
         Path var2 = fileDatapack;
         OpenOption[] var4 = new OpenOption[0];
         Charset var3 = Charsets.UTF_8;
         BufferedWriter var8 = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var2, Arrays.copyOf(var4, var4.length)), var3), 8192);

         for (ResourceLocation var9 : CollectionsKt.sorted(var6.keySet())) {
            var8.append("#" + INSTANCE.getOmittedString(var9)).append('\n');
            List var10000 = (List)var6.get(var9);
            if (var10000 != null) {
               var10000 = CollectionsKt.sorted(var10000);
               if (var10000 != null) {
                  for (ResourceLocation var5 : var10000) {
                     var8.append("    " + INSTANCE.getOmittedString(var5)).append('\n');
                  }
               }
            }
         }

         var8.close();
         Log.INSTANCE.traceIf(GenerateTagVanillaTxtButtonInfoDelegate::onClick$lambda$15);
      }
   }

   private final String getOmittedString(ResourceLocation var1) {
      if (Intrinsics.areEqual(var1.getNamespace(), "minecraft")) {
         String var2 = var1.getPath();
         Intrinsics.checkNotNullExpressionValue(var2, "");
         return var2;
      } else {
         String var10000 = var1.toString();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         return var10000;
      }
   }

   private final String getOmittedString(String var1) {
      return StringsKt.removePrefix(var1, "minecraft:");
   }

   private static final Unit onClick$lambda$3(Map var0, Pair var1) {
      ResourceLocation var2 = ((TagKey)var1.getFirst()).location();
      GenerateTagVanillaTxtButtonInfoDelegate var10000 = INSTANCE;
      Object var10001 = var1.getSecond();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      List var3 = var10000.toMutableListOf((Named)var10001);
      var0.put(var2, var3);
      return Unit.INSTANCE;
   }

   private static final void onClick$lambda$4(Function1 var0, Object var1) {
      var0.invoke(var1);
   }

   private static final String onClick$lambda$15$lambda$13$lambda$8(Pair var0) {
      return ((TagKey)var0.getFirst()).location() + " ->";
   }

   private static final String onClick$lambda$15$lambda$13$lambda$12$lambda$10$lambda$9(Holder var0) {
      return var0.unwrapKey() + ")";
   }

   private static final Unit onClick$lambda$15$lambda$13$lambda$12$lambda$10(Holder var0) {
      Log.INSTANCE.trace(GenerateTagVanillaTxtButtonInfoDelegate::onClick$lambda$15$lambda$13$lambda$12$lambda$10$lambda$9);
      return Unit.INSTANCE;
   }

   private static final void onClick$lambda$15$lambda$13$lambda$12$lambda$11(Function1 var0, Object var1) {
      var0.invoke(var1);
   }

   private static final Unit onClick$lambda$15$lambda$13(Pair var0) {
      Log.INSTANCE.trace(GenerateTagVanillaTxtButtonInfoDelegate::onClick$lambda$15$lambda$13$lambda$8);
      LogBase var1 = Log.INSTANCE;

      for (int var2 = 0; var2 < 4; var2++) {
         var1.indent();
      }

      ((Named)var0.getSecond()).stream().forEach(GenerateTagVanillaTxtButtonInfoDelegate::onClick$lambda$15$lambda$13$lambda$12$lambda$11);

      for (int var3 = 0; var3 < 4; var3++) {
         var1.unindent();
      }

      return Unit.INSTANCE;
   }

   private static final void onClick$lambda$15$lambda$14(Function1 var0, Object var1) {
      var0.invoke(var1);
   }

   private static final Unit onClick$lambda$15() {
      BuiltInRegistries.ITEM.getTags().forEach(GenerateTagVanillaTxtButtonInfoDelegate::onClick$lambda$15$lambda$14);
      return Unit.INSTANCE;
   }
}
