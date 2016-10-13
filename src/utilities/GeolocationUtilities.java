package utilities;

import org.gps.utils.LatLonPoint;
import org.gps.utils.ReferenceEllipsoids;
import org.gps.utils.UTMPoint;
import org.gps.utils.UTMUtils;

import beans.Point;
import beans.SamplingPoint;

/**
 * This class provides simple static methods for geolocation data conversion
 * 
 * @see Point
 * @see PontoDeAmostragem
 */
public class GeolocationUtilities {

  public static Point convertPointToLatLong(Point point, int zone) throws Exception {
    char letter;

    if (point.getY() > 5216980)
      letter = 'S';
    else
      letter = 'N';

    letter = regulateLetter(letter);
    UTMPoint p = new UTMPoint(point.getY(), point.getX(), zone, letter);
    LatLonPoint l = UTMUtils.UTMtoLL(ReferenceEllipsoids.WGS_84, p);
    point.setX(l.getLongitude());
    point.setY(l.getLatitude());

    return point;
  }


  public static SamplingPoint convertPointToLatLong(SamplingPoint point) throws Exception {

    char letter;

    if (point.getLatitude() > 5216980)
      letter = 'S';
    else
      letter = 'N';

    letter = regulateLetter(letter);
    UTMPoint pointUtm = new UTMPoint(point.getLatitude(), point.getLongitude(), Utilities.parseToInteger(point.getDatum()), letter);
    LatLonPoint latLongPoint = UTMUtils.UTMtoLL(ReferenceEllipsoids.WGS_84, pointUtm);
    point.setLongitude(latLongPoint.getLongitude());
    point.setLatitude(latLongPoint.getLatitude());

    return point;
  }

  /**
   * Defines de letter for UTM conversion.
   * @param letter
   * @return
   * @throws Exception
   */
  public static char regulateLetter(char letter) throws Exception {
    if (letter == 'N')
      return 'P';
    if (letter == 'S')
      return 'L';
    throw new Exception("Letra '" + letter + "' não permida. Tipos permitidos {'N','S'}");

  }

/**
 * Convert meridian to zone.
 * @param meridian
 * @return
 */
  public static int convertMeridianToZone(String meridian) {
    Integer meridianInt = Integer.parseInt(meridian);
    Integer zone = 1 + (meridianInt + 177) / 6;
    System.out.println(zone);
    return zone;
  }

}
