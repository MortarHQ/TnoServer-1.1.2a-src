package com.shin.tensura_kumodesu.helper;

import java.util.function.BiConsumer;

public final class TensuraKumodesuLanguage {
   public static void init(BiConsumer<String, String> biConsumer) {
      races(biConsumer);
      ui(biConsumer);
      skill(biConsumer);
      skillmode(biConsumer);
      addeffect(biConsumer);
      adddeath(biConsumer);
      block(biConsumer);
      entity(biConsumer);
   }

   private static void ui(BiConsumer<String, String> addTranslation) {
      addUI("any_sin_requirement", "any Deadly Sin Skill", addTranslation);
      addUI("lord_requirement", "a Spirit Lord", addTranslation);
   }

   private static void addUI(String id, String name, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("tensura_kumodesu.ui." + id, name);
   }

   public static void races(BiConsumer<String, String> addTranslation) {
      addRace(
         "small_lesser_taratect",
         "Small Lesser Taratect",
         "The weakest possible species, all it knows how to do is charge at opponents head-on.",
         addTranslation
      );
      addRace("orthocadinaht", "Orthocadinaht", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("arch_taratect", "Arch Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("greater_taratect", "Greater Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("great_taratect", "Great Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("queen_taratect", "Queen Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("small_taratect", "Small Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("taratect", "Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("poison_taratect", "Poison Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("lesser_poison_taratect", "Lesser Poison Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("lesser_taratect", "Lesser Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("great_poison_taratect", "Great Poison Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("small_poison_taratect", "Small Poison Taratect", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("zoa_ele", "Zoa Ele", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("ede_saine", "Ede Saine", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("zana_horowa", "Zana Horowa", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("arachne", "Arachne", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("ogre_shaman", "Ogre Shaman", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("high_ogre", "High Ogre", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("ogre_general", "Ogre General", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("ogre_king", "Ogre King", "If you're reading this then you are skipping progression.", addTranslation);
      addRace("wrath_oni", "Wrath Oni", "If you're reading this then you are skipping progression.", addTranslation);
   }

   private static void skill(BiConsumer<String, String> addTranslation) {
      addSkill("deadly_poison", "Deadly Poison", "Decently more lethal than Poison.", addTranslation);
      addSkill("deadly_spider_poison", "Deadly Spider Poison", "Highly lethal.", addTranslation);
      addSkill("fatal_spider_poison", "Fatal Spider Poison", "Corrodes the victims body and spirit.", addTranslation);
      addSkill("divine_magic_release", "Divine Magic Control", "Is this the pinnacle of magic control...?", addTranslation);
      addSkill("egg_laying", "Egg Laying", "Be a good mother.", addTranslation);
      addSkill("humanification", "Humanification", "Become more human-like.", addTranslation);
      addSkill("spider_movement", "Spider Movement", "Grants the user night vision, cobweb immunity and special climbing.", addTranslation);
      addSkill("taboo", "Taboo", "What sins have you committed?.", addTranslation);
      addSkill("resolve", "Resolve", "Keep going.", addTranslation);
      addSkill("determination", "Determination", "Don't let it end here.", addTranslation);
      addSkill("perseverance", "Perseverance", "Shine like a comet, until you burnout.", addTranslation);
      addSkill("weapon_creation", "Weapon Creation", "Allows the user to create unique weapons to wield.", addTranslation);
      addSkill("wondrous_weapon_creation", "Wondrous Weapon Creation", "Allows the user to create wondrous weapons to wield.", addTranslation);
      addSkill("height_of_occultism", "Height of Occultism", "The pinnacle of magic and sorcery.", addTranslation);
   }

   private static void skillmode(BiConsumer<String, String> addTranslation) {
      addSkillMode("mode", "default", "Default", addTranslation);
   }

   private static void addeffect(BiConsumer<String, String> addTranslation) {
      addEffect("fatal_spider_poison", "Fatal Spider Poison", addTranslation);
      addEffect("deadly_spider_poison", "Deadly Spider Poison", addTranslation);
      addEffect("deadly_poison", "Deadly Poison", addTranslation);
   }

   private static void adddeath(BiConsumer<String, String> addTranslation) {
      addDeath("fatal_spider_poison", "%1$s died to fatal spider poison", addTranslation);
      addDeath("deadly_spider_poison", "%1$s died to deadly spider poison", addTranslation);
      addDeath("deadly_poison", "%1$s died to deadly poison", addTranslation);
   }

   private static void block(BiConsumer<String, String> addTranslation) {
      addBlock("taratect_egg", "Taratect Egg", addTranslation);
   }

   private static void entity(BiConsumer<String, String> addTranslation) {
      addEntity("small_lesser_taratect", "Small Lesser Taratect", addTranslation);
      addEntity("orthocadinaht", "Orthocadinaht", addTranslation);
      addEntity("lesser_taratect", "Lesser Taratect", addTranslation);
      addEntity("lesser_poison_taratect", "Lesser Poison Taratect", addTranslation);
      addEntity("poison_taratect", "Poison Taratect", addTranslation);
      addEntity("great_poison_taratect", "Great Poison Taratect", addTranslation);
      addEntity("small_taratect", "Small Taratect", addTranslation);
      addEntity("small_poison_taratect", "Small Poison Taratect", addTranslation);
      addEntity("zoa_ele", "Zoa Ele", addTranslation);
      addEntity("ede_saine", "Ede Saine", addTranslation);
      addEntity("zana_horowa", "Zana Horowa", addTranslation);
      addEntity("arachne", "Arachne", addTranslation);
      addEntity("taratect", "Taratect", addTranslation);
      addEntity("great_taratect", "Great Taratect", addTranslation);
      addEntity("greater_taratect", "Greater Taratect", addTranslation);
      addEntity("arch_taratect", "Arch Taratect", addTranslation);
      addEntity("queen_taratect", "Queen Taratect", addTranslation);
   }

   private static void addEntity(String id, String name, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("entity.tensura_kumodesu." + id, name);
   }

   private static void addBlock(String id, String name, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("block.tensura_kumodesu." + id, name);
   }

   private static void addSkill(String id, String name, String description, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("tensura_kumodesu.skill." + id, name);
      addTranslation.accept("tensura_kumodesu.skill." + id + ".description", description);
   }

   private static void addEffect(String id, String name, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("effect.tensura_kumodesu." + id, name);
   }

   private static void addDeath(String id, String name, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("death.attack.tensura_kumodesu." + id, name);
   }

   private static void addSkillMode(String id, String name, String description, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("tensura_kumodesu.skill.mode.default", description);
   }

   private static void addRace(String id, String name, String description, BiConsumer<String, String> addTranslation) {
      addTranslation.accept("tensura_kumodesu.race." + id, name);
      addTranslation.accept("tensura_kumodesu.race." + id + ".description", description);
   }
}
