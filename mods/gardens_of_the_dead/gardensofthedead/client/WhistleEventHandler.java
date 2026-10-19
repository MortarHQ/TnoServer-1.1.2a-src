package gardensofthedead.client;

import gardensofthedead.registry.ModBlocks;
import gardensofthedead.registry.ModParticleTypes;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class WhistleEventHandler {
   private static final Map<BlockPos, WhistleEventHandler.WhistleEffect> WHISTLE_EFFECTS = new HashMap<>();
   private static ResourceKey<Level> dimension;

   public static void onClientTick(ClientLevel level) {
      if (level.dimension() != dimension) {
         dimension = level.dimension();
         WHISTLE_EFFECTS.clear();
      } else {
         WHISTLE_EFFECTS.entrySet()
            .removeIf(entry -> entry.getValue().whistlingTimeRemaining < -30 || !level.getBlockState(entry.getKey()).is((Block)ModBlocks.WHISTLECANE.get()));
         WHISTLE_EFFECTS.forEach((pos, effect) -> effect.tick(level, pos));
      }
   }

   public static void add(BlockPos pos) {
      WHISTLE_EFFECTS.put(pos, new WhistleEventHandler.WhistleEffect());
   }

   private static class WhistleEffect {
      private static final int WHISTLING_TIME = 50;
      private static final int SMOKE_TIME = 30;
      private int whistlingTimeRemaining = 50;

      private void tick(Level level, BlockPos pos) {
         if (this.whistlingTimeRemaining-- >= 0) {
            int particleCount = 2;

            for (int i = 0; i < particleCount; i++) {
               spawnParticle(level, pos, 0.2, 0.05);
            }
         }

         if (this.whistlingTimeRemaining % 5 == 0) {
            spawnParticle(level, pos, 0.1, 0.0);
         }
      }

      private static void spawnParticle(Level level, BlockPos pos, double verticalSpeed, double horizontalSpeed) {
         RandomSource randomsource = level.getRandom();
         Vec3 offset = ((Block)ModBlocks.WHISTLECANE.get()).defaultBlockState().getOffset(level, pos);
         double width = 0.3125;
         double x = pos.getX() + offset.x + 0.5 + randomsource.nextDouble() * width - width / 2.0;
         double z = pos.getZ() + offset.z + 0.5 + randomsource.nextDouble() * width - width / 2.0;
         double y = pos.getY() + offset.y + 1.0;
         double xSpeed = randomsource.nextDouble() * horizontalSpeed - horizontalSpeed / 2.0;
         double zSpeed = randomsource.nextDouble() * horizontalSpeed - horizontalSpeed / 2.0;
         level.addAlwaysVisibleParticle((ParticleOptions)ModParticleTypes.WHISTLECANE_SMOKE.get(), true, x, y, z, xSpeed, verticalSpeed, zSpeed);
      }
   }
}
