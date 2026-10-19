package team.creative.creativecore.common.gui.dialog;

import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.flow.GuiFlow;

public class DialogGuiLayer extends GuiLayer {
   public DialogGuiLayer.DialogButton[] buttons;
   public BiConsumer<DialogGuiLayer, DialogGuiLayer.DialogButton> onClicked;
   public Component title;

   public DialogGuiLayer(
      String name, Component title, BiConsumer<DialogGuiLayer, DialogGuiLayer.DialogButton> onClicked, DialogGuiLayer.DialogButton... buttons
   ) {
      super(name);
      this.title = title;
      this.buttons = buttons;
      this.onClicked = onClicked;
      this.align = Align.CENTER;
      this.valign = VAlign.CENTER;
      this.flow = GuiFlow.STACK_Y;
   }

   @Override
   public void create() {
      this.add(new GuiLabel("text").setTitle(this.title));
      GuiParent hBox = new GuiParent(GuiFlow.STACK_X);

      for (DialogGuiLayer.DialogButton button : this.buttons) {
         hBox.add(new GuiButton(button.name(), x -> this.closeDialog(button)).setTitle(Component.translatable("dialog.button." + button.name())));
      }

      this.add(hBox);
   }

   public void closeDialog(DialogGuiLayer.DialogButton button) {
      this.closeTopLayer();
      this.onClicked.accept(this, button);
   }

   public enum DialogButton {
      OK,
      YES,
      NO,
      MAYBE,
      CANCEL,
      ABORT,
      CONFIRM;
   }
}
