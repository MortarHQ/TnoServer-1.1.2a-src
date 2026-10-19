package dev.ftb.mods.ftblibrary.snbt.config;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LongValue extends NumberValue<Long> {
   LongValue(SNBTConfig c, String n, long def) {
      super(c, n, def);
   }

   public NumberValue<Long> range(long max) {
      return this.range(0L, max);
   }

   public void set(Long v) {
      super.set(MathUtils.clamp(v, this.minValue == null ? Long.MIN_VALUE : this.minValue, this.maxValue == null ? Long.MAX_VALUE : this.maxValue));
   }

   @Override
   public void write(SNBTCompoundTag tag) {
      super.write(tag);
      tag.putLong(this.key, this.get());
   }

   @Override
   public void read(SNBTCompoundTag tag) {
      this.set(tag.getLong(this.key));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void createClientConfig(ConfigGroup group) {
      group.addLong(
            this.key,
            this.get(),
            this::set,
            this.defaultValue,
            this.minValue == null ? Long.MIN_VALUE : this.minValue,
            this.maxValue == null ? Long.MAX_VALUE : this.maxValue
         )
         .fader(this.fader)
         .setCanEdit(this.enabled.getAsBoolean());
   }
}
