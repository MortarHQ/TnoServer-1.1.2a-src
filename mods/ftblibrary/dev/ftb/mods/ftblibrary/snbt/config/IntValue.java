package dev.ftb.mods.ftblibrary.snbt.config;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class IntValue extends NumberValue<Integer> {
   IntValue(SNBTConfig c, String n, int def) {
      super(c, n, def);
   }

   public NumberValue<Integer> range(int max) {
      return this.range(0, max);
   }

   public void set(Integer v) {
      super.set(Mth.clamp(v, this.minValue == null ? Integer.MIN_VALUE : this.minValue, this.maxValue == null ? Integer.MAX_VALUE : this.maxValue));
   }

   @Override
   public void write(SNBTCompoundTag tag) {
      super.write(tag);
      tag.putInt(this.key, this.get());
   }

   @Override
   public void read(SNBTCompoundTag tag) {
      this.set(tag.getInt(this.key));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void createClientConfig(ConfigGroup group) {
      group.addInt(
            this.key,
            this.get(),
            this::set,
            this.defaultValue,
            this.minValue == null ? Integer.MIN_VALUE : this.minValue,
            this.maxValue == null ? Integer.MAX_VALUE : this.maxValue
         )
         .fader(this.fader)
         .setCanEdit(this.enabled.getAsBoolean());
   }
}
