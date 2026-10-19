package twilightforest.block.entity;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.CarminiteReactorBlock;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.entity.monster.CarminiteGhastling;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class CarminiteReactorBlockEntity extends BlockEntity {
   private final int secX;
   private final int secY;
   private final int secZ;
   private int counter = 0;
   private int terX;
   private int terY;
   private int terZ;

   public CarminiteReactorBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.CARMINITE_REACTOR.get(), pos, state);
      RandomSource rand = RandomSource.create();
      this.secX = 3 * (rand.nextBoolean() ? 1 : -1);
      this.secY = 3 * (rand.nextBoolean() ? 1 : -1);
      this.secZ = 3 * (rand.nextBoolean() ? 1 : -1);
      this.terX = 3 * (rand.nextBoolean() ? 1 : -1);
      this.terY = 3 * (rand.nextBoolean() ? 1 : -1);
      this.terZ = 3 * (rand.nextBoolean() ? 1 : -1);
      if (this.secX == this.terX && this.secY == this.terY && this.secZ == this.terZ) {
         this.terX = -this.terX;
         this.terY = -this.terY;
         this.terZ = -this.terZ;
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, CarminiteReactorBlockEntity te) {
      if (!level.isDebug() && (Boolean)state.getValue(CarminiteReactorBlock.ACTIVE)) {
         te.counter++;
         if (!level.isClientSide()) {
            int offset = 10;
            if (te.counter % 5 == 0) {
               if (te.counter == 5) {
                  BlockState fakeGold = ((Block)TFBlocks.FAKE_GOLD.get()).defaultBlockState();
                  BlockState fakeDiamond = ((Block)TFBlocks.FAKE_DIAMOND.get()).defaultBlockState();
                  te.createFakeBlock(pos.offset(1, 1, 1), fakeDiamond);
                  te.createFakeBlock(pos.offset(1, 1, -1), fakeDiamond);
                  te.createFakeBlock(pos.offset(-1, 1, 1), fakeDiamond);
                  te.createFakeBlock(pos.offset(-1, 1, -1), fakeDiamond);
                  te.createFakeBlock(pos.offset(0, 1, 0), fakeDiamond);
                  te.createFakeBlock(pos.offset(0, 1, 1), fakeGold);
                  te.createFakeBlock(pos.offset(0, 1, -1), fakeGold);
                  te.createFakeBlock(pos.offset(1, 1, 0), fakeGold);
                  te.createFakeBlock(pos.offset(-1, 1, 0), fakeGold);
                  te.createFakeBlock(pos.offset(1, 0, 1), fakeGold);
                  te.createFakeBlock(pos.offset(1, 0, -1), fakeGold);
                  te.createFakeBlock(pos.offset(-1, 0, 1), fakeGold);
                  te.createFakeBlock(pos.offset(-1, 0, -1), fakeGold);
                  te.createFakeBlock(pos.offset(0, 0, 1), fakeDiamond);
                  te.createFakeBlock(pos.offset(0, 0, -1), fakeDiamond);
                  te.createFakeBlock(pos.offset(1, 0, 0), fakeDiamond);
                  te.createFakeBlock(pos.offset(-1, 0, 0), fakeDiamond);
                  te.createFakeBlock(pos.offset(0, -1, 0), fakeDiamond);
                  te.createFakeBlock(pos.offset(1, -1, 1), fakeDiamond);
                  te.createFakeBlock(pos.offset(1, -1, -1), fakeDiamond);
                  te.createFakeBlock(pos.offset(-1, -1, 1), fakeDiamond);
                  te.createFakeBlock(pos.offset(-1, -1, -1), fakeDiamond);
                  te.createFakeBlock(pos.offset(0, -1, 1), fakeGold);
                  te.createFakeBlock(pos.offset(0, -1, -1), fakeGold);
                  te.createFakeBlock(pos.offset(1, -1, 0), fakeGold);
                  te.createFakeBlock(pos.offset(-1, -1, 0), fakeGold);
               }

               int primary = te.counter - 80;
               if (primary >= offset && primary <= 249) {
                  te.drawBlob(pos, (primary - offset) / 40, Blocks.AIR.defaultBlockState(), primary - offset, false);
               }

               if (primary <= 200) {
                  te.drawBlob(pos, primary / 40, ((Block)TFBlocks.REACTOR_DEBRIS.get()).defaultBlockState(), te.counter, false);
               }

               int secondary = te.counter - 120;
               if (secondary >= offset && secondary <= 129) {
                  te.drawBlob(pos.offset(te.secX, te.secY, te.secZ), (secondary - offset) / 40, Blocks.AIR.defaultBlockState(), secondary - offset, false);
               }

               if (secondary >= 0 && secondary <= 160) {
                  te.drawBlob(pos.offset(te.secX, te.secY, te.secZ), secondary / 40, Blocks.AIR.defaultBlockState(), secondary, true);
               }

               int tertiary = te.counter - 160;
               if (tertiary >= offset && tertiary <= 129) {
                  te.drawBlob(pos.offset(te.terX, te.terY, te.terZ), (tertiary - offset) / 40, Blocks.AIR.defaultBlockState(), tertiary - offset, false);
               }

               if (tertiary >= 0 && tertiary <= 160) {
                  te.drawBlob(pos.offset(te.terX, te.terY, te.terZ), tertiary / 40, Blocks.AIR.defaultBlockState(), tertiary, true);
               }
            }

            if (te.counter >= 350) {
               level.destroyBlock(pos, false);
               level.explode(
                  null,
                  TFDamageTypes.getDamageSource(level, TFDamageTypes.REACTOR),
                  null,
                  pos.getX(),
                  pos.getY(),
                  pos.getZ(),
                  4.0F,
                  true,
                  ExplosionInteraction.BLOCK
               );

               for (int i = 0; i < 3; i++) {
                  te.spawnGhastNear(level, pos.getX() + te.secX, pos.getY() + te.secY, pos.getZ() + te.secZ);
                  te.spawnGhastNear(level, pos.getX() + te.terX, pos.getY() + te.terY, pos.getZ() + te.terZ);
               }
            }
         } else if (te.counter % 5 == 0 && te.counter <= 250) {
            level.playLocalSound(
               pos.getX() + 0.5,
               pos.getY() + 0.5,
               pos.getZ() + 0.5,
               (SoundEvent)TFSounds.REACTOR_AMBIENT.get(),
               SoundSource.BLOCKS,
               te.counter / 100.0F,
               te.counter / 100.0F,
               false
            );
         }
      }
   }

   private void spawnGhastNear(Level level, int x, int y, int z) {
      CarminiteGhastling ghast = (CarminiteGhastling)((EntityType)TFEntities.CARMINITE_GHASTLING.get()).create(level);
      if (ghast != null) {
         ghast.moveTo(
            x - 1.5 + level.getRandom().nextFloat() * 3.0,
            y - 1.5 + level.getRandom().nextFloat() * 3.0,
            z - 1.5 + level.getRandom().nextFloat() * 3.0,
            level.getRandom().nextFloat() * 360.0F,
            0.0F
         );
         level.addFreshEntity(ghast);
      }
   }

   private void drawBlob(BlockPos pos, int rad, BlockState state, int fuzz, boolean netherTransform) {
      for (byte dx = 0; dx <= rad; dx++) {
         int fuzzX = (fuzz + dx) % 8;

         for (byte dy = 0; dy <= rad; dy++) {
            int fuzzY = (fuzz + dy) % 8;

            for (byte dz = 0; dz <= rad; dz++) {
               byte dist;
               if (dx >= dy && dx >= dz) {
                  dist = (byte)(dx + (byte)(Math.max(dy, dz) * 0.5 + Math.min(dy, dz) * 0.25));
               } else if (dy >= dx && dy >= dz) {
                  dist = (byte)(dy + (byte)(Math.max(dx, dz) * 0.5 + Math.min(dx, dz) * 0.25));
               } else {
                  dist = (byte)(dz + (byte)(Math.max(dx, dy) * 0.5 + Math.min(dx, dy) * 0.25));
               }

               if (dist == rad && (dx != 0 || dy != 0 || dz != 0)) {
                  switch (fuzzX) {
                     case 0:
                        this.transformBlock(pos.offset(dx, dy, dz), state, fuzzY, netherTransform);
                        break;
                     case 1:
                        this.transformBlock(pos.offset(dx, dy, -dz), state, fuzzY, netherTransform);
                        break;
                     case 2:
                        this.transformBlock(pos.offset(-dx, dy, dz), state, fuzzY, netherTransform);
                        break;
                     case 3:
                        this.transformBlock(pos.offset(-dx, dy, -dz), state, fuzzY, netherTransform);
                        break;
                     case 4:
                        this.transformBlock(pos.offset(dx, -dy, dz), state, fuzzY, netherTransform);
                        break;
                     case 5:
                        this.transformBlock(pos.offset(dx, -dy, -dz), state, fuzzY, netherTransform);
                        break;
                     case 6:
                        this.transformBlock(pos.offset(-dx, -dy, dz), state, fuzzY, netherTransform);
                        break;
                     case 7:
                        this.transformBlock(pos.offset(-dx, -dy, -dz), state, fuzzY, netherTransform);
                  }
               }
            }
         }
      }
   }

   private void transformBlock(BlockPos pos, BlockState state, int fuzz, boolean netherTransform) {
      BlockState stateThere = this.getLevel().getBlockState(pos);
      if (stateThere.getBlock() == Blocks.AIR
         || !stateThere.is(BlockTagGenerator.CARMINITE_REACTOR_IMMUNE) && stateThere.getDestroySpeed(this.getLevel(), pos) != -1.0F) {
         if (fuzz == 0 && stateThere.getBlock() != Blocks.AIR) {
            this.getLevel().levelEvent(2001, pos, Block.getId(stateThere));
         }

         if (netherTransform && stateThere.getBlock() != Blocks.AIR) {
            Optional<Block> optional = BuiltInRegistries.BLOCK
               .getTag(BlockTagGenerator.CARMINITE_REACTOR_ORES)
               .flatMap(tag -> tag.getRandomElement(this.getLevel().getRandom()))
               .map(Holder::value);
            this.getLevel()
               .setBlock(
                  pos,
                  this.getLevel().getRandom().nextInt(8) == 0 && optional.isPresent()
                     ? optional.get().defaultBlockState()
                     : Blocks.NETHERRACK.defaultBlockState(),
                  3
               );
            if (this.getLevel().isEmptyBlock(pos.above()) && fuzz % 3 == 0) {
               this.getLevel().setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 3);
            }
         } else {
            this.getLevel().setBlock(pos, state, 3);
         }
      }
   }

   private void createFakeBlock(BlockPos pos, BlockState state) {
      BlockState stateThere = this.getLevel().getBlockState(pos);
      if (!stateThere.is(BlockTagGenerator.CARMINITE_REACTOR_IMMUNE) && stateThere.getDestroySpeed(this.getLevel(), pos) != -1.0F) {
         this.getLevel().setBlock(pos, state, 2);
      }
   }
}
