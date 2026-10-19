package com.teamresourceful.resourcefullib.common.codecs;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;

public final class PrimitiveCodecHelper {
   private PrimitiveCodecHelper() throws UtilityClassException {
      throw new UtilityClassException();
   }

   public static <T> PrimitiveCodec<T> create(PrimitiveCodecHelper.Reader<T> reader, PrimitiveCodecHelper.Writer<T> writer, String name) {
      return new PrimitiveCodec<T>() {
         public <I> DataResult<T> read(DynamicOps<I> ops, I input) {
            return reader.read(ops, input);
         }

         public <I> I write(DynamicOps<I> ops, T value) {
            return writer.write(ops, value);
         }

         @Override
         public String toString() {
            return name;
         }
      };
   }

   @FunctionalInterface
   public interface Reader<T> {
      <I> DataResult<T> read(DynamicOps<I> var1, I var2);
   }

   @FunctionalInterface
   public interface Writer<T> {
      <I> I write(DynamicOps<I> var1, T var2);
   }
}
