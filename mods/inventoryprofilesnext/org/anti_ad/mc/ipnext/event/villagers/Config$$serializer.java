package org.anti_ad.mc.ipnext.event.villagers;

import java.util.Map;
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
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import org.jetbrains.annotations.NotNull;

// $VF: synthetic class
@Deprecated(message = "This synthesized declaration should not be used directly", level = DeprecationLevel.HIDDEN)
public final class Config$$serializer implements GeneratedSerializer {
   @NotNull
   public static final Config$$serializer INSTANCE = new Config$$serializer();
   @NotNull
   private static final SerialDescriptor descriptor;

   private Config$$serializer() {
   }

   public final void serialize(@NotNull Encoder var1, @NotNull Config var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      SerialDescriptor var3 = descriptor;
      CompositeEncoder var4 = var1.beginStructure(var3);
      Config.write$Self$neoforge_1_21(var2, var4, var3);
      var4.endStructure(var3);
   }

   @NotNull
   public final Config deserialize(@NotNull Decoder var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SerialDescriptor var2 = descriptor;
      boolean var3 = true;
      byte var4 = 0;
      Map var5 = null;
      Map var6 = null;
      Map var7 = null;
      Map var8 = null;
      Map var9 = null;
      Map var10 = null;
      CompositeDecoder var11 = var1.beginStructure(var2);
      Lazy[] var12 = Config.access$get$childSerializers$cp();
      if (var11.decodeSequentially()) {
         var5 = (Map)var11.decodeSerializableElement(var2, 0, (DeserializationStrategy)var12[0].getValue(), null);
         var6 = (Map)var11.decodeSerializableElement(var2, 1, (DeserializationStrategy)var12[1].getValue(), null);
         var7 = (Map)var11.decodeSerializableElement(var2, 2, (DeserializationStrategy)var12[2].getValue(), null);
         var8 = (Map)var11.decodeSerializableElement(var2, 3, (DeserializationStrategy)var12[3].getValue(), null);
         var9 = (Map)var11.decodeSerializableElement(var2, 4, (DeserializationStrategy)var12[4].getValue(), null);
         var10 = (Map)var11.decodeSerializableElement(var2, 5, (DeserializationStrategy)var12[5].getValue(), null);
         var4 = 63;
      } else {
         while (var3) {
            int var13;
            switch (var13 = var11.decodeElementIndex(var2)) {
               case -1:
                  var3 = false;
                  break;
               case 0:
                  var5 = (Map)var11.decodeSerializableElement(var2, 0, (DeserializationStrategy)var12[0].getValue(), var5);
                  var4 |= 1;
                  break;
               case 1:
                  var6 = (Map)var11.decodeSerializableElement(var2, 1, (DeserializationStrategy)var12[1].getValue(), var6);
                  var4 |= 2;
                  break;
               case 2:
                  var7 = (Map)var11.decodeSerializableElement(var2, 2, (DeserializationStrategy)var12[2].getValue(), var7);
                  var4 |= 4;
                  break;
               case 3:
                  var8 = (Map)var11.decodeSerializableElement(var2, 3, (DeserializationStrategy)var12[3].getValue(), var8);
                  var4 |= 8;
                  break;
               case 4:
                  var9 = (Map)var11.decodeSerializableElement(var2, 4, (DeserializationStrategy)var12[4].getValue(), var9);
                  var4 |= 16;
                  break;
               case 5:
                  var10 = (Map)var11.decodeSerializableElement(var2, 5, (DeserializationStrategy)var12[5].getValue(), var10);
                  var4 |= 32;
                  break;
               default:
                  throw new UnknownFieldException(var13);
            }
         }
      }

      var11.endStructure(var2);
      return new Config(var4, var5, var6, var7, var8, var9, var10, null);
   }

   @NotNull
   public final SerialDescriptor getDescriptor() {
      return descriptor;
   }

   @NotNull
   public final KSerializer[] childSerializers() {
      Lazy[] var1 = Config.access$get$childSerializers$cp();
      KSerializer[] var2;
      (var2 = new KSerializer[6])[0] = (KSerializer)var1[0].getValue();
      var2[1] = (KSerializer)var1[1].getValue();
      var2[2] = (KSerializer)var1[2].getValue();
      var2[3] = (KSerializer)var1[3].getValue();
      var2[4] = (KSerializer)var1[4].getValue();
      var2[5] = (KSerializer)var1[5].getValue();
      return var2;
   }

   static {
      PluginGeneratedSerialDescriptor var0;
      (var0 = new PluginGeneratedSerialDescriptor("org.anti_ad.mc.ipnext.event.villagers.Config", INSTANCE, 6)).addElement("globalBookmarks", true);
      var0.addElement("localBookmarks", true);
      var0.addElement("globalBookmarks1", true);
      var0.addElement("localBookmarks1", true);
      var0.addElement("globalBookmarks2", true);
      var0.addElement("localBookmarks2", true);
      descriptor = (SerialDescriptor)var0;
   }
}
