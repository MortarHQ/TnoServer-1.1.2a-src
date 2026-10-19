package org.anti_ad.mc.ipnext.event.villagers;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;

// $VF: synthetic class
@Deprecated(message = "This synthesized declaration should not be used directly", level = DeprecationLevel.HIDDEN)
public final class VillagerTradeData$$serializer implements GeneratedSerializer {
   @NotNull
   public static final VillagerTradeData$$serializer INSTANCE = new VillagerTradeData$$serializer();
   @NotNull
   private static final SerialDescriptor descriptor;

   private VillagerTradeData$$serializer() {
   }

   public final void serialize(@NotNull Encoder var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      SerialDescriptor var3 = descriptor;
      CompositeEncoder var4 = var1.beginStructure(var3);
      VillagerTradeData.write$Self$neoforge_1_21(var2, var4, var3);
      var4.endStructure(var3);
   }

   @NotNull
   public final VillagerTradeData deserialize(@NotNull Decoder var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      SerialDescriptor var2 = descriptor;
      boolean var3 = true;
      byte var4 = 0;
      String var5 = null;
      String var6 = null;
      String var7 = null;
      String var8 = null;
      String var9 = null;
      String var10 = null;
      CompositeDecoder var11;
      if ((var11 = var1.beginStructure(var2)).decodeSequentially()) {
         var5 = var11.decodeStringElement(var2, 0);
         var6 = var11.decodeStringElement(var2, 1);
         var7 = (String)var11.decodeNullableSerializableElement(var2, 2, (DeserializationStrategy)StringSerializer.INSTANCE, null);
         var8 = (String)var11.decodeNullableSerializableElement(var2, 3, (DeserializationStrategy)StringSerializer.INSTANCE, null);
         var9 = (String)var11.decodeNullableSerializableElement(var2, 4, (DeserializationStrategy)StringSerializer.INSTANCE, null);
         var10 = (String)var11.decodeNullableSerializableElement(var2, 5, (DeserializationStrategy)StringSerializer.INSTANCE, null);
         var4 = 63;
      } else {
         while (var3) {
            int var12;
            switch (var12 = var11.decodeElementIndex(var2)) {
               case -1:
                  var3 = false;
                  break;
               case 0:
                  var5 = var11.decodeStringElement(var2, 0);
                  var4 |= 1;
                  break;
               case 1:
                  var6 = var11.decodeStringElement(var2, 1);
                  var4 |= 2;
                  break;
               case 2:
                  var7 = (String)var11.decodeNullableSerializableElement(var2, 2, (DeserializationStrategy)StringSerializer.INSTANCE, var7);
                  var4 |= 4;
                  break;
               case 3:
                  var8 = (String)var11.decodeNullableSerializableElement(var2, 3, (DeserializationStrategy)StringSerializer.INSTANCE, var8);
                  var4 |= 8;
                  break;
               case 4:
                  var9 = (String)var11.decodeNullableSerializableElement(var2, 4, (DeserializationStrategy)StringSerializer.INSTANCE, var9);
                  var4 |= 16;
                  break;
               case 5:
                  var10 = (String)var11.decodeNullableSerializableElement(var2, 5, (DeserializationStrategy)StringSerializer.INSTANCE, var10);
                  var4 |= 32;
                  break;
               default:
                  throw new UnknownFieldException(var12);
            }
         }
      }

      var11.endStructure(var2);
      return new VillagerTradeData(var4, var5, var6, var7, var8, var9, var10, null);
   }

   @NotNull
   public final SerialDescriptor getDescriptor() {
      return descriptor;
   }

   @NotNull
   public final KSerializer[] childSerializers() {
      KSerializer[] var1;
      (var1 = new KSerializer[6])[0] = StringSerializer.INSTANCE;
      var1[1] = StringSerializer.INSTANCE;
      var1[2] = BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE);
      var1[3] = BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE);
      var1[4] = BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE);
      var1[5] = BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE);
      return var1;
   }

   static {
      PluginGeneratedSerialDescriptor var0;
      (var0 = new PluginGeneratedSerialDescriptor("org.anti_ad.mc.ipnext.event.villagers.VillagerTradeData", INSTANCE, 6)).addElement("resultItem", false);
      var0.addElement("priceItem1", false);
      var0.addElement("priceItem2", true);
      var0.addElement("resultItemNBT", true);
      var0.addElement("priceItem1NBT", true);
      var0.addElement("priceItem2NBT", true);
      descriptor = (SerialDescriptor)var0;
   }
}
