package io.github.Memoires.mysticism.neoforge.data;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.Memoires.mysticism.registry.item.MysticismMaterialItems;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.Memoires.mysticism.registry.magic.SpiritualMysticismMagics;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UltimateMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MysticismLanguageProvider extends LanguageProvider {
   public MysticismLanguageProvider(PackOutput output) {
      super(output, "mysticism", "en_us");
   }

   protected void addTranslations() {
      this.entitiesAndSpawnEggs();
      this.races();
      this.skills();
      this.enchantments();
      this.soundSubtitles();
      this.miscItems();
      this.mobDropItems();
      this.weaponItems();
      this.tooltips();
      this.other();
      this.effects();
      this.evolutionRequirements();
      this.deaths();
      this.stats();
      this.oreBlocks();
      this.attributes();
      this.commandMessages();
      this.gameRules();
      this.guis();
   }

   protected void addRace(RegistrySupplier<? extends ManasRace> race, String name, String notes) {
      this.add(String.format("%s.race.%s", race.getId().getNamespace(), race.getId().getPath().replace('/', '.')), name);
      this.add(String.format("%s.race.%s.description", race.getId().getNamespace(), race.getId().getPath().replace('/', '.')), notes);
   }

   protected void addSkill(RegistrySupplier<? extends ManasSkill> skill, String name) {
      this.add(String.format("%s.skill.%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), name);
   }

   protected void addSkillWithDescription(RegistrySupplier<? extends ManasSkill> skill, String name, String description) {
      this.add(String.format("%s.skill.%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), name);
      this.add(String.format("%s.skill.%s.description", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), description);
   }

   protected void addSkillWithTitle(RegistrySupplier<? extends ManasSkill> skill, String name, String title) {
      this.add(String.format("%s.skill.%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), name);
      this.add(String.format("%s.skill.%s.title", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), title);
   }

   protected void addSkillWithDescriptionAndTitle(RegistrySupplier<? extends ManasSkill> skill, String name, String description, String title) {
      this.add(String.format("%s.skill.%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), name);
      this.add(String.format("%s.skill.%s.description", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), description);
      this.add(String.format("%s.skill.%s.title", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.')), title);
   }

   protected void addMultiDescription(RegistrySupplier<? extends ManasSkill> skill, String name, String... descriptions) {
      int i = 1;

      for (String description : descriptions) {
         this.add(String.format("%s.skill.%s.description_%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.'), i), description);
         i++;
      }
   }

   protected void addAttribute(Holder<Attribute> holder, String name) {
      this.add(((Attribute)holder.value()).getDescriptionId(), name);
   }

   protected void addSkillModeNames(RegistrySupplier<? extends ManasSkill> skill, String... modes) {
      for (String mode : modes) {
         String key = String.format("%s.skill.mode.%s.%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.'), mode);
         this.add(key, toTitle(mode));
      }
   }

   protected void addSkillModeNames(RegistrySupplier<? extends ManasSkill> skill, Map<String, String> modeToDisplay) {
      for (Entry<String, String> entry : modeToDisplay.entrySet()) {
         String key = String.format("%s.skill.mode.%s.%s", skill.getId().getNamespace(), skill.getId().getPath().replace('/', '.'), entry.getKey());
         this.add(key, entry.getValue());
      }
   }

   private static String toTitle(String s) {
      if (s != null && !s.isEmpty()) {
         String normalized = s.replace('_', ' ').replace('-', ' ').trim();
         String[] parts = normalized.split("\\s+");
         StringBuilder out = new StringBuilder();

         for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (!p.isEmpty()) {
               out.append(Character.toUpperCase(p.charAt(0)));
               if (p.length() > 1) {
                  out.append(p.substring(1));
               }

               if (i < parts.length - 1) {
                  out.append(' ');
               }
            }
         }

         return out.toString();
      } else {
         return s;
      }
   }

   protected void addEntityAndSpawnEgg(RegistrySupplier<? extends EntityType<?>> entity, String name) {
      String path = entity.getId().getPath().replace('/', '.');
      this.add(String.format("entity.%s.%s", entity.getId().getNamespace(), path), name);
      this.add(String.format("item.%s.%s_spawn_egg", entity.getId().getNamespace(), path), name + " Spawn Egg");
   }

   private void addEffect(MobEffect mobEffect, String name) {
      ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(mobEffect);
      this.add(String.format("effect.%s.%s", id.getNamespace(), id.getPath()), name);
   }

   private void addSound(SoundEvent soundEvent, String name) {
      this.add(MysticismSoundProvider.getSubtitle(soundEvent), name);
   }

   protected void addDeathMessage(ResourceKey<DamageType> key, String message, String sourceMessage) {
      this.add("death.attack." + MysticismDamageTypes.getMsgId(key), message);
      this.add("death.attack." + MysticismDamageTypes.getMsgId(key) + ".player", message + " whilst fighting %2$s");
      this.add("death.attack." + MysticismDamageTypes.getMsgId(key) + ".item", message + " whilst fighting %2$s wielding %3$s");
      this.add("death.attack." + MysticismDamageTypes.getMsgId(key) + ".source", sourceMessage);
      this.add("death.attack." + MysticismDamageTypes.getMsgId(key) + ".source.item", sourceMessage + " using %3$");
   }

   private void enchantments() {
      this.add("enchantment.mysticism.holy_flames", "Holy Flames");
      this.add("enchantment.mysticism.holy_flames.desc", "Damaging your foes with this weapon causes them to burst into bright, powerful Brimstone flames.");
      this.add("enchantment.mysticism.boreal_frost", "Boreal Frost");
      this.add("mysticism.effect.eternal_permafrost.hindered", "It is too cold. Ability activation failed.");
      this.add("enchantment.mysticism.boreal_frost.desc", "Damaging your foes with this weapon causes cold, biting Permafrost to pierce their very being.");
   }

   private void entitiesAndSpawnEggs() {
      this.addEntityAndSpawnEgg(MysticismMonsterEntityTypes.OKAMI, "Okami");
      this.addEntityAndSpawnEgg(MysticismMonsterEntityTypes.MEMOIRES, "Memoires");
      this.addEntityType(MysticismMonsterEntityTypes.CLAY_GOLEM, "Clay Golem");
      this.addEntityType(MysticismSkillEntityTypes.PROVIDER_HALO, "Provider Halo");
      this.addEntityType(MysticismSkillEntityTypes.SOLIDIFICATION_SHIELD, "Solidification Shield");
      this.addEntityType(MysticismProjectileEntityTypes.STASIS_SHOT, "Stasis Shot");
      this.addEntityType(MysticismSkillEntityTypes.DISPENSER, "Dispenser");
      this.addEntityType(MysticismSkillEntityTypes.SENTRY, "Sentry");
      this.addEntityAndSpawnEgg(MysticismMonsterEntityTypes.SHADOW_IMP, "Shadow Imp");
   }

   private void races() {
      this.addRace(
         MysticismRaces.INSECT,
         "Insect",
         "A cryptid from another world. They excel in physical combat, and specialise in different things. Which one will you be? (Selecting this will RE-ROLL your race among the races in the Mysticism General config file!)"
      );
      this.addRace(
         MysticismRaces.ANT,
         "Ant",
         "Ants are fearless critters that have absurdly tanky exoskeletons, known for being bulky and hard to take down. Prepare for a long, drawn out battle if you encounter one!"
      );
      this.addRace(
         MysticismRaces.BEETLE,
         "Beetle",
         "Beetles are heavy-hitting insects, focusing everything into their power. Though they are one of the stronger races, their defences are lacklustre, to put it nicely."
      );
      this.addRace(
         MysticismRaces.CENTIPEDE,
         "Centipede",
         "Centipedes are fast, speed-oriented insects that can escape virtually any fight. Their many legs makes up for their lack of power."
      );
      this.addRace(
         MysticismRaces.MANTIS,
         "Mantis",
         "Mantises are fast-hitting insects that whittle down their opponents with quick strikes and their powerful melee skills. Some variants have even been known to melt through armor."
      );
      this.addRace(
         MysticismRaces.SCORPION,
         "Scorpion",
         "Scorpions deliver strikes with their powerful stinger, which can inflict a potent venom on their targets. In rare cases, some even gain the ability to manipulate Gravity itself."
      );
      this.addRace(
         MysticismRaces.WASP,
         "Wasp",
         "Wasps are frail insects that have the innate ability to fly. Though they aren't quick, they have great versatility, and it all exists to protect the Queen."
      );
      this.addRace(
         MysticismRaces.FIRE_ANT,
         "Fire Ant",
         "An evolved ant, leaning into its offenses. They are able to ignite their physical attacks, akin to the Fire Aspect enchantment."
      );
      this.addRace(
         MysticismRaces.FIRE_ANT_INSECTAR,
         "Fire Ant Insectar",
         "A Fire Ant that has gained a humanoid body. It delivers painful, stinging bites and fights ferociously."
      );
      this.addRace(
         MysticismRaces.FIRE_ANT_SAVANT,
         "Fire Ant Savant",
         "A Fire Ant that has evolved \"the right way\" and became a spiritual life-form. It carries out the will of the army, being a shining beacon for other Fire Ants."
      );
      this.addRace(
         MysticismRaces.FLAME_SOUL_INSECT,
         "Flame Soul Insect",
         "A Fire Ant that has evolved \"the right way\" and became a spiritual life-form. It embodies the element of Flames perfectly, singing its foes to a fiery demise."
      );
      this.addRace(
         MysticismRaces.DIVINE_FIRE_ANT,
         "Divine Fire Ant",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Fire Ant that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.HARDSHELL_ANT,
         "Hardshell Ant",
         "An evolved ant, leaning into its defenses. They naturally have good bulk, making them extremely tough to take out of a fight."
      );
      this.addRace(
         MysticismRaces.HARDSHELL_ANT_INSECTAR,
         "Hardshell Ant Insectar",
         "A Hardshell Ant that has gained a humanoid body. Its shell has only become tougher, and it will continue to become stronger."
      );
      this.addRace(
         MysticismRaces.HARDSHELL_ANT_SAVANT,
         "Hardshell Ant Savant",
         "A Hardshell Ant that has evolved \"the right way\" and became a spiritual life-form. It carries out the will of the army, being a shining beacon for other Hardshell Ants."
      );
      this.addRace(
         MysticismRaces.EARTH_SOUL_INSECT,
         "Earth Soul Insect",
         "A Hardshell Ant that has evolved \"the right way\" and became a spiritual life-form. It embodies the element of Earth perfectly, defending the weak and fighting off those who wrong it."
      );
      this.addRace(
         MysticismRaces.DIVINE_HARDSHELL_ANT,
         "Divine Hardshell Ant",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Hardshell Ant that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.STAG_BEETLE,
         "Stag Beetle",
         "An evolved beetle, leaning into its offenses. Their tough horn allows them to jab opponents to deal great damage."
      );
      this.addRace(
         MysticismRaces.STAG_BEETLE_INSECTAR,
         "Stag Beetle Insectar",
         "A Stag Beetle that has gained a humanoid body. It can now funnel its strength into its fists, allowing it to dominate the battlefield."
      );
      this.addRace(
         MysticismRaces.STAG_BEETLE_SAVANT,
         "Stag Beetle Savant",
         "A Stag Beetle that has evolved \"the right way\" and became a spiritual life-form. Its exoskeleton hardens evermore, capable of utilising Mithril Strength to further enhance its capabilities."
      );
      this.addRace(
         MysticismRaces.FANTASY_SOUL_INSECT,
         "Fantasy Soul Insect",
         "A Stag Beetle that has evolved \"the right way\" and became a spiritual life-form. It embodies the element of Fantasy perfectly, distorting magic and removing threats with pure might alone."
      );
      this.addRace(
         MysticismRaces.DIVINE_STAG_BEETLE,
         "Divine Stag Beetle",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Stag Beetle that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.DRONE_BEETLE,
         "Drone Beetle",
         "An evolved beetle, leaning into its speed. Trading damage for speed, it is now capable of flight. However, it is still too young."
      );
      this.addRace(
         MysticismRaces.DRONE_BEETLE_INSECTAR,
         "Drone Beetle Insectar",
         "A Drone Beetle that has gained a humanoid body. Its wings are much stronger and capable of traveling much quicker."
      );
      this.addRace(
         MysticismRaces.DRONE_BEETLE_SAVANT,
         "Drone Beetle Savant",
         "A Drone Beetle that has evolved \"the right way\" and became a spiritual life-form. It flies at tremendous speeds, almost capable of breaking the sound barrier with how fast it flies."
      );
      this.addRace(
         MysticismRaces.LIGHTNING_SOUL_INSECT,
         "Lightning Soul Insect",
         "A Drone Beetle that has evolved \"the right way\" and became a spiritual life-form. It embodies the element of Lightning perfectly, rupturing the battlefield and being nigh untouchable."
      );
      this.addRace(
         MysticismRaces.DIVINE_DRONE_BEETLE,
         "Divine Drone Beetle",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Drone Beetle that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.BLUE_CENTIPEDE,
         "Blue Centipede",
         "An evolved centipede, leaning into its swimming ability. It loves to hunt in the vast oceans, making use of its ability to paralyze targets."
      );
      this.addRace(MysticismRaces.BLUE_CENTIPEDE_INSECTAR, "Blue Centipede Insectar", "WIP");
      this.addRace(MysticismRaces.BLUE_CENTIPEDE_SAVANT, "Blue Centipede Savant", "WIP");
      this.addRace(MysticismRaces.WATER_SOUL_INSECT, "Water Soul Insect", "WIP");
      this.addRace(MysticismRaces.DIVINE_BLUE_CENTIPEDE, "Divine Blue Centipede", "WIP");
      this.addRace(
         MysticismRaces.PURPLE_CENTIPEDE,
         "Purple Centipede",
         "An evolved centipede, leaning into its offenses. It creates spatial distortions to disorient its prey before striking."
      );
      this.addRace(MysticismRaces.PURPLE_CENTIPEDE_INSECTAR, "Purple Centipede Insectar", "WIP");
      this.addRace(MysticismRaces.PURPLE_CENTIPEDE_SAVANT, "Purple Centipede Savant", "WIP");
      this.addRace(MysticismRaces.SPATIAL_SOUL_INSECT, "Spatial Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_PURPLE_CENTIPEDE,
         "Divine Purple Centipede",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Purple Centipede that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.YELLOW_CENTIPEDE,
         "Yellow Centipede",
         "An evolved centipede, leaning into its speed. It swiftly dodges its target's attacks before paralyzing them, finishing the job."
      );
      this.addRace(MysticismRaces.YELLOW_CENTIPEDE_INSECTAR, "Yellow Centipede Insectar", "WIP");
      this.addRace(MysticismRaces.YELLOW_CENTIPEDE_SAVANT, "Yellow Centipede Savant", "WIP");
      this.addRace(MysticismRaces.PARALYSIS_SOUL_INSECT, "Paralysis Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_YELLOW_CENTIPEDE,
         "Divine Yellow Centipede",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Yellow Centipede that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.LIXIVANT_MANTIS,
         "Lixivant Mantis",
         "An evolved mantis, gaining the ability to corrode things. It melts through enemy defenses and cripples them before they even know what hit them."
      );
      this.addRace(MysticismRaces.LIXIVANT_MANTIS_INSECTAR, "Lixivant Mantis Insectar", "WIP");
      this.addRace(MysticismRaces.LIXIVANT_MANTIS_SAVANT, "Lixivant Mantis Savant", "WIP");
      this.addRace(MysticismRaces.CORROSION_SOUL_INSECT, "Corrosion Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_LIXIVANT_MANTIS,
         "Divine Lixivant Mantis",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Lixivant Mantis that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.PREYING_MANTIS,
         "Preying Mantis",
         "An evolved mantis, leaning into its attack speed. It punches quick and deals decisive blows that almost guarantees victory."
      );
      this.addRace(MysticismRaces.PREYING_MANTIS_INSECTAR, "Preying Mantis Insectar", "WIP");
      this.addRace(MysticismRaces.PREYING_MANTIS_SAVANT, "Preying Mantis Savant", "WIP");
      this.addRace(MysticismRaces.STEEL_SOUL_INSECT, "Steel Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_PREYING_MANTIS,
         "Divine Preying Mantis",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Preying Mantis that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.LOXODROME_SCORPION,
         "Loxodrome Scorpion",
         "An evolved scorpion, becoming the king of the pack. It uses deadly and lethal venom to destroy all of its targets from the inside."
      );
      this.addRace(MysticismRaces.LOXODROME_SCORPION_INSECTAR, "Loxodrome Scorpion Insectar", "WIP");
      this.addRace(MysticismRaces.LOXODROME_SCORPION_SAVANT, "Loxodrome Scorpion Savant", "WIP");
      this.addRace(MysticismRaces.POISON_SOUL_INSECT, "Poison Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_LOXODROME_SCORPION,
         "Divine Loxodrome Scorpion",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Loxodrome Scorpion that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.SINGULARITY_SCORPION,
         "Singularity Scorpion",
         "An evolved scorpion, with the capability of manipulating gravity itself. It floats around aimlessly and randomly stings things it finds interesting. It has never been hunted excessively due to its nature, so its defenses are extremely weak."
      );
      this.addRace(MysticismRaces.SINGULARITY_SCORPION_INSECTAR, "Singularity Scorpion Insectar", "WIP");
      this.addRace(MysticismRaces.SINGULARITY_SCORPION_SAVANT, "Singularity Scorpion Savant", "WIP");
      this.addRace(MysticismRaces.GRAVITY_SOUL_INSECT, "Gravity Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_SINGULARITY_SCORPION,
         "Divine Singularity Scorpion",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Singularity Scorpion that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.ARMY_WASP,
         "Army Wasp",
         "An evolved scorpion, with the capability of manipulating gravity itself. It floats around aimlessly and randomly stings things it finds interesting. It has never been hunted excessively due to its nature, so its defenses are extremely weak."
      );
      this.addRace(MysticismRaces.ARMY_WASP_INSECTAR, "Army Wasp Insectar", "WIP");
      this.addRace(MysticismRaces.ARMY_WASP_SAVANT, "Army Wasp Savant", "WIP");
      this.addRace(MysticismRaces.WIND_SOUL_INSECT, "Wind Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_ARMY_WASP,
         "Divine Army Wasp",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Army Wasp that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.QUEEN_WASP,
         "Queen Wasp",
         "An evolved scorpion, with the capability of manipulating gravity itself. It floats around aimlessly and randomly stings things it finds interesting. It has never been hunted excessively due to its nature, so its defenses are extremely weak."
      );
      this.addRace(MysticismRaces.QUEEN_WASP_INSECTAR, "Queen Wasp Insectar", "WIP");
      this.addRace(MysticismRaces.QUEEN_WASP_SAVANT, "Queen Wasp Savant", "WIP");
      this.addRace(MysticismRaces.STAR_SOUL_INSECT, "Star Soul Insect", "WIP");
      this.addRace(
         MysticismRaces.DIVINE_QUEEN_WASP,
         "Divine Queen Wasp",
         "Such a presence commands everyone's attention. Be warned that you may not make it out in one piece encountering this monster of a Queen Wasp that has gained Divinity."
      );
      this.addRace(
         MysticismRaces.DIREWOLF,
         "Direwolf",
         "Direwolves are a species of monster that love to cause havoc. The more intelligent and friendly ones are capable of giving others a ride on their back. They have great potential for growth."
      );
      this.addRace(
         MysticismRaces.BLACK_FANG,
         "Black Fang",
         "A Direwolf that has a connection to magic. It can tune in to the nearby magicules and use it to sense its surroundings."
      );
      this.addRace(
         MysticismRaces.MYSTICAL_BLACK_FANG,
         "Mystical Black Fang",
         "An evolved Black Fang that has a deep connection to magic. It can disrupt the magic of nearby enemies, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.MAGIC_SPIRIT_WOLF,
         "Magic Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Black Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.BLUE_FANG,
         "Blue Fang",
         "A Direwolf that has a connection to water. It can tune in to the nearby magicules and use it to sense its surroundings."
      );
      this.addRace(
         MysticismRaces.MYSTICAL_BLUE_FANG,
         "Mystical Black Fang",
         "An evolved Black Fang that has a deep connection to water. It can disrupt the magic of nearby enemies, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.OCEAN_SPIRIT_WOLF,
         "Magic Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Blue Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.BROWN_FANG,
         "Brown Fang",
         "A Direwolf that has a connection to the earth. It can tune in to the nearby magicules and use it to sense its surroundings."
      );
      this.addRace(
         MysticismRaces.MYSTICAL_BROWN_FANG,
         "Mystical Brown Fang",
         "An evolved Black Fang that has a deep connection to the earth. It can disrupt the magic of nearby enemies, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.MOUNTAIN_SPIRIT_WOLF,
         "Mountain Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Brown Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.DARKNESS_FANG,
         "Darkness Fang",
         "A Direwolf that has a connection to the dark. It can blend in with the shadows and become invisible, as dark as night."
      );
      this.addRace(
         MysticismRaces.MYSTICAL_DARKNESS_FANG,
         "Mystical Darkness Fang",
         "An evolved Darkness Fang that has a deep connection to the dark. It can disrupt the magic of nearby enemies, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.PENUMBRA_SPIRIT_WOLF,
         "Penumbra Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Darkness Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.GREEN_FANG,
         "Green Fang",
         "A Direwolf that has a connection to wind. It can tune in to the nearby magicules and use it to sense its surroundings."
      );
      this.addRace(
         MysticismRaces.MYSTICAL_GREEN_FANG,
         "Mystical Green Fang",
         "An evolved Black Fang that has a deep connection to wind. It can disrupt the magic of nearby enemies, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.GALE_SPIRIT_WOLF,
         "Magic Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Black Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.GUITAR_WOLF,
         "Guitar Wolf",
         "A Direwolf that has a connection to sound. It can tune in to the nearby magicules and use it to sense its surroundings."
      );
      this.addRace(
         MysticismRaces.AMPED_GUITAR_WOLF,
         "Amped Guitar Wolf",
         "An evolved Guitar Wolf that has a deep connection to sound. It can disrupt the magic of nearby enemies, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.STRING_SPIRIT_WOLF,
         "String Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Guitar Fangs bow to this creature."
      );
      this.addRace(MysticismRaces.LIGHT_FANG, "Light Fang", "A Direwolf that has a connection to the light. It blinds its prey before striking.");
      this.addRace(
         MysticismRaces.MYSTICAL_LIGHT_FANG,
         "Mystical Light Fang",
         "An evolved Light Fang that has a deep connection to the light. It can illuminate its surroundings instantly, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.ANTUMBRA_SPIRIT_WOLF,
         "Antumbra Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Light Fangs bow to this creature."
      );
      this.addRace(MysticismRaces.PURPLE_FANG, "Purple Fang", "A Direwolf that has a connection to space. It creates portals to move quickly.");
      this.addRace(
         MysticismRaces.MYSTICAL_PURPLE_FANG,
         "Mystical Purple Fang",
         "An evolved Purple Fang that has a deep connection to space. It can appear at any place at any time, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.PLANET_SPIRIT_WOLF,
         "Planet Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Purple Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.RED_FANG,
         "Red Fang",
         "A Direwolf that has a connection to fire. It incinerates its surroundings for fun, making trouble for travelers."
      );
      this.addRace(
         MysticismRaces.MYSTICAL_RED_FANG,
         "Mystical Red Fang",
         "An evolved Red Fang that has a deep connection to fire. It can destroy its surroundings in mere moments, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.VOLCANIC_SPIRIT_WOLF,
         "Volcanic Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Red Fangs bow to this creature."
      );
      this.addRace(
         MysticismRaces.STAR_WOLF,
         "Star Wolf",
         "A Direwolf that has a connection to storms. Their tails can create strong winds when wagging and they are able to reduce their size."
      );
      this.addRace(
         MysticismRaces.TEMPEST_STAR_WOLF,
         "Tempest Star Wolf",
         "An evolved Star Wolf that has a deep connection to storms. It can create storms without a care, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.STORM_SPIRIT_WOLF,
         "Storm Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Star Wolves bow to this creature."
      );
      this.addRace(
         MysticismRaces.SCORCH_WOLF,
         "Scorch Wolf",
         "A Direwolf that has a connection to pure heat and energy. Simply being near one causes the environment to heat up. It can create deadly fire storms and harnesses pure scorch itself."
      );
      this.addRace(
         MysticismRaces.BLAZING_SCORCH_WOLF,
         "Blazing Scorch Wolf",
         "An evolved Scorch Wolf that has a deep connection to pure heat and energy. It can create deadly fire storms, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.MOLTEN_SPIRIT_WOLF,
         "Molten Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Scorch Wolves bow to this creature."
      );
      this.addRace(
         MysticismRaces.FROST_WOLF,
         "Frost Wolf",
         "A Direwolf that has a connection to ice. Temperatures drop to lethal freezing points when one exists nearby."
      );
      this.addRace(
         MysticismRaces.NIVALIS_FROST_WOLF,
         "Nivalis Frost Wolf",
         "An evolved Frost Wolf that has a deep connection to ice. It can freeze entire landscapes easily, typically being a highly-feared and regarded monster."
      );
      this.addRace(
         MysticismRaces.GLACIER_SPIRIT_WOLF,
         "Glacier Spirit Wolf",
         "A truly scary being. This is a Direwolf that has \"evolved the correct way\" and become a Spiritual Lifeform. One is rarely born once in a millennium, and all normal Direwolves and Frost Wolves bow to this creature."
      );
      this.addRace(MysticismRaces.DIVINE_WOLF, "Divine Wolf", "Direwolf that achieved divinity, possessing an immortal physical body that will never age.");
      this.addRace(MysticismRaces.LESSER_ELEMENTAL, "Lesser Elemental", "A curious Elemental with a mind not yet formed. What shape will it take?");
      this.addRace(
         MysticismRaces.MEDIUM_ELEMENTAL,
         "Medium Elemental",
         "An Elemental that has evolved, taking the form of a mythological creature. Its arsenal of Spiritual Magic is now stronger as well."
      );
      this.addRace(
         MysticismRaces.GREATER_ELEMENTAL,
         "Greater Elemental",
         "An Elemental that evolved to take on a more human-like shape. Beware its powerful Greater Spiritual Magic!"
      );
      this.addRace(
         MysticismRaces.ELEMENTAL_LORD,
         "Elemental Lord",
         "A graceful and elegant evolved Elemental that wields powerful Lord-level Spiritual Magic, capable of going toe-to-toe with Ultimate Skills."
      );
      this.addRace(
         MysticismRaces.MAJIN_LORD,
         "Majin Elemental Lord",
         "A very powerful majinised Elemental that wields powerful Lord-level Spiritual Magic, capable of going toe-to-toe with Ultimate Skills."
      );
      this.addRace(
         MysticismRaces.DIVINE_MAJIN_ELEMENTAL,
         "Divine Majin Elemental",
         "An aspect of perfection that embodies its element to perfection. This being possesses God-level Spiritual Magic rivaling King-Tier Ultimate Skills that can wipe out continents should they be cast."
      );
      this.addRace(
         MysticismRaces.DIVINE_ELEMENTAL,
         "Divine Elemental",
         "An aspect of perfection that embodies its element to perfection. This being possesses God-level Spiritual Magic rivaling King-Tier Ultimate Skills that can wipe out continents should they be cast."
      );
      this.addRace(MysticismRaces.LESSER_ANGEL, "Lesser Angel", "A spiritual life-form similar to Daemons known to wreck havoc on the world every 500 years.");
      this.addRace(MysticismRaces.GREATER_ANGEL, "Greater Angel", "The evolution of a Lesser Angel with a more developed ego.");
      this.addRace(MysticismRaces.ARCH_ANGEL, "Arch Angel", "Only few Angels make it to this point because of their lack of ego.");
      this.addRace(MysticismRaces.CHERUB, "Cherub", "The second most powerful type of pure Angel known.");
      this.addRace(MysticismRaces.SERAPH, "Seraph", "Only the most powerful pure Angels with the most developed ego can attain Divinity.");
      this.addRace(
         MysticismRaces.FALLEN_LESSER_ANGEL,
         "Fallen Lesser Angel",
         "A lesser angel that got corrupted by magicules, despite that, they still retain the light element."
      );
      this.addRace(
         MysticismRaces.FALLEN_GREATER_ANGEL,
         "Fallen Greater Angel",
         "A greater angel that got corrupted by magicules, despite that, they still retain the light element."
      );
      this.addRace(
         MysticismRaces.FALLEN_ARCH_ANGEL,
         "Fallen Arch Angel",
         "An arch angel that got corrupted by magicules, despite that, they still retain the light element."
      );
      this.addRace(
         MysticismRaces.FALLEN_CHERUB, "Fallen SoulAberration", "A cherub that got corrupted by magicules, despite that, they still retain the light element."
      );
      this.addRace(
         MysticismRaces.FALLEN_SERAPH,
         "Fallen ReaperAberration",
         "A seraph that got corrupted by magicules, despite that, they still retain the light element."
      );
      this.addRace(
         MysticismRaces.LESSER_FALLEN, "Lesser Fallen", "A lesser angel that got corrupted by magicules, then chose to abandon the light for the darkness."
      );
      this.addRace(
         MysticismRaces.GREATER_FALLEN, "Greater Fallen", "A greater angel that got corrupted by magicules, then chose to abandon the light for the darkness."
      );
      this.addRace(
         MysticismRaces.ARCH_FALLEN, "Arch Fallen", "An arch angel that got corrupted by magicules, then chose to abandon the light for the darkness."
      );
      this.addRace(MysticismRaces.FALLEN_LORD, "Fallen Lord", "A cherub that got corrupted by magicules, then chose to abandon the light for the darkness.");
      this.addRace(MysticismRaces.FALLEN, "Fallen", "A seraph that got corrupted by magicules, then chose to abandon the light for the darkness.");
      this.addRace(MysticismRaces.PHANTOM, "Phantom", "A demi-spiritual creature born in the Underworld by the magicules of the World Destroying Dragon.");
      this.addRace(MysticismRaces.FIELD_OFFICER, "Field Officer", "The evolution of a Phantom with an ego, now having the ability to split bodies.");
      this.addRace(MysticismRaces.GENERAL, "General", "A commanding officer of the Phantom army.");
      this.addRace(MysticismRaces.STAFF_OFFICER, "Staff Officer", "A high graded officer of the Phantom army.");
      this.addRace(
         MysticismRaces.MYSTIC_ANGEL,
         "Mystic Angel",
         "A ReaperAberration that got corrupted by the magicules of the World Destroying Dragon, but still kept their status as holy beings."
      );
      this.addRace(MysticismRaces.TENGU, "Tengu", "The result of an angel possessing an Okami.");
      this.addRace(MysticismRaces.TENGU_SAINT, "Tengu Saint", "The result of a Tengu becoming once more a spiritual being.");
      this.addRace(MysticismRaces.DIVINE_TENGU, "Divine Tengu", "The result of a Tengu achieving Divinity.");
      this.addRace(MysticismRaces.GREATER_DOLL, "Greater Doll", "The result of a Greater Daemon possessing a magisteel body.");
      this.addRace(MysticismRaces.ARCH_DOLL, "Arch Doll", "The result of an Arch Daemon possessing a magisteel body.");
      this.addRace(MysticismRaces.CHAOS_DOLL, "Chaos Doll", "The result of an Arch Doll reversing its alignment to become Chaos aligned.");
      this.addRace(MysticismRaces.CHAOS_METALLOID, "Chaos Metalloid", "The result of a Chaos Doll  achieving Divinity and awakening.");
      this.addRace(MysticismRaces.DAEMON_DOLL, "Daemon Doll", "The result of an Arch Doll acquiring a name.");
      this.addRace(MysticismRaces.DEVIL_DOLL, "Devil Doll", "The result of a Daemon Doll achieving Divinity and awakening.");
      this.addRace(
         MysticismRaces.DRAGONOID,
         "Dragonoid",
         "A child of the stars and hopes of the world. Can you obtain true power? You can only ever evolve once, but your stats grow with your EP."
      );
      this.addRace(
         MysticismRaces.TRUE_DRAGONOID,
         "True Dragonoid",
         "A child of the stars and hopes of the world. A being that has achieved divinity, challenging the existence that brought you into this world."
      );
      this.addRace(
         MysticismRaces.SCULK_WORM,
         "Sculk Worm",
         "An ancient family of sculk inhabitants, where evolution has given them permanent darkness. Starts off weak, but grows to become a monstrosity."
      );
      this.addRace(
         MysticismRaces.SOUL_SHRIEKER,
         "Soul Shrieker",
         "A sculk worm has tapped into their inner potential as a sculk inhabitant, and learned how to utilize its sound-based organs to fire projectiles."
      );
      this.addRace(MysticismRaces.WARDEN, "Warden", "Wardens are terrible creatures that inhabit the Deep Dark, always waiting and always ready to strike.");
      this.addRace(
         MysticismRaces.SOUL_ABERRATION,
         "Soul Aberration",
         "Very rarely, a Warden can evolve to an even bigger monstrosity. What this one will do with its power is still unknown. It is a demi-spiritual lifeform."
      );
      this.addRace(
         MysticismRaces.REAPER_ABERRATION,
         "Reaper Aberration",
         "One can only hope this thing does not find you. It has achieved divinity, possessing an immortal physical body that will never age."
      );
      this.addRace(
         MysticismRaces.MOLTEN_PERFORATOR,
         "Molten Perforator",
         "A rare mutation of a sculk worm achieved through forcibly devouring Flame Essence. Now it thrives in the Nether."
      );
      this.addRace(
         MysticismRaces.MAGMA_WORM,
         "Magma Worm",
         "\"And his brother loved worms...\" A powerful evolved variant of the sculk worm, gaining a long, worm-like appendage."
      );
      this.addRace(
         MysticismRaces.ENFLAMED_ABERRATION,
         "Enflamed Aberration",
         "These creatures have evolved to look eerily similar to their overworld counterparts. Pray that its power is not truly unleashed. It is a demi-spiritual lifeform."
      );
      this.addRace(
         MysticismRaces.VIOLENCE_DEITY,
         "Violence Deity",
         "What was once a sculk worm has truly disappeared. This is a monstrosity and there is no going back. It has achieved divinity, possessing an immortal physical body that will never age."
      );
      this.addRace(
         MysticismRaces.CHARGED_PERFORATOR,
         "Charged Perforator",
         "A rare mutation of a sculk worm. It consumed essence of Lightning itself and gained specific abilities. But this one... Seems weird."
      );
      this.addRace(MysticismRaces.OVERLOADING_WORM, "Overloading Worm", "\"The Reminder.\" When two brothers played, this one watched.");
      this.addRace(
         MysticismRaces.LIGHTNING_ABERRATION,
         "Lightning Aberration",
         "Pray that its power is not truly unleashed. Know the fear and drive behind an aberration of pure electricity. It is a demi-spiritual lifeform."
      );
      this.addRace(
         MysticismRaces.DISSONANCE_DEITY,
         "Dissonance Deity",
         "What was once a sculk worm has truly disappeared. \"The beast stretched from one horizon to the next and belched storms that reminded all of the hero’s promise: that this land – and all those who live here - would never suffer again...\" It has achieved divinity, possessing an immortal physical body that will never age."
      );
      this.addRace(
         MysticismRaces.ATTUNED_WYRM,
         "Attuned Wyrm",
         "A race of wyrm that as yet to find a element to evolve into, it searches for ways to evolve while it wanders the world."
      );
      this.addRace(
         MysticismRaces.LESSER_GLACIER_WYRM,
         "Lesser Glacier Wyrm",
         "A Race of Wyrm that live in the depth of the Icy Tundras, thriving on the cold and all those live in it."
      );
      this.addRace(
         MysticismRaces.GREATER_GLACIER_WYRM,
         "Greater Glacier Wyrm",
         "A higher evolution of the wyrm that live in the Icy Tundras, even more powerful and deadly then their previous evolutions, using its icy breath to freeze all those who come across it.A higher evolution of the wyrm that live in the Icy Tundras, even more powerful and deadly then their previous evolutions, using its icy breath to freeze all those who come across it."
      );
      this.addRace(
         MysticismRaces.FROSTCOIL_SEA_SERPENT,
         "Frostcoil Sea Serpent",
         "A sea dragon race that lives in the coldest depths of the Ocean's tundra's, using its enhanced ears to find its prey, and its speed to catch them."
      );
      this.addRace(
         MysticismRaces.FROSTWROUGHT_LEVIATHAN,
         "Frostwrought Leviathan",
         "A deadly dragon that lives near the bottom of the ocean's, a tame but deadly race able to manipulate sound to find and catch threads."
      );
      this.addRace(
         MysticismRaces.RIMEFANG_DRAKE,
         "Rimefang Drake",
         "A wingless dragon monster that lives in the icy tundras, thanks to its improved eyesight, its able to see its prey from far distances."
      );
      this.addRace(
         MysticismRaces.RIMEBLIGHT_HYDRA,
         "Rimeblight Hydra",
         "A deadly, three headed dragon monster that thrives in the cold mountains of the tundra, using its 3 heads to Poison, Paralyze and freeze its foes."
      );
      this.addRace(
         MysticismRaces.LESSER_PYRE_WYRM, "Lesser Pyre Wyrm", "A race of wyrm that live hottest deserts, thriving on the heat and all those that live in it."
      );
      this.addRace(
         MysticismRaces.GREATER_PYRE_WYRM,
         "Greater Pyre Wyrm",
         "A higher evolution of the wyrm that lives in the deserts, using its flaming breath to scorch its preys as it hunts those unprepared for the heat."
      );
      this.addRace(
         MysticismRaces.SUNFIRE_LINDWURM,
         "Sunfire Lindwurm",
         "A wingless spiritual dragon race that crawls across the ground, it helps those that it can, while also hunting for its own prey, it shows mercy, making sure to end the lives of prey quick and swiftly using rays of light."
      );
      this.addRace(
         MysticismRaces.SUNDEITY_LOONG,
         "Sundeity Loong",
         "Having learned to fly without wings, it soars through the air, using magic to rain down light on its foes and save others."
      );
      this.addRace(
         MysticismRaces.SCORCHTAIL_SALAMANDER,
         "Scorchtail Salamander",
         "A monster race that soars through the skies as it hunts for its next meal, diving down as it breaths its flaming breath on unsuspecting prey below."
      );
      this.addRace(
         MysticismRaces.SCORCHTALON_WYVERN,
         "Scorchtalon Wyvern",
         "A dragon monster race that scorches all those who come across it. Even its own kind is not safe from it."
      );
      this.addRace(
         MysticismRaces.FORGOTTEN,
         "Forgotten",
         "The Forgotten Race struggle to remember. A ghost, an apparition of something that used to be. The remains of a soul from someone long departed, that couldn’t be reused in reincarnation. Left behind to linger the world eternally."
      );
      this.addRace(
         MysticismRaces.REMNANT,
         "Remnant",
         "These lost souls piece their past back together slowly. They have regained some semblance of self-being, but it's still not enough."
      );
      this.addRace(
         MysticismRaces.WHOLE,
         "Whole",
         "Acceptance. The first step to becoming complete. When the remnants of your past collide together, only when you accept your struggles can you become Whole."
      );
      this.addRace(
         MysticismRaces.ASCENDED,
         "Ascended",
         "It will be fine if you never remember your past. That is what they tell themselves. Being able to accept this truth and embrace it. That is why they Ascend past all odds to become Spiritual Lifeforms despite their challenges."
      );
      this.addRace(
         MysticismRaces.DIVINE_EXCELSIUS,
         "Divine Excelsius",
         "The culmination of one who has lost their past. They find meaning in their new life, pushing past all their worries and transgressions to attain Divinity."
      );
      this.addRace(
         MysticismRaces.EMPTY,
         "Empty",
         "A Remnant that chose the hard way out. Instead of accepting their forgotten past, they chose to fight it, to no avail. A greater, emptier husk awaits."
      );
      this.addRace(
         MysticismRaces.REVENANT,
         "Revenant",
         "A husk of what could have been. Revenants carve their way out of the hell they have created for themselves, one soul at a time."
      );
      this.addRace(
         MysticismRaces.DIVINE_INFERIUS,
         "Divine Inferius",
         "The culmination of one who has lost their past. They find meaning in death, defeating all, even one who has truly forgotten the world, just to attain Divinity."
      );
      this.addRace(MysticismRaces.RESTRICTED_HUMAN, "Restricted Human", "");
      this.addRace(MysticismRaces.RESTRICTED_SAINT, "Restricted Saint", "");
      this.addRace(MysticismRaces.BOUND_ENLIGHTENMENT, "Bound Enlightenment", "");
      this.addRace(MysticismRaces.HEAVENLY_RESTRICTION, "Heavenly Restriction", "");
   }

   private void skills() {
      this.add("mysticism.skill.mode.default", "Default");
      this.intrinsicSkills();
      this.extraSkills();
      this.uniqueSkills();
      this.ultimateSkills();
      this.spiritualMagics();
   }

   private void effects() {
      this.addEffect(MysticismMobEffects.AURA_HEALING, "Aura Healing");
      this.addEffect(MysticismMobEffects.AWAKENED_FORESIGHT, "Awakened Foresight");
      this.addEffect(MysticismMobEffects.BRIMSTONE_FLAMES, "Brimstone Flames");
      this.addEffect(MysticismMobEffects.COUNTERING, "Countering");
      this.addEffect(MysticismMobEffects.COLLECTIVE, "Collective");
      this.addEffect(MysticismMobEffects.CULTIVATING, "Cultivating");
      this.addEffect(MysticismMobEffects.CONSTANT_HEALTH, "Constant: Health");
      this.addEffect(MysticismMobEffects.CONSTANT_DESTRUCTION, "Constant: Destruction");
      this.addEffect(MysticismMobEffects.CONSTANT_ENERGY, "Constant: Energy");
      this.addEffect(MysticismMobEffects.CONSTANT_ENERGY_DEBUFF, "Constant: Energy Debuff");
      this.addEffect(MysticismMobEffects.DAZZLED, "Dazzled");
      this.addEffect(MysticismMobEffects.DRAGON_BURN, "Dragon Burn");
      this.addEffect(MysticismMobEffects.ETERNAL_PERMAFROST, "Eternal Permafrost");
      this.addEffect(MysticismMobEffects.FIXATION, "Fixation");
      this.addEffect(MysticismMobEffects.GRAVITY_FLUCTUATION, "Gravity Fluctuation");
      this.addEffect(MysticismMobEffects.IMBALANCED, "Imbalanced");
      this.addEffect(MysticismMobEffects.INTANGIBLE, "Intangible");
      this.addEffect(MysticismMobEffects.LIGHTNING_MODE, "Lightning Mode");
      this.addEffect(MysticismMobEffects.MARKED_FOR_DEATH, "Marked for Death");
      this.addEffect(MysticismMobEffects.PAY_IT_FORWARD, "Pay it Forward");
      this.addEffect(MysticismMobEffects.PRESSURE, "Pressure");
      this.addEffect(MysticismMobEffects.PROVIDER_BOOST, "Provider Boost");
      this.addEffect(MysticismMobEffects.REDUCER_HOLY_COAT, "Emancipation");
      this.addEffect(MysticismMobEffects.REDUCER_PURITY_EDGE, "Purity Edge");
      this.addEffect(MysticismMobEffects.REVENANT_HORROR, "Revenant's Horror");
      this.addEffect(MysticismMobEffects.RUPTURING, "Rupturing");
      this.addEffect(MysticismMobEffects.SANCTIFYING_LIGHT, "Sanctifying Light");
      this.addEffect(MysticismMobEffects.SEVERED_ARM, "Severed Arm");
      this.addEffect(MysticismMobEffects.SEVERED_LEG, "Severed Leg");
      this.addEffect(MysticismMobEffects.STAGNATE, "Stagnate");
      this.addEffect(MysticismMobEffects.STILLNESS, "Stillness");
      this.addEffect(MysticismMobEffects.TAILED_BEAST_CLOAK, "Tailed Beast Cloak");
      this.addEffect(MysticismMobEffects.UNSTABLE_REQUIEM, "Unstable Requiem");
   }

   private void intrinsicSkills() {
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.BULLET_PUNCH,
         "Bullet Punch",
         "You can only be fast, and go even faster. On the next attack, you speed up and deliver four quick attacks."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.CLANGOROUS_SOUL,
         "Clangorous Soul",
         "Your soul, your beats. Your shell is hard, and makes wonderful noises when it is banged against. Maybe it can help you."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.CORROSION_TRANSFORM, "Corrosion Transform", "Channel your inner corrosion to burn and bleed the flesh off your enemies."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.DISCHARGE,
         "Discharge",
         "Strike a bolt of lightning using your inner bio-electricity generated from a sac within you. Can be used without thundering weather."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.DISSONANCE,
         "Dissonance",
         "You are the calamity. You are the storm, and you are approaching. Strike lightning in all cardinal directions that deal massive Electricity Damage to all those unfortunate to be hit by it."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.EXOSKELETON,
         "Exoskeleton",
         "The Insects of the other world all possess this tough Exoskeleton. It grows together with the user, and even allows them to wear armor on top of it."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.HELL_HALL,
         "Hell Hall",
         "Rupture the floor, raising your natural body temperature to liquefy all surfaces around you to molten levels."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.LETHAL_POISON,
         "Lethal Poison",
         "As the King of Scorpions, your poison is highly dangerous. An upgrade to the Poison skill. Can only be used by King Scorpions and their evolutionary line."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.LIGHTNING_MODE,
         "Lightning Mode",
         "Burst forth with an incredible speed boost, gaining doubled EP temporarily on top of higher damage. Beware of the terrible drawbacks."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.MAGISTEEL_BODY,
         "Magisteel Body",
         "Have a body made of magisteel, making it much harder and gaining progressively stronger armor depending on your EP."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.PARALYSIS_TRANSFORM,
         "Paralysis Transform",
         "Channel your inner paralysis to emit paralysing enzymes that make your target go numb just from your presence."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.POISON_TRANSFORM,
         "Poison Transform",
         "Channel your inner poison to inject deadly venom's into your target through their every pore."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.RELAPSE,
         "Relapse",
         "Collapse back into whence you came. Your memory is fleeting and the people you once knew start to forget you as well. Relapse and claim what belongs to you."
      );
      this.addSkillModeNames(IntrinsicMysticismSkills.RELAPSE, "aleph", "bet", "gimel", "daleth");
      this.add("mysticism.skill.mode.relapse.gimel.not_enough_kills", "Not enough kills to activate this mode. Need %s. Currently have %s.");
      this.add("mysticism.skill.mode.relapse.aleph_null", "Aleph: Null");
      this.add(
         "mysticism.skill.mode.relapse.aleph_null.cooldown_incurred",
         "Using this skill mode caused [Aleph] and [Aleph: Null] to go on cooldown for %s seconds!"
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.SPARK, "Spark", "Light your weapon and fists aflame, igniting your next target on fire for a short period of time."
      );
      this.addSkillWithDescription(
         IntrinsicMysticismSkills.TENACITY,
         "Tenacity",
         "Become tenacious and repair your body. Since magic and mana itself has rejected you, utilise your pure aura alone."
      );
   }

   private void evolutionRequirements() {
      this.add("mysticism.evolution_menu.any_of", "Satisfy any of the following: %s");
      this.add("mysticism.evolution_menu.biome_requirement_any_of", "Be in any of the following biomes: %s");
      this.add("mysticism.evolution_menu.height_requirement", "Be %s Y-level %s");
      this.add("mysticism.evolution_menu.light_requirement", "Current light level must be %s %s");
      this.add("mysticism.evolution_menu.alignment_requirement", "Be aligned to %s.");
      this.add("mysticism.evolution_menu.slay_requirement", "Slay %s of %s.");
      this.add("mysticism.evolution_menu.possess_golem", "Possess a Bone Golem.");
   }

   private void extraSkills() {
      this.addSkillWithDescription(
         ExtraMysticismSkills.MITHRIL_STRENGTH,
         "Mithril Strength",
         "Turn your muscles as hard as Mithril and gain an increase in damage, toggleable when mastered."
      );
      this.addSkillWithDescription(ExtraMysticismSkills.DARKNESS_DOMINATION, "Darkness Domination", "Boosts the power of Dark abilities by a large amount.");
      this.addSkillWithDescription(
         ExtraMysticismSkills.DARKNESS_MANIPULATION, "Darkness Manipulation", "Boosts the power of Dark abilities by a decent amount."
      );
      this.addSkillWithDescription(
         ExtraMysticismSkills.GRAVITY_FLUX, "Gravity Flux", "Utilise the power of gravity to disrupt the target's movement and hamper their ability to move."
      );
      this.addSkillModeNames(ExtraMysticismSkills.GRAVITY_FLUX, "fluctuate", "hamper");
      this.addSkillWithDescription(ExtraMysticismSkills.LIGHT_DOMINATION, "Light Domination", "Boosts the power of Light abilities by a large amount.");
      this.addSkillWithDescription(ExtraMysticismSkills.LIGHT_MANIPULATION, "Light Manipulation", "Boosts the power of light abilities by a decent amount.");
      this.addSkillWithDescription(ExtraMysticismSkills.ICE_MANIPULATION, "Ice Manipulation", "Boosts the power of ice abilities by a decent amount.");
      this.addSkillWithDescription(ExtraMysticismSkills.ICE_DOMINATION, "Ice Domination", "Boosts the power of ice abilities by a large amount.");
      this.addSkillWithDescription(
         ExtraMysticismSkills.CRYOGENIC_CESSATION,
         "Cryogenic Cessation",
         "Command your absolute authority over Deceleration, allowing you to freeze all enemies with ice and spew superchilled ice. Additionally, freeze the surroundings into ice"
      );
      this.addSkillModeNames(ExtraMysticismSkills.CRYOGENIC_CESSATION, "ice_breath", "freezing_point");
      this.addSkillWithDescription(
         ExtraMysticismSkills.PROFANED_PROMINENCE,
         "Profaned Prominence",
         "Command your absolute authority over Acceleration, allowing you to inflict all enemies with fire and spew superheated flames. Additionally, melt the surroundings into magma."
      );
      this.addSkillModeNames(ExtraMysticismSkills.PROFANED_PROMINENCE, "fire_breath", "providence");
   }

   private void uniqueSkills() {
      this.addSkillWithDescription(
         UniqueMysticismSkills.BUTCHER,
         "Butcher",
         "Slice, dice, and feel your hunger. Your thirst for the hunt is unquenching, so grab a plate and have a taste."
      );
      this.addSkillModeNames(UniqueMysticismSkills.BUTCHER, "heighten", "rupture", "血淋林的爱");
      this.add("mysticism.skill.mode.butcher.aortic_work_of_art.target_marks", "%1$s's Marks: %2$s");
      this.add("mysticism.skill.mode.butcher.rupture.no_eligible_targets", "You do not have any targets above %s marks.");
      this.add("mysticism.skill.mode.butcher.血淋林的爱.no_charges", "No charges of 血淋林的爱. Kill an enemy with over 20 marks to gain a charge.");
      this.add("mysticism.skill.butcher.butchering_screen.selected_too_many", "Too many body parts selected. Maximum of three.");
      this.add("mysticism.skill.butcher.butchering_screen.no_parts_selected", "No body parts selected. Select one and try again.");
      this.add("mysticism.skill.butcher.butchering_screen.butcher", "Butcher");
      this.add("mysticism.skill.butcher.butchering_screen.head", "Head");
      this.add("mysticism.skill.butcher.butchering_screen.torso", "Torso");
      this.add("mysticism.skill.butcher.butchering_screen.arm", "Arm");
      this.add("mysticism.skill.butcher.butchering_screen.leg", "Leg");
      this.addSkillWithDescription(
         UniqueMysticismSkills.COALESCENCE,
         "Coalescence",
         "Freeze, stop and stagnate. Prevent hindering effects from applying to you and cause others to halt and solidify."
      );
      this.addSkillModeNames(UniqueMysticismSkills.COALESCENCE, "fixation", "solidification", "eternal_domain");
      this.add("mysticism.skill.mode.coalescence.fixation.on_cooldown", "The target cannot be given Fixation, it is on cooldown for %s seconds!");
      this.add("mysticism.skill.mode.coalescence.fixation.no_target", "There was no target!");
      this.add("mysticism.skill.mode.coalescence.fixation.being_fixated", "You are frozen!");
      this.addSkillWithDescription(
         UniqueMysticismSkills.CONSTANT,
         "Constant",
         "Use magicules to force differing stats to remain the same. Become nigh invincible for a short period of time while "
      );
      this.add("mysticism.skill.mode.constant.time_remaining", "Time Remaining: %s");
      this.add("mysticism.skill.mode.constant.health", "Constant: Health");
      this.add("mysticism.skill.mode.constant.health.activated", "Your health is being maintained!");
      this.add("mysticism.skill.mode.constant.physical", "Constant: Physical");
      this.add("mysticism.skill.mode.constant.destruction", "Constant: Destruction");
      this.add("mysticism.skill.mode.constant.destruction.available", "Constant: Destruction is now available, with a damage of %s!");
      this.add("mysticism.skill.mode.constant.energy", "Constant: Energy");
      this.addSkillWithDescription(
         UniqueMysticismSkills.SUBJUGATOR,
         "Subjugator",
         "You've always been a powerful manipulator. Capable of bringing anyone and anything under your control. ...But, something is strange..."
      );
      this.addSkillModeNames(
         UniqueMysticismSkills.SUBJUGATOR, "takeover", "communication_movement", "communication_targeting", "unbreakable_defense", "return", "the_voice"
      );
      this.add(
         "mysticism.skill.subjugator.unbreakable_defense_toggled",
         "Your Unbreakable Defense from [Subjugator] is active! Skills and Magic cannot be used except for Battlewills!"
      );
      this.add(
         "mysticism.skill.subjugator.the_voice.unbreakable_defense_toggled",
         "<%s> Unbreakable Defense is active. You need to turn it off if you want to use your other abilities."
      );
      this.add("mysticism.skill.mode.subjugator.return.no_target", "There was no target!");
      this.add("mysticism.skill.mode.subjugator.return.not_subordinate", "%s is not a subordinate!");
      this.add("mysticism.skill.mode.subjugator.return.points", "%s's Loyalty Points: %s");
      this.add("mysticism.skill.mode.subjugator.takeover.fail.mp_too_low", "Your Magicules are too low. Must be greater than %s to use this.");
      this.add("mysticism.skill.mode.subjugator.takeover.fail.target_has_sin", "The target possesses a Sin-series skill. %s failed to activate.");
      this.add("mysticism.skill.mode.subjugator.takeover.fail.failed_to_gain", "You could not acquire %s from Takeover as your Magicules are too low.");
      this.add(
         "mysticism.skill.mode.subjugator.takeover.powerful_skill_copying", "%s has begun analyzing the target's powerful skills. It will take %s seconds."
      );
      this.add("mysticism.skill.mode.subjugator.takeover.completed", "%s has finished analyzing the target's powerful skills.");
      this.add("mysticism.skill.mode.subjugator.takeover.canceled_source_death", "%s has died. Their powerful Unique Skill has stopped copying.");
      this.add("mysticism.skill.subjugator.voice_unavailable", "Something here lays dormant.");
      this.add("mysticism.skill.subjugator.convo_busy", "You are already in a conversation!");
      this.add("mysticism.skill.subjugator.awakening_1", "<???> Hello...? Where... Where am I?");
      this.add("mysticism.skill.subjugator.awakening_2", "<???> You... You can hear me, right? Please, answer me.");
      this.add("mysticism.skill.subjugator.awakening_other", "Someone has awakened something that was meant to lay dormant.");
      this.add("mysticism.skill.subjugator.awakening_response_1", "[1] Who is this...?");
      this.add("mysticism.skill.subjugator.awakening_response_2", "[2] Um??? Get out of my chat???");
      this.add("mysticism.skill.subjugator.awakening_response_3", "[3] ...");
      this.add("mysticism.skill.subjugator.awakening_3", "<???> ...Apologies. I cannot quite recall anything. What is my purpose?");
      this.add("mysticism.skill.subjugator.awakening_2_response_1", "[1] ");
      this.add("mysticism.skill.subjugator.kill_1", "<%s> Good job.");
      this.add("mysticism.skill.subjugator.kill_2", "<%s> You are doing excellent.");
      this.add("mysticism.skill.subjugator.kill_3", "<%s> Dang, their guts flew everywhere.");
      this.add("mysticism.skill.subjugator.kill_4", "<%s> Nice!");
      this.add("mysticism.skill.subjugator.kill_5", "<%s> You're strong!");
      this.add("mysticism.skill.subjugator.death_1", "<%s> Death... So that is what it feels like.");
      this.add("mysticism.skill.subjugator.death_2", "<%s> Perhaps you should adopt a different strategy?");
      this.add("mysticism.skill.subjugator.death_3", "<%s> Dang, almost had that one.");
      this.add("mysticism.skill.subjugator.death_4", "<%s> Ouch... That's gotta hurt. Get back up and try again.");
      this.add("mysticism.skill.subjugator.death_5", "<%s> Wish I could help you, but I'm just your skill.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.CORRODER,
         "Corroder",
         "Apply powerful Corrosion to your physical attacks. Turn into Corrosion and sear your enemies. Deal massive armor-ignoring damage with a Corrosion slash."
      );
      this.addSkillModeNames(UniqueMysticismSkills.CORRODER, "dissolve", "cross_corrosion");
      this.addSkillWithDescription(
         UniqueMysticismSkills.CRASHER,
         "Crasher",
         "The essence of \"deletion\". Completely and utterly erase your foes from the plane of existence with the power of your sheer will alone."
      );
      this.addSkillModeNames(UniqueMysticismSkills.CRASHER, "destroyer_haki", "dimension_hopper");
      this.add("mysticism.skill.mode.crasher.dimension_hopper.current_dimension", "Dimension Selected: %s");
      this.add("mysticism.skill.mode.crasher.dimension_hopper.received_same", "The target dimension is the same as the current dimension!");
      this.add("mysticism.skill.mode.crasher.dimension_hopper.too_weak", "Crasher is too weak to leap from this dimension!");
      this.addSkillWithDescription(
         UniqueMysticismSkills.DREAMER,
         "Dreamer",
         "Dream a dream so grand it becomes a nightmare... Gain control over the forces of life and death, allowing you to cheat death and restore yourself back to a \"save point\"."
      );
      this.addSkillModeNames(UniqueMysticismSkills.DREAMER, "dream", "hypnosis", "dream_eater");
      this.add("mysticism.skill.mode.dreamer.dream.create", "You think of the place you're in... It starts to become vivid.");
      this.add("mysticism.skill.mode.dreamer.dream.cancel", "You let go of a foolish ideology.");
      this.add("mysticism.skill.mode.dreamer.dream.restore", "Suddenly... It's like your dreams became a reality.");
      this.add("mysticism.skill.mode.dreamer.dream.death", "If only death could stop dreams...");
      this.addSkillWithDescription(
         UniqueMysticismSkills.ENGINEER,
         "Engineer",
         "Tinkering and tinkering. No one understood the machines as much as you did. In fact, no one understood you either. Perhaps it was always meant to be."
      );
      this.addSkillModeNames(UniqueMysticismSkills.ENGINEER, "build", "bubble_shield");
      this.add("mysticism.skill.engineer.turret_evolved", "Your turret has reached Level %s!");
      this.add("mysticism.skill.engineer.dispenser_evolved", "Your dispenser has reached Level %s!");
      this.addSkillWithDescription(
         UniqueMysticismSkills.GARDENER,
         "Gardener",
         "The crops, the wild, everything was there for you. You sow the seeds and wish only for a bountiful harvest. And in turn, the crops bless you."
      );
      this.addSkillModeNames(UniqueMysticismSkills.GARDENER, "blessing", "overgrow", "entwine");
      this.add("mysticism.skill.mode.gardener.blessing.already_blessed", "This food stack has already been Blessed by %s.");
      this.add("mysticism.skill.mode.gardener.blessing.bless", "Blessed by %1$s at %2$s");
      this.add("mysticism.skill.mode.gardener.overgrow.not_enough_crops", "You need to grow %s more crops to gain a charge for Overgrow.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.HIDDEN_RULER,
         "Hidden Ruler",
         "Inspire your allies. Conscript allies in the dark. Copy abilities from your foes and Paste them onto allies."
      );
      this.addSkillModeNames(UniqueMysticismSkills.HIDDEN_RULER, "copy", "paste", "clear_bank", "darkness_conscription");
      this.add("mysticism.skill.hidden_ruler.user_died", "You died! Your Hidden Ruler skill bank was wiped.");
      this.add("mysticism.skill.mode.hidden_ruler.no_target", "There was no target!");
      this.add("mysticism.skill.mode.hidden_ruler.copy.copy_header", "Copied Skills:");
      this.add("mysticism.skill.mode.hidden_ruler.copy.no_skills", "None!");
      this.add("mysticism.skill.mode.hidden_ruler.copy.target_too_strong", "The target is stronger than the failure threshold of %s!");
      this.add("mysticism.skill.mode.hidden_ruler.copy.success_bank", "Since you already had %s, this skill was copied to Hidden Ruler's bank.");
      this.add("mysticism.skill.mode.hidden_ruler.copy.too_many_skills", "You have too many Copied skills!");
      this.add("mysticism.skill.mode.hidden_ruler.paste.no_skills", "There are no skills in Hidden Ruler's bank!");
      this.add("mysticism.skill.mode.hidden_ruler.paste.target_already_knows", "The target already knows %s!");
      this.add("mysticism.skill.mode.hidden_ruler.paste.selected", "Selected Skill: %s");
      this.add("mysticism.skill.mode.hidden_ruler.paste.success", "%s was successfully transferred to the target!");
      this.add("mysticism.skill.mode.hidden_ruler.clear_list.warning_1", "Are you sure?");
      this.add(
         "mysticism.skill.mode.hidden_ruler.clear_list.warning_2",
         "Using this mode will wipe Hidden Ruler's skill bank. If you're certain, use this skill mode again while crouching.\nIf this was a mistake, don't use this mode for 3 seconds."
      );
      this.add("mysticism.skill.mode.hidden_ruler.clear_list.success", "Hidden Ruler's skill bank has been wiped!");
      this.add("mysticism.skill.mode.hidden_ruler.clear_list.warning_gone", "Cancelled bank wipe!");
      this.add(
         "mysticism.skill.mode.hidden_ruler.darkness_conscription.too_bright",
         "It's too bright! BOTH you and the target need to be under light level %s to use this."
      );
      this.addSkillWithDescription(
         UniqueMysticismSkills.MALLEABLE,
         "Malleable",
         "Malleable like clay, the tides of fate are yours to mold to your desire. Your body seems to work like the metal of a forge, hardening and relaxing under pressure, as you merge yourself with the elements around you."
      );
      this.addSkillModeNames(UniqueMysticismSkills.MALLEABLE, "alchemy", "harden", "vessel");
      this.add("mysticism.skill.mode.malleable.alchemy.currently_selected", "Alchemy Selected: %s");
      this.add("mysticism.skill.mode.malleable.alchemy.activated", "Alchemy activated for %s seconds!");
      this.add("mysticism.skill.mode.malleable.alchemy.activated_toggle", "Alchemy activated!");
      this.add("mysticism.skill.mode.malleable.alchemy.deactivated", "Alchemy deactivated!");
      this.add("mysticism.skill.mode.malleable.harden.activated", "Harden activated!");
      this.addSkillWithDescription(
         UniqueMysticismSkills.PHASER,
         "Phaser",
         "Your eyes shift. They become able to see things you normally weren't able to see before... And you become able to do things you weren't able to do before."
      );
      this.addSkillModeNames(UniqueMysticismSkills.PHASER, "eject", "authority_of_the_gods", "intangibility");
      this.add("mysticism.skill.mode.phaser.intangibility_toggle", "Intangibility: Toggle");
      this.add("mysticism.skill.mode.phaser.eject.empty", "Your Eject storage is empty.");
      this.add("mysticism.skill.mode.phaser.authority_of_the_gods.charged", "Your Authority has finished charging.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.PROVIDER,
         "Provider",
         "The more you give away, the more you feel closer to yourself. Perhaps it even strengthens you along the way."
      );
      this.addSkillModeNames(UniqueMysticismSkills.PROVIDER, "generosity", "pay_it_forward");
      this.add(
         "mysticism.skill.mode.provider.generosity.halo_already_spawned", "Your Halo is already spawned! Despawn it by crouching and using the skill mode."
      );
      this.add("mysticism.skill.mode.provider.generosity.halo_removed", "Your Halo was removed!");
      this.add("mysticism.skill.mode.provider.generosity.no_halo", "There was no Halo to remove!");
      this.add("mysticism.skill.mode.provider.pay_it_forward.too_little_contributions", "Too little contributions to use! Must have minimally %s.");
      this.add("mysticism.skill.mode.provider.pay_it_forward.already_active", "Pay It Forward is already active! Crouch and use to cancel it.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.REDUCER,
         "Reducer",
         "Manipulate and wield the power of energy itself, being able to channel the power of the holy element into super strong attacks."
      );
      this.addSkillModeNames(UniqueMysticismSkills.REDUCER, "emancipation", "purity_edge", "mono_breaker");
      this.addSkillWithDescription(
         UniqueMysticismSkills.REPEATER,
         "Repeater",
         "Through the lens, you trap light. You do something special, and you feel like you’ve already done it in millions of realities. Like the things you’ve done and led up to this moment were predestined. Maybe you can change that. "
      );
      this.addSkillModeNames(UniqueMysticismSkills.REPEATER, "recursion_depth", "flarefrost_storehouse");
      this.add("mysticism.skill.mode.repeater.recursion_depth.activated", "Recursion Depth activated.");
      this.add("mysticism.skill.mode.repeater.recursion_depth.deactivated", "Recursion Depth deactivated.");
      this.add("mysticism.skill.mode.repeater.flarefrost_storehouse.storehouse_empty", "The storehouse is empty.");
      this.add("mysticism.skill.mode.repeater.flarefrost_storehouse.released", "Defeating the Lost Spirit made its skill's energy combine with yours!");
      this.addSkillWithDescription(
         UniqueMysticismSkills.RESTRICTED,
         "Restricted",
         "You were cursed from birth. Your raw, unfiltered potential, blocked by a curse gifted by the heavens. Your skill... It's stopping you from learning other skills. But why...?"
      );
      this.addSkillModeNames(UniqueMysticismSkills.RESTRICTED, "dash", "counter");
      this.add("mysticism.skill.restricted.unable_to_learn_skills", "You tried to learn the skill %s, but %s prevents you from doing so.");
      this.add("mysticism.skill.restricted.cannot_copy_restricted", "You tried to copy the skill %s, but you were unable to.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.SCHOLAR,
         "Scholar",
         "Learn and master skills quicker. Study your targets when they attack. Learn enchantments and apply them to weaponry."
      );
      this.add("mysticism.skill.scholar.study_gain", "Gained a study point for %s!");
      this.add("mysticism.skill.scholar.library_of_babel.leveled_up", "Enchantment [%s]'s level was raised! Consumed %s experience levels.");
      this.add("mysticism.skill.scholar.library_of_babel.failed_to_level_up", "You could not raise the book's level! Needs %s experience levels.");
      this.add("mysticism.skill.scholar.library_of_babel.level_refunded", "By lowering the level of [%s] you have received %s experience levels.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.STAGNATOR,
         "Stagnator",
         "Cease all change. The times call for it. Prevent targets from moving or regenerating, and stop all things from harming you."
      );
      this.addSkillModeNames(UniqueMysticismSkills.STAGNATOR, "stagnate_aura", "inaction", "stasis_coat", "stasis_shot");
      this.addSkillWithDescription(
         UniqueMysticismSkills.SPIRITUALIST,
         "Spiritualist",
         "The souls of the world flock to you, the one who can both see and commune with the dead. Through your knowledge of the occult, harvest souls and use them to empower your attacks."
      );
      this.addSkillModeNames(UniqueMysticismSkills.SPIRITUALIST, "summon_scythe", "carve", "enhance");
      this.addSkillWithDescription(
         UniqueMysticismSkills.SCHRODINGER,
         "Schrödinger",
         "Is it alive, or is it dead? The only way to find out is to open the box. But since it's a cat, maybe it'll grant you some of its properties."
      );
      this.addSkillWithDescription(
         UniqueMysticismSkills.CULTIVATOR,
         "Cultivator",
         "Embody the relentless pursuit of ascension, gathering and refining energy to reach unparalleled heights. A Cultivator walks the path of self-perfection and dominion over all."
      );
      this.addSkillModeNames(UniqueMysticismSkills.CULTIVATOR, "breakthrough");
      this.add("mysticism.skill.mode.cultivator.breakthrough.not_enough_kills", "You need %s more mob kills in order to use this ability!");
      this.add(
         "mysticism.skill.mode.cultivator.breakthrough.cultivation_started",
         "Cultivation started! Do not die! Dying will cause Cultivation to fail and force you to go back to zero (0 mastery as well)!"
      );
      this.add(
         "mysticism.skill.mode.cultivator.breakthrough.died", "Your death caused the energy within you to become imbalanced. Start from Cultivation Level 0."
      );
      this.add("mysticism.skill.mode.cultivator.breakthrough.available", "You can now use Breakthrough!");
      this.add("mysticism.skill.mode.cultivator.breakthrough.broken_through", "You have broken through to Cultivation Level %s!");
      this.add("mysticism.skill.mode.cultivator.breakthrough.already_cultivating", "You are already Cultivating.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.CAPTIVATOR,
         "Captivator",
         "Your previous life, you were the brightest, shining star. Your ability to turn lies into truths… Perhaps it’s the other way around? Whatever it may be, your eyes shine brilliantly in response to being on the biggest stage."
      );
      this.addSkillModeNames(UniqueMysticismSkills.CAPTIVATOR, "performance", "masquerade", "unveil", "star_power");
      this.add("mysticism.skill.mode.captivator.performance.lie", "\"I'm in love with you,\" my career is built on such a lie!");
      this.add("mysticism.skill.mode.captivator.performance.truth", "So, lying surely is the greatest kind of love!");
      this.add("mysticism.skill.captivator.target_immune", "Target %s is immune to the effects of this mode.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.JINCHURIKI,
         "Jinchuriki",
         "That voice... It's familiar, yet something so distant. Another side of you, perhaps. But it's something that can be controlled. Provided you have the right tenacity to do it."
      );
      this.addSkillModeNames(UniqueMysticismSkills.JINCHURIKI, "tailed_beast_bomb", "tailed_beast_cloak");
      this.add("mysticism.skill.mode.jinchuriki.tailed_beast_cloak.check_uses", "Current number of uses: %s.");
      this.add("mysticism.skill.mode.jinchuriki.tailed_beast_cloak.current_cloak", "Current cloak state: %s.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.MELANCHOLY,
         "Melancholy",
         "Summon an aura of sorrow that slows and weakens nearby enemies, filling them with despair. The skill also throws objects or air, amplifying the feeling of hopelessness around you."
      );
      this.addSkillModeNames(UniqueMysticismSkills.MELANCHOLY, "gust", "turbulent_force", "grief");
      this.addSkillWithDescription(
         UniqueMysticismSkills.VICTORIOUS_HARBINGER,
         "Victorious Harbinger",
         "You are the forerunner of victory, your presence declares near fated victory, shatter the morale of your enemies and have no doubt in your abilities."
      );
      this.addSkillModeNames(UniqueMysticismSkills.VICTORIOUS_HARBINGER, "might", "tenacious_resolve");
      this.addSkillWithDescription(
         UniqueMysticismSkills.KYUREM,
         "Kyurem",
         "A husk of what you formally were. The leftovers, discarded without a second thought when the split happened. Coldness seeps through your skin as you freeze everything to reclaim your other parts."
      );
      this.addSkillModeNames(UniqueMysticismSkills.KYUREM, "glaciate", "draconic_pulse", "freeze_dry", "blizzard");
      this.addSkillWithDescription(
         UniqueMysticismSkills.RESHIRAM,
         "Reshiram",
         "You seek to build a world of truths. Are you prepared to scorch and burn everything in your path to make the truthful world you seek so badly?"
      );
      this.addSkillModeNames(UniqueMysticismSkills.RESHIRAM, "blue_flare", "fusion_flare", "draconic_meteor", "flamethrower");
      this.addSkillWithDescription(
         UniqueMysticismSkills.ZEKROM,
         "Zekrom",
         "You seek to build a world of ideals. Be warned as not everyone shares the same ideals. Raze anything in your path with a clap of lightning."
      );
      this.addSkillModeNames(UniqueMysticismSkills.ZEKROM, "bolt_strike", "fusion_bolt", "draconic_breath", "thunderbolt");
      this.add("mysticism.skill.looter.shadow_storage_mode.shadow_storage", "Changed %s's Shadow Storage to Shadow Storage.");
      this.add("mysticism.skill.looter.shadow_storage_mode.come_forth", "Changed %s's Shadow Storage to Come Forth.");
      this.add("mysticism.skill.looter.shadow_storage_mode.return", "Changed %s's Shadow Storage to Return.");
      this.addSkillWithDescription(
         UniqueMysticismSkills.GATEKEEPER,
         "Gatekeeper",
         "Wield the authority of kings and command treasures beyond mortal reach. Summon divine armaments from the vault, binding foes in chains of judgment while asserting dominion over all."
      );
      this.addSkillModeNames(UniqueMysticismSkills.GATEKEEPER, "enkidu", "kings_storehouse", "gate_of_babylon");
   }

   private void ultimateSkills() {
      this.addSkillWithTitle(UltimateMysticismSkills.EMBRYO, "Embryo", "The Seed of Genesis");
      this.addMultiDescription(UltimateMysticismSkills.EMBRYO, "Embryo", "Seems to do nothing...", "But...");
   }

   private void spiritualMagics() {
      this.addSkillWithDescription(SpiritualMysticismMagics.GRAVITATIONAL_VOID, "Gravitational Void", "A black hole sucking everything into a singularity");
   }

   private void deaths() {
      this.addDeathMessage(
         MysticismDamageTypes.BRIMSTONE_FLAMES,
         "%1$s's flesh was completely reduced to ash by Brimstone Flames",
         "%1$s's flesh was completely reduced to ash by %2$s's Brimstone Flames"
      );
      this.addDeathMessage(
         MysticismDamageTypes.DREAM_EATER,
         "%1$s's dreams turned into a nightmare by Dream Eater",
         "%1$s's dreams turned into a nightmare by %2$s's Dream Eater"
      );
      this.addDeathMessage(
         MysticismDamageTypes.REPEATED, "%1$s's existence was removed in another reality", "%1$s's existence was removed in another reality by %2$s"
      );
      this.addDeathMessage(MysticismDamageTypes.RUPTURED_VEINS, "%1$s's veins were ruptured", "%1$s's veins were ruptured by %2$s");
      this.addDeathMessage(MysticismDamageTypes.VOID, "%1$s was collapsed into the void", "%1$s was collapsed into the void by %2$s");
   }

   private void soundSubtitles() {
      this.addSound((SoundEvent)MysticismSoundEvents.BULLET.get(), "Bullet sound");
      this.addSound((SoundEvent)MysticismSoundEvents.SCYTHE_SLASH.get(), "Scythe slash sound");
      this.addSound((SoundEvent)MysticismSoundEvents.SENTRY_SHOOT.get(), "Sentry shoots");
      this.addSound((SoundEvent)MysticismSoundEvents.MISSILE_SHOOT.get(), "Sentry shoots missile");
      this.addSound((SoundEvent)MysticismSoundEvents.DISPENSER_HEAL.get(), "Dispenser heals");
   }

   private void miscItems() {
      this.addItem(MysticismMaterialItems.DARKNESS_CORE, "Darkness Core");
   }

   private void mobDropItems() {
      this.addItem(MysticismMobDropItems.FLAME_ESSENCE, "Flame Essence");
      this.addItem(MysticismMobDropItems.CRYPTID_ESSENCE, "Cryptid Essence");
      this.addItem(MysticismMobDropItems.ICE_ESSENCE, "Ice Essence");
      this.addItem(MysticismMobDropItems.LIGHTNING_ESSENCE, "Lightning Essence");
   }

   private void weaponItems() {
      this.addItem(MysticismWeaponItems.AXIOM, "Axiom");
      this.addItem(MysticismWeaponItems.RITUAL_SCYTHE, "Ritual Scythe");
      this.addItem(MysticismWeaponItems.WALTZ, "Waltz");
   }

   private void tooltips() {
      this.add("tooltip.mysticism.darkness_core", "Used to convert a Fallen Angel into a Fallen. Cannot be used by any other race.");
      this.add("tooltip.mysticism.strange_key_left", "Part of a greater whole. Received from felling the Ruler of the Skies.");
   }

   private void other() {
      this.add("mysticism.fallen_angel_evolution", "You feel your wings darkening... You fell from grace!");
      this.add("mysticism.phantom_evolution", "You feel your wings melting inside your body... You fell from grace!");
      this.add("mysticism.staff_officer_evolution", "You feel your purity leaving your being... You fell from grace!");
      this.add("mysticism.fallen_evolution", "You have completely lost your connection to the light attribute.");
      this.add("mysticism.fallen_evolution_fail", "You can't use that.");
      this.add("mysticism.tengu_evolution", "You feel yourself being incarnated. You became a Tengu!");
      this.add("mysticism.doll_evolution", "You feel yourself being incarnated.");
      this.add("mysticism.skill.output_percentage", "Current Percentage: %s%%");
      this.add("mysticism.targeting.clone", "You cannot target a clone.");
      this.add("mysticism.skill.acquire_failed.ap", "Failed to acquire %s due to the lack of Aura.");
   }

   private void stats() {
      this.add("stat.mysticism.lightning_strikes", "Number of Lightning Strikes.");
   }

   private void oreBlocks() {
      this.addBlock(MysticismBlocks.ICE_ORE, "Ice Ore");
   }

   private void attributes() {
      this.addAttribute(MysticismAttributes.MAX_SOUL_ENERGY, "Max Soul Energy");
      this.addAttribute(MysticismAttributes.COLD_BOOST, "Cold Boost");
      this.addAttribute(MysticismAttributes.COLD_RESIST_DEGRADATION, "Cold Resist Degradation");
      this.add("mysticism.soulEnergy.lost_energy", "Due to acquiring %s, you have lost %s Soul Energy.");
      this.add("mysticism.soulEnergy.skill_gain_energy", "Due to losing %s, you have gained %s Soul Energy back.");
      this.add("mysticism.soulEnergy.no_skill", "You did not have enough Soul Energy (needed %s) to acquire %s.");
      this.add("mysticism.soulEnergy.acquire_start", "You have %s Soul Energy.");
      this.add("mysticism.soulEnergy.acquire_awakening", "Awakening increased your Soul Energy by %s!");
      this.add("mysticism.soulEnergy.acquire_named_by", "Receiving a name by this individual increased your Soul Energy by %s!");
      this.add("mysticism.soulEnergy.acquire_named", "Receiving a name increased your Soul Energy by %s!");
      this.add("mysticism.soulEnergy.acquire_full_reset", "Reincarnating completely increased your Soul Energy by %s!");
   }

   private void commandMessages() {
      this.add("mysticism.command.soul_energy.get", "%s's soul energy is currently %s.");
      this.add("mysticism.command.soul_energy.get_max", "%s's Base amount of Max soul energy is currently %s.");
      this.add("mysticism.command.soul_energy.set", "%s's soul energy has been set to %s.");
      this.add("mysticism.command.soul_energy.set_max", "%s's Base amount of Max soul energy has been set to %s.");
      this.add("mysticism.command.soul_energy.reset", "%s's soul energy has been reset to %s.");
   }

   private void gameRules() {
      this.add("gamerule.uniqueSECost", "Will Unique Skills cost Soul Energy");
      this.add("gamerule.mpSkillCost.description", "Do Unique Skills cost Soul Energy on acquirement.");
   }

   private void guis() {
      this.add("mysticism.menu.element", "Element:");
   }
}
