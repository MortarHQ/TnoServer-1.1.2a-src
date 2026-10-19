package com.tensura_tno.mixin.client;

import com.tensura_tno.ability.skill.SpiritSummonSkill;
import com.tensura_tno.race.fox_spirit.FoxSpiritSummonBonus;
import io.github.manasmods.tensura.data.TensuraTags.DamageTypes;
import io.github.manasmods.tensura.storage.ep.ExistenceStorage;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ExistenceStorage.class, remap = false)
public class FoxSpiritDeathPenaltyMixin {
   @Inject(method = "applyDeathPenalty", at = @At("HEAD"), cancellable = true, remap = false)
   private static void tensuraTno$splitDeathPenalty(LivingEntity entity, IExistence storage, DamageSource source, CallbackInfo ci) {
      if (entity instanceof ServerPlayer player) {
         if (FoxSpiritSummonBonus.isFoxSpiritRace(player)) {
            if (source.is(DamageTypes.STOP_DEATH_PENALTY)) {
               ci.cancel();
            } else if (source.isCreativePlayer()) {
               ci.cancel();
            } else {
               float penaltyRate = entity.level().getGameRules().getInt(TensuraGameRules.EP_DEATH_PENALTY) / 100.0F;
               if (penaltyRate <= 0.0F) {
                  ci.cancel();
               } else {
                  double baseMaxEP = EnergyHelper.getBaseMaxEP(entity);
                  double totalLoss = baseMaxEP * penaltyRate;
                  double halfLoss = totalLoss / 2.0;
                  float halfPenaltyRate = penaltyRate / 2.0F;
                  EnergyHelper.multiplyMaxEP(entity, 1.0F - halfPenaltyRate);
                  if (storage.getSoulPoints() > 0) {
                     storage.setSoulPoints((int)(storage.getSoulPoints() * (1.0F - halfPenaltyRate)));
                  }

                  List<CompoundTag> entries = SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntries(player);
                  if (!entries.isEmpty()) {
                     double totalBonusEP = 0.0;

                     for (CompoundTag ct : entries) {
                        totalBonusEP += ct.getDouble("bonus_ep");
                     }

                     if (totalBonusEP > 0.0) {
                        double actualPocketLoss = Math.min(halfLoss, totalBonusEP);
                        double ratio = actualPocketLoss / totalBonusEP;

                        for (CompoundTag ct : entries) {
                           double currentBonus = ct.getDouble("bonus_ep");
                           if (currentBonus > 0.0) {
                              double loss = currentBonus * ratio;
                              SpiritSummonSkill.SpiritSummonPockets.addBonusEP(player, ct.getString("id"), -loss);
                           }
                        }
                     }
                  }

                  ci.cancel();
               }
            }
         }
      }
   }
}
