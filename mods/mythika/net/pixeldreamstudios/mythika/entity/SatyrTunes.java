package net.pixeldreamstudios.mythika.entity;

public final class SatyrTunes {
   public static final int REST = -1;
   private static final int REVEL_PHRASE_TICKS = 384;
   private static final int WAR_PHRASE_TICKS = 192;
   private static final int T = 8;
   private static final int W = 3;
   private static final SatyrTunes.Tune[] REVEL = new SatyrTunes.Tune[]{
      new SatyrTunes.Tune(12, 8, 15, new int[]{20, -1, 18, 16, 15, -1, 13, 15, 13, 11, 9, -1, 8, -1, -1, -1}),
      new SatyrTunes.Tune(8, 8, 15, new int[]{8, 10, 11, 13, -1, 15, 13, 11, 10, 8, -1, 11, 13, 11, 8, -1}),
      new SatyrTunes.Tune(6, 8, 15, new int[]{15, 15, 13, 15, 17, -1, 15, 13, 12, 13, 15, -1, 12, 10, 8, -1}),
      new SatyrTunes.Tune(12, 8, 13, new int[]{20, 18, 16, 14, -1, 16, 14, 13, 11, 9, 8, -1, 11, 9, 8, -1}),
      new SatyrTunes.Tune(8, 8, 15, new int[]{8, -1, 11, 13, 15, 16, 15, 13, -1, 11, 13, 11, 10, 8, -1, -1}),
      new SatyrTunes.Tune(6, 8, 15, new int[]{13, 15, 17, 15, 13, -1, 12, 13, 15, 13, 12, 10, 8, -1, 10, -1}),
      new SatyrTunes.Tune(8, 8, 15, new int[]{17, 15, 14, 15, 17, -1, 19, 17, 15, 14, 12, 10, -1, 12, 10, 8}),
      new SatyrTunes.Tune(8, 8, 15, new int[]{15, 16, 17, -1, 15, -1, 13, 10, 9, 8, -1, 9, 10, 13, -1, -1}),
      new SatyrTunes.Tune(12, 8, 15, new int[]{20, -1, 16, 15, -1, 16, 15, 13, 9, 8, -1, 9, 13, 9, 8, -1}),
      new SatyrTunes.Tune(
         8,
         8,
         15,
         new int[]{
            13,
            20,
            -1,
            20,
            -1,
            -1,
            17,
            18,
            20,
            18,
            -1,
            -1,
            17,
            -1,
            18,
            20,
            18,
            17,
            15,
            13,
            -1,
            15,
            11,
            -1,
            13,
            17,
            20,
            18,
            17,
            18,
            17,
            13,
            -1,
            15,
            11,
            -1,
            13,
            17,
            15,
            18,
            20,
            17,
            13,
            13,
            -1,
            13,
            10,
            8
         }
      ),
      new SatyrTunes.Tune(
         8,
         5,
         8,
         15,
         new int[]{
            8,
            -1,
            4,
            8,
            10,
            11,
            -1,
            11,
            10,
            10,
            8,
            -1,
            4,
            8,
            -1,
            8,
            4,
            4,
            4,
            8,
            8,
            10,
            11,
            10,
            8,
            10,
            8,
            4,
            8,
            -1,
            8,
            -1,
            8,
            8,
            -1,
            11,
            11,
            8,
            11,
            11,
            10,
            8,
            4,
            8,
            -1,
            8,
            4,
            4,
            8,
            -1
         }
      )
   };
   private static final SatyrTunes.Tune[] WAR = new SatyrTunes.Tune[]{
      new SatyrTunes.Tune(4, 3, 10, new int[]{3, 3, 6, 3, 8, 6, 3, -1, 3, 3, 6, 8, 10, 8, 6, 3}),
      new SatyrTunes.Tune(4, 3, 10, new int[]{3, 3, 4, 6, 4, 3, -1, 3, 8, 10, 8, 6, 4, 3, 4, 3}),
      new SatyrTunes.Tune(4, 3, 10, new int[]{10, 11, 10, 8, 5, 4, 3, -1, 3, 4, 5, 8, 10, 11, 12, -1}),
      new SatyrTunes.Tune(6, 3, 8, new int[]{3, 9, 3, 9, 8, 6, 4, 3, -1, 9, 8, 9, 11, 9, 8, 6}),
      new SatyrTunes.Tune(6, 3, 10, new int[]{3, 4, 3, 4, 8, -1, 4, 3, 10, 11, 10, 8, 4, 3, 4, 3})
   };
   private static final int[] REVEL_STARTS = starts(REVEL, 384);
   private static final int REVEL_ROUND = round(REVEL, 384);
   private static final int[] WAR_STARTS = starts(WAR, 192);
   private static final int WAR_ROUND = round(WAR, 192);

   private SatyrTunes() {
   }

   private static int[] starts(SatyrTunes.Tune[] tunes, int target) {
      int[] out = new int[tunes.length];

      for (int i = 1; i < tunes.length; i++) {
         out[i] = out[i - 1] + tunes[i - 1].phraseTicks(target);
      }

      return out;
   }

   private static int round(SatyrTunes.Tune[] tunes, int target) {
      int total = 0;

      for (SatyrTunes.Tune tune : tunes) {
         total += tune.phraseTicks(target);
      }

      return total;
   }

   public static int revelNote(long gameTime, long fireSeed, boolean drone) {
      return noteFrom(REVEL, REVEL_STARTS, REVEL_ROUND, gameTime, fireSeed, drone);
   }

   public static int warNote(long gameTime, boolean drone) {
      return noteFrom(WAR, WAR_STARTS, WAR_ROUND, gameTime, 0L, drone);
   }

   private static int noteFrom(SatyrTunes.Tune[] tunes, int[] starts, int roundTicks, long gameTime, long seed, boolean drone) {
      int open = starts[Math.floorMod(seed, tunes.length)];
      int now = Math.floorMod(gameTime + open, roundTicks);
      int i = tunes.length - 1;

      while (starts[i] > now) {
         i--;
      }

      return noteOf(tunes[i], now - starts[i], drone);
   }

   private static int noteOf(SatyrTunes.Tune tune, int tick, boolean drone) {
      if (tick % tune.beatTicks() != 0) {
         return -1;
      }

      int beat = tick / tune.beatTicks();
      if (drone) {
         if (beat % tune.droneEvery() != 0) {
            return -1;
         } else {
            return beat / tune.droneEvery() % 2 == 0 ? tune.droneRoot() : tune.droneAnswer();
         }
      } else {
         return tune.melody()[beat % tune.melody().length];
      }
   }

   public static float pitch(int note) {
      return (float)Math.pow(2.0, (note - 12) / 12.0);
   }

   public static double colour(int note) {
      return note / 24.0;
   }

   private record Tune(int beatTicks, int droneEvery, int droneRoot, int droneAnswer, int[] melody) {
      Tune(int beatTicks, int droneRoot, int droneAnswer, int[] melody) {
         this(beatTicks, 4, droneRoot, droneAnswer, melody);
      }

      int phraseTicks(int target) {
         int pass = this.beatTicks * this.melody.length;
         return pass * Math.max(1, Math.round((float)target / pass));
      }
   }
}
