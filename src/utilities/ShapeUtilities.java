package utilities;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;


/**
 * This class is used for creating shape containing only points and the desired parameters (name, color, value, etc.). The points can be selected by
 * creating a {@code LinkedHashMap<String, Object>} with the corresponding data of them.</br>
 * </br>
 * Eg.:</br>
 * {@code LinkedHashMap<String, Object> Point = new  LinkedHashMap<String, Object>();}</br>
 * {@code Point.put("x",8.6);}</br>
 * {@code Point.put("y",9.6);}</br>
 * {@code Point.put("name","point1");}</br>
 * </br>
 * If a shape containing a series of points is desired, these points can be added to a {@code List<LinkedHashMap<String,Object>>}</br>
 * </br>
 * Eg.:</br>
 * {@code List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();}</br>
 * {@code list.add(Point1);}</br>
 * {@code list.add(Point2);}</br>
 * {@code list.add(Point3);}</br>
 * {@code list.add(Point4);}</br>
 * {@code list.add(Point5);}</br> * 
 *
 */
public class ShapeUtilities {

  /**
   * Exemple case.
   * 
   * @throws Exception
   */
  public static void main(String args[]) throws Exception {

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    Map<String, Object> feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -41.0);
    feature.put("name", "MG-01");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 5.5);
    list.add(feature);

    feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -42.0);
    feature.put("name", "MG-02");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 4.2);
    list.add(feature);

    feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -43.0);
    feature.put("name", "MG-03");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 4.2);
    list.add(feature);

    feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -44.0);
    feature.put("name", "MG-04");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 4.2);
    list.add(feature);

    feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -45.0);
    feature.put("name", "MG-05");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 4.2);
    list.add(feature);

    feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -46.0);
    feature.put("name", "MG-06");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 4.2);
    list.add(feature);

    feature = new LinkedHashMap<String, Object>();
    feature.put("x", -10.0);
    feature.put("y", -47.0);
    feature.put("name", "MG-07");
    feature.put("parameter", "Antim�nio");
    feature.put("value", 4.2);
    list.add(feature);

    ShapeUtilities.writePointShape("c:/teste/shapesaida.shp", list);
  }


  /**
   * Overloading of the method. The only difference is that this version only will be used if the user desires to create a shape containing only one
   * point, this way, the method will encapsulate the {@code LinkedHashMap} on a {@code List} and then call the method responsible for creating the
   * shape.
   * 
   * @param fileOut
   * @param featureList
   * @throws ObjectNotFoundException
   * @throws SchemaException
   * @throws IOException
   */
  public void writePointShape1(String fileOut, Map<String, Object> featureList) throws NullPointerException,SchemaException, IOException {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    list.add(featureList);
    writePointShape(fileOut, list);
  }


  /**
   * Use this method for create a shape file containing the points of the list. At least one pair of {@code double} values x and y (longitude and
   * latitude) is needed for creating a point. Otherwise, an {@code ObjectNotFoundException} will be throwed.</br>
   * If only one point is needed, you can use this same method overcharged by passing a {@code (String) fileOut} and a {@code LinkedHashMap}.</br>
   * </br>
   * Eg.:<br/>
   * {@code List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();}<br/>
   * {@code Map<String, Object> feature = new LinkedHashMap<String, Object>();}<br/>
   * {@code feature.put("x", -20);}<br/>
   * {@code feature.put("y", -42);}<br/>
   * {@code feature.put("name", "Point 7");}<br/>
   * {@code feature.put("param", "Gold");}<br/>
   * 
   * {@code feature = new LinkedHashMap<String, Object>();}<br/>
   * {@code feature.put("x", -20);}<br/>
   * {@code feature.put("y", -42);}<br/>
   * {@code feature.put("name", "Point 7");}<br/>
   * {@code feature.put("param", "Gold");}<br/>
   * 
   * {@code list.add(feature);}<br/>
   * {@code try} {<br/>
   * {@code  ShapeUtilities.writePointShape(fileOutPath, list);}<br/>
   * }<br/>
   * 
   * @param fileOut
   * @param featureList
   * @throws SchemaException
   * @throws IOException
   * @throws ClassCastException
   */
  public static void writePointShape(String fileOut, List<Map<String, Object>> featureList) throws NullPointerException, SchemaException, IOException, ClassCastException {

    SimpleFeatureType TYPE = null;
    /*
     * A list to collect features as we create them.
     */
    List<SimpleFeature> features = new ArrayList<SimpleFeature>();
    for (Map<String, Object> map : featureList) {
      String createType = "";
      for (Entry<String, Object> entry : map.entrySet()) {
        if (!(entry.getKey().toLowerCase().equals("x") || entry.getKey().toLowerCase().equals("y"))) {
          createType += entry.getKey() + ":" + entry.getValue().getClass().getSimpleName() + ",";
        }
      }
      if (createType.endsWith(","))
        createType = createType.substring(0, createType.length() - 1);
      TYPE = DataUtilities.createType("Location", "the_geom:Point:srid=4326," + // <- the geometry attribute: Point type
          createType);
      /*
       * GeometryFactory will be used to create the geometry attribute of each feature, using a Point object for the location.
       */
      GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
      if (!(map.keySet().contains("x") || map.keySet().contains("X")) || !(map.keySet().contains("y") || map.keySet().contains("Y")))
        throw new NullPointerException("'x' or 'y' values not found.");

      Object x = map.get("x") == null ? map.get("X") : map.get("x"); //Longitude
      Object y = map.get("y") == null ? map.get("Y") : map.get("y"); //Latitude
      

      if (!(x instanceof Double) || !(y instanceof Double))
        throw new ClassCastException("'x' and 'y' values should be represented as double or Double inputs");
      
      Point point = geometryFactory.createPoint(new Coordinate((double) x, (double) y));
      SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
      featureBuilder.add(point);
      if (map.entrySet().size() > 2) {
        for (Entry<String, Object> entry : map.entrySet()) {
          if (!(entry.getKey().toLowerCase().equals("x") || entry.getKey().toLowerCase().equals("y")))
            featureBuilder.add(entry.getValue());
        }
      }
      SimpleFeature feature = featureBuilder.buildFeature(null);
      features.add(feature);
    }

    ShapefileDataStore newDataStore = getDataStore(fileOut);

    /*
     * TYPE is used as a template to describe the file contents
     */
    newDataStore.createSchema(TYPE);
    Transaction transaction = new DefaultTransaction("create");

    String typeName = newDataStore.getTypeNames()[0];
    SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
    SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();

    /*
     * The Shapefile format has a couple limitations: - "the_geom" is always first, and used for the geometry attribute name - "the_geom" must be of
     * type Point, MultiPoint, MuiltiLineString, MultiPolygon - Attribute names are limited in length - Not all data types are supported (example
     * Timestamp represented as Date)
     * 
     * Each data store has different limitations so check the resulting SimpleFeatureType.
     */
    System.out.println("SHAPE: " + SHAPE_TYPE);

    if (featureSource instanceof SimpleFeatureStore) {
      SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
      /*
       * SimpleFeatureStore has a method to add features from a SimpleFeatureCollection object, so we use the ListFeatureCollection class to wrap our
       * list of features.
       */
      SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
      featureStore.setTransaction(transaction);
      try {
        featureStore.addFeatures(collection);
        transaction.commit();
      }
      catch (Exception problem) {
        problem.printStackTrace();
        transaction.rollback();
      }
      finally {
        transaction.close();
      }
    }
    else {
      System.out.println(typeName + " does not support read/write access");
    }

  }


  /**
   * Returns the ShapefileDataStore
   * 
   * @param fileOut
   *          shape output file
   * @return
   * @throws IOException
   */
  private static ShapefileDataStore getDataStore(String fileOut) throws IOException {

    ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
    Map<String, Serializable> params = new HashMap<String, Serializable>();
    params.put("url", (new File(fileOut)).toURI().toURL());
    params.put("create spatial index", Boolean.TRUE);

    return (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

  }



  public Geometry searchAttribute(SimpleFeatureSource layer, String attributeName, String attributeValue) {
    try {
      SimpleFeatureCollection selectedFeatures = layer.getFeatures();
      SimpleFeatureIterator iter = selectedFeatures.features();
      try {
        while (iter.hasNext()) {
          SimpleFeature feature = iter.next();
          if (feature.getAttribute(attributeName).toString().equals(attributeValue)) {
            System.out.println(feature.getAttribute(attributeName));
            return (Geometry) feature.getAttribute(0);
          }
        }
      }
      finally {
        iter.close();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
