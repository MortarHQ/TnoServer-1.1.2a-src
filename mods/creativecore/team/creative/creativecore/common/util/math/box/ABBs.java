package team.creative.creativecore.common.util.math.box;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ABBs implements Iterable<ABB> {
   private List<ABB> boxes = new ArrayList<>();

   public static void cutOut(List<ABB> result, ABB bb, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      if (bb.intersects(minX, minY, minZ, maxX, maxY, maxZ)) {
         for (int x = 0; x < 3; x++) {
            double startX;
            double endX;
            switch (x) {
               case 0:
                  if (bb.minX >= minX) {
                     continue;
                  }

                  startX = bb.minX;
                  endX = minX;
                  break;
               case 1:
                  startX = Math.max(bb.minX, minX);
                  endX = Math.min(bb.maxX, maxX);
                  break;
               case 2:
                  if (bb.maxX < maxX) {
                     continue;
                  }

                  startX = maxX;
                  endX = bb.maxX;
                  break;
               default:
                  throw new UnsupportedOperationException();
            }

            for (int y = 0; y < 3; y++) {
               double startY;
               double endY;
               switch (y) {
                  case 0:
                     if (bb.minY >= minY) {
                        continue;
                     }

                     startY = bb.minY;
                     endY = minY;
                     break;
                  case 1:
                     startY = Math.max(bb.minY, minY);
                     endY = Math.min(bb.maxY, maxY);
                     break;
                  case 2:
                     if (bb.maxY < maxY) {
                        continue;
                     }

                     startY = maxY;
                     endY = bb.maxY;
                     break;
                  default:
                     throw new UnsupportedOperationException();
               }

               for (int z = 0; z < 3; z++) {
                  double startZ;
                  double endZ;
                  switch (z) {
                     case 0:
                        if (bb.minZ >= minZ) {
                           continue;
                        }

                        startZ = bb.minZ;
                        endZ = minZ;
                        break;
                     case 1:
                        startZ = Math.max(bb.minZ, minZ);
                        endZ = Math.min(bb.maxZ, maxZ);
                        break;
                     case 2:
                        if (bb.maxZ < maxZ) {
                           continue;
                        }

                        startZ = maxZ;
                        endZ = bb.maxZ;
                        break;
                     default:
                        throw new UnsupportedOperationException();
                  }

                  if (x != 1 || y != 1 || z != 1) {
                     result.add(new ABB(startX, startY, startZ, endX, endY, endZ));
                  }
               }
            }
         }
      }
   }

   public ABBs(VoxelShape shape) {
      this.addShape(shape);
   }

   public ABBs(AABB bb) {
      this.boxes.add(new ABB(bb));
   }

   public ABBs(ABB bb) {
      this.boxes.add(bb.copy());
   }

   public void addShape(VoxelShape shape) {
      if (shape instanceof BoxesVoxelShape bs) {
         for (ABB bb : bs.boxes) {
            this.boxes.add(new ABB(bb));
         }
      } else {
         for (AABB bb : shape.toAabbs()) {
            this.boxes.add(new ABB(bb));
         }
      }
   }

   public void cutOutVanilla(Iterable<AABB> bbs) {
      for (AABB bb : bbs) {
         this.cutOut(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
      }
   }

   public void cutOut(Iterable<ABB> bbs) {
      for (ABB bb : bbs) {
         this.cutOut(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
      }
   }

   public void cutOut(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      List<ABB> newBoxes = new ArrayList<>(this.boxes);

      for (ABB bb : this.boxes) {
         cutOut(newBoxes, bb, minX, minY, minZ, maxX, maxY, maxZ);
      }

      this.boxes = newBoxes;
      this.optimize();
   }

   public void intersectionVanilla(Iterable<AABB> bbs) {
      for (AABB bb : bbs) {
         this.cutOut(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
      }
   }

   public void intersection(Iterable<ABB> bbs) {
      List<ABB> oldBoxes = this.boxes;
      this.boxes = new ArrayList<>();

      for (ABB bb : oldBoxes) {
         for (ABB bb2 : bbs) {
            if (bb.intersects(bb2)) {
               this.addNonOverlapping(
                  new ABB(
                     Math.max(bb.minX, bb2.minX),
                     Math.max(bb.minY, bb2.minY),
                     Math.max(bb.minZ, bb2.minZ),
                     Math.min(bb.maxX, bb2.maxX),
                     Math.min(bb.maxY, bb2.maxY),
                     Math.min(bb.maxZ, bb2.maxZ)
                  )
               );
            }
         }
      }
   }

   public boolean isEmpty() {
      return this.boxes.isEmpty();
   }

   public List<ABB> getBoxes() {
      return this.boxes;
   }

   public void addNonOverlappingVanilla(Iterable<AABB> bbs) {
      for (AABB bb : bbs) {
         this.addNonOverlapping(new ABB(bb));
      }
   }

   public void addNonOverlapping(Iterable<ABB> bbs) {
      for (ABB bb : bbs) {
         this.addNonOverlapping(bb);
      }
   }

   public void addNonOverlapping(ABB bb) {
      List<ABB> toAdd = new ArrayList<>();
      toAdd.add(bb);
      List<ABB> newlyCut = new ArrayList<>();

      for (ABB cutter : this.boxes) {
         newlyCut.clear();

         for (ABB cutted : toAdd) {
            cutOut(newlyCut, cutted, cutter.minX, cutter.minY, cutter.minZ, cutter.maxX, cutter.maxY, cutter.maxZ);
         }

         List<ABB> temp = newlyCut;
         newlyCut = toAdd;
         toAdd = temp;
      }

      this.boxes.addAll(toAdd);
   }

   public void optimize() {
      boolean modified = true;

      while (modified) {
         modified = false;

         for (int i = 0; i < this.boxes.size(); i++) {
            int j = 0;

            while (j < this.boxes.size()) {
               if (i != j) {
                  ABB box = this.boxes.get(i).combine(this.boxes.get(j));
                  if (box != null) {
                     this.boxes.set(i, box);
                     this.boxes.remove(j);
                     modified = true;
                     if (i > j) {
                        i--;
                     }
                     continue;
                  }
               }

               j++;
            }
         }
      }
   }

   @Override
   public Iterator<ABB> iterator() {
      return this.boxes.iterator();
   }
}
