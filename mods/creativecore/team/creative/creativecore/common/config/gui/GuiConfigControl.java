package team.creative.creativecore.common.config.gui;

import com.google.gson.JsonElement;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.key.ConfigKeyType;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.text.TextBuilder;

public class GuiConfigControl extends GuiRow implements IGuiConfigParent {
   public final ConfigKeyType field;
   public final Side side;
   private GuiButton resetButton;
   private final GuiColumn main;
   private Object extra;
   private boolean loading = false;

   public GuiConfigControl(ConfigKeyType field, Side side, int width, boolean showReset) {
      this(field, side, null, null, width, showReset);
   }

   public GuiConfigControl(ConfigKeyType field, Side side, String caption, String comment) {
      this(field, side, caption, comment, 200, true);
   }

   public GuiConfigControl(ConfigKeyType field, Side side, String caption, String comment, int width, boolean showReset) {
      this.field = field;
      this.side = side;
      if (caption != null) {
         this.setExpandableX();
         GuiColumn text = (GuiColumn)new GuiColumn().setDim(new GuiSizeRule.GuiSizeRatioRules().maxWidth(200));
         text.valign = VAlign.CENTER;
         this.addColumn(text);
         text.add(new GuiLabel(caption + ":").setTitle(Component.literal(caption + ":")).setTooltip(new TextBuilder().translateIfCan(comment).build()));
      }

      this.addColumn(this.main = (GuiColumn)new GuiColumn(width).setExpandableX());
      if (showReset) {
         GuiColumn end = new GuiColumn(20);
         end.align = Align.CENTER;
         this.addColumn(end);
         this.resetButton = (GuiButton)new GuiButton("r", x -> this.reset()).setTranslate("gui.config.reset").setAlign(Align.CENTER);
         end.add(this.resetButton.setTooltip(new TextBuilder().text("reset to default").build()));
      }

      this.registerEventChanged(x -> {
         if (!this.loading) {
            this.changed();
         }
      });
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   public boolean isDefault() {
      return !this.field.isDefault(this.field.converation.save(this.main, this, this.field, this.side), this.side);
   }

   public void updateButton() {
      if (this.resetButton != null) {
         this.resetButton.enabled = this.isDefault();
      }
   }

   public void init(JsonElement initalValue) {
      this.loading = true;
      this.field.converation.createControls(this.main, this, this.field, this.side);
      this.field
         .converation
         .loadValue(
            initalValue != null
               ? this.field.converation.readElement(this.provider(), this.field.defaultValue, false, false, initalValue, this.side, this.field)
               : this.field.get(),
            this.field.defaultValue,
            this.main,
            this,
            this.field,
            this.side
         );
      this.loading = false;
      this.updateButton();
   }

   public void reset() {
      this.field.converation.restoreDefault(this.field.defaultValue, this.main, this, this.field, this.side);
      this.updateButton();
   }

   @Override
   public void changed() {
      this.updateButton();
   }

   public JsonElement save() {
      Object value = this.field.converation.save(this.main, this, this.field, this.side);
      return this.field.converation.shouldSave(value, this.main, this, this.field, this.side)
         ? this.field.converation.writeElement(this.provider(), value, true, false, this.side, this.field)
         : null;
   }

   @Override
   public void setCustomData(Object object) {
      this.extra = object;
   }

   @Override
   public Object getCustomData() {
      return this.extra;
   }
}
