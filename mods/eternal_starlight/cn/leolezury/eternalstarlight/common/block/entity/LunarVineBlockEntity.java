package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LunarVineBlockEntity extends BlockEntity {
   private final List<LunarVineBlockEntity.Flower> flowers = new ArrayList<>();
   private BlockState oldState = null;

   public LunarVineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public LunarVineBlockEntity(BlockPos pos, BlockState state) {
      super(ESBlockEntities.LUNAR_VINE.get(), pos, state);
   }

   public List<LunarVineBlockEntity.Flower> getOrCreateFlowers() {
      if (this.getBlockState() != this.oldState || this.flowers.isEmpty()) {
         this.oldState = this.getBlockState();
         this.flowers.clear();
         this.flowers.addAll(this.generateFlowers());
      }

      return this.flowers;
   }

   private List<LunarVineBlockEntity.Flower> generateFlowers() {
      RandomSource random = RandomSource.create(this.getBlockPos().asLong());
      List<LunarVineBlockEntity.Flower> positions = new ArrayList<>();
      if (random.nextInt(10) == 0) {
         int numFlowers = random.nextInt(3, 5);

         for (int i = 0; i < numFlowers; i++) {
            positions.add(
               new LunarVineBlockEntity.Flower(
                  new Vec3(random.nextDouble() - 0.5, -random.nextDouble(), 0.5), random.nextInt(120, 150), random.nextInt(-15, 15)
               )
            );
         }
      }

      return positions;
   }

   public record Flower(Vec3 pos, float xRot, float yRot) {
   }
}
