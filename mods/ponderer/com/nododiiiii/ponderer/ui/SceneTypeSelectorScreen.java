package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ui.catnip.AbstractReadonlyDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.FullButtonListEntry;
import com.nododiiiii.ponderer.ui.catnip.SectionHeaderListEntry;
import java.util.List;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;

public class SceneTypeSelectorScreen extends AbstractReadonlyDeclarativeListScreen {
   private final SceneEditorScreen parent;

   public SceneTypeSelectorScreen(SceneEditorScreen parent) {
      super(parent, "ponderer.ui.scope.editor", "ponderer.ui.scene_selector.title", 300);
      this.parent = parent;
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
      entries.add(new SectionHeaderListEntry(UIText.of("ponderer.ui.scene_selector.desc")));
      entries.add(new FullButtonListEntry(UIText.of("ponderer.ui.step.type.show_structure"), null, () -> this.parent.insertSplitStep("show_structure")));
      entries.add(
         new FullButtonListEntry(
            UIText.of("ponderer.ui.step.type.show_interface"),
            null,
            () -> ShowInterfaceExperimentalNoticeScreen.openIfNeeded(this, () -> this.parent.insertSplitStep("show_interface"))
         )
      );
   }

   @Override
   protected int getEntryHeight() {
      return 24;
   }
}
