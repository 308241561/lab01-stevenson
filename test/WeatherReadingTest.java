import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import weather.StevensonReading;
import weather.WeatherReading;


/**
 * This is the test class for WeatherReading class.
 */
public class WeatherReadingTest {

  private WeatherReading a9d8w7r6;

  @Before
  public void setUp() {
    a9d8w7r6 = adwr(9, 8, 7, 6);
  }

  /**
   * This method is providing short-hand way of creating instances of a new WeatherReading object.
   *
   * @param airTemp      the initial air temperature
   * @param dewPointTemp the initial dew point temperature
   * @param windSpeed    the initial wind speed
   * @param totalRainRec the initial total rain received
   * @return a new instance of a weatherReading object
   */
  protected WeatherReading adwr(int airTemp, int dewPointTemp, int windSpeed, int totalRainRec) {
    return new StevensonReading(airTemp, dewPointTemp, windSpeed, totalRainRec);
  }

  @Test
  public void testToString() {
    String expected = "Reading: T = 9, D = 8, v = 7, rain = 6";
    assertEquals(expected, a9d8w7r6.toString());

    assertEquals("test high T, low D, normal v, normal rain",
        "Reading: T = 40, D = 8, v = 7, rain = 6", adwr(40, 8,
            7, 6).toString());
    assertEquals("test high T, high D, normal v, normal rain",
        "Reading: T = 40, D = 39, v = 7, rain = 6", adwr(40, 39,
            7, 6).toString());
    assertEquals("test high T, high D, high v, normal rain",
        "Reading: T = 40, D = 39, v = 70, rain = 6", adwr(40, 39,
            70, 6).toString());
    assertEquals("test high T, high D, high v, high rain",
        "Reading: T = 40, D = 39, v = 70, rain = 160", adwr(40, 39,
            70, 160).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDewPointTemp() {
    //check by creating an object with invalid dew point temp
    //the dew point temperature in Celsius which cannot be greater than the air temperature,
    int airTemp = 10;
    int dewPointTemp = 12; // this is invalid for it is greater than airTemp
    int windSpeed = 10;
    int totalRainRec = 10;

    WeatherReading wr = new StevensonReading(airTemp, dewPointTemp, windSpeed, totalRainRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWindSpeed() {
    //check by creating an object with invalid wind speed
    int airTemp = 10;
    int dewPointTemp = 9;
    int windSpeed = -10; // invalid windSpeed
    int totalRainRec = 10;

    WeatherReading wr = new StevensonReading(airTemp, dewPointTemp, windSpeed, totalRainRec);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTotalRainRec() {
    //check by creating an object with invalid total rain received
    int airTemp = 10;
    int dewPointTemp = 9;
    int windSpeed = 10;
    int totalRainRec = -10; // invalid totalRainRec

    WeatherReading wr = new StevensonReading(airTemp, dewPointTemp, windSpeed, totalRainRec);
  }

  @Test
  public void testGetTemperature() {
    int expected = 9;
    assertEquals(expected, a9d8w7r6.getTemperature());
  }

  @Test
  public void testGetDewPoint() {
    int expected = 8;
    assertEquals(expected, a9d8w7r6.getDewPoint());
  }

  @Test
  public void testGetWindSpeed() {
    int expected = 7;
    assertEquals(expected, a9d8w7r6.getWindSpeed());
  }

  @Test
  public void testGetTotalRain() {
    int expected = 6;
    assertEquals(expected, a9d8w7r6.getTotalRain());
  }

  @Test
  public void testGetRelativeHumidity() {
    assertEquals(89, a9d8w7r6.getRelativeHumidity());
  }

  @Test
  public void testGetHeatIndex() {
    assertEquals(31, a9d8w7r6.getHeatIndex());
  }

  @Test
  public void testGetWindChill() {
    assertEquals(7, a9d8w7r6.getWindChill());
  }

  @Test
  public void testEquals() {
    WeatherReading a = adwr(5, 4, 3, 2);
    WeatherReading b = adwr(5, 4, 3, 2);
    assertEquals(a, b);
  }

  @Test
  public void testHashCode() {
    WeatherReading a = adwr(5, 4, 3, 2);
    WeatherReading b = adwr(5, 4, 3, 2);
    assertEquals(a.hashCode(), b.hashCode());
  }
}