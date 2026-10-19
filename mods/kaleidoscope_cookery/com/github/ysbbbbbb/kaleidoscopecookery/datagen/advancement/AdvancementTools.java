package com.github.ysbbbbbb.kaleidoscopecookery.datagen.advancement;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModTrigger;
import java.util.Optional;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.DistanceTrigger.TriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class AdvancementTools {
   public static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "textures/advancement/background.png");

   public static Builder makeTask(ItemLike item, String key) {
      MutableComponent title = Component.translatable("advancements.kaleidoscope_cookery.%s.title".formatted(key));
      MutableComponent desc = Component.translatable("advancements.kaleidoscope_cookery.%s.description".formatted(key));
      return Builder.advancement().display(item, title, desc, BG, AdvancementType.TASK, true, true, false);
   }

   public static Builder makeChallenge(ItemLike item, String key) {
      MutableComponent title = Component.translatable("advancements.kaleidoscope_cookery.%s.title".formatted(key));
      MutableComponent desc = Component.translatable("advancements.kaleidoscope_cookery.%s.description".formatted(key));
      return Builder.advancement().display(item, title, desc, BG, AdvancementType.CHALLENGE, true, true, true);
   }

   public static Builder makeGoal(ItemLike item, String key) {
      MutableComponent title = Component.translatable("advancements.kaleidoscope_cookery.%s.title".formatted(key));
      MutableComponent desc = Component.translatable("advancements.kaleidoscope_cookery.%s.description".formatted(key));
      return Builder.advancement().display(item, title, desc, BG, AdvancementType.GOAL, true, true, false);
   }

   public static Criterion<TriggerInstance> flatulenceFlyHeight(
      net.minecraft.advancements.critereon.EntityPredicate.Builder player, DistancePredicate distance, LocationPredicate startPosition
   ) {
      return ((DistanceTrigger)ModTrigger.FLATULENCE_FLY_HEIGHT.get())
         .createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap(player)), Optional.of(startPosition), Optional.of(distance)));
   }

   public static ResourceLocation modLoc(String id) {
      return ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", id);
   }
}
