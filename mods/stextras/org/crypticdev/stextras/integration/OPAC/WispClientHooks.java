package org.crypticdev.stextras.integration.OPAC;

import java.util.UUID;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xaero.pac.client.api.OpenPACClientAPI;
import xaero.pac.client.claims.api.IClientClaimsManagerAPI;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;

public class WispClientHooks {
   public static boolean canPhaseClientOpac(Player player, AABB bounds) {
      if (!OpenPacIntegration.isOPACLoaded()) {
         return true;
      }

      try {
         OpenPACClientAPI api = OpenPACClientAPI.get();
         if (api == null) {
            return true;
         }

         IClientClaimsManagerAPI manager = api.getClaimsManager();
         ResourceLocation dim = player.level().dimension().location();
         int minX = SectionPos.blockToSectionCoord((int)Math.floor(bounds.minX));
         int maxX = SectionPos.blockToSectionCoord((int)Math.floor(bounds.maxX));
         int minZ = SectionPos.blockToSectionCoord((int)Math.floor(bounds.minZ));
         int maxZ = SectionPos.blockToSectionCoord((int)Math.floor(bounds.maxZ));

         for (int cx = minX; cx <= maxX; cx++) {
            for (int cz = minZ; cz <= maxZ; cz++) {
               IPlayerChunkClaimAPI claim = manager.get(dim, cx, cz);
               if (claim != null && !hasPermission(api, player, claim.getPlayerId()) && intersectsSolidBlockInChunk(player, bounds, cx, cz)) {
                  return false;
               }
            }
         }
      } catch (Throwable var12) {
      }

      return true;
   }

   private static boolean hasPermission(OpenPACClientAPI api, Player player, UUID ownerId) {
      if (ownerId.equals(player.getUUID())) {
         return true;
      } else {
         return api.getClientPartyStorage().getPartyMemberDynamicInfoSyncableStorage().getForPlayer(ownerId) != null
            ? true
            : api.getClientPartyStorage().getAllyInfoStorage().get(ownerId) != null;
      }
   }

   private static boolean intersectsSolidBlockInChunk(Player player, AABB bounds, int cx, int cz) {
      MutableBlockPos mutable = new MutableBlockPos();
      int xStart = Math.max((int)Math.floor(bounds.minX), cx << 4);
      int xEnd = Math.min((int)Math.floor(bounds.maxX), (cx << 4) + 15);
      int zStart = Math.max((int)Math.floor(bounds.minZ), cz << 4);
      int zEnd = Math.min((int)Math.floor(bounds.maxZ), (cz << 4) + 15);

      for (int x = xStart; x <= xEnd; x++) {
         for (int y = (int)Math.floor(bounds.minY); y <= (int)Math.floor(bounds.maxY); y++) {
            for (int z = zStart; z <= zEnd; z++) {
               mutable.set(x, y, z);
               BlockState state = player.level().getBlockState(mutable);
               if (!state.isAir() && !state.getCollisionShape(player.level(), mutable).isEmpty()) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
