package com.shin.tensura_kumodesu.registry;

import com.shin.tensura_kumodesu.registry.attributes.KumodesuAttributes;
import com.shin.tensura_kumodesu.registry.effects.KumodesuEffects;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import com.shin.tensura_kumodesu.registry.skill.KumoCommonSkills;
import com.shin.tensura_kumodesu.registry.skill.KumoExtraSkills;
import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import com.shin.tensura_kumodesu.registry.skill.KumoResistanceSkills;
import com.shin.tensura_kumodesu.registry.skill.KumoUniqueSkills;

public class KumodesuRegistry {
   public static void init() {
      KumodesuAttributes.init();
      KumodesuEffects.init();
      KumodesuRaces.init();
      KumoCommonSkills.init();
      KumoExtraSkills.init();
      KumoIntrinsicSkills.init();
      KumoResistanceSkills.init();
      KumoUniqueSkills.init();
   }
}
