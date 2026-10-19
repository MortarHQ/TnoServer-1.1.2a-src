package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;

public class ESSoundProvider extends SoundDefinitionsProvider {
   public ESSoundProvider(PackOutput output, ExistingFileHelper helper) {
      super(output, "eternal_starlight", helper);
   }

   private ResourceLocation mcLoc(String s) {
      return ResourceLocation.withDefaultNamespace(s);
   }

   private ResourceLocation loc(String s) {
      return EternalStarlight.id(s);
   }

   public void registerSounds() {
      this.add(
         ESSoundEvents.MUSIC_DIMENSION.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_STARLIGHT_FOREST.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/starlight_permafrost_forest")).weight(5).stream(),
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_DARK_SWAMP.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/wailing_well")).weight(5).stream(),
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_SCARLET_FOREST.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/scarlet_forest")).weight(5).stream(),
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_TORREYA_FOREST.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/the_thorny_reign")).weight(5).stream(),
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_CRYSTALLIZED_DESERT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/posterity")).weight(5).stream(),
                  sound(this.loc("music/dimension/tranquility")).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_STARLIT_SEA.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/stars_shining_upon_the_sea")).weight(5).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(
         ESSoundEvents.MUSIC_BIOME_THE_ABYSS.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("music/biome/profundity")).weight(5).stream(),
                  sound(this.loc("music/dimension/whisper_of_the_stars")).stream(),
                  sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream(),
                  sound(this.loc("music/dimension/nest")).stream()
               }
            )
      );
      this.add(ESSoundEvents.MUSIC_BOSS_GATEKEEPER.get(), definition().with(sound(this.loc("music/boss/gatekeeper")).stream()));
      this.add(ESSoundEvents.MUSIC_BOSS_STARLIGHT_GOLEM.get(), definition().with(sound(this.loc("music/boss/starlight_golem")).stream()));
      this.add(ESSoundEvents.MUSIC_BOSS_LUNAR_MONSTROSITY.get(), definition().with(sound(this.loc("music/boss/lunar_monstrosity")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_WHISPER_OF_THE_STARS.get(), definition().with(sound(this.loc("music/dimension/whisper_of_the_stars")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_DUSK_O_EREYESTERDAY.get(), definition().with(sound(this.loc("music/dimension/dusk_o_ereyesterday")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_TRANQUILITY.get(), definition().with(sound(this.loc("music/dimension/tranquility")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_NEST.get(), definition().with(sound(this.loc("music/dimension/nest")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_POSTERITY.get(), definition().with(sound(this.loc("music/biome/posterity")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_THE_THORNY_REIGN.get(), definition().with(sound(this.loc("music/biome/the_thorny_reign")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_PROFUNDITY.get(), definition().with(sound(this.loc("music/biome/profundity")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_WAILING_WELL.get(), definition().with(sound(this.loc("music/biome/wailing_well")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_STARS_SHINING_UPON_THE_SEA.get(), definition().with(sound(this.loc("music/biome/stars_shining_upon_the_sea")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_OPTIMIZED_OPTION.get(), definition().with(sound(this.loc("music/boss/gatekeeper")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_MECHANICAL_FOSSIL.get(), definition().with(sound(this.loc("music/boss/starlight_golem")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_FAKE_LIGHT.get(), definition().with(sound(this.loc("music/boss/lunar_monstrosity")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_TRANQUILITY_II.get(), definition().with(sound(this.loc("music/disc/tranquility_ii")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_ATLANTIS.get(), definition().with(sound(this.loc("music/disc/atlantis")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_SACRED_DESERT.get(), definition().with(sound(this.loc("music/disc/sacred_desert")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_SPIRIT.get(), definition().with(sound(this.loc("music/disc/spirit")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_ETHER_RAIN.get(), definition().with(sound(this.loc("music/disc/ether_rain")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_BRISK.get(), definition().with(sound(this.loc("music/disc/brisk")).stream()));
      this.add(ESSoundEvents.MUSIC_DISC_MOONLIGHT.get(), definition().with(sound(this.loc("music/disc/moonlight")).stream()));
      this.add(
         ESSoundEvents.ARMOR_EQUIP_ALCHEMIST.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_leather1")),
                  sound(this.mcLoc("item/armor/equip_leather2")),
                  sound(this.mcLoc("item/armor/equip_leather3")),
                  sound(this.mcLoc("item/armor/equip_leather4")),
                  sound(this.mcLoc("item/armor/equip_leather5")),
                  sound(this.mcLoc("item/armor/equip_leather6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_alchemist")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_AMARAMBER.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_leather1")),
                  sound(this.mcLoc("item/armor/equip_leather2")),
                  sound(this.mcLoc("item/armor/equip_leather3")),
                  sound(this.mcLoc("item/armor/equip_leather4")),
                  sound(this.mcLoc("item/armor/equip_leather5")),
                  sound(this.mcLoc("item/armor/equip_leather6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_amaramber")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_AETHERSENT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_leather1")),
                  sound(this.mcLoc("item/armor/equip_leather2")),
                  sound(this.mcLoc("item/armor/equip_leather3")),
                  sound(this.mcLoc("item/armor/equip_leather4")),
                  sound(this.mcLoc("item/armor/equip_leather5")),
                  sound(this.mcLoc("item/armor/equip_leather6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_aethersent")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_THERMAL_SPRINGSTONE.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_iron1")),
                  sound(this.mcLoc("item/armor/equip_iron2")),
                  sound(this.mcLoc("item/armor/equip_iron3")),
                  sound(this.mcLoc("item/armor/equip_iron4")),
                  sound(this.mcLoc("item/armor/equip_iron5")),
                  sound(this.mcLoc("item/armor/equip_iron6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_thermal_springstone")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_GLACITE.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_iron1")),
                  sound(this.mcLoc("item/armor/equip_iron2")),
                  sound(this.mcLoc("item/armor/equip_iron3")),
                  sound(this.mcLoc("item/armor/equip_iron4")),
                  sound(this.mcLoc("item/armor/equip_iron5")),
                  sound(this.mcLoc("item/armor/equip_iron6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_glacite")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_STARLIT_DIAMOND.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_diamond1")),
                  sound(this.mcLoc("item/armor/equip_diamond2")),
                  sound(this.mcLoc("item/armor/equip_diamond3")),
                  sound(this.mcLoc("item/armor/equip_diamond4")),
                  sound(this.mcLoc("item/armor/equip_diamond5")),
                  sound(this.mcLoc("item/armor/equip_diamond6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_starlit_diamond")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_DEEPSILVER.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_diamond1")),
                  sound(this.mcLoc("item/armor/equip_diamond2")),
                  sound(this.mcLoc("item/armor/equip_diamond3")),
                  sound(this.mcLoc("item/armor/equip_diamond4")),
                  sound(this.mcLoc("item/armor/equip_diamond5")),
                  sound(this.mcLoc("item/armor/equip_diamond6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_deepsilver")
      );
      this.add(
         ESSoundEvents.ARMOR_EQUIP_UNREALIUM.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("item/armor/equip_diamond1")),
                  sound(this.mcLoc("item/armor/equip_diamond2")),
                  sound(this.mcLoc("item/armor/equip_diamond3")),
                  sound(this.mcLoc("item/armor/equip_diamond4")),
                  sound(this.mcLoc("item/armor/equip_diamond5")),
                  sound(this.mcLoc("item/armor/equip_diamond6"))
               }
            )
            .subtitle("subtitles.item.armor.eternal_starlight.equip_unrealium")
      );
      this.add(ESSoundEvents.WHIP_SWISH.get(), definition().with(sound(this.loc("item/whip/swish"))).subtitle("subtitles.item.eternal_starlight.whip.swish"));
      this.add(ESSoundEvents.WHIP_CRACK.get(), definition().with(sound(this.loc("item/whip/crack"))).subtitle("subtitles.item.eternal_starlight.whip.crack"));
      this.add(
         ESSoundEvents.CRYSTAL_GREATSWORD_CHIME.get(),
         definition().with(sound(this.loc("item/crystal_greatsword/chime"))).subtitle("subtitles.item.eternal_starlight.crystal_greatsword.chime")
      );
      this.add(
         ESSoundEvents.CHAIN_OF_SOULS_ABSORB.get(),
         definition().with(sound(this.loc("item/chain_of_souls/absorb"))).subtitle("subtitles.item.eternal_starlight.chain_of_souls.absorb")
      );
      this.add(
         ESSoundEvents.CRESCENT_SPEAR_THROW.get(),
         definition().with(sound(this.loc("item/crescent_spear/throw"))).subtitle("subtitles.item.eternal_starlight.crescent_spear.throw")
      );
      this.add(
         ESSoundEvents.SEEDS_LAUNCHER_SHOOT.get(),
         definition().with(sound(this.loc("item/seeds_launcher/shoot"))).subtitle("subtitles.item.eternal_starlight.seeds_launcher.shoot")
      );
      this.add(
         ESSoundEvents.AETHERSENT_METEOR_WHOOSH.get(),
         definition().with(sound(this.loc("entity/aethersent_meteor/whoosh"))).subtitle("subtitles.entity.eternal_starlight.aethersent_meteor.whoosh")
      );
      this.add(
         ESSoundEvents.LASER_BEAM_HUM.get(),
         definition().with(sound(this.loc("entity/laser_beam/hum"))).subtitle("subtitles.entity.eternal_starlight.laser_beam.hum")
      );
      this.add(
         ESSoundEvents.FROZEN_TUBE_THROW.get(),
         definition().with(sound(this.loc("entity/frozen_tube/throw"))).subtitle("subtitles.entity.eternal_starlight.frozen_tube.throw")
      );
      this.add(
         ESSoundEvents.FROZEN_TUBE_BREAK.get(),
         definition().with(sound(this.loc("entity/frozen_tube/break"))).subtitle("subtitles.entity.eternal_starlight.frozen_tube.break")
      );
      this.add(
         ESSoundEvents.SONAR_BOMB_EXPLODE.get(),
         definition().with(sound(this.loc("entity/sonar_bomb/explode"))).subtitle("subtitles.entity.eternal_starlight.sonar_bomb.explode")
      );
      this.add(
         ESSoundEvents.STARFIRE_WHOOSH.get(),
         definition().with(sound(this.loc("entity/starfire/whoosh"))).subtitle("subtitles.entity.eternal_starlight.starfire.whoosh")
      );
      this.add(
         ESSoundEvents.SEEKING_EYE_LAUNCH.get(),
         definition()
            .with(new Sound[]{sound(this.mcLoc("entity/endereye/endereye_launch1")), sound(this.mcLoc("entity/endereye/endereye_launch2"))})
            .subtitle("subtitles.entity.eternal_starlight.seeking_eye.launch")
      );
      this.add(
         ESSoundEvents.SEEKING_EYE_DEATH.get(),
         definition()
            .with(new Sound[]{sound(this.mcLoc("entity/endereye/dead1")).volume(1.3), sound(this.mcLoc("entity/endereye/dead2")).volume(1.3)})
            .subtitle("subtitles.entity.eternal_starlight.seeking_eye.death")
      );
      this.add(
         ESSoundEvents.BOARWARF_AMBIENT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/piglin/idle1")).volume(0.66),
                  sound(this.mcLoc("mob/piglin/idle2")).volume(0.66),
                  sound(this.mcLoc("mob/piglin/idle3")).volume(0.66),
                  sound(this.mcLoc("mob/piglin/idle4")).volume(0.66),
                  sound(this.mcLoc("mob/piglin/idle5")).volume(0.66)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.boarwarf.ambient")
      );
      this.add(
         ESSoundEvents.BOARWARF_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/piglin/hurt1")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/hurt2")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/hurt3")).volume(0.7)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.boarwarf.hurt")
      );
      this.add(
         ESSoundEvents.BOARWARF_DEATH.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/piglin/death1")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/death2")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/death3")).volume(0.8),
                  sound(this.mcLoc("mob/piglin/death4")).volume(0.8)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.boarwarf.death")
      );
      this.add(
         ESSoundEvents.BOARWARF_TRADE.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/piglin/admire1")).volume(0.8),
                  sound(this.mcLoc("mob/piglin/admire2")).volume(0.8),
                  sound(this.mcLoc("mob/piglin/celebrate2")).volume(0.8),
                  sound(this.mcLoc("mob/piglin/celebrate4")).volume(0.85)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.boarwarf.trade")
      );
      this.add(
         ESSoundEvents.BOARWARF_YES.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/piglin/celebrate1")).volume(0.8),
                  sound(this.mcLoc("mob/piglin/celebrate2")).volume(0.8),
                  sound(this.mcLoc("mob/piglin/celebrate3")).volume(0.75),
                  sound(this.mcLoc("mob/piglin/celebrate4")).volume(0.8)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.boarwarf.yes")
      );
      this.add(
         ESSoundEvents.BOARWARF_NO.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/piglin/angry1")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/angry2")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/angry3")).volume(0.7),
                  sound(this.mcLoc("mob/piglin/angry4")).volume(0.7)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.boarwarf.no")
      );
      this.add(
         ESSoundEvents.ASTRAL_GOLEM_ATTACK.get(),
         definition().with(sound(this.mcLoc("mob/irongolem/throw"))).subtitle("subtitles.entity.eternal_starlight.astral_golem.attack")
      );
      this.add(
         ESSoundEvents.ASTRAL_GOLEM_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/irongolem/hit1")),
                  sound(this.mcLoc("mob/irongolem/hit2")),
                  sound(this.mcLoc("mob/irongolem/hit3")),
                  sound(this.mcLoc("mob/irongolem/hit4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.astral_golem.hurt")
      );
      this.add(
         ESSoundEvents.ASTRAL_GOLEM_DEATH.get(),
         definition().with(sound(this.mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity.eternal_starlight.astral_golem.death")
      );
      this.add(
         ESSoundEvents.ASTRAL_GOLEM_REPAIR.get(),
         definition().with(sound(this.mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity.eternal_starlight.astral_golem.repair")
      );
      this.add(ESSoundEvents.ENT_HURT.get(), definition().with(sound(this.loc("mob/ent/hurt"))).subtitle("subtitles.entity.eternal_starlight.ent.hurt"));
      this.add(
         ESSoundEvents.AURORA_DEER_AMBIENT.get(),
         definition()
            .with(new Sound[]{sound(this.loc("mob/aurora_deer/idle1")), sound(this.loc("mob/aurora_deer/idle2")), sound(this.loc("mob/aurora_deer/idle3"))})
            .subtitle("subtitles.entity.eternal_starlight.aurora_deer.ambient")
      );
      this.add(
         ESSoundEvents.AURORA_DEER_HURT.get(),
         definition()
            .with(new Sound[]{sound(this.loc("mob/aurora_deer/hurt1")), sound(this.loc("mob/aurora_deer/hurt2"))})
            .subtitle("subtitles.entity.eternal_starlight.aurora_deer.hurt")
      );
      this.add(
         ESSoundEvents.AURORA_DEER_DEATH.get(),
         definition().with(sound(this.loc("mob/aurora_deer/hurt1"))).subtitle("subtitles.entity.eternal_starlight.aurora_deer.death")
      );
      this.add(
         ESSoundEvents.STARFIRE_BIRD_AMBIENT.get(),
         definition().with(sound(this.loc("mob/starfire_bird/idle"))).subtitle("subtitles.entity.eternal_starlight.starfire_bird.ambient")
      );
      this.add(
         ESSoundEvents.STARFIRE_BIRD_HURT.get(),
         definition().with(sound(this.loc("mob/starfire_bird/hurt"))).subtitle("subtitles.entity.eternal_starlight.starfire_bird.hurt")
      );
      this.add(
         ESSoundEvents.STARFIRE_BIRD_DEATH.get(),
         definition().with(sound(this.loc("mob/starfire_bird/death"))).subtitle("subtitles.entity.eternal_starlight.starfire_bird.death")
      );
      this.add(
         ESSoundEvents.AETHERSENT_GOLEM_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/irongolem/damage1")).volume(0.8),
                  sound(this.mcLoc("mob/irongolem/damage1")).pitch(0.8).volume(0.8),
                  sound(this.mcLoc("mob/irongolem/damage2")).volume(0.8),
                  sound(this.mcLoc("mob/irongolem/damage2")).pitch(0.8).volume(0.8)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.aethersent_golem.hurt")
      );
      this.add(
         ESSoundEvents.AETHERSENT_GOLEM_DEATH.get(),
         definition().with(sound(this.mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity.eternal_starlight.aethersent_golem.death")
      );
      this.add(
         ESSoundEvents.AETHERSENT_GOLEM_SHOOT.get(),
         definition().with(sound(this.loc("mob/aethersent_golem/shoot"))).subtitle("subtitles.entity.eternal_starlight.aethersent_golem.shoot")
      );
      this.add(
         ESSoundEvents.SEEKER_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/slime/big1")),
                  sound(this.mcLoc("mob/slime/big2")),
                  sound(this.mcLoc("mob/slime/big3")),
                  sound(this.mcLoc("mob/slime/big4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.seeker.hurt")
      );
      this.add(
         ESSoundEvents.SEEKER_DEATH.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/slime/big1")),
                  sound(this.mcLoc("mob/slime/big2")),
                  sound(this.mcLoc("mob/slime/big3")),
                  sound(this.mcLoc("mob/slime/big4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.seeker.death")
      );
      this.add(
         ESSoundEvents.THIRST_WALKER_AMBIENT.get(),
         definition()
            .with(
               new Sound[]{sound(this.loc("mob/thirst_walker/idle1")), sound(this.loc("mob/thirst_walker/idle2")), sound(this.loc("mob/thirst_walker/idle3"))}
            )
            .subtitle("subtitles.entity.eternal_starlight.thirst_walker.ambient")
      );
      this.add(
         ESSoundEvents.THIRST_WALKER_HURT.get(),
         definition()
            .with(new Sound[]{sound(this.loc("mob/thirst_walker/hurt1")), sound(this.loc("mob/thirst_walker/hurt2"))})
            .subtitle("subtitles.entity.eternal_starlight.thirst_walker.hurt")
      );
      this.add(
         ESSoundEvents.THIRST_WALKER_DEATH.get(),
         definition().with(sound(this.loc("mob/thirst_walker/death"))).subtitle("subtitles.entity.eternal_starlight.thirst_walker.death")
      );
      this.add(
         ESSoundEvents.CRETEOR_ROLL.get(), definition().with(sound(this.loc("mob/creteor/roll"))).subtitle("subtitles.entity.eternal_starlight.creteor.roll")
      );
      this.add(
         ESSoundEvents.CRETEOR_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.loc("mob/creteor/hurt1")),
                  sound(this.loc("mob/creteor/hurt2")),
                  sound(this.loc("mob/creteor/hurt3")),
                  sound(this.loc("mob/creteor/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.creteor.hurt")
      );
      this.add(
         ESSoundEvents.CRETEOR_DEATH.get(),
         definition().with(sound(this.loc("mob/creteor/death"))).subtitle("subtitles.entity.eternal_starlight.creteor.death")
      );
      this.add(
         ESSoundEvents.CRETEOR_CHARGE.get(),
         definition().with(sound(this.loc("mob/creteor/charge"))).subtitle("subtitles.entity.eternal_starlight.creteor.charge")
      );
      this.add(
         ESSoundEvents.CRETEOR_EXPLODE.get(),
         definition().with(sound(this.loc("mob/creteor/explode"))).subtitle("subtitles.entity.eternal_starlight.creteor.explode")
      );
      this.add(
         ESSoundEvents.STRANGHOUL_AMBIENT.get(),
         definition()
            .with(new Sound[]{sound(this.loc("mob/stranghoul/idle1")), sound(this.loc("mob/stranghoul/idle2"))})
            .subtitle("subtitles.entity.eternal_starlight.stranghoul.ambient")
      );
      this.add(
         ESSoundEvents.STRANGHOUL_HURT.get(),
         definition()
            .with(new Sound[]{sound(this.loc("mob/stranghoul/hurt1")), sound(this.loc("mob/stranghoul/hurt2"))})
            .subtitle("subtitles.entity.eternal_starlight.stranghoul.hurt")
      );
      this.add(
         ESSoundEvents.STRANGHOUL_DEATH.get(),
         definition()
            .with(new Sound[]{sound(this.loc("mob/stranghoul/death1")), sound(this.loc("mob/stranghoul/death2"))})
            .subtitle("subtitles.entity.eternal_starlight.stranghoul.death")
      );
      this.add(
         ESSoundEvents.STRANGHOUL_SHOOT.get(),
         definition().with(sound(this.mcLoc("random/bow"))).subtitle("subtitles.entity.eternal_starlight.stranghoul.shoot")
      );
      this.add(
         ESSoundEvents.LUMINOFISH_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/hurt1")),
                  sound(this.mcLoc("entity/fish/hurt2")),
                  sound(this.mcLoc("entity/fish/hurt3")),
                  sound(this.mcLoc("entity/fish/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminofish.hurt")
      );
      this.add(
         ESSoundEvents.LUMINOFISH_DEATH.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/hurt1")),
                  sound(this.mcLoc("entity/fish/hurt2")),
                  sound(this.mcLoc("entity/fish/hurt3")),
                  sound(this.mcLoc("entity/fish/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminofish.death")
      );
      this.add(
         ESSoundEvents.LUMINOFISH_FLOP.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/flop1")).volume(0.3),
                  sound(this.mcLoc("entity/fish/flop2")).volume(0.3),
                  sound(this.mcLoc("entity/fish/flop3")).volume(0.3),
                  sound(this.mcLoc("entity/fish/flop4")).volume(0.3)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminofish.flop")
      );
      this.add(
         ESSoundEvents.LUMINARIS_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/hurt1")),
                  sound(this.mcLoc("entity/fish/hurt2")),
                  sound(this.mcLoc("entity/fish/hurt3")),
                  sound(this.mcLoc("entity/fish/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminaris.hurt")
      );
      this.add(
         ESSoundEvents.LUMINARIS_DEATH.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/hurt1")),
                  sound(this.mcLoc("entity/fish/hurt2")),
                  sound(this.mcLoc("entity/fish/hurt3")),
                  sound(this.mcLoc("entity/fish/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminaris.death")
      );
      this.add(
         ESSoundEvents.LUMINARIS_FLOP.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/flop1")).volume(0.3),
                  sound(this.mcLoc("entity/fish/flop2")).volume(0.3),
                  sound(this.mcLoc("entity/fish/flop3")).volume(0.3),
                  sound(this.mcLoc("entity/fish/flop4")).volume(0.3)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminaris.flop")
      );
      this.add(
         ESSoundEvents.TWILIGHT_GAZE_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/hurt1")),
                  sound(this.mcLoc("entity/fish/hurt2")),
                  sound(this.mcLoc("entity/fish/hurt3")),
                  sound(this.mcLoc("entity/fish/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminaris.hurt")
      );
      this.add(
         ESSoundEvents.TWILIGHT_GAZE_DEATH.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("entity/fish/hurt1")),
                  sound(this.mcLoc("entity/fish/hurt2")),
                  sound(this.mcLoc("entity/fish/hurt3")),
                  sound(this.mcLoc("entity/fish/hurt4"))
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.luminaris.death")
      );
      this.add(
         ESSoundEvents.THE_GATEKEEPER_PARRY.get(),
         definition().with(sound(this.loc("mob/the_gatekeeper/parry"))).subtitle("subtitles.entity.eternal_starlight.the_gatekeeper.parry")
      );
      this.add(
         ESSoundEvents.STARLIGHT_GOLEM_HURT.get(),
         definition().with(sound(this.loc("mob/starlight_golem/hurt"))).subtitle("subtitles.entity.eternal_starlight.starlight_golem.hurt")
      );
      this.add(
         ESSoundEvents.STARLIGHT_GOLEM_DEATH.get(),
         definition().with(sound(this.mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity.eternal_starlight.starlight_golem.death")
      );
      this.add(
         ESSoundEvents.STARLIGHT_GOLEM_BLOCK.get(),
         definition().with(sound(this.loc("mob/starlight_golem/block"))).subtitle("subtitles.entity.eternal_starlight.starlight_golem.block")
      );
      this.add(
         ESSoundEvents.STARLIGHT_GOLEM_PREPARE_CHARGE.get(),
         definition().with(sound(this.mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity.eternal_starlight.starlight_golem.prepare_charge")
      );
      this.add(
         ESSoundEvents.STARLIGHT_GOLEM_PREPARE_BEAM.get(),
         definition().with(sound(this.mcLoc("block/beacon/activate"))).subtitle("subtitles.entity.eternal_starlight.starlight_golem.prepare_beam")
      );
      this.add(
         ESSoundEvents.FREEZE_HURT.get(), definition().with(sound(this.loc("mob/freeze/hurt"))).subtitle("subtitles.entity.eternal_starlight.freeze.hurt")
      );
      this.add(
         ESSoundEvents.FREEZE_DEATH.get(), definition().with(sound(this.loc("mob/freeze/death"))).subtitle("subtitles.entity.eternal_starlight.freeze.death")
      );
      this.add(
         ESSoundEvents.PERMAFROST_HURT.get(),
         definition().with(sound(this.loc("mob/freeze/hurt"))).subtitle("subtitles.entity.eternal_starlight.permafrost.hurt")
      );
      this.add(
         ESSoundEvents.PERMAFROST_DEATH.get(),
         definition().with(sound(this.loc("mob/freeze/death"))).subtitle("subtitles.entity.eternal_starlight.permafrost.death")
      );
      this.add(
         ESSoundEvents.PERMAFROST_SNEEZE.get(),
         definition().with(sound(this.loc("mob/permafrost/sneeze"))).subtitle("subtitles.entity.eternal_starlight.permafrost.sneeze")
      );
      this.add(
         ESSoundEvents.LUNAR_MONSTROSITY_HURT.get(),
         definition()
            .with(
               new Sound[]{
                  sound(this.mcLoc("mob/irongolem/damage1")).pitch(1.5).volume(0.8),
                  sound(this.mcLoc("mob/irongolem/damage1")).pitch(1.2).volume(0.8),
                  sound(this.mcLoc("mob/irongolem/damage2")).pitch(1.5).volume(0.8),
                  sound(this.mcLoc("mob/irongolem/damage2")).pitch(1.2).volume(0.8)
               }
            )
            .subtitle("subtitles.entity.eternal_starlight.lunar_monstrosity.hurt")
      );
      this.add(
         ESSoundEvents.LUNAR_MONSTROSITY_DEATH.get(),
         definition().with(sound(this.loc("mob/lunar_monstrosity/death"))).subtitle("subtitles.entity.eternal_starlight.lunar_monstrosity.death")
      );
      this.add(
         ESSoundEvents.LUNAR_MONSTROSITY_BITE.get(),
         definition().with(sound(this.mcLoc("mob/evocation_illager/fangs"))).subtitle("subtitles.entity.eternal_starlight.lunar_monstrosity.bite")
      );
      this.add(
         ESSoundEvents.LUNAR_MONSTROSITY_ROAR.get(),
         definition().with(sound(this.loc("mob/lunar_monstrosity/roar"))).subtitle("subtitles.entity.eternal_starlight.lunar_monstrosity.roar")
      );
      this.add(
         ESSoundEvents.TANGLED_SKULL_AMBIENT.get(),
         definition().with(sound(this.loc("mob/tangled_skull/idle"))).subtitle("subtitles.entity.eternal_starlight.tangled_skull.ambient")
      );
      this.add(
         ESSoundEvents.TANGLED_SKULL_ROAR.get(),
         definition().with(sound(this.loc("mob/tangled_skull/roar"))).subtitle("subtitles.entity.eternal_starlight.tangled_skull.roar")
      );
      this.add(
         ESSoundEvents.ETHER_TRANSFORM.get(),
         definition().with(sound(this.loc("block/ether/transform"))).subtitle("subtitles.block.eternal_starlight.ether.transform")
      );
      this.add(
         ESSoundEvents.STELLAR_RACK_AMBIENT.get(),
         definition().with(sound(this.loc("block/stellar_rack/idle"))).subtitle("subtitles.block.eternal_starlight.stellar_rack.ambient")
      );
   }
}
