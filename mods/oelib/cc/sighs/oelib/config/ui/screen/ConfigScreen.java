package cc.sighs.oelib.config.ui.screen;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.ClientConfigManager;
import cc.sighs.oelib.config.ConfigCodec;
import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.ServerConfigManager;
import cc.sighs.oelib.config.model.ConfigSide;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import cc.sighs.oelib.config.model.ConfigValueMeta;
import cc.sighs.oelib.config.net.ConfigUpdateRequestPacket;
import cc.sighs.oelib.config.ui.entries.AbstractConfigEntry;
import cc.sighs.oelib.config.ui.entries.CategoryTextEntry;
import cc.sighs.oelib.config.ui.entries.EmptyEntry;
import cc.sighs.oelib.config.ui.entries.FieldEntry;
import cc.sighs.oelib.config.ui.entries.ListEntry;
import cc.sighs.oelib.config.ui.entries.MapEntry;
import cc.sighs.oelib.config.ui.scissor.Rectangle;
import cc.sighs.oelib.config.ui.scissor.ScissorsHandler;
import cc.sighs.oelib.config.ui.widget.DynamicEntryListWidget;
import cc.sighs.oelib.config.util.ConfigGuiUtil;
import cc.sighs.oelib.config.util.ConfigSerializationUtil;
import cc.sighs.oelib.config.util.GsonUtil;
import cc.sighs.oelib.network.api.NetworkManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.Error;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ConfigScreen extends Screen {
   public final ResourceLocation configId;
   private final ConfigUnit<Object> unit;
   public final ConfigCodec<Object> codec;
   private final List<ConfigValueMeta> fields;
   private final List<Runnable> applyActions = new ArrayList<>();
   private final Map<String, Component> errors = new HashMap<>();
   private final int contentTop = 36;
   private final int rowHeight = 24;
   private final ConfigScreen.Scroller refScroller = new ConfigScreen.Scroller();
   private final ConfigScreen.Scroller sideSlider = new ConfigScreen.Scroller();
   private final List<ConfigScreen.ConfigCtx> contexts = new ArrayList<>();
   private final Map<AbstractConfigEntry<?>, JsonObject> entryDefaults = new HashMap<>();
   private final JsonObject working;
   private boolean dirty;
   private String searchText = "";
   private boolean sidebarExpanded = true;
   private List<ConfigScreen.FieldRef> references = List.of();
   private Button saveButton;
   private Button cancelButton;
   private Component lastTooltip;
   private int mouseXLast = -1;
   private int mouseYLast = -1;
   private DynamicEntryListWidget listWidget;
   private EditBox searchBox;
   private int sideExpandLimit = 120;
   private List<ResourceLocation> modConfigs = new ArrayList<>();

   public ConfigScreen(Screen parent, String modid) {
      super(Component.translatable("config." + modid + ".title"));
      Set<ResourceLocation> ids = new HashSet<>();
      ids.addAll(ClientConfigManager.all().keySet());
      ids.addAll(ServerConfigManager.all().keySet());
      ResourceLocation chosen = null;

      for (ResourceLocation id : ids) {
         if (id.getNamespace().equals(modid)) {
            chosen = id;
            break;
         }
      }

      if (chosen == null && !ids.isEmpty()) {
         chosen = ids.iterator().next();
      }

      if (chosen == null) {
         this.configId = ResourceLocation.fromNamespaceAndPath(modid, "none");
         this.unit = null;
         this.codec = null;
         this.fields = List.of();
         this.working = new JsonObject();
      } else {
         Optional<ConfigUnit<?>> opt = ConfigManager.get(chosen);
         ConfigUnit<Object> cast = opt.map(ConfigGuiUtil::castUnit).orElse(null);
         this.configId = chosen;
         this.unit = cast;
         this.codec = cast.codec();
         this.fields = this.codec.fields();
         this.unit.reload();
         this.working = ConfigGuiUtil.toJson(this.unit.get(), this);
      }
   }

   private int getSideSliderPosition() {
      return 14 + this.sideSlider.currentInt();
   }

   private int sidebarWidth() {
      return this.getSideSliderPosition();
   }

   private int sidebarContentWidth() {
      return this.sidebarWidth() - 14;
   }

   protected void init() {
      super.init();
      this.clearWidgets();
      this.applyActions.clear();
      this.errors.clear();
      this.lastTooltip = null;
      if (this.unit != null && this.codec != null) {
         int initialScroll = this.listWidget != null ? this.listWidget.getScrollOffset() : 0;
         this.contexts.clear();
         int sidebar = this.sidebarWidth();
         int labelWidth = 160;
         int left = sidebar + 16;
         int controlWidth = Math.min(220, this.width - left - 40 - labelWidth);
         int listControlWidth = controlWidth;
         int fieldControlWidth = Math.min(200, this.width - left - 60 - labelWidth);
         int y = 36;
         this.searchBox = new EditBox(Minecraft.getInstance().font, left, y, this.width - left - 20, 20, Component.empty());
         this.searchBox.setSuggestion(Component.translatable("config.oelib.search").getString());
         this.searchBox.setValue(this.searchText);
         this.searchBox.setResponder(s -> {
            this.searchText = s;
            this.searchBox.setSuggestion(s.isEmpty() ? Component.translatable("config.oelib.search").getString() : "");
            this.init();
         });
         this.searchBox.setFocused(true);
         this.setInitialFocus(this.searchBox);
         this.addRenderableWidget(this.searchBox);
         y += 24;
         this.listWidget = new DynamicEntryListWidget(Minecraft.getInstance(), this.width - left - 12, this.height, y, this.height - 32);
         this.listWidget.setLeftPos(left);
         Set<ResourceLocation> modsConfigsSet = new HashSet<>();
         modsConfigsSet.addAll(ClientConfigManager.all().keySet());
         modsConfigsSet.addAll(ServerConfigManager.all().keySet());
         this.modConfigs = modsConfigsSet.stream()
            .filter(id -> id.getNamespace().equals(this.configId.getNamespace()))
            .sorted(Comparator.comparing(ResourceLocation::getPath))
            .toList();
         List<ConfigScreen.FieldRef> refs = new ArrayList<>();

         for (ResourceLocation id : this.modConfigs) {
            refs.add(new ConfigScreen.FieldRef(null, Component.translatable("config." + id.getNamespace() + "." + id.getPath() + ".title"), 0));
         }

         this.entryDefaults.clear();
         List<AbstractConfigEntry<?>> items = this.listWidget.children();
         items.clear();
         items.add(new EmptyEntry(5));
         String searchLower = this.searchText.toLowerCase(Locale.ROOT);

         for (ResourceLocation id : this.modConfigs) {
            Optional<ConfigUnit<?>> optUnit = ConfigManager.get(id);
            if (!optUnit.isEmpty()) {
               ConfigUnit<Object> u = ConfigGuiUtil.castUnit(optUnit.get());
               u.reload();
               ConfigCodec<Object> c = u.codec();
               JsonObject workingJson = ConfigGuiUtil.encodeToJsonObject(c.codec(), u.get());
               Object defaultObj = c.codec().parse(JsonOps.INSTANCE, new JsonObject()).result().orElse(null);
               JsonObject defaultsJson = defaultObj != null ? ConfigGuiUtil.encodeToJsonObject(c.codec(), defaultObj) : new JsonObject();
               List<ConfigValueMeta> flds = c.fields();
               this.contexts.add(new ConfigScreen.ConfigCtx(id, u, c, flds, workingJson, defaultsJson));
               items.add(new CategoryTextEntry(Component.translatable("config." + id.getNamespace() + "." + id.getPath() + ".title"), Component.empty()));
               items.add(new EmptyEntry(5));

               for (ConfigValueMeta meta : flds) {
                  if (!meta.hidden()) {
                     if (!this.searchText.isEmpty()) {
                        String display = meta.translationKey().map(k -> Component.translatable(k).getString()).orElse(meta.key());
                        if (!display.toLowerCase(Locale.ROOT).contains(searchLower)) {
                           continue;
                        }
                     }

                     MutableComponent label = meta.translationKey().<MutableComponent>map(Component::translatable).orElse(Component.literal(meta.key()));
                     JsonElement v = ConfigGuiUtil.getPath(workingJson, meta.key());
                     if (v != null && v.isJsonArray()) {
                        ListEntry le = new ListEntry(
                           meta.key(), label, workingJson, listControlWidth, 24, meta.tooltip().<Component>map(Component::translatable).orElse(null)
                        );
                        items.add(le);
                        this.entryDefaults.put(le, defaultsJson);
                     } else if (v != null && v.isJsonObject()) {
                        MapEntry me = new MapEntry(
                           meta.key(), label, workingJson, listControlWidth, 24, meta.tooltip().<Component>map(Component::translatable).orElse(null)
                        );
                        items.add(me);
                        this.entryDefaults.put(me, defaultsJson);
                     } else {
                        FieldEntry fe = new FieldEntry(meta, workingJson, labelWidth, fieldControlWidth, 24);
                        items.add(fe);
                        this.entryDefaults.put(fe, defaultsJson);
                     }
                  }
               }
            }
         }

         this.references = refs;
         int longest = 0;

         for (ConfigScreen.FieldRef r : this.references) {
            int w = this.font.width(Component.literal("- ").append(r.label()).getVisualOrderText());
            if (w > longest) {
               longest = w;
            }
         }

         this.sideExpandLimit = Math.min(Math.max(80, longest + 16), this.width / 4);
         this.sideSlider.setMaxScroll(this.sideExpandLimit - 14);
         this.sideSlider.offset(this.sidebarExpanded ? this.sideExpandLimit - 14 : -this.sideExpandLimit);
         int attachY = y;

         for (AbstractConfigEntry<?> entry : items) {
            JsonObject def = this.entryDefaults.getOrDefault(entry, new JsonObject());
            if (entry instanceof FieldEntry fe) {
               fe.attach(this, left, attachY, def);
            } else if (entry instanceof MapEntry me) {
               me.attach(this, left, attachY, def);
            } else if (entry instanceof ListEntry le) {
               le.attach(this, left, attachY, def);
            }

            attachY += entry.getItemHeight();
         }

         if (initialScroll > 0 && this.listWidget != null) {
            this.listWidget.scrollTo(initialScroll, false);
         }

         if (!this.references.isEmpty()) {
            int itemHeight = 9 + 3;
            int available = this.height - 32 - 8;
            int total = this.references.size() * itemHeight;
            this.refScroller.setMaxScroll(Math.max(0, total - available));
         } else {
            this.refScroller.setMaxScroll(0);
         }

         int btnY = this.height - 28;
         this.saveButton = (Button)this.addRenderableWidget(
            Button.builder(Component.translatable("config.oelib.done"), b -> this.onSave()).bounds(this.width / 2 + 4, btnY, 120, 20).build()
         );
         this.cancelButton = (Button)this.addRenderableWidget(
            Button.builder(CommonComponents.GUI_CANCEL, b -> this.onClose()).bounds(this.width / 2 - 124, btnY, 120, 20).build()
         );
      }
   }

   private void onSave() {
      this.applyEdits();
      this.syncAndSaveConfigs();
      this.dirty = false;
      Minecraft.getInstance().setScreen(null);
   }

   private void applyEdits() {
      this.applyActions.forEach(Runnable::run);
      this.applyActions.clear();
   }

   public void markDirty() {
      this.dirty = true;
   }

   public void onClose() {
      if (!this.dirty) {
         super.onClose();
      } else {
         Minecraft.getInstance().setScreen(new ConfirmScreen(confirm -> {
            if (confirm) {
               this.applyEdits();
               this.syncAndSaveConfigs();
            }

            Minecraft.getInstance().setScreen(null);
         }, Component.translatable("config.oelib.unsaved.title"), Component.translatable("config.oelib.unsaved.message")));
      }
   }

   private void syncAndSaveConfigs() {
      for (ConfigScreen.ConfigCtx ctx : this.contexts) {
         for (Entry<String, JsonElement> rootEntry : ctx.working.entrySet()) {
            JsonElement v = rootEntry.getValue();
            if (v.isJsonObject()) {
               GsonUtil.removeBlankStringValues(v.getAsJsonObject());
            } else if (v.isJsonArray()) {
               GsonUtil.removeBlankStringElements(v.getAsJsonArray());
            }
         }

         DataResult<Object> parseResult = ctx.codec.codec().parse(JsonOps.INSTANCE, ctx.working);
         if (parseResult.error().isPresent()) {
            OELib.LOGGER.error("Failed to parse edited config {}: {}", ctx.id, ((Error)parseResult.error().get()).message());
         } else {
            parseResult.result().ifPresent(value -> {
               ConfigSide side = ctx.codec.meta().side();
               if (side == ConfigSide.CLIENT) {
                  ctx.unit.setValue(value);

                  try {
                     ctx.unit.save();
                  } catch (Throwable t) {
                     OELib.LOGGER.error("Save failed for client config {}: {}", new Object[]{ctx.id, t.getMessage(), t});
                  }
               } else if (side == ConfigSide.SERVER) {
                  ctx.unit.setValue(value);

                  try {
                     ctx.unit.save();
                  } catch (Throwable t) {
                     OELib.LOGGER.error("Save failed for server config {}: {}", new Object[]{ctx.id, t.getMessage(), t});
                  }

                  if (Minecraft.getInstance().getConnection() != null) {
                     ConfigStorageFormat format = ctx.codec.meta().format();
                     Optional<String> encoded = ConfigSerializationUtil.encodeToString(value, format, ctx.codec.codec(), ctx.codec.fields());
                     if (encoded.isEmpty()) {
                        OELib.LOGGER.error("Failed to encode server config {} for update request", ctx.id);
                        return;
                     }

                     NetworkManager.sendToServer(new ConfigUpdateRequestPacket(ctx.id, encoded.get(), format, true));
                  }
               }
            });
         }
      }
   }

   private void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      if (this.minecraft.level == null) {
         this.renderPanorama(guiGraphics, partialTick);
      }

      this.renderBlurredBackground(partialTick);
      guiGraphics.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
   }

   private void renderNoneBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      for (Renderable renderable : this.renderables) {
         renderable.render(guiGraphics, mouseX, mouseY, partialTick);
      }
   }

   public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
      if (this.needsRefreshFromUnits()) {
         this.init();
      }

      this.lastTooltip = null;
      this.mouseXLast = -1;
      this.mouseYLast = -1;
      this.refScroller.update(partialTick);
      this.sideSlider.update(partialTick);
      this.renderBg(gui, mouseX, mouseY, partialTick);
      ScissorsHandler.INSTANCE.clearScissors();
      int sidebar = this.sidebarWidth();
      int contentLeft = sidebar;
      int sliderX = sidebar - 14;
      gui.drawCenteredString(this.font, this.title, this.width / 2, 12, -1);
      int left = sidebar + 16;
      if (this.searchBox != null) {
         this.searchBox.setX(left);
         this.searchBox.setWidth(this.width - left - 20);
         if (this.listWidget != null) {
            int searchY = this.listWidget.top - this.listWidget.getScrollOffset() - 24;
            this.searchBox.setY(searchY);
         }
      }

      this.listWidget.setLeftPos(left);
      this.listWidget.width = this.width - left - 12;
      int arrowY = this.height / 2 - 9 / 2;
      String arrow = this.sidebarExpanded ? "<" : ">";
      gui.drawString(this.font, arrow, sliderX + 7 - this.font.width(arrow) / 2, arrowY, -1);
      if (this.sidebarExpanded) {
         Rectangle sideRect = new Rectangle(0, 0, this.sidebarContentWidth(), this.height);
         ScissorsHandler.INSTANCE.scissor(sideRect);
         int refX = 4;
         int refAreaTop = 8;
         int refAreaBottom = this.height - 32;
         int refWidth = this.sidebarContentWidth() - 8;
         int itemHeight = 9 + 6;
         int scroll = this.refScroller.currentInt();
         int refY = refAreaTop - scroll;

         for (ConfigScreen.FieldRef ref : this.references) {
            if (refY + itemHeight >= refAreaTop && refY <= refAreaBottom) {
               int color = -1;
               if (mouseX >= refX && mouseX <= refX + refWidth && mouseY >= refY && mouseY <= refY + itemHeight) {
                  color = -11393;
               }

               gui.drawString(this.font, ref.label().getVisualOrderText(), refX + 2, refY, color);
            }

            refY += itemHeight;
         }

         if (this.refScroller.hasScroll()) {
            int barAreaHeight = refAreaBottom - refAreaTop;
            if (barAreaHeight > 0) {
               int total = this.references.size() * itemHeight;
               int barHeight = Math.max(10, barAreaHeight * barAreaHeight / Math.max(barAreaHeight, total));
               int maxScroll = Math.max(1, this.refScroller.max);
               int barY = refAreaTop + (int)Math.round((double)scroll * (barAreaHeight - barHeight) / maxScroll);
               int barX1 = sliderX - 3;
               int barX2 = sliderX - 1;
               gui.fill(barX1, refAreaTop, barX2, refAreaBottom, 1711276032);
               gui.fill(barX1, barY, barX2, barY + barHeight, -855638017);
            }
         }

         ScissorsHandler.INSTANCE.removeLastScissor();
      }

      int headerBottom = 36;
      int bottomBarTop = this.height - 32;
      Rectangle contentRect = new Rectangle(0, headerBottom, this.width, bottomBarTop - headerBottom);
      ScissorsHandler.INSTANCE.scissor(contentRect);
      this.renderNoneBg(gui, mouseX, mouseY, partialTick);
      if (this.listWidget != null) {
         this.listWidget.render(gui, mouseX, mouseY, partialTick);
      }

      ScissorsHandler.INSTANCE.removeLastScissor();
      gui.blit(DynamicEntryListWidget.VERTICAL_HEADER_SEPARATOR, sliderX - 1, 0, 0.0F, 0.0F, 1, this.height, 2, 32);
      gui.fill(sliderX, headerBottom - 1, this.width, headerBottom, -2130706433);
      gui.fill(sliderX, bottomBarTop - 1, this.width, bottomBarTop, -2130706433);
      if (this.saveButton != null) {
         this.saveButton.render(gui, mouseX, mouseY, partialTick);
      }

      if (this.cancelButton != null) {
         this.cancelButton.render(gui, mouseX, mouseY, partialTick);
      }

      if (!this.errors.isEmpty()) {
         for (Entry<String, Component> entry : this.errors.entrySet()) {
            gui.drawString(this.font, entry.getValue(), contentLeft + 16, this.height - 40, -49088);
         }
      }

      if (this.lastTooltip != null && this.mouseXLast >= 0) {
         gui.renderTooltip(this.font, this.lastTooltip, this.mouseXLast, this.mouseYLast);
      }
   }

   public void setHoverTooltip(Component tooltip, int mouseX, int mouseY) {
      this.lastTooltip = tooltip;
      this.mouseXLast = mouseX;
      this.mouseYLast = mouseY;
   }

   public void mouseMoved(double mouseX, double mouseY) {
      this.mouseXLast = (int)mouseX;
      this.mouseYLast = (int)mouseY;
      super.mouseMoved(mouseX, mouseY);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      int bottomBarTop = this.height - 32;
      if (mouseY >= bottomBarTop) {
         boolean handled = false;
         if (this.saveButton != null) {
            handled = this.saveButton.mouseClicked(mouseX, mouseY, button);
         }

         if (!handled && this.cancelButton != null) {
            handled = this.cancelButton.mouseClicked(mouseX, mouseY, button);
         }

         return true;
      } else {
         int sidebar = this.sidebarWidth();
         int sliderX = sidebar - 14;
         if (button == 0) {
            if (mouseX >= sliderX && mouseX <= sidebar && mouseY >= 0.0 && mouseY <= this.height) {
               this.sidebarExpanded = !this.sidebarExpanded;
               this.sideSlider.offset(this.sidebarExpanded ? this.sideExpandLimit - 14 : -(this.sideExpandLimit - 14));
               return true;
            }

            if (this.sidebarExpanded && mouseX >= 0.0 && mouseX <= sliderX && mouseY >= 0.0 && mouseY <= this.height) {
               int refX = 4;
               int refY = 8 - this.refScroller.currentInt();
               int h = 9 + 6;

               for (ResourceLocation modConfig : this.modConfigs) {
                  if (refY + h > this.height - 32) {
                     break;
                  }

                  if (mouseX >= refX && mouseX <= sliderX && mouseY >= refY && mouseY <= refY + h) {
                     ResourceLocation id = modConfig;
                     int offset = 0;

                     for (AbstractConfigEntry<?> entry : this.listWidget.children()) {
                        if (entry instanceof CategoryTextEntry cte
                           && cte.getFieldName()
                              .getString()
                              .equals(Component.translatable("config." + id.getNamespace() + "." + id.getPath() + ".title").getString())) {
                           this.listWidget.scrollTo(offset, true);
                           break;
                        }

                        offset += entry.getItemHeight();
                     }

                     return true;
                  }

                  refY += h;
               }
            }

            if (this.listWidget != null && mouseX >= sliderX && mouseX <= this.width && mouseY >= 36.0 && mouseY <= this.height - 32) {
               int yIt = this.listWidget.top - this.listWidget.getScrollOffset();

               for (AbstractConfigEntry<?> entry : this.listWidget.children()) {
                  int headerH = 20;
                  if (entry instanceof MapEntry me) {
                     if (mouseY >= yIt && mouseY <= yIt + headerH && me.toggleIfHit(mouseX, mouseY)) {
                        return true;
                     }

                     yIt += me.getItemHeight();
                  } else if (entry instanceof ListEntry le) {
                     if (mouseY >= yIt && mouseY <= yIt + headerH && le.toggleIfHit(mouseX, mouseY)) {
                        return true;
                     }

                     yIt += le.getItemHeight();
                  } else {
                     yIt += entry.getItemHeight();
                  }
               }
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
      int sidebar = this.sidebarWidth();
      int sliderX = sidebar - 14;
      if (mouseX >= 0.0 && mouseX <= sliderX && mouseY >= 0.0 && mouseY <= this.height && amountY != 0.0) {
         int itemHeight = 9 + 3;
         this.refScroller.offset(-amountY * itemHeight);
         return true;
      } else if (this.listWidget != null && mouseX >= sliderX && mouseX <= this.width && mouseY >= 36.0 && mouseY <= this.height - 32 && amountY != 0.0) {
         this.listWidget.offset(-amountY * 12.0);
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, amountX, amountY);
      }
   }

   private boolean needsRefreshFromUnits() {
      if (this.dirty) {
         return false;
      }

      try {
         for (ConfigScreen.ConfigCtx ctx : this.contexts) {
            JsonObject fresh = ConfigGuiUtil.encodeToJsonObject(ctx.codec.codec(), ctx.unit.get());
            if (!Objects.equals(fresh.toString(), ctx.working.toString())) {
               return true;
            }
         }
      } catch (Throwable var4) {
      }

      return false;
   }

   private record ConfigCtx(
      ResourceLocation id, ConfigUnit<Object> unit, ConfigCodec<Object> codec, List<ConfigValueMeta> fields, JsonObject working, JsonObject defaults
   ) {
   }

   private record FieldRef(ConfigValueMeta meta, Component label, int topY) {
   }

   private static final class Scroller {
      private double value;
      private double target;
      private int max;

      void setMaxScroll(int max) {
         this.max = Math.max(0, max);
         if (this.target > this.max) {
            this.target = this.max;
         }

         if (this.value > this.max) {
            this.value = this.max;
         }

         if (this.target < 0.0) {
            this.target = 0.0;
         }

         if (this.value < 0.0) {
            this.value = 0.0;
         }
      }

      void offset(double delta) {
         this.target = this.clamp(this.target + delta);
      }

      void update(float delta) {
         double diff = this.target - this.value;
         if (Math.abs(diff) < 0.5) {
            this.value = this.target;
         } else {
            double speed = Math.min(1.0, delta * 15.0);
            this.value += diff * speed;
         }
      }

      int currentInt() {
         return (int)Math.round(this.value);
      }

      boolean hasScroll() {
         return this.max > 0;
      }

      private double clamp(double v) {
         if (v < 0.0) {
            return 0.0;
         } else {
            return v > this.max ? this.max : v;
         }
      }
   }
}
