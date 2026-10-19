package io.github.manasmods.tensura_neb.neoforge.data;

import com.mojang.datafixers.util.Pair;
import io.github.manasmods.tensura.data.template.critereon.TamedPredicate;
import io.github.manasmods.tensura.registry.item.TensuraConsumableItems;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;
import io.github.manasmods.tensura_neb.registry.NebItems;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;
import org.jetbrains.annotations.NotNull;

public class NebAdvancementGenerator implements AdvancementGenerator {
   public static Criterion<?> IMPOSSIBLE = CriteriaTriggers.IMPOSSIBLE.createCriterion(new TriggerInstance());
   public static final ResourceLocation MONSTER_RULER = ResourceLocation.fromNamespaceAndPath("tensura_neb", "monster_ruler");
   public static final ResourceLocation LION_KING = ResourceLocation.fromNamespaceAndPath("tensura_neb", "lion_king");
   public static final ResourceLocation QUEEN_OF_NIGHTMARES = ResourceLocation.fromNamespaceAndPath("tensura_neb", "queen_of_nightmares");

   public void generate(@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper helper) {
      AdvancementHolder MONSTER_RULER = this.addRoot(
         consumer,
         helper,
         NebAdvancementGenerator.MONSTER_RULER,
         (ItemLike)TensuraConsumableItems.CHILLED_SLIME.get(),
         ResourceLocation.fromNamespaceAndPath("tensura", "textures/block/cream_labyrinth_stone.png"),
         AdvancementType.CHALLENGE,
         true,
         true,
         true,
         null,
         Pair.of("rimuru", this.playerKilledEntityTrigger((EntityType<?>)NebEntityTypes.RIMURU_OGRE_FIGHT.get(), new TamedPredicate(false)))
      );
      AdvancementHolder LION_KING = this.addChild(
         consumer,
         helper,
         NebAdvancementGenerator.LION_KING,
         MONSTER_RULER,
         (ItemLike)NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD.get(),
         AdvancementType.CHALLENGE,
         true,
         true,
         true,
         null,
         Pair.of("carrion", this.playerKilledEntityTrigger((EntityType<?>)NebEntityTypes.CARRION.get(), new TamedPredicate(false)))
      );
      AdvancementHolder QUEEN_OF_NIGHTMARES = this.addChild(
         consumer,
         helper,
         NebAdvancementGenerator.QUEEN_OF_NIGHTMARES,
         LION_KING,
         (ItemLike)NebItems.NIGHT_ROSE.get(),
         AdvancementType.CHALLENGE,
         true,
         true,
         true,
         null,
         Pair.of("luminous", this.playerKilledEntityTrigger((EntityType<?>)NebEntityTypes.LUMINOUS_VALENTINE.get(), new TamedPredicate(false)))
      );
   }

   @SafeVarargs
   private AdvancementHolder addRoot(
      Consumer<AdvancementHolder> consumer,
      ExistingFileHelper helper,
      ResourceLocation location,
      ItemLike displayItem,
      ResourceLocation background,
      AdvancementType advancementType,
      boolean showToast,
      boolean announceInChat,
      boolean hidden,
      @Nullable Strategy requirements,
      Pair<String, Criterion<?>>... criterion
   ) {
      Component title = Component.translatable("tensura.advancements." + location.getPath() + ".title");
      Component description = Component.translatable("tensura.advancements." + location.getPath() + ".description");
      Builder builder = Builder.advancement().display(displayItem, title, description, background, advancementType, showToast, announceInChat, hidden);
      if (requirements != null) {
         builder.requirements(requirements);
      }

      if (criterion.length > 0) {
         for (Pair<String, Criterion<?>> map : criterion) {
            builder.addCriterion((String)map.getFirst(), (Criterion)map.getSecond());
         }
      } else {
         builder.addCriterion("custom", IMPOSSIBLE);
      }

      builder.save(consumer, ResourceLocation.parse(String.format("%s:%s", location.getNamespace(), location.getPath())), helper);
      return builder.build(location);
   }

   @SafeVarargs
   private AdvancementHolder addChild(
      Consumer<AdvancementHolder> consumer,
      ExistingFileHelper helper,
      ResourceLocation location,
      AdvancementHolder parent,
      ItemLike displayItem,
      AdvancementType advancementType,
      boolean showToast,
      boolean announceInChat,
      boolean hidden,
      @Nullable Strategy requirements,
      Pair<String, Criterion<?>>... criterion
   ) {
      return this.addChild(consumer, helper, location, parent, displayItem, advancementType, showToast, announceInChat, hidden, null, requirements, criterion);
   }

   @SafeVarargs
   private AdvancementHolder addChild(
      Consumer<AdvancementHolder> consumer,
      ExistingFileHelper helper,
      ResourceLocation location,
      AdvancementHolder parent,
      ItemLike displayItem,
      AdvancementType advancementType,
      boolean showToast,
      boolean announceInChat,
      boolean hidden,
      @Nullable AdvancementRewards rewards,
      @Nullable Strategy requirements,
      Pair<String, Criterion<?>>... criterion
   ) {
      Component title = Component.translatable("tensura.advancements." + location.getPath() + ".title");
      Component description = Component.translatable("tensura.advancements." + location.getPath() + ".description");
      Builder builder = Builder.advancement().parent(parent).display(displayItem, title, description, null, advancementType, showToast, announceInChat, hidden);
      if (requirements != null) {
         builder.requirements(requirements);
      }

      if (rewards != null) {
         builder.rewards(rewards);
      }

      if (criterion.length > 0) {
         for (Pair<String, Criterion<?>> map : criterion) {
            builder.addCriterion((String)map.getFirst(), (Criterion)map.getSecond());
         }
      } else {
         builder.addCriterion("custom", IMPOSSIBLE);
      }

      builder.save(consumer, ResourceLocation.parse(String.format("%s:%s", location.getNamespace(), location.getPath())), helper);
      return builder.build(location);
   }

   private Criterion<net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance> playerKilledEntityTrigger(
      EntityType<?> entity, EntitySubPredicate subPredicate
   ) {
      net.minecraft.advancements.critereon.EntityPredicate.Builder predicate = net.minecraft.advancements.critereon.EntityPredicate.Builder.entity()
         .of(entity)
         .subPredicate(subPredicate);
      return net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(predicate);
   }
}
