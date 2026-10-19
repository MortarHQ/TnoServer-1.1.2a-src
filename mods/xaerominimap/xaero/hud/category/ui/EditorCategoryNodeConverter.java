package xaero.hud.category.ui;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import xaero.hud.category.ObjectCategory;
import xaero.hud.category.setting.ObjectCategorySetting;
import xaero.hud.category.ui.node.EditorCategoryNode;
import xaero.hud.category.ui.node.EditorSettingsNode;
import xaero.hud.category.ui.node.options.range.setting.IEditorSettingNode;

public abstract class EditorCategoryNodeConverter<C extends ObjectCategory<?, C>, ED extends EditorCategoryNode<C, SD, ED>, CB extends ObjectCategory.Builder<C, CB>, SD extends EditorSettingsNode<?>, SDB extends EditorSettingsNode.Builder<SD, SDB>, EDB extends EditorCategoryNode.Builder<C, ED, SD, SDB, EDB>> {
   private final Supplier<CB> categoryBuilderFactory;
   private final Supplier<EDB> editorNodeBuilderFactory;

   public EditorCategoryNodeConverter(@Nonnull Supplier<CB> categoryBuilderFactory, @Nonnull Supplier<EDB> editorNodeBuilderFactory) {
      this.categoryBuilderFactory = categoryBuilderFactory;
      this.editorNodeBuilderFactory = editorNodeBuilderFactory;
   }

   public ED convert(C category, boolean canBeRoot) {
      return this.getConfiguredBuilder(category, canBeRoot).build();
   }

   protected EDB getConfiguredBuilder(C category, boolean canBeRoot) {
      EDB editorNodeBuilder = this.editorNodeBuilderFactory.get();
      editorNodeBuilder.setName(category.getName());
      SDB settingDataBuilder = editorNodeBuilder.getSettingDataBuilder();
      category.getSettingOverridesIterator().forEachRemaining(e -> this.setSettingValue(settingDataBuilder, e.getKey(), e.getValue()));
      settingDataBuilder.setRootSettings(canBeRoot && category.getSuperCategory() == null);
      settingDataBuilder.setProtection(category.getProtection());
      category.getDirectSubCategoryIterator().forEachRemaining(sc -> editorNodeBuilder.addSubCategoryBuilder(this.getConfiguredBuilder((C)sc, canBeRoot)));
      return editorNodeBuilder;
   }

   private <T> void setSettingValue(SDB settingOverridesBuilder, ObjectCategorySetting<T> setting, Object value) {
      settingOverridesBuilder.setSettingValue(setting, (T)value);
   }

   public C convert(ED editorData) {
      return this.getConfiguredBuilder(editorData).build();
   }

   protected CB getConfiguredBuilder(ED editorNode) {
      CB categoryBuilder = this.categoryBuilderFactory.get();
      categoryBuilder.setName(editorNode.getName());
      categoryBuilder.setProtection(editorNode.getSettingsNode().getProtection());
      editorNode.getSettingsNode().getSettings().forEach((k, d) -> this.setSettingValue(categoryBuilder, k, ((IEditorSettingNode)d).getSettingValue()));
      editorNode.getSubCategories().forEach(sed -> categoryBuilder.addSubCategoryBuilder(this.getConfiguredBuilder((ED)sed)));
      return categoryBuilder;
   }

   private <T> void setSettingValue(CB categoryBuilder, ObjectCategorySetting<T> setting, Object value) {
      categoryBuilder.setSettingValue(setting, (T)value);
   }

   public abstract static class Builder<C extends ObjectCategory<?, C>, ED extends EditorCategoryNode<C, SD, ED>, CB extends ObjectCategory.Builder<C, CB>, SD extends EditorSettingsNode<?>, SDB extends EditorSettingsNode.Builder<SD, SDB>, EDB extends EditorCategoryNode.Builder<C, ED, SD, SDB, EDB>, B extends EditorCategoryNodeConverter.Builder<C, ED, CB, SD, SDB, EDB, B>> {
      protected final B self = (B)this;
      protected final Supplier<CB> categoryBuilderFactory;
      protected final Supplier<EDB> editorDataBuilderFactory;

      protected Builder(Supplier<CB> categoryBuilderFactory, Supplier<EDB> editorDataBuilderFactory) {
         this.categoryBuilderFactory = categoryBuilderFactory;
         this.editorDataBuilderFactory = editorDataBuilderFactory;
      }

      protected B setDefault() {
         return this.self;
      }

      public EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> build() {
         return this.buildInternally();
      }

      protected abstract EditorCategoryNodeConverter<C, ED, CB, SD, SDB, EDB> buildInternally();
   }
}
