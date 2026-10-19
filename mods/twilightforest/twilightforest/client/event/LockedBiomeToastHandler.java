package twilightforest.client.event;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import twilightforest.client.LockedBiomeToast;
import twilightforest.config.TFConfig;
import twilightforest.util.Restriction;
import twilightforest.util.landmarks.LandmarkUtil;

public class LockedBiomeToastHandler {
   private static boolean shownToast = false;
   private static int timeUntilToast = 60;

   protected static void tickLockedToastLogic(Post event) {
      Player player = Minecraft.getInstance().player;
      if (player != null && player.level() instanceof ClientLevel level) {
         if (level.isClientSide()
            && player.tickCount % 5 == 0
            && LandmarkUtil.isProgressionEnforced(level)
            && !player.isCreative()
            && !player.isSpectator()
            && !TFConfig.disableLockedBiomeToasts) {
            Optional<Restriction> restriction = Restriction.getRestrictionForBiome((Biome)level.getBiome(player.blockPosition()).value(), player);
            if (restriction.isPresent() && restriction.get().lockedBiomeToast() != null) {
               timeUntilToast--;
               if (!shownToast && timeUntilToast <= 0) {
                  Minecraft.getInstance().getToasts().addToast(new LockedBiomeToast(restriction.get().lockedBiomeToast()));
                  shownToast = true;
               }
            } else if (shownToast) {
               timeUntilToast = 60;
               shownToast = false;
            }
         }
      }
   }
}
