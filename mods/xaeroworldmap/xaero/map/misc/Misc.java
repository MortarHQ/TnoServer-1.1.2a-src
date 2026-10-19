package xaero.map.misc;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.Team;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import xaero.map.WorldMap;

public class Misc {
   private static final long[] ZERO_LONG_1024 = new long[1024];
   private static long cpuTimerPreTime;
   private static long glTimerPreTime;
   public static final String OUTDATED_FILE_EXT = ".outdated";

   public static BlockState getStateById(int id) {
      try {
         return Block.stateById(id);
      } catch (Exception e) {
         return getDefaultBlockStateForStateId(id);
      }
   }

   private static BlockState getDefaultBlockStateForStateId(int id) {
      try {
         return Block.stateById(id).getBlock().defaultBlockState();
      } catch (Exception e) {
         return Blocks.AIR.defaultBlockState();
      }
   }

   public static void glTimerPre() {
      GL11.glFinish();
      glTimerPreTime = System.nanoTime();
   }

   public static int glTimerResult() {
      GL11.glFinish();
      return (int)(System.nanoTime() - glTimerPreTime);
   }

   public static void timerPre() {
      cpuTimerPreTime = System.nanoTime();
   }

   public static int timerResult() {
      return (int)(System.nanoTime() - cpuTimerPreTime);
   }

   public static double getMouseX(Minecraft mc, boolean raw) {
      return raw ? mc.mouseHandler.xpos() : mc.mouseHandler.xpos() * mc.getWindow().getWidth() / mc.getWindow().getScreenWidth();
   }

   public static double getMouseY(Minecraft mc, boolean raw) {
      return raw ? mc.mouseHandler.ypos() : mc.mouseHandler.ypos() * mc.getWindow().getHeight() / mc.getWindow().getScreenHeight();
   }

   public static void minecraftOrtho(Minecraft mc, boolean raw) {
      Window mainwindow = mc.getWindow();
      int width = raw ? mc.getWindow().getScreenWidth() : mainwindow.getWidth();
      int height = raw ? mc.getWindow().getScreenHeight() : mainwindow.getHeight();
      Matrix4f ortho = new Matrix4f()
         .setOrtho(0.0F, (float)(width / mainwindow.getGuiScale()), (float)(height / mainwindow.getGuiScale()), 0.0F, 1000.0F, 21000.0F);
      RenderSystem.setProjectionMatrix(ortho, VertexSorting.ORTHOGRAPHIC_Z);
   }

   public static void clearHeightsData1024(long[] data) {
      System.arraycopy(ZERO_LONG_1024, 0, data, 0, 1024);
   }

   public static <T extends Comparable<? super T>> void addToListOfSmallest(int maxSize, List<T> list, T element) {
      int currentSize = list.size();
      if (currentSize != maxSize || list.get(currentSize - 1).compareTo(element) > 0) {
         int iterLimit = currentSize == maxSize ? maxSize : currentSize + 1;

         for (int i = 0; i < iterLimit; i++) {
            if (i == currentSize || element.compareTo(list.get(i)) < 0) {
               list.add(i, element);
               if (currentSize == maxSize) {
                  list.remove(currentSize);
               }
               break;
            }
         }
      }
   }

   public static Path convertToOutdated(Path path, int attempts) throws IOException {
      if (path.getFileName().toString().endsWith(".outdated")) {
         return path;
      }

      Path outdatedPath = path.resolveSibling(path.getFileName().toString() + ".outdated");
      if (Files.exists(path)) {
         convertToOutdated(path, outdatedPath, attempts);
      }

      return outdatedPath;
   }

   private static void convertToOutdated(Path path, Path outdatedPath, int attempts) throws IOException {
      attempts--;

      try {
         Files.move(path, outdatedPath, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
         if (attempts <= 0) {
            throw e;
         }

         WorldMap.LOGGER.info("Failed to convert file to outdated! Retrying... " + attempts);

         try {
            Thread.sleep(50L);
         } catch (InterruptedException var5) {
         }

         convertToOutdated(path, outdatedPath, attempts);
      }
   }

   public static void drawNormalText(PoseStack matrices, String name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.NORMAL, 0, 15728880);
   }

   public static void drawPiercingText(PoseStack matrices, String name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.SEE_THROUGH, 0, 15728880);
   }

   public static void drawPiercingText(PoseStack matrices, Component name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      Minecraft.getInstance().font.drawInBatch(name, x, y, color, shadow, matrices.last().pose(), renderTypeBuffer, DisplayMode.SEE_THROUGH, 0, 15728880);
   }

   public static void drawCenteredPiercingText(PoseStack matrices, String name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      drawPiercingText(matrices, name, x - Minecraft.getInstance().font.width(name) / 2, y, color, shadow, renderTypeBuffer);
   }

   public static void drawCenteredPiercingText(PoseStack matrices, Component name, float x, float y, int color, boolean shadow, BufferSource renderTypeBuffer) {
      drawPiercingText(matrices, name, x - Minecraft.getInstance().font.width(name) / 2, y, color, shadow, renderTypeBuffer);
   }

   public static boolean hasItem(Player player, Item item) {
      return hasItem(player.getInventory().offhand, -1, item)
         || hasItem(player.getInventory().armor, -1, item)
         || hasItem(player.getInventory().items, 9, item);
   }

   public static boolean hasItem(NonNullList<ItemStack> inventory, int limit, Item item) {
      for (int i = 0; i < inventory.size() && (limit == -1 || i < limit); i++) {
         if (inventory.get(i) != null && ((ItemStack)inventory.get(i)).getItem() == item) {
            return true;
         }
      }

      return false;
   }

   public static int getTeamColour(Entity e) {
      Integer teamColour = null;
      Team team = e.getTeam();
      if (team != null) {
         teamColour = team.getColor().getColor();
      }

      return teamColour == null ? -1 : teamColour;
   }

   public static boolean hasEffect(Player player, Holder<MobEffect> effect) {
      return effect != null && player != null && player.hasEffect(effect);
   }

   public static boolean hasEffect(Holder<MobEffect> effect) {
      return hasEffect(Minecraft.getInstance().player, effect);
   }
}
