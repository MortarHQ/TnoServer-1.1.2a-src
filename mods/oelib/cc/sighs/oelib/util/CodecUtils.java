package cc.sighs.oelib.util;

import cc.sighs.oelib.OELib;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.Error;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class CodecUtils {
   private static final Map<Class<?>, Codec<?>> codecCache = new ConcurrentHashMap<>();

   public static <T> Optional<String> encodeToJson(Class<T> dataClass, Map<ResourceLocation, T> data) {
      return executeWithExceptionHandling(dataClass, "encoding", () -> {
         Codec<Map<ResourceLocation, T>> mapCodec = createMapCodec(dataClass);
         DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, data);
         return handleDataResult(dataClass, "encode", (DataResult<T>)result).map(JsonElement::toString);
      });
   }

   public static <T> Optional<Map<ResourceLocation, T>> decodeFromJson(Class<T> dataClass, String jsonData) {
      return executeWithExceptionHandling(dataClass, "decoding", () -> {
         JsonElement jsonElement = JsonParser.parseString(jsonData);
         Codec<Map<ResourceLocation, T>> mapCodec = createMapCodec(dataClass);
         DataResult<Map<ResourceLocation, T>> result = mapCodec.parse(JsonOps.INSTANCE, jsonElement);
         return handleDataResult(dataClass, "decode", result);
      });
   }

   public static <T> Optional<T> decodeSingle(Class<T> dataClass, JsonElement jsonElement) {
      return executeWithExceptionHandling(dataClass, "decoding", () -> {
         Codec<T> codec = getCodec(dataClass);
         DataResult<T> result = codec.parse(JsonOps.INSTANCE, jsonElement);
         return handleDataResult(dataClass, "decode", result);
      });
   }

   private static <T, R> Optional<R> executeWithExceptionHandling(Class<T> dataClass, String operationType, Supplier<Optional<R>> action) {
      try {
         return action.get();
      } catch (Exception e) {
         OELib.LOGGER.error("Exception during {} {} for {}: {}", new Object[]{dataClass.getSimpleName(), operationType, e.getMessage(), e});
         return Optional.empty();
      }
   }

   private static <T> Optional<T> handleDataResult(Class<?> dataClass, String operation, DataResult<T> result) {
      if (result.error().isPresent()) {
         Error<T> error = (Error<T>)result.error().get();
         OELib.LOGGER.error("Failed to {} {} data: {}", new Object[]{operation, dataClass.getSimpleName(), error.message()});
         return Optional.empty();
      } else {
         return result.result();
      }
   }

   private static <T> Codec<Map<ResourceLocation, T>> createMapCodec(Class<T> dataClass) {
      Codec<T> codec = getCodec(dataClass);
      return Codec.unboundedMap(ResourceLocation.CODEC, codec);
   }

   @Deprecated
   public static <T> Codec<T> getCodec(Class<T> dataClass) {
      return (Codec<T>)codecCache.computeIfAbsent(
         dataClass,
         cls -> {
            try {
               Field codecField = cls.getDeclaredField("CODEC");
               codecField.setAccessible(true);
               return (Codec<?>)codecField.get(null);
            } catch (Exception e) {
               throw new RuntimeException(
                  "Failed to get CODEC field from " + cls.getSimpleName() + ". Make sure the class has a public static final CODEC field.", e
               );
            }
         }
      );
   }

   public static <T> void registerCodec(Class<T> dataClass, Codec<T> codec) {
      codecCache.put(dataClass, codec);
   }
}
