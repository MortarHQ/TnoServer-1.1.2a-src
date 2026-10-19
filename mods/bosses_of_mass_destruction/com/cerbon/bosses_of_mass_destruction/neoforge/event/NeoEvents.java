package com.cerbon.bosses_of_mass_destruction.neoforge.event;

import com.cerbon.bosses_of_mass_destruction.block.custom.LevitationBlockEntity;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.neoforge.attachment.BMDAttachments;
import com.cerbon.bosses_of_mass_destruction.neoforge.attachment.saved_data.LevelChunkBlockCache;
import com.cerbon.cerbons_api.api.general.data.HistoricalData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "bosses_of_mass_destruction")
public class NeoEvents {
   @SubscribeEvent
   public static void onPlayerLogging(LoggingIn event) {
      LevelChunkBlockCache.INSTANCE = new LevelChunkBlockCache();
   }

   @SubscribeEvent
   protected static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide) {
         HistoricalData<Vec3> data = (HistoricalData<Vec3>)event.getEntity().getData(BMDAttachments.HISTORICAL_DATA);
         Vec3 previousPosition = (Vec3)data.get(0);
         Vec3 newPosition = event.getEntity().position();
         if (previousPosition.distanceToSqr(newPosition) > 5.0) {
            data.clear();
         }

         data.add(newPosition);
         LevitationBlockEntity.tickFlight((ServerPlayer)event.getEntity());
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onLivingDeath(LivingDeathEvent event) {
      if (BMDEntities.mobConfig.lichConfig.summonMechanic.isEnabled) {
         DamageSource damageSource = event.getSource();
         if (damageSource == null) {
            return;
         }

         Entity attacker = damageSource.getEntity();
         if (attacker != null) {
            BMDEntities.killCounter.afterKilledOtherEntity(attacker, event.getEntity());
         }
      }
   }
}
