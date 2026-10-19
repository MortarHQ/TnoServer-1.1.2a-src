package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

public final class WoodPaletteSwizzle extends StructureProcessor {
   private final Holder<WoodPalette> targetPalette;
   private final Holder<WoodPalette> replacementPalette;
   public static final MapCodec<WoodPaletteSwizzle> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            WoodPalettes.CODEC.fieldOf("target_palette").forGetter(s -> s.targetPalette),
            WoodPalettes.CODEC.fieldOf("replacement_palette").forGetter(s -> s.replacementPalette)
         )
         .apply(instance, WoodPaletteSwizzle::new)
   );

   public WoodPaletteSwizzle(Holder<WoodPalette> targetPalette, Holder<WoodPalette> replacementPalette) {
      this.targetPalette = targetPalette;
      this.replacementPalette = replacementPalette;
   }

   public StructureBlockInfo process(
      LevelReader worldIn,
      BlockPos pos,
      BlockPos piecepos,
      StructureBlockInfo p_215194_3_,
      StructureBlockInfo blockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      return ((WoodPalette)this.replacementPalette.value()).modifyBlockWithType((WoodPalette)this.targetPalette.value(), blockInfo);
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.PLANK_SWIZZLE.get();
   }
}
