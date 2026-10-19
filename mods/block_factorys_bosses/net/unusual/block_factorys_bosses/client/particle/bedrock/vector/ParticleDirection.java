package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.joml.Vector3f;

public interface ParticleDirection {
   void getDirection(Vector3f var1, Vector3f var2);

   static ParticleDirection deserialize(JsonElement directionJson) {
      if (directionJson instanceof JsonPrimitive jsonPrimitive && jsonPrimitive.isString()) {
         String directionString = jsonPrimitive.getAsString();
         if (directionString.equals("inwards")) {
            return InwardParticleDirection.INSTANCE;
         }

         if (directionString.equals("outwards")) {
            return OutwardParticleDirection.INSTANCE;
         }
      } else if (directionJson instanceof JsonArray jsonArray) {
         return ParticleVector.deserialize(jsonArray);
      }

      throw new IllegalArgumentException("Unknown shape direction: " + directionJson);
   }
}
