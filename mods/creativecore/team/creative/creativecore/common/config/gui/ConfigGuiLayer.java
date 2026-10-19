package team.creative.creativecore.common.config.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.key.ConfigKeyType;
import team.creative.creativecore.common.config.sync.ConfigurationChangePacket;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiLeftRightBox;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.control.parent.GuiScrollY;
import team.creative.creativecore.common.gui.control.parent.GuiTable;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.dialog.DialogGuiLayer;
import team.creative.creativecore.common.gui.dialog.GuiDialogHandler;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.util.mc.JsonUtils;
import team.creative.creativecore.common.util.text.TextBuilder;

public class ConfigGuiLayer extends GuiLayer {
   public JsonObject ROOT = new JsonObject();
   public Side side;
   public final ICreativeConfigHolder rootHolder;
   public ICreativeConfigHolder holder;
   public int nextAction;
   public boolean force;

   public ConfigGuiLayer(ICreativeConfigHolder holder, Side side) {
      super("config", 420, 234);
      this.flow = GuiFlow.STACK_Y;
      this.rootHolder = holder;
      this.holder = holder;
      this.side = side;
   }

   @Override
   public void create() {
      this.loadHolder(this.holder);
   }

   public void savePage() {
      GuiTable table = this.get("box.table");
      JsonObject parent = null;

      for (GuiControl control : table) {
         if (control instanceof GuiConfigControl c) {
            JsonElement element = c.save();
            if (element != null) {
               if (parent == null) {
                  parent = JsonUtils.get(this.ROOT, this.holder.path());
               }

               parent.add(c.field.name, element);
            }
         }
      }
   }

   public void loadHolder(ICreativeConfigHolder holder) {
      if (!this.isEmpty()) {
         this.savePage();
         this.clear();
      }

      GuiLeftRightBox upperBox = new GuiLeftRightBox();
      upperBox.addLeft(new GuiLabel("path").setTitle(Component.literal("/" + String.join("/", holder.path()))));
      upperBox.addRight(new GuiButton("back", x -> this.loadHolder(holder.parent())).setTranslate("gui.back").setEnabled(holder != this.rootHolder));
      this.holder = holder;
      this.add(upperBox);
      GuiScrollY box = new GuiScrollY("box").setDim(100, 100).setExpandable();
      this.add(box);
      GuiTable table = new GuiTable("table").setExpandable();
      box.add(table);
      JsonObject json = JsonUtils.tryGet(this.ROOT, holder.path());

      for (ConfigKey key : holder.fields()) {
         if (!key.requiresRestart && !key.hideFromGUI) {
            String path = "config." + String.join(".", holder.path());
            if (!path.endsWith(".")) {
               path = path + ".";
            }

            String caption = translateOrDefault(path + key.name + ".name", key.name);
            String comment = path + key.name + ".comment";
            if (key.isFolder()) {
               if (!key.holder().isEmpty(this.side)) {
                  GuiRow row = new GuiRow();
                  table.addRow(row);
                  GuiColumn col = new GuiColumn();
                  row.addColumn(col);
                  col.add(
                     new GuiButton(caption, x -> this.loadHolder(key.holder()))
                        .setTitle(Component.literal(caption))
                        .setTooltip(new TextBuilder().translateIfCan(comment).build())
                  );
               }
            } else if (key.is(this.side)) {
               GuiConfigControl control = new GuiConfigControl((ConfigKeyType)key, this.side, caption, comment);
               table.addRow(control);
               control.init(json != null ? json.get(key.name) : null);
            }
         }
      }

      GuiLeftRightBox lowerBox = new GuiLeftRightBox().addLeft(new GuiButton("cancel", x -> {
         this.nextAction = 0;
         this.closeTopLayer();
      }).setTitle(Component.translatable("gui.cancel")));
      if (this.side.isServer()) {
         lowerBox.addLeft(new GuiButton("client-config", x -> {
            this.nextAction = 1;
            this.closeTopLayer();
         }).setTitle(Component.translatable("gui.client-config")));
      }

      lowerBox.addRight(new GuiButton("save", x -> {
         this.nextAction = 0;
         this.savePage();
         this.sendUpdate();
         this.force = true;
         this.closeTopLayer();
      }).setTranslate("gui.save"));
      this.add(lowerBox);
      this.reinit();
   }

   public void sendUpdate() {
      if (this.side.isServer()) {
         this.getIntegratedParent().send(new ConfigurationChangePacket(this.rootHolder, this.ROOT));
      } else {
         this.rootHolder.load(this.provider(), false, true, JsonUtils.get(this.ROOT, this.rootHolder.path()), Side.CLIENT);
         CreativeCore.CONFIG_HANDLER.save(this.provider(), Side.CLIENT);
      }
   }

   @Override
   public void closeTopLayer() {
      this.savePage();
      if (!this.force && this.ROOT.size() != 0) {
         GuiDialogHandler.openDialog(this.getIntegratedParent(), "savechanges", (x, y) -> {
            if (y == DialogGuiLayer.DialogButton.YES) {
               this.savePage();
               this.sendUpdate();
            }

            if (y != DialogGuiLayer.DialogButton.CANCEL) {
               this.force = true;
               this.closeTopLayer();
            }
         }, DialogGuiLayer.DialogButton.YES, DialogGuiLayer.DialogButton.NO, DialogGuiLayer.DialogButton.CANCEL);
      } else if (this.nextAction == 0) {
         super.closeTopLayer();
      } else if (this.nextAction == 1) {
         CreativeCore.CONFIG_CLIENT_SYNC_OPEN.open(this.getPlayer());
      }
   }
}
