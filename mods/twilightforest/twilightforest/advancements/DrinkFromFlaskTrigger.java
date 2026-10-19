package twilightforest.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.alchemy.Potion;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.Component;
import twilightforest.beanification.Configurable;
import twilightforest.init.TFAdvancements;
import twilightforest.util.HolderMatcher;

@Configurable
public class DrinkFromFlaskTrigger extends SimpleCriterionTrigger<DrinkFromFlaskTrigger.TriggerInstance> {
   @Autowired
   private DrinkFromFlaskTrigger.TriggerInstance.DrinkFromFlaskTriggerInstanceFactory factory;
   @Autowired
   private HolderMatcher holderMatcher;

   public DrinkFromFlaskTrigger() {
      BeanContext.injectInto(this);
   }

   public Codec<DrinkFromFlaskTrigger.TriggerInstance> codec() {
      return this.factory.CODEC;
   }

   public void trigger(ServerPlayer player, int doses, int seconds, Holder<Potion> potion) {
      this.trigger(player, instance -> instance.matches(this, doses, seconds, potion));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player, Ints doses, Ints seconds, Holder<Potion> potion) implements SimpleInstance {
      public boolean matches(DrinkFromFlaskTrigger parent, int doses, int seconds, Holder<Potion> potion) {
         return this.doses().matches(doses) && this.seconds().matches(seconds) && parent.holderMatcher.match(this.potion(), potion);
      }

      @Component
      public static class DrinkFromFlaskTriggerInstanceFactory {
         public final Codec<DrinkFromFlaskTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                  EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DrinkFromFlaskTrigger.TriggerInstance::player),
                  Ints.CODEC.optionalFieldOf("doses", Ints.between(0, 4)).forGetter(DrinkFromFlaskTrigger.TriggerInstance::doses),
                  Ints.CODEC.optionalFieldOf("seconds", Ints.exactly(8)).forGetter(DrinkFromFlaskTrigger.TriggerInstance::seconds),
                  BuiltInRegistries.POTION.holderByNameCodec().fieldOf("potion").forGetter(DrinkFromFlaskTrigger.TriggerInstance::potion)
               )
               .apply(instance, DrinkFromFlaskTrigger.TriggerInstance::new)
         );

         public Criterion<DrinkFromFlaskTrigger.TriggerInstance> drankPotion(int doses, Ints seconds, Holder<Potion> potion) {
            return ((DrinkFromFlaskTrigger)TFAdvancements.DRINK_FROM_FLASK.get())
               .createCriterion(new DrinkFromFlaskTrigger.TriggerInstance(Optional.empty(), Ints.exactly(doses), seconds, potion));
         }

         public Criterion<DrinkFromFlaskTrigger.TriggerInstance> drankPotion(Ints doses, Ints seconds, Holder<Potion> potion) {
            return ((DrinkFromFlaskTrigger)TFAdvancements.DRINK_FROM_FLASK.get())
               .createCriterion(new DrinkFromFlaskTrigger.TriggerInstance(Optional.empty(), doses, seconds, potion));
         }
      }
   }
}
