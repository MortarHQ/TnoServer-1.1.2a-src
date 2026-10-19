package it.hurts.sskirillss.relics.items.relics.base.data.research;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ResearchData {
   private Map<Integer, StarData> stars;
   private Multimap<Integer, Integer> links;

   public List<StarData> getConnectedStars(StarData star) {
      List<StarData> connectedStars = new ArrayList<>();
      int starIndex = star.getIndex();
      if (this.links.containsKey(starIndex)) {
         for (Integer connectedIndex : this.links.get(starIndex)) {
            connectedStars.add(this.stars.get(connectedIndex));
         }
      }

      for (Entry<Integer, Integer> entry : this.links.entries()) {
         if (entry.getValue().equals(starIndex)) {
            connectedStars.add(this.stars.get(entry.getKey()));
         }
      }

      return connectedStars;
   }

   ResearchData(Map<Integer, StarData> stars, Multimap<Integer, Integer> links) {
      this.stars = stars;
      this.links = links;
   }

   public static ResearchData.ResearchDataBuilder builder() {
      return new ResearchData.ResearchDataBuilder();
   }

   public Map<Integer, StarData> getStars() {
      return this.stars;
   }

   public Multimap<Integer, Integer> getLinks() {
      return this.links;
   }

   public void setStars(Map<Integer, StarData> stars) {
      this.stars = stars;
   }

   public void setLinks(Multimap<Integer, Integer> links) {
      this.links = links;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ResearchData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$stars = this.getStars();
         Object other$stars = other.getStars();
         if (this$stars == null ? other$stars == null : this$stars.equals(other$stars)) {
            Object this$links = this.getLinks();
            Object other$links = other.getLinks();
            return this$links == null ? other$links == null : this$links.equals(other$links);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof ResearchData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $stars = this.getStars();
      result = result * 59 + ($stars == null ? 43 : $stars.hashCode());
      Object $links = this.getLinks();
      return result * 59 + ($links == null ? 43 : $links.hashCode());
   }

   @Override
   public String toString() {
      return "ResearchData(stars=" + this.getStars() + ", links=" + this.getLinks() + ")";
   }

   public static class ResearchDataBuilder {
      private Map<Integer, StarData> stars = new HashMap<>();
      private Multimap<Integer, Integer> links = LinkedHashMultimap.create();

      public ResearchData.ResearchDataBuilder star(int index, int x, int y) {
         this.stars.put(index, new StarData(index, x, y));
         return this;
      }

      public ResearchData.ResearchDataBuilder link(int first, int second) {
         int var3;
         if (first > second) {
            var3 = first + second;
            second = first;
            var3 -= first;
         } else {
            var3 = first;
         }

         this.links.put(var3, second);
         return this;
      }

      ResearchDataBuilder() {
      }

      public ResearchData.ResearchDataBuilder stars(Map<Integer, StarData> stars) {
         this.stars = stars;
         return this;
      }

      public ResearchData.ResearchDataBuilder links(Multimap<Integer, Integer> links) {
         this.links = links;
         return this;
      }

      public ResearchData build() {
         return new ResearchData(this.stars, this.links);
      }

      @Override
      public String toString() {
         return "ResearchData.ResearchDataBuilder(stars=" + this.stars + ", links=" + this.links + ")";
      }
   }
}
