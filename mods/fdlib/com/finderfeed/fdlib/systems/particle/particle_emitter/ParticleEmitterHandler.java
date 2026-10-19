package com.finderfeed.fdlib.systems.particle.particle_emitter;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;

@EventBusSubscriber(bus = Bus.GAME, value = Dist.CLIENT, modid = "fdlib")
public class ParticleEmitterHandler {
   private static List<ParticleEmitter> activeEmitters = new LinkedList<>();

   @SubscribeEvent
   public static void tickEmitters(Pre event) {
      if (event.getLevel().isClientSide) {
         ListIterator<ParticleEmitter> iterator = activeEmitters.listIterator();

         while (iterator.hasNext()) {
            ParticleEmitter emitter = iterator.next();
            if (emitter.removed) {
               iterator.remove();
            } else {
               emitter.tick();
            }
         }
      }
   }

   public static void addParticleEmitter(ParticleEmitterData data) {
      ParticleEmitter emitter = new ParticleEmitter(data);
      activeEmitters.add(emitter);
      emitter.init();
   }
}
