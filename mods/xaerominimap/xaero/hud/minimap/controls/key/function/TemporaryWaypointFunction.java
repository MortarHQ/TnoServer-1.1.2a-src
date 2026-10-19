package xaero.hud.minimap.controls.key.function;

import net.minecraft.client.Minecraft;
import xaero.common.effect.Effects;
import xaero.common.misc.Misc;
import xaero.common.misc.OptimizedMath;
import xaero.hud.controls.key.function.KeyMappingFunction;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.module.MinimapSession;

public class TemporaryWaypointFunction extends KeyMappingFunction {
   protected TemporaryWaypointFunction() {
      super(false);
   }

   @Override
   public void onPress() {
      Minecraft mc = Minecraft.getInstance();
      if (!Misc.hasEffect(mc.player, Effects.NO_WAYPOINTS) && !Misc.hasEffect(mc.player, Effects.NO_WAYPOINTS_HARMFUL)) {
         MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
         session.getWaypointSession()
            .getTemporaryHandler()
            .createTemporaryWaypoint(
               session.getWorldManager().getCurrentWorld(),
               OptimizedMath.myFloor(mc.cameraEntity.getX()),
               OptimizedMath.myFloor(mc.cameraEntity.getY() + 0.0625),
               OptimizedMath.myFloor(mc.cameraEntity.getZ())
            );
      }
   }

   @Override
   public void onRelease() {
   }
}
