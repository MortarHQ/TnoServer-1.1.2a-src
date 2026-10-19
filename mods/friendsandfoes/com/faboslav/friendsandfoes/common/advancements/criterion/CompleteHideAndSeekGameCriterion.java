package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.CriterionValidator;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public final class CompleteHideAndSeekGameCriterion extends SimpleCriterionTrigger<CompleteHideAndSeekGameCriterion.Conditions> {
   public Codec<CompleteHideAndSeekGameCriterion.Conditions> codec() {
      return CompleteHideAndSeekGameCriterion.Conditions.CODEC;
   }

   public void trigger(ServerPlayer player, RascalEntity rascal, ItemStack stack) {
      LootContext lootContext = EntityPredicate.createContext(player, rascal);
      this.trigger(player, conditions -> conditions.matches(lootContext, stack));
   }

   public record Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> rascal, Optional<ItemPredicate> item)
      implements SimpleInstance {
      public static final Codec<CompleteHideAndSeekGameCriterion.Conditions> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CompleteHideAndSeekGameCriterion.Conditions::player),
               EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("rascal").forGetter(CompleteHideAndSeekGameCriterion.Conditions::rascal),
               ItemPredicate.CODEC.optionalFieldOf("item").forGetter(CompleteHideAndSeekGameCriterion.Conditions::item)
            )
            .apply(instance, CompleteHideAndSeekGameCriterion.Conditions::new)
      );

      public static Criterion<CompleteHideAndSeekGameCriterion.Conditions> any() {
         return ((CompleteHideAndSeekGameCriterion)FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get())
            .createCriterion(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty(), Optional.empty()));
      }

      public static Criterion<CompleteHideAndSeekGameCriterion.Conditions> create(Builder playerPredicate) {
         return ((CompleteHideAndSeekGameCriterion)FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get())
            .createCriterion(
               new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(EntityPredicate.wrap(playerPredicate)), Optional.empty(), Optional.empty())
            );
      }

      public boolean matches(LootContext rascal, ItemStack stack) {
         return this.rascal.isPresent() && !this.rascal.get().matches(rascal) ? false : !this.item.isPresent() || this.item.get().test(stack);
      }

      public void validate(CriterionValidator validator) {
         super.validate(validator);
         validator.validateEntity(this.rascal, ".rascal");
      }
   }
}
