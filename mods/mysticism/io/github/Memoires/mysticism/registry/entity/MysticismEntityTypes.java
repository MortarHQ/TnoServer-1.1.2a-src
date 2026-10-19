package io.github.Memoires.mysticism.registry.entity;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import io.github.Memoires.mysticism.config.entity.MysticismSpawnRateConfig;
import io.github.Memoires.mysticism.entity.boss.memoires.MemoiresEntity;
import io.github.Memoires.mysticism.entity.golems.ClayGolemEntity;
import io.github.Memoires.mysticism.entity.monster.OkamiEntity;
import io.github.Memoires.mysticism.entity.monster.ShadowImpEntity;
import io.github.Memoires.mysticism.entity.skill.DispenserEntity;
import io.github.Memoires.mysticism.entity.skill.RelapseCloneEntity;
import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.entity.SpawnRateConfig;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class MysticismEntityTypes {
   public static final SpawnRateConfig CONFIG = (SpawnRateConfig)ConfigRegistry.getConfig(SpawnRateConfig.class);
   public static final MysticismSpawnRateConfig MYSTICISM_CONFIG = (MysticismSpawnRateConfig)ConfigRegistry.getConfig(MysticismSpawnRateConfig.class);

   public static void init() {
      MysticismMonsterEntityTypes.init();
      MysticismSkillEntityTypes.init();
      MysticismProjectileEntityTypes.init();
      createAttributes();
      spawnPlacements();
   }

   public static void createAttributes() {
      EntityAttributeRegistry.register(MysticismMonsterEntityTypes.OKAMI, OkamiEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismMonsterEntityTypes.CLAY_GOLEM, ClayGolemEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismMonsterEntityTypes.MEMOIRES, MemoiresEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismSkillEntityTypes.SENTRY, SentryEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismSkillEntityTypes.DISPENSER, DispenserEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismSkillEntityTypes.RELAPSE_CLONE, RelapseCloneEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI, UncontrolledJinchurikiEntity::setAttributes);
      EntityAttributeRegistry.register(MysticismMonsterEntityTypes.SHADOW_IMP, ShadowImpEntity::setAttributes);
   }

   public static void spawnPlacements() {
      SpawnPlacementsRegistry.register(
         MysticismMonsterEntityTypes.OKAMI, SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, TensuraTamableEntity::checkTensuraMobSpawnRules
      );
      SpawnPlacementsRegistry.register(
         MysticismMonsterEntityTypes.MEMOIRES, SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, TensuraTamableEntity::checkTensuraMobSpawnRules
      );
      SpawnPlacementsRegistry.register(
         MysticismMonsterEntityTypes.SHADOW_IMP,
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         TensuraTamableEntity::checkTensuraMobSpawnRules
      );
   }
}
