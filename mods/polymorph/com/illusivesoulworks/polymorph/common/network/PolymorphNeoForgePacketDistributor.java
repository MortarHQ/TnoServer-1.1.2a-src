package com.illusivesoulworks.polymorph.common.network;

import com.illusivesoulworks.polymorph.api.common.base.IPolymorphNetwork;
import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import com.illusivesoulworks.polymorph.common.network.client.CPacketBlockEntityListener;
import com.illusivesoulworks.polymorph.common.network.client.CPacketPersistentRecipeSelection;
import com.illusivesoulworks.polymorph.common.network.client.CPacketPlayerRecipeSelection;
import com.illusivesoulworks.polymorph.common.network.server.SPacketPlayerRecipeSync;
import com.illusivesoulworks.polymorph.common.network.server.SPacketRecipeHandshake;
import com.illusivesoulworks.polymorph.common.network.server.SPacketRecipesList;
import com.illusivesoulworks.polymorph.common.network.server.SPacketUpdatePreview;
import java.util.HashSet;
import java.util.Optional;
import java.util.SortedSet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class PolymorphNeoForgePacketDistributor implements IPolymorphNetwork {
   @Override
   public void sendPlayerRecipeSelectionC2S(ResourceLocation resourceLocation) {
      PacketDistributor.sendToServer(new CPacketPlayerRecipeSelection(resourceLocation), new CustomPacketPayload[0]);
   }

   @Override
   public void sendPersistentRecipeSelectionC2S(ResourceLocation resourceLocation) {
      PacketDistributor.sendToServer(new CPacketPersistentRecipeSelection(resourceLocation), new CustomPacketPayload[0]);
   }

   @Override
   public void sendRecipesListS2C(ServerPlayer player) {
      this.sendRecipesListS2C(player, null);
   }

   @Override
   public void sendRecipesListS2C(ServerPlayer player, SortedSet<IRecipePair> recipesList) {
      this.sendRecipesListS2C(player, recipesList, null);
   }

   @Override
   public void sendRecipesListS2C(ServerPlayer player, SortedSet<IRecipePair> recipesList, ResourceLocation selected) {
      HashSet<IRecipePair> set = null;
      if (recipesList != null) {
         set = new HashSet<>(recipesList);
      }

      PacketDistributor.sendToPlayer(player, new SPacketRecipesList(Optional.ofNullable(set), Optional.ofNullable(selected)), new CustomPacketPayload[0]);
   }

   @Override
   public void sendPlayerSyncS2C(ServerPlayer player, SortedSet<IRecipePair> recipesList, ResourceLocation selected) {
      HashSet<IRecipePair> set = null;
      if (recipesList != null) {
         set = new HashSet<>(recipesList);
      }

      PacketDistributor.sendToPlayer(player, new SPacketPlayerRecipeSync(Optional.ofNullable(set), Optional.ofNullable(selected)), new CustomPacketPayload[0]);
   }

   @Override
   public void sendBlockEntityListenerC2S(boolean add) {
      PacketDistributor.sendToServer(new CPacketBlockEntityListener(add), new CustomPacketPayload[0]);
   }

   @Override
   public void sendUpdatePreviewS2C(ServerPlayer player) {
      PacketDistributor.sendToPlayer(player, SPacketUpdatePreview.INSTANCE, new CustomPacketPayload[0]);
   }

   @Override
   public void sendRecipeHandshakeS2C(ServerPlayer player) {
      PacketDistributor.sendToPlayer(player, SPacketRecipeHandshake.INSTANCE, new CustomPacketPayload[0]);
   }
}
