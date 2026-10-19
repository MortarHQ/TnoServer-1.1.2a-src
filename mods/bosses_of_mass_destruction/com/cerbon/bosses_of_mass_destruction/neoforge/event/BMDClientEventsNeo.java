package com.cerbon.bosses_of_mass_destruction.neoforge.event;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.config.BMDConfig;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.particle.SimpleParticle;
import com.cerbon.cerbons_api.api.general.particle.SimpleParticleProvider;
import com.cerbon.cerbons_api.api.static_utilities.Geometries;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = "bosses_of_mass_destruction", bus = Bus.MOD, value = Dist.CLIENT)
public class BMDClientEventsNeo {
   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(
         () -> {
            BMDItems.initClient();
            BMDEntities.initClient();
            BMDBlockEntities.initClient();
            ModLoadingContext.get()
               .registerExtensionPoint(
                  IConfigScreenFactory.class, () -> (modContainer, screen) -> (Screen)AutoConfig.getConfigScreen(BMDConfig.class, screen).get()
               );
         }
      );
   }

   @SubscribeEvent
   public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet(
         (ParticleType)BMDParticles.DISAPPEARING_SWIRL.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet((ParticleType)BMDParticles.SOUL_FLAME.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true);
         particle.setColorOverride(f -> Vec3Colors.COMET_BLUE);
         particle.setBrightnessOverride(f -> 15728880);
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.MAGIC_CIRCLE.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, 40, Geometries::buildBillBoardGeometry, true, true);
         particle.setBrightnessOverride(f -> 15728880);
         particle.scale(4.0F);
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.OBSIDILITH_BURST.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(7, 15), Geometries::buildBillBoardGeometry, true, true);
         particle.setBrightnessOverride(f -> 15728880);
         particle.scale(4.0F);
         particle.setColorOverride(age -> MathUtils.lerpVec(age, Vec3Colors.ORANGE, Vec3Colors.RUNIC_BROWN));
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.ENCHANT.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(30, 50), Geometries::buildBillBoardGeometry, false, true);
         particle.setBrightnessOverride(f -> 15728880);
         particle.setScaleOverride(f -> (float)((Math.sin(f.floatValue() * Math.PI) + 1.0) * 0.1F));
         return particle;
      }));
      event.registerSpriteSet(
         (ParticleType)BMDParticles.OBSIDILITH_BURST_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, 30 + RandomUtils.range(-1, 2), Geometries::buildFlatGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.ORANGE);
            particle.setColorVariation(0.3);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
            return particle;
         })
      );
      event.registerSpriteSet((ParticleType)BMDParticles.OBSIDILITH_WAVE.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(7, 15), Geometries::buildBillBoardGeometry, true, true);
         particle.setBrightnessOverride(f -> 15728880);
         particle.scale(4.0F);
         particle.setColorVariation(0.25);
         particle.setColorOverride(age -> MathUtils.lerpVec(age, Vec3Colors.RED, Vec3Colors.DARK_RED));
         return particle;
      }));
      event.registerSpriteSet(
         (ParticleType)BMDParticles.OBSIDILITH_WAVE_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, 20 + RandomUtils.range(-1, 2), Geometries::buildFlatGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.RED);
            particle.setColorVariation(0.3);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
            return particle;
         })
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.DOWNSPARKLE.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.OBSIDILITH_SPIKE_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, 20 + RandomUtils.range(-1, 2), Geometries::buildFlatGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.COMET_BLUE);
            particle.setColorVariation(0.3);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
            return particle;
         })
      );
      event.registerSpriteSet((ParticleType)BMDParticles.OBSIDILITH_SPIKE.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true);
         particle.setColorOverride(age -> MathUtils.lerpVec(age, Vec3Colors.WHITE, Vec3Colors.COMET_BLUE));
         particle.setBrightnessOverride(f -> 15728880);
         particle.setColorVariation(0.25);
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.PILLAR_RUNE.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, 10, Geometries::buildBillBoardGeometry, false, true);
         particle.setBrightnessOverride(f -> 15728880);
         particle.setScaleOverride(f -> (float)((Math.sin(f.floatValue() * Math.PI) + 1.0) * 0.1F));
         particle.setColorOverride(age -> MathUtils.lerpVec(age, Vec3Colors.WHITE, Vec3Colors.ENDER_PURPLE));
         particle.setColorVariation(0.2);
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.PILLAR_SPAWN_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, 40, Geometries::buildBillBoardGeometry, true, true);
         particle.setColorOverride(f -> Vec3Colors.ENDER_PURPLE);
         particle.scale(2.0F);
         particle.setColorVariation(0.25);
         particle.setBrightnessOverride(f -> 15728880);
         return particle;
      }));
      event.registerSpriteSet(
         (ParticleType)BMDParticles.PILLAR_SPAWN_INDICATOR_2.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, 40, Geometries::buildBillBoardGeometry, false, true);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (float)((Math.sin(f.floatValue() * Math.PI) + 1.0) * 0.1F));
            particle.setColorOverride(age -> MathUtils.lerpVec(age, Vec3Colors.WHITE, Vec3Colors.ENDER_PURPLE));
            particle.setColorVariation(0.2);
            return particle;
         })
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.OBSIDILITH_ANVIL_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(25, 27), Geometries::buildFlatGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.ENDER_PURPLE);
            particle.setColorVariation(0.3);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
            return particle;
         })
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.SPARKLES.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.GAUNTLET_REVIVE_SPARKLES.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.LASER_RED);
            particle.setColorVariation(0.25);
            particle.setBrightnessOverride(f -> 15728880);
            return particle;
         })
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.EYE_OPEN.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(60, 70), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.LINE.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(20, 30), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.ROD.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(8, 10), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.GROUND_ROD.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(8, 10), Geometries::buildFlatGeometry, true, true)
         )
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.VOID_BLOSSOM_SPIKE_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, 20 + RandomUtils.range(-1, 2), Geometries::buildFlatGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.ENDER_PURPLE);
            particle.setColorVariation(0.2);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
            return particle;
         })
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.VOID_BLOSSOM_SPIKE_WAVE_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
            SimpleParticle particle = new SimpleParticle(particleContext, 30 + RandomUtils.range(-1, 2), Geometries::buildFlatGeometry, true, true);
            particle.setColorOverride(f -> Vec3Colors.ENDER_PURPLE);
            particle.setColorVariation(0.2);
            particle.setBrightnessOverride(f -> 15728880);
            particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
            return particle;
         })
      );
      event.registerSpriteSet(
         (ParticleType)BMDParticles.PETAL.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, false, true)
         )
      );
      event.registerSpriteSet((ParticleType)BMDParticles.POLLEN.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, false, true);
         particle.setColorOverride(f -> new Vec3(1.0, 0.9, 0.4));
         particle.setColorVariation(0.15);
         particle.setBrightnessOverride(f -> 15728880);
         particle.setScaleOverride(f -> 0.05F * (1.0F - f * 0.25F));
         int randomRot = RandomUtils.range(0, 360);
         float angularMomentum = RandomUtils.randSign() * 4.0F;
         particle.setRotationOverride(particle1 -> randomRot + particle1.getAge() * angularMomentum);
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.SPORE.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(7, 15), Geometries::buildBillBoardGeometry, true, true);
         particle.setBrightnessOverride(f -> 15728880);
         particle.scale(4.0F);
         particle.setColorOverride(age -> MathUtils.lerpVec(age, Vec3Colors.GREEN, Vec3Colors.DARK_GREEN));
         particle.setColorVariation(0.25);
         return particle;
      }));
      event.registerSpriteSet((ParticleType)BMDParticles.SPORE_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, 30 + RandomUtils.range(-1, 2), Geometries::buildFlatGeometry, true, true);
         particle.setColorOverride(f -> Vec3Colors.GREEN);
         particle.setColorVariation(0.35);
         particle.setBrightnessOverride(f -> 15728880);
         particle.setScaleOverride(f -> (1.0F + f) * 0.25F);
         return particle;
      }));
      event.registerSpriteSet(
         (ParticleType)BMDParticles.FLUFF.get(),
         spriteSet -> new SimpleParticleProvider(
            spriteSet, particleContext -> new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, true)
         )
      );
      event.registerSpriteSet((ParticleType)BMDParticles.EARTHDIVE_INDICATOR.get(), spriteSet -> new SimpleParticleProvider(spriteSet, particleContext -> {
         SimpleParticle particle = new SimpleParticle(particleContext, RandomUtils.range(15, 20), Geometries::buildBillBoardGeometry, true, false);
         particle.setColorOverride(f -> Vec3Colors.RUNIC_BROWN);
         particle.setColorVariation(0.25);
         particle.setBrightnessOverride(f -> 15728880);
         particle.setScaleOverride(f -> (1.0F - f * 0.25F) * 0.35F);
         return particle;
      }));
   }
}
