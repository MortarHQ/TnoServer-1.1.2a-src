package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import com.tensura_tno.network.ContractLittleFoxPackets;
import com.tensura_tno.registry.TensuraTNOSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;

@EventBusSubscriber(modid = "tensura_tno")
public class SummonDamageRedirectEventHandler {
   private static final int MIN_EVOLUTION_LEVEL = 2;
   private static final ThreadLocal<Boolean> REDIRECTING = ThreadLocal.withInitial(() -> Boolean.FALSE);

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onPlayerHurt(Pre event) {
      if (!event.getEntity().level().isClientSide()) {
         if (event.getEntity() instanceof ServerPlayer player) {
            if (!Boolean.TRUE.equals(REDIRECTING.get())) {
               if (FoxSpiritSummonBonus.getEvolutionLevel(player) >= 2) {
                  float damage = event.getContainer().getNewDamage();
                  if (!(damage <= 0.0F)) {
                     if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                        List<Mob> tempSummons = findActiveTempSummons(player);
                        if (!tempSummons.isEmpty()) {
                           Mob target = tempSummons.get(player.getRandom().nextInt(tempSummons.size()));
                           event.getContainer().setNewDamage(0.0F);
                           redirectTo(target, event.getSource(), damage);
                        } else {
                           FoxSpiritEntity fox = ContractLittleFoxPackets.findActiveFox(player);
                           if (fox != null) {
                              event.getContainer().setNewDamage(0.0F);
                              redirectTo(fox, event.getSource(), damage);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void redirectTo(LivingEntity target, DamageSource source, float damage) {
      REDIRECTING.set(Boolean.TRUE);

      try {
         target.hurt(source, damage);
      } finally {
         REDIRECTING.set(Boolean.FALSE);
      }
   }

   private static List<Mob> findActiveTempSummons(ServerPlayer player) {
      UUID playerUUID = player.getUUID();
      List<Mob> result = new ArrayList<>();

      for (Entity entity : ((ServerLevel)player.level()).getAllEntities()) {
         if (entity instanceof Mob mob && mob.isAlive()) {
            try {
               IExistence ex = TensuraStorages.getExistenceFrom(mob);
               if (playerUUID.equals(ex.getSummoner())
                  && ex.getSummonedAbility() != null
                  && ex.getSummonedAbility().getSkill() == TensuraTNOSkills.SPIRIT_SUMMON.get()) {
                  result.add(mob);
               }
            } catch (Exception var7) {
            }
         }
      }

      return result;
   }
}
