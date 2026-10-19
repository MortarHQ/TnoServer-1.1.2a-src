package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class RunInfo implements IScriptRunInfo {
   private final ScriptRunConfiguration configuration;
   private final RunInfo.Actions actions;
   private final RunInfo.ActionsView view;
   private boolean displayBranding;
   private boolean dumpClasses;
   private Boolean firstRun;

   private RunInfo(ScriptRunConfiguration configuration) {
      this.configuration = configuration;
      this.actions = new RunInfo.Actions();
      this.view = new RunInfo.ActionsView(this.actions);
      this.displayBranding = true;
      this.dumpClasses = false;
      this.firstRun = null;
   }

   static RunInfo create(ScriptRunConfiguration configuration) {
      return new RunInfo(configuration);
   }

   @Override
   public ScriptRunConfiguration configuration() {
      return this.configuration;
   }

   @Override
   public List<IAction> appliedActions() {
      return this.view.validActions();
   }

   @Override
   public List<IAction> invalidActions() {
      return this.view.invalidActions();
   }

   @Override
   public boolean displayBranding() {
      return this.displayBranding;
   }

   @Override
   public boolean dumpClasses() {
      return this.dumpClasses;
   }

   @Override
   public boolean isFirstRun() {
      return Objects.requireNonNull(this.firstRun, "Unable to determine at this stage if this is a first run");
   }

   void displayBranding(boolean displayBranding) {
      this.displayBranding = displayBranding;
   }

   void dumpClasses(boolean dumpClasses) {
      this.dumpClasses = dumpClasses;
   }

   void enqueueAction(IAction action, boolean valid) {
      (valid ? this.actions.validActions() : this.actions.invalidActions()).add(action);
   }

   void isFirstRun(boolean isFirstRun) {
      this.firstRun = isFirstRun;
   }

   private record Actions(List<IAction> validActions, List<IAction> invalidActions) {
      Actions() {
         this(new ArrayList<>(), new ArrayList<>());
      }
   }

   private record ActionsView(List<IAction> validActions, List<IAction> invalidActions) {
      ActionsView(RunInfo.Actions view) {
         this(Collections.unmodifiableList(view.validActions()), Collections.unmodifiableList(view.invalidActions()));
      }
   }
}
