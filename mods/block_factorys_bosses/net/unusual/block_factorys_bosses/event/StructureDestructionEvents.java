package net.unusual.block_factorys_bosses.event;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiManager.Occupancy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRisePOI;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@EventBusSubscriber
public class StructureDestructionEvents {
   public static String DESTRUCTION_CANCEL_MESSAGE = "message.block_factorys_bosses.dragon_tower.block_protection";

   @SubscribeEvent
   public static void onBlockBreak(BreakEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (!event.getPlayer().isCreative()) {
            if (!isProtectionExempt(event.getState())) {
               if (!isTowerDefeated(level, isPartOfDragonTower(level, event.getPos()))) {
                  event.setCanceled(true);
                  event.getPlayer().displayClientMessage(Component.translatable(DESTRUCTION_CANCEL_MESSAGE).withStyle(ChatFormatting.RED), true);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onBlockPlace(EntityPlaceEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (!(event.getEntity() instanceof Player player && player.isCreative())) {
            if (!isProtectionExempt(event.getState())) {
               if (!isTowerDefeated(level, isPartOfDragonTower(level, event.getPos()))) {
                  event.setCanceled(true);
                  if (event.getEntity() instanceof ServerPlayer player) {
                     player.displayClientMessage(Component.translatable(DESTRUCTION_CANCEL_MESSAGE).withStyle(ChatFormatting.RED), true);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onExplosion(Detonate event) {
      if (event.getLevel() instanceof ServerLevel level) {
         event.getAffectedBlocks().removeIf(pos -> !isProtectionExempt(level.getBlockState(pos)) && !isTowerDefeated(level, isPartOfDragonTower(level, pos)));
      }
   }

   private static boolean isProtectionExempt(BlockState blockState) {
      return blockState.is(BossesRiseTags.Blocks.PROTECTION_EXEMPT);
   }

   private static StructureStart isPartOfDragonTower(Level level, BlockPos pos) {
      if (level instanceof ServerLevel serverLevel) {
         StructureManager structureManager = serverLevel.structureManager();

         for (Entry<Structure, LongSet> entry : structureManager.getAllStructuresAt(pos).entrySet()) {
            if (entry.getKey().type() == BossesRise.DRAGON_TOWER.get()) {
               return structureManager.getStructureAt(pos, entry.getKey());
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static boolean isTowerDefeated(ServerLevel level, StructureStart structureStart) {
      if (structureStart != null && structureStart.isValid()) {
         BoundingBox boundingBox = structureStart.getBoundingBox();
         PoiManager poiManager = level.getPoiManager();
         int radius = 32;
         boolean hasBossSpawner = poiManager.getInRange(
               holder -> holder.is(BossesRisePOI.BOSS_SPAWNER.getKey()), boundingBox.getCenter().above(32), radius, Occupancy.ANY
            )
            .map(PoiRecord::getPos)
            .anyMatch(boundingBox::isInside);
         return !hasBossSpawner;
      } else {
         return true;
      }
   }
}
