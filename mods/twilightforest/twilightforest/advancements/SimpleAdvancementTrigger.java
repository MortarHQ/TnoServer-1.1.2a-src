package twilightforest.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import twilightforest.init.TFAdvancements;

public class SimpleAdvancementTrigger extends SimpleCriterionTrigger<SimpleAdvancementTrigger.TriggerInstance> {
   public Codec<SimpleAdvancementTrigger.TriggerInstance> codec() {
      return SimpleAdvancementTrigger.TriggerInstance.CODEC;
   }

   public void trigger(ServerPlayer player) {
      this.trigger(player, instance -> true);
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
      public static final Codec<SimpleAdvancementTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SimpleAdvancementTrigger.TriggerInstance::player))
            .apply(instance, SimpleAdvancementTrigger.TriggerInstance::new)
      );

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> makeTFPortal() {
         return ((SimpleAdvancementTrigger)TFAdvancements.MADE_TF_PORTAL.get()).createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> eatHydraChop() {
         return ((SimpleAdvancementTrigger)TFAdvancements.CONSUME_HYDRA_CHOP.get())
            .createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> completeQuestRam() {
         return ((SimpleAdvancementTrigger)TFAdvancements.QUEST_RAM_COMPLETED.get())
            .createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> activateGhastTrap() {
         return ((SimpleAdvancementTrigger)TFAdvancements.ACTIVATED_GHAST_TRAP.get())
            .createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> killAllPhantoms() {
         return ((SimpleAdvancementTrigger)TFAdvancements.KILL_ALL_PHANTOMS.get())
            .createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> activatedPedestal() {
         return ((SimpleAdvancementTrigger)TFAdvancements.PLACED_TROPHY_ON_PEDESTAL.get())
            .createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }

      public static Criterion<SimpleAdvancementTrigger.TriggerInstance> brokenSword() {
         return ((SimpleAdvancementTrigger)TFAdvancements.BROKE_GLASS_SWORD.get())
            .createCriterion(new SimpleAdvancementTrigger.TriggerInstance(Optional.empty()));
      }
   }
}
