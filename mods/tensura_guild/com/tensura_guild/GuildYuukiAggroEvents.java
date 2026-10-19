package com.tensura_guild;

import com.tensura_guild.entity.LaplaceEntity;
import com.tensura_guild.entity.YuukiKagurazakaEntity;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.util.BrainUtils;

public final class GuildYuukiAggroEvents {
   private static final double GUILD_BOSS_AGGRO_RANGE = 32.0;
   private static final double YUUKI_ASSIST_RANGE = 48.0;
   private static final String TENSURA_NAMESPACE = "tensura";
   private static final String TENSURA_MONSTER_PACKAGE = ".entity.monster.";
   private static final String TENSURA_MULTIPART_PACKAGE = ".entity.multipart.";

   private GuildYuukiAggroEvents() {
   }

   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof LivingEntity guildBoss
         && !guildBoss.level().isClientSide()
         && isGuildBoss(guildBoss)
         && guildBoss.isAlive()
         && guildBoss.tickCount % 20 == 0) {
         AABB area = guildBoss.getBoundingBox().inflate(32.0);

         for (Mob mob : guildBoss.level().getEntitiesOfClass(Mob.class, area, mobx -> shouldBecomeHostileToGuildBoss(mobx, guildBoss))) {
            mob.setTarget(guildBoss);
            if (mob instanceof SmartBrainOwner) {
               BrainUtils.setMemory(mob, MemoryModuleType.ATTACK_TARGET, guildBoss);
               BrainUtils.clearMemory(mob, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            }
         }
      }
   }

   public static void requestLaplaceAssist(YuukiKagurazakaEntity yuuki, LivingEntity attacker) {
      if (yuuki != null && attacker != null && !yuuki.level().isClientSide() && yuuki.isAlive() && attacker.isAlive() && !yuuki.isAlliedTo(attacker)) {
         AABB area = yuuki.getBoundingBox().inflate(48.0);

         for (LaplaceEntity laplace : yuuki.level()
            .getEntitiesOfClass(LaplaceEntity.class, area, laplacex -> shouldLaplaceAssistYuuki(laplacex, yuuki, attacker))) {
            laplace.setSpawnerDormant(false);
            laplace.setLastHurtByMob(attacker);
            laplace.setTarget(attacker);
            laplace.setAggressive(true);
            BrainUtils.setMemory(laplace, MemoryModuleType.ATTACK_TARGET, attacker);
            BrainUtils.clearMemory(laplace, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         }
      }
   }

   private static boolean shouldLaplaceAssistYuuki(LaplaceEntity laplace, YuukiKagurazakaEntity yuuki, LivingEntity attacker) {
      if (laplace != null && !laplace.is(yuuki) && laplace.isAlive() && !laplace.isAlliedTo(attacker) && laplace.canAttack(attacker)) {
         LivingEntity target = laplace.getTarget();
         return target == null || !target.isAlive() || target.is(attacker);
      } else {
         return false;
      }
   }

   private static boolean shouldBecomeHostileToGuildBoss(Mob mob, LivingEntity guildBoss) {
      if (mob != null && !mob.is(guildBoss) && !isGuildBoss(mob) && mob.isAlive() && shouldAutoTargetGuildBoss(mob, guildBoss) && !isTamedSubordinate(mob)) {
         LivingEntity target = mob.getTarget();
         return target != null && target.isAlive() && !target.is(guildBoss) ? false : mob.canAttack(guildBoss) && !mob.isAlliedTo(guildBoss);
      } else {
         return false;
      }
   }

   private static boolean isGuildBoss(LivingEntity entity) {
      return entity instanceof YuukiKagurazakaEntity || entity instanceof LaplaceEntity;
   }

   private static boolean shouldAutoTargetGuildBoss(Mob mob, LivingEntity guildBoss) {
      return guildBoss instanceof YuukiKagurazakaEntity ? isUndeadLike(mob) : isMonsterOrUndeadLike(mob);
   }

   private static boolean isMonsterOrUndeadLike(Mob mob) {
      if (mob.getType().getCategory() != MobCategory.MONSTER && !isUndeadLike(mob)) {
         ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType());
         String className = mob.getClass().getName();
         return "tensura".equals(id.getNamespace()) && (className.contains(".entity.monster.") || className.contains(".entity.multipart."));
      } else {
         return true;
      }
   }

   private static boolean isUndeadLike(Mob mob) {
      return mob.getType().is(EntityTypeTags.UNDEAD) || mob.getClass().getName().contains(".undead.");
   }

   private static boolean isTamedSubordinate(Mob mob) {
      return mob instanceof ISubordinate subordinate && subordinate.isTame();
   }
}
