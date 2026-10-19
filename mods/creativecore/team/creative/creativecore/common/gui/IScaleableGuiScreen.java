package team.creative.creativecore.common.gui;

public interface IScaleableGuiScreen {
   int getWidth();

   int getHeight();

   default int getMaxScale(int displayWidth, int displayHeight) {
      int scaleFactor = 1;
      int width = this.getWidth();
      int height = this.getHeight();

      while (scaleFactor < 100 && width * (scaleFactor + 1) <= displayWidth && height * (scaleFactor + 1) <= displayHeight) {
         scaleFactor++;
      }

      return scaleFactor;
   }

   void clientTick();
}
