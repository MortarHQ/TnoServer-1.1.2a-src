package fuzs.puzzleslib.api.block.v1.entity;

public interface TickingBlockEntity {
   default void clientTick() {
   }

   default void serverTick() {
   }
}
