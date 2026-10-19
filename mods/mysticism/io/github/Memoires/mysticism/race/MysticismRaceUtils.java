package io.github.Memoires.mysticism.race;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class MysticismRaceUtils {
   public static List<ManasSkill> parseResourceIntoSkills(List<String> resourceLocations) {
      List<ManasSkill> list = new ArrayList<>();

      for (String rs : resourceLocations) {
         list.add((ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(rs)));
      }

      return list;
   }
}
