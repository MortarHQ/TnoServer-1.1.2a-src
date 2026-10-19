package net.p3pp3rf1y.sophisticatedcore.crafting;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeWrapperSerializer<T extends Recipe<?>, R extends Recipe<?> & IWrapperRecipe<T>> implements RecipeSerializer<R> {
   @Nullable
   private MapCodec<R> codec;
   @Nullable
   private StreamCodec<RegistryFriendlyByteBuf, R> streamCodec;
   private final Function<T, R> initialize;
   private final RecipeSerializer<T> recipeSerializer;

   public RecipeWrapperSerializer(Function<T, R> initialize, RecipeSerializer<T> recipeSerializer) {
      this.initialize = initialize;
      this.recipeSerializer = recipeSerializer;
   }

   public MapCodec<R> codec() {
      if (this.codec == null) {
         this.codec = this.recipeSerializer.codec().xmap(this.initialize, rec$ -> ((IWrapperRecipe)rec$).getCompose());
      }

      return this.codec;
   }

   public StreamCodec<RegistryFriendlyByteBuf, R> streamCodec() {
      if (this.streamCodec == null) {
         this.streamCodec = new StreamCodec<RegistryFriendlyByteBuf, R>() {
            public R decode(RegistryFriendlyByteBuf buffer) {
               T compose = (T)RecipeWrapperSerializer.this.recipeSerializer.streamCodec().decode(buffer);
               return compose == null ? null : RecipeWrapperSerializer.this.initialize.apply(compose);
            }

            public void encode(RegistryFriendlyByteBuf pBuffer, R pValue) {
               RecipeWrapperSerializer.this.recipeSerializer.streamCodec().encode(pBuffer, pValue.getCompose());
            }
         };
      }

      return this.streamCodec;
   }
}
