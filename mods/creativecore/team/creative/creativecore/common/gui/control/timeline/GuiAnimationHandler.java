package team.creative.creativecore.common.gui.control.timeline;

public interface GuiAnimationHandler {
   void loop(boolean var1);

   void play();

   void pause();

   void stop();

   void set(int var1);

   int get();
}
