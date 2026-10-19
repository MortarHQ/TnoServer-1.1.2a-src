package xaero.hud.category.ui.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import xaero.common.misc.ListFactory;
import xaero.hud.category.ObjectCategory;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.entry.ConnectionLineType;
import xaero.hud.category.ui.entry.EditorListEntryCategory;
import xaero.hud.category.ui.entry.EditorListRootEntry;
import xaero.hud.category.ui.entry.EditorListRootEntryFactory;
import xaero.hud.category.ui.node.tooltip.IEditorDataTooltipSupplier;
import xaero.lib.client.gui.widget.Tooltip;

public abstract class EditorCategoryNode<C extends ObjectCategory<?, C>, SD extends EditorSettingsNode<?>, ED extends EditorCategoryNode<C, SD, ED>>
   extends EditorNode {
   private final ED self = (ED)this;
   private boolean cut;
   private final List<ED> subCategories;
   private final EditorAdderNode topAdder;
   private final Function<EditorAdderNode, ED> newCategorySupplier;
   private final SD settingsNode;

   protected EditorCategoryNode(
      @Nonnull SD settingNode,
      @Nonnull List<ED> subCategories,
      @Nonnull EditorAdderNode topAdder,
      @Nonnull Function<EditorAdderNode, ED> newCategorySupplier,
      boolean movable,
      int subIndex,
      @Nonnull EditorListRootEntryFactory listEntryFactory,
      IEditorDataTooltipSupplier tooltipSupplier
   ) {
      super(movable, listEntryFactory, tooltipSupplier);
      this.settingsNode = settingNode;
      this.subCategories = subCategories;
      this.topAdder = topAdder;
      this.newCategorySupplier = newCategorySupplier;
   }

   public SD getSettingsNode() {
      return this.settingsNode;
   }

   public final List<ED> getSubCategories() {
      return this.subCategories;
   }

   public String getName() {
      return this.settingsNode.getNameOption().getResult();
   }

   @Override
   public Component getDisplayName() {
      return Component.translatable(this.getName());
   }

   private BiConsumer<EditorAdderNode, Integer> getAdderHandler() {
      return (adder, i) -> {
         if (adder.isConfirmed()) {
            ED newCategory = this.newCategorySupplier.apply(adder);
            this.subCategories.add(i, newCategory);
            adder.reset();
         }
      };
   }

   private Runnable getDeletionHandler() {
      return () -> {
         Iterator<ED> subIterator = this.subCategories.iterator();

         while (subIterator.hasNext()) {
            ED subCategory = subIterator.next();
            if (subCategory.getSettingsNode().isToBeDeleted()) {
               subIterator.remove();
            }
         }
      };
   }

   public Supplier<Boolean> getMoveAction(int subIndex, int direction, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList) {
      return () -> {
         int newSlot = subIndex + direction;
         ED subCategoryToMove = this.subCategories.get(subIndex);
         rowList.setLastExpandedData(subCategoryToMove);
         if (newSlot < 0) {
            this.subCategories.remove(subCategoryToMove);
            this.subCategories.add(subCategoryToMove);
            return true;
         } else if (newSlot >= this.subCategories.size()) {
            this.subCategories.remove(subCategoryToMove);
            this.subCategories.add(0, subCategoryToMove);
            return true;
         } else {
            rowList.restoreScrollAfterUpdate();
            ED subCategoryToReplace = this.subCategories.get(newSlot);
            this.subCategories.set(subIndex, subCategoryToReplace);
            this.subCategories.set(newSlot, subCategoryToMove);
            return true;
         }
      };
   }

   public Supplier<Boolean> getDuplicateAction(int subIndex, GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowList) {
      return () -> {
         if (subIndex >= 0 && subIndex < this.subCategories.size()) {
            ED subCategoryToDuplicate = this.subCategories.get(subIndex);
            GuiCategoryEditor screenToRestore = (GuiCategoryEditor)Minecraft.getInstance().screen;
            Component confirmSecondLine = subCategoryToDuplicate.getDisplayName()
               .copy()
               .withStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.YELLOW)));
            Minecraft.getInstance().setScreen(new ConfirmScreen(result -> {
               if (!result) {
                  Minecraft.getInstance().setScreen(screenToRestore);
               } else {
                  C convertedCategory = rowList.getDataConverter().convert(subCategoryToDuplicate);
                  ED reconstructedEditorData = rowList.getDataConverter().convert(convertedCategory, false);
                  reconstructedEditorData.removeProtectionRecursive();
                  this.subCategories.add(subIndex + 1, reconstructedEditorData);
                  Minecraft.getInstance().setScreen(screenToRestore);
                  GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList newRowList = screenToRestore.getRowList();
                  newRowList.setLastExpandedData(reconstructedEditorData);
                  newRowList.updateEntries();
               }
            }, Component.translatable("gui.xaero_category_duplicate_confirm"), confirmSecondLine));
            return true;
         } else {
            return false;
         }
      };
   }

   public Supplier<Boolean> getCutAction(ED parent, GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowList) {
      return () -> {
         rowList.setCutCategory(this.self, parent);
         rowList.setLastExpandedData(this);
         rowList.restoreScrollAfterUpdate();
         return true;
      };
   }

   public Supplier<Boolean> getPasteAction(GuiCategoryEditor<C, ED, ?, ?, ?, ?>.SettingRowList rowList) {
      return () -> {
         rowList.pasteTo(this.self);
         rowList.restoreScrollAfterUpdate();
         return true;
      };
   }

   @Override
   public List<EditorNode> getSubNodes() {
      BiConsumer<EditorAdderNode, Integer> adderHandler = this.getAdderHandler();
      adderHandler.accept(this.topAdder, 0);
      this.getDeletionHandler().run();
      List<EditorNode> result = new ArrayList<>(this.subCategories);
      result.add(0, this.topAdder);
      result.add(0, this.settingsNode);
      return result;
   }

   public void removeProtectionRecursive() {
      this.getSettingsNode().setProtected(false);

      for (ED sub : this.subCategories) {
         sub.removeProtectionRecursive();
      }
   }

   public abstract static class Builder<C extends ObjectCategory<?, C>, ED extends EditorCategoryNode<C, SD, ED>, SD extends EditorSettingsNode<?>, SDB extends EditorSettingsNode.Builder<SD, SDB>, EDB extends EditorCategoryNode.Builder<C, ED, SD, SDB, EDB>>
      extends EditorNode.Builder<EDB> {
      protected final EDB self;
      protected String name;
      protected final SDB settingsDataBuilder;
      protected final List<EDB> subCategoryBuilders;
      protected final ListFactory listFactory;
      protected final EditorAdderNode.Builder topAdderBuilder;
      protected Function<EditorAdderNode, ED> newCategorySupplier;
      protected int subIndex;

      protected Builder(ListFactory listFactory, SDB settingsDataBuilder) {
         if (settingsDataBuilder == null) {
            throw new IllegalStateException("settings data builder cannot be null!");
         }

         this.settingsDataBuilder = settingsDataBuilder;
         this.subCategoryBuilders = listFactory.get();
         this.listFactory = listFactory;
         this.topAdderBuilder = EditorAdderNode.Builder.begin(listFactory);
         this.self = (EDB)this;
      }

      public EDB setDefault() {
         super.setDefault();
         this.setName(null);
         this.settingsDataBuilder.setDefault();
         this.subCategoryBuilders.clear();
         this.topAdderBuilder.setDisplayName(Component.translatable("gui.xaero_category_add_subcategory"));
         this.setMovable(true);
         this.setSubIndex(0);
         this.setTooltipSupplier((parent, data) -> {
            Component displayNameComponent = data.getDisplayName();
            Tooltip tooltip = new Tooltip(Component.translatable("gui.xaero_box_category", new Object[]{displayNameComponent}));
            tooltip.setAutoLinebreak(false);
            return tooltip;
         });
         return this.self;
      }

      @Override
      protected EditorListRootEntry mainEntryFactory(
         EditorNode data,
         EditorNode parent,
         int index,
         ConnectionLineType lineType,
         GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
         int screenWidth,
         boolean isFinalExpanded
      ) {
         return new EditorListEntryCategory(
            screenWidth,
            index,
            rowList,
            lineType,
            (EditorCategoryNode<?, ?, ?>)data,
            (EditorCategoryNode<?, ?, ?>)parent,
            data.getTooltipSupplier(parent),
            isFinalExpanded
         );
      }

      public EDB setNewCategorySupplier(Function<EditorAdderNode, ED> newCategorySupplier) {
         this.newCategorySupplier = newCategorySupplier;
         return this.self;
      }

      public EDB setSubIndex(int subIndex) {
         this.subIndex = subIndex;
         return this.self;
      }

      public EDB setName(String name) {
         this.name = name;
         return this.self;
      }

      public SDB getSettingDataBuilder() {
         return this.settingsDataBuilder;
      }

      public EDB addSubCategoryBuilder(EDB subCategory) {
         subCategory.setSubIndex(this.subCategoryBuilders.size());
         this.subCategoryBuilders.add(subCategory);
         return this.self;
      }

      protected List<ED> buildSubCategories() {
         return this.subCategoryBuilders.stream().map(EditorCategoryNode.Builder::build).collect(this.listFactory::get, List::add, List::addAll);
      }

      public ED build() {
         if (this.name != null && this.newCategorySupplier != null) {
            this.settingsDataBuilder.getNameOptionBuilder().setInput(this.name);
            this.settingsDataBuilder.getNameOptionBuilder().setDisplayName(Component.translatable("gui.xaero_category_name"));
            this.settingsDataBuilder.getNameOptionBuilder().setMaxLength(200);
            return (ED)super.build();
         } else {
            throw new IllegalStateException("required fields not set!");
         }
      }
   }
}
