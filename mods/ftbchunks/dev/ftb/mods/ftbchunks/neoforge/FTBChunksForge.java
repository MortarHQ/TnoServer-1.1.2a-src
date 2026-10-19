package dev.ftb.mods.ftbchunks.neoforge;

import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.api.FTBChunksTags;
import dev.ftb.mods.ftbchunks.api.Protection;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftbchunks.neoforge.integration.FTBBackups3Integration;
import dev.ftb.mods.ftbchunks.util.FireSpreadHelper;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseFireBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.neoforged.neoforge.event.level.BlockEvent.FluidPlaceBlockEvent;

@Mod("ftbchunks")
public class FTBChunksForge {
   public FTBChunksForge(IEventBus modEventBus) {
      NeoForge.EVENT_BUS.addListener(this::entityInteractSpecific);
      NeoForge.EVENT_BUS.addListener(this::mobGriefing);
      NeoForge.EVENT_BUS.addListener(this::fluidCauseFire);
      if (ModList.get().isLoaded("ftbbackups3")) {
         FTBBackups3Integration.init();
      }

      ForceLoading.setup(modEventBus);
      FTBChunks.instance = new FTBChunks();
   }

   private void entityInteractSpecific(EntityInteractSpecific event) {
      if (!event.getEntity().level().isClientSide
         && ClaimedChunkManagerImpl.getInstance()
            .shouldPreventInteraction(event.getEntity(), event.getHand(), event.getEntity().blockPosition(), Protection.INTERACT_ENTITY, event.getTarget())) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }

   private void mobGriefing(EntityMobGriefingEvent event) {
      if (event.getEntity().getType().is(FTBChunksTags.Entities.ENTITY_MOB_GRIEFING_BLACKLIST_TAG) && !event.getEntity().level().isClientSide()) {
         ClaimedChunkImpl cc = ClaimedChunkManagerImpl.getInstance().getChunk(new ChunkDimPos(event.getEntity()));
         if (cc != null && !cc.allowMobGriefing()) {
            event.setCanGrief(false);
         }
      }
   }

   private void fluidCauseFire(FluidPlaceBlockEvent event) {
      if (event.getNewState().getBlock() instanceof BaseFireBlock
         && FireSpreadHelper.shouldPreventFireSpread(event.getLevel(), event.getLiquidPos(), event.getPos())) {
         event.setCanceled(true);
      }
   }
}
