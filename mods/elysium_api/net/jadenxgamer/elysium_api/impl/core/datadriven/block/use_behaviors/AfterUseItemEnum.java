package net.jadenxgamer.elysium_api.impl.core.datadriven.block.use_behaviors;

import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.StringRepresentableCodec;
import org.jetbrains.annotations.NotNull;

public enum AfterUseItemEnum implements StringRepresentable {
   NOTHING("nothing"),
   CONSUME("consume"),
   DAMAGE("damage");

   private final String name;
   public static final StringRepresentableCodec<AfterUseItemEnum> CODEC = StringRepresentable.fromEnum(AfterUseItemEnum::values);

   AfterUseItemEnum(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return this.name;
   }

   @NotNull
   public String getSerializedName() {
      return this.name;
   }
}
