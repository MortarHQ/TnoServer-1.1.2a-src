package com.bobmowzie.mowziesmobs.datagen;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MMEntityTypeTags extends EntityTypeTagsProvider {
   public MMEntityTypeTags(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "mowziesmobs", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.addToVanillaTags();
      this.addToCommonTags();
      this.tag(key("umvuthana"))
         .add((EntityType)EntityHandler.UMVUTHI.value())
         .add((EntityType)EntityHandler.UMVUTHANA_MINION.value())
         .add((EntityType)EntityHandler.UMVUTHANA_RAPTOR.value())
         .add((EntityType)EntityHandler.UMVUTHANA_CRANE.value())
         .add((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.value())
         .add((EntityType)EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.value())
         .add((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.value());
      this.tag(key("umvuthana_umvuthi_aligned"))
         .add((EntityType)EntityHandler.UMVUTHI.value())
         .add((EntityType)EntityHandler.UMVUTHANA_MINION.value())
         .add((EntityType)EntityHandler.UMVUTHANA_RAPTOR.value())
         .add((EntityType)EntityHandler.UMVUTHANA_CRANE.value())
         .add((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.value());
   }

   private void addToVanillaTags() {
      this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add((EntityType)EntityHandler.GROTTOL.get());
      this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add((EntityType)EntityHandler.FROSTMAW.get());
      this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add((EntityType)EntityHandler.FROSTMAW.get());
      this.tag(EntityTypeTags.IMPACT_PROJECTILES)
         .add((EntityType)EntityHandler.DART.get())
         .add((EntityType)EntityHandler.BOULDER_PROJECTILE.get())
         .add((EntityType)EntityHandler.POISON_BALL.get());
   }

   private void addToCommonTags() {
      this.tag(EntityTypes.BOSSES)
         .add((EntityType)EntityHandler.FROSTMAW.get())
         .add((EntityType)EntityHandler.WROUGHTNAUT.get())
         .add((EntityType)EntityHandler.UMVUTHI.get())
         .add((EntityType)EntityHandler.SCULPTOR.get());
      this.tag(EntityTypes.CAPTURING_NOT_SUPPORTED)
         .add((EntityType)EntityHandler.FROSTMAW.get())
         .add((EntityType)EntityHandler.WROUGHTNAUT.get())
         .add((EntityType)EntityHandler.UMVUTHI.get())
         .add((EntityType)EntityHandler.SCULPTOR.get());
      this.tag(EntityTypes.TELEPORTING_NOT_SUPPORTED).add((EntityType)EntityHandler.UMVUTHI.get()).add((EntityType)EntityHandler.SCULPTOR.get());
   }

   private static TagKey<EntityType<?>> key(String path) {
      return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("mowziesmobs", path));
   }
}
