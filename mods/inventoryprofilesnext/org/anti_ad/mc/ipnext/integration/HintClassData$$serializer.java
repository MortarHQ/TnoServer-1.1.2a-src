package org.anti_ad.mc.ipnext.integration;

import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.DeserializationStrategy;
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
public final class HintClassData$$serializer implements GeneratedSerializer {
   @NotNull
   public static final HintClassData$$serializer INSTANCE = new HintClassData$$serializer();
   @NotNull
   private static final SerialDescriptor descriptor;

   private HintClassData$$serializer() {
   }

   public final void serialize(@NotNull Encoder var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      SerialDescriptor var3 = descriptor;
      CompositeEncoder var4 = var1.beginStructure(var3);
      HintClassData.write$Self$neoforge_1_21(var2, var4, var3);
      var4.endStructure(var3);
   }

   @NotNull
   public final HintClassData deserialize(@NotNull Decoder var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SerialDescriptor var2 = descriptor;
      boolean var3 = true;
      short var4 = 0;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      Map var8 = null;
      Set var9 = null;
      boolean var10 = false;
      boolean var11 = false;
      boolean var12 = false;
      CompositeDecoder var13 = var1.beginStructure(var2);
      Lazy[] var14 = HintClassData.access$get$childSerializers$cp();
      if (var13.decodeSequentially()) {
         var5 = var13.decodeBooleanElement(var2, 0);
         var6 = var13.decodeBooleanElement(var2, 1);
         var7 = var13.decodeBooleanElement(var2, 2);
         var8 = (Map)var13.decodeSerializableElement(var2, 3, (DeserializationStrategy)var14[3].getValue(), null);
         var9 = (Set)var13.decodeSerializableElement(var2, 4, (DeserializationStrategy)var14[4].getValue(), null);
         var10 = var13.decodeBooleanElement(var2, 5);
         var11 = var13.decodeBooleanElement(var2, 6);
         var12 = var13.decodeBooleanElement(var2, 7);
         var4 = 255;
      } else {
         while (var3) {
            int var15;
            switch (var15 = var13.decodeElementIndex(var2)) {
               case -1:
                  var3 = false;
                  break;
               case 0:
                  var5 = var13.decodeBooleanElement(var2, 0);
                  var4 |= 1;
                  break;
               case 1:
                  var6 = var13.decodeBooleanElement(var2, 1);
                  var4 |= 2;
                  break;
               case 2:
                  var7 = var13.decodeBooleanElement(var2, 2);
                  var4 |= 4;
                  break;
               case 3:
                  var8 = (Map)var13.decodeSerializableElement(var2, 3, (DeserializationStrategy)var14[3].getValue(), var8);
                  var4 |= 8;
                  break;
               case 4:
                  var9 = (Set)var13.decodeSerializableElement(var2, 4, (DeserializationStrategy)var14[4].getValue(), var9);
                  var4 |= 16;
                  break;
               case 5:
                  var10 = var13.decodeBooleanElement(var2, 5);
                  var4 |= 32;
                  break;
               case 6:
                  var11 = var13.decodeBooleanElement(var2, 6);
                  var4 |= 64;
                  break;
               case 7:
                  var12 = var13.decodeBooleanElement(var2, 7);
                  var4 |= 128;
                  break;
               default:
                  throw new UnknownFieldException(var15);
            }
         }
      }

      var13.endStructure(var2);
      return new HintClassData(var4, var5, var6, var7, var8, var9, var10, var11, var12, null);
   }

   @NotNull
   public final SerialDescriptor getDescriptor() {
      return descriptor;
   }

   @NotNull
   public final KSerializer[] childSerializers() {
      Lazy[] var1 = HintClassData.access$get$childSerializers$cp();
      KSerializer[] var2;
      (var2 = new KSerializer[8])[0] = BooleanSerializer.INSTANCE;
      var2[1] = BooleanSerializer.INSTANCE;
      var2[2] = BooleanSerializer.INSTANCE;
      var2[3] = (KSerializer)var1[3].getValue();
      var2[4] = (KSerializer)var1[4].getValue();
      var2[5] = BooleanSerializer.INSTANCE;
      var2[6] = BooleanSerializer.INSTANCE;
      var2[7] = BooleanSerializer.INSTANCE;
      return var2;
   }

   static {
      PluginGeneratedSerialDescriptor var0;
      (var0 = new PluginGeneratedSerialDescriptor("org.anti_ad.mc.ipnext.integration.HintClassData", INSTANCE, 8)).addElement("ignore", true);
      var0.addElement("playerSideOnly", true);
      var0.addElement("disableFastSwipe", true);
      var0.addElement("buttonHints", true);
      var0.addElement("slotIgnoreInventoryTypes", true);
      var0.addElement("ignoreCraftingGrid", true);
      var0.addElement("avoidShiftClick", true);
      var0.addElement("force", true);
      descriptor = (SerialDescriptor)var0;
   }
}
