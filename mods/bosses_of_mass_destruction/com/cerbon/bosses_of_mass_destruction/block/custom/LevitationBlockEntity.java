package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.ChunkBlockCache;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.config.BMDConfig;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.util.AnimationUtils;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LevitationBlockEntity extends ChunkCacheBlockEntity implements GeoBlockEntity {
   private final AnimatableInstanceCache animationFactory = GeckoLibUtil.createInstanceCache(this);
   private static final double tableOfElevationRadius = ((BMDConfig)AutoConfig.getConfigHolder(BMDConfig.class).getConfig()).generalConfig.tableOfElevationRadius;
   public int animationAge = 0;
   private static final HashSet<ServerPlayer> flight = new HashSet<>();

   public LevitationBlockEntity(BlockPos pos, BlockState blockState) {
      super((Block)BMDBlocks.LEVITATION_BLOCK.get(), (BlockEntityType<?>)BMDBlockEntities.LEVITATION_BLOCK_ENTITY.get(), pos, blockState);
   }

   public void registerControllers(ControllerRegistrar data) {
      data.add(new AnimationController(this, 0, AnimationUtils.createIdlePredicate("rotate")));
   }

   public static void tick(Level level, BlockPos pos, BlockState state, LevitationBlockEntity entity) {
      ChunkCacheBlockEntity.tick(level, pos, state, entity);
      if (level.isClientSide) {
         entity.animationAge++;
         AABB box = getAffectingBox(level, VecUtils.asVec3(pos));

         for (Player player : level.getEntitiesOfClass(Player.class, box)) {
            for (double x : List.of(box.minX, box.maxX)) {
               double zRand = box.getCenter().z + box.getZsize() * RandomUtils.randDouble(0.5);
               LevitationBlockEntity.Particles.particlesFactory.build(randYPos(x, player, zRand), Vec3.ZERO);
            }

            for (double z : List.of(box.minZ, box.maxZ)) {
               double xRand = box.getCenter().x + box.getXsize() * RandomUtils.randDouble(0.5);
               LevitationBlockEntity.Particles.particlesFactory.build(randYPos(xRand, player, z), Vec3.ZERO);
            }
         }
      }
   }

   private static Vec3 randYPos(double x, Player player, double z) {
      return new Vec3(x, player.getY() + RandomUtils.randDouble(0.5) + 1.0, z);
   }

   public static void tickFlight(ServerPlayer player) {
      List<ChunkPos> chunksToCheck = new ArrayList<>();

      for (int x = -2; x <= 2; x++) {
         for (int z = -2; z <= 2; z++) {
            chunksToCheck.add(new ChunkPos(player.blockPosition().offset(new ChunkPos(x, z).getBlockAt(0, 0, 0))));
         }
      }

      boolean hasLevitationBlock = chunksToCheck.stream().anyMatch(chunkPos -> {
         Optional<ChunkBlockCache> blockCache = BMDCapabilities.getChunkBlockCache(player.level());
         if (blockCache.isPresent()) {
            List<BlockPos> blocks = blockCache.get().getBlocksFromChunk(chunkPos, (Block)BMDBlocks.LEVITATION_BLOCK.get());
            return blocks.stream().anyMatch(pos -> getAffectingBox(player.level(), VecUtils.asVec3(pos)).contains(player.position()));
         } else {
            return false;
         }
      });
      if (!hasLevitationBlock) {
         if (flight.contains(player)) {
            if (!player.isCreative() && !player.isSpectator()) {
               player.getAbilities().mayfly = false;
               player.getAbilities().flying = false;
               player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
            }

            flight.remove(player);
         }
      } else if (!flight.contains(player)) {
         flight.add(player);
         if (!player.getAbilities().mayfly) {
            player.getAbilities().mayfly = true;
            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
         }
      }
   }

   private static AABB getAffectingBox(Level level, Vec3 pos) {
      return new AABB(pos.x, level.getMinBuildHeight(), pos.z, pos.x + 1.0, level.getHeight(), pos.z + 1.0)
         .inflate(tableOfElevationRadius, 0.0, tableOfElevationRadius);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationFactory;
   }

   private static class Particles {
      private static final ClientParticleBuilder particlesFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.LINE.get())
         .color(Vec3Colors.COMET_BLUE)
         .brightness(15728880)
         .colorVariation(0.5)
         .scale(0.075F);
   }
}
