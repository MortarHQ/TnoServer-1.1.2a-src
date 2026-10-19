package xaero.map.world;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class MapConnectionManager {
   private Map<MapConnectionNode, Set<MapConnectionNode>> allConnections = new HashMap<>();

   public void addConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
      this.addOneWayConnection(mapKey1, mapKey2);
      this.addOneWayConnection(mapKey2, mapKey1);
   }

   private void addOneWayConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
      Set<MapConnectionNode> connections = this.allConnections.get(mapKey1);
      if (connections == null) {
         this.allConnections.put(mapKey1, connections = new HashSet<>());
      }

      connections.add(mapKey2);
   }

   public void removeConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
      this.removeOneWayConnection(mapKey1, mapKey2);
      this.removeOneWayConnection(mapKey2, mapKey1);
   }

   private void removeOneWayConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
      Set<MapConnectionNode> connections = this.allConnections.get(mapKey1);
      if (connections != null) {
         connections.remove(mapKey2);
      }
   }

   public boolean isConnected(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
      if (mapKey1 == null || mapKey2 == null) {
         return false;
      }

      if (mapKey1.equals(mapKey2)) {
         return true;
      }

      Set<MapConnectionNode> connections = this.allConnections.get(mapKey1);
      return connections == null ? false : connections.contains(mapKey2);
   }

   public boolean isEmpty() {
      return this.allConnections.isEmpty();
   }

   public void save(PrintWriter writer) {
      if (!this.allConnections.isEmpty()) {
         Set<String> redundantConnections = new HashSet<>();

         for (Entry<MapConnectionNode, Set<MapConnectionNode>> entry : this.allConnections.entrySet()) {
            MapConnectionNode mapKey = entry.getKey();

            for (MapConnectionNode c : entry.getValue()) {
               String fullConnection = mapKey + ":" + c;
               if (!redundantConnections.contains(fullConnection)) {
                  writer.println("connection:" + fullConnection);
                  redundantConnections.add(c + ":" + mapKey);
               }
            }
         }
      }
   }

   private void swapConnections(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
      Set<MapConnectionNode> connections1 = new HashSet<>(this.allConnections.getOrDefault(mapKey1, new HashSet<>()));
      Set<MapConnectionNode> connections2 = new HashSet<>(this.allConnections.getOrDefault(mapKey2, new HashSet<>()));

      for (MapConnectionNode c : connections1) {
         this.removeConnection(mapKey1, c);
      }

      for (MapConnectionNode c : connections2) {
         this.addConnection(mapKey1, c);
      }

      for (MapConnectionNode c : connections2) {
         this.removeConnection(mapKey2, c);
      }

      for (MapConnectionNode c : connections1) {
         this.addConnection(mapKey2, c);
      }
   }

   public void renameDimension(String oldName, String newName) {
      for (MapConnectionNode mapKey : new HashSet<>(this.allConnections.keySet())) {
         if (mapKey.getDimId().location().toString().equals(oldName)) {
            String mwPart = mapKey.getMw();
            this.swapConnections(mapKey, new MapConnectionNode(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(newName)), mwPart));
         }
      }
   }
}
