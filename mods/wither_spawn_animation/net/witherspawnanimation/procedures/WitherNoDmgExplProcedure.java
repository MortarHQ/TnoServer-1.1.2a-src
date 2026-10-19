package net.witherspawnanimation.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.witherspawnanimation.WitherSpawnAnimationMod;

@EventBusSubscriber
public class WitherNoDmgExplProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(Post event) {
      if (event.getEntity() != null) {
         execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getOriginalDamage());
      }
   }

   public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, double amount) {
      execute(null, world, damagesource, entity, amount);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, double amount) {
      if (damagesource != null && entity != null) {
         if (entity instanceof WitherBoss && damagesource.is(DamageTypes.EXPLOSION)) {
            WitherSpawnAnimationMod.queueServerWork(1, () -> {
               if (entity instanceof LivingEntity _entity) {
                  _entity.setHealth((float)((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F) + amount));
               }
            });
         }
      }
   }
}
