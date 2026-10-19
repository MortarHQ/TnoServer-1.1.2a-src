package dev.ftb.mods.ftbchunks.util;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.property.PrivacyMode;
import dev.ftb.mods.ftbteams.api.property.PrivacyProperty;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;

public class PistonHelper {
   public static boolean shouldPreventPistonMovement(Level level, BlockPos pistonPos, PistonStructureResolver resolver) {
      if (!level.isClientSide && (Boolean)FTBChunksWorldConfig.PISTON_PROTECTION.get() && !(Boolean)FTBChunksWorldConfig.DISABLE_PROTECTION.get()) {
         PrivacyProperty editProp = Platform.isFabric() ? FTBChunksProperties.BLOCK_EDIT_AND_INTERACT_MODE : FTBChunksProperties.BLOCK_EDIT_MODE;
         ClaimedChunkManager mgr = FTBChunksAPI.api().getManager();
         ClaimedChunk srcClaim = mgr.getChunk(new ChunkDimPos(level, pistonPos));

         for (BlockPos pos : resolver.getToPush()) {
            if (prevent(editProp, srcClaim, mgr.getChunk(new ChunkDimPos(level, pos)))) {
               return true;
            }

            BlockPos toPos = pos.relative(resolver.getPushDirection());
            if ((pos.getX() >> 4 != toPos.getX() >> 4 || pos.getZ() >> 4 != toPos.getZ() >> 4)
               && prevent(editProp, srcClaim, mgr.getChunk(new ChunkDimPos(level, toPos)))) {
               return true;
            }
         }

         for (BlockPos pos : resolver.getToDestroy()) {
            if (prevent(editProp, srcClaim, mgr.getChunk(new ChunkDimPos(level, pos)))) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean prevent(PrivacyProperty editProp, ClaimedChunk srcClaim, ClaimedChunk dstClaim) {
      if (srcClaim != dstClaim && dstClaim != null) {
         UUID srcId = srcClaim == null ? Util.NIL_UUID : srcClaim.getTeamData().getTeam().getTeamId();
         UUID dstId = dstClaim.getTeamData().getTeam().getTeamId();
         return !srcId.equals(dstId) && dstClaim.getTeamData().getTeam().getProperty(editProp) != PrivacyMode.PUBLIC;
      } else {
         return false;
      }
   }
}
