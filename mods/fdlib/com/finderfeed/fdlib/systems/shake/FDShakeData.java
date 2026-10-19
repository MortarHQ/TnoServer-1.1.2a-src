package com.finderfeed.fdlib.systems.shake;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class FDShakeData {
   public static final StreamCodec<FriendlyByteBuf, FDShakeData> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      v -> v.inTime,
      ByteBufCodecs.INT,
      v -> v.stayTime,
      ByteBufCodecs.INT,
      v -> v.outTime,
      ByteBufCodecs.FLOAT,
      v -> v.amplitude,
      ByteBufCodecs.FLOAT,
      v -> v.frequency,
      (inTime, stayTime, outTime, amplitude, frequency) -> {
         FDShakeData data = new FDShakeData();
         data.inTime = inTime;
         data.stayTime = stayTime;
         data.outTime = outTime;
         data.amplitude = amplitude;
         data.frequency = frequency;
         return data;
      }
   );
   private int inTime = 2;
   private int stayTime = 2;
   private int outTime = 2;
   private float amplitude = 0.1F;
   private float frequency = 1.0F;

   public static FDShakeData.Builder builder() {
      return new FDShakeData.Builder();
   }

   public float getAmplitude() {
      return this.amplitude;
   }

   public int getOutTime() {
      return this.outTime;
   }

   public int getStayTime() {
      return this.stayTime;
   }

   public int getInTime() {
      return this.inTime;
   }

   public int duration() {
      return this.outTime + this.inTime + this.stayTime;
   }

   public float getFrequency() {
      return this.frequency;
   }

   public static class Builder {
      private FDShakeData data = new FDShakeData();

      public FDShakeData.Builder inTime(int inTime) {
         this.data.inTime = inTime;
         return this;
      }

      public FDShakeData.Builder outTime(int outTime) {
         this.data.outTime = outTime;
         return this;
      }

      public FDShakeData.Builder stayTime(int stayTime) {
         this.data.stayTime = stayTime;
         return this;
      }

      public FDShakeData.Builder amplitude(float amplitude) {
         this.data.amplitude = amplitude;
         return this;
      }

      public FDShakeData.Builder frequency(float frequency) {
         this.data.frequency = frequency;
         return this;
      }

      public FDShakeData build() {
         return this.data;
      }
   }
}
