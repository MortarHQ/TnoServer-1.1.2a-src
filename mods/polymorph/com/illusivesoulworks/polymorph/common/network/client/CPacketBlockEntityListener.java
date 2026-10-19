package com.illusivesoulworks.polymorph.common.network.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.common.util.BlockEntityTicker;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;

public record CPacketBlockEntityListener(boolean add) implements CustomPacketPayload {
   public static final Type<CPacketBlockEntityListener> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("polymorph", "block_entity_listener"));
   public static final StreamCodec<FriendlyByteBuf, CPacketBlockEntityListener> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, CPacketBlockEntityListener::add, CPacketBlockEntityListener::new
   );

   public static void handle(CPacketBlockEntityListener packet, ServerPlayer player) {
      if (player != null) {
         if (packet.add) {
            AbstractContainerMenu container = player.containerMenu;
            PolymorphApi api = PolymorphApi.getInstance();
            IBlockEntityRecipeData recipeData = api.getBlockEntityRecipeData(container);
            if (recipeData != null) {
               BlockEntityTicker.add(player, recipeData);
               ResourceLocation resourceLocation = null;
               RecipeHolder<?> recipeHolder = recipeData.getSelectedRecipe();
               if (recipeHolder != null) {
                  resourceLocation = recipeHolder.id();
               }

               api.getNetwork().sendRecipesListS2C(player, recipeData.isEmpty() ? new TreeSet<>() : recipeData.getRecipesList(), resourceLocation);
            }
         } else {
            BlockEntityTicker.remove(player);
         }
      }
   }

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
