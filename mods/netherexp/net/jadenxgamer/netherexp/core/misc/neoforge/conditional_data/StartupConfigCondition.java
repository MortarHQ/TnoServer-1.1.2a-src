package net.jadenxgamer.netherexp.core.misc.neoforge.conditional_data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ICondition.IContext;

public record StartupConfigCondition(String path) implements ICondition {
   public static final MapCodec<StartupConfigCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.STRING.fieldOf("path").forGetter(StartupConfigCondition::path)).apply(instance, StartupConfigCondition::new)
   );

   public boolean test(IContext iContext) {
      ConfigValue<?> configValue = (ConfigValue<?>)JNEConfigImpl.STARTUP.getValues().get(this.path);
      return configValue instanceof BooleanValue booleanValue ? (Boolean)booleanValue.get() : false;
   }

   public MapCodec<? extends ICondition> codec() {
      return CODEC;
   }
}
