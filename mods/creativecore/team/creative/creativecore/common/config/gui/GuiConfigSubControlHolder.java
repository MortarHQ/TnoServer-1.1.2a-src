package team.creative.creativecore.common.config.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.key.ConfigKeyType;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.flow.GuiFlow;

public class GuiConfigSubControlHolder extends GuiConfigSubControl {
   public ICreativeConfigHolder holder;
   public Object value;
   private final Runnable updateListener;
   private final Side side;

   public GuiConfigSubControlHolder(String name, ICreativeConfigHolder holder, Object value, Side side, @Nullable Runnable updateListener) {
      super(name);
      this.setExpandable();
      this.holder = holder;
      this.value = value;
      this.side = side;
      this.updateListener = updateListener;
      this.flow = GuiFlow.STACK_Y;
   }

   public void load(ICreativeConfigHolder holder, Object value) {
      this.holder = holder;
      this.value = value;
   }

   public void createControls() {
      for (ConfigKey key : this.holder.fields()) {
         if (!key.requiresRestart && !key.isFolder() && !key.hideFromGUI) {
            String path = "config." + String.join(".", this.holder.path());
            if (!path.endsWith(".")) {
               path = path + ".";
            }

            String caption = translateOrDefault(path + key.name + ".name", key.name);
            String comment = path + key.name + ".comment";
            GuiConfigControl config = new GuiConfigControl((ConfigKeyType)key, this.side, caption, comment) {
               @Override
               public void updateButton() {
                  super.updateButton();
                  if (GuiConfigSubControlHolder.this.updateListener != null) {
                     GuiConfigSubControlHolder.this.updateListener.run();
                  }
               }
            };
            this.add(config);
            config.init(null);
         }
      }
   }

   public void save() {
      JsonObject json = new JsonObject();

      for (GuiControl control : this.controls) {
         if (control instanceof GuiConfigControl c) {
            JsonElement element = c.save();
            if (element != null) {
               json.add(c.field.name, element);
            }
         }
      }

      this.holder.load(this.provider(), false, true, json, this.side);
   }
}
