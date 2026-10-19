package net.regions_unexplored.worldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.regions_unexplored.block.set.NaturalSet;
import net.regions_unexplored.block.type.leaves.HangingVinesBlock;

public class HangingVinesDecorator extends TreeDecorator {
   public static final MapCodec<HangingVinesDecorator> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(HangingVinesDecorator::block),
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(HangingVinesDecorator::probability)
         )
         .apply(i, HangingVinesDecorator::new)
   );
   public static final TreeDecoratorType<HangingVinesDecorator> TYPE = new TreeDecoratorType(CODEC);
   public static final float CONTINUE_EXTENSION_CHANCE = 0.4F;
   private final HangingVinesBlock block;
   private final float probability;

   public static HangingVinesDecorator create(NaturalSet set, float probability) {
      return new HangingVinesDecorator(set.getVines(), probability);
   }

   public HangingVinesDecorator(Block block, float probability) {
      if (block instanceof HangingVinesBlock hangingLeaves) {
         this.block = hangingLeaves;
         this.probability = probability;
      } else {
         throw new IllegalArgumentException("`hanging_leaves` tree decorator requires instance of HangingVinesBlock, got: " + block.getClass().getSimpleName());
      }
   }

   public HangingVinesBlock block() {
      return this.block;
   }

   public float probability() {
      return this.probability;
   }

   public void place(Context context) {
      ObjectListIterator var2 = context.leaves().iterator();

      while (var2.hasNext()) {
         BlockPos leafPos = (BlockPos)var2.next();
         if (this.probability > context.random().nextFloat()) {
            BlockPos pos = leafPos.below();
            if (context.isAir(pos)) {
               while (context.isAir(pos.below()) && context.random().nextFloat() > 0.4F) {
                  context.setBlock(pos, (BlockState)this.block.defaultBlockState().setValue(HangingVinesBlock.TIP, false));
                  pos = pos.below();
               }

               context.setBlock(pos, (BlockState)this.block.defaultBlockState().setValue(HangingVinesBlock.TIP, true));
            }
         }
      }
   }

   protected TreeDecoratorType<?> type() {
      return TYPE;
   }
}
