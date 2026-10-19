package org.anti_ad.mc.ipnext.integration;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.StringSerializer;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JvmStreamsKt;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.ipn.api.IPNGuiHint;
import org.anti_ad.mc.ipn.api.IPNIgnore;
import org.anti_ad.mc.ipn.api.IPNPlayerSideOnly;
import org.anti_ad.mc.ipn.api.IPNSlotsIgnoreForInventoryTypes;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nHintsManagerNG.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HintsManagerNG.kt\norg/anti_ad/mc/ipnext/integration/HintsManagerNG\n+ 2 JvmStreams.kt\nkotlinx/serialization/json/JvmStreamsKt\n+ 3 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 5 Misc.kt\norg/anti_ad/mc/ipnext/integration/MiscKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 7 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 8 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,336:1\n80#2:337\n80#2:350\n42#2:369\n42#2:394\n33#3:338\n89#3,4:339\n34#3:343\n33#3:351\n89#3,4:352\n34#3:356\n33#3:373\n89#3,4:374\n34#3:378\n33#3:379\n89#3,4:380\n34#3:384\n216#4,2:344\n216#4,2:346\n216#4:357\n216#4:358\n217#4:360\n217#4:361\n216#4,2:364\n216#4,2:366\n216#4:368\n217#4:370\n216#4,2:392\n216#4,2:397\n41#5,2:348\n43#5,2:362\n41#5,2:395\n43#5,2:399\n1#6:359\n13472#7,2:371\n506#8,7:385\n*S KotlinDebug\n*F\n+ 1 HintsManagerNG.kt\norg/anti_ad/mc/ipnext/integration/HintsManagerNG\n*L\n76#1:337\n132#1:350\n181#1:369\n322#1:394\n77#1:338\n77#1:339,4\n77#1:343\n134#1:351\n134#1:352,4\n134#1:356\n269#1:373\n269#1:374,4\n269#1:378\n276#1:379\n276#1:380,4\n276#1:384\n117#1:344,2\n121#1:346,2\n136#1:357\n137#1:358\n137#1:360\n136#1:361\n160#1:364,2\n165#1:366,2\n176#1:368\n176#1:370\n291#1:392,2\n97#1:397,2\n131#1:348,2\n131#1:362,2\n95#1:395,2\n95#1:399,2\n209#1:371,2\n289#1:385,7\n*E\n"
)
public final class HintsManagerNG {
   @NotNull
   public static final HintsManagerNG INSTANCE = new HintsManagerNG();
   @NotNull
   private static final String exampleFileName = "exampleIntegrationHints.json";
   @NotNull
   private static final String builtInHintsResource = "assets/inventoryprofilesnext/config/ModIntegrationHintsNG.json";
   @NotNull
   private static final String hintsExport = "ModIntegrationExport.json";
   @NotNull
   private static final String exampleHintsResource = "assets/inventoryprofilesnext/config/exampleIntegrationHints.json";
   @NotNull
   private static final String integratedOverride = "ModIntegrationOverride.json";
   private static Path externalHintsPath;
   private static Path configRoot;
   @NotNull
   private static final Map externalConfigs = new LinkedHashMap();
   @NotNull
   private static final Map internalConfigs = new LinkedHashMap();
   @NotNull
   private static final Map effectiveHints = new LinkedHashMap();

   private HintsManagerNG() {
   }

   private final Map processConfig(InputStream var1) {
      Json var10000 = MiscKt.getJson();
      var10000.getSerializersModule();
      Object var2 = JvmStreamsKt.decodeFromStream(
         var10000, (DeserializationStrategy)(new LinkedHashMapSerializer(StringSerializer.INSTANCE, HintClassData.Companion.serializer())), var1
      );

      try {
         var1.close();
      } catch (Throwable var3) {
      }

      return (Map)var2;
   }

   public final void init(@NotNull Path var1, @NotNull Path var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.reset();
      configRoot = var1;
      externalHintsPath = var2;
      this.doInit(var3);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void doInit(boolean var1) {
      Path var10000 = externalHintsPath;
      if (externalHintsPath == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      Path var2 = var10000;
      LinkOption[] var3 = new LinkOption[0];
      if (Files.isDirectory(var2, Arrays.copyOf(var3, var3.length))) {
         var10000 = externalHintsPath;
         if (externalHintsPath == null) {
            Intrinsics.throwUninitializedPropertyAccessException("");
            var10000 = null;
         }

         BiPredicate var10002 = HintsManagerNG::doInit$lambda$2;
         FileVisitOption[] var20;
         (var20 = new FileVisitOption[1])[0] = FileVisitOption.FOLLOW_LINKS;
         Files.find(var10000, 1, var10002, var20).forEach(HintsManagerNG::doInit$lambda$7);
      }

      var10000 = configRoot;
      if (configRoot == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      if (Java_ioKt.exists(var2 = Java_ioKt.div(var10000, "ModIntegrationOverride.json"))) {
         OpenOption[] var10001 = new OpenOption[0];
         InputStream var40 = Files.newInputStream(var2, Arrays.copyOf(var10001, var10001.length));
         Intrinsics.checkNotNullExpressionValue(var40, "");
         var var26 = var40;
         Throwable var22 = null;
         boolean var15 = false /* VF: Semaphore variable */;

         try {
            var15 = true;
            InputStream var4 = var26;
            INSTANCE.readInternalConfig(var4, var1);
            var15 = false;
         } catch (Throwable var18) {
            var22 = var18;
            throw var18;
         } finally {
            if (var15) {
               CloseableKt.closeFinally(var26, var22);
            }
         }

         CloseableKt.closeFinally(var26, null);
      } else {
         InputStream var41 = HintsManagerNG.class.getClassLoader().getResourceAsStream("assets/inventoryprofilesnext/config/ModIntegrationHintsNG.json");
         if (var41 != null) {
            var var23 = var41;
            Throwable var31 = null;
            boolean var10 = false /* VF: Semaphore variable */;

            try {
               var10 = true;
               InputStream var5 = var23;
               INSTANCE.readInternalConfig(var5, var1);
               var10 = false;
            } catch (Throwable var16) {
               var31 = var16;
               throw var16;
            } finally {
               if (var10) {
                  CloseableKt.closeFinally(var23, var31);
               }
            }

            CloseableKt.closeFinally(var23, null);
         }
      }

      Log.INSTANCE.info("Loaded " + externalConfigs.size() + " external GUI hints");
      Iterator var32 = externalConfigs.entrySet().iterator();

      while (var32.hasNext()) {
         Entry var27;
         String var24 = (String)(var27 = (Entry)var32.next()).getKey();
         HintClassData var28 = (HintClassData)var27.getValue();
         effectiveHints.putIfAbsent(var24, var28);
      }

      Log.INSTANCE.info("Loaded " + internalConfigs.size() + " build-in GUI hints");
      var32 = internalConfigs.entrySet().iterator();

      while (var32.hasNext()) {
         Entry var29;
         String var25 = (String)(var29 = (Entry)var32.next()).getKey();
         HintClassData var30 = (HintClassData)var29.getValue();
         effectiveHints.putIfAbsent(var25, var30);
      }

      if (var1) {
         TellPlayer.INSTANCE
            .chat("Found " + effectiveHints.size() + " hints. " + internalConfigs.size() + " internal and " + externalConfigs.size() + " external");
      }

      Log.INSTANCE.info("Effective GUI hints after merge: " + effectiveHints.size());
   }

   private final void readInternalConfig(InputStream var1, boolean var2) {
      String var3 = "";

      try {
         Json var10000 = MiscKt.getJson();
         var10000.getSerializersModule();
         Object var4 = JvmStreamsKt.decodeFromStream(
            var10000,
            (DeserializationStrategy)(
               new LinkedHashMapSerializer(
                  StringSerializer.INSTANCE, new LinkedHashMapSerializer(StringSerializer.INSTANCE, HintClassData.Companion.serializer())
               )
            ),
            var1
         );

         try {
            var1.close();
         } catch (Throwable var9) {
         }

         Iterator var5 = ((Map)var4).entrySet().iterator();

         while (var5.hasNext()) {
            for (Entry var6 : ((Map)(var11 = (Entry)var5.next()).getValue()).entrySet()) {
               Map var7 = internalConfigs;
               Object var8 = var6.getKey();
               Object var13;
               ((HintClassData)(var13 = var6.getValue())).changeId((String)var11.getKey());
               var7.put(var8, var13);
            }
         }
      } catch (Throwable var10) {
         MiscKt.logError(var10, var3, var2);
      }
   }

   public final void saveAllAsIntegrated(@NotNull MergePriority var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SortedMap var3 = MapsKt.toSortedMap(this.collectAllWithPriority(var1));
      Path var10000 = configRoot;
      if (configRoot == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      Path var2;
      Files.deleteIfExists(var2 = Java_ioKt.div(var10000, "ModIntegrationExport.json"));
      OpenOption[] var10001 = new OpenOption[0];
      OutputStream var5 = Files.newOutputStream(var2, Arrays.copyOf(var10001, var10001.length));
      Intrinsics.checkNotNullExpressionValue(var5, "");
      OutputStream var4 = var5;
      JvmStreamsKt.encodeToStream(
         MiscKt.getJson(),
         (SerializationStrategy)BuiltinSerializersKt.MapSerializer(
            BuiltinSerializersKt.serializer(StringCompanionObject.INSTANCE),
            BuiltinSerializersKt.MapSerializer(BuiltinSerializersKt.serializer(StringCompanionObject.INSTANCE), HintClassData.Companion.serializer())
         ),
         var3,
         var4
      );
      var4.close();
   }

   private final Map collectAllWithPriority(MergePriority var1) {
      Map var2 = var1 == MergePriority.EXTERNAL ? externalConfigs : internalConfigs;
      Map var7 = var1 == MergePriority.EXTERNAL ? internalConfigs : externalConfigs;
      Map var3 = new LinkedHashMap();
      Set var4 = new LinkedHashSet();
      Iterator var8 = var2.entrySet().iterator();

      while (var8.hasNext()) {
         Entry var5;
         String var6 = (String)(var5 = (Entry)var8.next()).getKey();
         HintClassData var10 = (HintClassData)var5.getValue();
         String var10001 = var10.readId();
         Intrinsics.checkNotNull(var10001);
         Map var10000 = var3.putIfAbsent(var10001, new LinkedHashMap());
         if (var10000 == null) {
            var10001 = var10.readId();
            Intrinsics.checkNotNull(var10001);
            var10000 = (Map)var3.get(var10001);
         }

         Intrinsics.checkNotNull(var10000);
         var10000.putIfAbsent(var6, HintClassData.copy$default(var10, false, false, false, var10.copyOnlyChanged(), null, false, false, false, 247, null));
         var4.add(var6);
      }

      Iterator var9 = var7.entrySet().iterator();

      while (var9.hasNext()) {
         Entry var11;
         String var13 = (String)(var11 = (Entry)var9.next()).getKey();
         HintClassData var12 = (HintClassData)var11.getValue();
         if (!var4.contains(var13)) {
            String var16 = var12.readId();
            Intrinsics.checkNotNull(var16);
            Map var14 = var3.putIfAbsent(var16, new LinkedHashMap());
            if (var14 == null) {
               var16 = var12.readId();
               Intrinsics.checkNotNull(var16);
               var14 = (Map)var3.get(var16);
            }

            Intrinsics.checkNotNull(var14);
            var14.putIfAbsent(var13, HintClassData.copy$default(var12, false, false, false, var12.copyOnlyChanged(), null, false, false, false, 247, null));
         }
      }

      return var3;
   }

   public final void saveAllAsSeparate(@NotNull MergePriority var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterator var5 = this.collectAllWithPriority(var1).entrySet().iterator();

      while (var5.hasNext()) {
         Entry var2;
         String var3 = (String)(var2 = (Entry)var5.next()).getKey();
         Map var6 = (Map)var2.getValue();
         Path var10000 = externalHintsPath;
         if (externalHintsPath == null) {
            Intrinsics.throwUninitializedPropertyAccessException("");
            var10000 = null;
         }

         Path var7;
         Files.deleteIfExists(var7 = Java_ioKt.div(var10000, var3 + ".json"));
         OpenOption[] var10001 = new OpenOption[0];
         OutputStream var8 = Files.newOutputStream(var7, Arrays.copyOf(var10001, var10001.length));
         Intrinsics.checkNotNullExpressionValue(var8, "");
         OutputStream var4 = var8;
         TellPlayer.INSTANCE.chat("Generating " + PathsKt.getName(var7));
         Json var9 = MiscKt.getJson();
         var9.getSerializersModule();
         JvmStreamsKt.encodeToStream(
            var9, (SerializationStrategy)(new LinkedHashMapSerializer(StringSerializer.INSTANCE, HintClassData.Companion.serializer())), var6, var4
         );
      }
   }

   @NotNull
   public final HintClassData getHints(@NotNull Class var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      HintClassData var2;
      HintClassData var10000 = (var2 = (HintClassData)effectiveHints.get(var1.getName())) == null
            || var2.getForce()
            || !var1.isAnnotationPresent(IPNIgnore.class)
               && !var1.isAnnotationPresent(IPNPlayerSideOnly.class)
               && !var1.isAnnotationPresent(IPNGuiHint.class)
               && !var1.isAnnotationPresent(IPNSlotsIgnoreForInventoryTypes.class)
         ? var2
         : null;
      if (var10000 == null) {
         HintsManagerNG var8 = INSTANCE;
         Class var17 = INSTANCE.getIgnoredClass(var1);
         Class var18;
         if (var17 != null) {
            Class var3 = var17;
            Class var4 = var17;
            if (!Intrinsics.areEqual(var17, var1)) {
               var8.getHints(var4);
            }

            var18 = var3;
         } else {
            var18 = null;
         }

         boolean var5 = var18 != null;
         Map var6 = new LinkedHashMap();
         Annotation[] var19 = var1.getAnnotationsByType(IPNGuiHint.class);
         Intrinsics.checkNotNullExpressionValue(var19, "");
         Object[] var12 = var19;
         int var9 = 0;

         for (int var14 = var12.length; var9 < var14; var9++) {
            IPNGuiHint var7 = (IPNGuiHint)var12[var9];
            Log.INSTANCE.trace("Found @IPNGuiHint for button: " + var7.button().name());
            var6.put(var7.button(), new ButtonPositionHint(var7.horizontalOffset(), var7.top(), var7.bottom(), var7.hide()));
         }

         label73: {
            var13 = var1.isAnnotationPresent(IPNPlayerSideOnly.class);
            Annotation[] var20 = var1.getAnnotationsByType(IPNSlotsIgnoreForInventoryTypes.class);
            Intrinsics.checkNotNullExpressionValue(var20, "");
            IPNSlotsIgnoreForInventoryTypes var21 = (IPNSlotsIgnoreForInventoryTypes)ArraysKt.firstOrNull(var20);
            if (var21 != null) {
               String[] var22 = var21.value();
               if (var22 != null) {
                  var23 = ArraysKt.toMutableSet(var22);
                  if (var23 != null) {
                     break label73;
                  }
               }
            }

            var23 = new LinkedHashSet();
         }

         Set var15 = var23;
         Annotation[] var24 = var1.getAnnotationsByType(IPNSlotsIgnoreForInventoryTypes.class);
         Intrinsics.checkNotNullExpressionValue(var24, "");
         IPNSlotsIgnoreForInventoryTypes var25 = (IPNSlotsIgnoreForInventoryTypes)ArraysKt.firstOrNull(var24);
         boolean var10 = var25 != null ? var25.ignoreCraftingSlots() : false;
         (var2 = !var5 && !var13 && !var10 && var6.isEmpty() && var15.isEmpty()
               ? new HintClassData(false, false, false, null, null, false, false, false, 255, null)
               : new HintClassData(var5, var13, false, var6, var15, var10, false, false, 192, null))
            .fillMissingHints();
         HintClassData var16 = var2;
         effectiveHints.put(var1.getName(), var16);
         var10000 = var16;
      }

      return var10000;
   }

   private final Class getIgnoredClass(Class var1) {
      while (!Intrinsics.areEqual(var1, Object.class)) {
         if (var1.isAnnotationPresent(IPNIgnore.class)) {
            return var1;
         }

         Class var10000 = var1.getSuperclass();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         var1 = var10000;
      }

      return null;
   }

   private final void reset() {
      externalConfigs.clear();
      internalConfigs.clear();
      effectiveHints.clear();
   }

   public final boolean isPlayerSideOnly(@Nullable Class var1) {
      return var1 != null && this.getHints(var1).getPlayerSideOnly();
   }

   public final boolean isFastSwipeDisabled(@Nullable Class var1) {
      return var1 != null && this.getHints(var1).getDisableFastSwipe();
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void upgradeOldConfig(@NotNull Path var1, @NotNull Path var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (Java_ioKt.exists(var1)) {
         try {
            Path var3 = var1;
            Path var4 = Java_ioKt.div(var2, "upgraded-From-Pre-v1.2.5.json");
            CopyOption[] var27 = new CopyOption[0];
            Intrinsics.checkNotNullExpressionValue(Files.move(var3, var4, Arrays.copyOf(var27, var27.length)), "");
         } catch (Throwable var26) {
         }
      }

      Path var10000 = var1 = Java_ioKt.div(var2, "exampleIntegrationHints.json");
      Object[] var10001 = new LinkOption[0];
      if (Files.notExists(var10000, Arrays.copyOf(var10001, var10001.length))) {
         InputStream var38 = HintsManagerNG.class.getClassLoader().getResourceAsStream("assets/inventoryprofilesnext/config/exampleIntegrationHints.json");
         if (var38 != null) {
            var var30 = var38;
            Throwable var31 = null;
            boolean var13 = false /* VF: Semaphore variable */;

            try {
               var13 = true;
               InputStream var32 = var30;

               try {
                  var10001 = new OpenOption[0];
                  OutputStream var39 = Files.newOutputStream(var1, Arrays.copyOf(var10001, var10001.length));
                  Intrinsics.checkNotNullExpressionValue(var39, "");
                  var var29 = var39;
                  Throwable var5 = null;
                  boolean var20 = false /* VF: Semaphore variable */;

                  try {
                     var20 = true;
                     OutputStream var6 = var29;
                     ByteStreamsKt.copyTo$default(var32, var6, 0, 2, null);
                     var6.close();
                     var20 = false;
                  } catch (Throwable var21) {
                     var5 = var21;
                     throw var21;
                  } finally {
                     if (var20) {
                        CloseableKt.closeFinally(var29, var5);
                     }
                  }

                  CloseableKt.closeFinally(var29, null);
                  var32.close();
                  var13 = false;
               } catch (Throwable var23) {
                  var13 = false;
               }
            } catch (Throwable var24) {
               var31 = var24;
               throw var24;
            } finally {
               if (var13) {
                  CloseableKt.closeFinally(var30, var31);
               }
            }

            CloseableKt.closeFinally(var30, null);
            return;
         }
      }
   }

   private final Map getAllById(String var1) {
      Map var2;
      Map var3 = var2 = new LinkedHashMap();
      Map var4 = effectiveHints;
      LinkedHashMap var5 = new LinkedHashMap();
      Iterator var9 = var4.entrySet().iterator();

      while (var9.hasNext()) {
         Entry var6;
         HintClassData var7 = (HintClassData)(var6 = (Entry)var9.next()).getValue();
         if (Intrinsics.areEqual(var1, var7.readId())) {
            var5.put(var6.getKey(), var6.getValue());
         }
      }

      Iterator var10 = var5.entrySet().iterator();

      while (var10.hasNext()) {
         Entry var11;
         if (((HintClassData)(var11 = (Entry)var10.next()).getValue()).hasInfo()) {
            Map var8 = ((HintClassData)var11.getValue()).copyOnlyChanged();
            var3.put(
               var11.getKey(), HintClassData.copy$default((HintClassData)var11.getValue(), false, false, false, var8, null, false, false, false, 247, null)
            );
         }
      }

      return var2;
   }

   public final void saveDirty(@NotNull HintClassData var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (Intrinsics.areEqual(var1.readId(), var2.readId())) {
         String var10001 = var1.readId();
         Intrinsics.checkNotNull(var10001);
         String var10003 = var1.readId();
         Intrinsics.checkNotNull(var10003);
         this.saveFile(var10001, this.getAllById(var10003));
      } else {
         String var3 = var1.readId();
         Intrinsics.checkNotNull(var3);
         String var5 = var1.readId();
         Intrinsics.checkNotNull(var5);
         this.saveFile(var3, this.getAllById(var5));
         var3 = var2.readId();
         Intrinsics.checkNotNull(var3);
         var5 = var2.readId();
         Intrinsics.checkNotNull(var5);
         this.saveFile(var3, this.getAllById(var5));
      }

      this.reset();
      this.doInit(false);
   }

   private final void saveFile(String var1, Map var2) {
      String var10000 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      var1 = !StringsKt.endsWith$default(var10000, ".json", false, 2, null) ? var1 + ".json" : var1;
      Path var7 = externalHintsPath;
      if (externalHintsPath == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var7 = null;
      }

      Path var5;
      Files.deleteIfExists(var5 = Java_ioKt.div(var7, var1));
      if (!var2.isEmpty()) {
         Json var3 = MiscKt.getJson();
         OpenOption[] var10001 = new OpenOption[0];
         OutputStream var8 = Files.newOutputStream(var5, Arrays.copyOf(var10001, var10001.length));
         Intrinsics.checkNotNullExpressionValue(var8, "");
         OutputStream var6 = var8;
         var3.getSerializersModule();
         JvmStreamsKt.encodeToStream(
            var3, (SerializationStrategy)(new LinkedHashMapSerializer(StringSerializer.INSTANCE, HintClassData.Companion.serializer())), var2, var6
         );
      }
   }

   private static final boolean doInit$lambda$2(Path var0, BasicFileAttributes var1) {
      return var1.isRegularFile()
         && StringsKt.endsWith$default(var0.getFileName().toString(), ".json", false, 2, null)
         && !Intrinsics.areEqual(var0.getFileName().toString(), "exampleIntegrationHints.json");
   }

   private static final Unit doInit$lambda$6(boolean var0, Path var1) {
      Intrinsics.checkNotNull(var1);
      String var2 = StringsKt.substringBeforeLast$default(PathsKt.getName(var1), ".json", null, 2, null);

      try {
         HintsManagerNG var10000 = INSTANCE;
         OpenOption[] var10002 = new OpenOption[0];
         InputStream var10001 = Files.newInputStream(var1, Arrays.copyOf(var10002, var10002.length));
         Intrinsics.checkNotNullExpressionValue(var10001, "");

         for (Entry var3 : var10000.processConfig(var10001).entrySet()) {
            Map var4 = externalConfigs;
            Object var5 = var3.getKey();
            HintClassData var6;
            Object var9;
            (var6 = (HintClassData)(var9 = var3.getValue())).changeId(var2);
            var6.fillMissingHints();
            var4.put(var5, var9);
         }
      } catch (Throwable var7) {
         MiscKt.logError(var7, var2, var0);
      }

      return Unit.INSTANCE;
   }

   private static final void doInit$lambda$7(Function1 var0, Object var1) {
      var0.invoke(var1);
   }
}
