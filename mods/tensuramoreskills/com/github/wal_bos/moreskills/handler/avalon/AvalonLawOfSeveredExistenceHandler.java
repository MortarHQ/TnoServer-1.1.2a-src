package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.net.msg.AvalonLawSeveredExistenceVfxPayload;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.LivingPostDamageEvent;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

final class AvalonLawOfSeveredExistenceHandler implements AvalonLawHandler {
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         initialized = true;
         TensuraEntityEvents.LIVING_POST_DAMAGE.register((LivingPostDamageEvent)(target, source, amount) -> {
            if (source.getEntity() instanceof LivingEntity attacker) {
               if (AvalonRoyalLawsHandler.isLawActive(attacker, 7)) {
                  TensuraStorages.getEffectFrom(target).increaseSeveranceAmount(amount * 0.3F);
                  TensuraStorages.getEffectFrom(target).markDirty();
               }
            }
         });
      }
   }

   @Override
   public String name() {
      return "Law of Severed Existence";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawSeveredExistenceVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }
}
