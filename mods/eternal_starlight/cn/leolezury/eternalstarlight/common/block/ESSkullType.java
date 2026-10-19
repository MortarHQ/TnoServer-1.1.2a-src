package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.world.level.block.SkullBlock.Type;

public enum ESSkullType implements Type {
   TANGLED("tangled");

   private final String name;

   ESSkullType(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }
}
