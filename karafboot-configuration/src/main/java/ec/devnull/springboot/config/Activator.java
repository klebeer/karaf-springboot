/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ec.devnull.springboot.config;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Dynamically registers services based on configuration files that starts with karaf.springboot-*
 *
 * @author Kleber Ayala
 */
public class Activator implements BundleActivator {

    private static final String FACTORY_PID = "karaf.springboot";

    private ServiceRegistration manServiceRegistration;

    @Override
    public void start(BundleContext context) {
        Dictionary<String, String> props = new Hashtable<>();
        props.put(Constants.SERVICE_PID, FACTORY_PID);
        SrpingBootConfigManager configManager = new SrpingBootConfigManager(context);
        manServiceRegistration = context.registerService(ManagedServiceFactory.class.getName(), configManager, props);
    }

    @Override
    public void stop(BundleContext context) {
        manServiceRegistration.unregister();
    }

}
