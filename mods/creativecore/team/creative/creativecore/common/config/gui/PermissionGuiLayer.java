package team.creative.creativecore.common.config.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.converation.ConfigTypeConveration;
import team.creative.creativecore.common.config.converation.ConfigTypePermission;
import team.creative.creativecore.common.config.holder.ConfigHolderObject;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.key.ConfigKeyType;
import team.creative.creativecore.common.config.premade.Permission;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiLeftRightBox;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.control.parent.GuiTableScrollable;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.dialog.DialogGuiLayer;
import team.creative.creativecore.common.gui.dialog.GuiDialogHandler;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.util.text.TextBuilder;

public class PermissionGuiLayer extends GuiLayer {
   public ConfigTypePermission.GuiPermissionConfigButton button;
   protected boolean force;
   protected GuiTableScrollable table;
   protected ConfigTypeConveration converation;
   private List<PermissionGuiLayer.PermissionGuiGroup> groups;
   private final HashMap<ConfigKeyType, GuiRow> rows = new HashMap<>();

   public PermissionGuiLayer() {
      super("permission", 500, 260);
   }

   @Override
   public void create() {
      if (this.button != null) {
         this.flow = GuiFlow.STACK_Y;
         this.align = Align.STRETCH;
         this.converation = this.button.key.converation();
         this.groups = new ArrayList<>();
         GuiParent top = new GuiParent();
         this.add(top);
         top.add(new GuiButton("add", x -> {
            final PermissionGuiLayer.PermissionGuiGroup groupx;
            if (this.converation != null) {
               GuiRow row = this.table.contentRows().iterator().next();
               groupx = new PermissionGuiLayer.PermissionGuiGroupSimple("", null);
               this.groups.add(groupx);
               GuiColumn colxx = new GuiColumn();
               ((PermissionGuiLayer.PermissionGuiGroupSimple)groupx).control = colxx;
               this.converation.createControls(colxx, this.button.configParent, this.button.key, this.button.side);
               row.addColumn(colxx);
            } else {
               Object newValue = ConfigTypeConveration.createObject(this.button.key.field());
               ConfigHolderObject holderx = ConfigHolderObject.createUnrelated(this.button.key.getRegistry(), this.button.side, newValue, newValue);
               groupx = new PermissionGuiLayer.PermissionGuiGroupMulti(this.provider(), "", holderx);
               this.groups.add(groupx);

               for (Entry<ConfigKeyType, GuiRow> entry : this.rows.entrySet()) {
                  GuiColumn colx = new GuiColumn();
                  GuiConfigControl configx = new GuiConfigControl(entry.getKey(), this.button.side, 100, false) {
                     @Override
                     public void changed() {
                        groupx.updateResetButton();
                     }
                  };
                  colx.add(configx);
                  ((PermissionGuiLayer.PermissionGuiGroupMulti)groupx).controls.add(configx);
                  configx.init(null);
                  entry.getValue().addColumn(colx);
               }
            }

            this.addGroupHeader(false, groupx, "");
            this.reflow();
         }).setTranslate("gui.perm.add"));
         this.table = new GuiTableScrollable();
         this.add(this.table);
         GuiRow topRow = this.table.getTopRow();
         topRow.addColumn(new GuiColumn());
         if (this.converation != null) {
            GuiRow row = new GuiRow();
            String caption = translateOrDefault("gui.content", "content");
            GuiColumn labelCol = new GuiColumn();
            labelCol.setVAlign(VAlign.CENTER).add(new GuiLabel("label").setTitle(Component.literal(caption + ":")));
            row.addColumn(labelCol);
            this.table.addRow(row);

            for (Entry<String, ?> entry : this.button.value.entrySet()) {
               Object defaultValue = this.button.defaultValue.getDirect(entry.getKey());
               PermissionGuiLayer.PermissionGuiGroupSimple group = new PermissionGuiLayer.PermissionGuiGroupSimple(entry.getKey(), defaultValue);
               this.groups.add(group);
               GuiColumn col = new GuiColumn();
               group.control = col;
               this.converation.createControls(col, this.button.configParent, this.button.key, this.button.side);
               this.converation.loadValue(entry.getValue(), defaultValue, col, this.button.configParent, this.button.key, this.button.side);
               row.addColumn(col);
            }
         } else {
            List<GuiRow> rows = new ArrayList<>();

            for (Entry<String, ?> entry : this.button.value.entrySet()) {
               this.button.key.forceValue(entry.getValue(), this.button.side);
               Object copiedEntry = this.button.key.copy(this.provider(), this.button.side);
               Object defaultReference = this.button.defaultValue.getDirect(entry.getKey());
               if (defaultReference == null) {
                  defaultReference = copiedEntry;
               }

               ConfigHolderObject holder = ConfigHolderObject.createUnrelated(this.button.key.getRegistry(), this.button.side, copiedEntry, defaultReference);
               final PermissionGuiLayer.PermissionGuiGroupMulti group = new PermissionGuiLayer.PermissionGuiGroupMulti(this.provider(), entry.getKey(), holder);
               this.groups.add(group);
               int i = 0;

               for (ConfigKey key : holder.fields()) {
                  if (!key.requiresRestart && !key.isFolder() && !key.hideFromGUI) {
                     if (rows.size() <= i) {
                        GuiRow row = new GuiRow();
                        String path = "config." + String.join(".", holder.path());
                        if (!path.endsWith(".")) {
                           path = path + ".";
                        }

                        String caption = translateOrDefault(path + key.name + ".name", key.name);
                        String comment = path + key.name + ".comment";
                        GuiColumn col = new GuiColumn();
                        col.setVAlign(VAlign.CENTER)
                           .add(
                              new GuiLabel("label" + i)
                                 .setTitle(Component.literal(caption + ":"))
                                 .setTooltip(new TextBuilder().translateIfCan(comment).build())
                           );
                        row.addColumn(col);
                        this.table.addRow(row);
                        rows.add(row);
                        this.rows.put((ConfigKeyType)key, row);
                     }

                     GuiColumn col = new GuiColumn();
                     GuiConfigControl config = new GuiConfigControl((ConfigKeyType)key, Side.SERVER, 100, false) {
                        @Override
                        public void changed() {
                           group.updateResetButton();
                        }
                     };
                     col.add(config);
                     group.controls.add(config);
                     config.init(null);
                     rows.get(i).addColumn(col);
                     i++;
                  }
               }
            }
         }

         int i = 0;
         boolean defaultCol = true;

         for (String name : this.button.value.keySet()) {
            this.addGroupHeader(defaultCol, this.groups.get(i), name);
            defaultCol = false;
            i++;
         }

         GuiLeftRightBox bottom = new GuiLeftRightBox();
         this.add(bottom.setUnexpandableX());
         bottom.addLeft(new GuiButton("cancel", x -> this.closeTopLayer()).setTranslate("gui.cancel"));
         bottom.addRight(new GuiButton("save", x -> {
            this.button.setNewValue(this.save());
            this.force = true;
            this.closeTopLayer();
         }).setTranslate("gui.save"));

         for (PermissionGuiLayer.PermissionGuiGroup group : this.groups) {
            group.updateResetButton();
         }
      }
   }

   protected void addGroupHeader(boolean defaultCol, PermissionGuiLayer.PermissionGuiGroup group, String name) {
      GuiColumn col = new GuiColumn();
      col.setVAlign(VAlign.CENTER).setDim(new GuiSizeRule.GuiSizeRules().maxWidth(100));
      if (defaultCol) {
         col.add(new GuiLabel("name").setTitle(Component.literal(name)));
      } else {
         col.add(group.textfield = new GuiTextfield("name", name).setDim(100));
      }

      if (!name.isEmpty() && this.button.defaultValue.containsKey(name)) {
         col.add(group.resetButton = (GuiButton)new GuiButton("r", x -> group.reset()).setTranslate("gui.config.reset").setAlign(Align.CENTER));
      }

      if (!defaultCol) {
         col.add(new GuiButton("x", x -> {
            int index = this.groups.indexOf(group);
            this.groups.remove(index);
            this.table.removeContentCol(index);
            this.reflow();
         }).setTranslate("gui.del").setAlign(Align.CENTER));
      }

      this.table.getTopRow().addColumn(col);
   }

   @Override
   public void closeTopLayer() {
      Permission newValue = this.save();
      if (!this.force && !this.button.configTypePerm.areEqual(this.button.value, newValue, this.button.key, this.button.side)) {
         GuiDialogHandler.openDialog(this.getIntegratedParent(), "savechanges", (x, y) -> {
            if (y == DialogGuiLayer.DialogButton.YES) {
               this.button.setNewValue(newValue);
            } else if (y != DialogGuiLayer.DialogButton.CANCEL) {
               this.force = true;
               this.closeTopLayer();
            }
         }, DialogGuiLayer.DialogButton.YES, DialogGuiLayer.DialogButton.NO, DialogGuiLayer.DialogButton.CANCEL);
      } else {
         super.closeTopLayer();
      }
   }

   public Permission save() {
      Permission perm = new Permission<>(this.groups.get(0).save());

      for (int i = 1; i < this.groups.size(); i++) {
         perm.add(this.groups.get(i).getTitle(), this.groups.get(i).save());
      }

      return perm;
   }

   public abstract static class PermissionGuiGroup {
      public final String originalGroup;
      public GuiTextfield textfield;
      public GuiButton resetButton;

      public PermissionGuiGroup(String group) {
         this.originalGroup = group;
      }

      public String getTitle() {
         return this.isDefaultGroup() ? "default" : this.textfield.getText();
      }

      public void updateResetButton() {
         if (this.resetButton != null) {
            this.resetButton.enabled = !this.isDefault();
         }
      }

      public abstract Object save();

      public void reset() {
         if (!this.isDefaultGroup()) {
            this.textfield.setText(this.originalGroup);
         }

         this.resetInternal();
         this.updateResetButton();
      }

      protected abstract void resetInternal();

      public abstract boolean isDefault();

      public boolean isDefaultGroup() {
         return this.textfield == null;
      }
   }

   public static class PermissionGuiGroupMulti extends PermissionGuiLayer.PermissionGuiGroup {
      public final Provider provider;
      public final ConfigHolderObject holder;
      public final List<GuiConfigControl> controls = new ArrayList<>();
      public GuiTextfield textfield;

      public PermissionGuiGroupMulti(Provider provider, String group, ConfigHolderObject holder) {
         super(group);
         this.provider = provider;
         this.holder = holder;
      }

      @Override
      public Object save() {
         JsonObject json = new JsonObject();

         for (GuiConfigControl control : this.controls) {
            JsonElement element = control.save();
            if (element != null) {
               json.add(control.field.name, element);
            }
         }

         this.holder.load(this.provider, false, true, json, Side.SERVER);
         return this.holder.object;
      }

      @Override
      protected void resetInternal() {
         for (GuiConfigControl control : this.controls) {
            control.reset();
         }
      }

      @Override
      public boolean isDefault() {
         this.save();
         return this.holder.isDefault(Side.SERVER);
      }
   }

   public class PermissionGuiGroupSimple extends PermissionGuiLayer.PermissionGuiGroup {
      public GuiParent control;
      public final Object defaultValue;

      public PermissionGuiGroupSimple(String group, Object defaultValue) {
         super(group);
         this.defaultValue = defaultValue;
      }

      @Override
      protected void resetInternal() {
         PermissionGuiLayer.this.converation
            .loadValue(
               this.defaultValue, this.defaultValue, this.control, PermissionGuiLayer.this.button.configParent, null, PermissionGuiLayer.this.button.side
            );
      }

      @Override
      public boolean isDefault() {
         return this.defaultValue == null
            ? false
            : PermissionGuiLayer.this.converation.areEqual(this.defaultValue, this.save(), null, PermissionGuiLayer.this.button.side);
      }

      @Override
      public Object save() {
         return PermissionGuiLayer.this.converation
            .save(this.control, PermissionGuiLayer.this.button.configParent, PermissionGuiLayer.this.button.key, PermissionGuiLayer.this.button.side);
      }
   }
}
