package cc.sighs.oelib.config.field;

import cc.sighs.oelib.config.model.ConfigValueMeta;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseFieldBuilder<T, B extends BaseFieldBuilder<T, B>> implements FieldBuilder<T> {
   protected final String key;
   protected final ConfigValueMeta.Builder metaBuilder;
   protected Codec<T> codec;
   protected T defaultValue;
   protected Consumer<ConfigValueMeta.Builder> beforeMetaHook;
   protected Consumer<ConfigValueMeta> afterMetaHook;
   protected Component validatorDescription;
   protected boolean tooltipEnabled;

   protected BaseFieldBuilder(String key, Codec<T> codec) {
      this.key = key;
      this.codec = codec;
      this.metaBuilder = ConfigValueMeta.builder(key);
   }

   public B comment(String text) {
      this.metaBuilder.comment(text);
      return (B)this;
   }

   public B tooltip() {
      this.tooltipEnabled = true;
      return (B)this;
   }

   public B defaultValue(T value) {
      this.defaultValue = value;
      return (B)this;
   }

   public B hiddenInUi() {
      this.metaBuilder.hidden(true);
      return (B)this;
   }

   public B beforeMeta(Consumer<ConfigValueMeta.Builder> hook) {
      this.beforeMetaHook = hook;
      return (B)this;
   }

   public B afterMeta(Consumer<ConfigValueMeta> hook) {
      this.afterMetaHook = hook;
      return (B)this;
   }

   @Override
   public <O> RecordCodecBuilder<O, T> forGetter(Function<O, T> getter) {
      Objects.requireNonNull(getter);
      if (this.beforeMetaHook != null) {
         this.beforeMetaHook.accept(this.metaBuilder);
      }

      ResourceLocation cfgId = ConfigField.CURRENT_CONFIG_ID.get();
      if (cfgId != null && this.metaBuilder != null) {
         String autoKey = "config." + cfgId.getNamespace() + "." + cfgId.getPath() + "." + this.key;
         this.metaBuilder.translationKey(autoKey);
         if (this.tooltipEnabled) {
            this.metaBuilder.tooltip(autoKey + ".tooltip");
         }
      }

      ConfigValueMeta meta = this.metaBuilder.build();
      ConfigField.recordMeta(meta);
      MapCodec<T> field = this.codec.fieldOf(meta.key());
      if (this.defaultValue != null) {
         field = field.orElse(this.defaultValue);
      }

      if (this.afterMetaHook != null) {
         this.afterMetaHook.accept(meta);
      }

      return field.forGetter(getter);
   }
}
