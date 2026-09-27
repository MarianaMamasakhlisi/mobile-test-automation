package mobiletests.support;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

// Wires RetryAnalyzer into every @Test automatically, so individual test classes don't each
// need retryAnalyzer = RetryAnalyzer.class repeated on every method.
public class RetryTransformer implements IAnnotationTransformer {

    @Override
    @SuppressWarnings({"rawtypes"})
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
