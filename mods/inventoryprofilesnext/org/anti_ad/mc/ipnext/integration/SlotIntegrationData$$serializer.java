package org.anti_ad.mc.ipnext.integration;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.internal.BooleanSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import org.jetbrains.annotations.NotNull;

// $VF: synthetic class
@Deprecated(message = "This synthesized declaration should not be used directly", level = DeprecationLevel.HIDDEN)
public final class SlotIntegrationData$$serializer implements GeneratedSerializer {
   @NotNull
   public static final SlotIntegrationData$$serializer INSTANCE = new SlotIntegrationData$$serializer();
   @NotNull
   private static final SerialDescriptor descriptor;

   private SlotIntegrationData$$serializer() {
   }

   public final void serialize(@NotNull Encoder var1, @NotNull SlotIntegrationData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      SerialDescriptor var3 = descriptor;
      CompositeEncoder var4 = var1.beginStructure(var3);
      SlotIntegrationData.write$Self$neoforge_1_21(var2, var4, var3);
      var4.endStructure(var3);
   }

   @NotNull
   public final SlotIntegrationData deserialize(@NotNull Decoder var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SerialDescriptor var2 = descriptor;
      boolean var3 = true;
      byte var4 = 0;
      boolean var5 = false;
      CompositeDecoder var6;
      if ((var6 = var1.beginStructure(var2)).decodeSequentially()) {
         var5 = var6.decodeBooleanElement(var2, 0);
         var4 = 1;
      } else {
         while (var3) {
            int var7;
            switch (var7 = var6.decodeElementIndex(var2)) {
               case -1:
                  var3 = false;
                  break;
               case 0:
                  var5 = var6.decodeBooleanElement(var2, 0);
                  var4 |= 1;
                  break;
               default:
                  throw new UnknownFieldException(var7);
            }
         }
      }

      var6.endStructure(var2);
      return new SlotIntegrationData(var4, var5, null);
   }

   @NotNull
   public final SerialDescriptor getDescriptor() {
      return descriptor;
   }

   @NotNull
   public final KSerializer[] childSerializers() {
      KSerializer[] var1;
      (var1 = new KSerializer[1])[0] = BooleanSerializer.INSTANCE;
      return var1;
   }

   static {
      PluginGeneratedSerialDescriptor var0;
      (var0 = new PluginGeneratedSerialDescriptor("org.anti_ad.mc.ipnext.integration.SlotIntegrationData", INSTANCE, 1)).addElement("ignore", true);
      descriptor = (SerialDescriptor)var0;
   }
}
