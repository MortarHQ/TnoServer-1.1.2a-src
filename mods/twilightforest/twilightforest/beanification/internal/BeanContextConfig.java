package twilightforest.beanification.internal;

public class BeanContextConfig {
   private final BeanContextConfig.LoggingSettings loggingSettings = new BeanContextConfig.LoggingSettings();

   public BeanContextConfig.LoggingSettings loggingSettings() {
      return this.loggingSettings;
   }

   public class LoggingSettings {
      private boolean injectInto = false;

      public BeanContextConfig enableInjectInto() {
         this.injectInto = true;
         return BeanContextConfig.this;
      }

      public boolean isInjectIntoEnabled() {
         return this.injectInto;
      }
   }
}
