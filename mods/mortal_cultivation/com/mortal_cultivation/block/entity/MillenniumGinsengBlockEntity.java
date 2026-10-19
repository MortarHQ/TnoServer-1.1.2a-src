package com.mortal_cultivation.block.entity;

import com.mortal_cultivation.block.MillenniumGinsengBlock;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.LevelChunk.EntityCreationType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.level.ChunkEvent.Load;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class MillenniumGinsengBlockEntity extends BlockEntity {
   public static final double STARTLE_DISTANCE = 7.0;
   private static final int CHECK_INTERVAL = 5;

   public MillenniumGinsengBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityRegistry.MILLENNIUM_GINSENG.get(), pos, state);
   }

   public static void serverTick(Level level, BlockPos pos, BlockState state, MillenniumGinsengBlockEntity ginseng) {
      if (level instanceof ServerLevel serverLevel
         && !ginseng.isRemoved()
         && state.getBlock() instanceof MillenniumGinsengBlock
         && level.getGameTime() % 5L == Math.floorMod(pos.asLong(), 5)) {
         Player player = serverLevel.getNearestPlayer(
            pos.getX() + 0.5,
            pos.getY(),
            pos.getZ() + 0.5,
            7.0,
            candidate -> candidate instanceof Player nearbyPlayer && MillenniumGinsengBlock.isThreat(nearbyPlayer)
         );
         if (player != null) {
            MillenniumGinsengBlock.startle(serverLevel, pos, player);
         }
      }
   }

   @SubscribeEvent
   public static void onChunkLoad(Load event) {
      if (event.getChunk() instanceof LevelChunk chunk && chunk.getLevel() instanceof ServerLevel) {
         LevelChunkSection[] sections = chunk.getSections();
         MutableBlockPos cursor = new MutableBlockPos();

         for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
            LevelChunkSection section = sections[sectionIndex];
            if (section.maybeHas(state -> state.is((Block)BlockRegistry.MILLENNIUM_GINSENG.get()))) {
               int minY = chunk.getSectionYFromSectionIndex(sectionIndex) << 4;

               for (int y = 0; y < 16; y++) {
                  for (int z = 0; z < 16; z++) {
                     for (int x = 0; x < 16; x++) {
                        if (section.getBlockState(x, y, z).is((Block)BlockRegistry.MILLENNIUM_GINSENG.get())) {
                           cursor.set(chunk.getPos().getMinBlockX() + x, minY + y, chunk.getPos().getMinBlockZ() + z);
                           BlockPos pos = cursor.immutable();
                           if (chunk.getBlockEntity(pos, EntityCreationType.CHECK) == null && chunk.getBlockEntity(pos, EntityCreationType.IMMEDIATE) != null) {
                              chunk.setUnsaved(true);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
