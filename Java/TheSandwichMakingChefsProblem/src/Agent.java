import java.util.ArrayList;

public class Agent implements Runnable {

	Table table;
	int sandwichLimit;
	
	public Agent(int sandwichLimit, Table table) {
		this.table = table;
		this.sandwichLimit = sandwichLimit;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < sandwichLimit; i ++){
			ArrayList<Ingredient> ingredients = chooseIngredients();
			table.put(ingredients);
			System.out.println("Round " + (i + 1) + ", agent put " + ingredients.get(0) + " " + ingredients.get(1));
		}
	}

	private ArrayList<Ingredient> chooseIngredients() {
		ArrayList<Ingredient> ingredients = new ArrayList<>(2);
		double rand = Math.random();
		if(rand < (1.0/3.0)){
			ingredients.add(Ingredient.PEANUT_BUTTER);
			ingredients.add(Ingredient.BREAD);
		}	
		else if(rand < (2.0/3.0)){
			ingredients.add(Ingredient.PEANUT_BUTTER);
			ingredients.add(Ingredient.JAM);
		}
		else{
			ingredients.add(Ingredient.BREAD);
			ingredients.add(Ingredient.JAM);
		}
		return ingredients;
	}

}
