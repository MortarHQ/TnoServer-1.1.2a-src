package com.moepus.byepregen.gcfree;

import java.util.Arrays;

public record RawChunkData(byte[] bytes, int length) {
   public RawChunkData(byte[] bytes, int length) {
      if (length >= 0 && length <= bytes.length) {
         this.bytes = bytes;
         this.length = length;
      } else {
         throw new IllegalArgumentException("Invalid raw chunk data length: " + length);
      }
   }

   public byte[] toByteArray() {
      return this.length == this.bytes.length ? this.bytes : Arrays.copyOf(this.bytes, this.length);
   }
}
