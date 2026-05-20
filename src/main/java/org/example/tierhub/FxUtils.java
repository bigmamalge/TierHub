package org.example.tierhub;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class FxUtils {

    public static List<HBox> findAllHBoxes(Parent root) {
        List<HBox> result = new ArrayList<>();
        traverse(root, result);
        return result;
    }

    private static void traverse(Node node, List<HBox> result) {

        if (node instanceof HBox hbox) {
            result.add(hbox);
        }

        if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                traverse(child, result);
            }
        }
    }
}