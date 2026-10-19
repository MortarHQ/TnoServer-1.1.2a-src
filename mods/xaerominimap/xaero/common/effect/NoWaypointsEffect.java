package xaero.common.effect;

import net.minecraft.world.effect.MobEffectCategory;

public class NoWaypointsEffect extends MinimapStatusEffect {
   public NoWaypointsEffect(MobEffectCategory type) {
      super(type, -16777216, "no_waypoints");
   }
}
