package net.witherspawnanimation.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.witherspawnanimation.network.WitherSpawnAnimationModVariables;

@EventBusSubscriber
public class DetectSpawnFixProcedure {
   @SubscribeEvent
   public static void onWorldLoad(Load event) {
      execute(event, event.getLevel());
   }

   public static void execute(LevelAccessor world) {
      execute(null, world);
   }

   private static void execute(@Nullable Event event, LevelAccessor world) {
      WitherSpawnAnimationModVariables.MapVariables.get(world).spawn_fix = ModList.get().isLoaded("wither_spawn_fix_wsa_compatible");
      WitherSpawnAnimationModVariables.MapVariables.get(world).syncData(world);
   }
}
