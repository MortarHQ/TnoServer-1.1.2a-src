package com.tno_tensura_extras.compat.ftbchunks;

import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.api.Protection;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI.API;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public final class FtbChunksProtection {
   private FtbChunksProtection() {
   }

   public static boolean mayBreakHere(ServerPlayer player, BlockPos pos) {
      API api = FTBChunksAPI.api();
      return !api.isManagerLoaded() || !api.getManager().shouldPreventInteraction(player, InteractionHand.MAIN_HAND, pos, Protection.EDIT_BLOCK, null);
   }

   public static boolean mayEdit(ServerPlayer player, ResourceKey<Level> dimension, BlockPos pos) {
      API api = FTBChunksAPI.api();
      if (api.isManagerLoaded() && !(Boolean)FTBChunksWorldConfig.DISABLE_PROTECTION.get()) {
         ClaimedChunkManager manager = api.getManager();
         if (manager.getBypassProtection(player.getUUID())) {
            return true;
         }

         ClaimedChunk chunk = manager.getChunk(new ChunkDimPos(dimension, new ChunkPos(pos)));
         return chunk == null || chunk.getTeamData().canPlayerUse(player, FTBChunksProperties.BLOCK_EDIT_MODE);
      } else {
         return true;
      }
   }
}
