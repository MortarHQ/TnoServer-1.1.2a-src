package dev.ftb.mods.ftbchunks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.neoforge.FTBCUtilsImpl;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class FTBCUtils {
   @ExpectPlatform
   @Transformed
   public static boolean isRail(Block block) {
      return FTBCUtilsImpl.isRail(block);
   }

   public static void forceHeldItemSync(ServerPlayer sp, InteractionHand hand) {
      if (sp.connection != null) {
         switch (hand) {
            case MAIN_HAND:
               sp.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, sp.getInventory().selected, sp.getItemInHand(hand)));
               break;
            case OFF_HAND:
               sp.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, 40, sp.getItemInHand(hand)));
         }
      }
   }

   public static boolean shouldProtectNonLivingEntity(Entity e, DamageSource source) {
      if (FTBChunks.isNonLivingOrArmorStand(e) && !e.level().isClientSide()) {
         ClaimedChunkManager manager = FTBChunksAPI.api().getManager();
         ClaimedChunk cc = manager.getChunk(new ChunkDimPos(e.level(), e.blockPosition()));
         if (cc == null) {
            return false;
         } else {
            return source.is(DamageTypeTags.IS_EXPLOSION) && !cc.getTeamData().canExplosionsDamageTerrain()
               ? true
               : source.getEntity() instanceof ServerPlayer serverPlayer
                  && !manager.getBypassProtection(serverPlayer.getUUID())
                  && !cc.getTeamData().canPlayerUse(serverPlayer, FTBChunksProperties.NONLIVING_ENTITY_ATTACK_MODE);
         }
      } else {
         return false;
      }
   }
}
