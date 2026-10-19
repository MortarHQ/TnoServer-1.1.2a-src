package twilightforest.world.components.structures.util;

import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public record StructureTemplateDefinition(Map<ResourceLocation, TemplatePoolInstance> poolWeights) {
   public static final Codec<StructureTemplateDefinition> CODEC = Codec.unboundedMap(ResourceLocation.CODEC, TemplatePoolInstance.CODEC)
      .xmap(StructureTemplateDefinition::new, StructureTemplateDefinition::poolWeights);
}
