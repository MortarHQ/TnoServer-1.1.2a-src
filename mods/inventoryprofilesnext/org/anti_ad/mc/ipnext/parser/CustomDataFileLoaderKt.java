package org.anti_ad.mc.ipnext.parser;

import java.nio.file.Path;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.util.LogicalStringComparator;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.jetbrains.annotations.NotNull;

public final class CustomDataFileLoaderKt {
   @NotNull
   private static final LogicalStringComparator strCmpLogical = (LogicalStringComparator)LogicalStringComparator.Companion.getFile().invoke();
   @NotNull
   private static final Path configFolder = VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext");
   @NotNull
   private static final List definedLoaders;

   private static final List getFiles(String var0) {
      return CollectionsKt.sortedWith(Java_ioKt.listFiles(configFolder, var0), CustomDataFileLoaderKt::getFiles$lambda$1);
   }

   private static final int getFiles$lambda$0(Path var0, Path var1) {
      LogicalStringComparator var10000 = strCmpLogical;
      Intrinsics.checkNotNull(var0);
      String var10001 = Java_ioKt.getName(var0);
      Intrinsics.checkNotNull(var1);
      return var10000.compare(var10001, Java_ioKt.getName(var1));
   }

   private static final int getFiles$lambda$1(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   static {
      Loader[] var0;
      (var0 = new Loader[7])[0] = LockSlotsLoader.INSTANCE;
      var0[1] = RefillSlotsLoader.INSTANCE;
      var0[2] = ProfilesLoader.INSTANCE;
      var0[3] = RuleLoader.INSTANCE;
      var0[4] = HintsLoader.INSTANCE;
      var0[5] = VillagerBookmarksLoader.INSTANCE;
      var0[6] = SlotSettingsLoader.INSTANCE;
      definedLoaders = CollectionsKt.listOf(var0);
   }
}
