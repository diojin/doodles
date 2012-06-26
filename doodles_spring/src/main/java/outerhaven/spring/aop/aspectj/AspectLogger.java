package outerhaven.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;

public class AspectLogger {
        public void logEntry(JoinPoint joinPoint) {
                log("logEntry():before in " + joinPoint.getSignature().getName() + "()");
        }

        public void logExit(JoinPoint joinPoint) {
                log("logExit():after in " + joinPoint.getSignature().getName() + "()");
        }

        public void logAroundAdvice(JoinPoint joinPoint) {
                log("logAroundAdvice() in " + joinPoint.getSignature().getName() + "()");
        }

        public void logExitAfterReturn(JoinPoint joinPoint) {
                log("logExitAfterReturn():after-returning in " + joinPoint.getSignature().getName() + "()");
        }

        public void logAfterThrowsAdvice(JoinPoint joinPoint) {
                log("logAfterThrowsAdvice():after-throwing in " + joinPoint.getSignature().getName() + "()");
        }

        public static void log(String LogMessage) {
                System.err.println(LogMessage);
        }
}