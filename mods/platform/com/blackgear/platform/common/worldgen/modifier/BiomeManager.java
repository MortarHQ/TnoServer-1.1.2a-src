package com.blackgear.platform.common.worldgen.modifier;

import com.blackgear.platform.common.worldgen.modifier.neoforge.BiomeManagerImpl;
import com.google.common.collect.Lists;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.List;
import java.util.function.BiConsumer;

public class BiomeManager {
   private static final List<BiConsumer<BiomeWriter, BiomeContext>> MODIFICATIONS = Lists.newArrayList();
   public static final BiomeManager INSTANCE = new BiomeManager();

   @ExpectPlatform
   @Transformed
   public static void bootstrap() {
      BiomeManagerImpl.bootstrap();
   }

   public void register(BiomeWriter writer) {
      MODIFICATIONS.forEach(writer::add);
   }

   public static void add(BiConsumer<BiomeWriter, BiomeContext> modifier) {
      MODIFICATIONS.add(modifier);
   }
}
