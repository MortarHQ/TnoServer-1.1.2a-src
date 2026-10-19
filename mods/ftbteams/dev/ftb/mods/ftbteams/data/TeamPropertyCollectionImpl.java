package dev.ftb.mods.ftbteams.data;

import dev.ftb.mods.ftbteams.api.event.TeamCollectPropertiesEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyType;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyValue;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class TeamPropertyCollectionImpl implements TeamPropertyCollection {
   private final TeamPropertyCollectionImpl.PropertyMap map = new TeamPropertyCollectionImpl.PropertyMap();
   public static final StreamCodec<RegistryFriendlyByteBuf, TeamPropertyCollection> STREAM_CODEC = StreamCodec.of((buffer, props) -> {
      buffer.writeVarInt(props.size());
      props.forEach((prop, value) -> {
         TeamPropertyType.write(buffer, prop);
         prop.writeValue(buffer, value.getValue());
      });
   }, buffer -> {
      TeamPropertyCollectionImpl props = new TeamPropertyCollectionImpl();
      int nProperties = buffer.readVarInt();

      for (int i = 0; i < nProperties; i++) {
         TeamProperty<?> tp = TeamPropertyType.read(buffer);
         props.map.putPropertyFromNetwork(tp, buffer);
      }

      return props;
   });

   public void collectProperties() {
      this.map.clear();
      ((Consumer)TeamEvent.COLLECT_PROPERTIES.invoker()).accept(new TeamCollectPropertiesEvent(this.map::putDefaultProperty));
   }

   @Override
   public <T> void forEach(BiConsumer<TeamProperty<T>, TeamPropertyValue<T>> consumer) {
      this.map.forEachProperty(consumer);
   }

   public TeamPropertyCollectionImpl copy() {
      return this.copyIf(p -> true);
   }

   @Override
   public void updateFrom(TeamPropertyCollection otherProperties) {
      otherProperties.forEach((key, value) -> this.set(key, value.getValue()));
   }

   @Override
   public <T> T get(TeamProperty<T> key) {
      TeamPropertyValue<T> v = this.map.getProperty(key);
      return v == null ? key.getDefaultValue() : v.getValue();
   }

   @Override
   public <T> void set(TeamProperty<T> key, T value) {
      if (this.map.hasProperty(key)) {
         this.map.getProperty(key).setValue(value);
      } else {
         this.map.putProperty(key, new TeamPropertyValue<>(key, value));
      }
   }

   @Override
   public int size() {
      return this.map.size();
   }

   public TeamPropertyCollectionImpl copyIf(Predicate<TeamProperty<?>> predicate) {
      TeamPropertyCollectionImpl p = new TeamPropertyCollectionImpl();
      this.map.forEachProperty((key, value) -> {
         if (predicate.test(key)) {
            p.map.putProperty(key, value.copy());
         }
      });
      return p;
   }

   public void write(RegistryFriendlyByteBuf buffer) {
      STREAM_CODEC.encode(buffer, this);
   }

   public void writeSyncableOnly(RegistryFriendlyByteBuf buffer) {
      TeamPropertyCollectionImpl.PropertyMap subMap = new TeamPropertyCollectionImpl.PropertyMap();
      this.map.forEachProperty((prop, val) -> {
         if (prop.shouldSyncToAll()) {
            subMap.backingMap.put(prop, this.map.backingMap.get(prop));
         }
      });
      buffer.writeVarInt(subMap.size());
      subMap.forEachProperty((key, value) -> {
         TeamPropertyType.write(buffer, key);
         key.writeValue(buffer, value.getValue());
      });
   }

   public void read(CompoundTag tag) {
      tag.getAllKeys().forEach(key -> this.map.findProperty(key).ifPresent(prop -> this.map.putPropertyFromNBT((TeamProperty<?>)prop, tag.get(key))));
   }

   public CompoundTag write(CompoundTag tag) {
      this.map.forEachProperty((key, value) -> tag.put(key.getId().toString(), key.toNBT(value.getValue())));
      return tag;
   }

   private static class PropertyMap {
      final Map<Object, Object> backingMap = new LinkedHashMap<>();
      final Map<ResourceLocation, TeamProperty<?>> byId = new HashMap<>();

      void clear() {
         this.backingMap.clear();
         this.byId.clear();
      }

      boolean hasProperty(TeamProperty<?> prop) {
         return this.backingMap.containsKey(prop);
      }

      int size() {
         return this.backingMap.size();
      }

      <T> void putProperty(TeamProperty<T> prop, TeamPropertyValue<T> value) {
         this.backingMap.put(prop, value);
         this.byId.put(prop.getId(), prop);
      }

      void putDefaultProperty(TeamProperty<?> prop) {
         this.backingMap.put(prop, TeamPropertyValue.createDefaultValue(prop));
         this.byId.put(prop.getId(), prop);
      }

      void putPropertyFromNetwork(TeamProperty<?> prop, RegistryFriendlyByteBuf buffer) {
         this.backingMap.put(prop, TeamPropertyValue.fromNetwork(prop, buffer));
         this.byId.put(prop.getId(), prop);
      }

      void putPropertyFromNBT(TeamProperty<?> prop, Tag tag) {
         this.backingMap.put(prop, TeamPropertyValue.fromNBT(prop, tag));
         this.byId.put(prop.getId(), prop);
      }

      <T> TeamPropertyValue<T> getProperty(TeamProperty<T> property) {
         return (TeamPropertyValue<T>)this.backingMap.get(property);
      }

      <T> void forEachProperty(BiConsumer<TeamProperty<T>, TeamPropertyValue<T>> consumer) {
         this.backingMap.forEach((k, v) -> consumer.accept((T)((TeamProperty)k), v));
      }

      Optional<TeamProperty<?>> findProperty(String key) {
         try {
            return Optional.ofNullable(this.byId.get(ResourceLocation.tryParse(key)));
         } catch (ResourceLocationException e) {
            return Optional.empty();
         }
      }
   }
}
