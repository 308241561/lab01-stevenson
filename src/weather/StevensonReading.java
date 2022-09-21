package weather;

/**
 * This is a StevensonReading class whose primary purpose is to store a reading that was taken from
 * a Stevenson Station, the only thing an object of this class needs to do is to provide different
 * values related to the reading. Each instance represents a single reading of a weather station.
 */
public final class StevensonReading implements WeatherReading {

  private final double airTemp;  //the air temperature in Celsius.
  private final double dewPointTemp; //the dew point temperature in Celsius no greater than airTemp.
  private final double windSpeed; //the non-negative wind speed in miles per hour.
  private final int totalRainRec; //and the non-negative total rain received in millimeters.

  /**
   * Construct a StevensonReading object using airTemp, dewPointTemp, windSpeed, totalRainRec.
   *
   * @param airTemp      the air temperature in Celsius,
   * @param dewPointTemp the dew point temperature in Celsius no greater than the air temperature
   * @param windSpeed    the non-negative wind speed in miles per hour
   * @param totalRainRec non-negative total rain received in millimeters in the last 24 hours
   * @throws IllegalArgumentException throws illegalArgumentException if air temp is lower than dew
   *                                  point temp or wind speed is negative, or totalRainRec is
   *                                  negative
   */
  public StevensonReading(double airTemp, double dewPointTemp, double windSpeed, int totalRainRec)
      throws IllegalArgumentException {
    if ((windSpeed < 0) || (totalRainRec < 0)) {
      throw new IllegalArgumentException("Negative wind speed or rain received are not supported");
    }

    if (airTemp < dewPointTemp) {
      throw new IllegalArgumentException(
          "Air temperature should not be lower than dew point temperature");
    }

    this.airTemp = airTemp;
    this.dewPointTemp = dewPointTemp;
    this.windSpeed = windSpeed;
    this.totalRainRec = totalRainRec;
  }

  /**
   * This method is to return the air temperature of the instance in celsius.
   *
   * @return the air temperature of the instance in celsius.
   */
  @Override
  public int getTemperature() {
    return (int) (Math.round(this.airTemp));
  }

  /**
   * This method is to return the dew point temperature of the instance in celsius.
   *
   * @return the dew point temperature of the instance in celsius
   */
  @Override
  public int getDewPoint() {
    return (int) (Math.round(this.dewPointTemp));
  }

  /**
   * This method is to return the wind speed of the instance in miles per hour.
   *
   * @return the wind speed of the instance in miles per hour
   */
  @Override
  public int getWindSpeed() {
    return (int) (Math.round(this.windSpeed));
  }

  /**
   * This method is to return the total rain received in mm in the last 24 hours.
   *
   * @return the total rain received in mm in the last 24 hours
   */
  @Override
  public int getTotalRain() {
    return Math.round(this.totalRainRec);
  }

  @Override
  public int getRelativeHumidity() {
    double actualVp = 6.11 * 10 * (7.5 * this.dewPointTemp) / (237.3 + this.dewPointTemp);
    double saturatedVp = 6.11 * 10 * (7.5 * this.airTemp) / (237.3 + this.airTemp);
    double res = Math.round(actualVp * 100 / saturatedVp);
    return (int) (res);
  }

  /**
   * Get the heat index (in Celsius) for this weather reading rounded to the nearest integer.
   *
   * @return the heat index
   */
  @Override
  public int getHeatIndex() {
    final double c1 = -8.78469475556;
    final double c2 = 1.61139411;
    final double c3 = 2.33854883889;
    final double c4 = -0.14611605;
    final double c5 = -0.012308094;
    final double c6 = -0.0164248277778;
    final double c7 = 0.002211732;
    final double c8 = 0.00072546;
    final double c9 = -0.000003582;

    double airT = this.airTemp;

    double actualVp = 6.11 * 10 * (7.5 * this.dewPointTemp) / (237.3 + this.dewPointTemp);
    double saturatedVp = 6.11 * 10 * (7.5 * this.airTemp) / (237.3 + this.airTemp);
    double relativeH = actualVp * 100 / saturatedVp;

    double heatIndex =
        c1 + c2 * airT + c3 * relativeH + c4 * airT * relativeH + c5 * Math.pow(airT, 2.0)
            + c6 * Math.pow(relativeH, 2.0) + c7 * Math.pow(airT, 2.0) * relativeH
            + c8 * airT * Math.pow(relativeH, 2.0) + c9 * Math.pow(airT, 2.0) * Math.pow(relativeH,
            2.0);

    heatIndex = Math.round(heatIndex);

    return (int) (heatIndex);
  }

  /**
   * Get the wind chill (in Celsius) rounded to the nearest integer.
   *
   * @return the wind chill
   */
  @Override
  public int getWindChill() {
    double feTemp = (9.0 / 5.0) * this.airTemp + 32;
    double windC =
        35.74 + (0.6215 * feTemp) - (35.75 * (Math.pow(this.windSpeed, 0.16))) + (0.4275 * feTemp
            * (Math.pow(this.windSpeed, 0.16)));
    double res = Math.round((windC - 32) * 5.0 / 9.0);
    return (int) res;
  }

  /**
   * To return a string of the representation, the form is: Reading: T = 23, D = 12, v = 3, rain =
   * 12.
   *
   * @return the string representation of the stevensonreading object
   */
  @Override
  public String toString() {
    return String.format("Reading: T = %d, D = %d, v = %d, rain = %d", Math.round(this.airTemp),
        Math.round(this.dewPointTemp), Math.round(this.windSpeed), Math.round(this.totalRainRec));
  }

  /**
   * To override the equals method inherited from object class.
   *
   * @param o object
   * @return true if the two comparing objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { // backward compatibility with default equals
      return true;
    }
    if (o instanceof WeatherReading) {
      WeatherReading test = (WeatherReading) o;
      return (test.getTemperature() == this.getTemperature()
          && test.getDewPoint() == this.getDewPoint() && test.getWindSpeed() == this.getWindSpeed()
          && test.getTotalRain() == this.getTotalRain());
    }
    return false;
  }

  /**
   * To provide the hashCode of the instance.
   *
   * @return hashCode of the instance
   */
  @Override
  public int hashCode() {

    return Long.hashCode(
        this.getTemperature() + this.getDewPoint()
            + this.getWindSpeed() + this.getTotalRain());
  }
}
