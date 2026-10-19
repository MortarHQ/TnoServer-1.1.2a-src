package de.keksuccino.konkrete.json.jsonpath;

public interface EvaluationListener {
   EvaluationListener.EvaluationContinuation resultFound(EvaluationListener.FoundResult var1);

   enum EvaluationContinuation {
      CONTINUE,
      ABORT;
   }

   interface FoundResult {
      int index();

      String path();

      Object result();
   }
}
