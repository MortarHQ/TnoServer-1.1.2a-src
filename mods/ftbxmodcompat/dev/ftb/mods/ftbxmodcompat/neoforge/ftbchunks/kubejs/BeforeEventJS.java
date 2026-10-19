package dev.ftb.mods.ftbxmodcompat.neoforge.ftbchunks.kubejs;

import dev.ftb.mods.ftbchunks.api.ClaimResult;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import net.minecraft.commands.CommandSourceStack;

public class BeforeEventJS extends AfterEventJS {
   private ClaimResult result;

   public BeforeEventJS(CommandSourceStack source, ClaimedChunk chunk) {
      super(source, chunk);
      this.result = chunk;
   }

   public ClaimResult getResult() {
      return this.result;
   }

   public void setResult(ClaimResult r) {
      this.result = r;
   }

   public void setCustomResult(String messageKey) {
      this.result = ClaimResult.customProblem(messageKey);
   }
}
