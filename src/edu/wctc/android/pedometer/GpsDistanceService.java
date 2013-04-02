package edu.wctc.android.pedometer;

public class GpsDistanceService {

	public enum Unit {
		MILES, KILOMETERS, NAUTICAL_MILES, FEET
	}

	public double calculateDistance(double latitudeStart,
			double longitudeStart, double latitudeEnd, double longitudeEnd,
			Unit unit) {

		double theta = longitudeStart - longitudeEnd;
		double calculation = Math.sin(convertToRadians(latitudeStart))
				* Math.sin(convertToRadians(latitudeEnd))
				+ Math.cos(convertToRadians(latitudeStart))
				* Math.cos(convertToRadians(latitudeEnd))
				* Math.cos(convertToRadians(theta));
		calculation = Math.acos(calculation);
		calculation = convertToDegrees(calculation);
		calculation = calculation * 60 * 1.1515;

		double distance = 0.0;

		if (unit == Unit.KILOMETERS) {
			distance = calculation * 1.609344;
		} else if (unit == Unit.NAUTICAL_MILES) {
			distance = calculation * 0.8684;
		} else if (unit == Unit.MILES) {
			distance = calculation;
		} else if (unit == Unit.FEET) {
			distance = calculation * 5280;
		}

		return distance;
	}

	private double convertToRadians(double degrees) {
		return (degrees * Math.PI / 180.0);
	}

	private double convertToDegrees(double radians) {
		return (radians * 180.0 / Math.PI);
	}

	public static void main(String[] args) {

		GpsDistanceService service = new GpsDistanceService();

		System.out.println(service.calculateDistance(32.9697, -96.80322,
				29.46786, -98.53506, Unit.MILES) + " Miles\n");
		System.out.println(service.calculateDistance(32.9697, -96.80322,
				29.46786, -98.53506, Unit.KILOMETERS) + " Kilometers\n");
		System.out
				.println(service.calculateDistance(32.9697, -96.80322,
						29.46786, -98.53506, Unit.NAUTICAL_MILES)
						+ " Nautical Miles\n");
	}
}