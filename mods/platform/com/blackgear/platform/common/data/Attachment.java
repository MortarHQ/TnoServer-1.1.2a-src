package com.blackgear.platform.common.data;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public interface Attachment<A> {
   ResourceLocation valueId();

   Codec<A> codec();

   Supplier<A> defaultSyncedValue();

   static <A> Attachment<A> create(ResourceLocation id, Codec<A> codec, Supplier<A> value) {
      return new Attachment<A>() {
         @Override
         public ResourceLocation valueId() {
            return id;
         }

         @Override
         public Codec<A> codec() {
            return codec;
         }

         @Override
         public Supplier<A> defaultSyncedValue() {
            return value;
         }
      };
   }
}
