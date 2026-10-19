package com.moepus.byepregen.compat;

import com.ibm.asyncutil.util.Either;
import com.ishland.c2me.base.common.registry.SerializerAccess;
import com.moepus.byepregen.ConfigParser;
import com.moepus.byepregen.MixinPlugin;
import com.moepus.byepregen.gcfree.GcFreeChunkSerializer;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class GcFreeCompat {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String C2ME_SERIALIZER_ACCESS = "com.ishland.c2me.base.common.registry.SerializerAccess";

   private GcFreeCompat() {
   }

   public static void register() {
      if (ConfigParser.getConfig().enableGcFreeWorldgenSave) {
         if (MixinPlugin.hasClass("com.ishland.c2me.base.common.registry.SerializerAccess")) {
            GcFreeCompat.C2ME.register();
         }
      }
   }

   public static final class C2ME {
      private C2ME() {
      }

      static void register() {
         try {
            SerializerAccess.registerSerializer(
               (level, chunk, preferNbtCompound) -> !GcFreeChunkSerializer.shouldUseGcFree(chunk)
                  ? SerializerAccess.VANILLA.serialize(level, chunk, preferNbtCompound)
                  : Either.right(GcFreeChunkSerializer.serializeRaw(level, chunk))
            );
            GcFreeCompat.LOGGER.info("Registered ByePregen GC-free worldgen chunk serializer with C2ME");
         } catch (RuntimeException | LinkageError e) {
            GcFreeCompat.LOGGER.warn("Failed to register ByePregen GC-free worldgen chunk serializer with C2ME", e);
         }
      }
   }
}
