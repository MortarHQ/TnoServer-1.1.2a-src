package team.lodestar.lodestone.systems.model.obj.data;

import java.util.Objects;

public record IndexedVertex(int positionIndex, int normalIndex, int uvIndex) {
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IndexedVertex that = (IndexedVertex)o;
         return this.positionIndex == that.positionIndex && this.normalIndex == that.normalIndex && this.uvIndex == that.uvIndex;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.positionIndex, this.normalIndex, this.uvIndex);
   }
}
