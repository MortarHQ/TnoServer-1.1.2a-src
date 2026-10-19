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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import org.jetbrains.annotations.NotNull;

// $VF: synthetic class
@Deprecated(message = "This synthesized declaration should not be used directly", level = DeprecationLevel.HIDDEN)
public final class ButtonPositionHint$$serializer implements GeneratedSerializer {
   @NotNull
   public static final ButtonPositionHint$$serializer INSTANCE = new ButtonPositionHint$$serializer();
   @NotNull
   private static final SerialDescriptor descriptor;

   private ButtonPositionHint$$serializer() {
   }

   public final void serialize(@NotNull Encoder var1, @NotNull ButtonPositionHint var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      SerialDescriptor var3 = descriptor;
      CompositeEncoder var4 = var1.beginStructure(var3);
      ButtonPositionHint.write$Self$neoforge_1_21(var2, var4, var3);
      var4.endStructure(var3);
   }

   @NotNull
   public final ButtonPositionHint deserialize(@NotNull Decoder var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SerialDescriptor var2 = descriptor;
      boolean var3 = true;
      byte var4 = 0;
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;
      CompositeDecoder var9;
      if ((var9 = var1.beginStructure(var2)).decodeSequentially()) {
         var5 = var9.decodeIntElement(var2, 0);
         var6 = var9.decodeIntElement(var2, 1);
         var7 = var9.decodeIntElement(var2, 2);
         var8 = var9.decodeBooleanElement(var2, 3);
         var4 = 15;
      } else {
         while (var3) {
            int var10;
            switch (var10 = var9.decodeElementIndex(var2)) {
               case -1:
                  var3 = false;
                  break;
               case 0:
                  var5 = var9.decodeIntElement(var2, 0);
                  var4 |= 1;
                  break;
               case 1:
                  var6 = var9.decodeIntElement(var2, 1);
                  var4 |= 2;
                  break;
               case 2:
                  var7 = var9.decodeIntElement(var2, 2);
                  var4 |= 4;
                  break;
               case 3:
                  var8 = var9.decodeBooleanElement(var2, 3);
                  var4 |= 8;
                  break;
               default:
                  throw new UnknownFieldException(var10);
            }
         }
      }

      var9.endStructure(var2);
      return new ButtonPositionHint(var4, var5, var6, var7, var8, null);
   }

   @NotNull
   public final SerialDescriptor getDescriptor() {
      return descriptor;
   }

   @NotNull
   public final KSerializer[] childSerializers() {
      KSerializer[] var1;
      (var1 = new KSerializer[4])[0] = IntSerializer.INSTANCE;
      var1[1] = IntSerializer.INSTANCE;
      var1[2] = IntSerializer.INSTANCE;
      var1[3] = BooleanSerializer.INSTANCE;
      return var1;
   }

   static {
      PluginGeneratedSerialDescriptor var0;
      (var0 = new PluginGeneratedSerialDescriptor("org.anti_ad.mc.ipnext.integration.ButtonPositionHint", INSTANCE, 4)).addElement("horizontalOffset", true);
      var0.addElement("top", true);
      var0.addElement("bottom", true);
      var0.addElement("hide", true);
      descriptor = (SerialDescriptor)var0;
   }
}
