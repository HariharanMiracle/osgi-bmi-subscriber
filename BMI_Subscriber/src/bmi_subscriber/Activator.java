package bmi_subscriber;

import bmi_publisher.BmiPublishService;
import model.BMI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	ServiceReference serviceReference; 
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Subscriber => start");
		serviceReference = context.getServiceReference(BmiPublishService.class.getName());
		BmiPublishService bmiPublishService = (BmiPublishService) context.getService(serviceReference);
//		BMI bmi = (BMI) context.getService(serviceReference);
		//implement dunctions
		bmiPublishService.testRun();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Subscriber => stop");
		context.ungetService(serviceReference);
	}

}
