package team.lodestar.lodestone.systems.model.obj.modifier;

import team.lodestar.lodestone.systems.model.obj.IndexedModel;
import team.lodestar.lodestone.systems.model.obj.ObjParser;
import team.lodestar.lodestone.systems.model.obj.data.IndexedMesh;

public abstract class ModelModifier {
   public void applyEarly(ObjParser.Builder parsedModel) {
   }

   public abstract void apply(IndexedModel var1);

   public abstract void apply(IndexedModel var1, IndexedMesh var2);
}
