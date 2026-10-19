package dev.ftb.mods.ftbquests.quest;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ImageResourceConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.net.EditObjectMessage;
import dev.ftb.mods.ftbquests.util.ConfigQuestObject;
import dev.ftb.mods.ftbquests.util.NetUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public final class ChapterImage implements Movable {
   public static final String FTBQ_IMAGE = "<ftbq-image>";
   public static WeakReference<ChapterImage> clipboard = new WeakReference<>(null);
   public static StreamCodec<FriendlyByteBuf, ChapterImage> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ChapterImage>() {
      public ChapterImage decode(FriendlyByteBuf buf) {
         long chapterId = buf.readLong();
         ChapterImage img = new ChapterImage(ServerQuestFile.INSTANCE.getChapter(chapterId));
         img.readNetData(buf);
         return img;
      }

      public void encode(FriendlyByteBuf buf, ChapterImage chapterImage) {
         buf.writeLong(chapterImage.getChapter().id);
         chapterImage.writeNetData(buf);
      }
   };
   private Chapter chapter;
   private double x;
   private double y;
   private double width;
   private double height;
   private double rotation;
   private Icon image;
   private Color4I color;
   private int alpha;
   private final List<String> hover;
   private String click;
   private boolean editorsOnly;
   private boolean alignToCorner;
   private Quest dependency;
   private int order;

   public ChapterImage(Chapter c) {
      this.chapter = c;
      this.x = this.y = 0.0;
      this.width = 1.0;
      this.height = 1.0;
      this.rotation = 0.0;
      this.image = Color4I.empty();
      this.color = Color4I.WHITE;
      this.alpha = 255;
      this.hover = new ArrayList<>();
      this.click = "";
      this.editorsOnly = false;
      this.alignToCorner = false;
      this.dependency = null;
      this.order = 0;
   }

   public Icon getImage() {
      return this.image;
   }

   public ChapterImage setImage(Icon image) {
      this.image = image;
      return this;
   }

   public ChapterImage setPosition(double x, double y) {
      this.x = x;
      this.y = y;
      return this;
   }

   public static ChapterImage fromNet(Chapter parent, FriendlyByteBuf buf) {
      return (ChapterImage)Util.make(new ChapterImage(parent), img -> img.readNetData(buf));
   }

   public Color4I getColor() {
      return this.color;
   }

   public int getAlpha() {
      return this.alpha;
   }

   public int getOrder() {
      return this.order;
   }

   @Override
   public double getRotation() {
      return this.rotation;
   }

   @Override
   public boolean isAlignToCorner() {
      return this.alignToCorner;
   }

   public String getClick() {
      return this.click;
   }

   public void addHoverText(TooltipList list) {
      this.hover.forEach(x$0 -> list.translate(x$0, new Object[0]));
   }

   public CompoundTag writeData(CompoundTag nbt) {
      nbt.putDouble("x", this.x);
      nbt.putDouble("y", this.y);
      nbt.putDouble("width", this.width);
      nbt.putDouble("height", this.height);
      nbt.putDouble("rotation", this.rotation);
      nbt.putString("image", this.image.toString());
      if (!this.color.equals(Color4I.WHITE)) {
         nbt.putInt("color", this.color.rgb());
      }

      if (this.alpha != 255) {
         nbt.putInt("alpha", this.alpha);
      }

      if (this.order != 0) {
         nbt.putInt("order", this.order);
      }

      if (!this.hover.isEmpty()) {
         nbt.put("hover", (Tag)Util.make(new ListTag(), l -> this.hover.forEach(s -> l.add(StringTag.valueOf(s)))));
      }

      if (!this.click.isEmpty()) {
         nbt.putString("click", this.click);
      }

      if (this.editorsOnly) {
         nbt.putBoolean("dev", true);
      }

      if (this.alignToCorner) {
         nbt.putBoolean("corner", true);
      }

      if (this.dependency != null) {
         nbt.putString("dependency", this.dependency.getCodeString());
      }

      return nbt;
   }

   public void readData(CompoundTag nbt) {
      this.x = nbt.getDouble("x");
      this.y = nbt.getDouble("y");
      this.width = nbt.getDouble("width");
      this.height = nbt.getDouble("height");
      this.rotation = nbt.getDouble("rotation");
      this.setImage(Icon.getIcon(nbt.getString("image")));
      this.color = nbt.contains("color") ? Color4I.rgb(nbt.getInt("color")) : Color4I.WHITE;
      this.alpha = nbt.contains("alpha") ? nbt.getInt("alpha") : 255;
      this.order = nbt.getInt("order");
      this.hover.clear();
      ListTag hoverTag = nbt.getList("hover", 8);

      for (int i = 0; i < hoverTag.size(); i++) {
         this.hover.add(hoverTag.getString(i));
      }

      this.click = nbt.getString("click");
      this.editorsOnly = nbt.getBoolean("dev");
      this.alignToCorner = nbt.getBoolean("corner");
      this.dependency = nbt.contains("dependency") ? this.chapter.file.getQuest(this.chapter.file.getID(nbt.get("dependency"))) : null;
   }

   public void writeNetData(FriendlyByteBuf buffer) {
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.width);
      buffer.writeDouble(this.height);
      buffer.writeDouble(this.rotation);
      NetUtils.writeIcon(buffer, this.image);
      buffer.writeInt(this.color.rgb());
      buffer.writeInt(this.alpha);
      buffer.writeInt(this.order);
      NetUtils.writeStrings(buffer, this.hover);
      buffer.writeUtf(this.click, 32767);
      buffer.writeBoolean(this.editorsOnly);
      buffer.writeBoolean(this.alignToCorner);
      buffer.writeLong(this.dependency == null ? 0L : this.dependency.id);
   }

   public void readNetData(FriendlyByteBuf buffer) {
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.width = buffer.readDouble();
      this.height = buffer.readDouble();
      this.rotation = buffer.readDouble();
      this.setImage(NetUtils.readIcon(buffer));
      this.color = Color4I.rgb(buffer.readInt());
      this.alpha = buffer.readInt();
      this.order = buffer.readInt();
      NetUtils.readStrings(buffer, this.hover);
      this.click = buffer.readUtf(32767);
      this.editorsOnly = buffer.readBoolean();
      this.alignToCorner = buffer.readBoolean();
      this.dependency = this.chapter.file.getQuest(buffer.readLong());
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      config.addDouble("x", this.x, v -> this.x = v, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      config.addDouble("y", this.y, v -> this.y = v, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      config.addDouble("width", this.width, v -> this.width = v, 1.0, 0.0, Double.POSITIVE_INFINITY);
      config.addDouble("height", this.height, v -> this.height = v, 1.0, 0.0, Double.POSITIVE_INFINITY);
      config.addDouble("rotation", this.rotation, v -> this.rotation = v, 0.0, -180.0, 180.0);
      config.add(
         "image",
         new ImageResourceConfig(),
         ImageResourceConfig.getResourceLocation(this.image),
         v -> this.setImage(Icon.getIcon(v)),
         ResourceLocation.withDefaultNamespace("textures/gui/presets/isles.png")
      );
      config.addColor("color", this.color, v -> this.color = v, Color4I.WHITE);
      config.addInt("order", this.order, v -> this.order = v, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      config.addInt("alpha", this.alpha, v -> this.alpha = v, 255, 0, 255);
      config.addList("hover", this.hover, new StringConfig(), "");
      config.addString("click", this.click, v -> this.click = v, "");
      config.addBool("dev", this.editorsOnly, v -> this.editorsOnly = v, false);
      config.addBool("corner", this.alignToCorner, v -> this.alignToCorner = v, false);
      Predicate<QuestObjectBase> depTypes = object -> object == null || object instanceof Quest;
      ((ConfigQuestObject)config.add("dependency", new ConfigQuestObject(depTypes), this.dependency, v -> this.dependency = v, null))
         .setNameKey("ftbquests.dependency");
   }

   @Override
   public long getMovableID() {
      return 0L;
   }

   @Override
   public Chapter getChapter() {
      return this.chapter;
   }

   @Override
   public double getX() {
      return this.x;
   }

   @Override
   public double getY() {
      return this.y;
   }

   @Override
   public double getWidth() {
      return this.width;
   }

   @Override
   public double getHeight() {
      return this.height;
   }

   @Override
   public String getShape() {
      return "square";
   }

   @Override
   public void initiateMoveClientSide(Chapter to, double _x, double _y) {
      this.x = _x;
      this.y = _y;
      if (to != this.chapter) {
         this.chapter.removeImage(this);
         NetworkManager.sendToServer(EditObjectMessage.forQuestObject(this.chapter));
         this.chapter = to;
         this.chapter.addImage(this);
      }

      NetworkManager.sendToServer(EditObjectMessage.forQuestObject(this.chapter));
   }

   @Override
   public void onMoved(double x, double y, long chapterId) {
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void drawMoved(GuiGraphics graphics) {
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      if (this.alignToCorner) {
         this.image.withColor(Color4I.WHITE.withAlpha(50)).draw(graphics, 0, 0, 1, 1);
      } else {
         poseStack.translate(0.5, 0.5, 0.0);
         poseStack.scale(0.5F, 0.5F, 1.0F);
         this.image.withColor(Color4I.WHITE.withAlpha(50)).draw(graphics, -1, -1, 2, 2);
      }

      poseStack.popPose();
   }

   @Override
   public void copyToClipboard() {
      clipboard = new WeakReference<>(this);
      Widget.setClipboardString("<ftbq-image>");
   }

   @Override
   public Component getTitle() {
      return Component.literal(this.image.toString());
   }

   public boolean isAspectRatioOff() {
      return !Mth.equal(this.image.aspectRatio(), this.width / this.height);
   }

   public void fixupAspectRatio(boolean adjustWidth) {
      if (this.isAspectRatioOff()) {
         if (adjustWidth) {
            this.width = this.height * this.image.aspectRatio();
         } else {
            this.height = this.width / this.image.aspectRatio();
         }

         NetworkManager.sendToServer(EditObjectMessage.forQuestObject(this.chapter));
      }
   }

   public ChapterImage copy(Chapter newChapter, double newX, double newY) {
      ChapterImage copy = new ChapterImage(newChapter);
      copy.readData(this.writeData(new CompoundTag()));
      copy.setPosition(newX, newY);
      return copy;
   }

   public boolean shouldShowImage(TeamData teamData) {
      return !this.editorsOnly && (this.dependency == null || teamData.isCompleted(this.dependency));
   }

   public static boolean isImageInClipboard() {
      return Widget.getClipboardString().equals("<ftbq-image>");
   }
}
