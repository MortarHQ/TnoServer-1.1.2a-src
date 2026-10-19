package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public abstract class TeamProperty<T> {
   protected final ResourceLocation id;
   private final Supplier<T> defaultValue;
   private boolean playerEditable;
   private boolean shouldSyncToAll;
   private boolean hidden;

   protected TeamProperty(ResourceLocation id, Supplier<T> defaultValue) {
      this.id = id;
      this.defaultValue = defaultValue;
      this.playerEditable = true;
      this.hidden = false;
      this.shouldSyncToAll = false;
   }

   protected TeamProperty(ResourceLocation id, T defaultValue) {
      this(id, () -> defaultValue);
   }

   public abstract TeamPropertyType<T> getType();

   public ResourceLocation getId() {
      return this.id;
   }

   public T getDefaultValue() {
      return this.defaultValue.get();
   }

   public boolean isPlayerEditable() {
      return this.playerEditable;
   }

   public TeamProperty<T> notPlayerEditable() {
      this.playerEditable = false;
      return this;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public TeamProperty<T> hidden() {
      this.hidden = true;
      return this;
   }

   public boolean shouldSyncToAll() {
      return this.shouldSyncToAll;
   }

   public TeamProperty<T> syncToAll() {
      this.shouldSyncToAll = true;
      return this;
   }

   public String getTranslationKey(String prefix) {
      return prefix + "." + this.id.getNamespace() + "." + this.id.getPath();
   }

   public abstract Optional<T> fromString(String var1);

   public abstract void write(RegistryFriendlyByteBuf var1);

   public String toString(T value) {
      return value.toString();
   }

   public void writeValue(RegistryFriendlyByteBuf buf, T value) {
      buf.writeUtf(this.toString(value), 32767);
   }

   public T readValue(RegistryFriendlyByteBuf buf) {
      return this.fromString(buf.readUtf(32767)).orElse(this.getDefaultValue());
   }

   public Tag toNBT(T value) {
      return StringTag.valueOf(this.toString(value));
   }

   public Optional<T> fromNBT(Tag tag) {
      return this.fromString(tag.getAsString());
   }

   @Nullable
   public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<T> value) {
      return null;
   }

   @Override
   public final boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return o instanceof TeamProperty ? this.id.equals(((TeamProperty)o).id) : false;
      }
   }

   @Override
   public final int hashCode() {
      return this.id.hashCode();
   }

   @Override
   public final String toString() {
      return this.id.toString();
   }

   @Deprecated(forRemoval = true)
   public TeamPropertyValue<T> createDefaultValue() {
      return new TeamPropertyValue<>(this, this.getDefaultValue());
   }

   @Deprecated(forRemoval = true)
   public TeamPropertyValue<T> createValueFromNetwork(RegistryFriendlyByteBuf buf) {
      return new TeamPropertyValue<>(this, this.readValue(buf));
   }

   @Deprecated(forRemoval = true)
   public TeamPropertyValue<T> createValueFromNBT(Tag tag) {
      return new TeamPropertyValue<>(this, this.fromNBT(tag).orElse(this.getDefaultValue()));
   }
}
