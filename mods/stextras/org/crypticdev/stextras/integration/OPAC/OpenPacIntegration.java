package org.crypticdev.stextras.integration.OPAC;

import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.claims.api.IServerClaimsManagerAPI;
import xaero.pac.common.server.claims.protection.api.IChunkProtectionAPI;

public final class OpenPacIntegration {
   public static boolean isOPACLoaded() {
      return Platform.isModLoaded("openpartiesandclaims");
   }

   public static boolean canPhaseThrough(ServerPlayer player, AABB bounds) {
      if (isOPACLoaded()) {
         return true;
      }

      OpenPACServerAPI api = OpenPACServerAPI.get(player.server);
      IServerClaimsManagerAPI manager = api.getServerClaimsManager();
      IChunkProtectionAPI protection = api.getChunkProtection();
      ResourceLocation dim = player.serverLevel().dimension().location();
      int minX = (int)Math.floor(bounds.minX) >> 4;
      int maxX = (int)Math.floor(bounds.maxX) >> 4;
      int minZ = (int)Math.floor(bounds.minZ) >> 4;
      int maxZ = (int)Math.floor(bounds.maxZ) >> 4;

      for (int cx = minX; cx <= maxX; cx++) {
         for (int cz = minZ; cz <= maxZ; cz++) {
            IPlayerChunkClaimAPI claim = manager.get(dim, cx, cz);
            if (claim != null
               && !protection.hasChunkAccess(protection.getClaimConfig(claim), player)
               && containsSolidBlocks(player.serverLevel(), bounds, cx, cz)) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean containsSolidBlocks(Level level, AABB bounds, int cx, int cz) {
      MutableBlockPos mutable = new MutableBlockPos();
      int xStart = Math.max((int)Math.floor(bounds.minX), cx << 4);
      int xEnd = Math.min((int)Math.floor(bounds.maxX), (cx << 4) + 15);
      int zStart = Math.max((int)Math.floor(bounds.minZ), cz << 4);
      int zEnd = Math.min((int)Math.floor(bounds.maxZ), (cz << 4) + 15);

      for (int x = xStart; x <= xEnd; x++) {
         for (int y = (int)Math.floor(bounds.minY); y <= (int)Math.floor(bounds.maxY); y++) {
            for (int z = zStart; z <= zEnd; z++) {
               mutable.set(x, y, z);
               BlockState state = level.getBlockState(mutable);
               if (!state.isAir() && !state.getCollisionShape(level, mutable).isEmpty()) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
