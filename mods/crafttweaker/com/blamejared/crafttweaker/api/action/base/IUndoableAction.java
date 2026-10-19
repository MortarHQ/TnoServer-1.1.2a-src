package com.blamejared.crafttweaker.api.action.base;

public interface IUndoableAction extends IRuntimeAction {
   void undo();

   String describeUndo();
}
