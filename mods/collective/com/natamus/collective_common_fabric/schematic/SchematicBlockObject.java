package com.natamus.collective_common_fabric.schematic;

import net.minecraft.class_2338;
import net.minecraft.class_2680;

public class SchematicBlockObject {
   private final class_2338 position;
   private final class_2680 state;

   public SchematicBlockObject(class_2338 position, class_2680 state) {
      this.position = position;
      this.state = state;
   }

   public class_2338 getPosition() {
      return this.position;
   }

   public class_2680 getState() {
      return this.state;
   }

   public class_2338 getPositionWithOffset(int x, int y, int z) {
      return new class_2338(x + this.position.method_10263(), y + this.position.method_10264(), z + this.position.method_10260());
   }
}
