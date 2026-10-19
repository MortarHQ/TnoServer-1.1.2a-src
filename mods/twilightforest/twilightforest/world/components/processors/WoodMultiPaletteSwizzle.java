package twilightforest.world.components.processors;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureProcessors;
import twilightforest.init.custom.WoodPalettes;
import twilightforest.util.woods.WoodPalette;

public final class WoodMultiPaletteSwizzle extends StructureProcessor {
   private final List<Pair<Holder<WoodPalette>, Holder<WoodPalette>>> palettes;
   public static final MapCodec<WoodMultiPaletteSwizzle> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.pair(WoodPalettes.CODEC, WoodPalettes.CODEC).listOf().fieldOf("palettes").forGetter(s -> s.palettes))
         .apply(instance, WoodMultiPaletteSwizzle::new)
   );

   public WoodMultiPaletteSwizzle(List<Pair<Holder<WoodPalette>, Holder<WoodPalette>>> palettes) {
      this.palettes = palettes;
   }

   public StructureBlockInfo process(
      LevelReader level,
      BlockPos offset,
      BlockPos piecePos,
      StructureBlockInfo originalInfo,
      StructureBlockInfo modifiedInfo,
      StructurePlaceSettings placeSettings,
      @Nullable StructureTemplate template
   ) {
      for (Pair<Holder<WoodPalette>, Holder<WoodPalette>> palettePair : this.palettes) {
         StructureBlockInfo newInfo = ((WoodPalette)((Holder)palettePair.getSecond()).value())
            .modifyBlockWithType((WoodPalette)((Holder)palettePair.getFirst()).value(), modifiedInfo);
         if (newInfo != modifiedInfo) {
            return newInfo;
         }
      }

      return modifiedInfo;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.PLANK_MULTISWIZZLE.get();
   }
}
