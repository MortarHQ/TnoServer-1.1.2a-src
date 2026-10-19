package twilightforest.beanification;

public class CircularDependencyException extends RuntimeException {
   private static final long serialVersionUID = 5485181528021698444L;

   public CircularDependencyException(String message) {
      super(message);
   }
}
