package org.unisys.jmsrest;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreditCardDetails")
public class CreditCardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> attributes = new HashMap<>();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String jsonObject;
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        JSONObject obj1 = new JSONObject();
        JSONArray list = new JSONArray();
        int i = 2;
        while (i >= 0) {
            /*output.add( "Read from DB: " + rs.getBigDecimal("Card Number"));
            output.add( "Read from DB: " + rs.getBigDecimal("Bill Amount"));
            output.add( "Read from DB: " + rs.getString("Card Type"));
            output.add( "Read from DB: " + rs.getBigDecimal("Credit Amount"));
            output.add( "Read from DB: " + rs.getBigDecimal("Debit Amount"));
            output.add( "Read from DB: " + rs.getDate("Due Date"));*/
      		obj1.put("Card Number","Card Number");
      		obj1.put("Bill Amount","Bill Amount");
      		obj1.put("Card Type","Card Type");
      		i--;
      		list.add(obj1);
         }
      	 obj.put("credit card", list);
      	 out.print(obj);
         out.flush();	
	}
}
