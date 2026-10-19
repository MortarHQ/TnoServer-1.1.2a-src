package net.jadenxgamer.netherexp.core.block;

import java.awt.Color;
import javax.annotation.Nullable;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class SwirlsBlock extends AmethystClusterBlock implements BonemealableBlock {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   @Nullable
   private final Color particleColor;

   public SwirlsBlock(Color particleColor, Properties properties) {
      super(7.0F, 3.0F, properties);
      this.particleColor = particleColor;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(ACTIVE, false));
   }

   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         if ((Boolean)state.getValue(ACTIVE) || !this.canActivateSwirls(living)) {
            return;
         }

         level.playSound(null, pos, JNESoundEvents.SOUL_SWIRLS_BOOST.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         level.setBlock(pos, (BlockState)state.cycle(ACTIVE), 3);
         level.scheduleTick(pos, this, (Integer)JNEConfigs.SOUL_SWIRLS_COOLDOWN.get() * 20);
         if (!level.isClientSide()) {
            int amplifier = 0;
            MobEffectInstance currentEffect = living.getEffect(JNEMobEffects.SOUL_SPEED);
            if (currentEffect != null) {
               amplifier = Math.min(2, currentEffect.getAmplifier() + 1);
            }

            living.addEffect(
               new MobEffectInstance(JNEMobEffects.SOUL_SPEED, (Integer)JNEConfigs.SOUL_SPEED_DURATION.get() * 20, amplifier, false, true), entity
            );
         } else {
            Direction[] directions = Direction.values();

            for (Direction direction : directions) {
               BlockPos directionPos = pos.relative(direction);
               if (!level.getBlockState(directionPos).isSolidRender(level, directionPos)) {
                  Axis axis = direction.getAxis();
                  double x = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : level.random.nextFloat();
                  double y = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : level.random.nextFloat();
                  double z = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : level.random.nextFloat();
                  SwirlsBlock.Client.swirlPopParticle(this.particleColor, level, level.random, pos.getX() + x, pos.getY() + y, pos.getZ() + z);
               }
            }
         }
      }
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(ACTIVE)) {
         level.setBlock(pos, (BlockState)state.cycle(ACTIVE), 3);
         level.playSound(null, pos, JNESoundEvents.SOUL_SWIRLS_DEACTIVATE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (level.isClientSide()) {
         if ((Boolean)state.getValue(ACTIVE) && random.nextInt(20) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 0.8;
            double z = pos.getZ() + random.nextDouble();
            SwirlsBlock.Client.swirlPopParticle(this.particleColor, level, random, x, y, z);
         }
      }
   }

   private boolean canActivateSwirls(LivingEntity entity) {
      return !entity.getType().is(JNETags.EntityTypes.CANT_ACTIVATE_SWIRLS)
         && HolderHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, entity.getItemBySlot(EquipmentSlot.FEET)) <= 0;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE, FACING, WATERLOGGED});
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return state.getValue(FACING) == Direction.UP && (Boolean)JNEConfigs.BONE_MEAL_SOUL_SWIRLS.get();
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
      BlockPos relativePos = pos.relative(direction);
      if (level.getBlockState(relativePos).isAir() && level.getBlockState(relativePos.below()).is(JNETags.Blocks.SOUL_SANDS)) {
         level.setBlock(pos, this.defaultBlockState(), 3);
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return state.getValue(ACTIVE) ? 15 : 0;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      private static void swirlPopParticle(@Nullable Color particleColor, Level level, RandomSource random, double x, double y, double z) {
         WorldParticleBuilder.create(particleColor == null ? JNEParticleTypes.SOUL_SWIRL_POP : JNEParticleTypes.TINT_SWIRL_POP)
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.05F, 0.13F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(particleColor == null ? Color.WHITE : particleColor).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(random.nextInt(20, 30))
            .disableNoClip()
            .setGravity(0.05F)
            .setMotion(0.0, 0.04, 0.0)
            .spawn(level, x, y, z);
      }
   }
}
