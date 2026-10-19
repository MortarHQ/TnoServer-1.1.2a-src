package toni.lib.animation.easing;

import java.util.Objects;

public record EasingFunctionArgs(EasingType easingType, Double arg0) {
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EasingFunctionArgs that = (EasingFunctionArgs)o;
         return this.easingType == that.easingType && Objects.equals(this.arg0, that.arg0);
      } else {
         return false;
      }
   }
}
