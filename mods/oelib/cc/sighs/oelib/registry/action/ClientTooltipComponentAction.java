package cc.sighs.oelib.registry.action;

import java.util.Objects;
import java.util.function.Function;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ClientTooltipComponentAction<T extends TooltipComponent>(Class<T> clazz, Function<? super T, ? extends ClientTooltipComponent> factory)
   implements RegistrationAction {
   public ClientTooltipComponentAction(Class<T> clazz, Function<? super T, ? extends ClientTooltipComponent> factory) {
      this.clazz = Objects.requireNonNull(clazz, "clazz");
      this.factory = Objects.requireNonNull(factory, "factory");
   }
}
