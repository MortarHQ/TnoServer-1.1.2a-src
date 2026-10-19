package com.github.dominickwd04.traddon.registry;

import com.github.dominickwd04.traddon.registry.effect.TrAddonMobEffects;
import com.github.dominickwd04.traddon.registry.entity.TrAddonMiscEntityTypes;
import com.github.dominickwd04.traddon.registry.race.TrAddonRaces;
import com.github.dominickwd04.traddon.registry.skill.TrAddonIntrinsicSkills;
import com.github.dominickwd04.traddon.registry.skill.TrAddonUniqueSkills;

public class TrAddonRegistry {
   public static void init() {
      TrAddonMobEffects.init();
      TrAddonUniqueSkills.init();
      TrAddonIntrinsicSkills.init();
      TrAddonRaces.init();
      TrAddonMiscEntityTypes.init();
   }
}
