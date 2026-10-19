package dev.ftb.mods.ftbchunks.client.minimap.components;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapContext;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import dev.ftb.mods.ftbchunks.api.client.minimap.TranslatedOption;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftbteams.api.Team;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ZoneInfoComponent implements MinimapInfoComponent {
   public static final ResourceLocation ID = FTBChunksAPI.rl("zone");
   public static final Component WILDNESS = Component.translatable("wilderness").withStyle(s -> s.withColor(ChatFormatting.DARK_GREEN).withItalic(true));
   private Team team;

   @Override
   public ResourceLocation id() {
      return ID;
   }

   @Override
   public void render(MinimapContext context, GuiGraphics graphics, Font font) {
      String setting = context.getSetting(this);
      if (this.team != null) {
         this.drawCenteredText(context.minecraft().font, graphics, this.team.getColoredName(), 0);
      } else if (setting.equals(ZoneInfoComponent.ShowWilderness.SHOW_WILDERNESS.name())) {
         this.drawCenteredText(context.minecraft().font, graphics, WILDNESS, 0);
      }
   }

   @Override
   public boolean shouldRender(MinimapContext context) {
      MapRegionData data = context.mapDimension().getRegion(XZ.regionFromChunk(context.mapChunksPos().x(), context.mapChunksPos().z())).getData();
      this.team = null;
      if (data != null) {
         Optional<Team> foundTeam = data.getChunk(XZ.of(context.mapChunksPos().x(), context.mapChunksPos().z())).getTeam();
         if (foundTeam.isPresent()) {
            this.team = foundTeam.get();
            return true;
         }
      }

      String setting = context.getSetting(this);
      return !setting.isEmpty() && !setting.equals(ZoneInfoComponent.ShowWilderness.JUST_CLAIMED.name());
   }

   @Override
   public Set<TranslatedOption> getConfigComponents() {
      return Arrays.stream(ZoneInfoComponent.ShowWilderness.values())
         .map(value -> new TranslatedOption(value.name(), "ftbchunks.show_wilderness." + ZoneInfoComponent.ShowWilderness.NAME_MAP.getName(value)))
         .collect(Collectors.toSet());
   }

   public enum ShowWilderness {
      JUST_CLAIMED,
      SHOW_WILDERNESS;

      public static final NameMap<ZoneInfoComponent.ShowWilderness> NAME_MAP = NameMap.of(SHOW_WILDERNESS, values())
         .baseNameKey("ftbchunks.show_wilderness")
         .create();
   }
}
