package cn.leolezury.eternalstarlight.common.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;

public class MazeGenerator {
   private final int size;
   private final boolean[][] maze;
   private RandomSource random;

   public MazeGenerator(int size, RandomSource random) {
      this.size = size;
      this.maze = new boolean[size][size];
      this.random = random;
   }

   public void setRandom(RandomSource random) {
      this.random = random;
   }

   public boolean[][] generateMaze(int startX, int startY) {
      for (int i = 0; i < this.size; i++) {
         Arrays.fill(this.maze[i], true);
      }

      this.generate(startX, startY);
      return this.maze;
   }

   private void generate(int x, int y) {
      this.maze[x][y] = false;
      List<Integer> directions = Arrays.asList(0, 1, 2, 3);
      Util.shuffle(directions, this.random);

      for (int direction : directions) {
         int dx = x + 2 * this.getDX(direction);
         int dy = y + 2 * this.getDY(direction);
         if (this.isInRange(dx, dy) && this.maze[dx][dy]) {
            this.maze[x + this.getDX(direction)][y + this.getDY(direction)] = false;
            this.generate(dx, dy);
         }
      }
   }

   private boolean isInRange(int x, int y) {
      return x >= 0 && x < this.size && y >= 0 && y < this.size;
   }

   private int getDX(int direction) {
      if (direction == 1) {
         return -1;
      } else {
         return direction == 3 ? 1 : 0;
      }
   }

   private int getDY(int direction) {
      if (direction == 0) {
         return -1;
      } else {
         return direction == 2 ? 1 : 0;
      }
   }
}
