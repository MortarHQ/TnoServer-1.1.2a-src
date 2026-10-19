package cc.sighs.oelib.registry.action;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.world.level.ItemLike;

public record FuelAction(int time, Supplier<? extends ItemLike>[] items) implements RegistrationAction {
   public FuelAction {
      Objects.requireNonNull(items, "items");
   }
}
