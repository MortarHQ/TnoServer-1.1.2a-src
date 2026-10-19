package net.jadenxgamer.netherexp.core.item;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.core.block.interfaces.GlowsporesApplicable;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class GlowsporesItem extends Item {
   public final Supplier<LodestoneWorldParticleType> particle;

   public GlowsporesItem(Supplier<LodestoneWorldParticleType> particle, Properties properties) {
      super(properties);
      this.particle = particle;
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      Player player = context.getPlayer();
      ItemStack stack = context.getItemInHand();
      BlockPos pos = context.getClickedPos();
      BlockState state = level.getBlockState(pos);
      boolean passed = false;
      if (player != null && state.getBlock() instanceof GlowsporesApplicable glowsporesApplicable && stack.is(glowsporesApplicable.glowsporeOfBlock())) {
         if (!glowsporesApplicable.canSporesBeApplied(state)) {
            return InteractionResult.FAIL;
         }

         if (glowsporesApplicable.affectedProperty() instanceof IntegerProperty integerProperty) {
            int currentValue = (Integer)state.getValue(integerProperty);
            int maxValue = integerProperty.getPossibleValues().size();
            if (currentValue < maxValue) {
               level.setBlock(pos, (BlockState)state.setValue(integerProperty, currentValue + 1), 3);
            }

            passed = true;
         } else if (glowsporesApplicable.affectedProperty() instanceof BooleanProperty booleanProperty) {
            boolean currentValue = (Boolean)state.getValue(booleanProperty);
            if (!currentValue) {
               level.setBlock(pos, (BlockState)state.cycle(booleanProperty), 3);
            }

            passed = true;
         }
      }

      if (passed) {
         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }

         if (level.isClientSide()) {
            GlowsporesItem.Client.glowsporeSorroundedParticle(level, pos, this.particle.get());
         }

         level.playSound(null, pos, JNESoundEvents.GLOWSPORES_APPLY.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useOn(context);
      }
   }

   public static void glowsporeParticle(Level level, RandomSource random, double x, double y, double z, LodestoneWorldParticleType particle, boolean goingUp) {
      if (level.isClientSide()) {
         GlowsporesItem.Client.glowsporeParticle(level, random, x, y, z, particle, goingUp);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void glowsporeParticle(Level level, RandomSource random, double x, double y, double z, LodestoneWorldParticleType particle, boolean goingUp) {
         WorldParticleBuilder.create(particle)
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.05F, 0.13F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(random.nextInt(30, 40))
            .disableNoClip()
            .setGravity(goingUp ? 0.0F : 0.05F)
            .setMotion(0.0, goingUp ? 0.04 : 0.0, 0.0)
            .spawn(level, x, y, z);
      }

      public static void glowsporeSorroundedParticle(Level level, BlockPos pos, LodestoneWorldParticleType particle) {
         RandomSource random = level.random;
         Direction[] directions = Direction.values();

         for (Direction direction : directions) {
            BlockPos relativePos = pos.relative(direction);
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
               Axis axis = direction.getAxis();
               double x = pos.getX() + (axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat());
               double y = pos.getY() + (axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat());
               double z = pos.getZ() + (axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat());
               WorldParticleBuilder.create(particle)
                  .setFullBrightLighting()
                  .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
                  .setScaleData(GenericParticleData.create(0.05F, 0.13F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
                  .setTransparencyData(GenericParticleData.create(1.0F).build())
                  .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                  .setSpritePicker(ParticleSpritePicker.WITH_AGE)
                  .setLifetime(random.nextInt(30, 40))
                  .disableNoClip()
                  .setGravity(0.05F)
                  .setMotion(0.0, 0.0, 0.0)
                  .spawn(level, x, y, z);
            }
         }
      }
   }
}
