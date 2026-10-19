package net.regions_unexplored;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ICondition.IContext;
import net.regions_unexplored.config.RUConfigHandler;
import org.jetbrains.annotations.NotNull;

public record RUConfigCondition(String key) implements ICondition {
   public static final MapCodec<RUConfigCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.STRING.fieldOf("key").forGetter(RUConfigCondition::key)).apply(instance, RUConfigCondition::new)
   );

   public boolean test(@NotNull IContext context) {
      return RUConfigHandler.COMMON.test(this.key);
   }

   @NotNull
   public MapCodec<? extends ICondition> codec() {
      return CODEC;
   }
}
