package team.lodestar.lodestone.systems.rendering;

public interface IBufferObject {
   default void registerBufferObject() {
      LodestoneRenderSystem.registerBufferObject(this);
   }

   void destroy();
}
