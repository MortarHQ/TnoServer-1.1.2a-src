package twilightforest.util.jigsaw;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

public class JigsawUtil {
   public static Direction getAbsoluteHorizontal(FrontAndTop orientation) {
      return orientation.front().getAxis() == Axis.Y ? orientation.top() : orientation.front();
   }

   public static FrontAndTop process(FrontAndTop source, StructurePlaceSettings settings) {
      return settings.getRotation().rotation().rotate(source);
   }

   public static boolean canRearrangeForConnection(FrontAndTop sourceOrientation, StructureBlockInfo otherJigsaw) {
      FrontAndTop otherOrientation = (FrontAndTop)otherJigsaw.state().getValue(JigsawBlock.ORIENTATION);
      boolean frontFacesAlignable = canBeRotatedToAlign(sourceOrientation.front(), otherOrientation.front());
      boolean topFacesAlignable = canBeRotatedToAlign(sourceOrientation.top().getOpposite(), otherOrientation.top());
      return frontFacesAlignable && topFacesAlignable;
   }

   private static boolean canBeRotatedToAlign(Direction source, Direction target) {
      Plane sourcePlane = source.getAxis().getPlane();
      boolean planesMatch = sourcePlane == target.getAxis().getPlane();
      return sourcePlane != Plane.VERTICAL ? planesMatch : planesMatch && source.getOpposite() == target;
   }

   public static List<StructureBlockInfo> readConnectableJigsaws(
      StructureTemplateManager manager, ResourceLocation templateLocation, StructurePlaceSettings settings, @Nullable RandomSource random
   ) {
      return readConnectableJigsaws(manager.getOrCreate(templateLocation), settings, random);
   }

   public static List<StructureBlockInfo> readConnectableJigsaws(
      @Nullable StructureTemplate template, StructurePlaceSettings settings, @Nullable RandomSource random
   ) {
      if (template != null && !BlockPos.ZERO.equals(template.getSize())) {
         List<StructureBlockInfo> returnables = template.filterBlocks(BlockPos.ZERO, settings, Blocks.JIGSAW);
         if (random != null) {
            Util.shuffle(returnables, random);
            SinglePoolElement.sortBySelectionPriority(returnables);
         }

         return returnables;
      } else {
         return List.of();
      }
   }
}
