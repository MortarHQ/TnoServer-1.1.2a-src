package team.creative.creativecore.common.config.gui;

import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.control.collection.GuiComboBox;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.util.player.PlayerSelector;
import team.creative.creativecore.common.util.text.TextMapBuilder;

public class PlayerSelectorDialog extends GuiLayer {
   public GuiPlayerSelectorButton button;
   public GuiPlayerSelectorHandler handler;

   public PlayerSelectorDialog() {
      super("playerselector", 150, 150);
      this.registerEventChanged(event -> {
         if (event.control.is("type")) {
            this.reinit();
         } else {
            this.handler.onChanged(this, event);
         }
      });
   }

   @Override
   public void create() {
      PlayerSelector selector = this.button.get();
      this.handler = GuiPlayerSelectorHandler.get(selector);
      GuiComboBox<String> box = this.get("type");
      if (box != null) {
         this.handler = GuiPlayerSelectorHandler.REGISTRY.get(box.selected());
      }

      this.clear();
      box = new GuiComboBox<>("type", new TextMapBuilder<String>().addComponent(GuiPlayerSelectorHandler.REGISTRY.keys(), Component::literal));
      box.select(this.handler.getName());
      this.add(box);
      this.handler.createControls(this, selector);
      this.add(new GuiButton("Cancel", x -> this.closeTopLayer()));
      this.add(new GuiButton("Save", x -> {
         PlayerSelector parsed = this.handler.parseSelector(this);
         if (parsed != null) {
            this.button.set(parsed);
            this.closeTopLayer();
         }
      }));
   }
}
