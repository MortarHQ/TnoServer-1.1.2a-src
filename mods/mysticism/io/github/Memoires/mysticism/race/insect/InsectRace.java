package io.github.Memoires.mysticism.race.insect;

import io.github.Memoires.mysticism.config.race.insect.AntConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;

public class InsectRace extends DefaultRace {
   protected static final AntConfig.Ant CONFIG = ((AntConfig)ConfigRegistry.getConfig(AntConfig.class)).Ant;

   public InsectRace() {
      super(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }
}
