package io.github.Memoires.mysticism.data.existence;

import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class MysticismEntityExistenceData {
   public static void bootstrap(BootstrapContext<EntityExistenceData> context) {
      applyMysticism(context);
   }

   public static void register(BootstrapContext<EntityExistenceData> context, EntityExistenceData data) {
      ResourceKey<EntityExistenceData> key = ResourceKey.create(TensuraCustomData.ENTITY_EXISTENCE, data.entity());
      context.register(key, data);
   }

   private static void applyMysticism(BootstrapContext<EntityExistenceData> context) {
      handleOkami(context);
      handleMemoires(context);
      handleInsectlike(context);
      handleShadowImp(context);
      register(context, EntityExistenceData.getDefault(MysticismMonsterEntityTypes.CLAY_GOLEM.getId(), 50, 50, 50, 50));
   }

   private static void handleMemoires(BootstrapContext<EntityExistenceData> context) {
      List<ResourceLocation> memoiresSkill = new ArrayList<>();
      memoiresSkill.add(UniqueMysticismSkills.REPEATER.getId());
      memoiresSkill.add(IntrinsicMysticismSkills.RELAPSE.getId());
      memoiresSkill.add(ExtraSkills.STEEL_STRENGTH.getId());
      memoiresSkill.add(ExtraSkills.MAGIC_SENSE.getId());
      memoiresSkill.add(ResistanceSkills.HEAT_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.COLD_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId());
      memoiresSkill.add(ResistanceSkills.ABNORMAL_CONDITION_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.POISON_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.PARALYSIS_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.CORROSION_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.MAGIC_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.LIGHT_ATTACK_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.WATER_ATTACK_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.FLAME_ATTACK_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.ELECTRICITY_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.EARTH_ATTACK_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.WIND_ATTACK_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.getId());
      memoiresSkill.add(ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.getId());
      register(context, EntityExistenceData.getDefault(MysticismMonsterEntityTypes.MEMOIRES.getId(), 13200, 325000, 350000, 285000, 295000, memoiresSkill));
   }

   private static void handleOkami(BootstrapContext<EntityExistenceData> context) {
      List<ResourceLocation> okamiSkills = new ArrayList<>();
      okamiSkills.add(CommonSkills.SELF_REGENERATION.getId());
      okamiSkills.add(IntrinsicSkills.BEAST_TRANSFORMATION.getId());
      register(context, EntityExistenceData.getDefault(MysticismMonsterEntityTypes.OKAMI.getId(), 70, 300, 600, 1500, 2500, okamiSkills));
   }

   private static void handleInsectlike(BootstrapContext<EntityExistenceData> context) {
      List<ResourceLocation> exoskeleton = new ArrayList<>();
      exoskeleton.add(IntrinsicMysticismSkills.EXOSKELETON.getId());
      List<ResourceLocation> vanillaCaveSpider = new ArrayList<>(exoskeleton);
      vanillaCaveSpider.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(EntityType.getKey(EntityType.SPIDER), 40, 850, 850, 200, 200, exoskeleton));
      register(context, EntityExistenceData.getDefault(EntityType.getKey(EntityType.CAVE_SPIDER), 30, 250, 250, 800, 800, vanillaCaveSpider));
      register(context, EntityExistenceData.getDefault(MonsterEntityTypes.ARMY_WASP.getId(), 160, 10000, 15000, vanillaCaveSpider));
      List<ResourceLocation> blackSpiderSkills = new ArrayList<>(exoskeleton);
      blackSpiderSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      register(context, EntityExistenceData.getDefault(MonsterEntityTypes.BLACK_SPIDER.getId(), 200, 5000, 6000, 1000, 1500, blackSpiderSkills));
      register(context, EntityExistenceData.getDefault(MonsterEntityTypes.KNIGHT_SPIDER.getId(), 180, 5000, 6500, 4000, 4500, exoskeleton));
   }

   private static void handleShadowImp(BootstrapContext<EntityExistenceData> context) {
      List<ResourceLocation> impSkills = new ArrayList<>();
      impSkills.add(IntrinsicSkills.DARKNESS_TRANSFORM.getId());
      impSkills.add(ExtraMysticismSkills.DARKNESS_MANIPULATION.getId());
      impSkills.add(ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.getId());
      register(context, EntityExistenceData.getDefault(MysticismMonsterEntityTypes.SHADOW_IMP.getId(), 500, 9000, 1000, impSkills));
   }
}
