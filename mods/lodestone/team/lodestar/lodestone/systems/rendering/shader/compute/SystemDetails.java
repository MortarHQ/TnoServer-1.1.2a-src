package team.lodestar.lodestone.systems.rendering.shader.compute;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;

@EventBusSubscriber(value = Dist.CLIENT, modid = "lodestone", bus = Bus.MOD)
public class SystemDetails {
   private static boolean initialized = false;
   private static String VENDOR;
   private static String RENDERER;
   private static String VERSION;
   private static String SHADING_LANGUAGE_VERSION;
   private static boolean COMPUTE_SUPPORTED = false;
   private static boolean SSBO_SUPPORTED = false;
   private static int MAX_SSBO_BINDINGS = -1;
   private static int MAX_SHADER_STORAGE_BLOCK_SIZE = -1;

   public static String getVendor() {
      init();
      return VENDOR;
   }

   public static String getRenderer() {
      init();
      return RENDERER;
   }

   public static String getVersion() {
      init();
      return VERSION;
   }

   public static int[] getOpenglVersion() {
      init();
      String version = getVersion();
      String[] parts = version.split(" ")[0].split("\\.");
      return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
   }

   public static String getShadingLanguageVersion() {
      init();
      return SHADING_LANGUAGE_VERSION;
   }

   public static boolean isComputeSupported() {
      init();
      return COMPUTE_SUPPORTED;
   }

   public static boolean isSsboSupported() {
      init();
      return SSBO_SUPPORTED;
   }

   public static int getMaxSsboBindings() {
      init();
      return MAX_SSBO_BINDINGS;
   }

   public static int getMaxShaderStorageBlockSize() {
      return MAX_SHADER_STORAGE_BLOCK_SIZE;
   }

   private static void fetchDeviceData() {
      VENDOR = GL43.glGetString(7936);
      RENDERER = GL43.glGetString(7937);
      VERSION = GL43.glGetString(7938);
      SHADING_LANGUAGE_VERSION = GL43.glGetString(35724);
      GLCapabilities caps = GL.getCapabilities();
      COMPUTE_SUPPORTED = caps.OpenGL43 || caps.GL_ARB_compute_shader;
      SSBO_SUPPORTED = caps.OpenGL43 || caps.GL_ARB_shader_storage_buffer_object;
      MAX_SSBO_BINDINGS = SSBO_SUPPORTED ? GL43.glGetInteger(37085) : 0;
      MAX_SHADER_STORAGE_BLOCK_SIZE = SSBO_SUPPORTED ? GL43.glGetInteger(37086) : 0;
   }

   public static void init() {
      if (!initialized) {
         fetchDeviceData();
         initialized = true;
      }
   }

   @SubscribeEvent
   public static void fetchDeviceData(RegisterShadersEvent event) {
      init();
   }
}
