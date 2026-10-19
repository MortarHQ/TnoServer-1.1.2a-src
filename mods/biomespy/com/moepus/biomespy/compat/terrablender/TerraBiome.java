package com.moepus.biomespy.compat.terrablender;

import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.TargetPoint;
import terrablender.worldgen.IExtendedParameterList;

public class TerraBiome {
   public static Holder<Biome> getNoiseBiome(ParameterList<Holder<Biome>> parameters, TargetPoint climate, int x, int y, int z) {
      return (Holder<Biome>)((IExtendedParameterList)parameters)
         .findValuePositional(climate, QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z));
   }

   public static int getUniqueness(ParameterList<Holder<Biome>> parameters, int qx, int qy, int qz) {
      return ((IExtendedParameterList)parameters).getUniqueness(qx, qy, qz);
   }
}
