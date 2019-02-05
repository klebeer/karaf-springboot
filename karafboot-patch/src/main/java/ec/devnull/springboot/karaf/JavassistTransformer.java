package ec.devnull.springboot.karaf;

import javassist.*;

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

        File classesDir = new File(args[0]);

        new JavassistTransformer().instrumentClassesIn(classesDir);


    }

    private void instrumentClassesIn(File classesDir) throws NotFoundException {
        pool.appendClassPath(classesDir.getPath());

        Map<String, Map<String, Object>> targetClasses = getClasses();
        try {
            patchClasses(classesDir, targetClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void patchClasses(File classesDir, Map<String, Map<String, Object>> targetClasses) throws Exception {

        targetClasses.entrySet().stream().forEach(
                patch ->
                {
                    try {
                        patchMethod(classesDir, patch.getKey(), patch.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        CtClass nodeClass = pool.get("org.springframework.core.io.ClassPathResource");
        final CtClass stringClass = pool.get("java.lang.String");
        final CtClass classloaderClass = pool.get("java.lang.ClassLoader");
        CtConstructor ctConstructor = nodeClass.getDeclaredConstructor(new CtClass[]{stringClass, classloaderClass});
        ctConstructor.setBody("{ org.springframework.util.Assert.notNull($1, \"Path must not be null\"); java.lang.String pathToUse = org.springframework.util.StringUtils.cleanPath($1); if (pathToUse.equals(\"\")) { pathToUse = \"/\";} this.path = pathToUse; this.classLoader = classLoader != null ? classLoader : org.springframework.util.ClassUtils.getDefaultClassLoader(); }");
        nodeClass.writeFile(classesDir.getPath());
    }

    private void patchMethod(File classesDir, String targetClass, Map<String, Object> params) throws Exception {

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


        Map<String, Object> resourcePatch = new HashMap<>();
        final CtClass urlResource = pool.get("org.springframework.core.io.Resource");

        resourcePatch.put(METHOD, "doFindPathMatchingFileResources");
        resourcePatch.put(METHOD_PARAMS, new CtClass[]{urlResource, stringClass});
        resourcePatch.put(BODY_REPLACEMENT, "{  ec.devnull.springboot.karaf.BundleURL bundleURL=new ec.devnull.springboot.karaf.BundleURL(); return bundleURL.findResources($1,$2);  }");

        targetClasses.put("org.springframework.core.io.support.PathMatchingResourcePatternResolver", resourcePatch);

        Map<String, Object> tomcatPatch = new HashMap<>();

        tomcatPatch.put(METHOD, "registerURLStreamHandlerFactory");
        tomcatPatch.put(BODY_REPLACEMENT, "{ }");


        targetClasses.put("org.apache.catalina.webresources.StandardRoot", tomcatPatch);


        ClassClassPath ccpath = new ClassClassPath(org.hibernate.boot.archive.spi.ArchiveContext.class);
        pool.insertClassPath(ccpath);

        Map<String, Object> hibernatePatch = new HashMap<>();


        hibernatePatch.put(METHOD, "buildArchiveDescriptor");
        hibernatePatch.put(METHOD_PARAMS, new CtClass[]{urlClass, stringClass});

        hibernatePatch.put(BODY_REPLACEMENT, "{         final String protocol = $1.getProtocol();\n" +
                "        if ( \"jar\".equals( protocol ) ) {\n" +
                "            return new org.hibernate.boot.archive.internal.JarProtocolArchiveDescriptor( this, $1, $2 );\n" +
                "        }\n" +
                "        else if ( org.hibernate.internal.util.StringHelper.isEmpty( protocol )\n" +
                "                || \"file\".equals( protocol )\n" +
                "                || \"vfszip\".equals( protocol )\n" +
                "                || \"vfsfile\".equals( protocol ) ) {\n" +
                "            final java.io.File file = new java.io.File( extractLocalFilePath( $1 ) );\n" +
                "            if ( file.isDirectory() ) {\n" +
                "                return new org.hibernate.boot.archive.internal.ExplodedArchiveDescriptor( this, $1, $2 );\n" +
                "            }\n" +
                "            else {\n" +
                "                return new org.hibernate.boot.archive.internal.JarFileBasedArchiveDescriptor( this, $1, $2 );\n" +
                "            }\n" +
                "        }\n" +
                "        else {\n" +
                "            //let's assume the $1 can return the jar as a zip stream\n" +
                "            return new ec.devnull.springboot.karaf.JarInputStreamBasedArchiveDescriptor( this, $1, $2 );\n" +
                "        } }");


        targetClasses.put("org.hibernate.boot.archive.internal.StandardArchiveDescriptorFactory", hibernatePatch);


        return targetClasses;
    }


}
