package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class ESParticleDescriptionProvider extends ParticleDescriptionProvider {
   public ESParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
      super(output, fileHelper);
   }

   protected void addDescriptions() {
      this.spriteSet((ParticleType)ESParticles.STARLIGHT.get(), this.loc("glitter"), 6, false);
      this.spriteSet((ParticleType)ESParticles.STARDUST.get(), this.loc("stardust"), 2, false);
      this.spriteSet((ParticleType)ESParticles.FIREFLY.get(), this.loc("firefly"), 5, false);
      this.spriteSet((ParticleType)ESParticles.SCARLET_LEAVES.get(), this.loc("scarlet_leaves"), 5, false);
      this.spriteSet((ParticleType)ESParticles.CRADLEWOOD_LEAVES.get(), this.loc("cradlewood_leaves"), 8, false);
      this.spriteSet((ParticleType)ESParticles.SHADEGRIEVE_LEAVES.get(), this.loc("shadegrieve_leaves"), 4, false);
      this.spriteSet((ParticleType)ESParticles.SPIRAL_KELP_LEAVES.get(), this.loc("spiral_kelp_leaves"), 2, false);
      this.sprite((ParticleType)ESParticles.FALLING_RED_CRYSTAL_MOSS.get(), this.loc("falling_red_crystal_moss"));
      this.sprite((ParticleType)ESParticles.FALLING_BLUE_CRYSTAL_MOSS.get(), this.loc("falling_blue_crystal_moss"));
      this.sprite((ParticleType)ESParticles.ENERGY.get(), this.loc("energy"));
      this.sprite((ParticleType)ESParticles.ELECTRIC_SPARK.get(), this.loc("electric_spark"));
      this.sprite((ParticleType)ESParticles.LUNAR_SLASH.get(), this.loc("electric_spark"));
      this.sprite((ParticleType)ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), this.loc("crystallized_moth_sonar"));
      this.sprite((ParticleType)ESParticles.AMARAMBER_FLAME.get(), this.loc("amaramber_flame"));
      this.spriteSet(ESParticles.EXPLOSION.get(), this.mcLoc("explosion"), 16, false);
      this.spriteSet(ESParticles.BLAST.get(), this.loc("blast"), 4, false);
      this.spriteSet(ESParticles.SMOKE.get(), this.loc("big_smoke"), 12, false);
      this.sprite(ESParticles.RING_EXPLOSION.get(), this.loc("ring"));
      this.sprite(ESParticles.RING.get(), this.loc("ring"));
      this.sprite(ESParticles.ORBITAL_TRAIL.get(), this.loc("trail"));
      this.sprite((ParticleType)ESParticles.METEOR.get(), this.loc("trail"));
      this.sprite((ParticleType)ESParticles.PARRY.get(), this.loc("parry_trail"));
      this.sprite(ESParticles.GATHERING_ENERGY.get(), this.loc("energy_trail"));
      this.sprite(ESParticles.GATHERING_SOUL.get(), this.loc("soul_trail"));
      this.sprite(ESParticles.GATHERING_FLARE.get(), this.loc("flare_trail"));
      this.sprite(ESParticles.GLOW.get(), this.loc("glow"));
      this.sprite((ParticleType)ESParticles.AETHERSENT_SMOKE.get(), this.loc("big_smoke_3"));
      this.spriteSet((ParticleType)ESParticles.ASHEN_SNOW.get(), this.loc("ashen_snow"), 4, false);
      this.spriteSet((ParticleType)ESParticles.ORBITAL_ASHEN_SNOW.get(), this.loc("ashen_snow"), 4, false);
      this.sprite(ESParticles.EXPLOSION_SHOCK.get(), this.loc("blank"));
      this.spriteSet(ESParticles.COLORED_INK.get(), this.mcLoc("generic"), 8, true);
      this.sprite((ParticleType)ESParticles.AMARAMBER_WAX_ON.get(), this.loc("amaramber_wax_on"));
      this.sprite((ParticleType)ESParticles.DRIPPING_MUD.get(), this.mcLoc("drip_hang"));
      this.sprite((ParticleType)ESParticles.FALLING_MUD.get(), this.mcLoc("drip_fall"));
      this.sprite((ParticleType)ESParticles.LANDING_MUD.get(), this.mcLoc("drip_land"));
      this.sprite((ParticleType)ESParticles.ALLIED.get(), this.loc("allied"));
      this.spriteSet((ParticleType)ESParticles.PUNGENCY_FRUIT_SMOKE.get(), this.mcLoc("generic"), 8, true);
      this.sprite((ParticleType)ESParticles.STARFIRE.get(), this.loc("starfire"));
      this.spriteSet((ParticleType)ESParticles.STARFIRE_EXPLOSION.get(), this.loc("starfire_explosion"), 5, false);
      this.spriteSet((ParticleType)ESParticles.STARFIRE_EXPLOSION_SMALL.get(), this.loc("starfire_explosion_small"), 6, false);
      this.spriteSet((ParticleType)ESParticles.SOUL_TRAIL.get(), this.loc("soul_trail"), 5, false);
      this.sprite((ParticleType)ESParticles.CANDLASH_TRAIL.get(), this.loc("candlash_trail"));
      this.sprite((ParticleType)ESParticles.ETHER_TRAIL.get(), this.loc("ether_trail"));
      this.spriteSet(ESParticles.GEYSER_BASE.get(), this.loc("geyser_base"), 8, false);
      this.spriteSet(ESParticles.GEYSER_POOF.get(), this.loc("geyser_poof"), 8, false);
      this.spriteSet(ESParticles.GEYSER_PLUME.get(), this.loc("geyser_plume"), 8, false);
      this.sprite((ParticleType)ESParticles.ADVANCED_GLOW.get(), this.loc("glow"));
      this.sprite((ParticleType)ESParticles.SHINE.get(), this.loc("shine"));
   }

   private ResourceLocation loc(String s) {
      return EternalStarlight.id(s);
   }

   private ResourceLocation mcLoc(String s) {
      return ResourceLocation.withDefaultNamespace(s);
   }
}
