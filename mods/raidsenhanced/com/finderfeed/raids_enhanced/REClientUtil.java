package com.finderfeed.raids_enhanced;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.raids_enhanced.content.entities.player_blimp.PlayerBlimpEntity;
import com.finderfeed.raids_enhanced.content.entities.raid_drill.RaidDrill;
import com.finderfeed.raids_enhanced.content.util.HorizontalCircleRandomDirections;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class REClientUtil {
   public static void playerBlimpRotationPacket(int entityId, int rotationDirection) {
      if (FDClientHelpers.getClientLevel().getEntity(entityId) instanceof PlayerBlimpEntity playerBlimp) {
         playerBlimp.setRotatingState(rotationDirection);
      }
   }

   public static void handlePosEvent(Vec3 pos, int event, int data) {
      switch (event) {
         case 1:
            golemSmack(pos, data);
            break;
         case 2:
            lightningDebris(pos, data);
            break;
         case 3:
            drillDeath(pos, data);
      }
   }

   private static void drillDeath(Vec3 pos, int data) {
      Level level = FDClientHelpers.getClientLevel();
      ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
      Vec3 offsetVec = Vec3.ZERO;
      if (level.getEntity(data) instanceof RaidDrill raidDrill) {
         float yRot = raidDrill.getYRot();
         offsetVec = new Vec3(0.0, 0.0, 0.5).yRot((float)Math.toRadians(-yRot + 180.0F));
      }

      for (int i = 0; i < 80; i++) {
         float rndHeight = level.random.nextFloat() * 2.0F;
         float rndX = level.random.nextFloat() * 2.0F - 1.0F;
         float rndZ = level.random.nextFloat() * 2.0F - 1.0F;
         Vec3 ppos = pos.add(rndX, rndHeight, rndZ);
         float p = rndHeight / 2.0F;
         ppos = ppos.add(offsetVec.scale(p));
         Vec3 pspeed = new Vec3(rndX, 0.0, rndZ).normalize().scale(0.05 + level.random.nextFloat() * 0.1F).add(0.0, 0.05 + level.random.nextFloat() * 0.1, 0.0);
         Particle particle = particleEngine.createParticle(
            new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DARK_OAK_PLANKS.defaultBlockState()), ppos.x, ppos.y, ppos.z, pspeed.x, pspeed.y, pspeed.z
         );
         if (particle != null) {
            particle.setParticleSpeed(pspeed.x, pspeed.y, pspeed.z);
         }
      }
   }

   public static void lightningDebris(Vec3 pos, int data) {
      Level level = FDClientHelpers.getClientLevel();
      List<BlockState> states = collectStates(level, pos, 2);
      if (!states.isEmpty()) {
         ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

         for (int i = 0; i < 4; i++) {
            for (Vec3 dir : new HorizontalCircleRandomDirections(level.random, (i + 1) * 5, 1.0F)) {
               float strength = i * 0.05F + level.random.nextFloat() * 0.025F;
               float vspeed = level.random.nextFloat() * 0.4F + 0.2F;
               Vec3 ppos = pos.add(dir.scale(level.random.nextFloat())).add(0.0, 0.1, 0.0);
               Vec3 pspeed = dir.scale(strength).add(0.0, vspeed, 0.0);
               Particle particle = particleEngine.createParticle(
                  new BlockParticleOption(ParticleTypes.BLOCK, states.get(level.random.nextInt(states.size()))),
                  ppos.x,
                  ppos.y,
                  ppos.z,
                  pspeed.x,
                  pspeed.y,
                  pspeed.z
               );
               if (particle != null) {
                  particle.setParticleSpeed(pspeed.x, pspeed.y, pspeed.z);
               }
            }
         }
      }
   }

   public static void golemSmack(Vec3 pos, int data) {
      Level level = FDClientHelpers.getClientLevel();
      List<BlockState> states = collectStates(level, pos, 2);
      if (states.isEmpty()) {
         states.add(Blocks.STONE.defaultBlockState());
      }

      ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

      for (int i = 0; i < 7; i++) {
         for (Vec3 dir : new HorizontalCircleRandomDirections(level.random, i * 5, 1.0F)) {
            float strength = i * 0.05F + level.random.nextFloat() * 0.05F;
            float vspeed = level.random.nextFloat() * 0.4F + 0.05F;
            Vec3 ppos = pos.add(dir.scale(level.random.nextFloat())).add(0.0, 0.2, 0.0);
            Vec3 pspeed = dir.scale(strength).add(0.0, vspeed, 0.0);
            Particle particle = particleEngine.createParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, states.get(level.random.nextInt(states.size()))),
               ppos.x,
               ppos.y,
               ppos.z,
               pspeed.x,
               pspeed.y,
               pspeed.z
            );
            if (particle != null) {
               particle.setParticleSpeed(pspeed.x, pspeed.y, pspeed.z);
            }
         }
      }

      for (int i = 0; i < 4; i++) {
         for (Vec3 dir : new HorizontalCircleRandomDirections(level.random, i * 3, 1.0F)) {
            float strength = level.random.nextFloat() * 0.03F;
            float vspeed = level.random.nextFloat() * 0.01F;
            Vec3 ppos = pos.add(dir.scale(level.random.nextFloat() * i)).add(0.0, 0.2, 0.0);
            Vec3 pspeed = dir.scale(strength).add(0.0, vspeed, 0.0);
            Particle particle = particleEngine.createParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, ppos.x, ppos.y, ppos.z, pspeed.x, pspeed.y, pspeed.z);
            if (particle != null) {
               particle.setLifetime(20 + level.random.nextInt(10));
               particle.setParticleSpeed(pspeed.x, pspeed.y, pspeed.z);
            }
         }
      }
   }

   public static List<BlockState> collectStates(Level level, Vec3 pos, int radius) {
      BlockPos blockPos = new BlockPos((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z));
      List<BlockState> blockStates = new ArrayList<>();

      for (int x = -radius; x <= radius; x++) {
         for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
               BlockPos offset = blockPos.offset(x, y, z);
               BlockState blockState = level.getBlockState(offset);
               if (!blockState.isAir() && blockState.isCollisionShapeFullBlock(level, offset)) {
                  blockStates.add(blockState);
               }
            }
         }
      }

      return blockStates;
   }
}
