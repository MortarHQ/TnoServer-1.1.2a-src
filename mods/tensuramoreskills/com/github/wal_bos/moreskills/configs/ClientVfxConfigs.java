package com.github.wal_bos.moreskills.configs;

import com.github.wal_bos.moreskills.client.fx.ClientVfxIds;
import java.util.LinkedHashMap;
import java.util.Map;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public final class ClientVfxConfigs {
   public static final ModConfigSpec CLIENT_SPEC;
   public static final ClientVfxConfigs.ClientConfig CLIENT;

   private ClientVfxConfigs() {
   }

   public static void init() {
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.CLIENT, CLIENT_SPEC, "tensuramoreskills-client-vfx.toml");
   }

   static {
      Pair<ClientVfxConfigs.ClientConfig, ModConfigSpec> pair = new Builder().configure(ClientVfxConfigs.ClientConfig::new);
      CLIENT = (ClientVfxConfigs.ClientConfig)pair.getLeft();
      CLIENT_SPEC = (ModConfigSpec)pair.getRight();
   }

   public static final class ClientConfig {
      public final BooleanValue allEffects;
      public final Map<String, BooleanValue> effects = new LinkedHashMap<>();

      ClientConfig(Builder builder) {
         builder.comment("Client side VFX render toggles. These only affect this Minecraft client.");
         builder.push("vfx");
         this.allEffects = builder.comment("If false, all Tensura More Skills VFX are disabled on this client.").define("allEffects", true);
         builder.push("effects");

         for (String id : ClientVfxIds.ALL) {
            this.effects.put(id, builder.define(id, true));
         }

         builder.pop();
         builder.pop();
      }

      public boolean isEnabled(String id) {
         BooleanValue value = this.effects.get(id);
         return value == null || (Boolean)value.get();
      }

      public boolean hasAnyDisabled() {
         if (!(Boolean)this.allEffects.get()) {
            return true;
         }

         for (BooleanValue value : this.effects.values()) {
            if (!(Boolean)value.get()) {
               return true;
            }
         }

         return false;
      }
   }
}
