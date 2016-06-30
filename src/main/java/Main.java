import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

    get("/db", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = DatabaseUrl.extract().getConnection();

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }, new FreeMarkerEngine());

    get("/CreditCardDetails", (req,res) ->	{
    	Connection connection = null;
        Map<String, Object> attributes = new HashMap<>();
        //.setContentType("application/json");
        //res.setCharacterEncoding("UTF-8");
        String jsonObject;
        try {
          connection = DatabaseUrl.extract().getConnection();
          Statement stmt = connection.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT \"Card Number\", \"Bill Amount\", \"Card Type\", "
          		+ 	"\"Due Date\", \"Credit Amount\", \"Debit Amount\" FROM public.\"CreditCardBill\" ");

          ArrayList<String> output = new ArrayList<String>();
          PrintWriter out = response.getWriter();
          JSONObject obj = new JSONObject();
          JSONObject obj1 = new JSONObject();
          JSONArray list = new JSONArray();
      	  //list.add("msg 1");
      	  
      	  while (rs.next()) {
            /*output.add( "Read from DB: " + rs.getBigDecimal("Card Number"));
            output.add( "Read from DB: " + rs.getBigDecimal("Bill Amount"));
            output.add( "Read from DB: " + rs.getString("Card Type"));
            output.add( "Read from DB: " + rs.getBigDecimal("Credit Amount"));
            output.add( "Read from DB: " + rs.getBigDecimal("Debit Amount"));
            output.add( "Read from DB: " + rs.getDate("Due Date"));*/
      		obj1.put("Card Number",rs.getBigDecimal("Card Number"));
      		obj1.put("Bill Amount",rs.getBigDecimal("Bill Amount"));
      		obj1.put("Card Type",rs.getString("Card Type"));
      		obj1.put("Crebit Amount",rs.getBigDecimal("Crebit Amount"));
      		obj1.put("Debit Amount",rs.getBigDecimal("Debit Amount"));
      		obj1.put("Due Date",rs.getDate("Due Date"));
      		list.add(obj1);
          }
      	  obj.put("credit card", list);
          out.print(obj);
          out.flush();
          attributes.put("results", obj1);
          return new ModelAndView(attributes, "json.ftl");
        } catch (Exception e) {
          attributes.put("message", "There was an error: " + e);
          return new ModelAndView(attributes, "error.ftl");
        } finally {
          if (connection != null) try{connection.close();} catch(SQLException e){}
        }
    }, new FreeMarkerEngine());
  }

}
