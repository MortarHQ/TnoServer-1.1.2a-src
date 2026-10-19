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

public record ChickenVariant(ModelAndTexture<ChickenVariant.ModelType> modelAndTexture, SpawnPrioritySelectors spawnConditions)
   implements PriorityProvider<SpawnContext, SpawnCondition> {
   public static final StreamCodec<RegistryFriendlyByteBuf, ChickenVariant> STREAM_CODEC = ByteBufCodecs.registry(ModRegistries.CHICKEN_VARIANT.key());
   public static final Codec<ChickenVariant> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ModelAndTexture.codec(ChickenVariant.ModelType.CODEC, ChickenVariant.ModelType.NORMAL).forGetter(ChickenVariant::modelAndTexture),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(ChickenVariant::spawnConditions)
         )
         .apply(instance, ChickenVariant::new)
   );

   private ChickenVariant(ModelAndTexture<ChickenVariant.ModelType> modelAndTexture) {
      this(modelAndTexture, SpawnPrioritySelectors.EMPTY);
   }

   @Override
   public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
      return this.spawnConditions.selectors();
   }

   public enum ModelType implements StringRepresentable {
      NORMAL("normal"),
      COLD("cold");

      public static final Codec<ChickenVariant.ModelType> CODEC = StringRepresentable.fromEnum(ChickenVariant.ModelType::values);
      private final String name;

      ModelType(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
