package maxcomputercounter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import jdk.nashorn.api.scripting.URLReader;

/**
 *
 * @author dominikschmidtlein
 */
public class MaxComputerCounter {
    
    //Sales tax Ontario
    private static final double TAX_RATE = 0.13;
    private static final int GRAM_THRESHOLD = 100000;
    private static final double SHIPPING_COST_PER_GRAM = 0.0;
    private static final String REQUIRES_SHIPPING_KEY = "requires_shipping";
    private static final String TAXABLE_KEY = "taxable";
    private static final String GRAMS_KEY = "grams";
    private static final String PRICE_KEY = "price";
    private static final String VARIANTS_KEY = "variants";
    private static final String COMPUTER_KEY = "Computer";
    private static final String KEYBOARD_KEY = "Keyboard";
    private static final String PRODUCT_TYPE_KEY = "product_type";
    private static final String PRODUCTS_KEY = "products";

    /*Search the array of products for keyboards and computers. Add all 
     variants of each computer and keyboard to the variants list. */
    private static void getVariants(JsonArray products, ArrayList<JsonObject> variants) {
        for (JsonElement product : products) {

            JsonObject jProduct = product.getAsJsonObject();
            if (jProduct.get(PRODUCT_TYPE_KEY).getAsString().equals(KEYBOARD_KEY)
                    || jProduct.get(PRODUCT_TYPE_KEY).getAsString().equals(COMPUTER_KEY)) {
                for (JsonElement element : jProduct.getAsJsonArray(VARIANTS_KEY)) {

                    variants.add(element.getAsJsonObject());
                }
            }
        }
    }
    
    /*A recursive function for calculating the highest possible cost of a 100kg
    order. For each variant in the remainingVariant array, the function
    simulates a case where the variant is selected and where it is not selected.
    Taxes and shipping costs are then added on if necessary.
    Base case: if the total grams of the selection exceeds the threshold,
    this is not a valid case (return -1)
    Base case: if there are no more remaining variants to choose, the simulation
    is complete.
    
    input selVarCost: the total cost of the variants in selVar
    input selVarGrams: the total mass in grams of the variants in selVar
    input selVar (selectedVariants): variants that are in the "cart"
    input remVar (remainingVariants): list of remaining variants to choose from
    
    output: the highest possible cost of computers and keyboards that weigh under
    100kg*/
    private static double getHighestCost(double selVarCost, int selVarGrams, 
            ArrayList<JsonObject> selVar, ArrayList<JsonObject> remVar){
        
        if(selVarGrams > GRAM_THRESHOLD){
            return -1;
        }
        if(remVar.isEmpty()){
            return selVarCost;
        }
        
        int tempGrams;
        double tempCost;
        double taxCost;
        double shippingCost;
        
        ArrayList<JsonObject> tempSelVar = (ArrayList < JsonObject >) selVar.clone();
        ArrayList<JsonObject> tempRemVar = (ArrayList < JsonObject >) remVar.clone();
        
        JsonObject tempVariant = tempRemVar.get(0);
        tempGrams = tempVariant.get(GRAMS_KEY).getAsInt();
        tempCost = tempVariant.get(PRICE_KEY).getAsDouble();
        
        tempSelVar.add(tempVariant);
        tempRemVar.remove(tempVariant);
        
        //consider taxes
        taxCost = tempVariant.get(TAXABLE_KEY).getAsBoolean()? TAX_RATE * tempCost : 0;
        //consider shipping
        shippingCost = tempVariant.get(REQUIRES_SHIPPING_KEY).getAsBoolean()? 
                SHIPPING_COST_PER_GRAM * tempGrams : 0;
        
        //don't select temp
        double costWithout = getHighestCost(selVarCost, selVarGrams, selVar, tempRemVar);
        //selects temp
        double costWith = getHighestCost(selVarCost + tempCost + taxCost + shippingCost, 
                selVarGrams + tempGrams, tempSelVar, tempRemVar);
        
        return costWithout > costWith ? costWithout : costWith;
    }

    /*Main is responsible for connecting to the array and extracting the
    products array. Main then calls other functions to extract data from the
    json array and get the highest possible cost.*/
    public static void main(String[] args) {

        double cost;
        
        ArrayList<JsonObject> variants = new ArrayList<>();
        JsonParser parser = new JsonParser();
        
        try {
            JsonObject shopicruitData = (JsonObject) parser.parse(
                    new URLReader(new URL("http://shopicruit.myshopify.com/products.json")));

            JsonArray products = shopicruitData.getAsJsonArray(PRODUCTS_KEY);
            getVariants(products, variants);

            cost = getHighestCost(0, 0, new ArrayList<>(), variants);

            System.out.println("Maximum cost: $" + Math.round(cost * 100) / 100.0);

        } catch (MalformedURLException | JsonIOException | JsonSyntaxException e) {
            System.exit(0);
        }
    }
}