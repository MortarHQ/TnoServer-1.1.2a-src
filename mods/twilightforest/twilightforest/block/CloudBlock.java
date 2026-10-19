package twilightforest.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.config.TFConfig;
import twilightforest.init.TFParticleType;
import twilightforest.network.ParticlePacket;

public class CloudBlock extends Block {
   @Nullable
   protected final Precipitation precipitation;

   public CloudBlock(Properties properties, @Nullable Precipitation precipitation) {
      super(properties);
      this.precipitation = precipitation;
   }

   public static boolean shouldSnow(LevelReader level, BlockPos pos) {
      if (pos.getY() >= level.getMinBuildHeight() && pos.getY() < level.getMaxBuildHeight() && level.getBrightness(LightLayer.BLOCK, pos) < 10) {
         BlockState blockstate = level.getBlockState(pos);
         return (blockstate.isAir() || blockstate.is(Blocks.SNOW)) && Blocks.SNOW.defaultBlockState().canSurvive(level, pos);
      } else {
         return false;
      }
   }

   public static void addEntityMovementParticles(Level level, BlockPos pos, Entity entity, boolean jumping) {
      if (!level.getRandom().nextBoolean()) {
         Vec3 deltaMovement = entity.getDeltaMovement();
         BlockPos blockpos1 = entity.blockPosition();
         double jumpMultiplier = jumping ? 2.0 : 1.0;
         double x = entity.getX() + (level.getRandom().nextDouble() - 0.5) * entity.dimensions.width() * jumpMultiplier;
         double y = entity.getY() + 0.1;
         double z = entity.getZ() + (level.getRandom().nextDouble() - 0.5) * entity.dimensions.width() * jumpMultiplier;
         if (blockpos1.getX() != pos.getX()) {
            x = Mth.clamp(x, pos.getX(), pos.getX() + 1.0);
         }

         if (blockpos1.getZ() != pos.getZ()) {
            z = Mth.clamp(z, pos.getZ(), pos.getZ() + 1.0);
         }

         level.addParticle((ParticleOptions)TFParticleType.CLOUD_PUFF.get(), x, y, z, deltaMovement.x * -0.5, 0.015 * jumpMultiplier, deltaMovement.z * -0.5);
      }
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance, 0.1F, level.damageSources().fall());
   }

   public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
      return 1;
   }

   @Nullable
   public Precipitation getPrecipitation() {
      return this.precipitation;
   }

   public Pair<Precipitation, Float> getCurrentPrecipitation(BlockPos pos, Level level, float rainLevel) {
      if (this.getPrecipitation() == null) {
         return rainLevel > 0.0F ? Pair.of(((Biome)level.getBiome(pos).value()).getPrecipitationAt(pos), rainLevel) : Pair.of(Precipitation.NONE, 0.0F);
      } else {
         return Pair.of(this.getPrecipitation(), 1.0F);
      }
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (level.isAreaLoaded(pos, 1) && TFConfig.commonCloudBlockPrecipitationDistance != 0) {
         Pair<Precipitation, Float> pair = this.getCurrentPrecipitation(pos, level, level.getRainLevel(1.0F));
         if ((Float)pair.getRight() > 0.0F) {
            Precipitation precipitation = (Precipitation)pair.getLeft();
            if (precipitation == Precipitation.RAIN || precipitation == Precipitation.SNOW) {
               int highestRainyBlock = pos.getY() - 1;

               for (int y = pos.getY() - 1;
                  y > pos.getY() - TFConfig.commonCloudBlockPrecipitationDistance && !Types.MOTION_BLOCKING.isOpaque().test(level.getBlockState(pos.atY(y)));
                  y--
               ) {
                  highestRainyBlock = y - 1;
               }

               if (highestRainyBlock > level.getMinBuildHeight()) {
                  if (precipitation == Precipitation.SNOW) {
                     int snowHeight = level.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
                     BlockPos snowOnPos = pos.atY(highestRainyBlock + 1);
                     if (snowHeight > 0 && shouldSnow(level, snowOnPos)) {
                        BlockState snowOnState = level.getBlockState(snowOnPos);
                        if (snowOnState.is(Blocks.SNOW)) {
                           int k = (Integer)snowOnState.getValue(SnowLayerBlock.LAYERS);
                           if (k < Math.min(snowHeight, 8)) {
                              BlockState snowLayerState = (BlockState)snowOnState.setValue(SnowLayerBlock.LAYERS, k + 1);
                              Block.pushEntitiesUp(snowOnState, snowLayerState, level, snowOnPos);
                              level.setBlockAndUpdate(snowOnPos, snowLayerState);
                           }
                        } else {
                           level.setBlockAndUpdate(snowOnPos, Blocks.SNOW.defaultBlockState());
                        }
                     }
                  }

                  BlockPos rainOnPos = pos.atY(highestRainyBlock);
                  BlockState rainOnState = level.getBlockState(rainOnPos);
                  rainOnState.getBlock().handlePrecipitation(rainOnState, level, rainOnPos, precipitation);
               }
            }
         }
      }
   }

   public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity living, int numberOfParticles) {
      ParticlePacket particlePacket = new ParticlePacket();
      int maxI = Mth.clamp((int)living.fallDistance * 2, 8, 40);
      double bbWidth = living.getBbWidth();
      double y = living.getY() + 0.1;
      double ySpeed = 5.0E-4 * maxI;

      for (int i = 0; i < maxI; i++) {
         double xSpd = (living.getRandom().nextDouble() - 0.5) * bbWidth * 2.5;
         double zSpd = (living.getRandom().nextDouble() - 0.5) * bbWidth * 2.5;
         double x = living.getX() + xSpd;
         double z = living.getZ() + zSpd;
         double xSpeed = xSpd * 0.0035 * maxI;
         double zSpeed = zSpd * 0.0035 * maxI;
         particlePacket.queueParticle((ParticleOptions)TFParticleType.CLOUD_PUFF.get(), false, x, y, z, xSpeed, ySpeed, zSpeed);
      }

      PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(pos), particlePacket, new CustomPacketPayload[0]);
      return true;
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (this.canSpawnCloudParticles(entity, level.getRandom())) {
         addEntityMovementParticles(level, pos, entity, false);
      }
   }

   public boolean canSpawnCloudParticles(Entity entity, RandomSource random) {
      return entity.getDeltaMovement().x() == 0.0 && entity.getDeltaMovement().z() == 0.0 && random.nextInt(20) != 0
         ? false
         : entity.tickCount % 2 == 0 && !entity.isSpectator();
   }

   public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (level.isClientSide() && state.getRenderShape() != RenderShape.INVISIBLE) {
         addEntityMovementParticles(level, pos, entity, false);
      }

      return true;
   }
}
