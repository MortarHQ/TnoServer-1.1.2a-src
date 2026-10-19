package team.lodestar.lodestone.systems.model.obj.modifier;

public interface ModifierQueue {
   void queueEarlyModifier(ModelModifier var1);

   void queueModifier(ModelModifier var1);
}
