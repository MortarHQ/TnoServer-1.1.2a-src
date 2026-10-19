package team.lodestar.lodestone.systems.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class LodestoneRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
   public final MapCodec<T> codec;
   public final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

   public LodestoneRecipeSerializer(MapCodec<T> codec) {
      this.codec = codec;
      this.streamCodec = ByteBufCodecs.fromCodecWithRegistries(codec.codec());
   }

   public MapCodec<T> codec() {
      return this.codec;
   }

   public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
      return this.streamCodec;
   }
}
