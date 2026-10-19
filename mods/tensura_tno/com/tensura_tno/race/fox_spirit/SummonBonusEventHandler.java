package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = "tensura_tno")
public class SummonBonusEventHandler {
   private static final ResourceLocation HP_BONUS_ID = ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_spirit_summon_hp_bonus");
   private static final ResourceLocation ATK_BONUS_ID = ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_spirit_summon_atk_bonus");

   @SubscribeEvent
   public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide()) {
         if (event.getEntity() instanceof LivingEntity summon) {
            if (!(summon instanceof Player)) {
               if (!(summon instanceof FoxSpiritEntity)) {
                  Player owner = findOwner(summon);
                  if (owner != null) {
                     float bonus = FoxSpiritSummonBonus.getBonusPercentage(owner);
                     if (!(bonus <= 0.0F)) {
                        applyHealthBonus(summon, bonus);
                        applyAttackBonus(summon, bonus);
                     }
                  }
               }
            }
         }
      }
   }

   private static Player findOwner(LivingEntity entity) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         UUID summonerUUID = existence.getSummoner();
         if (summonerUUID != null && entity.level() instanceof ServerLevel sl && sl.getEntity(summonerUUID) instanceof Player player) {
            return player;
         }
      } catch (Exception var6) {
      }

      if (entity instanceof OwnableEntity ownable) {
         UUID ownerUUID = ownable.getOwnerUUID();
         if (ownerUUID != null && entity.level() instanceof ServerLevel sl && sl.getEntity(ownerUUID) instanceof Player player) {
            return player;
         }
      }

      return null;
   }

   private static void applyHealthBonus(LivingEntity summon, float bonus) {
      AttributeInstance hpAttr = summon.getAttribute(Attributes.MAX_HEALTH);
      if (hpAttr != null) {
         hpAttr.removeModifier(HP_BONUS_ID);
         AttributeModifier modifier = new AttributeModifier(HP_BONUS_ID, bonus, Operation.ADD_MULTIPLIED_BASE);
         hpAttr.addPermanentModifier(modifier);
         if (summon.getHealth() >= summon.getMaxHealth() - 0.5F) {
            summon.setHealth(summon.getMaxHealth());
         }
      }
   }

   private static void applyAttackBonus(LivingEntity summon, float bonus) {
      AttributeInstance atkAttr = summon.getAttribute(Attributes.ATTACK_DAMAGE);
      if (atkAttr != null) {
         atkAttr.removeModifier(ATK_BONUS_ID);
         AttributeModifier modifier = new AttributeModifier(ATK_BONUS_ID, bonus, Operation.ADD_MULTIPLIED_BASE);
         atkAttr.addPermanentModifier(modifier);
      }
   }
}
