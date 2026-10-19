package com.tensura_tno.client;

import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;

public final class RimuruPlushParticles implements IClientBlockExtensions {
   private final BlockState lightWool;
   private final BlockState darkWool;

   public RimuruPlushParticles() {
      this(Blocks.LIGHT_BLUE_WOOL, Blocks.BLUE_WOOL);
   }

   public RimuruPlushParticles(Block light, Block dark) {
      this.lightWool = light.defaultBlockState();
      this.darkWool = dark.defaultBlockState();
   }

   public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
      if (!(level instanceof ClientLevel clientLevel)) {
         return true;
      } else {
         List<AABB> boxes = state.getShape(level, pos).toAabbs();
         if (boxes.isEmpty()) {
            return true;
         }

         double volume = boxes.stream().mapToDouble(RimuruPlushParticles::volume).sum();
         Vec3 centre = state.getShape(level, pos).bounds().getCenter();

         for (int i = 0; i < 32; i++) {
            double remaining = level.random.nextDouble() * volume;
            AABB selected = boxes.getLast();

            for (AABB box : boxes) {
               remaining -= volume(box);
               if (remaining <= 0.0) {
                  selected = box;
                  break;
               }
            }

            double x = selected.minX + level.random.nextDouble() * selected.getXsize();
            double y = selected.minY + level.random.nextDouble() * selected.getYsize();
            double z = selected.minZ + level.random.nextDouble() * selected.getZsize();
            manager.add(
               new TerrainParticle(
                     clientLevel, pos.getX() + x, pos.getY() + y, pos.getZ() + z, x - centre.x, y - centre.y, z - centre.z, this.wool(i % 2 == 0), pos
                  )
                  .setPower(0.5F)
                  .scale(0.75F)
            );
         }

         return true;
      }
   }

   public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
      if (level instanceof ClientLevel clientLevel && target instanceof BlockHitResult hit) {
         Vec3 point = hit.getLocation().relative(hit.getDirection(), 0.02);
         manager.add(
            new TerrainParticle(clientLevel, point.x, point.y, point.z, 0.0, 0.0, 0.0, this.wool(level.random.nextBoolean()), hit.getBlockPos())
               .setPower(0.2F)
               .scale(0.6F)
         );
      }

      return true;
   }

   private BlockState wool(boolean light) {
      return light ? this.lightWool : this.darkWool;
   }

   private static double volume(AABB box) {
      return box.getXsize() * box.getYsize() * box.getZsize();
   }
}
