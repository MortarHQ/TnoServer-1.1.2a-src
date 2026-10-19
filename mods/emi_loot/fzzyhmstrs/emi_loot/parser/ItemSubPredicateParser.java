package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.mixins.EnchantmentsPredicateAccessor;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.critereon.ItemAttributeModifiersPredicate;
import net.minecraft.advancements.critereon.ItemBundlePredicate;
import net.minecraft.advancements.critereon.ItemContainerPredicate;
import net.minecraft.advancements.critereon.ItemCustomDataPredicate;
import net.minecraft.advancements.critereon.ItemDamagePredicate;
import net.minecraft.advancements.critereon.ItemFireworkExplosionPredicate;
import net.minecraft.advancements.critereon.ItemFireworksPredicate;
import net.minecraft.advancements.critereon.ItemPotionsPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.advancements.critereon.ItemTrimPredicate;
import net.minecraft.advancements.critereon.ItemWritableBookPredicate;
import net.minecraft.advancements.critereon.ItemWrittenBookPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate.Enchantments;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate.StoredEnchantments;
import net.minecraft.advancements.critereon.ItemFireworkExplosionPredicate.FireworkPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate.Type;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.component.FireworkExplosion.Shape;

public class ItemSubPredicateParser {
   public static Component parseItemSubPredicate(Type<? extends ItemSubPredicate> type, ItemSubPredicate predicate) {
      if (predicate instanceof ItemDamagePredicate damagePredicate) {
         Ints durability = damagePredicate.durability();
         if (durability != Ints.ANY) {
            int finalMax = durability.max().orElse(0);
            int finalMin = durability.min().orElse(0);
            return LText.translatable("emi_loot.item_predicate.durability", Integer.toString(finalMin), Integer.toString(finalMax));
         }

         Ints damage = damagePredicate.damage();
         if (damage != Ints.ANY) {
            int finalMax = durability.max().orElse(0);
            int finalMin = durability.min().orElse(0);
            return LText.translatable("emi_loot.item_predicate.damage", Integer.toString(finalMin), Integer.toString(finalMax));
         }
      } else {
         if (predicate instanceof Enchantments enchantmentPredicate) {
            return EnchantmentPredicateParser.parseEnchantmentPredicates(((EnchantmentsPredicateAccessor)enchantmentPredicate).getEnchantments());
         }

         if (predicate instanceof StoredEnchantments enchantmentPredicate) {
            return EnchantmentPredicateParser.parseEnchantmentPredicates(((EnchantmentsPredicateAccessor)enchantmentPredicate).getEnchantments());
         }

         if (predicate instanceof ItemPotionsPredicate potionContentsPredicate) {
            HolderSet<Potion> potions = potionContentsPredicate.potions();
            if (potions.unwrapKey().isPresent()) {
               return LText.translatable("emi_loot.predicate.potion.tag", ((TagKey)potions.unwrapKey().get()).location().toString());
            }

            if (potions.size() > 0) {
               List<MutableComponent> list = potions.stream()
                  .map(p -> LText.translatable(Potion.getName(Optional.of(p), Items.POTION.getDescriptionId() + ".effect.")))
                  .toList();
               return LText.translatable("emi_loot.predicate.potion.list", ListProcessors.buildOrList(list));
            }
         } else {
            if (predicate instanceof ItemCustomDataPredicate customDataPredicate) {
               return NbtPredicateParser.parseNbtPredicate(customDataPredicate.value());
            }

            if (predicate instanceof ItemContainerPredicate) {
               return LText.translatable("emi_loot.predicate.container");
            }

            if (predicate instanceof ItemBundlePredicate) {
               return LText.translatable("emi_loot.predicate.container");
            }

            if (predicate instanceof ItemFireworkExplosionPredicate fireworkExplosionPredicate) {
               FireworkPredicate fireworkExplosionPredicatePredicate = fireworkExplosionPredicate.predicate();
               if (fireworkExplosionPredicatePredicate.shape().isPresent()) {
                  Component v = ((Shape)fireworkExplosionPredicatePredicate.shape().get()).getName();
                  return LText.translatable("emi_loot.predicate.firework.shape", v);
               }

               if (fireworkExplosionPredicatePredicate.twinkle().isPresent()) {
                  return fireworkExplosionPredicatePredicate.twinkle().get()
                     ? LText.translatable("emi_loot.predicate.firework.twinkle")
                     : LText.translatable("emi_loot.predicate.firework.no_twinkle");
               }

               if (fireworkExplosionPredicatePredicate.trail().isPresent()) {
                  return fireworkExplosionPredicatePredicate.trail().get()
                     ? LText.translatable("emi_loot.predicate.firework.trail")
                     : LText.translatable("emi_loot.predicate.firework.no_trail");
               }

               return LText.translatable("emi_loot.predicate.container");
            }

            if (predicate instanceof ItemFireworksPredicate) {
               return LText.translatable("emi_loot.predicate.fireworks");
            }

            if (predicate instanceof ItemWritableBookPredicate) {
               return LText.translatable("emi_loot.predicate.writable_book");
            }

            if (predicate instanceof ItemWrittenBookPredicate) {
               return LText.translatable("emi_loot.predicate.writable_book");
            }

            if (predicate instanceof ItemAttributeModifiersPredicate) {
               return LText.translatable("emi_loot.predicate.attribute");
            }

            if (predicate instanceof ItemTrimPredicate) {
               return LText.translatable("emi_loot.predicate.trim");
            }
         }
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty item predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
