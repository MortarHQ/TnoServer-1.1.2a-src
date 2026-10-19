package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.network.ParticlePacket;
import twilightforest.util.WorldUtil;

public class TimeLogCoreBlock extends SpecialMagicLogBlock {
   public TimeLogCoreBlock(Properties properties) {
      super(properties);
   }

   @Override
   public boolean doesCoreFunction() {
      return !TFConfig.disableTimeCore;
   }

   @Override
   void performTreeEffect(ServerLevel level, BlockPos pos, RandomSource rand) {
      int numticks = 24 * this.tickRate();

      for (int i = 0; i < numticks; i++) {
         BlockPos dPos = WorldUtil.randomOffset(rand, pos, TFConfig.timeCoreRange);
         BlockState state = level.getBlockState(dPos);
         if (!state.is(BlockTagGenerator.TIME_CORE_EXCLUDED)) {
            boolean worked = false;
            if (state.isRandomlyTicking()) {
               state.randomTick(level, dPos, rand);
               worked = true;
            }

            BlockEntity entity = level.getBlockEntity(dPos);
            if (entity != null) {
               BlockEntityTicker<BlockEntity> ticker = state.getTicker(level, entity.getType());
               if (ticker != null) {
                  ticker.tick(level, dPos, state, entity);
                  worked = true;
               }
            }

            if (worked) {
               Vec3 xyz = Vec3.atCenterOf(dPos);
               ParticlePacket particlePacket = new ParticlePacket();
               double yOffset = state.getOcclusionShape(level, dPos).max(Axis.Y);
               particlePacket.queueParticle(
                  (ParticleOptions)TFParticleType.LOG_CORE_PARTICLE.get(), false, xyz.add(0.0, yOffset - 0.5, 0.0), new Vec3(0.953, 0.698, 0.0)
               );
               PacketDistributor.sendToPlayersNear(level, null, xyz.x(), xyz.y(), xyz.z(), 64.0, particlePacket, new CustomPacketPayload[0]);
            }
         }
      }
   }

   @Override
   protected void playSound(Level level, BlockPos pos, RandomSource rand) {
      level.playSound(null, pos, (SoundEvent)TFSounds.TIME_CORE.get(), SoundSource.BLOCKS, 0.35F, 0.5F);
   }
}
