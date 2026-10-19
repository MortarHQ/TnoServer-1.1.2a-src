package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.MobEffectsPredicate.MobEffectInstancePredicate;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;

public class EntityEffectPredicateParser {
   public static Component parseEntityEffectPredicate(MobEffectsPredicate predicate) {
      Map<Holder<MobEffect>, MobEffectInstancePredicate> effects = predicate.effectMap();
      List<MutableComponent> list = new LinkedList<>();

      for (Entry<Holder<MobEffect>, MobEffectInstancePredicate> entry : effects.entrySet()) {
         Component name = ((MobEffect)entry.getKey().value()).getDisplayName();
         MobEffectInstancePredicate data = entry.getValue();
         Ints amplifier = data.amplifier();
         if (!amplifier.equals(Ints.ANY)) {
            Optional<Integer> min = amplifier.min();
            Optional<Integer> max = amplifier.max();
            if (Objects.equals(min, max) && min.isPresent()) {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.amplifier", name, min.get() + 1));
            } else if (min.isPresent() && max.isPresent()) {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.amplifier_2", name, min.get() + 1, max.get() + 1));
            } else {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.amplifier_3", name));
            }
         }

         Ints duration = data.duration();
         if (!duration.equals(Ints.ANY)) {
            Optional<Integer> min = duration.min();
            Optional<Integer> max = duration.max();
            if (Objects.equals(min, max) && min.isPresent()) {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.duration", name, min.get() + 1));
            } else if (min.isPresent() && max.isPresent()) {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.duration_2", name, min.get() + 1, max.get() + 1));
            } else {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.duration_3", name));
            }
         }

         Optional<Boolean> ambient = data.ambient();
         if (ambient.isPresent()) {
            if (ambient.get()) {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.ambient_true", name));
            } else {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.ambient_false", name));
            }
         }

         Optional<Boolean> visible = data.visible();
         if (visible.isPresent()) {
            if (visible.get()) {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.visible_true", name));
            } else {
               list.add(LText.translatable("emi_loot.entity_predicate.effect.visible_false", name));
            }
         }

         list.add(LText.translatable("emi_loot.entity_predicate.effect.fallback", name));
      }

      if (!list.isEmpty()) {
         return LText.translatable("emi_loot.entity_predicate.effect_1", ListProcessors.buildAndList(list));
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Unparsable entity effect predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
