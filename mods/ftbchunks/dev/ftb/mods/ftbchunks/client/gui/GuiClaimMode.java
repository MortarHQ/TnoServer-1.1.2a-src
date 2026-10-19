package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import net.minecraft.network.chat.Component;

public enum GuiClaimMode {
   FREEHAND("freehand"),
   RECTANGLE("rectangle"),
   CIRCLE("circle");

   public static final NameMap<GuiClaimMode> NAME_MAP = NameMap.of(FREEHAND, values()).baseNameKey("ftbchunks.claim_mode").create();
   private final String key;
   private final Icon icon;

   GuiClaimMode(String key) {
      this.key = key;
      this.icon = Icon.getIcon(FTBChunksAPI.rl("textures/" + key + ".png"));
   }

   Component description() {
      return Component.translatable("ftbchunks.claim_mode." + this.key);
   }

   public Icon icon() {
      return this.icon;
   }

   public GuiClaimMode next() {
      return values()[(this.ordinal() + 1) % values().length];
   }
}
