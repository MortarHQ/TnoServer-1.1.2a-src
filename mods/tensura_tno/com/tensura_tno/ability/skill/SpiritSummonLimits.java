package com.tensura_tno.ability.skill;

import com.tensura_tno.world.TensuraTNOGameRules;
import java.util.ArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

public final class SpiritSummonLimits {
   private static final String VANILLA_NAMESPACE = "minecraft";

   private SpiritSummonLimits() {
   }

   public static boolean isVanillaOnlyEnforced(Level level) {
      return level.getGameRules().getBoolean(TensuraTNOGameRules.SPIRIT_SUMMON_VANILLA_ONLY);
   }

   public static int getMaxHealthLimit(Level level) {
      return level.getGameRules().getInt(TensuraTNOGameRules.SPIRIT_SUMMON_MAX_HEALTH);
   }

   public static boolean isVanilla(ResourceLocation entityId) {
      return entityId != null && "minecraft".equals(entityId.getNamespace());
   }

   public static boolean isMaxHealthAllowed(Level level, LivingEntity entity) {
      AttributeInstance attr = entity.getAttribute(Attributes.MAX_HEALTH);
      return attr == null ? false : attr.getBaseValue() <= getMaxHealthLimit(level);
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         Level level = player.level();
         GameRules rules = level.getGameRules();
         boolean enforceVanilla = rules.getBoolean(TensuraTNOGameRules.SPIRIT_SUMMON_VANILLA_ONLY);
         int maxHpLimit = rules.getInt(TensuraTNOGameRules.SPIRIT_SUMMON_MAX_HEALTH);

         for (String entityId : new ArrayList<>(SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntities(player))) {
            if (!isEntryAllowed(level, entityId, enforceVanilla, maxHpLimit)) {
               SpiritSummonSkill.SpiritSummonPockets.removeAbsorbedEntity(player, entityId);
            }
         }
      }
   }

   private static boolean isEntryAllowed(Level level, String entityId, boolean enforceVanilla, int maxHpLimit) {
      ResourceLocation rl = ResourceLocation.tryParse(entityId);
      if (rl == null) {
         return false;
      }

      if (enforceVanilla && !isVanilla(rl)) {
         return false;
      }

      if (!BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
         return false;
      }

      EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(rl);
      Entity probe = type.create(level);

      try {
         if (probe instanceof LivingEntity living) {
            AttributeInstance attr = living.getAttribute(Attributes.MAX_HEALTH);
            return attr == null ? false : attr.getBaseValue() <= maxHpLimit;
         } else {
            return false;
         }
      } finally {
         if (probe != null) {
            probe.discard();
         }
      }
   }
}
