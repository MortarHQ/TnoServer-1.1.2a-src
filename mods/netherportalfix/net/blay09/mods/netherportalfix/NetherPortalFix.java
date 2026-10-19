package net.blay09.mods.netherportalfix;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.PlayerChangedDimensionEvent;
import net.blay09.mods.netherportalfix.mixin.LivingEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherPortalFix {
   public static final String MOD_ID = "netherportalfix";
   public static final Logger logger = LogManager.getLogger();

   public static void initialize() {
      Balm.getEvents().onEvent(PlayerChangedDimensionEvent.class, event -> {
         ServerPlayer player = event.getPlayer();
         ResourceKey<Level> fromDim = event.getFromDim();
         ResourceKey<Level> toDim = event.getToDim();
         ResourceKey<Level> OVERWORLD = Level.OVERWORLD;
         ResourceKey<Level> THE_NETHER = Level.NETHER;
         if (fromDim == OVERWORLD && toDim == THE_NETHER || fromDim == THE_NETHER && toDim == OVERWORLD) {
            BlockPos lastPos = ((LivingEntityAccessor)player).getLastPos();
            if (lastPos == null) {
               logger.debug("Not storing return portal because I just spawned.");
            } else {
               BlockPos fromPortal = ReturnPortalManager.findPortalAt(player, fromDim, lastPos);
               BlockPos toPos = player.blockPosition();
               if (fromPortal == null) {
                  logger.debug("Not storing return portal because I'm not in a portal.");
               } else {
                  ReturnPortalManager.storeReturnPortal(player, toDim, toPos, fromPortal);
                  logger.debug("Storing return portal from {} to {} in {}", toDim, fromPortal, fromDim);
               }
            }
         } else {
            logger.debug("Not storing return portal because it's from {} to {}", fromDim, toDim);
         }
      });
   }
}
