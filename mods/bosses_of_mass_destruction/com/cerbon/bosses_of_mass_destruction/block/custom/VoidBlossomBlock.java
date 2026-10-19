package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityAdapter;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityStats;
import com.cerbon.bosses_of_mass_destruction.packet.custom.HealS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class VoidBlossomBlock extends Block {
   private static final int healAnimationDelay = 16;
   private static final int healDelay = 64;
   private final VoxelShape shape = box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0);

   public VoidBlossomBlock(Properties properties) {
      super(properties);
   }

   public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
      level.scheduleTick(pos, this, 1);
   }

   public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
      level.scheduleTick(pos, this, 64);
      this.healNearbyEntities(level, pos);
   }

   private void healNearbyEntities(ServerLevel level, BlockPos pos) {
      level.getEntitiesOfClass(VoidBlossomEntity.class, new AABB(pos).inflate(40.0, 20.0, 40.0))
         .forEach(
            voidBlossom -> {
               CapabilityUtils.getLevelEventScheduler(level)
                  .addEvent(
                     new TimedEvent(
                        () -> LichUtils.cappedHeal(
                           new EntityAdapter(voidBlossom), new EntityStats(voidBlossom), VoidBlossomEntity.hpMilestones, 10.0F, voidBlossom::heal
                        ),
                        16
                     )
                  );
               Dispatcher.sendToClientsLoadingPos(
                  new HealS2CPacket(VecUtils.asVec3(pos).add(VecUtils.unit.scale(0.5)), voidBlossom.position().add(VecUtils.yAxis.scale(5.0))),
                  level,
                  voidBlossom.position()
               );
            }
         );
   }

   @NotNull
   public BlockState updateShape(
      @NotNull BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState neighborState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos neighborPos
   ) {
      if (direction == Direction.DOWN && !this.canSurvive(state, level, pos)) {
         this.destroy(level, pos, state);
         return Blocks.AIR.defaultBlockState();
      } else {
         return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
      }
   }

   public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
      return canSupportCenter(level, pos, Direction.UP) && !level.isWaterAt(pos);
   }

   public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = -1; y <= 1; y++) {
               if (x != 0 || z != 0) {
                  BlockPos pos1 = pos.offset(x, y, z);
                  if (level.getBlockState(pos1).getBlock() == BMDBlocks.VINE_WALL.get()) {
                     level.scheduleTick(pos1, (Block)BMDBlocks.VINE_WALL.get(), (2 - y) * 20 + RandomUtils.range(0, 19));
                  }
               }
            }
         }
      }
   }

   @NotNull
   public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return this.shape;
   }

   @NotNull
   public BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (level.isClientSide) {
         for (int i = 0; i < 12; i++) {
            Vec3 vel = VecUtils.yAxis.scale(RandomUtils.range(0.1, 0.2));
            Vec3 spawnPos = VecUtils.asVec3(pos)
               .add(VecUtils.unit.scale(0.5))
               .add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().scale(0.5));
            VoidBlossomBlock.Particles.spikeParticleFactory.build(spawnPos, vel);
         }
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   @OnlyIn(Dist.CLIENT)
   public static void handleVoidBlossomHeal(ClientLevel level, Vec3 source, Vec3 dest) {
      spawnHealParticle(dest, source, level);
      spawnChargeParticle(source, level);
   }

   @OnlyIn(Dist.CLIENT)
   private static void spawnChargeParticle(Vec3 source, ClientLevel level) {
      Vec3 particlePos = source.add(VecUtils.yAxis.scale(0.25));
      CapabilityUtils.getLevelEventScheduler(level)
         .addEvent(
            new TimedEvent(
               () -> VoidBlossomBlock.Particles.healParticleFactory.build(particlePos.add(RandomUtils.randVec().scale(0.2)), Vec3.ZERO), 32, 32, () -> false
            )
         );
   }

   @OnlyIn(Dist.CLIENT)
   private static void spawnHealParticle(Vec3 dest, Vec3 source, ClientLevel level) {
      List<Vec3> particlePositions = new ArrayList<>();
      int numCirclePoints = 16;
      List<Vec3> circlePoints = MathUtils.circlePoints(0.5, numCirclePoints, dest.subtract(source).normalize()).stream().toList();
      MathUtils.lineCallback(source, dest, 32, (pos, ix) -> particlePositions.add(pos.add(circlePoints.get(ix % numCirclePoints))));
      AtomicInteger i = new AtomicInteger();
      CapabilityUtils.getLevelEventScheduler(level).addEvent(new TimedEvent(() -> {
         VoidBlossomBlock.Particles.healParticleFactory.build(particlePositions.get(i.get()), Vec3.ZERO);
         VoidBlossomBlock.Particles.healParticleFactory.build(particlePositions.get(i.get() + 1), Vec3.ZERO);
         i.addAndGet(2);
      }, 0, 16, () -> false));
   }

   public static void handleVoidBlossomPlace(Vec3 pos) {
      for (int i = 0; i <= 12; i++) {
         Vec3 spawnPos = pos.add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().scale(0.5));
         Vec3 vel = VecUtils.yAxis.scale(RandomUtils.range(0.1, 0.3));
         int randomRot = RandomUtils.range(0, 360);
         float angularMomentum = RandomUtils.randSign() * 4.0F;
         VoidBlossomBlock.Particles.petalParticleFactory
            .continuousRotation(f -> randomRot + f.getAge() * angularMomentum)
            .continuousVelocity(f -> vel.scale(1.0 - f.ageRatio))
            .build(spawnPos, vel);
      }
   }

   public static class Particles {
      private static final ClientParticleBuilder spikeParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.LINE.get())
         .color(f -> MathUtils.lerpVec(f, Vec3Colors.VOID_PURPLE, Vec3Colors.ULTRA_DARK_PURPLE))
         .colorVariation(0.15)
         .brightness(15728880)
         .scale(0.25F)
         .age(10, 15);
      private static final ClientParticleBuilder healParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.OBSIDILITH_BURST.get())
         .color(f -> MathUtils.lerpVec(f, Vec3Colors.PINK, Vec3Colors.ULTRA_DARK_PURPLE))
         .colorVariation(0.15)
         .brightness(15728880)
         .scale(f -> 0.4F * (1.0F - f * 0.75F))
         .age(10);
      private static final ClientParticleBuilder petalParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.PETAL.get())
         .color(f -> MathUtils.lerpVec(f, Vec3Colors.PINK, Vec3Colors.ULTRA_DARK_PURPLE))
         .brightness(15728880)
         .colorVariation(0.15)
         .scale(f -> 0.15F * (1.0F - f * 0.25F))
         .age(30);
   }
}
