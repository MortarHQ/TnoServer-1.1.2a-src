package io.github.Memoires.mysticism.registry;

import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import io.github.Memoires.mysticism.registry.component.MysticismDataComponents;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismEntityTypes;
import io.github.Memoires.mysticism.registry.item.MysticismMaterialItems;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.item.MysticismSpawnEggs;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.Memoires.mysticism.registry.item.misc.MysticismEnchantmentEffectComponents;
import io.github.Memoires.mysticism.registry.magic.SpiritualMysticismMagics;
import io.github.Memoires.mysticism.registry.menu.MysticismMenuTypes;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UltimateMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.Memoires.mysticism.registry.world.MysticismFeatures;

public class MysticismRegistry {
   public static void init() {
      MysticismDataComponents.init();
      MysticismBlocks.init();
      MysticismEntityTypes.init();
      MysticismAttributes.init();
      MysticismEnchantmentEffectComponents.init();
      MysticismFeatures.init();
      MysticismRaces.init();
      MysticismMenuTypes.init();
      ExtraMysticismSkills.init();
      IntrinsicMysticismSkills.init();
      UniqueMysticismSkills.init();
      UltimateMysticismSkills.init();
      SpiritualMysticismMagics.init();
      MysticismSoundEvents.init();
      MysticismMobDropItems.init();
      MysticismMaterialItems.init();
      MysticismMobEffects.init();
      MysticismSpawnEggs.init();
      MysticismWeaponItems.init();
      MysticismStats.init();
   }
}
