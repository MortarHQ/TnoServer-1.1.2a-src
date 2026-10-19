package twilightforest.client.overlay.display;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import twilightforest.TwilightForestMod;
import twilightforest.config.TFConfig;

public class ClockDisplay implements ItemDisplay {
   private static final long INITIAL_DAY_OFFSET_IN_TICKS = 6000L;
   private static final long MINECRAFT_DAY_LENGTH_IN_TICKS = 24000L;
   private static final long REAL_LIFE_DAY_LENGTH_IN_SECONDS = 86400L;
   private static final DateTimeFormatter FORMAT_24 = DateTimeFormatter.ofPattern("HH:mm");
   private static final DateTimeFormatter FORMAT_12 = DateTimeFormatter.ofPattern("hh:mm a");
   private static final NavigableMap<Long, ClockDisplay.TimeFrame> TIME_FRAMES = new TreeMap<>(
      Map.ofEntries(
         Map.entry(0L, ClockDisplay.TimeFrame.SUNRISE),
         Map.entry(501L, ClockDisplay.TimeFrame.DAY),
         Map.entry(12500L, ClockDisplay.TimeFrame.SUNSET),
         Map.entry(14000L, ClockDisplay.TimeFrame.NIGHT),
         Map.entry(22000L, ClockDisplay.TimeFrame.SUNRISE)
      )
   );

   private static long getMinecraftClockTimeInTicks(long ticks) {
      return (ticks + 6000L) % 24000L;
   }

   private static Component getGameTime(Level level, boolean use24HourFormat) {
      if (!level.dimensionType().natural()) {
         return Component.translatable("travellers_gear.modifier.twilightforest.item_display.clock.unknown");
      }

      long rawTime = level.dimensionType().fixedTime().orElse(level.getDayTime());
      long ticksOfDay = getMinecraftClockTimeInTicks(rawTime);
      long secondsOfDay = ticksOfDay * 86400L / 24000L;
      LocalTime localTime = LocalTime.ofSecondOfDay(secondsOfDay);
      return Component.literal(localTime.format(use24HourFormat ? FORMAT_24 : FORMAT_12));
   }

   @Override
   public void render(ItemStack item, GuiGraphics graphics, Minecraft minecraft, Gui gui, Player player, int widestWidgetWidth) {
      FormattedCharSequence formattedcharsequence = this.getText(minecraft).getVisualOrderText();
      if (minecraft.level.dimensionType().natural()) {
         int k = this.getFrameForTime(minecraft.level.dimensionType().fixedTime().orElse(minecraft.level.getDayTime())).frame;
         int xRow = k % 2;
         int yRow = k / 2 % 2;
         float xMin = xRow * 8;
         float yMin = yRow * 8;
         graphics.blit(
            TwilightForestMod.getGuiTexture("time.png"),
            widestWidgetWidth / 2 - 5 - minecraft.font.width(formattedcharsequence) / 2,
            0,
            xMin,
            yMin,
            8,
            8,
            16,
            16
         );
      }

      graphics.drawString(
         minecraft.font, formattedcharsequence, Math.max(0, widestWidgetWidth / 2 + 5 - minecraft.font.width(formattedcharsequence) / 2), 0, 16777215
      );
   }

   private ClockDisplay.TimeFrame getFrameForTime(long dayTimeInTicks) {
      long time = dayTimeInTicks % 24000L;
      return TIME_FRAMES.floorEntry(time).getValue();
   }

   @Override
   public ItemDisplay.Bounds getWidgetSize(ItemStack item, Minecraft minecraft, Gui gui, Player player, int widestWidgetWidth) {
      int textWidth = minecraft.font.width(this.getText(minecraft));
      boolean natural = minecraft.level.dimensionType().natural();
      return new ItemDisplay.Bounds(Math.max(0, widestWidgetWidth / 2 - (natural ? 5 : 0) - textWidth / 2), 0, textWidth + (natural ? 10 : 0), 9);
   }

   private Component getText(Minecraft minecraft) {
      return getGameTime(minecraft.level, TFConfig.clock24HourFormat);
   }

   private enum TimeFrame {
      SUNRISE(0),
      DAY(1),
      SUNSET(2),
      NIGHT(3);

      final int frame;

      TimeFrame(int frame) {
         this.frame = frame;
      }
   }
}
