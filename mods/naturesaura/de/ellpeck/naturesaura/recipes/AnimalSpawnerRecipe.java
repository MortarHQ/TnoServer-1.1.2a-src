package de.ellpeck.naturesaura.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AnimalSpawnerRecipe extends ModRecipe {
   public final List<Ingredient> ingredients;
   public final EntityType<?> entity;
   public final int aura;
   public final int time;

   public AnimalSpawnerRecipe(ResourceLocation entityType, int aura, int time, List<Ingredient> ingredients) {
      this.ingredients = ingredients;
      this.entity = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(entityType);
      this.aura = aura;
      this.time = time;
   }

   public Entity makeEntity(Level level, BlockPos pos) {
      return pos == BlockPos.ZERO ? this.entity.create(level) : this.entity.create((ServerLevel)level, null, pos, MobSpawnType.SPAWNER, false, false);
   }

   public ItemStack getResultItem(Provider registries) {
      return ItemStack.EMPTY;
   }

   public RecipeSerializer<?> getSerializer() {
      return ModRecipes.ANIMAL_SPAWNER_SERIALIZER;
   }

   public RecipeType<?> getType() {
      return ModRecipes.ANIMAL_SPAWNER_TYPE;
   }

   public static class Serializer implements RecipeSerializer<AnimalSpawnerRecipe> {
      private static final MapCodec<AnimalSpawnerRecipe> CODEC = RecordCodecBuilder.mapCodec(
         i -> i.group(
               ResourceLocation.CODEC.fieldOf("entity").forGetter(r -> BuiltInRegistries.ENTITY_TYPE.getKey(r.entity)),
               Codec.INT.fieldOf("aura").forGetter(r -> r.aura),
               Codec.INT.fieldOf("time").forGetter(r -> r.time),
               Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.ingredients)
            )
            .apply(i, AnimalSpawnerRecipe::new)
      );
      private static final StreamCodec<RegistryFriendlyByteBuf, AnimalSpawnerRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

      public MapCodec<AnimalSpawnerRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, AnimalSpawnerRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }
}
