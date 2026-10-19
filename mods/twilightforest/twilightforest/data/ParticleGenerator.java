package twilightforest.data;

import java.util.Iterator;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFParticleType;

public class ParticleGenerator extends ParticleDescriptionProvider {
   public ParticleGenerator(PackOutput output, ExistingFileHelper helper) {
      super(output, helper);
   }

   protected void addDescriptions() {
      this.sprite((ParticleType)TFParticleType.ANNIHILATE.get(), TwilightForestMod.prefix("annihilate_particle"));
      this.spriteSet((ParticleType)TFParticleType.CLOUD_PUFF.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
      this.sprite((ParticleType)TFParticleType.DIM_FLAME.get(), TwilightForestMod.prefix("dim_flame"));
      this.spriteSet((ParticleType)TFParticleType.EXTENDED_SNOW_WARNING.get(), TwilightForestMod.prefix("snow"), 4, false);
      this.sprite((ParticleType)TFParticleType.FALLEN_LEAF.get(), TwilightForestMod.prefix("fallen_leaf"));
      this.sprite((ParticleType)TFParticleType.FIREFLY.get(), TwilightForestMod.prefix("firefly"));
      this.spriteSet((ParticleType)TFParticleType.GHAST_TRAP.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
      this.spriteSet((ParticleType)TFParticleType.HUGE_SMOKE.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
      this.spriteSet((ParticleType)TFParticleType.ICE_BEAM.get(), TwilightForestMod.prefix("snow"), 4, false);
      this.sprite((ParticleType)TFParticleType.LARGE_FLAME.get(), ResourceLocation.withDefaultNamespace("flame"));
      this.spriteSet((ParticleType)TFParticleType.LEAF_RUNE.get(), () -> new Iterator<ResourceLocation>() {
         private int counter = 0;

         @Override
         public boolean hasNext() {
            return this.counter < 26;
         }

         public ResourceLocation next() {
            ResourceLocation texture = ResourceLocation.withDefaultNamespace("sga_" + Character.toString(97 + this.counter));
            this.counter++;
            return texture;
         }
      });
      this.sprite((ParticleType)TFParticleType.LOG_CORE_PARTICLE.get(), TwilightForestMod.prefix("log_core"));
      this.sprite((ParticleType)TFParticleType.OMINOUS_FLAME.get(), TwilightForestMod.prefix("ominous_flame"));
      this.sprite((ParticleType)TFParticleType.PARTICLE_SPAWNER_FIREFLY.get(), TwilightForestMod.prefix("firefly"));
      this.spriteSet((ParticleType)TFParticleType.PERFECT_DODGE.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
      this.spriteSet((ParticleType)TFParticleType.DOUBLE_JUMP.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
      this.sprite((ParticleType)TFParticleType.PROTECTION.get(), ResourceLocation.withDefaultNamespace("glint"));
      this.spriteSet((ParticleType)TFParticleType.SNOW.get(), TwilightForestMod.prefix("snow"), 4, false);
      this.spriteSet((ParticleType)TFParticleType.SNOW_GUARDIAN.get(), TwilightForestMod.prefix("snow"), 4, false);
      this.spriteSet((ParticleType)TFParticleType.SNOW_WARNING.get(), TwilightForestMod.prefix("snow"), 4, false);
      this.sprite((ParticleType)TFParticleType.SORTING_PARTICLE.get(), TwilightForestMod.prefix("log_core"));
      this.sprite((ParticleType)TFParticleType.TRANSFORMATION_PARTICLE.get(), TwilightForestMod.prefix("log_core"));
      this.sprite((ParticleType)TFParticleType.WANDERING_FIREFLY.get(), TwilightForestMod.prefix("firefly"));
      this.spriteSet((ParticleType)TFParticleType.DRYING_RACK.get(), ResourceLocation.withDefaultNamespace("generic"), 8, true);
      this.spriteSet((ParticleType)TFParticleType.MAGIC_EFFECT.get(), ResourceLocation.withDefaultNamespace("effect"), 8, true);
      this.sprite((ParticleType)TFParticleType.ANGRY_LICH.get(), ResourceLocation.withDefaultNamespace("angry"));
      this.sprite((ParticleType)TFParticleType.TWILIGHT_ORB.get(), TwilightForestMod.prefix("twilight_orb"));
      this.sprite((ParticleType)TFParticleType.SHIELD_BREAK.get(), TwilightForestMod.prefix("shield_break"));
   }
}
