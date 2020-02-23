package bmi_subscriber;

import bmi_publisher.BmiPublishService;
import model.BMI;

import java.util.Scanner;

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
		Scanner scanner = new Scanner(System.in);
		
		serviceReference = context.getServiceReference(BmiPublishService.class.getName());
		BmiPublishService bmiPublishService = (BmiPublishService) context.getService(serviceReference);
		String continueX = "";
		
		do {
			BMI bmi = new BMI();
			
			System.out.println("Enter a unit for weight (g)(Kg)(t)(lb): ");
			String unitWeight = scanner.next();
			bmi.setWeightUnit(unitWeight);
			System.out.println("Enter a weight: ");
			double weight = scanner.nextDouble();
			bmi.setWeight(weight);
			
			double weightInKG = bmiPublishService.standardizedWeight(bmi.getWeight(), bmi.getWeightUnit());
			if(weightInKG == -1) {
				System.out.println("Invalid unit in weight");
				break;
			}
			else if(weightInKG == -2) {
				System.out.println("Invalid value in weight");
				break;
			}
			bmi.setWeightInKG(weightInKG);
			
			System.out.println("Enter a unit for height (m)(cm)(inches)(km): ");
			String unitHeight = scanner.next();
			bmi.setHeightUnit(unitHeight);
			System.out.println("Enter a height: ");
			double height = scanner.nextDouble();
			bmi.setHeight(height);
			
			double heightInM = bmiPublishService.standardizedHeight(bmi.getHeight(), bmi.getHeightUnit());
			if(heightInM == -1) {
				System.out.println("Invalid value in length");
				break;
			}
			else if(heightInM == -2) {
				System.out.println("Invalid value in length");
				break;
			}
			bmi.setHeightInM(heightInM);
			
			bmi.setBmiValue(bmiPublishService.calculateBMI(bmi.getWeightInKG(), bmi.getHeightInM()));
			
			bmiPublishService.suggestHealthTips(bmi.getBmiValue());
			
			System.out.println("Do you want to continue (y/n): ");
			continueX = scanner.next();
		} while(continueX.equalsIgnoreCase("y"));
		System.out.println("Thankyou for using our service :)");
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
