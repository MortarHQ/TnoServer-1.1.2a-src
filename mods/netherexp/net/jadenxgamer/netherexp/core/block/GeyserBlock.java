package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GeyserBlock extends Block {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final BooleanProperty COOLDOWN = BooleanProperty.create("cooldown");
   private final Supplier<SimpleParticleType> ashParticle;
   private final Supplier<SimpleParticleType> smokeParticle;
   protected final TagKey<Biome> homeBiome;

   public GeyserBlock(TagKey<Biome> homeBiome, Supplier<SimpleParticleType> ashParticle, Supplier<SimpleParticleType> smokeParticle, Properties properties) {
      super(properties);
      this.homeBiome = homeBiome;
      this.ashParticle = ashParticle;
      this.smokeParticle = smokeParticle;
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(ACTIVE, false)).setValue(COOLDOWN, false));
   }

   public GeyserBlock(TagKey<Biome> homeBiome, ResourceLocation ashParticleId, Supplier<SimpleParticleType> smokeParticle, Properties properties) {
      super(properties);
      this.homeBiome = homeBiome;
      this.ashParticle = () -> this.getAshFromResourceLocation(ashParticleId);
      this.smokeParticle = smokeParticle;
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(ACTIVE, false)).setValue(COOLDOWN, false));
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (!entity.isShiftKeyDown() && !(Boolean)state.getValue(COOLDOWN)) {
         Vec3 velocity = entity.getDeltaMovement();
         entity.push(velocity.x, (Double)JNEConfigs.GEYSER_HEIGHT_VELOCITY.get(), velocity.z);
         level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 1.0F);
         level.setBlock(pos, (BlockState)state.setValue(COOLDOWN, true), 3);
         level.scheduleTick(pos, this, (Integer)JNEConfigs.GEYSER_COOLDOWN.get() * 20);
      }
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance, 0.5F, entity.damageSources().fall());
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(COOLDOWN)) {
         level.setBlock(pos, (BlockState)state.cycle(COOLDOWN), 3);
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
      return (BlockState)this.defaultBlockState().setValue(ACTIVE, !sneaking);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      double x = pos.getX();
      double y = pos.getY();
      double z = pos.getZ();
      if ((Boolean)state.getValue(ACTIVE)) {
         boolean inHomeBiome = level.getBiome(pos).is(this.homeBiome);
         MutableBlockPos mPos = new MutableBlockPos();
         if (!inHomeBiome) {
            for (int i = 0; i < 14; i++) {
               mPos.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
               BlockState mutableState = level.getBlockState(mPos);
               if (!mutableState.isSolidRender(level, mPos)) {
                  level.addParticle(
                     (ParticleOptions)this.ashParticle.get(),
                     mPos.getX() + random.nextDouble(),
                     mPos.getY() + random.nextDouble(),
                     mPos.getZ() + random.nextDouble(),
                     0.0,
                     0.0,
                     0.0
                  );
               }
            }
         }

         if (!(Boolean)state.getValue(COOLDOWN) && random.nextInt(2) == 0) {
            level.addParticle(
               (ParticleOptions)this.smokeParticle.get(),
               x + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1),
               y + 1.4,
               z + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1),
               0.0,
               0.008,
               0.0
            );
         }
      }
   }

   private SimpleParticleType getAshFromResourceLocation(ResourceLocation id) {
      return LookupRegistryHelper.getParticleType(id) instanceof SimpleParticleType simpleParticleType ? simpleParticleType : ParticleTypes.ASH;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE, COOLDOWN});
   }

   @Nullable
   public PushReaction getPistonPushReaction(BlockState state) {
      return PushReaction.DESTROY;
   }
}
