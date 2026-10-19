package net.unusual.block_factorys_bosses.state;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class StateRef<E extends Entity> {
   private final String name;

   public StateRef(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   @Override
   public String toString() {
      return "StateRef(" + this.name + "}";
   }
}
