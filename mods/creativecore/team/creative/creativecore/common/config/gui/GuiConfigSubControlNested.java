package team.creative.creativecore.common.config.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.key.ConfigKeyType;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.parent.GuiPanel;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiConfigSubControlNested extends GuiConfigSubControl {
   public ICreativeConfigHolder holder;
   public Object value;
   private final Runnable updateListener;
   private final Side side;
   private final GuiParent panel;

   public GuiConfigSubControlNested(
      String name, ICreativeConfigHolder holder, Object value, Side side, @Nullable Runnable updateListener, boolean invisiblePanel
   ) {
      super(name);
      this.setExpandable();
      this.panel = invisiblePanel ? new GuiParent(GuiFlow.STACK_Y) : new GuiPanel(GuiFlow.STACK_Y);
      this.add(this.panel);
      this.holder = holder;
      this.value = value;
      this.side = side;
      this.updateListener = updateListener;
      this.flow = GuiFlow.STACK_Y;
      this.createControls();
   }

   public void load(ICreativeConfigHolder holder, Object value) {
      this.holder = holder;
      this.value = value;
   }

   @Override
   public void addNameUnmodifieable(String name) {
      this.nameLabel = new GuiLabel("title").setTitle(Component.literal(name));
      this.panel.add(this.nameLabel);
   }

   @Override
   public void addNameTextfield(String name) {
      this.panel.add(this.nameField = new GuiTextfield("title", name).setDim(50, 8));
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   public void createControls() {
      for (ConfigKey key : this.holder.fields()) {
         if (!key.hideFromGUI) {
            String path = "config." + String.join(".", this.holder.path());
            if (!path.endsWith(".")) {
               path = path + ".";
            }

            String caption = translateOrDefault(path + key.name + ".name", key.name);
            String comment = path + key.name + ".comment";
            if (key.isFolder()) {
               GuiConfigSubControlNested config = new GuiConfigSubControlNested(
                  key.name, key.holder(), key.field().get(), this.side, this.updateListener, false
               );
               this.panel.add(config);
               config.addNameUnmodifieable(caption);
               config.createControls();
            } else {
               GuiConfigControl config = new GuiConfigControl((ConfigKeyType)key, this.side, caption, comment) {
                  @Override
                  public void updateButton() {
                     super.updateButton();
                     if (GuiConfigSubControlNested.this.updateListener != null) {
                        GuiConfigSubControlNested.this.updateListener.run();
                     }
                  }
               };
               this.panel.add(config);
               config.init(null);
            }
         }
      }
   }

   public JsonObject save() {
      JsonObject json = new JsonObject();

      for (GuiControl control : this.panel) {
         if (control instanceof GuiConfigControl c) {
            JsonElement element = c.save();
            if (element != null) {
               json.add(c.field.name, element);
            }
         } else if (control instanceof GuiConfigSubControlNested n) {
            json.add(n.name, n.save());
         }
      }

      this.holder.load(this.provider(), false, true, json, this.side);
      return this.holder.save(this.provider(), false, true, this.side);
   }
}
