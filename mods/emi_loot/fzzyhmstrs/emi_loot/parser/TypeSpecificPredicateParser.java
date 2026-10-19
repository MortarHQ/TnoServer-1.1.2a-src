package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.mixins.VariantPredicateAccessor;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.advancements.critereon.GameTypePredicate;
import net.minecraft.advancements.critereon.LightningBoltPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.SlimePredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates.EntityVariantPredicateType;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.PlayerPredicate.AdvancementCriterionsPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate.AdvancementDonePredicate;
import net.minecraft.advancements.critereon.PlayerPredicate.AdvancementPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate.StatMatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.MushroomCow.MushroomType;
import net.minecraft.world.entity.animal.TropicalFish.Pattern;
import net.minecraft.world.entity.animal.axolotl.Axolotl.Variant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.minecraft.world.level.GameType;

public class TypeSpecificPredicateParser {
   public static Component parseTypeSpecificPredicate(EntitySubPredicate predicate) {
      if (predicate instanceof LightningBoltPredicate) {
         return parseLightningBoltPredicate((LightningBoltPredicate)predicate);
      }

      if (predicate instanceof FishingHookPredicate) {
         return parseFishingHookPredicate((FishingHookPredicate)predicate);
      }

      if (predicate instanceof PlayerPredicate) {
         return parsePlayerPredicate((PlayerPredicate)predicate);
      }

      if (predicate instanceof SlimePredicate) {
         return parseSlimePredicate((SlimePredicate)predicate);
      }

      if (predicate instanceof EntityVariantPredicateType<?>.Instance variantPredicate) {
         if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof CatVariant cat) {
            ResourceLocation id = BuiltInRegistries.CAT_VARIANT.getKey(cat);
            if (id != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.cat." + id);
               return LText.translatable("emi_loot.entity_predicate.type_specific.cat", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof FrogVariant frog) {
            ResourceLocation id = BuiltInRegistries.FROG_VARIANT.getKey(frog);
            if (id != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.frog." + id);
               return LText.translatable("emi_loot.entity_predicate.type_specific.frog", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof Variant axolotl) {
            String name = axolotl.getName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.axolotl." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.axolotl", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof Type boat) {
            String name = boat.getName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.boat." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.boat", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof net.minecraft.world.entity.animal.Fox.Type fox) {
            String name = fox.getSerializedName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.fox." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.fox", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof MushroomType moo) {
            String name = moo.getSerializedName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.mooshroom." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.mooshroom", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof net.minecraft.world.entity.animal.Rabbit.Variant rabbit) {
            String name = rabbit.getSerializedName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.rabbit." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.rabbit", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof net.minecraft.world.entity.animal.horse.Variant horse) {
            String name = horse.getSerializedName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.horse." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.horse", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof net.minecraft.world.entity.animal.horse.Llama.Variant llama) {
            String name = llama.getSerializedName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.llama." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.llama", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof VillagerType villager) {
            String name = villager.toString();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.villager." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.villager", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof net.minecraft.world.entity.animal.Parrot.Variant parrot) {
            String name = parrot.getSerializedName();
            if (name != null) {
               MutableComponent v = LText.translatable("emi_loot.entity_predicate.type_specific.parrot." + name);
               return LText.translatable("emi_loot.entity_predicate.type_specific.parrot", v);
            }
         } else if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof Pattern tropical_fish) {
            Component name = tropical_fish.displayName();
            if (name != null) {
               return LText.translatable("emi_loot.entity_predicate.type_specific.tropical_fish", name);
            }
         } else {
            if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof PaintingVariant) {
               return LText.translatable("emi_loot.entity_predicate.type_specific.painting");
            }

            if (((VariantPredicateAccessor)variantPredicate).getVariant() instanceof WolfVariant) {
               return LText.translatable("emi_loot.entity_predicate.type_specific.wolf");
            }
         }
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Type specific predicate undefined or unparsable. Affects table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }

   public static Component parseLightningBoltPredicate(LightningBoltPredicate predicate) {
      Ints blocksSetOnFire = predicate.blocksSetOnFire();
      if (!blocksSetOnFire.equals(Ints.ANY)) {
         return LText.translatable(
            "emi_loot.entity_predicate.type_specific.lightning",
            NumberProcessors.processNumberRange(
               blocksSetOnFire,
               "emi_loot.entity_predicate.type_specific.lightning.blocks",
               "emi_loot.entity_predicate.type_specific.lightning.blocks_2",
               "emi_loot.entity_predicate.type_specific.lightning.blocks_3",
               "emi_loot.entity_predicate.type_specific.lightning.blocks_4",
               ""
            )
         );
      }

      Optional<EntityPredicate> entityStruck = predicate.entityStruck();
      if (entityStruck.isPresent()) {
         return LText.translatable(
            "emi_loot.entity_predicate.type_specific.lightning",
            LText.translatable("emi_loot.entity_predicate.type_specific.lightning.struck", EntityPredicateParser.parseEntityPredicate(entityStruck.get()))
         );
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Lightning bolt predicate empty or unparsable. Affects table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }

   public static Component parseFishingHookPredicate(FishingHookPredicate predicate) {
      Optional<Boolean> inOpenWater = predicate.inOpenWater();
      return inOpenWater.isPresent() && inOpenWater.get()
         ? LText.translatable("emi_loot.entity_predicate.type_specific.fishing_hook_true")
         : LText.translatable("emi_loot.entity_predicate.type_specific.fishing_hook_false");
   }

   public static Component parsePlayerPredicate(PlayerPredicate predicate) {
      Ints experienceLevel = predicate.level();
      if (!experienceLevel.equals(Ints.ANY)) {
         return LText.translatable(
            "emi_loot.entity_predicate.type_specific.player",
            NumberProcessors.processNumberRange(
               experienceLevel,
               "emi_loot.entity_predicate.type_specific.player.level",
               "emi_loot.entity_predicate.type_specific.player.level_2",
               "emi_loot.entity_predicate.type_specific.player.level_3",
               "emi_loot.entity_predicate.type_specific.player.level_4",
               ""
            )
         );
      }

      GameTypePredicate gameMode = predicate.gameType();
      if (gameMode != GameTypePredicate.ANY) {
         return LText.translatable(
            "emi_loot.entity_predicate.type_specific.player",
            LText.translatable(
               "emi_loot.entity_predicate.type_specific.player.gamemode",
               ListProcessors.buildOrList(gameMode.types().stream().map(GameType::getShortDisplayName).<MutableComponent>map(Component::plainCopy).toList())
            )
         );
      }

      List<StatMatcher<?>> stats = predicate.stats();
      if (!stats.isEmpty()) {
         List<MutableComponent> list = new LinkedList<>();

         for (StatMatcher<?> stat : stats) {
            String name = ((Stat)stat.stat().get()).getName();
            String[] namePieces = name.split(":");
            if (namePieces.length == 2) {
               String typeId = namePieces[0].replace('.', ':');
               String valueId = namePieces[1].replace('.', ':');
               MutableComponent num = NumberProcessors.processNumberRange(
                  stat.range(),
                  "emi_loot.entity_predicate.type_specific.player.stats.exact",
                  "emi_loot.entity_predicate.type_specific.player.stats.between",
                  "emi_loot.entity_predicate.type_specific.player.stats.at_least",
                  "emi_loot.entity_predicate.type_specific.player.stats.at_most",
                  "emi_loot.entity_predicate.type_specific.player.stats.fallback"
               );
               list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.stats.type." + typeId, valueId, num));
            }
         }

         if (!list.isEmpty()) {
            return LText.translatable("emi_loot.entity_predicate.type_specific.player", ListProcessors.buildAndList(list));
         }
      }

      Object2BooleanMap<ResourceLocation> recipes = predicate.recipes();
      if (!recipes.isEmpty()) {
         List<MutableComponent> list = new LinkedList<>();
         ObjectIterator var18 = recipes.object2BooleanEntrySet().iterator();

         while (var18.hasNext()) {
            Entry<ResourceLocation> entry = (Entry<ResourceLocation>)var18.next();
            list.add(
               entry.getBooleanValue()
                  ? LText.translatable("emi_loot.entity_predicate.type_specific.player.recipe_true", ((ResourceLocation)entry.getKey()).toString())
                  : LText.translatable("emi_loot.entity_predicate.type_specific.player.recipe_false", ((ResourceLocation)entry.getKey()).toString())
            );
         }

         if (!list.isEmpty()) {
            return LText.translatable("emi_loot.entity_predicate.type_specific.player", ListProcessors.buildAndList(list));
         }
      }

      Map<ResourceLocation, AdvancementPredicate> advancements = predicate.advancements();
      if (advancements.isEmpty()) {
         Optional<EntityPredicate> entityPredicate = predicate.lookingAt();
         if (entityPredicate.isPresent()) {
            return LText.translatable(
               "emi_loot.entity_predicate.type_specific.player",
               LText.translatable("emi_loot.entity_predicate.type_specific.player.looking", EntityPredicateParser.parseEntityPredicate(entityPredicate.get()))
            );
         }

         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Lightning bolt predicate empty or unparsable. Affects table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.predicate.invalid");
      } else {
         List<MutableComponent> list = new LinkedList<>();

         for (java.util.Map.Entry<ResourceLocation, AdvancementPredicate> entry : advancements.entrySet()) {
            String idString = entry.getKey().toString();
            AdvancementPredicate advancementPredicate = entry.getValue();
            if (advancementPredicate instanceof AdvancementDonePredicate) {
               boolean done = ((AdvancementDonePredicate)advancementPredicate).state();
               if (done) {
                  list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.id_true", idString));
               } else {
                  list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.id_false", idString));
               }
            } else if (advancementPredicate instanceof AdvancementCriterionsPredicate) {
               Object2BooleanMap<String> criteria = ((AdvancementCriterionsPredicate)advancementPredicate).criterions();
               if (!criteria.isEmpty()) {
                  List<MutableComponent> list2 = new LinkedList<>();
                  ObjectIterator var13 = criteria.object2BooleanEntrySet().iterator();

                  while (var13.hasNext()) {
                     Entry<String> criteriaEntry = (Entry<String>)var13.next();
                     if (criteriaEntry.getBooleanValue()) {
                        list2.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.crit_true", criteriaEntry.getKey()));
                     } else {
                        list2.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.crit_false", criteriaEntry.getKey()));
                     }
                  }

                  list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.crit_base", idString, ListProcessors.buildAndList(list2)));
               }
            }
         }

         return LText.translatable("emi_loot.entity_predicate.type_specific.player", ListProcessors.buildAndList(list));
      }
   }

   public static Component parseSlimePredicate(SlimePredicate predicate) {
      Ints size = predicate.size();
      if (size.equals(Ints.ANY)) {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Undefined slime size predicate in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.predicate.invalid");
      } else {
         return NumberProcessors.processNumberRange(
            size,
            "emi_loot.entity_predicate.type_specific.slime",
            "emi_loot.entity_predicate.type_specific.slime_2",
            "emi_loot.entity_predicate.type_specific.slime_3",
            "emi_loot.entity_predicate.type_specific.slime_4",
            ""
         );
      }
   }
}
