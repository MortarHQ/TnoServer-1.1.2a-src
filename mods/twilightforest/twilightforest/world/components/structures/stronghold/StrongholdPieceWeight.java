package twilightforest.world.components.structures.stronghold;

public class StrongholdPieceWeight {
   public final KnightStrongholdComponent.Factory<? extends KnightStrongholdComponent> factory;
   public final int pieceWeight;
   public int instancesSpawned;
   public final int instancesLimit;
   public final int minimumDepth;

   public <T extends KnightStrongholdComponent> StrongholdPieceWeight(KnightStrongholdComponent.Factory<T> factory, int weight, int limit) {
      this(factory, weight, limit, 0);
   }

   public <T extends KnightStrongholdComponent> StrongholdPieceWeight(KnightStrongholdComponent.Factory<T> factory, int weight, int limit, int minDepth) {
      this.factory = factory;
      this.pieceWeight = weight;
      this.instancesLimit = limit;
      this.minimumDepth = minDepth;
      this.instancesSpawned = 0;
   }

   public boolean isDeepEnough(int depth) {
      return this.canSpawnMoreStructures() && depth >= this.minimumDepth;
   }

   public boolean canSpawnMoreStructures() {
      return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
   }

   public StrongholdPieceWeight recount() {
      return new StrongholdPieceWeight(this.factory, this.pieceWeight, this.instancesLimit, this.minimumDepth);
   }
}
