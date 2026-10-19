package xaero.hud.category.ui.node.options;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.Component;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.entry.EditorListEntryExpandingOptions;
import xaero.hud.category.ui.entry.EditorListRootEntry;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.entry.widget.EditorButton;
import xaero.hud.category.ui.node.EditorNode;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;

public final class EditorSimpleButtonNode extends EditorNode {
   protected final Component displayName;
   private EditorSimpleButtonNode.ISimpleButtonCallback callback;
   private EditorButton.PressActionWithContext pressAction;
   private EditorSimpleButtonNode.ISimpleButtonMessageSupplier messageSupplier;
   private final EditorSimpleButtonNode.ISimpleButtonIsActiveSupplier isActiveSupplier;

   private EditorSimpleButtonNode(
      @Nonnull Component displayName,
      @Nonnull IEditorDataTooltipSupplier tooltipSupplier,
      boolean movable,
      EditorSimpleButtonNode.ISimpleButtonCallback callback,
      @Nonnull EditorListRootEntryFactory listEntryFactory,
      EditorSimpleButtonNode.ISimpleButtonMessageSupplier messageSupplier,
      EditorSimpleButtonNode.ISimpleButtonIsActiveSupplier isActiveSupplier
   ) {
      super(movable, listEntryFactory, tooltipSupplier);
      this.displayName = displayName;
      this.callback = callback;
      this.messageSupplier = messageSupplier;
      this.isActiveSupplier = isActiveSupplier;
   }

   public Supplier<Component> getMessageSupplier(EditorNode parent, EditorSimpleButtonNode data) {
      return this.messageSupplier.get(parent, data);
   }

   public boolean getIsActiveSupplier(EditorNode parent, EditorSimpleButtonNode data, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList) {
      return this.isActiveSupplier.get(parent, data, rowList);
   }

   @Override
   public Component getDisplayName() {
      return this.displayName;
   }

   public EditorButton.PressActionWithContext getPressAction() {
      if (this.pressAction == null) {
         this.pressAction = new EditorButton.PressActionWithContext() {
            @Override
            public void onPress(EditorButton button, EditorNode parent, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList) {
               if (EditorSimpleButtonNode.this.callback != null) {
                  EditorSimpleButtonNode.this.callback.onButtonPress(parent, EditorSimpleButtonNode.this, rowList);
               }
            }
         };
      }

      return this.pressAction;
   }

   @Override
   public List<EditorNode> getSubNodes() {
      return null;
   }

   public static final class Builder extends EditorNode.Builder<EditorSimpleButtonNode.Builder> {
      private Component displayName;
      private EditorSimpleButtonNode.ISimpleButtonCallback callback;
      private EditorSimpleButtonNode.ISimpleButtonMessageSupplier messageSupplier;
      private EditorSimpleButtonNode.ISimpleButtonIsActiveSupplier isActiveSupplier;

      private Builder() {
      }

      public EditorSimpleButtonNode.Builder setDefault() {
         super.setDefault();
         this.setDisplayName(null);
         this.setCallback(null);
         this.setMessageSupplier((parent, node) -> node::getDisplayName);
         this.setIsActiveSupplier((p, d, rowList) -> true);
         return this;
      }

      @Override
      protected EditorListRootEntry.CenteredEntryFactory getCenteredEntryFactory(
         EditorNode node, EditorNode parent, int index, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList
      ) {
         EditorSimpleButtonNode buttonNode = (EditorSimpleButtonNode)node;
         Supplier<Component> messageSupplier = buttonNode.getMessageSupplier(parent, buttonNode);
         return (x, y, width, height, root) -> {
            boolean isActive = buttonNode.getIsActiveSupplier(parent, buttonNode, rowList);
            EditorButton widget = new EditorButton(parent, messageSupplier, isActive, 216, 20, buttonNode.getPressAction(), rowList);
            return new EditorListEntryExpandingOptions(x, y, width, height, index, rowList, root, widget, messageSupplier, node.getTooltipSupplier(parent));
         };
      }

      public EditorSimpleButtonNode.Builder setDisplayName(Component displayName) {
         this.displayName = displayName;
         return this;
      }

      public EditorSimpleButtonNode.Builder setCallback(EditorSimpleButtonNode.ISimpleButtonCallback callback) {
         this.callback = callback;
         return this;
      }

      public EditorSimpleButtonNode.Builder setMessageSupplier(EditorSimpleButtonNode.ISimpleButtonMessageSupplier messageSupplier) {
         this.messageSupplier = messageSupplier;
         return this;
      }

      public EditorSimpleButtonNode.Builder setIsActiveSupplier(EditorSimpleButtonNode.ISimpleButtonIsActiveSupplier isActiveSupplier) {
         this.isActiveSupplier = isActiveSupplier;
         return this;
      }

      public EditorSimpleButtonNode build() {
         if (this.displayName != null && this.callback != null) {
            return (EditorSimpleButtonNode)super.build();
         } else {
            throw new IllegalStateException("required fields not set!");
         }
      }

      @Override
      protected EditorNode buildInternally() {
         return new EditorSimpleButtonNode(
            this.displayName, this.tooltipSupplier, this.movable, this.callback, this.listEntryFactory, this.messageSupplier, this.isActiveSupplier
         );
      }

      public static EditorSimpleButtonNode.Builder begin() {
         return new EditorSimpleButtonNode.Builder().setDefault();
      }
   }

   @FunctionalInterface
   public interface ISimpleButtonCallback {
      void onButtonPress(EditorNode var1, EditorSimpleButtonNode var2, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList var3);
   }

   @FunctionalInterface
   public interface ISimpleButtonIsActiveSupplier {
      boolean get(EditorNode var1, EditorSimpleButtonNode var2, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList var3);
   }

   @FunctionalInterface
   public interface ISimpleButtonMessageSupplier {
      Supplier<Component> get(EditorNode var1, EditorSimpleButtonNode var2);
   }
}
