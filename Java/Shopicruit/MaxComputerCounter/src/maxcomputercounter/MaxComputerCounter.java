/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maxcomputercounter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import jdk.nashorn.api.scripting.URLReader;

/**
 *
 * @author dominikschmidtlein
 */
public class MaxComputerCounter {
    
    private static final double TAX_RATE = 0.13;
    private static final int GRAM_THRESHOLD = 100000;
    private static final double SHIPPING_COST_PER_GRAM = 0.0;
    private static final String REQUIRES_SHIPPING_KEY = "requires_shipping";
    private static final String TAXABLE_KEY = "taxable";
    private static final String PRICE_PER_GRAM = "price_per_gram";
    private static final String PRICE = "price";
    private static final String GRAMS_KEY = "grams";
    private static final String PRICE_KEY = PRICE;
    private static final String PRICE_PER_GRAM_KEY = PRICE_PER_GRAM;
    private static final String VARIANTS_KEY = "variants";
    private static final String COMPUTER_KEY = "Computer";
    private static final String KEYBOARD_KEY = "Keyboard";
    private static final String PRODUCT_TYPE_KEY = "product_type";
    private static final String PRODUCTS_KEY = "products";
    

    
    private static void sortByPricePerGrams(LinkedList<JsonObject> list){
       boolean swap;
       JsonObject temp;
       do{
           swap = false;
          for(int i = 0; i < list.size() - 1; i ++){
              if(list.get(i).get(PRICE_PER_GRAM).getAsFloat() < list.get(i + 1).get(PRICE_PER_GRAM).getAsFloat()){
                  temp = list.get(i);
                  list.set(i, list.get(i+1));
                  list.set(i+1, temp);
                  swap = true;
              }
          }
       }while(swap);
    }
    
    public static void main(String[] args) {
        
        try{
            int grams = 0;
            double cost = 0;
            int itemGrams;
            double itemCost;
            
            LinkedList<JsonObject> variants = new LinkedList<>();
            
            JsonParser parser = new JsonParser();
            
            JsonObject shopicruitData = (JsonObject) parser.parse(
                    new URLReader(new URL("http://shopicruit.myshopify.com/products.json")));
            
            JsonArray products = shopicruitData.getAsJsonArray(PRODUCTS_KEY);
            for(JsonElement product: products){
                
                JsonObject jProduct = product.getAsJsonObject();
                if(jProduct.get(PRODUCT_TYPE_KEY).getAsString().equals(KEYBOARD_KEY) ||
                        jProduct.get(PRODUCT_TYPE_KEY).getAsString().equals(COMPUTER_KEY))
                    
                    for(JsonElement variant: jProduct.getAsJsonArray(VARIANTS_KEY)){
                        JsonObject castedVariant = variant.getAsJsonObject();
                        
                        castedVariant.addProperty(PRICE_PER_GRAM_KEY, new Double(
                                castedVariant.get(PRICE_KEY).getAsDouble()/
                                        castedVariant.get(GRAMS_KEY).getAsInt()));
                        
                        variants.add(castedVariant);
                        
                    }
            }
            
            sortByPricePerGrams(variants);
            
            for(JsonObject variant : variants){
                itemGrams = variant.get(GRAMS_KEY).getAsInt();
                itemCost = variant.get(PRICE).getAsDouble();
                
                if(variant.get(TAXABLE_KEY).getAsBoolean())
                    itemCost *= 1+TAX_RATE;
                
                if(variant.get(REQUIRES_SHIPPING_KEY).getAsBoolean())
                    itemCost += itemGrams * SHIPPING_COST_PER_GRAM;
                
                cost += itemCost;
                grams += itemGrams;
                
                if(grams > GRAM_THRESHOLD)
                    break;
            }
            
            System.out.println("Maximum cost: " + Math.round(cost*100)/100.0);
            
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    
    
}
