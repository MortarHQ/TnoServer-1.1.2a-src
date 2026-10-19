package com.moepus.biomespy.biome;

import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public final class BiomeEnvelope {
   public boolean impossible = false;
   public long tMin = Long.MAX_VALUE;
   public long tMax = Long.MIN_VALUE;
   public long hMin = Long.MAX_VALUE;
   public long hMax = Long.MIN_VALUE;
   public long cMin = Long.MAX_VALUE;
   public long cMax = Long.MIN_VALUE;
   public long eMin = Long.MAX_VALUE;
   public long eMax = Long.MIN_VALUE;
   public long wMin = Long.MAX_VALUE;
   public long wMax = Long.MIN_VALUE;

   public void add(ParameterPoint pp) {
      Parameter T = pp.temperature();
      this.tMin = Math.min(this.tMin, T.min());
      this.tMax = Math.max(this.tMax, T.max());
      Parameter H = pp.humidity();
      this.hMin = Math.min(this.hMin, H.min());
      this.hMax = Math.max(this.hMax, H.max());
      Parameter C = pp.continentalness();
      this.cMin = Math.min(this.cMin, C.min());
      this.cMax = Math.max(this.cMax, C.max());
      Parameter E = pp.erosion();
      this.eMin = Math.min(this.eMin, E.min());
      this.eMax = Math.max(this.eMax, E.max());
      Parameter W = pp.weirdness();
      this.wMin = Math.min(this.wMin, W.min());
      this.wMax = Math.max(this.wMax, W.max());
   }

   public void add(BiomeEnvelope other) {
      this.tMin = Math.min(this.tMin, other.tMin);
      this.tMax = Math.max(this.tMax, other.tMax);
      this.hMin = Math.min(this.hMin, other.hMin);
      this.hMax = Math.max(this.hMax, other.hMax);
      this.cMin = Math.min(this.cMin, other.cMin);
      this.cMax = Math.max(this.cMax, other.cMax);
      this.eMin = Math.min(this.eMin, other.eMin);
      this.eMax = Math.max(this.eMax, other.eMax);
      this.wMin = Math.min(this.wMin, other.wMin);
      this.wMax = Math.max(this.wMax, other.wMax);
   }

   @Override
   public String toString() {
      return this.impossible
         ? "BiomeEnvelope{impossible}"
         : "BiomeEnvelope{T=["
            + this.tMin
            + ","
            + this.tMax
            + "],H=["
            + this.hMin
            + ","
            + this.hMax
            + "],C=["
            + this.cMin
            + ","
            + this.cMax
            + "],E=["
            + this.eMin
            + ","
            + this.eMax
            + "],W=["
            + this.wMin
            + ","
            + this.wMax
            + "]}";
   }

   boolean isValid() {
      return this.tMin != this.tMax && this.hMin != this.hMax && this.cMin != this.cMax && this.eMin != this.eMax && this.wMin != this.wMax;
   }
}
