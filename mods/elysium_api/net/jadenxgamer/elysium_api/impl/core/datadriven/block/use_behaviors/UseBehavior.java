package net.jadenxgamer.elysium_api.impl.core.datadriven.block.use_behaviors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.jadenxgamer.elysium_api.impl.util.BlockStatePropertiesCondition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record UseBehavior(
   HolderSet<Block> blocks,
   Optional<BlockStatePropertiesCondition> blockstateCondition,
   HolderSet<Item> itemCondition,
   int chanceToFail,
   UseBehavior.Behavior behavior
) {
   public static final Codec<UseBehavior> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(UseBehavior::blocks),
            BlockStatePropertiesCondition.CODEC.optionalFieldOf("blockstate_condition").forGetter(UseBehavior::blockstateCondition),
            RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("item_condition").forGetter(UseBehavior::itemCondition),
            Codec.INT.optionalFieldOf("chance_to_fail", 0).forGetter(UseBehavior::chanceToFail),
            UseBehavior.Behavior.CODEC.fieldOf("behaviors").forGetter(UseBehavior::behavior)
         )
         .apply(instance, UseBehavior::new)
   );

   public record Behavior(
      UseBehaviorTypeEnum type,
      Optional<BlockState> block,
      Optional<ResourceLocation> item,
      int itemCount,
      Optional<ResourceLocation> feature,
      PosEnum pos,
      int posOffset,
      AfterUseItemEnum afterUseItem,
      boolean canReplace,
      boolean breakParticles,
      Optional<UseBehavior.Sounds> sounds,
      Optional<UseBehavior.Particles> particles,
      Optional<List<String>> copyProperties
   ) {
      public static final Codec<UseBehavior.Behavior> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               UseBehaviorTypeEnum.CODEC.optionalFieldOf("type", UseBehaviorTypeEnum.PLACE).forGetter(UseBehavior.Behavior::type),
               BlockState.CODEC.optionalFieldOf("blockstate").forGetter(UseBehavior.Behavior::block),
               ResourceLocation.CODEC.optionalFieldOf("item").forGetter(UseBehavior.Behavior::item),
               Codec.INT.optionalFieldOf("item_count", 1).forGetter(UseBehavior.Behavior::itemCount),
               ResourceLocation.CODEC.optionalFieldOf("feature").forGetter(UseBehavior.Behavior::feature),
               PosEnum.CODEC.optionalFieldOf("pos", PosEnum.NOOP).forGetter(UseBehavior.Behavior::pos),
               Codec.INT.optionalFieldOf("pos_offset", 1).forGetter(UseBehavior.Behavior::posOffset),
               AfterUseItemEnum.CODEC.optionalFieldOf("after_use_item", AfterUseItemEnum.NOTHING).forGetter(UseBehavior.Behavior::afterUseItem),
               Codec.BOOL.optionalFieldOf("can_replace_solids", false).forGetter(UseBehavior.Behavior::canReplace),
               Codec.BOOL.optionalFieldOf("break_particles", false).forGetter(UseBehavior.Behavior::breakParticles),
               UseBehavior.Sounds.CODEC.optionalFieldOf("sounds").forGetter(UseBehavior.Behavior::sounds),
               UseBehavior.Particles.CODEC.optionalFieldOf("particles").forGetter(UseBehavior.Behavior::particles),
               Codec.list(Codec.STRING).optionalFieldOf("copy_properties").forGetter(UseBehavior.Behavior::copyProperties)
            )
            .apply(instance, UseBehavior.Behavior::new)
      );
   }

   public record Particles(ResourceLocation particleType, int count, double speed, double xOffset, double yOffset, double zOffset) {
      public static final Codec<UseBehavior.Particles> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("particle_type").forGetter(UseBehavior.Particles::particleType),
               Codec.INT.optionalFieldOf("count", 1).forGetter(UseBehavior.Particles::count),
               Codec.DOUBLE.optionalFieldOf("speed", 0.0).forGetter(UseBehavior.Particles::speed),
               Codec.DOUBLE.optionalFieldOf("x_offset", 0.0).forGetter(UseBehavior.Particles::xOffset),
               Codec.DOUBLE.optionalFieldOf("y_offset", 0.0).forGetter(UseBehavior.Particles::yOffset),
               Codec.DOUBLE.optionalFieldOf("z_offset", 0.0).forGetter(UseBehavior.Particles::zOffset)
            )
            .apply(instance, UseBehavior.Particles::new)
      );
   }

   public record Sounds(SoundEvent soundEvent, float volume, float pitch) {
      public static final Codec<UseBehavior.Sounds> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("sound_event").forGetter(UseBehavior.Sounds::soundEvent),
               Codec.FLOAT.optionalFieldOf("volume", 1.0F).forGetter(UseBehavior.Sounds::volume),
               Codec.FLOAT.optionalFieldOf("pitch", 1.0F).forGetter(UseBehavior.Sounds::pitch)
            )
            .apply(instance, UseBehavior.Sounds::new)
      );

      public float getVolume() {
         return this.volume;
      }
   }
}
