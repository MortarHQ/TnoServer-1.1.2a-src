package com.teamresourceful.resourcefullib.client.sysinfo.defaults;

import com.mojang.blaze3d.platform.Window;
import com.teamresourceful.resourcefullib.client.sysinfo.SystemInfoBuilder;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.util.FormatUtil;

public record OperatingSystem(SystemInfo info) implements Consumer<SystemInfoBuilder> {
   private static final Pattern INFO_PATTERN = Pattern.compile("(.*)=(.*)");

   public static void register() {
      com.teamresourceful.resourcefullib.client.sysinfo.SystemInfo.addBuilder("System", new OperatingSystem(new SystemInfo()));
   }

   private void appendGPU(SystemInfoBuilder builder, List<GraphicsCard> gpus) {
      int i = 0;

      for (GraphicsCard gpu : gpus) {
         builder.append("GPU #" + i, gpu.getName());

         for (String info : gpu.getVersionInfo().split(", ")) {
            Matcher matcher = INFO_PATTERN.matcher(info);
            if (matcher.matches()) {
               builder.append("GPU #%d %s".formatted(i, matcher.group(1)), matcher.group(2));
            } else {
               builder.append("GPU #%d Version Info".formatted(i), info);
            }
         }

         builder.append("GPU #%d VRAM".formatted(i), FormatUtil.formatBytes(gpu.getVRam()));
         i++;
      }
   }

   private void appendMemorySlots(SystemInfoBuilder builder, List<PhysicalMemory> slots) {
      int i = 0;

      for (PhysicalMemory memory : slots) {
         builder.append("Memory Slot #" + i, "%s (%s)", memory.getMemoryType(), FormatUtil.formatHertz(memory.getClockSpeed()));
         builder.append("Memory Slot #" + i + " Capacity", FormatUtil.formatBytes(memory.getCapacity()));
         i++;
      }
   }

   private void appendMemory(SystemInfoBuilder builder, GlobalMemory memory) {
      long current = memory.getAvailable();
      long total = memory.getTotal();
      builder.append("RAM", "%s/%s", FormatUtil.formatBytes(current), FormatUtil.formatBytes(total));
   }

   public void accept(SystemInfoBuilder builder) {
      HardwareAbstractionLayer hardware = this.info.getHardware();
      CentralProcessor processor = hardware.getProcessor();
      Window window = Minecraft.getInstance().getWindow();
      builder.append("OS", System.getProperty("os.name"));
      builder.append("CPU", processor.getProcessorIdentifier().getName());
      builder.append("Display", "%dx%d (%s)", window.getWidth(), window.getHeight(), GL11.glGetString(7936));
      builder.append("Language", Locale.getDefault().getDisplayLanguage());
      this.appendGPU(builder, hardware.getGraphicsCards());
      this.appendMemory(builder, hardware.getMemory());
      this.appendMemorySlots(builder, hardware.getMemory().getPhysicalMemory());
   }
}
