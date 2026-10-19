package org.crypticdev.stextras.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import java.util.ArrayList;
import java.util.List;

@SyncToClient
public class STExtrasUniqueSkillConfig extends ManasConfig {
   public STExtrasUniqueSkillConfig.Imitator Imitator = new STExtrasUniqueSkillConfig.Imitator();

   public String getFileName() {
      return "stextras/ability/skill/unique_config";
   }

   public static class Imitator extends ManasSubConfig {
      @Comment("The cooldown in seconds for the Imitator skill.")
      public int cooldown = 1800;
      @Comment("List of entities. Format: 'entity_id|TYPE|Description|skill1,skill2'")
      public List<String> entries = new ArrayList<>();

      public Imitator() {
         this.addEntry(
            "tensura:hinata_sakaguchi",
            "BOSS",
            "As the second highest authority in the western holy church, Hinata's only goal is to protect humanity with no room for compromise.",
            "tensura:usurper",
            "tensura:mathematician"
         );
         this.addEntry(
            "tensura:shogo_taguchi",
            "OTHERWORLDER",
            "Shogo was a human summoned from another world, one of three Otherworlders working for the Falmuth Kingdom. Consumed by power and arrogance, he died after colliding with Rimuru's forces.",
            "tensura:berserker",
            "tensura:survivor"
         );
         this.addEntry(
            "tensura:shizu",
            "BOSS",
            "Shizue, known as the Conqueror of Flames, was a human summoned from Japan and a former Hero. She possessed the spirit Ifrit and served as a teacher and inspiration to many, including Rimuru Tempest, who inherited her will and physical appearance.",
            "tensura:degenerate"
         );
         this.addEntry(
            "tensura:folgen",
            "OTHERWORLDER",
            "Folgen was a loyal yet ruthless knight of Falmuth, devoted to his nation above all else. Cold and pragmatic, he saw others as tools and met his end facing Rimuru’s wrath.",
            "tensura:spearhead"
         );
         this.addEntry(
            "tensura:kyoya_tachibana",
            "OTHERWORLDER",
            "Enjoying the fantasy of being summoned, Kyoya holds no hate for the cardinal world. His calm and curious demeanor hides his more violent tendencies.",
            "tensura:severer"
         );
         this.addEntry(
            "tensura:mai_furuki",
            "OTHERWORLDER",
            "Serious and driven to repay her debts, Mai aims to master her ability so that she can return home and can care for her family.",
            "tensura:traveler"
         );
         this.addEntry(
            "tensura:mark_lauren",
            "OTHERWORLDER",
            "Short tempered and with a strong dislike of being kept out of the loop, Mark strives to be successful in life and live luxuriously.",
            "tensura:thrower"
         );
         this.addEntry(
            "tensura:shinji_tanimura",
            "OTHERWORLDER",
            "Shinji Tanimura is a Japanese Otherworlder who previously served as a military officer in the Eastern Empire. He was granted asylum in Tempest and now works as Ramiris's assistant.",
            "tensura:healer"
         );
         this.addEntry(
            "tensura:shin_ryusei",
            "OTHERWORLDER",
            "Shin Ryusei is an Otherworlder and disciple of Gadra. After defecting to the Jura Tempest Federation, he now works as Ramiris' assistant, helping her with research and dungeon development.",
            "tensura:observer"
         );
         this.addEntry(
            "tensura:orc_disaster",
            "BOSS",
            "The Orc Disaster, also known as Geld, was an Orc Lord who transformed into a Demon Lord Seed after devouring numerous foes.",
            "tensura:starved"
         );
         this.addEntry(
            "tensura:kirara_mizutani",
            "OTHERWORLDER",
            "Only caring for her former world, Kirara views the cardinal world as nothing more than an illusion for her to play with. She is willing to cross almost any line to get her way.",
            "tensura:bewilder"
         );
         this.addEntry(
            "tensura_neb:rimuru_ogre_fight",
            "BOSS",
            "Rimuru is a Demon Lord who founded and rules the Jura Tempest Federation. He is also one of the Octagram's Eight Demon Lords. Rimuru, once a human, was reincarnated as a Slime in another world and quickly rose to power.",
            "tensura:predator",
            "tensura:great_sage"
         );
         this.addEntry(
            "tensura_neb:carrion",
            "BOSS",
            "Carrion, also known as Beast Master, is the ruler of the Beast Kingdom of Eurazania and the lycanthropes. He is a former Demon Lord of the Ten Great Demon Lords and now works as Milim Nava's assistant alongside Frey.",
            "tensura:royal_beast"
         );
         this.addEntry(
            "tensura_neb:luminous_valentine",
            "BOSS",
            "Luminous is a Demon Lord, the fifth seat of the Octagram, and the legitimate ruler of the Holy Empire of Lubelius. She is the leader of all vampires and one of the oldest Demon Lords.",
            "tensura:lust"
         );
      }

      private void addEntry(String entity, String type, String desc, String... skills) {
         String skillStr = String.join(",", skills);
         this.entries.add(entity + "|" + type + "|" + desc + "|" + skillStr);
      }
   }
}
