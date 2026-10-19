package net.jadenxgamer.netherexp.core.block;

import java.awt.Color;
import java.util.function.Supplier;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class SoulGlassBlock extends LightableBlock {
   private final Color color;

   public SoulGlassBlock(Color color, Supplier<SimpleParticleType> fireParticle, Properties properties) {
      super(
         fireParticle,
         properties.isValidSpawn(Blocks::never).isViewBlocking((s, g, p) -> false).isRedstoneConductor((s, g, p) -> false).isSuffocating((s, g, p) -> false)
      );
      this.color = color;
   }

   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      RandomSource random = level.random;
      if (entity instanceof LivingEntity living) {
         if (level.isClientSide() && random.nextInt(7) == 0) {
            SoulGlassBlock.Client.particle(level, random, living.getRandomX(1.5), living.getRandomY() - 0.25, living.getRandomZ(1.5), this.color);
         }

         if (HolderHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, living.getItemBySlot(EquipmentSlot.FEET)) > 0) {
            return;
         }

         double slowdown = (Double)JNEConfigs.SOUL_GLASS_MOVEMENT_SLOWDOWN.get();
         entity.makeStuckInBlock(state, new Vec3(slowdown, slowdown, slowdown));
      }
   }

   protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.getEntity();
         if (entity != null && entity.getType().is(JNETags.EntityTypes.CAN_PHASE_THROUGH_SOUL_GLASS)) {
            if (context.isAbove(Shapes.block(), pos, true) && !context.isDescending()) {
               return Shapes.block();
            }

            return Shapes.empty();
         }
      }

      return state.getShape(level, pos);
   }

   protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
      return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
   }

   protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
      return 1.0F;
   }

   protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   private static class Client {
      public static void particle(Level level, RandomSource random, double x, double y, double z, Color color) {
         WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
            .setFullBrightLighting()
            .setColorData(ColorParticleData.create(color).build())
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.05F, 0.13F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
            .setTransparencyData(GenericParticleData.create(0.5F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(random.nextInt(30, 40))
            .enableNoClip()
            .setGravity(0.0F)
            .setMotion(0.0, 0.04, 0.0)
            .spawn(level, x, y, z);
      }
   }
}
