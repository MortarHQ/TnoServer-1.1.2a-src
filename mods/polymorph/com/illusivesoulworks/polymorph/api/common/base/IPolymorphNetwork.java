package com.illusivesoulworks.polymorph.api.common.base;

import java.util.SortedSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public interface IPolymorphNetwork {
   void sendPlayerRecipeSelectionC2S(ResourceLocation var1);

   void sendPersistentRecipeSelectionC2S(ResourceLocation var1);

   void sendRecipesListS2C(ServerPlayer var1);

   void sendRecipesListS2C(ServerPlayer var1, SortedSet<IRecipePair> var2);

   void sendRecipesListS2C(ServerPlayer var1, SortedSet<IRecipePair> var2, ResourceLocation var3);

   void sendPlayerSyncS2C(ServerPlayer var1, SortedSet<IRecipePair> var2, ResourceLocation var3);

   void sendBlockEntityListenerC2S(boolean var1);

   void sendUpdatePreviewS2C(ServerPlayer var1);

   void sendRecipeHandshakeS2C(ServerPlayer var1);
}
