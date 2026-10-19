package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Stranghoul;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESStructureTypes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;

public class StranghoulDenStructure extends Structure {
   public static final MapCodec<StranghoulDenStructure> CODEC = simpleCodec(StranghoulDenStructure::new);

   public StranghoulDenStructure(StructureSettings structureSettings) {
      super(structureSettings);
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      return onTopOfChunkCenter(context, Types.WORLD_SURFACE_WG, builder -> {
         ChunkPos chunkPos = context.chunkPos();
         int x = chunkPos.getMiddleBlockX();
         int z = chunkPos.getMiddleBlockZ();
         int y = context.chunkGenerator().getFirstOccupiedHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()) + 3;
         builder.addPiece(new StranghoulDenMainPiece(x, y, z));
      });
   }

   public void afterPlace(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGenerator,
      RandomSource random,
      BoundingBox boundingBox,
      ChunkPos chunkPos,
      PiecesContainer piecesContainer
   ) {
      MutableBlockPos pos = new MutableBlockPos();
      BoundingBox piecesBox = piecesContainer.calculateBoundingBox();
      if (ESConfig.INSTANCE.mobsConfig.stranghoul.canSpawn()) {
         int num = random.nextInt(3, 6);
         boolean farmer = false;

         for (int x = boundingBox.minX(); x <= boundingBox.maxX() && num > 0; x++) {
            for (int z = boundingBox.minZ(); z <= boundingBox.maxZ() && num > 0; z++) {
               for (int y = piecesBox.minY(); y <= piecesBox.maxY(); y++) {
                  pos.set(x, y, z);
                  if (piecesBox.isInside(pos)
                     && piecesContainer.isInsidePiece(pos)
                     && (level.isEmptyBlock(pos) || level.getBlockState(pos).is((Block)ESBlocks.FANTASY_GRASS_CARPET.get()))
                     && level.isEmptyBlock(pos.above())
                     && (level.getBlockState(pos.below()).is(BlockTags.DIRT) || level.getBlockState(pos.below()).is(ESTags.Blocks.BASE_STONE_STARLIGHT))) {
                     Stranghoul stranghoul = new Stranghoul(ESEntities.STRANGHOUL.get(), level.getLevel());
                     stranghoul.setPos(pos.getBottomCenter());
                     stranghoul.setPersistenceRequired();
                     stranghoul.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
                     level.addFreshEntity(stranghoul);
                     if (stranghoul.getMainHandItem().is(ItemTags.HOES)) {
                        farmer = true;
                     }

                     if (--num == 0 && !farmer) {
                        stranghoul.setItemInHand(InteractionHand.MAIN_HAND, ESItems.MALARITE_HOE.get().getDefaultInstance());
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   public StructureType<?> type() {
      return ESStructureTypes.STRANGHOUL_DEN.get();
   }
}
