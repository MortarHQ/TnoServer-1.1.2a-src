package com.illusivesoulworks.polymorph.common.network.server;

import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import com.illusivesoulworks.polymorph.common.util.RecipePair;
import java.util.HashSet;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SPacketRecipesList(Optional<HashSet<IRecipePair>> recipeList, Optional<ResourceLocation> selected) implements CustomPacketPayload {
   public static final Type<SPacketRecipesList> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("polymorph", "recipes_list"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SPacketRecipesList> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.optional(RecipePair.STREAM_CODEC.apply(ByteBufCodecs.collection(HashSet::new))),
      SPacketRecipesList::recipeList,
      ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
      SPacketRecipesList::selected,
      SPacketRecipesList::new
   );

   public static void handle(SPacketRecipesList packet) {
      ClientPacketHandler.handle(packet);
   }

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
