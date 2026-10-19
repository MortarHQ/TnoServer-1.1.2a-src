package net.p3pp3rf1y.sophisticatedcore.settings;

import java.util.Optional;
import java.util.function.BiFunction;
import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.util.TriConsumer;

public class MainSetting<T> {
   private final String tagName;
   private final BiFunction<CompoundTag, String, Optional<T>> getValue;
   private final TriConsumer<CompoundTag, String, T> setValue;
   private final T defaultValue;

   public MainSetting(String tagName, BiFunction<CompoundTag, String, Optional<T>> getValue, TriConsumer<CompoundTag, String, T> setValue, T defaultValue) {
      this.tagName = tagName;
      this.getValue = getValue;
      this.setValue = setValue;
      this.defaultValue = defaultValue;
   }

   public String getName() {
      return this.tagName;
   }

   public void setValue(CompoundTag tag, T value) {
      this.setValue.accept(tag, this.tagName, value);
   }

   public void removeFrom(CompoundTag tag) {
      tag.remove(this.tagName);
   }

   public Optional<T> getValue(CompoundTag tag) {
      return this.getValue.apply(tag, this.tagName);
   }

   public T getDefaultValue() {
      return this.defaultValue;
   }
}
