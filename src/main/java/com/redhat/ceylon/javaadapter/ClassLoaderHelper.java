package com.redhat.ceylon.javaadapter;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;
import org.jboss.modules.ModuleLoader;

/**
 * @author Matej Lazar
 */
public class ClassLoaderHelper {

    /**
     * Helper method to create instances of given class in a module.
     * Method trys to use modular class loader, if it is not available (running from IDE) a flat one is used.
     */
    public static Object createInstance(Object obj, String moduleName, String className, String moduleSlot) throws ModuleLoadException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader cl = obj.getClass().getClassLoader();
        if (cl instanceof ModuleClassLoader) {
            ModuleClassLoader mcl = (ModuleClassLoader)cl;
            ModuleLoader ml =  mcl.getModule().getModuleLoader();
            ModuleIdentifier identifier = ModuleIdentifier.create(moduleName, moduleSlot);
            Module module = ml.loadModule(identifier);
            cl = module.getClassLoader();
        }
        Class<?> clazz = cl.loadClass(className);
        return clazz.newInstance();
    }

}
