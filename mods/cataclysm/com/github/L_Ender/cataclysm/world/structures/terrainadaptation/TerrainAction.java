package com.github.L_Ender.cataclysm.world.structures.terrainadaptation;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum TerrainAction implements StringRepresentable {
   CARVE("carve", -1),
   BURY("bury", 1),
   NONE("none", 0);

   public static final Codec<TerrainAction> CODEC = StringRepresentable.fromValues(TerrainAction::values);
   private final String name;
   private final int densityModifier;

   TerrainAction(String name, int densityModifier) {
      this.name = name;
      this.densityModifier = densityModifier;
   }

   public int getDensityModifier() {
      return this.densityModifier;
   }

   @NotNull
   public String getSerializedName() {
      return this.name;
   }
}
