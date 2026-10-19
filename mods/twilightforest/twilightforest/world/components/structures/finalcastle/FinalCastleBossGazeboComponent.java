package twilightforest.world.components.structures.finalcastle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.FrontAndTop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.Display.BillboardConstraints;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.StructureTemplateDefinitions;

@ParametersAreNonnullByDefault
public class FinalCastleBossGazeboComponent extends TFStructureComponentOld {
   public static final ResourceLocation GAZEBO_TEMP_POOL = TwilightForestMod.prefix("final_castle/temp/gazebo");
   @Autowired
   private static StructureTemplateDefinitions structureTemplateDefinitions;
   public static final String INTERACTION_TAG = "final_castle_wip";

   public FinalCastleBossGazeboComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCBoGaz.get(), nbt);
   }

   public FinalCastleBossGazeboComponent(int i, TFStructureComponentOld keep, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCBoGaz.get(), i, x, y, z);
      this.spawnListIndex = -1;
      this.setOrientation(keep.getOrientation());
      this.boundingBox = new BoundingBox(
         keep.getBoundingBox().minX() + 14,
         keep.getBoundingBox().maxY() + 2,
         keep.getBoundingBox().minZ() + 14,
         keep.getBoundingBox().maxX() - 14,
         keep.getBoundingBox().maxY() + 13,
         keep.getBoundingBox().maxZ() - 14
      );
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      this.deco = new StructureTFDecoratorCastle();
      this.deco.blockState = ((Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get()).defaultBlockState();
      this.deco.fenceState = ((Block)TFBlocks.VIOLET_FORCE_FIELD.get()).defaultBlockState();
      TwilightJigsawPiece templatePiece = structureTemplateDefinitions.initializeTemplateFromPool(
         GAZEBO_TEMP_POOL,
         this.getWorldPos(10, -1, 10),
         this.rotation.rotation().rotate(FrontAndTop.UP_SOUTH),
         "twilightforest:final_castle/final_boss",
         rand,
         this.genDepth + 1,
         ServerLifecycleHooks.getCurrentServer().getStructureManager()
      );
      if (templatePiece != null) {
         list.addPiece(templatePiece);
      }
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource randomIn, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
   }

   private void willBeAddingFinalBossSoon(WorldGenLevel world, BoundingBox sbb) {
      Builder<Vec3> positionAccumulator = ImmutableList.builder();
      this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "Final Castle WIP.", true, 2.3F, positionAccumulator::add, BillboardConstraints.VERTICAL);
      this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "Join our Discord server to", true, 1.3F, positionAccumulator::add, BillboardConstraints.VERTICAL);
      this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "follow development of the mod:", true, 1.0F, positionAccumulator::add, BillboardConstraints.VERTICAL);
      this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "discord.experiment115.com", true, 0.7F, positionAccumulator::add, BillboardConstraints.VERTICAL);
      AABB punchBox = BoundingBoxUtils.vectorsMinMax(positionAccumulator.build(), 1.0);
      if (punchBox != null) {
         double width = (punchBox.getXsize() + punchBox.getZsize()) * 0.5;
         double height = punchBox.getYsize();
         Interaction interaction = new Interaction(EntityType.INTERACTION, world.getLevel());
         interaction.addTag("final_castle_wip");
         interaction.setHeight((float)height);
         interaction.setWidth((float)width);
         Vec3 center = punchBox.getCenter();
         interaction.setPos(center.x, punchBox.minY, center.z);
         world.addFreshEntity(interaction);
      }
   }
}
