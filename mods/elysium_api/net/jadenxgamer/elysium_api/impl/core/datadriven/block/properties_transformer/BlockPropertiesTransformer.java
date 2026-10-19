package net.jadenxgamer.elysium_api.impl.core.datadriven.block.properties_transformer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.jadenxgamer.elysium_api.impl.util.ConditionalBoolean;
import net.jadenxgamer.elysium_api.impl.util.ConditionalInt;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public record BlockPropertiesTransformer(
   HolderSet<Block> blocks, BlockPropertiesTransformer.BasicProperties baseProperties, BlockPropertiesTransformer.ConditionalProperties conditionalProperties
) {
   public static final Codec<BlockPropertiesTransformer> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(BlockPropertiesTransformer::blocks),
            BlockPropertiesTransformer.BasicProperties.CODEC.fieldOf("base_properties").forGetter(BlockPropertiesTransformer::baseProperties),
            BlockPropertiesTransformer.ConditionalProperties.CODEC
               .fieldOf("conditional_properties")
               .forGetter(BlockPropertiesTransformer::conditionalProperties)
         )
         .apply(instance, BlockPropertiesTransformer::new)
   );

   public record BasicProperties(
      Optional<Float> destroySpeed,
      Optional<Float> explosionResistance,
      Optional<Boolean> requiresCorrectToolForDrops,
      Optional<Boolean> randomTicking,
      Optional<Float> friction,
      Optional<Float> speedFactor,
      Optional<Float> jumpFactor,
      Optional<Boolean> canOcclude,
      Optional<Boolean> isAir,
      Optional<Boolean> ignitedByLava,
      Optional<PushReactionEnum> pushReaction,
      Optional<Boolean> spawnTerrainParticles,
      Optional<Boolean> canBeReplaced
   ) {
      public static final Codec<BlockPropertiesTransformer.BasicProperties> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.FLOAT.optionalFieldOf("destroy_speed").forGetter(BlockPropertiesTransformer.BasicProperties::destroySpeed),
               Codec.FLOAT.optionalFieldOf("explosion_resistance").forGetter(BlockPropertiesTransformer.BasicProperties::explosionResistance),
               Codec.BOOL.optionalFieldOf("requires_correct_tool_for_drops").forGetter(BlockPropertiesTransformer.BasicProperties::requiresCorrectToolForDrops),
               Codec.BOOL.optionalFieldOf("random_ticking").forGetter(BlockPropertiesTransformer.BasicProperties::randomTicking),
               Codec.FLOAT.optionalFieldOf("friction").forGetter(BlockPropertiesTransformer.BasicProperties::friction),
               Codec.FLOAT.optionalFieldOf("speed_factor").forGetter(BlockPropertiesTransformer.BasicProperties::speedFactor),
               Codec.FLOAT.optionalFieldOf("jump_factor").forGetter(BlockPropertiesTransformer.BasicProperties::jumpFactor),
               Codec.BOOL.optionalFieldOf("can_occlude").forGetter(BlockPropertiesTransformer.BasicProperties::canOcclude),
               Codec.BOOL.optionalFieldOf("is_air").forGetter(BlockPropertiesTransformer.BasicProperties::isAir),
               Codec.BOOL.optionalFieldOf("ignited_by_lava").forGetter(BlockPropertiesTransformer.BasicProperties::ignitedByLava),
               PushReactionEnum.CODEC.optionalFieldOf("push_reaction").forGetter(BlockPropertiesTransformer.BasicProperties::pushReaction),
               Codec.BOOL.optionalFieldOf("spawn_terrain_particles").forGetter(BlockPropertiesTransformer.BasicProperties::spawnTerrainParticles),
               Codec.BOOL.optionalFieldOf("can_be_replaced").forGetter(BlockPropertiesTransformer.BasicProperties::canBeReplaced)
            )
            .apply(instance, BlockPropertiesTransformer.BasicProperties::new)
      );
   }

   public record ConditionalProperties(
      Optional<ConditionalInt> lightEmission,
      Optional<ConditionalBoolean> isValidSpawn,
      Optional<ConditionalBoolean> isRedstoneConductor,
      Optional<ConditionalBoolean> isSuffocating,
      Optional<ConditionalBoolean> isViewBlocking,
      Optional<ConditionalBoolean> hasPostProcess,
      Optional<ConditionalBoolean> emissiveRendering
   ) {
      public static final Codec<BlockPropertiesTransformer.ConditionalProperties> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ConditionalInt.CODEC.optionalFieldOf("light_emission").forGetter(BlockPropertiesTransformer.ConditionalProperties::lightEmission),
               ConditionalBoolean.CODEC.optionalFieldOf("is_valid_spawn").forGetter(BlockPropertiesTransformer.ConditionalProperties::isValidSpawn),
               ConditionalBoolean.CODEC
                  .optionalFieldOf("is_redstone_conductor")
                  .forGetter(BlockPropertiesTransformer.ConditionalProperties::isRedstoneConductor),
               ConditionalBoolean.CODEC.optionalFieldOf("is_suffocating").forGetter(BlockPropertiesTransformer.ConditionalProperties::isSuffocating),
               ConditionalBoolean.CODEC.optionalFieldOf("is_view_blocking").forGetter(BlockPropertiesTransformer.ConditionalProperties::isViewBlocking),
               ConditionalBoolean.CODEC.optionalFieldOf("has_post_process").forGetter(BlockPropertiesTransformer.ConditionalProperties::hasPostProcess),
               ConditionalBoolean.CODEC.optionalFieldOf("emissive_rendering").forGetter(BlockPropertiesTransformer.ConditionalProperties::emissiveRendering)
            )
            .apply(instance, BlockPropertiesTransformer.ConditionalProperties::new)
      );
   }
}
