package cc.sighs.oelib.network.serialization;

import cc.sighs.oelib.OELib;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.Error;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public final class NetworkSerialization {
   private static final ConcurrentHashMap<Class<?>, StreamCodec<RegistryFriendlyByteBuf, ?>> RECORD_CODEC_CACHE = new ConcurrentHashMap<>();

   private NetworkSerialization() {
   }

   public static <T> StreamCodec<RegistryFriendlyByteBuf, T> jsonCodec(Codec<T> codec) {
      return new StreamCodec<RegistryFriendlyByteBuf, T>() {
         @NotNull
         public T decode(RegistryFriendlyByteBuf buf) {
            String json = buf.readUtf();
            JsonElement element = JsonParser.parseString(json);
            DataResult<T> result = codec.parse(JsonOps.INSTANCE, element);
            if (result.error().isPresent()) {
               String message = ((Error)result.error().get()).message();
               OELib.LOGGER.error("Failed to decode json payload: {}", message);
               throw new IllegalStateException("Failed to decode json payload: " + message);
            } else {
               return (T)result.result().orElseThrow(() -> new IllegalStateException("Failed to decode json payload: empty result"));
            }
         }

         public void encode(RegistryFriendlyByteBuf buf, T value) {
            DataResult<JsonElement> result = codec.encodeStart(JsonOps.INSTANCE, value);
            if (result.error().isPresent()) {
               String message = ((Error)result.error().get()).message();
               OELib.LOGGER.error("Failed to encode json payload: {}", message);
               throw new IllegalStateException("Failed to encode json payload: " + message);
            }

            JsonElement element = (JsonElement)result.result().orElseThrow(() -> new IllegalStateException("Failed to encode json payload: empty result"));
            buf.writeUtf(element.toString());
         }
      };
   }

   public static <T> StreamCodec<RegistryFriendlyByteBuf, T> autoCodec(Class<T> recordClass) {
      if (!recordClass.isRecord()) {
         throw new IllegalArgumentException("autoCodec only supports record types: " + recordClass.getName());
      }

      StreamCodec<RegistryFriendlyByteBuf, T> existing = (StreamCodec<RegistryFriendlyByteBuf, T>)RECORD_CODEC_CACHE.get(recordClass);
      if (existing != null) {
         return existing;
      }

      StreamCodec<RegistryFriendlyByteBuf, T> built = NetworkRecordCodecBuilder.build(recordClass);
      StreamCodec<RegistryFriendlyByteBuf, T> prev = (StreamCodec<RegistryFriendlyByteBuf, T>)RECORD_CODEC_CACHE.putIfAbsent(recordClass, built);
      return prev != null ? prev : built;
   }
}
