package com.foxablazeultimate.network;

import com.foxablazeultimate.client.FoxAblazeUltimateClientAccess;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record OpenShubWarehousePayload(
   int mode, int abilitySlot, List<ResourceLocation> skills, List<ResourceLocation> giftOnlySkills, List<OpenShubWarehousePayload.RecipeEntry> recipes
) implements CustomPacketPayload {
   private static final int MAX_SKILLS = 1024;
   private static final int MAX_RECIPES = 1024;
   public static final Type<OpenShubWarehousePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "open_shub_warehouse"));
   public static final StreamCodec<FriendlyByteBuf, OpenShubWarehousePayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenShubWarehousePayload::encode, OpenShubWarehousePayload::new
   );

   public OpenShubWarehousePayload(FriendlyByteBuf buf) {
      this(buf.readVarInt(), buf.readVarInt(), readSkills(buf, "skills"), readSkills(buf, "gift-only skills"), readRecipes(buf));
   }

   private static List<ResourceLocation> readSkills(FriendlyByteBuf buf, String name) {
      int size = readBoundedSize(buf, 1024, name);
      List<ResourceLocation> skills = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         skills.add(buf.readResourceLocation());
      }

      return skills;
   }

   private static List<OpenShubWarehousePayload.RecipeEntry> readRecipes(FriendlyByteBuf buf) {
      int size = readBoundedSize(buf, 1024, "recipes");
      List<OpenShubWarehousePayload.RecipeEntry> recipes = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         recipes.add(new OpenShubWarehousePayload.RecipeEntry(buf.readResourceLocation(), buf.readResourceLocation(), buf.readResourceLocation()));
      }

      return recipes;
   }

   private static int readBoundedSize(FriendlyByteBuf buf, int maximum, String name) {
      int size = buf.readVarInt();
      if (size >= 0 && size <= maximum) {
         return size;
      } else {
         throw new IllegalArgumentException("Shub warehouse " + name + " size out of bounds: " + size);
      }
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeVarInt(this.mode);
      buf.writeVarInt(this.abilitySlot);
      int skillCount = Math.min(1024, this.skills.size());
      buf.writeVarInt(skillCount);

      for (int i = 0; i < skillCount; i++) {
         buf.writeResourceLocation(this.skills.get(i));
      }

      int giftOnlyCount = Math.min(1024, this.giftOnlySkills.size());
      buf.writeVarInt(giftOnlyCount);

      for (int i = 0; i < giftOnlyCount; i++) {
         buf.writeResourceLocation(this.giftOnlySkills.get(i));
      }

      int recipeCount = Math.min(1024, this.recipes.size());
      buf.writeVarInt(recipeCount);

      for (int i = 0; i < recipeCount; i++) {
         OpenShubWarehousePayload.RecipeEntry recipe = this.recipes.get(i);
         buf.writeResourceLocation(recipe.first());
         buf.writeResourceLocation(recipe.second());
         buf.writeResourceLocation(recipe.result());
      }
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> FoxAblazeUltimateClientAccess.handleOpenShubWarehouse(this));
      }
   }

   public Type<OpenShubWarehousePayload> type() {
      return TYPE;
   }

   public record RecipeEntry(ResourceLocation first, ResourceLocation second, ResourceLocation result) {
   }
}
