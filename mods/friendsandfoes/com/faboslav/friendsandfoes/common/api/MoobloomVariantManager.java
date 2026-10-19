package com.faboslav.friendsandfoes.common.api;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public final class MoobloomVariantManager extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
   private static final MoobloomVariant DEFAULT_MOOBLOOM_VARIANT = new MoobloomVariant(
      "buttercup", (Block)FriendsAndFoesBlocks.BUTTERCUP.get(), FriendsAndFoesTags.HAS_MOOBLOOMS
   );
   public static final MoobloomVariantManager MOOBLOOM_VARIANT_MANAGER = new MoobloomVariantManager();
   private List<MoobloomVariant> moobloomVariants = new ArrayList<MoobloomVariant>() {
      {
         this.add(MoobloomVariantManager.DEFAULT_MOOBLOOM_VARIANT);
      }
   };

   private MoobloomVariantManager() {
      super(GSON, "moobloom_variants");
   }

   protected void apply(Map<ResourceLocation, JsonElement> moobloomVariantsJson, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<ResourceLocation, MoobloomVariant> moobloomVariants = new HashMap<>();

      for (Entry<ResourceLocation, JsonElement> entry : moobloomVariantsJson.entrySet()) {
         ResourceLocation resourceLocation = entry.getKey();
         JsonElement moobloomVariantJson = entry.getValue();
         MoobloomVariant moobloomVariant = (MoobloomVariant)MoobloomVariant.CODEC.parse(JsonOps.INSTANCE, moobloomVariantJson).getOrThrow();
         moobloomVariants.put(resourceLocation, moobloomVariant);
      }

      this.apply(moobloomVariants);
   }

   public void apply(Map<ResourceLocation, MoobloomVariant> moobloomVariants) {
      this.moobloomVariants.clear();
      this.moobloomVariants.add(DEFAULT_MOOBLOOM_VARIANT);

      for (Entry<ResourceLocation, MoobloomVariant> entry : moobloomVariants.entrySet()) {
         MoobloomVariant moobloomVariant = entry.getValue();
         this.moobloomVariants.add(new MoobloomVariant(moobloomVariant.getName(), moobloomVariant.getFlower(), moobloomVariant.getBiomes()));
      }

      FriendsAndFoes.getLogger().info("Loaded {} moobloom variants", this.moobloomVariants.size());
   }

   public void setMoobloomVariants(List<MoobloomVariant> moobloomVariants) {
      this.moobloomVariants = moobloomVariants;
   }

   public List<MoobloomVariant> getMoobloomVariants() {
      return this.moobloomVariants;
   }

   public MoobloomVariant getDefaultMoobloomVariant() {
      return DEFAULT_MOOBLOOM_VARIANT;
   }

   public MoobloomVariant getRandomMoobloomVariant(RandomSource random) {
      Object[] values = this.getMoobloomVariants().toArray();
      int min = 0;
      int max = values.length - 1;
      return (MoobloomVariant)values[random.nextInt(max - min + 1) + min];
   }

   @Nullable
   public MoobloomVariant getMoobloomVariantByName(String name) {
      for (MoobloomVariant moobloomVariant : this.getMoobloomVariants()) {
         if (Objects.equals(moobloomVariant.getName(), name)) {
            return moobloomVariant;
         }
      }

      return null;
   }

   @Nullable
   public MoobloomVariant getRandomBiomeSpecificMoobloomVariant(ServerLevelAccessor serverWorldAccess, BlockPos blockPos) {
      List<MoobloomVariant> possibleMoobloomVariants = new ArrayList<>();
      Holder<Biome> biome = serverWorldAccess.getBiome(blockPos);

      for (MoobloomVariant moobloomVariant : this.getMoobloomVariants()) {
         if (biome.is(moobloomVariant.getBiomes())) {
            possibleMoobloomVariants.add(moobloomVariant);
         }
      }

      return possibleMoobloomVariants.isEmpty() ? null : possibleMoobloomVariants.get(serverWorldAccess.getRandom().nextInt(possibleMoobloomVariants.size()));
   }

   @Nullable
   public MoobloomVariant getByFlowerItem(Item flowerItem) {
      for (MoobloomVariant moobloomVariant : this.getMoobloomVariants()) {
         if (moobloomVariant.getFlowerAsItem() == flowerItem) {
            return moobloomVariant;
         }
      }

      return null;
   }
}
