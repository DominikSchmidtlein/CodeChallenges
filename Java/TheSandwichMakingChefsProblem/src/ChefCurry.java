import java.util.ArrayList;

public class ChefCurry implements Runnable {

	private String name;
	private ArrayList<Ingredient> neededIngredients;
	private Table table;
	private int sandwichLimit;
	
	public ChefCurry(Table table, Ingredient ingredient, int sandwichLimit) {		
		this.name = "CHEF " + ingredient.toString();
		this.table = table;
		this.sandwichLimit = sandwichLimit;
		neededIngredients = new ArrayList<>(3);
		
		neededIngredients.add(Ingredient.PEANUT_BUTTER);
		neededIngredients.add(Ingredient.JAM);
		neededIngredients.add(Ingredient.BREAD);
		
		neededIngredients.remove(ingredient);		
	}
	
	@Override
	public void run() {
		while(table.getNumberOfSandwichesMade() < sandwichLimit){
			if(table.get(neededIngredients) == null)
				break;
			System.out.println(name + " got " + neededIngredients.get(0) + ", " + neededIngredients.get(1));
			this.withThePot();
		}
		System.out.println(name + " is done");
	}
	
	private void withThePot(){
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(name + " made a sandwich.");
	}
	
}
