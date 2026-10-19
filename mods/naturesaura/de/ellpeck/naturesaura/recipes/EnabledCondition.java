package de.ellpeck.naturesaura.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.NaturesAura;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ICondition.IContext;

public class EnabledCondition implements ICondition {
   private static final MapCodec<EnabledCondition> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(Codec.STRING.fieldOf("name").forGetter(c -> c.name)).apply(i, EnabledCondition::new)
   );
   private ConfigValue<Boolean> config;
   private final String name;

   public EnabledCondition(String name) {
      this.name = name;

      try {
         this.config = (ConfigValue<Boolean>)ModConfig.class.getField(name).get(ModConfig.instance);
      } catch (IllegalAccessException | NoSuchFieldException e) {
         NaturesAura.LOGGER.error(e);
      }
   }

   public boolean test(IContext context) {
      return this.config != null && (Boolean)this.config.get();
   }

   public MapCodec<? extends ICondition> codec() {
      return CODEC;
   }
}
