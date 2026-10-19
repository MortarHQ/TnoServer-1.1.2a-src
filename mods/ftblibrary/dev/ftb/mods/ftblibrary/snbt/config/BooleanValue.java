package dev.ftb.mods.ftblibrary.snbt.config;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import java.util.ArrayList;
import java.util.List;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BooleanValue extends BaseValue<Boolean> {
   BooleanValue(SNBTConfig c, String n, boolean def) {
      super(c, n, def);
   }

   public void toggle() {
      this.set(!this.get());
   }

   @Override
   public void write(SNBTCompoundTag tag) {
      List<String> s = new ArrayList<>(this.comment);
      s.add("Default: " + this.defaultValue);
      tag.comment(this.key, String.join("\n", s));
      tag.putBoolean(this.key, this.get());
   }

   @Override
   public void read(SNBTCompoundTag tag) {
      this.set(tag.getBoolean(this.key));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void createClientConfig(ConfigGroup group) {
      group.addBool(this.key, this.get(), this::set, this.defaultValue).setCanEdit(this.enabled.getAsBoolean());
   }
}
