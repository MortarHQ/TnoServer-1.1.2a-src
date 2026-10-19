package dev.ftb.mods.ftbchunks;

import com.mojang.logging.LogUtils;
import dev.ftb.mods.ftbchunks.client.gui.EntityIconSettingsScreen;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.snbt.config.BaseValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class EntityTypeBoolMapValue extends BaseValue<Map<ResourceKey<EntityType<?>>, Boolean>> {
   private static final Logger LOGGER = LogUtils.getLogger();

   public EntityTypeBoolMapValue(@Nullable SNBTConfig c, String n, Map<ResourceKey<EntityType<?>>, Boolean> def) {
      super(c, n, def);
      super.set(new HashMap<>(def));
   }

   public void write(SNBTCompoundTag tag) {
      Map<ResourceKey<EntityType<?>>, Boolean> map = (Map<ResourceKey<EntityType<?>>, Boolean>)this.get();
      SNBTCompoundTag mapTag = new SNBTCompoundTag();

      for (Entry<ResourceKey<EntityType<?>>, Boolean> entry : map.entrySet()) {
         mapTag.putBoolean(entry.getKey().location().toString(), entry.getValue());
      }

      tag.put(this.key, mapTag);
   }

   public void read(SNBTCompoundTag tag) {
      Map<ResourceKey<EntityType<?>>, Boolean> map = new HashMap<>();
      SNBTCompoundTag compound = tag.getCompound(this.key);

      for (String key : compound.getAllKeys()) {
         try {
            ResourceLocation parse = ResourceLocation.parse(key);
            map.put(ResourceKey.create(Registries.ENTITY_TYPE, parse), compound.getBoolean(key));
         } catch (ResourceLocationException e) {
            LOGGER.error("Failed to parse {} skipping", key, e);
         }
      }

      this.set(map);
   }

   public void createClientConfig(ConfigGroup group) {
      group.add(this.key, new EntityTypeBoolMapValue.EntityTypeBoolMapConfigValue(), (Map)this.get(), stringBooleanMap -> {}, (Map)this.defaultValue);
   }

   public static class EntityTypeBoolMapConfigValue extends ConfigValue<Map<ResourceKey<EntityType<?>>, Boolean>> {
      public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
         new EntityIconSettingsScreen(false).openGui();
      }

      public Component getStringForGUI(@Nullable Map<ResourceKey<EntityType<?>>, Boolean> v) {
         if (v == null) {
            return super.getStringForGUI(null);
         }

         int enabled = 0;
         int disabled = 0;

         for (ResourceKey<EntityType<?>> entityTypeResourceKey : v.keySet()) {
            EntityType<?> entityType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(entityTypeResourceKey);
            if (entityType != null) {
               if (v.get(entityTypeResourceKey)) {
                  enabled++;
               } else {
                  disabled++;
               }
            }
         }

         return Component.translatable("ftbchunks.gui.enabled_disabled_count", new Object[]{enabled, disabled});
      }
   }
}
