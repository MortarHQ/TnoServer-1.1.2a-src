package me.wesley1808.servercore.neoforge.common;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent.Nodes;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContextKey;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;

public class NeoForgePermissions {
   private static final Map<String, PermissionNode<Boolean>> PERMISSIONS = build("command.config", "command.settings", "command.statistics");

   public static PermissionNode<Boolean> getPermissionNode(String node) {
      return PERMISSIONS.get(node);
   }

   public static void addNodes(Nodes event) {
      for (PermissionNode<Boolean> node : PERMISSIONS.values()) {
         event.addNodes(new PermissionNode[]{node});
      }
   }

   private static Map<String, PermissionNode<Boolean>> build(String... nodes) {
      Map<String, PermissionNode<Boolean>> permissions = new Object2ObjectOpenHashMap();

      for (String node : nodes) {
         permissions.put(node, new PermissionNode("servercore", node, PermissionTypes.BOOLEAN, (x, y, z) -> false, new PermissionDynamicContextKey[0]));
      }

      return permissions;
   }
}
