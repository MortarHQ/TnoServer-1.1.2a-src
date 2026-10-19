package com.github.wal_bos.moreskills.ai;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class TensuraAiKnowledge {
   private static final List<TensuraAiKnowledge.Entry> ANSWERS = new ArrayList<>();

   private TensuraAiKnowledge() {
   }

   public static String getLocalAnswer(String question) {
      String q = normalize(question);
      String stripped = stripQuestionWords(q);
      TensuraAiKnowledge.Entry best = null;
      int bestScore = 0;

      for (TensuraAiKnowledge.Entry entry : ANSWERS) {
         int score = scoreEntry(q, stripped, entry);
         if (score > bestScore || score == bestScore && isMoreSpecific(entry, best)) {
            bestScore = score;
            best = entry;
         }
      }

      return best != null && bestScore >= 18 ? best.answer() : null;
   }

   public static boolean probablyTensuraQuestion(String question) {
      String q = normalize(question);
      String stripped = stripQuestionWords(q);

      for (TensuraAiKnowledge.Entry entry : ANSWERS) {
         if (scoreEntry(q, stripped, entry) >= 8) {
            return true;
         }
      }

      return containsAny(
         q,
         "tensura",
         "skill",
         "unique",
         "ultimate",
         "extra",
         "intrinsic",
         "magic",
         "magicule",
         "aura",
         "ep",
         "race",
         "evolution",
         "demon lord",
         "hero",
         "labyrinth",
         "boss",
         "resistance",
         "nullification",
         "spiritual",
         "predator",
         "gluttony",
         "mammon",
         "belphegor",
         "spatial",
         "raphael",
         "ciel"
      );
   }

   public static Set<String> guessImportantTerms(String question) {
      String q = normalize(question);
      String stripped = stripQuestionWords(q);
      LinkedHashSet<String> terms = new LinkedHashSet<>();
      addTermsFromText(terms, q);
      addTermsFromText(terms, stripped);

      for (TensuraAiKnowledge.Entry entry : ANSWERS) {
         if (scoreEntry(q, stripped, entry) >= 8) {
            for (String key : entry.keys()) {
               if (key.length() >= 3) {
                  terms.add(key);
               }
            }
         }
      }

      while (terms.size() > 30) {
         String last = null;

         for (String term : terms) {
            last = term;
         }

         if (last == null) {
            break;
         }

         terms.remove(last);
      }

      return terms;
   }

   private static int scoreEntry(String q, String stripped, TensuraAiKnowledge.Entry entry) {
      int score = 0;

      for (String key : entry.keys()) {
         String normalizedKey = normalize(key);
         if (!normalizedKey.isBlank()) {
            if (q.equals(normalizedKey)) {
               score += 100;
            }

            if (stripped.equals(normalizedKey)) {
               score += 90;
            }

            if (q.contains(normalizedKey)) {
               score += 28 + normalizedKey.length() / 2;
            }

            if (stripped.contains(normalizedKey)) {
               score += 32 + normalizedKey.length() / 2;
            }

            String compactQ = q.replace(" ", "");
            String compactStripped = stripped.replace(" ", "");
            String compactKey = normalizedKey.replace(" ", "");
            if (compactKey.length() >= 4 && compactQ.contains(compactKey)) {
               score += 20;
            }

            if (compactKey.length() >= 4 && compactStripped.contains(compactKey)) {
               score += 25;
            }

            for (String token : normalizedKey.split("\\s+")) {
               if (token.length() >= 3) {
                  if (q.contains(token)) {
                     score += 5;
                  }

                  if (stripped.contains(token)) {
                     score += 6;
                  }
               }
            }
         }
      }

      return score;
   }

   private static void add(String keys, String answer) {
      String[] split = keys.split("\\|");
      List<String> list = new ArrayList<>();

      for (String key : split) {
         String clean = normalize(key);
         if (!clean.isBlank()) {
            list.add(clean);
         }
      }

      ANSWERS.add(new TensuraAiKnowledge.Entry(list, answer));
   }

   private static void addTermsFromText(Set<String> terms, String text) {
      for (String token : text.split("\\s+")) {
         String clean = token.replaceAll("[^a-zA-Z0-9']", "").trim();
         if (clean.length() >= 3 && !isQuestionJunk(clean)) {
            terms.add(clean);
         }
      }

      String[] words = text.split("\\s+");

      for (int size = Math.min(4, words.length); size >= 2; size--) {
         for (int i = 0; i <= words.length - size; i++) {
            StringBuilder builder = new StringBuilder();

            for (int j = 0; j < size; j++) {
               String clean = words[i + j].replaceAll("[^a-zA-Z0-9' ]", "").trim();
               if (clean.length() >= 2 && !isQuestionJunk(clean)) {
                  if (!builder.isEmpty()) {
                     builder.append(" ");
                  }

                  builder.append(clean);
               }
            }

            String phrase = builder.toString().trim();
            if (phrase.length() >= 5) {
               terms.add(phrase);
            }
         }
      }
   }

   private static String stripQuestionWords(String input) {
      String stripped = " " + normalize(input) + " ";
      String[] phrases = new String[]{
         " can you tell me about ",
         " could you tell me about ",
         " please tell me about ",
         " tell me everything about ",
         " tell me all about ",
         " tell me about ",
         " can you explain ",
         " could you explain ",
         " please explain ",
         " explain to me ",
         " can you show me ",
         " could you show me ",
         " show me ",
         " can you find ",
         " could you find ",
         " please find ",
         " find ",
         " can you search ",
         " could you search ",
         " please search ",
         " search ",
         " look up ",
         " lookup ",
         " check wiki for ",
         " check the wiki for ",
         " check records for ",
         " use wiki for ",
         " use the wiki for ",
         " from the wiki ",
         " from wiki ",
         " in the wiki ",
         " on the wiki ",
         " wiki page for ",
         " page for ",
         " what exactly does ",
         " what exactly is ",
         " what exactly are ",
         " what does ",
         " what do ",
         " what is ",
         " what are ",
         " what was ",
         " what were ",
         " whats ",
         " what's ",
         " wat does ",
         " wat is ",
         " wut does ",
         " wut is ",
         " wha does ",
         " wha is ",
         " wt does ",
         " wt is ",
         " how exactly does ",
         " how exactly do i get ",
         " how exactly to get ",
         " how do i get ",
         " how do you get ",
         " how can i get ",
         " how to get ",
         " how do i obtain ",
         " how do you obtain ",
         " how to obtain ",
         " where do i get ",
         " where to get ",
         " where can i get ",
         " where do i find ",
         " where to find ",
         " where can i find ",
         " how does ",
         " how do ",
         " how can i ",
         " can i ",
         " do i ",
         " does it ",
         " is it ",
         " why does ",
         " why do ",
         " why is ",
         " why are ",
         " in tensura reincarnated ",
         " in tensura reicarnated ",
         " in tensura reincarnatd ",
         " in tensura ",
         " in tesnura ",
         " in tensra ",
         " in tenusra ",
         " in the tensura mod ",
         " in the tesnura mod ",
         " in this mod ",
         " in mod ",
         " for players ",
         " as a player ",
         " gameplay wise ",
         " gameplay ",
         " addon wiki ",
         " mod wiki ",
         " official wiki ",
         " wiki gg ",
         " wiki.gg ",
         " ultimate skill ",
         " uniqe skill ",
         " unique skill ",
         " intrinsic skill ",
         " extra skill ",
         " common skill ",
         " resist skill ",
         " resistance skill ",
         " ability skill ",
         " skill ",
         " ability ",
         " abiltiy ",
         " abilty ",
         " abillity ",
         " passive ability ",
         " passive skill ",
         " passive ",
         " passives ",
         " pasive ",
         " pasives ",
         " active ability ",
         " active skill ",
         " active ",
         " actives ",
         " actve ",
         " actives ",
         " toggle ability ",
         " toggle skill ",
         " toggle ",
         " toggled ",
         " mode ",
         " modes ",
         " stance ",
         " stances ",
         " obtain cost ",
         " points to master ",
         " mastery points ",
         " cost ",
         " costs ",
         " cooldown ",
         " cooldowns ",
         " cool down ",
         " cool downs ",
         " requirement ",
         " requirements ",
         " req ",
         " reqs ",
         " obtain ",
         " obtained ",
         " get ",
         " gets ",
         " getting ",
         " got ",
         " use ",
         " uses ",
         " used ",
         " using ",
         " work ",
         " works ",
         " working ",
         " do ",
         " does ",
         " did ",
         " give ",
         " gives ",
         " giving ",
         " have ",
         " has ",
         " had ",
         " need ",
         " needs ",
         " needed ",
         " information about ",
         " info about ",
         " details about ",
         " detail about ",
         " full info ",
         " full details ",
         " all info ",
         " all details ",
         " exact info ",
         " exact details ",
         " exact values ",
         " exact value ",
         " exact ",
         " detailed ",
         " detail ",
         " details ",
         " info ",
         " information ",
         " summary ",
         " summarize ",
         " simple explanation ",
         " explanation ",
         " the ",
         " a ",
         " an ",
         " and ",
         " or ",
         " but ",
         " if ",
         " then ",
         " of the ",
         " for the ",
         " with the ",
         " to the ",
         " from the ",
         " of ",
         " for ",
         " with ",
         " without ",
         " from ",
         " into ",
         " onto ",
         " about ",
         " regarding ",
         " pls ",
         " plz ",
         " please ",
         " bro ",
         " bruh "
      };

      for (boolean changed = true; changed; stripped = stripped.replaceAll("\\s+", " ")) {
         changed = false;

         for (String phrase : phrases) {
            if (stripped.contains(phrase)) {
               stripped = stripped.replace(phrase, " ");
               changed = true;
            }
         }
      }

      stripped = stripped.replaceAll("\\b(wat|wut|wha|whats|what's)\\b", " ");
      stripped = stripped.replaceAll("\\b(does|do|did|is|are|was|were|can|could|would|should)\\b", " ");
      stripped = stripped.replaceAll("\\b(skill|ability|passive|active|mode|cost|cooldown|requirement|requirements|obtain|get|use|work|works)\\b", " ");
      return stripped.replaceAll("\\s+", " ").trim();
   }

   private static boolean containsAny(String input, String... values) {
      String q = normalize(input);

      for (String value : values) {
         String v = normalize(value);
         if (!v.isBlank() && q.contains(v)) {
            return true;
         }

         if (!v.isBlank() && q.replace(" ", "").contains(v.replace(" ", ""))) {
            return true;
         }
      }

      return false;
   }

   private static boolean isQuestionJunk(String input) {
      String q = normalize(input);
      if (q.isBlank()) {
         return true;
      } else {
         return q.length() <= 2 && !q.equals("ep") && !q.equals("hp") && !q.equals("mp")
            ? true
            : q.equals("what")
               || q.equals("wat")
               || q.equals("wut")
               || q.equals("wha")
               || q.equals("whats")
               || q.equals("what's")
               || q.equals("does")
               || q.equals("dose")
               || q.equals("do")
               || q.equals("did")
               || q.equals("how")
               || q.equals("hwo")
               || q.equals("where")
               || q.equals("wher")
               || q.equals("when")
               || q.equals("why")
               || q.equals("the")
               || q.equals("teh")
               || q.equals("and")
               || q.equals("or")
               || q.equals("but")
               || q.equals("for")
               || q.equals("with")
               || q.equals("without")
               || q.equals("from")
               || q.equals("about")
               || q.equals("regarding")
               || q.equals("tell")
               || q.equals("show")
               || q.equals("find")
               || q.equals("search")
               || q.equals("lookup")
               || q.equals("look")
               || q.equals("explain")
               || q.equals("info")
               || q.equals("information")
               || q.equals("detail")
               || q.equals("details")
               || q.equals("exact")
               || q.equals("full")
               || q.equals("summary")
               || q.equals("summarize")
               || q.equals("skill")
               || q.equals("skills")
               || q.equals("skil")
               || q.equals("abilty")
               || q.equals("abiltiy")
               || q.equals("ability")
               || q.equals("abilities")
               || q.equals("passive")
               || q.equals("passives")
               || q.equals("pasive")
               || q.equals("pasives")
               || q.equals("active")
               || q.equals("actives")
               || q.equals("actve")
               || q.equals("toggle")
               || q.equals("toggled")
               || q.equals("mode")
               || q.equals("modes")
               || q.equals("stance")
               || q.equals("stances")
               || q.equals("cost")
               || q.equals("costs")
               || q.equals("cooldown")
               || q.equals("cooldowns")
               || q.equals("requirement")
               || q.equals("requirements")
               || q.equals("req")
               || q.equals("reqs")
               || q.equals("use")
               || q.equals("uses")
               || q.equals("used")
               || q.equals("using")
               || q.equals("work")
               || q.equals("works")
               || q.equals("working")
               || q.equals("get")
               || q.equals("gets")
               || q.equals("getting")
               || q.equals("got")
               || q.equals("obtain")
               || q.equals("obtained")
               || q.equals("need")
               || q.equals("needs")
               || q.equals("needed")
               || q.equals("have")
               || q.equals("has")
               || q.equals("had")
               || q.equals("in")
               || q.equals("on")
               || q.equals("at")
               || q.equals("to")
               || q.equals("of")
               || q.equals("as")
               || q.equals("by")
               || q.equals("wiki")
               || q.equals("records")
               || q.equals("record")
               || q.equals("tensura")
               || q.equals("tesnura")
               || q.equals("mod")
               || q.equals("addon")
               || q.equals("minecraft")
               || q.equals("mc")
               || q.equals("pls")
               || q.equals("plz")
               || q.equals("please")
               || q.equals("bro")
               || q.equals("bruh");
      }
   }

   private static String normalize(String input) {
      if (input == null) {
         return "";
      }

      String normalized = input.toLowerCase(Locale.ROOT);
      normalized = normalized.replace('’', '\'').replace('‘', '\'').replace('“', '"').replace('”', '"').replace('`', '\'').replace('´', '\'');
      normalized = normalized.replace("tesnura", "tensura")
         .replace("tensra", "tensura")
         .replace("tenusra", "tensura")
         .replace("tensure", "tensura")
         .replace("reicarnated", "reincarnated")
         .replace("reincarnatd", "reincarnated")
         .replace("reincarnateed", "reincarnated")
         .replace("abiltiy", "ability")
         .replace("abilty", "ability")
         .replace("abillity", "ability")
         .replace("skil ", "skill ")
         .replace("pasive", "passive")
         .replace("actve", "active")
         .replace("cool down", "cooldown")
         .replace("cool downs", "cooldowns")
         .replace("reqs", "requirements")
         .replace("req ", "requirement ")
         .replace("spacial", "spatial")
         .replace("magiculs", "magicules")
         .replace("magiculees", "magicules")
         .replace("magicles", "magicules")
         .replace("existense", "existence")
         .replace("existance", "existence")
         .replace("spirtual", "spiritual")
         .replace("spirtitual", "spiritual")
         .replace("resistence", "resistance")
         .replace("resistace", "resistance")
         .replace("nullifcation", "nullification")
         .replace("nullfication", "nullification")
         .replace("demonloard", "demon lord")
         .replace("demonlord", "demon lord")
         .replace("harvist", "harvest")
         .replace("belphagor", "belphegor")
         .replace("belphgor", "belphegor")
         .replace("belph", "belphegor")
         .replace("mammonn", "mammon")
         .replace("mamon", "mammon")
         .replace("preditor", "predator")
         .replace("preddator", "predator")
         .replace("spacial motion", "spatial motion");
      normalized = normalized.replaceAll("[\\p{Cntrl}]", " ");
      normalized = normalized.replaceAll("[\\[\\]{}()<>]", " ");
      normalized = normalized.replaceAll("[\";:]", " ");
      normalized = normalized.replace("?", " ")
         .replace("!", " ")
         .replace(",", " ")
         .replace(".", " ")
         .replace("_", " ")
         .replace("-", " ")
         .replace("/", " ")
         .replace("\\", " ")
         .replace("|", " ");
      return normalized.replaceAll("\\s+", " ").trim();
   }

   private static boolean isMoreSpecific(TensuraAiKnowledge.Entry candidate, TensuraAiKnowledge.Entry current) {
      if (current == null) {
         return true;
      }

      int candidateSpecificity = specificity(candidate);
      int currentSpecificity = specificity(current);
      return candidateSpecificity > currentSpecificity;
   }

   private static int specificity(TensuraAiKnowledge.Entry entry) {
      int best = 0;

      for (String key : entry.keys()) {
         int words = key.isBlank() ? 0 : key.split("\\s+").length;
         int length = key.length();
         best = Math.max(best, words * 10 + length);
      }

      return best;
   }

   private static int levenshteinDistance(String a, String b) {
      int[] previous = new int[b.length() + 1];
      int[] current = new int[b.length() + 1];
      int j = 0;

      while (j <= b.length()) {
         previous[j] = j++;
      }

      for (int i = 1; i <= a.length(); i++) {
         current[0] = i;

         for (int jx = 1; jx <= b.length(); jx++) {
            int cost = a.charAt(i - 1) == b.charAt(jx - 1) ? 0 : 1;
            current[jx] = Math.min(Math.min(current[jx - 1] + 1, previous[jx] + 1), previous[jx - 1] + cost);
         }

         int[] temp = previous;
         previous = current;
         current = temp;
      }

      return previous[b.length()];
   }

   static {
      add(
         "ep|existence point|existence points|existence|power level|how strong|strength number",
         "Raphael: EP means Existence Points. It represents your overall existence, power, growth, race progression, and combat potential. Higher EP usually means stronger stats, better evolution potential, and access to higher-level progression."
      );
      add(
         "farm ep|gain ep|get ep|increase ep|raise ep|ep farming|ep gain|how do i get ep",
         "Raphael: Gain EP by defeating valid enemies, progressing your race, learning stronger abilities, improving equipment, and fighting enemies that are not excluded by server rules. Summoned, triggered, spawner, or protected entities may give reduced or no EP depending on rules."
      );
      add(
         "how to get strong|how to get stronger|how get strong|hw to gte strong|how to become strong|how to be strong|how do i become strong|how do i get powerful|how to get powerful|how to progress|how do i progress",
         "Raphael: To get stronger, follow this order: survive first, then farm valid enemies for EP, evolve your race, improve gear, gain resistances, train skill mastery, increase aura and magicules, then challenge stronger bosses. Do not rush bosses if your Spiritual Health or defenses are weak."
      );
      add(
         "what do i do first|what should i do first|first thing to do|new player what do i do|beginner what do i do|how to start|start guide|beginner guide|new player guide",
         "Raphael: First, get basic gear, food, shelter, and a safe weapon. Fight weak enemies for early EP, avoid bosses, learn your first useful skills, and keep aura, magicules, HP, and Spiritual Health stable before taking risks."
      );
      add(
         "what do i do next|what next|next step|where do i go next|progression next|stuck progression|im stuck|i am stuck",
         "Raphael: If you are stuck, check your weakest layer: EP, race evolution, gear, resistances, skill mastery, aura, magicules, or Spiritual Health. Upgrade the weakest one first, then attempt harder enemies."
      );
      add(
         "why am i weak|why so weak|i feel weak|i do no damage|i deal no damage|my damage low|low damage",
         "Raphael: You feel weak because one or more layers are behind: weapon damage, aura, magicules, mastery, EP, race evolution, element matchup, or enemy resistance. Improve gear and mastery first, then raise EP and upgrade skills."
      );
      add(
         "how to deal more damage|how get more damage|increase damage|more damage|stronger attacks|hit harder|how to hit harder",
         "Raphael: Increase damage by upgrading your weapon, raising aura or magicules, mastering skills, evolving your race, using the right element, improving attack attributes, and choosing skills that bypass armor, barriers, or resistance."
      );
      add(
         "how to survive|how to not die|how do i survive|survival tips|keep dying|i keep dying|dying too much",
         "Raphael: To survive, improve armor, resistances, regeneration, barriers, movement, food, healing, and Spiritual Health protection. Retreat before resources hit zero. Most deaths happen because players stay after their defenses are already failing."
      );
      add(
         "how to get ep fast|how to farm ep|fast ep|farm ep fast|best ep farm|ep farming guide",
         "Raphael: Farm EP by killing valid enemies you can defeat safely and repeatedly. Avoid enemies that are too weak if they give poor growth, and avoid enemies too strong if they drain resources or Spiritual Health. Server rules may block EP from summoned or farmed mobs."
      );
      add(
         "why no ep|not getting ep|ep not going up|why am i not gaining ep|no ep from mobs|mobs give no ep",
         "Raphael: If EP is not increasing, the target may be invalid, summoned, farm-limited, protected by server rules, too weak, already counted, or excluded from EP gain. Try natural stronger enemies and check server settings."
      );
      add(
         "how to evolve|how do i evolve|race evolution|evolve race|next evolution|how to get next race",
         "Raphael: Race evolution usually requires enough EP, race-specific conditions, and sometimes alignment or special progress. Raise EP, check available evolution prompts, prepare resources, and evolve only when safe."
      );
      add(
         "which race is best|best race|what race should i choose|best starter race|best race for beginner",
         "Raphael: The best race depends on your build. Beginners should choose a race that survives well. Magic builds want magicules and casting support. Melee builds want aura and durability. Summoners want magicules and control."
      );
      add(
         "how to get skills|how do i get skills|how to learn skills|learn more skills|get more skills|obtain skills",
         "Raphael: Skills come from race progression, combat, tomes, manuals, evolutions, special conditions, and requirements. Some need enough EP, a previous skill, a race, an alignment, or mastery."
      );
      add(
         "what skill should i get|best skill for me|which skill should i use|good skill for beginner|best beginner skill",
         "Raphael: Beginners should prioritize survival and consistency: detection, regeneration, resistance, movement, basic damage, and resource recovery. Do not chase a flashy Ultimate Skill before you can survive normal fights."
      );
      add(
         "how to master skills|how to get mastery|skill mastery fast|master skill fast|increase mastery",
         "Raphael: Master skills by using them in valid situations and meeting their mastery conditions. Use the skill often, keep resources high, and fight enemies safely enough that you can practice without dying."
      );
      add(
         "what is mastery|what does mastery do|why master skill|mastery meaning",
         "Raphael: Mastery improves a skill's reliability or strength. It can reduce cooldowns, improve values, unlock effects, evolve the skill, or make modes stronger depending on the skill."
      );
      add(
         "how to get magicules|increase magicules|get more magicules|max magicules|magicule capacity",
         "Raphael: Increase magicules through race evolution, magic-focused skills, gear, enchantments, and progression. Magic builds should prioritize magicule capacity and regeneration before expensive spells."
      );
      add(
         "how to get aura|increase aura|get more aura|max aura|aura capacity",
         "Raphael: Increase aura through race growth, combat-focused skills, physical progression, gear, and attributes. Melee and reinforcement builds need high aura and good aura recovery."
      );
      add(
         "what is aura|what does aura do|aura meaning|how aura works",
         "Raphael: Aura is combat energy used for physical pressure, reinforcement, movement, and some skills. If aura is low, melee pressure and sustained combat become weaker."
      );
      add(
         "what are magicules|what is magicule|magicules meaning|how magicules work",
         "Raphael: Magicules are magical energy used for magic, barriers, transformations, and many skills. If magicules are low, casting and high-level abilities become unreliable."
      );
      add(
         "what is spiritual health|spiritual hp|shp meaning|what does spiritual health do",
         "Raphael: Spiritual Health protects your existence beyond normal HP. Soul, death, spiritual, and high-level attacks can threaten it. If Spiritual Health drops, retreat immediately."
      );
      add(
         "how to protect spiritual health|increase spiritual health|spiritual health low|low shp|protect soul",
         "Raphael: Protect Spiritual Health with spiritual attack resistance, higher existence, better race progression, defensive skills, barriers, regeneration, and avoiding high-level enemies before you are ready."
      );
      add(
         "how to get ultimate skill|get ultimate skill|obtain ultimate|ultimate skill guide|how do i get ultimate",
         "Raphael: Ultimate Skills usually require high progression, strong prerequisites, mastery, EP, race growth, and special conditions. Master your current path first instead of trying to skip straight to an Ultimate."
      );
      add(
         "how to get unique skill|get unique skill|obtain unique|unique skill guide",
         "Raphael: Unique Skills can come from progression, conditions, race paths, combat, tomes, or special triggers. Focus on survival and growth first, then meet the skill's specific requirement."
      );
      add(
         "how to beat bosses|boss guide|how do i fight bosses|boss tips|boss keeps killing me",
         "Raphael: Before fighting bosses, fill resources, upgrade gear, get resistances, bring healing, protect Spiritual Health, learn attack patterns, and retreat if your defenses collapse. Do not face-tank unknown boss mechanics."
      );
      add(
         "when fight boss|am i ready for boss|should i fight boss|boss readiness",
         "Raphael: You are ready for a boss when you can survive its normal attacks, cleanse or resist its debuffs, keep resources stable, and escape if the fight turns bad. If one hit nearly kills you, you are not ready."
      );
      add(
         "best gear early|early gear|starter gear|what gear early|beginner gear",
         "Raphael: Early gear should focus on survival: armor, weapon, shield or defense tool, food, blocks, water, and a ranged option. Better gear lets you farm EP safely."
      );
      add(
         "best gear mid game|mid game gear|midgame gear|what gear midgame",
         "Raphael: Midgame gear should add enchantments, resistances, better weapons, mobility, healing, and resource-support items. Build around your main skill instead of using random stats."
      );
      add(
         "best gear late game|late game gear|endgame gear|what gear endgame",
         "Raphael: Endgame gear should include specialized resistances, high durability, strong damage, mobility, resource sustain, Spiritual Health protection, and backup gear for dangerous fights."
      );
      add(
         "what build should i use|best build|good build|build guide|build help",
         "Raphael: Choose one main win condition. Melee builds need aura and durability. Magic builds need magicules and casting speed. Tank builds need resistance and regeneration. Summon builds need magicules and owner safety."
      );
      add(
         "melee or magic|magic or melee|should i go melee|should i go magic",
         "Raphael: Choose melee if you like close combat, aura, speed, and weapon pressure. Choose magic if you like range, elements, burst, and utility. Hybrid builds are strong but need more resources."
      );
      add(
         "how to make good build|make build|build tips|how build works",
         "Raphael: A good build needs damage, defense, mobility, sustain, and a win condition. If one layer is missing, enemies will exploit it. Do not build only damage."
      );
      add(
         "how to counter strong players|strong player counter|beat stronger player|enemy stronger than me",
         "Raphael: Against stronger players, do not trade fairly. Use terrain, traps, resistance, burst timing, debuffs, mobility, and escape routes. Win by countering their kit, not by matching raw power."
      );
      add(
         "how to counter magic|beat mage|fight caster|counter caster",
         "Raphael: Counter magic with Magic Interference, silence, line-of-sight breaks, mobility, burst pressure, magic resistance, and resource drain. Do not let casters free-cast."
      );
      add(
         "how to counter melee|beat melee|fight sword user|counter fighter",
         "Raphael: Counter melee with range, slows, knockback, traps, barriers, movement, and terrain. Never let melee builds stay in their ideal range for free."
      );
      add(
         "how to counter tank|beat tank|counter tank|kill tank",
         "Raphael: Counter tanks with spiritual damage, corrosion, resource drain, anti-heal, barrier piercing, execution effects, and sustained pressure that outpaces regeneration."
      );
      add(
         "how to counter speed|beat speed build|counter fast player|catch fast enemy",
         "Raphael: Counter speed with slows, area attacks, prediction, spatial blockade, traps, auto-targeting, and forcing the fight into tight terrain."
      );
      add(
         "how to counter summons|beat summoner|counter summoner|too many summons",
         "Raphael: Counter summons with area damage, anti-summon pressure, killing the owner, crowd control, and not wasting all cooldowns on disposable minions."
      );
      add(
         "how to counter poison|beat poison|counter infection|counter dot",
         "Raphael: Counter poison and infection with abnormal nullification, poison resistance, cleanse, burst damage, spacing, and ending the fight before stacks overwhelm healing."
      );
      add(
         "how to counter death|beat death skill|counter execution|counter instant death",
         "Raphael: Counter death and execution skills with death resistance, Spiritual Health protection, cleanse, high HP, barriers, and staying above execution thresholds."
      );
      add(
         "how to counter time|beat time skill|counter time stop|counter temporal",
         "Raphael: Counter time skills with time resistance, instant attacks, passive defenses, non-projectile damage, mobility, and avoiding situations where time stop guarantees a hit."
      );
      add(
         "how to counter spatial|beat teleport|counter teleport|counter space skill",
         "Raphael: Counter spatial skills with spatial resistance, spatial blockade, prediction, area damage, and punishing the enemy after teleport or movement cooldowns."
      );
      add(
         "how to counter reflection|beat reflector|counter reflect|counter axiom",
         "Raphael: Counter reflection by avoiding reckless burst, using controlled damage, bypass effects, debuffs, resource drain, and waiting until reflection or death-save effects are unavailable."
      );
      add(
         "how to counter adaptation|beat adaptation|counter mahoraga|counter adaptability",
         "Raphael: Counter adaptation with burst damage, varied damage types, execution, spiritual pressure, and avoiding repeated use of the same attack pattern."
      );
      add(
         "how to counter drain|beat lifesteal|counter resource drain|counter magicule drain",
         "Raphael: Counter drain with burst damage, distance, anti-heal, resource regeneration, barriers, and leaving before your aura or magicules collapse."
      );
      add(
         "how to counter evil|beat evil kits|counter evil build|fight evil player",
         "Raphael: Evil builds usually rely on debuffs, fear, curse, drain, infection, summons, or execution. Counter them with cleanse, resistance, holy pressure, burst damage, and refusing long trades."
      );
      add(
         "what is good early skill|early skill|starter skill|beginner skill",
         "Raphael: Good early skills are reliable and defensive: detection, regeneration, resistance, movement, basic damage, and resource recovery. A skill that keeps you alive is better than one huge attack you cannot afford."
      );
      add(
         "what is good late skill|late skill|endgame skill|strong late skill",
         "Raphael: Good late skills have authority, survivability, resource control, and a clear win condition. Ultimate Skills, adaptation, reflection, spatial control, conquest, and death resistance matter more late game."
      );
      add(
         "how to avoid dying in pvp|pvp survival|pvp tips|fight players",
         "Raphael: In PvP, track cooldowns, use terrain, keep healing ready, protect Spiritual Health, do not waste escape tools, and identify the enemy kit before committing."
      );
      add(
         "how to win pvp|pvp guide|pvp help|player fight guide",
         "Raphael: To win PvP, know your win condition, bait enemy cooldowns, fight in favorable terrain, keep resources high, and disengage when your main defense is down."
      );
      add(
         "how to use terrain|terrain advantage|fight terrain|use blocks",
         "Raphael: Terrain is a weapon. Use blocks for cover, cliffs for knockback, caves for traps, open fields for mobility, water for tridents, and walls to break line of sight."
      );
      add(
         "how to escape fight|run away|escape enemy|retreat guide",
         "Raphael: Escape by breaking line of sight, using blocks, teleportation, speed, water, pearls, summons, or terrain. Retreat before resources hit zero, not after."
      );
      add(
         "how to farm safely|safe farming|farm without dying|grinding safely",
         "Raphael: Farm safely by choosing enemies you can kill quickly, keeping an escape route, avoiding boss areas, watching durability, and not fighting while low on food, aura, or magicules."
      );
      add(
         "how to prepare|prepare for fight|before fighting|combat preparation",
         "Raphael: Before fighting, check food, armor durability, weapon, healing, cleanse, resources, escape tools, and cooldowns. Preparation wins more fights than raw stats."
      );
      add(
         "what should i carry|what items carry|combat inventory|hotbar setup",
         "Raphael: Carry weapon, ranged option, blocks, food, healing, water, escape tool, cleanse item, and backup utility. Keep the emergency item in a slot you can press instantly."
      );
      add(
         "how to stop being one shot|getting one shot|one shot help|enemy one shots me",
         "Raphael: If you get one-shot, improve armor, resistances, barriers, Spiritual Health, HP, and mobility. Also check whether the attack bypasses armor, targets soul, or uses execution damage."
      );
      add(
         "how to fight high ep enemy|enemy high ep|fight higher ep|beat high ep",
         "Raphael: High EP enemies should be fought through counters, not raw trades. Use debuffs, terrain, resistance, burst timing, resource drain, and escape before they force a long fight."
      );
      add(
         "how to choose skill|choose skill|which ability|which skill fits me",
         "Raphael: Choose skills based on playstyle. If you die often, choose defense. If fights take too long, choose damage. If enemies escape, choose control. If you lack resources, choose sustain."
      );
      add(
         "how to get ultimate faster|rush ultimate|fast ultimate|ultimate progression",
         "Raphael: To reach Ultimate faster, master prerequisite skills, farm EP safely, evolve race, improve gear, and focus one progression path. Splitting too many builds slows progress."
      );
      add(
         "why skill wont activate|skill not activating|ability not activating|cant activate skill",
         "Raphael: A skill may not activate because of cooldown, missing resources, wrong mode, invalid target, silence, skill seal, spatial restriction, protected area, or server rule."
      );
      add(
         "why mode not working|mode not changing|cant switch mode|wrong mode",
         "Raphael: Mode issues usually mean the wrong keybind, ability preset, selected skill slot, cooldown, or client/server sync problem. Check the selected mode name before using the skill."
      );
      add(
         "how to use modes|mode guide|skill mode guide|ability mode guide",
         "Raphael: Skill modes change what activation does. Learn each mode's purpose: damage, defense, mobility, control, summon, drain, or ultimate. Use the right mode for the situation."
      );
      add(
         "simple answer|basic answer|explain simple|explain easy",
         "Raphael: Basic rule: get stronger by improving survival, EP, race, gear, skills, mastery, aura, magicules, and resistances. Upgrade the weakest part first."
      );
      add(
         "get stronger|stronger|progress faster|increase power|power up|early game|mid game|late game",
         "Raphael: To get stronger, raise EP, evolve your race, train mastery, improve gear, increase aura and magicule capacity, gain resistances, and avoid enemies that damage your Spiritual Health faster than you can recover."
      );
      add(
         "aura|ap|aura points|aura regen|aura regeneration|aura gain|physical energy|battlewill",
         "Raphael: Aura is combat energy used for physical pressure, reinforcement, some skills, dodging, and battlewill-style effects. Higher aura capacity and aura regeneration improve sustained combat."
      );
      add(
         "magicule|magicules|mp|magicule regen|magicule regeneration|magicule gain|magic energy|mana",
         "Raphael: Magicules are magical energy used by magic, barriers, transformations, and many skills. Higher magicule capacity, magicule gain, and regeneration let you cast longer and survive prolonged fights."
      );
      add(
         "spiritual health|spiritual hp|shp|soul health|spiritual damage|soul damage",
         "Raphael: Spiritual Health protects your existence beyond normal HP. High-level skills, soul damage, spiritual attacks, and boss abilities may threaten it. If it drops too low, retreat and recover."
      );
      add(
         "limited spiritual aura|limited aura|limited spiritual magicule|limited magicule|spiritual ep limit|summoning limit",
         "Raphael: Some states can limit spiritual aura and magicules. When limited, your effective maximum aura or magicules can be reduced until the limiting condition is removed."
      );
      add(
         "skill mode|mode|change mode|switch mode|ability mode|preset|selected mode",
         "Raphael: Skills with multiple modes change behavior based on the selected mode. Check the mode name before activating because one skill can have separate passives, actives, toggles, or held effects."
      );
      add(
         "cooldown|cool down|skill not working|cannot use skill|activation failed|why cant i use",
         "Raphael: A skill may fail because it is on cooldown, you lack aura or magicules, the wrong mode is selected, the target is invalid, the skill is sealed, or the ability has a special condition."
      );
      add(
         "mastery|skill mastery|mastered|master skill|mastery points|ability mastery",
         "Raphael: Skill mastery improves by using skills or meeting their progression rules. Mastery often improves cooldowns, values, effects, modes, cast time, or unlocks stronger behavior."
      );
      add(
         "learning gain|mastery gain|ability learning|learn faster|master faster",
         "Raphael: Ability learning gain and mastery gain affect how quickly skills are obtained or improved. Gear, race, attributes, and server settings may influence these values."
      );
      add(
         "learn skill|get skill|gain skill|obtain skill|new skill|how do i get skills",
         "Raphael: Skills can come from race progression, combat, tomes, evolutions, special conditions, intrinsic skills, copied skills, plunder-like effects, or server rules. Some require specific races, EP, alignment, or previous skills."
      );
      add(
         "forget skill|remove skill|unlearn skill|lost skill",
         "Raphael: Forgetting a skill can remove it from ability presets and turn off toggled states. Relearning may depend on the skill source, race, tomes, or progression rules."
      );
      add(
         "unique skill|ultimate skill|extra skill|intrinsic skill|common skill|resist skill|skill type",
         "Raphael: Common and Extra Skills are lower-order abilities. Unique Skills are stronger individual powers. Ultimate Skills have higher authority and broader effects. Intrinsic skills usually come from race or evolution."
      );
      add(
         "ultimate skill|ultimate|lord skill|god skill|angelic skill|demonic skill",
         "Raphael: Ultimate Skills are high-authority abilities. They usually have stronger effects, broader resistances, and more demanding obtain conditions than Unique Skills."
      );
      add(
         "unique skill|unique",
         "Raphael: Unique Skills are powerful personal abilities below Ultimate Skills. They can define a build and may evolve or be sacrificed into stronger forms depending on progression."
      );
      add(
         "intrinsic skill|intrinsic|race skill|racial skill",
         "Raphael: Intrinsic skills are usually tied to race or body traits. Race evolution can add or improve intrinsic abilities."
      );
      add(
         "resistance|nullification|resist|immune|immunity|negate|block effect",
         "Raphael: Resistance reduces or blocks certain damage types or effects. Nullification is stronger than resistance. Some high-authority attacks may bypass weak resistance or barriers."
      );
      add(
         "abnormal condition|nullify abnormal|abnormal nullification|poison resist|paralysis resist|petrification resist|hypnosis resist|curse resist",
         "Raphael: Abnormal Condition Nullification protects against abnormal damage and many harmful effects such as hunger, poison, blindness, confusion, darkness, weakness, burden, fragility, curse, fatal poison, paralysis, petrification, and hypnosis."
      );
      add(
         "barrier|barriers|multilayer barrier|magic barrier|physical barrier|barrier bypass",
         "Raphael: Barriers reduce or block incoming damage depending on type and strength. Some attacks have barrier bypass, so stronger enemies can pierce weak defenses."
      );
      add(
         "resistance bypass|bypass resistance|barrier bypass|dodge bypass",
         "Raphael: Some damage sources can bypass resistance, barriers, or dodging. If an attack has high bypass authority, normal defenses may not fully protect you."
      );
      add(
         "dodge|dodge strength|auto dodge|projectile dodge|melee dodge|dodge negate",
         "Raphael: Dodge mechanics can avoid attacks through dodge strength, invulnerability ticks, melee dodge chance, projectile dodge chance, or dodge negation. Strong attacks may bypass or reduce dodge effectiveness."
      );
      add(
         "chant speed|chant|casting speed|cast speed|cast time|instant cast|chant annulment",
         "Raphael: Magic has casting time. Chant speed and Chant Annulment reduce casting time. If the magic is mastered and Chant Annulment applies, casting can become nearly instant."
      );
      add(
         "magic|spell|cast magic|how to use magic|magic tome|tome|elemental magic",
         "Raphael: Magic usually requires the correct magic skill, enough magicules, and a valid cast mode. Casting speed, mastery, and Chant Annulment strongly affect how fast magic activates."
      );
      add(
         "element|elements|fire|water|wind|earth|space|light|darkness|gravity|ice|lightning",
         "Raphael: Tensura magic and damage can use elements such as earth, fire, water, wind, space, light, darkness, gravity, ice, and lightning. Elemental resistance or weakness changes how dangerous each attack is."
      );
      add(
         "slotting|slotting combination|magic combination|combine magic|stone shot|fire bolt|space cut|water ball|wind sphere",
         "Raphael: Slotting combines elemental inputs into magic projectiles or effects. Basic combinations include stone, fire, space, water, and wind-style projectiles, with stronger combinations coming from multiple elements."
      );
      add(
         "projectile|magic projectile|magic ball|shot|bolt|sphere|projectile damage",
         "Raphael: Projectiles can carry skill data, mode, aura cost, magicule cost, elements, and special effects. Some projectiles can explode, shake the screen, or apply skill-based damage."
      );
      add(
         "warp shot|homing projectile|projectile speed|tracking shot",
         "Raphael: Warp Shot-style effects can redirect or empower projectiles based on attributes and skill data. Higher values improve projectile behavior if the skill supports it."
      );
      add(
         "explosion|explode|magic explosion|blast|aoe",
         "Raphael: Some projectiles and skills create explosions. Explosion behavior can depend on the owner, griefing rules, element, skill instance, ability mode, and aura or magicule costs."
      );
      add(
         "damage type|damage types|elemental damage|aura damage|magic damage|spiritual damage",
         "Raphael: Damage can carry ability instance, ability mode, skill type, magic type, element, aura cost, magicule cost, spiritual flags, barrier bypass, resistance bypass, and dodge bypass."
      );
      add(
         "black flame|black lightning|disintegration|megiddo|dimension ray|severance|soul consumed|energy drain|devoured",
         "Raphael: Those are special Tensura damage concepts. Their danger depends on source skill, element, bypass flags, resistance, barrier strength, and whether the attack affects Spiritual Health or energy."
      );
      add(
         "energy drain|drain energy|steal energy|max ep steal|ep steal|magicule steal|aura steal",
         "Raphael: Energy drain can remove or steal aura, magicules, EP, or related resources. Some entities or conditions have energy drain immunity."
      );
      add(
         "energy drain immunity|no energy drain|immune to drain",
         "Raphael: Some targets are immune to energy drain because of entity tags, special states, infinite materials, or protected dimensions/rules."
      );
      add(
         "predator|predation|devour|devoured|eat enemy",
         "Raphael: Predator-style abilities focus on consuming enemies, resources, or skills. Exact effects depend on the specific skill page or implementation, so for exact passives and actives I will consult wiki records."
      );
      add(
         "starved|starved skill|corrosion|plunder skill|copy skill|skill copy",
         "Raphael: Some devour or corrosion-style skills can copy or plunder skills from targets under valid conditions. Targets can be protected by no-skill-plunder tags, rules, mastery, or chance checks."
      );
      add(
         "greed|mammon|lord of greed|desire",
         "Raphael: Greed-themed skills often focus on desire, stealing, scaling power, or controlling resources. For exact Mammon mechanics, I will consult the Nightmare wiki records."
      );
      add(
         "belphegor|sloth|lord of sloth",
         "Raphael: Belphegor is a sloth-themed authority. For exact passives, actives, cooldowns, and mastery effects, I will consult the Mysticism wiki records."
      );
      add(
         "spatial motion|spatial|space movement|teleport|spatial skill",
         "Raphael: Spatial Motion is a space-related movement ability. For exact mechanics, costs, cooldowns, and restrictions, I will consult Tensura wiki records."
      );
      add(
         "spatial domination|space domination|space manipulation|spacetime",
         "Raphael: Space-related authorities usually control movement, distance, teleportation, distortion, or space attacks. Exact effects depend on the skill page."
      );
      add(
         "demon lord|true demon lord|demon lord seed|harvest festival|awaken demon lord|awakening",
         "Raphael: Demon Lord progression depends on race path, EP, awakening conditions, souls or server rules, and whether you qualify as a seed. Prepare strong defenses before attempting awakening."
      );
      add(
         "hero|true hero|hero egg|chosen hero|hero awakening",
         "Raphael: Hero progression can involve spiritual requirements, alignment, element/spirit progression, and Hero Egg conditions. Certain progression can grant Eye of Truth or hero-related states."
      );
      add(
         "alignment|chaos|holy|good|evil|majin|hero alignment|demon alignment",
         "Raphael: Alignment can affect race paths, hero eligibility, demon lord progression, skill compatibility, and how some systems treat your character."
      );
      add(
         "race|best race|race path|evolve race|race evolution|evolution|next race",
         "Raphael: Race evolution depends on your current race path, EP, conditions, alignment, and server progression rules. Choose race paths that support your skill build."
      );
      add(
         "majin|monster|human|spiritual lifeform|slime|demon|vampire|dragon|oni|ogre",
         "Raphael: Race choice changes your progression, intrinsic skills, stats, and evolution path. Exact paths depend on the mod version and server settings."
      );
      add(
         "labyrinth|labyrinth death|labyrinth portal|labyrinth dimension",
         "Raphael: The Labyrinth has special death and teleport rules. Some deaths may be prevented or redirected depending on dimension, game rules, protection tags, and summon status."
      );
      add(
         "boss|boss fight|boss area|prepare boss|dwarf king|arena",
         "Raphael: Before fighting bosses, raise EP, fill aura and magicules, improve resistances, check Spiritual Health, prepare healing, and learn the boss mechanics. Some boss areas use special dimensions or structures."
      );
      add(
         "equipment|gear|weapon|armor|tool|strong gear",
         "Raphael: Equipment improves survival and damage. Use gear that supports your build, then improve it with enchants, resistances, magicule capacity, aura support, and damage modifiers."
      );
      add(
         "enchantment|engraving|engraving helper|enchants|holy coat|barrier piercing|magicule absorption|soul eater|growth|restoration",
         "Raphael: Tensura enchantments include effects like holy coat, magic interference, barrier piercing, magicule absorption, magic capacity, soul eater, growth, restoration, and many defensive or offensive engravings."
      );
      add(
         "dead end rainbow|holy weapon|magic weapon|intangibility|severance|spiritual protection|energy steal",
         "Raphael: Those are Tensura enchantment or engraving concepts. Their value depends on your build: damage, defense, energy economy, spiritual protection, or bypass effects."
      );
      add(
         "water capacity|lava capacity|dark vision|view zoom|law degradation|height multiplier|width multiplier",
         "Raphael: These are attributes or capability-style stats. They may affect special movement, storage, vision, size, law effects, or utility depending on the related skill or item."
      );
      add(
         "attribute|attributes|stat|stats|attack damage|movement speed|knockback resistance|chant speed",
         "Raphael: Attributes control combat and utility stats such as attack, movement, dodge, chant speed, aura, magicules, barrier power, size, and special capacities."
      );
      add(
         "presence sense|enemy search|sense|detection|vision",
         "Raphael: Sensing skills help detect entities, danger, sound, presence, or enemies. They are useful for exploration, combat awareness, and avoiding ambushes."
      );
      add(
         "presence concealment|stealth|hide presence|concealment",
         "Raphael: Presence Concealment-style effects reduce detection and help avoid enemy attention. Stronger enemies or sensing skills may still detect you."
      );
      add(
         "instant regeneration|self regeneration|regeneration|heal|healing",
         "Raphael: Regeneration skills improve survival by restoring health or preventing death under certain conditions. Strong enemies may bypass weak healing with spiritual or special damage."
      );
      add(
         "anti skill|anti magic|anti shock|silence|magic interference|energy blockade",
         "Raphael: Anti-skill, anti-magic, silence, magic interference, and energy blockade effects can prevent or weaken abilities. Remove or resist these before relying on skills."
      );
      add(
         "fear|insanity|hypnosis|mind control|sleep|drowsiness|oppression|burden|webbed|spatial blockade",
         "Raphael: These are disabling effects. Resistances, nullifications, cleanse effects, or strong skill authority help prevent control, movement loss, or ability disruption."
      );
      add(
         "fatal poison|poison|curse|fragility|petrification|paralysis|corrosion|black burn|disintegrating",
         "Raphael: These are dangerous debuffs. Fatal poison, curse, corrosion, petrification, paralysis, and disintegration can quickly end fights if you lack resistance or cleansing."
      );
      add(
         "magicule poison|majin chance|become majin",
         "Raphael: Magicule Poison can interact with majin progression. Certain deaths or damage sources may trigger majin chance depending on rules and source."
      );
      add(
         "summon|subordinate|summoned mob|revive summon|summoning",
         "Raphael: Summons and subordinates can have special EP, ownership, death, and energy rules. Summoned mobs may not give normal EP and may be limited by spiritual resource rules."
      );
      add(
         "subordinate owner|tame|owner|permanent owner",
         "Raphael: Subordinate ownership tracks who controls or owns a creature. Death, storage, and skill systems may clear or update ownership."
      );
      add(
         "skill grief|griefing|block damage|destroy blocks",
         "Raphael: Some skills or explosions can affect blocks, but skill grief events and game rules may block or allow terrain damage."
      );
      add(
         "server rule|gamerule|config|settings|server settings",
         "Raphael: Many Tensura mechanics depend on server rules or config, including EP gain, player targeting, labyrinth behavior, griefing, and progression requirements."
      );
      add(
         "why no ep|no ep|not gaining ep|ep not increasing",
         "Raphael: EP may not increase if the target is excluded, summoned, triggered, spawner-limited, protected by rules, already drained, or not valid for EP gain."
      );
      add(
         "why no skill|didnt learn skill|skill not learned|plunder failed|copy failed",
         "Raphael: Skill learning or plunder can fail if the target is protected, the skill is temporary, chance fails, requirements are missing, events deny it, or the skill is excluded from plunder."
      );
      add(
         "what should i do next|advice|recommendation|guide me|progression help",
         "Raphael: Recommended path: stabilize resources first, then raise EP, get defensive resistances, improve gear, train mastery, unlock better race options, then challenge bosses or higher progression systems."
      );
      add(
         "best build|build|magic build|melee build|tank build|speed build",
         "Raphael: A strong build needs a clear focus. Magic builds need magicules and chant speed. Melee builds need aura, attack, dodge, and durability. Tank builds need resistances, barriers, regeneration, and Spiritual Health protection."
      );
      add(
         "raphael|ciel|great sage|wise one|analysis|analyze",
         "Raphael: Analysis-type skills should identify information, explain mechanics, and guide progression. Exact ability effects depend on the skill implementation or wiki page."
      );
      add(
         "michael|uriel|metatron|beelzebub|azathoth|yog sothoth|shub niggurath|lucifer|satanael|asmodeus|leviathan",
         "Raphael: This appears to be an Ultimate Skill or authority name. I will need wiki records for exact passives, actives, costs, and obtain requirements."
      );
      add(
         "raphael|great sage|wise one|analysis|analytical mind|analyze|appraisal|assess|information skill",
         "Raphael: Analysis-type skills are used to identify mechanics, explain progression, detect threats, and guide decisions. Exact skill effects depend on the specific skill page or implementation."
      );
      add(
         "ciel|manas|manas ciel|manas core|core assistant",
         "Raphael: A Manas-type assistant represents a higher-order will or analytical entity. Exact behavior depends on the mod or addon implementation, so I will consult wiki records for exact skill data."
      );
      add(
         "skill acquisition|acquire skill|skill acquired|learned skill|skill learning chance|skill chance",
         "Raphael: Skill acquisition can depend on race, EP, previous skills, chance, combat events, special requirements, tomes, or evolution. If a skill fails to appear, requirements or server rules may be missing."
      );
      add(
         "skill evolution|evolve skill|unique to ultimate|upgrade skill|skill upgrade|sacrifice skill",
         "Raphael: Some skills can evolve into stronger forms when conditions are met. Evolution may consume previous skills, require EP or mastery, or be tied to race and progression systems."
      );
      add(
         "temporary skill|temp skill|temporary ability|borrowed skill|copied skill",
         "Raphael: Temporary or copied skills may not persist forever. They can be removed on death, logout, timer expiry, skill reset, or when the source condition ends."
      );
      add(
         "skill storage|skill slot|ability slot|preset slot|skill preset|ability preset",
         "Raphael: Ability presets determine which skills or modes are currently accessible. If a skill exists but will not activate, check whether it is slotted, selected, toggled, or blocked by cooldown/resources."
      );
      add(
         "toggle skill|toggled skill|toggle passive|turn on skill|turn off skill",
         "Raphael: Toggle skills remain active while enabled and may consume resources, apply effects, or modify attributes. If resources are low or conditions fail, toggles may deactivate."
      );
      add(
         "held skill|hold skill|charge skill|charging|hold ability",
         "Raphael: Held skills usually build power while the key is held, then release an effect. They may consume aura or magicules over time and can fail if interrupted."
      );
      add(
         "passive skill|passive effect|always active|on learn passive",
         "Raphael: Passive effects can apply automatically when the skill is learned, equipped, toggled, or mastered. Exact trigger depends on the skill type."
      );
      add(
         "active skill|activate skill|press skill|use ability|fire ability",
         "Raphael: Active skills trigger when used. They may require a target, selected mode, enough aura or magicules, line of sight, cooldown availability, and valid world conditions."
      );
      add(
         "cost|skill cost|aura cost|magicule cost|resource cost|pay cost",
         "Raphael: Skill costs can consume aura, magicules, HP, Spiritual Health, or special resources. If activation fails, check current resources and whether the skill requires a specific target."
      );
      add(
         "cooldown reduction|lower cooldown|reduce cooldown|cooldown mastery",
         "Raphael: Cooldowns may decrease with mastery, attributes, stronger skill versions, or config rules. Some powerful abilities keep fixed cooldowns even after mastery."
      );
      add(
         "ep cost|obtain cost|skill cost mp|points to master|mastery points",
         "Raphael: Many skills have an obtain cost and points required to master. Obtain cost affects acquisition, while mastery points control how much use or progression is needed to master the skill."
      );
      add(
         "spiritual attack|spiritual damage|soul attack|soul strike|soul consume",
         "Raphael: Spiritual attacks threaten the target beyond normal HP. They can affect Spiritual Health, soul resources, or existence-level defenses."
      );
      add(
         "normal hp|health|hearts|hp damage|physical hp",
         "Raphael: Normal HP is your basic health pool. High-level enemies can still kill through normal HP even if your EP is high, especially if you lack armor, resistance, or regeneration."
      );
      add(
         "death|died|death penalty|what happens when i die|respawn",
         "Raphael: Death behavior depends on dimension, race, skills, gamerules, and special protections. Some skills can prevent death, revive summons, preserve data, or redirect death effects."
      );
      add(
         "death prevention|survive lethal|lethal damage|death protection|undying",
         "Raphael: Death prevention skills can intercept lethal damage and leave you alive with partial HP or spiritual resources. They usually have strict cooldowns or resource costs."
      );
      add(
         "revive|resurrection|resurrect|come back to life|revival",
         "Raphael: Revival effects may restore the user or summons after death. Costs can increase with repeated revives, and some damage types may bypass revival."
      );
      add(
         "summon revive|subordinate revive|revive servant|revive minion",
         "Raphael: Summon revival can bring back dead active summons if the owner has enough resources. Some systems increase revive cost each time that summon is revived."
      );
      add(
         "true dragon|dragon factor|dragon|veldora|velgrynd|velzard",
         "Raphael: True Dragon-related progression is extremely high-level. Exact mechanics depend on the mod version or addon, so I will need wiki records for exact requirements."
      );
      add(
         "demon|greater demon|arch demon|demon peer|primordial demon",
         "Raphael: Demon progression usually focuses on magicules, spiritual strength, alignment, and evolution. Higher demon stages are dangerous but can provide strong magic and skill growth."
      );
      add(
         "vampire|true vampire|vampire lord|blood|night",
         "Raphael: Vampire paths often emphasize regeneration, blood, darkness, and survival. Exact race progression depends on the wiki or mod version."
      );
      add(
         "slime|slime race|rimuru|slime evolution",
         "Raphael: Slime progression often supports absorption, resistance, and adaptive growth. Exact evolution route depends on race conditions and server settings."
      );
      add(
         "oni|ogre|kijin|fair oni|wicked oni",
         "Raphael: Oni and ogre-style paths usually focus on physical combat, aura, durability, and race evolutions. Exact route depends on requirements."
      );
      add(
         "human|otherworlder|summoned human|hero path",
         "Raphael: Human and Otherworlder paths may support Hero progression, skills, and flexible builds. Exact mechanics depend on starting conditions and server rules."
      );
      add(
         "spirit|elemental spirit|spirit contract|spirit integration|spirit summon",
         "Raphael: Spirit systems often involve elemental affinity, contracts, and hero-related progression. Exact effects depend on the spirit type and wiki records."
      );
      add(
         "angel|angelic skill|virtue skill|angel series|angelic system",
         "Raphael: Angelic or virtue-themed skills usually have high authority and strong defensive or conceptual effects. Exact mechanics require wiki records."
      );
      add(
         "sin skill|demonic skill|sin series|demon lord skill|deadly sin",
         "Raphael: Sin-themed skills usually represent strong authorities such as greed, sloth, gluttony, wrath, pride, lust, or envy. Exact passives and actives depend on the skill page."
      );
      add(
         "wrath|satanael|rage|anger",
         "Raphael: Wrath-themed abilities usually focus on rage, damage, punishment, or destructive scaling. Exact mechanics require wiki records."
      );
      add(
         "pride|lucifer|arrogance|domination",
         "Raphael: Pride-themed abilities usually focus on domination, superiority, or authority-based effects. Exact mechanics require wiki records."
      );
      add(
         "envy|leviathan|jealousy",
         "Raphael: Envy-themed abilities usually focus on copying, stealing, rivalry, or debuffs. Exact mechanics require wiki records."
      );
      add(
         "lust|asmodeus|charm|control",
         "Raphael: Lust-themed abilities may involve charm, control, desire, or mental interference. Exact mechanics require wiki records."
      );
      add(
         "gluttony|beelzebub|devour|stomach|isolation|food chain",
         "Raphael: Gluttony and Beelzebub-style abilities usually focus on devouring, storing, analyzing, isolating, or converting targets and skills. Exact mechanics require wiki records."
      );
      add(
         "wisdom|raphael lord of wisdom|lord of wisdom|analysis lord",
         "Raphael: Wisdom-themed skills usually enhance analysis, prediction, processing, optimization, and guidance. Exact effects depend on the skill page."
      );
      add(
         "vows|uriel|lord of vows|absolute defense|universal barrier",
         "Raphael: Uriel-style authority is commonly associated with vows, defense, barriers, and spatial control. Exact effects require wiki records."
      );
      add(
         "justice|michael|lord of justice|dominion|regalia dominion",
         "Raphael: Michael-style authority is commonly associated with justice, control, angelic authority, and domination. Exact effects require wiki records."
      );
      add(
         "purity|metatron|lord of purity|holy attack|holy magic",
         "Raphael: Metatron-style authority is usually associated with holy power, purity, and high-level light attacks. Exact mechanics require wiki records."
      );
      add(
         "void|azathoth|god of void|nihility|turn null",
         "Raphael: Void-themed authority is extremely high-level and commonly linked to null, void, storage, or ultimate destruction. Exact mechanics require wiki records."
      );
      add(
         "time|time stop|time control|time manipulation|temporal",
         "Raphael: Time-related abilities can affect speed, cooldowns, movement, projectiles, or action flow. Exact mechanics vary greatly and require wiki or skill records."
      );
      add(
         "space|spatial|teleport|warp|spatial travel|dimension",
         "Raphael: Space-related abilities affect movement, distance, teleportation, dimensional travel, or spatial attacks. Exact mechanics require wiki records."
      );
      add(
         "gravity|gravity magic|black hole|weight",
         "Raphael: Gravity abilities can control movement, pressure, attraction, knockback, and area damage. Exact mechanics depend on the skill or magic page."
      );
      add(
         "light|holy|holy attack|holy magic|purification",
         "Raphael: Light and holy effects can damage undead/demonic targets, cleanse corruption, or bypass certain defenses depending on skill authority."
      );
      add(
         "darkness|shadow|dark magic|shadow movement",
         "Raphael: Darkness effects can support stealth, damage, debuffs, or mobility. Exact results depend on the skill or magic page."
      );
      add(
         "fire|flame|burn|black flame|heat",
         "Raphael: Fire abilities deal heat or burn damage. Stronger fire skills may inflict special burns, black flame, or spiritual effects."
      );
      add(
         "water|water blade|water manipulation|water magic",
         "Raphael: Water abilities can create projectiles, control flow, support mobility, or combine with other elements. Exact mechanics depend on the magic system."
      );
      add(
         "wind|wind blade|air|wind sphere|flight",
         "Raphael: Wind abilities support projectiles, movement, cutting attacks, and area control. Exact mechanics depend on magic combination and mastery."
      );
      add(
         "earth|stone|rock|earth magic|stone shot", "Raphael: Earth abilities support defense, stone projectiles, terrain control, and physical reinforcement."
      );
      add(
         "ice|freeze|freezing|ice magic",
         "Raphael: Ice abilities can slow, freeze, damage, or control enemies. Resistance and nullification can reduce or prevent freezing effects."
      );
      add(
         "lightning|thunder|electric|electricity",
         "Raphael: Lightning abilities usually deal fast elemental damage and may stun or pierce depending on implementation."
      );
      add(
         "law manipulation|law|law domination|law degradation",
         "Raphael: Law-related abilities interact with high-level rules or conceptual effects. Exact mechanics require skill records because they vary widely."
      );
      add(
         "thought acceleration|accelerate thought|speed thinking|mental speed",
         "Raphael: Thought Acceleration improves mental processing and can support casting, reaction, analysis, or combat control. Exact multipliers depend on the skill."
      );
      add(
         "parallel processing|multi processing|parallel thought",
         "Raphael: Parallel Processing allows multiple mental tasks at once, supporting analysis, casting, and complex skill use."
      );
      add(
         "chant annulment|no chant|silent casting|chantless",
         "Raphael: Chant Annulment removes or reduces the need for chanting. With mastery and sufficient resources, magic can become much faster."
      );
      add(
         "all of creation|analyze and assess|analysis assess",
         "Raphael: All of Creation-style abilities improve analysis of phenomena, items, enemies, and skills. Exact scope depends on the skill."
      );
      add(
         "future attack prediction|predict attack|prediction|danger sense",
         "Raphael: Prediction abilities can warn about attacks or guide dodging. Stronger enemies, hidden attacks, or authority-based skills may bypass prediction."
      );
      add(
         "magic sense|magic perception|sense magicules|detect magic",
         "Raphael: Magic Sense detects magicules and helps perceive surroundings, enemies, and attacks. It is valuable for both combat and exploration."
      );
      add(
         "universal sense|presence sense|sound sense|detect enemy",
         "Raphael: Sensing skills expand awareness through presence, sound, magicules, or universal perception. Strong concealment may still hide enemies."
      );
      add(
         "ultraspeed regeneration|ultra speed regeneration|instant regeneration|self regeneration",
         "Raphael: High-speed regeneration restores HP quickly. It may not save you from spiritual damage, instant death, or attacks that bypass regeneration."
      );
      add(
         "pain nullification|pain resistance|pain",
         "Raphael: Pain Nullification or resistance reduces pain effects and prevents combat disruption from damage reactions."
      );
      add(
         "thermal fluctuation resistance|temperature resistance|heat resistance|cold resistance",
         "Raphael: Thermal resistance protects against heat and cold effects. Strong elemental attacks may still require higher resistances."
      );
      add(
         "physical attack resistance|physical nullification|weapon resistance",
         "Raphael: Physical resistance reduces normal physical attacks. Barrier bypass, spiritual damage, or high-authority attacks may still deal damage."
      );
      add(
         "natural effects resistance|natural effect nullification|environment resistance",
         "Raphael: Natural effect resistance helps protect against environmental hazards and natural damage sources."
      );
      add(
         "spiritual attack resistance|soul resistance|spiritual nullification",
         "Raphael: Spiritual attack resistance protects Spiritual Health and soul-level existence. It is critical against high-level enemies and Ultimate Skill effects."
      );
      add(
         "mental attack resistance|mental attack nullification|mind attack resistance",
         "Raphael: Mental resistance protects against hypnosis, insanity, fear, mind control, charm, and other control effects."
      );
      add(
         "holy demonic attack resistance|holy attack resistance|demonic attack resistance",
         "Raphael: Holy and demonic attack resistances protect against opposing spiritual forces. Builds facing angels or demons should value these defenses."
      );
      add(
         "status effect|mob effect|effect|debuff|buff",
         "Raphael: Status effects can buff or debuff combat. Stronger skills may apply effects like fear, burden, fragility, paralysis, corrosion, curse, or regeneration."
      );
      add(
         "burden|fragility|movement interference|slow|slowness|webbed",
         "Raphael: Movement and burden effects reduce mobility or combat responsiveness. Remove them quickly in boss fights."
      );
      add(
         "fear|insanity|mind control|hypnosis|sleep|drowsiness",
         "Raphael: Mental control effects can disable or redirect the player. Mental resistance, abnormal nullification, or cleanse effects are important protection."
      );
      add(
         "petrification|paralysis|stun|immobilize",
         "Raphael: Immobilizing effects can leave you unable to dodge or act. Resistance and cleansing are essential against enemies that chain-control."
      );
      add(
         "corrosion|organ decay|life seizure|decay|rot",
         "Raphael: Decay or corrosion effects can deal dangerous damage over time. These are often more threatening than normal poison."
      );
      add(
         "fatal poison|poison|venom|toxin",
         "Raphael: Poison damages over time, while fatal poison is much more dangerous. Abnormal condition resistance or nullification can prevent it."
      );
      add(
         "curse|cursed|hex",
         "Raphael: Curse effects may weaken, damage, or interfere with recovery and skill use. Remove or resist curses before prolonged combat."
      );
      add(
         "cleanse|remove effect|clear debuff|purify",
         "Raphael: Cleansing removes harmful effects. Some high-authority debuffs may resist cleansing unless the cleanse effect is strong enough."
      );
      add(
         "boss arena|arena|structure|spawn boss|boss spawn",
         "Raphael: Bosses may require structures, dimensions, triggers, or specific progression. Prepare resources and escape options before starting a boss event."
      );
      add(
         "dwarf king|orc disaster|charybdis|hinata|clayman|milim|rimuru|veldora",
         "Raphael: This appears to be a boss or character. Exact mechanics, drops, and strategy require wiki records because implementations vary by version."
      );
      add(
         "drop|loot|drops|rare drop|item drop",
         "Raphael: Drops depend on enemy type, loot tables, server settings, and sometimes progression. If an item does not drop, check whether the enemy is valid and not summoned or excluded."
      );
      add(
         "item|material|ore|ingot|gem|crystal|core",
         "Raphael: Items and materials are used for crafting, equipment, magic, progression, or skill systems. Exact recipes require JEI/REI or wiki records."
      );
      add(
         "recipe|craft|crafting|how to craft|make item",
         "Raphael: Recipes depend on the modpack and datapacks. Use the in-game recipe viewer first, then consult wiki records for progression-only items."
      );
      add(
         "dimension|world|biome|where find|where is|location",
         "Raphael: Locations depend on dimensions, structures, biomes, and server settings. Exact spawn locations should be checked through wiki records or map exploration."
      );
      add(
         "training|train|practice|level up|grind",
         "Raphael: Train by fighting valid enemies, using skills, improving mastery, and upgrading gear. Avoid enemies that outscale your Spiritual Health protection."
      );
      add(
         "party|team|guild|ally|friendly fire",
         "Raphael: Team systems and friendly fire rules can affect targeting. Some skills may ignore allies while others require careful aim."
      );
      add(
         "pvp|player fight|fight players|duel",
         "Raphael: PvP depends heavily on server rules. Resistance, mobility, burst damage, cooldown management, and Spiritual Health protection are more important than raw EP alone."
      );
      add(
         "why did i get one shot|oneshot|instant death|too much damage",
         "Raphael: You may be one-shot by spiritual damage, bypass attacks, low resistance, barrier failure, weak armor, low Spiritual Health, or enemies far above your EP."
      );
      add(
         "why did my projectile stop|projectile stopped|projectile blocked|projectile disappeared",
         "Raphael: Projectiles can be stopped by barriers, world collision, owner checks, range limits, timeouts, resistance, or special anti-projectile effects."
      );
      add(
         "why cant i teleport|teleport failed|spatial failed|blocked teleport",
         "Raphael: Teleportation can fail because of spatial blockade, invalid destination, dimension restrictions, insufficient resources, cooldown, or protected areas."
      );
      add(
         "why cant i cast|cast failed|magic failed|spell failed",
         "Raphael: Casting can fail if you lack magicules, are silenced, have magic interference, lack the correct magic skill, are on cooldown, or selected the wrong mode."
      );
      add(
         "why am i slow|movement slow|cant move|stuck",
         "Raphael: Movement problems can come from burden, webbed, spatial blockade, gravity, paralysis, terrain, armor penalties, or low resources."
      );
      add(
         "why am i weak|weakness|low damage|damage low",
         "Raphael: Low damage can come from enemy resistance, armor, barrier, low aura, weak weapon, wrong element, low mastery, or attacking a target far above your power."
      );
      add(
         "why no damage|immune enemy|enemy immune|cant damage",
         "Raphael: If an enemy takes no damage, it may have immunity, resistance, barrier, invulnerability frames, protected tags, wrong damage type, or boss phase protection."
      );
      add(
         "spatial motion exact|spatial motion cost|spatial motion cooldown|spatial motion requirement|how to get spatial motion",
         "Raphael: Spatial Motion is specific enough that exact cost, cooldown, obtain conditions, and usage should come from wiki records. I will consult Tensura wiki context for the exact page."
      );
      add(
         "mammon exact|mammon passive|mammon active|mammon cost|mammon cooldown|mammon mastery|mammon obtain|how to get mammon",
         "Raphael: Mammon is a specific Nightmare skill. Exact passives, actives, mastery effects, costs, and obtain conditions should come from the Nightmare wiki records."
      );
      add(
         "belphegor exact|belphegor passive|belphegor active|belphegor cost|belphegor cooldown|belphegor mastery|belphegor obtain|how to get belphegor",
         "Raphael: Belphegor is a specific Mysticism skill. Exact passives, actives, mastery effects, costs, and obtain conditions should come from the Mysticism wiki records."
      );
      add(
         "predator exact|predator passive|predator active|predator cost|predator cooldown|predator obtain|how to get predator",
         "Raphael: Predator is a specific skill. Exact devour behavior, obtain conditions, and skill interactions should come from wiki records, because different versions can change details."
      );
      add(
         "snatch exact|snatch passive|snatch active|snatch cost|snatch cooldown|snatch obtain|what does snatch do",
         "Raphael: Snatch is specific enough that exact mechanics should be read from wiki records. If wiki context is found, I will use that instead of guessing."
      );
      add(
         "spatial travel|spatial movement|teleport movement|move through space|instant movement",
         "Raphael: Spatial movement skills usually allow teleportation, short-range repositioning, or space-based travel. Exact range, cooldown, and restrictions depend on the skill page."
      );
      add(
         "spatial blockade|space blockade|teleport block|blocked space|cant teleport",
         "Raphael: Spatial Blockade prevents or interferes with teleportation and space movement. If you cannot teleport, check for blockade effects, protected areas, resource costs, and cooldowns."
      );
      add(
         "space cut|spatial cut|spatial slash|cut space|dimension slash",
         "Raphael: Space-cutting attacks usually bypass normal distance or defense logic more than basic physical attacks. Exact damage and bypass behavior depends on the skill or magic page."
      );
      add(
         "spacetime domination|space time domination|time space domination",
         "Raphael: Spacetime authority generally affects space, movement, distance, time flow, or dimensional control. Exact mechanics require wiki records because implementations vary heavily."
      );
      add(
         "time stop|stopped time|time stopped|move in time stop|time stop resistance",
         "Raphael: Time Stop is a high-level effect that can freeze actions or movement. Only certain high-authority skills, resistances, or conditions may allow movement during stopped time."
      );
      add(
         "time acceleration|speed up time|slow time|temporal acceleration|temporal slow",
         "Raphael: Time acceleration and slowing effects can alter movement, cooldown feel, projectiles, or enemy action speed. Exact mechanics depend on the skill implementation."
      );
      add(
         "projectile stop|stop projectiles|infinity|projectiles freeze|projectile barrier",
         "Raphael: Projectile-stopping effects can freeze, slow, redirect, or delete incoming projectiles. They usually check range, owner, projectile type, and whether the attack bypasses barriers."
      );
      add(
         "anti projectile|projectile immunity|arrow immunity|magic projectile immunity",
         "Raphael: Anti-projectile defenses protect against arrows, magic bolts, and other projectile entities. Some high-authority projectiles may bypass normal projectile protection."
      );
      add(
         "analysis failed|cannot analyze|unknown target|analysis blocked",
         "Raphael: Analysis can fail if the target has concealment, higher authority, insufficient data, a blocked skill, unknown addon mechanics, or wiki records are unavailable."
      );
      add(
         "information insufficient|not enough records|no records|records unavailable",
         "Raphael: If records are unavailable, I cannot safely give exact mechanics. Try asking with the exact skill name, addon name, or page title so wiki search can find the right record."
      );
      add(
         "wrong answer|random answer|hallucination|made up|dont guess",
         "Raphael: For exact mechanics, I should answer only from local records or wiki context. If no records are found, the safe answer is that I do not have enough Tensura records."
      );
      add(
         "wiki search|wiki records|search wiki|check wiki|use wiki",
         "Raphael: Wiki records are used for exact skill pages, addon skills, costs, cooldowns, passives, actives, and obtain requirements. Local answers are only safe for broad mechanics."
      );
      add(
         "nightmare wiki|tensura nightmares|nightmares addon|nightmare skill",
         "Raphael: Tensura Nightmares content should be answered from the Nightmares wiki records when exact skills, passives, actives, or obtain conditions are requested."
      );
      add(
         "mysticism wiki|tr mysticism|mysticism addon|mysticism skill",
         "Raphael: Mysticism addon content should be answered from Mysticism wiki records when exact skill mechanics, costs, passives, actives, or progression are requested."
      );
      add(
         "main tensura wiki|tensura wiki|reincarnated wiki|base mod wiki",
         "Raphael: Base Tensura mechanics should be checked against the main Tensura wiki when exact values, requirements, or skill descriptions are needed."
      );
      add(
         "demon lord seed requirement|how get demon lord seed|become demon lord seed",
         "Raphael: Demon Lord Seed progression usually depends on race path, EP, souls or server rules, and progression conditions. Exact requirements should come from wiki or server config."
      );
      add(
         "true demon lord requirement|awaken true demon lord|harvest festival requirement",
         "Raphael: True Demon Lord awakening is high-level progression. It may require souls, EP, Demon Lord Seed status, race conditions, and server rules. Exact requirements should be checked from records."
      );
      add(
         "hero egg requirement|how get hero egg|become hero|true hero requirement",
         "Raphael: Hero progression can require alignment, spiritual conditions, race path, Hero Egg status, and special events. Exact requirements should come from wiki records."
      );
      add(
         "chosen hero|hero awakening|awaken hero|hero progression",
         "Raphael: Hero awakening is a major progression route. Prepare strong defenses and check exact requirements in records, because it can differ between versions and servers."
      );
      add(
         "majin evolution|become majin|how to become majin|majin path",
         "Raphael: Majin progression can involve magicules, alignment, race state, and special triggers. Exact conversion rules depend on the mod version and server settings."
      );
      add(
         "monster evolution|monster race|evolve monster|monster path",
         "Raphael: Monster races often evolve through EP and race-specific conditions. Their paths usually improve intrinsic skills, stats, aura, magicules, or resistances."
      );
      add(
         "human evolution|otherworlder progression|summoned human progression",
         "Raphael: Human or Otherworlder progression may focus on flexible skills, Hero paths, equipment, and special acquired abilities rather than monster race evolution."
      );
      add(
         "demon evolution|lesser demon|greater demon|arch demon|demon peer",
         "Raphael: Demon evolution generally improves magicules, spiritual strength, resistances, and magic potential. Exact stages and requirements need wiki or server records."
      );
      add(
         "vampire evolution|vampire lord|true vampire|blood evolution",
         "Raphael: Vampire progression often involves blood, regeneration, darkness, magicules, and race evolution. Exact requirements depend on the wiki or server settings."
      );
      add(
         "oni evolution|ogre evolution|kijin evolution|fair oni evolution",
         "Raphael: Ogre and Oni paths often favor physical combat, aura, durability, and strong racial skills. Exact evolution names and requirements depend on records."
      );
      add(
         "slime evolution|slime path|strong slime|how to evolve slime",
         "Raphael: Slime progression usually favors adaptation, absorption, resistance, and utility. Exact evolution route depends on race conditions and version."
      );
      add(
         "dragon evolution|dragon path|dragon race|true dragon path",
         "Raphael: Dragon progression is high-level and often tied to massive EP, magicules, aura, and strong intrinsic abilities. Exact route needs wiki records."
      );
      add(
         "spirit evolution|spirit path|elemental path|spirit race",
         "Raphael: Spirit progression usually depends on elemental affinity, magicules, spiritual strength, and contracts. Exact mechanics need wiki records."
      );
      add(
         "orc evolution|orc lord|orc disaster|orc path",
         "Raphael: Orc progression can involve appetite, army scaling, and evolution into stronger forms. Exact mechanics depend on records and version."
      );
      add(
         "goblin evolution|hobgoblin|goblin lord|goblin path",
         "Raphael: Goblin progression usually begins weaker but can evolve into stronger humanoid forms. Exact route depends on race and server progression."
      );
      add(
         "lizardman evolution|dragonewt|lizard path",
         "Raphael: Lizardman and Dragonewt-style paths usually mix physical combat, water affinity, and racial evolution. Exact requirements need records."
      );
      add(
         "spider evolution|arachnid|insectar|insect race",
         "Raphael: Spider or insect-style races often emphasize mobility, poison, webs, armor, or special senses. Exact race mechanics need records."
      );
      add(
         "undead evolution|zombie|skeleton|death path|undead race",
         "Raphael: Undead paths often interact with darkness, regeneration, holy weakness, and spiritual effects. Exact mechanics depend on version and addon."
      );
      add(
         "angel evolution|angel race|seraph|angel path",
         "Raphael: Angelic progression usually involves holy power, light, virtue skills, and high authority. Exact mechanics require records."
      );
      add(
         "fairy evolution|pixie|spirit fairy|fairy path",
         "Raphael: Fairy-style paths usually lean toward magic, movement, and elemental affinity. Exact progression needs records."
      );
      add(
         "what is battlewill|battlewill|willpower|fighting spirit",
         "Raphael: Battlewill is a combat-force concept tied to will, pressure, and reinforcement. It can support physical combat and aura-based effects depending on implementation."
      );
      add(
         "what is existence|existence level|existence value|existence strength",
         "Raphael: Existence represents more than normal stats. It reflects spiritual weight, race state, energy, skills, and overall being. EP is a rough number for that existence."
      );
      add(
         "what is soul|soul system|soul count|souls",
         "Raphael: Souls can be used in high-level progression such as Demon Lord awakening or special skills. Exact use depends on server rules and records."
      );
      add(
         "soul consume|consume souls|soul harvest|soul collection",
         "Raphael: Soul collection may be tied to kills, progression, or awakening. Some servers change soul requirements or restrict which kills count."
      );
      add(
         "name monster|naming|named monster|give name|name cost",
         "Raphael: Naming can strengthen monsters or subordinates but may cost magicules or resources. Exact effects depend on the naming system and server settings."
      );
      add(
         "subordinate evolution|subordinate naming|named subordinate|evolve subordinate",
         "Raphael: Subordinates can grow through naming, combat, race progression, or owner effects. Exact evolution depends on race, EP, and server rules."
      );
      add(
         "party ep|shared ep|subordinate ep|summon ep",
         "Raphael: EP sharing or subordinate EP rules depend on server configuration. Summoned or owned entities may be excluded from normal EP gain."
      );
      add(
         "friendly fire|ally damage|hit allies|team damage",
         "Raphael: Friendly fire depends on skill targeting logic, party systems, and server rules. Some skills avoid allies, while others can damage everything in range."
      );
      add(
         "safe zone|protected area|claim|spawn protection|skill blocked area",
         "Raphael: Protected areas can block skill activation, explosions, teleportation, griefing, or PvP. If a skill fails in one location, test outside protected zones."
      );
      add(
         "griefing rule|skill grief|explosion grief|block break skill",
         "Raphael: Skill grief rules decide whether abilities can break or change blocks. Server settings often disable destructive skill effects."
      );
      add(
         "mob griefing|tensura griefing|destroy terrain",
         "Raphael: Terrain damage depends on Minecraft mob griefing, Tensura-specific gamerules, and event permissions. Explosions may still deal entity damage even if blocks are protected."
      );
      add(
         "dimension blocked|wrong dimension|cant use in dimension|dimension restriction",
         "Raphael: Some skills cannot activate in certain dimensions, boss arenas, labyrinths, or protected worlds. Teleport and spatial skills are often dimension-restricted."
      );
      add(
         "labyrinth death rule|labyrinth respawn|death in labyrinth",
         "Raphael: Labyrinth death can have special rules. Some systems prevent normal death, relocate the player, or preserve certain data depending on dimension and settings."
      );
      add(
         "labyrinth boss|labyrinth floor|labyrinth room|labyrinth portal",
         "Raphael: Labyrinth content can have special progression, portals, bosses, and death handling. Exact mechanics require records or server configuration."
      );
      add(
         "magic circle|casting circle|magic circle color|magic circle variant",
         "Raphael: Magic circles are visual and mechanical markers for casting or skills. Variant and color often depend on skill type, element, or magic category."
      );
      add(
         "particle effect|screen effect|shader|camera shake|visual effect",
         "Raphael: Visual effects such as particles, shaders, camera shake, or screen tint usually indicate strong skills, debuffs, casting states, or ultimate attacks."
      );
      add(
         "gray screen|screen gray|colors gone|screen effect time",
         "Raphael: A gray or color-drained screen usually indicates a powerful temporal, spiritual, or mental effect. Exact meaning depends on the skill applying it."
      );
      add(
         "stunned|cant move after hit|frozen in place|action locked",
         "Raphael: Stun or action-lock effects can come from paralysis, fear, time, gravity, spatial blockade, or strong boss skills. Resistances and cleansing help."
      );
      add(
         "knockback|knockback resistance|no knockback|too much knockback",
         "Raphael: Knockback resistance reduces displacement from attacks. High knockback enemies or explosions can still move you if resistance is low."
      );
      add(
         "attack speed|swing speed|slow attack|fast attack",
         "Raphael: Attack speed affects melee combat rate. Skills, attributes, equipment, and debuffs can raise or lower it."
      );
      add(
         "movement speed|run speed|speed boost|slow movement",
         "Raphael: Movement speed affects repositioning, dodging, and chasing. It can be modified by skills, race, gear, burden, webs, gravity, or paralysis."
      );
      add(
         "attack damage|melee damage|physical damage|weapon damage",
         "Raphael: Attack damage is only one part of real damage. Enemy armor, resistance, aura, barriers, bypass flags, and skill scaling also matter."
      );
      add(
         "magic damage|spell damage|magic scaling|increase magic damage",
         "Raphael: Magic damage depends on magicules, skill values, element, mastery, target resistance, barriers, and bypass rules."
      );
      add(
         "aura damage|battlewill damage|physical reinforcement damage",
         "Raphael: Aura-based damage scales with combat energy, reinforcement, and relevant skills. It can be reduced by physical or aura defenses."
      );
      add(
         "true damage|ignore armor|bypass armor|unblockable",
         "Raphael: Some attacks bypass armor, barriers, dodge, or resistances. If an attack feels unblockable, check whether it has bypass flags or high authority."
      );
      add(
         "barrier strength|strong barrier|barrier durability|break barrier",
         "Raphael: Barrier strength determines how much it can block. Strong attacks, bypass effects, or repeated hits can break or ignore weak barriers."
      );
      add(
         "multi layer barrier|multilayer barrier|barrier layers",
         "Raphael: Multilayer barriers stack defensive layers. They are stronger than simple barriers but may still fail against high-authority or bypass attacks."
      );
      add(
         "universal barrier|absolute defense|absolute barrier",
         "Raphael: Universal or absolute defenses are high-level defensive concepts. Exact protection depends on skill authority and whether the attack bypasses it."
      );
      add(
         "magic interference|anti magic area|cant use magic|magic blocked",
         "Raphael: Magic Interference can weaken or prevent magic. Use resistance, higher authority, or leave the interference area before casting."
      );
      add(
         "skill seal|sealed skill|ability sealed|cant use abilities",
         "Raphael: Skill sealing prevents activation or disables abilities. Remove the seal or overpower it with stronger authority if the mod allows that."
      );
      add(
         "silence|silenced|cant chant|no casting",
         "Raphael: Silence prevents or disrupts casting. Chant Annulment may help with chants, but silence effects can still block magic depending on implementation."
      );
      add(
         "energy blockade|resource blocked|cant recover magicules|cant recover aura",
         "Raphael: Energy Blockade prevents or reduces resource recovery. It is dangerous in long fights because skills eventually become unusable."
      );
      add(
         "webbed|stuck in web|web effect|web debuff",
         "Raphael: Webbed effects reduce movement and can trap the player. Movement interference resistance, cleansing, or strong mobility skills help escape."
      );
      add(
         "burden effect|heavy|weight debuff|burdened",
         "Raphael: Burden reduces mobility or combat performance. It can make dodging and repositioning much harder."
      );
      add(
         "fragility effect|fragile|take more damage|defense down",
         "Raphael: Fragility increases danger by reducing durability or increasing damage taken. Cleanse it quickly before fighting bosses."
      );
      add(
         "oppression|aura pressure|presence pressure|enemy pressure",
         "Raphael: Oppression-style effects use aura, presence, or authority to weaken or pressure enemies. Stronger targets may resist it."
      );
      add(
         "fear effect|terrified|fear debuff|enemy fear",
         "Raphael: Fear can disrupt action or movement. Mental resistance and abnormal nullification help prevent it."
      );
      add(
         "insanity effect|madness|confusion|mental corruption",
         "Raphael: Insanity-style effects interfere with control, perception, or combat. Mental protection is important against these attacks."
      );
      add(
         "hypnosis|charm|mind control|controlled",
         "Raphael: Hypnosis and mind control are mental effects. Mental attack resistance, abnormal nullification, or strong authority can prevent or remove them."
      );
      add(
         "sleep effect|drowsiness|fall asleep|sleep debuff",
         "Raphael: Sleep and drowsiness disable or weaken action. Abnormal resistance or cleansing can counter it."
      );
      add(
         "petrified|stone effect|turned to stone",
         "Raphael: Petrification immobilizes or disables the target. Resistance or cleansing is required before it becomes fatal."
      );
      add(
         "paralyzed|paralysis|cant act|stun locked",
         "Raphael: Paralysis prevents action or movement. It is extremely dangerous against enemies with follow-up burst attacks."
      );
      add(
         "disintegration|disintegrating|holy disintegration|melted",
         "Raphael: Disintegration is a dangerous high-level effect often linked to holy or conceptual damage. Strong resistance and barriers are needed."
      );
      add(
         "black burn|black flame burn|black flames",
         "Raphael: Black Flame effects are often stronger than normal burning and may bypass common defenses depending on implementation."
      );
      add(
         "dark flame|hell flame|inferno|flame debuff",
         "Raphael: Strong flame skills can inflict powerful burns or area denial. Fire resistance may not fully protect against special flame authority."
      );
      add(
         "corroded|corrosion damage|body corrosion",
         "Raphael: Corrosion damages or weakens the body over time. It can be more dangerous than normal poison if left uncleansed."
      );
      add(
         "organ decay|organs decaying|decay effect",
         "Raphael: Organ Decay-style effects imply severe internal damage over time. Cleanse or retreat immediately."
      );
      add(
         "life seizure|life drain|life stolen|hp drain",
         "Raphael: Life Seizure or life drain effects steal or remove vitality. High regeneration may not keep up if the effect scales strongly."
      );
      add(
         "magicule absorption|absorb magicules|magicule steal",
         "Raphael: Magicule absorption drains or steals magicules from targets. This is useful against casters but dangerous if used against protected enemies."
      );
      add(
         "aura absorption|absorb aura|aura steal",
         "Raphael: Aura absorption drains combat energy. It weakens physical and aura-based fighters by reducing their ability to reinforce or use aura skills."
      );
      add(
         "soul eater|eat soul|soul stealing|soul damage enchant",
         "Raphael: Soul Eater-style effects interact with soul or spiritual damage. These are dangerous because they may bypass normal HP logic."
      );
      add(
         "growth enchant|growth effect|item growth|weapon growth",
         "Raphael: Growth effects improve an item or entity over time through combat or progression. Exact scaling depends on the enchantment or system."
      );
      add(
         "restoration enchant|restore item|repair gear|self repair",
         "Raphael: Restoration effects repair or recover items/resources. Exact behavior depends on the item, enchantment, and resource source."
      );
      add(
         "barrier piercing|pierce barrier|ignore barrier enchant",
         "Raphael: Barrier Piercing lets attacks bypass or weaken barriers. It is strong against defensive builds."
      );
      add(
         "holy coat|holy coating|holy weapon enchant",
         "Raphael: Holy Coat-style effects add holy power or protection. This can be useful against demonic or undead targets."
      );
      add(
         "dead end rainbow|rainbow blade|dead end",
         "Raphael: Dead End Rainbow is a named high-level concept. Exact behavior should be taken from records because versions may differ."
      );
      add(
         "intangibility|phase|phase through|intangible",
         "Raphael: Intangibility can reduce physical interaction or allow phase-like defense. Exact bypasses and weaknesses depend on implementation."
      );
      add(
         "severance|sever|cut connection|severing attack",
         "Raphael: Severance effects may cut, disconnect, or heavily damage targets. They can be dangerous if they affect spiritual or conceptual links."
      );
      add(
         "holy field|holy area|sanctuary|holy ground",
         "Raphael: Holy areas can empower holy effects or weaken demonic/undead targets. Exact mechanics depend on skill and dimension rules."
      );
      add(
         "dark field|shadow area|dark domain|domain",
         "Raphael: Dark or domain-like fields can empower certain skills and weaken enemies. Exact mechanics depend on the skill."
      );
      add(
         "domain expansion|domain skill|field skill|area authority",
         "Raphael: Domain-style abilities usually create an area where the user gains authority or enemies are debuffed. Exact behavior requires skill records."
      );
      add(
         "aura release|release aura|aura pressure skill",
         "Raphael: Aura release can pressure nearby enemies, intimidate weaker targets, or empower attacks. Strong enemies may resist pressure."
      );
      add(
         "magicule release|release magicules|magic pressure",
         "Raphael: Releasing magicules can pressure enemies or fuel magic effects. It may also reveal your presence to sensing targets."
      );
      add(
         "presence concealment exact|hide from enemies|stealth skill|undetectable",
         "Raphael: Presence Concealment hides or reduces detection. Strong sensing skills, bosses, or higher authority can still reveal the user."
      );
      add(
         "enemy search exact|search enemies|detect mobs|find mobs",
         "Raphael: Enemy Search or detection skills locate nearby hostile entities. Exact range and filters depend on the skill."
      );
      add(
         "sound sense|hear enemies|detect sound",
         "Raphael: Sound Sense detects entities or events through sound. It helps with awareness but may fail against silent or concealed targets."
      );
      add(
         "danger sense|sense danger|incoming attack warning",
         "Raphael: Danger Sense warns about incoming threats. It is useful for survival but can be bypassed by hidden, instant, or high-authority attacks."
      );
      add(
         "future prediction exact|future attack prediction|predict future",
         "Raphael: Future attack prediction estimates incoming attacks. It helps dodge but may fail against unpredictable, hidden, or superior authority attacks."
      );
      add(
         "appraisal|identify item|identify enemy|inspect",
         "Raphael: Appraisal identifies information about enemies, items, or phenomena. Strong concealment or unknown addon data can limit results."
      );
      add(
         "analyze enemy|enemy info|enemy stats|scan enemy",
         "Raphael: Enemy analysis can reveal threat level, skills, or weaknesses if the skill supports it. Stronger enemies may resist analysis."
      );
      add(
         "weakness|enemy weakness|find weakness|element weakness",
         "Raphael: Weaknesses depend on race, element, resistance, and skill effects. Analysis skills or wiki records are best for exact weaknesses."
      );
      add(
         "resist fire|fire resistance|fire nullification|burn immunity",
         "Raphael: Fire resistance reduces fire and burn damage. Fire nullification is stronger, but special flames may still need higher authority."
      );
      add(
         "resist water|water resistance|water nullification",
         "Raphael: Water resistance reduces water-type damage and effects. Exact protection depends on the attack and resistance level."
      );
      add(
         "resist wind|wind resistance|wind nullification",
         "Raphael: Wind resistance reduces cutting wind, air pressure, and wind projectiles. Stronger spatial or cutting effects may bypass it."
      );
      add(
         "resist earth|earth resistance|earth nullification",
         "Raphael: Earth resistance reduces stone, ground, and earth-element attacks. It may not stop gravity or space effects."
      );
      add(
         "resist lightning|lightning resistance|electric resistance|thunder nullification",
         "Raphael: Lightning resistance reduces electric and thunder damage. Some lightning effects may also stun unless abnormal resistance blocks it."
      );
      add(
         "resist ice|ice resistance|freeze resistance|freezing nullification",
         "Raphael: Ice resistance reduces freezing and ice damage. Freeze immunity or abnormal nullification helps avoid immobilization."
      );
      add(
         "resist light|light resistance|holy resistance",
         "Raphael: Light or holy resistance helps against purification and holy attacks, especially useful for demonic or undead builds."
      );
      add(
         "resist dark|dark resistance|darkness resistance",
         "Raphael: Darkness resistance helps against shadow, curse, and dark-element effects. Exact protection depends on the skill."
      );
      add(
         "resist gravity|gravity resistance|gravity nullification",
         "Raphael: Gravity resistance helps against pressure, pull, crushing, and movement-control effects. It may not stop spatial blockade."
      );
      add(
         "resist space|space resistance|spatial resistance|spatial nullification",
         "Raphael: Spatial resistance protects against teleport disruption, space cuts, and spatial control. Stronger space authorities may still bypass weak resistance."
      );
      add(
         "resist time|time resistance|temporal resistance|time nullification",
         "Raphael: Time resistance protects against slow, stop, or temporal interference. True time-stop resistance is usually high-level."
      );
      add(
         "resist death|death resistance|instant death resistance|death nullification",
         "Raphael: Death resistance protects against instant death or death-authority effects. Spiritual protection is also important."
      );
      add(
         "resist soul|soul resistance|soul nullification",
         "Raphael: Soul resistance protects against attacks targeting the soul or Spiritual Health. It is vital against high-level spiritual enemies."
      );
      add(
         "resist mental|mental resistance|mind resistance|mental nullification",
         "Raphael: Mental resistance protects against mind control, fear, hypnosis, insanity, charm, and confusion."
      );
      add(
         "resist poison|poison resistance|fatal poison resistance|toxin nullification",
         "Raphael: Poison resistance reduces poison damage, while abnormal nullification can fully block many poison-type effects."
      );
      add(
         "resist curse|curse resistance|curse nullification",
         "Raphael: Curse resistance protects against weakening, damage-over-time, and recovery interference effects."
      );
      add(
         "resist paralysis|paralysis resistance|stun resistance",
         "Raphael: Paralysis resistance helps prevent action-lock effects. It is very important in boss fights."
      );
      add(
         "resist petrification|petrification resistance|stone resistance",
         "Raphael: Petrification resistance prevents being turned to stone or immobilized by petrifying effects."
      );
      add("resist fear|fear resistance|fear nullification", "Raphael: Fear resistance prevents panic or combat disruption from oppressive enemies.");
      add("resist hypnosis|hypnosis resistance|mind control resistance", "Raphael: Hypnosis resistance prevents loss of control or charm-like effects.");
      add("resist sleep|sleep resistance|drowsiness resistance", "Raphael: Sleep resistance prevents forced sleep or drowsiness effects that interrupt combat.");
      add("resist corrosion|corrosion resistance|decay resistance", "Raphael: Corrosion resistance reduces decay-like damage and weakening effects.");
      add("resist burden|burden resistance|weight resistance", "Raphael: Burden resistance helps prevent heavy, slow, or pressure-like debuffs.");
      add("resist web|web resistance|webbed resistance", "Raphael: Web resistance helps prevent movement loss from webbed effects.");
      add(
         "resist spatial blockade|spatial blockade resistance|teleport block resistance",
         "Raphael: Spatial blockade resistance helps protect teleportation and space movement from being sealed."
      );
      add(
         "resist magic interference|magic interference resistance|anti magic resistance",
         "Raphael: Magic interference resistance helps preserve casting and magic performance in anti-magic fields."
      );
      add(
         "resist silence|silence resistance|chant block resistance",
         "Raphael: Silence resistance helps prevent casting shutdown. It is important for magic-focused builds."
      );
      add(
         "resist energy blockade|energy blockade resistance|resource block resistance",
         "Raphael: Energy blockade resistance helps preserve aura and magicule regeneration during fights."
      );
      add(
         "increase magicules|more magicules|max magicules|magicule capacity",
         "Raphael: Increase magicule capacity through race progression, skills, attributes, gear, enchantments, and high-level evolution. Magic builds should prioritize this."
      );
      add(
         "increase aura|more aura|max aura|aura capacity",
         "Raphael: Increase aura through race progression, physical training, aura-focused skills, gear, and attributes. Melee and reinforcement builds need strong aura."
      );
      add(
         "increase spiritual health|more spiritual health|max spiritual health",
         "Raphael: Spiritual Health improves through higher existence, race progression, spiritual defenses, and high-level skills. It is critical against soul and spiritual attacks."
      );
      add(
         "increase regeneration|regen faster|resource regen|recover faster",
         "Raphael: Regeneration can be improved through skills, race traits, gear, enchantments, and resource-focused attributes. Separate HP, aura, and magicule recovery may scale differently."
      );
      add(
         "increase damage|more damage|deal more damage|damage boost",
         "Raphael: Increase damage by improving weapon quality, aura, magicules, mastery, attributes, element matchups, skill rank, and bypass effects."
      );
      add(
         "increase defense|more defense|take less damage|survivability",
         "Raphael: Improve defense through armor, barriers, resistances, regeneration, Spiritual Health protection, dodge, and avoiding bad elemental matchups."
      );
      add(
         "increase speed|move faster|faster movement|speed build",
         "Raphael: Movement speed improves with attributes, skills, race traits, gear, and buffs. Speed builds should also maintain dodge and resource recovery."
      );
      add(
         "increase casting speed|cast faster|faster magic|chant faster",
         "Raphael: Casting speed improves through chant speed, mastery, Chant Annulment, magic-focused skills, and sometimes equipment."
      );
      add(
         "increase mastery|mastery faster|skill xp|ability xp",
         "Raphael: Mastery generally improves through repeated valid use, progression conditions, and mastery modifiers. Some skills only gain mastery under specific conditions."
      );
      add(
         "best resistance|most important resistance|what resistance should i get",
         "Raphael: The best resistance depends on enemies. General priorities are abnormal condition protection, spiritual attack resistance, mental resistance, elemental resistance, and barrier or bypass protection."
      );
      add(
         "best skill|strongest skill|op skill|most powerful skill",
         "Raphael: The strongest skill depends on build and server balance. Ultimate Skills and high-authority addon skills are usually strongest, but exact ranking requires wiki records."
      );
      add(
         "best magic|strongest magic|op magic",
         "Raphael: The best magic depends on target resistance and build. Space, gravity, holy, dark, and high-tier elemental magic are often strong if properly mastered."
      );
      add(
         "best race for magic|magic race|caster race",
         "Raphael: Magic builds want races with high magicules, chant/casting support, spiritual defenses, and magic-oriented intrinsic skills."
      );
      add(
         "best race for melee|melee race|physical race",
         "Raphael: Melee builds want high aura, attack, durability, movement, regeneration, and physical combat intrinsic skills."
      );
      add(
         "best race for tank|tank race|defensive race",
         "Raphael: Tank builds want resistances, barriers, regeneration, high HP, Spiritual Health protection, and strong resource recovery."
      );
      add(
         "best race for speed|speed race|fast race",
         "Raphael: Speed builds want movement speed, dodge, reaction skills, aura recovery, and mobility skills like spatial movement."
      );
      add(
         "best race for summoner|summon build|subordinate build",
         "Raphael: Summoner builds want magicules, control skills, subordinate support, revive mechanics, and enough defenses to survive while summons fight."
      );
      add(
         "build around predator|predator build|devour build",
         "Raphael: Predator-style builds should focus on survival, target control, analysis, resource recovery, and valid enemies to devour. Exact skill interactions require records."
      );
      add(
         "build around mammon|mammon build|greed build",
         "Raphael: Mammon or greed builds likely focus on resource control, stealing, scaling, or reward mechanics. Exact build advice should use Nightmare wiki records."
      );
      add(
         "build around belphegor|belphegor build|sloth build",
         "Raphael: Belphegor or sloth builds likely focus on debuffs, control, slowing, or weakening enemies. Exact build advice should use Mysticism wiki records."
      );
      add(
         "build around spatial|spatial build|space build",
         "Raphael: Spatial builds should prioritize magicules, spatial resistance, mobility, casting speed, and skills that control distance or teleportation."
      );
      add(
         "build around time|time build|temporal build",
         "Raphael: Time builds should prioritize resource recovery, cooldown management, resistance to time effects, and high-authority skills that affect movement or actions."
      );
      add(
         "build around fire|fire build|flame build",
         "Raphael: Fire builds should prioritize fire damage, burn effects, magicules, casting speed, and ways to handle fire-resistant enemies."
      );
      add(
         "axiom|axiom lord of reflected judgment|reflected judgment|lord of reflected judgment|reflection ultimate|reflector evolution",
         "Raphael: Axiom, Lord of Reflected Judgment is an Ultimate Skill evolved from mastered Reflector. It records damage and kills as memories, reduces incoming damage while toggled, reflects attacks, reverses projectiles, marks targets, creates a mirror field, replays stored judgment as burst damage, and can survive death once when mastered."
      );
      add(
         "how to get axiom|obtain axiom|axiom requirement|axiom obtain|axiom cost|axiom acquire",
         "Raphael: Axiom requires mastered Reflector and has a 500,000 magicule acquire cost. It is a reflection/judgment Ultimate Skill focused on damage memory, counters, projectile reversal, and stored retaliation."
      );
      add(
         "axiom modes|axiom mode|axiom abilities|axiom skills|axiom actives",
         "Raphael: Axiom has four modes: Ripple Script, Mirrorfield, Truth, and Planetfall. Ripple Script marks a target, Mirrorfield is a held area-control field, Truth replays stored damage memories, and Planetfall is the large ultimate blast/buff mode."
      );
      add(
         "axiom passive|axiom toggle|axiom toggled|axiom reflection|axiom counter|axiom damage reduction",
         "Raphael: While toggled, Axiom records incoming damage, reduces damage by about 40%, and can counter attackers with reflected magic damage. Mastery improves counter cooldown and reflected multiplier."
      );
      add(
         "axiom projectile|axiom projectile reflect|axiom reverse projectile|axiom arrow reflect|axiom deflect",
         "Raphael: Axiom can reverse incoming projectiles while toggled. It changes the projectile owner to the Axiom user, reverses its velocity, and strengthens arrows if the reflected projectile is an arrow."
      );
      add(
         "axiom ripple script|ripple script|axiom mark|prophecy mark|axiom script target",
         "Raphael: Ripple Script marks the looked-at target within about 40 blocks. While marked, damage dealt to that target is recorded and boosted, making the target ideal for follow-up burst and Truth replay."
      );
      add(
         "axiom mirrorfield|mirrorfield|mirror field|axiom field|held mirrorfield",
         "Raphael: Mirrorfield is Axiom's held field mode. It creates a reflective area around the user, damages nearby enemies, records extra damage memories, applies Movement Interference, and works as close-range battlefield control."
      );
      add(
         "axiom truth|truth replay|replay truth|axiom memories|axiom stored damage|axiom memory burst",
         "Raphael: Truth consumes Axiom's stored damage and kill memories, then releases them as area burst damage around the user. It is strongest after taking, reflecting, or dealing large amounts of damage."
      );
      add(
         "axiom planetfall|planetfall|axiom final|axiom ultimate attack|planet fall",
         "Raphael: Planetfall is Axiom's large ultimate move. It buffs the user with Magic Barrier, Reinforcement, and Self Regeneration, creates explosions and rings, then damages enemies in a wide area with base damage plus max-HP scaling."
      );
      add(
         "axiom inversion|judgment inversion|axiom death save|axiom revive|axiom death prevention",
         "Raphael: Mastered Axiom can trigger Inversion once while toggled if lethal damage would kill the user and the source does not bypass invulnerability. It restores health, grants barrier/regeneration, and releases Truth from stored memories."
      );
      add(
         "axiom build|best axiom build|axiom gear|axiom stats",
         "Raphael: Axiom builds should stack durability, magicules, projectile defense, regeneration, barrier strength, and enough damage intake/sustain to fill memory safely. It rewards surviving pressure, then reflecting it back."
      );
      add(
         "counter axiom|beat axiom|fight axiom|axiom counterplay",
         "Raphael: Counter Axiom by avoiding feeding it big stored damage, using bypass-invulnerability or non-projectile attacks, draining resources, forcing cooldowns, and bursting after its counter or Inversion is unavailable."
      );
      add(
         "caedros|caedros god of conquest|god of conquest|conquest ultimate|caedros skill",
         "Raphael: Caedros, God of Conquest is an Ultimate Skill built around Guilt, conquest, plague, Bang beams, Eden domain, Fang and Serpent control, construct swords, and four Aspects: Control, Famine, War, and Death."
      );
      add(
         "how to get caedros|obtain caedros|caedros requirement|caedros cost|caedros acquire",
         "Raphael: Caedros has an extremely high acquire cost of 50,000,000,000 magicules. It is designed as an endgame conquest authority with modes, aspects, Guilt economy, skill-eating punishment, and battlefield domination."
      );
      add(
         "caedros modes|caedros mode|caedros abilities|caedros actives",
         "Raphael: Caedros has six modes: Conquest, Plague, Bang, Cloud, Fang and Serpent, and Construct. Shift opens Aspect menus, while normal activation uses the selected mode."
      );
      add(
         "caedros guilt|guilt|guilt resource|caedros resource|caedros drain",
         "Raphael: Guilt is Caedros's core resource. It builds while toggled off, drains while toggled on, scales buffs, supports Bang, and if the user cannot pay Guilt, Ouroboros can eat a random skill or kill the user if no skill can be eaten."
      );
      add(
         "caedros toggle|caedros toggled|caedros buffs|guilt buffs|caedros passive",
         "Raphael: When toggled, Caedros consumes Guilt to gain attack damage, attack speed, knockback resistance, auto projectile dodge, auto melee dodge, magicule gain, aura gain, Guarded, Haki Coat, Strengthen, Inspiration, Magic Aura, and regeneration scaling by Guilt tier."
      );
      add(
         "caedros fear aura|caedros insanity aura|fear insanity aura|conquest aura",
         "Raphael: While toggled, Caedros periodically applies Fear and Insanity to nearby enemies. Its damage can scale with nearby Fear stacks, making fear pressure part of its damage engine."
      );
      add(
         "caedros ouroboros|ouroboros|caedros eats skill|guilt empty|not enough guilt caedros",
         "Raphael: Ouroboros is Caedros's punishment engine. If Guilt runs out while toggled, it can eat a random learned skill and store it as eaten. If no skill can be eaten, the user dies and Caedros toggles off."
      );
      add(
         "caedros conquest|conquest mode|ashen halberd|caedros halberd|conquest sweep|conquest release",
         "Raphael: Conquest mode summons the Ashen Halberd, fires a mini Bang, performs a sweeping area attack, then charges and releases a stronger forward strike. It is Caedros's weapon-combo mode."
      );
      add(
         "caedros mini bang|mini bang|conquest mini bang|halberd bang",
         "Raphael: Conquest Mini Bang fires a short red-black lightning beam from the user's look direction, dealing disintegration-style damage and knockback to enemies in its path."
      );
      add(
         "caedros plague|plague mode|plague slash|caedros infection|plague debuffs",
         "Raphael: Plague mode releases multiple forward plague slashes. It deals infection-style damage and randomly applies dangerous debuffs such as Infection, Curse, Fragility, Magic Interference, Corrosion, Chill, Webbed, Paralysis, Black Burn, Drowsiness, Movement Interference, and Oppression."
      );
      add(
         "caedros bang|bang mode|bang beam|caedros beam|bang attack",
         "Raphael: Bang is Caedros's huge charged beam. It consumes Guilt, has a windup, then fires a long destructive beam that damages enemies, carves terrain if skill grief allows, and becomes absurd during Total War."
      );
      add(
         "caedros total war bang|total war bang|war bang|infinite bang damage",
         "Raphael: If Caedros War Aspect reaches Total War, Bang upgrades into an enormous beam with much larger range/radius and overwhelming damage. It is the conquest authority's annihilation state."
      );
      add(
         "caedros cloud|cloud the skies|eden|eden domain|paths dimension|caedros domain",
         "Raphael: Cloud the Skies opens Eden after a windup. It teleports players into the Paths dimension, strengthens the owner, places a world border, leashes players near the owner, and restores players when the session ends."
      );
      add(
         "caedros fang|fang and serpent|fang serpent|serpent hiss|caedros hiss",
         "Raphael: Fang and Serpent applies fear/control pressure in an area. It can apply Fear, Insanity, Burden, Magic Interference, and disables random Unique or Ultimate skills from affected enemies."
      );
      add(
         "caedros construct|construct mode|construct swords|sword spirits|caedros swords",
         "Raphael: Construct mode creates orbiting sword spirits from kills. The swords slash nearby targets, apply debuffs, consume uses, generate Guilt when they break, and can multiply through kills."
      );
      add(
         "caedros reconstruct|reconstruct menu|rebuy eaten skill|eaten skills|restore eaten skill",
         "Raphael: Reconstruct lets the Caedros user recover eaten skills from storage. Skills can be rebought through Guilt or EP if the stored skill ID is valid and the player can pay the cost."
      );
      add(
         "caedros aspects|caedros aspect|aspect menu|control famine war death",
         "Raphael: Caedros has four Aspects: Control, Famine, War, and Death. Control suppresses weaker players and locks skills, Famine stacks debuffs and starvation, War conquers weaker players and boosts EP, and Death enables soul-based revival and replica control."
      );
      add(
         "caedros control|control aspect|caedros control aspect|control locked skill|lock skill",
         "Raphael: Control Aspect targets players far below the owner’s EP threshold. It can force them into spectator mode temporarily and lock or unslot a chosen Unique or Ultimate skill until control ends."
      );
      add(
         "caedros famine|famine aspect|caedros famine aspect|starvation aspect",
         "Raphael: Famine Aspect starves other players and stacks debuffs on hit. It repeatedly applies effects like Infection, Burden, Curse, Fragility, Magic Interference, Corrosion, Chill, Fear, Insanity, Paralysis, Webbed, Black Burn, Drowsiness, Oppression, Soul Drain, and Spatial Blockade."
      );
      add(
         "caedros war|war aspect|caedros war aspect|conquered|total war",
         "Raphael: War Aspect compares other players to the owner's base EP. Weaker players become conquered and feed temporary EP to Caedros. If everyone nearby is conquered, Total War activates and massively empowers Bang."
      );
      add(
         "caedros death|death aspect|caedros death aspect|death revive|soul revive",
         "Raphael: Death Aspect gives Caedros a soul-point revive. If the user dies with enough soul points, the skill consumes 10,000 soul points, restores health, removes effects, and prevents death."
      );
      add(
         "caedros build|best caedros build|caedros gear|caedros stats",
         "Raphael: Caedros builds should stack Guilt generation, survivability, soul points, EP pressure, magicule/aura gain, dodge, and area-control resistance. It is strongest when rotating Aspects instead of spamming only Bang."
      );
      add(
         "counter caedros|beat caedros|fight caedros|caedros counterplay",
         "Raphael: Counter Caedros by denying Guilt value, avoiding fear stacking, using cleanse and abnormal nullification, staying out of Bang lines, preventing Total War, bursting while Guilt is low, and resisting Magic Interference and Spatial Blockade."
      );
      add(
         "mahoraga|divine general mahoraga|divine general|mahoraga skill|adaptation ultimate",
         "Raphael: Divine General Mahoraga is an Ultimate Skill evolved from mastered Adaptability. It adapts to damage types and effects, learns nullifications, gains regeneration, boosts mastery gain, and can summon an Extermination Blade."
      );
      add(
         "how to get mahoraga|obtain mahoraga|mahoraga requirement|mahoraga cost|mahoraga acquire",
         "Raphael: Mahoraga requires mastered Adaptability and has a 10,000,000 magicule acquire cost. It is a long-fight adaptation Ultimate Skill based on learning damage, effects, and resistances."
      );
      add(
         "mahoraga modes|mahoraga mode|mahoraga abilities|mahoraga actives",
         "Raphael: Mahoraga has two modes: Summon Sword and Adapted. Summon Sword creates the Extermination Blade. Adapted opens the adaptation menu where learned damage/effect adaptations can be enabled or disabled."
      );
      add(
         "mahoraga toggle|mahoraga toggled|mahoraga passive|mahoraga regeneration",
         "Raphael: While toggled, Mahoraga keeps regeneration active, learns from active effects, cleanses enabled adapted effects, tracks incoming and outgoing damage keys, and grants mastery gain over time."
      );
      add(
         "mahoraga adapted effects|adapted effects|mahoraga effect immunity|mahoraga immune effects",
         "Raphael: Mahoraga learns harmful effects when they are applied or active on the user. Once an effect is adapted, the player can enable it in the adaptation menu to become immune and cleanse it automatically."
      );
      add(
         "mahoraga damage adaptation|adapted damage|damage learning|mahoraga damage reduction",
         "Raphael: Mahoraga records damage source keys when taking damage. Enabled adapted damage types reduce future damage, starting low and scaling with repeated hits, with a higher damage-reduction cap when mastered."
      );
      add(
         "mahoraga nullification|learn nullification|mahoraga resistance|mahoraga learns resistance",
         "Raphael: After taking enough hits from a damage bucket, Mahoraga can learn the matching Tensura nullification, such as Fire, Cold, Water, Wind, Earth, Lightning, Magic, Holy, Darkness, Gravity, Spatial, Spiritual, Pierce, Poison, Corrosion, or Physical Nullification."
      );
      add(
         "mahoraga outgoing damage|mahoraga stage|adaptation stage|mahoraga disruption",
         "Raphael: Mahoraga also adapts offensively. Repeated outgoing hits against a target build stages that increase damage multipliers. Higher stages strip defensive effects and apply Magic Interference, Fragility, Oppression, and Soul Drain."
      );
      add(
         "mahoraga adapted menu|adaptation menu|mahoraga menu|toggle adaptation",
         "Raphael: Mahoraga's Adapted mode opens a menu listing learned effect and damage adaptations. The player can select and toggle entries to decide which immunities or reductions are currently active."
      );
      add(
         "mahoraga extermination blade|extermination blade|mahoraga sword|summon sword mahoraga",
         "Raphael: Mahoraga can summon the Extermination Blade. If mastered, the blade gets stronger enchantments. The blade can receive Severance, Tsukumogami, Unbreaking, Sharpness, Dead End Rainbow, Soul Eater, Energy Steal, Holy Weapon, and Holy Coat."
      );
      add(
         "mahoraga resistance degradation|mahoraga mastered|mastered mahoraga|resistance degradation",
         "Raphael: Mastered Mahoraga gains Resistance Degradation, improving its ability to break through enemy resistance. Mastery also raises damage-reduction caps and improves offensive scaling."
      );
      add(
         "mahoraga build|best mahoraga build|mahoraga gear|mahoraga stats",
         "Raphael: Mahoraga builds should stack HP, regeneration, broad resistances, Spiritual Health protection, armor, cleanse, and enough time to survive repeated hits. It becomes stronger the longer a fight continues."
      );
      add(
         "counter mahoraga|beat mahoraga|fight mahoraga|mahoraga counterplay",
         "Raphael: Counter Mahoraga by killing it before adaptation stacks, rotating damage types, using burst damage, execution effects, spiritual pressure, resource drain, and not repeating the same attack pattern."
      );
      add(
         "axiom vs caedros|caedros vs axiom|reflection vs conquest",
         "Raphael: Axiom wants to store and reflect enemy pressure, while Caedros wants to overwhelm with Guilt, Aspects, Bang, and conquest control. Axiom punishes direct burst; Caedros punishes prolonged domination."
      );
      add(
         "axiom vs mahoraga|mahoraga vs axiom|reflection vs adaptation",
         "Raphael: Axiom reflects and stores damage for burst, while Mahoraga adapts over time. Axiom should try to end the fight with Truth or Planetfall before Mahoraga adapts too many damage keys."
      );
      add(
         "caedros vs mahoraga|mahoraga vs caedros|conquest vs adaptation",
         "Raphael: Caedros can pressure Mahoraga with many damage/effect types through Plague, Bang, Conquest, and Aspects. Mahoraga wins if it survives long enough to adapt and reduce Caedros's repeated patterns."
      );
      add(
         "custom ultimate list|my ultimate skills|moreskills ultimates|tenaddex ultimates",
         "Raphael: Current custom Ultimate Skills include Axiom, Lord of Reflected Judgment; Caedros, God of Conquest; and Divine General Mahoraga. Axiom reflects judgment, Caedros conquers through Guilt and Aspects, and Mahoraga adapts through repeated exposure."
      );
      add(
         "build around holy|holy build|light build",
         "Raphael: Holy builds should prioritize light or holy damage, purification effects, casting speed, and defenses against demonic or dark enemies."
      );
      add(
         "build around darkness|dark build|shadow build",
         "Raphael: Darkness builds should prioritize stealth, debuffs, dark damage, mobility, and mental or curse effects depending on skill records."
      );
      add(
         "build around gravity|gravity build",
         "Raphael: Gravity builds should prioritize magicules, area control, movement disruption, and defense against ranged enemies."
      );
      add(
         "gear for magic|magic gear|caster gear",
         "Raphael: Magic gear should improve magicules, casting speed, magic damage, regeneration, and protection while casting."
      );
      add(
         "gear for melee|melee gear|fighter gear",
         "Raphael: Melee gear should improve attack damage, aura, attack speed, movement, durability, and resistance."
      );
      add(
         "gear for tank|tank gear|defense gear",
         "Raphael: Tank gear should improve armor, barriers, resistances, regeneration, Spiritual Health protection, and knockback resistance."
      );
      add("gear for speed|speed gear|mobility gear", "Raphael: Speed gear should improve movement speed, dodge, aura recovery, and cooldown consistency.");
      add(
         "weapon choice|best weapon|sword|katana|axe|staff|bow",
         "Raphael: Weapon choice depends on build. Melee builds favor high damage and attack speed, casters may prefer magic-support gear, and ranged builds need projectile support."
      );
      add(
         "armor choice|best armor|heavy armor|light armor",
         "Raphael: Armor should match your build. Heavy armor improves survival, while lighter or special gear may support speed, casting, magicules, or aura."
      );
      add(
         "early progression|early game guide|start guide|beginner guide",
         "Raphael: Early progression should focus on survival, simple EP gain, basic skills, gear, food, and avoiding spiritual or boss-level enemies."
      );
      add(
         "mid progression|mid game guide|what after early game",
         "Raphael: Midgame progression should focus on race evolution, stronger skills, mastery, resistances, magicule/aura capacity, and better equipment."
      );
      add(
         "late progression|late game guide|end game guide",
         "Raphael: Late progression focuses on Ultimate Skills, high-level race evolution, boss fights, spiritual protection, specialized builds, and strong resource regeneration."
      );
      add(
         "what to do before boss|boss preparation|prepare for fight",
         "Raphael: Before a boss, fill aura and magicules, bring healing, check resistances, improve gear, master key skills, prepare escape options, and avoid entering with low Spiritual Health."
      );
      add(
         "how to survive boss|boss survival|boss keeps killing me",
         "Raphael: To survive bosses, prioritize resistance, barriers, movement, regeneration, resource recovery, and learning attack patterns. Do not rely only on EP."
      );
      add(
         "how to beat boss|boss strategy|kill boss",
         "Raphael: Boss strategy depends on mechanics, but general rules are: avoid burst attacks, maintain resources, punish cooldown windows, keep distance if fragile, and retreat when Spiritual Health drops."
      );
      add(
         "enemy too strong|cant beat enemy|enemy stronger than me",
         "Raphael: If an enemy is too strong, improve resistances, gear, resource pools, mastery, and race evolution before retrying. High EP alone does not guarantee survival."
      );
      add(
         "how to counter magic|counter caster|fight magic enemy",
         "Raphael: Counter casters with magic resistance, movement, silence, magic interference, barriers, ranged pressure, and enough burst to interrupt casting."
      );
      add(
         "kronairos|kronairos lord of the endless|lord of the endless|endless skill|infinity prison evolution",
         "Raphael: Kronairos, Lord of the Endless is an Ultimate Skill evolved from mastered Infinity Prison. It focuses on endless hunger, isolation, spatial imprisonment, lifesteal, EP pressure, and controlling enemies through confinement."
      );
      add(
         "how to get kronairos|obtain kronairos|kronairos requirement|kronairos cost|kronairos acquire",
         "Raphael: Kronairos requires mastered Infinity Prison and has a 1,000,000 magicule acquire cost. It is an endgame spatial-control and drain Ultimate Skill."
      );
      add(
         "kronairos modes|kronairos mode|kronairos abilities|kronairos actives",
         "Raphael: Kronairos has five modes: Taker's Will, Power Hunger, Room, Isolation, and Divine Lifesteal. It combines EP draining, confinement, area control, and sustain."
      );
      add(
         "kronairos takers will|takers will|taker will|kronairos will|endless will",
         "Raphael: Taker's Will marks a target for a long duration and repeatedly pressures them over time. It is Kronairos's sustained drain or punishment mode."
      );
      add(
         "kronairos power hunger|power hunger|hunger mode|endless hunger",
         "Raphael: Power Hunger is Kronairos's direct hunger mode. It should be used when you want to pressure enemy resources or punish targets with superior raw power."
      );
      add(
         "kronairos room|room mode|endless room|kronairos domain|kronairos area",
         "Raphael: Room creates a temporary spatial control area. It is used to control battlefield positioning and trap enemies inside Kronairos's authority."
      );
      add(
         "kronairos isolation|isolation mode|isolate target|kronairos isolate",
         "Raphael: Isolation locks down a chosen target after being held long enough. It is best against single dangerous enemies, casters, or players who rely on mobility."
      );
      add(
         "kronairos divine lifesteal|divine lifesteal|lifesteal mode|endless lifesteal",
         "Raphael: Divine Lifesteal is Kronairos's sustain mode. It drains life or power from enemies and converts pressure into survival."
      );
      add(
         "kronairos toggle|kronairos passive|kronairos aura|kronairos toggled",
         "Raphael: Kronairos can be toggled. Its passive theme is endless spatial pressure and hunger, with effects centered around nearby aura, imprisonment, and resource control."
      );
      add(
         "kronairos cost|kronairos magicule cost|takers will cost|power hunger cost|room cost|isolation cost|divine lifesteal cost",
         "Raphael: Kronairos mode costs are high: Taker's Will costs 25,000 magicules, Power Hunger 35,000, Room 45,000, Isolation 15,000, and Divine Lifesteal 60,000."
      );
      add(
         "kronairos cooldown|takers will cooldown|power hunger cooldown|room cooldown|isolation cooldown|divine lifesteal cooldown",
         "Raphael: Kronairos has separate cooldowns: Taker's Will is 200 ticks, Power Hunger 100 ticks, Room 100 ticks, Isolation 200 ticks, and Divine Lifesteal 50 ticks."
      );
      add(
         "kronairos build|best kronairos build|kronairos gear|kronairos stats",
         "Raphael: Kronairos builds should focus on magicules, sustain, spatial control, resource drain, durability, and single-target lockdown. It is strongest when the user traps enemies and drains them instead of trading burst."
      );
      add(
         "counter kronairos|beat kronairos|fight kronairos|kronairos counterplay",
         "Raphael: Counter Kronairos with spatial resistance, mobility, burst damage before isolation finishes, anti-drain defenses, magicule sustain, and avoiding fights inside Room."
      );
      add(
         "kronairos vs axiom|axiom vs kronairos|reflection vs endless",
         "Raphael: Axiom wants to reflect damage and replay memories. Kronairos wants to isolate and drain. Axiom punishes direct burst, while Kronairos wins by starving the enemy of space and resources."
      );
      add(
         "kronairos vs caedros|caedros vs kronairos|conquest vs endless",
         "Raphael: Caedros overwhelms through Guilt, Aspects, and conquest pressure. Kronairos counters by isolating key targets and draining sustain, but must avoid Total War Bang."
      );
      add(
         "kronairos vs mahoraga|mahoraga vs kronairos|endless vs adaptation",
         "Raphael: Kronairos should end the fight through isolation and drain before Mahoraga adapts. Mahoraga wins if it survives long enough to learn Kronairos's repeated damage and effects."
      );
      add(
         "kronairos vs istaroth|istaroth vs kronairos|time vs endless",
         "Raphael: Istaroth controls time and projectiles, while Kronairos controls space and confinement. Istaroth denies action; Kronairos denies escape."
      );
      add(
         "kronairos player advice|how to use kronairos|kronairos tips",
         "Raphael: Use Kronairos by trapping targets, forcing them to fight in your area, draining resources, then finishing them while they cannot escape. Do not waste Room or Isolation on weak enemies."
      );
      add(
         "mammon casino|casino mammon|mammon casino skill|greed casino|casino skill",
         "Raphael: Mammon Casino is a Greed-based Ultimate Skill system that opens a casino menu and tracks chips. It rewards kills with chips and turns greed into a gambling/resource mechanic."
      );
      add(
         "how to get mammon casino|obtain mammon casino|mammon casino requirement|mammon casino cost",
         "Raphael: Mammon Casino requires mastered Greed. It is a casino-style greed authority where kills generate chips and the active mode opens the Greed Casino menu."
      );
      add(
         "mammon casino mode|mammon casino modes|mammon casino ability|mammon casino active",
         "Raphael: Mammon Casino has one main mode that opens the Greed Casino interface. Its real gameplay is in chip tracking, kill rewards, and casino interactions."
      );
      add(
         "mammon casino chips|chips|greed chips|casino chips|how get chips",
         "Raphael: Mammon Casino tracks chips in player data. Valid kills add tracked kills and grant chips, while duplicate kill tracking is prevented for the same entity too quickly."
      );
      add(
         "mammon casino kills|tracked kills|casino kill counter|mammon kills",
         "Raphael: Mammon Casino stores total kills and last killed target data. Kills increase total kill count and grant chips if they are not duplicate spam kills."
      );
      add(
         "mammon casino build|best mammon casino build|casino build|greed casino build",
         "Raphael: Mammon Casino builds should focus on safe kill farming, mobility, survivability, area damage, and anything that lets you generate chips efficiently without dying."
      );
      add(
         "counter mammon casino|beat mammon casino|mammon casino counter",
         "Raphael: Counter Mammon Casino by denying kills, forcing the user away from farming, bursting them before they snowball chips, and preventing them from safely using casino rewards."
      );
      add(
         "mammon casino vs mammon|mammon vs mammon casino|greed vs casino",
         "Raphael: Mammon Casino is a gambling/resource version of greed. Regular greed-style skills steal, desire, or scale power, while Mammon Casino turns kills into chips and casino value."
      );
      add(
         "mammon casino tips|how to use mammon casino|casino tips",
         "Raphael: Use Mammon Casino by farming valid kills, building chips safely, and spending them through the casino system. It is stronger in long sessions where the user can keep collecting value."
      );
      add(
         "adaptability|adaptability skill|adaptive unique|adaptability unique|adapt skill",
         "Raphael: Adaptability is a Unique Skill focused on learning from repeated damage. It builds damage-reduction stacks, can gain severance immunity, gives mastery progress over time while toggled, and can summon an Extermination Blade."
      );
      add(
         "how to get adaptability|obtain adaptability|adaptability cost|adaptability requirement",
         "Raphael: Adaptability has a 90,000 magicule acquire cost and 2,000 max mastery. It is the prerequisite path toward Divine General Mahoraga."
      );
      add(
         "adaptability modes|adaptability mode|adaptability active|adaptability ability",
         "Raphael: Adaptability has one mode: Extermination Blade. Pressing the skill summons an adaptive blade with a cooldown, while the toggle handles adaptation and mastery behavior."
      );
      add(
         "adaptability toggle|adaptability passive|adaptability toggled|adaptive passive",
         "Raphael: While toggled, Adaptability tracks adaptation data, clears severance damage if severance immunity has been obtained, and periodically grants mastery progress."
      );
      add(
         "adaptability damage reduction|adaptive damage reduction|dr stacks|adaptability stacks",
         "Raphael: Adaptability builds damage-reduction stacks from repeated damage. Normal stacks cap lower, mastered stacks cap higher, and damage cannot be reduced below a minimum multiplier."
      );
      add(
         "adaptability severance immunity|severance immunity|immune severance|adapt severance",
         "Raphael: Adaptability tracks Severance hits. After enough Severance exposure, it grants severance immunity and clears severance damage while toggled."
      );
      add(
         "adaptability blade|adaptability extermination blade|adaptive blade|summon adaptive blade",
         "Raphael: Adaptability can summon an Extermination Blade. The blade uses EP as a stored power pool and becomes stronger when the user has adapted to severance."
      );
      add(
         "adaptability build|best adaptability build|adaptability gear|adaptability stats",
         "Raphael: Adaptability builds should focus on survival, regeneration, broad resistances, armor, HP, Spiritual Health protection, and enough sustain to survive repeated hits until adaptation stacks matter."
      );
      add(
         "counter adaptability|beat adaptability|fight adaptability|adaptability counter",
         "Raphael: Counter Adaptability with burst damage, varied damage types, execution effects, soul damage, and by not feeding it the same damage source repeatedly."
      );
      add(
         "adaptability vs mahoraga|mahoraga vs adaptability|adaptability evolve mahoraga",
         "Raphael: Adaptability is the lower-stage adaptation skill. Mahoraga is the Ultimate form that expands adaptation into effect immunity, damage menus, nullifications, and offensive adaptation."
      );
      add(
         "extermination blade|adaptive severance blade|mahoraga blade|adaptability blade item",
         "Raphael: Extermination Blade is an adaptive severance weapon used by Adaptability and Mahoraga. It stores EP, cannot be dropped when marked as the special blade, and gains powerful enchantments when created."
      );
      add(
         "extermination blade ep|blade ep|adaptive blade ep|extermination blade energy",
         "Raphael: Extermination Blade stores EP up to a cap. On hit, it consumes stored EP to add bonus damage. Higher stored EP increases bonus damage up to its damage cap."
      );
      add(
         "extermination blade damage|blade bonus damage|adaptive blade damage|extermination blade hit",
         "Raphael: Extermination Blade adds extra damage on hit if it has enough EP. If the user has severance adaptation, the blade's bonus damage is improved."
      );
      add(
         "extermination blade enchantments|blade enchants|mahoraga blade enchants|adaptive blade enchants",
         "Raphael: Extermination Blade can receive enchantments such as Severance, Tsukumogami, Unbreaking, Sharpness, Dead End Rainbow, Soul Eater, Energy Steal, Holy Weapon, and Holy Coat depending on the source and mastery."
      );
      add(
         "extermination blade cannot drop|cant drop blade|blade invulnerable|blade item update",
         "Raphael: Special Extermination Blade items are protected. They can prevent dropping, stay alive longer as item entities, and are marked with custom data so the skill can recognize them."
      );
      add(
         "extermination blade build|blade build|adaptive sword build|mahoraga sword build",
         "Raphael: Extermination Blade builds should stack attack damage, attack speed, EP sustain, Severance synergy, Soul Eater, Energy Steal, and durability. It pairs best with adaptation or anti-tank play."
      );
      add(
         "counter extermination blade|beat extermination blade|adaptive blade counter",
         "Raphael: Counter Extermination Blade with disarm effects, distance, projectile pressure, anti-melee control, high mobility, and avoiding repeated melee trades that feed adaptation."
      );
      add(
         "all custom skills|all moreskills skills|all tenaddex skills|custom skills list",
         "Raphael: Known custom skills include Adaptability, Divine General Mahoraga, Axiom, Lord of Reflected Judgment, Caedros, God of Conquest, Kronairos, Lord of the Endless, Mammon Casino, plus your other custom concepts like Istaroth, Ronova, Naberius, and Fungus Infection."
      );
      add(
         "custom skill progression|moreskills progression|tenaddex progression|skill evolution path",
         "Raphael: Custom progression has several paths: Reflector can lead to Axiom, Greed can lead to Mammon Casino, Infinity Prison can lead to Kronairos, and Adaptability can lead to Divine General Mahoraga."
      );
      add(
         "reflector to axiom|axiom evolution path|reflector evolution",
         "Raphael: Master Reflector first if you want Axiom. Axiom expands reflection into damage memory, projectile reversal, Truth replay, and Planetfall judgment."
      );
      add(
         "greed to mammon casino|mammon casino evolution path|greed evolution casino",
         "Raphael: Master Greed first if you want Mammon Casino. Mammon Casino changes Greed into a chip and casino resource system based around tracked kills."
      );
      add(
         "infinity prison to kronairos|kronairos evolution path|infinity prison evolution",
         "Raphael: Master Infinity Prison first if you want Kronairos. Kronairos upgrades prison-style control into endless spatial authority, isolation, Room, and lifesteal."
      );
      add(
         "adaptability to mahoraga|mahoraga evolution path|adaptability evolution",
         "Raphael: Master Adaptability first if you want Divine General Mahoraga. Mahoraga is the Ultimate adaptation upgrade with effect immunity, damage adaptation, nullification learning, and Extermination Blade."
      );
      add(
         "which custom skill should i get|best custom skill|which moreskills skill|which tenaddex skill",
         "Raphael: Choose Axiom for reflection and counterplay, Caedros for conquest and domination, Mahoraga for long-fight adaptation, Kronairos for spatial drain and isolation, Mammon Casino for greed resource snowballing, and Adaptability as the path to Mahoraga."
      );
      add(
         "best custom skill for pvp|best moreskills pvp|best tenaddex pvp",
         "Raphael: For PvP, Caedros has oppressive control, Axiom punishes burst and projectiles, Kronairos traps and drains, and Mahoraga wins long fights. The best depends on whether you prefer domination, counterplay, lockdown, or adaptation."
      );
      add(
         "best custom skill for pve|best moreskills pve|best tenaddex pve",
         "Raphael: For PvE, Mahoraga and Adaptability are safe long-fight choices, Caedros clears groups and bosses with huge area power, Axiom punishes boss damage, and Mammon Casino rewards farming."
      );
      add(
         "best custom skill for bosses|boss custom skill|raid custom skill",
         "Raphael: Against bosses, Axiom is strong if the boss feeds damage memories, Mahoraga is strong if the fight lasts long enough, Caedros is strong with Guilt and Bang, and Kronairos is strong if the boss can be controlled or drained."
      );
      add(
         "best custom skill for farming|farming custom skill|kill farm skill",
         "Raphael: For farming, Mammon Casino rewards kills with chips, Caedros clears groups with Plague, Bang, and Construct, while Adaptability and Mahoraga are safer but slower."
      );
      add(
         "best custom skill for survival|survival custom skill|unkillable custom skill",
         "Raphael: For survival, Mahoraga and Adaptability are strongest over time, Axiom has Inversion and damage reduction, Caedros Death Aspect can revive with soul points, and Kronairos can sustain through Divine Lifesteal."
      );
      add(
         "best custom skill for control|control custom skill|cc custom skill",
         "Raphael: For control, Kronairos uses Room and Isolation, Caedros uses Control/Famine/Fang and Serpent, Istaroth uses time denial, and Axiom uses Mirrorfield and projectile reversal."
      );
      add(
         "best custom skill for damage|damage custom skill|burst custom skill",
         "Raphael: For burst, Caedros Bang and Total War Bang are the most explosive, Axiom Planetfall and Truth are stored-damage nukes, Kronairos drains while controlling, and Mahoraga scales damage after adaptation stages."
      );
      add(
         "axiom caedros mahoraga kronairos mammon adaptability|custom skill comparison|compare custom skills",
         "Raphael: Axiom reflects, Caedros conquers, Mahoraga adapts, Kronairos confines and drains, Mammon Casino gambles and snowballs from kills, and Adaptability is the pre-Ultimate adaptation path."
      );
      add(
         "custom skill counters|counter custom skills|beat custom ultimates",
         "Raphael: Counter custom ultimates by identifying their win condition: stop Axiom from storing damage, starve Caedros of Guilt, burst Mahoraga before adaptation, escape Kronairos Room/Isolation, deny Mammon Casino kills, and vary damage against Adaptability."
      );
      add(
         "custom skill beginner advice|new to moreskills|new to tenaddex",
         "Raphael: Start with Adaptability or a safer defensive skill if you die often. Choose Mammon Casino for farming, Axiom for counterplay, Kronairos for lockdown, Mahoraga for long fights, and Caedros only if you can manage Guilt."
      );
      add(
         "custom skill resource management|manage resources custom skills|moreskills resources",
         "Raphael: Custom skills are resource-heavy. Axiom needs magicules and damage memory, Caedros needs Guilt, Kronairos needs magicules, Mammon Casino needs chips, and Mahoraga needs time and repeated exposure."
      );
      add(
         "custom skill mastery priority|which custom skill master first|mastery custom skill",
         "Raphael: Master prerequisites first: Reflector for Axiom, Greed for Mammon Casino, Infinity Prison for Kronairos, and Adaptability for Mahoraga. Mastery usually improves caps, cooldowns, effects, or survivability."
      );
      add(
         "custom skill anti projectile|projectile counter custom|reflect projectile custom",
         "Raphael: Axiom is the strongest custom projectile counter because it reverses projectiles while toggled. Istaroth-style time skills can stop projectiles, while barriers and spatial movement avoid them."
      );
      add(
         "custom skill anti drain|counter drain custom|resist lifesteal",
         "Raphael: To counter drain skills like Kronairos or resource-drain builds, use Energy Drain resistance, high resource regeneration, burst pressure, distance, and avoid extended trapped fights."
      );
      add(
         "custom skill anti casino|deny chips|stop mammon chips",
         "Raphael: Stop Mammon Casino by denying kill chains. Do not feed it weak enemies, summons, or repeated easy kills if the casino system rewards them."
      );
      add(
         "custom skill anti adaptation|deny adaptation|stop adaptation stacking",
         "Raphael: Stop Adaptability and Mahoraga by rotating elements, using burst, using execution, and switching damage types before they build strong reductions or nullifications."
      );
      add(
         "custom skill anti reflection|deny reflection|stop axiom memories",
         "Raphael: Stop Axiom by using controlled low hits, bypass damage, debuffs, resource drain, or waiting until Truth and Inversion are down before committing burst."
      );
      add(
         "custom skill anti conquest|deny conquest|stop caedros",
         "Raphael: Stop Caedros by cleansing fear and insanity, resisting Magic Interference, avoiding Bang lines, preventing Total War, and forcing Guilt starvation."
      );
      add(
         "custom skill anti isolation|escape isolation|counter room",
         "Raphael: Counter Isolation and Room with spatial resistance, teleport immunity, cleanse if supported, burst before the lock completes, and staying outside Kronairos's domain range."
      );
      add(
         "custom skill combo axiom mahoraga|axiom mahoraga combo|reflection adaptation combo",
         "Raphael: Axiom plus Mahoraga-style defenses would be a brutal long-fight combo: Axiom stores and reflects damage while Mahoraga adapts to repeated attacks."
      );
      add(
         "custom skill combo caedros mammon|caedros mammon combo|conquest greed combo",
         "Raphael: Caedros plus Mammon Casino-style greed is a snowball combo: Caedros conquers and clears enemies while Mammon Casino rewards kill chains with chips."
      );
      add(
         "custom skill combo kronairos axiom|kronairos axiom combo|isolation reflection combo",
         "Raphael: Kronairos plus Axiom is a trap-and-punish combo: Kronairos prevents escape while Axiom reflects damage and replays stored judgment."
      );
      add(
         "custom skill combo kronairos mahoraga|kronairos mahoraga combo|drain adaptation combo",
         "Raphael: Kronairos plus Mahoraga is a long-fight monster combo: Kronairos drains and isolates while Mahoraga adapts to whatever the enemy tries."
      );
      add(
         "custom skill combo caedros kronairos|caedros kronairos combo|conquest isolation combo",
         "Raphael: Caedros plus Kronairos combines conquest and confinement. Caedros pressures everyone, while Kronairos locks down priority targets who try to escape."
      );
      add(
         "custom skill combo adaptability blade|adaptability blade combo|mahoraga blade combo",
         "Raphael: Adaptability or Mahoraga with Extermination Blade rewards melee pressure. Survive repeated hits, adapt to threats, then use the blade's stored EP and Severance synergy to punish tanks."
      );
      add(
         "custom ultimate weakness|weakness of custom skills|custom skill downside",
         "Raphael: Every custom ultimate has a weakness: Axiom needs stored pressure, Caedros needs Guilt, Mahoraga needs time, Kronairos needs control windows, Mammon Casino needs kills, and Adaptability can be burst before scaling."
      );
      add(
         "custom ultimate safe play|play safe custom ultimate|safe custom build",
         "Raphael: Safe custom play means keeping resources high, not wasting long cooldowns, watching Spiritual Health, and using the skill's main win condition only when the target cannot escape or punish you."
      );
      add(
         "custom ultimate aggressive play|aggressive custom build|rush custom ultimate",
         "Raphael: Aggressive custom play works best with Caedros, Kronairos, or Axiom burst windows. Engage when your resource engine is ready and enemy counters are down."
      );
      add(
         "custom ultimate defensive play|defensive custom build|turtle custom ultimate",
         "Raphael: Defensive custom play favors Mahoraga, Adaptability, Axiom, and Caedros Death Aspect. Stack resistances, barriers, regeneration, and force enemies to overcommit."
      );
      add(
         "how to counter melee|counter fighter|fight melee enemy",
         "Raphael: Counter melee enemies with movement, range, barriers, slows, dodge, knockback control, and debuffs that reduce attack speed or mobility."
      );
      add(
         "how to counter tank|counter tank|fight tank",
         "Raphael: Counter tanks with bypass effects, spiritual damage, corrosion, resource drain, barrier piercing, or sustained damage that outlasts regeneration."
      );
      add(
         "how to counter speed|counter speed|fast enemy",
         "Raphael: Counter speed enemies with area control, slowing effects, prediction, barriers, auto-targeting projectiles, and movement interference."
      );
      add(
         "how to counter summoner|counter summons|too many summons",
         "Raphael: Counter summons with area damage, owner targeting, crowd control, anti-summon effects, and resource pressure on the summoner."
      );
      add(
         "how to counter spatial|counter teleport|enemy teleports",
         "Raphael: Counter teleportation with spatial blockade, prediction, area damage, barriers, and attacks that do not require precise line-of-sight."
      );
      add(
         "how to counter time|counter time stop|enemy stops time",
         "Raphael: Counter time effects with time resistance, higher authority, prediction, passive defenses, and avoiding fights where you cannot act during time stop."
      );
      add(
         "how to counter poison|counter poison|poison enemy",
         "Raphael: Counter poison with abnormal condition resistance, cleansing, regeneration, ranged combat, and avoiding prolonged exposure."
      );
      add(
         "how to counter mind control|counter hypnosis|counter charm",
         "Raphael: Counter mental control with mental attack resistance, abnormal nullification, cleanse effects, and avoiding enemies with control-based skills."
      );
      add(
         "how to counter spiritual damage|counter soul damage|spiritual enemy",
         "Raphael: Counter spiritual damage with Spiritual Health protection, spiritual attack resistance, high existence, regeneration, and avoiding repeated direct hits."
      );
      add(
         "why wiki failed|wiki didnt work|wiki not found|could not find records",
         "Raphael: Wiki lookup can fail if the page title differs, the wiki blocks requests, search returns no results, the page is named differently, or records are unavailable. Try the exact page name."
      );
      add(
         "exact page name|page title|wiki title|wrong title",
         "Raphael: Exact page titles matter. If a wiki answer fails, ask with the exact skill name as shown on the wiki, including commas or subtitles like 'Mammon, Lord Of Greed'."
      );
      add(
         "ask exact|how should i ask|better question",
         "Raphael: For best results, ask with the exact skill name and what you want: 'What are Mammon, Lord Of Greed passives?' or 'How do I obtain Spatial Motion?'"
      );
      add(
         "safe answer|dont know|unknown skill|not in records",
         "Raphael: If I do not have records for a skill, the safe answer is that I do not know. Guessing exact mechanics would be unreliable."
      );
      add(
         "short answer|summarize|summary|simple explanation",
         "Raphael: I will summarize mechanics briefly. For exact values, ask for passives, actives, cooldowns, obtain method, or mastery effects."
      );
      add(
         "reflection|reflector|reflector unique|reflector skill|reflect damage|reflection unique",
         "Raphael: Reflector is the Unique Skill path that leads into Axiom, Lord of Reflected Judgment. It is a reflection-style ability focused on countering enemy damage, projectiles, and direct aggression."
      );
      add(
         "reflector build|best reflector build|reflector gear|reflector stats",
         "Raphael: Reflector builds should stack durability, barriers, regeneration, projectile defense, and enough sustain to survive hits before reflecting pressure back. It is a counterattack skill, not a pure burst skill."
      );
      add(
         "counter reflector|beat reflector|fight reflector|reflector counter",
         "Raphael: Counter Reflector by avoiding reckless burst, using bypass effects, resource drain, debuffs, and attacks that do not feed easy reflection value."
      );
      add(
         "adaptability unique|adaptability|adaptability skill|adaptive unique|adapt skill|damage learning unique",
         "Raphael: Adaptability is a Unique Skill that learns from repeated damage. It reduces damage from repeated sources, can gain severance immunity after enough exposure, grants mastery progress while toggled, and summons the Extermination Blade."
      );
      add(
         "adaptability obtain|how to get adaptability|adaptability cost|adaptability requirement",
         "Raphael: Adaptability costs 90,000 magicules to acquire and has 2,000 max mastery. It is the prerequisite path for Divine General Mahoraga."
      );
      add(
         "adaptability passive|adaptability toggle|adaptability toggled|adaptability damage reduction",
         "Raphael: While toggled, Adaptability records damage exposure, builds damage reduction stacks, clears severance damage after severance immunity is learned, and gives mastery progress over time."
      );
      add(
         "adaptability severance|severance immunity|adaptability severance immunity|adapt severance",
         "Raphael: Adaptability can learn severance immunity after enough Severance exposure. Once learned, it clears stored severance damage while toggled and becomes much safer against severing attacks."
      );
      add(
         "adaptability mode|adaptability modes|adaptability active|adaptability summon sword",
         "Raphael: Adaptability has one main active mode: Extermination Blade. It summons an adaptive blade that stores EP and becomes stronger with adaptation synergy."
      );
      add(
         "adaptability build|best adaptability build|adaptability gear|adaptability stats",
         "Raphael: Adaptability builds should focus on HP, regeneration, armor, broad resistances, Spiritual Health protection, and surviving repeated hits. It scales best in longer fights."
      );
      add(
         "counter adaptability|beat adaptability|fight adaptability|adaptability counterplay",
         "Raphael: Counter Adaptability by using burst damage, rotating damage types, using execution or spiritual pressure, and not repeating the same damage source long enough for it to adapt."
      );
      add(
         "extermination blade|adaptive blade|adaptability blade|mahoraga blade|extermination sword",
         "Raphael: Extermination Blade is the adaptive weapon used by Adaptability and Mahoraga. It stores EP, uses stored EP for bonus damage, can be protected as a special blade, and gains powerful enchantments."
      );
      add(
         "extermination blade enchants|extermination blade enchantments|adaptive blade enchants|mahoraga blade enchants",
         "Raphael: Extermination Blade can gain powerful enchantments such as Severance, Tsukumogami, Unbreaking, Sharpness, Dead End Rainbow, Soul Eater, Energy Steal, Holy Weapon, and Holy Coat depending on source and mastery."
      );
      add(
         "extermination blade ep|blade ep|extermination blade damage|adaptive blade damage",
         "Raphael: Extermination Blade stores EP as weapon energy. On hit, it consumes stored EP to add bonus damage. Severance adaptation improves the blade's bonus damage potential."
      );
      add(
         "counter extermination blade|beat extermination blade|fight adaptive blade",
         "Raphael: Counter Extermination Blade by staying out of melee, using disarm or control effects, kiting, using projectile pressure, and preventing long trades that feed adaptation."
      );
      add(
         "feldway angel summoner|angel summoner|feldway summoner|angel summon item|summoner item",
         "Raphael: Feldway Angel Summoner is a mode-based item that summons and commands Feldway Angels. Sneak-use cycles modes, then normal use performs the selected mode."
      );
      add(
         "feldway angel summoner modes|angel summoner modes|summoner item modes|feldway item modes",
         "Raphael: Feldway Angel Summoner has seven modes: Summon, Attack, Army, Despawn, Ultimate, Cathedral, and Immunity. Sneak-use cycles through these modes."
      );
      add(
         "angel summoner summon mode|feldway summon mode|summon mode angel item",
         "Raphael: Summon mode creates Feldway Angels at the targeted ground location after a summoning circle. It requires solid ground and respects the active summon limit."
      );
      add(
         "angel summoner attack mode|feldway attack mode|command angels attack",
         "Raphael: Attack mode is for commanding owned Feldway Angels toward combat. It works best when you already have active angels and want them focusing threats."
      );
      add(
         "angel summoner army mode|feldway army mode|army angel summon",
         "Raphael: Army mode summons multiple Feldway Angels up to the army summon cap. It is better for group fights and area pressure than single summon mode."
      );
      add(
         "angel summoner despawn mode|feldway despawn mode|dismiss angels|despawn angels",
         "Raphael: Despawn mode dismisses your owned Feldway Angels. If you have no owned summons nearby, it tells you there are no summons to dismiss."
      );
      add(
         "angel summoner ultimate mode|feldway ultimate mode|ultimate angel summon",
         "Raphael: Ultimate mode uses the Angel Summoner's highest-impact summon behavior. It requires solid ground and ignores the normal non-ultimate summon-cap check before activation."
      );
      add(
         "angel summoner cathedral mode|feldway cathedral mode|cathedral angels|angel cathedral",
         "Raphael: Cathedral mode creates a huge summoning circle and places angels around the target position in a wide formation. It is the large-scale formation mode."
      );
      add(
         "angel summoner immunity mode|feldway immunity mode|magicule poison immunity|angel item immunity",
         "Raphael: Immunity mode toggles Magicule Poison immunity data on the item. When enabled, the item can mark itself as providing Magicule Poison immunity support."
      );
      add(
         "angel summoner cooldown|feldway summoner cooldown|summon cooldown|angel item cooldown",
         "Raphael: Feldway Angel Summoner has item cooldown data. If cooldown is active, it displays the cooldown type and remaining seconds before another summon action can be used."
      );
      add(
         "angel summoner solid ground|need solid ground to summon|cant summon angels|angel summon failed",
         "Raphael: Feldway Angel Summoner needs solid ground under the target location. If the block below or two blocks below is not solid, summoning fails."
      );
      add(
         "angel summoner max summons|maximum angels|too many angels|angel summon limit",
         "Raphael: Feldway Angel Summoner checks nearby owned Feldway Angels within a large range. Summon, Army, and Cathedral modes cannot exceed their mode-specific active summon limit."
      );
      add(
         "angel summoner circle|angel magic circle|feldway summoning circle|otherworlder magic circle",
         "Raphael: The Angel Summoner creates an Otherworlder-variant magic circle at the target position, binds that circle to the item, and uses it to spawn angels when the summon completes."
      );
      add(
         "feldway angel|feldway angels|summoned angel|angel summon mob",
         "Raphael: Feldway Angels are owned summon entities created by the Angel Summoner. They start from a summoning formation, become owned by the player, and can retaliate against enemies that hurt the owner or targets the owner attacks."
      );
      add(
         "feldway angel attacks|angel attacks|feldway angel abilities|what do feldway angels do",
         "Raphael: Feldway Angels can use Solar Wave, Solar Beam, Holy Field, Flash Burst, Wind Blade, Wind Sphere, Spatial Ray, Dimension Cut, and melee attacks depending on range and cooldown."
      );
      add(
         "feldway angel solar wave|solar wave angel|angel solar wave",
         "Raphael: Solar Wave is a Feldway Angel ranged holy/light-style attack used when conditions and cooldown allow. It is part of the angel's long-range pressure kit."
      );
      add(
         "feldway angel solar beam|solar beam angel|angel solar beam",
         "Raphael: Solar Beam is a Feldway Angel beam attack used around mid range. It gives the angel direct ranged burst pressure against its target."
      );
      add(
         "feldway angel holy field|holy field angel|angel holy field",
         "Raphael: Holy Field is a Feldway Angel area attack used at closer range. It pressures enemies around the target area with holy-style control."
      );
      add(
         "feldway angel flash burst|flash burst angel|angel flash burst",
         "Raphael: Flash Burst is a close-range Feldway Angel burst attack. It is used when enemies get near the angel."
      );
      add(
         "feldway angel wind blade|wind blade angel|angel wind blade",
         "Raphael: Wind Blade is a Feldway Angel ranged wind attack. It helps the angel pressure targets without needing melee range."
      );
      add(
         "feldway angel wind sphere|wind sphere angel|angel wind sphere",
         "Raphael: Wind Sphere is a Feldway Angel ranged area projectile-style attack used from longer range."
      );
      add(
         "feldway angel spatial ray|spatial ray angel|angel spatial ray",
         "Raphael: Spatial Ray is a Feldway Angel spatial attack. It is useful against targets that rely on distance or normal defenses."
      );
      add(
         "feldway angel dimension cut|dimension cut angel|angel dimension cut",
         "Raphael: Dimension Cut is a Feldway Angel spatial slash-style attack. It is one of the angel's stronger space-control attacks."
      );
      add(
         "feldway angel melee|angel melee|feldway angel close combat",
         "Raphael: Feldway Angels can melee when extremely close to a target, but their main danger comes from ranged holy, wind, and spatial abilities."
      );
      add(
         "feldway angel owner|angel owner|owned angel|angel protects owner",
         "Raphael: Feldway Angels track their master. They can target enemies that hurt the owner and enemies the owner attacks, making them good bodyguards and pressure summons."
      );
      add(
         "counter feldway angel|beat feldway angel|fight feldway angel|kill angels",
         "Raphael: Counter Feldway Angels with area damage, anti-summon pressure, holy/spatial resistance, mobility, burst damage before they chain abilities, and by targeting the summoner when possible."
      );
      add(
         "feldway angel summoner build|angel summoner build|best angel summoner build",
         "Raphael: Angel Summoner builds should focus on survivability, magicules, summon support, cooldown control, and staying alive while angels deal holy, wind, and spatial pressure."
      );
      add(
         "feldway boss|feldway|feldway fight|feldway boss fight|feldway boss entity",
         "Raphael: Feldway is a boss-style angelic enemy tied to the same angelic toolkit. Expect holy, wind, spatial attacks, summon pressure, and high-range ability usage."
      );
      add(
         "feldway boss attacks|feldway abilities|feldway boss abilities|what does feldway do",
         "Raphael: Feldway-style combat revolves around angelic ranged pressure: solar/light attacks, holy fields, wind projectiles, spatial rays, dimension cuts, and close-range burst if you get too near."
      );
      add(
         "counter feldway boss|beat feldway boss|fight feldway|feldway counter",
         "Raphael: Counter Feldway with holy resistance, spatial resistance, wind resistance, mobility, cleanse, ranged pressure, and area damage for summons. Avoid standing still inside holy or spatial pressure."
      );
      add(
         "feldway boss build|build to beat feldway|gear for feldway",
         "Raphael: To fight Feldway, build holy resistance, spatial resistance, wind resistance, strong healing, Spiritual Health protection, mobility, and enough burst to punish cooldown windows."
      );
      add(
         "feldway summon strategy|angel summon strategy|how to use feldway angels",
         "Raphael: Use Feldway Angels by summoning them on solid ground, keeping yourself safe, letting them punish enemies that hit you, and using Army or Cathedral mode for larger fights."
      );
      add(
         "feldway army strategy|angel army strategy|cathedral strategy",
         "Raphael: Army and Cathedral modes are best when fighting groups or bosses with adds. They create formation pressure and let multiple angels chain ranged attacks."
      );
      add(
         "angel summoner pvp|feldway summoner pvp|angel item pvp",
         "Raphael: In PvP, Angel Summoner is a pressure and distraction tool. Use angels to force movement, punish attackers, and create openings, but protect yourself from players who rush the summoner."
      );
      add(
         "angel summoner pve|feldway summoner pve|angel item pve",
         "Raphael: In PvE, Angel Summoner is strong for boss pressure and mob clearing. Army and Cathedral modes are better for large fights, while Summon mode is safer for normal combat."
      );
      add(
         "michael manas|michael summon|michael entity|angel summoner michael",
         "Raphael: The Angel Summoner stores Michael-related UUID data and can remove failed Michael entities if summoning fails. Michael appears as a special Manas-style summon/entity tied to high angelic authority."
      );
      add(
         "failed michael|remove failed michael|michael failed summon",
         "Raphael: If a Michael summon fails while still no-AI, the item removes the failed Michael entity, plays failure sound effects, spawns flash particles, and clears the stored Michael UUID."
      );
      add(
         "evil kit|evil kits|dark kit|villain kit|bad guy kit|corrupt kit",
         "Raphael: Evil kits usually rely on curses, fear, soul damage, drain, summons, poison, darkness, control, or execution. Counter them with cleanse, mental resistance, spiritual resistance, mobility, burst damage, and anti-summon tools."
      );
      add(
         "counter evil kit|beat evil kit|fight evil kit|evil kit counter",
         "Raphael: To counter an evil kit, identify its win condition first. If it drains, burst it. If it curses, cleanse. If it summons, kill the owner. If it executes, stay above threshold. If it controls, stack mental resistance."
      );
      add(
         "evil build|dark build|villain build|corruption build",
         "Raphael: Evil builds are strongest when they stack debuffs, fear, curse, drain, infection, darkness, and execution. Their weakness is usually cleanse, resistance stacking, holy damage, and burst before setup finishes."
      );
      add(
         "hero kit vs evil kit|good kit counter evil|holy counter evil",
         "Raphael: Hero or holy kits counter evil kits through cleanse, holy damage, resistance, barriers, and anti-curse effects. Do not let evil builds extend the fight long enough to stack debuffs."
      );
      add(
         "holy kit|angel kit|light kit|paladin kit",
         "Raphael: Holy kits are strong against demons, undead, curses, and dark builds. They usually focus on barriers, purification, light damage, cleanse, and spiritual protection."
      );
      add(
         "counter holy kit|beat holy kit|fight holy kit|anti holy",
         "Raphael: Counter holy kits with mobility, magic interference, resource drain, darkness resistance, burst windows after barriers drop, and avoiding predictable direct trades."
      );
      add(
         "demon kit|demonic kit|demon build|hell kit",
         "Raphael: Demon kits usually focus on magicules, dark damage, curses, fear, fire, and spiritual pressure. They are strong in long fights but often weak to holy resistance and purification."
      );
      add(
         "counter demon kit|beat demon kit|fight demon build",
         "Raphael: Counter demon kits with holy resistance, holy damage, mental resistance, curse cleanse, fire/dark resistance, and pressure that prevents them from freely casting."
      );
      add(
         "undead kit|death kit|necromancer kit|zombie kit|skeleton kit",
         "Raphael: Undead and necromancer kits rely on summons, lifesteal, decay, fear, and attrition. Their weakness is holy damage, area damage, anti-summon pressure, and killing the controller."
      );
      add(
         "counter undead kit|counter necromancer|beat necromancer kit|fight undead",
         "Raphael: Counter necromancers by killing summons with area damage, rushing the owner, using holy attacks, preventing corpse value, and avoiding long fights where their army grows."
      );
      add(
         "summon spam|summoner spam|too many summons|minion spam|mob army",
         "Raphael: Summon spam is countered by area damage, owner targeting, crowd control, anti-summon effects, knockback, terrain abuse, and killing low-HP minions before they surround you."
      );
      add(
         "counter summon spam|beat summon spam|anti summon spam|summon counter",
         "Raphael: Do not chase every summon forever. Clear enough space to move, then pressure the summoner. Summon builds collapse if the owner cannot safely command or resummon."
      );
      add(
         "assassin kit|stealth kit|shadow assassin|invisible kit|backstab kit",
         "Raphael: Assassin kits rely on stealth, burst, speed, debuffs, and attacking first. They are dangerous if you lack detection or reaction tools."
      );
      add(
         "counter assassin kit|beat assassin|anti assassin|fight stealth",
         "Raphael: Counter assassins with Presence Sense, Magic Sense, area damage, armor, reaction passives, knockback resistance, traps, and staying above burst-execution thresholds."
      );
      add(
         "invisibility counter|counter invis|invisible enemy|cant see enemy",
         "Raphael: Counter invisibility with particles, hitboxes, area attacks, Presence Sense, Magic Sense, splash effects, fire, poison clouds, and watching footsteps or projectile origin."
      );
      add(
         "trap kit|trap build|web trap|cage kit|prison kit",
         "Raphael: Trap kits win by locking movement with webs, spatial blockade, gravity, terrain, or cages. If trapped, cleanse, teleport, break blocks, use area damage, or force the trapper to retreat."
      );
      add(
         "counter trap kit|beat trapper|anti trap|escape trap",
         "Raphael: Counter trappers with teleportation, spatial resistance, mining tools, pearls if allowed, knockback, cleanse, and never chasing them into prepared terrain."
      );
      add(
         "grief kit|explosion kit|terrain destruction kit|block break kit",
         "Raphael: Grief kits use terrain destruction, explosions, lava, fall traps, and block denial. Counter them by fighting away from bases, using blast resistance, mobility, and server claim protection."
      );
      add(
         "counter explosion kit|beat explosion build|blast counter|anti explosion",
         "Raphael: Counter explosions with blast resistance, distance, barriers, knockback resistance, water placement, shields if relevant, and avoiding confined spaces."
      );
      add(
         "lava kit|fire kit|burn kit|magma kit",
         "Raphael: Lava and fire kits use burn damage, area denial, and panic. Counter with fire resistance, water, mobility, cleanse, and not fighting in cramped terrain."
      );
      add(
         "counter lava kit|counter fire kit|anti fire build|beat burn kit",
         "Raphael: Fire Resistance is the main counter. Also use water buckets, movement skills, blocks, ranged attacks, and punish fire users when their area control is wasted."
      );
      add(
         "poison kit|toxin kit|venom kit|infection kit",
         "Raphael: Poison and infection kits win through damage over time and debuff stacking. Counter them with abnormal nullification, poison resistance, cleanse, burst damage, and spacing."
      );
      add(
         "counter poison kit|counter infection kit|anti poison|anti infection",
         "Raphael: Cleanse early, do not let stacks build, use poison resistance, stay mobile, and kill the user before the damage-over-time engine overwhelms your healing."
      );
      add(
         "curse kit|hex kit|debuff kit|curse build",
         "Raphael: Curse kits weaken healing, damage, movement, or defenses over time. They are strong against tanks that cannot cleanse."
      );
      add(
         "counter curse kit|anti curse|beat curse build|curse counter",
         "Raphael: Counter curses with cleanse, curse resistance, holy effects, burst damage, and avoiding long trades. If your healing is blocked, disengage immediately."
      );
      add(
         "fear kit|insanity kit|mind kit|mental kit|control kit",
         "Raphael: Mental kits use fear, insanity, hypnosis, confusion, charm, or mind control. They punish players without mental resistance."
      );
      add(
         "counter mental kit|counter fear kit|anti mind control|beat mind kit",
         "Raphael: Counter mental kits with mental attack resistance, abnormal nullification, cleanse, long-range pressure, and passive defenses that still work while controlled."
      );
      add(
         "silence kit|anti mage kit|magic interference kit|caster counter kit",
         "Raphael: Silence and Magic Interference kits shut down casters. They are dangerous for magic builds but weaker against physical or aura-based fighters."
      );
      add(
         "counter silence kit|counter magic interference|anti anti mage",
         "Raphael: Counter silence with melee backup, aura skills, cleanse, resistance, mobility, and casting before the enemy enters silence range."
      );
      add(
         "drain kit|lifesteal kit|magicule drain kit|aura drain kit|resource drain",
         "Raphael: Drain kits win by stealing HP, aura, magicules, or spiritual resources. They become stronger the longer the fight lasts."
      );
      add(
         "counter drain kit|anti lifesteal|beat drain build|resource drain counter",
         "Raphael: Counter drain with burst damage, anti-heal, distance, resource regeneration, barriers, and disengaging before your aura or magicules hit zero."
      );
      add(
         "execution kit|instant death kit|one shot kit|death mark kit",
         "Raphael: Execution kits punish low HP, marked targets, or weak Spiritual Health. They are strongest when enemies panic and stay in kill range."
      );
      add(
         "counter execution kit|anti execute|counter instant death|beat one shot kit",
         "Raphael: Stay above execution threshold, cleanse marks, stack death and spiritual resistance, use barriers, and do not fight while low on Spiritual Health."
      );
      add(
         "tank kit|immortal kit|unkillable kit|regen kit",
         "Raphael: Tank kits rely on armor, barriers, regeneration, resistance, and sustain. They are hard to burst but can be drained or bypassed."
      );
      add(
         "counter tank kit|anti tank|beat immortal build|kill regen build",
         "Raphael: Counter tanks with corrosion, spiritual damage, resource drain, barrier piercing, anti-heal, execution, and sustained pressure that outpaces regeneration."
      );
      add(
         "speed kit|runner kit|mobility kit|teleport kit|dash kit",
         "Raphael: Speed kits win by avoiding hits, kiting, and picking when to engage. They are weak to slows, area control, prediction, and spatial blockade."
      );
      add(
         "counter speed kit|anti speed|beat runner|catch fast player",
         "Raphael: Counter speed with slowing effects, wide-area attacks, auto-targeting, traps, prediction, teleport counters, and forcing them into enclosed spaces."
      );
      add(
         "archer kit|bow kit|sniper kit|projectile kit",
         "Raphael: Archer and sniper kits rely on range, projectile speed, knockback, and terrain advantage. They lose value if forced into close range or denied line of sight."
      );
      add(
         "counter archer kit|anti bow|beat sniper|counter projectile spam",
         "Raphael: Counter archers with shields, projectile reflection, barriers, strafing, cover, teleportation, speed, and closing distance unpredictably."
      );
      add(
         "trident kit|riptide kit|loyalty trident|water pvp kit",
         "Raphael: Trident kits are dangerous near water and storms. They combine burst, mobility, and ranged pressure depending on enchantments."
      );
      add(
         "counter trident kit|anti trident|beat riptide",
         "Raphael: Counter tridents by leaving water, using projectile defense, cover, lightning resistance if needed, and punishing missed throws or bad riptide angles."
      );
      add(
         "crossbow kit|piercing kit|multishot kit|firework crossbow",
         "Raphael: Crossbow kits can burst hard with fireworks or piercing shots. They are dangerous at mid range and around corners."
      );
      add(
         "counter crossbow kit|anti crossbow|beat firework crossbow",
         "Raphael: Counter crossbows with blast protection, cover, rush timing after shots, projectile reflection, and not standing in tight groups."
      );
      add(
         "elytra kit|flying kit|air kit|sky kit",
         "Raphael: Elytra or flight kits use height, speed, and diving attacks. They are strong in open terrain but weaker in caves or enclosed arenas."
      );
      add(
         "counter elytra kit|anti flight|beat flying player|air counter",
         "Raphael: Counter flyers with ranged pressure, gravity, wind control, prediction, anti-projectile timing, ceiling terrain, and forcing fights indoors."
      );
      add(
         "end crystal kit|crystal pvp|crystal kit|crystal spam",
         "Raphael: Crystal kits rely on massive explosion burst and placement speed. They are extremely dangerous in open end-stone or obsidian terrain."
      );
      add(
         "counter crystal kit|anti crystal|beat crystal pvp",
         "Raphael: Counter crystal kits with blast protection, distance, breaking crystals early, shields if useful, movement, not standing near obsidian, and punishing failed placements."
      );
      add(
         "totem kit|totem spam|undying kit|revive kit",
         "Raphael: Totem kits survive lethal burst and reset fights. They are weak to repeated pressure, cooldown denial, inventory pressure, and spiritual or execution effects if supported."
      );
      add(
         "counter totem kit|anti totem|beat totem spam",
         "Raphael: Counter totems by forcing multiple pops quickly, using follow-up burst, disabling escape, applying debuffs after the pop, and pressuring inventory recovery."
      );
      add(
         "shield kit|shield turtle|shield user|blocking kit",
         "Raphael: Shield kits block arrows and frontal damage but are weak to axes, magic, explosions, flanking, spiritual damage, and attacks from behind."
      );
      add(
         "counter shield kit|anti shield|beat shield user",
         "Raphael: Counter shields with axe pressure, flank attacks, splash effects, magic, knockback, fire, explosions, or attacks that bypass blocking."
      );
      add(
         "netherite kit|heavy armor kit|max armor player|prot armor",
         "Raphael: Heavy armor kits are durable but can be countered by armor bypass, spiritual damage, magic, corrosion, fall traps, resource drain, and mobility denial."
      );
      add(
         "counter netherite kit|anti netherite|beat max armor|counter prot armor",
         "Raphael: Counter max armor with armor-piercing damage, magic, spiritual attacks, potion effects, void/fall traps, durability pressure, and avoiding raw melee trades."
      );
      add(
         "mace kit|mace player|wind charge mace|smash attack",
         "Raphael: Mace kits rely on height-based burst. They are dangerous if they land the smash but vulnerable if you deny vertical setup."
      );
      add(
         "counter mace kit|anti mace|beat mace player",
         "Raphael: Counter mace users by staying under cover, moving unpredictably, using knockback, projectile pressure, slow effects, and not standing below them."
      );
      add(
         "potion kit|potion spam|splash potion kit|alchemy kit",
         "Raphael: Potion kits use buffs, debuffs, splash damage, invisibility, speed, strength, poison, weakness, or slowness. They win through preparation."
      );
      add(
         "counter potion kit|anti potion|beat potion spam",
         "Raphael: Counter potion kits with milk if allowed, cleanse effects, distance, burst damage before rebuffing, and not letting them reset behind cover."
      );
      add(
         "milk bucket|milk cleanse|remove effects|minecraft cleanse",
         "Raphael: Milk can remove many Minecraft potion effects, but modded effects may require special cleanse or resistance. Use it carefully because it can also remove your buffs."
      );
      add(
         "water bucket|mlg water|bucket clutch|water counter",
         "Raphael: Water buckets counter fall damage, fire, lava, and some terrain traps. Keep one ready unless your server or dimension blocks water placement."
      );
      add(
         "lava bucket|lava trap|lava pvp|bucket lava",
         "Raphael: Lava is strong area denial. Counter with water, fire resistance, blocks, movement skills, and not chasing enemies into narrow corridors."
      );
      add(
         "cobweb kit|web kit|web trap|cobweb pvp",
         "Raphael: Cobweb kits trap movement and enable burst. Counter with shears, fire, water, teleportation, knockback, and avoiding predictable melee paths."
      );
      add(
         "counter cobweb|anti web|escape cobweb|beat web kit",
         "Raphael: Carry shears or a sword, use teleport/mobility, place water, burn the web if safe, and do not let the enemy combo you while stuck."
      );
      add(
         "pearl kit|ender pearl kit|pearl escape|pearl rush",
         "Raphael: Pearl kits rely on instant repositioning. They are strong for escapes, chases, and surprise engages."
      );
      add(
         "counter pearls|anti pearl|beat pearl user|stop teleport",
         "Raphael: Counter pearls with spatial blockade, prediction, area damage at landing, projectile pressure during throw, and punishing pearl damage or cooldown."
      );
      add(
         "chorus fruit kit|chorus escape|random teleport",
         "Raphael: Chorus fruit creates unpredictable escape movement. It is useful for leaving traps but unreliable for exact positioning."
      );
      add(
         "counter chorus fruit|anti chorus|beat chorus escape",
         "Raphael: Counter chorus by keeping pressure, using area detection, controlling terrain, and predicting that the enemy will reappear nearby rather than far away."
      );
      add(
         "warden kit|sonic boom kit|deep dark kit",
         "Raphael: Warden-style kits rely on sonic damage, darkness, tankiness, and fear pressure. Sonic attacks can bypass normal cover-style thinking."
      );
      add(
         "counter warden kit|anti sonic boom|beat warden style",
         "Raphael: Counter sonic pressure with mobility, distance, sound control, resistance, burst windows, and avoiding straight-line predictable movement."
      );
      add(
         "enderman kit|teleport mob kit|ender kit",
         "Raphael: Ender kits use teleportation, mobility, and surprise attacks. They are strongest when enemies cannot track movement."
      );
      add(
         "counter enderman kit|anti ender|beat teleport mob",
         "Raphael: Counter ender kits with water, spatial blockade, area damage, prediction, and keeping your back protected."
      );
      add(
         "creeper kit|explosive mob kit|charged creeper kit",
         "Raphael: Creeper-style kits use burst explosions and fear pressure. They punish players who tunnel vision or stand too close."
      );
      add(
         "counter creeper kit|anti creeper|beat explosive mob",
         "Raphael: Counter creeper kits with knockback, blast protection, ranged attacks, shields, water, and staying outside detonation range."
      );
      add(
         "skeleton kit|bow mob kit|ranged mob kit",
         "Raphael: Skeleton-style kits use ranged pressure and knockback. They are weak if rushed, blocked, reflected, or forced into melee."
      );
      add(
         "zombie kit|horde kit|swarm kit|meat shield kit",
         "Raphael: Zombie or horde kits use numbers, body blocking, and attrition. Counter with area damage, fire, knockback, and terrain chokepoints."
      );
      add(
         "spider kit|poison spider kit|web spider kit",
         "Raphael: Spider kits use mobility, poison, webs, and wall pressure. Counter with poison resistance, anti-web tools, and area knockback."
      );
      add(
         "blaze kit|nether fire kit|fireball kit",
         "Raphael: Blaze kits use fireballs, ranged fire pressure, and air movement. Counter with fire resistance, projectile reflection, cover, and water if available."
      );
      add(
         "ghast kit|large fireball kit|explosive ranged kit",
         "Raphael: Ghast-style kits use explosive projectiles from range. Counter with reflection, cover, fast ranged attacks, and closing distance."
      );
      add(
         "phantom kit|air dive kit|night flyer kit",
         "Raphael: Phantom kits use aerial dives and harassment. Counter with ranged attacks, ceilings, shields, and prediction."
      );
      add(
         "slime kit|knockback kit|bounce kit",
         "Raphael: Slime kits use knockback, splitting pressure, and area nuisance. Counter with sweeping attacks, fire, and controlling where you stand."
      );
      add(
         "witch kit|witch potion kit|debuff mob kit",
         "Raphael: Witch kits use poison, weakness, harming, and healing. Counter with burst damage, cleanse, poison resistance, and preventing potion resets."
      );
      add(
         "ravager kit|charge kit|knockback beast kit",
         "Raphael: Ravager-style kits use charge, knockback, and high melee pressure. Counter with range, slows, terrain, and not fighting in open lanes."
      );
      add(
         "pillager kit|raider kit|crossbow mob kit",
         "Raphael: Pillager kits use crossbow pressure and group tactics. Counter with shields, cover, rush timing, and area damage."
      );
      add(
         "evoker kit|vex kit|fang kit|evocation kit",
         "Raphael: Evoker kits use summoned vexes and fang attacks. Counter with mobility, area damage, killing the caster, and avoiding straight predictable movement."
      );
      add(
         "vex kit|small summon kit|flying summon kit",
         "Raphael: Vex-style summons are hard to hit and pressure through walls. Counter with area damage, thorns-style effects, shields, and killing the summoner quickly."
      );
      add(
         "wither kit|wither effect kit|wither skull kit",
         "Raphael: Wither kits use decay damage, explosions, and ranged pressure. Counter with wither resistance, regeneration, milk or cleanse, and ranged mobility."
      );
      add(
         "counter wither kit|anti wither|beat wither skulls",
         "Raphael: Counter wither damage with cleanse, regeneration, ranged cover, projectile defense, and avoiding clustered allies."
      );
      add(
         "shulker kit|levitation kit|shulker bullet kit",
         "Raphael: Shulker kits use levitation and tracking projectiles. Counter with projectile destruction, feather falling, slow falling, roofs, and ranged pressure."
      );
      add(
         "counter levitation|anti shulker|beat levitation",
         "Raphael: Counter levitation with roofs, slow falling, pearls, water clutching, feather falling, and killing the caster before you float into danger."
      );
      add(
         "guardian kit|laser kit|ocean kit|elder guardian kit",
         "Raphael: Guardian kits use beams, mining fatigue, and underwater advantage. Counter with water breathing, depth strider, ranged burst, and leaving water if possible."
      );
      add(
         "counter ocean kit|anti water kit|beat water build",
         "Raphael: Counter water builds by fighting on land, using lightning, ranged pressure, mobility, and denying them water movement advantages."
      );
      add(
         "nether kit|nether build|basalt kit|lava terrain kit",
         "Raphael: Nether kits use lava, fire, vertical terrain, and blast pressure. Counter with fire resistance, blocks, pearls, and not fighting near cliffs or lava lakes."
      );
      add(
         "end kit|void kit|end island kit|void trap",
         "Raphael: End or void kits use knockback, pearls, crystals, and void pressure. Counter with slow falling, pearls, knockback resistance, blocks, and staying away from edges."
      );
      add(
         "void counter|anti void|counter knock into void|void survival",
         "Raphael: Counter void threats with ender pearls, elytra, slow falling, blocks, knockback resistance, teleport skills, and never fighting with your back to an edge."
      );
      add(
         "base raid|raiding kit|base defense|base attack",
         "Raphael: Base raids are won by preparation: claims, traps, escape routes, backup gear, anti-explosion defense, hidden storage, and knowing enemy kits before they arrive."
      );
      add(
         "defend base|base defense kit|protect base|anti raid",
         "Raphael: Defend a base with claims, obsidian or reinforced blocks, water layers, escape tunnels, anti-teleport zones, alarms, and gear sets for different enemy kits."
      );
      add(
         "raid enemy base|attack base|base raiding",
         "Raphael: Raid carefully. Scout first, identify claims, traps, respawn points, defenders, escape routes, and whether destructive skills are allowed."
      );
      add(
         "trap base|defensive trap|minecraft trap|kill trap",
         "Raphael: Good traps use surprise, movement denial, fall damage, lava, suffocation, explosions, or mob pressure. Strong players counter traps with pearls, blocks, and detection."
      );
      add(
         "counter traps base|anti trap base|survive trap",
         "Raphael: Counter traps by carrying blocks, pearls, water, fire resistance, pickaxe, shears, slow falling, and never entering unknown bases without testing the floor and walls."
      );
      add(
         "kit weakness|find kit weakness|counter build weakness",
         "Raphael: Every kit has a weakness. Burst beats scaling, cleanse beats debuffs, area damage beats summons, mobility beats melee, control beats speed, bypass beats tanks, and resistance beats element spam."
      );
      add(
         "counter any kit|universal counter|general pvp counter",
         "Raphael: A universal counter loadout needs mobility, cleanse, ranged option, melee option, emergency heal, escape tool, resistance gear, and one way to stop summons or projectiles."
      );
      add(
         "pvp checklist|before pvp|prepare for pvp",
         "Raphael: Before PvP, check armor durability, food, healing, cleanse, escape tool, ranged weapon, blocks, water, fire resistance, cooldowns, and whether your Spiritual Health is safe."
      );
      add(
         "boss checklist|before boss|prepare boss minecraft",
         "Raphael: Before a boss, bring healing, food, blocks, ranged damage, mobility, resistances, backup gear, cleanse, and an escape plan. Never enter a boss fight with low resources."
      );
      add(
         "inventory setup|pvp inventory|hotbar setup|combat hotbar",
         "Raphael: A strong hotbar has weapon, ranged option, blocks, healing, water, escape tool, utility item, cleanse item, and food. Keep emergency tools in easy slots."
      );
      add(
         "armor durability|gear durability|repair armor|mending gear",
         "Raphael: Watch durability before long fights. A broken helmet or chestplate can instantly turn a winning fight into death. Repair gear before bosses or PvP."
      );
      add(
         "food saturation|best food|combat food|hunger",
         "Raphael: High-saturation food keeps natural regeneration active. If hunger drops, long fights become much harder, especially against poison, wither, or drain builds."
      );
      add(
         "blocks in pvp|building in combat|combat blocks",
         "Raphael: Blocks are defensive tools. Use them for cover, height, trap escape, line-of-sight breaking, lava blocking, crystal denial, and creating breathing room."
      );
      add(
         "line of sight|los|break line of sight|cover",
         "Raphael: Breaking line of sight counters bows, beams, magic, projectiles, and some boss attacks. Use blocks, terrain, pillars, and corners to force enemies closer."
      );
      add(
         "height advantage|high ground|low ground|terrain advantage",
         "Raphael: High ground helps ranged pressure and vision but makes you vulnerable to knockback and fall setups. Low ground can provide cover but risks traps."
      );
      add(
         "cave fight|fight in cave|underground pvp",
         "Raphael: Cave fights favor traps, lava, shields, short-range burst, and area damage. Mobility and elytra matter less, while blocks and awareness matter more."
      );
      add(
         "open field fight|fight in open|plains pvp",
         "Raphael: Open fields favor speed, ranged attacks, elytra, summons, and projectile pressure. Bring cover blocks and mobility counters."
      );
      add(
         "forest fight|jungle fight|trees pvp",
         "Raphael: Forest fights favor ambush, traps, line-of-sight breaks, and short-range burst. Watch for hidden players and avoid getting stuck on leaves or vines."
      );
      add(
         "water fight|underwater fight|ocean pvp",
         "Raphael: Water fights favor tridents, Depth Strider, water breathing, conduits, and aquatic mobility. Without water gear, leave the water quickly."
      );
      add(
         "nether fight|fight in nether|lava pvp",
         "Raphael: Nether fights are lethal because lava and cliffs punish mistakes. Bring fire resistance, blocks, pearls, and avoid fighting near drops."
      );
      add(
         "end fight|fight in end|void pvp",
         "Raphael: End fights are decided by knockback, pearls, crystals, elytra, and void control. Always carry emergency movement and avoid edges."
      );
      add(
         "mob farm danger|farm safety|grinder safety",
         "Raphael: Mob farms can become dangerous if mobs escape, lag builds up, or players lure enemies into you. Keep kill switches, safe rooms, and clear exits."
      );
      add(
         "xp farm|ep farm|farm mobs|grinding mobs",
         "Raphael: For farming, prioritize area damage, sustain, durability, safe terrain, and avoiding enemies that apply dangerous spiritual or control effects."
      );
      add(
         "lag counter|too many mobs|server lag fight",
         "Raphael: If mobs or summons cause lag, reduce entity count, use area clearing, despawn unused summons, avoid particle spam, and fight away from farms."
      );
      add(
         "summon lag|too many summons lag|angel lag|necromancer lag",
         "Raphael: Summon-heavy builds can lag servers. Use summon caps, despawn modes, short lifetimes, and avoid leaving armies loaded when not fighting."
      );
      add(
         "grief prevention|protect world|skill grief off|block grief",
         "Raphael: To prevent destructive skills from ruining terrain, use claims, gamerules, skill grief restrictions, explosion protection, and safe arenas for boss fights."
      );
      add(
         "arena build|boss arena|pvp arena|fight arena",
         "Raphael: A good arena has cover, escape routes, enough space, no void/lava unless intentional, anti-cheese boundaries, and clear rules for destructive skills."
      );
      add(
         "anti cheese boss|boss cheese|cheese strategy",
         "Raphael: Boss cheese usually uses terrain, range abuse, traps, or AI limits. Strong bosses counter cheese with teleportation, ranged attacks, area damage, or block destruction."
      );
      add(
         "cheese evil kit|counter cheese|anti cheese pvp",
         "Raphael: Counter cheese by changing terrain, forcing movement, using ranged pressure, destroying trap setup, and refusing to fight where the enemy prepared."
      );
      add(
         "safe retreat|run away|escape fight|disengage",
         "Raphael: Retreat is correct if Spiritual Health, durability, food, or resources are low. Use blocks, pearls, teleport, smoke/particles, summons, or terrain to break chase."
      );
      add(
         "chasing enemy|enemy running|how to chase|catch runner",
         "Raphael: Chase with speed, prediction, ranged pressure, slows, teleportation, and cutting off exits. Do not chase into traps or unknown terrain."
      );
      add(
         "bait enemy|bait cooldown|force cooldown",
         "Raphael: Baiting cooldowns is powerful. Pretend to commit, dodge the enemy's major skill, then punish while it is unavailable."
      );
      add(
         "cooldown tracking|track cooldowns|enemy cooldown",
         "Raphael: Track enemy cooldowns by watching animations, particles, sounds, and repeated timing. Attack after their strongest skill misses."
      );
      add(
         "resource tracking|enemy low resources|aura low|magicules low",
         "Raphael: If an enemy stops casting, slows down, or retreats, they may be low on aura or magicules. Pressure them before they recover."
      );
      add(
         "spiritual health low|my spiritual health low|shp low",
         "Raphael: If Spiritual Health is low, stop trading hits. Retreat, cleanse, regenerate, and avoid soul, death, or spiritual attacks until stable."
      );
      add(
         "armor broke|gear broke|item broke in fight",
         "Raphael: If gear breaks mid-fight, disengage immediately. Swap backup armor, use barriers, or escape. Fighting without key armor is usually fatal."
      );
      add(
         "enemy has better gear|outgeared|gear gap",
         "Raphael: If outgeared, do not raw trade. Use traps, terrain, debuffs, resource drain, fall damage, lava, summons, or wait until you have counters."
      );
      add(
         "enemy higher ep|ep gap|enemy more ep",
         "Raphael: Against higher EP enemies, rely on counters, terrain, burst timing, debuffs, and escape routes. Do not fight fair unless your kit specifically counters theirs."
      );
      add(
         "low ep fighting high ep|weak vs strong|underdog fight",
         "Raphael: As the weaker player, win through preparation: traps, resistance, mobility, anti-heal, burst, and refusing long direct trades."
      );
      add(
         "kit synergy|combine kits|best kit combo",
         "Raphael: Strong kit synergy pairs one win condition with support. Example: summon plus debuff, speed plus burst, tank plus drain, holy plus cleanse, trap plus execution."
      );
      add(
         "bad kit combo|weak kit combo|kit doesnt work",
         "Raphael: A kit feels weak when it has no win condition, no defense, no mobility, or too many conflicting resources. Fix it by choosing one main strategy and supporting it."
      );
      add(
         "evil kit combo|best evil combo|dark combo",
         "Raphael: Strong evil combos include curse plus drain, fear plus execution, poison plus trap, summons plus darkness, and death marks plus movement control."
      );
      add(
         "anti evil loadout|loadout vs evil|gear vs evil kits",
         "Raphael: Anti-evil loadout should include holy resistance or holy damage, cleanse, mental resistance, spiritual resistance, poison/curse protection, mobility, and burst damage."
      );
      add(
         "anti summon loadout|gear vs summons|summon counter loadout",
         "Raphael: Anti-summon loadout should include sweeping or area damage, fire or holy effects, movement, owner-targeting pressure, knockback, and enough armor to survive chip damage."
      );
      add(
         "anti debuff loadout|gear vs debuffs|cleanse loadout",
         "Raphael: Anti-debuff loadout needs cleanse, abnormal nullification, poison resistance, curse resistance, mental resistance, and enough burst to stop the debuff user."
      );
      add(
         "anti burst loadout|gear vs one shot|survive burst",
         "Raphael: Anti-burst loadout needs barriers, totems or death prevention, high armor, Spiritual Health protection, resistance, and instant escape tools."
      );
      add(
         "anti tank loadout|gear vs tank|kill tank player",
         "Raphael: Anti-tank loadout needs corrosion, anti-heal, barrier piercing, spiritual damage, resource drain, execution, and enough sustain to keep pressure."
      );
      add(
         "anti mobility loadout|gear vs speed|catch mobility",
         "Raphael: Anti-mobility loadout needs slows, spatial blockade, area attacks, prediction, ranged pressure, and terrain control."
      );
      add(
         "anti projectile loadout|gear vs arrows|projectile defense",
         "Raphael: Anti-projectile loadout needs shields, projectile reflection, barriers, cover blocks, speed, and teleportation."
      );
      add(
         "anti magic loadout|gear vs magic|fight caster",
         "Raphael: Anti-magic loadout needs magic resistance, silence, Magic Interference, mobility, burst, line-of-sight breaks, and resource pressure."
      );
      add(
         "anti melee loadout|gear vs melee|fight sword user",
         "Raphael: Anti-melee loadout needs knockback, slows, ranged pressure, shields, traps, and mobility. Never let melee kits stay in ideal range."
      );
      add(
         "anti ranged loadout|gear vs ranged|fight bow user",
         "Raphael: Anti-ranged loadout needs cover, speed, teleportation, projectile defense, and burst once you close distance."
      );
      add(
         "minecraft kit tier|kit ranking|best minecraft kit",
         "Raphael: Kit strength depends on arena, gear, rules, and counters. Generally, mobility, cleanse, burst, sustain, and control are the most valuable traits."
      );
      add(
         "most dangerous kit|scariest kit|hardest kit to beat",
         "Raphael: The most dangerous kits combine burst, control, and escape. A kit that can trap you, kill you fast, and leave safely is more dangerous than raw damage alone."
      );
      add(
         "noob kit|beginner pvp kit|easy kit",
         "Raphael: Beginner kits should use simple melee, bow, shield, healing, blocks, water, and one escape option. Avoid complex drain or summon kits until you can manage cooldowns."
      );
      add(
         "advanced kit|hard kit|skill based kit",
         "Raphael: Advanced kits rely on timing, baiting, resource tracking, mode switching, and terrain control. They are strong only if the player manages them correctly."
      );
      add(
         "kit for solo|solo kit|alone kit",
         "Raphael: Solo kits need healing, mobility, damage, cleanse, and escape. You cannot rely on teammates to cover weaknesses."
      );
      add(
         "kit for team|team kit|group pvp kit",
         "Raphael: Team kits can specialize. One player controls, one bursts, one supports, one summons, and one tanks. Coordination matters more than everyone using the same kit."
      );
      add(
         "support kit|healer kit|buffer kit",
         "Raphael: Support kits win by keeping allies alive, cleansing debuffs, shielding burst, and enabling damage dealers. Stay protected because supports are priority targets."
      );
      add(
         "controller kit|cc kit|crowd control kit",
         "Raphael: Control kits win by denying enemy movement and actions. Their counters are cleanse, resistance, burst, and long-range pressure."
      );
      add(
         "bruiser kit|fighter kit|balanced melee kit",
         "Raphael: Bruiser kits mix damage and durability. They are reliable in most fights but can struggle against extreme mobility, range, or bypass damage."
      );
      add(
         "glass cannon kit|burst kit|nuke kit",
         "Raphael: Glass cannon kits kill fast but die fast. Use surprise, mobility, and cooldown timing. Never take extended trades."
      );
      add(
         "attrition kit|long fight kit|sustain kit",
         "Raphael: Attrition kits win by surviving and wearing enemies down. They are weak to burst, execution, and resource denial."
      );
      add(
         "snowball kit|scaling kit|kill scaling kit",
         "Raphael: Snowball kits get stronger from kills, stacks, chips, summons, or stored damage. Stop them early before their resource engine grows."
      );
      add(
         "counter snowball kit|stop scaling|anti snowball",
         "Raphael: Counter snowball kits by denying kills, disengaging when they are stacked, forcing them to waste resources, and attacking before they scale."
      );
      add(
         "minecraft boss counter|counter boss mechanics|boss kit counter",
         "Raphael: Bosses are countered by reading patterns, building resistances, avoiding greed, using terrain, and punishing cooldown windows. Do not face-tank unknown mechanics."
      );
      add(
         "mob weakness|monster weakness|enemy weakness minecraft",
         "Raphael: Most mobs have simple weaknesses: undead hate sunlight/holy/fire, flying mobs hate ranged pressure, casters hate rushdown, tanks hate bypass, and summons hate area damage."
      );
      add(
         "undead weakness|zombie skeleton weakness|holy fire",
         "Raphael: Undead enemies are usually vulnerable to holy effects, fire, sunlight, and area clearing. Avoid being surrounded."
      );
      add(
         "nether mob weakness|blaze ghast wither weakness",
         "Raphael: Nether mobs often resist fire but can be countered with ranged attacks, cover, knockback, and non-fire damage."
      );
      add(
         "end mob weakness|enderman shulker dragon weakness",
         "Raphael: End enemies often rely on teleportation, levitation, crystals, or void pressure. Bring pearls, slow falling, ranged options, and knockback control."
      );
      add(
         "farm evil players|hunt evil players|track evil kit",
         "Raphael: Hunting evil players requires detection, cleanse, burst, and escape. Do not chase into bases, caves, or dark terrain where traps and summons are waiting."
      );
      add(
         "evil player hiding|find hidden player|track invisible player",
         "Raphael: Find hidden players using particles, sound, hit detection, Presence Sense, Magic Sense, pets/summons, area attacks, and checking common escape tunnels."
      );
      add(
         "evil base|dark base|villain base|enemy base",
         "Raphael: Evil-style bases usually use traps, darkness, mobs, lava, false floors, and ambush routes. Scout first and never enter without escape tools."
      );
      add(
         "counter dark base|raid evil base|anti villain base",
         "Raphael: Bring blocks, pearls, fire resistance, water, cleanse, area damage, anti-summon tools, and backup gear. Move slowly and assume every hallway is trapped."
      );
      add(
         "minecraft survival kit|survival loadout|general survival",
         "Raphael: A survival loadout should include weapon, pickaxe, blocks, food, water, healing, ranged option, torches, escape item, and backup durability."
      );
      add(
         "hardcore survival|dont die|survival tips",
         "Raphael: In hardcore-style play, avoid unknown bosses, never fight low on food, carry escape tools, keep backup gear, and retreat before resources hit zero."
      );
      add(
         "early minecraft gear|early gear|starter gear",
         "Raphael: Early gear should prioritize iron armor, shield, bow, water bucket, food, blocks, and safe mining before chasing dangerous fights."
      );
      add(
         "midgame minecraft gear|midgame gear|progression gear",
         "Raphael: Midgame gear should add diamonds or netherite, enchantments, potions, pearls, better food, and specific resistances for your next boss."
      );
      add(
         "endgame minecraft gear|endgame gear|max gear",
         "Raphael: Endgame gear should include max armor, backup sets, specialized resistance gear, high-damage weapon, ranged option, mobility, cleanse, and emergency revival tools."
      );
      add(
         "best enchantments pvp|pvp enchants|combat enchants",
         "Raphael: Strong PvP enchantments usually include Protection, Unbreaking, Mending, Sharpness, Power, Feather Falling, Depth Strider, Swift Sneak, and situational Blast/Fire/Projectile Protection."
      );
      add(
         "blast protection vs protection|blast prot or prot|which protection",
         "Raphael: General Protection is flexible. Blast Protection is better against crystals, explosions, creepers, and blast kits. Mix based on your enemies."
      );
      add(
         "fire protection vs protection|fire prot or prot",
         "Raphael: Fire Protection is useful against lava/fire kits, but general Protection is better if enemies use mixed damage."
      );
      add(
         "projectile protection vs protection|proj prot or prot",
         "Raphael: Projectile Protection is useful against bow/crossbow/sniper kits, but weak if enemies switch to melee or magic."
      );
      add(
         "feather falling|fall damage counter|anti fall",
         "Raphael: Feather Falling counters fall traps, knock-up combos, levitation, mace bait, and terrain mistakes. It is one of the safest defensive enchants."
      );
      add(
         "knockback resistance minecraft|anti knockback|kb counter",
         "Raphael: Knockback resistance prevents edge kills, combo chains, and displacement. It is extremely valuable in End fights, bridges, and cliff terrain."
      );
      add(
         "shield disable|axe shield|disable shield",
         "Raphael: Axes can pressure shields by disabling or punishing blocking. If enemies turtle behind shields, use axe hits, magic, explosions, or flanks."
      );
      add(
         "bow vs crossbow|which ranged weapon|bow or crossbow",
         "Raphael: Bows give sustained ranged pressure. Crossbows give prepared burst, piercing, multishot, and firework burst. Choose based on whether you want pressure or ambush."
      );
      add(
         "sword vs axe|which melee weapon|axe or sword",
         "Raphael: Swords are better for consistent DPS and sweeping. Axes are better for burst and shield pressure. Use whichever fits your kit."
      );
      add(
         "mace vs sword|mace or sword|mace weapon",
         "Raphael: Mace is bursty and height-based. Sword is consistent. Use mace if you can control vertical movement; use sword for reliable combat."
      );
      add(
         "trident vs bow|trident or bow|ranged choice",
         "Raphael: Tridents are strong in water or storms. Bows are more consistent on land. Use the terrain to decide."
      );
      add(
         "netherite sword|netherite axe|best weapon minecraft",
         "Raphael: Best weapon depends on target. Sword for DPS, axe for shield pressure, mace for vertical burst, bow/crossbow for ranged control, trident for water mobility."
      );
      add(
         "evil kit with summons|dark summoner|evil summoner",
         "Raphael: Evil summoner kits are dangerous because they force you to split attention. Use area damage to clear minions, then immediately pressure the summoner."
      );
      add(
         "evil kit with drain|dark drain|evil lifesteal",
         "Raphael: Evil drain kits win long trades. Do not slowly trade hits. Burst, kite, anti-heal, or disengage before your resources collapse."
      );
      add(
         "evil kit with curse|dark curse|evil debuff",
         "Raphael: Evil curse kits weaken you before killing you. Cleanse early. Waiting too long lets curse stacks become impossible to fight through."
      );
      add(
         "evil kit with fear|dark fear|evil insanity",
         "Raphael: Evil fear kits disrupt control and movement. Mental resistance is the main answer. Passive defenses help if you lose control."
      );
      add(
         "evil kit with death mark|dark execution|evil execute",
         "Raphael: Evil execution kits want you low or marked. Cleanse marks, heal before threshold, and never stay in melee range while vulnerable."
      );
      add(
         "evil kit with traps|dark trapper|evil prison",
         "Raphael: Evil trap kits use fear, darkness, webs, cages, or spatial blockade. Bring mobility, cleanse, blocks, and do not enter their chosen arena."
      );
      add(
         "evil kit with invisibility|dark assassin|invisible evil",
         "Raphael: Evil invisibility kits rely on surprise. Use detection, area particles, summons, traps, and never stand still in dark terrain."
      );
      add(
         "evil kit with fire|hellfire kit|dark flame",
         "Raphael: Evil fire kits use panic and area denial. Fire Resistance and water remove most of their advantage, forcing them to rely on secondary damage."
      );
      add(
         "evil kit with poison|venom evil kit|toxic evil",
         "Raphael: Evil poison kits punish long fights. Poison resistance, cleanse, and burst damage are the answer."
      );
      add(
         "evil kit with teleport|dark teleport|shadow teleport",
         "Raphael: Evil teleport kits use surprise and escape. Counter with spatial blockade, prediction, area damage, and pressure at landing points."
      );
      add(
         "evil kit with projectile spam|dark sniper|evil archer",
         "Raphael: Evil projectile kits rely on distance and debuffs. Counter with cover, reflection, speed, shields, and forcing close combat."
      );
      add(
         "evil kit with boss summon|boss summoner|summon boss kit",
         "Raphael: Boss-summon kits should be countered by killing the summoner, separating the boss, using terrain, and not wasting everything on the summon if the owner is exposed."
      );
      add(
         "counter feldway item|counter angel summoner|anti angel summoner minecraft",
         "Raphael: Counter Angel Summoner with area damage, anti-summon pressure, killing the owner, holy/spatial/wind resistance, and forcing summons into bad terrain."
      );
      add(
         "counter feldway boss minecraft|anti feldway|feldway kit counter",
         "Raphael: Against Feldway, stack holy, spatial, and wind resistance. Use cover against beams, mobility against area fields, and area damage for angel pressure."
      );
      add(
         "counter caedros evil kit|anti caedros kit|beat conquest kit",
         "Raphael: Caedros is an evil conquest kit. Counter with cleanse, Fear/Insanity resistance, Magic Interference resistance, avoiding Bang lines, and starving Guilt."
      );
      add(
         "counter axiom evil kit|anti axiom kit|beat reflection kit",
         "Raphael: Axiom punishes burst and projectiles. Use controlled damage, bypass effects, debuffs, resource drain, and wait for Truth or Inversion to be unavailable."
      );
      add(
         "counter mahoraga evil kit|anti adaptation kit|beat adaptation kit",
         "Raphael: Mahoraga punishes repetition. Rotate damage types, burst before stacks rise, use execution, and avoid feeding one damage source again and again."
      );
      add(
         "counter kronairos evil kit|anti kronairos kit|beat isolation kit",
         "Raphael: Kronairos denies escape and drains. Counter with spatial resistance, burst before Isolation completes, anti-drain, and never fight inside Room if avoidable."
      );
      add(
         "counter mammon evil kit|anti greed kit|beat greed kit",
         "Raphael: Greed and casino-style kits snowball from value. Deny kills, deny loot, deny resources, and force them into fights where they cannot safely farm."
      );
      add(
         "counter ronova evil kit|anti death kit|beat ronova",
         "Raphael: Ronova death kits require death resistance, Spiritual Health protection, cleanse, and staying above execution thresholds."
      );
      add(
         "counter naberius evil kit|anti grave kit|beat naberius",
         "Raphael: Naberius grave kits require anti-summon pressure, corpse denial, area damage, and killing the owner before the archive becomes overwhelming."
      );
      add(
         "counter fungus evil kit|anti fungus kit|beat infection fungus",
         "Raphael: Fungus infection kits are countered by cleanse, poison resistance, abnormal nullification, spacing, and killing the source before spread gets out of control."
      );
      add(
         "counter istaroth evil kit|anti time kit|beat istaroth",
         "Raphael: Istaroth time kits require time resistance, instant damage, non-projectile attacks, spatial mobility, and passive defenses for moments when you cannot act."
      );
      add(
         "minecraft themed raphael|minecraft advice|vanilla advice",
         "Raphael: Minecraft combat is not only stats. Terrain, blocks, water, lava, food, durability, inventory, cooldowns, and escape tools decide many fights."
      );
      add(
         "use terrain|terrain pvp|terrain fight",
         "Raphael: Terrain is a weapon. Fight where your kit is strongest: open fields for speed and ranged, caves for traps, water for tridents, enclosed rooms for tanks, and high ground for bows."
      );
      add(
         "do not fight fair|unfair fight|smart fight",
         "Raphael: Do not fight fair if you are countered. Change terrain, bait cooldowns, retreat, swap gear, use traps, or force the enemy into your kit's advantage."
      );
      add(
         "how to know enemy kit|identify kit|read enemy build",
         "Raphael: Identify enemy kits by watching particles, weapons, armor, movement, summons, debuffs, and first cooldowns. The first 10 seconds reveal their win condition."
      );
      add(
         "enemy win condition|what is enemy trying|kit win condition",
         "Raphael: Find the enemy win condition: burst, drain, control, summons, traps, scaling, or execution. Once you know that, deny it instead of randomly trading hits."
      );
      add(
         "my win condition|what is my win condition|how to win fight",
         "Raphael: Your win condition is what your kit does best. Burst kits need a clean engage, tank kits need long fights, summon kits need owner safety, and control kits need enemies stuck."
      );
      add(
         "panic in fight|stop panicking|pvp nerves",
         "Raphael: When panicking, block line of sight, heal, create distance, check debuffs, then decide to re-engage or escape. Do not spam random skills while low."
      );
      add(
         "when to run|should i retreat|fight or run",
         "Raphael: Run if armor is low, food is low, Spiritual Health is low, major cooldowns are down, enemy counters you, or the terrain favors them."
      );
      add(
         "when to push|should i attack|commit fight",
         "Raphael: Push when the enemy misses a major cooldown, loses resources, gets trapped, has low healing, or their escape tool is gone."
      );
      add(
         "when to heal|heal timing|combat healing",
         "Raphael: Heal before execution range, not after. If the enemy has burst, heal while behind cover or after forcing their cooldown."
      );
      add(
         "when to cleanse|cleanse timing|remove debuffs timing",
         "Raphael: Cleanse early against stacking debuffs. Against single short debuffs, wait if cleanse is rare. Against curse, poison, fear, or control chains, cleanse immediately."
      );
      add(
         "when to use ultimate|ultimate timing|final move timing",
         "Raphael: Use ultimates when the enemy cannot escape, after their defensive cooldowns are down, or when you need to stop a losing fight immediately."
      );
      add(
         "wasting ultimate|missed ultimate|ultimate failed",
         "Raphael: Missing an ultimate is usually worse than waiting. Bait movement first, trap or slow the enemy, then use the final move."
      );
      add(
         "evil kit final move|dark ultimate|villain ultimate",
         "Raphael: Evil final moves should create dread: mark the target, deny escape, drain resources, then execute or corrupt. Counter with cleanse and escaping before the final trigger."
      );
      add(
         "counter final move|survive ultimate|enemy ultimate",
         "Raphael: Survive ultimates by saving mobility, barriers, cleanse, death prevention, and line-of-sight breaks. Do not waste escape tools before the enemy commits."
      );
      add(
         "angel summoner tips|feldway summoner tips|how to use angel summoner",
         "Raphael: Sneak-use to choose a mode, aim at solid ground, then hold/use to summon. Use Despawn to clean up owned angels and Immunity mode when you need Magicule Poison immunity support."
      );
      add(
         "angel summoner not working|why wont angel summoner work|cant use angel summoner",
         "Raphael: Angel Summoner may fail because you are on cooldown, not aiming at valid solid ground, already have the maximum summons for that mode, or the bound summoning circle became invalid."
      );
      add(
         "angel summoner best mode|best angel mode|which angel summoner mode",
         "Raphael: Use Summon for normal fights, Army for groups, Cathedral for large formation pressure, Attack to command pressure, Despawn to clean summons, Ultimate for high-impact use, and Immunity for Magicule Poison protection."
      );
      add(
         "angel summoner vs naberius|feldway angels vs naberius|angels vs grave summons",
         "Raphael: Angel Summoner creates holy/wind/spatial pressure summons, while Naberius focuses on corpse records and revival. Angels are cleaner burst support; Naberius is stronger in long corpse-value fights."
      );
      add(
         "angel summoner vs mahoraga|feldway angels vs mahoraga|angels counter mahoraga",
         "Raphael: Feldway Angels can pressure Mahoraga with varied holy, wind, spatial, and melee attacks, but if Mahoraga survives too long it can adapt to repeated angel attack patterns."
      );
      add(
         "angel summoner vs axiom|feldway angels vs axiom|angels counter axiom",
         "Raphael: Axiom can punish repeated projectile or direct pressure, so Angel Summoner should overwhelm with multiple angles and avoid feeding one huge reflected memory burst."
      );
      add(
         "angel summoner vs caedros|feldway angels vs caedros|angels counter caedros",
         "Raphael: Caedros has huge area control and Bang pressure that can erase summons. Angel Summoner must use spacing and multiple angels to pressure Caedros before Guilt snowballs."
      );
      add(
         "angel summoner vs kronairos|feldway angels vs kronairos|angels counter kronairos",
         "Raphael: Kronairos wants to isolate and drain. Angel Summoner should keep distance, use angels to pressure from outside Room, and avoid being trapped alone."
      );
      add(
         "holy angel build|angelic build|feldway themed build",
         "Raphael: A Feldway-themed angelic build should stack holy damage, spatial attacks, wind pressure, summon support, magicules, Spiritual Health protection, and mobility."
      );
      add(
         "anti angel build|angel counter build|counter angelic build",
         "Raphael: Anti-angel builds should stack holy resistance, spatial resistance, wind resistance, burst damage, area damage, anti-summon tools, and Magic Interference or silence against angelic casters."
      );
      add(
         "angel summoner magicule poison|magicule poison immunity item|angel item magicule poison",
         "Raphael: Angel Summoner has an Immunity mode that stores Magicule Poison immunity as item data. That makes it useful when Magicule Poison is a threat."
      );
      add(
         "angel summon active range|owned angels range|angel control range",
         "Raphael: Owned Feldway Angels are searched in a large range around the player, about 256 blocks in the item logic. If they are too far away or dead, they no longer count as active nearby summons."
      );
      add(
         "angel summon formation|cathedral formation|army formation",
         "Raphael: Army and Cathedral modes place angels in a circular formation around the target position. Cathedral uses a wider radius than Army, making it better for dramatic large-area summons."
      );
      add(
         "feldway angel target ai|angel target logic|who do angels attack",
         "Raphael: Feldway Angels can target enemies that attacked their owner or enemies their owner recently hurt. This makes them function like guardian summons."
      );
      add(
         "feldway angel weaknesses|angel weaknesses|weakness of feldway angels",
         "Raphael: Feldway Angels are strongest when allowed to cast at range. Their weaknesses are area damage, burst focus, anti-summon control, and enemies with holy/spatial/wind resistance."
      );
      add(
         "feldway boss weaknesses|feldway weakness|weakness of feldway",
         "Raphael: Feldway's likely weaknesses are resistance stacking against holy, wind, and spatial attacks, plus mobility and burst timing. Do not let angelic pressure trap you in one position."
      );
      add(
         "unique skills from my mod|custom unique skills|moreskills unique skills|tenaddex unique skills",
         "Raphael: Your custom unique path includes Reflector as the Axiom prerequisite and Adaptability as the Mahoraga prerequisite. Mammon Casino evolves from mastered Greed, and Kronairos evolves from mastered Infinity Prison."
      );
      add(
         "unique skill progression my mod|custom unique progression|custom unique evolve",
         "Raphael: Custom Unique-to-Ultimate progression includes Reflector into Axiom and Adaptability into Divine General Mahoraga. Greed and Infinity Prison are base Unique prerequisites for Mammon Casino and Kronairos."
      );
      add(
         "reflector vs adaptability|adaptability vs reflector|which unique better reflector adaptability",
         "Raphael: Reflector is better for counterplay and projectile/damage reflection. Adaptability is better for long fights, damage learning, severance immunity, and eventually Mahoraga."
      );
      add(
         "best custom unique|best unique in my mod|which unique should i use",
         "Raphael: Choose Reflector if you want counterattacks and Axiom later. Choose Adaptability if you want survival, damage learning, Extermination Blade, and Mahoraga later."
      );
      add(
         "custom unique pvp|best unique pvp|reflector adaptability pvp",
         "Raphael: For PvP, Reflector punishes careless burst and projectiles, while Adaptability punishes repeated damage patterns. Reflector is better for immediate counterplay; Adaptability is better if the fight lasts."
      );
      add(
         "custom unique pve|best unique pve|reflector adaptability pve",
         "Raphael: For PvE, Adaptability is safer because mobs and bosses often repeat damage patterns. Reflector is stronger when enemies hit hard enough to be punished by reflection."
      );
      add(
         "unique to ultimate custom|unique ultimate path|custom ultimate requirement",
         "Raphael: For your custom paths, master Reflector to unlock Axiom and master Adaptability to unlock Mahoraga. Mammon Casino requires mastered Greed, and Kronairos requires mastered Infinity Prison."
      );
      add(
         "detailed answer|full explanation|explain fully|all details",
         "Raphael: For detailed mechanics, I need wiki records or exact local data. Without records, I can only explain the general mechanic safely."
      );
   }

   private record Entry(List<String> keys, String answer) {
   }
}
