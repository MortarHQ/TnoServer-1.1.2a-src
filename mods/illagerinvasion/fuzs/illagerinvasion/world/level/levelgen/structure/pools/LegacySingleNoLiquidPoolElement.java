package fuzs.illagerinvasion.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class LegacySingleNoLiquidPoolElement extends LegacySinglePoolElement {
   public static final MapCodec<LegacySinglePoolElement> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(templateCodec(), processorsCodec(), projectionCodec(), overrideLiquidSettingsCodec())
         .apply(instance, LegacySingleNoLiquidPoolElement::new)
   );

   protected LegacySingleNoLiquidPoolElement(
      Either<ResourceLocation, StructureTemplate> either, Holder<StructureProcessorList> holder, Projection projection, Optional<LiquidSettings> optional
   ) {
      super(either, holder, projection, optional);
   }

   protected StructurePlaceSettings getSettings(Rotation rotation, BoundingBox boundingBox, LiquidSettings liquidSettings, boolean offset) {
      return super.getSettings(rotation, boundingBox, liquidSettings, offset).setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
   }

   public StructurePoolElementType<?> getType() {
      return (StructurePoolElementType<?>)ModRegistry.LEGACY_SINGLE_POOL_ELEMENT_TYPE.value();
   }

   public String toString() {
      return "LegacySingleNoLiquid[" + this.template + "]";
   }
}
