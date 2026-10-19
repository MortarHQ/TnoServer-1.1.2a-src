package com.nododiiiii.ponderer.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.LocalizedText;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class UndoManager {
   private static final int MAX_HISTORY = 50;
   private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalizedText.class, new LocalizedText.GsonAdapter()).create();
   private static final Type STEP_LIST_TYPE = (new TypeToken<List<DslScene.DslStep>>() {}).getType();
   private final Deque<String> undoStack = new ArrayDeque<>();
   private final Deque<String> redoStack = new ArrayDeque<>();

   public void saveState(List<DslScene.DslStep> steps) {
      String json = GSON.toJson(steps, STEP_LIST_TYPE);
      this.undoStack.push(json);
      if (this.undoStack.size() > 50) {
         ((ArrayDeque)this.undoStack).removeLast();
      }

      this.redoStack.clear();
   }

   public List<DslScene.DslStep> undo(List<DslScene.DslStep> currentSteps) {
      if (this.undoStack.isEmpty()) {
         return null;
      }

      String currentJson = GSON.toJson(currentSteps, STEP_LIST_TYPE);
      this.redoStack.push(currentJson);
      String previousJson = this.undoStack.pop();
      return (List<DslScene.DslStep>)GSON.fromJson(previousJson, STEP_LIST_TYPE);
   }

   public List<DslScene.DslStep> redo(List<DslScene.DslStep> currentSteps) {
      if (this.redoStack.isEmpty()) {
         return null;
      }

      String currentJson = GSON.toJson(currentSteps, STEP_LIST_TYPE);
      this.undoStack.push(currentJson);
      String nextJson = this.redoStack.pop();
      return (List<DslScene.DslStep>)GSON.fromJson(nextJson, STEP_LIST_TYPE);
   }

   public boolean canUndo() {
      return !this.undoStack.isEmpty();
   }

   public boolean canRedo() {
      return !this.redoStack.isEmpty();
   }

   public void clear() {
      this.undoStack.clear();
      this.redoStack.clear();
   }
}
