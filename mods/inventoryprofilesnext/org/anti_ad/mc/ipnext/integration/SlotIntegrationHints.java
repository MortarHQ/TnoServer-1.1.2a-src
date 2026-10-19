package org.anti_ad.mc.ipnext.integration;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.StringSerializer;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JvmStreamsKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSlotIntegrationHints.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SlotIntegrationHints.kt\norg/anti_ad/mc/ipnext/integration/SlotIntegrationHints\n+ 2 JvmStreams.kt\nkotlinx/serialization/json/JvmStreamsKt\n+ 3 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,79:1\n80#2:80\n33#3:81\n89#3,4:82\n34#3:86\n*S KotlinDebug\n*F\n+ 1 SlotIntegrationHints.kt\norg/anti_ad/mc/ipnext/integration/SlotIntegrationHints\n*L\n73#1:80\n74#1:81\n74#1:82,4\n74#1:86\n*E\n"
)
public final class SlotIntegrationHints {
   @NotNull
   public static final SlotIntegrationHints INSTANCE = new SlotIntegrationHints();
   @NotNull
   private static final SlotIntegrationData DEFAULT_ENABLE = new SlotIntegrationData(false);
   @NotNull
   private static final String exampleFileName = "exampleIntegrationHints.json";
   @NotNull
   private static final String builtInHintsResource = "assets/inventoryprofilesnext/config/SlotIntegrationHints.json";
   private static Path externalHintsPath;
   private static Path configRoot;
   @NotNull
   private static Map hints = new LinkedHashMap();

   private SlotIntegrationHints() {
   }

   @NotNull
   public final SlotIntegrationData hintFor(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SlotIntegrationData var10000 = (SlotIntegrationData)hints.get(var1);
      if (var10000 == null) {
         var10000 = DEFAULT_ENABLE;
      }

      return var10000;
   }

   public final void init(@NotNull Path var1, @NotNull Path var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.reset();
      configRoot = var1;
      externalHintsPath = var2;
      this.doInit(var3);
   }

   private final void reset() {
      hints.clear();
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void doInit(boolean var1) {
      InputStream var10000 = SlotIntegrationHints.class.getClassLoader().getResourceAsStream("assets/inventoryprofilesnext/config/SlotIntegrationHints.json");
      if (var10000 != null) {
         var var2 = var10000;
         Throwable var3 = null;
         boolean var7 = false /* VF: Semaphore variable */;

         try {
            var7 = true;
            InputStream var4 = var2;
            INSTANCE.readConfig(var4, var1);
            var7 = false;
         } catch (Throwable var8) {
            var3 = var8;
            throw var8;
         } finally {
            if (var7) {
               CloseableKt.closeFinally(var2, var3);
            }
         }

         CloseableKt.closeFinally(var2, null);
      }
   }

   private final void readConfig(InputStream var1, boolean var2) {
      Json var10000 = MiscKt.getJson();
      var10000.getSerializersModule();
      Object var5 = JvmStreamsKt.decodeFromStream(
         var10000, (DeserializationStrategy)(new LinkedHashMapSerializer(StringSerializer.INSTANCE, SlotIntegrationData.Companion.serializer())), var1
      );

      try {
         var1.close();
      } catch (Throwable var3) {
      }

      Map var4 = (Map)var5;
      hints.putAll(var4);
   }
}
