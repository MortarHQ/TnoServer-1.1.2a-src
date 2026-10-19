package com.iafenvoy.iceandfire.config;

import com.iafenvoy.jupiter.config.container.FileConfigContainer;
import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.iafenvoy.jupiter.config.entry.BooleanEntry.Builder;
import net.minecraft.resources.ResourceLocation;

public class IafClientConfig extends FileConfigContainer {
   public static final IafClientConfig INSTANCE = new IafClientConfig();
   public final BooleanEntry customMainMenu = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.customMainMenu", true).key("customMainMenu"))
      .build();
   public final BooleanEntry dragonAuto3rdPerson = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragonAuto3rdPerson", false)
         .key("dragonAuto3rdPerson"))
      .build();
   public final BooleanEntry sirenShader = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.siren.shader", true).key("siren.shader")).build();

   public IafClientConfig() {
      super(ResourceLocation.fromNamespaceAndPath("iceandfire", "client"), "screen.iceandfire.client.title", "./config/iceandfire/iaf-client.json");
   }

   public void init() {
      this.createTab("client", "config.iceandfire.client").addEntry(this.customMainMenu).addEntry(this.dragonAuto3rdPerson).addEntry(this.sirenShader);
   }
}
