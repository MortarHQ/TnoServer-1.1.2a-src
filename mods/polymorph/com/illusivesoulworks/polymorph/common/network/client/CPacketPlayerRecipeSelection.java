package com.illusivesoulworks.polymorph.common.network.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.common.capability.IPlayerRecipeData;
import com.illusivesoulworks.polymorph.common.integration.PolymorphIntegrations;
import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;

public record CPacketPlayerRecipeSelection(ResourceLocation recipe) implements CustomPacketPayload {
   public static final Type<CPacketPlayerRecipeSelection> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("polymorph", "player_recipe_selection"));
   public static final StreamCodec<FriendlyByteBuf, CPacketPlayerRecipeSelection> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC, CPacketPlayerRecipeSelection::recipe, CPacketPlayerRecipeSelection::new
   );

   public static void handle(CPacketPlayerRecipeSelection packet, ServerPlayer player) {
      AbstractContainerMenu container = player.containerMenu;
      player.level().getRecipeManager().byKey(packet.recipe).ifPresent(recipe -> {
         PolymorphApi api = PolymorphApi.getInstance();
         IPlayerRecipeData recipeData = api.getPlayerRecipeData(player);
         if (recipeData != null) {
            recipeData.selectRecipe((RecipeHolder<?>)recipe);
         }

         PolymorphIntegrations.selectRecipe(container, (RecipeHolder<?>)recipe);
         container.slotsChanged(player.getInventory());
         if (container instanceof ItemCombinerMenu) {
            ((ItemCombinerMenu)container).createResult();
            container.broadcastChanges();
            api.getNetwork().sendUpdatePreviewS2C(player);
         }
      });
   }

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
