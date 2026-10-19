package com.blamejared.crafttweaker.api.action.network;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.network.CTNetwork;
import com.blamejared.crafttweaker.api.network.CTNetworkReceiver;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;

public class ActionAddDataReceiver extends CraftTweakerAction implements IUndoableAction {
   private final String id;
   private final CTNetworkReceiver receiver;

   public ActionAddDataReceiver(String id, CTNetworkReceiver receiver) {
      this.id = id;
      this.receiver = receiver;
   }

   @Override
   public void apply() {
      CTNetwork.INSTANCE.clientReceivers.computeIfAbsent(this.id, s -> new ArrayList<>()).add(this.receiver);
   }

   @Override
   public void undo() {
      CTNetwork.INSTANCE.clientReceivers.computeIfPresent(this.id, (s, receivers) -> {
         receivers.remove(this.receiver);
         return receivers.isEmpty() ? null : receivers;
      });
   }

   @Override
   public String describe() {
      return "Adding a data receiver with id: " + this.id;
   }

   @Override
   public String describeUndo() {
      return "Undoing addition of a data receiver with id: " + this.id;
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return true;
   }
}
