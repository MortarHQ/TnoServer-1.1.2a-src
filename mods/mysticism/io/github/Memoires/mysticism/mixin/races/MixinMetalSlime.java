package io.github.Memoires.mysticism.mixin.races;

import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.race.slime.MetalSlimeRace;
import io.github.manasmods.tensura.race.slime.SlimeRace;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MetalSlimeRace.class)
public class MixinMetalSlime extends SlimeRace {
   @NotNull
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = super.getIntrinsicSkills(instance, entity);
      list.add((ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
      list.add((ManasSkill)IntrinsicSkills.BODY_ARMOR.get());
      list.add((ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.COLD_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.CORROSION_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.EARTH_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.ELECTRICITY_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.GRAVITY_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.HEAT_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.LIGHT_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.WATER_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.WIND_ATTACK_RESISTANCE.get());
      return list;
   }
}
