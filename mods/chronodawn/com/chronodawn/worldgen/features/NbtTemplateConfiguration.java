package com.chronodawn.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record NbtTemplateConfiguration(
   ResourceLocation template, boolean randomRotate, int yOffset, double minGroundContactRatio, int groundContactYOffset, boolean clearReplaceableBlocks
) implements FeatureConfiguration {
   public static final Codec<NbtTemplateConfiguration> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("template").forGetter(NbtTemplateConfiguration::template),
            Codec.BOOL.optionalFieldOf("random_rotate", Boolean.TRUE).forGetter(NbtTemplateConfiguration::randomRotate),
            Codec.INT.optionalFieldOf("y_offset", 0).forGetter(NbtTemplateConfiguration::yOffset),
            Codec.doubleRange(0.0, 1.0).optionalFieldOf("min_ground_contact_ratio", 0.0).forGetter(NbtTemplateConfiguration::minGroundContactRatio),
            Codec.INT.optionalFieldOf("ground_contact_y_offset", -1).forGetter(NbtTemplateConfiguration::groundContactYOffset),
            Codec.BOOL.optionalFieldOf("clear_replaceable_blocks", Boolean.TRUE).forGetter(NbtTemplateConfiguration::clearReplaceableBlocks)
         )
         .apply(instance, NbtTemplateConfiguration::new)
   );
}
