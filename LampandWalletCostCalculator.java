package lampandwalletcostcalculator;

import java.net.URL;
import jdk.nashorn.api.scripting.URLReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author dominikschmidtlein
 */
public class LampandWalletCostCalculator {

    public static final String TRUE = "true";
    public static final double TAX = 0.13; //sales tax ON, Canada
    public static final double COSTPERGRAMSRATE = 0.005; //estimate
    
    private static double cost;
    
    public static void main(String[] args) {
        cost = 0;
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new URLReader(new URL(
                    "http://shopicruit.myshopify.com/products.json")));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray productsArray = (JSONArray)jsonObject.get("products");
            
            for(Object product: productsArray){
                JSONObject jProduct = (JSONObject) product;
                String productType = (String) jProduct.get("product_type");
                if(productType.equals("Wallet") || productType.equals("Lamp")){
                    JSONArray variantsArray = (JSONArray) jProduct.get("variants");
                    for(Object variant: variantsArray){
                        JSONObject jVariant = (JSONObject) variant;
                        if(jVariant.get("available").toString().equals(TRUE)){
                            double price = Double.parseDouble(jVariant.get("price").toString());
                            if(jVariant.get("taxable").toString().equals(TRUE))
                                price = price * (1 + TAX);
                            if(jVariant.get("requires_shipping").toString().equals(TRUE))
                                price += COSTPERGRAMSRATE * Double.parseDouble(
                                        jVariant.get("grams").toString()); //shipping estimate already includes taxes
                            cost += price;
                        }
                    }
                }              
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        cost = cost * 100;
        int roundedCost = (int) cost;
        cost = roundedCost/100.0;
        
        System.out.println("Cost: $" + cost);
    }
    
}
