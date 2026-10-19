package com.blackgear.vanillabackport.common.level.entities.animal;

import com.blackgear.vanillabackport.common.api.variant.ModelAndTexture;
import com.blackgear.vanillabackport.common.api.variant.spawn.PriorityProvider;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnCondition;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnContext;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnPrioritySelectors;
import com.blackgear.vanillabackport.core.registries.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public record PigVariant(ModelAndTexture<PigVariant.ModelType> modelAndTexture, SpawnPrioritySelectors spawnConditions)
   implements PriorityProvider<SpawnContext, SpawnCondition> {
   public static final StreamCodec<RegistryFriendlyByteBuf, PigVariant> STREAM_CODEC = ByteBufCodecs.registry(ModRegistries.PIG_VARIANT.key());
   public static final Codec<PigVariant> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ModelAndTexture.codec(PigVariant.ModelType.CODEC, PigVariant.ModelType.NORMAL).forGetter(PigVariant::modelAndTexture),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(PigVariant::spawnConditions)
         )
         .apply(instance, PigVariant::new)
   );

   private PigVariant(ModelAndTexture<PigVariant.ModelType> modelAndTexture) {
      this(modelAndTexture, SpawnPrioritySelectors.EMPTY);
   }

   @Override
   public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
      return this.spawnConditions.selectors();
   }

   public enum ModelType implements StringRepresentable {
      NORMAL("normal"),
      COLD("cold");

      public static final Codec<PigVariant.ModelType> CODEC = StringRepresentable.fromEnum(PigVariant.ModelType::values);
      private final String name;

      ModelType(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
