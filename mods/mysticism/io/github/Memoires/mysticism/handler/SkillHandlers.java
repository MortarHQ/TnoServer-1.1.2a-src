package io.github.Memoires.mysticism.handler;

import io.github.Memoires.mysticism.handler.skill.ButcherHandler;
import io.github.Memoires.mysticism.handler.skill.CoalescenceHandler;
import io.github.Memoires.mysticism.handler.skill.EngineerHandler;
import io.github.Memoires.mysticism.handler.skill.GardenerHandler;
import io.github.Memoires.mysticism.handler.skill.JinchurikiHandler;
import io.github.Memoires.mysticism.handler.skill.MalleableHandler;
import io.github.Memoires.mysticism.handler.skill.PhaserHandler;
import io.github.Memoires.mysticism.handler.skill.RelapseHandler;
import io.github.Memoires.mysticism.handler.skill.RepeaterHandler;
import io.github.Memoires.mysticism.handler.skill.RestrictedHandler;
import io.github.Memoires.mysticism.handler.skill.StagnatorHandler;
import io.github.Memoires.mysticism.handler.skill.SubjugatorHandler;

public class SkillHandlers {
   public static void init() {
      ButcherHandler.init();
      CoalescenceHandler.init();
      EngineerHandler.init();
      GardenerHandler.init();
      JinchurikiHandler.init();
      MalleableHandler.init();
      PhaserHandler.init();
      RelapseHandler.init();
      RepeaterHandler.init();
      RestrictedHandler.init();
      StagnatorHandler.init();
      SubjugatorHandler.init();
   }
}
