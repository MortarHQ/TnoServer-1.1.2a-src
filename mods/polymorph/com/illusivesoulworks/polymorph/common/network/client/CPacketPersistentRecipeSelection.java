package com.illusivesoulworks.polymorph.common.network.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.common.integration.PolymorphIntegrations;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public record CPacketPersistentRecipeSelection(ResourceLocation recipe) implements CustomPacketPayload {
   public static final Type<CPacketPersistentRecipeSelection> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("polymorph", "persistent_recipe_selection"));
   public static final StreamCodec<FriendlyByteBuf, CPacketPersistentRecipeSelection> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC, CPacketPersistentRecipeSelection::recipe, CPacketPersistentRecipeSelection::new
   );

   public static void handle(CPacketPersistentRecipeSelection packet, ServerPlayer player) {
      Level world = player.getCommandSenderWorld();
      Optional<RecipeHolder<?>> maybeRecipe = world.getRecipeManager().byKey(packet.recipe);
      maybeRecipe.ifPresent(recipe -> {
         AbstractContainerMenu container = player.containerMenu;
         IBlockEntityRecipeData recipeData = PolymorphApi.getInstance().getBlockEntityRecipeData(container);
         if (recipeData != null) {
            recipeData.selectRecipe((RecipeHolder<?>)recipe);
            PolymorphIntegrations.selectRecipe(recipeData.getOwner(), container, (RecipeHolder<?>)recipe);
         }
      });
   }

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
