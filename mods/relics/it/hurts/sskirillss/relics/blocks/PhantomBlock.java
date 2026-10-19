package it.hurts.sskirillss.relics.blocks;

import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.feet.PhantomBootItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PhantomBlock extends Block {
   public PhantomBlock() {
      super(Properties.of().friction(0.98F).sound(SoundType.WOOL).dynamicShape().noOcclusion().instabreak());
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      level.scheduleTick(pos, this, Mth.nextInt(level.getRandom(), 80, 120));
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
      level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.MASTER, 1.0F, 2.0F);
      level.sendParticles(
         ParticleUtils.constructSimpleSpark(new Color(100 + random.nextInt(100), 0, 200 + random.nextInt(50)), 0.1F + random.nextFloat() * 0.2F, 40, 0.9F),
         pos.getX() + 0.5F,
         pos.getY() + 0.5F,
         pos.getZ() + 0.5F,
         25,
         0.5,
         0.5,
         0.5,
         0.05F
      );
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
      level.playSound(null, pos, SoundEvents.PHANTOM_FLAP, SoundSource.MASTER, 1.0F, 2.0F);
      RandomSource random = level.getRandom();
      double speed = Math.abs(entity.getDeltaMovement().y());

      for (int i = 0; i < speed * 75.0; i++) {
         level.addParticle(
            ParticleUtils.constructSimpleSpark(
               new Color(100 + random.nextInt(100), 0, 200 + random.nextInt(50)), 0.1F + random.nextFloat() * 0.2F, 40 + random.nextInt(40), 0.95F
            ),
            entity.getX() + MathUtils.randomFloat(random) * speed,
            entity.getY(),
            entity.getZ() + MathUtils.randomFloat(random) * speed,
            0.0,
            0.05F + speed * random.nextFloat() * 0.25,
            0.0
         );
      }
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (level.isClientSide()) {
         RandomSource random = level.getRandom();
         Vec3 motion = entity.getDeltaMovement();
         double speed = motion.multiply(1.0, 0.0, 1.0).length();
         float width = entity.getBbWidth();

         for (float i = 0.0F; i < speed; i += 0.05F) {
            level.addParticle(
               ParticleUtils.constructSimpleSpark(
                  new Color(100 + random.nextInt(100), 0, 200 + random.nextInt(50)), 0.2F + random.nextFloat() * 0.25F, 10 + random.nextInt(20), 0.9F
               ),
               entity.getX() + MathUtils.randomFloat(random) * width,
               entity.getY(),
               entity.getZ() + MathUtils.randomFloat(random) * width,
               -motion.x(),
               0.0,
               -motion.z()
            );
         }
      }
   }

   public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
      Vec3 motion = entity.getDeltaMovement();
      if (motion.y > -0.5) {
         super.updateEntityAfterFallOn(level, entity);
      } else {
         entity.setDeltaMovement(motion.x, -(motion.y / 1.5), motion.z);
      }
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext entityContext) {
         Entity entity = entityContext.getEntity();
         if (entity == null) {
            return Shapes.empty();
         }

         ItemStack stack = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.PHANTOM_BOOT.get());
         if (!entity.isShiftKeyDown() && !stack.isEmpty() && stack.getItem() instanceof PhantomBootItem relic && relic.isToggled(stack)) {
            VoxelShape shape = Shapes.block();
            if (entityContext.isAbove(shape, pos, true)) {
               return shape;
            }
         }

         return Shapes.empty();
      } else {
         return Shapes.empty();
      }
   }
}
