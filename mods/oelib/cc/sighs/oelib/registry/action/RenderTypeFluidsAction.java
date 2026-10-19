package cc.sighs.oelib.registry.action;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluid;

public record RenderTypeFluidsAction(RenderType type, Supplier<? extends Fluid>[] fluids) implements RegistrationAction {
   public RenderTypeFluidsAction {
      Objects.requireNonNull(type, "type");
      Objects.requireNonNull(fluids, "fluids");
   }
}
