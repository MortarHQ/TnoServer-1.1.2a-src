package mcjty.incontrol.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.BiPredicate;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.tools.rules.TestingBlockTools;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

public class EventTypeBlockBroken implements EventType {
   private BiPredicate<LevelAccessor, BlockPos> predicate;

   @Override
   public EventType.Type type() {
      return EventType.Type.BLOCK_BROKEN;
   }

   public BiPredicate<LevelAccessor, BlockPos> getBlockTest() {
      return this.predicate;
   }

   @Override
   public boolean parse(JsonObject object) {
      JsonElement block = object.get("block");
      if (block == null) {
         ErrorHandler.error("Block broken event has no 'block' parameter!");
         return false;
      } else {
         this.predicate = TestingBlockTools.parseBlockJson(block);
         return this.predicate != null;
      }
   }
}
