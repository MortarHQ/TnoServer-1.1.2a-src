package dev.ftb.mods.ftbquests.quest;

import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ImageIcon;
import dev.ftb.mods.ftblibrary.math.PixelBuffer;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public final class QuestShape extends Icon {
   private static final Map<String, QuestShape> MAP = new LinkedHashMap<>();
   private static QuestShape defaultShape;
   public static NameMap<String> idMap;
   public static NameMap<String> idMapWithDefault;
   private final String id;
   private final ImageIcon background;
   private final ImageIcon outline;
   private final ImageIcon shape;
   private final boolean shouldDraw;
   private PixelBuffer shapePixels;

   public QuestShape(String id) {
      this.id = id;
      this.background = new ImageIcon(FTBQuestsAPI.rl("textures/shapes/" + this.id + "/background.png"));
      this.outline = new ImageIcon(FTBQuestsAPI.rl("textures/shapes/" + this.id + "/outline.png"));
      this.shape = new ImageIcon(FTBQuestsAPI.rl("textures/shapes/" + this.id + "/shape.png"));
      this.shouldDraw = !id.equals("none");
   }

   public static void reload(List<String> list) {
      MAP.clear();
      list.forEach(s -> MAP.put(s, new QuestShape(s)));
      defaultShape = MAP.values().iterator().next();
      idMap = NameMap.of(list.get(0), list).baseNameKey("ftbquests.quest.shape").create();
      list.add(0, "default");
      idMapWithDefault = NameMap.of(list.get(0), list).baseNameKey("ftbquests.quest.shape").create();
   }

   public static QuestShape get(String id) {
      return MAP.getOrDefault(id, defaultShape);
   }

   public String toString() {
      return "quest_shape:" + this.id;
   }

   @OnlyIn(Dist.CLIENT)
   public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
      this.background.draw(graphics, x, y, w, h);
      this.outline.draw(graphics, x, y, w, h);
   }

   public ImageIcon getBackground() {
      return this.background;
   }

   public ImageIcon getOutline() {
      return this.outline;
   }

   public ImageIcon getShape() {
      return this.shape;
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public boolean equals(Object o) {
      return o == this;
   }

   public PixelBuffer getShapePixels() {
      if (this.shapePixels == null) {
         try {
            ResourceLocation shapeLoc = FTBQuestsAPI.rl("textures/shapes/" + this.id + "/shape.png");
            Resource resource = (Resource)Minecraft.getInstance().getResourceManager().getResource(shapeLoc).get();

            try (InputStream stream = resource.open()) {
               this.shapePixels = PixelBuffer.from(stream);
            }
         } catch (Exception ex) {
            this.shapePixels = new PixelBuffer(1, 1);
            this.shapePixels.setRGB(0, 0, -1);
         }
      }

      return this.shapePixels;
   }

   public boolean shouldDraw() {
      return this.shouldDraw;
   }

   public static Map<String, QuestShape> map() {
      return Collections.unmodifiableMap(MAP);
   }
}
