package net.unusual.block_factorys_bosses.block;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDoubleBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TallCandlesBlock extends AbstractDoubleBlock {
   private static final VoxelShape UPPER = box(3.0, -16.0, 3.0, 13.0, 16.0, 13.0);
   private static final VoxelShape LOWER = box(3.0, 0.0, 3.0, 13.0, 32.0, 13.0);
   public static final Vec3[] FLAME_POINTS_TWISTED = new Vec3[]{
      new Vec3(0.6875, 0.8125, 0.5), new Vec3(0.5625, 1.125, 0.5625), new Vec3(0.4375, 1.0, 0.4375), new Vec3(0.3125, 0.625, 0.5)
   };
   public static final Vec3[] FLAME_POINTS_CROSS = new Vec3[]{
      new Vec3(0.5, 1.0625, 0.5), new Vec3(0.875, 1.0625, 0.5), new Vec3(0.125, 1.0625, 0.5), new Vec3(0.5, 1.0625, 0.125), new Vec3(0.5, 1.0625, 0.875)
   };
   private final Supplier<SimpleParticleType> particleType;
   private final Vec3[] particlePoints;

   public TallCandlesBlock(Supplier<SimpleParticleType> particleType, Vec3[] particlePoints) {
      super(
         Properties.of()
            .sound(SoundType.STONE)
            .strength(1.0F, 10.0F)
            .lightLevel(s -> s.getValue(AbstractDoubleBlock.BLOCK_HALF) == 1 ? 14 : 0)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      );
      this.particleType = particleType;
      this.particlePoints = particlePoints;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, list, tooltipFlag);
      list.add(Component.translatable(this.getDescriptionId() + ".description_0"));
   }

   @Override
   protected VoxelShape getUpperShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return UPPER;
   }

   @Override
   protected VoxelShape getLowerShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return LOWER;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Integer)state.getValue(AbstractDoubleBlock.BLOCK_HALF) == 1) {
         int x = pos.getX();
         int y = pos.getY();
         int z = pos.getZ();
         if (random.nextBoolean()) {
            for (Vec3 point : this.particlePoints) {
               level.addParticle((ParticleOptions)this.particleType.get(), x + point.x, y + point.y, z + point.z, 0.0, 0.0, 0.0);
            }
         } else {
            for (Vec3 point : this.particlePoints) {
               level.addParticle(
                  (ParticleOptions)this.particleType.get(),
                  x + point.x + Mth.nextDouble(random, -0.05, 0.05),
                  y + point.y + Mth.nextDouble(random, -0.05, 0.05),
                  z + point.z + Mth.nextDouble(random, -0.05, 0.05),
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }
   }
}
