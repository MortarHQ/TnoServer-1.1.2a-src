package toni.lib.animation.effects;

public interface IAnimationEffect {
   float getOut();

   float getIn();

   default IAnimationEffect.Position getPosition(float current) {
      if (current > this.getOut()) {
         return IAnimationEffect.Position.AFTER;
      } else {
         return current > this.getIn() ? IAnimationEffect.Position.DURING : IAnimationEffect.Position.BEFORE;
      }
   }

   enum Position {
      BEFORE,
      DURING,
      AFTER;
   }
}
