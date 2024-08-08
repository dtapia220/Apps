import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeManagerApp {
    private RecipeManager recipeManager;
    private JFrame frame;
    private JList<Recipe> recipeList;
    private DefaultListModel<Recipe> listModel;

    public RecipeManagerApp() {
        recipeManager = new RecipeManager();
        frame = new JFrame("Recipe Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(recipeList);

        JButton addButton = new JButton("Add Recipe");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecipe();
            }
        });

        JButton editButton = new JButton("Edit Recipe");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editRecipe();
            }
        });

        JButton deleteButton = new JButton("Delete Recipe");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecipe();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        frame.getContentPane().add(listScrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addRecipe() {
        Recipe recipe = getRecipeFromUser(null);
        if (recipe != null) {
            recipeManager.addRecipe(recipe);
            listModel.addElement(recipe);
        }
    }

    private void editRecipe() {
        Recipe selectedRecipe = recipeList.getSelectedValue();
        if (selectedRecipe != null) {
            Recipe updatedRecipe = getRecipeFromUser(selectedRecipe);
            if (updatedRecipe != null) {
                selectedRecipe.setName(updatedRecipe.getName());
                selectedRecipe.setIngredients(updatedRecipe.getIngredients());
                selectedRecipe.setInstructions(updatedRecipe.getInstructions());
                recipeList.repaint();
            }
        }
    }

    private void deleteRecipe() {
        Recipe selectedRecipe = recipeList.getSelectedValue();
        if (selectedRecipe != null) {
            recipeManager.deleteRecipe(selectedRecipe);
            listModel.removeElement(selectedRecipe);
        }
    }

    private Recipe getRecipeFromUser(Recipe recipe) {
        JTextField nameField = new JTextField(10);
        JTextArea ingredientsArea = new JTextArea(5, 20);
        JTextArea instructionsArea = new JTextArea(5, 20);

        if (recipe != null) {
            nameField.setText(recipe.getName());
            ingredientsArea.setText(recipe.getIngredients());
            instructionsArea.setText(recipe.getInstructions());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Ingredients:"));
        panel.add(new JScrollPane(ingredientsArea));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Instructions:"));
        panel.add(new JScrollPane(instructionsArea));

        int result = JOptionPane.showConfirmDialog(frame, panel, "Recipe Details", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String ingredients = ingredientsArea.getText();
            String instructions = instructionsArea.getText();
            return new Recipe(name, ingredients, instructions);
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RecipeManagerApp();
            }
        });
    }
}
