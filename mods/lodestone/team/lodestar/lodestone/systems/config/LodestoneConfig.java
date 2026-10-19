package team.lodestar.lodestone.systems.config;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class LodestoneConfig {
   public static final ConcurrentHashMap<Pair<String, LodestoneConfig.ConfigPath>, ArrayList<LodestoneConfig.ConfigValueHolder>> VALUE_HOLDERS = new ConcurrentHashMap<>();

   public LodestoneConfig(String modId, String configType, Builder builder) {
      for (Entry<Pair<String, LodestoneConfig.ConfigPath>, ArrayList<LodestoneConfig.ConfigValueHolder>> next : VALUE_HOLDERS.entrySet()) {
         Pair<String, LodestoneConfig.ConfigPath> s = next.getKey();
         if (((String)s.getFirst()).equals(modId + "/" + configType)) {
            builder.push(List.of(((LodestoneConfig.ConfigPath)s.getSecond()).strings));

            for (LodestoneConfig.ConfigValueHolder configValueHolder : next.getValue()) {
               configValueHolder.setConfig(builder);
            }

            builder.pop(((LodestoneConfig.ConfigPath)s.getSecond()).strings.length);
         }
      }
   }

   public interface BuilderSupplier<T> {
      ConfigValue<T> createBuilder(Builder var1);
   }

   public record ConfigPath(String... strings) {
      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            LodestoneConfig.ConfigPath otherPath = (LodestoneConfig.ConfigPath)o;
            return Arrays.equals(this.strings, otherPath.strings);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode(this.strings);
      }
   }

   public static class ConfigValueHolder<T> {
      private final LodestoneConfig.BuilderSupplier<T> valueSupplier;
      private ConfigValue<T> config;

      public ConfigValueHolder(String modId, String path, LodestoneConfig.BuilderSupplier<T> valueSupplier) {
         this.valueSupplier = valueSupplier;
         ArrayList<String> entirePath = new ArrayList<>(List.of(path.split("/")));
         String configType = modId + "/" + entirePath.remove(0);
         LodestoneConfig.VALUE_HOLDERS
            .computeIfAbsent(Pair.of(configType, new LodestoneConfig.ConfigPath(entirePath.toArray(new String[0]))), s -> new ArrayList<>())
            .add(this);
      }

      public void setConfig(Builder builder) {
         this.config = this.valueSupplier.createBuilder(builder);
      }

      public void setConfigValue(T t) {
         this.config.set(t);
      }

      public ConfigValue<T> getConfig() {
         return this.config;
      }

      public T getConfigValue() {
         return (T)this.config.get();
      }
   }
}
