package ec.devnull.springboot.patch;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Parche en bytecode  para las clases ResourceUtils y StandardRoot para  subir en karaf un Uber Jar de springboot
 *
 * @author Kleber Ayala
 */
public class JavassistTransformer {


    public static final String METHOD = "method";
    public static final String METHOD_PARAMS = "methodParams";
    public static final String BODY_REPLACEMENT = "bodyReplacement";


    private final ClassPool pool = ClassPool.getDefault();

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument: directory with classes to transform");
        }
        File classesDir = new File(args[0]);

        new JavassistTransformer().instrumentClassesIn(classesDir);


    }

    private void instrumentClassesIn(File classesDir) throws NotFoundException {
        pool.appendClassPath(classesDir.getPath());
        Map<String, Map<String, Object>> targetClasses = getClasses();
        patchClasses(classesDir, targetClasses);


    }

    public void patchClasses(File classesDir, Map<String, Map<String, Object>> targetClasses) {

        targetClasses.entrySet().stream().forEach(
                patch ->
                        patchIt(classesDir, patch.getKey(), patch.getValue()))
        ;

    }

    private void patchIt(File classesDir, String targetClass, Map<String, Object> params) {
        try {
            final CtClass nodeClass = pool.get(targetClass);
            CtMethod ctMethod = null;

            if (params.get(METHOD_PARAMS) == null) {
                ctMethod = nodeClass.getDeclaredMethod((String) params.get(METHOD));
            } else {
                ctMethod = nodeClass.getDeclaredMethod((String) params.get(METHOD), (CtClass[]) params.get(METHOD_PARAMS));
            }
            String body = (String) params.get(BODY_REPLACEMENT);
            ctMethod.setBody(body);
            nodeClass.writeFile(classesDir.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Map<String, Map<String, Object>> getClasses() throws NotFoundException {

        Map<String, Map<String, Object>> targetClasses = new HashMap<>();
        Map<String, Object> springPatch = new HashMap<>();

        final CtClass stringClass = pool.get("java.lang.String");
        final CtClass urlClass = pool.get("java.net.URL");

        springPatch.put(METHOD, "getFile");
        springPatch.put(METHOD_PARAMS, new CtClass[]{urlClass, stringClass});
        springPatch.put(BODY_REPLACEMENT, "{try {return new java.io.File(toURI($1).getSchemeSpecificPart());}catch (java.net.URISyntaxException ex) {return new java.io.File($1.getFile());}}");

        targetClasses.put("org.springframework.util.ResourceUtils", springPatch);


        Map<String, Object> tomcatPatch = new HashMap<>();

        tomcatPatch.put(METHOD, "registerURLStreamHandlerFactory");
        tomcatPatch.put(BODY_REPLACEMENT, "{ }");


        targetClasses.put("org.apache.catalina.webresources.StandardRoot", tomcatPatch);
        return targetClasses;
    }


}
