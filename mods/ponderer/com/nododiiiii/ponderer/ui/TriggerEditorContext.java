package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import java.util.Map;
import net.minecraft.client.Minecraft;

public record TriggerEditorContext(DslScene scene, int sceneIndex, SceneEditorScreen parent) implements SnapshotReturnContext {
   @Override
   public void reopenEditor(Map<String, String> snapshot) {
      Minecraft.getInstance().setScreen(new TriggerEditorScreen(this.scene, this.sceneIndex, this.parent).setPendingFormRestore(snapshot));
   }
}
