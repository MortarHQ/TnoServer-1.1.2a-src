package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class SpellCooldown {
   public static final Codec<SpellCooldown> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ESSpells.CODEC.fieldOf("spell").forGetter(SpellCooldown::getSpell), Codec.INT.fieldOf("cooldown").forGetter(SpellCooldown::getCooldown)
         )
         .apply(instance, SpellCooldown::new)
   );
   public static final Codec<List<SpellCooldown>> LIST_CODEC = CODEC.listOf();
   private final AbstractSpell spell;
   private int cooldown;

   public SpellCooldown(AbstractSpell spell) {
      this(spell, 0);
   }

   public SpellCooldown(AbstractSpell spell, int cooldown) {
      this.spell = spell;
      this.cooldown = cooldown;
   }

   public AbstractSpell getSpell() {
      return this.spell;
   }

   public void setCooldown(int cooldown) {
      this.cooldown = cooldown;
   }

   public int getCooldown() {
      return this.cooldown;
   }

   public void tick() {
      this.cooldown = Math.max(this.cooldown - 1, 0);
   }
}
