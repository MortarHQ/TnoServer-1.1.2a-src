package net.jadenxgamer.elysium_api.impl.core.misc.neoforge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public record EffectsBiomeModifier(
   HolderSet<Biome> biomes,
   Optional<Integer> skyColor,
   Optional<Integer> fogColor,
   Optional<Integer> waterColor,
   Optional<Integer> waterFogColor,
   Optional<Integer> grassColor,
   Optional<Integer> foliageColor,
   Optional<GrassColorModifier> grassColorModifier,
   Optional<AmbientParticleSettings> particle,
   Optional<Holder<SoundEvent>> ambientSound,
   Optional<AmbientAdditionsSettings> additionsSound,
   Optional<AmbientMoodSettings> moodSound,
   Optional<Music> music
) implements BiomeModifier {
   public static final MapCodec<EffectsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(EffectsBiomeModifier::biomes),
            Codec.INT.optionalFieldOf("sky_color").forGetter(EffectsBiomeModifier::skyColor),
            Codec.INT.optionalFieldOf("fog_color").forGetter(EffectsBiomeModifier::fogColor),
            Codec.INT.optionalFieldOf("water_color").forGetter(EffectsBiomeModifier::waterColor),
            Codec.INT.optionalFieldOf("water_fog_color").forGetter(EffectsBiomeModifier::waterFogColor),
            Codec.INT.optionalFieldOf("grass_color").forGetter(EffectsBiomeModifier::grassColor),
            Codec.INT.optionalFieldOf("foliage_color").forGetter(EffectsBiomeModifier::foliageColor),
            GrassColorModifier.CODEC.optionalFieldOf("grass_color_modifier").forGetter(EffectsBiomeModifier::grassColorModifier),
            AmbientParticleSettings.CODEC.optionalFieldOf("particle").forGetter(EffectsBiomeModifier::particle),
            SoundEvent.CODEC.optionalFieldOf("ambient_sound").forGetter(EffectsBiomeModifier::ambientSound),
            AmbientAdditionsSettings.CODEC.optionalFieldOf("additions_sound").forGetter(EffectsBiomeModifier::additionsSound),
            AmbientMoodSettings.CODEC.optionalFieldOf("mood_sound").forGetter(EffectsBiomeModifier::moodSound),
            Music.CODEC.optionalFieldOf("music").forGetter(EffectsBiomeModifier::music)
         )
         .apply(instance, EffectsBiomeModifier::new)
   );

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.MODIFY && this.biomes.contains(biome)) {
         BiomeSpecialEffectsBuilder effectBuilder = builder.getSpecialEffects();
         this.skyColor.ifPresent(effectBuilder::skyColor);
         this.fogColor.ifPresent(effectBuilder::fogColor);
         this.waterColor.ifPresent(effectBuilder::waterColor);
         this.waterFogColor.ifPresent(effectBuilder::waterFogColor);
         this.grassColor.ifPresent(effectBuilder::grassColorOverride);
         this.foliageColor.ifPresent(effectBuilder::foliageColorOverride);
         this.grassColorModifier.ifPresent(effectBuilder::grassColorModifier);
         this.particle.ifPresent(effectBuilder::ambientParticle);
         this.ambientSound.ifPresent(effectBuilder::ambientLoopSound);
         this.additionsSound.ifPresent(effectBuilder::ambientAdditionsSound);
         this.moodSound.ifPresent(effectBuilder::ambientMoodSound);
         this.music.ifPresent(effectBuilder::backgroundMusic);
      }
   }

   public MapCodec<? extends BiomeModifier> codec() {
      return CODEC;
   }
}
