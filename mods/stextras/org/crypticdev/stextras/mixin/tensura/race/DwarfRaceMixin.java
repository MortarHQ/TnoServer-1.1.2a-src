package org.crypticdev.stextras.mixin.tensura.race;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.race.dwarf.DwarfRace;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.registary.skill.STExtrasIntrinsicSkills;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DwarfRace.class)
public class DwarfRaceMixin {
   @Unique
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)STExtrasIntrinsicSkills.BLACKSMITH.get());
      return list;
   }
}
