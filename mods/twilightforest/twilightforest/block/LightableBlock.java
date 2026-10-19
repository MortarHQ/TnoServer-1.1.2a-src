package twilightforest.block;

import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFParticleType;

public interface LightableBlock {
   EnumProperty<LightableBlock.Lighting> LIGHTING = EnumProperty.create("lighting", LightableBlock.Lighting.class);

   default ItemInteractionResult tryLightCandles(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player) {
      if (stack.isEmpty() && player.getAbilities().mayBuild && state.getValue(LIGHTING) != LightableBlock.Lighting.NONE) {
         this.extinguish(player, state, level, pos);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return this.canBeLit(state) && stack.canPerformAction(ItemAbilities.FIRESTARTER_LIGHT)
            ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
            : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   default void lightCandlesWithProjectile(Level level, BlockState state, BlockHitResult result, Projectile projectile) {
      if (!level.isClientSide() && projectile.isOnFire() && this.canBeLit(state)) {
         this.setLit(level, state, result.getBlockPos(), true);
      }
   }

   default boolean canBeLit(BlockState state) {
      return state.getValue(LIGHTING) == LightableBlock.Lighting.NONE;
   }

   Iterable<Vec3> getParticleOffsets(BlockState var1, LevelAccessor var2, BlockPos var3);

   default void addParticlesAndSound(
      Level level, BlockPos pos, double xFraction, double yFraction, double zFraction, RandomSource rand, LightableBlock.Lighting lighting
   ) {
      this.addParticlesAndSound(level, pos.getX() + xFraction, pos.getY() + yFraction, pos.getZ() + zFraction, rand, lighting);
   }

   default void addParticlesAndSound(Level level, double x, double y, double z, RandomSource rand, LightableBlock.Lighting lighting) {
      float var3 = rand.nextFloat();
      if (var3 < 0.3F) {
         if (lighting == LightableBlock.Lighting.NORMAL) {
            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
         }

         if (var3 < 0.17F) {
            level.playLocalSound(
               x + 0.5, y + 0.5, z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false
            );
         }
      }
      ParticleOptions particle = switch (lighting) {
         case DIM -> (SimpleParticleType)TFParticleType.DIM_FLAME.get();
         case OMINOUS -> (SimpleParticleType)TFParticleType.OMINOUS_FLAME.get();
         default -> ParticleTypes.SMALL_FLAME;
      };
      level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
   }

   default void extinguish(@Nullable Player player, BlockState state, LevelAccessor accessor, BlockPos pos) {
      this.setLit(accessor, state, pos, false);
      if (state.getBlock() instanceof LightableBlock lightableBlock) {
         lightableBlock.getParticleOffsets(state, accessor, pos)
            .forEach(vec3 -> accessor.addParticle(ParticleTypes.SMOKE, pos.getX() + vec3.x, pos.getY() + vec3.y, pos.getZ() + vec3.z, 0.0, 0.025, 0.0));
      }

      accessor.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
      accessor.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
   }

   default void setLit(LevelAccessor accessor, BlockState state, BlockPos pos, boolean lit) {
      accessor.setBlock(pos, (BlockState)state.setValue(LIGHTING, lit ? LightableBlock.Lighting.NORMAL : LightableBlock.Lighting.NONE), 11);
   }

   enum Lighting implements StringRepresentable {
      NONE,
      NORMAL,
      DIM,
      OMINOUS;

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
