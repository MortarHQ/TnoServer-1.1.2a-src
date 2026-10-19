package net.jadenxgamer.elysium_api.impl.core.datadriven.block.use_behaviors;

import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.StringRepresentableCodec;
import org.jetbrains.annotations.NotNull;

public enum UseBehaviorTypeEnum implements StringRepresentable {
   PLACE("place"),
   PLACE_ITSELF("place_itself"),
   DROP("drop"),
   DROP_ITSELF("drop_itself"),
   FEATURE("feature"),
   INSERT_STACK("insert_stack");

   private final String name;
   public static final StringRepresentableCodec<UseBehaviorTypeEnum> CODEC = StringRepresentable.fromEnum(UseBehaviorTypeEnum::values);

   UseBehaviorTypeEnum(String name) {
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
