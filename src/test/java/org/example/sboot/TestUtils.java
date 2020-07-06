package org.example.sboot;

import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TestUtils {
  public static Date getDate(String dateStr) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      return new Date(formatter.parse(dateStr).getTime());
    } catch (Exception ex) {
      return null;
    }
  }

  public static void sleep(int numOfSecs) {
    try {
      Thread.sleep(numOfSecs * 1000);
    } catch (Exception ex) {
    }
  }

  public static JSONObject getAsJson(String jsonStr) {
    try {
      return new JSONObject(jsonStr);
    } catch (Exception ex) {
      return null;
    }
  }
}
